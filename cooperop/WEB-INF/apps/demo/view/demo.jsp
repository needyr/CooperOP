<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="测试页面" disloggedin="true" flag="" schemei图片组件d="" type="" ismodal="" modalheight="" modalwidth="">
<script src="/theme/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<input type="hidden" name="djlx" value="600"/>
	<s:toolbar>
		<s:button label="保存" icon="fa fa-save" action="x41" ></s:button>
		<s:button label="提交" icon="fa fa-check" action="x41"></s:button>
	</s:toolbar>
	<s:row>
		<s:tabpanel>
			<s:form label="页面一" active="true">
				<s:toolbar>
					<s:button label="Profile1" icon="fa fa-user" action="x41"></s:button>
					<s:button label="Settings1" icon="fa fa-cogs" action="x41"></s:button>
					<s:button label="登记指纹" onclick="regf()"></s:button>
				</s:toolbar>
				<div class="form-group">
					<label class="control-label">TextLeft AlignedLeft AlignedLeft AlignedLeft AlignedLeft AlignedLeft AlignedLeft AlignedLeft AlignedLeft AlignedLeft AlignedLeft AlignedLeft AlignedLeft Aligned</label>
					<input type="text" class="form-control" placeholder="Enter text">
					<span class="help-block">
					A block of help text. </span>
				</div>
				<s:row>
					<s:button label="验证指纹" onclick="cP();"></s:button>
				</s:row>
				<s:row>
					<s:image label="图片组件" required="true" cols="2">
				</s:image>
				</s:row>
				<s:row>
					<s:textfield label="wenben" name="1" onmousewheel="return false;" type="number"></s:textfield>
					<s:textfield name="1" islabel="true" value="没有标签"></s:textfield>
				</s:row>
				<s:row>
					<s:autocomplete label="自动补全" name="eautocomplete" action="crdc.product.query" limit="10">
						<s:option value="$[code]" label="$[name]"><span style="float:left;display:block;width:100px;">$[code]</span><span style="color:red;float:left;display:block;width:200px;">$[name]</span><span style="color:red;float:left;display:block;width:200px;">$[popedom_name]</span></s:option>
					</s:autocomplete>
					<s:autocomplete label="自动补全" editable="true" name="dautocomplete" action="crdc.product.query" limit="10" value="asdfasd">
						<s:option value="$[code]" label="$[name]"><span style="float:left;display:block;width:100px;">$[code]</span><span style="color:red;float:left;display:block;width:200px;">$[name]</span><span style="color:red;float:left;display:block;width:200px;">$[popedom_name]</span></s:option>
						<s:option data-code="1" data-name="选项1" data-popedom_name="选项1"></s:option>
						<s:option data-code="2" data-name="选项2" data-popedom_name="选项1"></s:option>
						<s:option data-code="3" data-name="选项3" data-popedom_name="选项1"></s:option>
						<s:option data-code="4" data-name="选项4" data-popedom_name="选项1"></s:option>
						<s:option data-code="5" data-name="选项5" data-popedom_name="选项1"></s:option>
						<s:option data-code="6" data-name="选项6" data-popedom_name="选项1"></s:option>
						<s:option data-code="7" data-name="选项7" data-popedom_name="选项1"></s:option>
						<s:option data-code="8" data-name="选项8" data-popedom_name="选项1"></s:option>
						<s:option data-code="9" data-name="选项9" data-popedom_name="选项1"></s:option>
						<s:option data-code="10" data-name="选项10" data-popedom_name="选项1"></s:option>
						<s:option data-code="11" data-name="选项11" data-popedom_name="选项1"></s:option>
						<s:option data-code="12" data-name="选项12" data-popedom_name="选项1"></s:option>
						<s:option data-code="13" data-name="选项13" data-popedom_name="选项1"></s:option>
						<s:option data-code="14" data-name="选项14" data-popedom_name="选项1"></s:option>
						<s:option data-code="15" data-name="选项15" data-popedom_name="选项1"></s:option>
						<s:option data-code="16" data-name="选项16" data-popedom_name="选项1"></s:option>
						<s:option data-code="17" data-name="选项17" data-popedom_name="选项1"></s:option>
						<s:option data-code="18" data-name="选项18" data-popedom_name="选项1"></s:option>
						<s:option data-code="19" data-name="选项19" data-popedom_name="选项1"></s:option>
						<s:option data-code="20" data-name="选项20" data-popedom_name="选项1"></s:option>
					</s:autocomplete>
					<s:button label="拿值" onclick="console.log($('[name=dautocomplete]').getData());"></s:button>
				</s:row>
				<s:row>
					<s:textfield label="wenben"  name="1" ishidden="true"></s:textfield>
					<s:textfield name="1" islabel="true" value="没有标签" ishidden="true"></s:textfield>
					<s:autocomplete label="自动补全" name="dautocomplete" action="crdc.product.query" limit="10" ishidden="true">
						<s:option value="$[code]" label="$[name]"><span style="float:left;display:block;width:100px;">$[code]</span><span style="color:red;float:left;display:block;width:200px;">$[name]</span><span style="color:red;float:left;display:block;width:200px;">$[popedom_name]</span></s:option>
						<s:option data-code="1" data-name="选项1" data-popedom_name="选项1"></s:option>
						<s:option data-code="2" data-name="选项2" data-popedom_name="选项1"></s:option>
						<s:option data-code="3" data-name="选项3" data-popedom_name="选项1"></s:option>
						<s:option data-code="4" data-name="选项4" data-popedom_name="选项1"></s:option>
						<s:option data-code="5" data-name="选项5" data-popedom_name="选项1"></s:option>
						<s:option data-code="6" data-name="选项6" data-popedom_name="选项1"></s:option>
						<s:option data-code="7" data-name="选项7" data-popedom_name="选项1"></s:option>
						<s:option data-code="8" data-name="选项8" data-popedom_name="选项1"></s:option>
						<s:option data-code="9" data-name="选项9" data-popedom_name="选项1"></s:option>
						<s:option data-code="10" data-name="选项10" data-popedom_name="选项1"></s:option>
						<s:option data-code="11" data-name="选项11" data-popedom_name="选项1"></s:option>
						<s:option data-code="12" data-name="选项12" data-popedom_name="选项1"></s:option>
						<s:option data-code="13" data-name="选项13" data-popedom_name="选项1"></s:option>
						<s:option data-code="14" data-name="选项14" data-popedom_name="选项1"></s:option>
						<s:option data-code="15" data-name="选项15" data-popedom_name="选项1"></s:option>
						<s:option data-code="16" data-name="选项16" data-popedom_name="选项1"></s:option>
						<s:option data-code="17" data-name="选项17" data-popedom_name="选项1"></s:option>
						<s:option data-code="18" data-name="选项18" data-popedom_name="选项1"></s:option>
						<s:option data-code="19" data-name="选项19" data-popedom_name="选项1"></s:option>
						<s:option data-code="20" data-name="选项20" data-popedom_name="选项1"></s:option>
					</s:autocomplete>
				</s:row>
			</s:form>
			<s:table label="第二个页面">
				<s:toolbar>
					<s:button label="Profile2" icon="fa fa-user" action="x41"></s:button>
					<s:button label="Settings2" icon="fa fa-cogs" action="x41"></s:button>
				</s:toolbar>
				<s:table.fields>
					<s:table.field name="1" label="12"></s:table.field>
					<s:table.field name="1" label="13"></s:table.field>
				</s:table.fields>
				<s:table.data>
					<tr>
						<td>12</td>
						<td>3124123</td>
					</tr>
				</s:table.data>
			</s:table>
		</s:tabpanel>
	</s:row>
	<s:row>
		<s:form label="Form Sample" icon="fa fa-gift" collapsed="true" extendable="true">
			<s:toolbar>
				<s:button label="Profile" icon="fa fa-user" action="x41"></s:button>
				<s:button label="Settings" icon="fa fa-cogs" action="x41"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield digits="2" create_action="getv()" encryption="true" modify_action="alert('是否修改')" out_action="" label="文本框" maxlength="5" required="true" placeholder="Chee Kin" cols="2"></s:textfield>
				<s:textfield label="密码框" datatype="password" required="true" 
					placeholder="Chee Kin"></s:textfield>
				<s:select label="下拉选择" required="true" dictionary="deployed">
					<s:option value="1" label="1"></s:option>
					<s:option value="2" label="2"></s:option>
					<s:option value="3" label="3"></s:option>
					<s:option value="4" label="4"></s:option>
				</s:select>
				<s:switch label="逻辑开关" value="是" name="alkf" onvalue="是" offvalue="否" ontext="是1" offtext="否1" ></s:switch>
				<s:button label="拿值" onclick="console.log($('[ctype=switch]').getData());"></s:button>
			</s:row>
			<s:row>
				<s:textfield islabel="true" label="文本框" required="true"
					placeholder="Chee Kin" value="2dfasdf文本框"></s:textfield>
				<s:textfield islabel="true" label="密码框" datatype="password"
					required="true" placeholder="Chee Kin"></s:textfield>
				<s:select islabel="true" label="下拉选择" required="true"
					readonly="readonly">
					<s:option value="1" label="1"></s:option>
					<s:option value="2" label="2"></s:option>
					<s:option value="3" label="3"></s:option>
					<s:option value="4" label="4"></s:option>
				</s:select>
				<s:switch label="12312" readonly="true"></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="文本框" cols="2" readonly="" value="只读文本框"></s:textfield>
				<s:datefield label="日期" datatype="date" format="yyyy" 
					required="true" placeholder="Chee Kin"></s:datefield>
				<s:timefield label="时间" datatype="time" required="true"
					placeholder="Chee Kin"></s:timefield>
			</s:row>
			<s:row>
				<s:textfield islabel="true" label="文本框" cols="2" readonly="readonly"
					value="只读文本框"></s:textfield>
				<s:datefield islabel="true" label="日期" datatype="date"
					format="yyyy/mm/dd-DD" required="true" placeholder="Chee Kin"></s:datefield>
				<s:timefield islabel="true" label="时间" datatype="time"
					required="true" placeholder="Chee Kin"></s:timefield>
			</s:row>
			<s:row>
				<s:textarea label="文本区域" cols="4" placeholder="Chee Kin"
					required="true" rows="1"></s:textarea>
			</s:row>
			<s:row>
				<s:textarea islabel="true" label="文本区域" cols="4"
					placeholder="Chee Kin" required="true" rows="1"></s:textarea>
			</s:row>
			<s:row>
				<s:radio label="单选" name="radio" required="true" cols="2" dictionary="view_type">
					<s:option value="1" label="Radio1"></s:option>
					<s:option value="2" label="Radio2"></s:option>
					<s:option value="3" label="Disabled" disabled="true"></s:option>
				</s:radio>
				<s:checkbox label="复选" name="radio" required="true" cols="2">
					<s:option value="1" label="Radio1"></s:option>
					<s:option value="2" label="Radio2"></s:option>
					<s:option value="3" label="Disabled" disabled="true"></s:option>
				</s:checkbox>
			</s:row>
			<s:row>
				<s:radio islabel="true" label="单选" name="radio" required="true"
					cols="2">
					<s:option value="1" label="Radio1"></s:option>
					<s:option value="2" label="Radio2"></s:option>
					<s:option value="3" label="Disabled" disabled="true"></s:option>
				</s:radio>
				<s:checkbox islabel="true" label="复选" name="radio" required="true"
					cols="2">
					<s:option value="1" label="Radio1"></s:option>
					<s:option value="2" label="Radio2"></s:option>
					<s:option value="3" label="Disabled" disabled="true"></s:option>
				</s:checkbox>
			</s:row>
			<s:row>
				<s:textfield name="tbname" label="双击事件001" placeholder="Chee Kin" dbl_action="zl_select_LCT600_01"></s:textfield>
				<s:textfield name="fdname" label="双击事件002" placeholder="Chee Kin" dbl_action="zl_select_LCT600_02"></s:textfield>
				<s:textfield label="双击事件(图标)" icon="fa fa-weixin" cols="2"
					placeholder="Chee Kin" dbl_action="xsl"></s:textfield>
				<s:button label="这是个表单按钮" icon="fa fa-ellipsis-h" color="red"
					action="x41"></s:button>
			</s:row>
			<s:row>
				<s:textfield islabel="true" label="双击事件003" placeholder="Chee Kin"
					dbl_action="xsl"></s:textfield>
				<s:textfield islabel="true" label="双击事件(图标)" icon="fa fa-weixin"
					cols="2" placeholder="Chee Kin" dbl_action="xsl"></s:textfield>
				<s:button label="这是个表单按钮" icon="fa fa-ellipsis-h" color="red"
					action="x41"></s:button>
			</s:row>
			<s:row>
				<s:richeditor label="富文本编辑器" cols="4" placeholder="Chee Kin"
					required="true" height="200"></s:richeditor>
			</s:row>
			<s:row>
				<s:richeditor islabel="true" label="富文本编辑器" cols="4"
					placeholder="Chee Kin" required="true" height="200"></s:richeditor>
			</s:row>
			<s:row>
				<s:image label="图片组件" required="true" cols="2" >
				</s:image>
				<s:file label="文件组件" required="true" cols="2">
				</s:file>
			</s:row>
			<s:row>
				<s:image islabel="true" label="图片组件" required="true" value="ff7be07a36792cdc6b1d8d52fd8a265d" cols="2">
				</s:image>
				<s:file islabel="true" label="文件组件" value="1d4d0670680cc8a0023dbeb9cb66ca73,5bc8f2b4a5fa01c08f011c97a3b3a3aa" required="true" cols="2">
				</s:file>
			</s:row>
			<s:row>
				<s:button label="1" icon="fa fa-user"
					style="margin-left: 80px;" action="x41"></s:button>
				<s:button label="Profile2" icon="fa fa-user" color="btn-success"
					action="x41" size="" type="link"></s:button>
				<s:button label="Profile3" icon="fa fa-user" color="btn-info"
					action="x41"></s:button>
				<s:button label="Profile4" icon="fa fa-user" color="btn-warning"
					action="x41"></s:button>
				<s:button label="Profile5" icon="fa fa-user" color="btn-danger"
					action="x41"></s:button>
				<s:button label="Profile5" icon="fa fa-user" color="btn-primary"
					action="x41"></s:button>
				<s:button label="Profile6" icon="fa fa-user" color="btn-link"
					action="x41"></s:button>
				<s:button label="Profile6" icon="fa fa-user" color="blue"
					action="x41"></s:button>
				<s:button label="Profile7" icon="fa fa-user" color="red-pink"
					action="x41"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="demotable" label="Table Sample" icon="fa fa-gift" sort="true" limit="10" fitwidth="true" action="" select="single"
		 recordAddtAction="" recordCheckAction="" recordDeleteAction="" recordMoveAction="" recordSubmitAction=""
		 >
			<s:toolbar>
				<s:button label="Profile" icon="fa fa-user" onclick="alert('选择了' + $('#demotable').getSelected().length + '条记录');console.log($('#demotable').getSelected());"></s:button>
				<s:button label="Settings" icon="fa fa-cogs" action="x41"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="cols1" label="Column header 1" sort="true" width="400"
					defaultsort="asc"></s:table.field>
				<s:table.field name="cols2" label="Column header 2" sort="true"
					defaultsort="asc" datatype="template">
					<s:textfield name="tbname" value="$[cols2]"></s:textfield>
				</s:table.field>
				<s:table.field name="cols3" label="Column header 3" sort="true">
				</s:table.field>
				<s:table.field name="cols4" label="Column header 4" sort="true" datatype="template">
					<s:select label="下拉选择" required="true" dictionary="view_type" >
						<s:option value="1" label="1"></s:option>
						<s:option value="2" label="2"></s:option>
						<s:option value="3" label="3"></s:option>
						<s:option value="4" label="4"></s:option>
					</s:select>
				</s:table.field>
				<s:table.field name="cols5" label="Column header 5" sort="true" datatype="template">
					<s:textfield name="chnname" value="$[cols5]" dbl_action="zl_select_LCT600_01"></s:textfield>
				</s:table.field>
				<s:table.field name="cols6" label="Column header 6" sort="true" datatype="template">
					<s:textfield name="cols6" value="$[cols6]" icon="fa fa-weixin"></s:textfield>
				</s:table.field>
				<s:table.field name="cols7" label="Column header 7" sort="true" datatype="template">
					<s:textfield name="cols7" value="$[cols7]" icon="fa fa-weixin" dbl_action="zl_select_LCT600_01"></s:textfield>
				</s:table.field>
				<s:table.field name="cols8" label="Column header 8" sort="true">
				</s:table.field>
				<s:table.field name="cols9" label="Column header 9" sort="true"></s:table.field>
				<s:table.field name="cols10" label="Column header 10" sort="true"></s:table.field>
			</s:table.fields>
			<s:table.data>
				<tr>
					<td>我去我去阿里妈妈我去我去阿里妈妈我去我去阿里妈妈我去我去阿里妈妈我去我去阿里妈妈我去我去阿里妈妈</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
				<tr>
					<td>你去你去</td>
					<td>Table data</td>
					<td><s:textfield name="cols2" value="aaaa"></s:textfield></td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
				<tr>
					<td>阿里巴巴</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
				<tr>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
				<tr>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
				<tr>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
				<tr>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
				<tr>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
				<tr>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
				<tr>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
				<tr>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
				<tr>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
					<td>Table data</td>
				</tr>
			</s:table.data>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
$(document).ready(function(){
	 $("[name='eautocomplete']").setData('oa');
	//给表格单选按钮的行添加点击事件
		$("#demotable").bind("click",function(){
			$.each($('#demotable').getSelected(), function(i, val) {
				console.log(val.data);
			});
	    });
})
	function regf(){
		$.regfingerFun("XTY00000008");
		
	}
	function cP(){
		$.checkfingerFun("XTY00000008", {
		});
		
	}
</script>