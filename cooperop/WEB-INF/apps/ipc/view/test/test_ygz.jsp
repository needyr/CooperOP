<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="【测试】抓取药品说明书" disloggedin="true">
<style>
	/* 定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸 */
	::-webkit-scrollbar
	{
	    width: 5px;
	    height: 10px;
	    background-color: #F5F5F5;
	}
	 
	/*定义滚动条轨道 内阴影+圆角*/
	::-webkit-scrollbar-track
	{
	    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	    border-radius: 10px;
	    background-color: #F5F5F5;
	}
	 
	/* 定义滑块 内阴影+圆角 */
	::-webkit-scrollbar-thumb
	{
	    border-radius: 10px;
	    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
	    background-color: #c5c5c5;
	}
	
	.line-info{
	font-size:14px;
	}
	
	p.line-info:hover {
    color: #185850;
    cursor: pointer;
    font-weight: 600;
    background-color: #c4ecba;
	}
	.dtitle{
	margin-left: 40px;
    padding: 6px;
    line-height: 23px;
    background: #c8dcd4;}
</style>
	
	<body>
		<s:form label="药品说明书抓取(测试)" id="form1">
			<s:toolbar>
				<s:button label="抓取全部药品到数据库" onclick="tt();"  icon="fa search" style="background-color: #ffecca"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="标准库药品编号" name="drug_code" cols="2" placeholder="请输入标准药品编号" ></s:textfield>
				<s:button label="抓取一个药品查看" onclick="getd();"></s:button>
				<s:button label="新版抓取一个" onclick="getOne();"></s:button>
				<s:button label="新版抓取所有" onclick="getAll();"></s:button>
			</s:row>
			<s:row>
				<s:textfield label="HIS药品编号" name="his_drug_code" cols="2" placeholder="请输入HIS药品编号" ></s:textfield>
				<s:button label="说明书单独抓取到数据库" onclick="his_shuoms();"></s:button>
				<s:button label="同步患者数据到药师端" onclick="syncuser();"></s:button>
				<s:button label="同步说明书数据到药师端" onclick="syncsms();"></s:button>
				<%-- <s:button label="说明书图片数据解析测试" onclick="img_test();"></s:button> --%>
			</s:row>
		</s:form>
		<s:row>
			<span class="dtitle">抓取记录</span>
			<div id="urlp" style="width: 95% ;max-height: 100px ; overflow:auto; margin: 0px 0px 10px 40px; border: 2px solid #ded5d5;padding: 10px;">
			</div>
		</s:row>
		<span class="dtitle">说明书内容</span>
		<div id="drugbody" style="width: 95% ;max-height: 470px ; overflow: auto;margin-left:40px; border: 2px solid #ded5d5;padding: 10px;" >
		</div>
		
	</body>
</s:page>
<script type="text/javascript">
	var num = 0;
	function tt(){
		try{
			$.call("ipc.test.test", {}, function(rtn) {
				$.message("抓取完成 !");
			})
		}catch(error){
			//$.message("正在抓取数据...");
		}
	}
	
	function getd(){
		var data = $("#form1").getData();
		try{
			$.call("ipc.test.test_getdrug", data, function(rtn) {
				num++;
				$("#drugbody").empty();
				var html = [];
				var html2 = [];
				html.push('<p>');
				html.push('<a href = "'+ rtn.drug_url +'" target="view_window">');
				var a1= $(rtn.drug_body).not('h5').find(".col-md-9 div");
				if(rtn.drug_body == null){
					html2.push('<p style="color : red">远程服务被关闭 </p>');
				}else{
					if(a1.length > 0){
						for(i=0;i<25;i++){
							try{
								html2.push('<div class="line-info">');
								html2.push('<p class="line-info">'+a1.find("h5").eq(i).text()+'</p>');
								html2.push(a1.eq(i).html());
								html2.push('</div>');
							}catch(error){
								break;
							}
						}
						html.push(num +' '+'【'+ a1.find("p").eq(0).text() +'】'+ rtn.drug_url);
					}else{
						html2.push('<p class="line-info" style="color: red">');
						html2.push(" 未配对的药品 ！ ");
						html2.push('</p>');
						html.push(num +' '+'【未配对的药品】'+ rtn.drug_url);
					}
				}
				html.push('</a>');
				html.push('</p>');
				$("#urlp").prepend(html.join(''));
				$("#drugbody").append(html2.join(''));
				$('.line-info img').each(function(){
					var _path= '${pageContext.request.contextPath}/res/hospital_common/hytimages/' + $(this).attr('src');
					//var _path= '${pageContext.request.contextPath}/res/ipc/hytimages/' + '0000009.jpg';
					$(this).attr('src', _path);
				});
			})
		}catch(error){
			alert("ipc服务断开...");
		}
	}
	
	function his_shuoms(){
		var data = $("#form1").getData();
		$.call("ipc.test.test_getdrug_one", data, function(rtn) {
			
		})
	}
	
	function img_test(){
		$.call("ipc.test.img_test", {}, function(rtn) {
			
		})
	}
	
	function syncuser(){
		$.call("ipc.test.syncuser", {}, function(rtn) {
			
		})
	}

	function syncsms(){
		$.call("ipc.test.syncsms", {}, function(rtn) {

		})
	}

	function getOne(){
		var data = $('#form1').getData();
		$.call("ipc.test.getInstruction_new", {sys_drug_code: data.drug_code}, function(ret) {
			$.message("已完成！");
		})
	}

	function getAll(){
		$.call("ipc.test.getAllInstruction_new", {}, function(ret) {
			$.message("已发起！");
		})
	}

</script>