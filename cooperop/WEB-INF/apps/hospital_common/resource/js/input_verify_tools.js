//window.onload = function() {
	/*
	 * 判断输入的数据类型 使用方式：
	 * 1、输入框添加 类和data-type属性 ，如：class="judge_type" data-type="int" 
	 * 2、提交时，利用infoState()判断 
	 *  数据类型：整数 int; 浮点型 number;
	 */
	$('.judge_type').on('input propertychange', function() {
		var data_type = $(this).attr('data-type');
		if (data_type == 'int') {
			var regEx = /^-?\d+$/;
			reg($(this), regEx, '请输入整数！');
		} else if (data_type == 'number') {
			var regEx = /^(-?\d+)(\.\d+)?$/;
			reg($(this), regEx, '请输入小数!');
		}

	})
//}

/**
 * 单独添加事件保证能够实时校验
 */
function add_event(_this){
	$(_this).on('input propertychange', function() {
		var data_type = $(this).attr('data-type');
		if (data_type == 'int') {
			var regEx = /^-?\d+$/;
			reg($(this), regEx, '请输入整数！');
		} else if (data_type == 'number') {
			var regEx = /^(-?\d+)(\.\d+)?$/;
			reg($(this), regEx, '请输入小数!');
		}

	})
}

/*
 * 根据正则表达式判断数据类型 参数：obj-当前输入框，regEx-正则表达式, tips-提示信息
 */
function reg(obj, regEx, tips) {
	var inputVal = obj.val();
	// 如果不符合正则表达式，且不存在提示 就 添加提示
	if (!regEx.test(inputVal) && !obj.parent().find("span").hasClass("judgeTips")) {
		obj.parent().append('<span class="judgeTips" style="color:red">' + tips+ '</span>');
	}
	
	// 如果符合正则表达式，且存在提示 就 移除提示
	if (regEx.test(inputVal) && obj.parent().find("span").hasClass("judgeTips")) {
		obj.parent().find(".judgeTips").remove();
	}
	
	if(inputVal == ''){
		obj.parent().find(".judgeTips").remove();
	}
}

function infoState() {
	// 判断有无错误提示信息
	if ($("span").hasClass("judgeTips")) {
		return false;
	} else {
		return true;
	}
}