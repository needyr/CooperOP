<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑药师信息" ismodal="true">
	<s:row>
		<s:form id="form" label="药师信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
				<s:row>
					<s:image cols="3" label="照片" name="avatar" value="${avatar}" maxlength="1"></s:image>
				</s:row> 
				<input type="hidden" name="position" value="药师"/>
				<input type="hidden" name="sid" value="${sid }"/>
				<s:row>
					<s:textfield label="姓名" name="name" required="true" cols="1" value="${name }" />
					<s:textfield label="职工号" name="no" required="true" cols="1" value="${no }" />
					<%-- <s:textfield label="密码" name="password" datatype="password" placeholder="如不输入密码，默认：000000" value="${password }"></s:textfield> --%>
				</s:row>
				<s:row>
					<input type="hidden" name="doctor_no" value="${doctor_no}">
					<s:textfield label="座机" name="telephone" cols="1" value="${telephone }" />
					<s:textfield label="手机" name="mobile" required="true" cols="1" value="${mobile }" />
					<s:datefield label="生日" name="birthday" format="yyyy-MM-dd"  value="${birthday}" ></s:datefield>
				</s:row>
				<s:row>
					<s:textfield label="微信" name="weixin" cols="1" value="${weixin }" />
					<s:textfield label="QQ" name="qq" cols="1" value="${qq }" />
					<s:textfield label="邮箱" name="email" cols="1" value="${email }" />
				</s:row>
				<s:row>
					<s:radio label="性别" name="gender" value="${gender}" cols="1">
						<s:option value="1" label="男"></s:option>
						<s:option value="0" label="女"></s:option>
						<s:option value="2" label="待定"></s:option>
					</s:radio>
					<s:select label="证件类型" name="idcard_type" value="${idcard_type }" required="true" islabel="true"  readonly="readonly">
						<s:option value="1" label="身份证"></s:option>
						<s:option value="2" label="护照"></s:option>
					</s:select>
					<s:textfield label="证件编号" name="idcard_no" cols="1" value="${idcard_no }"  />
				</s:row>
				<s:row>
					<s:autocomplete label="部门" name="department" action="setting.dep.querydep"  value="${department}" text="${dept_name}" limit="10" required="true">
						<s:option value="$[id]" label="$[name]">$[name]</s:option>
					</s:autocomplete>
					<s:checkbox label="审查类型" name="order_type" value="${order_type}">
						<s:option value="1" label="住院"></s:option>
						<s:option value="2" label="门诊"></s:option>
						<s:option value="3" label="急诊"></s:option>
					</s:checkbox>
					<s:switch label="是否启用"  name="state" onvalue="1" offvalue="0" ontext="是" offtext="否" value="${state }"/>
				</s:row>
				<s:row>
					<s:richeditor label="介绍" name="description" cols="3" placeholder="" required="false" height="100" >${description }</s:richeditor>
				</s:row>				
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		if('${param.id}'!=null&&typeof('${param.id}')!='undefind'&&'${param.id}'!=''){
			$("input[name='no']").attr("disabled","disabled");
		}
	})

	function save(){
		if (!$("form").valid()){
    		return false;
    	}
		var sdata=$("#form").getData();
		sdata.type='2';
		sdata.name=$("input[name='name']").val();
		sdata.user_state = 1;
		sdata.position = 'hospital';
		//console.log(sdata);
		if('${param.id}'!=null&&typeof('${param.id}')!='undefind'&&'${param.id}'!=''){
			sdata.id='${param.id}';
		}
		if('${param.sid}'!=null&&typeof('${param.sid}')!='undefind'&&'${param.sid}'!=''){
			sdata.sid='${param.sid}';
			$.call("ipc.pharmacist.save",sdata,function(s){
	    		$.closeModal(s);
	    	});
		}else{
			$.call("ipc.pharmacist.queryHas",{"no":sdata.no},function(rtn){
				if(rtn.resultset.length<1){
	    			$.call("ipc.pharmacist.save",sdata,function(s){
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