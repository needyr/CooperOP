<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	pageContext.setAttribute("check_result_y_notice",  SystemConfig.getSystemConfigValue("ipc", "check_result_y_notice", "药师:pharmacist:state患者:patient的:p_type_name！")) ;
	pageContext.setAttribute("check_result_n_notice",SystemConfig.getSystemConfigValue("ipc", "check_result_n_notice", "药师:pharmacist:state患者:patient的:p_type_name！")) ;
	pageContext.setAttribute("check_result_d_notice",SystemConfig.getSystemConfigValue("ipc", "check_result_d_notice", "药师:pharmacist:state患者:patient的:p_type_name，由医生决定是否双签名用药！")) ;
%>
<s:page title="审查结果" disloggedin="true">
<style type="text/css">
	
	.page-content {
    min-height: auto !important;
	}
	
    .closehh{
        height: 25px;
        position: absolute;
        bottom: 0;
        width: 100%;
    }

    .cmain{
        width: 240px;
	    height: 80px;
	    border: 1px solid #bbb;
	    border-radius: 3px;
	    margin-bottom: 10px;
	    position: relative;
	    padding: 0 5px;
	    margin: 5px 0;
    }

    .cmain>p{
        font-size: 13px;
        text-align: left;
        margin-top: 5px;
        color: #6a6969;
        line-height: 23px;
    }

    .cmain>p .color1{
   		font-size:14px;
        margin: 0 2px;
    }

    .cmain>p .color2{
    	font-size:14px;
        margin: 0 2px;
        color: #cf0000;
    }
    
    .cmain>p .color3{
    	font-size:14px;
        margin: 0 2px;
        color: #038a08;
    }
    
    .cmain>p .color4{
   		font-size:14px;
        margin: 0 2px;
        color: #bd880b;
    }

    .queren{
        font-size: 12px;
        width: 50px;
        height: 20px;
        display: block;
        text-align: center;
        margin-right: 10px;
        float: right;
        color: #fff;
        border-radius: 3px;
        background-color: #0d98d2;
        line-height: 20px;
    }

    .queren:hover{
        background-color: #0e77d2;
        cursor: pointer;
    }
    .page-content{
	    padding: 0 5px 0 5px !important;
    }
  
</style>
<body>
<!-- <div class="cmain">
	
</div> -->
<%-- <div class="cmain">
    <div class="closehh"><span class="queren">确认</span></div>
    <p>药师陈药师<span class="color2">已通过</span>患者<span class="color1">吴国强</span>的医嘱</p>
</div> --%>
</body>

<script type="text/javascript">
	var messageNum = 0;
	$(document).ready(function(){
		var check_result_y_notice = '${check_result_y_notice}';
		var check_result_n_notice = '${check_result_n_notice}';
		var check_result_d_notice = '${check_result_d_notice}';
		var auto_audit_id = [];
		$.call("ipc.auditresult.notices",{uid: '${param.uid}'},function(rtn){
			if(rtn){
				var check = 0;
				for(var i in  rtn.notices){
					var html = [];
					 var notice = rtn.notices[i];
					 auto_audit_id.push(notice.id); 
						html.push('<div class="cmain" >');
						html.push('<div class="closehh">');
						if(notice.state == 'Y'){
							html.push('<span class="queren" onclick="is_sure(this,\''+notice.id+'\');">');
							html.push('已阅');
							html.push('</span>');
						}
						html.push('<span class="queren" onclick="show(\''+notice.state+'\',\''+notice.id+'\',this);">');
						if(notice.state == 'D'){
							html.push('处理');
						}else{
							html.push('查看');
						}
						html.push('</span>');
						html.push('</div>');
						html.push('<p>');
						if(notice.state == 'Y'){
							html.push(check_result_y_notice.replace(":pharmacist", notice.yaoshi_name + notice.pharmacist_id)
									.replace(":doctor",notice.doctor_name)
									.replace(":state","<span class='color3'>已通过</span>")
									.replace(":patient", "<span class='color1'>" + notice.patient_name + "</span>")
									.replace(":p_type_name", "1" == notice.p_type?"医嘱":"处方"));
						}else if(notice.state == 'N'){
							html.push(check_result_n_notice.replace(":pharmacist", notice.yaoshi_name + notice.pharmacist_id)
									.replace(":doctor",notice.doctor_name)
									.replace(":state","<span class='color2'>已驳回</span>")
									.replace(":patient", "<span class='color1'>" + notice.patient_name + "</span>")
									.replace(":p_type_name", "1" == notice.p_type?"医嘱":"处方"));
						}else if(notice.state == 'D'){
							html.push(check_result_d_notice.replace(":pharmacist", notice.yaoshi_name + notice.pharmacist_id)
									.replace(":doctor",notice.doctor_name)
									.replace(":state","<span class='color4'>医生决定</span>")
									.replace(":patient", "<span class='color1'>" + notice.patient_name + "</span>")
									.replace(":p_type_name", "1" == notice.p_type?"医嘱":"处方"));
						}
						html.push('</p></div>');
						$("form").append(html.join(''));
					}
				check = 1;
				messageNum = rtn.notices.length;
			}
			//crtech.addWindowHeight(x); 为默认高度+新增数字*高度，如果为负数则关闭页面
			crtech.addWindowHeight(--messageNum);
			if(check == 1){
				$.call("ipc.auditresult.updateNotices",{auto_audit_ids: $.toJSON(auto_audit_id)},function(rtn){
				
				},function(){},{async:false}) 
				//setTimeout(function(){alert('有药师审查结果未处理,请处理看!')},200);
			}
		});
	});
	
	function is_sure(_this, id){
		//页面删除消息
		var $this = $(_this);
		//后台将is_sure改为1
		$.call("ipc.autoaudit.update",{id: id,is_sure: 1},function(rtn){
			if(rtn){
				var size = $("form").find(".cmain").length;
				$this.parents(".cmain").remove();
				//调用crtech.addWindowHeight(size);刷新窗口大小
				//alert(messageNum);
				//crtech.addWindowHeight(''+ --messageNum +'');
				if(--messageNum < 0){
					crtech.closeModal();
				}else{
					//alert(messageNum);
					crtech.addWindowHeight(messageNum);
				}
			}
		});
	}
	
	function del_state_d(_this, id){
		//页面删除消息
		var $this = $(_this);
		var size = $("form").find(".cmain").length;
		$this.parents(".cmain").remove();
		//调用crtech.addWindowHeight(size);刷新窗口大小
		//alert(messageNum);
		//crtech.addWindowHeight(''+ --messageNum +'');
		if(--messageNum < 0){
			crtech.closeModal();
		}else{
			//alert(messageNum);
			crtech.addWindowHeight(messageNum);
		}
	}
	
	function show(state, id, _this){
		var url = '';
		if(state == 'D'){
			url = encodeURIComponent("/w/ipc/auditflow/timeoutdetail.html?auto_audit_id="+id);
			crtech.modal(url);
			del_state_d(_this, id);
		}else{
			url = encodeURIComponent("/w/ipc/auditflow/show.html?auto_audit_id="+id);
			is_sure(_this, id);
			crtech.modal(url);
		}
	}
</script>

</s:page>
