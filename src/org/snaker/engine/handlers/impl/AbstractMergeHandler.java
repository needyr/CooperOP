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
package org.snaker.engine.handlers.impl;

import java.util.List;

import org.snaker.engine.IQueryService;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;
import org.snaker.engine.handlers.IHandler;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.model.SubProcessModel;
import org.snaker.engine.model.TaskModel;

import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.workflow.core.service.WorkFlowService;

/**
 * 合并处理的抽象处理器
 * 需要子类提供查询无法合并的task集合的参数map
 * @author yuqs
 * @since 1.0
 */
public abstract class AbstractMergeHandler implements IHandler {
	public void handle(NodeModel nodemodel, Execution execution) {
		handle(execution);
	}

	public void handle(Execution execution) {
		/**
		 * 查询当前流程实例的无法参与合并的node列表
		 * 若所有中间node都完成，则设置为已合并状态，告诉model可继续执行join的输出变迁
		 */
		IQueryService queryService = execution.getEngine().query();
		Order order = execution.getOrder();
		ProcessModel model = execution.getModel();
		String[] activeNodes = findActiveNodes();
		Task task = execution.getTask();
		boolean isSubProcessMerged = false;
		boolean isTaskMerged = false;
		boolean isCountersignMerged = true;//设置true ，普通节点直接通过
		
		if(model.containsNodeNames(SubProcessModel.class, activeNodes)) {
			QueryFilter filter = new QueryFilter().setParentId(order.getId())
					.setExcludedIds(new String[]{execution.getChildOrderId()});
			List<Order> orders = queryService.getActiveOrders(filter);
			//如果所有子流程都已完成，则表示可合并
			if(orders == null || orders.isEmpty()) {
				isSubProcessMerged = true;
			}
		} else {
			isSubProcessMerged = true;
		}
		if(isSubProcessMerged && model.containsNodeNames(TaskModel.class, activeNodes)) {
			QueryFilter filter = new QueryFilter().
					setOrderId(order.getId()).
					setExcludedIds(new String[]{task.getId() }).
					setNames(activeNodes);
			List<Task> tasks = queryService.getActiveTasks(filter);
			if(tasks == null || tasks.isEmpty()) {
				//如果所有task都已完成，则表示可合并
				isTaskMerged = true;
			}
		}
		// 蒋强 19-11-25 添加会签处理逻辑
		//根据任务通过数，和节点设置会签成功人数比较，会签已通过数大于等于设置数或者会签驳回数加上设置数大于等于待办数 会签结束
		try {
			String check = new WorkFlowService().checkCountersign(order.getId(), model.getName().split(WorkFlowService.FLOW_NAME_SPLIT)[1], task.getTaskName(), null);
			if("Y".equals(check)){
				isTaskMerged = true;
				execution.getArgs().put("audited", "Y");
			}else if("N".equals(check)){
				isTaskMerged = true;
				execution.getArgs().put("audited", "N");
			}else{
				isCountersignMerged = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//
		execution.setMerged(isSubProcessMerged && isTaskMerged && isCountersignMerged);
	}

	/**
	 * 子类需要提供如何查询未合并任务的参数map
	 * @return
	 */
	protected abstract String[] findActiveNodes();
}
