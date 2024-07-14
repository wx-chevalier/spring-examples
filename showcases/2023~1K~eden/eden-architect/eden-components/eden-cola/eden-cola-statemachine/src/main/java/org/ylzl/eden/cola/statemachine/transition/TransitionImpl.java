/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ylzl.eden.cola.statemachine.transition;

import lombok.extern.slf4j.Slf4j;
import org.ylzl.eden.cola.statemachine.Action;
import org.ylzl.eden.cola.statemachine.Condition;
import org.ylzl.eden.cola.statemachine.state.State;
import org.ylzl.eden.cola.statemachine.exception.StateMachineException;
import org.ylzl.eden.commons.lang.MessageFormatUtils;

/**
 * 流转实现
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Slf4j
public class TransitionImpl<S, E, C> implements Transition<S, E, C> {

	private static final String CONDITION_IS_NOT_SATISFIED = "Condition is not satisfied, stay at the {} state.";

	private static final String TRANS_SAME_NAME = "Internal transition source state '{}' and target state '{}' must be same.";

	private State<S, E, C> source;

	private State<S, E, C> target;

	private E event;

	private Condition<C> condition;

	private Action<S, E, C> action;

	private TransitionType type = TransitionType.EXTERNAL;

	@Override
	public State<S, E, C> getSource() {
		return source;
	}

	@Override
	public void setSource(State<S, E, C> state) {
		this.source = state;
	}

	@Override
	public State<S, E, C> getTarget() {
		return target;
	}

	@Override
	public void setTarget(State<S, E, C> target) {
		this.target = target;
	}

	@Override
	public E getEvent() {
		return this.event;
	}

	@Override
	public void setEvent(E event) {
		this.event = event;
	}

	@Override
	public void setType(TransitionType type) {
		this.type = type;
	}

	@Override
	public Condition<C> getCondition() {
		return this.condition;
	}

	@Override
	public void setCondition(Condition<C> condition) {
		this.condition = condition;
	}

	@Override
	public Action<S, E, C> getAction() {
		return this.action;
	}

	@Override
	public void setAction(Action<S, E, C> action) {
		this.action = action;
	}

	@Override
	public State<S, E, C> transit(C ctx, boolean checkCondition) {
		this.verify();
		if (!checkCondition || condition == null || condition.isSatisfied(ctx)) {
			if (action != null) {
				action.execute(source.getId(), target.getId(), event, ctx);
			}
			return target;
		}

		log.debug(CONDITION_IS_NOT_SATISFIED, source);
		return source;
	}

	@Override
	public void verify() {
		if (type == TransitionType.INTERNAL && source != target) {
			throw new StateMachineException(MessageFormatUtils.format(TRANS_SAME_NAME, source, target));
		}
	}
}
