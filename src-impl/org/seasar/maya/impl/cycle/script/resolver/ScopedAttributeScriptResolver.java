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
package org.seasar.maya.impl.cycle.script.resolver;

import org.seasar.maya.cycle.script.resolver.ScriptResolver;
import org.seasar.maya.impl.util.CycleUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class ScopedAttributeScriptResolver implements ScriptResolver {

    public void putParameter(String name, String value) {
        throw new UnsupportedOperationException();
    }
    
    public Object getVariable(String name) {
        Object value = CycleUtil.findAttribute(name);
        if(value != null) {
            return value;
        }
        return UNDEFINED; 
    }

	public boolean setVariable(String name, Object value) {
		CycleUtil.rewriteAttribute(name, value);
		return true;
	}

}