<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="点评预判规则维护">
<s:row>
	<s:form id="Mform" label="信息">
		<s:toolbar>
			<s:button label="确认" onclick="save()" icon=""></s:button>
			<s:button label="取消" onclick="cancel()" icon=""></s:button>
		</s:toolbar>
		<s:row>
			<s:row>
			<c:if test="${not empty id}">
			<input type="hidden" name="id" value="${id}" >
			</c:if>
			<s:autocomplete label="选择问题类型" name="type_p_key" value="${type_p_key}" text="${thirdt_name}" action="hospital_common.commentmanage.opinion.queryAuditType" required="true" cols="3" limit="10">
					<s:option value="$[p_key]" label="$[thirdt_name]">
						$[thirdt_name]
					</s:option>
			</s:autocomplete>
			</s:row>
			
			<s:row>
			<input type="hidden" name="comment_content_code" value="${comment_content_code}" >
			<div class="cols2">
			<label class="control-label">点评内容</label>
			<div class="control-content">
			<textarea style="height: 100px;" readonly="readonly" class="form-control" placeholder="双击选择"  name="comment_content" cols="4"  data-autosize-on="true">${comment_content}</textarea>
			</div>
			</div>
			<a onclick="_clean()">清空</a>
			</s:row>
			
			<s:row>
			<s:switch label="合理性" value="${comment_result}" name="comment_result" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch>
			</s:row>
		</s:row>
	</s:form>
</s:row>
</s:page>
<script type="text/javascript">
$(document).ready(function() {
	
});

function save(){
	//空表单提交验证
	if (!$("form").valid()){
		return false;
	}
	var d = $("#Mform").getData();
	//新增
   $.call("hospital_common.commentmanage.opinion.save",d,function(s){
		$.closeModal(s);
	}); 
}

//取消
function cancel(){
	$.closeModal(false);
}

$('[name=comment_content]').dblclick(function(){
	var data2= {} ;
	//data2.check_gx = _code;
	//data2.comment_content = $("#comment_content_id").val();
	$.modal('/w/hospital_common/commentmanage/opinion/commentlist.html','添加',{
		content: 'no',
		data: JSON.stringify(data2),
		width: '450px',
		comment_code: $('[name=comment_content_code]').val(),
		callback : function(rtn){
			if(rtn){
				if(rtn.code){
					var code = '';
					for(var i=0;i< rtn.code.length;i++){
						if(i == 0){
							code = rtn.code[i].code;
						}else{
							code = code + ',' + rtn.code[i].code;
						}
					}
					$('[name=comment_content_code]').empty();
					$('[name=comment_content_code]').val('');
					$('[name=comment_content_code]').val(code);
				}
				if(rtn.content || rtn.code){
					$('[name=comment_content]').empty();
					$('[name=comment_content]').val('');
					$('[name=comment_content]').val(rtn.content);
				}
			}
        }
	})
});

function _clean(){
	$('[name=comment_content_code]').empty();
	$('[name=comment_content_code]').val('');
	$('[name=comment_content]').empty();
	$('[name=comment_content]').val('');
}
</script>