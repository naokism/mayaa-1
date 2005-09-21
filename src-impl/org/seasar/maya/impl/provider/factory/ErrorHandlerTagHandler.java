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
package org.seasar.maya.impl.provider.factory;

import org.seasar.maya.engine.error.ErrorHandler;
import org.seasar.maya.impl.util.XMLUtil;
import org.seasar.maya.provider.Parameterizable;
import org.xml.sax.Attributes;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class ErrorHandlerTagHandler extends AbstractParameterizableTagHandler {
    
    private EngineTagHandler _parent;
    private ErrorHandler _handler;
    
    public ErrorHandlerTagHandler(EngineTagHandler parent) {
        super("errorHandler");
        if(parent == null) {
            throw new IllegalArgumentException();
        }
        _parent = parent;
    }
    
    protected void start(Attributes attributes) {
        _handler = (ErrorHandler)XMLUtil.getObjectValue(
                attributes, "class", null, ErrorHandler.class);
        _parent.getEngine().setErrorHandler(_handler);
    }
    
    protected void end(String body) {
        _handler = null;
    }
    
    public Parameterizable getParameterizable() {
        if(_handler == null) {
            throw new IllegalStateException();
        }
        return _handler;
    }
    
}
