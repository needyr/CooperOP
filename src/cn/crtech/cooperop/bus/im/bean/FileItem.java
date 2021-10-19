package cn.crtech.cooperop.bus.im.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;

import cn.crtech.cooperop.bus.util.MD5;

public class FileItem implements org.apache.commons.fileupload.FileItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2929885261989931679L;
	
	private File file = null;
	private String fileName = null;
	private String contentType = null;
	private String md5 = null;

	public FileItem(File file) {
		super();
		this.fileName = file.getName();
		this.contentType = URLConnection.guessContentTypeFromName(file.getName());
		this.file = file;
		this.md5 = MD5.md5(file);
	}
	
	public FileItem(File file, String fileName) {
		super();
		this.fileName = fileName;
		this.contentType = URLConnection.guessContentTypeFromName(fileName);
		this.file = file;
		this.md5 = MD5.md5(file);
	}
	
	public FileItem(File file, String fileName, String contentType) {
		super();
		this.fileName = fileName;
		this.contentType = contentType;
		this.file = file;
		this.md5 = MD5.md5(file);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null ) return false;
		if (obj instanceof FileItem) {
			return this.md5.equals(((FileItem)obj).md5);
		}
		return super.equals(obj);
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
		return file.length();
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