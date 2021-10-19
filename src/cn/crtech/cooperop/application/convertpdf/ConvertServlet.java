package cn.crtech.cooperop.application.convertpdf;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.rm.core.service.ResourceIndexService;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class ConvertServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6131502256691097322L;
	private String sourceSWFTools;			//SWFTools安装目录
	private String sourceTomcat;			//SWFTools安装目录
	private String sourceOpenOffice;			//openoffice安装目录
	private Runtime r;		
	
	public void init() throws ServletException {
		//sourceFile = new File("D:/Users/jq123/Desktop/123.docx");
		//pdfFile = new File("E:/apache/apache-tomcat-8.0.24/readonline/swfFile/s.pdf");
		//swfFile = new File("E:/apache/apache-tomcat-8.0.24/readonline/swfFile/s.pdf.swf");
		
		sourceSWFTools = GlobalVar.getSystemProperty("sfwtools.path");
		sourceTomcat = GlobalVar.getSystemProperty("tomcat.path");
//		sourceTomcat = "E:/apache/apache-tomcat-8.0.24";
		sourceOpenOffice = GlobalVar.getSystemProperty("openoffice.path");
		
		//TODO 自动开openoffice
		startProcess();
		log.debug("第一步：生成文件对象，准备转换");
	}
	private void startProcess(){
		try {
			Runtime.getRuntime().exec(sourceOpenOffice +" -headless -accept=\"socket,host=127.0.0.1,port=8154;urp;\" -nofirststartwizard");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private boolean checkProcess(){
		Process process = null;
		Scanner in = null;
		try {
           process = Runtime.getRuntime().exec("tasklist");
           in = new Scanner(process.getInputStream());
           while (in.hasNextLine()) {
        	   String processString = in.nextLine();
        	   if (processString.contains("soffice.exe")) {
        		  return true;
        	   }
           }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	if(in != null){
        		in.close();
        	}
        	if(process != null){
        		process.destroy();
        	}
        }
		return false;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(!checkProcess()){
			startProcess();
		}
		File sourceFile;		//转换源文件
		File pdfFile;			//PDF目标文件
		File swfFile;			//SWF目标文件
		File htmlFile;			//SWF目标文件
		String server = req.getRequestURL().toString();
		String uri = req.getRequestURI();
		req.setCharacterEncoding("utf-8");
		server = server.replaceFirst(uri, "");
		String su = uri.replaceFirst(req.getContextPath(), "");
		su = su.replaceFirst(req.getServletPath(), "/convertUtil");
		su = su.replaceFirst(req.getServletPath(), "");
		su = su.substring(1);

		String[] params = su.split("/");
		//String cmd = params[0];
		String module = params[2];
		
		String fileid = params[3];
		File file ;
		String type;
		boolean thumb = false;
		if (fileid.endsWith("S")) {
			thumb = true;
			fileid = fileid.substring(0, fileid.length() - 1);
		}
		try {
			ResourceIndexService service = new ResourceIndexService();
			Record rtn = service.getResource(module, fileid);
			file = ResourceManager.getFile(thumb, rtn);
			type = rtn.getString("file_name").substring(rtn.getString("file_name").lastIndexOf(".") + 1);
			if("doc".equals(type) || "docx".equals(type) || "ppt".equals(type) || "pptx".equals(type) || "xls".equals(type)|| "xlsx".equals(type) || "pdf".equals(type)){
				OutputStream os = null;
				InputStream stream = null;
				try {
					stream = new BufferedInputStream(new FileInputStream(file));
					sourceFile = new File(sourceTomcat + "/readonline/swfFile/",fileid+"."+type);
					os = new FileOutputStream(sourceFile);
			
					int readBytes = 0;
			
					byte buffer[] = new byte[8192];
			
					while ((readBytes = stream.read(buffer, 0, 8192)) != -1) {
			
						os.write(buffer, 0, readBytes);
			
					}
					os.flush();
				} catch (Exception ex) {
					log.error(ex.getMessage());
					throw ex;
				} finally {
					if (os != null)
						try {
							os.close();
						} catch (Exception e) {
							;
						}
					if (stream != null)
						stream.close();
				}
				if("pdf".equals(type)){
					pdfFile = sourceFile;
				}
			}else{
				return;
			}
			if (!file.exists()) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			//String filename = new String(((String) rtn.get("file_name")).getBytes("GBK"), "ISO-8859-1");
		} catch (Exception e) {
			log.error(e);
			throw new ServletException(e);
		}
		
		pdfFile = new File(sourceTomcat + "/readonline/swfFile/",fileid+".pdf");
		swfFile = new File(sourceTomcat + "/readonline/swfFile/",fileid+".pdf.swf");
		htmlFile = new File(sourceTomcat + "/readonline/swfFile/",fileid+".jsp");
		//转换成pdf文件
		if(sourceFile.exists()) {
			if(!pdfFile.exists()) {
				try {
					SocketOpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8154);
					connection.connect();
					DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);   
					converter.convert(sourceFile, pdfFile);
					//pdfFile.createNewFile();
					connection.disconnect();
					log.debug("第二步：转换为PDF格式	路径" + pdfFile.getPath());
				} catch (java.net.ConnectException e) {
					e.printStackTrace();
					log.debug("OpenOffice服务未启动");
					log.error(e);
					throw e;
				} catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
					e.printStackTrace();
					log.debug("读取文件失败");
					log.error(e);
					throw e;
				} catch (Exception e){
					e.printStackTrace();
					log.error(e);
					throw e;
				}
			} else {
				log.debug("已转换为PDF，无需再次转换");
			}
		} else {
			log.debug("要转换的文件不存在");
		} 
		//转换成swf文件
		r = Runtime.getRuntime();
		HttpSession session = req.getSession();
		if("Y".equals(req.getParameter("use_pdf"))){
			/*if(!htmlFile.exists()){
				pdf2html("D:/CooperOP/pdf2html/pdf2htmlEX.exe " ,pdfFile.getPath(), sourceTomcat+"/readonline/swfFile", fileid+".html");
				InputStreamReader isr = null;
				OutputStream os = null;
				BufferedReader br = null;
				PrintWriter pw = null;
				try {
					os = new FileOutputStream(new File(sourceTomcat + "/readonline/swfFile/"+fileid+".jsp"));
					
					pw = new PrintWriter(os);
					pw.println("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>");
					isr = new InputStreamReader(new FileInputStream(sourceTomcat + "/readonline/swfFile/"+fileid+".html"));
					br = new BufferedReader(isr);
					String line=null;
					while ( (line = br.readLine()) != null) {
					    if (pw != null){
					    	pw.println(line);
					    }
					}
		            if (pw != null){
		            	pw.flush();
		            }
				 } catch (IOException ioe) {
			            ioe.printStackTrace();  
			        } finally{
			        	try {
			        		if(pw!=null)
			        			pw.close();
			        		if(br!=null)
			        			br.close();
			        		if(isr!=null)
			        			isr.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
			        }
			}*/
			Record r = new Record();
			r.put("fileName_pdf", "/readonline/swfFile/"+fileid+".pdf");
			resp.getWriter().write(CommonFun.object2Json(r));
			resp.getWriter().flush();
		}else{
			if(!swfFile.exists()){
				if(pdfFile.exists()) {
					try {
						Process p = r.exec(sourceSWFTools + " " + pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9");
						p.waitFor();
						swfFile.createNewFile();
						log.debug("第三步：转换为SWF格式	路径：" + swfFile.getPath());
						log.debug("第si步：转换为SWF格式mingcheng：" + swfFile.getName());
						/*if(pdfFile.exists()) {//删除pdf临时文件
							pdfFile.delete();
						}*/
						if(sourceFile.exists()) {//删除源文件临时文件
							sourceFile.delete();
						}
					} catch (Exception e) {
						e.printStackTrace();
						try {
							throw e;
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					log.debug("PDF文件不存在，无法转换");
				}
			} else {
				log.debug("已经转为SWF文件，无需再次转换");
			}
			session.setAttribute("fileName", swfFile.getName());
			resp.sendRedirect(req.getContextPath()+"/readFile.jsp");
		}
		
	}
	
	@Override
	public void destroy() {
		//关闭  openoffice服务
		Process process = null;
		Scanner in = null;
		try {
           process = Runtime.getRuntime().exec("tasklist");
           in = new Scanner(process.getInputStream());
           while (in.hasNextLine()) {
        	   String processString = in.nextLine();
        	   if (processString.contains("soffice.exe")) {
        		   String cmd = "taskkill /f /im soffice.exe";
        		   process = Runtime.getRuntime().exec(cmd);
        		   log.debug("openoffice正常关闭.......");
        	   }
           }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	if(in != null){
        		in.close();
        	}
        	if(process != null){
        		process.destroy();
        	}
        }
		super.destroy();
	}
	
	
	public static boolean pdf2html(String exeFilePath,String pdfFile,String destDir,String htmlFileName){
		if (!(exeFilePath != null && !"".equals(exeFilePath) && pdfFile != null
                && !"".equals(pdfFile) && htmlFileName != null && !""
                    .equals(htmlFileName))) {
            log.debug("传递的参数有误！");
            return false;
        }
        Runtime rt = Runtime.getRuntime();
        StringBuilder command = new StringBuilder();
        command.append(exeFilePath).append(" ");
        if (destDir != null && !"".equals(destDir.trim()))// 生成文件存放位置,需要替换文件路径中的空格
            command.append("--dest-dir ").append(destDir.replace(" ", "\" \""))
                    .append(" ");
        command.append("--optimize-text 1 ");// 尽量减少用于文本的HTML元素的数目 (default: 0)
        command.append("--zoom 1.4 ");
        command.append("--process-outline 0 ");// html中显示链接：0——false，1——true
        command.append("--font-format woff ");// 嵌入html中的字体后缀(default ttf)
                                                // ttf,otf,woff,svg
        command.append(pdfFile.replace(" ", "\" \"")).append(" ");// 需要替换文件路径中的空格
        if (htmlFileName != null && !"".equals(htmlFileName.trim())) {
            command.append(htmlFileName);
            /*if (htmlFileName.indexOf(".html") == -1)
                command.append(".html");*/
        }
        try {
            log.debug("Command："+command.toString());  
            Process p = rt.exec(command.toString());  
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");                
            //开启屏幕标准错误流  
            errorGobbler.start();    
            StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(), "STDOUT");    
            //开启屏幕标准输出流  
            outGobbler.start();   
            int w = p.waitFor();  
            int v = p.exitValue();  
            if(w==0&&v==0){  
                return true;  
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  
	}
	
	public static void main(String[] args) {
		pdf2html("E:/java-source/pdf2html/pdf2htmlEX.exe ",
				"E:/java-source/test.pdf","E:/java-source","test.html");
	}
}

