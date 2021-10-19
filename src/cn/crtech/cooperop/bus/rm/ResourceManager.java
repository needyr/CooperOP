package cn.crtech.cooperop.bus.rm;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.jdom.Document;

import cn.crtech.cooperop.bus.license.License;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.rm.core.service.ResourceIndexService;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.MD5;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class ResourceManager {

	public static void main(String[] args) {
		try {
//			GlobalVar.init("D:/product_new/source/speedframework/sfw.war/", "D:/product_new/source/speedframework/sfw.war/WEB-INF/config/sfw.properties");
//			License.validate();
//			for (int i = 0; i < 10001; i++) {
//				String path = getStorePath();
//				File file = new File(GlobalVar.getSystemProperty("rm.path"), path);
//				file = new File(file, generateResourceID());
//				file.createNewFile();
//				log.debug((i + 1) + ":" + file.getAbsolutePath());
//			}
			
			distoryOpenOffice();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// // Rotation r = new Rotation();
		// File f = new File("e:/11.jpg");
		// // rotation("assets", "42a2c943bca301b7b1dd828b599a7717", true, 90,
		// null, null);
		// rotation(img, angle)
		// BufferedImage bi = ImageIO.read(new FileImageInputStream(f));
		// BufferedImage bt = r.apply(bi, 90);
		// ImageIO.write(bt, "jpg", new FileOutputStream("e:/11zz.jpg"));
	}

	public static final String THUMB_EXT = "S";

	public static final int IS_TEMP = 1;
	public static final int NOT_TEMP = 0;
	public static final int TEMP_OPER_NORMAL = 0;
	public static final int TEMP_OPER_INSERT = 1;
	public static final int TEMP_OPER_UPDATE = 2;
	public static final int TEMP_OPER_DELETE = 3;

	protected static final int BUFFER_LENGTH = 1024 * 16;
	protected static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;
	protected static final Pattern RANGE_PATTERN = Pattern.compile("bytes=(?<start>\\d*)-(?<end>\\d*)");
	
	protected static final String RESOURCE_URI = "res";

	public static Record getResource(String module, String fileid) throws Exception {
		ResourceIndexService service = new ResourceIndexService();
		return service.getResource(module, fileid);
	}

	public static List<Record> getResource(String module, String[] fileids) throws Exception {
		ResourceIndexService service = new ResourceIndexService();
		return service.getResource(module, fileids);
	}

	/*
	 * public static void showResourceRange(String module, String fileid,
	 * boolean thumb, HttpServletRequest req, HttpServletResponse resp) throws
	 * Exception { ResourceIndexService service = new ResourceIndexService();
	 * Record rtn = service.getResource(module, fileid);
	 * 
	 * if (rtn == null) { resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	 * return; }
	 * 
	 * File file = getFile(thumb, rtn);
	 * 
	 * if (!file.exists()) { resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	 * return; }
	 * 
	 * // 设置缓存 if (checkNotModified(req, resp, file.lastModified())) { return; }
	 * 
	 * int length = (int) file.length(); int start = 0; int end = length - 1;
	 * 
	 * Enumeration<String> headers = req.getHeaderNames();
	 * 
	 * while (headers.hasMoreElements()) { String h = headers.nextElement();
	 * log.debug(h + ": " + req.getHeader(h)); }
	 * 
	 * String range = CommonFun.isNe(req.getHeader("Range")) ?
	 * req.getHeader("range") : req.getHeader("Range"); range =
	 * CommonFun.isNe(range) ? "" : range;
	 * 
	 * Matcher matcher = RANGE_PATTERN.matcher(range);
	 * 
	 * if (matcher.matches()) { String startGroup = matcher.group("start");
	 * start = startGroup.isEmpty() ? start : Integer.valueOf(startGroup); start
	 * = start < 0 ? 0 : start;
	 * 
	 * String endGroup = matcher.group("end"); end = endGroup.isEmpty() ? end :
	 * Integer.valueOf(endGroup); end = end > length - 1 ? length - 1 : end; }
	 * 
	 * int contentLength = end - start + 1;
	 * 
	 * resp.setContentType((String) rtn.get("mime_type") + "; charset=UTF-8");
	 * resp.setBufferSize(BUFFER_LENGTH); resp.setHeader("Content-Disposition",
	 * "inline;filename=\"" + new String(((String)
	 * rtn.get("file_name")).getBytes("GBK"), "ISO-8859-1") + "\"");
	 * resp.setHeader("Accept-Ranges", "bytes");
	 * resp.setDateHeader("Last-Modified", file.lastModified());
	 * resp.setDateHeader("Expires", System.currentTimeMillis() + EXPIRE_TIME);
	 * resp.setHeader("Content-Range", String.format("bytes %s-%s/%s", start,
	 * end, length)); resp.setHeader("Content-Length", String.format("%s",
	 * contentLength)); resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
	 * 
	 * InputStream stream = null; OutputStream os = null; try { stream = new
	 * FileInputStream(file);
	 * 
	 * os = resp.getOutputStream();
	 * 
	 * int bytesRead; int bytesLeft = contentLength; ByteBuffer buffer =
	 * ByteBuffer.allocate(BUFFER_LENGTH);
	 * 
	 * try (SeekableByteChannel input = Files.newByteChannel(file.toPath(),
	 * StandardOpenOption.READ); OutputStream output = resp.getOutputStream()) {
	 * input.position(start); while ((bytesRead = input.read(buffer)) != -1 &&
	 * bytesLeft > 0) { buffer.clear(); output.write(buffer.array(), 0,
	 * bytesLeft < bytesRead ? bytesLeft : bytesRead); bytesLeft -= bytesRead; }
	 * } os.flush(); os.close();
	 * 
	 * stream.close();
	 * 
	 * } catch (Exception ex) { if (os != null) os.close(); if (stream != null)
	 * stream.close(); throw ex; } }
	 */
	public static void showResource(String module, String fileid, boolean thumb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ResourceIndexService service = new ResourceIndexService();
		Record rtn = service.getResource(module, fileid);

		if (rtn == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		File file = getFile(thumb, rtn);

		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		String contextPath = req.getContextPath() == null ? "" : req.getContextPath();
		resp.sendRedirect(contextPath + req.getServletPath() + "/" + RESOURCE_URI + rtn.getString("path") + "/" + file.getName() + "?m=" + rtn.getString("mime_type") + "&s="
		+ URLEncoder.encode(rtn.getString("file_name"), "UTF-8"));


		/*resp.setContentType((String) rtn.get("mime_type") + "; charset=UTF-8");
		resp.setHeader("Content-Disposition", "filename=\"" + new String(((String) rtn.get("file_name")).getBytes("GBK"), "ISO-8859-1") + "\"");
		// 设置缓存
		if (checkNotModified(req, resp, file.lastModified())) {
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
				stream = new FileInputStream(file);

				os = resp.getOutputStream();

				int readBytes = 0;

				byte buffer[] = new byte[8192];

				while ((readBytes = stream.read(buffer, 0, 8192)) != -1) {

					os.write(buffer, 0, readBytes);

				}
			} else {
				range = CommonFun.isNe(range) ? "" : range;

				Matcher matcher = RANGE_PATTERN.matcher(range);

				if (matcher.matches()) {
					String startGroup = matcher.group("start");
					start = startGroup.isEmpty() ? start : Integer.valueOf(startGroup);
					start = start < 0 ? 0 : start;

					String endGroup = matcher.group("end");
					end = endGroup.isEmpty() ? end : Integer.valueOf(endGroup);
					end = end > length - 1 ? length - 1 : end;
				}

				int contentLength = end - start + 1;

				resp.setContentType((String) rtn.get("mime_type") + "; charset=UTF-8");
				resp.setBufferSize(BUFFER_LENGTH);
				resp.setHeader("Content-Disposition", "inline;filename=\"" + new String(((String) rtn.get("file_name")).getBytes("GBK"), "ISO-8859-1") + "\"");
				resp.setHeader("Accept-Ranges", "bytes");
				resp.setDateHeader("Last-Modified", file.lastModified());
				resp.setDateHeader("Expires", System.currentTimeMillis() + EXPIRE_TIME);
				resp.setHeader("Content-Range", String.format("bytes %s-%s/%s", start, end, length));
				resp.setHeader("Content-Length", String.format("%s", contentLength));
				resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

				stream = new FileInputStream(file);

				os = resp.getOutputStream();

				int bytesRead;
				int bytesLeft = contentLength;
				ByteBuffer buffer = ByteBuffer.allocate(BUFFER_LENGTH);

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
		}*/
	}

	public static File getFile(boolean thumb, Record rtn) {
		File folder = new File(GlobalVar.getSystemProperty("rm.path"), rtn.getString("path"));
		if (thumb) {
			File file = new File(folder, rtn.getString("file_id") + THUMB_EXT);
			if (file.exists()) {
				return file;
			} else {
				try {
					file = new File(folder, rtn.getString("file_id"));
					String file_ext = rtn.getString("file_name").substring(rtn.getString("file_name").lastIndexOf(".") + 1).toLowerCase();
					if (file.exists()) {
						if (GlobalVar.getSystemProperty("rm.image.mimetype").indexOf(rtn.getString("mime_type")) > -1) {
							return getThumb(file, file_ext);
						} else if (GlobalVar.getSystemProperty("rm.pdfconvert.filetypeext").toLowerCase().indexOf("*." + file_ext + ";") > -1) {
							return getPDF(file, file_ext);
						} else {
							return file;
						}
					} else {
						return file;
					}
				} catch (Exception ex) {
					return new File(folder, rtn.getString("file_id"));
				}
			}
		} else {
			return new File(folder, rtn.getString("file_id"));
		}
	}

	public static void downloadTempResource(String module, String fileid, String fileName, ServletContext servletContext, HttpServletResponse resp) throws Exception {
		File file = getTempFilePath(fileid);
		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		log.debug("============下载文件，编码前 before  " + fileName);
		file = new File(file, URLDecoder.decode(fileName, "UTF-8"));
		log.debug("============下载文件，编码后 after  " + file.getAbsoluteFile());
		if (!file.exists() || !file.isFile()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		resp.setContentType(servletContext.getMimeType(fileName) + "; charset=UTF-8");
		resp.setContentLength((int) file.length());
		resp.setHeader("Content-Disposition", "attachment;filename*=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

		InputStream stream = null;
		OutputStream os = null;
		try {
			stream = new BufferedInputStream(new FileInputStream(file));

			os = resp.getOutputStream();

			int readBytes = 0;

			byte buffer[] = new byte[8192];

			while ((readBytes = stream.read(buffer, 0, 8192)) != -1) {

				os.write(buffer, 0, readBytes);

			}
			os.flush();
		} catch (Exception ex) {

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
			File tempDir = file.getParentFile().getParentFile();
			File[] needDeleteFileArray = tempDir.listFiles(new FilenameFilter() {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

				@Override
				public boolean accept(File dir, String name) {
					if (name.matches("[0-9]{20}")) {
						String filetime = name.substring(0, 14);
						Date fileDate;
						try {
							fileDate = format.parse(filetime);
						} catch (ParseException e) {
							return true;
						}
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.DAY_OF_MONTH, -1);
						if (fileDate.before(cal.getTime())) {
							return true;
						}
					}
					return false;
				}
			});

			for (File needDeleteFile : needDeleteFileArray) {
				CommonFun.deleteFile(needDeleteFile);
			}
		}
	}

	/**
	 * 
	 * 下载资源文件夹下指定目录的指定文件 .
	 * 
	 * @param servletContext
	 *            ServletContext
	 * @param request
	 *            HttpServletRequest 请求对象
	 * @param response
	 *            HttpServletResponse 响应对象
	 * @throws Exception
	 *             所有异常
	 *             <dt><b>修改历史：</b></dt>
	 */
	public static void downloadResourceByDir(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonFun.requestMap(request);
		String fileDir = request.getParameter("d");
		String name = request.getParameter("f");
		File file = new File(GlobalVar.getSystemProperty("rm.path"), fileDir);
		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		name = verifyUser(request, name);
		if (CommonFun.isNe(name)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		RandomAccessFile randomf = null;
		OutputStream outS = null;
		try {
			file = new File(file, name);
			response.reset();
			String range = request.getHeader("range");
			long fileLen = file.length();
			long begin = 0;
			long end = fileLen - 1;
			String contentRange = null;
			if (range != null) {
				// 设置状态
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				// 得到请求byte范围
				String rangeBytes = range.replace("bytes=", "");
				String[] rangeArr = rangeBytes.trim().split("-");
				begin = Long.parseLong(rangeArr[0]);
				// 如果请求有结束范围 eg:1024000-2058220
				if (rangeArr.length > 1) {
					end = Long.parseLong(rangeArr[1]);
				}
				contentRange = new StringBuffer("bytes ").append(begin).append("-").append(end).append("/").append(fileLen).toString();
			}
			outS = response.getOutputStream();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setHeader("Content-disposition", "attachment;fileName=\"" + new String(name.getBytes("gb2312"), "iso-8859-1") + "\"");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-Range", contentRange);
			response.addHeader("Content-Length", String.valueOf(end + 1 - begin));
			randomf = new RandomAccessFile(file, "r");
			randomf.seek(begin);
			byte[] tmp = new byte[1024 * 1024];
			int i = -1;
			while ((i = randomf.read(tmp)) != -1) {
				if (randomf.getFilePointer() >= end) {
					outS.write(tmp, 0, (int) (i + end - randomf.getFilePointer() + 1));
					break;
				} else
					outS.write(tmp, 0, i);
			}
		} catch (Exception e) {
			if (e.getClass().getSimpleName().equalsIgnoreCase("ClientAbortException")) {
				log.debug("=========================下载被中断============");
			} else {
				log.debug("=========================错误原因error because============" + e.getMessage());
			}
		} finally {
			if (outS != null) {
				outS.close();
			}
			if (randomf != null) {
				randomf.close();
			}
		}
		return;
	}

	/**
	 * 
	 * 校验下载用户是否有权下载.
	 * <dl>
	 * <dt><b>业务描述：</b></dt>
	 * <dd>通过下载的uid，timestamp,verify判断，且判断下载时间，超过一周，将超时</dd>
	 * </dl>
	 * 
	 * @param request
	 *            请求
	 * @param name
	 *            文件名
	 * @return 编码后的文件名
	 * @throws UnsupportedEncodingException
	 *             不支持UTF-8编码抛出
	 *             <dt><b>修改历史：</b></dt>
	 */
	private static String verifyUser(HttpServletRequest request, String name) throws UnsupportedEncodingException {
		String uid = request.getParameter("uid");
		String timestamp = request.getParameter("timestamp");
		String verify = request.getParameter("verify");
		if (CommonFun.isNe(name)) {
			return null;
		}
		name = java.net.URLEncoder.encode(name, "utf-8");
		// 校验下载权限
		String verify_n = MD5.md5(uid + name + GlobalVar.getSystemProperty("sso.key") + timestamp);
		if (!verify_n.equals(verify)) {
			name = URLDecoder.decode(name, "UTF-8");
			verify_n = MD5.md5(uid + name + GlobalVar.getSystemProperty("sso.key") + timestamp);
			if (!verify_n.equals(verify)) {
				return null;
			}
		}

		long curts = System.currentTimeMillis() / 1000;
		if (timestamp != null) {
			long ts = 0;
			try {
				ts = Long.parseLong(timestamp);
			} catch (NumberFormatException nfe) {
				return null;
			}
			if (curts - ts > 60 * 60 * 24 * 7) {
				return null;
			}
		}
		name = URLDecoder.decode(name, "UTF-8");
		return name;
	}

	public static void downloadResource(String module, String fileid, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ResourceIndexService service = new ResourceIndexService();
		Record rtn = service.getResource(module, fileid);

		if (rtn == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		File file = new File(GlobalVar.getSystemProperty("rm.path"), (String) rtn.get("path"));

		file = new File(file, fileid);
		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String contextPath = req.getContextPath() == null ? "" : req.getContextPath();
		resp.sendRedirect(contextPath + req.getServletPath() + "/" + RESOURCE_URI + rtn.getString("path") + "/" + file.getName() + "?m=" + rtn.getString("mime_type") + "&d="
		+ URLEncoder.encode(rtn.getString("file_name"), "UTF-8"));

		/*resp.setContentType((String) rtn.get("mime_type") + "; charset=UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename=\"" + new String(((String) rtn.get("file_name")).getBytes("GBK"), "ISO-8859-1") + "\"");

		InputStream stream = null;
		OutputStream os = null;
		try {
			stream = new FileInputStream(file);

			os = resp.getOutputStream();

			int readBytes = 0;

			byte buffer[] = new byte[8192];

			while ((readBytes = stream.read(buffer, 0, 8192)) != -1) {

				os.write(buffer, 0, readBytes);

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
		}*/
	}

	public static String storeResource(String module, String userid, FileItem file) throws Exception {
		return storeResource(module, userid, file, null);
	}

	public static String storeResource(String module, String userid, FileItem file, String description, int imgWidth, int imgHeight) throws Exception {
		String fileid = generateResourceID();
		String path = getStorePath();
		boolean encrypt = Boolean.parseBoolean(GlobalVar.getSystemProperty("rm.file.encrypt"));
		boolean compress = Boolean.parseBoolean(GlobalVar.getSystemProperty("rm.file.compress"));
		File sf = new File(GlobalVar.getSystemProperty("rm.path"), path);
		sf = new File(sf, fileid);
		sf.createNewFile();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mimetype", getMimeType(file.getName()));
		params.put("filename", file.getName());
		params.put("filesize", file.getSize());
		boolean saved = false;
		if (GlobalVar.getSystemProperty("rm.image.mimetype").indexOf((String) params.get("mimetype")) > -1) {
			// 图片
			// String compressSize =
			// GlobalVar.getSystemProperty("rm.image.compress");
			// if (compressSize != null && compressSize.matches("[0-9]+")) {
			// compressLen = Integer.parseInt(compressSize);
			// if (compressLen != 0 && file.getSize() > compressLen) {
			String notcompressmaxwidth = GlobalVar.getSystemProperty("rm.image.notcompressmaxwidth");
			String notcompressmaxheight = GlobalVar.getSystemProperty("rm.image.notcompressmaxheight");
			long notcompressmaxwidthLen = 0;
			long notcompressmaxheightLen = 0;
			if ((notcompressmaxwidth != null && notcompressmaxwidth.matches("[0-9]+")) || (notcompressmaxheight != null && notcompressmaxheight.matches("[0-9]+"))) {
				if (notcompressmaxwidth != null) {
					notcompressmaxwidthLen = Integer.parseInt(notcompressmaxwidth);
				}
				if (notcompressmaxheight != null) {
					notcompressmaxheightLen = Integer.parseInt(notcompressmaxheight);
				}
				InputStream in = null;
				OutputStream out = null;
				try {
					in = file.getInputStream();
					BufferedImage bImg = ImageIO.read(in);
					int width = bImg.getWidth();
					int height = bImg.getHeight();
					// 如果大于了设置的要压缩的大小，则进行压缩
					if (width > notcompressmaxwidthLen || height > notcompressmaxheightLen) {
						if (imgWidth > 0 || imgHeight > 0) {
							if (imgWidth > 0 && imgWidth < width) {
								width = imgWidth;
							}
							if (imgHeight > 0 && imgHeight < height) {
								height = imgHeight;
							}
						} else {
							String widthStr = GlobalVar.getSystemProperty("rm.image.compress.width");
							String heightStr = GlobalVar.getSystemProperty("rm.image.compress.height");
							if (widthStr.matches("[0-9]+") && Integer.parseInt(widthStr) < width) {
								width = Integer.parseInt(widthStr);
							}
							if (heightStr.matches("[0-9]+") && Integer.parseInt(heightStr) < height) {
								height = Integer.parseInt(heightStr);
							}
						}
						BufferedImage targetImg = resize(bImg, width, height, true);
						out = new BufferedOutputStream(new FileOutputStream(sf));
						ImageIO.write(targetImg, file.getName().substring(file.getName().lastIndexOf(".") + 1), out);
						saved = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (out != null)
						try {
							out.close();
						} catch (IOException e) {
							out = null;
						}
					if (in != null)
						in.close();
				}
			}
		}
		if (!saved) {
			InputStream stream = null;
			OutputStream os = null;
			try {
				stream = file.getInputStream();

				os = new FileOutputStream(sf);

				int readBytes = 0;

				byte buffer[] = new byte[8192];

				while ((readBytes = stream.read(buffer, 0, 8192)) != -1) {

					os.write(buffer, 0, readBytes);

				}

				os.close();

				stream.close();

				file.delete();

			} catch (Exception ex) {
				if (os != null)
					os.close();
				if (stream != null)
					stream.close();
				// if (file != null)
				file.delete();
				throw ex;
			}
		}
		if (GlobalVar.getSystemProperty("rm.image.mimetype").indexOf((String) params.get("mimetype")) > -1) {
			getThumb(sf, file.getName().substring(file.getName().lastIndexOf(".") + 1));
		}

		try {
			ResourceIndexService service = new ResourceIndexService();
			params.put("fileid", fileid);
			params.put("module", module);
			params.put("userid", userid);
			params.put("path", path);
			params.put("encrypt", encrypt ? 1 : 0);
			params.put("compress", compress ? 1 : 0);
			params.put("description", description);

			service.storeResource(module, params);
		} catch (Exception ex) {
			sf.delete();
			throw ex;
		}

		return fileid;

	}

	public static String storeResource(String module, String userid, FileItem file, String description) throws Exception {
		return storeResource(module, userid, file, description, 0, 0);
	}

	/**
	 * 
	 * 存储文件，通过调用者传递过来的参数.
	 * <dl>
	 * <dt><b>业务描述：</b></dt>
	 * <dd>通过用户调用者传入参数，对文件进行保存操作，并返回文件编号。
	 * 如果文件是图片，且图片已经超过系统设置的大小，那么此方法会将图片压缩至系统配置。</dd>
	 * </dl>
	 * 
	 * @param module
	 *            文件所属模块 ，如：assets、psms
	 * @param userid
	 *            用户id，如：120032016
	 * @param stream
	 *            输入字节流，此方法会将此流最后关闭。
	 * @param mimetype
	 *            文件mime类型，如：text/html等，可以通过调用方法
	 *            URLConnection.guessContentTypeFromName(filename) 得到
	 * @param fileName
	 *            要保存的文件的名称，如：规范文档.doc
	 * @param filesize
	 *            文件大小，数字，如：“10240”
	 * @param description
	 *            文件描述说明
	 * @return 文档id，
	 * @throws Exception
	 *             存储或业务代码错误，都将抛出
	 *             <dt><b>修改历史：</b></dt>
	 */
	public static String storeResourceFile(String module, String userid, InputStream stream, String mimetype, String fileName, String filesize, String description) throws Exception {
		return storeResource(module, userid, stream, mimetype, fileName, filesize);
	}

	public static String storeResourceImage(String module, String userid, InputStream stream, String mimetype, String filename, String filesize) throws Exception {
		return storeResource(module, userid, stream, mimetype, filename, filesize);
	}

	private static String storeResource(String module, String userid, InputStream stream, String mimetype, String filename, String filesize) throws Exception, IOException {
		String fileid = generateResourceID();
		String path = getStorePath();
		boolean encrypt = Boolean.parseBoolean(GlobalVar.getSystemProperty("rm.file.encrypt"));
		boolean compress = Boolean.parseBoolean(GlobalVar.getSystemProperty("rm.file.compress"));
		File sf = new File(GlobalVar.getSystemProperty("rm.path"), path);
		sf = new File(sf, fileid);
		sf.createNewFile();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mimetype", mimetype);
		params.put("filename", filename);
		params.put("filesize", filesize);
		boolean saved = false;
		String imgType = "jpg";
		// 缓存起流对象
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		try {
			int readBytes = 0;
			byte buffer[] = new byte[8192];
			while ((readBytes = stream.read(buffer)) != -1) {
				bao.write(buffer, 0, readBytes);
			}
			if (GlobalVar.getSystemProperty("rm.image.mimetype").indexOf((String) params.get("mimetype")) > -1) {
				String notcompressmaxwidth = GlobalVar.getSystemProperty("rm.image.notcompressmaxwidth");
				String notcompressmaxheight = GlobalVar.getSystemProperty("rm.image.notcompressmaxheight");
				long notcompressmaxwidthLen = 0;
				long notcompressmaxheightLen = 0;
				if ((notcompressmaxwidth != null && notcompressmaxwidth.matches("[0-9]+")) || (notcompressmaxheight != null && notcompressmaxheight.matches("[0-9]+"))) {
					if (notcompressmaxwidth != null) {
						notcompressmaxwidthLen = Integer.parseInt(notcompressmaxwidth);
					}
					if (notcompressmaxheight != null) {
						notcompressmaxheightLen = Integer.parseInt(notcompressmaxheight);
					}
					OutputStream out = null;
					ByteArrayInputStream input = new ByteArrayInputStream(bao.toByteArray());
					try {
						BufferedImage bImg = ImageIO.read(input);
						int width = bImg.getWidth();
						int height = bImg.getHeight();
						// 如果大于了设置的要压缩的大小，则进行压缩
						if (width > notcompressmaxwidthLen || height > notcompressmaxheightLen) {
							BufferedImage targetImg = resize(bImg, bImg.getWidth(), bImg.getHeight(), true);
							out = new BufferedOutputStream(new FileOutputStream(sf));
							ImageIO.write(targetImg, imgType, out);
							saved = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (out != null)
							try {
								out.close();
							} catch (IOException e) {
								out = null;
							}
						input.close();
					}
				}
			}
			if (!saved) {
				OutputStream os = null;
				ByteArrayInputStream input = new ByteArrayInputStream(bao.toByteArray());
				try {
					os = new FileOutputStream(sf);
					os.write(bao.toByteArray());
				} catch (Exception ex) {
					throw ex;
				} finally {
					if (os != null)
						os.close();
					input.close();
				}
			}
			// 图片
			if (GlobalVar.getSystemProperty("rm.image.mimetype").indexOf((String) params.get("mimetype")) > -1) {
				getThumb(sf, imgType);
			}

			try {
				ResourceIndexService service = new ResourceIndexService();
				params.put("fileid", fileid);
				params.put("module", module);
				params.put("userid", userid);
				params.put("path", path);
				params.put("encrypt", encrypt ? 1 : 0);
				params.put("compress", compress ? 1 : 0);
				params.put("description", null);

				service.storeResource(module, params);
			} catch (Exception ex) {
				sf.delete();
				throw ex;
			}
		} finally {
			if (stream != null) {
				stream.close();
			}
			bao.close();
		}
		return fileid;
	}

	public static void commit(String module, String fileid) throws Exception {
		if (!CommonFun.isNe(fileid))
			commit(module, fileid.split("/,/g"));
	}

	public static void commit(String module, String[] fileids) throws Exception {
		try {
			if (CommonFun.isNe(fileids))
				return;

			ResourceIndexService service = new ResourceIndexService();
			service.commit(module, fileids);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static void rollback(String module, String fileid) throws Exception {
		try {
			if (CommonFun.isNe(fileid))
				return;
			ResourceIndexService service = new ResourceIndexService();
			service.rollback(module, fileid.split("/,/g"));
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static void rollback(String module, String[] fileids) throws Exception {
		try {
			if (CommonFun.isNe(fileids))
				return;

			ResourceIndexService service = new ResourceIndexService();
			service.rollback(module, fileids);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static void gc() throws Exception {
		try {
			ResourceIndexService service = new ResourceIndexService();
			service.gc();
		} catch (Exception ex) {
			throw ex;
		}
	}

	private static String getStorePath() throws Exception {
		File file = new File(GlobalVar.getSystemProperty("rm.path"));
		if (!file.exists()) {
			file.mkdirs();
		}

		// int level =
		// Integer.parseInt(GlobalVar.getSystemProperty("rm.path.level"));
		// int max_count =
		// Integer.parseInt(GlobalVar.getSystemProperty("rm.path.count"));
		// 太大会出现问题，在远程的NFS时候
		int level = 3;
		int max_count = 1024;
		int num = 0;
		int[] paths = new int[level];
		File[] subfiles;
		for (int i = 0; i < level; i++) {
			subfiles = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					if (file.isDirectory() && file.getName().matches("[0-9]+")) {
						return true;
					}
					return false;
				}
			});
			int maxNum = 1;
			int currNum = 0;
			for (File fi : subfiles) {
				currNum = Integer.parseInt(fi.getName());
				if (currNum > maxNum) {
					maxNum = currNum;
				}
			}
			paths[i] = maxNum;
			file = new File(file, "" + maxNum);
			if (!file.exists()) {
				file.mkdirs();
			}
		}

		subfiles = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		num = subfiles.length + 1;
		int i = level - 1;
		while (num > max_count && i >= 0) {
			if (i < level - 1) {
				paths[i + 1] = 1;
			}
			paths[i]++;
			num = paths[i];
			i--;
		}

		if (paths[0] > max_count) {
			throw new Exception("resource store area is full.");
		}

		String path = "";
		for (int p : paths) {
			path += "/" + p;
		}

		file = new File(GlobalVar.getSystemProperty("rm.path"), path);
		if (!file.exists()) {
			file.mkdirs();
		}

		return path;
	}

	private static String generateResourceID() {
		String code = CommonFun.getSSID();
		code = MD5.md5(code + GlobalVar.getLicense().getPassword());
		return code;
	}

	private static RethumbThread rtt;

	public static void rethumb(String system_product_code) throws Exception {
		if (rtt != null && rtt.overflag == 0)
			throw new Exception("thread is now running");
		rtt = new RethumbThread();
		rtt.system_product_code = system_product_code;
		rtt.start();
	}

	public static Map<String, Object> getRethumbProcess() throws Exception {
		if (rtt == null) {
			Record r = new Record();
			r.put("overflag", 1);
			return r;
		}
		return rtt.getProcess();
	}

	public static File getThumb(File srcfile, String type) throws Exception {
		int iwidth = Integer.parseInt(GlobalVar.getSystemProperty("rm.image.thumb.width", "124")), iheight = Integer.parseInt(GlobalVar.getSystemProperty("rm.image.thumb.height", "176"));
		File thumbfile = new File(srcfile.getParent(), srcfile.getName() + THUMB_EXT);
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(srcfile));
			BufferedImage bImg = null;
			// 修复由于某些图片很大，且格式存在问题导致IO错误，出现 ice bad sequence 问题
			if ("JPG".equalsIgnoreCase(type) || "JPEG".equalsIgnoreCase(type)) {
				JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
				bImg = decoder.decodeAsBufferedImage();
			} else {
				bImg = ImageIO.read(in);
			}
			int width = bImg.getWidth();
			int height = bImg.getHeight();
			// 如果大于了设置的要压缩的大小，则进行压缩
			BufferedImage targetImg = bImg;
			if (width > iwidth || height > iheight) {
				targetImg = resize(bImg, iwidth, iheight, true);
			}
			out = new BufferedOutputStream(new FileOutputStream(thumbfile));
			ImageIO.write(targetImg, type, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					out = null;
				}
			if (in != null)
				in.close();
		}
		return thumbfile;
	}

	private static String openofficeport;
	private static Process openofficeservice;

	public static void distoryOpenOffice() {
		if (openofficeservice != null) {
			openofficeservice.destroy();
		}
	}

	private static int startOpenOffice() throws Exception {
		if (openofficeservice == null) {
			openofficeport = GlobalVar.getSystemProperty("openoffice.port", "8145");
			distoryOpenOffice();
			try {
				String cmd = GlobalVar.getSystemProperty("openoffice.path") + " -headless -accept=\"socket,host=127.0.0.1,port="
						+ GlobalVar.getSystemProperty("openoffice.port", "8145") + ";urp;\" -nofirststartwizard";
				openofficeservice = Runtime.getRuntime().exec(cmd);
			} catch (Exception ex) {
				throw new Exception("启动转换服务失败，端口已被占用", ex);
			}
		}
		return Integer.parseInt(openofficeport);
	}

	public static File getPDF(File srcfile, String file_ext) throws Exception {
		File tf = new File(srcfile.getParent(), srcfile.getName() + "." + file_ext);
		File ff = new File(srcfile.getParentFile(), srcfile.getName() + THUMB_EXT);
		File thumbfile = new File(srcfile.getParent(), srcfile.getName() + THUMB_EXT + ".pdf");
		try {
			CommonFun.copyFile(srcfile, tf, true);
			SocketOpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", startOpenOffice());
			connection.connect();
			DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
			converter.convert(tf, thumbfile);
			thumbfile.createNewFile();
			connection.disconnect();
		} catch (java.net.ConnectException e) {
			throw new Exception("转换失败", new Exception("转换服务未启动", e));
		} catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
			throw new Exception("转换失败", new Exception("读取源文件失败", e));
		} catch (Exception e) {
			throw new Exception("转换失败", e);
		} finally {
			tf.delete();
			thumbfile.renameTo(ff);
		}
		return ff;
	}

	public static int deleteResource(String module, String[] fileids) throws Exception {
		ResourceIndexService service = new ResourceIndexService();
		int i = service.deleteResource(module, fileids);
		return i;
	}

	private static String getMimeType(String filename) {
		String mimetype = URLConnection.guessContentTypeFromName(filename);
		if (mimetype == null)
			return "application/octet-stream";
		return mimetype;
	}

	/**
	 * @param module
	 * @param id
	 * @param fileid
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public static int updateDescription(String module, String system_user_id, String fileid, String description) throws Exception {
		ResourceIndexService service = new ResourceIndexService();
		return service.updateDescription(module, system_user_id, fileid, description);
	}

	/**
	 * @param module
	 * @param id
	 * @param fileid
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public static int updateName(String module, String system_user_id, String fileid, String file_name) throws Exception {
		ResourceIndexService service = new ResourceIndexService();
		return service.updateDescription(module, system_user_id, fileid, file_name);
	}

	/**
	 * 检查文件是否已经更新，通过修改时间
	 * 
	 * @param request
	 * @param response
	 * @param lastModifiedTimestamp
	 * @return
	 */
	public static boolean checkNotModified(HttpServletRequest request, HttpServletResponse response, long lastModifiedTimestamp) {
		boolean notModified = false;
		if (lastModifiedTimestamp >= 0 && (response == null || !response.containsHeader("Last-Modified"))) {
			long ifModifiedSince = -1;
			Object last_modify_time = request.getDateHeader("If-Modified-Since");
			if (last_modify_time != null && last_modify_time.toString().matches("[\\d]+")) {
				ifModifiedSince = Long.parseLong(last_modify_time.toString());
			}
			// IE 当图片在iframe里面时，图片将使用缓存，即使设置了不缓存图片 .
			// log.debug("lastModifiedTimestamp==========================="+new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new
			// Date(lastModifiedTimestamp)));
			// log.debug("lastModifiedTimestamp==========================="+lastModifiedTimestamp);
			// log.debug("ifModifiedSince================================="+new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new
			// Date(ifModifiedSince)));
			// log.debug("ifModifiedSince================================="+ifModifiedSince);
			notModified = ifModifiedSince == lastModifiedTimestamp / 1000 * 1000;
			if (response != null) {
				if (notModified) {
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					// log.debug("ifModifiedSince=================================333333300000044444");
				}
				response.setDateHeader("Last-Modified", lastModifiedTimestamp);
			}
		}
		// 缓存10分钟
		response.setHeader("Cache-Control", "max-age=600");
		return notModified;
	}

	/**
	 * 通过源图的BufferedImage对象取得缩放后的BufferedImage对象
	 * 
	 * @param source
	 *            源文件
	 * @param targetW
	 *            缩放后的width
	 * @param targetH
	 *            缩放后的 height
	 * @param keepScale
	 *            是否按照比例缩放
	 * @return 缩放后的BufferedImage对象
	 * @throws IOException
	 */
	public static BufferedImage resize(BufferedImage source, int targetW, int targetH, boolean keepScale) throws IOException {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		if (keepScale)
			if (sx > sy) {
				sx = sy;
				targetW = (int) (sx * source.getWidth());
			} else {
				sy = sx;
				targetH = (int) (sy * source.getHeight());
			}
		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);

		// 表示放大
		if (sx >= 1 && sy >= 1) {
			Graphics2D g = target.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setComposite(AlphaComposite.Src);
			g.drawImage(source, 0, 0, targetW, targetH, null);
			g.dispose();
		} else {// 表示缩小
			final int bs = 2;
			int currentWidth = source.getWidth();
			int currentHeight = source.getHeight();
			if (type == BufferedImage.TYPE_CUSTOM) {
				type = BufferedImage.TYPE_3BYTE_BGR;
			}
			BufferedImage tempImage = new BufferedImage(currentWidth, currentHeight, type);
			Graphics2D g = tempImage.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setComposite(AlphaComposite.Src);
			int startWidth = targetW;
			int startHeight = targetH;

			while (startWidth < currentWidth && startHeight < currentHeight) {
				startWidth *= bs;
				startHeight *= bs;
			}
			currentWidth = startWidth / bs;
			currentHeight = startHeight / bs;
			g.drawImage(source, 0, 0, currentWidth, currentHeight, null);
			while ((currentWidth >= targetW * bs) && (currentHeight >= targetH * bs)) {
				currentWidth /= bs;
				currentHeight /= bs;
				if (currentWidth < targetW)
					currentWidth = targetW;
				if (currentHeight < targetH)
					currentHeight = targetH;
				g.drawImage(tempImage, 0, 0, currentWidth, currentHeight, 0, 0, currentWidth * bs, currentHeight * bs, null);
			}
			g.dispose();
			Graphics2D destg = target.createGraphics();
			destg.drawImage(tempImage, 0, 0, targetW, targetH, 0, 0, currentWidth, currentHeight, null);
			destg.dispose();
		}
		return target;
	}

	private static double[] calculatePosition(double x, double y, double angle) {
		angle = Math.toRadians(angle);

		double nx = (Math.cos(angle) * x) - (Math.sin(angle) * y);
		double ny = (Math.sin(angle) * x) + (Math.cos(angle) * y);

		return new double[] { nx, ny };
	}

	public static void rotation(String module, String fileid, boolean thumb, double angle, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ResourceIndexService service = new ResourceIndexService();
		Record rtn = service.getResource(module, fileid);

		if (rtn == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		File file = new File(GlobalVar.getSystemProperty("rm.path"), (String) rtn.get("path"));
		File sFile = new File(file, rtn.getString("file_id") + THUMB_EXT);
		file = new File(file, rtn.getString("file_id"));
		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// 旋转大图
		rotationImage(angle, file);
		// 旋转缩略图
		if (!sFile.exists()) {
			return;
		}
		rotationImage(angle, sFile);
	}

	private static void rotationImage(double angle, File sFile) throws Exception {
		FileImageInputStream imgIn = null;
		BufferedImage targetImg = null;
		BufferedImage bi = null;
		FileOutputStream fout = null;
		try {
			imgIn = new FileImageInputStream(sFile);
			bi = ImageIO.read(imgIn);
			targetImg = rotation(bi, angle);
			fout = new FileOutputStream(sFile);
			ImageIO.write(targetImg, "jpg", fout);
		} catch (Exception e) {
			throw e;
		} finally {
			if (fout != null)
				fout.close();
		}
		sFile.setLastModified(System.currentTimeMillis());
	}

	public static BufferedImage rotation(BufferedImage img, double angle) {
		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage newImage;

		double[][] newPositions = new double[4][];
		newPositions[0] = calculatePosition(0, 0, angle);
		newPositions[1] = calculatePosition(width, 0, angle);
		newPositions[2] = calculatePosition(0, height, angle);
		newPositions[3] = calculatePosition(width, height, angle);

		double minX = Math.min(Math.min(newPositions[0][0], newPositions[1][0]), Math.min(newPositions[2][0], newPositions[3][0]));
		double maxX = Math.max(Math.max(newPositions[0][0], newPositions[1][0]), Math.max(newPositions[2][0], newPositions[3][0]));
		double minY = Math.min(Math.min(newPositions[0][1], newPositions[1][1]), Math.min(newPositions[2][1], newPositions[3][1]));
		double maxY = Math.max(Math.max(newPositions[0][1], newPositions[1][1]), Math.max(newPositions[2][1], newPositions[3][1]));

		int newWidth = (int) Math.round(maxX - minX);
		int newHeight = (int) Math.round(maxY - minY);
		// ColorModel cm = source.getColorModel();
		// WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
		// targetH);
		// boolean alphaPremultiplied = cm.isAlphaPremultiplied();
		// target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		newImage = new BufferedImage(newWidth, newHeight, img.getType());

		Graphics2D g = newImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setComposite(AlphaComposite.Src);

		double w = newWidth / 2.0;
		double h = newHeight / 2.0;
		g.rotate(Math.toRadians(angle), w, h);
		int centerX = (int) Math.round((newWidth - width) / 2.0);
		int centerY = (int) Math.round((newHeight - height) / 2.0);

		g.drawImage(img, centerX, centerY, null);
		g.dispose();

		return newImage;
	}

	/**
	 * 获得临时文件存放目录
	 * 
	 * @param fileId
	 * @return
	 */
	public static File getTempFilePath(String fileId) {
		String tempfilepath = GlobalVar.getSystemProperty("rm.tempfilepath", "/cooperop/resources/tempfile/");
		File file = null;
		if (tempfilepath.startsWith("/")) {
			file = new File(tempfilepath + "/" + fileId);
		} else {
			file = new File("/" + tempfilepath + "/" + fileId);
		}
		return file;
	}

	public static void downloadUserFile(String filedir, String fileName, ServletContext servletContext, HttpServletResponse resp) throws Exception {
		String path = GlobalVar.getWorkPath() + "/";
		Document docCK = CommonFun.loadXMLFile(path + "WEB-INF/config/ckfinder.xml");
		File file = null;
		file = new File(docCK.getRootElement().getChildText("baseDir") + "/" + filedir);
		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		file = new File(file, URLDecoder.decode(fileName, "UTF-8"));
		if (!file.exists() || !file.isFile()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		resp.setContentType(servletContext.getMimeType(fileName) + "; charset=UTF-8");
		resp.setContentLength((int) file.length());
		resp.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName) + "\"");
		InputStream stream = null;
		OutputStream os = null;
		try {
			stream = new BufferedInputStream(new FileInputStream(file));
			os = resp.getOutputStream();
			int readBytes = 0;
			byte buffer[] = new byte[8192];
			while ((readBytes = stream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, readBytes);
			}
			os.flush();
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (Exception e) {
				}
			if (stream != null)
				stream.close();
		}
	}

	public static void getFileLastModify(String module, String[] fileIdArray, HttpServletRequest req, HttpServletResponse resp) {
		try {
			StringBuffer sb = getFileLastModifyStr(module, fileIdArray);
			String rtnString = sb.substring(0, sb.length() - 1);
			resp.setContentType("text/plain");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(rtnString);
			resp.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resp.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static StringBuffer getFileLastModifyStr(String module, String[] fileIdArray) throws Exception {
		ResourceIndexService service = new ResourceIndexService();
		Result rtn = service.getResources(module, fileIdArray);
		List<Record> list = rtn.getResultset();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fileIdArray.length; i++) {
			String fileId = fileIdArray[i];
			if (!CommonFun.isNe(fileId)) {
				sb.append(fileId);
				sb.append("=");
				File file = null;
				for (Record record : list) {
					if (fileId.equals(record.get("file_id"))) {
						file = getFile(true, record);
						break;
					}
				}
				if (file != null) {
					long lastmodify = file.lastModified() / 1000 * 1000;
					sb.append(lastmodify + ",");
				} else {
					sb.append(0 + ",");
				}
			}
		}
		return sb;
	}

	/**
	 * 用于地图图片的获取 .
	 * 
	 * @param servletContext
	 * @param req
	 * @param resp
	 *            <dt><b>修改历史：</b></dt>
	 * @throws IOException
	 */

	public static void showMapFile(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws IOException {
		CommonFun.requestMap(request);
		String fileDir = request.getParameter("d");
		String name = request.getParameter("f");
		String level = request.getParameter("l");
		File file = new File(GlobalVar.getSystemProperty("rm.path"), fileDir);
		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String match = request.getHeader("If-None-Match");
		match = match == null ? "0" : match;
		InputStream inStream = null;
		OutputStream outS = null;
		try {
			file = new File(file + "/" + level, name);
			if (!file.exists()) {
				file = new File(file + "/" + level, name.toLowerCase());
				if (!file.exists()) {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
			}
			if (file.lastModified() == Long.parseLong(match.trim())) {
				// response.addHeader("Cache-Control", "max-age="+(10));
				response.addHeader("ETag", file.lastModified() + "");
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}
			response.reset();
			long fileLen = file.length();
			// long begin = 0;
			// long end = fileLen - 1;
			// String contentRange = null;
			// if (range != null) {
			// //设置状态
			// response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			// //得到请求byte范围
			// String rangeBytes = range.replace("bytes=", "");
			// String[] rangeArr = rangeBytes.trim().split("-");
			// begin = Long.parseLong(rangeArr[0]);
			// // 如果请求有结束范围 eg:1024000-2058220
			// if (rangeArr.length > 1) {
			// end = Long.parseLong(rangeArr[1]);
			// }
			// contentRange = new StringBuffer("bytes ").append(begin)
			// .append("-").append(end).append("/")
			// .append(fileLen).toString();
			// }
			outS = response.getOutputStream();
			String mineType = servletContext.getMimeType(name);
			response.setContentType(mineType + "; charset=UTF-8");
			response.setHeader("Content-disposition", "fileName=\"" + new String(name.getBytes("gb2312"), "iso-8859-1") + "\"");
			response.addHeader("Content-Length", fileLen + "");
			response.addHeader("Cache-Control", "max-age=" + (60));
			response.addHeader("ETag", file.lastModified() + "");
			inStream = new BufferedInputStream(new FileInputStream(file));
			byte[] tmp = new byte[1024 * 1024];
			int i = -1;
			while ((i = inStream.read(tmp)) != -1) {
				outS.write(tmp, 0, i);
			}
		} catch (Exception e) {
			log.debug("=======error because==" + e.getMessage());
		} finally {
			if (outS != null) {
				outS.close();
			}
			if (inStream != null) {
				inStream.close();
			}
		}
	}

}

class RethumbThread extends Thread {
	public String system_product_code;
	public String tables;
	public String current_table;
	public int image_num;
	public int process_image_num;
	public String current_image;
	public int overflag;
	public String errormsg;

	public Map<String, Object> getProcess() {
		Record r = new Record();
		r.put("system_product_code", system_product_code);
		r.put("tables", tables);
		r.put("current_table", current_table);
		r.put("image_num", image_num);
		r.put("process_image_num", process_image_num);
		r.put("current_image", current_image);
		r.put("overflag", overflag);
		r.put("errormsg", errormsg);
		return r;
	}

	public void run() {
		try {
			overflag = 0;
			if (!CommonFun.isNe(system_product_code)) {
				String table_name = new ResourceIndexService().getResourceTable(system_product_code).getString("table_name");
				tables = table_name;
				rethumbModule(table_name);
			} else {
				Result ts = new ResourceIndexService().listResourceTable();
				String[] ta = new String[(int) ts.getCount()];
				for (int i = 0; i < ts.getCount(); i++) {
					ta[i] = ts.getResultset(i).getString("table_name");
				}
				tables = CommonFun.joinArray(ta, ",");
				for (Record table : ts.getResultset()) {
					rethumbModule(table.getString("table_name"));
				}
			}
			overflag = 1;
		} catch (Exception ex) {
			overflag = -1;
			errormsg = ex.getMessage();
			log.error(ex);
		}
	}

	private void rethumbModule(String table_name) throws Exception {
		Result images = new ResourceIndexService().getImageResource(table_name);
		image_num += images.getCount();
		current_table = table_name;
		for (Record image : images.getResultset()) {
			File folder = new File(GlobalVar.getSystemProperty("rm.path"));
			current_image = table_name + ":" + image.getString("file_id") + "[" + image.getString("path") + "]";
			File f = new File(folder, image.getString("path") + "/" + image.getString("file_id"));
			if (f.exists()) {
				ResourceManager.getThumb(f, image.getString("file_name").substring(image.getString("file_name").lastIndexOf(".") + 1));
			}
			process_image_num++;
		}
	}
}
