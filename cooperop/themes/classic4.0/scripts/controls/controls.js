$.fn.extend({
	"open_tabwindow": function(id, title, url, need_refresh) {
		$.open(url);
	},
	"ccinit": function() {
		var $this = this;
		if ($this.is("[ctype]") && $this.is(":not([cinited])")) {
			if (!$.isFunction($this["ccinit_" + $this.attr("ctype")])) {
				$.console().error("ccinit_" + $t.attr("ctype"));
			} else {
				$this["ccinit_" + $this.attr("ctype")]();
			}
		}
		this.find("[ctype]").each(function() {
			var $t = $(this);
			if ($t.is(":not([cinited])")) {
				if($t.attr("ctype") == 'chart'){
					console.log($t["ccinit_" + $t.attr("ctype")])
				}
				if (!$.isFunction($t["ccinit_" + $t.attr("ctype")])) {
					$.console().error("ccinit_" + $t.attr("ctype"));
					return;
				}
				$t["ccinit_" + $t.attr("ctype")]();
			}
		});
		
		$("#pageloading").remove();
	},
	"getData": function(trid) {
		var $this = this;
		var data = {};
		if ($this.is("[ctype]") && $this.is("[cinited]")) {
			if (!$.isFunction($this["getData_" + $this.attr("ctype")])) {
				var v = $this.val();
				if ($this.attr("placeholder") == v) {
					v = '';
				}
				$.pushData(data, $this.attr("name"), v);
			} else {
				$.pushData(data, $this["getData_" + $this.attr("ctype")](trid));
			}
		} else if ($this.is(":not([ctype])") && $this.is("input:not([ccinput]), textarea:not([ccinput]), select:not([ccinput])")){
			var v = $this.val();
			if ($this.attr("placeholder") == v) {
				v = '';
			}
			$.pushData(data, $this.attr("name"), v);
		}
		$this.find("[ctype]").each(function() {
			var $t = $(this);
			if ($t.is("[cinited]")) {
				if (!$.isFunction($t["getData_" + $t.attr("ctype")])) {
					var v = $t.val();
					if ($t.attr("placeholder") == v) {
						v = '';
					}
					$.pushData(data, $t.attr("name"), v);
				} else {
					$.pushData(data, $t["getData_" + $t.attr("ctype")]());
				}
			}
		});
		$this.find("input:not([ccinput],[ctype]), textarea:not([ccinput],[ctype]), select:not([ccinput],[ctype])").each(function() {
			var $t = $(this);
			if ($t.is(":checkbox")) {
				if (!this.checked) {
					return;
				}
			} else if ($t.is(":radio")) {
				if (!this.checked) {
					return;
				}
			}
			var v = $t.val();
			if ($t.attr("placeholder") == v) {
				v = '';
			}
			$.pushData(data, $t.attr("name"), v);
		});
		return data;
	},
	"setData": function(data) {
		var $this = this;
		if ($this.is("[ctype]") && $this.is("[cinited]")) {
			if (!$.isFunction($this["setData_" + $this.attr("ctype")])) {
				$this.val(data);
			} else {
				$this["setData_" + $this.attr("ctype")](data);
			}
		} else if ($this.is(":not([ctype])") && $.isFunction($this["val"])){
			$this.val(data);
		}
	},
	"clearData": function() {
		var $this = this;
		if ($this.is("[ctype]") && $this.is("[cinited]")) {
			if (!$.isFunction($this["clearData_" + $this.attr("ctype")])) {
				$this.val("");
			} else {
				$this["clearData_" + $this.attr("ctype")]();
			}
		} else if ($this.is(":not([ctype])") && $.isFunction($this["val"])){
			$this.val("");
		}
	},
	"params": function(data) {
		var $this = this;
		if ($this.is("[ctype]") && $this.is("[cinited]")) {
			if ($.isFunction($this["params_" + $this.attr("ctype")])) {
				$this["params_" + $this.attr("ctype")](data);
			}
		}
	},
	"refresh": function(index) {
		var $this = this;
		if ($this.is("[ctype]") && $this.is("[cinited]")) {
			if ($.isFunction($this["refresh_" + $this.attr("ctype")])) {
				$this["refresh_" + $this.attr("ctype")](index);
			}
		}
	},
	"export": function(data) {
		var $this = this;
		if ($this.is("[ctype]") && $this.is("[cinited]")) {
			if ($.isFunction($this["export_" + $this.attr("ctype")])) {
				$this["export_" + $this.attr("ctype")](data);
			}
		}
	},
	"print": function(data) {
		var $this = this;
		if ($this.is("[ctype]") && $this.is("[cinited]")) {
			if ($.isFunction($this["print_" + $this.attr("ctype")])) {
				$this["print_" + $this.attr("ctype")](data);
			}
		}
	},
	"getSelected": function() {
		var $this = this;
		if ($this.is("[ctype]") && $this.is("[cinited]")) {
			if ($.isFunction($this["getSelected_" + $this.attr("ctype")])) {
				return $this["getSelected_" + $this.attr("ctype")]();
			}
		}
		return [];
	},
	"resetForm": function() {
		var $this = this;
		$this.find('.control-content[eidx]').each(function() {
			layer.close($(this).attr("eidx"));
			$(this).removeClass("f_error");
		});
	}
});

$.extend({
	"pushData" : function(data, d, v) {
		if (d != null && d != undefined && d) {
			var t = d;
			if ($.type(d) === "string") {
				t = {};
				t[d] = v;
			}
			for (var key in t) {
				var value = t[key];
				if ($.isArray(value)) {
					for (var i in value) {
						value[i] = $.trim(value[i]);
					}
				}
				if (data[key]) {
					if ($.isArray(data[key])) {
						if ($.isArray(value)) {
							$.merge(data[key], value);
						} else {
							data[key].push(value);
						}
					} else {
						var a = [];
						a.push(data[key]);
						if ($.isArray(value)) {
							$.merge(a, value);
						} else {
							a.push(value);
						}
					}
				} else {
					data[key] = value;
				}
			}
		}
		return data;
	}
});

jQuery.extend(jQuery.validator.messages, {
	required : "此项必填",
	remote : "请修正该字段",
	email : "请输入正确格式的电子邮件",
	url : "请输入合法的网址",
	date : "请输入合法的日期",
	dateISO : "请输入合法的日期 (ISO).",
	number : "请输入合法的数字",
	digits : "只能输入整数",
	creditcard : "请输入合法的信用卡号",
	equalTo : "请再次输入相同的值",
	accept : "请输入拥有合法后缀名的字符串",
	maxlength : jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
	minlength : jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
	rangelength : jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
	range : jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
	max : jQuery.validator.format("请输入一个最大为{0} 的值"),
	min : jQuery.validator.format("请输入一个最小为{0} 的值")
});
jQuery.validator.addMethod("rangelength",
		function(value, element, param) {
			var length = value.length;
			for (var i = 0; i < value.length; i++) {
				if (value.charCodeAt(i) > 127) {
					length++;
				}
			}
			return this.optional(element)
					|| (length >= param[0] && length <= param[1]);
		}, $.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"));
jQuery.validator.addMethod("minlength", function(value, element, param) {
	var length = value.length;
	for (var i = 0; i < value.length; i++) {
		if (value.charCodeAt(i) > 127) {
			length++;
		}
	}
	return this.optional(element) || (length >= param);
}, $.validator.format("请输入一个 长度最少是 {0} 的字符串"));
jQuery.validator.addMethod("maxlength", function(value, element, param) {// 此算法应该和autocomple中maxlength限制一致
	if (value == undefined) {
		value = "";
	} else if (typeof value == 'object') {
		value = value + "";
	}
	var length = value.length;
	var hanzLen = 0;
	for (var i = 0; i < value.length; i++) {
		if (value.charCodeAt(i) > 127) {
			if (i < (param - hanzLen))
				hanzLen++;
			length++;
		}
	}
	if (!(this.optional(element) || (length <= param))) {
		value = value.substring(0, param - hanzLen);
		$(element).val(value);
		$(element).change();
	}
	$.validator.messages['maxlength'] = "最多允许输入" + param + "个英文字符或"
			+ parseInt(param / 2) + "个汉字";
	return this.optional(element) || (length <= param);
}, $.validator.format(""));
// 电话号码验证
jQuery.validator.addMethod("tel", function(value, element) {
	var tel = /^((0\d{2,3})-)?(\d{7,8})(-(\d{2,5}))?$/; // 电话号码格式:010-12345678
	var length = value.length;
	var mobile = /(^0{0,1}1[3|5|7|8][0-9]{9}$)/;
	return this.optional(element) || (tel.test(value)) || (length == 11 && mobile.test(value));
}, "请输入正确格式的电话号码。格式：区号-电话号码或电话号码或手机号码");

//手机号码验证
jQuery.validator.addMethod("mobile", function(value, element) {
	var length = value.length;
	var mobile = /(^0{0,1}1[3|5|7|8][0-9]{9}$)/;
	return this.optional(element) || (length == 11 && mobile.test(value));
}, "请输入正确格式的手机号码");

//身份证校验
jQuery.validator.addMethod("idcard", function(value, element) {
	var length = value.length;
	var idcard = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X|x)$/;
	return this.optional(element) || ((length == 15 || length == 18) && idcard.test(value));
}, "请输入正确格式的身份证号码");

//中文校验
jQuery.validator.addMethod("chinese", function(value, element) {
	var chinese = /^[\u4E00-\u9FA5]+$/;
	return this.optional(element) || (chinese.test(value));
}, "请输入全中文内容");

// 验证必填
jQuery.validator.addMethod("required", function(value, element) {
	if (value == $(element).attr("placeholder") || value === null
			|| value == "") {
		return false;
	}
	if (value instanceof String) {
		if (typeof value == "undefined" || value.trim() == "") {
			return false;
		}
	}
	return true;
}, $.validator.messages['required']);
var calender_config = {
		header: {
		    left: 'prev,next',
		    center: 'title',
		    right: 'today,month,agendaWeek'
		},
		titleFormat: {
		    month: 'yyyy年MM月',
		    week: 'yyyy年MM月dd日至{yyyy年MM月dd日}',
		    day: 'yyyy年MM月dd日'
		},
		columnFormat : {
		    week : "ddd M/d"
		},
		timeFormat : {
		    month: "HH:mm{-HH:mm}",
		    agenda: "HH:mm{-HH:mm}"
		},
		axisFormat : "HH:mm",
		firstDay : 1,
		defaultView: "month",  //month, agendaWeek, basicWeek, agendaWeekDay, basicDay
		weekMode: "variable",  //fixed, liquid, variable
		allDaySlot: false,
		allDayText: '全天',
		minTime : 0,
		maxTime : 24,
		firstHour: 8,
		slotMinutes: 30,
		defaultEventMinutes: 30,
		editable: false,
		draggable: false,
		dragOpacity: 0.3,
		aspectRatio: 2,
		monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
		monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
		dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
		dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
		today: ["今天"],
		buttonText: {
			prev: '<i class="icon-chevron-left cal_prev" />',
		    next: '<i class="icon-chevron-right cal_next" />',
		    prevYear: '&nbsp;&lt;&lt;&nbsp;',
		    nextYear: '&nbsp;&gt;&gt;&nbsp;',
		    today: '返回今天',
		    month: '月视图',
		    week: '周视图',
		    day: '日视图'
		},
		eventColor: '#D1D1D1'
};
$(document).ready(function() {
	$(this).ccinit();
	var v = $("form").validate({
		errorClass : 'error',
		validClass : 'valid',
		ignore : ":hidden",
		highlight : function(element) {
			$(element).parents('.control-content:eq(0)').addClass("f_error");
		},
		unhighlight : function(element) {
			$(element).parents('.control-content:eq(0)').removeClass("f_error");
		},
		invalidHandler : function(form, validator) {
			// $.sticky("There are some errors. Please corect them and submit
			// again.", {autoclose : 5000, position: "top-right", type: "st-error"
			// });
		},
		errorPlacement : function(error, element) {
			// Set positioning based on the elements position in the form
			var elem = $(element);
			// Check we have a valid error message
			if (!error.is(':empty')) {
				if (elem.parents('.control-content:eq(0)').is("[eidx]")) {
					layer.close(elem.parents('.control-content:eq(0)').attr("eidx"));
				}
				var eidx = layer.tips(error.text(), elem.parents('.control-content:eq(0)'), {tipsMore: true, time: 0, shift: -1, tips: [2, "#EF4836"]});
				elem.parents('.control-content:eq(0)').attr("eidx", eidx);
				elem.parents('.control-content:eq(0)').attr("emsg", error.text());
			}
			// If the error is empty, remove the message
			else {
				if (elem.parents('.control-content:eq(0)').is("[eidx]")) {
					layer.close(elem.parents('.control-content:eq(0)').attr("eidx"));
					elem.parents('.control-content:eq(0)').removeAttr("eidx");
				}
				elem.parents('.control-content:eq(0)').removeAttr("emsg");
			}
		},
		success : $.noop
	});
	$("form").data("validator", v);
});

function show_maxlength(this_){
	var $this = $(this_);
	if($this.attr("title")){
		layer.open({
			  type: 1,
			  skin: 'layer-ext-moon', //样式类名
			  closeBtn: 0, //不显示关闭按钮
			  //title : false,
			  anim: 2,
			  area: ['400px', '300px'],
			  shadeClose: true, //开启遮罩关闭
			  content: '<div style="padding: 20px;font-size:16px;">'+$this.attr("title")+'</div>'
			});
	}
}