<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑字典信息" disloggedin="true">
	<s:row>
		<s:form id="Mform" label="字典信息" >
			<s:toolbar>
				<s:button label="保存" onclick="saveOrUpdate();" icon="fa fa-edit" size="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="$.closeModal();" icon="fa fa-ban" size="btn-sm btn-default"></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="id" value="${id }"/>
				<s:row>
				<c:if test="${not empty param.first}">
				<s:autocomplete label="选择产品" name="system_product_code" value="${system_product_name }" action="hospital_common.commentmanage.queryProduct" required="true" cols="3" limit="10">
					<s:option value="$[code]" label="$[name]">
						$[code]-$[name]
					</s:option>
				</s:autocomplete>
				</c:if>
				<c:if test="${not empty param.ptid }">
					<input type="hidden" name="parent_id" value="${param.pname }"/>
				</c:if>
				<c:if test="${empty param.ptid }">
					<c:if test="${not empty param.first }">
						<input type="hidden" name="parent_id" value=""/>
					</c:if>
					<c:if test="${empty param.first }">
						<s:autocomplete label="选择上级节点" name="parent_id" value="${pjiancheng }" action="hospital_common.commentmanage.queryPid" required="true" cols="3" limit="10" 
						params="{&#34;level&#34;:&#34;${level }&#34;,&#34;id&#34;:&#34;${id }&#34;}">
							<s:option value="$[id]" label="$[comment_jiancheng]">
								<span style="float:left;display:block;width:100px;">$[comment_jiancheng]</span>
							</s:option>
						</s:autocomplete>
					</c:if>
				</c:if>
				<s:textfield label="顺序号" name="comment_code" cols="3" value="${comment_code }" required="true" disabled="disabled"></s:textfield>
				<s:textfield label="使用代码" name="system_code" cols="3" value="${system_code }" required="true" disabled="disabled"></s:textfield>
				<s:textarea label="点评名称" name="comment_name" cols="3" value="${comment_name }" required="true"></s:textarea>
				<s:textfield label="点评简称" name="comment_jiancheng" cols="3" value="${comment_jiancheng }" required="true"></s:textfield>
				<s:textfield label="排序号" name="sort" cols="3" value="${sort }" required="true"></s:textfield>
				</s:row>
				<s:row>
					<s:textarea label="规则说明" name="description" cols="3" >${description}</s:textarea>
				</s:row>
				<s:row><s:switch label="是否活动" value="${beactive }" name="beactive" onvalue="是" offvalue="否" ontext="是" offtext="否" ></s:switch></s:row>
				<s:row><s:switch label="是否系统条目" value="${is_sys }" name="is_sys" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch></s:row>
				<c:if test="${param.tolevel == 4 }">
				    <s:row><s:switch label="是否合理" value="${is_hl}" name="is_hl" onvalue="1" offvalue="0" ontext="是" offtext="否"></s:switch></s:row>
				</c:if>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	
	$(function(){
		growSort();
	})

	//新增的时候，自动生成顺序号 和 使用代码
	function growSort(){
		gdata  = $('#Mform').getData();
		if(gdata.id == ''){
			var sort = 0;
			var parent_id = '${param.ptid}';
			$.call('hospital_common.commentmanage.growSort', {"parent_id": parent_id}, function(rtn){
				sort = ++ rtn.maxsort;
				$('[name="comment_code"]').setData(sort);
				if(rtn.system_code == null){
					$('[name="system_code"]').setData('(' + sort + ')');
				}else{
					$('[name="system_code"]').setData(rtn.system_code + '-' +sort);
				}
			});
		}
	}
	
	function saveOrUpdate(){
		var product_name=$("input[name='system_product_code']").val();
		var data = $("#Mform").getData();
		data.system_product_name=product_name;
		if (!$("form").valid()){
    		return false;
    	}
		if ("${pageParam.id}") {
			if(data.parent_id =='${param.pname}'){
				data.parent_id = '${param.ptid}';
			}
			if(data.parent_id =='${pjiancheng}'){
				data.parent_id = '${parent_id}';
			}
			if(data.system_product_code =='${system_product_name }'){
				data.system_product_code = '${system_product_code }'
			}
			$.call("hospital_common.commentmanage.update", data, function(rtn) {
				$.closeModal(true);
			});
		} else {
			if(data.parent_id =='${pjiancheng}'){
				data.parent_id = '${parent_id}';
			}
			if(data.parent_id =='${param.pname}'){
				data.parent_id = '${param.ptid}';
			}
			$.call("hospital_common.commentmanage.save", data, function(rtn) {
				$.closeModal(true);
			});
		}
	}
	
</script>