package cn.crtech.cooperop.core.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.core.domain.RequestTask;
import cn.crtech.cooperop.core.service.CoreService;
import cn.crtech.cooperop.core.service.ExecuteService;
import cn.crtech.cooperop.core.utils.RequestHandleUtils;
import cn.crtech.cooperop.core.utils.ThreadPoolUtils;

/**
 * 新组织结构下的审核请求入口servlet
 * @author chenjunhong
 * 2021年2月8日
 */
public class IFSAuditServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private ExecutorService requestPool = null;
	private List<ExecuteService> services = null;
	private String position = "audit";
	
	public void init() throws ServletException {
		print();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("position", position);
		services = new CoreService().loadExecuteService(params);
		//请求无限制，使用默认的缓存线程池
		requestPool = ThreadPoolUtils.loadCachedThreadPool();
	} 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		execute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		execute(req, resp);
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}
	
	private void print() {
		//测试
		System.out.println("当前线程：" + Thread.currentThread().getName());
		System.out.println(this.getServletName());
		System.out.println(this);
	}
	
	//注：新的执行过程
	protected void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		print();
		//1-封装
		RequestTask task = RequestHandleUtils.pretreatment(request, response, position);
		//2-匹配
		ExecuteService service = RequestHandleUtils.findMatchService(task, this.services);
		//3-执行
		if(service!=null) {
			System.out.println("执行服务 - " + service);
			task.setLog(true);
			service.initRequestPool(this.requestPool);
			service.process(task);
		}
	}
	
}

