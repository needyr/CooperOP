<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
*{
	margin: 0px auto;
	padding: 0px;
}

	body {
	background-color:gainsboro;
	overflow: hidden;
}
.fuselage {
	/* border-radius:20px; */
	border:white solid 1.5px;
	width:248px;
	height:274px;
	background-color:#EAEAEA;
	margin:0 auto;
}
.nose {
	width:400px;
	height:50px;
	display:block;
	margin:20px 95px 0 95px;
}
.nose-topic {
	color:rgba(0,0,0,0.8);
	font-size:25px;
}
#cc {
	text-align: right;
   /*  border-radius: 10px; */
    width: 246px;
    height: 50px;
    color: rgba(0,0,0,0.6);
    background-color: #F2F2F2;
    border: rgba(0,0,0,0.3) solid 2px;
    font-size: 40px;
    border: none;
}
.key ul {
	background-color:white;
	/* border-radius:10px; */
	border:#fff 2px solid;
	height:220px;
	overflow:hidden;
	cursor: pointer;
}
.key li {
	/* border-radius: 10px; */
    list-style: none;
    float: left;
    width: 21.8px;
    height: 40px;
    margin: 1px;
    padding: 9px 0px 4px 25px;
    display: inline;
    font-size: 18px;
    background-color: #e0e1e2;
}
.key li:hover {
	/* color:white;
	border-radius:10px;
	list-style:none;
	float:left;
	width:46.5px;
	height:72px;
	margin:1px;
	padding:9px 0px 4px 25px;
	display:inline;
	font-size:38px; */
	background-color:#c9c8c8;
}
.key li:active {
	/* color:white;
	border-radius:10px;
	list-style:none;
	float:left;
	width:46.5px;
	height:72px;
	margin:1px;
	padding:9px 0px 4px 25px;
	display:inline;
	font-size:38px; */
	background-color: #b2b2b2;
}
	
</style>
</head>
<body>
	<div class="fuselage">
    <!-- <div class="nose">
        <span class="nose-topic">
					辅助计算器
				</span>
        <br>
    </div> -->
    <input type="text" name="cc" id="cc" value="0" disabled="true">
    <div class="key">
        <ul>
            <li onclick="run(7)">7</li>
            <li onclick="run(8)">8</li>
            <li onclick="run(9)">9</li>
            <li onclick="del()">←</li>
            <li onclick="clearscreen()">C</li>
            <li onclick="run(4)">4</li>
            <li onclick="run(5)">5</li>
            <li onclick="run(6)">6</li>
            <li onclick="times()">×</li>
            <li onclick="divide()">÷</li>
            <li onclick="run(1)">1</li>
            <li onclick="run(2)">2</li>
            <li onclick="run(3)">3</li>
            <li onclick="plus()">+</li>
            <li onclick="minus()">-</li>
            <li onclick="run(0)">0</li>
            <li onclick="dzero()">00</li>
            <li onclick="dot()">.</li>
            <li onclick="persent()">%</li>
            <li onclick="equal()">=</li>
        </ul>
    </div>
</div>
</body>
<script>
var num = 0,
result = 0,
numshow = "0";
var operate = 0; //判断输入状态的标志 
var calcul = 0; //判断计算状态的标志 
var quit = 0; //防止重复按键的标志 
function run(num) {
var c = document.getElementById("cc").value;
c = (c != "0") ? ((operate == 0) ? c : "") : "";
c = c + num;
document.getElementById("cc").value = c;
operate = 0; //重置输入状态 
quit = 0; //重置防止重复按键的标志 
}

function dzero() {
var c = document.getElementById("cc").value;
c = (c != "0") ? ((operate == 0) ? c + "00" : "0") : "0"; //如果当前值不是"0"，且状态为0，则返回当str+"00"，否则返回"0"; 
document.getElementById("cc").value = c;
operate = 0;
}

function dot() {
var c = document.getElementById("cc").value;
c = (c != "0") ? ((operate == 0) ? c : "0") : "0"; //如果当前值不是"0"，且状态为0，则返回当前值，否则返回"0"; 
for (i = 0; i <= c.length; i++) { //判断是否已经有一个点号 
    if (c.substr(i, 1) == ".")
        return false; //如果有则不再插入
}
c = c + ".";
document.getElementById("cc").value = c;
operate = 0;
}

function del() { //退格 
var c = document.getElementById("cc").value;
c = (c != "0") ? c : "";
c = c.substring(0, c.length - 1);
c = (c != "") ? c : "0";
document.getElementById("cc").value = c;
}

function clearscreen() { //清除数据 
num = 0;
result = 0;
numshow = "0";
document.getElementById("cc").value = "0";
}

function plus() { //加法 
calculate(); //调用计算函数 
operate = 1; //更改输入状态 
calcul = 1; //更改计算状态为加 
}

function minus() { //减法 
calculate();
operate = 1;
calcul = 2;
}

function times() { //乘法 
calculate();
operate = 1;
calcul = 3;
}

function divide() { //除法 
calculate();
operate = 1;
calcul = 4;
}

function persent() { //求余 
calculate();
operate = 1;
calcul = 5;
}

function equal() {
calculate(); //等于 
operate = 1;
num = 0;
result = 0;
numshow = "0";
}

function calculate() {
numshow = Number(document.getElementById("cc").value);
if (num != 0 && quit != 1) { //判断前一个运算数是否为零以及防重复按键的状态 
    switch (calcul) { //判断要输入状态 
        case 1:
            result = num + numshow;
            break; //计算"+" 
        case 2:
            result = num - numshow;
            break; //计算"-" 
        case 3:
            result = num * numshow;
            break;
        case 4:
            if (numshow != "0") {
                result = num / numshow;
            } else {
                var xjx = '被除数不能为零！'
                document.getElementById("cc").value = xjx;
                setTimeout(clearnote, 4000)
            }
            break;
        case 5:
            result = num % numshow;
            break;
    }
    quit = 1; //避免重复按键 
} else {
    result = numshow;
}
numshow = String(result);
document.getElementById("cc").value = numshow;
num = result; //存储当前值 
}

function clearnote() { //清空提示 
document.getElementById("cc").value = "";
}
</script>
</html>