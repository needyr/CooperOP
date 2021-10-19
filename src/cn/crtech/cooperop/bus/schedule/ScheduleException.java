package cn.crtech.cooperop.bus.schedule;

/**
 * 自动任务异常
 * 
 * @author FORWAY R&D fuhong.chen
 * @version swork 1.0
 * @createTime 2010-11-1 下午01:35:05
 */
public class ScheduleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ScheduleException() {

	}

	public ScheduleException(String message) {
		super(message);
	}

	public ScheduleException(Throwable cause) {
		super(cause);
	}

	public ScheduleException(String message, Throwable cause) {
		super(message, cause);
	}
}
