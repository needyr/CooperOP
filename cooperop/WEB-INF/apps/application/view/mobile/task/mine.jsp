<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<style type="text/css">
	.page-container .page-content {
    padding: 0px 0px 35px 0px !important;
}

 .row-fluid>.cols4 {
    position: relative;
    padding-right: 0px !important;
    padding-left: 0px !important;
}

.page-container .page-content .portlet.box>.portlet-body {
    padding: 32px 0px 10px 0px;
}

.page-container .page-content .portlet.light.bordered {
    border:none !important;
}

.cols4 .tab-portlet>.portlet-title>.nav-tabs {
    border-bottom:none;
}

.page-container .page-content .portlet > .portlet-title > .nav-tabs > li.active > a {
    padding:0 !important;
    border-bottom: 1px solid #7fbff3 !important;
    text-align: center;
}
.tab-portlet > .portlet-title {
    background-color: #BFBFBF;
    width: 100%;
    padding: 0;
    background-color: #fff !important;
    border-top: 1px solid #d6d6d6;
    border-bottom: 1px solid #edebeb;
    position: fixed;
    top: 0;
    z-index: 999;
    /* box-shadow: 0px 2px 20px #e8e5e5; */
}

.main-massage .massage-content .zi a {
    color: #fff;
    width: 50px;
    height: 50px;
    display: block;
    text-align: center;
    line-height: 1.8em;
    font-size: 27px;
   	margin: 0 !important;
    font-weight: 300;
  
    /* top: 12px; */
    overflow: hidden;
}

.tab-pane .form-horizontal {
    padding: 0px 0px !important;
}

.tab-pane>.form-horizontal>.row-fluid:first-child{
   	 display:none; 
}


.xiala{
	width: 100%;
    height: 20px;
}

.xiala span{
	color: #bbb;
    display: block;
    padding-top:6px;
    text-align:center;
	}
</style>
<s:page title="我的任务列表" dispermission="true">
		<%-- <s:row>
			<s:table id="optionlist" action="application.task.queryMine" autoload="false"  
				fitwidth=" " sort="true" label="任务列表" custom="true">
				<s:table.fields>
					<s:table.field name="table_field" datatype="template">
						 <div class="massage-content">
		                    <button name="button" class="btn" onclick="todeal('$[info_bill]','$[order_no]','$[task_id]','$[order_id]','$[node_name]')">处理</button>
		                    <div class="trdiv">
			                    <b class="zi">$[subject]</b>
			                    <span class="zhuangtai_button">$[system_product_code]</span>
			                    <span class="zhubiaoti_button">$[subject]</span>
			                    <span class="fubiaoti_button">$[node_name]</span>
			                    <span class="neirong_button">$[create_user_name]发起业务$[djbh]</span>
		                    </div>
		                </div>
					</s:table.field>
				</s:table.fields>
			</s:table>
		</s:row> --%>
			<s:row>
		<s:tabpanel>
			<s:form label="待办事项" icon="fa fa-calendar" active="true" onclick="query();">
				<s:row>
					<s:form id="conditions" fclass="portlet light bordered" collapsed="true" extendable="true">
						<s:row>
							<s:select label="所属产品" name="system_product_code" action="application.common.listProducts" cols="1" value="" onchange="changeProduct()">
								<s:option value="" label="全部"></s:option>
								<s:option value="$[code]" label="$[name]"></s:option>
							</s:select>
							<s:autocomplete label="流程" id="process_id" name="process_id" action="application.task.listProcess" cols="1" limit="10">
								<s:option value="$[id]" label="$[name]($[count])">$[name]($[count])</s:option>
							</s:autocomplete>
							<s:autocomplete label="发起人" name="creator" action="application.contacter.queryMine" params="{&#34;type&#34;:&#34;U&#34;}" cols="1" limit="10">
								<s:option value="$[id]" label="$[name]">$[name]</s:option>
							</s:autocomplete>
							<s:select label="期限" name="expire_mins" value="">
								<s:option value="" label="全部"></s:option>
								<s:option value="-1" label="已过期"></s:option>
								<s:option value="1440" label="24小时内到期"></s:option>
								<s:option value="2880" label="48小时内到期"></s:option>
							</s:select>
						</s:row>
						<s:row>
							<s:datefield label="开始日期" name="create_time_b" format="yyyy-MM-dd"></s:datefield>
							<s:datefield label="截至日期" name="create_time_e" format="yyyy-MM-dd"></s:datefield>
						</s:row>
						<s:row>
							<s:textfield label="关键字" cols="2" name="keyword" placeholder="匹配单据号、主题"></s:textfield>
							<s:button label="查询" icon="fa fa-search" color="green" onclick="exe_query()"></s:button>
						</s:row>
					</s:form>
				</s:row>
			
				<s:row>
					<s:table id="optionlist" action="application.task.queryMine" custom="true" autoload="false" fitwidth="true" sort="true" label="任务列表">
					<s:toolbar>
						<s:button label="筛选" icon="fa fa-filter" onclick="location.reload();"></s:button>
						<s:button label="取消" icon="icon-close" onclick="close_cx();" style="display:none;"></s:button>
					</s:toolbar>
						<s:table.fields>
							<s:table.field name="table_field" datatype="template">
								 <div class="massage-content">
				                    <!-- <button name="button" class="btn" onclick="todeal('$[info_bill]','$[order_no]','$[task_id]','$[order_id]','$[node_name]')"></button> -->
				                    <div class="trdiv">
					                    <b class="zi">$[node_name]</b>
					                    <span class="zhuangtai_button">$[system_product_name]</span>
					                    <span class="zhubiaoti_button">$[node_name]$[node_bill]</span>
					                    <span class="fubiaoti_button">$[order_no] $[create_user_name]</span>
					                    <span class="neirong_button">$[subject]</span>
					                    <div class="btn-div" style="display:none;">
						                    <a class="btn-divBtn" href="javascript:void(0);" 
						                    node_bill="$[node_bill]"
						                    order_no="$[order_no]"
						                    task_id="$[task_id]"
						                    order_id="$[order_id]"
						                    node_name="$[node_name]"
						                    onclick="todeal('$[node_bill]','$[order_no]','$[task_id]','$[order_id]','$[node_name]')">处理</a>
					                    </div>
				                    </div>
				                </div>
							</s:table.field>
							<s:table.field name="node_bill" label="node_bill"  sort="true" width="80" ></s:table.field>
							<s:table.field name="system_product_name" label="所属产品"  sort="true" ></s:table.field>
							<s:table.field name="order_no" label="单据号"  sort="true"></s:table.field>
							<s:table.field name="subject" label="主题"  datatype="script" sort="true" >
								var html = [];
								html.push(record.subject);
								return html.join("");
							</s:table.field>
							<s:table.field name="node_name" label="当前环节" >
							</s:table.field>
							<s:table.field name="create_user_name" label="发起人" hidden="true" ></s:table.field>
							<s:table.field name="create_time" label="到达时间" align="center" sort="true"   hidden="true"></s:table.field>
							<s:table.field name="expire_mins" label="剩余时间" hidden="true" align="right" sort="true" defaultsort="asc" datatype="script" >
								var comtime = function(mins) {
									if (mins / (24 * 60) >= 1) {
										return Math.floor(10 * mins / (24 * 60)) / 10 + "天";
									} else if (mins / 60 >= 1) {
										return Math.floor(10 * mins / 60) / 10 + "小时";
									} else {
										return mins + "分钟";
									}
								}
								var html = [];
								if (record.expire_mins == 99999999) {
									html.push('<font class="font-green">充裕</font>');
								} else if (record.expire_mins < 0) {
									html.push('<font class="font-red-flamingo">已过期' + comtime(-record.expire_mins) + '</font>');
								} else if (record.expire_mins > 0 && record.expire_mins <= 24 * 60) {
									html.push('<font class="font-yellow-gold">' + comtime(record.expire_mins) + '</font>');
								} else if (record.expire_mins > 0 && record.expire_mins <= 48 * 60) {
									html.push('<font class="font-yellow">' + comtime(record.expire_mins) + '后到期</font>');
								}
								return html.join("");
							</s:table.field>
							<s:table.field name="oper" datatype="script" label="操作" 
								align="center"  >
								var html = [];
								if (record.is_cc == 1) {
									html.push('待<font class="font-yellow-casablanca">' + (record.operator_name ? record.operator_name.substring(0, record.operator_name.length - 1) : '') + '</font>处理');
								} else {
									html.push('<a href="javascript:void(0)" class="todeal" ');
									html.push('onclick="todeal(\'' + record.node_bill + '\', \'' + record.order_no + '\', ');
									html.push('\'' + record.task_id + '\',\'' + record.order_id + '\',\'' + record.node_name + '\')">处理</a>');
								}
								return html.join("");
							</s:table.field>
						</s:table.fields>
					</s:table>
				</s:row>
 			</s:form>
			<s:form label="我发起的" icon="fa fa-tasks" onclick="query2();">
				<s:row>
					<s:form id="conditions2" fclass="portlet light bordered">
						<s:row>
							<s:select label="所属产品" name="system_product_code" action="application.common.listProducts" cols="1" value="" onchange="changeProduct()">
								<s:option value="" label="全部"></s:option>
								<s:option value="$[code]" label="$[name]"></s:option>
							</s:select>
							<s:autocomplete label="流程" id="process_id" name="process_id" action="application.task.listProcess" cols="1" limit="10">
								<s:option value="$[id]" label="$[name]($[count])">$[name]($[count])</s:option>
							</s:autocomplete>
							<s:autocomplete label="发起人" name="creator" action="application.contacter.queryMine" params="{&#34;type&#34;:&#34;U&#34;}" cols="1" limit="10">
								<s:option value="$[id]" label="$[name]">$[name]</s:option>
							</s:autocomplete>
						</s:row>
						<s:row>
							<s:datefield label="开始日期" name="create_time_b" format="yyyy-MM-dd"></s:datefield>
							<s:datefield label="截至日期" name="create_time_e" format="yyyy-MM-dd"></s:datefield>
						</s:row>
						<s:row>
							<s:textfield label="单据号/主题" name="keyword" tips="匹配单据号、主题"></s:textfield>
							<s:checkbox label="流程状态" name="process_state" cols="2">
								<s:option label="全部" value=""></s:option>
								<s:option label="进行中" value="1"></s:option>
								<s:option label="已办结" value="2"></s:option>
							</s:checkbox>
							<s:button label="查询" icon="fa fa-search" color="green" onclick="exe_query2()"></s:button>
						</s:row>
					</s:form>
				</s:row>
				<s:row>
					<s:table id="optionlist2" custom="true" action="application.task.queryTasking" autoload="true" fitwidth="true" sort="true" label="任务列表">
						<s:toolbar>
							<s:button label="筛选" icon="fa fa-filter" onclick="show_cx();"></s:button>
							<s:button label="取消" icon="icon-close" onclick="close_cx();" style="display:none;"></s:button>
						</s:toolbar>
						<s:table.fields>
							<s:table.field name="table_field" datatype="template">
								<div class="massage-content">
				                    <div class="trdiv">
					                    <b class="zi">$[state]</b>
					                    <span class="zhuangtai">$[system_product_name]</span>
					                    <span class="zhubiaoti">$[order_no]</span>
					                    <span class="fubiaoti">$[operator_name]</span>
					                    <span class="neirong">发起时间$[process_create_time]<br />$[subject]</span>
					                    <div class="btn-div" style="display:none;">
						                    <a class="btn-divBtn" href="javascript:void(0);" 
						                   info_bill="$[info_bill]"
						                    order_no="$[order_no]"
						                    task_id="$[task_id]"
						                    order_id="$[order_id]"
						                    node_name="$[node_name]"
						                    onclick="todeal('$[info_bill]','$[order_no]','$[task_id]','$[order_id]','$[node_name]')">处理</a>
					                    </div>
				                    </div>
				                </div>
			                </s:table.field>
							<s:table.field name="system_product_name" label="所属产品"  sort="true" width="80" ></s:table.field>
							<s:table.field name="order_no" label="单据号"  sort="true" width="120" ></s:table.field>
							<s:table.field name="subject" label="主题"  datatype="script" sort="true" >
								var html = [];
								html.push(record.subject);
								return html.join("");
							</s:table.field>
							<s:table.field name="process_create_time" label="发起时间" align="center" defaultsort="desc" sort="true" width="120" ></s:table.field>
							<s:table.field name="state" label="当前状态" width="80" datatype="script">
								var html=[];
								if (record.order_end_time) {
									if(record.audited == 'N'){
										html.push("已驳回");
									}else{
										html.push(" 已通过");
									}
								} else if (record.system_product_process_id == 'free') {
									html.push('待' + ((record.operator_name && record.operator_name.indexOf(',') > 0) ? record.operator_name.substring(0, record.operator_name.length - 1) : record.operator_name) + '处理');
								} else {
									if(record.is_skim_time){
										html.push("处理中");
									}else{
										html.push("待处理");
									}
								}
								return html.join("");
							</s:table.field>
							<s:table.field name="operator_name" label="当前处理人" width="80" datatype="script">
								var html=[];
								if (record.order_end_time) {
									if(record.audited == 'N'){
										html.push('<span style="color:red"> 已驳回</span>');
									}else{
										html.push(' 已通过');
									}
								} else {
									 if (record.system_product_process_id == 'free') {
										html.push('待' + ((record.operator_name && record.operator_name.indexOf(',') > 0) ? record.operator_name.substring(0, record.operator_name.length - 1) : record.operator_name) + '处理');
									} else {
										if(record.is_skim_time){
											html.push(record.operator_name+' <span style="color:blue">[处理中]</span>'+record.node_name);
										}else{
											html.push(record.operator_name+'<span style="color: #409c58">[待处理]</span>'+record.node_name);
										}
									}
								}
								return html.join('');
							</s:table.field>
							<s:table.field name="task_time_start" datatype="datetime" label="到达时间" align="center" sort="true" width="120"></s:table.field>
							<s:table.field name="expire_mins" label="剩余时间" align="right" sort="true" datatype="script" width="80">
								var comtime = function(mins) {
									if (mins / (24 * 60) >= 1) {
										return Math.floor(10 * mins / (24 * 60)) / 10 + "天";
									} else if (mins / 60 >= 1) {
										return Math.floor(10 * mins / 60) / 10 + "小时";
									} else {
										return mins + "分钟";
									}
								}
								var html = [];
								if (record.expire_mins == 99999999 || record.order_end_time) {
									html.push('<font class="font-grey">无</font>');
								} else if (record.expire_mins < 0) {
									html.push('<font class="font-red-flamingo">已过期' + comtime(-record.expire_mins) + '</font>');
								} else if (record.expire_mins > 0 && record.expire_mins <= 24 * 60) {
									html.push('<font class="font-yellow-gold">' + comtime(record.expire_mins) + '</font>');
								} else if (record.expire_mins > 0 && record.expire_mins <= 48 * 60) {
									html.push('<font class="font-yellow">' + comtime(record.expire_mins) + '后到期</font>');
								}
								return html.join("");
							</s:table.field>
							<%-- <s:table.field name="oper" hidden="true" datatype="script" label="操作"
								align="center" width="140">
								var html = [];
								html.push('<a style="margin: 0px 5px;"  class="todeal" href="javascript:void(0)" ');
								html.push('onclick="todeal(\'' + record.info_bill + '\', \'' + record.order_no + '\', ');
								html.push('\'' + record.task_id + '\',\'' + record.order_id + '\',\'' + record.node_name + '\')">');
								if (record.is_cc == 1) {
									html.push('抄送：');
								}
								html.push(record.subject);
								html.push('</a>');
								html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" ');
								html.push('onclick="todeal(\'' + record.node_bill + '\', \'' + record.order_no + '\', ');
								html.push('\'' + record.task_id + '\',\'' + record.node_name + '\')">催办</a>');
								html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" ');
								html.push('onclick="todeal(\'' + record.node_bill + '\', \'' + record.order_no + '\', ');
								html.push('\'' + record.task_id + '\',\'' + record.node_name + '\')">补充</a>');
								html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" ');
								html.push('onclick="todeal(\'' + record.node_bill + '\', \'' + record.order_no + '\', ');
								html.push('\'' + record.task_id + '\',\'' + record.node_name + '\')">撤销</a>');
								return html.join("");
							</s:table.field> --%>
						</s:table.fields>
					</s:table>
				</s:row>
			</s:form>
			<s:form label="我经办的" icon="fa fa-check-square-o" onclick="query3();">
				<s:row>
					<s:form id="conditions3" fclass="portlet light bordered">
						<s:row>
							<s:select label="所属产品" name="system_product_code" action="application.common.listProducts" cols="1" value="" onchange="changeProduct()">
								<s:option value="" label="全部"></s:option>
								<s:option value="$[code]" label="$[name]"></s:option>
							</s:select>
							<s:autocomplete label="流程" id="process_id" name="process_id" action="application.task.listProcess" cols="1" limit="10">
								<s:option value="$[id]" label="$[name]($[count])">$[name]($[count])</s:option>
							</s:autocomplete>
							<s:autocomplete label="发起人" name="creator" action="application.contacter.queryMine" params="{&#34;type&#34;:&#34;U&#34;}" cols="1" limit="10">
								<s:option value="$[id]" label="$[name]">$[name]</s:option>
							</s:autocomplete>
						</s:row>
						<s:row>
							<s:datefield label="开始日期" name="create_time_b" format="yyyy-MM-dd"></s:datefield>
							<s:datefield label="截至日期" name="create_time_e" format="yyyy-MM-dd"></s:datefield>
							<s:textfield label="单据号/主题" name="keyword" tips="匹配单据号"></s:textfield>
							<s:button label="查询" icon="fa fa-search" color="green" onclick="exe_query3()"></s:button>
						</s:row>
					</s:form>
				</s:row>
				<s:row>
					<s:table id="optionlist3" action="application.task.queryHistory" autoload="true" fitwidth="true" custom="true" sort="true" label="任务列表">
						<s:toolbar>
							<s:button label="筛选" icon="fa fa-filter" onclick="show_cx();"></s:button>
							<s:button label="取消" icon="icon-close" onclick="close_cx();" style="display:none;"></s:button>
						</s:toolbar>
						<s:table.fields>
							<s:table.field name="table_field" datatype="template">
								<div class="massage-content">
				                    <div class="trdiv">
					                    <b class="zi">$[state]</b>
					                    <span class="zhuangtai">$[system_product_name]</span>
					                    <span class="zhubiaoti">$[node_name]</span>
					                    <span class="fubiaoti">$[order_no]$[operator_name]</span>
					                    <span class="neirong">经办时间$[finish_time]<br />$[subject]</span>
					                    <div class="btn-div" style="display:none;">
						                    <a class="btn-divBtn" href="javascript:void(0);" 
						                    node_bill="$[node_bill]"
						                    info_bill="$[info_bill]"
						                    order_no="$[order_no]"
						                    task_id="$[task_id]"
						                    order_id="$[order_id]"
						                    node_name="$[node_name]"
						                    onclick="todeal('$[info_bill]','$[order_no]','$[task_id]','$[order_id]','$[node_name]')">处理</a>
					                    </div>
				                    </div>
				                </div>
			                </s:table.field>
							<s:table.field name="system_product_name" label="所属产品"  sort="true" width="80"></s:table.field>
							<s:table.field name="order_no" label="单据号"  sort="true" width="120"></s:table.field>
							<s:table.field name="subject" label="主题"  datatype="script" sort="true">
								var html = [];
								html.push(record.subject);
								return html.join("");
							</s:table.field>
							<s:table.field name="node_name" label="处理环节" width="100" datatype="script">
								var html=[];
								if (record.system_product_process_id == 'free') {
									html.push('处理');
								} else {
									html.push(record.node_name);
								}
								return html.join("");
							</s:table.field>
							<s:table.field name="audited" label="处理意见" align="center" sort="true" width="60" datatype="script">
								var html=[];
								if (record.audited == "Y") {
									html.push('				<font class="font-green-haze">通过</font>');
								} else if (record.audited == "N") {
									html.push('				<font class="font-red-intense">驳回</font>');
								} else if (record.audited == "R") {
									html.push('				<font class="font-yellow-casablanca">重审</font>');
								} else if (record.audited == "B") {
									html.push('				<font class="font-grey-cascade">撤回</font>');
								} else if (record.audited == "F") {
									html.push('				<font class="font-blue">结束</font>');
								}
								return html.join("");
							</s:table.field>
							<s:table.field name="finish_time" label="处理时间" align="center" sort="true" width="120"></s:table.field>
							<s:table.field name="state" label="当前状态" width="80" datatype="script">
								var html=[];
								if (record.order_end_time) {
									if(record.last_audited == 'N'){
										html.push("已驳回");
									}else{
										html.push("已通过");
									}
								} else if (record.system_product_process_id == 'free') {
									html.push('待<font class="font-yellow-casablanca">' + ((record.operator_name && record.operator_name.indexOf(',') > 0) ? record.operator_name.substring(0, record.operator_name.length - 1) : record.operator_name) + '</font>处理');
								} else {
									if(record.is_skim_time){
										html.push("处理中");
									}else{
										html.push("待处理");
									}
								}
								return html.join("");
							</s:table.field>
							<s:table.field name="operator_name" label="当前处理人" width="80" datatype="script">
								var html=[];
								if (record.order_end_time) {
									if(record.last_audited == 'N'){
										html.push('<span style="color:red;">[已驳回]</span>');
									}else{
										html.push('[已通过]');
									}
								} else {
									if(record.is_skim_time){
										html.push(record.operator_name+' <span style="color:blue">[处理中]</font>'+record.node_name);
									}else{
										html.push(record.operator_name+'<font style="color:#409c58">[待处理]</font>'+record.node_name);
									}
								}
								return html.join('');
							</s:table.field>
							<%-- <s:table.field name="oper" hidden="true" datatype="script" label="操作"
								align="center" width="140">
								var html = [];
								html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" class="todeal" ');
								html.push('onclick="todeal(\'' + record.info_bill + '\', \'' + record.order_no + '\', ');
								html.push('\'' + record.task_id + '\',\'' + record.order_id + '\',\'' + record.node_name + '\')">');
								html.push(record.subject);
								html.push('</a>');
								html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" ');
								html.push('onclick="todeal(\'' + record.node_bill + '\', \'' + record.order_no + '\', ');
								html.push('\'' + record.task_id + '\',\'' + record.node_name + '\')">补充</a>');
								html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" ');
								html.push('onclick="todeal(\'' + record.node_bill + '\', \'' + record.order_no + '\', ');
								html.push('\'' + record.task_id + '\',\'' + record.node_name + '\')">重审</a>');
								return html.join("");
							</s:table.field> --%>
						</s:table.fields>
					</s:table>
				</s:row>
			</s:form>
		</s:tabpanel>
	</s:row>
</s:page>
<script type="text/javascript">

$(document).ready(function(){
		query();
		$("#optionlist").find("tbody").on("click", "tr", function() {
			$("#optionlist").find("tbody").find("tr").find(".massage-content").css("background-color", "");
			$(this).find(".massage-content").css("background-color", "#f8f7f7");
			var b = $(this).find("a[class='btn-divBtn']");
			todeal(b.attr("node_bill"),b.attr("order_no"),b.attr("task_id"),b.attr("order_id"),b.attr("node_name"));
		});
		$("#optionlist2").find("tbody").on("click", "tr", function() {
			$("#optionlist").find("tbody").find("tr").find(".massage-content").css("background-color", "");
			$(this).find(".massage-content").css("background-color", "#f8f7f7");
			var b = $(this).find("a[class='btn-divBtn']");
			todeal(b.attr("info_bill"),b.attr("order_no"),b.attr("task_id"),b.attr("order_id"),b.attr("node_name"));
		});
		$("#optionlist3").find("tbody").on("click", "tr", function() {
			$("#optionlist").find("tbody").find("tr").find(".massage-content").css("background-color", "");
			$(this).find(".massage-content").css("background-color", "#f8f7f7");
			var b = $(this).find("a[class='btn-divBtn']");
			todeal(b.attr("info_bill"),b.attr("order_no"),b.attr("task_id"),b.attr("order_id"),b.attr("node_name"));
		});
	});
	function changeProduct() {
		var data = $("#conditions").getData();
		$("#process_id").params({system_product_code: data["system_product_code"]});
	}
	function query() {
		$("#optionlist").params($("#conditions").getData());
		$("#optionlist").refresh();
	}
	function query2() {
		$("#optionlist2").params($("#conditions2").getData());
		$("#optionlist2").refresh();
	}
	function query3() {
		$("#optionlist3").params($("#conditions3").getData());
		$("#optionlist3").refresh();
	}
	function todeal(pageid,order_no,task_id,order_id,node_name){
		var u = pageid.split(".").join("/");
		var url = cooperopcontextpath + "/w/" + u + ".html";
		if(pageid.indexOf("bmc")>=0){
			url = cooperopcontextpath +"/w/"+ pageid;
		}
// 		alert(url+"|"+order_no+"|"+task_id+"|"+order_id+"|"+node_name);
		$.modal(url,node_name,{
			width :'80%',
			height :'99%',
			djbh: order_no,
			order_id: order_id,
			task_id: task_id,
			callback : function(rtn){
				if(rtn){
					start = $("#optionlist").dataTable().fnSettings()._iDisplayStart; 
					total = $("#optionlist").dataTable().fnSettings().fnRecordsDisplay();
					var p = $("#optionlist").DataTable().page();
					if((total-start)==1){
						if (start > 0) {
							$("#optionlist").refresh_table(p-1);
						}else{
							$("#optionlist").refresh();
						}
					}else{
						$("#optionlist").refresh_table(p);
					}
				}
			}
		})
	}
	function createFreeTask() {
		var url = cooperopcontextpath + "/w/application/task/freetask.html";
		$.modal(url, "新建个人配合单", {
			callback : function(rtn){
				$("#optionlist2").refresh();
			}
		});
	}
	function showdetail(pageid,order_no,task_id,node_name){
		var u = pageid.split(".").join("/");
		var url = cooperopcontextpath + "/w/" + u + ".html";
		$.modal(url,node_name,{
			djbh: order_no,
			task_id: task_id,
			callback : function(rtn){
				$("#optionlist").refresh_talbe($("#optionlist").DataTable().page());
			}
		})
	}
	function show_cx(){
		$(".tab-pane>.form-horizontal>.row-fluid:first-child").slideDown(600);
		$(".fa-filter").parent().hide();
		$(".icon-close").parent().show();
	}
	function close_cx(){
		$(".tab-pane>.form-horizontal>.row-fluid:first-child").slideUp(600);
		$(".fa-filter").parent().show();
		$(".icon-close").parent().hide();
	}
	function exe_query(){
		$(".tab-pane>.form-horizontal>.row-fluid:first-child").slideUp(600);
		$(".fa-filter").parent().show();
		$(".icon-close").parent().hide();
		query();
	}
	function exe_query2(){
		$(".tab-pane>.form-horizontal>.row-fluid:first-child").slideUp(600);
		$(".fa-filter").parent().show();
		$(".icon-close").parent().hide();
		query2();
	}
	function exe_query3(){
		$(".tab-pane>.form-horizontal>.row-fluid:first-child").slideUp(600);
		$(".fa-filter").parent().show();
		$(".icon-close").parent().hide();
		query3();
	}
</script>