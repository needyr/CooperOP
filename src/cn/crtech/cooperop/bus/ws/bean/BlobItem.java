package cn.crtech.cooperop.bus.ws.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.MD5;

public class BlobItem implements FileItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2154416909165906462L;

	private File file = null;
	private String fileName = null;
	private String contentType = null;
	private long size = 0L;
	private String md5 = null;

	public BlobItem(File file) {
		super();
		this.fileName = file.getName();
		this.contentType = URLConnection.guessContentTypeFromName(file.getName());
		this.size = file.length();
		this.md5 = MD5.md5(file);
		this.file = file;
	}

	public BlobItem(File file, String contenttype) {
		super();
		this.fileName = file.getName();
		this.contentType = CommonFun.isNe(contenttype) ? URLConnection.guessContentTypeFromName(file.getName()) : contenttype;
		this.size = file.length();
		this.md5 = MD5.md5(file);
		this.file = file;
	}

	public BlobItem(File file, String filename, String contenttype) {
		super();
		this.fileName = CommonFun.isNe(filename) ? file.getName() : filename;
		this.contentType = CommonFun.isNe(contenttype) ? URLConnection.guessContentTypeFromName(CommonFun.isNe(filename) ? file.getName() : filename) : contenttype;
		this.size = file.length();
		this.md5 = MD5.md5(file);
		this.file = file;
	}

	//接收时使用
	public BlobItem(String filename, String contenttype, long size, String md5, File file) {
		super();
		this.fileName = CommonFun.isNe(filename) ? file.getName() : filename;
		this.contentType = CommonFun.isNe(contenttype) ? URLConnection.guessContentTypeFromName(CommonFun.isNe(filename) ? file.getName() : filename) : contenttype;
		this.size = size;
		this.md5 = md5;
		this.file = file;
	}

	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fileName", fileName);
		map.put("contentType", contentType);
		map.put("size", size);
		map.put("md5", md5);
		map.put("file", file == null ? null : file.getAbsolutePath());
		return map;
	}
	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return this.file;
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
		return size;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	@Override
	public String toString() {
		return this.fileName + "[" + this.contentType + "]: " + this.file.getAbsolutePath();
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
