/*
 * Copyright (c) 2004-2005 the Seasar Project and the Others.
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
package org.seasar.maya.engine.processor;

import javax.servlet.jsp.PageContext;

/**
 * TemplateProcessorの拡張インターフェイス。処理のイテレート機能。
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public interface IterationProcessor extends TemplateProcessor {

    /**
     * イテレート実行するかどうかを返す。JSPのIterationTagやBodyTagをホスト
     * している場合に利用する。デフォルトではfalseを返す。trueだと、子プロセッサ
     * の実行後にdoAfterChildProcess()メソッドがコンテナより呼び出される。
     * @param context プロセス中のステートフルな情報を保持するコンテキスト。
     * @return イテレート実行する場合、true。普通はfalse。
     */
    boolean isIteration(PageContext context);

    /**
     * イテレート実行する場合、子プロセッサの実行後にコンテナより呼び出される。
     * @param context プロセス中のステートフルな情報を保持するコンテキスト。
     * @return javax.servlet.jsp.tagext.IterationTag#doAfterBody()の返値と同じ仕様。
     */
    int doAfterChildProcess(PageContext context);

}