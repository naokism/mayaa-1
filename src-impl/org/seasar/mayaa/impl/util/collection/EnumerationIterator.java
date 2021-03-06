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
package org.seasar.mayaa.impl.util.collection;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class EnumerationIterator implements Iterator {

    private Enumeration _enumration;

    public static Iterator getInstance(Enumeration enumration) {
        if (enumration == null) {
            return NullIterator.getInstance();
        }
        if (enumration instanceof IteratorEnumeration) {
            return ((IteratorEnumeration) enumration).getInternalIterator();
        }
        return new EnumerationIterator(enumration);
    }

    private EnumerationIterator(Enumeration enumration) {
        if (enumration == null) {
            throw new IllegalArgumentException();
        }
        _enumration = enumration;
    }

    public Enumeration getInternalEnumeration() {
        return _enumration;
    }

    public boolean hasNext() {
        return _enumration.hasMoreElements();
    }

    public Object next() {
        return _enumration.nextElement();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

}
