<%@page import="cn.crtech.cooperop.application.action.SchemeAction"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<style>
.step {
	position: relative;
	vertical-align: top;
	display: inline-block;
}

.step-head {
	width: 18px;
	height: 18px;
	border-radius: 50%;
	line-height: 19px;
	text-align: center;
	vertical-align: top;
	color: #bfcbd9;
	font-size: 14px;
	border: 2px solid #bfcbd9;
	z-index: 10000;
}

.step-main {
	font-size: 12px;
	color: #48576a;
}

.step-line {
	position: absolute;
	top: 9px;
	height: 5px;
	left: 22px;
	right: -5px;
	display: inline-block;
	background-color: #bfcbd9;
	z-index: 10000;
}

.step-circle {
	margin: 4px;
	width: 10px;
	height: 10px;
	border-radius: 50%;
	background-color: #bfcbd9;
}

.is-sucess>.step-head {
	color: #2f318e;
	border-color: #2f318e;
}

.is-sucess>.step-head .step-circle {
	background-color: #2f318e;
}

.is-sucess>.step-head>.step-line {
	background-color: #2f318e;
}

.last-sucess>.step-head {
	color: #2f318e;
	border-color: #2f318e;
}

.last-sucess>.step-head .step-circle {
	background-color: #2f318e;
}
</style>

<s:page dispermission="true" disloggedin="true" title="测试2" ismodal="false">
	<s:row>
		<button onclick="addStep()">addStep</button>
		<button onclick="clearSteps()">clearSteps</button>
		<div class="steps">
			<div class="step ">
				<div class="step-head">
					<div class="step-line"></div>
					<div class="step-icon">1</div>
				</div>
				<div class="step-main">步骤一</div>
			</div>
			<div class="step">
				<div class="step-head">
					<div class="step-line"></div>
					<div class="step-icon">2</div>
				</div>
				<div class="step-main">步骤二</div>
			</div>
			<div class="step">
				<div class="step-head">
					<div class="step-line"></div>
					<div class="step-icon">
						<div class="step-circle"></div>
					</div>
				</div>
				<div class="step-main">步骤三</div>
			</div>

			<div class="step">
				<div class="step-head">

					<div class="step-icon">
						<div class="step-circle"></div>
					</div>
				</div>
				<div class="step-main">步骤四</div>
			</div>
		</div>
	</s:row>


</s:page>

<script type="text/javascript">
var steps = $(".step");
var stepIndex = 0;

setStep(stepIndex);

$(".step-icon").click(function () {
    var me = this;
    stepIndex = $(me).parents(".step").index();
    setStep(stepIndex);
});


function setStep(stepIndex) {
    $(steps).removeClass("is-sucess");
    $(steps).removeClass("last-sucess");

    for (var i = 0; i <= stepIndex; i++) {
        var step = steps[i];
        if (i < stepIndex) {
            $(step).addClass("is-sucess");
        }
        else {
            $(step).addClass("last-sucess");
        }
    }
}


function clearSteps() {
　stepIndex = -1;
　setStep(stepIndex);

}


function addStep(addnum) {
    if (!addnum) { addnum = 1 }
    stepIndex += addnum;
    setStep(stepIndex);
}
</script>
