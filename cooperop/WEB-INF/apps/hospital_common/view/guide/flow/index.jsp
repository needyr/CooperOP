<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="实施向导" >
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<div class="page-wrap">
	<div class="tab-progress column-default">
		
			<div class="progress-bar-s">
				<ul>
					<c:forEach var="flow" items="${$return.flows}">
						<c:if test="${empty flow.parent_id}">
							<li><a class="${flow.state eq '0'?'':flow.state eq '1'?'active':'complete'}" 
							herf="#" data-address="${flow.address}" data-state="${flow.state}"
							data-parent_id="${flow.parent_id}"
							data-id="${flow.id}" title="${flow.name}">${flow.name}</a></li>
						</c:if>
					</c:forEach>
					<!-- <li><a class="active" href="#" title="初始化">1.初始化</a></li>
					<li><a  class="complete" href="#" title="系统参数配置">2.系统参数配置</a></li>
					<li><a href="#" title="数据采集">3.数据采集</a></li>
					<li><a href="#" title="数据校验">4.数据校验</a></li>
					<li><a href="#" title="配对数据">5.配对数据</a></li>
					<li><a href="#" title="人员组织导入">6.人员组织导入</a></li>
					<li><a href="#" title="事后审查">7.事后审查</a></li>
					<li><a href="#" title="备份数据程序配置">8.备份数据程序配置</a></li> -->
				</ul>
			</div>
			<div class="btn-bar">
			<a href="#" onclick="log();"><i class="icon-book-open"></i>流程日志</a>
			<a href="#" onclick="log2();"><i class="icon-notebook"></i>数据校验日志</a>
			</div>
	</div>
	<div class="tab-progress-child column-default">
		<div class="progress-bar-s">
			<ul>
				<!-- <li><a class="active" href="#" titl e="数据库还原">1.数据库还原</a></li>
				<li><a href="#" title="初始化开始">2.初始化开始</a></li> -->
			</ul>
		</div>	
	</div>
	<iframe id="ifr" src="/w/hospital_common/guide/flow/welcome.html" class="column-default" style="min-height: 300px;width: 100%;border: 2px solid gainsboro;
    box-shadow: 0 2px 8px rgba(0,0,0,0.05);">

	</iframe>
	<div class="btn-bottom"><a class="syb" href="#"><i class="cicon icon-back"></i>上一步</a><a class="blueBtn" href="#">下一步<i class="cicon icon-go"></i></a>
	</div>
	<div class="btn-bottom2">
    <a href="#" class="" style="" onclick="resetWizard()">重置向导</a><a style="" class="" href="#" onclick="deleteLog()">清空日志</a>
    </div>
	<div class="btn-bottom3">
		<a href="#" class="" style="background: #0a6aa1;color: aliceblue;" onclick="rbf()">注意事项</a>
	</div>
</div>
</s:page>
<script type="text/javascript">
	var cur_node;
	var cur_node_child;
	$(function(){
		$(window).resize(function(){
			$('#ifr').css('height',$(window).height() - $('.tab-progress').height() 
					- $('.tab-progress-child').height() - $('.btn-bottom').height() - 150);
		}).resize();
		
		$('.syb').click(function(){
			if(cur_node_child && cur_node_child.length > 0 && cur_node_child.parent().prev().find('a').length > 0){
				cur_node_child.parent().prev().find('a').addClass('change').parent().siblings().find('a').removeClass('change');
				change_ifr(cur_node_child.parent().prev().find('a').attr('data-address'),cur_node_child.parent().prev().find('a').attr('data-id'))
				cur_node_child = cur_node_child.parent().prev().find('a');
				finish(cur_node_child)
			}
			else if(cur_node && cur_node.length > 0 && cur_node.parent().prev().find('a').length > 0){
				cur_node.parent().prev().find('a').addClass('change').parent().siblings().find('a').removeClass('change');
				tz(cur_node.parent().prev().find('a'),'s')
				cur_node = cur_node.parent().prev().find('a');
				finish(cur_node)
			}
		})
		
		$('.blueBtn').click(function(){
			if(cur_node_child && cur_node_child.length > 0 && cur_node_child.parent().next().find('a').length > 0){
				if(is_next(cur_node_child.parent().next().find('a').attr('data-id'))){
					cur_block(cur_node_child.parent().next().find('a'))
					change_ifr(cur_node_child.parent().next().find('a').attr('data-address'),cur_node_child.parent().next().find('a').attr('data-id'))
					cur_node_child = cur_node_child.parent().next().find('a');
				}
			}else if(cur_node && cur_node.length >0){
				if(cur_node.parent().next().find('a').length > 0 
						&& ((cur_node_child && is_next(cur_node_child.attr('data-id'))) || !cur_node_child) 
						&& is_next(cur_node.parent().next().find('a').attr('data-id'))){
					cur_block(cur_node.parent().next().find('a'))
					cur_node = cur_node.parent().next().find('a');
					tz(cur_node,'n');
				}
			}else{
				cur_node = $('.tab-progress .progress-bar-s ul li').eq(0).find('a');
				cur_block(cur_node)
				tz(cur_node,'n');
			}
		})
		
		$('.tab-progress .progress-bar-s ul li a').click(function(){
			var rtn;
			$.call("hospital_common.guide.flow.hasPermit",{id:$(this).attr('data-id')},function(rtns){
				rtn = rtns
			},null,{async:false})
			if(rtn && rtn == 'pass'){
				if($(this).attr('class') && $(this).attr('class') != ''){
					$('.btn-bottom .finish').remove();
					cur_node_child = null;
					cur_node = $(this);
					cur_block(cur_node)
					tz($(this),'n');
				}
			}
		});
		
		$('.tab-progress .progress-bar-s ul li a[data-state="1"]').click();
	});

	function rbf(){
		$.modal("/rm/lookTemp/hospital_common/实施向导注意事项-2021.01.28.pdf","实施向导注意事项",{
			width:"90%",
			height:"90%",
			callback : function(e){
				if(e){

				}
			}
		});
	}
	
	function ref_next(){
		$.call('hospital_common.guide.flow.insertLog',{info:"实施流程正式开始"},function(rtn){
			$('.blueBtn').click();
		},null,{nomask:true})
	}
	
	function tz(_this,sn){
		$.call("hospital_common.guide.flow.querychild",{parent_id:$(_this).attr('data-id')},function(rtn){
			if(rtn){
				$('.tab-progress-child .progress-bar-s ul').empty();
				for(var i = 0;i<rtn.length;i++){
					var html = '';
					html += '<li><a class="'+(rtn[i].state == 0?'':rtn[i].state == 1?'active':'complete')+'" href="#" ';
					html += 'data-address="'+rtn[i].address+'" data-parent_id="'+rtn[i].parent_id+'" data-id="'+rtn[i].id+'" data-state="'+rtn[i].state+'"';
					html += 'title="'+rtn[i].name+'">'+rtn[i].name+'</a></li>';
					$('.tab-progress-child .progress-bar-s ul').append(html);
				}
				$('.tab-progress-child .progress-bar-s ul li a').click(function(){
					var data;
					$.call("hospital_common.guide.flow.hasPermit",{id:$(this).attr('data-id')},function(datas){
						data = datas
					},null,{async:false})
					if(data && data == 'pass' && $(this).attr('class') && $(this).attr('class') != ''){
						cur_block($(this))
						change_ifr($(this).attr('data-address'),$(this).attr('data-id'))
						cur_node_child = $(this);
						finish($(this))
					}
				})
				//console.log(sn)
				if($('.tab-progress-child .progress-bar-s ul li a[data-state="1"]').length > 0){
					$('.tab-progress-child .progress-bar-s ul li a[data-state="1"]').click()
				}else if($(_this).attr('data-state') == '1'){
					if(is_next($('.tab-progress-child .progress-bar-s ul li a:eq(0)').attr('data-id'))){
						$.call('hospital_common.guide.flow.updateOneActive',{
							id:$('.tab-progress-child .progress-bar-s ul li a:eq(0)').attr('data-id')},
							function(rtn){},null,{async:false})
						cur_block($('.tab-progress-child .progress-bar-s ul li a:eq(0)'))
						$('.tab-progress-child .progress-bar-s ul li a:eq(0)').click()
					}
				}else if($(_this).attr('data-state') == '2' && sn == 's'){
					$.call('hospital_common.guide.flow.updateOneActive',{
								id:$('.tab-progress-child .progress-bar-s ul li a:last-child').attr('data-id')},
							function(rtn){},null,{async:false})
					cur_block($('.tab-progress-child .progress-bar-s ul li a:last-child'))
					$('.tab-progress-child .progress-bar-s ul li a:last-child').click()
				}else if($(_this).attr('data-state') == '2' && sn == 'n'){
					$.call('hospital_common.guide.flow.updateOneActive',{
								id:$('.tab-progress-child .progress-bar-s ul li a:eq(0)').attr('data-id')},
							function(rtn){},null,{async:false})
					cur_block($('.tab-progress-child .progress-bar-s ul li a:eq(0)'))
					$('.tab-progress-child .progress-bar-s ul li a:eq(0)').click()
				}
			}
		},null,{nomask:true})
	}
	
	function change_ifr(address,flow_id){
		//日志插入,信息填写操作
		$.call('hospital_common.guide.flow.insertLog',{flow_id:flow_id,info:"进入流程页面"},function(rtn){
			$('#ifr').attr('src','/w/'+address.replace(/\./g,'/')+'.html?id='+flow_id);
		},null,{nomask:true})
	}

	(function ($) {
		$.fn.extend({
			insertAtCaret : function (myValue) {
				var $t = $(this)[0];
				if (document.selection) {
					this.focus();
					var sel = document.selection.createRange();
					sel.text = myValue;
					this.focus();
				} else
				if ($t.selectionStart || $t.selectionStart == '0') {
					var startPos = $t.selectionStart;
					var endPos = $t.selectionEnd;
					var scrollTop = $t.scrollTop;
					$t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
					this.focus();
					$t.selectionStart = startPos + myValue.length;
					$t.selectionEnd = startPos + myValue.length;
					$t.scrollTop = scrollTop;
				} else {
					this.value += myValue;
					this.focus();
				}
			}
		});
	})(jQuery);
	
	function cur_block(_this){
		if($(_this).attr('data-state') != '2'){
			$(_this).addClass('active').parent().siblings().find('a.active').addClass('complete');	
			$(_this).addClass('active').parent().siblings().find('a').removeClass('active');	
			$(_this).attr('data-state','1').parent().siblings().find('a[data-state="1"]').attr('data-state','2')
		}
		$(_this).addClass('change').parent().siblings().find('a').removeClass('change');
		finish($(_this))
	}
	
	function is_next(id){
		var is_next = false;
		$.call('hospital_common.guide.flow.is_next',{id:id},function(rtn){
			if(rtn == true){
				is_next = true; 
			}
		},null,{async:false})
		return is_next;
	}
	
	function finish(_this){
		$('.btn-bottom .finish').remove();
		if($(_this).attr('data-state') == 1 && $(_this).attr('data-parent_id')){
			var html2 = '<a class="finish" style="background-color: #d27d00;" href="#"><i class="cicon icon-check"></i>完成</a>';
			var _child_this = _this;
			$('.btn-bottom').prepend(html2);
			$('.finish').click(function(){
				layer.open({
				  type: 1, 
				  title: '填写信息',
				  area: ['400px', '300px'],
				  btn: ['确定', '取消'],
				  skin: 'layui-layer-lan',
			      closeBtn: 0,
				  content:'<div class="control-content">'
					  +'<textarea style="height: 170px;margin-top:20px" class="form-control" type="textarea" cols="4" maxlength="2000" id="finish_text"></textarea>'
					  +'</div>', //这里content是一个普通的String
				  success: function(layero, index){
					layero.find('#finish_text').keydown(function(event){
						if(event.keyCode==13){
							layero.find('#finish_text').insertAtCaret('\n');
							return false;
						}
					})
				  },
				  yes: function(index, layero){
					  if(layero.find('#finish_text').val().length < 10){
						  $.message('至少填写10个字符');
					  }else{
						  $.call("hospital_common.guide.flow.finish",{id:$(_child_this).attr('data-id'),parent_id:$(_child_this).attr('data-parent_id')},function(rtn){
							  if(rtn && rtn.state){
								  $.call('hospital_common.guide.flow.insertLog',{flow_id:$(_child_this).attr('data-id'),info:"完成流程", remark:layero.find('#finish_text').val()},function(rtn){
									  $(_child_this).attr('data-state','2');
									  $(_child_this).removeClass('active');
									  $(_child_this).addClass('complete');
									  $('.blueBtn').click();
									  $.call('hospital_common.guide.flow.welcome',{},function(rtn){
										  if(rtn && rtn.is_finish == true){
										  	$('#ifr').attr('src','/w/hospital_common/guide/flow/welcome.html');
										  	$('.tab-progress .progress-bar-s ul li a[data-state="1"]').addClass('complete').removeClass('active')
										  	$.call('hospital_common.guide.flow.insertLog',{info:"实施流程结束"},function(rtn){
											},null,{async:false})
										  }
									  },null,{async:false})
									  layer.close(index);
								  },null,{async:false})
							  }else{
								  var ss = '';
								  if(rtn.verify){
									  $.message('还存在校验问题未更改或未进行校验!!');
								  }else{
									  for(var i=0;i<rtn.re.length;i++){
										  ss += '[' + rtn.re[i].mapcn + ']<br>'
									  }
									  $.message('还存在：<br>'+ss+'未配对! 请完成配对!');
								  }
								  layer.close(index);
							  }
						  },null,{nomask:true})
					  }
				  }
				});
			})
		}else if($(_this).attr('data-state') == 2 && $(_this).attr('data-parent_id')){
			var html2 = '<a class="finish" style="background-color: #d27d00;" href="#"><i class="cicon icon-check"></i>增加/查看完成记录</a>';
			var _child_this = _this;
			$('.btn-bottom').prepend(html2);
			$('.finish').click(function(){
				$.call('hospital_common.guide.flow.getFinishLog',{id:$(_this).attr('data-id')},function (rtn) {
					var log_text = '完成日志：\n';
					if(rtn){
						for(var i=0;i<rtn.length;i++){
							log_text += '    '+rtn[i].create_time+'\n'+rtn[i].remark + '\n';
						}
					}
					layer.open({
						type: 1,
						title: '填写信息',
						area: ['800px', '300px'],
						btn: ['确定', '取消'],
						skin: 'layui-layer-lan',
						closeBtn: 0,
						content:'<div class="control-content">'
								+'<textarea style="height: 170px;margin-top:20px;width: 50%;" disabled type="textarea" cols="4" maxlength="2000" id="log"></textarea>'
								+'<textarea style="height: 170px;margin-top:20px;width: 50%;" type="textarea" cols="4" maxlength="2000" id="finish_text"></textarea>'
								+'</div>', //这里content是一个普通的String
						success: function(layero, index){
							layero.find('#finish_text').keydown(function(event){
								if(event.keyCode==13){
									layero.find('#finish_text').insertAtCaret('\n');
									return false;
								}
							})
							layero.find('#log').val(log_text);
						},
						yes: function(index, layero){
							$.call('hospital_common.guide.flow.insertLog',{flow_id:$(_child_this).attr('data-id'),info:"完成流程", remark:layero.find('#finish_text').val()},function(rtn){
								layer.close(index);
							})
						}
					});
				})
			})
		}
	}

	function log(){
		$.modal("/w/hospital_common/guide/flow/flow_log_tz.html","流程日志",{
	        width:"40%",
	        height:"50%",
	        callback : function(e){
	        }
		});
	}
	
	function log2(){
		//TODO 等待加入具体地址
		var page = "hospital_common.guide.verify.log";
		$.openTabPage(page,'数据校验日志',page,true,null,null)
	}
	
	
	function resetWizard(){
		$.confirm("是否确认重置向导？",function callback(e){
			if(e==true){
				$.call("hospital_common.guide.verify.reset",{},function (rtn){
					document.location.reload();
				})	
			}
		})
	}
	
	function deleteLog(){
		$.confirm("是否确认清空所有日志？",function callback(e){
			if(e==true){
				$.call("hospital_common.guide.verify.deletelog",{},function (rtn){
					document.location.reload();
				})	
			}
		})
	}
</script>