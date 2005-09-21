/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.maya.impl.builder.library.tld;

import org.seasar.maya.impl.util.xml.TagHandler;
import org.xml.sax.Attributes;

/**
 * @author suga
 */
public class TaglibTagHandler extends TagHandler {

    private TLDLibraryDefinition _library;

    public TaglibTagHandler() {
        super("taglib");
        putHandler(new TagTagHandler(this));
        putHandler(new TagHandler("jsp-version") {
            protected void end(String body) {
                _library.setRequiredVersion(body);
            }
        });
        putHandler(new TagHandler("jspversion") {
            protected void end(String body) {
                _library.setRequiredVersion(body);
            }
        });
        putHandler(new TagHandler("uri") {
            protected void end(String body) {
                _library.setNamespaceURI(body);
            }
        });
    }
    
    protected void start(Attributes attributes) {
        _library = new TLDLibraryDefinition();
    }

    public TLDLibraryDefinition getLibraryDefinition() {
        return _library;
    }
    
}
