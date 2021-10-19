<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="">
	<s:row >
		<s:form label="消息设置" id="setFrom">
			<s:toolbar>
				<s:button label="保存" onclick="save();"></s:button>
				<s:button label="测试" onclick="send();"></s:button>
			</s:toolbar>
			<s:row>
			<input type="hidden" name="id" value="${msort.id }"/>
				<%-- <s:radio label="模版类型" name="sort" value="${empty sort?1:sort }" required="true" onclick="showp()">
					<s:option label="业务" value="1"></s:option>
					<s:option label="单据" value="3"></s:option>
				</s:radio> --%>
				<s:textfield label="模版名称" name="title" value="${msort.title }" cols="2" maxlength="64" required="true"></s:textfield>
			</s:row>
			<s:row>
				<s:radio onchange="setbills()" label="所属产品" name="system_product_code" action="application.common.listProducts" required="true"
					cols="4" value="${msort.system_product_code }">
					<s:option value="$[code]" label="$[name]"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<c:if test="${param.sort eq '3' }">
					<input type="hidden" name="sort" value="${empty msort.sort?sort:msort.sort }"/>
					<s:autocomplete label="单据" name="pageurl" value="${msort.pageurl }" id="autodj" cols="2"  
						required="true" text="${msort.page_name }"
						action="crdc.designer.query">
						<s:option value="$[system_product_code].$[type].$[flag].$[id]" label="$[id]-$[description]">$[id]-$[description]</s:option>
					</s:autocomplete>
				</c:if>
				<c:if test="${param.sort eq '1' }">
					<input type="hidden" name="sort" value="${empty msort.sort?sort:msort.sort }"/>
					<s:textfield label="业务代码" name="action_" value="${msort.action_ }" cols="2"  required="true" placeholder="如：hr.user.delete"></s:textfield>
				</c:if>
				<c:if test="${param.sort eq '2' or param.sort eq '4' }">
					<s:autocomplete label="流程" onchange="setNode();" name="system_product_process_id" value="${msort.system_product_process_id }" id="process_" cols="2"  required="true"
						action="application.systemProcess.query" text="${msort.process_name }">
						<s:option value="$[id]" label="$[id]-$[name]">$[id]-$[name]</s:option>
					</s:autocomplete>
					<s:autocomplete label="流程节点" name="system_product_process_node" value="${msort.system_product_process_node }" id="node_" cols="2"  required="true"
						action="application.systemProcess.queryNode" text="${msort.node_name }">
						<s:option value="$[id]" label="$[id]-$[name]">$[id]-$[name]</s:option>
					</s:autocomplete>
					<s:radio label="发送阶段" name="sort" value="${empty msort.sort?'2':msort.sort }" required="true" cols="2">
						<s:option label="到达节点" value="4"></s:option>
						<s:option label="节点结束" value="2"></s:option>
					</s:radio>
				</c:if>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:tabpanel>
			<s:form label="邮件模版" id="emailform" active="true">
				<s:row>
					<input type="hidden" name="id" value="${email.id }"/>
					<s:textarea label="主题模版" name="subject" maxlength="2048" required="true" cols="4" placeholder="发送邮件的时候使用">${email.subject }</s:textarea>
					<s:textarea label="内容模版" name="content" maxlength="2048" required="true" cols="4"  autosize="true">${email.content }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="发送条件" name="condition_sql" maxlength="2048" required="true" cols="4" placeholder="返回结果为‘Y’时发送" autosize="true">${email.condition_sql }</s:textarea>
					<s:textarea label="接收人sql" name="sendto_sql" maxlength="2048" required="true" cols="4" autosize="true">${email.sendto_sql }</s:textarea>
				</s:row>
				<s:row>
					<s:autocomplete label="通道" name="tunnel_id" required="true" value="${email.tunnel_id }"
						action="application.tunnel.email.query" text="${email.tunnel_name }">
						<s:option label="$[id]-$[name]" value="$[id]">$[id]-$[name]</s:option>
					</s:autocomplete>
					<s:switch label="开启" name="state" value="${email.state }" onvalue="1" offvalue="-1"></s:switch>
					<s:switch label="实时消息" name="instant" value="${email.instant }" onvalue="1" offvalue="-1"></s:switch>
				</s:row>
			</s:form>
			<s:form label="短信模版" id="smsform">
				<s:row>
					<s:textarea label="发送条件" name="condition_sql" required="true" maxlength="2048" cols="4" placeholder="返回结果为‘Y’时发送" autosize="true">${sms.condition_sql }</s:textarea>
					<s:textarea label="接收人sql" name="sendto_sql" required="true" maxlength="2048" cols="4" autosize="true">${sms.sendto_sql }</s:textarea>
				</s:row>
				<s:row>
					<input type="hidden" name="id" value="${sms.id }"/>
					<s:textarea label="内容模版" name="content" required="true" cols="4" maxlength="2048" autosize="true">${sms.content }</s:textarea>
					<s:textarea label="内容sql" name="content_sql" required="true" cols="4" maxlength="2048" autosize="true">${sms.content_sql }</s:textarea>
				</s:row>
				<s:row>
					<s:textfield label="模板code" name="content_template_id" value="${sms.content_template_id}"></s:textfield>
					<s:autocomplete label="通道" name="tunnel_id" required="true" value="${sms.tunnel_id }"
						action="application.tunnel.sms.query" text="${sms.tunnel_name }">
						<s:option label="$[name]" value="$[id]">$[name]</s:option>
					</s:autocomplete>
					<s:switch label="开启" name="state" value="${sms.state }" onvalue="1" offvalue="-1"></s:switch>
					<s:switch label="实时消息" name="instant" value="${sms.instant }" onvalue="1" offvalue="-1"></s:switch>
				</s:row>
			</s:form>
			<s:form label="系统即时消息模版" id="xtform">
				<s:row>
					<input type="hidden" name="id" value="${xt.id }"/>
					<s:textarea label="标题模版" name="subject" maxlength="2048" required="true" cols="4" placeholder="推送通知栏信息使用">${xt.subject }</s:textarea>
					<s:textarea label="内容模版sql" name="content" cols="4" required="true" maxlength="2048" autosize="true">${xt.content }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="发送条件sql" name="condition_sql" maxlength="2048" cols="4" placeholder="返回结果为‘Y’时发送" required="true" autosize="true">${xt.condition_sql }</s:textarea>
					<s:textarea label="接收人sql" name="sendto_sql" maxlength="2048" cols="4" required="true" autosize="true">${xt.sendto_sql }</s:textarea>
				</s:row>
				<s:row>
					<s:switch label="开启" name="state" value="${xt.state }" onvalue="1" offvalue="-1"></s:switch>
					<s:switch label="实时消息" name="instant" value="${xt.instant }" onvalue="1" offvalue="-1"></s:switch>
				</s:row>
			</s:form>
			<s:form label="微信消息模版" id="wxform">
				<s:row>
					<input type="hidden" name="id" value="${wx.id }"/>
					<s:textarea label="内容模版" required="true" name="content" maxlength="2048" cols="4" autosize="true">${wx.content }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="发送条件" required="true" name="condition_sql" maxlength="2048" cols="4" placeholder="返回结果为‘Y’时发送" autosize="true">${wx.condition_sql }</s:textarea>
					<s:textarea label="接收人sql" required="true" name="sendto_sql" maxlength="2048" cols="4" autosize="true">${wx.sendto_sql }</s:textarea>
				</s:row>
				<s:row>
					<s:switch label="开启" name="state" value="${wx.state }" onvalue="1" offvalue="-1"></s:switch>
					<s:switch label="实时消息" name="instant" value="${wx.instant }" onvalue="1" offvalue="-1"></s:switch>
				</s:row>
			</s:form>
			<s:form label="站内消息模版" id="zhannform">
				<s:row>
					<input type="hidden" name="id" value="${wx.id }"/>
					<s:textarea label="内容模版" required="true" name="content" maxlength="2048" cols="4" autosize="true">${wx.content }</s:textarea>
				</s:row>
				<s:row>
					<s:textarea label="发送条件" required="true" name="condition_sql" maxlength="2048" cols="4" placeholder="返回结果为‘Y’时发送" autosize="true">${wx.condition_sql }</s:textarea>
					<s:textarea label="接收人sql" required="true" name="sendto_sql" maxlength="2048" cols="4" autosize="true">${wx.sendto_sql }</s:textarea>
				</s:row>
				<s:row>
					<s:switch label="开启" name="state" value="${wx.state }" onvalue="1" offvalue="-1"></s:switch>
					<s:switch label="实时消息" name="instant" value="${wx.instant }" onvalue="1" offvalue="-1"></s:switch>
				</s:row>
			</s:form>
		</s:tabpanel>
	</s:row>
</s:page>
<script type="text/javascript">
$(document).ready(function(){
	setbills();
	setNode();
})
function setbills(){
	var d = $("#setFrom").getData();
	$("#autodj").params({type : "bill", system_product_code: d.system_product_code});
	$("#process_").params({system_product_code: d.system_product_code});
}
function setNode(){
	var d = $("#setFrom").getData();
	$("#node_").params({system_product_process_id: d.system_product_process_id});
}
function save(){
	var d ={};
	d.sort = $("#setFrom").getData()
	d.id = d.sort.id;
	var xt = $("#xtform").getData();
	/* if(xt.state=='1'){
		if (!$("#xtform").valid()) {
			return false;	
		}
	} */
	d.xt = xt;
	var sms = $("#smsform").getData();
	/* if(sms.state=='1'){
		if (!$("#smsform").valid()) {
			return false;	
		}
	} */
	d.sms = sms;
	var email = $("#emailform").getData();
	/* if(email.state=='1'){
		if (!$("#emailform").valid()) {
			return false;	
		}
	} */
	d.email = email;
	var wx = $("#wxform").getData();
	/* if(wx.state=='1'){
		if (!$("#wxform").valid()) {
			return false;	
		}
	} */
	d.wx = wx;
	var sysmes = $("#zhannform").getData();
	d.sysmes = sysmes;
	$.call("application.messageTemplate.save", {jdata: $.toJSON(d)}, function(rtn) {
		if (rtn) {
			$.closeModal(true);
		}
	});
}
 function send (){
	 $.call("application.messageTemplate.jqtest", {code: '123451', p_code: 'oa', p_id:'ZTAZ99', p_node:'task3'}, function(rtn) {
			if (rtn) {
				$.closeModal(true);
			}
		});
 }
</script>
