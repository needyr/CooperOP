package cn.crtech.precheck.client.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.precheck.client.Engine;
import cn.crtech.precheck.client.dao.ClientDao;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ClientService extends BaseService {
	public static final String TRADE_KEY_PRIX = "trd_";

	/**
	 * 返回所有客服端的服务记录并封装该服务的所有接口
	 * 
	 * @return 所有客服端服务（服务的所有接口也加进去）
	 * @throws Exception
	 */
	public List<Record> listClient() throws Exception {
		try {
			connect("hospital_common");
			ClientDao wsd = new ClientDao();
			List<Record> clients = wsd.listClient().getResultset();
			for (Record client : clients) {
				client.put("methods", wsd.queryMethod(client.getString("code"), null).getResultset());
			}
			return clients;
		} finally {
			disconnect();
		}
	}

	/**
	 * 获取指定编号的服务
	 * 
	 * @param code
	 * @return 指定客服端服务（服务的所有接口也加进去）
	 * @throws Exception
	 */
	public Record getClient(String code) throws Exception {
		try {
			connect("hospital_common");
			ClientDao wsd = new ClientDao();
			Record client = wsd.getClient(code, "1");
			if (client != null) {
				client.put("methods", wsd.queryMethod(client.getString("code"), "1").getResultset());
			}
			return client;
		} finally {
			disconnect();
		}
	}
	/**
	 * 获取指定编号的服务
	 * 
	 * @param code
	 * @return 指定客服端服务（服务的所有接口也加进去）
	 * @throws Exception
	 */
	public Record getClientByManual(String code, String p_type) throws Exception {
		try {
			connect("hospital_common");
			ClientDao wsd = new ClientDao();
			Record client = wsd.getClient(code, null);
			if (client != null) {
				client.put("methods", wsd.queryMethodByManual(client.getString("code"), p_type).getResultset());
			}
			return client;
		} finally {
			disconnect();
		}
	}
	/**
	 * 
	 * @param code
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public Record getClientMethod(String code, String method) throws Exception {
		try {
			connect("hospital_common");
			ClientDao wsd = new ClientDao();
			return wsd.getMethod(code, method);
		} finally {
			disconnect();
		}
	}

	public List<Record> listClientTrading() throws Exception {
		return listClientTrading(null, null);
	}

	public List<Record> listClientTrading(String data_webservice_code, String data_webservice_method_code) throws Exception {
		try {
			connect("hospital_common");
			List<Record> rtn = new ClientDao().listClientTrading(data_webservice_code, data_webservice_method_code).getResultset();
			return rtn;
		} finally {
			disconnect();
		}
	}

	public String getTradeJSON(Record trade) throws Exception {
		ClientDao wsd = null;
		try {
			connect("hospital_common");
			wsd = new ClientDao();
			Result params = wsd.listParams(trade.getLong("id"));
			Result tables = wsd.listTableRef(trade.getString("data_webservice_code"), trade.getString("data_webservice_method_code"));

			List<Object> rtn = new ArrayList<Object>();
			for (Record table : tables.getResultset()) {
				table.put("fields", wsd.getMetaData(table.getString("table_name")).getResultset());
				List<Record> subtables = new ArrayList<Record>();
				for (Record subtable : tables.getResultset()) {
					if (table.getString("table_name").equalsIgnoreCase(subtable.getString("parent_table_name")) && subtable.getString("type").equalsIgnoreCase(table.getString("type"))) {
						subtables.add(subtable);
					}
				}
				table.put("subtables", subtables);
			}
			for (Record param : params.getResultset()) {
				if (CommonFun.isNe(param.get("table_name"))) {
					rtn.add(param.get("value"));
				} else {
					for (Record table : tables.getResultset()) {
						if ("out".equalsIgnoreCase(table.getString("type")) && param.getString("table_name").equalsIgnoreCase(table.getString("table_name"))) {
							rtn.add(queryOutParams(trade.getLong("id"), 0, table, wsd));
						}
					}
				}
			}

			Record l = new Record();
			l.put("begin_time", "sysdate");
			if (trade.getInt("state") == -1) {
				l.put("retry_times", trade.getInt("retry_times") + 1);
			}
			// +++
			Record webservice = getClient(trade.getString("data_webservice_code"));
			if (CommonFun.isNe(webservice)) {
				log.error("webservice 为空.");
				throw new Exception("webservice 为空.");
			}
			l.put("request",
					((Engine) Engine.engines.get(trade.getString("data_webservice_code"))).getRequest(trade.getString("data_webservice_method_code"), webservice.getString("header"), rtn.toArray()));
			// +++
			// l.put("request", CommonFun.object2Json(rtn));
			l.put("state", "1");
			wsd.updateLog(trade.getLong("id"), l);

			return l.getString("request");
			// return CommonFun.object2Json(rtn);
		} finally {
			disconnect();
		}
	}

	public void callback(Record trade, Object rtn) throws Exception {
		ClientDao wsd = null;
		try {
			connect("hospital_common");
			wsd = new ClientDao();
			Record method = wsd.getMethod(trade.getString("data_webservice_code"), trade.getString("data_webservice_method_code"));
			Result tables = wsd.listTableRef(trade.getString("data_webservice_code"), trade.getString("data_webservice_method_code"));

			Record main_in = null;
			for (Record table : tables.getResultset()) {
				table.put("fields", wsd.getMetaData(table.getString("table_name")).getResultset());
				if ("in".equalsIgnoreCase(table.getString("type")) && CommonFun.isNe(table.getString("parent_table_name"))) {
					main_in = table;
				}
				List<Record> subtables = new ArrayList<Record>();
				for (Record subtable : tables.getResultset()) {
					if (table.getString("table_name").equalsIgnoreCase(subtable.getString("parent_table_name")) && subtable.getString("type").equalsIgnoreCase(table.getString("type"))) {
						subtables.add(subtable);
					}
				}
				table.put("subtables", subtables);
			}

			Record l = new Record();
			l.put("return_time", "sysdate");
			l.put("state", "2");
			// +++
			Record webservice = getClient(trade.getString("data_webservice_code"));
			if (CommonFun.isNe(webservice)) {
				throw new Exception("webservice 为空.");
			}
			l.put("result", ((Engine) Engine.engines.get(trade.getString("data_webservice_code"))).getResponse(trade.getString("data_webservice_method_code"), webservice.getString("header"), rtn));
			// +++
			// l.put("result", CommonFun.object2Json(rtn));
			wsd.updateLog(trade.getLong("id"), l);

			if (main_in != null) {
				insertInParams(trade.getLong("id"), 0, rtn, main_in, wsd);
			}

			l = new Record();
			l.put("execute_begin_time", "sysdate");
			l.put("state", "3");
			wsd.updateLog(trade.getLong("id"), l);

			if (!CommonFun.isNe(method.getString("execute_procedure"))) {
				StringBuffer sql = new StringBuffer();
				sql.append("{call " + method.getString("execute_procedure") + "(:data_webservice_quene_id)}");
				Record ins = new Record();
				ins.put("data_webservice_quene_id", trade.getLong("id"));
				Map<String, Integer> outs = new HashMap<String, Integer>();
				wsd.executeCall(sql.toString(), ins, outs);
			}

			l = new Record();
			l.put("end_time", "sysdate");
			l.put("state", "4");
			wsd.updateLog(trade.getLong("id"), l);

			wsd.backup(trade.getLong("id"));
		} catch (Exception ex) {
			log.error(ex);
			saveError(trade, ex.getClass() + ": " + ex.getMessage());
		} finally {
			disconnect();
		}
	}

	public void saveError(Record trade, String errormsg) throws Exception {
		ClientDao wsd = null;
		try {
			connect("hospital_common");
			Record l = new Record();
			l.put("end_time", "sysdate");
			l.put("state", "-1");
			l.put("error_message", errormsg);
			wsd = new ClientDao();
			wsd.updateLog(trade.getLong("id"), l);
			wsd.backup(trade.getLong("id"));
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			disconnect();
		}
	}

	@SuppressWarnings("unchecked")
	private void insertInParams(long data_webservice_quene_id, int parent_id, Object params, Record table, ClientDao wsd) throws Exception {
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

		for (Map<String, Object> param : lin) {
			Record r = new Record();
			r.put("data_webservice_quene_id", data_webservice_quene_id);
			if (parent_id > 0) {
				r.put("parent_id", parent_id);
			}

			for (Record f : (List<Record>) table.get("fields")) {
				// +++
				String field_name = f.getString("name");
				String alias_name = wsd.getAlias(table.getString("table_name"), field_name);
				if ((TRADE_KEY_PRIX + "data_webservice_quene_id").equalsIgnoreCase(f.getString("name")) || (TRADE_KEY_PRIX + "id").equalsIgnoreCase(f.getString("name"))
						|| (TRADE_KEY_PRIX + "parent_id").equalsIgnoreCase(f.getString("name"))) {
					field_name = f.getString("name").substring(TRADE_KEY_PRIX.length());
				}
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
							if (f.getInt("size") < value.toString().getBytes().length) {
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
			int id = wsd.insertTable(table.getString("table_name"), r);
			for (Record t : (List<Record>) table.get("subtables")) {
				insertInParams(data_webservice_quene_id, id, param, t, wsd);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Object queryOutParams(long data_webservice_quene_id, int parent_id, Record table, ClientDao wsd) throws Exception {
		Object rtn = null;
		Record r = new Record();
		r.put("data_webservice_quene_id", data_webservice_quene_id);
		StringBuffer sql = new StringBuffer();
		sql.append("select * ");
		sql.append("from " + table.getString("table_name") + " ");
		sql.append("where data_webservice_quene_id = :data_webservice_quene_id ");
		if (parent_id > 0) {
			r.put("parent_id", parent_id);
			sql.append("and parent_id = :parent_id ");
		}
		Result rs = wsd.executeQuery(sql.toString(), r);
		List<Record> lin = new ArrayList<Record>();
		for (Record re : rs.getResultset()) {
			Record data = new Record();
			for (Record t : (List<Record>) table.get("subtables")) {
				Object op = queryOutParams(data_webservice_quene_id, re.getInt("id"), t, wsd);
				if (CommonFun.isCollection(op)) {
					return op;
				} else {
					data.putAll((Record) op);
				}
			}
			List<Record> fields = (List<Record>) table.get("fields");
			for (Record field : fields) {
				// +++
				String field_name = field.getString("name");
				String alias_name = wsd.getAlias(table.getString("table_name"), field_name);
				// +++
				Object value = re.get(field.getString("name").toLowerCase());
				if (value instanceof String && !CommonFun.isNe(value)) {
					String t = (String) value;
					if (t.startsWith("[")) {
						value = CommonFun.json2Object(t, List.class);
					} else if (t.startsWith("{")) {
						value = CommonFun.json2Object(t, Map.class);
					}
				}
				if (value != null && (Types.BLOB == field.getInt("type") || Types.BINARY == field.getInt("type") || Types.CLOB == field.getInt("type") || Types.VARBINARY == field.getInt("type")
						|| Types.LONGVARBINARY == field.getInt("type") || Types.NCLOB == field.getInt("type"))) {
					value = new BASE64Encoder().encode((byte[]) value);
				}
				if (!"data_webservice_quene_id".equalsIgnoreCase(field.getString("name")) && !"id".equalsIgnoreCase(field.getString("name"))
						&& !"parent_id".equalsIgnoreCase(field.getString("name"))) {
					if ((TRADE_KEY_PRIX + "data_webservice_quene_id").equalsIgnoreCase(field.getString("name")) || (TRADE_KEY_PRIX + "id").equalsIgnoreCase(field.getString("name"))
							|| (TRADE_KEY_PRIX + "parent_id").equalsIgnoreCase(field.getString("name"))) {
						data.put(field.getString("name").substring(TRADE_KEY_PRIX.length()), value);
						// +++
						field_name = field.getString("name").substring(TRADE_KEY_PRIX.length());
						// +++
					} else {
						data.put(field.getString("name"), value);
					}
				}
				// +++
				if (!CommonFun.isNe(alias_name)) {
					data.put(alias_name, data.remove(field_name));
				}
				// +++
			}
			lin.add(data);
		}

		if (!CommonFun.isNe(table.getString("param_name"))) {
			rtn = new Record();
			if ("M".equals(table.getString("param_type"))) {
				((Record) rtn).put(table.getString("param_name"), lin.get(0));
			} else {
				((Record) rtn).put(table.getString("param_name"), lin);
			}
		} else {
			if ("M".equals(table.getString("param_type"))) {
				rtn = lin.get(0);
			} else {
				rtn = lin;
			}
		}
		return rtn;
	}

	public void writeLog(Record logs) throws Exception {
		try {
			connect("hospital_common");
			ClientDao wsd = new ClientDao();
			wsd.writeLog(logs);
		} finally {
			disconnect();
		}
	}
	
	public void executeParamProcedure(Record method) throws Exception {
		executeParamProcedure(method, null, null);
	}
	public void executeParamProcedure(Record method, String patient_id, String visit_id) throws Exception {
		if (!CommonFun.isNe(method.getString("param_procedure"))) {
			try {
				connect("hospital_common");
				ClientDao wsd = new ClientDao();
				StringBuffer sql = new StringBuffer();
				Record ins = new Record();
				sql.append("{call " + method.getString("param_procedure") + "(:data_webservice_code, :data_webservice_method_code");
				ins.put("data_webservice_code", method.get("data_webservice_code"));
				ins.put("data_webservice_method_code", method.get("code"));
				if(patient_id != null){
					ins.put("patient_id", patient_id);
					sql.append(",:patient_id");
					if(visit_id != null){
						ins.put("visit_id", visit_id);
						sql.append(",:visit_id");
					}
				}
				sql.append(")}");
				Map<String, Integer> outs = new HashMap<String, Integer>();
				log.debug("SQL:" + sql.toString() + "参数： " + ins.toString());
				wsd.executeCall(sql.toString(), ins, outs);
			} finally {
				disconnect();
			}
		}
	}
}
