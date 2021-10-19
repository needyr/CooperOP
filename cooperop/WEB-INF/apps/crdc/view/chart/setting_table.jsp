<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.application.dao.BillDao"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page ismodal="true" title="">
<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/plugins/icheck/icheck.min.js"></script>
<script type="text/javascript"
		src="${pageContext.request.contextPath}/theme/pages/scripts/form-icheck.js"></script>
<%
	String str = request.getParameter("attdata");
	Map<String, Object> map = CommonFun.json2Object(str, Map.class);
	Map<String, Object> attrs = (Map<String, Object>) map.get("attrs");
	List<Map<String, Object>> contents = (List<Map<String, Object>>) map.get("contents");
	pageContext.setAttribute("mxpre", BillDao.TABLE_NAME_TEMP_MX);
	pageContext.setAttribute("table", attrs);
	pageContext.setAttribute("contents", (List<Map<String, Object>>)contents.get(0).get("contents"));
%>
<s:row>
	<input type="hidden" id="system_product_code" value="${table.system_product_code}"/>
	<input type="hidden" id="schemeid" value="${table.schemeid}"/>
	<input type="hidden" id="flag" value="${table.flag}"/>
	<s:form label="查询页面属性" icon="fa fa-gift" id="setFrom">
		<s:toolbar>
			<s:button onclick="save();return false;" color="green" label="保存"></s:button>
			<s:button color="red" onclick="returnback();return false;" label="取消"></s:button>
		</s:toolbar>
		<s:row>
			<input type="hidden" name="autoload" value="false"/>
			<s:textfield label="查询id" name="tableid" value="${table.tableid }" required="true"></s:textfield>
			<s:textfield label="中文说明" name="label" value="${table.label }"></s:textfield>
			<s:textfield label="图标" name="icon" value="${table.icon }"></s:textfield>
			<s:button label="查看图标库" onclick="showicon();" color="green" icon="fa fa-eye"></s:button>
		</s:row>
		<s:row>
			<s:autocomplete label="下钻" name="djselect" action="crdc.designer.query" value="${table.djselect}" editable="true">
				<s:option value="$[system_product_code].$[type].$[flag].$[id]" label="$[system_product_code].$[type].$[flag].$[id]">$[system_product_code].$[type].$[flag].$[id]($[description])</s:option>
			</s:autocomplete>
			<s:textfield label="下钻页面名称" name="subpage_name" value="${table.subpage_name }" placeholder="下钻页面{:name}"></s:textfield>
		</s:row>
		<s:row>
			<s:switch label="自动加载" name="autoload" onvalue="true" offvalue="false" value="${table.autoload }"></s:switch>
			<div class="cols">
				<label class="control-label">颜色</label>
				<div class="control-content">
					<ul class="icheck-colors">
						<li class="${(empty table.color or table.color eq 'grey-silver' or table.color eq 'default')?'active':'' }"></li>
						<li class="red ${table.color eq 'red'?'active':'' }"></li>
						<li class="green ${table.color eq 'green'?'active':'' }"></li>
						<li class="blue ${table.color eq 'blue'?'active':'' }"></li>
						<li class="yellow ${table.color eq 'yellow'?'active':'' }"></li>
						<li class="purple ${table.color eq 'purple'?'active':'' }"></li>
					</ul>
				</div>
				<input type="hidden" name="color" id="color_"
					class="form-control" value="${table.color }" />
			</div>
			<s:select label="每页显示行数" name="limit" value="${empty table.limit?'25':table.limit }">
				<s:option label="10" value="10"></s:option>
				<s:option label="25" value="25"></s:option>
				<s:option label="50" value="50"></s:option>
				<s:option label="75" value="75"></s:option>
				<s:option label="100" value="100"></s:option>
				<s:option label="200" value="200"></s:option>
				<s:option label="500" value="500"></s:option>
			</s:select>
		</s:row>
		<s:row>
			<s:textarea label="查询sql" name="initsql" cols="3">${table.initsql }</s:textarea>
			<s:textarea label="计数sql" name="countsql" cols="3">${table.countsql }</s:textarea>
			<s:textfield label="totals" name="totals" value="${table.totals }" title="格式：{info: '患者费用合计:p1元;:p2',params: 'sum(costs),p1,sum(COSTS_Except),p2'}。有计数sql是具体数据在其中进行查询"></s:textfield>
			<%-- <c:if test="${table.canSort eq 'Y'}">
	 			<s:textfield label="显示顺序" name="toIndex" value="${table.myIndex }" max="${table.myMaxIndex }" min="0"></s:textfield>
	 			<input type="hidden" name="myIndex" value="${table.myIndex }"/>
	 		</c:if>	 --%>
			<s:switch label="是否开启排序" name="sort" value="${table.sort }" onvalue="true" offvalue="false"></s:switch>
			<s:switch label="是否自适应" name="fitwidth" onvalue="true" offvalue="false" value="${table.fitwidth }"></s:switch>
			<s:switch label="app表格展示" name="app_table" value="${table.app_table }" onvalue="Y" offvalue="N"></s:switch>
		</s:row>
	</s:form>
</s:row>
<div class="row">
	<s:table label="字段设置" select="multi" id="table_" fitwidth="true" limit="50">
		<s:toolbar>
			<s:button label="新增" action="addrow"></s:button>
			<s:button label="删除" onclick="deletec()"></s:button>
		</s:toolbar>
		<s:table.fields>
			<s:table.field name="name" label="字段名称" width="120" datatype="template">
				<s:textfield dblaction="fieldsmodify,query" name="name" value="$[name]"/>
			</s:table.field>
			<s:table.field name="fdtype" label="字段类型" datatype="template">
				<s:textfield name="fdtype" value="$[fdtype]"></s:textfield>
			</s:table.field>
			<s:table.field name="format" label="显示格式" datatype="template">
				<s:textfield name="format" type="text" value="$[format]"/>
			</s:table.field>
			<s:table.field name="dictionary" label="表达式" width="120" datatype="template">
				<s:textfield name="dictionary" type="text" value="$[dictionary]"/>
			</s:table.field>
			<s:table.field name="label" label="显示标题" width="160" datatype="template">
				<s:textfield name="label" type="text" value="$[label]"/>
			</s:table.field>
			<s:table.field name="size" label="长度" datatype="template">
				<s:textfield name="size" type="text" value="$[size]" readonly="true"/>
			</s:table.field>
			<s:table.field name="maxlength_show" label="显示长度" datatype="template">
				<s:textfield name="maxlength_show" type="text" value="$[maxlength_show]"/>
			</s:table.field>
			<s:table.field name="digitsize" label="小数位" datatype="template">
				<s:textfield name="digitsize" type="text" value="$[digitsize]" readonly="true"/>
			</s:table.field>
			<s:table.field name="digits" label="显示小数位" datatype="template">
				<s:textfield name="precision" type="text" value="$[precision]"/>
			</s:table.field>
			<%-- <s:table.field name="editable" label="可编辑" width="60" datatype="template">
				<s:select name="editable" type="text" value="$[editable]">
					<s:option label="是" value="true"></s:option>
					<s:option label="否" value="false"></s:option>
				</s:select>
			</s:table.field> --%>
			<s:table.field name="available" label="活动显示" datatype="template">
				<s:select name="available" type="text" value="$[available]">
					<s:option label="是" value="true"></s:option>
					<s:option label="否" value="false"></s:option>
				</s:select>
			</s:table.field>
			<s:table.field name="sort" label="手动排序" datatype="template">
				<s:select name="sort" type="text" value="$[sort]">
					<s:option label="是" value="true"></s:option>
					<s:option label="否" value="false"></s:option>
				</s:select>
			</s:table.field>
		<%-- 	<s:table.field name="checkable" label="启动校验" datatype="template">
				<s:select name="checkable" type="text" value="$[checkable]">
					<s:option label="是" value="true"></s:option>
					<s:option label="否" value="false"></s:option>
				</s:select>
			</s:table.field>
			<s:table.field name="callduplicate" label="查重字段" datatype="template">
				<s:select name="callduplicate" type="text" value="$[callduplicate]">
					<s:option label="是" value="true"></s:option>
					<s:option label="否" value="false"></s:option>
				</s:select>
			</s:table.field> --%>
			<s:table.field name="controltype" label="字段展示控件" width="60" datatype="template">
				<s:select label="字段展示控件" name="controltype" value="$[controltype]">
					<s:option label="文本框" value="textfield"></s:option>
					<s:option label="下拉框" value="select"></s:option>
					<s:option label="日期" value="datefield"></s:option>
					<s:option label="时间" value="timefield"></s:option>
					<s:option label="文件" value="file"></s:option>
				</s:select>
			</s:table.field>
			<s:table.field name="align" label="排列" width="60" datatype="template">
				<s:select name="align" type="text" value="$[align]">
					<s:option label="居左" value="left"></s:option>
					<s:option label="居中" value="center"></s:option>
					<s:option label="居右" value="right"></s:option>
				</s:select>
			</s:table.field>
			<s:table.field name="width" label="列宽" datatype="template" width="40" >
				<s:textfield name="width" type="text" value="$[width]"/>
			</s:table.field>
			<%-- <s:table.field name="nextfocus" label="焦点去向" datatype="template">
				<s:textfield name="nextfocus" type="text" value="$[nextfocus]"/>
			</s:table.field> --%>
			<s:table.field name="fieldorder" label="列显示顺序" datatype="template">
				<s:textfield name="fieldorder" type="text" value="$[fieldorder]"/>
			</s:table.field>
			<%-- <s:table.field name="enter_action" label="进入执行函数" datatype="template">
				<s:textfield name="enter_action" type="text" value="$[enter_action]"/>
			</s:table.field>
			<s:table.field name="dbl_action" label="双击执行函数" datatype="template">
				<s:textfield name="dbl_action" type="text" value="$[dbl_action]"/>
			</s:table.field>
			<s:table.field name="modify_action" label="修改执行函数" datatype="template">
				<s:textfield name="modify_action" type="text" value="$[modify_action]"/>
			</s:table.field> --%>
			<s:table.field name="is_href" label="链接地址" datatype="template" width="60" >
				<s:textfield name="is_href" type="text" value="$[is_href]"/>
			</s:table.field>
			<s:table.field name="app_field" label="手机字段" width="60" datatype="template">
				<s:select name="app_field" type="text" value="$[app_field]">
					<s:option label="图片展示" value="image_field"></s:option>
					<s:option label="标题展示" value="title_field"></s:option>
					<s:option label="副标题展示" value="pre_title_field"></s:option>
					<s:option label="内容展示" value="content_field"></s:option>
					<s:option label="状态展示" value="state_field"></s:option>
				</s:select>
			</s:table.field>
		</s:table.fields>
		<s:table.data>
			<c:forEach items="${contents }" var="f">
				<tr id="childs">
					<td>${f.attrs.name }</td>
					<td>${f.attrs.fdtype }</td>
					<td>${f.attrs.format }</td>
					<td>${f.attrs.dictionary }</td>
					<td>${f.attrs.label }</td>
					<td>${f.attrs.size }</td>
					<td>${f.attrs.maxlength_show }</td>
					<td>${f.attrs.digitsize }</td>
					<td>${f.attrs.precision }</td>
					<%-- <td>${empty f.attrs.editable?'true':f.attrs.editable }</td> --%>
					<td>${empty f.attrs.available?'true':f.attrs.available }</td>
					<td>${empty f.attrs.sort?'true':f.attrs.sort }</td>
					<%-- <td>${empty f.attrs.checkable?'false':f.attrs.checkable }</td>
					<td>${empty f.attrs.callduplicate?'false':f.attrs.callduplicate }</td> --%>
					<td>${(empty f.attrs.controltype)?'':(f.attrs.controltype) }</td>
					<td>${(empty f.attrs.align)?'left':f.attrs.align }</td>
					<td>${(empty f.attrs.width)?'120':f.attrs.width }</td>
					<%-- <td>${f.attrs.nextfocus }</td> --%>
					<td>${f.attrs.fieldorder }</td>
					<%-- <td>${f.attrs.enter_action }</td>
					<td>${f.attrs.dbl_action }</td>
					<td>${f.attrs.modify_action }</td> --%>
					<td>${f.attrs.is_href }</td>
					<td>${f.attrs.app_field }</td>
				</tr>
			</c:forEach>
		</s:table.data>
	</s:table>
</div>
</s:page>
<script type="text/javascript">
	$(document).ready(function(){
		var system_product_code = $("#system_product_code").val();
		$("[name='djselect']").params({"system_product_code":system_product_code,type: 'chart'});
	})
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var d = $("#setFrom").getData();
		d.mxkey = d.name;
		delete d.name;
		var data = {};
		data.attrs = d;
		data.children = [];
		var trdata = $("#table_ > tbody > tr");
		//data.attrs.yaxis = [];
		trdata.each(function(){
			var tdd = $(this).getData();
			//delete tdd.controltype;
			data.children.push(tdd);
			/* if(tdd.chart_field_type == 'G'){
				data.attrs.g_name = tdd.name;
			}
			if(tdd.chart_field_type == 'S'){
				data.attrs.yaxis.push({"y_name": tdd.name,"y_name_ch": tdd.label, "chart_type": tdd.chart_type});
			} */
		  });
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
 	function addc() {
 		var tr = $("#table_ > tbody > tr");
 		var trnew = $(tr[0]).clone();
 		trnew.find("input").val("");
 		$("#table_ > tbody").append(trnew);
	} 
	function deletec() {
		$("#table_ > tbody").find("input[type=checkbox]").each(function(){
			if(this.checked){
				$(this).parents("tr").remove();
			}
		});
	}
$(document).ready(function() {
	$(".icheck-colors").click(function(e) {
		var c = $(this).find(".active").attr("class");
		var color = c.split(" ")[0];
		if (color == "active") {
			color = "default";
		}
		$("#color_").val(color);
	});
});
function initfield(_this){
	var d = $(_this).val();
	var tr = $(_this).parents("tr");
	$.call("crdc.scheme.fields.list",{"name":d},function(data){
		var d = data.resultset[0];
		if(d){
			tr.find("input[name='fdtype']").val(d.fdtype);
			tr.find("input[name='maxlength']").val(d.fdsize);
			tr.find("input[name='size']").val(d.fdsize);
			tr.find("input[name='digitsize']").val(d.fddec);
			tr.find("input[name='precision']").val(d.fddec);
			tr.find("input[name='placeholder']").val(d.beizhu);
			tr.find("input[name='label']").val(d.chnname);
			if(d.nouse){
				if(d.nouse<1){
					d.nouse = 1;
				}else if(d.nouse>4){
					d.nouse = 4;
				}
				$("#setFrom").find("select[name='cols']").val(d.nouse);
			}
		}
	});
}
function showicon(){
	window.open(cooperopcontextpath + "/w/crdc/ui_icons.html?ismodal=true");
}
function createtablesql(){
	var d = $("#setFrom").getData();
	d.system_product_code = $("#system_product_code").val();
	d.schemeid = $("#schemeid").val();
	d.flag = $("#flag").val();
	if(!schemeid){
		$.message("单据还未保存");
		return;
	}
	d.tr = [];
	var trdata = $("#table_ > tbody > tr");
	var ccontent = [];
	ccontent.push("create table ${mxpre}"+d.flag+d.schemeid+d.tableid+" (");
	ccontent.push("gzid varchar(11),");
	ccontent.push("dj_sn integer,");
	ccontent.push("dj_sort integer null default 0,");
	var b = false;
	trdata.each(function(index){
		var tdd = $(this).getData();
		if(!tdd.fdtype){
			b = true;
			return;
		}
		if(!tdd.name){
			return;
		}
		if(tdd.fdtype=="字符"){
			tdd.type = " varchar("+tdd.size+") null default '' ";
		}else if(tdd.fdtype=="整数"){
			tdd.type = " integer null default 0 ";
		}else if(tdd.fdtype=="实数"){
			tdd.type = " decimal("+tdd.size+","+tdd.digitsize+") null default 0";
		}else if(tdd.fdtype=="位图" || tdd.fdtype=="二进制"){
			tdd.type = " image null";
		}else if(tdd.fdtype=="文本"){
			tdd.type = " text null";
		}else{
			tdd.type = " varchar("+tdd.size+")";
		}
		if(index>0){
			ccontent.push(",");
		}
		ccontent.push(tdd.name +tdd.type);
		d.tr.push(tdd);
	  });
	if(b){
		$.message("‘字段类型’不能为空");
		return;
	}
	ccontent.push(" CONSTRAINT ${mxpre}"+d.flag+d.schemeid+d.tableid+" PRIMARY KEY NONCLUSTERED (gzid,dj_sn))");
	var createsql = ccontent.join("");
	//$.message(createsql);
	d.createsql = createsql;
	$.call("crdc.designer.querymxtable", {jdata: $.toJSON(d)}, function(r) {
		if(r.c>0){
			$.confirm("该工作表已经存在，覆盖工作表？",function(rtn){
				if(rtn){
					d.hastable = true;
					$.call("crdc.designer.createmxtable", {jdata: $.toJSON(d)}, function(data) {
						$.message("ok!");
					});
				}
			})
		}else{
			$.call("crdc.designer.createmxtable", {jdata: $.toJSON(d)}, function(data) {
				$.message("ok!");
			});
		}
	});
}
jQuery(document).ready(function() {    
	FormiCheck.init(); // init page demo
    });
</script>
