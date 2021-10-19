package cn.crtech.precheck.message.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.MethodDao;
import cn.crtech.cooperop.hospital_common.dao.ServicelogDao;
import cn.crtech.cooperop.hospital_common.dao.TypeDao;
import cn.crtech.cooperop.hospital_common.dao.WeblogDao;
import cn.crtech.cooperop.hospital_common.dao.WebmethodDao;
import cn.crtech.cooperop.hospital_common.dao.WebserviceDao;

public class MessageDao extends BaseDao {
	public final static String EMAIL_TABLE_NAME = "errmessage_email";
	
	public Record getEmailConfig() throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ");
		sql.append(EMAIL_TABLE_NAME);
		sql.append(" (nolock) ");
		sql.append(" where state = 1 ");
		return executeQuerySingleRecord(sql.toString(), null);
	}
	
	public Result queryServerError(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                                                            ");
		sql.append(" 	dslb.*, ds.name AS date_service_name,                                          ");
		sql.append(" 	dsm.name AS data_service_method_name                                           ");
		sql.append(" FROM                                                                              ");
		sql.append(" 	(                                                                              ");
		sql.append(" 		SELECT                                                                     ");
		sql.append(" 			data_service_code,                                                     ");
		sql.append(" 			data_service_method_code,                                              ");
		sql.append(" 			CONVERT (VARCHAR(MAX), error_message) AS error_message,                ");
		sql.append(" 			MAX (end_time) AS last_error_time                                      ");
		sql.append(" 		FROM                                                                       ");
		sql.append(ServicelogDao.TABLE_NAME_DONE);
		sql.append(" 	(nolock)	WHERE                                                                      ");
		sql.append(" 			state = - 1                                                            ");
		sql.append(" 		AND end_time > DATEADD(MINUTE, -" + params.get("cycle") + ", GETDATE())                              ");
		sql.append(" 		GROUP BY                                                                   ");
		sql.append(" 			data_service_code,                                                     ");
		sql.append(" 			data_service_method_code,                                              ");
		sql.append(" 			CONVERT (VARCHAR(MAX), error_message)                                  ");
		sql.append(" 	) dslb  (nolock)                                                                       ");
		sql.append(" LEFT JOIN " + MethodDao.TABLE_NAME + " dsm ON dsm.code = dslb.data_service_method_code     ");
		sql.append(" AND dsm.data_service_code = dslb.data_service_code                                ");
		sql.append(" LEFT JOIN " + TypeDao.TABLE_NAME + " ds ON ds.code = dslb.data_service_code                     ");
		sql.append(" ORDER BY                                                                          ");
		sql.append(" 	data_service_code ASC,                                                         ");
		sql.append(" 	data_service_method_code ASC,                                                  ");
		sql.append(" 	last_error_time DESC,                                                          ");
		sql.append(" 	error_message ASC                                                              ");
		return executeQuery(sql.toString(), params);
	}

	public Result queryClientError(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                                                                ");
		sql.append(" 	dwqb.*, dw.name AS date_webservice_name,                                           ");
		sql.append(" 	dwm.name AS data_webservice_method_name                                            ");
		sql.append(" FROM                                                                                  ");
		sql.append(" 	(                                                                                  ");
		sql.append(" 		SELECT                                                                         ");
		sql.append(" 			data_webservice_code,                                                      ");
		sql.append(" 			data_webservice_method_code,                                               ");
		sql.append(" 			CONVERT (VARCHAR(MAX), error_message) AS error_message,                    ");
		sql.append(" 			MAX (end_time) AS last_error_time                                          ");
		sql.append(" 		FROM                                                                           ");
		sql.append(WeblogDao.TABLE_NAME_DONE);
		sql.append(" 	(nolock)	WHERE                                                                          ");
		sql.append(" 			state = - 1                                                                ");
		sql.append(" 		AND end_time > DATEADD(MINUTE, -" + params.get("cycle") + ", GETDATE())                                  ");
		sql.append(" 		GROUP BY                                                                       ");
		sql.append(" 			data_webservice_code,                                                      ");
		sql.append(" 			data_webservice_method_code,                                               ");
		sql.append(" 			CONVERT (VARCHAR(MAX), error_message)                                      ");
		sql.append(" 	) dwqb   (nolock)                                                                          ");
		sql.append(" LEFT JOIN " + WebmethodDao.TABLE_NAME + " dwm ON dwm.code = dwqb.data_webservice_method_code   ");
		sql.append(" AND dwm.data_webservice_code = dwqb.data_webservice_code                              ");
		sql.append(" LEFT JOIN " + WebserviceDao.TABLE_NAME + " dw ON dw.code = dwqb.data_webservice_code                   ");
		sql.append(" ORDER BY                                                                              ");
		sql.append(" 	data_webservice_code ASC,                                                          ");
		sql.append(" 	data_webservice_method_code ASC,                                                   ");
		sql.append(" 	last_error_time DESC,                                                              ");
		sql.append(" 	error_message ASC		                                                           ");
		
		return executeQuery(sql.toString(), params);
	}

	public Result queryServerOverTime(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                                                         "); 
		sql.append(" 	dsl.*, ds.name AS date_service_name,                                        "); 
		sql.append(" 	dsm.name AS data_service_method_name                                        "); 
		sql.append(" FROM                                                                           "); 
		sql.append(" 	(                                                                           "); 
		sql.append(" 		SELECT                                                                  "); 
		sql.append(" 			data_service_code,                                                  "); 
		sql.append(" 			data_service_method_code,                                           "); 
		sql.append(" 			state AS state,                                                     "); 
		sql.append(" 			CONVERT (VARCHAR(MAX), error_message) AS error_message,             "); 
		sql.append(" 			MIN (begin_time) AS first_begin_time                                "); 
		sql.append(" 		FROM                                                                    "); 
		sql.append(ServicelogDao.TABLE_NAME_DONE);
		sql.append(" 	(nolock)	WHERE                                                                   "); 
		sql.append(" 			begin_time < DATEADD(MINUTE, -" + params.get("overtime") + ", GETDATE())                       "); 
		sql.append(" 		GROUP BY                                                                "); 
		sql.append(" 			data_service_code,                                                  "); 
		sql.append(" 			data_service_method_code,                                           "); 
		sql.append(" 			state,                                                              "); 
		sql.append(" 			CONVERT (VARCHAR(MAX), error_message)                               "); 
		sql.append(" 	) dsl   (nolock)                                                                    "); 
		sql.append(" LEFT JOIN " + MethodDao.TABLE_NAME + " dsm ON dsm.code = dsl.data_service_method_code   "); 
		sql.append(" AND dsm.data_service_code = dsl.data_service_code                              "); 
		sql.append(" LEFT JOIN " + TypeDao.TABLE_NAME + " ds ON ds.code = dsl.data_service_code                   "); 
		sql.append(" ORDER BY                                                                       "); 
		sql.append(" 	data_service_code ASC,                                                      "); 
		sql.append(" 	data_service_method_code ASC,                                               "); 
		sql.append(" 	first_begin_time ASC,                                                       "); 
		sql.append(" 	state ASC,                                                                  "); 
		sql.append(" 	error_message ASC                                                           "); 
		return executeQuery(sql.toString(), params);
	}

	public Result queryClientOverTime(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                                                              ");
		sql.append(" 	dwq.*, dw.name AS date_webservice_name,                                          ");
		sql.append(" 	dwm.name AS data_webservice_method_name                                          ");
		sql.append(" FROM                                                                                ");
		sql.append(" 	(                                                                                ");
		sql.append(" 		SELECT                                                                       ");
		sql.append(" 			data_webservice_code,                                                    ");
		sql.append(" 			data_webservice_method_code,                                             ");
		sql.append(" 			state AS state,                                                          ");
		sql.append(" 			CONVERT (VARCHAR(MAX), error_message) AS error_message,                  ");
		sql.append(" 			MIN (begin_time) AS first_begin_time                                     ");
		sql.append(" 		FROM                                                                         ");
		sql.append(WeblogDao.TABLE_NAME_DONE);
		sql.append(" 	(nolock) 	WHERE                                                                        ");
		sql.append(" 			begin_time < DATEADD(MINUTE, -" + params.get("overtime") + ", GETDATE())                            ");
		sql.append(" 		GROUP BY                                                                     ");
		sql.append(" 			data_webservice_code,                                                    ");
		sql.append(" 			data_webservice_method_code,                                             ");
		sql.append(" 			state,                                                                   ");
		sql.append(" 			CONVERT (VARCHAR(MAX), error_message)                                    ");
		sql.append(" 	) dwq  (nolock)                                                                           ");
		sql.append(" LEFT JOIN " + WebmethodDao.TABLE_NAME + " dwm ON dwm.code = dwq.data_webservice_method_code  ");
		sql.append(" AND dwm.data_webservice_code = dwq.data_webservice_code                             ");
		sql.append(" LEFT JOIN " + WebserviceDao.TABLE_NAME + " dw ON dw.code = dwq.data_webservice_code                  ");
		sql.append(" ORDER BY                                                                            ");
		sql.append(" 	data_webservice_code ASC,                                                        ");
		sql.append(" 	data_webservice_method_code ASC,                                                 ");
		sql.append(" 	first_begin_time ASC,                                                            ");
		sql.append(" 	state ASC,                                                                       ");
		sql.append(" 	error_message ASC                                                                ");
		return executeQuery(sql.toString(), params);
	}
}
