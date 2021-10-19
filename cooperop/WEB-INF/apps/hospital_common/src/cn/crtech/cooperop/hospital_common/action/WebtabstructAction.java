package cn.crtech.cooperop.hospital_common.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.WebtabstructService;

public class WebtabstructAction extends BaseAction{

	/**
	 * 插入
	 */
	public int insert(Map<String,Object> req) throws Exception{
		Map<String, Object> params = CommonFun.json2Object((String)req.get("json"), Map.class);
		return new WebtabstructService().insert(params);
	}
	
	/**
	 * 修改表结构
	 */
 	public int update(Map<String,Object> req) throws Exception{
 		Map<String, Object> params = CommonFun.json2Object((String)req.get("json"), Map.class);
 		//先drop表在创建
 //		int i  = new WebtabstructService().dropTable(params);
 //		return new WebtabstructService().insert(params);
 		return new WebtabstructService().modify(params);
 	}
 	
 	public Map<String, Object> edit(Map<String,Object> req) throws Exception{
 		Record params = new Record(req);
 		WebtabstructService service = new WebtabstructService();
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
	 			if("data_webservice_quene_id".equals((String)record.get("name"))){
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
 		/*for(Record record : fields){
 			if("id".equals((String)record.get("name"))){
 				params.put("id", (String)record.get("name"));
 				return params;
 			}
 		}*/
 		return params;
 	}
}
