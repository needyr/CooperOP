$.fn.extend({
	"ccinit_selecttree": function() {
		var $this = this;
		var _value = this[0].getAttribute("value");
		//$this[0].removeAttribute("value");
		$this.data("callb", function(){});
		$this.on({
			"focus": function() {
				if ($this.is("[readonly]")) {
					$this.data("defaultValue", $(this).val());
				}
			},
			"change": function(event) {
				if ($this.is("[readonly]")) {
					$this.val($(this).data("defaultValue"));
					event.stopImmediatePropagation();
				}
			}
		});
		var html = [];
		html.push('<div id="menuContent_'+$this.attr("name")+'" class="menuContent" style="display:none; position: absolute;">');
		html.push('	<ul id="tree_'+$this.attr("name")+'" class="ztree" style="margin-top:0; height: 300px;">');
		html.push('</ul>');
		html.push('</div>');
		$("body").append(html.join(''));
		var setting = {
				check: {
					enable: true,
					chkStyle: $this.attr("select") || "radio"
				},
				view: {
					dblClickExpand: false,
					showIcon: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick,
					onCheck: onCheck
				}
			};
		if($this.attr("select") == "radio"){
			setting.check.radioType = "all";
		}else{
			setting.check.chkboxType = { "Y" : "s", "N" : "ps" };

		}
		
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("tree_"+$this.attr("name"));
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("tree_"+$this.attr("name")),
			nodes = zTree.getCheckedNodes(true),
			v = "",
			va = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				va += nodes[i].id + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (va.length > 0 ) va = va.substring(0, va.length-1);
			$this.attr("value", v);
			var ov = $this.parent().find("."+$this.attr("name")+"_v").attr("data-v");
			$this.parent().find("."+$this.attr("name")+"_v").attr("data-v", va);
			$this.data("callb")();
		}
		function showMenu() {
			$("#tree_"+$this.attr("name")).html('');
			var va = ","+$this.parent().find("input[name='"+$this.attr("name")+"_v']").val()+",";
			var zNodes = [];
			if ($this.attr("action")) {
				$this.data("params", $this.attr("params") ? $.parseJSON($this.attr("params")) : ($this.data("params") || {}));
				$.call($this.attr("action"), $this.data("params"), function(rtn) {
					for (var i in rtn.resultset) {
						var n = {
							id: rtn.resultset[i][$this.attr("nodeid")], 
							pId: rtn.resultset[i][$this.attr("pid")], 
							name: rtn.resultset[i][$this.attr("nodename")]
						};
						if(va.indexOf(","+n.id+",") > -1){
							n.checked = true;
						}
						zNodes.push(n);
					}
					$.fn.zTree.init($("#tree_"+$this.attr("name")), setting, zNodes);
				});
			}
			
			var cityOffset = $this.offset();
			var w = $this.outerWidth();
			$("#menuContent_"+$this.attr("name")).css({width: w,left:cityOffset.left + "px", top:cityOffset.top + $this.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent_"+$this.attr("name")).fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.name == $this.attr("name") || event.target.id == "menuContent_"+$this.attr("name") 
				|| $(event.target).parents("#menuContent_"+$this.attr("name")).length>0)) {
				hideMenu();
			}
		}
		$this.on("click", showMenu);
		
		if ($this.attr("nextfocusfield")) {
			if ($this.is("[intable]")) {
				$this.on("blur", function() {
					$("#" + $this.attr("trid")).find("[name='"+$this.attr("nextfocusfield")+"']").focus();
				});
			}else{
				$this.on("blur", function() {
					$("[name='"+$this.attr("nextfocusfield")+"']").focus();
				});
			}
		}
		this.attr("cinited", "cinited");
	},
	"changetree": function(callback) {
		var $this = this;
		$this.data("callb", callback);
	},
	"params_selecttree": function(params) {
		var $this = this;
		var tps = $this.attr("params") ? $.parseJSON($this.attr("params")) : {};
		$this.data("params", $.extend(true, tps, params));
	},
	"getData_selecttree": function(params) {
		var $this = this;var d={};
		d[$this.attr("name")] = $this.parent().find("."+$this.attr("name")+"_v").attr("data-v");
		return d;
	},
	"refresh_selecttree": function(params) {
		var $this = this;
		
	}
});
