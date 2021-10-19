<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="事后审查队列列表" disloggedin="false">
<style type="text/css">
.choose{background-color: #d3d3d3 !important;}
</style>
	<s:row>
		<s:form id="form" label="筛选条件">
			<s:row>
				<s:datefield label="队列创建时间从" name="start_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:datefield label="至" name="end_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:button label="查询" icon="fa fa-search" onclick="query();"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="事后审查队列列表" autoload="false" action="hospital_common.afaudit.query" icon="fa fa-list" sort="true">
            <%--<s:toolbar>
                <s:button label="新增队列" icon="fa fa-plus" onclick="addQueue();"></s:button>
            </s:toolbar>--%>
            <s:table.fields>
                <s:table.field name="createtime" label="队列创建时间" sort="true" defaultsort="desc"></s:table.field>
                <s:table.field name="audit_times" label="执行次数" sort="true"></s:table.field>
                <s:table.field name="execute_date" label="执行时间" sort="true"></s:table.field>
                <s:table.field name="audit_end_time" label="审查完成时间" sort="true"></s:table.field>
                <s:table.field name="remark" label="描述"></s:table.field>
                <s:table.field name="state" label="状态" datatype="script">
                    var state = record.state;
					if(state == '0'){return "<span>已执行</span>" ;}
					else if(state == '1'){return "<span style='color:#DAA520'>待执行</span>";}
					else if(state == '2'){return "<span style='color:#32CD32'>正在执行</span>";}
					else {return "<span style='color:red'>状态异常</span>";}
                </s:table.field>
                <s:table.field name="products" label="审查产品" datatype="script">
                	var prods = record.products;
                	if(prods){
                		prods = prods.replace(/ipc/, '合理用药');
                		prods = prods.replace(/hospital_imic/, '医保控费');
                		return prods;
                	}else{
                		return '默认';
                	}
                </s:table.field>
                <s:table.field name="caozuo" label="操作" datatype="script">
                	var fhtml = [] ;
                	fhtml.push('<a href="javascript:void(0)" onclick="auditDetail(\''+ record.id +'\', \''+ record.audit_times +'\', \''+ record.products +'\')">');
                	fhtml.push('详情</a>&nbsp;&nbsp;');
                	var state = record.state; 
                	fhtml.push('<a href="javascript:void(0)" onclick="changeQState(\''+ record.id +'\',\''+ record.state +'\', this)">');
                	if(state == '0'){fhtml.push('再次执行</a>');}
					else{fhtml.push('</a>');}
                	return fhtml.join('');
                </s:table.field>
            </s:table.fields>
        </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		query();
		setInterval("query()",20000);//每20秒更新一次
		$('#datatable').on('click', 'tbody tr', function(e) { 
			$(this).addClass('choose').siblings().removeClass('choose');
		});
	});
	
	function query(){
		var data = $('#form').getData();
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	
	/** 查询事后审查详情 */
	function auditDetail(audit_queue_id, audit_times, products){
	    $.modal("/w/hospital_common/afaudit/show.html?audit_queue_id="+ audit_queue_id+"&audit_times="+audit_times+"&products="+products,"事后审查队列详情",{
	    	width: '99%',
	    	height: '99%',
	        callback : function(e){}
	    });
	}
	
	//新增队列
	function addQueue(){
		$.modal("/w/hospital_common/afaudit/edit.html","添加事后审查队列",{
	    	width: '1200px',
	    	height: '99%',
	        callback : function(e){query();}
	    });
	}
	
	//再次执行或取消执行
	function changeQState(id,state,obj){
		if(state=='0'){
			//判断审查规则是否有更新
			$.call("hospital_common.afaudit.isUpdateRegulation",{},
			function(rtn){
				if(rtn=='0'){
					$.confirm("当前审查规则未改变，审查结果可能一致，确定再次审查吗？",function callback(e){
						if(e==true){changeQState2(id,state,obj);}
					});
				}else{changeQState2(id,state,obj);}
			});
		}else{changeQState2(id,state,obj);}
	}
	//改变队列是否执行状态
	function changeQState2(id,state,obj){
		$.call("hospital_common.afaudit.changeState",{id : id,state:state==0?1:0},
		function(rtn){
			if(rtn){query();}
		});
	}
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
</script>
