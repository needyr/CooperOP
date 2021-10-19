package cn.crtech.cooperop.application.action;

import java.util.Map;

import cn.crtech.cooperop.application.service.WeiXinService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;

public class WeixinAction extends BaseAction {
	public Result querySubscribeUser(Map<String, Object> req) throws Exception {
		return new WeiXinService().querySubscribeUser(req);
	}

	public void refreshSubscribeUser(Map<String, Object> req) throws Exception {
		//new WeiXinService().refreshSubscribeUser(req);
	}

	public Result queryMaterialNews(Map<String, Object> req) throws Exception {
		return new WeiXinService().queryMaterialNews(req);
	}

	public void refreshMaterialNews(Map<String, Object> req) throws Exception {
		new WeiXinService().refreshMaterialNews(req);
	}

	public Result queryMaterialImage(Map<String, Object> req) throws Exception {
		return new WeiXinService().queryMaterialImage(req);
	}

	public void refreshMaterialImage(Map<String, Object> req) throws Exception {
		new WeiXinService().refreshMaterialImage(req);
	}

	public Result queryMaterialVoice(Map<String, Object> req) throws Exception {
		return new WeiXinService().queryMaterialVoice(req);
	}

	public void refreshMaterialVoice(Map<String, Object> req) throws Exception {
		new WeiXinService().refreshMaterialVoice(req);
	}

	public Result queryMaterialVideo(Map<String, Object> req) throws Exception {
		return new WeiXinService().queryMaterialVideo(req);
	}

	public void refreshMaterialVideo(Map<String, Object> req) throws Exception {
		new WeiXinService().refreshMaterialVideo(req);
	}

	public Result queryTemplate(Map<String, Object> req) throws Exception {
		return new WeiXinService().queryTemplate(req);
	}

	public void refreshTemplate(Map<String, Object> req) throws Exception {
		new WeiXinService().refreshTemplate(req);
	}

	public Result queryTemplateTransform(Map<String, Object> req) throws Exception {
		return new WeiXinService().queryTemplateTransform(req);
	}

	public void sendTemplateMessage(Map<String, Object> req) throws Exception {
		new WeiXinService().sendTemplateMessage(req);
	}

	public Result queryMenu(Map<String, Object> req) throws Exception {
		return new WeiXinService().queryMenu(req);
	}

	public void refreshMenu(Map<String, Object> req) throws Exception {
		new WeiXinService().refreshMenu(req);
	}

	public void uploadMenu(Map<String, Object> req) throws Exception {
		new WeiXinService().uploadMenu(req);
	}

	public Result queryQRCode(Map<String, Object> req) throws Exception {
		return new WeiXinService().queryQRCode(req);
	}

	public void createQRCode(Map<String, Object> req) throws Exception {
		//new WeiXinService().createQRCode(req);
	}

}
