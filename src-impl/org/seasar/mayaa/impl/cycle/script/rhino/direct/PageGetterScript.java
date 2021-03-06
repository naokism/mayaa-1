/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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
package org.seasar.mayaa.impl.cycle.script.rhino.direct;

import org.seasar.mayaa.PositionAware;
import org.seasar.mayaa.cycle.scope.AttributeScope;
import org.seasar.mayaa.impl.cycle.CycleUtil;
import org.seasar.mayaa.impl.cycle.script.rhino.PageAttributeScope;
import org.seasar.mayaa.impl.util.ObjectUtil;

/**
 * pageスコープ(のカレント)から変数を取得するだけの処理をするスクリプト。
 *
 * @author Koji Suga (Gluegent Inc.)
 */
public class PageGetterScript extends AbstractGetterScript {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES =
        ObjectUtil.getPropertyNames(AttributeScope.class);


    public PageGetterScript(
            String text, PositionAware position, int offsetLine,
            String scopeName, String attributeName, String propertyName) {
        super(text, position, offsetLine, scopeName, attributeName, propertyName, PROPERTY_NAMES);
    }

    /**
     * pageスコープ(のカレント)を返します。
     *
     * @return pageスコープ(のカレント)。
     */
    protected AttributeScope getScope() {
        return CycleUtil.getCurrentPageScope();
    }

    /**
     * {@link #execute(Object[])}で呼び出され、スコープから属性値を取得します。
     * 指定された属性名がスコープのプロパティを指している場合はプロパティの値を
     * 返します。
     *
     * @return 属性値
     */
    protected Object getAttribute() {
        if ("__parent__".equals(_attributeName)) {
            AttributeScope scope = getScope();
            if (scope == null) {
                return null;
            }
            return ((PageAttributeScope) scope).getParentScope();
        }
        return super.getAttribute();
    }
}
