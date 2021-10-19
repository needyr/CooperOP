<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="数据校验" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.bar-box {
    position: absolute;
    width: 100%;
    top: 50px;	
}

.main {
	padding: 0px;
}
</style>
<s:row>
<s:form extendable="true" collapsed="true" id="dataform">
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
<s:table action="hospital_common.guide.verify.queryQuestion" limit="-1" sort="true" fitwidth="true" label="日志查看"  id="datatable">
	<s:toolbar>
	<button onClick="start()" class="btn btn-sm btn-sm btn-default"><i class="fa fa-tags"></i>数据校验开始</button>
	</s:toolbar>
	<s:table.fields>
		<s:table.field name="table_name" label="校验表" ></s:table.field>
		<s:table.field name="msg" label="信息" datatype="script" >
			var msg = record.msg
			return '<a data-tit="'+msg+'" onclick="look(this)" style="color:#3c6abb;">'+(msg?(msg.length>20?msg.substring(0,20)+'...':msg):'')+'</a>';
		</s:table.field>
		<s:table.field name="i_table_name" label="主表" ></s:table.field>
		<s:table.field name="i_field" label="主表字段" datatype="script">
			var msg = record.i_field
			return '<a data-tit="'+msg+'" onclick="look(this)" style="color:#3c6abb;">'+(msg?(msg.length>20?msg.substring(0,20)+'...':msg):'')+'</a>';
		</s:table.field>
		<s:table.field name="c_table_name" label="联合表" ></s:table.field>
		<s:table.field name="c_field" label="联合表字段" datatype="script">
			var msg = record.c_field
			return '<a data-tit="'+msg+'" onclick="look(this)" style="color:#3c6abb;">'+(msg?(msg.length>20?msg.substring(0,20)+'...':msg):'')+'</a>';
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
				return '<a href="javascript:void(0)" style="width:180px;color:#3c6abb;" onclick="detail(\''+record.id+'\')" style="color:blue;">处理</a>';
			}
		</s:table.field>
	</s:table.fields>
</s:table>
</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		$.call('hospital_common.guide.verify.getBar',{},function(r){
			if(r && r.verify_thread1){
				var i = 0;
				var index = layer.open({
					type: 1, 
					shade: 0.3,
					closeBtn: 0,
					move: false,
					//time:2000,
					area:["60%","60%"],
				  	content: '<div class="bar-box" style="">'
						+'<div class="progress-tips-bar" style="">'
						+'<div class="pack"><i style="width: 0%"><div class="num">0%</div></i></div>'
						+'<span class="tips">正在校验表:</span>'
						+'</div>'
						+'</div>'
				})
				var ii = setInterval(function(){
					$.call('hospital_common.guide.verify.getBar',{},function(rtn){
						if(rtn && rtn.verify_thread1){
							if(rtn.data1_bar){
								i = rtn.data1_bar
							}
							if(rtn.data1){
								$('.bar-box .progress-tips-bar .tips').text('正在校验表: ' + rtn.data1)
							}
						}else{
							i = 100;
						}
						$('.bar-box .progress-tips-bar .pack i').css('width',i+'%')
						$('.bar-box .progress-tips-bar .pack .num').text(i+'%')
						if(i >= 100){
							query()
							clearInterval(ii);
							layer.close(index)
						}
					},null,{nomask:true})
				},1000)
			}
		})
		
		$(window).resize(function(){
			var wid = $(window).height() - 
			240 - $("div[ctype='form']").parent().parent().height();
			if(wid <= 250){
				wid = 250
			}
			$('.dataTables_scrollBody').css('height',wid);
		}).resize();
		
		$("i.showhide").on("click", function(){
			setTimeout(function(){
				var wid = $(window).height() - 
				240 - $("div[ctype='form']").parent().parent().height();
				if(wid <= 250){
					wid = 250
				}
				$('.dataTables_scrollBody').css('height',wid);
			},500)
		})
		query();
		
	});

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
	
	function query() {
		var data = $('#dataform').getData();
		data.master_bh = 1;
		$("#datatable").params(data);
		$("#datatable").refresh();
		getState();
	}
	
	function start(){
		$.call('hospital_common.guide.verify.queryQuestion',{master_bh:1,is_deal:0},function(rtn){
			if(rtn && rtn.count > 0){
				$.message("处理完之前校验所有问题后，才能进行校验！");
			}else{
				var i = 0;
				var index = layer.open({
					type: 1, 
					shade: 0.3,
					closeBtn: 0,
					move: false,
					//time:2000,
					area:["60%","60%"],
				  	content: '<div class="bar-box" style="">'
						+'<div class="progress-tips-bar" style="">'
						+'<div class="pack"><i style="width: 0%"><div class="num">0%</div></i></div>'
						+'<span class="tips">正在校验表:</span>'
						+'</div>'
						+'</div>'
				})
				$.call('hospital_common.guide.flow.execVerify',{master_bh:"1"},function(rtn){
					var ii = setInterval(function(){
						$.call('hospital_common.guide.verify.getBar',{},function(rtn){
							if(rtn && rtn.verify_thread1){
								if(rtn.data1_bar){
									i = rtn.data1_bar
								}
								if(rtn.data1){
									$('.bar-box .progress-tips-bar .tips').text('正在校验表: ' + rtn.data1)
								}
							}else{
								i = 100;
							}
							$('.bar-box .progress-tips-bar .pack i').css('width',i+'%')
							$('.bar-box .progress-tips-bar .pack .num').text(i+'%')
							if(i >= 100){
								query()
								clearInterval(ii);
								layer.close(index)
							}
						},null,{nomask:true})
					},1000)
				},null,{nomask:true})
			}
		})
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
	
	function getState(){
		$.call('hospital_common.guide.verify.getState',{master_bh:1},function(rtn){
			if(rtn == 0){
				$('.caption').text('请进行校验');
				$('.caption').css('color','red')
			}else if(rtn == 1){
				$('.caption').text('未处理完毕校验不通过问题');
				$('.caption').css('color','red')
			}else if(rtn == 2){
				$('.caption').text('问题处理完毕，可以完成');
				$('.caption').css('color','green')
			}
		})
	}
</script>