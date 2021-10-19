package cn.crtech.precheck.server;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.VersionControlClientService;
import cn.crtech.precheck.IMUPCache;

public class IMDownLoadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static volatile long max_update_num = 0 ;

	
	public void init() throws ServletException {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			log.error(e2);
		}
		String max_num = SystemConfig.getSystemConfigValue("hospital_common", "client_max_mun", "10");
		//String max_num = "1";
		String is_im_update = SystemConfig.getSystemConfigValue("hospital_common", "is_im_update","N");
		if("Y".equals(is_im_update)) {
			if(max_update_num < Long.parseLong(max_num)) {
				max_update_num = max_update_num + 1;
				log.info("------------有"+max_update_num+"个客户端正在升级");
				/*try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}*/
				try {
					//HashMap<String, Object> hmap = CommonFun.requestMap(req);
					//@SuppressWarnings("unchecked")
					//Map<String, Object> p = CommonFun.json2Object((String)hmap.get("json"), Map.class);
					Map<String, Object> map = new HashMap<String, Object>();
					String fileName = SystemConfig.getSystemConfigValue("hospital_common", "im_file_name", "iadscpC916.rar");
					ServletOutputStream out = null;
			        FileInputStream fis = null;
			        // 1.获取资源文件的路径，当文件名是中文的时候出现不正常的情况，所以需要进行url编码
			        String path = this.getServletContext().getRealPath("/WEB-INF/apps/hospital_common/resource/im/" + URLEncoder.encode(fileName, "UTF-8"));
			        //String path = this.getServletContext().getRealPath("/WEB-INF/apps/hospital_common/resource/im/111.pdf");
			        String im_version = SystemConfig.getSystemConfigValue("hospital_common", "im_version");
			        String ipAddr = getIpAddr(req);
			        try {
			        	Map<String, Object> cache = new HashMap<String, Object>();
			            // 2.根据获取到的路径，构建文件流对象
			            fis = new FileInputStream(path);
			        	//fis =  get_file_stream(path);
			            out = resp.getOutputStream();
			            // 3.设置让浏览器不进行缓存，不然会发现下载功能在opera和firefox里面好好的没问题，在IE下面就是不行，就是找不到文件
			            resp.setHeader("Pragma", "No-cache");
			            resp.setHeader("Cache-Control", "No-cache");
			            resp.setDateHeader("Expires", -1);
			            // 4.设置Content-type字段
			            resp.setContentType("application/octet-stream");
			            // 5.设置http响应头，告诉浏览器以下载的方式处理我们的响应信息
			            resp.setHeader("content-disposition", "attachment;filename=" + fileName);
			            // 6.开始写文件
			            byte[] buf = new byte[1024];
			            int len = 0;
		            	while ((len = fis.read(buf)) != -1) {
		            		out.write(buf, 0, len);
		            	}
			            map.put("ip_address", ipAddr);
						map.put("version", im_version);
						map.put("state", "1");
						map.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
						cache.putAll(map);
						//new VersionControlClientService().updateByIp(map);
						IMUPCache.updateVersion(cache);
			        } finally {
			        	if(max_update_num > 0 ) {
			        		--max_update_num;
			        	}
			        	if(fis != null) {
			        		fis.close();
			        	}
			            if (out != null) {
			            	out.flush();
			            	out.close();
			            }
			        }
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}
	
	/*private FileInputStream get_file_stream(String path) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fis;
	}*/
	
	// 返回用IP地址
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
		}
		return ip;
	}
}
