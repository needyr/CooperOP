package cn.crtech.cooperop.hospital_common.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.CustomSpecificationDao;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CustomSpecificationService extends BaseService {
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CustomSpecificationDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record record=new CustomSpecificationDao().get(params);
			Iterator<String> iterator = record.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				if(!CommonFun.isNe(record.get(key))) {
					String content = String.valueOf(record.get(key));
					String regxpForImgTag = "<\\s*img\\s*([^>]*)\\s*/?\\s*>";
					Pattern p = Pattern.compile(regxpForImgTag, Pattern.MULTILINE);
					Matcher m = p.matcher(content);
					while(m.find()) {
						String data = m.group(0).trim();//img标签<img src="agwawgagag" />
						String regxpForImgSrc = "(src|SRC)=(\\\"|\\')(.*?)(\\\"|\\')";
						Pattern pattern = Pattern.compile(regxpForImgSrc, Pattern.MULTILINE);
						Matcher matcher = pattern.matcher(data);
						while (matcher.find()) {
							String src_old = matcher.group(0).trim();
							if(src_old.contains("/res/hospital_common/")) {
								String hz = src_old.substring(src_old.indexOf(".")+1, src_old.indexOf("?"));//图片后缀
								String img_name = src_old.substring(0, src_old.indexOf(".")).replace("src=\"/res/hospital_common/drugimg/", ""); //图片名称
								URL resource = Thread.currentThread().getContextClassLoader().getResource("/");
								String courseFile = resource.getPath().substring(1, resource.getPath().length()-8).replace("/", "\\");
								String shuoms_img_address = SystemConfig.getSystemConfigValue("ipc", "shuoms_img_address", "apps\\hospital_common\\resource\\drugimg");
								String address = courseFile+shuoms_img_address+"\\"; //存储路径
								String imgFilePath = address+img_name+"."+hz;//图片位置
								String imageStr = getImageStr(imgFilePath);
								content = content.replace(src_old, "src=\""+ imageStr +"\"");
								record.put(key, content);
							}
						}
					}
				}
			}
			return record;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Iterator<String> iterator = params.keySet().iterator();
			int count = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				if(!CommonFun.isNe(params.get(key))) {
					String content = (String)params.get(key);
					String regxpForImgTag = "<\\s*img\\s*([^>]*)\\s*/?\\s*>";
					Pattern p = Pattern.compile(regxpForImgTag, Pattern.MULTILINE);
					Matcher m = p.matcher(content);
					while(m.find()) {
						count++;
						String data = m.group(0).trim();//img标签<img src="agwawgagag" />
						String regxpForImgSrc = "(src|SRC)=(\\\"|\\')(.*?)(\\\"|\\')";
						Pattern pattern = Pattern.compile(regxpForImgSrc, Pattern.MULTILINE);
						Matcher matcher = pattern.matcher(data);
						while (matcher.find()) {
							String src_old = matcher.group(0).trim();
							String img_name = ((String)params.get("sys_drug_code")) + "_" + count;
							String img_houz = CreateImage(src_old,img_name); //进行存储图片
							if(img_houz == null) {
								return 3;
							}else if("not_save".equals(img_houz)) {
								continue;
							}
							String new_src ="src=\"/res/hospital_common/drugimg/" + img_name + "." + img_houz+"?"+CommonFun.getITEMID()+"\"";
							content = content.replace(src_old, new_src);
							params.put(key, content);
						}
					}
				}
			}
			params.put("revision_date", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			params.remove("his_drug_code");
			return new CustomSpecificationDao().insert(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Iterator<String> iterator = params.keySet().iterator();
			int count = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				if(!CommonFun.isNe(params.get(key))) {
					String content = (String)params.get(key);
					String regxpForImgTag = "<\\s*img\\s*([^>]*)\\s*/?\\s*>";
					Pattern p = Pattern.compile(regxpForImgTag, Pattern.MULTILINE);
					Matcher m = p.matcher(content);
					while(m.find()) {
						count++;
						String data = m.group(0).trim();//img标签<img src="agwawgagag" />
						String regxpForImgSrc = "(src|SRC)=(\\\"|\\')(.*?)(\\\"|\\')";
						Pattern pattern = Pattern.compile(regxpForImgSrc, Pattern.MULTILINE);
						Matcher matcher = pattern.matcher(data);
						while (matcher.find()) {
							String src_old = matcher.group(0).trim();
							String img_name = ((String)params.get("sys_drug_code")) + "_" + count;
							String img_houz = CreateImage(src_old,img_name); //进行存储图片
							if(img_houz == null) {
								return 3;
							}else if("not_save".equals(img_houz)) {
								continue;
							}
							String new_src ="src=\"/res/hospital_common/drugimg/" + img_name + "." + img_houz+"?"+CommonFun.getITEMID()+"\"";
							content = content.replace(src_old, new_src);
							params.put(key, content);
						}
					}
				}
			}
			params.put("revision_date", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			params.remove("his_drug_code");
			return new CustomSpecificationDao().update(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result isHas(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CustomSpecificationDao().isHas(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CustomSpecificationDao().delete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	

	// base64字符串转化成图片---对字节数组字符串进行Base64解码并生成图片
	public static String CreateImage(String string,String name) { 
		boolean contains = string.contains("data:image/");
		boolean cc = string.contains(";base64,");
		if(contains && cc) {
			String imgStr = string.substring(string.indexOf(",")+1, string.length()-1);
			if (imgStr == null) // 图像数据为空
				return null;
			BASE64Decoder decoder = new BASE64Decoder();
			OutputStream out = null;
			try {
				// Base64解码
				byte[] b = decoder.decodeBuffer(imgStr);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {// 调整异常数据
						b[i] += 256;
					}
				}
				String hz = string.substring(string.indexOf("/")+1, string.indexOf(";"));//图片后缀
				String img_name = name; //图片名称
				URL resource = Thread.currentThread().getContextClassLoader().getResource("/");
				String courseFile = resource.getPath().substring(1, resource.getPath().length()-8).replace("/", "\\");
				String shuoms_img_address = SystemConfig.getSystemConfigValue("ipc", "shuoms_img_address", "apps\\hospital_common\\resource\\drugimg");
				String address = courseFile+shuoms_img_address+"\\"; //存储路径
				File craete_file = new File(address);
				if(!craete_file.exists()) {
					craete_file.mkdirs();
				}
				// 生成图片
				String imgFilePath = address+img_name+"."+hz;// 新生成的图片位置
				out = new FileOutputStream(imgFilePath);
				File file = new File(imgFilePath);
				if(file.exists()) {
					file.delete();
				}
				out.write(b);
				out.flush();
				return hz;
			} catch (Exception e) {
				return null;
			}finally {
				if(out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else {
			return "not_save";
		}
	}
	
	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author: 
	 * @CreateTime: 
	 * @return
	 */
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	    //去除回车,换行
	    String encode = encoder.encode(data).replaceAll("\r|\n", "");
	    return "data:image/"+ imgFile.substring(imgFile.indexOf(".")+1, imgFile.length()) + ";base64," + encode;
	}
}
