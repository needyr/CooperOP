/**
 * 
 */
package cn.crtech.cooperop.bus.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * 
 */
public class CooperopException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7233423649983859420L;

	private static Map<Integer, String> msgMap = new HashMap<Integer, String>();

	public static final int UNKOWN = 0;
	public final static int DATA_FORBIDDEN = 100;
	public final static int DATA_NOT_FOUND = 101;
	public final static int AUDIT_FORBIDDEN = 102;
	public final static int MODIFY_FORBIDDEN = 103;
	public final static int DATA_FORBIDDEN_OR_NOT_FOUND = 104;
	public final static int PROCESS_HAS_ENDED = 200;
	public final static int PROCESS_NOT_FOUND = 201;
	public final static int PROCESS_CANNOT_BACK = 202;
	public static final int TASK_HAS_ENDED = 210;
	public static final int TASK_NOT_FOUND = 211;

	static {
		msgMap.put(DATA_FORBIDDEN, "无权操作此数据。");
		msgMap.put(DATA_NOT_FOUND, "数据未找到。");
		msgMap.put(AUDIT_FORBIDDEN, "无权审核该任务。");
		msgMap.put(MODIFY_FORBIDDEN, "该业务正在办理中，不允许修改。");
		msgMap.put(PROCESS_HAS_ENDED, "流程已结束。");
		msgMap.put(PROCESS_NOT_FOUND, "流程实例未找到。");
		msgMap.put(PROCESS_CANNOT_BACK, "流程不可撤回。");
		msgMap.put(TASK_HAS_ENDED, "任务已结束。");
		msgMap.put(TASK_NOT_FOUND, "任务未找到。");
		msgMap.put(DATA_FORBIDDEN_OR_NOT_FOUND, "数据未找到或无权操作数据");
	}

	private final int code;
	private final String message;
	private Throwable cause;

	public CooperopException(int code) {
		this.code = code;
		this.message = msgMap.get(code);
	}

	public CooperopException(int code, Throwable cause) {
		this.code = code;
		this.message = msgMap.get(code);
		this.cause = cause;
	}

	/**
	 * @param string
	 */
	public CooperopException(String message) {
		this.code = UNKOWN;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return this.code;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return this.message;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getCause()
	 */
	@Override
	public Throwable getCause() {
		return this.cause;
	}
}
