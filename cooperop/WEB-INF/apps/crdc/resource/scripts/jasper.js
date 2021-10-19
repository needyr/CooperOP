var dtype = "";
var ptype = "";
var srcdom;
function allowDrop(ev) {
	ev.preventDefault();
}
function drag(ev, divdom) {
	srcdom = divdom;
}
function dragadd(ev, divdom) {
	var $srcd = $(divdom);
	dtype = $srcd.attr("stype");
	ptype = $srcd.parent().attr("stype");
}
	function drop(e,_this){
		if(ptype != ""){
			if(ptype == "row"){
				var p = [];
				p.push('<div class="row '+dtype+'" jtype="'+dtype+'" style="height:60px;" ondragover="allowDrop(event)" ondrop="drop(event,this)" data-toggle="context" data-target="#context-menu">');
				p.push('</div>');
				$("#_uicontent_").append(p.join(''));
			}else if(ptype == "text" && $(_this).hasClass("row")){
				var p = [];
				var x , y;
				if(e.layerX>40){
					x = e.layerX-40;
				}else{
					x = 0;
				}
				if(e.layerY>15){
					y = e.layerY-15;
				}else{
					y = 0;
				}
				p.push('<div class="'+dtype+'" jtype="'+dtype+'" style="height:30px;width:80px;position:absolute;left:'+x+'px;top:'+y+'px; border: 1px; padding:1px;border-style: solid;"');
				p.push(' draggable="true" ondragstart="drag(event, this)" ondragover="allowDrop(event)" ondrop="drop(event,this)" data-toggle="context" data-target="#context-menu">');
				p.push(dtype);
				p.push('</div>');
				$(_this).append(p.join(''));
			}
			
		}else{
			if(!srcdom){
				return ;
			}
			var x=$(srcdom).outerWidth();
			var y=$(srcdom).outerHeight();
			if(e.layerX > x/2){
				x = e.layerX-x/2;
			}else{
				x = 0;
			}
			if(e.layerY > y/2){
				y = e.layerY-y/2;
			}else{
				y = 0;
			}
			console.log($(srcdom));
			console.log($(_this));
			if(srcdom.parentElement != _this && $(_this).hasClass("row")){
				$(_this).append($(srcdom));
			}
			$(srcdom).css("left",x).css("top",y); 
			
		}
		
		$('div[jtype]').contextmenu({
			  target:'#context-menu',
			  before: function(e,context) {
				  var jtype = $(context).attr("jtype");
				  $("#context-menu").find("[atype=report]").parent().hide();
				  if($(context).attr("class") == "row"){
					  $("#context-menu").find("[atype=expression]").parent().hide();
					  $("#context-menu").find("[atype=pattern]").parent().hide();
					  $("#context-menu").find("[atype=padding]").parent().show();
				  }else if(jtype == "page"){
					  $("#context-menu").find("[atype=report]").parent().show();
					  $("#context-menu").find("[atype=expression]").parent().hide();
					  $("#context-menu").find("[atype=pattern]").parent().hide();
					  $("#context-menu").find("[atype=padding]").parent().hide();
				  }else if(jtype == "staticText"){
					  $("#context-menu").find("[atype=expression]").parent().show();
					  $("#context-menu").find("[atype=padding]").parent().show();
					  $("#context-menu").find("[atype=pattern]").parent().hide();
				  }else if(jtype == "textField"){
					  $("#context-menu").find("[atype=expression]").parent().show();
					  $("#context-menu").find("[atype=pattern]").parent().show();
					  $("#context-menu").find("[atype=padding]").parent().show();
				  }
				  return true;
			  },
			  onItem: function(context,e) {
				  var atype = $(e.toElement).attr("atype");
				  var jtype = $(context).attr("jtype");
				  if(atype == "report"){
					  $.modal("setting.html", "report", {
						  height:"400px",
						  width:"800px",
						  name: context.attr("name"),
						  pageHeight: context.css("height").split("px")[0],
						  pageWidth: context.css("width").split("px")[0],
						  topMargin: context.css("margin-top").split("px")[0],
						  rightMargin: context.css("margin-right").split("px")[0],
						  bottomMargin: context.css("margin-bottom").split("px")[0],
						  leftMargin: context.css("margin-left").split("px")[0],
						  callback: function(rtn) {
							if(rtn){
								$(context).attr("name",rtn.name);
								$(context).css("height",rtn.pageHeight);
								$(context).css("width",rtn.pageWidth);
								$(context).css("margin-top",rtn.topMargin);
								$(context).css("margin-right",rtn.rightMargin);
								$(context).css("margin-bottom",rtn.bottomMargin);
								$(context).css("margin-left",rtn.leftMargin);
							}
						  }
					  });
				  }else if(atype == "expression"){
					  $.modal("settingtext.html", "expression", {
						  textval : $(context).html(),
						  jtype:jtype,
						  fieldtype:$(context).attr("fieldtype"),
						  classtype:$(context).attr("classtype"),
						  callback: function(rtn) {
							if(rtn){
								$(context).html(rtn.textval);
								$(context).attr("fieldtype",rtn.fieldtype);
								$(context).attr("classtype",rtn.classtype);
							}
						  }
					  });
				  }else if(atype == "pattern"){
					  
				  }else if(atype == "padding"){
					  $.modal("settingborder.html", "padding", {
						  height:"400px",
						  width:"800px",
						  padding_top: context.css("padding-top").split("px")[0],
						  padding_bottom: context.css("padding-bottom").split("px")[0],
						  padding_left: context.css("padding-left").split("px")[0],
						  padding_right: context.css("padding-right").split("px")[0],
						  border_top: context.css("border-top").split("px")[0],
						  border_bottom: context.css("border-bottom").split("px")[0],
						  border_left: context.css("border-left").split("px")[0],
						  border_right: context.css("border-right").split("px")[0],
						  height_: context.css("height").split("px")[0],
						  width_: context.css("width").split("px")[0],
						  callback: function(rtn) {
							if(rtn){
								$(context).css("padding-top",rtn.padding_top);
								$(context).css("padding-bottom",rtn.padding_bottom);
								$(context).css("padding-left",rtn.padding_left);
								$(context).css("padding-right",rtn.padding_right);
								$(context).css("border-top",rtn.border_top);
								$(context).css("border-bottom",rtn.border_bottom);
								$(context).css("border-left",rtn.border_left);
								$(context).css("border-right",rtn.border_right);
								$(context).css("height",rtn.height_);
								$(context).css("width",rtn.width_);
							}
						  }
					  });
				  }else if(atype == "delete"){
					  $(context).remove();
				  }
			  }
			});
		dragclear();
	}
	function dragclear(){
		dtype = "";
		ptype = "";
		srcdom = "";
	}
function deployjasper(){
	var p = {};
	p.name=$("#_uicontent_").attr("name");
	p.language = "java";
	p.style = $("#_uicontent_").attr("style");
	p.childs = [];
	p.pf = [];
	$("#_uicontent_").children("div[jtype]").each(function(){
		var $trow = $(this);
		var row = {};
		row.type = $trow.attr("jtype");
		row.style = $trow.attr("style");
		row.childs = [];
		$trow.children("div[jtype]").each(function(){
			var $tch = $(this);
			var ch = {};
			var pf = {};
			ch.type = $tch.attr("jtype");
			ch.fieldtype = $tch.attr("fieldtype");
			ch.classtype = $tch.attr("classtype");
			pf.fieldtype = $tch.attr("fieldtype");
			pf.classtype = $tch.attr("classtype");
			ch.style = $tch.attr("style");
			ch.value = $tch.html();
			row.childs.push(ch);
			p.pf.push(pf);
		});
		row.childs.join(",");
		p.childs.push(row);
	});
	$.call("crdc.designer.designer.createJasper", {jdata:$.toJSON(p)}, function(data) {
		
	});
}
function savejasper(){
	
	$.call("crdc.designer.designer.saveJasperHtml", {name : $("#_uicontent_").attr("name"),
		hdata: $("#_uicontent_")}, function(data) {
		
	});
}
