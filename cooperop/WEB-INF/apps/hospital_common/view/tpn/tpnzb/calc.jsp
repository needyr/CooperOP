<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="TPN公式计算器">
<s:row>
	<s:form border="0" id="allform" cols="1" label="TPN公式计算器">
		<s:toolbar>
			<s:button label="保存" onclick="save();"></s:button>
			<s:button label="返回" onclick="cancel();"></s:button>
		</s:toolbar>
		<s:row>
			<s:textarea name="formual" label="公式" cols="4" readonly="true"></s:textarea>
		</s:row>
		<s:row>
			<s:button label="糖(g)" onclick="add('糖(g)','sugar','1');"></s:button>
			<s:button label="脂肪(g)" onclick="add('脂肪(g)','fat','1');"></s:button>
			<s:button label="蛋白质(g)" onclick="add('蛋白质(g)','protein','1');"></s:button>
			<s:button label="(" onclick="add('(','(','3');"></s:button>

		</s:row>
		<s:row>
			<s:button label="液体量(ml)" onclick="add('液体量(ml)','lq','1');"></s:button>
			<s:button label="K等渗当量" onclick="add('K等渗当量','kioe','1');"></s:button>
			<s:button label="Ca等渗当量" onclick="add('Ca等渗当量','caioe','1');"></s:button>
			<s:button label=")" onclick="add(')',')','3');"></s:button>	
		</s:row>
		<s:row>
			<s:button label="Mg等渗当量" onclick="add('Mg等渗当量','mgioe','1');"></s:button>
			<s:button label="氨基酸(g)" onclick="add('氨基酸(g)','bcaa','1');"></s:button>
			<s:button label="Na等渗当量" onclick="add('Na等渗当量','naioe','1');"></s:button>
			<s:button label="+" onclick="add('+','+','2');"></s:button>
		</s:row>
		<s:row>
			<s:button label="K+(mmol)" onclick="add('K+(mmol)','kmol','1');"></s:button>
			<s:button label="Mg++(mmol)" onclick="add('Mg++(mmol)','mgmol','1');"></s:button>
			<s:button label="Na+(mmol)" onclick="add('Na+(mmol)','namol','1');"></s:button>
			<s:button label="-" onclick="add('-','-','2');"></s:button>
		</s:row>
		<s:row>
			<s:button label="Ca++(mmol)" onclick="add('Ca++(mmol)','camol','1');"></s:button>
			<s:button label="P+(mmol)" onclick="add('P+(mmol)','pmol','1');"></s:button>			
		</s:row>
		<s:row>
			<s:button label="1" onclick="add('1','1','4');"></s:button>
			<s:button label="2" onclick="add('2','2','4');"></s:button>
			<s:button label="3" onclick="add('3','3','4');"></s:button>
			<s:button label="*" onclick="add('*','*','2');"></s:button>
		</s:row>
		<s:row>
			<s:button label="4" onclick="add('4','4','4');"></s:button>
			<s:button label="5" onclick="add('5','5','4');"></s:button>
			<s:button label="6" onclick="add('6','6','4');"></s:button>
			<s:button label="/" onclick="add('/','/','2');"></s:button>
		</s:row>
		<s:row>
			<s:button label="7" onclick="add('7','7','4');"></s:button>
			<s:button label="8" onclick="add('8','8','4');"></s:button>
			<s:button label="9" onclick="add('9','9','4');"></s:button>

		</s:row>
		<s:row>
			<s:button label="." onclick="add('.','.','5');"></s:button>
			<s:button label="0" onclick="add('0','0','4');"></s:button>
			<s:button label="清空" onclick="clearA();"></s:button>
			<s:button label="后退" onclick="back();"></s:button>
		</s:row>
<%-- 		<s:row>
			<s:textarea name="formuals" label="公式" cols="4" readonly="true"></s:textarea>
		</s:row> --%>
	</s:form>
</s:row>
</s:page>
<script type="text/javascript">
var formulalist=[];
var formulastr='';
var leftbc=0;//左括号统计
var rightbc=0;//右括号统计
var formualbalance=0;//公式平衡性	0:平衡	大于0:需要还	小于0不存在

var laststrtp;
var lastview;
var lastsend;
var firstsend;
$(function(){
/*  	formulalist =${param.formulalist};
 	updateview(); */
/*  	if('${param.formulalist}'!=0){
 		updateview();
	}  */
}) 
function back(){
	//回退括号统计、公式平衡性
	if(lastsend=="("){
		leftbc--;
		formualbalance--;
	}
	if(lastsend==")"){
		rightbc--;
		formualbalance++;
	}
	formulalist.pop();//删除最后一项
	updateview();
}
//添加
function add(view,send,strtp){
		var formulaobj={};
		var len = formulalist.length;
		//第一个输入
		if(len==0){
			switch(true){
			case (strtp=='2')://一开始就是符号
				tips(3);
				return;
			case (send==')')://一开始就是右子括号
				tips(4);
				return;
			case (strtp=='5')://一开始就是点
				tips(13);
				return;
			case (send=='(')://左子括号++
				leftbc++;
				formualbalance++;
				break;
			}
			formulaobj.view=view;
			formulaobj.strtp=strtp;
			formulaobj.send=send;
			formulalist.push(formulaobj);
			updateview();
		}else{
			//第一个输入
			laststrtp = formulalist[formulalist.length-1].strtp;//类型
			lastview = formulalist[formulalist.length-1].view;//显示字串
			lastsend = formulalist[formulalist.length-1].send;//计算字串
			firstsend = formulalist[0].send;//第一次计算字串
			//羁绊
			switch(true){
			case (strtp=='1'&&laststrtp=='1')://连续类型1
				tips(1);
				return;
			case (strtp=='2'&&laststrtp=='2')://连续类型2
				tips(2);
				return;
			case (strtp=='5'&&(laststrtp!='4'||isvalidity()))://点之前不能是数字和数字规范校验
				tips(11);
				return;
			case (send=='('&&(laststrtp=='1'||laststrtp=='4'||laststrtp=='5'))://'('之前不能是字串和数字和点
				tips(5);
				return;
			case (send==')'&&(laststrtp=='2'||laststrtp=='5'||lastsend=='('))://')'之前不能是运算符和点和'('
				tips(6);
				return;
			case (strtp=='2'&&(lastsend=='('||laststrtp=='5'))://运算符之前不能是'('和点
				tips(7);
				return;
			case (strtp=='1'&&(lastsend==')'||laststrtp=='4'||laststrtp=='5'))://字串之前不能是')'和数字和点
				tips(8);
				return;
			case (strtp=='4'&&(laststrtp=='1'||lastsend==')'))://数字之前不能是字串和')'
				tips(12);
				return;
			case (formualbalance==0&&send==')')://公式平衡，有借有还，未还不能借
				tips(9);
				return;
			case (send=='(')://左子括号++
				leftbc++;
				formualbalance++;
				break;
			case (send==')')://右子括号--
				rightbc++;
				formualbalance--;
				break;
			}
			formulaobj.view=view;
			formulaobj.strtp=strtp;
			formulaobj.send=send;
			formulalist.push(formulaobj);
			updateview();
		}
}
//数字规范校验
function isvalidity(){
	var isstate=true;
	if(formulalist.length>=3){
		for(var i=1;i<=formulalist.length;i++){
			var emp_strtp = formulalist[formulalist.length-i].strtp;
			if(emp_strtp=='4'){
				emp_strtp=true;
			}else if(emp_strtp=='5'){
				emp_strtp=false;
				break;
			}else{
				emp_strtp=true;
				break;
			}
		}
	}
	if(emp_strtp==false){
		return true;
	}else{
		return false;
	}
}
function tips(num){
	$.message("请输入正确的公式:"+num);
}
//保存公式
function save(){
	if(formulalist.length==0){
		$.message("请输入公式");
		return;
	}
	laststrtp = formulalist[formulalist.length-1].strtp;
 	if(laststrtp=='2'){	//最后不能以运算符结束
		$.message("请输入完整的公式");
		return;
	}
 	if(leftbc!=rightbc){	//借多少还多少括号
		$.message("请输入正确的公式");
		return;
	}
/* 	var formualobj={"view":viewstr,"send":sendstr,"strtype":strtype}; */
 	$.closeModal(formulalist);
}
function updateview(){
	var formula='';
	for(var i=0;i<formulalist.length;i++){
		formulastr=formulastr+formulalist[i].view;
	}
	for(var i=0;i<formulalist.length;i++){
		formula=formula+formulalist[i].send;
	}
	$("[name='formual']").prop('value',formulastr);
	$("[name='formuals']").prop('value',formula);
	formulastr='';
	formula='';
}
function clearA(){
	formulalist=[];
	formulaobj={};
	formulastr='';
	leftbc=0;//左括号统计
	rightbc=0;//右括号统计
	formualbalance=0;//公式平衡性	0:平衡	大于0:需要还	小于0不存在
	laststrtp=null;
	lastview=null;
	lastsend=null;
	firstsend=null;
	updateview();
}
function cancel(){
	$.closeModal(true);
}
</script>