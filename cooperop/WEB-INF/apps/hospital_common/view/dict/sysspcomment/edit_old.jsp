<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style type="text/css">
#tags_choose{width: calc(80% - 101px);border: 1px dashed #d0d0d0;min-height: 28px;float: right;margin-bottom: 6px;margin-right: 10px;padding: 3px;}
.tag_type{border: 1px solid #ff8100;border-radius: 8px !important;padding: 2px;font-family: 微软雅黑;background-color: #ffdd00;font-weight: 600;font-size: 10px;margin: 2px;cursor: pointer;display: -webkit-inline-box;}
.bootstrap-switch{height:30px;}
</style>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/ChinesePY.js"></script>
<s:page title="专项点评字典信息" ismodal="true">
	<s:row>
		<s:form id="form" label="专项点评字典信息">
			<s:toolbar>
				<s:button label="保存" onclick="saveOrUpdate()" icon="glyphicon glyphicon-floppy-saved"/>
				<s:button label="取消" onclick="cancel()" icon="glyphicon glyphicon-remove"/>
			</s:toolbar>
				<s:row>
				    <c:if test="${!empty param.id}">
				        <input value="${id}" name="id" type="hidden">
				    </c:if>
				</s:row>
				<s:row>
				    <s:textfield label="点评字典名称" name="spcomment_name" value="${spcomment_name}" required="true" cols="3"/>
			    </s:row>
			    <s:row>
			        <s:textfield label="拼音名" name="pym" value="${pym}" required="true" cols="2"/>
			        <s:textfield label="起始间隔天数" name="day_interval" value="${day_interval}" required="true" cols="1" placeholder="大于等于0纯数字"/>
			    </s:row>
			    <s:row>
					<div style="width:100%;margin: 1px 0px 6px;">
						<font style="float: left;width:96px;margin-left: 4px;margin-top:5px;">标签类型</font>
						<div id="tags_choose"></div>
						<select style="width:calc(20% - 9px);height: 31px;" id="tags_select">
							<option value="" selected = "selected">--必选--</option>
						</select>
					</div>
				</s:row>
				<s:row>
					<s:select label="抽样类型" name="spcomment_unit" value="${spcomment_unit}" cols="1">
						<s:option label="按人为单位" value="1"></s:option>
						<s:option label="按处方为单位" value="2"></s:option>
						<s:option label="按医嘱为单位" value="3"></s:option>
					</s:select>
					<s:select label="数据来源" name="d_type" value="${d_type}" cols="1">
						<s:option label="门急诊" value="-1"></s:option>
						<s:option label="住院" value="1"></s:option>
						<s:option label="急诊" value="3"></s:option>
						<s:option label="门诊" value="2"></s:option>
					</s:select>
					<s:select label="样本类型" name="p_type" value="${p_type}" cols="1">
						<s:option label="已收费处方" value="1"></s:option>
						<s:option label="在院医嘱" value="2"></s:option>
						<s:option label="出院医嘱" value="3"></s:option>
					</s:select>
			    </s:row>
			    <s:row>
					<s:textfield label="点评规则" name="sys_comment_rules_code_name" value="${sys_comment_rules_code_name}"  readonly="true" placeholder="双击选取点评规则(必填)" dbl_action="noaction()" cols="3"/>
			        <input name="sys_comment_rules_code" value="${sys_comment_rules_code}" type="hidden">
			    </s:row>
				<s:row>
					<s:textfield label="开嘱科室" name="dept_name" value="${dept_name}" readonly="true" placeholder="双击选取科室" dbl_action="noaction()" cols="3"/>
					<input name="dept_code" value="${dept_code}" type="hidden">
				</s:row>
				<s:row>
					<s:textfield label="费别" name="feibie_name" value="${feibie_name}" readonly="true" placeholder="双击选取费别" dbl_action="noaction()" cols="3"/>	
					<input name="feibie_code" value="${feibie_code}" type="hidden">
				</s:row>
				<s:row>
					<s:textarea label="备注" name="beizhu" value="${beizhu}" cols="3"/>
				</s:row>
				<s:row>
				    <s:switch label="是否启用" value="${beactive}" name="beactive" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch>
				</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		loadzhuanxiangleixing();
		d_typefun();//根据数据来源执行逻辑
	});
	
	$('[name="spcomment_name"]').bind('input propertychange',function(){
		var pym = Pinyin.GetJP($('[name="spcomment_name"]').val());
		$('[name="pym"]').val(pym);
	})
	//加载专项类型
	function loadzhuanxiangleixing(){
		$.call("hospital_common.dict.sysspcomment.querytags",{},function(s){
			if(s&&s.resultset){
				var objs=s.resultset;
				var optionhtml=[];
				for(var i=0;i<objs.length;i++){
					var obj=objs[i];
					optionhtml.push('<option value="'+obj.drugtagbh+'" data-tagid="'+obj.drugtagid+'" data-tagname="'+obj.drugtagname+'" data-tagshow="'+obj.drugtag_show+'" data-tagshuom="'+obj.drugtag_shuom+'">'+obj.drugtag_show+'</option>');
				}
				$("#tags_select").append(optionhtml.join(''));
			}
			var order='${order}';
			if(order){
				var json=(eval("("+order+")"));
				var times=json.drugtagid.length;
				var spanhtml=[];
				for(var i=0;i<times;i++){
					spanhtml.push('<font data-tagbh="'+json.drugtagbh[i]+'" data-tagid="'+json.drugtagid[i]+'" data-tagname="'+json.drugtagname[i]+'" data-tagshow="'+json.drugtag_show[i]+'" class="tag_type">'+json.drugtag_show[i]+'</font>');
				}
				$("#tags_choose").append(spanhtml);
				$('[class="tag_type"]').dblclick(function(){
					$(this).remove();
				})
			}
   		});
	}

	//规范检查
	function runcheck(){
		if(!$("form").valid()){return false;}//必填检查
		else if(isNaN($('[name="day_interval"]').val())||$('[name="day_interval"]').val()<0){$('[name="day_interval"]').focus();return false;}//间隔天数必须大于等于0的纯数字
		else if($("#tags_choose").find("font").length==0){return false;}//专项类型必填
		else if($("[name='sys_comment_rules_code']").val()==''){return false;}//点评规则必填
		return true;
	}
	
	function saveOrUpdate(){
		if(runcheck()){//规范检查
			var data=$("#form").getData();
			var tagbh=[],tagid=[],tagname=[],tagshow=[];
			$("#tags_choose").find("font").each(function(){
				tagbh.push($(this).attr("data-tagbh"));
				tagid.push($(this).attr("data-tagid"));
				tagname.push($(this).attr("data-tagname"));
				tagshow.push($(this).attr("data-tagshow"));
			});
			data.ordertagbh=tagbh.toString();
			data.ordertagid=tagid.toString();
			data.ordertagname=tagname.toString();
			data.ordertag_show=tagshow.toString();
			var id = '${param.id}';
			if(id!=null&&typeof(id)!='undefind'&&id!=''){//id不为空,修改
				$.call("hospital_common.dict.sysspcomment.update",data,function(s){
					if(s==1){$.closeModal(s);}
		    	});
			}else{//为空，新增
				$.call("hospital_common.dict.sysspcomment.save",data,function(s){
					if(s==1){$.closeModal(s);}
		   		});
			}
		}
	}
    //选择科室
	$('[name=dept_name]').dblclick(function(){
		var code = $('[name=dept_code]').val();
		var d_type = $('[name=d_type]').val();
		$.modal("/w/ipc/sample/choose/department.html", "添加科室", {
			height: "550px",
			width: "40%",
			code: code,
			maxmin: false,
			datasouce: d_type,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=dept_name]').val(name);
					$('[name=dept_code]').val(code);
				}
		    }
		});
	});
	//选择费别
	$('[name=feibie_name]').dblclick(function(){
		var code = $('[name=feibie_code]').val();
		$.modal("/w/ipc/sample/choose/feibie.html", "添加费别", {
			height: "550px",
			width: "40%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=feibie_name]').val(name);
					$('[name=feibie_code]').val(code);
				}
		    }
		});
	});
	//选择点评规则
	$("[name='sys_comment_rules_code_name']").dblclick(function(){
		var code = $('[name=sys_comment_rules_code]').val();
		$.modal("/w/hospital_common/dictsyscomment/show.html", "添加点评规则", {
			height: "550px",
			width: "40%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=sys_comment_rules_code_name]').val(name);
					$('[name=sys_comment_rules_code]').val(code);
				}
		    }
		});
	});
	$('#tags_select').change(function(){
		var value = $('#tags_select option:selected').val();
		var check = 1;
		if(value){
			$('#tags_choose font').each(function(index){
				var tagbh = $(this).attr('data-tagbh');
				if(tagbh == value)
					check = 0;	
			})
			if(check == 1){
				var label = $('#tags_select option:selected').text();
				var tagshuom = $('#tags_select option:selected').attr('data-tagshuom');
				var tagid = $('#tags_select option:selected').attr('data-tagid');
				var tagname = $('#tags_select option:selected').attr('data-tagname');
				var tagshow = $('#tags_select option:selected').attr('data-tagshow');
				var html = $('#tags_choose').html();
				html+='<font data-tagbh="'+value+'" data-tagid="'+tagid+'" data-tagname="'+tagname+'" data-tagshow="'+tagshow+'" class="tag_type" title="'+tagshuom+'">'+label+'</font>';
				$('#tags_choose').html(html);
				$('[class="tag_type"]').dblclick(function(){
					$(this).remove();
				})
			}
		}
	});
	//监听数据来源change事件，执行逻辑
	$('[name="d_type"]').change(function(){
		d_typefun();
	});
	//根据数据来源，执行逻辑
	function d_typefun(){
		var d_typeobj=$('[name="d_type"]');
		var html='<option value="1">已收费处方</option>';
		if(d_typeobj.val()=='1'){//如果为住院
			$('[name="p_type"]').find('[value="1"]').remove();//移除已收费处方
			$('[name="p_type"]').val('2');//默认在院医嘱
		}else {//不为住院
			if($('[name="p_type"]').find('[value="1"]').length==0){//如果不存在已收费医嘱，则添加已收费医嘱
				$('[name="p_type"]').prepend(html);
			}
			if(d_typeobj.val()=='2'||d_typeobj.val()=='3'||d_typeobj.val()=='-1'){//门诊，急诊，门急诊，默认已收费医嘱
				$('[name="p_type"]').val('1');
			}
		}
	}
	//取消
    function cancel(){
    	$.closeModal(false);
    }
</script>