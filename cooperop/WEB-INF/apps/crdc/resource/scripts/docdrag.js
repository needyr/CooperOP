var designer;
var dtype = "";
var ptype = "";
var srcdom = "";
var srcRowdom = "";
var srcFRowdom = "";
$(document).ready(function() {
	$("#_uicontent_").closest(".form-horizontal").removeClass("form-horizontal");
	designer = $("#_uicontent_").ccdesigner();
});
function allowDrop(ev) {
	ev.preventDefault();
}
function drag(ev, divdom) {
	srcdom = divdom;
}
function dragrow(ev, divdom) {
	srcRowdom = divdom;
}
function dragfrow(ev, divdom) {
	srcFRowdom = divdom;
}
function dragadd(ev, divdom) {
	var $srcd = $(divdom);
	dtype = $srcd.attr("stype");
	ptype = $srcd.parent().attr("stype");
}
function dropRow(divdom) {//放到行内
	if(ptype != ""){//表示从左边拖过来新增的
		var cc;
		if(dtype=="table"){
			var t = designer.page.get(divdom);
			cc = t.append(dtype,{label:"表格",cols:"4",color:"grey-silver",
				crid:new Date().getTime()
//				ondrop:"dropTable(this)",
//				ondragover:"allowDrop(event)"	
			});
			cc.append("toolbar",{tooltype:"tftool"},"tool2form");
		}else if(dtype=="form"){
			var t = designer.page.get(divdom);
			cc = t.append(dtype, {
				label:"表单",icon:"fa fa-list-alt",color:"grey-silver",border:"1",cols:"4",
				crid:new Date().getTime(),
				ondrop:"dropForm(this)",
				ondragover:"allowDrop(event)"
			});
			var r = cc.append("row",{
				type:"frow",
				crid:new Date().getTime(),
				draggable:"true",
				ondragstart:"dragfrow(event, this)",
				ondrop:"dropFormRow(this)",
				ondragover:"allowDrop(event)"
				},"row2form");
		}else if(dtype=="tabpanel"){
			var t = designer.page.get(divdom);
			cc = t.append(dtype, {
				crid:new Date().getTime(),
				color:"grey-silver",
				cols:"4",
				ondrop:"dropTabPanel(this)",
				ondragover:"allowDrop(event)"
			});
		}
	}else{
		var d = $(divdom);
		if($.trim($(srcRowdom).attr("class")) =="row-fluid" && $.trim($(divdom).attr("class"))=="row-fluid"
			&& $(srcRowdom).attr("type")!="frow" && $(divdom).attr("type")!="frow"){
			$(srcRowdom).insertBefore(divdom);
		//	console.log("换外行");
			moveContents(srcRowdom,divdom,"insert");
		}
	}
	dragclear();
}
//function dropTable(divdom) {
//	if(ptype != ""){//表示从左边拖过来新增的
//		if(dtype == "button"){
//			var cc = designer.page.get(divdom);
//			var t1 = $(divdom).find(".portlet-title .tools");
//			var t = cc.get(t1[0]);
//			t.append(dtype, {
//				label:"保存",icon:"fa fa-save",size:"btn-sm"
//			});
//			//cc.append("button",{"label":"保存","icon":"fa fa-save"});
//		}
//	}
//}
function dropToolbar(divdom) {
	if(ptype != ""){//表示从左边拖过来新增的
		if(dtype == "button"){
			var t = designer.page.get(divdom);
			t.append(dtype, {
				label:"保存",icon:"fa fa-save",size:"btn-sm",crid:new Date().getTime()
			});
		}
	}
	dragclear();
}
function dropPageToolbar(divdom) {
	if(ptype != ""){//表示从左边拖过来新增的
		if(dtype == "button"){
			var t = designer.page.get(divdom);
			t.append(dtype, {
				label:"保存",icon:"fa fa-save",size:"btn-sm",crid:new Date().getTime(),type:"link"
			},"button2pagetoolbar");
		}
	}
	dragclear();
}
function dropFormRow(divdom) {//放到from的行内
	if(ptype != ""){//表示从左边拖过来新增的
		var t = designer.page.get(divdom);
		if(dtype == "row"){//拖行到form
			var r = t.parent.append("row",{
				crid:new Date().getTime(),
				type:"frow",
				draggable:"true",
				ondragstart:"dragfrow(event, this)",
				ondrop:"dropFormRow(this)",
				ondragover:"allowDrop(event)"
				},"row2form");
		}else if(dtype == "row_hidden"){//拖行到form
			var r = t.parent.append("row",{
				crid:new Date().getTime(),
				type:"frow",
				ishidden:"ishidden",
				draggable:"true",
				ondragstart:"dragfrow(event, this)",
				ondrop:"dropFormRow(this)",
				ondragover:"allowDrop(event)"
				},"row2form");
		}else{//拖行内元素到form
			var cc;
			var cols ="";
			if(dtype=="richeditor"){
				cols = 4;
			}
			if(dtype=="table"){//
			}else {
				cc = t.append(dtype, {
					label:dtype,
					cols:cols,
					crid:new Date().getTime(),
					draggable:"true",
					ondragstart:"drag(event, this)",
					ondrop:"dropmove(this)",
					ondragover:"allowDrop(event)"
				});
				if(dtype == "image"){
					cc.attr("accept","image/*");
				}
			}
		}
	}else{//表示拖动已有元素
		if(srcdom != ""){//拖的是行内元素
			$(divdom).append(srcdom);
			//console.log("换行内到行");
			moveContents(srcdom,divdom,"append");
		}else if(srcFRowdom != ""){//拖的是行
			//console.log("换行");
			moveContents(srcFRowdom,divdom,"insert");
			$(srcFRowdom).insertBefore(divdom);
		}
	}
	dragclear();
}
function dropTabPanel(divdom){
	if(ptype != ""){//表示从左边拖过来新增的
		var t = designer.page.get(divdom);
		var active = "true";
		if(t.contents.length>0){
			var active = "false";
		}
		if(dtype == "button"){//拖button到tabpanel的active页面
			var tools = designer.page.get($(t).find(".tools"));//获取tools
			tools.append("button",{label:"保存",crid:new Date().getTime()});
		}else if(dtype == "button"){
			
		}else if(dtype == "table"){
			cc = t.appendAndRepaint(dtype,{label:"表格",cols:"4",color:"grey-silver",
				crid:new Date().getTime(),active:active
//				ondrop:"dropTable(this)",
//				ondragover:"allowDrop(event)"
			});
			t.element.ccinit();
		}else if(dtype == "form"){
			cf = t.appendAndRepaint(dtype, {
				label:"表单",icon:"fa fa-list-alt",color:"grey",border:"1",cols:"4",
				crid:new Date().getTime(),active:active,
				ondrop:"dropForm(this)",
				ondragover:"allowDrop(event)"
			});
			var r = cf.append("row",{
				type:"frow",
				crid:new Date().getTime(),
				draggable:"true",
				ondragstart:"dragfrow(event, this)",
				ondrop:"dropFormRow(this)",
				ondragover:"allowDrop(event)"
				},"row2form");
			t.element.ccinit();
		}
	}
	dragclear();
}
function dropForm(divdom) {//放到from的行内
	if(ptype != ""){//表示从左边拖过来新增的
		var t = designer.page.get(divdom);
		if(dtype == "row"){//拖行到form
			var r = t.append("row",{
				type:"frow",
				crid:new Date().getTime(),
				draggable:"true",
				ondragstart:"dragfrow(event, this)",
				ondrop:"dropFormRow(this)",
				ondragover:"allowDrop(event)"
				},"row2form");
		}else if(dtype == "button"){//拖行内元素到form，先建一个行，再将元素放入行中
			var tools = t.get($(t).find(".tools"));
			tools.append("button",{label:"保存",crid:new Date().getTime()});
		}else if(dtype == "row_hidden"){
			var r = t.append("row",{
				type:"frow",
				ishidden:"ishidden",
				isdesign:"true",
				crid:new Date().getTime(),
				draggable:"true",
				ondragstart:"dragfrow(event, this)",
				ondrop:"dropFormRow(this)",
				ondragover:"allowDrop(event)"
				},"row2form");
		}else{//拖行内元素到form，先建一个行，再将元素放入行中
			//var r = t.append("row",{type:"frow"},"row2form");
			//dropFormRow(r);
		}
	}else{
		
	}
	dragclear();
}
function dropmove(divdom) {
	if(ptype != ""){//表示从左边拖过来新增的
		//$(srcdom).insertBefore(divdom);
		if(ptype == "control"){
			var cc;
			var crid = new Date().getTime();
			cc = designer.page.get(divdom).parent.append(dtype, {
				color: "grey",
				crid: crid,
				draggable:"true",
				ondragstart:"drag(event, this)",
				ondrop:"dropmove(this)",
				ondragover:"allowDrop(event)",
				label:dtype
			},null,designer.page.get(divdom));
			moveContents($("[crid='"+crid+"'"),divdom,"insert");
			dragclear();
		}
	}else{
		//行内元素换位置
		var sp = $(srcdom).parent();
		var dp = $(divdom).parent();
		if((sp.attr("class") == dp.attr("class")) &&
				(sp[0].tagName == dp[0].tagName)){
			//console.log("换行内")
			moveContents(srcdom,divdom,"insert");
			$(srcdom).insertBefore(divdom);
		}
		dragclear();
	}
}
function dropPage(divdom) {
	if(ptype != ""){//表示从左边拖过来新增的
		var cc;
		if(dtype != ""){
			if(dtype=="toolbar"){
				cc = designer.page.append(dtype, {
					color: "grey"
				},"tool2page");
				
			}else if(dtype=="row"){
				cc = designer.page.append(dtype,{
					crid:new Date().getTime(),
					draggable:"true",
					ondragstart:"dragrow(event, this)",
					ondrop:"dropRow(this)",
					ondragover:"allowDrop(event)"
					});
			}
		}
	}else{//表示是已经有的元素拖动，如果是行元素就放到页面最后；如果是行内元素，则表示是要删除
		
	}
	dragclear();
}
function moveContents(srcdiv,divdom,type){
	if(srcdiv!="" && divdom!=""){
		var sd = designer.page.get(srcdiv);
		var dd = designer.page.get(divdom);
		$(sd.parent.contents).each(function(index, obj) {
			if(obj ==sd){
				sd.parent.contents.splice(index,1);
			}
		});
		if(type=="insert"){//换位置
			$(dd.parent.contents).each(function(index, obj) {
				if(obj == dd){
					dd.parent.contents.splice(index, 0, sd);
				}
			});
		}else if(type=="append"){//目标是行，则需要push
			dd.contents.push(sd);
		}
	}
}
function dragclear(){
	dtype = "";
	ptype = "";
	srcdom = "";
	srcRowdom = "";
	srcFRowdom = "";
	$("[crid]").attr("data-toggle","context");
}