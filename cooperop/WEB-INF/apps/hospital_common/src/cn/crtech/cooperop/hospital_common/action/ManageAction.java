package cn.crtech.cooperop.hospital_common.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ManageService;
import cn.crtech.cooperop.hospital_common.service.WebtabstructService;

public class ManageAction extends BaseAction {

	/**
	 * 插入
	 */
	public int insert(Map<String,Object> req) throws Exception{
		Map<String, Object> params = CommonFun.json2Object((String)req.get("json"), Map.class);
		return new ManageService().insert(params);
	}
	
	/**
	 * 修改表结构
	 */
	
 	public int update(Map<String,Object> req) throws Exception{
 		Map<String, Object> params = CommonFun.json2Object((String)req.get("json"), Map.class);
 		//先drop表在创建
// 		int i  = new ManageService().dropTable(params);
// 		return new ManageService().insert(params);
 		return new ManageService().modify(params);
 	}
	
	/**
	 * 初始化表结构界面
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> edit(Map<String,Object> req) throws Exception{
 		Record params = new Record(req);
 		ManageService service = new ManageService();
		List<Record> fields = service.query(params);
		// +++
		String table_name = (String)params.get("tablename");
		// +++
		//remove三个主键不显示到界面
		if(!CommonFun.isNe(fields)){
			Iterator<Record> iterator = fields.iterator();
			while(iterator.hasNext()){
				Record record = iterator.next();
				if("id".equals((String)record.get("name"))){
	 				//id用于界面获取update时判断表是否存在
	 				params.put("id", (String)record.get("name"));
	 				iterator.remove();
	 			}
	 			if("data_service_log_id".equals((String)record.get("name"))){
	 				iterator.remove();
	 			}
	 			if("parent_id".equals((String)record.get("name"))){
	 				iterator.remove();
	 			}
	 			// +++
	 			if ("numeric".equalsIgnoreCase((String) record.get("type_name")) ||
	 					"decimal".equalsIgnoreCase((String) record.get("type_name"))) {
	 				record.put("precision", record.remove("precision") + "," + record.get("scale"));
	 			} else if ("image".equalsIgnoreCase((String) record.get("type_name"))) {
	 				record.put("precision", 0);
	 			}
	 			String alias_name = service.getAlias(table_name, (String)record.get("name"));
	 			if (!CommonFun.isNe(alias_name)) {
	 				record.put("alias_name", alias_name);
	 			}
	 			// +++
			}
		}
 		params.put("fields", fields);
 		return params;
 	}
	
}
