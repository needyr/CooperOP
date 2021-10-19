<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑医生信息" ismodal="true">
	<s:row>
		<s:form id="form" label="医生信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:image cols="3" label="照片" name="avatar" value="${avatar}" maxlength="1"></s:image>
			</s:row> 
			<s:row>
				<s:autocomplete id="toinfo" editable="true" label="姓名" name="name" action="ipc.doctor.querydoctor" limit="10" cols="1" value="${name}" required="true" >
					<s:option value="$[user_id]" label="$[user_name]" >
						<span style="float:left;display:block;width:100px;">$[user_name]</span>
						<span style="float:left;display:block;">$[dept_name]</span>
					</s:option>
				</s:autocomplete>
				<input type="hidden" name="doctor_no" value="${doctor_no}">
				<s:textfield label="职工号" name="no" required="true" cols="1" value="${no}" />
				<%-- <s:textfield label="密码" name="password" datatype="password" placeholder="如不输入密码，默认：000000" value="${password }"></s:textfield> --%>
			</s:row>
			<s:row>
				<s:autocomplete label="部门" name="department" action="setting.dep.querydep"  value="${department}" text="${dept_name}" limit="10" required="true">
						<s:option value="$[id]" label="$[name]">$[name]</s:option>
				</s:autocomplete>
				<s:textfield label="电话" name="telephone" required="true" cols="1" value="${telephone}" />
				<s:datefield label="生日" name="birthday" format="yyyy-MM-dd"  placeholder="请选择日期" value="${birthday}"></s:datefield>
			</s:row>
			<s:row>
				<s:radio label="性别" name="gender" value="${gender}" cols="1">
						<s:option value="1" label="男"></s:option>
						<s:option value="0" label="女"></s:option>
						<s:option value="2" label="待定"></s:option>
				</s:radio>
				<s:switch label="是否启用"  name="state" onvalue="1" offvalue="0" ontext="是" offtext="否" value="${state }" />
			</s:row>
			<s:row>
				<s:textfield label="主治" name="attending" required="false" cols="3" value="${attending}" />
			</s:row>
			<s:row>
				<s:richeditor label="介绍" name="description" cols="4" placeholder="" required="false" height="200" value="">${description}</s:richeditor>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		if('${param.id}'){
			$("input[name='no']").attr("disabled","disabled");
		}
		
		$("#toinfo").on("change",function(){
			var no = $("#form").getData().name;
			$.call("ipc.doctor.getcdoctor",{"user_id":no},function(rtn){
				console.log(rtn);
				if(rtn){
					$("[name='no']").setData(rtn.db_user);
					$("[name='department']").setData(rtn.dept_name); 
					$("[name='doctor_no']").setData(rtn.user_id);
					console.log(rtn);
				}
			},function(e){},{async: false, remark: false });
		})
	})

	function save(){
		
		if (!$("form").valid()){
    		return false;
    	}
		
		var sdata=$("#form").getData();
		sdata.type='1';
		console.log(sdata);
		sdata.name=$("#toinfo").val();
		sdata.position = '医生';
		//console.log(sdata);
		if('${param.id}'!=null&&typeof('${param.id}')!='undefind'&&'${param.id}'!=''){
			sdata.id='${param.id}';
		}
		var sid = '${param.sid}';
		if(sid){
			sdata.sid=sid;
			$.call("ipc.doctor.save",sdata,function(s){
	    		$.closeModal(s);
	    	});
		}else{
			$.call("ipc.doctor.queryHas",{"no":sdata.no},function(rtn){
				if(rtn.resultset.length<1){
	    			$.call("ipc.doctor.save",sdata,function(s){
	    	    		$.closeModal(s);
	    	    	});
	    		}else{
	    			$.message("登录名已存在，请重新输入!");
	    			$("input[name='no']").val("");
	    		} 
			});
		}
    	
	}

	//取消
    function cancel(){
    	$.closeModal(false);
    }
</script>