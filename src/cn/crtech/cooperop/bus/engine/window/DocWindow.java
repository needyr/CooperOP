package cn.crtech.cooperop.bus.engine.window;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import cn.crtech.cooperop.application.authenticate.Authenticate;
import cn.crtech.cooperop.bus.engine.bean.ProcessBean;
import cn.crtech.cooperop.bus.engine.Engine;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CooperopException;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.ELExpression;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.LocalThreadMap;

public class DocWindow implements Window {
	private static final int DEFAULT_LIMIT = 10000;
	private static final int DEFAULT_START = 1;
	private static final String PROCESS_JSP = "/tools/process.jsp";


	@Override
	public void excute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String seq = CommonFun.getSSID();
		CreateWordAssist createExcelAssist = new CreateWordAssist(req, resp, seq);
		new Thread(createExcelAssist).start();
		req.setAttribute("$seq", seq);
		req.getRequestDispatcher(PROCESS_JSP).forward(req, resp);
	}

	class CreateWordAssist implements Runnable {
		private String seq;
		private Map<String, Object> requestParams = new HashMap<String, Object>(0);
		private ProcessBean processBean = new ProcessBean();
		private File template;
		public CreateWordAssist(HttpServletRequest req, HttpServletResponse resp, String seq) throws UnsupportedEncodingException {
			String cp = req.getContextPath() == null ? "" : req.getContextPath();
			String sp = req.getServletPath();
			String res = req.getRequestURI().substring((cp + sp).length() + 1);
			String pageid = res.substring(0, res.lastIndexOf(".")).replace('/', '.');
			this.seq = seq;
			Enumeration<String> headernames = req.getHeaderNames();
			while (headernames.hasMoreElements()) {
				String name = headernames.nextElement();
				processBean.getHeader().put(name, URLDecoder.decode(req.getHeader(name), "UTF-8"));
			}
			Session session = Session.getSession(req, resp);
			processBean.setSequence(seq);
			processBean.setPageid(pageid);
			processBean.setSessionId(session.getId());
			session.put(this.seq, processBean);
			//this.requestParams = getParam(req);
			this.requestParams = CommonFun.requestMap(req);
			setTemplate(req);
		}

		/**
		 * 
		 * 获得request参数，由于编码问题，不能使用comonfun中的得到参数方法.
		 * 
		 * @param request
		 * @return 参数map
		 *         <dt><b>修改历史：</b></dt>
		 */
		private Map<String, Object> getParam(HttpServletRequest request) {
			Map<String, Object> map = new HashMap<String, Object>();
			Enumeration<String> names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				String[] values = request.getParameterValues(name);
				try {
					for (int i = 0; values != null && i < values.length; i++) {
						values[i] = new String(values[i].getBytes("iso-8859-1"), "utf-8");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (values != null && values.length == 1) {
					map.put(name, values[0]);
				} else {
					map.put(name, values);
				}
			}
			return map;
		}

		/**
		 * 
		 * 获取得到导出模版文件，并设置到全局变量.
		 * 
		 * @param req
		 *            <dt><b>修改历史：</b></dt>
		 */
		private void setTemplate(HttpServletRequest req) {
			String pageid = processBean.getPageid();
			String module = pageid.substring(0, pageid.indexOf('.'));
			pageid = pageid.substring(pageid.indexOf('.') + 1);
			String uri = null;
			String custom_id = GlobalVar.getSystemProperty("custom.id");
			if(!CommonFun.isNe(custom_id)){
				uri = GlobalVar.getSystemProperty("view.excel.custom.rule");
				uri = uri.replace("@[CUSTOM]", custom_id);
				uri = uri.replace("@[MODULE]", module);
				uri = uri.replace("@[PAGE]", pageid.replace('.', '/'));
				if(!new File(GlobalVar.getWorkPath() + uri).exists()){
					uri = null;
				}
			}
			if(uri == null){
				uri = GlobalVar.getSystemProperty("view.excel.access.rule");
				uri = uri.replace("@[MODULE]", module);
				uri = uri.replace("@[PAGE]", pageid.replace('.', '/'));
			}
			template = new File(req.getSession().getServletContext().getRealPath(uri));
		}

		/**
		 * <dt><b>修改历史：</b></dt>
		 */
		@Override
		public void run() {
			try {
				processBean.setCurrNumBegin(DEFAULT_START);
				processBean.setCurrNumEnd(DEFAULT_LIMIT);
				processBean.setBeginTime(System.currentTimeMillis());
				processBean.setTotal(-1);
				executeCallActionAndCreateExcel();
				processBean.setEndTime(System.currentTimeMillis());
			} catch (Exception e) {
				processBean.setMessage("生成doc失败！" + e.getMessage());
				e.printStackTrace();
			}
		}

		/**
		 * 
		 * 执行调用action,由于此处不能使用httpservletrequest作参数，故不能使用 FrameEngine的executeCallAction 方法.
		 * 
		 * @param map
		 *            请求参数Map
		 * @return
		 * @throws Exception
		 *             <dt><b>修改历史：</b></dt>
		 */
		public Object executeCallAction(Map<String, Object> map) throws Exception {
			String pageid = processBean.getPageid();
			try {
				Map<String, Object> a = Authenticate.getAction(pageid);
				if (a != null) {
					Class<?> c = (Class<?>) a.get("class");
					Method m = (Method) a.get("method");
					Object t = Engine.callAction(c, m, map);
					return t;
				} else {
					throw new CooperopException(CooperopException.DATA_NOT_FOUND);
				}
			} catch (Exception e) {
				throw e;
			}
		}

		/**
		 * 
		 * 调用action并生成excel .
		 * 
		 * @throws Exception
		 *             <dt><b>修改历史：</b></dt>
		 */
		@SuppressWarnings("unchecked")
		private void executeCallActionAndCreateExcel() throws Exception {
			String pageid = processBean.getPageid();
			pageid = pageid.substring(pageid.indexOf('.') + 1);
			LocalThreadMap.put("pageid", processBean.getPageid());
			LocalThreadMap.put("sessionId", processBean.getSessionId());
			LocalThreadMap.put("start", requestParams.get("start"));
			LocalThreadMap.put("limit", requestParams.get("limit"));
			LocalThreadMap.put("sort", requestParams.get("sort"));
			LocalThreadMap.put("filter", requestParams.get("filter"));
			
			// 用clone是因为FrameEngine.callAction方法中对参数page window等进行了删除，由于存方的数据都是简单数据，故hashMap的clone足矣
			Object t = executeCallAction((HashMap<String, Object>) ((HashMap<String, Object>) requestParams).clone());
			if (t == null) {
				processBean.setMessage("导出失败");
				return;
			}
			if (t instanceof Result) {
				Map<String, Object> temp = new HashMap<String, Object>();
				String filename = null;
				if (CommonFun.isNe(requestParams.get("$exportname"))) {
					filename = "noname";
				} else {
					filename = requestParams.get("$exportname").toString();
				}
				temp.put("filename", filename);
				temp.put(filename, t);
				temp.put(filename + ".name", filename);
				temp.put(filename + ".title", filename);
				String [] ss = requestParams.get("fields").toString().split(",");
				temp.put(filename + ".fields", ss);
				Map<String, Object> m = CommonFun.json2Object((String)requestParams.get("fielddefs"), Map.class);
				temp.put(filename + ".fielddefs", m);
				try {
					long l = System.currentTimeMillis();
					exportWord(temp);
					System.out.println("====================total= " + (System.currentTimeMillis() - l));
				} catch (Throwable e) {
					throw new ServletException(e);
				}
			} else if(t instanceof Map){
				if (template.exists()) {
					try {
						Map<String, Object> m = (Map<String, Object>) t;
						exportWord(m);
					} catch (Throwable e) {
						throw new ServletException(e);
					}
				} else {
					try {
						exportWord((Map<String, Object>) t);
					} catch (Throwable e) {
						throw new ServletException(e);
					}
				}
			}else {
				
			}
		}

		private boolean checkAvailable(String available, Map<String, Object> params) throws Exception {
			if (available == null)
				return true;
			Object value = ELExpression.excuteExpression(available, params);
			if (value == null) {
				return false;
			} else if (value.getClass() == String.class) {
				return "true".equalsIgnoreCase((String) value);
			} else if (value.getClass() == Boolean.class) {
				return (Boolean) value;
			} else {
				return false;
			}
		}

		public void exportWord(Map<String, Object> t) throws Throwable {
			   boolean w = false;
			   String rmPath =  GlobalVar.getSystemProperty("rm.tempfilepath");
			   Record re = (Record)t.get("re");
			   StringBuffer sb = new StringBuffer();
			   sb.append("<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></meta>");
			   sb.append("  </head>");
			   sb.append("<body style='width:800px;margin:30px auto;line-height: 24px;font-size:14px;'>");
			   sb.append("<div>");
			   sb.append(t.get("content").toString());
			   sb.append("</div></body><html>");
			   String str = sb.toString();
			   str = str.replace("&nbsp;","");
			   String url = null;
			   if (!"".equals(rmPath)) {
				   // 检查目录是否存在
				   String folderName = CommonFun.getSSID();
				   String path = rmPath + folderName;
				   File tempFolder = new File(path);
				   //创建临时文件夹
				   tempFolder.mkdir();
				   //File fileDir = new File(rmPath);
				   if (tempFolder.exists()) {
					   // 生成临时文件名称
					   String fileName = re.get("system_user_name").toString()+requestParams.get("type_").toString()+".doc";
					   File file=new File(tempFolder + "\\" +fileName);
					   if(file.exists()){
						   file.delete();
					   }
					   byte b[] = str.getBytes();
					   ByteArrayInputStream bais = new ByteArrayInputStream(b);
					   POIFSFileSystem poifs = new POIFSFileSystem();
					   DirectoryEntry directory = poifs.getRoot();
					   DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
					   FileOutputStream ostream = new FileOutputStream(tempFolder + "\\" + fileName);
					   poifs.writeFilesystem(ostream);
					   
					   url = "rm/td/" + folderName + "/" + URLEncoder.encode(fileName, "UTF-8");
					   bais.close();
					   ostream.close();
				   }
			   }
			
			processBean.setDownloadUrl(url);
		}


	}
}
