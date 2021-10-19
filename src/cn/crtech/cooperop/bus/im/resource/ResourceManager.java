package cn.crtech.cooperop.bus.im.resource;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import cn.crtech.cooperop.bus.im.service.DocumentService;
import cn.crtech.cooperop.bus.im.transfer.Engine;
import cn.crtech.cooperop.bus.license.License;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.MD5;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class ResourceManager {

	public static final String THUMB_EXT = "S";

	public static final int IS_TEMP = 1;
	public static final int NOT_TEMP = 0;
	public static final int TEMP_OPER_NORMAL = 0;
	public static final int TEMP_OPER_INSERT = 1;
	public static final int TEMP_OPER_UPDATE = 2;
	public static final int TEMP_OPER_DELETE = 3;

	private static final int BUFFER_LENGTH = 1024 * 16;
	private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;
	private static final Pattern RANGE_PATTERN = Pattern.compile("bytes=(?<start>\\d*)-(?<end>\\d*)");

	public static Record getResource(String fileid) throws Exception {
		DocumentService service = new DocumentService();
		return service.getResource(fileid);
	}

	public static List<Record> getResource(String[] fileids) throws Exception {
		DocumentService service = new DocumentService();
		return service.getResource(fileids);
	}

//	public static void showResource(String fileid, boolean thumb, HttpServletRequest req,
//			HttpServletResponse resp) throws Exception {
//		DocumentService service = new DocumentService();
//		Record rtn = service.getResource(fileid);
//
//		if (rtn == null) {
//			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
//			return;
//		}
//
//		File file = getFile(thumb, rtn);
//
//		if (!file.exists()) {
//			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
//			return;
//		}
//
//		// 设置缓存
//		if (checkNotModified(req, resp, file.lastModified())) {
//			return;
//		}
//
//		int length = (int) file.length();
//		int start = 0;
//		int end = length - 1;
//
//		Enumeration<String> headers = req.getHeaderNames();
//
//		while (headers.hasMoreElements()) {
//			String h = headers.nextElement();
//			log.debug(h + ": " + req.getHeader(h));
//		}
//
//		String range = CommonFun.isNe(req.getHeader("Range")) ? req.getHeader("range") : req.getHeader("Range");
//		range = CommonFun.isNe(range) ? "" : range;
//
//		Matcher matcher = RANGE_PATTERN.matcher(range);
//
//		if (matcher.matches()) {
//			String startGroup = matcher.group("start");
//			start = startGroup.isEmpty() ? start : Integer.valueOf(startGroup);
//			start = start < 0 ? 0 : start;
//
//			String endGroup = matcher.group("end");
//			end = endGroup.isEmpty() ? end : Integer.valueOf(endGroup);
//			end = end > length - 1 ? length - 1 : end;
//		}
//
//		int contentLength = end - start + 1;
//
//		resp.setContentType((String) rtn.get("mime_type") + "; charset=UTF-8");
//		resp.setBufferSize(BUFFER_LENGTH);
//		resp.setHeader("Content-Disposition", "inline;filename=\""
//				+ new String(((String) rtn.get("file_name")).getBytes("GBK"), "ISO-8859-1") + "\"");
//		resp.setHeader("Accept-Ranges", "bytes");
//		resp.setDateHeader("Last-Modified", file.lastModified());
//		resp.setDateHeader("Expires", System.currentTimeMillis() + EXPIRE_TIME);
//		resp.setHeader("Content-Range", String.format("bytes %s-%s/%s", start, end, length));
//		resp.setHeader("Content-Length", String.format("%s", contentLength));
//		resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
//
//		InputStream stream = null;
//		OutputStream os = null;
//		try {
//			stream = new FileInputStream(file);
//
//			os = resp.getOutputStream();
//
//			int bytesRead;
//			int bytesLeft = contentLength;
//			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_LENGTH);
//
//			try (SeekableByteChannel input = Files.newByteChannel(file.toPath(), StandardOpenOption.READ);
//					OutputStream output = resp.getOutputStream()) {
//				input.position(start);
//				while ((bytesRead = input.read(buffer)) != -1 && bytesLeft > 0) {
//					buffer.clear();
//					output.write(buffer.array(), 0, bytesLeft < bytesRead ? bytesLeft : bytesRead);
//					bytesLeft -= bytesRead;
//				}
//			}
//			os.flush();
////			os.close();
//
//			stream.close();
//
//		} catch (Exception ex) {
//			if (os != null)
//				os.close();
//			if (stream != null)
//				stream.close();
//			throw ex;
//		}
//	}
	
	public static void showResource(String fileid, boolean thumb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		DocumentService service = new DocumentService();
		Record rtn = service.getResource(fileid);

		if (rtn == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		File file = getFile(thumb, rtn);

		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		resp.setContentType((String) rtn.get("mime_type") + "; charset=UTF-8");
		resp.setHeader("Content-Disposition", "filename=\"" + new String(((String) rtn.get("file_name")).getBytes("GBK"), "ISO-8859-1")+"\"");
		// 设置缓存
		if(checkNotModified(req, resp, file.lastModified())){
			return;
		}
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
		}
	}

	public static File getFile(boolean thumb, Record rtn) {
		File folder = new File(Engine.getProperty("rm.path"), rtn.getString("path"));
		if (thumb) {
			File file = new File(folder, rtn.getString("file_id") + THUMB_EXT);
			if (file.exists()) {
				return file;
			} else {
				try {
					file = new File(folder, rtn.getString("file_id"));
					if (file.exists()) {
						return getThumb(file,
								rtn.getString("file_name").substring(rtn.getString("file_name").lastIndexOf(".") + 1));
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

	public static void downloadResource(String fileid, HttpServletResponse resp) throws Exception {
		DocumentService service = new DocumentService();
		Map<String, Object> rtn = service.getResource(fileid);

		if (rtn == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		File file = new File(Engine.getProperty("rm.path"), (String) rtn.get("path"));

		file = new File(file, fileid);
		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		resp.setContentType((String) rtn.get("mime_type") + "; charset=UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename=\""
				+ new String(((String) rtn.get("file_name")).getBytes("GBK"), "ISO-8859-1") + "\"");

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
		}
	}

	public static String storeResource(String userid, FileItem file) throws Exception {
		return storeResource(userid, file, null);
	}

	public static String storeResource(String userid, FileItem file, String description, int imgWidth,
			int imgHeight) throws Exception {
		String fileid = generateResourceID();
		String path = getStorePath();
		boolean encrypt = Boolean.parseBoolean(Engine.getProperty("rm.file.encrypt"));
		boolean compress = Boolean.parseBoolean(Engine.getProperty("rm.file.compress"));
		File sf = new File(Engine.getProperty("rm.path"), path);
		sf = new File(sf, fileid);
		sf.createNewFile();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mimetype", CommonFun.isNe(file.getContentType()) ? getMimeType(file.getName()) : file.getContentType());
		params.put("filename", file.getName());
		params.put("filesize", file.getSize());
		boolean saved = false;
		if (Engine.getProperty("rm.image.mimetype").indexOf((String) params.get("mimetype")) > -1) {
			// 图片
			// String compressSize =
			// Engine.getProperty("rm.image.compress");
			// if (compressSize != null && compressSize.matches("[0-9]+")) {
			// compressLen = Integer.parseInt(compressSize);
			// if (compressLen != 0 && file.getSize() > compressLen) {
			String notcompressmaxwidth = Engine.getProperty("rm.image.notcompressmaxwidth");
			String notcompressmaxheight = Engine.getProperty("rm.image.notcompressmaxheight");
			long notcompressmaxwidthLen = 0;
			long notcompressmaxheightLen = 0;
			if ((notcompressmaxwidth != null && notcompressmaxwidth.matches("[0-9]+"))
					|| (notcompressmaxheight != null && notcompressmaxheight.matches("[0-9]+"))) {
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
							String widthStr = Engine.getProperty("rm.image.compress.width");
							String heightStr = Engine.getProperty("rm.image.compress.height");
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
		if (Engine.getProperty("rm.image.mimetype").indexOf((String) params.get("mimetype")) > -1) {
			getThumb(sf, file.getName().substring(file.getName().lastIndexOf(".") + 1));
		}

		try {
			DocumentService service = new DocumentService();
			params.put("fileid", fileid);
			params.put("userid", userid);
			params.put("path", path);
			params.put("encrypt", encrypt ? 1 : 0);
			params.put("compress", compress ? 1 : 0);
			params.put("description", description);

			service.storeResource(params);
		} catch (Exception ex) {
			sf.delete();
			throw ex;
		}

		return fileid;

	}

	public static String storeResource(String userid, FileItem file, String description)
			throws Exception {
		return storeResource(userid, file, description, 0, 0);
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
	public static String storeResourceFile(String userid, InputStream stream, String mimetype,
			String fileName, String filesize, String description) throws Exception {
		return storeResource(userid, stream, mimetype, fileName, filesize);
	}

	public static String storeResourceImage(String userid, InputStream stream, String mimetype,
			String filename, String filesize) throws Exception {
		return storeResource(userid, stream, mimetype, filename, filesize);
	}

	private static String storeResource(String userid, InputStream stream, String mimetype,
			String filename, String filesize) throws Exception, IOException {
		String fileid = generateResourceID();
		String path = getStorePath();
		boolean encrypt = Boolean.parseBoolean(Engine.getProperty("rm.file.encrypt"));
		boolean compress = Boolean.parseBoolean(Engine.getProperty("rm.file.compress"));
		File sf = new File(Engine.getProperty("rm.path"), path);
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
			if (Engine.getProperty("rm.image.mimetype").indexOf((String) params.get("mimetype")) > -1) {
				String notcompressmaxwidth = Engine.getProperty("rm.image.notcompressmaxwidth");
				String notcompressmaxheight = Engine.getProperty("rm.image.notcompressmaxheight");
				long notcompressmaxwidthLen = 0;
				long notcompressmaxheightLen = 0;
				if ((notcompressmaxwidth != null && notcompressmaxwidth.matches("[0-9]+"))
						|| (notcompressmaxheight != null && notcompressmaxheight.matches("[0-9]+"))) {
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
			if (Engine.getProperty("rm.image.mimetype").indexOf((String) params.get("mimetype")) > -1) {
				getThumb(sf, imgType);
			}

			try {
				DocumentService service = new DocumentService();
				params.put("fileid", fileid);
				params.put("userid", userid);
				params.put("path", path);
				params.put("encrypt", encrypt ? 1 : 0);
				params.put("compress", compress ? 1 : 0);
				params.put("description", null);

				service.storeResource(params);
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

	public static void commit(String fileid) throws Exception {
		if (!CommonFun.isNe(fileid))
			commit(fileid.split("/,/g"));
	}

	public static void commit(String[] fileids) throws Exception {
		try {
			if (CommonFun.isNe(fileids))
				return;

			DocumentService service = new DocumentService();
			service.commit(fileids);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static void rollback(String fileid) throws Exception {
		try {
			if (CommonFun.isNe(fileid))
				return;
			DocumentService service = new DocumentService();
			service.rollback(fileid.split("/,/g"));
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static void rollback(String[] fileids) throws Exception {
		try {
			if (CommonFun.isNe(fileids))
				return;

			DocumentService service = new DocumentService();
			service.rollback(fileids);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static void gc() throws Exception {
		try {
			DocumentService service = new DocumentService();
			service.gc();
		} catch (Exception ex) {
			throw ex;
		}
	}

	private static String getStorePath() throws Exception {
		File file = new File(Engine.getProperty("rm.path"));
		if (!file.exists()) {
			file.mkdirs();
		}

		// int level =
		// Integer.parseInt(Engine.getProperty("rm.path.level"));
		// int max_count =
		// Integer.parseInt(Engine.getProperty("rm.path.count"));
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

		file = new File(Engine.getProperty("rm.path"), path);
		if (!file.exists()) {
			file.mkdirs();
		}

		return path;
	}

	private static String generateResourceID() {
		String code = CommonFun.getSSID();
		code = MD5.md5(code);
		return code;
	}

	public static File getThumb(File srcfile, String type) throws Exception {
		int iwidth = Integer.parseInt(Engine.getProperty("rm.image.thumb.width")),
				iheight = Integer.parseInt(Engine.getProperty("rm.image.thumb.height"));
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

	public static int deleteResource(String[] fileids) throws Exception {
		DocumentService service = new DocumentService();
		int i = service.deleteResource(fileids);
		return i;
	}

	private static String getMimeType(String filename) {
		String mimetype = URLConnection.guessContentTypeFromName(filename);
		if (mimetype == null)
			return "application/octet-stream";
		return mimetype;
	}

	/**
	 * 检查文件是否已经更新，通过修改时间
	 * 
	 * @param request
	 * @param response
	 * @param lastModifiedTimestamp
	 * @return
	 */
	public static boolean checkNotModified(HttpServletRequest request, HttpServletResponse response,
			long lastModifiedTimestamp) {
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
	public static BufferedImage resize(BufferedImage source, int targetW, int targetH, boolean keepScale)
			throws IOException {
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
				g.drawImage(tempImage, 0, 0, currentWidth, currentHeight, 0, 0, currentWidth * bs, currentHeight * bs,
						null);
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

	public static void rotation(String fileid, boolean thumb, double angle, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		DocumentService service = new DocumentService();
		Record rtn = service.getResource(fileid);

		if (rtn == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		File file = new File(Engine.getProperty("rm.path"), (String) rtn.get("path"));
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

		double minX = Math.min(Math.min(newPositions[0][0], newPositions[1][0]),
				Math.min(newPositions[2][0], newPositions[3][0]));
		double maxX = Math.max(Math.max(newPositions[0][0], newPositions[1][0]),
				Math.max(newPositions[2][0], newPositions[3][0]));
		double minY = Math.min(Math.min(newPositions[0][1], newPositions[1][1]),
				Math.min(newPositions[2][1], newPositions[3][1]));
		double maxY = Math.max(Math.max(newPositions[0][1], newPositions[1][1]),
				Math.max(newPositions[2][1], newPositions[3][1]));

		int newWidth = (int) Math.round(maxX - minX);
		int newHeight = (int) Math.round(maxY - minY);
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

	public static void getFileLastModify(String[] fileIdArray, HttpServletRequest req,
			HttpServletResponse resp) {
		try {
			StringBuffer sb = getFileLastModifyStr(fileIdArray);
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

	public static StringBuffer getFileLastModifyStr(String[] fileIdArray) throws Exception {
		DocumentService service = new DocumentService();
		Result rtn = service.getResources(fileIdArray);
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
}