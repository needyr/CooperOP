package cn.crtech.cooperop.bus.schedule;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.schedule.core.Task;
import cn.crtech.cooperop.bus.schedule.core.TaskLog;
import cn.crtech.cooperop.bus.schedule.core.TaskService;
import cn.crtech.cooperop.bus.util.CommonFun;

/**
 * 
 * @author FORWAY R&D fuhong.chen
 * @version xx 1.0
 * @createTime 2010-11-1 下午07:52:12
 */
public abstract class AbstractSchedule implements Job {
	private JobExecutionContext jobContext = null;
	public AbstractSchedule() {
	}

	/**
	 * 任务将要被执行时触发
	 * 
	 * @return 若返回false，不继续执行executeOn，并置任务执行状态为‘终止’
	 * @throws ScheduleException
	 *             抛出异常后，该任务执行状态被标记为‘异常’
	 */
	public boolean executeBefore() throws Exception {
		return true;
	}

	/**
	 * 任务执行时被触发
	 * 
	 * @return 若返回false，不继续执行executeAfter，并置任务执行状态为‘终止’
	 * @throws ScheduleException
	 *             抛出异常后，该任务执行状态被标记为‘异常’
	 */
	public boolean executeOn() throws Exception {
		return true;
	}

	/**
	 * 任务执行完后被触发
	 * 
	 * @throws ScheduleException
	 *             抛出异常后，该任务执行状态被标记为‘异常’
	 */
	public boolean executeAfter() throws Exception {
		return true;
	}

	@Override
	public final void execute(JobExecutionContext arg0)
			throws JobExecutionException {
		jobContext = arg0;
		TaskLog logs = null;
		String result = "[正常]";
		try {
			boolean first = false, second = false, third = false;
			Task task = (Task) arg0.getMergedJobDataMap().get("TASK");
			if (task != null) {
				first = true;
				logs = new TaskLog();
				logs.setInstanceId(task.getInstanceId());
				logs.setStartTime(new Date());
				logs.setComputer(CommonFun.getMACAddress() + "["
						+ CommonFun.getIPAddress() + "]");
			} else {
				return;
			}
			log.debug("Schedule Task[" + task.getName() + ", class=" + this.getClass() + "]: begin");
			if (first) {
				log.debug("Schedule Task[" + task.getName() + ", class=" + this.getClass() + "]: execute_before");
				second = this.executeBefore();
			}
			if (second) {
				log.debug("Schedule Task[" + task.getName() + ", class=" + this.getClass() + "]: execute_on");
				third = this.executeOn();
			}
			if (third) {
				log.debug("Schedule Task[" + task.getName() + ", class=" + this.getClass() + "]: execute_after");
				this.executeAfter();
			}
			log.debug("Schedule Task[" + task.getName() + ", class=" + this.getClass() + "]: end");
		} catch (Exception ex) {
			result = "[异常]" + ex.getMessage();
			log.error("Schedule Task:" + this.getClass(), ex);
		} finally {
			// 仅仅记录错误运行日志
			if (logs != null && !result.equals("[正常]")) {
				logs.setEndTime(new Date());
				logs.setResult(result);
				TaskService service = new TaskService();
				try {
					service.writeLog(logs);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logs = null;
			}
		}
	}
	public Scheduler getScheduler(){
		if(jobContext!=null)
			return jobContext.getScheduler();
		else 
			return null;
	}
}
