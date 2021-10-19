package cn.crtech.precheck.message.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.crtech.cooperop.bus.message.sender.EmailSender;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.precheck.message.dao.MessageDao;

public class MessageService extends BaseService {
	private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Record getEmailConfig() throws Exception {
		try {
			connect();
			return new MessageDao().getEmailConfig();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void send() throws Exception {
		sendEmail();
	}

	private Record sendEmail() throws Exception {
		try {
			connect();
			MessageDao md = new MessageDao();

			Record config = md.getEmailConfig();

			StringBuffer datahtml = new StringBuffer();

			Result se = md.queryServerError(config);
			if (se.getCount() > 0) {
				datahtml.append("<h2>服务端异常报告(" + se.getCount() + ")</h2>");
				datahtml.append("<table>");
				datahtml.append("	<thead>");
				datahtml.append("		<tr>");
				datahtml.append("			<th>服务编号</th>");
				datahtml.append("			<th>服务名称</th>");
				datahtml.append("			<th>接口编号</th>");
				datahtml.append("			<th>接口名称</th>");
				datahtml.append("			<th>最后一次异常时间</th>");
				datahtml.append("			<th>异常信息</th>");
				datahtml.append("		</tr>");
				datahtml.append("	</thead>");
				datahtml.append("	<tbody>");
				for (Record r : se.getResultset()) {
					datahtml.append("		<tr>");
					datahtml.append("			<td>" + r.getString("data_service_code") + "</td>");
					datahtml.append("			<td>" + r.getString("date_service_name") + "</td>");
					datahtml.append("			<td>" + r.getString("data_service_method_code") + "</td>");
					datahtml.append("			<td>" + r.getString("data_service_method_name") + "</td>");
					datahtml.append("			<td>" + FORMAT.format(r.getDate("last_error_time")) + "</td>");
					datahtml.append("			<td>" + r.getString("error_message") + "</td>");
					datahtml.append("		</tr>");
				}
				datahtml.append("	</tbody>");
				datahtml.append("</table>");
			}

			Result ce = md.queryClientError(config);
			if (ce.getCount() > 0) {
				datahtml.append("<h2>客户端异常报告(" + ce.getCount() + ")</h2>");
				datahtml.append("<table>");
				datahtml.append("	<thead>");
				datahtml.append("		<tr>");
				datahtml.append("			<th>客户端编号</th>");
				datahtml.append("			<th>客户端名称</th>");
				datahtml.append("			<th>接口编号</th>");
				datahtml.append("			<th>接口名称</th>");
				datahtml.append("			<th>最后一次异常时间</th>");
				datahtml.append("			<th>异常信息</th>");
				datahtml.append("		</tr>");
				datahtml.append("	</thead>");
				datahtml.append("	<tbody>");
				for (Record r : ce.getResultset()) {
					datahtml.append("		<tr>");
					datahtml.append("			<td>" + r.getString("data_webservice_code") + "</td>");
					datahtml.append("			<td>" + r.getString("date_webservice_name") + "</td>");
					datahtml.append("			<td>" + r.getString("data_webservice_method_code") + "</td>");
					datahtml.append("			<td>" + r.getString("data_webservice_method_name") + "</td>");
					datahtml.append("			<td>" + FORMAT.format(r.getDate("last_error_time")) + "</td>");
					datahtml.append("			<td>" + r.getString("error_message") + "</td>");
					datahtml.append("		</tr>");
				}
				datahtml.append("	</tbody>");
				datahtml.append("</table>");
			}

			Result so = md.queryServerOverTime(config);
			if (so.getCount() > 0) {
				datahtml.append("<h2>服务端交易超时报告(" + so.getCount() + ")</h2>");
				datahtml.append("<table>");
				datahtml.append("	<thead>");
				datahtml.append("		<tr>");
				datahtml.append("			<th>服务编号</th>");
				datahtml.append("			<th>服务名称</th>");
				datahtml.append("			<th>接口编号</th>");
				datahtml.append("			<th>接口名称</th>");
				datahtml.append("			<th>交易状态</th>");
				datahtml.append("			<th>最后一次交易时间</th>");
				datahtml.append("			<th>异常信息</th>");
				datahtml.append("		</tr>");
				datahtml.append("	</thead>");
				datahtml.append("	<tbody>");
				for (Record r : so.getResultset()) {
					datahtml.append("		<tr>");
					datahtml.append("			<td>" + r.getString("data_service_code") + "</td>");
					datahtml.append("			<td>" + r.getString("date_service_name") + "</td>");
					datahtml.append("			<td>" + r.getString("data_service_method_code") + "</td>");
					datahtml.append("			<td>" + r.getString("data_service_method_name") + "</td>");
					datahtml.append("			<td>" + r.getString("state") + "</td>");
					datahtml.append("			<td>" + FORMAT.format(r.getDate("first_begin_time")) + "</td>");
					datahtml.append("			<td>" + r.getString("error_message") + "</td>");
					datahtml.append("		</tr>");
				}
				datahtml.append("	</tbody>");
				datahtml.append("</table>");
			}

			Result co = md.queryClientOverTime(config);
			if (co.getCount() > 0) {
				datahtml.append("<h2>客户端交易超时报告(" + co.getCount() + ")</h2>");
				datahtml.append("<table>");
				datahtml.append("	<thead>");
				datahtml.append("		<tr>");
				datahtml.append("			<th>客户端编号</th>");
				datahtml.append("			<th>客户端名称</th>");
				datahtml.append("			<th>接口编号</th>");
				datahtml.append("			<th>接口名称</th>");
				datahtml.append("			<th>交易状态</th>");
				datahtml.append("			<th>最后一次交易时间</th>");
				datahtml.append("			<th>异常信息</th>");
				datahtml.append("		</tr>");
				datahtml.append("	</thead>");
				datahtml.append("	<tbody>");
				for (Record r : co.getResultset()) {
					datahtml.append("		<tr>");
					datahtml.append("			<td>" + r.getString("data_webservice_code") + "</td>");
					datahtml.append("			<td>" + r.getString("date_webservice_name") + "</td>");
					datahtml.append("			<td>" + r.getString("data_webservice_method_code") + "</td>");
					datahtml.append("			<td>" + r.getString("data_webservice_method_name") + "</td>");
					datahtml.append("			<td>" + r.getString("state") + "</td>");
					datahtml.append("			<td>" + FORMAT.format(r.getDate("first_begin_time")) + "</td>");
					datahtml.append("			<td>" + r.getString("error_message") + "</td>");
					datahtml.append("		</tr>");
				}
				datahtml.append("	</tbody>");
				datahtml.append("</table>");
			}

			if (datahtml.length() > 0) {
				String content = config.getString("content");
				content = content.replaceAll("(?i)@\\[TIME\\]", FORMAT.format(new Date()));
				content = content.replaceAll("(?i)@\\[DATA\\]", datahtml.toString().replaceAll("\\$", "\\\\\\$"));
				EmailSender.send("crdxp", null, config.getString("to"), config.getString("subject"), content, null);
			}
			return config;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
