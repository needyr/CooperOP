$.fn.extend({
	"flowdesigner" : function() {
		var Flow = function() {
			this.id = null;
			this.name = null;
			
			this.version = 0;
			this.nodes = {};
			this.nodeCount = 0;
			this.routes = {};
			this.routeCount = 0;
			
			this.getData = function() {
				var d = {};
				for (var k in this) {
					if (this[k] == null || this[k] == undefined) {
						d[k] = this[k];
					} else if (!$.isFunction(this[k])) {
						if (k == 'nodes' || k == 'routes') {
							d[k] = [];
							for (var k1 in this[k]) {
								d[k].push(this[k][k1].getData());
							}
						} else {
							d[k] = this[k];
						}
					}
				}
				return d;
			}
			
			this.getSData = function() {
				//snaker
				this.name = null;  //组件名称，模型内名称唯一
				this.displayName = null;  //组件中文显示名称，方便阅读
				this.preInterceptors = null;  //前置拦截器
				this.postInterceptors = null;  //后置拦截器
				
				this.expireTime	= null;  //期望完成时间，设置表达式变量由参数传递
				this.instanceUrl = null;  //流程定义列表页面直接启动流程实例的URL
				this.instanceNoClass = null;  //流程实例编号生成类
			}
			
			this.draw = function(canvas) {
				var canvas_context = canvas[0].getContext("2d");
				canvas_context.font=FLOW_STYLE.flow_font;
				canvas_context.fillStyle=FLOW_STYLE.flow_color;
				canvas_context.textAlign=FLOW_STYLE.textAlign;
				canvas_context.textBaseline=FLOW_STYLE.baseLine;
				canvas_context.fillText(this.name, FLOW_STYLE.x, FLOW_STYLE.y);
				var w = canvas_context.measureText(this.name).width;
				canvas_context.font=FLOW_STYLE.version_font;
				canvas_context.fillStyle=FLOW_STYLE.version_color;
				canvas_context.textAlign=FLOW_STYLE.textAlign;
				canvas_context.textBaseline=FLOW_STYLE.baseLine;
				canvas_context.fillText(" V" + this.version, FLOW_STYLE.x + w, FLOW_STYLE.y);

				for (var i in this.nodes) {
					this.nodes[i].draw(canvas);
				}
				for (var i in this.routes) {
					this.routes[i].draw(canvas);
				}
			};
			this.createNode = function(nodetype, x, y) {
				var node_type = null;
				for (var i = 0; i < DEFAULT_NODE_TYPES.length ; i ++) {
					if (nodetype == DEFAULT_NODE_TYPES[i].id) {
						node_type = DEFAULT_NODE_TYPES[i];
						break;
					}
				}
				if (node_type == null) {
					alert("对不起没有找到指定类型的节点。");
					return;
				}
				if (node_type.limit > 0) {
					var n = 0;
					for (var i in this.nodes) {
						if (this.nodes[i].type.id == node_type.id) {
							n ++;
						}
					}
					if (n >= node_type.limit) {
						return;
					}
				}
		 		var node = new Node();
				node.type = node_type;
				node.id = node_type.id + (this.nodeCount + 1);
				node.name = node_type.name + (this.nodeCount + 1);
				node.x = (x - NODE_STYLE.width / 2 < 2) ? 2 : (x - NODE_STYLE.width / 2);
				node.y = (y - NODE_STYLE.height / 2 < 2) ? 2 : (y - NODE_STYLE.height / 2);
				this.addNode(node);
			}

			this.addNode = function(node) {
				this.nodes[node.id] = node;
				this.nodeCount ++;
			}

			this.deleteNode = function(nodeid) {
				delete this.nodes[nodeid];
				for (var i in this.routes) {
					if (this.routes[i].fromNode.id == nodeid || this.routes[i].toNode.id == nodeid) {
						this.deleteRoute(this.routes[i].rid);
					}
				}
			}

			this.addRoute = function(route) {
				//fromNode、toNode
				for (var i in this.routes) {
					if (this.routes[i].fromNode == route.fromNode && this.routes[i].toNode == route.toNode) {
						alert("重复流向！");
						return;
					}
				}
				this.routes[route.rid] = route;
				this.routeCount ++;
			}
			
			this.deleteRoute = function(routerid) {
				delete this.routes[routerid];
			}
			
			this.autoLayout = function() {
				var start_node = null;
				for (var i in this.nodes) {
					if (this.nodes[i].type.id == 'start') {
						start_node = this.nodes[i];
						break;
					}
				}
				if (start_node == null) {
					alert("必须具备开始节点!");
					return;
				}

				for (var i in this.nodes) {
					this.nodes[i].x = 0;
					this.nodes[i].y = 0;
				}

				start_node.x = AUTO_LAYOUT.x;
				start_node.y = AUTO_LAYOUT.y;

				var maxpoint = this.sortChild(start_node);
				var n = 0;
				for (var i in this.nodes) {
					var node = this.nodes[i];
					if (node.x == 0 && node.y == 0) {
						if (node.type.id == 'end') {
							node.x = maxpoint.x + 2 * AUTO_LAYOUT.x_space;
							node.y = AUTO_LAYOUT.y;
						}
						else {
							node.x = maxpoint.x + AUTO_LAYOUT.x_space;
							node.y = AUTO_LAYOUT.y + n * AUTO_LAYOUT.y_space;
							n ++;
						}
					}
				}

			};

			this.sortChild = function(node) {
				var maxpoint = {"x": AUTO_LAYOUT.x, "y": AUTO_LAYOUT.y};
				var n = 0;
				for (var i in this.routes) {
					var route = this.routes[i];
					if (route.fromNode.id == node.id) {
						var child = this.nodes[route.toNode.id];
						if (child.x == 0 || child.x < node.x + (n * AUTO_LAYOUT.x_space)) {
							child.x = node.x + (n * AUTO_LAYOUT.x_space);
							n ++;
						}
						if (child.y == 0 || child.y < node.y + AUTO_LAYOUT.y_space) {
							child.y = node.y + AUTO_LAYOUT.y_space;
						}

						if (child.x > maxpoint.x) {
							maxpoint.x = child.x;
						}
						if (child.y > maxpoint.y) {
							maxpoint.y = child.y;
						}

						var childmax = this.sortChild(child);

						if (childmax.x > maxpoint.x) {
							maxpoint.x = childmax.x;
						}
						if (childmax.y > maxpoint.y) {
							maxpoint.y = childmax.y;
						}
					}
				}
				return maxpoint;
			}
		}
		
		var Node = function() {
			this.type = null;  //task、custom、fork、join、start、end、sub-process、decision
			this.id = null;
			this.name = null; 

			this.getData = function() {
				var d = {};
				for (var k in this) {
					if (!$.isFunction(this[k])) {
						if (k == 'type') {
							d[k] = this[k].id;
						} else {
							d[k] = this[k];
						}
					}
				}
				return d;
			}

			this.getSData = function() {
				//snaker
				this.name = null;  //组件名称，模型内名称唯一
				this.displayName = null;  //组件中文显示名称，方便阅读
				this.preInterceptors = null;  //前置拦截器
				this.postInterceptors = null;  //后置拦截器
				
				//task
				this.form = null;  //用户参与的表单任务对应的URL
				this.assignee = null;  //任务参与者变量
				this.assignmentHandler = null; //任务参与者处理类
				this.taskType = "Major"; //任务类型（主办Major/协办Aidant）
				this.performType = "ANY"; //任务参与类型（针对多个参与者），ANY为其中一个参与者完成即往下流转；ALL为所有参与者完成才往下流转
				this.reminderTime = 0;  //任务提醒时间
				this.reminderRepeat = 0;  //提示重复次数
				this.expireTime = 0; //期望完成时间
				this.autoExecute = "N"; //超时是否自动执行
				this.callback = null; //自动执行的回调设置
				
				//custom(auto)
				this.clazz = null; //自定义节点的Java类路径，两种方式：1.实现IHandler接口，实现接口时不需要设置下面三个属性。2.无接口实现的普通java类，需要设置下面方法名称、参数属性
				this.methodName = null; //定义需要执行的java类的方法名称
				this.args = null; //定义传递的参数表达式
				this.vars = null; //定义返回值变量名称
				
				//decision
				this.expr = null; //决策选择表达式
				this.handleClass = null;  //决策选择的处理类，实现DecisionHandler接口
				
				//fork/join
				
			}
			
			
			this.x = 0;
			this.y = 0;
			this.level = 0;
			this.inRoute = 0;
			this.outRoute = 0;
			this.draw = function(canvas) {
				var canvas_context = canvas[0].getContext("2d");
				/*
				//前置锚点
				if (!this.type.isStart)
				{
					canvas_context.beginPath();
					canvas_context.fillStyle = NODE_STYLE.PREPOSITION.color;
					canvas_context.arc(this.x + NODE_STYLE.PREPOSITION.x + NODE_STYLE.PREPOSITION.r, this.y + NODE_STYLE.PREPOSITION.y + NODE_STYLE.PREPOSITION.r, NODE_STYLE.PREPOSITION.r, 0*Math.PI, 2*Math.PI);
					canvas_context.fill();
				}
				*/

				//节点主体
				{
					//渐变色
					var gradient=canvas_context.createLinearGradient(this.x + NODE_STYLE.RECTANGLE.x, this.y + NODE_STYLE.RECTANGLE.y, this.x + NODE_STYLE.RECTANGLE.x, this.y + NODE_STYLE.RECTANGLE.y +NODE_STYLE.height);
					for (var i = 0; i < NODE_STYLE.RECTANGLE.gradient.length; i++)
					{
						gradient.addColorStop(NODE_STYLE.RECTANGLE.gradient[i].stop, NODE_STYLE.RECTANGLE.gradient[i].color);
					}

					//圆角矩形
					canvas_context.beginPath();
					canvas_context.lineWidth=NODE_STYLE.RECTANGLE.lineWidth;
					canvas_context.strokeStyle=NODE_STYLE.RECTANGLE.lineColor;
					canvas_context.fillStyle=gradient;
					canvas_context.moveTo(this.x + NODE_STYLE.RECTANGLE.x, this.y + NODE_STYLE.RECTANGLE.y + NODE_STYLE.RECTANGLE.round);
					// ◤
					canvas_context.arc(this.x + NODE_STYLE.RECTANGLE.x + NODE_STYLE.RECTANGLE.round, this.y + NODE_STYLE.RECTANGLE.y + NODE_STYLE.RECTANGLE.round, NODE_STYLE.RECTANGLE.round, 1*Math.PI, 1.5*Math.PI);
					//   ￣
					canvas_context.lineTo(this.x + NODE_STYLE.RECTANGLE.x + NODE_STYLE.RECTANGLE.width - NODE_STYLE.RECTANGLE.round, this.y + NODE_STYLE.RECTANGLE.y);
					//      ◥
					canvas_context.arc(this.x + NODE_STYLE.RECTANGLE.x + NODE_STYLE.RECTANGLE.width - NODE_STYLE.RECTANGLE.round, this.y + NODE_STYLE.RECTANGLE.y + NODE_STYLE.RECTANGLE.round, NODE_STYLE.RECTANGLE.round, 1.5*Math.PI, 2*Math.PI);
					//     ｜
					canvas_context.lineTo(this.x + NODE_STYLE.RECTANGLE.x + NODE_STYLE.RECTANGLE.width, this.y + NODE_STYLE.RECTANGLE.y + NODE_STYLE.RECTANGLE.height - NODE_STYLE.RECTANGLE.round);
					//     ◢
					canvas_context.arc(this.x + NODE_STYLE.RECTANGLE.x + NODE_STYLE.RECTANGLE.width - NODE_STYLE.RECTANGLE.round, this.y + NODE_STYLE.RECTANGLE.y + NODE_STYLE.RECTANGLE.height - NODE_STYLE.RECTANGLE.round, NODE_STYLE.RECTANGLE.round, 0*Math.PI, 0.5*Math.PI);
					//  __
					canvas_context.lineTo(this.x + NODE_STYLE.RECTANGLE.x + NODE_STYLE.RECTANGLE.round, this.y + NODE_STYLE.RECTANGLE.y + NODE_STYLE.RECTANGLE.height);
					// ◣
					canvas_context.arc(this.x + NODE_STYLE.RECTANGLE.x + NODE_STYLE.RECTANGLE.round, this.y + NODE_STYLE.RECTANGLE.y + NODE_STYLE.RECTANGLE.height - NODE_STYLE.RECTANGLE.round, NODE_STYLE.RECTANGLE.round, 0.5*Math.PI, 1*Math.PI);
					// |
					canvas_context.closePath();
					canvas_context.stroke();
					canvas_context.fill();

					//节点类型
					canvas_context.font=NODE_STYLE.TITLE.font;
					canvas_context.fillStyle=NODE_STYLE.TITLE.color;
					canvas_context.textAlign=NODE_STYLE.TITLE.textAlign;
					canvas_context.textBaseline=NODE_STYLE.TITLE.baseLine;
					canvas_context.fillText(this.type.name,this.x + NODE_STYLE.TITLE.x,this.y + NODE_STYLE.TITLE.y);
					//图标
					canvas_context.font=NODE_STYLE.ICON.font;
					canvas_context.fillStyle=NODE_STYLE.ICON.color;
					canvas_context.textAlign=NODE_STYLE.ICON.textAlign;
					canvas_context.textBaseline=NODE_STYLE.ICON.baseLine;
					canvas_context.fillText(this.type.icon,this.x + NODE_STYLE.ICON.x,this.y + NODE_STYLE.ICON.y);
					//节点名称
					canvas_context.font=NODE_STYLE.TEXT.font;
					canvas_context.fillStyle=NODE_STYLE.TEXT.color;
					canvas_context.textAlign=NODE_STYLE.TEXT.textAlign;
					canvas_context.textBaseline=NODE_STYLE.TEXT.baseLine;
					canvas_context.fillText(this.name,this.x + NODE_STYLE.TEXT.x,this.y + NODE_STYLE.TEXT.y);


				}

					//后置锚点
				if (!this.type.isEnd)
				{
					canvas_context.beginPath();
					canvas_context.fillStyle=NODE_STYLE.POSTPOSITION.color;
					canvas_context.moveTo(this.x + NODE_STYLE.POSTPOSITION.x,this.y + NODE_STYLE.POSTPOSITION.y);
					canvas_context.lineTo(this.x + NODE_STYLE.POSTPOSITION.x + 2 * NODE_STYLE.POSTPOSITION.r,this.y + NODE_STYLE.POSTPOSITION.y);
					canvas_context.lineTo(this.x + NODE_STYLE.POSTPOSITION.x + NODE_STYLE.POSTPOSITION.r,this.y + NODE_STYLE.POSTPOSITION.y + NODE_STYLE.POSTPOSITION.r * 2);
					canvas_context.closePath();
					canvas_context.fill();
				}
			};
		}
		
		var Route = function() {
			this.rid = null; //组件id，模型内id唯一
			this.id = null;
			this.name = null;  
			
			this.getData = function() {
				var d = {};
				for (var k in this) {
					if (this[k] == null || this[k] == undefined) {
						d[k] = this[k];
					} else if (!$.isFunction(this[k])) {
						if (k == 'fromNode' || k == 'toNode') {
							d[k] = this[k].id;
						} else {
							d[k] = this[k];
						}
					}
				}
				return d;
			}
			this.getSData = function() {
				//snaker
				this.name = null;  //组件名称，模型内名称唯一
				this.displayName = null;  //组件中文显示名称，方便阅读
				this.preInterceptors = null;  //前置拦截器
				this.postInterceptors = null;  //后置拦截器
				this.expr = null; //决策选择Decision节点的输出变迁表达式
			}

			this.fromNode = null;
			this.toNode = null;
			this.fromX = 0;
			this.fromY = 0;
			this.toX = 0;
			this.toY = 0;
			this.draw = function(canvas) {
				if (this.fromNode != null) {
					this.fromX = this.fromNode.x + NODE_STYLE.width / 2;
					this.fromY = this.fromNode.y + NODE_STYLE.height + ROUTE_STYLE.leave;
				}
				if (this.toNode != null) {
					this.toX = this.toNode.x + NODE_STYLE.width / 2;
					this.toY = this.toNode.y;
				}
				var middleX = this.fromX + (this.toX - this.fromX) / 2
				var middleY = this.fromY + (this.toY - this.fromY) / 2

				var canvas_context = canvas[0].getContext("2d");
				canvas_context.beginPath();
				canvas_context.lineJoin="round";
				canvas_context.lineWidth=ROUTE_STYLE.width;
				canvas_context.strokeStyle=ROUTE_STYLE.color;
				canvas_context.moveTo(this.fromX, this.fromY);
				canvas_context.lineTo(this.fromX, this.fromY + ROUTE_STYLE.leave + ROUTE_STYLE.startLength);
				canvas_context.lineTo(this.toX, this.toY - ROUTE_STYLE.leave - ROUTE_STYLE.endLength);
				canvas_context.lineTo(this.toX, this.toY - ROUTE_STYLE.leave - ROUTE_STYLE.arrowSize*2);
				canvas_context.stroke();

				canvas_context.beginPath();
				canvas_context.lineJoin="miter";
				canvas_context.lineWidth=1;
				canvas_context.moveTo(this.toX - ROUTE_STYLE.arrowSize, this.toY - ROUTE_STYLE.leave - ROUTE_STYLE.arrowSize*2);
				canvas_context.lineTo(this.toX, this.toY - ROUTE_STYLE.leave);
				canvas_context.lineTo(this.toX + ROUTE_STYLE.arrowSize, this.toY - ROUTE_STYLE.leave - ROUTE_STYLE.arrowSize*2);
				canvas_context.closePath();
				canvas_context.fillStyle=ROUTE_STYLE.color;
				canvas_context.fill();

				canvas_context.font=ROUTE_STYLE.font;
				canvas_context.fillStyle=ROUTE_STYLE.font_color;
				canvas_context.textAlign="center";
				canvas_context.textBaseline="middle";
				canvas_context.fillText(this.name || "", middleX, middleY);
			};
		}
		
		// 右键菜单定义
		var CONTEXT_MENU = [ {
			"id" : "flowproperty",
			"type" : "menu",
			"value" : "设置流程属性",
			"title" : "设置流程属性"
		}, {
			"id" : "nodeproperty",
			"type" : "menu",
			"value" : "设置节点属性",
			"title" : "设置节点属性"
		}, {
			"id" : "routeproperty",
			"type" : "menu",
			"value" : "设置流向属性",
			"title" : "设置流向属性"
		}, {
			"type" : "split"
		}, {
			"id" : "deletenode",
			"type" : "menu",
			"value" : "删除节点",
			"title" : "删除节点"
		}, {
			"id" : "deleteroute",
			"type" : "menu",
			"value" : "删除流向",
			"title" : "删除流向"
		} ];

		// 自动布局定义
		var AUTO_LAYOUT = {
			"x" : 40,
			"y" : 40,
			"x_space" : 230,
			"y_space" : 100
		};

		// 流程标题定义
		var FLOW_STYLE = {
			"x" : 20,
			"y" : 30,
			"textAlign" : "left",
			"baseLine" : "bottom",
			"flow_font" : "20px Microsoft YaHei",
			"version_font" : "16px Microsoft YaHei",
			"flow_color" : "#6F6F6F",
			"version_color" : "#6F6F6F",
			"split" : " V",
			"contextmenu" : [ {
				"id" : "flowproperty",
				"type" : "menu",
				"value" : "设置流程属性",
				"title" : "设置流程属性"
			} ]
		};

		// 节点定义
		var DEFAULT_NODE_TYPES = [ {
			"id" : "start",
			"name" : "开始节点",
			"icon" : "\uf04b",
			"isStart" : true,
			"isEnd" : false,
			"isNeed" : true,
			"limit" : 1
		}, {
			"id" : "judge",
			"name" : "判断节点",
			"icon" : "\uf0e8",
			"isStart" : false,
			"isEnd" : false,
			"isNeed" : false,
			"limit" : 0
		}, {
			"id" : "task",
			"name" : "任务节点",
			"icon" : "\uf007",
			"isStart" : false,
			"isEnd" : false,
			"isNeed" : false,
			"limit" : 0
		}, {
			"id" : "countersign",
			"name" : "会签节点",
			"icon" : "\uf0c0",
			"isStart" : false,
			"isEnd" : false,
			"isNeed" : false,
			"limit" : 0
		}, {
			"id" : "auto",
			"name" : "规则节点",
			"icon" : "\uf013",
			"isStart" : false,
			"isEnd" : false,
			"isNeed" : false,
			"limit" : 0
		}, {
			"id" : "state",
			"name" : "状态节点",
			"icon" : "\uf02b",
			"isStart" : false,
			"isEnd" : false,
			"isNeed" : false,
			"limit" : 0
		}, {
			"id" : "end",
			"name" : "结束节点",
			"icon" : "\uf04d",
			"isStart" : false,
			"isEnd" : true,
			"isNeed" : true,
			"limit" : 1
		}, {
			"id" : "sub-process",
			"name" : "子流程",
			"icon" : "\uf152",
			"isStart" : false,
			"isEnd" : false,
			"isNeed" : false,
			"limit" : 0
		} ];

		// 节点样式定义
		var NODE_STYLE = {
			"width" : 180,
			"height" : 55,
			"PREPOSITION" : {
				"x" : 85,
				"y" : 0,
				"r" : 5,
				"color" : "#AAAAAA"
			},
			"RECTANGLE" : {
				"x" : 0,
				"y" : 0,
				"width" : 180,
				"height" : 40,
				"round" : 0,
				"lineWidth" : 1,
				"lineColor" : "#AAAAAA",
				"gradient" : [ {
					"stop" : 0,
					"color" : "#FFFFFF"
				}, {
					"stop" : 0.3,
					"color" : "#FFFFFF"
				}, {
					"stop" : 0.9,
					"color" : "#FFFFFF"
				}, {
					"stop" : 1,
					"color" : "#FFFFFF"
				} ]
			},
			"POSTPOSITION" : {
				"x" : 85,
				"y" : 45,
				"r" : 5,
				"color" : "#AAAAAA"
			},
			"ICON" : {
				"x" : 20,
				"y" : 20,
				"textAlign" : "center",
				"baseLine" : "middle",
				"font" : "24px FontAwesome",
				"color" : "#333333"
			},
			"TITLE" : {
				"x" : 180,
				"y" : 40,
				"textAlign" : "right",
				"baseLine" : "bottom",
				"font" : "10px Microsoft YaHei",
				"color" : "#DDDDDD"
			}, // x 100
			"TEXT" : {
				"x" : 42,
				"y" : 20,
				"textAlign" : "left",
				"baseLine" : "middle",
				"font" : "12px Microsoft YaHei",
				"color" : "#333333"
			}
		};

		// 流向定义
		var DEFAULT_ROUTE_DEFINITION = {
			"rid" : "route",
			"contextmenu" : [ {
				"id" : "nodeproperty",
				"type" : "menu",
				"value" : "设置节点属性",
				"title" : "设置节点属性"
			}, {
				"type" : "split"
			}, {
				"id" : "deletenode",
				"type" : "menu",
				"value" : "删除节点",
				"title" : "删除节点"
			} ]
		};

		// 流向样式定义
		var ROUTE_STYLE = {
			"width" : 2,
			"color" : "#999999",
			"font" : "12px 微软雅黑",
			"font_color" : "#1E1E1E",
			"leave" : 0,
			"startLength" : 10,
			"endLength" : 15,
			"arrowSize" : 5
		};
		var $this = this;
		
		$this.on("contextmenu", function() {
			return false;
		});
		
		var _editor = $("<canvas class='editor' width='" + $this.innerWidth() + "' height='" + $this.innerHeight() + "'><p>对不起，您的浏览器不支持HTML5，请换用Chrome,firefox或其他支持高速模式的浏览器</p></canvas>");
		$this.append(_editor);
		
		$this.editor = _editor[0];
		
		
		$this.flow = null;
		$this.selected_node = null;
		$this.create_route = null;
		$this.zoom = 1;
		$this.zoomScale = 1.2;

		_editor.bind("click", function(mozilla_event) {
			context_menu.hide();
		});
		
		_editor.bind("mousedown", function(mozilla_event) {
			var eventX = (($.browser.mozilla ? mozilla_event.pageX : event.x)  - _editor.parent().offset().left + _editor.parent().scrollLeft()) / $this.zoom;
			var eventY = (($.browser.mozilla ? mozilla_event.pageY : event.y) - _editor.parent().offset().top + _editor.parent().scrollTop()) / $this.zoom;
			var button = ($.browser.mozilla ? mozilla_event.button : event.button);
			
			$this.selected_node = null;
			$this.create_route = null;
			if ($this.flow != null) {
				for (var i in $this.flow.nodes) {
					//矩形区
					if ($this.flow.nodes[i].x + NODE_STYLE.RECTANGLE.x < eventX && eventX < $this.flow.nodes[i].x + NODE_STYLE.RECTANGLE.x + NODE_STYLE.RECTANGLE.width
						&& $this.flow.nodes[i].y + NODE_STYLE.RECTANGLE.y < eventY && eventY < $this.flow.nodes[i].y + NODE_STYLE.RECTANGLE.y + NODE_STYLE.RECTANGLE.height) {
						if (button < 2) {
							$this.selected_node = $this.flow.nodes[i];
						}
					}
					//后置锚点区
					if (!$this.flow.nodes[i].type.isEnd) {
						if ($this.flow.nodes[i].x + NODE_STYLE.POSTPOSITION.x < eventX && eventX < $this.flow.nodes[i].x + NODE_STYLE.POSTPOSITION.x + NODE_STYLE.POSTPOSITION.r * 2
						&& $this.flow.nodes[i].y + NODE_STYLE.POSTPOSITION.y < eventY && eventY < $this.flow.nodes[i].y + NODE_STYLE.POSTPOSITION.y + NODE_STYLE.POSTPOSITION.r * 2) {
							if (button  < 2) {
								$this.create_route = new Route();
								$this.create_route.rid = DEFAULT_ROUTE_DEFINITION.rid + ($this.flow.routeCount + 1);
								$this.create_route.fromNode = $this.flow.nodes[i];
							}
						}
					}
				}
				for (var i in $this.flow.routes ) {
					//后引导-箭头
					if ($this.flow.routes[i].toX - ROUTE_STYLE.arrowSize < eventX && eventX < $this.flow.routes[i].toX + ROUTE_STYLE.arrowSize 
						&& $this.flow.routes[i].toY - ROUTE_STYLE.leave - ROUTE_STYLE.arrowSize*2 < eventY && eventY < $this.flow.routes[i].toY - ROUTE_STYLE.leave) {
							if (button  < 2) {
								$this.create_route = $this.flow.routes[i];
								$this.flow.deleteRoute($this.flow.routes[i].rid);
								$this.create_route.toNode = null;
							}
					}
				}
			}
		});

		_editor.bind("mousemove", function(mozilla_event) {
			var eventX = (($.browser.mozilla ? mozilla_event.pageX : event.x)  - _editor.parent().offset().left + _editor.parent().scrollLeft()) / $this.zoom;
			var eventY = (($.browser.mozilla ? mozilla_event.pageY : event.y) - _editor.parent().offset().top + _editor.parent().scrollTop()) / $this.zoom;
			var button = ($.browser.mozilla ? mozilla_event.button : event.button);

			_editor.css("cursor", "default");
			_editor.attr("title", "");
			if ($this.flow != null) {
				for (var i in $this.flow.nodes ) {
					//矩形区
					if ($this.flow.nodes[i].x + NODE_STYLE.RECTANGLE.x < eventX && eventX < $this.flow.nodes[i].x + NODE_STYLE.RECTANGLE.x + NODE_STYLE.RECTANGLE.width
						&& $this.flow.nodes[i].y + NODE_STYLE.RECTANGLE.y < eventY && eventY < $this.flow.nodes[i].y + NODE_STYLE.RECTANGLE.y + NODE_STYLE.RECTANGLE.height) {
						_editor.css("cursor", "pointer");
						_editor.attr("title", $this.flow.nodes[i].name);
					}
					//后置锚点区
					if (!$this.flow.nodes[i].type.isEnd) {
						if ($this.flow.nodes[i].x + NODE_STYLE.POSTPOSITION.x < eventX && eventX < $this.flow.nodes[i].x + NODE_STYLE.POSTPOSITION.x + NODE_STYLE.POSTPOSITION.r * 2
						&& $this.flow.nodes[i].y + NODE_STYLE.POSTPOSITION.y < eventY && eventY < $this.flow.nodes[i].y + NODE_STYLE.POSTPOSITION.y + NODE_STYLE.POSTPOSITION.r * 2) {
							_editor.css("cursor", "pointer");
						_editor.attr("title", $this.flow.nodes[i].name);
						}
					}
				}

				for (var i in $this.flow.routes ) {
					//前引导
					if ($this.flow.routes[i].fromX - ROUTE_STYLE.width < eventX && eventX < $this.flow.routes[i].fromX + ROUTE_STYLE.width
						&& $this.flow.routes[i].fromY + ROUTE_STYLE.leave < eventY && eventY < $this.flow.routes[i].fromY + ROUTE_STYLE.leave + ROUTE_STYLE.startLength ) {
						_editor.css("cursor", "pointer");
						_editor.attr("title", $this.flow.routes[i].fromNode.name + "至" + $this.flow.routes[i].toNode.name);
					}
					//斜线
					var y1 = $this.flow.routes[i].fromY + ROUTE_STYLE.leave + ROUTE_STYLE.startLength;
					var y2 = $this.flow.routes[i].toY - ROUTE_STYLE.leave - ROUTE_STYLE.endLength;
					var x1 = $this.flow.routes[i].fromX;
					var x2 = $this.flow.routes[i].toX;
					var y = y1 + (y2 - y1) * (eventX - x1) / (x2 - x1);
					if (y -  ROUTE_STYLE.width < eventY && eventY < y + ROUTE_STYLE.width) {
						_editor.css("cursor", "pointer");
						_editor.attr("title", $this.flow.routes[i].fromNode.name + "至" + $this.flow.routes[i].toNode.name);
					}

					//后引导-直线
					if ($this.flow.routes[i].toX - ROUTE_STYLE.width < eventX && eventX < $this.flow.routes[i].toX + ROUTE_STYLE.width 
						&& $this.flow.routes[i].toY - ROUTE_STYLE.leave - ROUTE_STYLE.endLength < eventY && eventY < $this.flow.routes[i].toY - ROUTE_STYLE.leave - ROUTE_STYLE.arrowSize*2) {
						_editor.css("cursor", "pointer");
						_editor.attr("title", $this.flow.routes[i].fromNode.name + "至" + $this.flow.routes[i].toNode.name);
					}
					
					//后引导-箭头
					if ($this.flow.routes[i].toX - ROUTE_STYLE.arrowSize < eventX && eventX < $this.flow.routes[i].toX + ROUTE_STYLE.arrowSize
						&& $this.flow.routes[i].toY - ROUTE_STYLE.leave - ROUTE_STYLE.arrowSize*2 < eventY && eventY < $this.flow.routes[i].toY - ROUTE_STYLE.leave ) {
						_editor.css("cursor", "pointer");
						_editor.attr("title", $this.flow.routes[i].fromNode.name + "至" + $this.flow.routes[i].toNode.name);
					}
				}
			}

			if ($this.selected_node != null) {
				if (button  < 2) {
					$this.selected_node.x = (eventX - NODE_STYLE.width / 2 < 2) ? 2 : (eventX - NODE_STYLE.width / 2);
					$this.selected_node.y = (eventY - NODE_STYLE.height / 2 < 2) ? 2 : (eventY - NODE_STYLE.height / 2);
					$this.paint();
				}
			}
			if ($this.create_route != null) {
				if (button  < 2) {
					$this.create_route.toX =eventX;
					$this.create_route.toY = eventY;
					$this.paint();
				}
			}
		});

		_editor.bind("mouseup", function(mozilla_event) {
			var eventX = (($.browser.mozilla ? mozilla_event.pageX : event.x)  - _editor.parent().offset().left + _editor.parent().scrollLeft()) / $this.zoom;
			var eventY = (($.browser.mozilla ? mozilla_event.pageY : event.y) - _editor.parent().offset().top + _editor.parent().scrollTop()) / $this.zoom;
			var button = ($.browser.mozilla ? mozilla_event.button : event.button);

			if (button  < 2) {
				if ($this.selected_node != null) {
					$this.selected_node.x = (eventX - NODE_STYLE.width / 2 < 2) ? 2 : (eventX - NODE_STYLE.width / 2);
					$this.selected_node.y = (eventY - NODE_STYLE.height / 2 < 2) ? 2 : (eventY - NODE_STYLE.height / 2);
					$this.paint();

					$this.selected_node = null;
				}
				if ($this.create_route != null) {
					if (button  < 2) {
						for (var i in $this.flow.nodes) {
							//矩形区
							if ($this.flow.nodes[i].x + NODE_STYLE.RECTANGLE.x < eventX && eventX < $this.flow.nodes[i].x + NODE_STYLE.RECTANGLE.x + NODE_STYLE.RECTANGLE.width
								&& $this.flow.nodes[i].y + NODE_STYLE.RECTANGLE.y < eventY && eventY < $this.flow.nodes[i].y + NODE_STYLE.RECTANGLE.y + NODE_STYLE.RECTANGLE.height) {
								$this.create_route.toNode = $this.flow.nodes[i];
								$this.flow.addRoute($this.create_route);
							}
						}
						$this.create_route = null;
						$this.paint();
					}
				}
			}

			if ($this.flow != null && button == 2) {
				var isNone = true;
				for (var i in $this.flow.nodes) {
					//矩形区
					if ($this.flow.nodes[i].x + NODE_STYLE.RECTANGLE.x < eventX && eventX < $this.flow.nodes[i].x + NODE_STYLE.RECTANGLE.x + NODE_STYLE.RECTANGLE.width
						&& $this.flow.nodes[i].y + NODE_STYLE.RECTANGLE.y < eventY && eventY < $this.flow.nodes[i].y + NODE_STYLE.RECTANGLE.y + NODE_STYLE.RECTANGLE.height) {
							$this.showContextMenu($this.flow.nodes[i], eventX, eventY);
							isNone = false;
					}
				}
				for (var i in $this.flow.routes ) {
					//前引导
					if ($this.flow.routes[i].fromX - ROUTE_STYLE.width < eventX && eventX < $this.flow.routes[i].fromX + ROUTE_STYLE.width
						&& $this.flow.routes[i].fromY + ROUTE_STYLE.leave < eventY && eventY < $this.flow.routes[i].fromY + ROUTE_STYLE.leave + ROUTE_STYLE.startLength ) {
							$this.showContextMenu($this.flow.routes[i], eventX, eventY);
							isNone = false;
					}

					//斜线
					var y1 = $this.flow.routes[i].fromY + ROUTE_STYLE.leave + ROUTE_STYLE.startLength;
					var y2 = $this.flow.routes[i].toY - ROUTE_STYLE.leave - ROUTE_STYLE.endLength;
					var x1 = $this.flow.routes[i].fromX;
					var x2 = $this.flow.routes[i].toX;
					var y = y1 + (y2 - y1) * (eventX - x1) / (x2 - x1);
					if (y -  ROUTE_STYLE.width < eventY && eventY < y + ROUTE_STYLE.width) {
							$this.showContextMenu($this.flow.routes[i], eventX, eventY);
							isNone = false;
					}

					//后引导-直线
					if ($this.flow.routes[i].toX - ROUTE_STYLE.width < eventX && eventX < $this.flow.routes[i].toX + ROUTE_STYLE.width 
						&& $this.flow.routes[i].toY - ROUTE_STYLE.leave - ROUTE_STYLE.endLength < eventY && eventY < $this.flow.routes[i].toY - ROUTE_STYLE.leave - ROUTE_STYLE.arrowSize*2) {
							$this.showContextMenu($this.flow.routes[i], eventX, eventY);
							isNone = false;
					}
					
					//后引导-箭头
					if ($this.flow.routes[i].toX - ROUTE_STYLE.arrowSize < eventX && eventX < $this.flow.routes[i].toX + ROUTE_STYLE.arrowSize 
						&& $this.flow.routes[i].toY - ROUTE_STYLE.leave - ROUTE_STYLE.arrowSize*2 < eventY && eventY < $this.flow.routes[i].toY - ROUTE_STYLE.leave) {
							$this.showContextMenu($this.flow.routes[i], eventX, eventY);
							isNone = false;
					}
				}
				if (isNone) {
					$this.showContextMenu($this.flow, eventX, eventY);
				}
			}
		});
		
		$this.save = function() {
			var data = $this.flow.getData();
			if (!data.system_product_code) {
				$.warning("所属产品必须填写！");
				return;
			}
			$.call("crdc.ruleflowdesigner.save", {"wfjson": $.toJSON(data)}, function(rtn) {
				$.success("保存成功！");
			});
		}
		
		$this.load = function(system_product_code, id) {
			$.call("crdc.ruleflowdesigner.get", {"system_product_code": system_product_code, id: id}, function(rtn) {
				$this.flow = $.extend(true, new Flow(), rtn);
				var nodes = $.extend(true, [], rtn.nodes);
				var routes = $.extend(true, [], rtn.routes);
				$this.flow.nodes = {};
				$this.flow.routes = {};
				
				for (var i = 0, ilen = nodes.length; i < ilen; i ++) {
					var node_type = null;
					for (var t = 0; t < DEFAULT_NODE_TYPES.length ; t ++) {
						if (nodes[i].type == DEFAULT_NODE_TYPES[t].id) {
							node_type = DEFAULT_NODE_TYPES[t];
							break;
						}
					}
					if (node_type == null) {
						alert("对不起没有找到指定类型的节点[" + nodes[i].type + "]。");
						return;
					}
					$this.flow.nodes[nodes[i].id] = $.extend(true, new Node(), nodes[i], {
						type: $.extend(true, {}, node_type)
					});
				}
				
				for (var i = 0, ilen = routes.length; i < ilen; i ++) {
					if (!routes[i].rid || routes[i].rid == "null") {
						routes[i].rid = DEFAULT_ROUTE_DEFINITION.rid + (i + 1);
					}
					$this.flow.routes[routes[i].rid] = $.extend(true, new Route(), routes[i], {
						fromNode: $this.flow.nodes[routes[i].fromNode],
						toNode: $this.flow.nodes[routes[i].toNode],
					});
				}
				$this.paint();
			});
		}
		
		$this.deploy = function() {
			var data = $this.flow.getData();
			if (!data.system_product_code) {
				$.warning("所属产品必须填写！");
				return;
			}
			$.call("crdc.ruleflowdesigner.deploy", {"wfjson": $.toJSON(data)}, function(rtn) {
				$.success("发布成功！");
			});
		}
		
		$this.paint = function() {
			var max_width = _editor.parent().innerWidth();
			var max_height = _editor.parent().innerHeight();
			if ($this.flow != null) {
				for (var i in $this.flow.nodes) {
					if (($this.flow.nodes[i].x + NODE_STYLE.width)  * $this.zoom > max_width) {
						max_width = ($this.flow.nodes[i].x + NODE_STYLE.width)  * $this.zoom;
					}
					if (($this.flow.nodes[i].y + NODE_STYLE.height)  * $this.zoom > max_height) {
						max_height = ($this.flow.nodes[i].y + NODE_STYLE.height)  * $this.zoom;
					}
				}
			}
			_editor[0].width = max_width;
			_editor[0].height = max_height;

			var canvas_context = _editor[0].getContext("2d");
			canvas_context.scale($this.zoom, $this.zoom);
			
			if ($this.flow != null) {
				$this.flow.draw(_editor);
			}

			if ($this.create_route != null) {
				$this.create_route.draw(_editor);
			}
		}

		$this.createNode = function(nodetype, x, y) {
			if ($this.flow != null) {
				$this.flow.createNode(nodetype, x, y);
				$this.paint();
			}
			else {
				alert("请新建或打开！");
			}
		}

		$this.newFlow = function() {
			$this.flow = new Flow();
			$this.flow.version = 1;
			context_menu[0].menuObject = $this.flow;
			$this.onContextMenuClick("flowproperty");
		}

		$this.zoomIn = function() {
			$this.zoom = $this.zoom * $this.zoomScale;
			$this.paint();
		}

		$this.zoomOut = function() {
			$this.zoom = $this.zoom / $this.zoomScale;
			$this.paint();
		}

		$this.fitWidth = function() {
			var max_width = 0;
			if ($this.flow != null) {
				for (var i in $this.flow.nodes) {
					if (($this.flow.nodes[i].x + NODE_STYLE.width) > max_width) {
						max_width = ($this.flow.nodes[i].x + NODE_STYLE.width);
					}
				}
			}
			$this.zoom = (_editor.parent().width()) / (max_width);
			$this.paint();
		}

		$this.fitHeight = function() {
			var max_height = 0;
			if ($this.flow != null) {
				for (var i in $this.flow.nodes) {
					if (($this.flow.nodes[i].y + NODE_STYLE.height) > max_height) {
						max_height = ($this.flow.nodes[i].y + NODE_STYLE.height);
					}
				}
			}
			$this.zoom = (_editor.parent().height()) / (max_height);
			$this.paint();
		}

		$this.fitPage = function() {
			var max_width = 0;
			var max_height = 0;
			if ($this.flow != null) {
				for (var i in $this.flow.nodes) {
					if (($this.flow.nodes[i].x + NODE_STYLE.width) > max_width) {
						max_width = ($this.flow.nodes[i].x + NODE_STYLE.width);
					}
					if (($this.flow.nodes[i].y + NODE_STYLE.height) > max_height) {
						max_height = ($this.flow.nodes[i].y + NODE_STYLE.height);
					}
				}
			}
			var zoomX = (_editor.parent().width()) / (max_width);
			var zoomY = (_editor.parent().height()) / (max_height);
			$this.zoom = zoomX > zoomY ? zoomY : zoomX;
			$this.paint();
		}

		$this.actualSize = function() {
			$this.zoom = 1;
			$this.paint();
		}

		$this.autoLayout = function() {
			$this.flow.autoLayout();

			_editor.parent().scrollLeft(0);
			_editor.parent().scrollTop(0);

			this.paint();
		}
		
		var context_menu = $("<div class='contextmenu'></div>");
		$this.prepend(context_menu);
		for (var i = 0; i < CONTEXT_MENU.length; i ++) {
			for (var i = 0; i < CONTEXT_MENU.length; i ++) {
				if (CONTEXT_MENU[i].type == "menu") {
					context_menu.append($("<a herf='javascript:void(0)' ceid='" + CONTEXT_MENU[i].id + "' class='menu " + CONTEXT_MENU[i].id + "' title='" + CONTEXT_MENU[i].title + "'>" + CONTEXT_MENU[i].value + "</a>"));
				}
				else if (CONTEXT_MENU[i].type == "split") {
					context_menu.append($("<a herf='javascript:void(0)' class='sepbtn'></a>"));
				}
			}
			context_menu.find("a[ceid]").click(function() {
				$this.onContextMenuClick($(this).attr("ceid"));
			});
		}

		$this.showContextMenu = function(obj, x, y) {
			context_menu.find("a").hide();
			if (obj instanceof Flow) {
				context_menu.find(".flowproperty").show();
			}
			else if (obj instanceof Node) {
				if (obj.type.id != "start" && obj.type.id != "end") {
					context_menu.find(".nodeproperty").show();
					context_menu.find(".sepbtn").show();
				}
				context_menu.find(".deletenode").show();
			}
			else if (obj instanceof Route) {
				context_menu.find(".routeproperty").show();
				context_menu.find(".sepbtn").show();
				context_menu.find(".deleteroute").show();
			}
			context_menu[0].menuObject = obj;
			context_menu.show();
			context_menu.css("left", x + "px");
			context_menu.css("top", y + "px");
		}

		$this.onContextMenuClick = function(menu) {
			if (context_menu[0].menuObject instanceof Flow) {
				if (menu == "flowproperty") {
					//显示流程属性页
					var t = context_menu[0].menuObject;
					var data = {};
					for (var k in context_menu[0].menuObject) {
						if (!$.isFunction(context_menu[0].menuObject[k]) && k != 'nodes' && k != 'routes') {
							data[k] = context_menu[0].menuObject[k];
						}
					}
					$.modal(cooperopcontextpath + "/w/crdc/ruleflow/flow.html", "流程属性", $.extend(true, data, {
						//width: "640px",
						//height: "480px",
						callback: function(rtn) {
							if (rtn) {
								t = $.extend(true, t, rtn);
								$this.paint();
							}
						}
					}));
				}
			}
			else if (context_menu[0].menuObject instanceof Node) {
				if (context_menu[0].menuObject.type.id != "start" && context_menu[0].menuObject.type.id != "end" && menu == "nodeproperty") {
					//显示节点属性页
					var t = context_menu[0].menuObject;
					var data = {};
					for (var k in context_menu[0].menuObject) {
						if (!$.isFunction(context_menu[0].menuObject[k]) && k != 'type') {
							data[k] = context_menu[0].menuObject[k];
						}
					}
					data["system_product_code"] = $this.flow.system_product_code;
					$.modal(cooperopcontextpath + "/w/crdc/ruleflow/node.html", "节点属性 [" + t.name + "]", $.extend(true, data, {
						type: t.type.id,
						//width: "640px",
						//height: "480px",
						callback: function(rtn) {
							if (rtn) {
								t = $.extend(true, t, rtn);
								$this.paint();
							}
						}
					}));
				}
				else if (menu == "deletenode") {
					$this.flow.deleteNode(context_menu[0].menuObject.id);
					$this.paint();
				}
			}
			else if (context_menu[0].menuObject instanceof Route) {
				if (menu == "routeproperty") {
					//显示流向属性页
					var t = context_menu[0].menuObject;
					var data = {};
					for (var k in context_menu[0].menuObject) {
						if (!$.isFunction(context_menu[0].menuObject[k]) && k != 'fromNode' && k != 'toNode') {
							data[k] = context_menu[0].menuObject[k];
						}
					}
					$.modal(cooperopcontextpath + "/w/crdc/ruleflow/route.html", "流向属性 [" + t.fromNode.name + " -> " + t.toNode.name + "]", $.extend(true, data, {
						//width: "640px",
						//height: "480px",
						callback: function(rtn) {
							if (rtn) {
								t = $.extend(true, t, rtn);
								$this.paint();
							}
						}
					}));
				}
				else if (menu == "deleteroute") {
					$this.flow.deleteRoute(context_menu[0].menuObject.rid);
					$this.paint();
				}
			}
			context_menu[0].menuObject = null;
			context_menu.hide();
		}

		
		$this.paint();
		
		return $this;
	}
});