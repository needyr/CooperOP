package cn.crtech.cooperop.bus.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import cn.crtech.cooperop.bus.session.Session;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;

public class CommonFun {

	public static boolean isNe(Object o) {
		if (o == null)
			return true;
		if (o instanceof String) {
			if (o.toString().trim().length() == 0) {
				return true;
			}
		} else if (o.getClass().isArray()) {
			if (!o.getClass().getComponentType().isPrimitive()) {
				Object[] tmp = (Object[])o;
				for (int i = 0; i < tmp.length; i++) {
					if (!isNe(tmp[i])) {
						return false;
					}
				}
			} else {
				return false;
			}
		} else if (o instanceof Collection) {
			Collection tmp = (Collection) o;
			for (Object t : tmp) {
				if (!isNe(t)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static String capitalize(String str) {
		if (isNe(str))
			return str;
		byte[] items = str.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	private static ObjectMapper mapper = new ObjectMapper();

	public static String object2Xml(Object obj) {
		return json2Xml(object2Json(obj));
	}

	public static <T> T xml2Object(String xml, Class<T> valueType) {
		return json2Object(xml2Json(xml), valueType);
	}

	public static <T> T map2Object(Map<String, Object> obj, Class<T> valueType) {
		return json2Object(object2Json(obj), valueType);
	}

	public static Map<String, Object> object2Map(Object obj) {
		return json2Object(object2Json(obj), Map.class);
	}

	public static String object2Json(Object obj) {
		if (obj == null)
			return "null";
		try {
			StringWriter sw = new StringWriter();
			JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
			mapper.writeValue(gen, obj);
			gen.close();
			return sw.toString();
		} catch (Exception e) {
			try {
				return JSONObject.fromObject(obj).toString();
			} catch (Exception ex) {
				System.err.println(e.getClass() + ":" + e.getMessage());
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T json2Object(String json, Class<T> valueType) {
		try {
			return mapper.readValue(json, valueType);
		} catch (Exception e) {
			try {
				return (T) JSONObject.toBean(JSONObject.fromObject(json), valueType);
			} catch (Exception ex) {
				return null;
			}
		}
	}

	/**
	 * json string convert to xml string
	 */
	public static String json2Xml(String json) {
		if (json == null) return null;
		StringReader input = new StringReader(json);
		StringWriter output = new StringWriter();
		JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).build();
		try {
			XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
			XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
			writer = new PrettyXMLEventWriter(writer);
			writer.add(reader);
			reader.close();
			writer.close();
			if (output.toString().indexOf("<?xml") >= 0) {// remove <?xml version="1.0" encoding="UTF-8"?>
				String tmp = output.toString();
				tmp = tmp.substring(tmp.indexOf("?>") + 2);
				if (tmp.startsWith("\n")) tmp = tmp.substring(1);
				return tmp;
			}
			return output.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		} finally {
			try {
				output.close();
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * xml string convert to json string
	 */
	public static String xml2Json(String xml) {
		StringReader input = new StringReader(xml);
		StringWriter output = new StringWriter();
		JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
		try {
			XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
			XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
			writer.add(reader);
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output.toString();
	}

	public static String urlEncode(String str) {
		try {
			str = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String urlDecode(String str) {
		try {
			str = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static Properties loadPropertiesFile(String file) throws IOException {
		Properties p = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			p.load(fis);
			String charset = p.getProperty("charset");
			if (charset != null) {
				for (Enumeration<?> e = p.propertyNames(); e.hasMoreElements();) {
					String key = (String) e.nextElement();
					String str = p.getProperty(key);
					if (str != null && !str.equals(new String(str.getBytes(charset), charset))) {
						p.setProperty(key, new String(p.getProperty(key).getBytes("ISO-8859-1"), charset));
					}
				}
			}
			fis.close();
		} catch (IOException iex) {
			if (fis != null) {
				fis.close();
			}
			throw iex;
		}
		return p;
	}

	public static Document loadXMLFile(String file) throws Exception {
		return loadXMLFile(new File(file));
	}

	public static Document loadXMLFile(File file) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			doc = builder.build(fis);
			fis.close();
		} catch (Exception ex) {
			if (fis != null) {
				fis.close();
			}
			throw ex;
		}
		return doc;
	}

	public static void saveXMLFile(Document doc, String filename) throws Exception {
		File tmp = new File(filename);
		if (!tmp.getParentFile().exists()) {
			tmp.getParentFile().mkdirs();
		}

		XMLOutputter oXMLOutputter = new XMLOutputter();
		oXMLOutputter.setIndent("    ");
		oXMLOutputter.setEncoding("UTF-8");
		oXMLOutputter.setNewlines(true);
		oXMLOutputter.setExpandEmptyElements(true);
		oXMLOutputter.setTextNormalize(true);

		FileOutputStream oFileOutputStream = null;
		try {
			oFileOutputStream = new FileOutputStream(filename);
			oXMLOutputter.output(doc, oFileOutputStream);
			oFileOutputStream.close();
		} catch (Exception exc) {
			if (oFileOutputStream != null) {
				oFileOutputStream.close();
			}
			throw exc;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Object createBeanFromProperties(String file) throws Exception {
		Properties p = loadPropertiesFile(file);
		ClassLoader classloader = new CommonFun().getClass().getClassLoader();
		Class<?> c = classloader.loadClass((String) p.remove("class"));

		Object o = c.newInstance();

		for (Enumeration<?> e = p.propertyNames(); e.hasMoreElements();) {
			String fieldname = (String) e.nextElement();
			Field field = null;
			try {
				field = c.getField(fieldname);
			} catch (Exception ex) {

			}
			if (field == null) {
				try {
					field = c.getDeclaredField(fieldname);
				} catch (Exception ex) {

				}
				if (field == null) {
					continue;
				}
			}

			String methodname = "set" + capitalize(fieldname);
			Class[] setclass = { field.getType() };
			Method m = null;
			try {
				m = c.getMethod(methodname, setclass);
			} catch (Exception ex) {

			}
			if (m == null) {
				try {
					m = c.getDeclaredMethod(methodname, setclass);
				} catch (Exception ex) {

				}
				if (m == null) {
					continue;
				}
			}

			Object value = conversion(field.getType(), p.getProperty(fieldname));
			Object[] args = { value };
			m.invoke(o, args);

		}

		return o;
	}

	@SuppressWarnings("rawtypes")
	public static Vector<Object> createVectorFromXML(String file) throws Exception {
		Vector<Object> rtn = new Vector<Object>();

		Document doc = loadXMLFile(file);
		Element root = doc.getRootElement();

		ClassLoader classloader = new CommonFun().getClass().getClassLoader();
		Class<?> c = classloader.loadClass(root.getAttributeValue("class"));

		List<?> children = root.getChildren();

		for (int i = 0; i < children.size(); i++) {
			Object o = c.newInstance();
			Element child = (Element) children.get(i);
			List<?> fields = child.getChildren();
			for (int j = 0; j < fields.size(); j++) {
				Element f = (Element) fields.get(j);
				String fieldname = f.getName();
				Field field = null;
				try {
					field = c.getField(fieldname);
				} catch (Exception ex) {

				}
				if (field == null) {
					try {
						field = c.getDeclaredField(fieldname);
					} catch (Exception ex) {

					}
					if (field == null) {
						continue;
					}
				}

				String methodname = "set" + capitalize(fieldname);
				Class[] setclass = { field.getType() };
				Method m = null;
				try {
					m = c.getMethod(methodname, setclass);
				} catch (Exception ex) {

				}
				if (m == null) {
					try {
						m = c.getDeclaredMethod(methodname, setclass);
					} catch (Exception ex) {

					}
					if (m == null) {
						continue;
					}
				}

				Object value = conversion(field.getType(), f.getTextTrim());
				Object[] args = { value };
				m.invoke(o, args);
			}
			rtn.add(o);
		}

		return rtn;
	}

	@SuppressWarnings("rawtypes")
	private static Object conversion(Class<?> clazz, String value) {
		if (value == null) {
			return null;
		}

		if (clazz == String.class) {
			return value;
		}

		if (clazz == Character.class || clazz == Character.TYPE) {
			if (value.length() == 1) {
				return new Character(value.charAt(0));
			} else {
				throw new IllegalArgumentException(
						"Can't more than one character in string - can't convert to char: '" + value + "'");
			}
		}

		String trimValue = value.trim();

		if (clazz == Boolean.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Boolean.valueOf(trimValue);
		}

		if (clazz == Boolean.TYPE) {
			return Boolean.valueOf(trimValue);
		}

		if (clazz == Integer.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Integer.valueOf(trimValue);
		}

		if (clazz == Integer.TYPE) {
			if (trimValue.length() == 0) {
				return Integer.valueOf("0");
			}

			return Integer.valueOf(trimValue);
		}

		if (clazz == Short.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Short.valueOf(trimValue);
		}

		if (clazz == Short.TYPE) {
			if (trimValue.length() == 0) {
				return Short.valueOf("0");
			}

			return Short.valueOf(trimValue);
		}

		if (clazz == Byte.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Byte.valueOf(trimValue);
		}

		if (clazz == Byte.TYPE) {
			if (trimValue.length() == 0) {
				return Byte.valueOf("");
			}

			return Byte.valueOf(trimValue);
		}

		if (clazz == Long.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Long.valueOf(trimValue);
		}

		if (clazz == Long.TYPE) {
			if (trimValue.length() == 0) {
				return Long.valueOf("0");
			}

			return Long.valueOf(trimValue);
		}

		if (clazz == Float.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Float.valueOf(trimValue);
		}

		if (clazz == Float.TYPE) {
			if (trimValue.length() == 0) {
				return Float.valueOf("0");
			}

			return Float.valueOf(trimValue);
		}

		if (clazz == Double.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Double.valueOf(trimValue);
		}

		if (clazz == Double.TYPE) {
			if (trimValue.length() == 0) {
				return Double.valueOf("0.0D");
			}

			return Double.valueOf(trimValue);
		}

		throw new IllegalArgumentException("Unsupported conversion type: " + clazz.getName());
	}

	public static void moveFile(File srcfile, File destfile, boolean overwrite) throws Exception {
		copyFile(srcfile, destfile, overwrite);
		deleteFile(srcfile);
	}

	public static void copyFile(File srcfile, File destfile, boolean overwrite) throws IOException {

		if (srcfile == null) {
			throw new FileNotFoundException(null);
		}
		if (!srcfile.exists()) {
			throw new java.io.FileNotFoundException(srcfile.getAbsolutePath());
		}

		if (!destfile.getParentFile().exists()) {
			destfile.getParentFile().mkdirs();
		}

		if (overwrite) {
			if (destfile.exists()) {
				deleteFile(destfile);
			}
		} else {
			if (destfile.exists()) {
				throw new IOException(destfile.getAbsolutePath() + " is exists!");
			}
		}

		if (srcfile.isDirectory()) {
			File[] oFiles = srcfile.listFiles();
			for (int i = 0; i < oFiles.length; i++) {
				copyFile(oFiles[i], new File(destfile, oFiles[i].getName()), overwrite);
			}
		} else {
			destfile.createNewFile();

			byte[] bytearray = new byte[512];
			int len = 0;
			FileInputStream input = new FileInputStream(srcfile);
			FileOutputStream output = new FileOutputStream(destfile);
			try {
				while ((len = input.read(bytearray)) != -1) {
					output.write(bytearray, 0, len);
				}
			} catch (FileNotFoundException exc) {
				throw exc;
			} catch (SecurityException exc) {
				throw exc;
			} finally {
				input.close();
				output.close();
			}

		}
	}

	public static void deleteFile(File file) {
		deleteFile(file, null);
	}

	public static void deleteFile(File file, FileFilter filter) {
		if (!file.exists()) {
			return;
		}
		File[] files = null;
		if (filter != null) {
			files= file.listFiles(filter);
		} else {
			files = file.listFiles();
		}
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteFile(files[i], filter);
				} else {
					files[i].delete();
				}
			}
		}
		file.delete();
	}
	@SuppressWarnings("rawtypes")
	public static Object simpleConvert(String value, Class<?> paramType) throws Exception {
		if (value == null) {
			return null;
		}
		if (paramType == String.class) {
			return value;
		}

		if (paramType == Character.class || paramType == Character.TYPE) {
			value = urlDecode(value);
			if (value.length() == 1) {
				return new Character(value.charAt(0));
			} else {
				throw new IllegalArgumentException(
						"Can't more than one character in string - can't convert to char: '" + value + "'");
			}
		}

		String trimValue = value.trim();

		if (paramType == Boolean.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Boolean.valueOf(trimValue);
		}

		if (paramType == Boolean.TYPE) {
			return Boolean.valueOf(trimValue);
		}

		if (paramType == Integer.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Integer.valueOf(trimValue);
		}

		if (paramType == Integer.TYPE) {
			if (trimValue.length() == 0) {
				return Integer.valueOf("0");
			}

			return Integer.valueOf(trimValue);
		}

		if (paramType == Short.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Short.valueOf(trimValue);
		}

		if (paramType == Short.TYPE) {
			if (trimValue.length() == 0) {
				return Short.valueOf("0");
			}

			return Short.valueOf(trimValue);
		}

		if (paramType == Byte.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Byte.valueOf(trimValue);
		}

		if (paramType == Byte.TYPE) {
			if (trimValue.length() == 0) {
				return Byte.valueOf("");
			}

			return Byte.valueOf(trimValue);
		}

		if (paramType == Long.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Long.valueOf(trimValue);
		}

		if (paramType == Long.TYPE) {
			if (trimValue.length() == 0) {
				return Long.valueOf("0");
			}

			return Long.valueOf(trimValue);
		}

		if (paramType == Float.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Float.valueOf(trimValue);
		}

		if (paramType == Float.TYPE) {
			if (trimValue.length() == 0) {
				return Float.valueOf("0");
			}

			return Float.valueOf(trimValue);
		}

		if (paramType == Double.class) {
			if (trimValue.length() == 0) {
				return null;
			}

			return Double.valueOf(trimValue);
		}

		if (paramType == Double.TYPE) {
			if (trimValue.length() == 0) {
				return Double.valueOf("0.0D");
			}

			return Double.valueOf(trimValue);
		}

		throw new IllegalArgumentException("Unsupported conversion type: " + paramType.getName());
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEquivalent(Class<?> c1) {
		if (c1 == String.class) {
			return true;
		} else if (c1 == Boolean.class || c1 == Boolean.TYPE) {
			return true;
		} else if (c1 == Byte.class || c1 == Byte.TYPE) {
			return true;
		} else if (c1 == Character.class || c1 == Character.TYPE) {
			return true;
		} else if (c1 == Short.class || c1 == Short.TYPE) {
			return true;
		} else if (c1 == Integer.class || c1 == Integer.TYPE) {
			return true;
		} else if (c1 == Long.class || c1 == Long.TYPE) {
			return true;
		} else if (c1 == Float.class || c1 == Float.TYPE) {
			return true;
		} else if (c1 == Double.class || c1 == Double.TYPE) {
			return true;
		} else if (c1 == Void.class || c1 == Void.TYPE) {
			return true;
		} else if (c1 == BigDecimal.class) {
			return true;
		}

		return false;
	}

	public static boolean isCollection(Object c1) {
		if (c1 instanceof Collection) {
			return true;
		}
		return false;
	}

	public static boolean isMap(Object c1) {
		if (c1 instanceof Map) {
			return true;
		}
		return false;
	}

	public static String replaceOnlyStr(String str, String patten, String replacement) {
		if (str == null) {
			return "";
		}
		if (str.indexOf(patten) < 0)
			return str;

		int len = str.length();
		int plen = patten.length();
		StringBuilder newContent = new StringBuilder(len);

		int lastPos = 0, pos = str.indexOf(patten);

		do {
			newContent.append(str, lastPos, pos);
			newContent.append(replacement == null ? "" : replacement);
			lastPos = pos + plen;
			pos = str.indexOf(patten, lastPos);
		} while (pos > 0);
		newContent.append(str, lastPos, len);
		return newContent.toString();
	}

	public static String leftFillString(String str, String fillstr, int length) {
		if (str == null) {
			str = "";
		}

		byte[] bstr = str.getBytes();
		byte[] bnew = new byte[length];

		if (bstr.length >= length) {
			System.arraycopy(bstr, bstr.length - length, bnew, 0, length);
			return new String(bnew);
		}

		if (fillstr == null) {
			fillstr = " ";
		}
		if (fillstr.length() == 0) {
			fillstr = " ";
		}

		byte[] bfill = fillstr.getBytes();

		int cursor = length;
		cursor -= bstr.length;
		System.arraycopy(bstr, 0, bnew, cursor, bstr.length);
		for (int i = 0; i < length - bstr.length; i += bfill.length) {
			if (cursor - bfill.length < 0) {
				System.arraycopy(bfill, bfill.length - cursor, bnew, 0, cursor);
			} else {
				cursor -= bfill.length;
				System.arraycopy(bfill, 0, bnew, cursor, bfill.length);
			}
		}

		return new String(bnew);
	}

	public static String rightFillString(String str, String fillstr, int length) {

		if (str == null) {
			str = "";
		}

		byte[] bstr = str.getBytes();
		byte[] bnew = new byte[length];

		if (bstr.length >= length) {
			System.arraycopy(bstr, 0, bnew, 0, length);
			return new String(bnew);
		}

		if (fillstr == null) {
			fillstr = " ";
		}
		if (fillstr.length() == 0) {
			fillstr = " ";
		}

		byte[] bfill = fillstr.getBytes();

		int cursor = 0;
		System.arraycopy(bstr, 0, bnew, cursor, bstr.length);
		cursor += bstr.length;
		for (int i = 0; i < length - bstr.length; i += bfill.length) {
			if (bfill.length + cursor > length) {
				System.arraycopy(bfill, 0, bnew, cursor, length - cursor);
			} else {
				System.arraycopy(bfill, 0, bnew, cursor, bfill.length);
				cursor += bfill.length;
			}
		}

		return new String(bnew);
	}

	private static long startVaue = 0;

	public static synchronized String getSSID() {
		java.util.Date oToday;
		SimpleDateFormat oFormat;
		String id;
		startVaue++;
		startVaue = startVaue % 1000;
		java.text.DecimalFormat format = new java.text.DecimalFormat("000");

		String sStartVaue = format.format(startVaue);
		oToday = new Date();
		oFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String sDate = oFormat.format(oToday);
		id = sDate + sStartVaue;
		return id;
	}

	public static synchronized String getSessionID(String ip, int port) {
		String id = UUID.nameUUIDFromBytes((ip + port).getBytes()).toString();
		id = id.replaceAll("-", "");
		id = id.toUpperCase();
		return id;
	}

	public static synchronized String getITEMID() {
		String id = UUID.randomUUID().toString();
		id = id.replaceAll("-", "");
		id = id.toUpperCase();
		return id;
	}

	public static String encryptString(String str) {
		if (str == null) {
			return null;
		}

		String rtn = "";

		byte[] tmp = str.getBytes();

		for (int i = 0; i < tmp.length; i++) {
			rtn += Integer.toHexString(tmp[i] + 123).toUpperCase();
		}

		return rtn;
	}

	public static String decryptString(String str) {
		if (str == null) {
			return null;
		}

		byte[] tmp = new byte[str.length() / 2];

		for (int i = 0; i < str.length(); i += 2) {
			tmp[i / 2] = (byte) (Integer.parseInt(str.substring(i, i + 2), 16) - 123);
		}

		return new String(tmp).trim();
	}

	public static Map<String, Object> requestMap(String url) {
		Map<String, Object> p = new HashMap<String, Object>();
		if (CommonFun.isNe(url)) {
			return p;
		}
		if (url.indexOf("?") < 0) {
			return p;
		}
		url = url.substring(url.indexOf("?") + 1);
		String[] params = url.split("&");
		for (String param : params) {
			String[] t = param.split("=");
			p.put(t[0], t.length>1? t[1]: null);
		}
		return p;
	}

	public static HashMap<String, Object> requestMap(HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if ("post".equals(request.getMethod().toLowerCase()) && request.getContentType().toLowerCase().startsWith("application/json")) {
			BufferedReader reader;
			try {
				reader = request.getReader();
				StringBuffer stringBuffer = new StringBuffer();
				String result = null;
				while ((result = reader.readLine()) != null) {
					stringBuffer.append(result);
				}
				return json2Object(stringBuffer.toString(), HashMap.class);
			} catch (IOException e) {
				
			}
		}
		if ("post".equals(request.getMethod().toLowerCase()) && request.getContentType().toLowerCase().startsWith("application/json")) {
			BufferedReader reader;
			try {
				reader = request.getReader();
				StringBuffer stringBuffer = new StringBuffer();
				String result = null;
				while ((result = reader.readLine()) != null) {
					stringBuffer.append(result);
				}
				return json2Object(stringBuffer.toString(), HashMap.class);
			} catch (IOException e) {
				
			}
		}

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			requestMultipartMap(request, map);
		}

		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			if (values != null && values.length == 1) {
				map.put(name, values[0]);
			} else {
				map.put(name, values);
			}
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	private static void requestMultipartMap(HttpServletRequest request, HashMap<String, Object> map) {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		if (items == null) {
			return;
		}

		HashMap<String, List<Object>> listMap = new HashMap<String, List<Object>>();
		Iterator<FileItem> it = items.iterator();
		while (it.hasNext()) {
			FileItem item = it.next();

			String name = item.getFieldName();
			Object value = null;
			if (item.isFormField() == true) {
				name = item.getFieldName();
				try {
					value = new String(item.get(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					;
				}
			} else {
				value = new FileItemImpl(item);
			}

			if (listMap.containsKey(name) == false) {
				listMap.put(name, new ArrayList<Object>());
			}

			List<Object> list = listMap.get(name);
			list.add(value);
		}

		Iterator<String> its = listMap.keySet().iterator();
		while (its.hasNext()) {
			String name = its.next();
			List<Object> list = listMap.get(name);

			if (list.size() == 1) {
				map.put(name, list.get(0));
			} else {
				map.put(name, list.toArray());
			}
		}
	}

	private static class FileItemImpl implements FileItem {
		private static final long serialVersionUID = 1L;

		private FileItem item = null;

		public FileItemImpl(FileItem item) {
			super();
			this.item = item;
		}

		@Override
		public void delete() {
			item.delete();
		}

		@Override
		public byte[] get() {
			return item.get();
		}

		@Override
		public String getContentType() {
			return item.getContentType();
		}

		@Override
		public String getFieldName() {
			return item.getFieldName();
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return item.getInputStream();
		}

		@Override
		public String getName() {
			String name = item.getName();
			return name.substring(name.lastIndexOf('\\') + 1, name.length());
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			return item.getOutputStream();
		}

		@Override
		public long getSize() {
			return item.getSize();
		}

		@Override
		public String getString() {
			return item.getString();
		}

		@Override
		public String getString(String encoding) throws UnsupportedEncodingException {
			return item.getString(encoding);
		}

		@Override
		public boolean isFormField() {
			return item.isFormField();
		}

		@Override
		public boolean isInMemory() {
			return item.isInMemory();
		}

		@Override
		public void setFieldName(String name) {
			item.setFieldName(name);
		}

		@Override
		public void setFormField(boolean state) {
			item.setFormField(state);
		}

		@Override
		public void write(File file) throws Exception {
			item.write(file);
		}

	}

	public static String getMACAddress() {
		String address = GlobalVar.getSystemProperty("server.mac");
		if (address != null && !"".equals(address)) {
			return address;
		} else {
			address = "";
		}
		String os = System.getProperty("os.name");
		if (os != null) {
			if (os.startsWith("Windows")) {
				try {
					ProcessBuilder pb = new ProcessBuilder("ipconfig", "/all");
					Process p = pb.start();
					BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "ISO-8859-1"));
					String line;
					while ((line = br.readLine()) != null) {
						line = new String(line.getBytes("ISO-8859-1"), "GB2312");
						if (line.indexOf("Physical Address") != -1 || line.indexOf("鐗╃悊鍦板潃") != -1) {
							int index = line.indexOf(":");
							address = line.substring(index + 1);
							break;
						}
					}
					br.close();
					return address.trim();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (os.startsWith("Linux")) {
				try {
					ProcessBuilder pb = new ProcessBuilder("ifconfig", "eth0");
					Process p = pb.start();
					BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "ISO-8859-1"));
					String line;
					while ((line = br.readLine()) != null) {
						line = new String(line.getBytes("ISO-8859-1"), "GB2312");
						String key = "纭欢鍦板潃";
						int index = line.indexOf(key);
						if (index < 0) {
							key = "hwaddr";
							index = line.toLowerCase().indexOf(key);// 瀵绘壘鏍囩ず瀛楃涓瞇hwaddr]
						}
						if (index != -1) {
							address = line.substring(index + key.length() + 1);// 鍙栧嚭mac鍦板潃骞跺幓闄�2杈圭┖鏍�
							break;
						}
					}
					br.close();
					return address.trim();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return address;
	}

	public static String getIPAddress() {
		String ip = "127.0.0.1";
		try {
			String os = System.getProperty("os.name");
			if (os != null) {
				if (os.startsWith("Windows")) {
					ip = InetAddress.getLocalHost().getHostAddress();
				} else if (os.startsWith("Linux")) {
					try {
						// 鏍规嵁缃戝崱鍙栨湰鏈洪厤缃殑IP
						Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
						InetAddress ipA = null;
						while (netInterfaces.hasMoreElements()) {
							NetworkInterface ni = netInterfaces.nextElement();
							ipA = ni.getInetAddresses().nextElement();
							if (!ipA.isSiteLocalAddress() && !ipA.isLoopbackAddress()
									&& ipA.getHostAddress().indexOf(":") == -1) {
								ip = ipA.getHostAddress();
								break;
							} else {
								ipA = null;
							}
						}
					} catch (SocketException e1) {
						e1.printStackTrace();
					}
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	public static boolean inArray(String[] array, String target) {
		if (array == null)
			return false;
		if (array.length == 0)
			return false;
		for (String s : array) {
			if (s == null) {
				if (target == null) {
					return true;
				}
				continue;
			}
			if (s.equals(target)) {
				return true;
			}
		}
		return false;
	}

	public static String[] mergeArray(String[] array, String[] array2) {
		if (array == null)
			return array2;
		if (array2 == null)
			return array;

		String[] rtn = new String[array.length + array2.length];
		for (int i = 0; i < array.length; i++) {
			rtn[i] = array[i];
		}

		for (int i = 0; i < array2.length; i++) {
			rtn[array.length + i] = array[i];
		}

		return rtn;
	}

	public static String joinArray(String[] array, String split) {
		if (array == null || array.length == 0)
			return null;
		if (split == null) {
			split = ",";
		}
		StringBuffer rtn = new StringBuffer();
		for (String s : array) {
			if (s == null) {
				continue;
			}
			rtn.append(s);
			rtn.append(split);
		}
		return rtn.toString().substring(0, rtn.length() - split.length());
	}

	public static StringBuilder getResourceAsString(Class<?> clazz, String filepath)
			throws UnsupportedEncodingException, IOException {
		return getResourceAsString(clazz, filepath, "UTF-8");
	}

	public static StringBuilder getResourceAsString(Class<?> clazz, String filepath, String charsetName)
			throws UnsupportedEncodingException, IOException {
		StringBuilder buf = new StringBuilder();
		InputStreamReader isr = null;
		BufferedReader br = null;
		InputStream is = null;
		try {
			is = clazz.getResourceAsStream(filepath);
			isr = new InputStreamReader(is, charsetName);
			br = new BufferedReader(isr);
			String data = null;
			while ((data = br.readLine()) != null) {
				buf.append(data);
			}
		} finally {
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (is != null) {
				is.close();
			}
		}
		return buf;
	}

	public static String removeHtmlTag(String input) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		return removeHtmlTag(input, input.length(), "");
	}

	public static String removeHtmlTag(String input, int length) {
		return removeHtmlTag(input, length, "");
	}

	public static String removeHtmlTag(String input, int length, String overflowStr) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");

		if (str.length() > length) {
			str = str.substring(0, length);
			if (overflowStr != null) {
				str += overflowStr;
			}
			String regEx = "\\pP|\\pS";
			Pattern p = Pattern.compile(regEx);
			p.matches(regEx, str);
			if (p.matches(regEx, str.substring(str.length() - 1))) {
				str = str.substring(0, str.length() - 1);
			}
			str += "...";
		}
		return str;
	}

	public static Map<String, String> getUserAgent(HttpServletRequest req) {
		Map<String, String> sys = new HashMap<String, String>();
		String ua = req.getHeader("User-Agent").toLowerCase();
		String s;
		String msieP = "msie ([\\d.]+)";
		String firefoxP = "firefox\\/([\\d.]+)";
		String chromeP = "chrome\\/([\\d.]+)";
		String operaP = "opera\\/([\\d.]+)";
		String safariP = "version\\/([\\d.]+).*safari";
		String xiaomiP = "xiaomi\\/miuibrowser\\/([\\d.]+)";// xiaomi/miuibrowser/2.0.1
		try {
			Pattern pattern = Pattern.compile(msieP);
			Matcher mat = pattern.matcher(ua);
			if (mat.find()) {
				s = mat.group();
				sys.put("type", "ie");
				sys.put("version", s.split(" ")[1]);
				return sys;
			}
			pattern = Pattern.compile(firefoxP);
			mat = pattern.matcher(ua);
			if (mat.find()) {
				s = mat.group();
				sys.put("type", "firefox");
				sys.put("version", s.split("/")[1]);
				return sys;
			}
			pattern = Pattern.compile(chromeP);
			mat = pattern.matcher(ua);
			if (mat.find()) {
				s = mat.group();
				sys.put("type", "chrome");
				sys.put("version", s.split("/")[1]);
				return sys;
			}
			pattern = Pattern.compile(operaP);
			mat = pattern.matcher(ua);
			if (mat.find()) {
				Pattern pattern1 = Pattern.compile("version\\/([\\d.]+)");
				Matcher mat1 = pattern1.matcher(ua);
				if (mat1.find()) {
					s = mat1.group();
					sys.put("type", "opera");
					sys.put("version", s.split("/")[1]);
					return sys;
				}
			}
			pattern = Pattern.compile(safariP);
			mat = pattern.matcher(ua);
			if (mat.find()) {
				s = mat.group();
				sys.put("type", "safari");
				sys.put("version", s.split("/")[1].split(" ")[0]);
				return sys;
			}
			pattern = Pattern.compile(xiaomiP);
			mat = pattern.matcher(ua);
			if (mat.find()) {
				s = mat.group();
				sys.put("type", "xiaomi_miui");
				sys.put("version", s.split("/")[2]);
				return sys;
			}
		} catch (Exception e) {
			sys.put("type", "other");
			sys.put("version", "0");
			return sys;
		}
		sys.put("type", "other");
		sys.put("version", "0");
		return sys;
	}

	public static String getIp(HttpServletRequest req) {
		String ip = req.getRemoteAddr();
		/*String rip = req.getHeader("X-Real-IP");
		if (!CommonFun.isNe(rip)) {
			ip = rip;
		}*/
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

	public static ByteArrayOutputStream getResponseHtml(InputStream resStream) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		byte[] bt = new byte[102400];
		int j = -1;
		while ((j = resStream.read(bt)) != -1) {
			byteOut.write(bt, 0, j);
		}
		return byteOut;
	}

	public static String formatDate(Date date, String toFormatString) {
		if (date == null) {
			return null;
		} else {
			return new SimpleDateFormat(toFormatString).format(date);
		}
	}

	public static String formatDateStr(String source, String toFormatString) {
		Date date = parseDate(source);
		if (date == null) {
			return source;
		} else {
			return new SimpleDateFormat(toFormatString).format(date);
		}
	}

	public static Date parseDate(String source) {
		if (CommonFun.isNe(source)) {
			return null;
		}
		String splitChar = "/";
		if (source.indexOf("-") > -1) {
			splitChar = "-";
		}
		try {
			if (source.matches("\\d{4}" + splitChar + "\\d{2}" + splitChar + "\\d{2}")) {
				return new SimpleDateFormat("yyyy" + splitChar + "MM" + splitChar + "dd").parse(source);
			} else if (source.matches("\\d{4}" + splitChar + "\\d{2}" + splitChar + "\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
				return new SimpleDateFormat("yyyy" + splitChar + "MM" + splitChar + "dd HH:mm:ss").parse(source);
			} else if (source.matches("\\d{4}" + splitChar + "\\d{2}" + splitChar + "\\d{2} \\d{2}:\\d{2}")) {
				return new SimpleDateFormat("yyyy" + splitChar + "MM" + splitChar + "dd HH:mm").parse(source);
			} else if (source.matches("\\d{4}" + splitChar + "\\d{2}" + splitChar + "\\d{2} \\d{2}")) {
				return new SimpleDateFormat("yyyy" + splitChar + "MM" + splitChar + "dd HH").parse(source);
			} else if (source
					.matches("\\d{4}" + splitChar + "\\d{2}" + splitChar + "\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{0,3}")) {
				return new SimpleDateFormat("yyyy" + splitChar + "MM" + splitChar + "dd HH:mm:ss.S").parse(source);
			} else if (source.matches("\\d{2}" + splitChar + "\\d{2} \\d{2}:\\d{2}")) {
				return new SimpleDateFormat("MM" + splitChar + "dd HH:mm").parse(source);
			} else if (source.matches("\\d{2}" + splitChar + "\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
				return new SimpleDateFormat("MM" + splitChar + "dd HH:mm:ss").parse(source);
			} else if (source.matches("\\d{2}:\\d{2}")) {
				return new SimpleDateFormat("HH:mm").parse(source);
			}
		} catch (ParseException e) {
			;
		}
		return null;
	}
	
	public static void unZip(String filePath, String unZipToPath) throws Exception {
		File rootFile = new File(unZipToPath);
		createFolder(rootFile);
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(filePath, "GBK");
			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> entries = zipFile.getEntries();
			byte[] b = new byte[1024 * 100];
			InputStream input = null;
			OutputStream output = null;
			while (entries.hasMoreElements()) {
				ZipEntry zipEntry = entries.nextElement();
				String name = zipEntry.getName();
				System.out.println(name);
				if (zipEntry.isDirectory()) {
					File sub = new File(rootFile, name);
					createFolder(sub);
				} else {
					try {
						input = new BufferedInputStream(zipFile.getInputStream(zipEntry));
						File outFile = new File(rootFile, name);
						createFolder(outFile.getParentFile());
						output = new BufferedOutputStream(new FileOutputStream(outFile));
						int len = 0;
						while ((len = input.read(b)) != -1) {
							output.write(b, 0, len);
						}
					} finally {
						if (output != null) {
							output.close();
						}
						if (input != null)
							input.close();
					}
				}
			}
		} finally {
			if (zipFile != null) {
				zipFile.close();
			}
		}

	}

	public static void zip(String path, String tempname, String rootPath, boolean includeRoot, FileFilter filter) {
		ZipOutputStream zos = null;
		File file = new File(path);
		if (!(file.exists() && file.isDirectory())) {
			file.mkdirs();
		}
		try {
			InputStream input = null;
			File outFile = new File(path + File.separator + tempname);
			outFile.delete();
			zos = new ZipOutputStream(new FileOutputStream(outFile));
			zos.setEncoding("GBK");
			File rootFile = new File(rootPath);
			List<File> listFile = dirallFile(rootFile, filter);
			String rootCanonicalPath = "";
			if (includeRoot) {
				File parentFile = rootFile.getParentFile();
				if (parentFile == null) {
					parentFile = rootFile;
				}
				rootCanonicalPath = parentFile.getCanonicalPath();
			} else {
				rootCanonicalPath = rootFile.getCanonicalPath();
			}
			int rootLen = rootCanonicalPath.length();
			byte[] b = new byte[1024 * 1024];
			for (File childFile : listFile) {
				try {
					String childCanonicalPath = childFile.getCanonicalPath();
					String name = childCanonicalPath.substring(rootLen);
					if (name.startsWith(File.separator)) {
						name = name.substring(1);
					}
					ZipEntry ze = new ZipEntry(name);
					ze.setSize(childFile.length());
					zos.putNextEntry(ze);
					input = new BufferedInputStream(new FileInputStream(childFile));
					int len = 0;
					while ((len = input.read(b)) != -1) {
						zos.write(b, 0, len);
						zos.flush();
					}
				} catch (Exception e) {
					throw e;
				} finally {
					if (input != null)
						input.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (zos != null)
					zos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private static void createFolder(File f) throws IOException {
		if (f.exists()) {
			if (f.isFile()) {
				throw new IOException("创建目录失败，将要创建的目标目录是个文件: " + f.getAbsolutePath());
			}
		} else {
			if (!f.mkdirs())
				throw new IOException("创建目录失败: " + f.getAbsolutePath());
		}
	}
	
	private static List<File> dirallFile(File file, FileFilter filter) throws IOException {
		List<File> fileList = new ArrayList<File>();
		if (!file.exists() || file.isFile()) {
		}
		File[] files = null;
		if (filter != null) {
			files= file.listFiles(filter);
		} else {
			files = file.listFiles();
		}
		if (files != null)
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.isDirectory()) {
					fileList.addAll(dirallFile(f, filter));
				} else {
					fileList.add(f);
				}
			}
		return fileList;
	}
	
	public static void main(String[] args) {
		System.out.println(encryptString("wx3a113c00402dfb78"));
//		HashMap<String, Object> t3 = new HashMap<String, Object>();
//		t3.put("PAADMVisitNumber", "554026");
//		ArrayList<HashMap<String, Object>> l3 = new ArrayList<HashMap<String, Object>>();
//		l3.add(t3);
//		HashMap<String, Object> t2 = new HashMap<String, Object>();
//		t2.put("VitalSignInfoRt", l3);
//		ArrayList<HashMap<String, Object>> l2 = new ArrayList<HashMap<String, Object>>();
//		l2.add(t2);
//		HashMap<String, Object> t = new HashMap<String, Object>();
//		t.put("Body", l2);
//		System.out.println(t);
//		System.out.println(object2Json(t));
//		System.out.println(object2Xml(t));
//		StringBuffer str = new StringBuffer();
//		str.append("<Body>");
//		str.append("<VitalSignInfoRt>");
//		str.append("<PAADMVisitNumber>554026</PAADMVisitNumber>");
//		str.append("<PAADMVisitNumber>554026</PAADMVisitNumber>");
//		str.append("</VitalSignInfoRt>");
//		str.append("</Body>");
//		System.out.println(str.toString());
//		System.out.println(xml2Json(str.toString()));
//		str = new StringBuffer();
//		str.append("<Body>");
//		str.append("<ResultCode>0</ResultCode>");
//		str.append("<ResultContent>成功</ResultContent>");
//		str.append("<SuccessIDList>");
//		str.append("<RowID>1</RowID>");
//		str.append("<RowID>2</RowID>");
//		str.append("</SuccessIDList>");
//		str.append("</Body>");
//		System.out.println(str.toString());
//		System.out.println(xml2Json(str.toString()));
	}
	public static boolean isMoblie(HttpServletRequest request) {
		
        /*String[] mobileAgents = { "iphone", "android", "phone", "mobile",  
                "wap", "netfront", "java", "opera mobi", "opera mini", "ucweb",  
                "windows ce", "symbian", "series", "webos", "sony",  
                "blackberry", "dopod", "nokia", "samsung", "palmsource", "xda",  
                "pieplus", "meizu", "midp", "cldc", "motorola", "foma",  
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin",  
                "huawei", "novarra", "coolpad", "webos", "techfaith",  
                "palmsource", "alcatel", "amoi", "ktouch", "nexian",  
                "ericsson", "philips", "sagem", "wellcom", "bunjalloo", "maui",  
                "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",  
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop",  
                "benq", "haier", "^lct", "320x320", "240x320", "176x220",  
                "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq",  
                "bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang",  
                "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs", "kddi",  
                "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo",  
                "midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-",  
                "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play",  
                "port", "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-",  
                "send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar",  
                "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-",  
                "upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp",  
                "wapr", "webc", "winw", "winw", "xda", "xda-",  
                "Googlebot-Mobile" };  
        if (request.getHeader("User-Agent") != null) {  
            for (String mobileAgent : mobileAgents) {  
                if (request.getHeader("User-Agent").toLowerCase()  
                        .indexOf(mobileAgent) >= 0) {  
                    isMoblie = true;  
                    break;  
                }  
            }  
        } */
		Session s = Session.getSession(request, null);
        if(s != null && "Y".equals(s.get("is_mobile_login"))){
        	//登陆后根据session中判断是从哪个登陆页面登陆的
        	return true;  
        }
        if("Y".equals(request.getParameter("iml"))){//登陆页面  在资源后加上参数
        	return true;
        }
        return false;  
    }
	public static boolean isPad(HttpServletRequest request) {
		
		Session s = Session.getSession(request, null);
        if(s != null && "Y".equals(s.get("is_pad_login"))){
        	//登陆后根据session中判断是从哪个登陆页面登陆的
        	return true;  
        }
        if("Y".equals(request.getParameter("ipl"))){//登陆页面  在资源后加上参数
        	return true;
        }
        return false;  
    }  
	public static boolean isWX(HttpServletRequest request) {
		
		Session s = Session.getSession(request, null);
        if(s != null && "Y".equals(s.get("is_wx_login"))){
        	//登陆后根据session中判断是从哪个登陆页面登陆的
        	return true;  
        }
        if("Y".equals(request.getParameter("iwxl"))){//登陆页面  在资源后加上参数
        	return true;
        }
        return false;  
    } 
	public static String getLoginType(HttpServletRequest request) {
		String type = null;
		if("Y".equals(request.getParameter("iml"))){//登陆页面  在资源后加上参数
			type = "m";
		}else if("Y".equals(request.getParameter("ipl"))){//登陆页面  在资源后加上参数
			type = "p";
		}else if("Y".equals(request.getParameter("iwxl"))){//登陆页面  在资源后加上参数
			type = "w";
		}
		Session s = Session.getSession(request, null);
		if(s == null){
			return type;
		}
		if("Y".equals(s.get("is_pad_login"))){
			type = "p";  
		}else if("Y".equals(s.get("is_mobile_login"))){
			type = "m";  
		}else if("Y".equals(s.get("is_wx_login"))){
			type = "w";  
		}
		return type;
	}
	public static Map<String,Object> trimMapValue(Map<String,Object> targetMap){
		Set<String> set = targetMap.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String key = (String) it.next();
			if(!isNe(targetMap.get(key)))
			targetMap.put(key, targetMap.get(key).toString().trim());
		} 
		return targetMap;
	}
}
