package cn.crtech.cooperop.bus.rm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.crtech.cooperop.application.authenticate.Authenticate;
import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.application.service.CommonService;
import cn.crtech.cooperop.bus.engine.Engine;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.CooperopException;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.MD5;
import cn.crtech.cooperop.bus.util.QrcodeUtil;

public class RMServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2958352453709921811L;

	private static ServletContext servletContext;

	private static GarbageClearService gc;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		servletContext = config.getServletContext();
		gc = new GarbageClearService();
		gc.start();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		super.destroy();
		ResourceManager.distoryOpenOffice();
		gc.terminal();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String server = req.getRequestURL().toString();
		String uri = req.getRequestURI();

		server = server.replaceFirst(uri, "");
		String su = uri.replaceFirst(req.getContextPath(), "");
		su = su.replaceFirst(req.getServletPath(), "");
		su = su.substring(1);

		String[] params = su.split("/");
		String cmd = params[0];
		
		if ("l".equalsIgnoreCase(cmd) || "s".equalsIgnoreCase(cmd) || "x".equalsIgnoreCase(cmd)
				|| "d".equalsIgnoreCase(cmd) || "td".equalsIgnoreCase(cmd) || "uf".equalsIgnoreCase(cmd)
				|| "df".equalsIgnoreCase(cmd) || "map".equalsIgnoreCase(cmd) || "qrcode".equalsIgnoreCase(cmd) 
				|| "appscan".equalsIgnoreCase(cmd)|| "exportexcel".equalsIgnoreCase(cmd)|| "downTemp".equalsIgnoreCase(cmd)||
				"downexcel".equalsIgnoreCase(cmd)|| ResourceManager.RESOURCE_URI.equalsIgnoreCase(cmd) ||
				"pdflookTemp".equalsIgnoreCase(cmd)) {
			doPost(req, resp);
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 允许跨域
		// resp.setHeader("Access-Control-Allow-Origin", "*");
		String server = req.getRequestURL().toString();
		String uri = req.getRequestURI();
		req.setCharacterEncoding("utf-8");
		server = server.replaceFirst(uri, "");
		String su = uri.replaceFirst(req.getContextPath(), "");
		su = su.replaceFirst(req.getServletPath(), "");
		su = su.substring(1);

		String[] params = su.split("/");
		String cmd = params[0];
		String module = params[1];
		log.info(req.getRemoteAddr() + " connect in: " + req.getRequestURI());
		String sequ = req.getParameter("sequ");
		if ("u".equalsIgnoreCase(cmd)) {
			Session session;
			String s = req.getParameter("_sssid");
			if(CommonFun.isNe(req.getParameter("_sssid"))){
				session = Session.getSession(req, resp);
			}else{
				session = Session.getSession(req.getParameter("_sssid"));
			}
			HashMap<String, Object> map = CommonFun.requestMap(req);
			Account user = (Account) session.get("userinfo");
			String userid;
			if (user == null) {
				if(!CommonFun.isMoblie(req) && !CommonFun.isPad(req)){
					if (CommonFun.isNe(map.get("userid")) || CommonFun.isNe(map.get("timestamp"))
							|| CommonFun.isNe(map.get("verify"))) {
						resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
						return;
					}
					String newverify = MD5
							.md5(map.get("userid") + GlobalVar.getSystemProperty("sso.key") + map.get("timestamp"));
					if (!newverify.equals(map.get("verify"))) {
						resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
						return;
					}
					if (Long.parseLong((String) map.get("timestamp")) < System.currentTimeMillis() - 30 * 60 * 1000) {
						resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
						return;
					}
				}				
				userid = (String) map.get("userid");
			} else {
				userid = user.getId();
			}

			List<Object> tmp = new ArrayList<Object>(map.values());
			List<String> fileids = new ArrayList<String>();
			String maxwidthStr = req.getParameter("maxwidth");
			String maxheightStr = req.getParameter("maxheight");
			int maxwidth = 0;
			int maxheight = 0;
			if (!CommonFun.isNe(maxwidthStr)) {
				maxwidth = Integer.parseInt(maxwidthStr);
			}
			if (!CommonFun.isNe(maxheightStr)) {
				maxheight = Integer.parseInt(maxheightStr);
			}
			for (Object o : tmp) {
				if (o instanceof FileItem) {
					try {
						fileids.add(ResourceManager.storeResource(module, userid, (FileItem) o,
								(String) map.get("description"), maxwidth, maxheight));
					} catch (Exception e) {
						log.error(e);
						throw new ServletException(e);
					}
				}
				if (o.getClass().isArray()) {
					Object[] os = (Object[]) o;
					for (Object oc : os) {
						if (oc instanceof FileItem) {
							try {
								fileids.add(ResourceManager.storeResource(module, userid, (FileItem) oc,
										(String) map.get("description"), maxwidth, maxheight));
							} catch (Exception e) {
								log.error(e);
								throw new ServletException(e);
							}
						}
					}
				}
			}
			resp.setContentType("text/html; charset=UTF-8");
			String[] fileid = new String[fileids.size()];
			for (int i = 0; i < fileids.size(); i++) {
				fileid[i] = fileids.get(i);
			}
			List<Record> rtn = new ArrayList<Record>();
			if (!CommonFun.isNe(fileid)) {
				try {
					rtn = ResourceManager.getResource(module, fileid);
				} catch (Exception e) {
					log.error(e);
					throw new ServletException(e);
				}
			}
			Record r = new Record();
			r.put("files", rtn);
			if(CommonFun.isNe(session.get("file_" + sequ))){
				String[] fids = (String[]) session.get("file_" + sequ);
				String[] both = (String[]) ArrayUtils.addAll(fids, fileid);
				session.put("file_" + sequ, both);
			}else{
				session.put("file_" + sequ, fileid);
			}
			resp.getWriter().write(CommonFun.object2Json(r));
			resp.getWriter().flush();
		} else if ("ub".equalsIgnoreCase(cmd)) {
			Session session = Session.getSession(req, resp);
			Account user = (Account) session.get("userinfo");
			if (user == null)
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);

			String userid = user.getId();
			String file_name = su.substring(su.lastIndexOf("/")+1);
			List<String> fileids = new ArrayList<String>();
			try {
				BlobItem bi = new BlobItem(URLDecoder.decode(file_name, "UTF-8"), req.getContentType(), req.getInputStream());
				fileids.add(ResourceManager.storeResource(module, userid, bi, "", 0, 0));
				bi.delete();
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
			resp.setContentType("text/html; charset=UTF-8");
			String[] fileid = new String[fileids.size()];
			for (int i = 0; i < fileids.size(); i++) {
				fileid[i] = fileids.get(i);
			}
			List<Record> rtn = new ArrayList<Record>();
			if (!CommonFun.isNe(fileid)) {
				try {
					rtn = ResourceManager.getResource(module, fileid);
				} catch (Exception e) {
					log.error(e);
					throw new ServletException(e);
				}
			}
			Record r = new Record();
			r.put("files", rtn);
			resp.getWriter().write(CommonFun.object2Json(r));
			resp.getWriter().flush();
		} else if ("ud".equalsIgnoreCase(cmd)) {
			Session session = Session.getSession(req, resp);
			Account user = (Account) session.get("userinfo");
			if (user == null) {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}

			String fileid = params[2];
			int rtn = 0;
			try {
				rtn = ResourceManager.updateDescription(module, user.getId(), fileid, req.getParameter("description"));
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().write(CommonFun.object2Json(rtn));
			resp.getWriter().flush();
		} else if ("l".equalsIgnoreCase(cmd)) {
			
			List<Record> rtn = new ArrayList<Record>();
			if(!CommonFun.isNe(req.getParameter("is_check"))){
				Session session = Session.getSession(req, resp);
				String[] fileids =  (String[]) session.get("file_"+req.getParameter("sequ"));
				if(!CommonFun.isNe(fileids)){
					try {
						rtn = ResourceManager.getResource(module, fileids);
					} catch (Exception e) {
						log.error(e);
						throw new ServletException(e);
					}
				}
			} else if (!CommonFun.isNe(params[2])) {
				String fileid = params[2];
				try {
					rtn = ResourceManager.getResource(module, fileid.split(","));
				} catch (Exception e) {
					log.error(e);
					throw new ServletException(e);
				}
			}
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().write(CommonFun.object2Json(rtn));
			resp.getWriter().flush();
		} else if ("s".equalsIgnoreCase(cmd)) {
			String fileid = params[2];
			boolean thumb = false;
			if (fileid.endsWith("S")) {
				thumb = true;
				fileid = fileid.substring(0, fileid.length() - 1);
			}
			try {
				ResourceManager.showResource(module, fileid, thumb, req, resp);
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
		} else if ("st".equalsIgnoreCase(cmd)) {
			String files = req.getParameter("files");
			String[] fileArray = files.split(",");
			try {
				ResourceManager.getFileLastModify(module, fileArray, req, resp);
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
		} else if ("x".equalsIgnoreCase(cmd)) {
			String fileid = params[2];
			String angle = params[3];
			boolean thumb = false;
			if (fileid.endsWith("S")) {
				thumb = true;
				fileid = fileid.substring(0, fileid.length() - 1);
			}

			try {
				ResourceManager.rotation(module, fileid, thumb, Integer.parseInt(angle), req, resp);
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
		} else if ("d".equalsIgnoreCase(cmd)) {
			String fileid = params[2];
			try {
				ResourceManager.downloadResource(module, fileid, req, resp);
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
		} else if ("td".equalsIgnoreCase(cmd)) {
			String filedir = params[1];
			String fileName = params[2];
			try {
				ResourceManager.downloadTempResource(module, filedir, fileName, servletContext, resp);
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
		} else if ("df".equalsIgnoreCase(cmd)) {
			try {
				ResourceManager.downloadResourceByDir(servletContext, req, resp);
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
		} else if ("map".equalsIgnoreCase(cmd)) {
			try {
				ResourceManager.showMapFile(servletContext, req, resp);
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
		} else if ("uf".equalsIgnoreCase(cmd)) {
			String filedir = su.substring(3).replace(params[params.length - 1], "");
			String fileName = params[params.length - 1];
			try {
				ResourceManager.downloadUserFile(filedir, fileName, servletContext, resp);
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
		} else if ("r".equalsIgnoreCase(cmd)) {

			Session session = Session.getSession(req, resp);
			Account user = (Account) session.get("userinfo");
			if (user == null) {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			String fileid = params[2];
			int rtn = 0;
			if (!CommonFun.isNe(fileid)) {
				try {
					rtn = ResourceManager.deleteResource(module, fileid.split(","));
				} catch (Exception e) {
					log.error(e);
					throw new ServletException(e);
				}
			}
			try {
				resp.setContentType("text/html; charset=UTF-8");
				resp.getWriter().write(CommonFun.object2Json(rtn));
				resp.getWriter().flush();
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
		} else if("qrcode".equalsIgnoreCase(cmd)){
			Session session = Session.getSession(req, resp);
			Account user = (Account) session.get("userinfo");
			if (user == null) {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			HashMap<String, Object> map = CommonFun.requestMap(req);
			if(!CommonFun.isNe(map.get("qrcode_content"))){
				try {
					String fileid = QrcodeUtil.qrCodeEncode(module, user.getId(), (String)map.get("qrcode_content"), (String)map.get("qrcode_filename"));
					Record rtn = ResourceManager.getResource(module, fileid);
					resp.setContentType("text/html; charset=UTF-8");
					resp.getWriter().write(CommonFun.object2Json(rtn));
					resp.getWriter().flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				String content = server+"/fileupload.jsp?_CRSID="+session.getId()+"&sequ="+params[2]+"&module="+module;
				QrcodeUtil.qrCodeEncode(content, new File(GlobalVar.getSystemProperty("rm.qrcode.path"), params[2]+".png"));
				try {    
					ServletOutputStream outStream = resp.getOutputStream();// 得到向客户端输出二进制数据的对象    
					FileInputStream fis = new FileInputStream(new File(GlobalVar.getSystemProperty("rm.qrcode.path"),params[2]+".png")); //  
					// 读数据    
					byte data[] = new byte[1000];    
					while (fis.read(data) > 0) {    
						outStream.write(data);    
					}    
					fis.close();    
					resp.setContentType("image/*"); // 设置返回的文件类型    
					outStream.write(data); // 输出数据    
					outStream.close();    
				} catch (IOException e) {    
					e.printStackTrace();    
				}    
			}
			
		} else if("i".equalsIgnoreCase(cmd)){
			Record r = new Record();
			r.put("sequid", CommonFun.getSSID());
			resp.getWriter().write(CommonFun.object2Json(r));
		}else if("appscan".equalsIgnoreCase(cmd)){
			Session session = Session.getSession(req, resp);
			String content = "{\"loginid\":\""+session.getId()+"\",\"type\":\"barcode_login\"}";
			QrcodeUtil.qrCodeEncode(content, resp.getOutputStream());
		}else if("downTemp".equalsIgnoreCase(cmd)){
			String file = su.substring(su.lastIndexOf("/")+1);
			file = URLDecoder.decode(file, "UTF-8");
			String uu = GlobalVar.getSystemProperty("view.resource.access.rule");

			uu = uu.replace("@[MODULE]", module);
			uu = uu.replace("@[RESOURCE]", file);

			File resource = new File(req.getSession().getServletContext()
					.getRealPath(uu));
			if(resource.exists()){
				FileInputStream fis = null;
		        ServletOutputStream out = null;
		        try{
		        	fis = new FileInputStream(resource);
		        	resp.setContentType("application/vnd.ms-excel;charset=utf-8");
		        	resp.setHeader("Content-Disposition", "attachment;filename="
		        			+ new String((file).getBytes(), "iso-8859-1"));
		        	out = resp.getOutputStream();
		        	byte[] bt = new byte[1024];
		        	int length = 0;
		        	while((length=fis.read(bt))!=-1){
		        		out.write(bt,0,length);
		        	}
		        }finally{
		        	if(fis != null){
		        		fis.close();
		        	}
		        	if(out != null){
		        		out.close();
		        	}
		        }
			}
	        
		}else if("exportexcel".equalsIgnoreCase(cmd)){
			HashMap<String, Object> map = CommonFun.requestMap(req);
			List<Map<String, Object>> datas = null;
			if(CommonFun.isNe(map.get("datas"))){
				String pageid = (String) map.get("pageid");
				try {
					Map<String, Object> a = Authenticate.getAction(pageid);
					if (a != null) {
						Class<?> c = (Class<?>) a.get("class");
						Method m = (Method) a.get("method");
						datas = (List<Map<String, Object>>) Engine.callAction(c, m, map);
					} else {
						throw new CooperopException(CooperopException.DATA_NOT_FOUND);
					}
				} catch (Exception e) {
					e.printStackTrace();;
				}
			}else{
				datas = CommonFun.json2Object((String)map.get("datas"), List.class);
			}
			
			if(datas != null){
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("sheet1");
				sheet.setColumnWidth(0, 4000);
		    	HSSFCellStyle style = wb.createCellStyle();
		    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    	style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    	for(int i=0; i< datas.size(); i++){
		    		HSSFRow row = sheet.createRow(i);
			    	row.setHeight((short) 500);
		    		Map<String, Object> f = datas.get(i);
		    		Iterator<String> it = f.keySet().iterator();
		    		int indx = 0;
		    		while(it.hasNext()){
		    			String key = it.next();
		    			HSSFCell cell = row.createCell(indx);
			    		cell.setCellValue((String)f.get(key));
			    		cell.setCellStyle(style);
			    		indx++;
		    		}
		    		
		    	}
		    	
		    	resp.setCharacterEncoding("UTF-8");
		    	resp.setContentType("application/octet-stream;charset=UTF-8");
	            OutputStream out = resp.getOutputStream();
	            wb.write(out);
		    	 /*ByteArrayOutputStream os = new ByteArrayOutputStream();
		         wb.write(os);
		         byte[] content = os.toByteArray();
		         InputStream is = new ByteArrayInputStream(content);
		         resp.reset();
		         resp.setContentType("application/vnd.ms-excel;charset=utf-8");
		         resp.setHeader("Content-Disposition", "attachment;filename="
		             + new String((map.get("$exportname") + ".xls").getBytes(), "iso-8859-1"));
		         ServletOutputStream out = resp.getOutputStream();
		         BufferedInputStream bis = null;
		         BufferedOutputStream bos = null;
		         
		         try {
	        	 	bis = new BufferedInputStream(is);
	        	 	bos = new BufferedOutputStream(out);
	        	 	byte[] buff = new byte[2048];
	        	 	int bytesRead;
	        	 	while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	        	 		bos.write(buff, 0, bytesRead);
	        	 	}
		         } catch (Exception e) {
		        	 e.printStackTrace();
		         } finally {
		        	 if (bis != null)
		        		 bis.close();
		        	 if (bos != null)
		        		 bos.close();
		         }*/
			}
		}else if("downexcel".equalsIgnoreCase(cmd)){
			String pageid = req.getParameter("pageid");
			String[] s = pageid.split("\\.");
			Map<String, Object> pageinfo = new HashMap<String, Object>();
			pageinfo.put("system_product_code", s[0]);
			pageinfo.put("type", s[1]);
			pageinfo.put("flag", s[2]);
			pageinfo.put("id", s[3]);
			
			List<Map<String, Object>> fields = null;
			try {
				Map<String, Object> fmap = new CommonService().getFields(pageinfo);
				if("Y".equals(req.getParameter("is_mx"))){
					String tableid = req.getParameter("tableid");
					if(tableid != null){
						List<Map<String, Object>> tables = (List<Map<String, Object>>) fmap.get("tables");
						for(Map<String, Object> t : tables){
							if(tableid.equals(t.get("tableid"))){
								fields = (List<Map<String, Object>>) t.get("fields");
							}
						}
					}
				}else{
					fields = (List<Map<String, Object>>) fmap.get("hz_fields");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(fields != null){
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("sheet1");
				sheet.setColumnWidth(0, 4000);
		    	sheet.setColumnWidth(1, 3500);
		    	HSSFCellStyle style = wb.createCellStyle();
		    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    	style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    	HSSFRow row = sheet.createRow(0);
		    	row.setHeight((short) 500);
		    	HSSFRow row1 = sheet.createRow(1);
		    	row1.setHeight((short) 500);
		    	for(int i=0; i< fields.size(); i++){
		    		Map<String, Object> f = fields.get(i);
		    		HSSFCell cell = row.createCell(i);
		    		HSSFCell cell1 = row1.createCell(i);
		    		cell.setCellValue((String)f.get("chnname"));
		    		cell1.setCellValue((String)f.get("name"));
		    		cell.setCellStyle(style);
		    		cell1.setCellStyle(style);
		    	}
		    	 ByteArrayOutputStream os = new ByteArrayOutputStream();
		         wb.write(os);
		         byte[] content = os.toByteArray();
		         InputStream is = new ByteArrayInputStream(content);
		         resp.reset();
		         resp.setContentType("application/vnd.ms-excel;charset=utf-8");
		         resp.setHeader("Content-Disposition", "attachment;filename="
		             + new String((pageinfo.get("id") + ".xls").getBytes(), "iso-8859-1"));
		         ServletOutputStream out = resp.getOutputStream();
		         BufferedInputStream bis = null;
		         BufferedOutputStream bos = null;
		    
		         try {
	        	 	bis = new BufferedInputStream(is);
	        	 	bos = new BufferedOutputStream(out);
	        	 	byte[] buff = new byte[2048];
	        	 	int bytesRead;
	        	 	while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	        	 		bos.write(buff, 0, bytesRead);
	        	 	}
		         } catch (Exception e) {
		        	 e.printStackTrace();
		         } finally {
		        	 if (bis != null)
		        		 bis.close();
		        	 if (bos != null)
		        		 bos.close();
		         }
			}
		} else if (ResourceManager.RESOURCE_URI.equals(cmd)) {
			String f = su.substring(ResourceManager.RESOURCE_URI.length() + 1);
			String mt = req.getParameter("m");
			String st = req.getParameter("s");
			String dt = req.getParameter("d");
			String fn = CommonFun.isNe(st) ? dt : st;
			
			File file = new File(GlobalVar.getSystemProperty("rm.path"), f);

			if (!file.exists()) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
			// 设置缓存
			if (ResourceManager.checkNotModified(req, resp, file.lastModified())) {
				return;
			}
			InputStream stream = null;
			OutputStream os = null;

			try {

				int length = (int) file.length();
				int start = 0;
				int end = length - 1;

				Enumeration<String> headers = req.getHeaderNames();

				while (headers.hasMoreElements()) {
					String h = headers.nextElement();
					log.debug(h + ": " + req.getHeader(h));
				}

				String range = CommonFun.isNe(req.getHeader("Range")) ? req.getHeader("range") : req.getHeader("Range");
				if (CommonFun.isNe(range)) {
					resp.setContentType(mt + "; charset=UTF-8");
					resp.setHeader("Content-Disposition", (CommonFun.isNe(st) ? "attachment;" : "") + "filename=\"" + new String(fn.getBytes("GBK"), "ISO-8859-1") + "\"");
					resp.setHeader("Accept-Ranges", "bytes");
					resp.setDateHeader("Last-Modified", file.lastModified());
					resp.setDateHeader("Expires", System.currentTimeMillis() + ResourceManager.EXPIRE_TIME);
					resp.setHeader("Content-Length", String.format("%s", file.length()));
					
					stream = new FileInputStream(file);

					os = resp.getOutputStream();

					int readBytes = 0;

					byte buffer[] = new byte[8192];

					while ((readBytes = stream.read(buffer, 0, 8192)) != -1) {

						os.write(buffer, 0, readBytes);

					}
				} else {
					range = CommonFun.isNe(range) ? "" : range;

					Matcher matcher = ResourceManager.RANGE_PATTERN.matcher(range);

					if (matcher.matches()) {
						String startGroup = matcher.group("start");
						start = startGroup.isEmpty() ? start : Integer.valueOf(startGroup);
						start = start < 0 ? 0 : start;

						String endGroup = matcher.group("end");
						end = endGroup.isEmpty() ? end : Integer.valueOf(endGroup);
						end = end > length - 1 ? length - 1 : end;
					}

					int contentLength = end - start + 1;

					resp.setBufferSize(ResourceManager.BUFFER_LENGTH);
					resp.setContentType(mt + "; charset=UTF-8");
					resp.setHeader("Content-Disposition", (CommonFun.isNe(st) ? "attachment;" : "") + "inline;filename=\"" + new String(fn.getBytes("GBK"), "ISO-8859-1") + "\"");
					resp.setHeader("Accept-Ranges", "bytes");
					resp.setDateHeader("Last-Modified", file.lastModified());
					resp.setDateHeader("Expires", System.currentTimeMillis() + ResourceManager.EXPIRE_TIME);
					resp.setHeader("Content-Range", String.format("bytes %s-%s/%s", start, end, length));
					resp.setHeader("Content-Length", String.format("%s", contentLength));
					resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

					stream = new FileInputStream(file);

					os = resp.getOutputStream();

					int bytesRead;
					int bytesLeft = contentLength;
					ByteBuffer buffer = ByteBuffer.allocate(ResourceManager.BUFFER_LENGTH);

					try (SeekableByteChannel input = Files.newByteChannel(file.toPath(), StandardOpenOption.READ); OutputStream output = resp.getOutputStream()) {
						input.position(start);
						while ((bytesRead = input.read(buffer)) != -1 && bytesLeft > 0) {
							buffer.clear();
							output.write(buffer.array(), 0, bytesLeft < bytesRead ? bytesLeft : bytesRead);
							bytesLeft -= bytesRead;
						}
					}
				}

				os.flush();
				os.close();

				stream.close();

			} catch (Exception ex) {
				if (os != null)
					os.close();
				if (stream != null)
					stream.close();
				throw ex;
			}
		}else if("lookTemp".equalsIgnoreCase(cmd)){
			String file = su.substring(su.lastIndexOf("/")+1);
			file = URLDecoder.decode(file, "UTF-8");
			String uu = GlobalVar.getSystemProperty("view.resource.access.rule");

			uu = uu.replace("@[MODULE]", module);
			uu = uu.replace("@[RESOURCE]", file);

			File resource = new File(req.getSession().getServletContext()
					.getRealPath(uu));

			if(resource.exists()){
				String suffix = resource.getName().substring(resource.getName().lastIndexOf(".")).toLowerCase();
				String ContentType = "";
				if (".pdf".equals(suffix)){
					ContentType = "application/pdf";
				}else if(".txt".equals(suffix)){
					ContentType = "text/plain";
				}else if(".html".equals(suffix)){
					ContentType = "text/html";
				}else if(".xml".equals(suffix)){
					ContentType = "text/xml";
				}else{
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
				FileInputStream fis = null;
				ServletOutputStream out = null;
				try{
					fis = new FileInputStream(resource);
					resp.setContentType(ContentType+";charset=utf-8");
					resp.setHeader("Content-Disposition", "inline;filename="
							+ new String((file).getBytes(), "iso-8859-1"));
					out = resp.getOutputStream();
					byte[] bt = new byte[1024];
					int length = 0;
					while((length=fis.read(bt))!=-1){
						out.write(bt,0,length);
					}
				}finally{
					if(fis != null){
						fis.close();
					}
					if(out != null){
						out.close();
					}
				}
			}else{
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}else{
			log.error(req.getRemoteAddr() + " bad request: " + req.getRequestURI());
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}

	private class BlobItem implements FileItem {
		private static final long serialVersionUID = 1L;

		private File file = null;
		private String fileName = null;
		private String contentType = null;

		public BlobItem(String filename, String contenttype, InputStream io) throws Exception {
			super();
			this.fileName = filename;
			this.contentType = contenttype;
			String mimetype = URLConnection.guessContentTypeFromName(filename);
			if (mimetype != null)
				this.contentType = mimetype;
			FileOutputStream fos = null;
			File f = new File(GlobalVar.getSystemProperty("rm.tempfilepath"));
			if (!f.exists()) {
				f.mkdirs();
			}
			f = new File(f, CommonFun.getSSID());
			if (f.exists()) {
				f.delete();
			}
			try {
				fos = new FileOutputStream(f);
				int readBytes = 0;
				byte buffer[] = new byte[8192];
				while ((readBytes = io.read(buffer, 0, 8192)) != -1) {
					fos.write(buffer, 0, readBytes);
				}
				fos.flush();
			} catch (Throwable ex) {
				throw new ServletException(ex);
			} finally {
				if (fos != null) {
					fos.close();
				}
				if (io != null) {
					io.close();
				}
			}
			this.file = f;
		}

		@Override
		public void delete() {
			file.delete();
		}

		@Override
		public byte[] get() {
			byte buffer[] = new byte[(int) file.length()];
			FileInputStream fio = null;
			try {
				fio = new FileInputStream(file);
				fio.read(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fio != null) {
					try {
						fio.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return buffer;
		}

		@Override
		public String getContentType() {
			return this.contentType;
		}

		@Override
		public String getFieldName() {
			return "blob";
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new FileInputStream(file);
		}

		@Override
		public String getName() {
			return this.fileName;
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			return null;
		}

		@Override
		public long getSize() {
			return this.file.length();
		}

		@Override
		public String getString() {
			return this.fileName + "[" + this.contentType + "]: " + this.file.getAbsolutePath();
		}

		@Override
		public String getString(String encoding) throws UnsupportedEncodingException {
			return new String((this.fileName + "[" + this.contentType + "]: " + this.file.getAbsolutePath()).getBytes(), encoding);
		}

		@Override
		public boolean isFormField() {
			return false;
		}

		@Override
		public boolean isInMemory() {
			return false;
		}

		@Override
		public void setFieldName(String name) {
		}

		@Override
		public void setFormField(boolean state) {
		}

		@Override
		public void write(File file) throws Exception {
		}

	}

}
