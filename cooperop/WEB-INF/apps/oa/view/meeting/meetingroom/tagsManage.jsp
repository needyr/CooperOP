<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true" >
	<s:row>
		<s:form label="增加标签" border="0" id="cfrom" >
			<s:row>
				<s:autocomplete label="标签" name="name" action="oa.meeting.meetingTags.queryDistinct" 
					limit="10" editable="true" cols="1" id="name"> 
 					<s:option value="$[name]" label="$[name]"></s:option>
 				</s:autocomplete>
				<s:button label="新增" style="margin-left: 10px;" onclick="save()" color="green" ></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table  label="标签" autoload="false" id="dtable"  action="oa.meeting.meetingTags.query" limit="10">
			<s:table.fields>
				<s:table.field name="name" datatype="string"  label="标签名" sort="true"></s:table.field>
				<s:table.field name="oper" datatype="script"  label="操作" >
	 				var html=[];
					html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="del('+record.id+')">删除</a>');
					return html.join('');
	 			</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	var meeting_id=${pageParam.id};

	$(document).ready(function() {
		query();
	});
	function query(){
		var d={meeting_room_id:meeting_id};
		$("#dtable").params(d);
		$("#dtable").refresh();
	}
	
	//新增
	function save(){
		if (!$("form").valid()) {
			return false;	
		}
		var d = $("#cfrom").getData();
		if(d.name){
			d.meeting_room_id=meeting_id;
			$.call("oa.meeting.meetingTags.insert", d, function(rtn) {
				if (rtn.result == "success") {
					$("#name").setData(value="",acvalue="");
                    query();
				}else{
					$.error(rtn.msg);
				}
			});
		}else{
			$.message("请输入有效标签名称！");
		}
		
	}
	
	//删除
	function del(id){
		$.confirm("删除后不可恢复，是否继续？", function(crtn){
			if(crtn){
				$.call("oa.meeting.meetingTags.update", {id:id,state:-1}, function(rtn) {
					if (rtn.result == "success") {
						query();
					}else{
						$.error(rtn.msg);
					}
				});
			}
		})
	}
	
</script>