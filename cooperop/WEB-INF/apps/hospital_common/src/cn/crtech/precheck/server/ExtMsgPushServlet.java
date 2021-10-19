package cn.crtech.precheck.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.UserService;
import cn.crtech.ylz.MAEngine;

    /**
    * @ClassName ExtMsgPushServlet
    * @Description 外部接口： 审方客户端弹窗提示
    * @author yanguozhi 2020-02-01
    */
public class ExtMsgPushServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Record USERS = new Record();

	/**
	 * 初始化 用户no与用户id的关系
	 * @throws ServletException
	 */
	@Override
	public void init() throws ServletException {
		try {
			List<Record> userList = new UserService().queryUsers(null).getResultset();
			if(userList != null && userList.size() > 0){
				for (Record r: userList) {
					if(r.get("id") != null) {
						USERS.put(r.getString("no"), r.get("id"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Map<String, Object> reqMap = CommonFun.requestMap(req);
		// ==== 测试数据
		/**
		reqMap.put("title", "静配消息提示");
		reqMap.put("sketch", "患者张三的医嘱被审核为：不通过");
		reqMap.put("detail", "患者张三 xx xxx xxx xxxx，医嘱编号： xxxx ,医嘱内容：xxxxx ， <br>药师理由：xxx xxxx xxxx xx");
		reqMap.put("send_to_user", "9888");
		reqMap.put("send_by_user", "fnetyy_jp");
		reqMap.put("can_is_read", 0);
		reqMap.put("type", 101);
		*/
		// 明细网页
		reqMap.put("page_url", "/w/hospital_common/system/msgalert/detail.html");
		// ==== 
		Record ret = new Record();
		if(isW(reqMap)) {
			Record ins = new Record();
			ins.put("title", reqMap.get("title"));
			ins.put("content", reqMap.get("sketch"));
			ins.put("content_detail", reqMap.get("detail"));
			// 转化为可识别的user_id
			ins.put("send_to_user", USERS.get(reqMap.get("send_to_user")));
			// jp
			ins.put("send_by_user", reqMap.get("send_by_user"));
			// 传 >100的数字（内部使用<100）
			ins.put("source_type", reqMap.get("type"));
			// 是否允许已阅
			ins.put("can_is_read", reqMap.get("can_is_read"));
			// 是否允许已阅
			ins.put("page_url", reqMap.get("page_url"));
			ret.put("state", 1);
			ret.put("msg", "success");
			try {
				String rtn = MAEngine.addNewMsg(ins);
				if("-1".equals(rtn)) {
					ret.put("state", 0);
					ret.put("msg", rtn);
				}else {
					ret.put("msg_id", rtn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			ret.put("state", 0);
			ret.put("msg", "重要参数为null，请求参数：" + CommonFun.requestMap(req).toString());
		}
		resp.setContentType("application/json; charset=UTF-8");
		resp.getWriter().write(CommonFun.object2Json(ret));
		resp.getWriter().flush();
	}
	
	public boolean isW (Map<String, Object> params) {
		boolean isW = true;
		if(CommonFun.isNe(params.get("send_to_user"))) {
			isW = false;
		}
		if(CommonFun.isNe(params.get("sketch"))) {
			isW = false;
		}
		if(CommonFun.isNe(params.get("send_by_user"))) {
			isW = false;
		}
		if(CommonFun.isNe(params.get("type"))) {
			isW = false;
		}
		return isW;
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}
}
