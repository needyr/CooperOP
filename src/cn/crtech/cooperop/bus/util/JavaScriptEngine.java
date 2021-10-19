package cn.crtech.cooperop.bus.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import cn.crtech.cooperop.bus.util.javascript.JavaScriptShell;

/**
 * <p>
 * 鍚嶇О: JavaScriptEngine
 * </p>
 * <p>
 * 鎻忚堪: JS鑴氭湰瑙ｆ瀽鎵ц绫�
 * </p>
 * <p>
 * 鏀寔鍙傛暟绫诲瀷: java.lang.String; java.lang.Integer; java.lang.Double;
 * java.lang.Boolean
 * </p>
 * 
 * @author Shine.Xia
 * @version 1.0
 */

public class JavaScriptEngine {

	// main function(Example)
	public static void main(String[] args) {
		try {

			StringBuffer script = new StringBuffer();
			script.append("amount ++;\r\n");
			script.append("cash = 100.01 * 100;\r\n");
			script.append("flag = true;\r\n");
			script.append("flag = true;\r\n");
			script.append("var tmp = \"Shine\";\r\n");
			script.append("tmp =\" test\";\r\n");
			script.append("name = \" \" + tmp;\r\n");
			script.append("for (var i = 0; i < tmparray.length; i ++) {\r\n");
			script.append("tmparray[i] = \"SHINE\" + i;\r\n");
			script.append("}\r\n");
			script.append("tmphash.put(\"id\", \"SHINE\");\r\n");
			script.append("tmphash.put(\"date\", new Date());\r\n");
			script.append("tmphash.put(\"name\", \"澶忎簯\");\r\n");
			script.append("var t = {a: 1, b: 2};");
			script.append("12");

			// 瀹炰緥鍖栧璞�
			JavaScriptEngine engine = new JavaScriptEngine();

			// 璧嬪�煎弬鏁�
			engine.addParam("name", "USA911", false);
			engine.addParam("amount", "0", true);
			engine.addParam("cash", new Double(0.00), true);
			engine.addParam("flag", "false", true);

			String[] tmparray = new String[3];
			tmparray[0] = "ARRAY0";
			tmparray[1] = "ARRAY1";
			tmparray[2] = "ARRAY2";
			engine.addParam("tmparray", tmparray, true);

			Hashtable tmphash = new Hashtable();
			tmphash.put("id", "JOHNY");
			engine.addParam("tmphash", tmphash, true);

			// 鎵ц鑴氭湰
			// boolean tmp = engine.excuteBoolean(script);
			String tmp = engine.excuteString(script.toString());
			// engine.excute(script);

			// 鑴氭湰杩斿洖鍊�
			System.err.println("jq=="+tmp);

			// 鑾峰彇璧嬪�煎弬鏁扮殑鏈�缁堝��
			Object tmp2;

			tmp2 = engine.getParamValue("name");
			System.err.println(tmp2);

			tmp2 = engine.getParamValue("amount");
			System.err.println(tmp2);

			tmp2 = engine.getParamValue("cash");
			System.err.println(tmp2);

			tmp2 = engine.getParamValue("flag");
			System.err.println(tmp2);

			tmp2 = engine.getParamValue("tmparray");
			String[] tmp3 = (String[]) tmp2;
			for (int i = 0; i < tmp3.length; i++) {
				System.err.println(tmp3[i]);
			}

			tmp2 = engine.getParamValue("tmphash");
			Hashtable tmp4 = (Hashtable) tmp2;
			System.err.println(tmp4.get("id"));
			System.err.println(tmp4.get("name"));
			System.err.println(tmp4.get("date"));

			for (int i = 0; i < engine.getParamSize(); i++) {
				System.err.println(engine.getParamName(i) + ":"
						+ engine.getParamValue(i));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// 鍙橀噺鍒楄〃
	private Vector paramlist = new Vector();
	// 瑙ｆ瀽JS涓婁笅鏂囧璞�
	private Context cx;
	// 瑙ｆ瀽JS鍙橀噺瀹瑰櫒瀵硅薄
	private Scriptable scope;

	// 鏋勯�犲嚱鏁�
	public JavaScriptEngine() {
		paramlist = new Vector();
		cx = ContextFactory.getGlobal().enterContext();
		cx.setOptimizationLevel(-1);
		cx.setLanguageVersion(Context.VERSION_1_8);
		JavaScriptShell shell = new JavaScriptShell();
		String[] names = { "print", "quit", "version", "load", "help" };
		shell.defineFunctionProperties(names, JavaScriptShell.class, ScriptableObject.DONTENUM);
		scope = cx.initStandardObjects(shell);

		try {
			// env.js
			cx.evaluateReader(scope,
					new InputStreamReader(JavaScriptShell.class.getResourceAsStream("env.rhino.1.2.13.js")),
					"env.rhino.1.2.13", 1, null);
			// jquery
			cx.evaluateReader(scope,
					new InputStreamReader(JavaScriptShell.class.getResourceAsStream("jquery.1.11.2.min.js")),
					"jquery.1.11.2", 1, null);
		} catch (IOException e) {
		}

	}


	/**
	 * 澧炲姞JS鍙傛暟
	 * 
	 * @param name
	 *            鍙傛暟鍚嶇О
	 * @param type
	 *            鍙傛暟绫诲瀷锛岀洰鍓嶆敮鎸�: java.lang.String; java.lang.Integer;
	 *            java.lang.Double; java.lang.Boolean
	 * @param value
	 *            鍙傛暟鍊�
	 * @param writeable
	 *            鏄惁鍙啓
	 * @throws java.lang.Exception
	 */
	public void addParam(String name, String type, String value,
			boolean writeable) throws Exception {
		for (int i = 0; i < paramlist.size(); i++) {
			param tmp = (param) paramlist.get(i);
			if (tmp.getName().equalsIgnoreCase(name)) {
				throw new Exception("\"" + tmp.getName()
						+ "\" is already defined in context.");
			}
		}
		param p = new param(name, type, value, writeable);
		paramlist.add(p);
	}

	/**
	 * 澧炲姞JS鍙傛暟
	 * 
	 * @param name
	 *            鍙傛暟鍚嶇О
	 * @param type
	 *            鍙傛暟绫诲瀷锛岀洰鍓嶆敮鎸�: java.lang.String; java.lang.Integer;
	 *            java.lang.Double; java.lang.Boolean
	 * @param value
	 *            鍙傛暟鍊�
	 * @param writeable
	 *            鏄惁鍙啓
	 * @throws java.lang.Exception
	 */
	public void addParams(HashMap<String, Object> values, boolean writeable)
			throws Exception {
		List<String> keys = new ArrayList<String>(values.keySet());
		for (int i = 0; i < keys.size(); i ++) {
			addParam(keys.get(i), values.get(keys.get(i)), writeable);
		}
	}

	public void addParam(String name, Object value, boolean writeable)
			throws Exception {
		for (int i = 0; i < paramlist.size(); i++) {
			param tmp = (param) paramlist.get(i);
			if (tmp.getName().equalsIgnoreCase(name)) {
				throw new Exception("\"" + tmp.getName()
						+ "\" is already defined in context.");
			}
		}
		param p = new param(name, value, writeable);
		paramlist.add(p);
	}
	public void setParams(HashMap<String, Object> values, boolean writeable) throws Exception {
		List<String> keys = new ArrayList<String>(values.keySet());
		for (int i = 0; i < keys.size(); i++) {
			setParam(keys.get(i), values.get(keys.get(i)), writeable);
		}
	}

	public void setParam(String name, Object value, boolean writeable) throws Exception {
		boolean update = false;
		for (int i = 0; i < paramlist.size(); i++) {
			param tmp = (param) paramlist.get(i);
			if (tmp.getName().equalsIgnoreCase(name)) {
				tmp.value = value;
				tmp.writeable = writeable;
				update = true;
			}
		}
		if (!update) {
			param p = new param(name, value, writeable);
			paramlist.add(p);
		}
	}

	/**
	 * 鎵цJS鑴氭湰
	 * 
	 * @param script
	 *            - 鏂囦欢缁濆璺緞 鎴� 瀛楃涓茶剼鏈�
	 * @return
	 * @throws java.lang.Exception
	 */
	public void excute(String script) throws Exception {
		putParams();

		Object result;
		if (script.endsWith(".js")) {
			FileReader reader = null;
			try {
				reader = new FileReader(script);
				result = cx.evaluateReader(scope, reader, null, 0, null);
			} catch (Exception ex) {
				ex.printStackTrace();
				if (reader != null) {
					reader.close();
				}
				throw new Exception(ex.getMessage());
			}
		} else {
			result = cx.evaluateString(scope, script, null, 0, null);
		}

		updateParams();
	}

	/**
	 * 鎵цJS鑴氭湰
	 * 
	 * @param script
	 *            - 鏂囦欢缁濆璺緞 鎴� 瀛楃涓茶剼鏈�
	 * @return
	 * @throws java.lang.Exception
	 */
	public String excuteString(String script) throws Exception {
		String sRtn = "";

		putParams();

		Object result;
		if (script.endsWith(".js")) {
			FileReader reader = null;
			try {
				reader = new FileReader(script);
				result = cx.evaluateReader(scope, reader, null, 0, null);
			} catch (Exception ex) {
				if (reader != null) {
					reader.close();
				}
				throw new Exception(ex.getMessage());
			}
		} else {
			result = cx.evaluateString(scope, script, null, 0, null);
		}

		updateParams();

		if (result.getClass().getName().equals("java.lang.String")) {
			sRtn = (String) result;
		}

		return sRtn;
	}

	/**
	 * 鎵цJS鑴氭湰
	 * 
	 * @param script
	 *            - 鏂囦欢缁濆璺緞 鎴� 瀛楃涓茶剼鏈�
	 * @return
	 * @throws java.lang.Exception
	 */
	public boolean excuteBoolean(String script) throws Exception {
		boolean bRtn = false;

		putParams();

		Object result;
		if (script.endsWith(".js")) {
			FileReader reader = null;
			try {
				reader = new FileReader(script);
				result = cx.evaluateReader(scope, reader, null, 0, null);
			} catch (Exception ex) {
				if (reader != null) {
					reader.close();
				}
				throw new Exception(ex.getMessage());
			}
		} else {
			result = cx.evaluateString(scope, script, null, 0, null);
		}

		updateParams();

		if (result.getClass().getName().equals("java.lang.Boolean")) {
			Boolean tmp = (Boolean) result;
			bRtn = tmp.booleanValue();
		}

		return bRtn;
	}

	/**
	 * 鑾峰彇褰撳墠JS鑴氭湰涓弬鏁版暟閲�
	 * 
	 * @return
	 */
	public int getParamSize() {
		return this.paramlist.size();
	}

	/**
	 * 鑾峰彇褰撳墠JS鑴氭湰涓i涓弬鏁扮殑鍚嶇О
	 * 
	 * @param i
	 *            鎸囧畾绗琲涓弬鏁板悕绉拌繑鍥�
	 * @return
	 */
	public String getParamName(int i) {
		return ((param) this.paramlist.get(i)).getName();
	}

	/**
	 * 鑾峰彇褰撳墠JS鑴氭湰涓i涓弬鏁扮殑鍊�
	 * 
	 * @param i
	 *            鎸囧畾绗琲涓弬鏁板�艰繑鍥�
	 * @return
	 */
	public Object getParamValue(int i) {
		return ((param) this.paramlist.get(i)).getValue();
	}

	/**
	 * 鑾峰彇褰撳墠JS鑴氭湰涓寚瀹氬弬鏁扮殑鍊�
	 * 
	 * @param paramname
	 *            鎸囧畾鍚嶇О鐨勫弬鏁板�艰繑鍥�
	 * @return
	 */
	public Object getParamValue(String paramname) {
		return getParam(paramname).getValue();
	}

	private param getParam(String paramname) {
		for (int i = 0; i < paramlist.size(); i++) {
			param tmp = (param) paramlist.get(i);
			if (tmp.getName().equals(paramname)) {
				return tmp;
			}
		}
		return null;
	}

	private void putParams() {
		for (int i = 0; i < paramlist.size(); i++) {
			param p = (param) paramlist.get(i);
			scope.put(p.getName(), scope, p.getValue());
		}
	}

	private void updateParams() {
		for (int i = 0; i < paramlist.size(); i++) {
			param p = (param) paramlist.get(i);

			if (p.getWriteable()) {
				Object tmpvalue = scope.get(p.getName(), scope);

				if (!tmpvalue.getClass().getName().equals(p.getType())) {
					// 姝ゅ闇�娣诲姞鍙橀噺杞崲澶勭悊锛屼緥濡� Double -> Integer
					if (p.getType().equals("java.lang.Integer")) {
						String tmp = String.valueOf(tmpvalue);
						tmp = tmp.substring(0, tmp.indexOf("."));
						tmpvalue = Integer.valueOf(tmp);
					}
				}

				p.setValue(tmpvalue);
			}
		}
	}

	private class param {
		private String name;
		private String type;
		private Object value;
		private boolean writeable;

		public param(String name, String type, String value, boolean writeable)
				throws Exception {
			this.name = name;
			this.type = type;
			this.writeable = writeable;

			// 杞寲绫诲瀷淇濆瓨

			// 瀛楃涓蹭笉闇�瑕佽浆鎹�
			if (type.equals("java.lang.String")) {
				this.value = value;
			}
			// 鍏朵粬绫诲瀷杞崲
			else {
				Class tmpclass = Class.forName(type);

				Class[] cArgs = new Class[1];
				cArgs[0] = Class.forName("java.lang.String");

				Method oMethod = tmpclass.getMethod("valueOf", cArgs);

				Object[] oArgs = new Object[1];
				oArgs[0] = value;

				this.value = oMethod.invoke(tmpclass, oArgs);
			}

		}

		public param(String name, Object value, boolean writeable)
				throws Exception {
			this.name = name;
			this.type = value.getClass().getName();
			this.value = value;
			this.writeable = writeable;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getType() {
			return this.type;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public Object getValue() {
			return this.value;
		}

		public void setWriteable(boolean writeable) {
			this.writeable = writeable;
		}

		public boolean getWriteable() {
			return this.writeable;
		}
	}
}
