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
package org.seasar.maya.engine.processor;

/**
 * TemplateProcessorの拡張インターフェイス。例外処理関連のイベントを
 * 受け取る機能を持たせる。
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public interface TryCatchFinallyProcessor extends TemplateProcessor {

    /**
     * 例外をcatchするかどうかを返す。JSPのTryCatchFinallyをホストしている場合に
     * 利用する。デフォルトではfalseを返す。trueだと、例外発生時に
     * doCatchProcess、例外とは無関係にdoFinallyProcessがコンテナより呼び出される。
     * @return 例外をcatchする場合、true。普通はfalse。
     */
    boolean canCatch();

    /**
     * プロセス中の例外をキャッチして行う処理。
     * @param t プロセス中に発生した例外
     */
    void doCatchProcess(Throwable t);

    /**
     * プロセス中に例外が起きても行う後処理。
     */
    void doFinallyProcess();

}