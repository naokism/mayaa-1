/*
 * Copyright (c) 2004-2005 the Seasar Foundation and the Others.
 * 
 * Licensed under the Seasar Software License, v1.1 (aka "the License"); you may
 * not use this file except in compliance with the License which accompanies
 * this distribution, and is available at
 * 
 *     http://www.seasar.org/SEASAR-LICENSE.TXT
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.seasar.maya.impl.engine.processor;

import java.util.Iterator;
import java.util.List;

import org.cyberneko.html.HTMLElements;
import org.seasar.maya.cycle.ServiceCycle;
import org.seasar.maya.engine.processor.ProcessorProperty;
import org.seasar.maya.engine.specification.Namespaceable;
import org.seasar.maya.engine.specification.NodeNamespace;
import org.seasar.maya.engine.specification.QName;
import org.seasar.maya.engine.specification.QNameable;
import org.seasar.maya.impl.CONST_IMPL;
import org.seasar.maya.impl.cycle.AbstractServiceCycle;
import org.seasar.maya.impl.engine.specification.QNameImpl;
import org.seasar.maya.impl.engine.specification.QNameableImpl;
import org.seasar.maya.impl.util.StringUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class ElementProcessor extends AbstractAttributableProcessor
		implements CONST_IMPL {

	private static final long serialVersionUID = 923306412062075314L;

    private String _namespaceURI;
    private String _localName;
    private String _prefix;
	private QNameable _name;
    private boolean _duplicated;

    // exported property
    public boolean isDuplicated() {
    	return _duplicated;
    }
    
    // MLD property
    public void setDuplicated(boolean duplicated) {
        _duplicated = duplicated;
    }
    
    // MLD property
    public void setName(QNameable name) {
        if(name == null) {
            throw new IllegalArgumentException();
        }
        _name = name;
    }
    
    // MLD property
    public void setNamespaceURI(String namespaceURI) {
        if(StringUtil.isEmpty(namespaceURI)) {
            throw new IllegalArgumentException();
        }
        _namespaceURI = namespaceURI;
    }
    
    // MLD property
    public void setLocalName(String localName) {
        if(StringUtil.isEmpty(localName)) {
            throw new IllegalArgumentException();
        }
        _localName = localName;
    }
    
    // MLD property
    public void setPrefix(String prefix) {
        if(prefix == null) {
            throw new IllegalArgumentException();
        }
        _prefix = prefix;
    }
    
    private boolean isHTML(QName qName) {
        String namespaceURI = qName.getNamespaceURI();
        return URI_HTML.equals(namespaceURI);
    }
    
    private boolean needsCloseElement(QName qName) {
        if(isHTML(qName)) {
            String localName = qName.getLocalName();
            HTMLElements.Element element = HTMLElements.getElement(localName);
            return element.isEmpty() == false;
        }
        return getChildProcessorSize() > 0;
    }
    
    private void appendAttributeString(StringBuffer buffer, ProcessorProperty prop) {
        QName qName = prop.getName().getQName();
        if(URI_MAYA.equals(qName.getNamespaceURI())) {
            return;
        }
        buffer.append(" ");
        String attrPrefix = prop.getName().getPrefix();
        if(StringUtil.hasValue(attrPrefix)) {
            buffer.append(attrPrefix).append(":");
        }
        buffer.append(qName.getLocalName());
        buffer.append("=\"").append(prop.getValue().execute()).append("\"");
    }
    
    private void write(StringBuffer buffer) {
        ServiceCycle cycle = AbstractServiceCycle.getServiceCycle();
        cycle.getResponse().write(buffer.toString());
    }
    
    private void checkName() {
        if(_name == null) {
            if(StringUtil.isEmpty(_namespaceURI) || 
                    StringUtil.isEmpty(_localName) || _prefix == null) {
                throw new IllegalStateException();
            }
            QName qName = new QNameImpl(_namespaceURI, _localName);
            QNameableImpl name = new QNameableImpl(qName);
            name.addNamespace(_prefix, _namespaceURI);
            _name = name;
        }
    }
    
    protected ProcessStatus writeStartElement() {
        checkName();
        StringBuffer buffer = new StringBuffer("<");
        String prefix = _name.getPrefix();
        if(StringUtil.hasValue(prefix)) {
            buffer.append(prefix).append(":");
        }
        QName qName = _name.getQName();
        buffer.append(qName.getLocalName());
        if(isHTML(qName) == false) {
            Namespaceable space = _name.getParentScope();
            if(space == null) {
                throw new IllegalArgumentException();
            }
            for(Iterator it = space.iterateNamespace(false);
                    it.hasNext(); ) {
                NodeNamespace mapping = (NodeNamespace)it.next();
                String pre = mapping.getPrefix();
                String uri = mapping.getNamespaceURI();
                if(StringUtil.hasValue(pre)) {
                    buffer.append(" xmlns:").append(pre);
                } else {
                    buffer.append(" xmlns");
                }
                buffer.append("=\"").append(uri).append("\"");
            }
        }
        List additionalAttributes = getProcesstimeProperties();
        for(Iterator it = additionalAttributes.iterator(); it.hasNext(); ) {
            ProcessorProperty prop = (ProcessorProperty)it.next();
            QName propQName = prop.getName().getQName(); 
            if(_duplicated && (QH_ID.equals(propQName) ||
                    QX_ID.equals(propQName))) {
                continue;
            }
            appendAttributeString(buffer, prop);
        }
        for(Iterator it = getInformalProperties().iterator(); it.hasNext(); ) {
            ProcessorProperty prop = (ProcessorProperty)it.next();
            if(additionalAttributes.contains(prop) == false) {
                appendAttributeString(buffer, prop);
            }
        }
        if(isHTML(qName) || getChildProcessorSize() > 0) {
            buffer.append(">");
        } else {
            buffer.append("/>");
        }
        write(buffer);
        return EVAL_BODY_INCLUDE;
    }
    
    protected void writeEndElement() {
        if(_name == null) {
            throw new IllegalStateException();
        }
        QName qName = _name.getQName();
        if(needsCloseElement(qName)) {
            StringBuffer buffer = new StringBuffer("</");
            String prefix = _name.getPrefix();
            if(StringUtil.hasValue(prefix)) {
                buffer.append(prefix).append(":");
            }
            buffer.append(qName.getLocalName()).append(">");
            write(buffer);
        }
    }
    
}
