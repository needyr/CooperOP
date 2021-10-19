package cn.crtech.cooperop.bus.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.schedule.core.Task;
import cn.crtech.cooperop.bus.schedule.core.TaskService;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class ScheduleEngine {
	public static final String DEFAULT_GROUP = "SCHEDULER_GROUP";
	private static String server_mac;
	private static Scheduler schedule;
	private static List<Task> tasklist;

	public static void init() throws Exception {
		if ("false".equalsIgnoreCase(GlobalVar.getSystemProperty("server.schedule", "true"))) {
			log.release("“server.schedule” is false ");
			return;
		}
		server_mac = CommonFun.getMACAddress();
		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Properties props = new Properties();
		long t = System.nanoTime();
		// props.put(StdSchedulerFactory.AUTO_GENERATE_INSTANCE_ID,
		// server_mac+"_"+t);
		// props.put(StdSchedulerFactory.DEFAULT_INSTANCE_ID, server_mac+"_"+t);
		props.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_ID, server_mac + "_" + t);
		props.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, server_mac + "_" + t);
		props.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
		props.put("org.quartz.threadPool.threadCount", "10");// 必填
		schedulerFactory.initialize(props);
		ScheduleEngine.schedule = schedulerFactory.getScheduler();
		reload();
		ScheduleEngine.schedule.start();
		log.release("init schedule engine success. [MAC=" + server_mac + "]");
	}

	private static void reload() throws Exception {
		GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(DEFAULT_GROUP);
		Set<JobKey> jobs = ScheduleEngine.schedule.getJobKeys(matcher);
		for (JobKey job : jobs) {
			ScheduleEngine.schedule.deleteJob(job);
		}

		TaskService service = new TaskService();
		tasklist = service.queryTask(server_mac);
		for (Task task : tasklist) {
			try {
				if (task.getState() == 0 || !task.isRunable() || !server_mac.equals(task.getMac())) {
					continue;
				}
				String jobName = task.getName() + "[" + task.getInstanceId() + "]";
				String triggerCron = task.getCron();
				@SuppressWarnings("unchecked")
				JobDetail job = JobBuilder.newJob((Class<? extends Job>) Class.forName(task.getClassz()))
						.withIdentity(jobName, DEFAULT_GROUP).build();
				job.getJobDataMap().put("TASK", task);
				CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
						.withIdentity("trigger[" + task.getInstanceId() + "]", "trigger_" + DEFAULT_GROUP)
						.withSchedule(CronScheduleBuilder.cronSchedule(triggerCron)).build();
				ScheduleEngine.schedule.scheduleJob(job, trigger);
				log.release("register schedule task success, " + task.getName() + "[id=" + task.getInstanceId() + "]");
			} catch (Exception e) {
				log.error("register schedule task failed, " + task.getName() + "[id=" + task.getInstanceId() + "]", e);
			}
		}
	}

	public static Task getTask(String taskid) throws SchedulerException {
		for (Task task : tasklist) {
			if (task.getInstanceId().equals(taskid)) {
				return task;
			}
		}
		return null;
	}

	public static List<Task> getTasks() throws SchedulerException {
		return tasklist;
	}

	public static List<Task> getCurrentlyExecutingTasks() throws SchedulerException {
		List<Task> tasks = new ArrayList<Task>();
		List jobs = ScheduleEngine.schedule.getCurrentlyExecutingJobs();
		for (Task task : tasklist) {
			for (Object o : jobs) {
				JobDetail job = (JobDetail) o;
				if (job.getKey().equals(task.getName() + "[" + task.getInstanceId() + "]")) {
					tasks.add(task);
				}
			}
		}
		return tasks;
	}

	public static void pauseTask(String taskid) throws SchedulerException {
		Task task = getTask(taskid);
		String jobName = task.getName() + "[" + task.getInstanceId() + "]";
		String jobGroup = ScheduleEngine.DEFAULT_GROUP;
		ScheduleEngine.schedule.pauseJob(JobKey.jobKey(jobName, jobGroup));
	}

	public static void resumeTask(String taskid) throws SchedulerException {
		Task task = getTask(taskid);
		String jobName = task.getName() + "[" + task.getInstanceId() + "]";
		String jobGroup = ScheduleEngine.DEFAULT_GROUP;
		ScheduleEngine.schedule.resumeJob(JobKey.jobKey(jobName, jobGroup));
	}

	public static void pauseAll() throws SchedulerException {
		ScheduleEngine.schedule.pauseAll();
	}

	public static void resumeAll() throws SchedulerException {
		ScheduleEngine.schedule.resumeAll();
	}

	public static Scheduler getSchedule() {
		return schedule;
	}
}
