<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="测试" disloggedin="true" dispermission="true"
	ismodal="true">
	
	<s:row>
<!-- 自动补全框
	说明：用于自动补全的输入 -->
<s:autocomplete action="" cols="" editable="" help="" htmlEscape=""
isherf="" islabel="" label="" maxlength="" minlength="" readonly=""
text="" tips="" value="" ></s:autocomplete>

<!--按钮
	说明：可放置于toolbar和row中  -->
<s:button action="" icon="" label="" size="" type="" ></s:button>
<!-- 按钮组
	说明：可放置于toolbar和row中 -->
<s:buttongroup icon="" label="" ></s:buttongroup>
<!--按钮组按钮
	说明：可放置于ButtonGroup中  -->
<s:buttongroup.button action="" icon="" label="" type="" ></s:buttongroup.button>
<!--图标
	说明：放在行中  -->
<s:chart label="" ></s:chart>
<!-- 复选组
	说明：采用checkbox的方式列出所有选项，由用户选择 -->
<s:checkbox action="" cols="" column_type="" create_action="" dbl_action=""
dictionary="" encryption="" enter_action="" height="" help=""
isherf="" islabel="" label="" modify_action="" out_action=""
readonly="" value="" ></s:checkbox>

<!--日期  -->
<s:datefield cols="" format="" label="" name="" readonly="" ></s:datefield>
<!--文件控件
	说明：用于上传文件，并保存文件id做为控件值，“,”分割  -->
<s:file addable="" autoupload="" cols="" column_type="" create_action=""
dbl_action="" deleteable="" encryption="" enter_action="" filetypes="" 
height="" help="" islabel="" label="" maxlength="" maxsize=""
minlength="" minsize="" modify_action="" out_action="" value=""></s:file>

<!--表单  -->
<s:form border="" collapsed="" color="" cols="" extendable=""
icon="" label=""></s:form>
<!--图片控件
	说明：用于上传图片，并预览，保存文件id做为控件值，“,”分割  -->
<s:image autoupload="" cols="" column_type="" create_action=""
dbl_action="" encryption="" enter_action="" filetypes="" height=""
help="" islabel="" label="" maxlength="" maxsize="" minlength=""
minsize="" modify_action="" out_action="" value="" ></s:image>
<!--列表
	说明：表格类组件，可以是多级树状列表。  -->
<s:list action="" autoload="" cols="" height="" key="" total=""
treeaction="" ></s:list>
<!--列表内容模板
	说明：列表的内容模板  -->
<s:list.content type="" ></s:list.content>
<!-- 列表字段
说明：用于定义列表的排序字段，sort为false的字段将不在排序区域出现，但仍可做为列表内容的参数，此标签
 在datatype为script时文本内容区域允许写javascript脚本文件，用于动态生成字段变量内容，必须存在返回值，
 默认变量参数：record, code, dictionary
例子1：if (record.a > record.b) return 'yes' else return 'no'
例子2：return (record.a > record.b ? 'yes' : 'no')
例子3：var html = []; html.push('123'); html.push('456'); return html.join(""); -->
<s:list.field code="" datatype="" defaultsort="" dictionary="" format=""
label="" sort="" ></s:list.field>

<!--选择项
说明：供select、radio、checkbox的生成值使用，在action状态下文本内容区域允许写javascript脚本文件，用
 于动态生成单元格内容，必须存在返回值，默认变量参数：record, code
例子1：if (record.a > record.b) return 'yes' else return 'no'
例子2：return (record.a > record.b ? 'yes' : 'no')
例子3：var html = []; html.push('123'); html.push('456'); return html.join("");  -->
<s:option disabled="" label="" value="" ></s:option>

<!--页面根
说明：用于引用框架所需的相关脚本及样式表，并生成页头页脚内容（由modal属性决定）  -->
<s:page title="" disloggedin="" dispermission="" flag="" ismobile=""
ismodal="" modalheight="" modalwidth="" schemeid="" themetype="" type="" ></s:page>
<!--块容器
说明：生成页面的展示类区块，内容中以row标签为主要载体，以保证分栏式布局，可包含一个Toolbar做为块工
 具条。  -->
<s:panel cols="" icon="" title="" ></s:panel>
<!--单选组
	说明：采用radio的方式列出所有选项，由用户选择  -->
<s:radio action="" cols="" column_type="" create_action=""
dbl_action="" dictionary="" encryption="" enter_action=""
height="" help="" isherf="" islabel="" label="" modify_action=""
out_action="" readonly="" value="" ></s:radio>

<!--文档编辑控件  -->
<s:richeditor cols="" height="" help="" islabel="" label=""
maxlength="" minlength="" toolbar="" value="" ></s:richeditor>
<!--行容器
	说明：页面布局的必须元素，用于放置于容器型元素（page、panel、tabcontent）中做行布局，同时其他组件
 （具备cols属性）除page外均需放置在row标签中，以保证分栏式布局  -->
<s:row ></s:row>

<!--下拉选择、弹框选择（暂不启用）
	说明：用于静态值选择  -->
<s:select action="" cols="" column_type="" create_action="" dbl_action=""
dictionary="" encryption="" enter_action="" help="" isherf=""
islabel="" label="" modify_action="" more="" out_action="" readonly=""
value="" ></s:select>
<!--  -->


<s:selecttree action="" cols="" islabel="" label="" nodeid=""
nodename="" pid="" value="" ></s:selecttree>

<!--逻辑开关
	说明：用开关的方式控制逻辑性选择 -->
<s:switch cols="" column_type="" create_action="" dbl_action=""
encryption="" enter_action="" help="" isherf="" islabel="" label=""
modify_action="" offtext="" offvalue="" ontext="" onvalue=""
out_action="" readonly="" value="" ></s:switch>

<!--滑块选择控件（暂不提供）
	说明：用于滑块选择输入数字类数据  -->
<s:slider cols="" datatype="" format="" help="" islabel="" 
label="" max="" min="" step="" value="" ></s:slider>


<!--表格
	说明：表格类组件，可以是多级树状表格，可以静态填充数据。  -->
<s:table action="" autoload="" border="" collapsed="" color=""
cols="" extendable="" fitwidth="" height=""
icon="" key="" label="" recordAddtAction="" recordCheckAction=""
recordDeleteAction="" recordMoveAction="" recordSubmitAction=""
sort="" total="" treeaction="" ></s:table>

<!--表格静态数据，在没有action的生效
	说明：以tr为单位，实际的html table body的内容。  -->
<s:table.data ></s:table.data>

<!--表格字段
说明：用于定义表格的显示及隐藏字段，此标签在datatype为script时文本内容区域允许写javascript脚本文件，
 用于动态生成单元格内容，必须存在返回值，默认变量参数：record, code, dictionary
例子1：if (record.a > record.b) return 'yes' else return 'no'
例子2：return (record.a > record.b ? 'yes' : 'no')
例子3：var html = []; html.push('123'); html.push('456'); return html.join("");  -->
<s:table.field name="" app_field="" datatype="" dbl_action=""
defaultsort="" dictionary="" editable="" enter_action=""
format="" hidden="" label="" maxdigits="" maxlength="" modify_action=""
sort="" tsize="" width=""  ></s:table.field>
<!--表格字段列表容器  -->
<s:table.fields ></s:table.fields>
<!--多页签容器
	说明：生成页面的多页签展示类区块，一个多页签容器至少需要一个页签。  -->
<s:tabpanel color="" cols="" ></s:tabpanel>

<!--任务记录者  -->
<s:taskhistory djbh="" ></s:taskhistory>

<!--文本区域输入框
	说明：用于手工输入较长的文本类数据  -->
<s:textarea autosize="" cols="" column_type="" create_action="" dbl_action=""
encryption="" enter_action="" height="" help="" htmlEscape="" isherf=""
islabel="" label="" maxlength="" minlength="" 
modify_action="" out_action="" readonly="" tips=""
value="" ></s:textarea>

<!--文本输入框
	说明：用于手工输入文本类，数字类，日期时间类的数据  -->
<s:textfield cols="" column_type="" create_action="" datatype=""
dbl_action="" dictionary="" encryption="" enter_action=""
format="" help="" htmlEscape="" isherf="" islabel="" label=""
max="" maxlength="" min="" minlength="" modify_action="" out_action="" 
precision="" readonly="" step="" tips="" 
value="" ></s:textfield>

<!--时间域  -->
<s:timefield cols="" label="" name="" readonly="" ></s:timefield>
<!--工具条
	说明：仅对panel和tab有效，标题栏右侧的工具条  -->
<s:toolbar ></s:toolbar>






	</s:row>

</s:page>