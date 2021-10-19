<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="患者费用数据查看" disloggedin="true">
<style type="text/css">
	.main{
		    padding: 0px;
	}
	div.dataTables_length label {
   		display: none;
	}
	
	.portlet {
   		margin: 0px !important;
	}
	
	.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
    padding: 3px !important;
	}
	
	.showhide-div {
    	margin-top: 0px !important;
	}
	
	.tri-tab{
		margin-bottom: 10px;
		border: 2px #cfe0e0 solid; 
	}
	
	#tab2-div{
		display: none;
	}
	
	#data1-table_processing{
		display: none !important;
	}
	
	#mleft{
		width: calc(100% - 200px);
		float: left;
	}
	
	#mright{
	    display: block;
		background: white;
	    height: 100%;
	    width: 200px;
	    float: right;
	    position: absolute;
	    right: 0px;
	    top: 0px;
	    border-left: 2px solid #f2f2f2;
	}
	
	#data-tabdiv{
		height: 400px;
	}
	
	.dataTables_scrollBody{
		/* height: 370px !important; */
	}

	.caption{
		color: #986518 !important;
	}
	.ccl{
		background: #cfe0e0 !important;
	}
	.haszzf{
		color: #ec6d00;
		background: #f0e4f7 !important;
	}
	
	.wk .left-content ul {
		list-style: none;
		margin: 0px auto;
		height: 100%;
		overflow: auto;
	}
	#mright ul{
		cursor: pointer;
	}
	#mright ul li {
		line-height: 40px;
		padding: 0 10px;
		font-size: 16px;
		list-style: none;
	}
	
	#mright ul li:hover {
		background-color: #ededed;
	}
	
	#mright ul .li-active {
		background-color: #ededed;
	}
	
	#mright ul li b {
		color: #009688;
	}
	#tit a{
		cursor: pointer;
	}
</style>
	<s:row>
			<s:form id="form" fclass="portlet light bordered" collapsed="true" extendable="true">
				<s:row>
					<s:datefield label="计费时间" autocomplete="off" name="s_time" format="yyyy-MM-dd" cols="1"></s:datefield>
					<s:datefield label="至" autocomplete="off" name="e_time" format="yyyy-MM-dd" cols="1"></s:datefield>
					<s:checkbox name="onlg_sf" onclick="onlgSF(this);">
						<s:option value="1" label="只显示转自费项目"></s:option>
					</s:checkbox>
				</s:row>
				<s:row>
					<s:textfield label="关键字" placeholder="请输入项目名称或编码" name="key_word" cols="2"></s:textfield>
					<s:button label="搜索" icon="fa fa-search" onclick="query()"></s:button>
				</s:row>
			</s:form>
	</s:row>
	<s:row>
		<div id="data-tabdiv">
			<div id="mleft">
				<s:table autoload="false" fitwidth="false" label="费用信息" action="hospital_imic.simic.queryItemCost" id="data-table" limit="-1">
					<s:toolbar>
						<span id="tit" style="font-size: 16px">
							已选择[手术费用]
							<a style="font-size: 16px" onclick="tri_type(this);">
								<i class="fa fa-tasks"></i>隐藏费用分类
							</a>
						</span>
					</s:toolbar>
					<s:table.fields>
						<s:table.field name="x" label=" " datatype="script" width="20px">
							return '<a class="a-am" onclick="opendtl(this,\''+record.item_code+'\');" data-sw="0" style="font-size: 18px;font-weight: 600;">+</a>'
						</s:table.field>
						<s:table.field name="level_name" label="费用等级" width="60px"></s:table.field>
						<%-- <s:table.field name="zf_state" label="自费状态" width="60px" datatype="script">
							var zf_state = record.zf_state;
							if(zf_state && parseInt(zf_state) > 0){
								return '<font style="color: #ec6d00">已转自费</font>';
							}
							return '';
						</s:table.field> --%>
						<s:table.field name="item_class" label="类别" width="70px"></s:table.field>
						<s:table.field name="item_code" label="目录编码" width="100px"></s:table.field>
						<s:table.field name="item_name" label="项目名称" width="180px"></s:table.field>
						<s:table.field name="all2sf" label="操作" width="60px" datatype="script">
							var all_sf = record.all_zzf_button;
							var html = [];
							if(all_sf == 1){
								html.push('<a onclick="all_item2sf(\''+ record.item_code +'\');">全转自费</a>');
							}
							return html.join('');
						</s:table.field>
						<s:table.field name="item_spec" label="规格" width="80px"></s:table.field>
						<s:table.field name="units" label="单位" width="60px"></s:table.field>
						<s:table.field name="amount" label="数量" datatype="script" width="80px">
							var show = parseFloat(record.amount);
							if(record.zzf_amount > 0){
								show += ' <font style="color: #ce4a2c;" title="转：转自费数量">(转' + parseFloat(record.zzf_amount) + ')</font>'
							}
							return show;
						</s:table.field>
						<s:table.field name="costs" label="计价金额" datatype="script" width="100px">
							var show = parseFloat(record.costs);
							if(record.zzf_costs > 0){
								show += ' <span class="zftag" style="color: #ce4a2c;" title="转：转自费金额">(转' + parseFloat(record.costs) + ')</span>'
							}
							return show;
						</s:table.field>
						<s:table.field name="q" label="应交金额" width="60px"></s:table.field>
						<s:table.field name="限价" label="限价" datatype="script" width="50px">
							return parseFloat(record.限价);
						</s:table.field>
						<s:table.field name="insur_scale" label="自付比例" width="60px" datatype="script" >
							return parseFloat(record.insur_scale);
						</s:table.field>
						<s:table.field name="自付金额" label="自付金额" datatype="script" width="60px">
							return parseFloat(record.自付金额);
						</s:table.field>
						<s:table.field name="自费金额" label="自费金额" datatype="script" width="60px">
							return parseFloat(record.自费金额);
						</s:table.field>
					</s:table.fields>
				</s:table>
			</div>
			
			<div id="mright">
				<ul>
					<!-- <li>手术 10%</li>
					<li>耗材 100%</li>
					<li>抗菌药物 78%</li> -->
				</ul>
			</div>
			
		</div>
	</s:row>
	
<script>
	var pgo = {};
	pgo.onlg_sf = 0;
	
	//++增加表格高度自适应
	$(window).resize(function() {
		setTimeout(function(){
			$('.dataTables_scrollBody').css('height',$(window).height() - 160);
		},100)
	})
	$(window).resize();
	
	$(function(){
		query();
		//$.call('hospital_imic.simic.queryItemDtlCount', {}, function(){});
		//setTimeout("$('.caption').append('[计价金额总计：11111；应交金额总计：1111；自付金额总计：121233；自费金额总计：sadsad]');", 300);
	});
	
	function ref_count(){
		var data = {};
		data.patient_id = '${param.patient_id}';
		data.visit_id = '${param.visit_id}';
		$.call('hospital_imic.simic.bill_dtl', data, function(ret){
			console.log(ret);
			var txt = "[计价金额总计：" + ret.cot.costs + "；自付金额总计：" + ret.cot.自付金额 + "；自费金额总计：" + ret.cot.自费金额 + "]";
			$('.caption').text(txt);
		});
	}
	
	function query(){
		var data = $('#form').getData();
		data.patient_id = '${param.patient_id}';
		data.visit_id = '${param.visit_id}';
		data.onlg_sf = pgo.onlg_sf;
		//console.log(data);
		$("#data-table").params(data);
		$("#data-table").refresh();
		setTimeout(function(){
			if($('.zftag').length > 0){
				$('.zftag').parent('td').parent('tr').addClass('haszzf');
			}else{
				setTimeout(function(){
					$('.zftag').parent('td').parent('tr').addClass('haszzf');
				},1000)
			}
		},300)
	}
	
	function opendtl(_this, item_code){
		var dataq = $('#form').getData();
		$('.tri-tr').remove();
		dataq.patient_id = '${param.patient_id}';
		dataq.visit_id = '${param.visit_id}';
		dataq.item_code = item_code;
		var tab_html = [];
		tab_html.push('<tr class="tri-tr"><td colspan="14"><div class="tri-tab">');
		$.call('hospital_imic.simic.queryItemCostDtl', dataq, function(rtn){
			if(rtn.resultset && rtn.resultset.length >0){
				tab_html.push('<table class="table table-striped table-bordered table-hover table-nowrap dataTable no-footer">');
				tab_html.push('<thead><tr><th>计价时间</th><th>操作</th><th>自费状态</th><th>开单科室</th>');
				tab_html.push('<th>开单医生</th><th>数量</th><th>计价金额</th>');
				tab_html.push('<th>应交金额</th><th>限价</th><th>自付比例</th>');
				tab_html.push('<th>自付金额</th><th>自费金额</th></tr></thead>');
				tab_html.push('<tbody>');
				var rarr = rtn.resultset;
				for(var i =0; i<rarr.length; i++){
					tab_html.push('<tr>');
					tab_html.push('<td width="120px">'+(rarr[i].billing_date_time)+'</td>');
					var zf_state = rarr[i].zf_state;
					if(zf_state && parseInt(zf_state) > 0){
						tab_html.push('<td width="60px"></td>');
					}else{
						tab_html.push('<td width="60px"><a onclick="toSF(\''+rarr[i].p_key+'\',\''
								+rarr[i].item_code+'\', \''+rarr[i].group_id
								+'\', \''+rarr[i].order_no+'\', \''+rarr[i].order_sub_no+'\', \''
								+item_code+'\');">转为自费</a></td>');
					}
					if(zf_state && parseInt(zf_state) > 0){
						tab_html.push('<td width="60px" style="color: #ec6d00">已转自费</td>');
					}else{
						tab_html.push('<td width="60px"></td>');
					}
					tab_html.push('<td width="100px">'+(rarr[i].dept_name ==null?"": rarr[i].dept_name)+'</td>');
					tab_html.push('<td width="60px">'+(rarr[i].doctor == null?"": rarr[i].doctor)+'</td>');
					tab_html.push('<td width="60px">'+(rarr[i].amount ==null?"": parseFloat(rarr[i].amount))+'</td>');
					tab_html.push('<td width="60px">'+(rarr[i].costs == null?"": parseFloat(rarr[i].costs))+'</td>');
					tab_html.push('<td width="60px"></td>');
					tab_html.push('<td width="50px">'+(rarr[i].限价 == null? "": parseFloat(rarr[i].限价))+'</td>');
					tab_html.push('<td width="60px">'+(rarr[i].insur_scale == null?"": parseFloat(rarr[i].insur_scale))+'</td>');
					tab_html.push('<td width="60px">'+(rarr[i].自付金额 == null?"": parseFloat(rarr[i].自付金额))+'</td>');
					tab_html.push('<td width="60px">'+(rarr[i].自费金额 == null?"": parseFloat(rarr[i].自费金额))+'</td>');
					tab_html.push('</tr>');
				}
				tab_html.push('</tbody>');
			}else{
				tab_html.push('没有查询到数据！');
			}
		},function(e){}, {async: false, remark: false});
		
		//var tab_html = $('#tab2-div').html();
		tab_html.push('</div></td></tr>')
		//console.log(tab_html);
		$(_this).parents('tr').addClass('ccl').siblings().removeClass('ccl');
		if($(_this).data('sw') == 0){
			$(_this).parents('tr').after(tab_html.join(''));
			$('.a-am').data('sw', '0').text('+');
			$(_this).data('sw', '1').text('-');
		}else{
			$(_this).data('sw', '0').text('+');
		}
	}
	
	function toSF(p_key, item_code, group_id, order_no, order_sub_no, item_code_main){
		if('${imic_can_toselfpaid}' != '1'){
			$.message('系统关闭转自费操作，请在HIS系统中操作转自费！');
			return;
		}
		// 转自费操作
		var data = {};
		data.p_key = p_key;
		data.item_code = item_code;
		data.group_id = group_id;
		data.order_no = order_no;
		data.order_sub_no = order_sub_no;
		data.imic_audit_id = '${param.imic_audit_id}';
		$.call('hospital_imic.selfpaid.recordBill2SF', data, function(rtn){
			refDetail(item_code_main);
			ref_count();
		},function(e){}, {async: false, remark: false});
	}
	
	function refDetail(item_code){
		var dataq = $('#form').getData();
		$('.tri-tr').empty();
		dataq.patient_id = '${param.patient_id}';
		dataq.visit_id = '${param.visit_id}';
		dataq.item_code = item_code;
		var tab_html = [];
		tab_html.push('<td></td><td colspan="13"><div class="tri-tab">');
		$.call('hospital_imic.simic.queryItemCostDtl', dataq, function(rtn){
			if(rtn.resultset && rtn.resultset.length >0){
				tab_html.push('<table class="table table-striped table-bordered table-hover table-nowrap dataTable no-footer">');
				tab_html.push('<thead><tr><th>计价时间</th><th>操作</th><th>自费状态</th><th>开单科室</th>');
				tab_html.push('<th>开单医生</th><th>数量</th><th>计价金额</th>');
				tab_html.push('<th>应交金额</th><th>限价</th><th>自付比例</th>');
				tab_html.push('<th>自付金额</th><th>自费金额</th></tr></thead>');
				tab_html.push('<tbody>');
				var rarr = rtn.resultset;
				for(var i =0; i<rarr.length; i++){
					tab_html.push('<tr>');
					tab_html.push('<td width="120px">'+(rarr[i].billing_date_time)+'</td>');
					var zf_state = rarr[i].zf_state;
					if(zf_state && parseInt(zf_state) > 0){
						tab_html.push('<td width="60px"></td>');
					}else{
						tab_html.push('<td width="60px"><a onclick="toSF(\''+rarr[i].p_key+'\',\''+rarr[i].item_code
								+'\', \''+rarr[i].group_id+'\', \''+rarr[i].order_no+'\', \''
								+rarr[i].order_sub_no+'\', \''+item_code+'\');">转为自费</a></td>');
					}
					if(zf_state && parseInt(zf_state) > 0){
						tab_html.push('<td width="60px" style="color: #ec6d00">已转自费</td>');
					}else{
						tab_html.push('<td width="60px"></td>');
					}
					tab_html.push('<td width="100px">'+(rarr[i].dept_name ==null?"": rarr[i].dept_name)+'</td>');
					tab_html.push('<td width="50px">'+(rarr[i].doctor == null?"": rarr[i].doctor)+'</td>');
					tab_html.push('<td width="40px">'+(rarr[i].amount ==null?"": parseFloat(rarr[i].amount))+'</td>');
					tab_html.push('<td width="40px">'+(rarr[i].costs == null?"": parseFloat(rarr[i].costs))+'</td>');
					tab_html.push('<td width="40px"></td>');
					tab_html.push('<td width="40px">'+(rarr[i].限价 == null? "": parseFloat(rarr[i].限价))+'</td>');
					tab_html.push('<td width="30px">'+(rarr[i].insur_scale == null?"": parseFloat(rarr[i].insur_scale))+'</td>');
					tab_html.push('<td width="40px">'+(rarr[i].自付金额 == null?"": parseFloat(rarr[i].自付金额))+'</td>');
					tab_html.push('<td width="40px">'+(rarr[i].自费金额 == null?"": parseFloat(rarr[i].自费金额))+'</td>');
					tab_html.push('</tr>');
				}
				tab_html.push('</tbody>');
			}else{
				tab_html.push('没有查询到数据！');
			}
		},function(e){}, {async: false, remark: false});
		tab_html.push('</div></td>');
		$('.tri-tr').append(tab_html.join(''));
	};
	
	function onlgSF(this_){
		var checks = $(this_).getData();
		if(checks.onlg_sf[0]){
			pgo.onlg_sf = checks.onlg_sf[0];
		}else{
			pgo.onlg_sf = 0;
		}
		query();
	}
	
	// 项目全转自费
	function all_item2sf(item_code){
		$.confirm("确认将该项目的所有费用都转为自费支付吗?",function callback(e){
			if(e==true){
				var data = {
					imic_audit_id: '${param.imic_audit_id}',
					item_code: item_code,
					type: 0
				};
				$.call('hospital_imic.selfpaid.all_item2SF', data , function(ret){
					query();
					ref_count();
				});
			}
		});
	}
	
	var lefth = $('#mleft').width();
	function tri_type(_this){
		var disp = $('#mright').css('display');
		if(disp == 'none'){
			//$('#mright').css('display', 'block');
			$('#mright').fadeIn()
			$('#mleft').animate({'width': lefth + 'px'});
			$(_this).html('<i class="fa fa-tasks"></i>隐藏费用分类');
		}else{
			//$('#mright').css('display', 'none');
			$('#mright').fadeOut()
			$('#mleft').animate({'width': '100%'});
			$(_this).html('<i class="fa fa-tasks"></i>显示费用分类');
		}
		
	}
</script>
</s:page>