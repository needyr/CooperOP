$.fn.extend({
	"print_new": function(foot){
		//var $this = $(table);
		var $this = this;
		var _tc_tmp = $this.data("_tc");
		var html = [];
		var data_new = [];
		var _tc = [];
		for (var c in _tc_tmp) {
			_tc.push(_tc_tmp[c]);
		}
		_tc.sort(function(a, b) {
			return a.order - b.order;
		});
		/*data_new = _tc;
		var userAgent = navigator.userAgent.toLowerCase(); //取得浏览器的userAgent字符串
		if (userAgent.indexOf("trident") > -1) {
			alert("请使用google或者360浏览器打印");
			return false;
		} else if (userAgent.indexOf('msie') > -1) {
			var onlyChoseAlert = simpleAlert({
				"content" : "请使用Google或者360浏览器打印",
				"buttons" : {
					"确定" : function() {
						onlyChoseAlert.close();
					}
				}
			})
			alert("请使用google或者360浏览器打印");
			return false;
		} else {//其它浏览器使用lodop
			var action = $this.attr("action");
			var data = $this.data("params") || {};
			data.start = 1;
			data.limit = -1;
			$.call(action,data,function(rtn){
				var oldstr = document.body.innerHTML;
				var headstr = "<html><head><title></title></head><body>";
				var footstr = "</body></html>";
				var html = [];
				var rt = rtn.resultset;
				if(rtn.count>0){
					//$this.parents('.portlet').children('.portlet-title').children('.nav-tabs').hasClass("active").text()
					html.push('<div style="text-align: center;width:100%;"><h1>'+$this.parents('.portlet').children('.portlet-title').children('.caption').text()+'</h1></div>');
					html.push('<table style="border-top: 1px solid;border-left: 1px solid;" cellspacing="0" cellpadding="0" width="100%">');
					html.push('<tr>');
					for(s in data_new){
						html.push('<th style="border-bottom: 1px solid;border-right: 1px solid;">'+data_new[s].label+'</th>');
					}
					html.push('</tr>');
					for(var i=0; i< rt.length;i++){
						var r = rt[i];
						html.push('<tr>');
						for(s in data_new){
							if(data_new[s].datatype == 'script'){
								var funcStr = "function test(record){"+data_new[s].script+"}";
								var funcTest = new Function('return '+funcStr);
								html.push('<td style="border-bottom: 1px solid;border-right: 1px solid;">'+funcTest()(r)+'</td>');
							}
							else{
								html.push('<td style="border-bottom: 1px solid;border-right: 1px solid;">'+r[data_new[s].name]+'</td>');
							}
						}
						html.push('</tr>');
					}
					html.push('</table>');
					html.push(foot);
				}
				//执行隐藏打印区域不需要打印的内容
				var wind = window.open("", "newwin",
						"toolbar=no,scrollbars=yes,menubar=no");
				wind.document.body.innerHTML = headstr +  html.join('') + footstr;
				wind.print();
				wind.close();
			},function(e){},{async:false,timeout:0})
		}*/
		
		
		
		html.push('<div class="dataTables_setting">');
		html.push('	<div class="dataTables_columns">');
		html.push('		<h3 style="margin-top: 0px;">选择打印列</h3>');
		html.push('		<ul>');
		for (var c = $this.data("select") != "none" ? 1 : 0, cLen = _tc.length; c < cLen; c++) {
			if (_tc[c].datatype == "select") continue;
			html.push('		<li>');
			html.push('			<input type="checkbox" id="column_' + _tc[c].name + '" data-name="' + _tc[c].name + '" name="columns" value="' + _tc[c].name + '"' + (_tc[c].hidden == "true" ? '' : ' checked="checked"') + '>');
			html.push('			<label for="column_' + _tc[c].name + '" title="' + _tc[c].label + '">' + _tc[c].label + '</label>');
			html.push('		</li>');
		}
		html.push('		</ul>');
		html.push('		<div class="columns_all">');
		html.push('			<input type="checkbox" id="columns_all">');
		html.push('			<label for="columns_all">全部选择/全部取消</label>');
		html.push('		</div>');
		html.push('	</div>');
		html.push('</div>');
		layer.alert(html.join(""), {
			area: ["600px", "480px"],
			title: "表格打印",
			scrollbar: true,
			btn: ["打印", "取消"],
			success: function(layero, index) {
				var selectall = true;
				$(layero).find("input[name='columns']").each(function() {
					if (!this.checked) {
						selectall = false;
						return false;
					}
				});
				if (selectall) $(layero).find("#columns_all")[0].checked = true;
				$(layero).find("#columns_all").on("click", function(e) {
					if (this.checked) {
						$(layero).find("input[name='columns']").each(function() {
							this.checked = true;
						});
					} else {
						$(layero).find("input[name='columns']").each(function() {
							this.checked = false;
						});
					}
				});
			},
			yes: function(index, layero) {
				//var _tc = $this.data("_tc");
				var co = $(layero).find("input[name='columns']");
				for(var i=0;i<co.length;i++){
					if ($(co).get(i).checked) {
						var c = i + ($this.data("select") != "none" ? 1 : 0);
						data_new.push(_tc[c]);
					}
				}
				var userAgent = navigator.userAgent.toLowerCase(); //取得浏览器的userAgent字符串
				if (userAgent.indexOf("trident") > -1) {
					alert("请使用google或者360浏览器打印");
					layer.close(index);
					return false;
				} else if (userAgent.indexOf('msie') > -1) {
					var onlyChoseAlert = simpleAlert({
						"content" : "请使用Google或者360浏览器打印",
						"buttons" : {
							"确定" : function() {
								onlyChoseAlert.close();
							}
						}
					})
					alert("请使用google或者360浏览器打印");
					layer.close(index);
					return false;
				} else {//其它浏览器使用lodop
					var action = $this.attr("action");
					var data = $this.data("params") || {};
					data.start = 0;
					data.limit = -1;
					$.call(action,data,function(rtn){
						var oldstr = document.body.innerHTML;
						var headstr = "<html><head><title></title></head><body>";
						var footstr = "</body></html>";
						var html = [];
						var rt = rtn.resultset;
						if(rtn.count>0){
							var length_ = data_new.length/100;
							//$this.parents('.portlet').children('.portlet-title').children('.nav-tabs').hasClass("active").text()
							html.push('<div style="text-align: center;width:100%;"><h1>'+$this.parents('.portlet').children('.portlet-title').children('.caption').text()+'</h1></div>');
							html.push('<table style="border-top: 1px solid;border-left: 1px solid;" cellspacing="0" cellpadding="0" width="100%">');
							html.push('<thead>');
							html.push('<tr>')
							for(s in data_new){
								html.push('<th style="border-bottom: 1px solid;border-right: 1px solid;">'+data_new[s].label+'</th>');
							}
							html.push('</thead>');
							html.push('<tbody>');
							for(var i=0; i< rt.length;i++){
								var r = rt[i];
								html.push('<tr>');
								for(s in data_new){
									if(data_new[s].datatype == 'script'){
										var funcStr = "function test(record){"+data_new[s].script+"}";
										var funcTest = new Function('return '+funcStr);
										html.push('<td style="border-bottom: 1px solid;border-right: 1px solid;">'+funcTest()(r)+'</td>');
									}else{
										html.push('<td style="border-bottom: 1px solid;border-right: 1px solid;">'+r[data_new[s].name]+'</td>');
									}
								}
								html.push('</tr>');
							}
							html.push('</tbody>');
							html.push('</table>');
							html.push(foot);
						}
						//执行隐藏打印区域不需要打印的内容
						var wind = window.open("", "newwin",
								"toolbar=no,scrollbars=yes,menubar=no");
						wind.document.body.innerHTML = headstr +  html.join('') + footstr;
						wind.print();
						wind.close();
						layer.close(index);
					},function(e){},{async:true,timeout:0})
				}
			},
			cancel: function(index) {
				layer.close(index);
			}
		});
	},
	"excel_new" : function(foot,page){
		//var $this = $(table);
		var $this = this;
		var _tc_tmp = $this.data("_tc");
		var html = [];
		var data_new = [];
		var _tc = [];
		for (var c in _tc_tmp) {
			_tc.push(_tc_tmp[c]);
		}
		_tc.sort(function(a, b) {
			return a.order - b.order;
		});
		html.push('<div class="dataTables_setting">');
		html.push('	<div class="dataTables_columns">');
		html.push('		<h3 style="margin-top: 0px;">选择导出列</h3>');
		html.push('		<ul>');
		for (var c = $this.data("select") != "none" ? 1 : 0, cLen = _tc.length; c < cLen; c++) {
			if (_tc[c].datatype == "select") continue;
			html.push('		<li>');
			html.push('			<input type="checkbox" id="column_' + _tc[c].name + '" data-name="' + _tc[c].name + '" name="columns" value="' + _tc[c].name + '"' + (_tc[c].hidden == "true" ? '' : ' checked="checked"') + '>');
			html.push('			<label for="column_' + _tc[c].name + '" title="' + _tc[c].label + '">' + _tc[c].label + '</label>');
			html.push('		</li>');
		}
		html.push('		</ul>');
		html.push('		<div class="columns_all">');
		html.push('			<input type="checkbox" id="columns_all">');
		html.push('			<label for="columns_all">全部选择/全部取消</label>');
		html.push('		</div>');
		html.push('	</div>');
		html.push('</div>');
		layer.alert(html.join(""), {
			area: ["600px", "480px"],
			title: "表格导出",
			scrollbar: true,
			btn: ["导出", "取消"],
			success: function(layero, index) {
				var selectall = true;
				$(layero).find("input[name='columns']").each(function() {
					if (!this.checked) {
						selectall = false;
						return false;
					}
				});
				if (selectall) $(layero).find("#columns_all")[0].checked = true;
				$(layero).find("#columns_all").on("click", function(e) {
					if (this.checked) {
						$(layero).find("input[name='columns']").each(function() {
							this.checked = true;
						});
					} else {
						$(layero).find("input[name='columns']").each(function() {
							this.checked = false;
						});
					}
				});
			},
			yes: function(index, layero) {
				var co = $(layero).find("input[name='columns']");
				for(var i=0;i<co.length;i++){
					if ($(co).get(i).checked) {
						var c = i + ($this.data("select") != "none" ? 1 : 0);
						data_new.push(_tc[c]);
					}
				}
				var action = $this.attr("action");
				var data = $this.data("params") || {};
				if(page && (page.limit != -1 || page.limit != 0)){
					data.start = page.start;
					data.limit = page.limit;
				}else{
					//data.limit = $this.dataTable().fnSettings()._iDisplayLength;
					//data.start = $this.dataTable().fnSettings()._iDisplayStart + 1;
					data.start = 0;
					data.limit = -1;
				}
				$.call(action,data,function(rtn){
					var rt = rtn.resultset;
					var fileName = $this.parents('.portlet').children('.portlet-title').children('.caption').text();
					fileName = fileName?fileName:'noname';
					var excelContent = $this.html();
					var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";
					excelFile += "<head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head>";
					excelFile += "<body><table width='10%'  border='1'>";
					//excelFile += (th + excelContent);
					
					excelFile += '<thead><tr>';
					for(s in data_new){
						if(!data_new[s].hidden || data_new[s].hidden == 'false'){
							excelFile += '<th>'+data_new[s].label+'</th>';
						}
					}
					excelFile += '</tr></thead>';
					excelFile += '<tbody>';
					for(var i=0; i< rt.length;i++){
						var r = rt[i];
						excelFile += '<tr>';
						for(s in data_new){
							if(!data_new[s].hidden || data_new[s].hidden == 'false'){
								if(data_new[s].datatype == 'script'){
									var funcStr = "function test(record){"+data_new[s].script+"}";
									var funcTest = new Function('return '+funcStr);
									excelFile += '<td style="vnd.ms-excel.numberformat:@">'+funcTest()(r)+'</td>';
								}
								else if(data_new[s].datatype == 'template'){
									var template = data_new[s].template;
									template = excuteExpressionD(template,r);
									template = getHtmlTagVal(template, data_new[s].name);
									excelFile += '<td style="vnd.ms-excel.numberformat:@">'+template+'</td>'
								}
								else{
									excelFile += '<td style="vnd.ms-excel.numberformat:@">'+(r[data_new[s].name]?r[data_new[s].name]:'')+'</td>';
								}
							}
						}
						excelFile += '</tr>';
					}
					
					excelFile += "</tbody></table>";
					excelFile += (foot?foot:'');
					excelFile += "</body>";
					excelFile += "</html>";
					
					//console.log(excelFile)
					//var link = "data:application/vnd.ms-excel;base64," + window.btoa(unescape(encodeURIComponent(excelFile)));
					var blob = new Blob([excelFile], {type: "data:application/vnd.ms-excel;base64"});  	
					//解决中文乱码问题
					blob =  new Blob([String.fromCharCode(0xFEFF), blob], {type: blob.type});  
					var a = document.createElement("a");
					a.download = fileName + ".xls";
					//a.href = link;
					a.href = window.URL.createObjectURL(blob);
					document.body.appendChild(a);
					a.click();
					document.body.removeChild(a);
					layer.close(index);
				},function(e){},{async:true,timeout:0})
			},
			cancel: function(index) {
				layer.close(index);
			}
		});
	}
})

function excuteExpressionD(expression,map){
	for(var key in map){
		expression = replaceOnlyStr(expression,"\\$\\[" + key + "\\]", map[key] ? map[key] + "" : "");
	}
	return expression;
}

function replaceOnlyStr(str,patten,replacement){
	if (str) {
		return str.replace(new RegExp(patten,"g"), replacement);
	}else if(str.indexOf(patten) < 0){
		return str;
	}else{
		return "";
	}
}

function getHtmlTagVal(input, key){
	if (input) {
		var rows = $(input).find("[name="+key+"]");
		if (rows.size() <= 0) {
			return "";
		}else {
			var str = [];
			rows.each(function(){
				var row = $(this)[0];
				if(row.tagName == 'SELECT'){
					var value_sel = row.getAttribute('value');
					var opts = row.getElementsByTagName('option');
					for(var j=0;j<opts.length;j++){
						var opts_val = opts[j].getAttribute('value');
						if(value_sel == opts_val){
							if(opts[j].innerText) {
								str.push(opts[j].innerText);
							}else if(opts_val) {
								str.push(opts_val);
							}
						}
					}
				}else if(row.tagName == 'INPUT' || row.tagName == 'DIV'){
					var check = (row.getAttribute("type") == "checkbox" 
						|| row.getAttribute("type") == "radio"
						||row.getAttribute("ctype") == "checkbox" 
						|| row.getAttribute("ctype") == "radio");
					if(check && row.tagName == 'DIV'){
						var checkboxs = row.getElementsByClassName("checkbox-inline");
	    				var val = ","+(row.getAttribute('value')?row.getAttribute('value'):'') + ",";
	    				for(var j2=0;j2<checkboxs.length;j2++){
	    					var checkbox = $(checkboxs[j2]).find('[name='+key+']');
	    					if(val.indexOf(","+checkbox[0].getAttribute('value')+",")>=0) {
								if($(checkboxs[j2])[0].innerText) {
									str.push($(checkboxs[j2])[0].innerText);
								}else if(checkbox[0].getAttribute('value')) {
									str.push(checkbox[0].getAttribute('value'));
								}
							}
	    				}
	    			}else if(!check && row.getAttribute("type") != "hidden" && row.tagName == 'INPUT'){
	    				if(row.getAttribute('value')) {
	    					str.push(row.getAttribute('value'));
	        			}
	    			}else if(row.tagName == 'DIV' && row.getAttribute("ctype") != "hidden"){
	    				if(row.innerText) {
		    				str.push(row.innerText);
		    			}
	    			}
				}else if(row.getAttribute("ctype") != "checkbox" && row.getAttribute("ctype") != "radio"){
					if(row.innerText) {
	    				str.push(row.innerText);
	    			}
				}
			})
			return str.join(',');
		}
	}else{
		return "";
	}
}