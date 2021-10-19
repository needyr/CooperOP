package cn.crtech.precheck.ipc.service;

import java.util.Map;

public interface AuditService {
	
		/**
	    * @Method joinParams
		* @param params his审查请求串
		* @param common_id auto_common表的id
		* @return 拼接完成的接口请求参数
		* @throws Exception
	    * @Description TODO 拼接HYT审查服务请求参数
		* @author yanguozhi  2019-06-20
	    */
	public Map<String, Object> joinParams(Map<String, Object> params, String common_id) throws Exception;
	
	
	/**
	 * 返回自定义审查需要的数据结构
	 * @param auto_audit_id 审查表id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> get_audit_def_data(String auto_audit_id) throws Exception;
	
		/**
	    * @Method callAudit
		* @param map
		* @param request 请求参数
	    * @Description TODO()
		* @author yanguozhi  2019-06-20
	    */
	public void callAudit(Map<String, Object> map, Object request);
	
	/**
	    * @Method pr_callAudit
		* @param request 请求参数
	    * @Description TODO()
		* @author wangruiheng  2020-01-02
	    */
	public String pr_callAudit(Object request);

		/**
	    * @Method getResult
		* @param xmlStr 审查服务返回数据
		* @param map 存储解析数据的必要条件
		* @return 执行成功Y
		* @throws Exception
	    * @Description TODO 解析审查结果，并存储
		* @author yanguozhi  2019-06-20
	    */
	public String getResult(String xmlStr, Map<String, Object> map) throws Exception;
	
	public void getDrugINS();
	
}
