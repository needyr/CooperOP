<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="专项点评列表">
    <s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="快速查找" name="filter" placeholder="请输入点评字典名称、code或者拼音名" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="专项点评字典列表" icon="fa fa-list" autoload="false"
			sort="true" limit="10" fitwidth="true" action="hospital_common.dict.sysspcomment.query" 
			 height="400" >
			<s:toolbar>
				<s:button icon="glyphicon glyphicon-plus" label="新增点评" onclick="add()"/>
			</s:toolbar>
			<s:table.fields>
				<s:table.field label="点评名称" name="spcomment_name" datatype="string" width="100px"/>
				<s:table.field label="倒推天数" name="day_interval" datatype="string" width="30px"/>
				<s:table.field label="点评规则" name="sys_comment_rules_code_name" datatype="string"/>
				<s:table.field label="点评科室" name="dept_name" datatype="string"/>
				<s:table.field label="费别" name="feibie" datatype="string"/>
				<s:table.field label="开单类型" name="type" datatype='script' width="65px">
					var type = record.type + ',';
					var str = '';
					if(type){
						if(type.indexOf('1,') != -1 ){
							str = str + ' 门诊 ';
						}
						if(type.indexOf('2,') != -1 ){
							str = str + ' 急诊 ';
						}
						if(type.indexOf('3,') != -1 ){
							str = str + ' 在院 ';
						}
						if(type.indexOf('4,') != -1 ){
							str = str + ' 出院 ';
						}
					}
	                return str;
				</s:table.field>
				<s:table.field label="专项类型" name="ordertag" datatype="string"/>
				<s:table.field label="状态" name="beactive" datatype="script" width="30px">
				    var beactive = record.beactive;
	              	if(beactive == 1){beactive= '启用';}
	               	else if (beactive == 0){beactive= '禁止';}
	                return beactive;
				</s:table.field>
				<s:table.field label="备注" name="beizhu" datatype="string"/>
				<s:table.field name="caozuo" label="操作" datatype="script" width="60px">
                	var fhtml = [] ;
                	fhtml.push('<a href="javascript:void(0)" onclick="edit(\''+ record.id +'\')">');
                	fhtml.push('编辑</a>&nbsp;&nbsp;');
                	fhtml.push('<a href="javascript:void(0)" onclick="Delete(\''+ record.mx_id +'\',\''+ record.id +'\')">');
					fhtml.push('删除</a>');
                	return fhtml.join('');
                </s:table.field>
		    </s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		query();
	});
	function query(){
		var data = $("#form").getData();
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	//执行前对数据规范的检查
	function standardCheck(){
		//var re = /^[0-9]+.?[0-9]*$/; 
	    //判断正整数 /^[1-9]+[0-9]*]*$/ (非空项用正则表达式，允许空用isNaN())
	    //if(re.test(nubmer))
	    //if(isNaN(nubmer))
		if($("form").valid()){//必填检查
			//就诊日期检查
			var qTimeLock=0;
			var mintimeval=$("[name='mintime']").val();
		    var maxtimeval=$("[name='maxtime']").val();
		    if(mintimeval != "" && maxtimeval != ""){qTimeLock=1;}//就诊日期前后都填了
			if((mintimeval != "" && maxtimeval != "") || (mintimeval == "" && maxtimeval == "")){//就诊日期前后都填了或者前后都没填
				if(qTimeLock == 1){//就诊日期前后都填了
					var arr = mintimeval.split('-');
					var min = new Date(arr[0],arr[1],arr[2]);
					var mintime = min.getTime();
					var arrB = maxtimeval.split('-');
					var max = new Date(arrB[0],arrB[1],arrB[2]);
					var maxtime = max.getTime();
					if(mintime > maxtime){$.message("就诊时间填写有误！");}
				}
				//就诊年龄检查
				var qAgeLock=0;
				var p_min_age=$("[name='p_min_age']").val();
	    		var p_max_age=$("[name='p_max_age']").val();
	    		if(p_min_age != "" && p_max_age != ""){qAgeLock==1;}//就诊年龄前后都填了
	    		if((p_min_age!=""&&p_max_age!="")||(p_min_age==""&&p_max_age=="")){//就诊年龄前后都填了或者前后都没填
	    			if(qAgeLock==1){//就诊年龄前后都填了
	    				if(isNaN(p_min_age)||isNaN(p_max_age)||(p_min_age>p_max_age)){$.message("就诊年龄填写有误！");}//就诊年龄不符合规则，允许空
	    			}
	    			//队列上限检查
	    			var nubmer = $("[name='qMaxCount']").val();
	    			if(!isNaN(nubmer)){//队列上限是纯数字,允许空
	    				return true;
	    			}else{$.message("队列上限填写有误！");}
	    		}else{$.message("就诊年龄填写有误！");}
			}else{$.message("就诊时间填写有误！");}
		}
		return false;
	}
	//选择科室
	$('#dept_name').dblclick(function(){
		var code = $('[name=deptfifter]').val();
		var d_type = $('[name=d_type]').val();
		$.modal("/w/ipc/sample/choose/department.html", "添加科室", {
			height: "550px",
			width: "40%",
			code: code,
			maxmin: false,
			d_type: d_type,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('#dept_name').val(name);
					$('[name=deptfifter]').val(code);
				}
		    }
		});
	});
	//选择费别
	$('#feibie_name').dblclick(function(){
		var code = $('[name=feibiefifter]').val();
		$.modal("/w/ipc/sample/choose/feibie.html", "添加费别", {
			height: "550px",
			width: "40%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('#feibie_name').val(name);
					$('[name=feibiefifter]').val(code);
				}
		    }
		});
	});
	function Delete(mx_id,id){
		$.confirm("是否确认删除？删除后无法恢复！",function callback(e){
			if(e){
				$.call("hospital_common.dict.sysspcomment.delete",
						{mx_id : mx_id,id:id},
						function(rtn){
							if(rtn==1){query();
						}
				});
			}
		});
	}
	function add(){
		$.modal("/w/hospital_common/dict/sysspcomment/edit.html","新增点评信息",{
			width:"750px",
			height:"90%",
			callback : function(e){query();}
		});
	}
	function edit(id){
		$.modal("/w/hospital_common/dict/sysspcomment/edit.html","编辑点评信息",{
			width:"750px",
			height:"90%",
			id:id,
			callback : function(e){query();}
		});
	}
</script>