<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="数据校验日志详情" >
<style type="text/css">
.choose{background-color: #d3d3d3 !important;}
</style>
	<s:row>
<s:form extendable="true" collapsed="false" id="form">
<s:row>
<s:textfield label="错误表名称"  cols="1" name="table_name"></s:textfield>
<s:radio label="状态" cols="1" name="is_deal" value="">
	<s:option label="全部" value=""></s:option>
	<s:option label="未处理" value="0"></s:option>
	<s:option label="处理" value="1"></s:option>
</s:radio>
<s:button label="查询"  onClick="query()"></s:button>
</s:row>
</s:form>
<s:table action="hospital_common.guide.verify.queryLogMx" sort="true" fitwidth="true" label="日志查看"  id="datatable">
	<s:table.fields>
		<s:table.field name="table_name" label="校验表" ></s:table.field>
		<s:table.field name="msg" label="信息" datatype="script" >
			var msg = record.msg
			return '<a data-tit="'+msg+'" onclick="look(this)">'+(msg?(msg.length>20?msg.substring(0,20)+'...':msg):'')+'</a>';
		</s:table.field>
		<s:table.field name="i_table_name" label="主表" ></s:table.field>
		<s:table.field name="i_field" label="主表字段" datatype="script">
			var msg = record.i_field
			return '<a data-tit="'+msg+'" onclick="look(this)">'+(msg?(msg.length>20?msg.substring(0,20)+'...':msg):'')+'</a>';
		</s:table.field>
		<s:table.field name="c_table_name" label="联合表" ></s:table.field>
		<s:table.field name="c_field" label="联合表字段" datatype="script">
			var msg = record.c_field
			return '<a data-tit="'+msg+'" onclick="look(this)">'+(msg?(msg.length>20?msg.substring(0,20)+'...':msg):'')+'</a>';
		</s:table.field>
		<s:table.field name="is_deal" label="是否已经处理"  datatype="script">
			return record.is_deal == 1?'<lable style="color:green;">已处理</lable>':'<lable style="color:red;">未处理</lable>';
		</s:table.field>
		<s:table.field name="create_time" label="校验时间" datatype="datetime" format="yyyy-MM-dd HH:mm:ss"></s:table.field>
		<s:table.field name="deal_time" label="处理时间" datatype="datetime" format="yyyy-MM-dd HH:mm:ss"></s:table.field>
		<s:table.field name="caozuo" label="操作" datatype="script" width="180px" >
			if(record.is_deal == 1){
			return '';
			}else{
			return '<a href="javascript:void(0)" onclick="detail(\''+record.id+'\')" style="color:blue;">处理</a>';
			}
		</s:table.field>
	</s:table.fields>
</s:table>
</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		query();
		$('#datatable').on('click', 'tbody tr', function(e) { 
			$(this).addClass('choose').siblings().removeClass('choose');
		});
		
		$(window).resize(function(){
			var wid = $(window).height() - 
			280 - $("div[ctype='form']").parent().parent().height();
			if(wid <= 250){
				wid = 250
			}
			$('.dataTables_scrollBody').css('height',wid);
		}).resize();
		
		$("i.showhide").on("click", function(){
			setTimeout(function(){
				var wid = $(window).height() - 
				280 - $("div[ctype='form']").parent().parent().height();
				if(wid <= 250){
					wid = 250
				}
				$('.dataTables_scrollBody').css('height',wid);
			},500)
		})
	});
	
	function query(){
		var data = $('#form').getData();
		data.log_bh = '${param.log_bh}'
		$("#datatable").params(data);
		$("#datatable").refresh();
	}

	function look(_this){
		layer.open({
			type: 1,
			title: '详细信息',
			area: ['400px', '300px'],
			skin: 'layui-layer-lan',
			shadeClose: true,
			content:'<div style="padding: 10px">'+$(_this).attr('data-tit')+'</div>'
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

	function detail(id){
		$.confirm("是否确认已经处理?",function callback(e){
			if(e==true){
				$.call('hospital_common.guide.verify.updateLog',{id:id,is_deal:1},function(rtn){
					query();
				})
			}
		})
	}
</script>
