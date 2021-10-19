package cn.crtech.precheck.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CRPasswordEncrypt;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.precheck.dao.TradeDao;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TradeSerivce extends BaseService {


	public List<Record> listService() throws Exception {
		try {
			connect("hospital_common");
			TradeDao td = new TradeDao();
			return td.listService().getResultset();
		} finally {
			disconnect();
		}
	}

	public Record getService(String code) throws Exception {
		try {
			connect("hospital_common");
			TradeDao td = new TradeDao();
			return td.getService(code);
		} finally {
			disconnect();
		}
	}

	public String openpre(String appId, String service_code, String method_name, String message) throws Exception {
		return "";
	}
	public String savepre(String appId, String service_code, String method_name, String message) throws Exception {
		
		return null;
	}
	@SuppressWarnings("unchecked")
	private void insertInParams(int data_service_log_id, int parent_id, Object params, Record table, TradeDao td) throws Exception {
		List<Map<String, Object>> lin = null;
		if (table != null) {
			if (CommonFun.isNe(table.getString("param_name"))) {
				if (params instanceof Map) {
					lin = new ArrayList<Map<String, Object>>();
					lin.add((Map<String, Object>) params);
				} else if (params instanceof List) {
					lin = (List<Map<String, Object>>) params;
				}
			} else {
				if (((Map<String, Object>) params).get(table.getString("param_name")) instanceof List) {
					lin = (List<Map<String, Object>>) ((Map<String, Object>) params).get(table.getString("param_name"));
				} else if (((Map<String, Object>) params).get(table.getString("param_name")) instanceof Map) {
					lin = new ArrayList<Map<String, Object>>();
					lin.add((Map<String, Object>) ((Map<String, Object>) params).get(table.getString("param_name")));
				}
			}
			if (lin == null) {
				throw new Exception("错误的交易体格式-" + table.getString("param_name"));
			}
		} else if (parent_id == 0) {
			throw new Exception("交易定义中未找到数据项");
		}

		Record encrypt_fields = new Record();
		if (!CommonFun.isNe(table.getString("encrypt_fields"))) {
			for (String f : table.getString("encrypt_fields").split(",")) {
				encrypt_fields.put(f.toLowerCase(), f.toLowerCase());
			}
		}

		for (Map<String, Object> param : lin) {
			Record r = new Record();
			r.put("data_service_log_id", data_service_log_id);
			if (parent_id > 0) {
				r.put("parent_id", parent_id);
			}
			
			for (Record f : (List<Record>) table.get("fields")) {
				// +++
				String field_name = f.getString("name");
				String alias_name = td.getAlias(table.getString("table_name"), field_name);

				//增加in表字段忽略大小写写入
				Iterator<String> keys = param.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					if (key.equalsIgnoreCase(field_name)) {
						param.put(f.getString("name"), param.remove(key));
						break;
					}
					if (key.equalsIgnoreCase(alias_name)) {
						param.put(f.getString("name"), param.remove(key));
						break;
					}
				}
				
				// +++
				if (param.containsKey(f.getString("name"))) {
					if (CommonFun.isNe(param.get(f.getString("name"))) && Types.CHAR != f.getInt("type") && Types.VARCHAR != f.getInt("type") && Types.LONGVARCHAR != f.getInt("type")
							&& Types.NCHAR != f.getInt("type") && Types.NVARCHAR != f.getInt("type") && Types.LONGNVARCHAR != f.getInt("type")) {

					} else if (CommonFun.isCollection(param.get(f.getString("name")))) {
						r.put(f.getString("name"), CommonFun.object2Json(param.get(f.getString("name"))));
					} else if (CommonFun.isMap(param.get(f.getString("name")))) {
						r.put(f.getString("name"), CommonFun.object2Json(param.get(f.getString("name"))));
					} else {
						Object value = param.get(f.getString("name"));
						if ((Types.CHAR == f.getInt("type") || Types.VARCHAR == f.getInt("type") || Types.LONGVARCHAR == f.getInt("type") || Types.NCHAR == f.getInt("type")
								|| Types.NVARCHAR == f.getInt("type") || Types.LONGNVARCHAR == f.getInt("type")) && !CommonFun.isNe(value)) {
							if (encrypt_fields.containsKey(f.getString("name").toLowerCase())) {
								r.put(f.getString("name"), CRPasswordEncrypt.Encryptstring(value.toString()));
							} else if (f.getInt("size") < value.toString().getBytes().length) {
								int length = f.getInt("size");
								byte[] b = value.toString().getBytes(); // UTF-8中文占3字节，数据库按2位计算中文
								int tl = 0, ll = 0;
								for (int i = 0; i < b.length; i++) {
									tl++;
									if (b[i] < 0) {
										tl++;
										i += 2;
									}
									if (tl > length)
										break;
									ll++;
								}
								value = value.toString().substring(0, ll);
							}
						} else if (Types.BLOB == f.getInt("type") || Types.BINARY == f.getInt("type") || Types.CLOB == f.getInt("type") || Types.VARBINARY == f.getInt("type")
								|| Types.LONGVARBINARY == f.getInt("type") || Types.NCLOB == f.getInt("type")) {
							value = new BASE64Decoder().decodeBuffer((String) value);
						}
						r.put(f.getString("name"), value);
					}
				}
			}
			int id = td.insertTable(table.getString("table_name"), r);
			for (Record t : (List<Record>) table.get("subtables")) {
				insertInParams(data_service_log_id, id, param, t, td);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Object queryOutParams(int data_service_log_id, int parent_id, Record table, TradeDao td) throws Exception {
		Object rtn = null;
		Record r = new Record();
		r.put("data_service_log_id", data_service_log_id);
		StringBuffer sql = new StringBuffer();
		sql.append("select * ");
		sql.append("from " + table.getString("table_name") + "(nolock) ");
		sql.append("where data_service_log_id = :data_service_log_id ");
		if (parent_id > 0) {
			r.put("parent_id", parent_id);
			sql.append("and parent_id = :parent_id ");
		}
		Record encrypt_fields = new Record();
		if (!CommonFun.isNe(table.getString("encrypt_fields"))) {
			for (String f : table.getString("encrypt_fields").split(",")) {
				encrypt_fields.put(f.toLowerCase(), f.toLowerCase());
			}
		}
		Result rs = td.executeQuery(sql.toString(), r);
		List<Record> lin = new ArrayList<Record>();
		for (Record re : rs.getResultset()) {
			Record data = new Record();
			List<Record> fields = (List<Record>) table.get("fields");
			for (Record field : fields) {
				Object value = re.get(field.getString("name").toLowerCase());
				if (value instanceof String && !CommonFun.isNe(value)) {
					String t = (String) value;
					if (t.startsWith("[") && t.endsWith("]")) {
						value = CommonFun.json2Object(t, List.class);
					} else if (t.startsWith("{") && t.endsWith("}")) {
						value = CommonFun.json2Object(t, Map.class);
					}
				}
				if (value != null && (Types.BLOB == field.getInt("type") || Types.BINARY == field.getInt("type") || Types.CLOB == field.getInt("type") || Types.VARBINARY == field.getInt("type")
						|| Types.LONGVARBINARY == field.getInt("type") || Types.NCLOB == field.getInt("type"))) {
					value = new BASE64Encoder().encode((byte[]) value);
				}
				// +++
				String field_name = field.getString("name");
				String alias_name = td.getAlias(table.getString("table_name"), field_name);
				// +++
				if (encrypt_fields.containsKey(field.getString("name").toLowerCase()) && !CommonFun.isNe(re.get(field.getString("name").toLowerCase()))) {
					r.put(field.getString("name"), CRPasswordEncrypt.Encryptstring(value.toString()));
				} else {
					data.put(field.getString("name"), value);
				}
				// +++
				if (!CommonFun.isNe(alias_name)) {
					data.put(alias_name, data.remove(field_name));
				}
				// +++
			}
			for (Record t : (List<Record>) table.get("subtables")) {
				data.putAll((Record) queryOutParams(data_service_log_id, re.getInt("id"), t, td));
			}
			data.remove("data_service_log_id");
			data.remove("id");
			data.remove("parent_id");
			lin.add(data);
		}
		if (!CommonFun.isNe(table.getString("param_name"))) {
			rtn = new Record();
			((Record) rtn).put(table.getString("param_name"), lin);
		} else {
			rtn = lin;
		}
		return rtn;
	}
}
