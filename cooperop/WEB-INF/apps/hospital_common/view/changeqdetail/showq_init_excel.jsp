<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<script src="/res/hospital_common/layui/layui.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="/res/hospital_common/layui/css/layui.css">
<s:page title="查看审查问题" disloggedin="true">
<style type="text/css">
	.descr_cla{
		overflow: hidden;
	    text-overflow: ellipsis;
	    white-space: nowrap;
	}
	.tioaz{
		margin-right: 40px;
    	float: right;
	}
</style>
	<s:row>
		<s:form id="form"  fclass="portlet light bordered" collapsed="false" extendable="true">
			<s:row>
				<s:datefield label="开始时间" name="s_time" format="yyyy-MM-dd HH:mm:ss" ></s:datefield>
				<s:datefield label="截止时间" name="e_time" format="yyyy-MM-dd HH:mm:ss" ></s:datefield>
				<s:button label="按时间初始化" onclick="init()"></s:button>
			</s:row>
			<s:row>
				<%-- <s:select label="科室类型" name="d_type" value="1">
					<s:option label="住院" value="1"></s:option>
					<s:option label="门诊" value="2"></s:option>
					<s:option label="急诊" value="3"></s:option>
				</s:select> --%>
				<s:checkbox name="d_type" label="科室类型" cols="1">
					<s:option value="1" label="住院"></s:option>
					<s:option value="2" label="门诊"></s:option>
					<s:option value="3" label="急诊"></s:option>
				</s:checkbox>
				<s:textfield label="科室名称" name="dept_name"  cols="1" dbl_action='zl_select_jcx057_2_01_common,jcx057_2_common' disabled="disabled"></s:textfield>
			</s:row>
			<s:row>
				<s:select label="处方/医嘱" name="p_type" value="0" >
					<s:option label="全部" value="0"></s:option>
					<s:option label="医嘱" value="1"></s:option>
					<s:option label="处方" value="2"></s:option>
				</s:select>
				<s:textfield label="问题类型" name="sort_name"  cols="1" dbl_action='zl_select_jcx057_2_02_common,jcx057_2_common' disabled="disabled"></s:textfield>
			</s:row>
			<s:row>
				<s:checkbox label="严重程度" name="level" cols="1" style="border:none">
					<s:option label="关注" value="1"></s:option>
					<s:option label="慎用" value="2"></s:option>
					<s:option label="不推荐" value="3"></s:option>
					<s:option label="禁忌" value="4"></s:option>
				</s:checkbox>
				<s:radio label="排除已经调整" name="is_tiaoz_audit" cols="1" value="2">
					<s:option label="全部" value="2"></s:option>
					<s:option label="是" value="1"></s:option>
					<s:option label="否" value="0"></s:option>
				</s:radio>
				<s:textfield label="医嘱信息" name="drug_name_w"  cols="1" ></s:textfield>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<!-- 等待添加数据datatables -->
	    <table class="layui-table" id="test" lay-filter="test"></table>
	</s:row>
</s:page>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/table_print_excel.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/lay_tabletoexcel.js" type="text/javascript"></script>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
    <span style="font-size:14px"></span>
  	<button id="export" style="margin-left: 10px;" class="layui-btn layui-btn-primary layui-btn layui-btn-sm">导出结果清单</button>
  	<button class="layui-btn layui-btn-primary layui-btn layui-btn-sm" onclick="query()">查询</button>
  </div>
</script>
<script type="text/javascript">
	var myscroll = 0;
	var widthscroll = 0;
	var drug_name;
	var cols1;
	$(function(){
		defaultChoose();
		query();
	});
	
	function p(s) {
	    return s < 10 ? '0' + s: s;
	}
	
	function defaultChoose(){
		var myDate = new Date();
		//获取当前年
		var year=myDate.getFullYear();
		//获取当前月
		var month=myDate.getMonth()+1;
		//获取当前日
		var date=myDate.getDate(); 
		//var h=myDate.getHours();       //获取当前小时数(0-23)
		//var m=myDate.getMinutes();     //获取当前分钟数(0-59)
		//var s=myDate.getSeconds();  
		var startTime=year+'-'+p(month)+"-"+p(date)+" 00:00:00";
		var endTime=year+'-'+p(month)+"-"+p(date)+" 23:59:59";
		$('[name="s_time"]').val(startTime);
		$('[name="e_time"]').val(endTime);
		$('[name="dept_name"]').val('全部');
		$('[name="sort_name"]').val('全部');
	}
	var me = ['patient_name','dept_name']
	function query(){
		var data = $("#form").getData();
		if (!$("form").valid()){
		       return false;
		}
		create_table1(data);
	}
	
	function create_table1(params){
		var data_cols1 = [];
		var data_cols2 = [];
		data_cols1.push({field:'patient_id', title:'患者ID', width:80, rowspan: 2});
		data_cols1.push({field:'kdlx', title:'开单类型', width:80, rowspan: 2});
		data_cols1.push({field:'patient_name_d', title:'患者姓名', width:100, rowspan: 2});
		data_cols1.push({field:'sex', title:'性别', width:80, rowspan: 2});
		data_cols1.push({field:'age', title:'年龄', width:80, rowspan: 2});
		data_cols1.push({field:'dept_name', title:'科室', width:130, rowspan: 2});
		data_cols1.push({field:'diagnosis_name', title:'诊断', width:280, rowspan: 2});
		data_cols1.push({field:'check_level_name', title:'严重程度', width:80, rowspan: 2});
		data_cols1.push({field:'sort_name', title:'审查类型', width:80, rowspan: 2});
		data_cols1.push({field:'description', title:'描述', width:280, rowspan: 2});
		data_cols1.push({field:'reference', title:'参考', width:120, rowspan: 2});
		
		
		data_cols1.push({field:'yzxx', title:'医嘱信息', colspan: 4, align:'center'});
		
		data_cols2.push({field: 'drug_name', title:'药品名称', width:120});
		data_cols2.push({field: 'routename', title:'用法', width:120});
		data_cols2.push({field: 'dosage', title:'用量', width:120});
		data_cols2.push({field: 'freq', title:'频率', width:120});
		cols1 = [data_cols1, data_cols2];
		
		layui.use('table', function(){
		  var table = layui.table;
		  var ins1 = table.render({
		    elem: '#test'
		    ,url:'/w/hospital_common/changeqdetail/query_init_excel.json'
		    ,where: {'json':JSON.stringify(params)}
		    ,toolbar: '#toolbarDemo'
		    ,title: ''
	    	,defaultToolbar: ['filter']
		    //,totalRow: true
		    ,loading:true
		    ,height: $(window).height() - 
			150 - $("div[ctype='form']").parent().parent().height()
		    ,cols: cols1
		    ,page: true
		    ,autoSort: false //禁用前端自动排序。
		    ,limit: 50
		    ,response: {
		    	statusName: 'status' //规定数据状态的字段名称，默认：code
	            ,statusCode: null //规定成功的状态码，默认：0
	            ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
	            ,countName: 'count' //规定数据总数的字段名称，默认：count
	            ,dataName: 'resultset' //规定数据列表的字段名称，默认：data
		    }
		    ,request: {
			   pageName: 'layui_page' //页码的参数名称，默认：page
			   ,limitName: 'limit' //每页数据量的参数名，默认：limit
			}
		    //,data: real_re
		    ,done: function(res, curr, count){
		    	merge(res.resultset, curr, count,['patient_id','kdlx','patient_name_d',
		    		'sex','age','dept_name','diagnosis_name','check_level_name',
		    		'sort_name','description','reference'],[0,1,2,3,4,5,6,7,8,9,10],true);
		    	$("#export").click(function(){
		    		$.call('hospital_common.changeqdetail.query_init_excel',{'json':JSON.stringify(params),limit:-1},function(myresult){
		    			tabletoexcel(ins1.config.cols, myresult.resultset, ''
		    					, '审查问题' + $('[name="s_time"]').val() + ' ' + $('[name="e_time"]').val()
		    					,['patient_id','kdlx','patient_name_d',
				    			'sex','age','dept_name','diagnosis_name','check_level_name',
				    			'sort_name','description','reference'],[0,1,2,3,4,5,6,7,8,9,10],true)
		    		},null,{timeout:0})
		    	})
		    }
		    })
	    	$("i.showhide").on("click", function(){
	    		setTimeout(function(){
					table.reload('test', {height: $(window).height() - 
						150 - $("div[ctype='form']").parent().parent().height()});
				},500)
			})
	    	$(".form-collapse-btn").on("click", function(){
				setTimeout(function(){
					table.reload('test', {height: $(window).height() - 
						150 - $("div[ctype='form']").parent().parent().height()});
				},500)
			})
		});
	}
	
	function patientinfo(patient_id, visit_id){
		$.modal('/w/hospital_common/patient/index.html?patient_id='+patient_id+'&visit_id='+visit_id+'',
				'患者详情',
				{
			       width:"96%",
			       height:"99%",
			       callback : function(e){}
				}
		);
	}
	
	function changeLevel(auto_audit_id, check_result_info_id, t_this){
		$.modal('/w/ipc/comment/pass.html?auto_audit_id='+auto_audit_id+'&check_result_info_id='+check_result_info_id+'',
				'调整问题等级',
				{
			       width:"500px",
			       height:"460px",
			       callback : function(rtn){
			    	   if(rtn){
			    		   //changeTZ(rtn);
			    		   if(rtn.pharmacist_todoctor_advice){
				    		   $.call("hospital_common.changeqdetail.updateTmp",{cz:"0",auto_audit_id:auto_audit_id,check_result_info_id:check_result_info_id},function(rtn){
				    		   	query_myscroll();
				    		   })
			    		   }else{
			    			   $.call("hospital_common.changeqdetail.updateTmp",{cz:"2",auto_audit_id:auto_audit_id,check_result_info_id:check_result_info_id},function(rtn){
				    		   	query_myscroll();
				    		   })
			    		   }
			    	   }
			       }
				}
		);
		event.stopPropagation();
	}
	
	function addAdvice(auto_audit_id, check_result_info_id, level,t_this){
		if($(t_this).text() == "已经调整"){
			$.modal('/w/ipc/comment/pass.html?auto_audit_id='+auto_audit_id+'&check_result_info_id='+check_result_info_id+'&level='+level+'&is_add=1',
					'添加药师意见',
					{
				       width:"500px",
				       height:"460px",
				       callback : function(rtn){
				    	   if(rtn){
				    		   if(rtn.pharmacist_todoctor_advice){
				    			   //changeTZ(rtn);
				    			   query_myscroll();
				    			}
				    	   }
				       }
					}
			);
		}else{
			$.modal('/w/ipc/comment/pass.html?auto_audit_id='+auto_audit_id+'&check_result_info_id='+check_result_info_id+'&level='+level+'',
					'添加药师意见',
					{
				       width:"500px",
				       height:"460px",
				       callback : function(rtn){
				    	   if(rtn){
				    		   if(rtn.pharmacist_todoctor_advice){
				    			   //changeTZ(rtn);
				    			   $.call("hospital_common.changeqdetail.updateTmp",{auto_audit_id:auto_audit_id,check_result_info_id:check_result_info_id,cz:"1"},function(rtn){
					    		   query_myscroll();
					    		   })
				    			}
				    	   }
				       }
					}
			);
		}
		event.stopPropagation();
	}
	
	function changeTZ(rtn){
		var d = rtn.description.replace(/(^\s*)/g, "").replace(/\ /g,"\\ ").replace(/\	/g,"\\	").replace(/\./g,"\\.").replace(/\(/g,"\\(").replace(/\]/g,"\\]").replace(/[\r\n]/g,"");
		var elelist = document.querySelectorAll('[data-content="'+d+'"]');
		var ad = document.querySelectorAll('[data-content-ad="'+d+'"] a');
		for(var i=0;i<elelist.length;i++){
		 	elelist[i].innerHTML = "已经调整";
		}
		if(rtn.pharmacist_todoctor_advice){
			 for(var j=0;j<ad.length;j++){
				  ad[j].setAttribute('style', 'color: #b88b07 !important');
				  ad[j].innerText = '已经调整';
			 }
		}
	}
	
	function drugSms(drug_code){
		 if(drug_code.indexOf('[') > 0){
			 return ;
		 }
		 $.modal("/w/ipc/auditresult/instruction.html?his_drug_code="+drug_code,"查看药品说明书",{
	        width:"70%",
	        height:"90%",
	        callback : function(e){
	        }
		}); 
		event.stopPropagation();
	}
	
	function copy(becopy){
		becopy = becopy.replace('\t','');
		const input = document.createElement('input');
	    document.body.appendChild(input);
	    input.setAttribute('value', becopy);
	    input.select();
	    if (document.execCommand('copy')) {
	        document.execCommand('copy');
	        console.log('复制成功');
	    }
	 	document.body.removeChild(input);
	 	layer.msg('复制成功', {
	 		  icon: 1,
	 		  time: 800 //2秒关闭（如果不配置，默认是3秒）
	 		}, function(){
	 		  //do something
	 		}); 
	 	event.stopPropagation(); 
	}
	
	function init(){
		var formData = $('#form').getData();
		$.call('hospital_common.changeqdetail.init', formData, function(rtn){
			$.message("初始化成功");
		},function(e){},{async:true,remark:false,timeout:null});
	}
	
	function shaixuan(){
		$('#datatable').setting_table();
	}
	
	function daochu(){
		var data = $("#form").getData();
		$("#datatable").parents('.portlet').children('.portlet-title').children('.caption').text("审查结果"+data.s_time+'_'+data.e_time)
		$("#datatable").params_table(data);
		$("#datatable").excel_new();
	}
</script>