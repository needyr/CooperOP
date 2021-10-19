package cn.crtech.cooperop.hospital_common.schedule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;
import cn.crtech.cooperop.hospital_common.service.DictCGService;
import cn.crtech.cooperop.hospital_common.util.PdfConvertUtil;

public class GetCG extends AbstractSchedule{
	private static boolean b = false;
	//static int name_no = 1;
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		b = true;
		try{
			String CG_resource_path = SystemConfig.getSystemConfigValue("hospital_common", "CG_resource_path");
			String CG_new_path = SystemConfig.getSystemConfigValue("hospital_common", "CG_new_path");
			copyFolder(CG_resource_path, CG_new_path);
			PDFHandle(CG_new_path,CG_new_path, null , CG_new_path);
		}catch(Exception ex){
			log.debug("sync message: 抓取临床指南失败...");
		}finally{
			b = false;
		}
		return true;
	}
	
	
	public static void main(String[] args) throws Exception {
		//String path = "D:\\自定义PC桌面\\NFM-04-1 中国2型糖尿病防治指南（2017年版）";
		//PDFHandle(path,path, null , path);
		//new PdfConvertUtil().pdfToHtmlPath(path+".pdf", path+".html");
		String CG_resource_path = "H:\\hyt_cg";
		String CG_new_path = "H:\\hyt_cg_html";
		//removeDir(new File(CG_new_path));
		//copyFolder(CG_resource_path, CG_new_path);
		//PDFHandle(CG_new_path,CG_new_path, null , CG_new_path);
	}
	
	private void removeDir(File dir) {
		File[] files=dir.listFiles();
		if(files != null) {
			for(File file:files){
				if(file.isDirectory()){
					removeDir(file);
				}else{
					log.debug(file+":"+file.delete());
				}
			}
			log.debug(dir+":"+dir.delete());
		}
	}

	
	public void PDFHandle(String CG_resource_path, String CG_new_path, String dept_name,String real_path) throws Exception {
		File a=new File(CG_new_path); 
		String[] file=a.list();
		File temp=null;
		PdfConvertUtil pdfConvertUtil = new PdfConvertUtil();
		for (String string : file) {
			if(CG_new_path.endsWith(File.separator)){ 
			temp=new File(CG_new_path+string); 
			} 
			else{ 
			temp=new File(CG_new_path+File.separator+string); 
			}
			if(temp.isFile()){
				try {
					Map<String, Object> map = new HashMap<String, Object>(); 
					String name = temp.getName();
					String new_name = dept_name+"_"+name.substring(0,name.lastIndexOf("."));
					String readPdfText = pdfConvertUtil.readPdfText(CG_resource_path+"/"+name);
					map.put("cg_name", new_name);
					map.put("cg_description", readPdfText);
					int name_no = new DictCGService().insert(map);
					pdfConvertUtil.pdfToHtmlPath(CG_resource_path+"/"+name, real_path+"/"+name_no+".html");
					//++name_no;
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e);
					continue;
				}
			}
			if (temp.isDirectory()) {
				PDFHandle(CG_resource_path+"/"+string,CG_new_path+"/"+string,temp.getName(), real_path); 
			}
			
		}
	}
	
	/** 
	* 复制整个文件夹内容 
	* @param oldPath String 原文件路径 如：c:/fqf 
	* @param newPath String 复制后路径 如：f:/fqf/ff 
	* @return boolean 
	*/ 
	public void copyFolder(String oldPath, String newPath) { 

	try {
		removeDir(new File(newPath));
		(new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹 
		File a=new File(oldPath); 
		String[] file=a.list(); 
		File temp=null; 
		for (int i = 0; i < file.length; i++) { 
			if(oldPath.endsWith(File.separator)){ 
			temp=new File(oldPath+file[i]); 
			}
			else{ 
			temp=new File(oldPath+File.separator+file[i]); 
			} 
			if(temp.isFile()){ 
				FileInputStream input = new FileInputStream(temp); 
				FileOutputStream output = new FileOutputStream(newPath + "/" + 
				(temp.getName()).toString()); 
				byte[] b = new byte[1024 * 5]; 
				int len; 
				while ( (len = input.read(b)) != -1) { 
				output.write(b, 0, len); 
				} 
				output.flush(); 
				output.close(); 
				input.close(); 
			} 
			if(temp.isDirectory()){//如果是子文件夹 
				copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
			} 
		} 
	} 
	catch (Exception e) { 
		log.debug("复制整个文件夹内容操作出错"); 
		e.printStackTrace(); 
	} 

	}
}
