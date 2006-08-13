/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
 */package org.seasar.mayaa.impl.source;

import java.io.File;
import java.io.FilenameFilter;

import org.seasar.mayaa.impl.util.FileSearchIterator;
import org.seasar.mayaa.impl.util.StringUtil;

/**
 * @author Taro Kato (Gluegent, Inc.)
 */
public class SystemIDFileSearchIterator extends FileSearchIterator {

    public SystemIDFileSearchIterator(File rootDir, final String[] filters) {
        super(rootDir, new FilenameFilter() {
            public boolean accept(File dir, String name) {
                File file = new File(dir.getPath() + File.separatorChar + name);
                if (file.isHidden()) {
                    return false;
                }
                if (file.isDirectory()) {
                    return true;
                }
                if (filters == null) {
                    return name.toLowerCase().endsWith(".html");
                }
                for (int i = 0; i < filters.length; i++) {
                    String filter = filters[i].trim();
                    // �g���q�̃t�B���^���H
                    if (filter.matches("^\\.[a-z0-9]+")) {
                        if (name.toLowerCase().endsWith(filter)) {
                            return true;
                        }
                    } else {
                        // ����ȊO�͐��K�\���Ƃ݂Ȃ�
                        if (name.matches(filter)) {
                            return true;
                        }
                    }
                }
                return false;
            }
            public String toString() {
                if (filters != null) {
                    return "[" + StringUtil.join(filters, ",") + "]";
                }
                return null;
            }
        });
    }

    public Object next() {
        return makeSystemID((File)super.next());
    }

    protected String makeSystemID(File current) {
        String rootPath = getRoot().getPath();
        String filePath = current.getPath();
        filePath = filePath.substring(rootPath.length());
        filePath = filePath.replace(File.separatorChar, '/');
        if (filePath.length() > 0 && filePath.startsWith("/")) {
            filePath.substring(1);
        }
        return filePath;
    }

}