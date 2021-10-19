<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="资料检索-科室" disloggedin="true">
<style type="text/css">
	.left{width:79%;float: left;}
	.right{width:20%;float: left;}
	#ul-choose{
	    padding: 0px;
	}
	.li-title{
		list-style: none;
	    line-height: 24px;
	    padding-left: 3px;
	    border: 1px solid #56a9a8;
	        margin-top: 1px;
	}
	.li-item{
		list-style: none;
	    line-height: 24px;
	    padding-left: 3px;
	    border: 1px solid #56a9a8;
	        margin-top: 1px;
	}
</style>
	<s:row>
		<s:form id="form1">
			<s:row>
				<s:textfield name="f_key" label="关键字" placeholder="请输入科室编码，输入码或名称" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query();" ></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<div class="left" >
			<!-- select="multi" -->
			<s:table label="科室列表" select="multi" action="hospital_common.abase.queryDept" autoload="false" id="table-dept" limit="0">
				<s:table.fields>
					<s:table.field name="dept_code" label="科室编码"></s:table.field>
					<s:table.field name="jianchen" label="科室简称"></s:table.field>
					<s:table.field name="dept_name" label="科室名称"></s:table.field>
					<s:table.field name="outp_or_inp" label="科室属性"></s:table.field>
					<s:table.field name="input_code" label="输入码"></s:table.field>
				</s:table.fields>
			</s:table>
		</div>
		<div class="right">
			<s:form label="已选择(双击取消)">
				<s:toolbar>
					<s:button label="保存" icon="fa fa-save" onclick="save();" style="background: #bbdcd5;"></s:button>
				</s:toolbar>
				<s:row>
					<ul id="ul-choose">
						<!-- <li class="li-title">值</li>
						<li class="li-item">值</li>
						<li class="li-item">值</li> -->
					</ul>
				</s:row>
			</s:form>
		</div>
	</s:row>
</s:page>
<script>
	var sel_map = new Map();
	/* sel_map.add();
	 Array.from(sel_map); */
	$(function(){
		//query();
		joinOld();
	});
	 
	 function trclick(){
		 $('#table-dept tbody tr').click(function(){
				var data = $('#table-dept').getSelected();
				var old_sel = $(this).hasClass('selected');
				var sel_data = {};
				var sel_td = $(this).find('td');
				sel_data.dept_code = $(sel_td[1]).text();
				sel_data.dept_name = $(sel_td[2]).text();
				if(old_sel){
					sel_map.delete(sel_data.dept_code);
				}else{
					sel_map.set(sel_data.dept_code ,sel_data);
				}
				//console.log(sel_map);
				// 遍历map
				var dept = "";
				var li_html = [];
				for (let v of sel_map.values()) {
					li_html.push('<li class="li-item" ondblclick="removeMe(this)">');
					li_html.push(v.dept_code + ',' +v.dept_name);
					li_html.push('</li>');
				}
				$('#ul-choose').empty();
				$('#ul-choose').append(li_html.join(''));
				
		});
	 }
	
	function query(){
		var fdata = $('#form1').getData();
		if(fdata.f_key == ""){
			$.message('建议添加关键字搜索，可有效提高响应速度');
		}
		$('#table-dept').params(fdata);
		$('#table-dept').refresh();
		setTimeout("trclick()", 300);
		$('#table-dept tbody tr').each(function(){
			if($(this).hasClass('selected')){
				$(this).removeClass('selected');
			}
		});
	}
	
	function removeMe(_this){
		sel_map.delete(($(_this).text().split(','))[0]);
		$(_this).remove();
	}
	
	function save(){
		$.closeModal(sel_map);
	}
	
	function joinOld(){
		var dept_codes = '${param.dept_codes}'.split(',');
		var dept_names = '${param.dept_names}'.split(',');
		if(dept_codes.length == 1 && dept_codes[0] == ''){
			// 没有old科室
			return;
		}
		var li_html = [];
		for(var i= 0; i< dept_codes.length; i++){
			li_html.push('<li class="li-item" ondblclick="removeMe(this)">');
			li_html.push(dept_codes[i] + ',' +dept_names[i]);
			li_html.push('</li>');
			var data = {
				dept_code: dept_codes[i],
				dept_name: dept_names[i]
			};
			sel_map.set(dept_codes[i] , data);
		}
		$('#ul-choose').empty();
		$('#ul-choose').append(li_html.join(''));
	}
</script>