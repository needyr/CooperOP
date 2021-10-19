package cn.crtech.cooperop.application.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class WeiXinDao extends BaseDao {
	public Result querySubscribeUser(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from users(nolock) ");
		sql.append(" where 1 = 1 ");

		return executeQueryLimit(sql.toString(), params);
	}

	public int deleteSubscribeUser(Map<String, Object> conditions) throws Exception {
		return executeDelete("users", conditions);
	}

	public int insertSubscribeUser(Map<String, Object> params) throws Exception {
		return executeInsert("users", params);
	}

	public int updateSubscribeUser(Map<String, Object> params, Map<String, Object> conditions) throws Exception {
		return executeUpdate("users", params, conditions);
	}

	public Result queryMaterial(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from materials(nolock) ");
		sql.append(" where 1 = 1 ");

		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryMaterialContent(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from materials_content(nolock) ");
		sql.append(" where 1 = 1 ");

		return executeQueryLimit(sql.toString(), params);
	}

	public int deleteMaterial(Map<String, Object> conditions) throws Exception {
		int i = executeDelete("materials", conditions);
		executeDelete("materials_content", conditions);
		return i;
	}

	public int insertMaterial(Map<String, Object> params) throws Exception {
		return executeInsert("materials", params);
	}

	public int insertMaterialContent(Map<String, Object> params) throws Exception {
		return executeInsert("materials_content", params);
	}

	public Result queryTemplate(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from templates(nolock) ");
		sql.append(" where 1 = 1 ");

		return executeQueryLimit(sql.toString(), params);
	}

	public int deleteTemplate(Map<String, Object> conditions) throws Exception {
		int i = executeDelete("templates", conditions);
		return i;
	}

	public int insertTemplate(Map<String, Object> params) throws Exception {
		return executeInsert("templates", params);
	}

	public Result queryTemplateTransform(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from template_transform(nolock) ");
		sql.append(" where 1 = 1 ");

		return executeQueryLimit(sql.toString(), params);
	}

	public Record getTemplateTransform(String wx_code, String code) throws Exception {
		Record params = new Record();
		params.put("wx_code", wx_code);
		params.put("code", code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from template_transform(nolock) ");
		sql.append(" where wx_code = :wx_code ");
		sql.append("   and code = :code ");

		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Result queryMenu(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from menu(nolock) ");
		sql.append(" where 1 = 1 ");
		if (CommonFun.isNe(params.get("sort"))) {
			params.put("sort", "parent_name asc, order_no asc");
		}
		return executeQuery(sql.toString(), params);
	}

	public int deleteMenu(Map<String, Object> conditions) throws Exception {
		int i = executeDelete("menu", conditions);
		return i;
	}

	public int insertMenu(Map<String, Object> params) throws Exception {
		return executeInsert("menu", params);
	}

	public Result queryQRCode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from qrcode(nolock) ");
		sql.append(" where 1 = 1 ");

		return executeQueryLimit(sql.toString(), params);
	}

	public int insertQRCode(Map<String, Object> params) throws Exception {
		return executeInsert("qrcode", params);
	}

	public int deleteQRCode(Map<String, Object> params) throws Exception {
		return executeDelete("qrcode", params);
	}

	public Record getEventReply(String wx_code, String event, String eventkey) throws Exception {
		Record params = new Record();
		params.put("wx_code", wx_code);
		params.put("event", event);
		params.put("eventkey", eventkey);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from event_reply(nolock) ");
		sql.append(" where wx_code = :wx_code ");
		sql.append("   and event = :event ");
		sql.append("   and eventkey = :eventkey ");

		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Result listEventReplyArticles(String wx_code, String event, String eventkey) throws Exception {
		Record params = new Record();
		params.put("wx_code", wx_code);
		params.put("event", event);
		params.put("eventkey", eventkey);
		params.put("sort", "order_no asc");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from event_reply_article(nolock) ");
		sql.append(" where wx_code = :wx_code ");
		sql.append("   and event = :event ");
		sql.append("   and eventkey = :eventkey ");

		return executeQuery(sql.toString(), params);
	}

	public int insertEventLog(Record log) throws Exception {
		return executeInsert("event_log", log);
	}

}
