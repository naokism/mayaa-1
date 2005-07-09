/*
 * Copyright (c) 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Seasar Software License, v1.1 (aka "the License");
 * you may not use this file except in compliance with the License which
 * accompanies this distribution, and is available at
 *
 *     http://www.seasar.org/SEASAR-LICENSE.TXT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.seasar.maya.impl.cycle.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.seasar.maya.impl.cycle.AbstractResponse;
import org.seasar.maya.impl.util.StringUtil;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class WebResponse extends AbstractResponse {

    private static final long serialVersionUID = -4653384671998376182L;

    private HttpServletResponse _httpServletResponse;

    private void check() {
        if(_httpServletResponse == null) {
            throw new IllegalStateException();
        }
    }
    
    public Object getUnderlyingObject() {
        check();
        return _httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        if(httpServletResponse == null) {
            throw new IllegalArgumentException();
        }
        _httpServletResponse = httpServletResponse;
    }
    
    protected void setMimeTypeToUnderlyingObject(String mimeType) {
        check();
        if(StringUtil.isEmpty(mimeType)) {
            throw new IllegalArgumentException();
        }
        _httpServletResponse.setContentType(mimeType);
    }

    protected void writeToUnderlyingObject(String text) {
        check();
        try {
            Writer writer = new OutputStreamWriter(
                    _httpServletResponse.getOutputStream(), getEncoding());
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public OutputStream getUnderlyingOutputStream() {
        check();
        try {
            return _httpServletResponse.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}