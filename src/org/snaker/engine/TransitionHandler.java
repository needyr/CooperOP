/* Copyright 2013-2015 www.snakerflow.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.snaker.engine;

import org.snaker.engine.core.Execution;
import org.snaker.engine.model.TransitionModel;

/**
 * 流向判断处理类
 * @author shine
 * @since 1.0
 */
public interface TransitionHandler {
	/**
	 * 定义流向判断方法，实现类需要根据执行对象做处理，并返回是否满足条件
	 * @param execution
	 * @return boolean 是否满足条件
	 */
	boolean match(Execution execution);
	boolean match(TransitionModel transitionmodel, Execution execution);
}
