package cn.crtech.cooperop.bus.engine.window;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jdom.Document;
import org.jdom.Element;

import cn.crtech.cooperop.application.authenticate.Authenticate;
import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.bus.engine.bean.ProcessBean;
import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.engine.Engine;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CooperopException;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.ELExpression;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.JavaScriptEngine;
import cn.crtech.cooperop.bus.util.LocalThreadMap;

public class XlsxWindow implements Window {
	private static final int DEFAULT_LIMIT = 10000;
	private static final int DEFAULT_START = 1;
	private static final String PROCESS_JSP = "/tools/process.jsp";
	private String defaultFontName = "??????";
	// ??????sheet???rowno
	private Map<Sheet, Integer> sheetBodyRowNo = new HashMap<Sheet, Integer>();
	// ??????sheet???rownum
	private Map<Sheet, Integer> sheetSheetRowNum = new HashMap<Sheet, Integer>();
	// ????????????????????????????????????sheet
	private Map<String, Sheet> sheetUsedResultName = new HashMap<String, Sheet>();

	private boolean isdebug = true;

	private SXSSFWorkbook currWorkbook;

	private Map<Integer, CellStyle> cacheCellStyle = new HashMap<Integer, CellStyle>();
	private static final String SEQ_KEY = "_eid";
	private static Map<String, Record> processes = new HashMap<String, Record>();
	
	
	@Override
	public void excute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getServletPath().startsWith("/excel/"))
			return;
		String seq = CommonFun.getSSID();
		CreateExcelAssist createExcelAssist = new CreateExcelAssist(req, resp, seq);
		new Thread(createExcelAssist).start();
		resp.setContentType("text/html; charset=UTF-8");
		Record t = new Record();
		t.put(SEQ_KEY, seq);
		resp.getWriter().write(CommonFun.object2Json(t));
		resp.getWriter().flush();
	}
	public static Record getProgress(HttpServletRequest req) {
		Map<String, Object> params = CommonFun.requestMap(req);
		return processes.get(params.get(SEQ_KEY).toString());
	}
	class CreateExcelAssist implements Runnable {
		private String seq;
		private Map<String, Object> requestParams = new HashMap<String, Object>(0);
		private Record processBean = new Record();
		private File template;
		public CreateExcelAssist(HttpServletRequest req, HttpServletResponse resp, String seq) throws UnsupportedEncodingException {
			String cp = req.getContextPath() == null ? "" : req.getContextPath();
			String sp = req.getServletPath();
			String res = req.getRequestURI().substring((cp + sp).length() + 1);
			String pageid = res.substring(0, res.lastIndexOf(".")).replace('/', '.');
			this.seq = seq;
			Session session = Session.getSession(req, resp);
			
			session.put(this.seq, processBean);
			
			processBean.put("sequence", seq);
			processBean.put("pageid", pageid);
			processBean.put("sessionId", session.getId());
			processes.put(this.seq, processBean);
			
			//this.requestParams = getParam(req);
			this.requestParams = CommonFun.requestMap(req);
			setTemplate(req);
		}

		/**
		 * 
		 * ??????request??????????????????????????????????????????comonfun????????????????????????.
		 * 
		 * @param request
		 * @return ??????map
		 *         <dt><b>???????????????</b></dt>
		 */
		private Map<String, Object> getParam(HttpServletRequest request) {
			Map<String, Object> map = new HashMap<String, Object>();
			Enumeration<String> names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				String[] values = request.getParameterValues(name);
				try {
					for (int i = 0; values != null && i < values.length; i++) {
						values[i] = new String(values[i].getBytes("iso-8859-1"), "utf-8");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (values != null && values.length == 1) {
					map.put(name, values[0]);
				} else {
					map.put(name, values);
				}
			}
			return map;
		}

		/**
		 * 
		 * ?????????????????????????????????????????????????????????.
		 * 
		 * @param req
		 *            <dt><b>???????????????</b></dt>
		 */
		private void setTemplate(HttpServletRequest req) {
			String pageid = processBean.getString("pageid");
			String module = pageid.substring(0, pageid.indexOf('.'));
			pageid = pageid.substring(pageid.indexOf('.') + 1);

			String uri = null;
			String custom_id = GlobalVar.getSystemProperty("custom.id");
			if(!CommonFun.isNe(custom_id)){
				uri = GlobalVar.getSystemProperty("view.excel.custom.rule");
				uri = uri.replace("@[CUSTOM]", custom_id);
				uri = uri.replace("@[MODULE]", module);
				uri = uri.replace("@[PAGE]", pageid.replace('.', '/'));
				if(!new File(GlobalVar.getWorkPath() + uri).exists()){
					uri = null;
				}
			}
			if(uri == null){
				uri = GlobalVar.getSystemProperty("view.excel.access.rule");
				uri = uri.replace("@[MODULE]", module);
				uri = uri.replace("@[PAGE]", pageid.replace('.', '/'));
			}

			template = new File(req.getSession().getServletContext().getRealPath(uri));
		}

		/**
		 * <dt><b>???????????????</b></dt>
		 */
		@Override
		public void run() {
			try {
				processBean.put("currNumBegin", DEFAULT_START);
				processBean.put("currNumEnd", DEFAULT_LIMIT);
				processBean.put("beginTime", System.currentTimeMillis());
				processBean.put("total", -1);
				executeCallActionAndCreateExcel();
				processBean.put("endTime", System.currentTimeMillis());

			} catch (Exception e) {
				processBean.put("message", "??????excel?????????" + e.getMessage());
				e.printStackTrace();
			}
		}

		/**
		 * 
		 * ????????????action,????????????????????????httpservletrequest??????????????????????????? FrameEngine???executeCallAction ??????.
		 * 
		 * @param map
		 *            ????????????Map
		 * @return
		 * @throws Exception
		 *             <dt><b>???????????????</b></dt>
		 */
		public Object executeCallAction(Map<String, Object> map) throws Exception {
			String pageid = processBean.getString("pageid");
			try {
				Map<String, Object> a = Authenticate.getAction(pageid);
				if (a != null) {
					Class<?> c = (Class<?>) a.get("class");
					Method m = (Method) a.get("method");
					Object t = Engine.callAction(c, m, map);
					return t;
				} else {
					throw new CooperopException(CooperopException.DATA_NOT_FOUND);
				}
			} catch (Exception e) {
				throw e;
			}
		}

		/**
		 * 
		 * ??????action?????????excel .
		 * 
		 * @throws Exception
		 *             <dt><b>???????????????</b></dt>
		 */
		@SuppressWarnings("unchecked")
		private void executeCallActionAndCreateExcel() throws Exception {
			String pageid = processBean.getString("pageid");
			pageid = pageid.substring(pageid.indexOf('.') + 1);
			LocalThreadMap.put("pageid", processBean.getString("pageid"));
			LocalThreadMap.put("sessionId", processBean.getString("sessionId"));
			LocalThreadMap.put("start", requestParams.get("start"));
			LocalThreadMap.put("limit", requestParams.get("limit"));
			LocalThreadMap.put("sort", requestParams.get("sort"));
			LocalThreadMap.put("filter", requestParams.get("filter"));
			
			// ???clone?????????FrameEngine.callAction??????????????????page window??????????????????????????????????????????????????????????????????hashMap???clone??????
			Object t = executeCallAction((HashMap<String, Object>) ((HashMap<String, Object>) requestParams).clone());
			if (t == null) {
				processBean.put("message", "????????????");
				return;
			}
			if (t instanceof Result) {
				Map<String, Object> temp = new HashMap<String, Object>();
				String filename = null;
				if (CommonFun.isNe(requestParams.get("$exportname"))) {
					filename = "noname";
				} else {
					filename = requestParams.get("$exportname").toString();
				}
				temp.put("filename", filename);
				temp.put(filename, t);
				temp.put(filename + ".name", filename);
				temp.put(filename + ".title", filename);
				String [] ss = requestParams.get("fields").toString().split(",");
				temp.put(filename + ".fields", ss);
				Map<String, Object> m = CommonFun.json2Object((String)requestParams.get("fielddefs"), Map.class);
				temp.put(filename + ".fielddefs", m);
				try {
					long l = System.currentTimeMillis();
					exportXLSX(temp);
				} catch (Throwable e) {
					throw new ServletException(e);
				}
			} else if(t instanceof Map){
				if (template.exists()) {
					try {
						Map<String, Object> m = (Map<String, Object>) t;
						Session session = Session.getSession();
						Account user = (Account) session.get("userinfo");
						m.put("param", requestParams);
						m.put("session", session);
						m.put("user", user);
						exportExcel(template, m);
					} catch (Throwable e) {
						throw new ServletException(e);
					}
				} else {
					try {
						exportXLSX((Map<String, Object>) t);
					} catch (Throwable e) {
						throw new ServletException(e);
					}
				}
			}else {
				
			}
		}

		private boolean checkAvailable(String available, Map<String, Object> params) throws Exception {
			if (available == null)
				return true;
			Object value = ELExpression.excuteExpression(available, params);
			if (value == null) {
				return false;
			} else if (value.getClass() == String.class) {
				return "true".equalsIgnoreCase((String) value);
			} else if (value.getClass() == Boolean.class) {
				return (Boolean) value;
			} else {
				return false;
			}
		}

		public void exportXLSX(Map<String, Object> t) throws Throwable {
			String fileName = (String) t.get("filename");
			if (fileName == null) {
				fileName = "noname";
			}
			String fileId = CommonFun.getSSID();
			File file = ResourceManager.getTempFilePath(fileId);
			file.mkdirs();
			file = new File(file, fileName + ".xlsx");
			String url = null;
			OutputStream out = null;
			try {
				url = "rm/td/" + fileId + "/" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx";
				currWorkbook = new SXSSFWorkbook(100); // keep 50 rows in memory, exceeding rows will be flushed to disk
				// int rowno;
				createXLSX(t);
				out = new BufferedOutputStream(new FileOutputStream(file));
				currWorkbook.write(out);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (out != null)
					out.close();
			}
			processBean.put("downloadUrl", url);
		}

		private void createXLSX(Map<String, Object> t) throws Throwable {
			Iterator<String> keys = t.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				Object tmp = t.get(key);
				// ?????????Result??????
				if (tmp instanceof Result) {
					Result r = (Result) tmp;
					// ????????????????????????????????????????????????
					// keys.remove();
					// tmp = null;
					// ??????excel??????
					String sheetName = (String) t.get(key + ".name");
					sheetName = sheetName == null ? key : sheetName;
					System.out.println(sheetName);
					Sheet sh = currWorkbook.createSheet(sheetName);
					sheetSheetRowNum.put(sh, 0);
					sheetBodyRowNo.put(sh, 0);
					String title = (String) t.get(key + ".title");
					title = CommonFun.replaceOnlyStr(title, "<", "&lt;");
					title = CommonFun.replaceOnlyStr(title, ">", "&gt;");
					String[] fields = (String[]) t.get(key + ".fields");
					long rowcount = r.getCount();
					if (fields == null && rowcount == 0) {
						continue;
					}
					@SuppressWarnings("unchecked")
					HashMap<String, HashMap<String, Object>> fielddefs = (HashMap<String, HashMap<String, Object>>) t.get(key + ".fielddefs");
					if (fields == null) {
						Object record = r.getResultset(0);
						Object[] fs = ((Map) record).keySet().toArray();
						fields = new String[fs.length];
						for (int i = 0; i < fs.length; i++) {
							fields[i] = (String) fs[i];
						}
					} else {
						boolean hasRowno = false;
						for (int i = 0; i < fields.length; i++) {
							if ("rowno".equals(fields[i])) {
								hasRowno = true;
								break;
							}
						}
						if (!hasRowno) {
							String[] fs = new String[fields.length + 1];
							fs[0] = "rowno";
							for (int i = 0; i < fields.length; i++) {
								fs[i + 1] = fields[i];
							}
							fields = fs;
							HashMap<String, Object> fd = new HashMap<String, Object>();
							fd.put("name", "rowno");
							fd.put("label", "??????");
							fd.put("type", "number");
							fielddefs.put("rowno", fd);
						}
					}
					int columncount = fields.length;
					// ??????title??? begin
					Row titleRow = getOrCreateRow(sh, rownumAddOne(sh));
					Cell titleCell = titleRow.createCell(0);
					titleCell.setCellValue(title);
					sh.addMergedRegion(new CellRangeAddress(0, 0, 0, columncount - 1));
					// ??????title??? end
					// ??????????????? begin
					Row thRow = getOrCreateRow(sh, rownumAddOne(sh));
					for (int c = 0; c < fields.length; c++) {
						String svalue = "" + (CommonFun.isNe(fielddefs.get(fields[c])) ? "" : fielddefs.get(fields[c]).get("label"));
						svalue = CommonFun.replaceOnlyStr(svalue, "<", "&lt;");
						svalue = CommonFun.replaceOnlyStr(svalue, ">", "&gt;");
						Cell thCell = thRow.createCell(c);
						thCell.setCellValue(svalue);
					}
					// ??????????????? end
					// ???????????????begin
					Record total = r.getTotals();
					setXlsxRecord(r, sh, fields, fielddefs);
					// ???????????????end
					// ???????????????begin
					Row totalRow = getOrCreateRow(sh, rownumAddOne(sh));
					for (int c = 0; c < fields.length; c++) {
						Object value = total.get(fields[c]);
						Cell totalCell = totalRow.createCell(c);
						if ("rowno".equals(fields[c])) {
							value = "??????";
						}
						if (CommonFun.isNe(value)) {
							totalCell.setCellType(Cell.CELL_TYPE_BLANK);
						} else {
							totalCell.setCellType(Cell.CELL_TYPE_STRING);
							totalCell.setCellValue(value + "");
						}
					}
					// ???????????????end
				}

			}
		}

		@SuppressWarnings("unchecked")
		private void setXlsxRecord(Result r, Sheet sh, String[] fields, HashMap<String, HashMap<String, Object>> fielddefs) throws Throwable {
			long len = r.getResultset().size();
			for (int i = 0; i < len; i++) {
				Row recordRow = getOrCreateRow(sh, rownumAddOne(sh));
				sheetBodyRowNo.put(sh, sheetBodyRowNo.get(sh) + 1);
				for (int c = 0; c < fields.length; c++) {
					HashMap<String, Object> fielddef = fielddefs.get(fields[c]);
					if (fielddef != null) {
						Object record = r.getResultset(i);
						Object value = ((Map) r.getResultset(i)).get(fields[c]);
						if (CommonFun.isNe(value)) {
							Cell recordCell = recordRow.createCell(c);
							recordCell.setCellType(Cell.CELL_TYPE_BLANK);
							continue;
						}
						int datatype = Cell.CELL_TYPE_STRING;
						Object svalue = "" + (CommonFun.isNe(value) ? "" : value);
						if(!CommonFun.isNe(fielddef.get("dictionary"))){
							HashMap<String, String> dictionary = (HashMap<String, String>) fielddef.get("dictionary");
							if (dictionary != null && dictionary.size() > 0 && value != null) {
								value = dictionary.get(value) == null ? value : dictionary.get(value);
								svalue = "" + (CommonFun.isNe(value) ? "" : value);
							}
						}
						if ("rowno".equals(fields[c])) {
							datatype = Cell.CELL_TYPE_STRING;
							svalue = sheetBodyRowNo.get(sh);
						} else if ("date".equals(fielddef.get("type"))) {
							datatype = Cell.CELL_TYPE_STRING;
							if (value instanceof java.util.Date) {
								svalue = new java.text.SimpleDateFormat("yyyy-MM-dd").format(value);
							}
						} else if ("time".equals(fielddef.get("type"))) {
							datatype = Cell.CELL_TYPE_STRING;
							if (value instanceof java.util.Date) {
								svalue = new java.text.SimpleDateFormat("HH:mm:ss").format(value);
							}
						} else if ("datetime".equals(fielddef.get("type"))) {
							datatype = Cell.CELL_TYPE_STRING;
							if (value instanceof java.util.Date) {
								svalue = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
							}
						} else if ("number".equals(fielddef.get("type")) || "decimal".equals(fielddef.get("type"))
								|| "currency".equals(fielddef.get("type"))) {
							datatype = Cell.CELL_TYPE_NUMERIC;
							svalue = Double.parseDouble(value.toString());
						} else if ("script".equals(fielddef.get("datatype"))) {
							datatype = Cell.CELL_TYPE_STRING;
							try {
								JavaScriptEngine engine = new JavaScriptEngine();
								engine.setParam("_svalue_", svalue, true);
								engine.setParam("record", "", false);
								StringBuffer script = new StringBuffer();
								script.append("record = " + CommonFun.object2Json(record) + ";\r\n");
								script.append("function _script_() {\r\n");
								script.append(fielddef.get("script"));
								script.append("};\r\n");
								script.append("_svalue_ = _script_();");
								engine.excuteString(script.toString());
								svalue = engine.getParamValue("_svalue_");
								if (svalue != null) {
									svalue = CommonFun.removeHtmlTag(svalue.toString());									
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if ("template".equals(fielddef.get("datatype"))) {
							datatype = Cell.CELL_TYPE_STRING;
							try {
								svalue = ELExpression.excuteExpressionD(fielddef.get("template").toString(),
										(Map<String, Object>) record);
								svalue = CommonFun.removeHtmlTag((String) svalue);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						Cell recordCell = recordRow.createCell(c);
						if (datatype == Cell.CELL_TYPE_STRING) {
							svalue = CommonFun.replaceOnlyStr(svalue.toString(), "<", "&lt;");
							svalue = CommonFun.replaceOnlyStr(svalue.toString(), ">", "&gt;");
							recordCell.setCellType(datatype);
							recordCell.setCellValue(svalue.toString());
						} else if (datatype == Cell.CELL_TYPE_NUMERIC) {
							recordCell.setCellType(datatype);
							recordCell.setCellValue((Double) svalue);
						}
					}
				}
			}
			// ???????????????????????????????????????begin
			boolean needSelect = false;
			int rowno = sheetBodyRowNo.get(sh);
			if (r.getCount() > rowno) {
				needSelect = true;
				r.setResultset(new ArrayList<Record>());
				processBean.put("total", r.getCount());
			}
			if (needSelect) {
				processBean.put("currNumBegin", (rowno + 1));
				processBean.put("currNumEnd", (rowno + 1 + DEFAULT_LIMIT));
				System.out.println("===================" + processBean);
				// ??????????????????
				requestParams.put("start", rowno + 1 + "");
				requestParams.put("limit", DEFAULT_LIMIT + "");
				LocalThreadMap.put("start", requestParams.get("start"));
				LocalThreadMap.put("limit", requestParams.get("limit"));
				long t1 = System.currentTimeMillis();
				// ????????????
				Object rtn = executeCallAction((HashMap<String, Object>) ((HashMap<String, Object>) requestParams).clone());
				long t2 = System.currentTimeMillis();
				if (rtn instanceof Result) {
					setXlsxRecord((Result) rtn, sh, fields, fielddefs);
				} else {
					Map<String, Object> map = (Map<String, Object>) rtn;
					Set<String> keys = map.keySet();
					for (String key : keys) {
						Object value = map.get(key);
						if (value instanceof Result) {
							setXlsxRecord((Result) value, sh, fields, fielddefs);
						}
					}
				}
			}
			// ???????????????????????????????????????end
		}

		@SuppressWarnings("unchecked")
		private void createTemplateXLSX(Map<String, Object> t, Element root) throws Throwable {
			List<Element> tables = root.getChildren("table");
			// ??????????????????table ????????????????????? begin
			for (Element table : tables) {
				// ???????????????key
				String key = table.getAttributeValue("result");
				// ???????????????key??????????????????
				Result result = (Result) t.get(key);
				// ???????????????????????????????????????????????????
				if (result == null) {
					continue;
				}
				// ??????sheetName
				String sheetName = table.getAttributeValue("name");
				sheetName = sheetName == null ? key : sheetName;
				Sheet sheet = currWorkbook.createSheet(sheetName);
				if (isdebug) {
					System.out.println("??????Sheet :" + sheetName);
				}
				// ??????????????????body???????????????
				sheetBodyRowNo.put(sheet, 0);
				// ??????????????????????????????
				sheetSheetRowNum.put(sheet, 0);
				// ?????????????????????????????????
				sheetUsedResultName.put(key, sheet);
				Element details = table.getChild("tbody");
				Element thead = table.getChild("thead");
				// ?????????????????????begin
				setTableDefaultStyle(table, sheet);
				// ?????????????????????end
				int columnCount = getColumnCount(details, thead);
				// ??????title end
				List<List<Integer>> headindexs = getColumnIndexs(thead, "th");
				List<List<Integer>> recordindexs = getColumnIndexs(details, "td");
				List<List<Integer>> tableindexs = thead != null ? headindexs : recordindexs;

				createTemplTableTitle(sheet, columnCount, table);
				// ??????thead begin
				createTemplTableHead(thead, sheet, tableindexs, t);
				// ??????thead end
			}
			// ??????body
			for (Element table : tables) {
				String key = table.getAttributeValue("result");
				// ???????????????key??????????????????
				Result result = (Result) t.remove(key);
				// ???????????????????????????????????????????????????
				if (result == null) {
					continue;
				}
				Sheet sheet = sheetUsedResultName.get(key);
				// ??????tbody begin
				Element details = table.getChild("tbody");
				List<List<Integer>> recordindexs = getColumnIndexs(details, "td");
				createTemplTableBody(t, result, sheet, details, recordindexs);
				// ??????tbody end
			}
			// ??????foot
			for (Element table : tables) {
				String key = table.getAttributeValue("result");
				Sheet sheet = sheetUsedResultName.get(key);
				Element tfoot = table.getChild("tfoot");
				List<List<Integer>> footindexs = getColumnIndexs(tfoot, "td");
				// ??????tfoot begin
				createTemplTableFoot(t, sheet, tfoot, footindexs);
				// ??????tfoot end
			}
		}

		private void setTableDefaultStyle(Element table, Sheet sheet) {
			// ??????????????????
			if (!CommonFun.isNe(table.getAttributeValue("columnwidth"))) {
				sheet.setDefaultColumnWidth((int) (Integer.parseInt(table.getAttributeValue("columnwidth")) / 6));
			}
			// ????????????
			if (!CommonFun.isNe(table.getAttributeValue("rowheight"))) {
				sheet.setDefaultRowHeightInPoints(Integer.parseInt(table.getAttributeValue("rowheight")));
			}
			// ????????????font
			String currFontName = CommonFun.isNe(table.getAttributeValue("font")) ? defaultFontName : table.getAttributeValue("font");
			String currFontSize = table.getAttributeValue("font-size");
			Font font = currWorkbook.getFontAt((short) 0);
			if (!CommonFun.isNe(currFontSize)) {
				font.setFontHeightInPoints(Short.parseShort(currFontSize));
			}
			font.setFontName(currFontName);
		}

		/**
		 * 
		 * ?????????titl?????????.
		 * <dl>
		 * <dt><b>???????????????</b></dt>
		 * <dd>???????????????????????????title?????????????????????????????????title???????????????</dd>
		 * </dl>
		 * 
		 * @param sheet
		 *            ?????????
		 * @param columnCount
		 *            ?????????
		 * @param table
		 *            xmltable??????
		 *            <dt><b>???????????????</b></dt>
		 */
		private void createTemplTableTitle(Sheet sheet, int columnCount, Element table) {
			Element titleElement = table.getChild("title");
			CellStyle titleStyle = createStyle(titleElement);
			Row titleRow = getOrCreateRow(sheet, rownumAddOne(sheet));
			titleRow.setHeightInPoints(Float.parseFloat(titleElement.getAttributeValue("height", sheet.getDefaultRowHeightInPoints() + "")));
			Cell titleCell = titleRow.createCell(0);
			String title = titleElement.getText();
			title = CommonFun.replaceOnlyStr(title, "<", "&lt;");
			title = CommonFun.replaceOnlyStr(title, ">", "&gt;");
			titleCell.setCellStyle(titleStyle);
			titleCell.setCellValue(title);
			// ?????????????????????
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount - 1));
			for (int f = 1; f < columnCount - 1; f++) {
				Cell tempCell = titleRow.createCell(1 + f);
				tempCell.setCellStyle(titleStyle);
			}
			if (isdebug) {
				System.out.println("??????Sheet :" + sheet.getSheetName() + " ???Title :" + title);
			}
		}

		private int rownumAddOne(Sheet sheet) {
			int rownum = sheetSheetRowNum.get(sheet);
			sheetSheetRowNum.put(sheet, rownum + 1);
			return rownum;
		}

		private Row getOrCreateRow(Sheet sheet, int rownum) {
			Row tepmRow = sheet.getRow(rownum);
			if (tepmRow == null) {
				tepmRow = sheet.createRow(rownum);
			}
			return tepmRow;
		}

		@SuppressWarnings("unchecked")
		private void createTemplTableFoot(Map<String, Object> t, Sheet sheet, Element tfoot, List<List<Integer>> footindexs) throws Exception {
			if (!CommonFun.isNe(tfoot)) {
				// ??????tfoot?????????tr??????
				List<Element> rows = tfoot.getChildren("tr");
				for (int r = 0; r < rows.size(); r++) {
					Element row = rows.get(r);
					if (!checkAvailable(row.getAttributeValue("available"), t))
						continue;
					Row footRow = getOrCreateRow(sheet, rownumAddOne(sheet));
					// ????????????
					setRowHeight(sheet, row, footRow);
					List<Element> cols = row.getChildren("td");
					int cellNo = 0;
					for (int c = 0; c < cols.size(); c++) {
						Element col = cols.get(c);
						if (!checkAvailable(col.getAttributeValue("available"), t))
							continue;
						Object value = col.getText();
						value = CommonFun.isNe(value) ? col.getAttributeValue("value") : value;
						List<Integer> rowspanList = footindexs.get(r);
						cellNo = createCell(col, sheet, footRow, value, t, rowspanList, cellNo);
					}
				}
			}
		}

		@SuppressWarnings("unchecked")
		private void createTemplTableBody(Map<String, Object> t, Result result, Sheet sheet, Element details,
				List<List<Integer>> recordindexs) throws Exception {
			int resultRowCount = result.getResultset().size();
			if (isdebug) {
				System.out.println("??????Sheet :" + sheet.getSheetName() + " ???body." + sheetBodyRowNo.get(sheet));
			}
			for (int i = 0; i < resultRowCount; i++) {
				sheetBodyRowNo.put(sheet, sheetBodyRowNo.get(sheet) + 1);
				// ?????????????????????Map
				Map<String, Object> params = new HashMap<String, Object>();
				params.putAll(t);
				params.putAll(result.getResultset(i));
				// ??????????????????
				if (!checkAvailable(details.getAttributeValue("available"), params))
					continue;
				List<Element> rows = details.getChildren("tr");
				for (int r = 0; r < rows.size(); r++) {
					Element row = rows.get(r);
					if (!checkAvailable(row.getAttributeValue("available"), params))
						continue;
					Row tbodyRow = getOrCreateRow(sheet, rownumAddOne(sheet));
					// ????????????
					setRowHeight(sheet, row, tbodyRow);
					List<Element> cols = row.getChildren("td");
					int cellNo = 0;
					for (int c = 0; c < cols.size(); c++) {
						Element col = cols.get(c);
						if (!checkAvailable(col.getAttributeValue("available"), params))
							continue;
						Object value = col.getAttributeValue("value");
						List<Integer> rowspanList = recordindexs.get(r);
						cellNo = createCell(col, sheet, tbodyRow, value, params, rowspanList, cellNo);
					}
				}
			}
			if (isdebug) {
				System.out.println("??????Sheet :" + sheet.getSheetName() + " ???body." + sheetBodyRowNo.get(sheet));
			}
			// ???????????????????????????????????????begin
			boolean needSelect = false;
			int rowno = sheetBodyRowNo.get(sheet);
			if (result.getCount() > rowno) {
				needSelect = true;
				result.setResultset(new ArrayList<Record>());
				processBean.put("total", result.getCount());
			}
			// ?????????????????????????????????????????????????????????????????????????????????
			if (result.getPerpage() > 0 && result.getPerpage() != DEFAULT_LIMIT) {
				needSelect = false;
			}
			result = null;
			if (needSelect) {
				processBean.put("currNumBegin", rowno + 1);
				processBean.put("currNumEnd", rowno + 1 + DEFAULT_LIMIT);
				System.out.println("===================" + processBean);
				// ??????????????????
				requestParams.put("start", rowno + 1 + "");
				requestParams.put("limit", DEFAULT_LIMIT + "");
				LocalThreadMap.put("start", requestParams.get("start"));
				LocalThreadMap.put("limit", requestParams.get("limit"));
				Set<String> keys = sheetUsedResultName.keySet();
				for (String key : keys) {
					if (sheetUsedResultName.get(key) == sheet) {
						requestParams.put("$needResultName", key);
					}
				}
				// ????????????
				long t1 = System.currentTimeMillis();
				Map<String, Object> map = (Map<String, Object>) executeCallAction(
						(HashMap<String, Object>) ((HashMap<String, Object>) requestParams).clone());
				long t2 = System.currentTimeMillis();
				// $return????????????map
				keys = map.keySet();
				for (String key : keys) {
					Object value = map.get(key);
					if (value instanceof Result) {
						Sheet tmpSheet = sheetUsedResultName.get(key);
						if (tmpSheet == sheet) {
							createTemplTableBody(t, (Result) value, sheet, details, recordindexs);
						}
					}
				}
			}
			// ???????????????????????????????????????end
		}

		private void setRowHeight(Sheet sheet, Element row, Row tbodyRow) {
			if (!CommonFun.isNe(row.getAttributeValue("autoheight"))) {
				tbodyRow.setHeight((short) -1);
			}
			if (!CommonFun.isNe(row.getAttributeValue("height"))) {
				tbodyRow.setHeightInPoints(Integer.parseInt(row.getAttributeValue("height")));
			} else {
				tbodyRow.setHeightInPoints(sheet.getDefaultRowHeightInPoints());
			}
		}

		private void setCellTypeAndValueByElement(Element col, Object value, Cell colsCell, Map<String, Object> t, int currRowNo) throws Exception {
			value = "" + (CommonFun.isNe(value) ? "" : value);
			value = CommonFun.replaceOnlyStr(value.toString(), "<", "&lt;");
			value = CommonFun.replaceOnlyStr(value.toString(), ">", "&gt;");
			if ("${rowno}".equalsIgnoreCase(value.toString())) {
				value = currRowNo;
			}
			value = ELExpression.excuteExpression(value.toString(), t);
			String dictionary = col.getAttributeValue("dictionary");
			if (dictionary != null && value != null) {
				value = Dictionary.getName(dictionary, value.toString());
			}
			if (CommonFun.isNe(value)) {
				colsCell.setCellType(Cell.CELL_TYPE_BLANK);
			} else {
				if (!CommonFun.isNe(col.getAttributeValue("datatype"))) {
					if ("double".equalsIgnoreCase(col.getAttributeValue("datatype")) || "float".equalsIgnoreCase(col.getAttributeValue("datatype"))
							|| "long".equalsIgnoreCase(col.getAttributeValue("datatype")) || "int".equalsIgnoreCase(col.getAttributeValue("datatype"))
							|| "short".equalsIgnoreCase(col.getAttributeValue("datatype"))
							|| "currency".equalsIgnoreCase(col.getAttributeValue("datatype"))
							|| "percent".equalsIgnoreCase(col.getAttributeValue("datatype"))) {
						// colsCell.setCellType(Cell.CELL_TYPE_NUMERIC);
						colsCell.setCellType(Cell.CELL_TYPE_STRING);
						colsCell.setCellValue(Double.parseDouble(value.toString()));
					} else if ("date".equalsIgnoreCase(col.getAttributeValue("datatype"))) {
						colsCell.setCellType(Cell.CELL_TYPE_STRING);
						if (value instanceof java.util.Date) {
							colsCell.setCellValue(new java.text.SimpleDateFormat("yyyy-MM-dd").format(value));
						} else {
							colsCell.setCellValue((String) value);
						}
					} else if ("time".equalsIgnoreCase(col.getAttributeValue("datatype"))) {
						colsCell.setCellType(Cell.CELL_TYPE_STRING);
						if (value instanceof java.util.Date) {
							colsCell.setCellValue(new java.text.SimpleDateFormat("hh:mm:ss").format(value));
						} else {
							colsCell.setCellValue((String) value);
						}
					} else if ("datetime".equalsIgnoreCase(col.getAttributeValue("datatype"))) {
						colsCell.setCellType(Cell.CELL_TYPE_STRING);
						if (value instanceof java.util.Date) {
							colsCell.setCellValue(new java.text.SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(value));
						} else {
							colsCell.setCellValue((String) value);
						}
					}
				} else {
					colsCell.setCellType(Cell.CELL_TYPE_STRING);
					colsCell.setCellValue(value + "");
				}
			}
		}

		/**
		 * ??????excel???????????????
		 * <dl>
		 * <dt><b>???????????????</b></dt>
		 * <dd>??????row,???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????</dd>
		 * </dl>
		 * <dt><b>???????????????</b></dt>
		 * 
		 * @throws Exception
		 */

		@SuppressWarnings("unchecked")
		private void createTemplTableHead(Element thead, Sheet sheet, List<List<Integer>> tableindexs, Map<String, Object> t) throws Exception {
			if (!CommonFun.isNe(thead)) {
				// ??????thead?????????tr??????
				List<Element> rows = thead.getChildren("tr");
				for (int r = 0; r < rows.size(); r++) {
					Element row = rows.get(r);
					Row theadRow = getOrCreateRow(sheet, rownumAddOne(sheet));
					// ????????????
					setRowHeight(sheet, row, theadRow);
					// ??????tr?????????th??????
					List<Element> cols = row.getChildren("th");
					int cellNo = 0;
					for (int i = 0; i < cols.size(); i++) {
						Element col = cols.get(i);
						if (!checkAvailable(col.getAttributeValue("available"), t))
							continue;
						Object value = col.getText();
						List<Integer> rowspanList = tableindexs.get(r);
						cellNo = createCell(col, sheet, theadRow, value, t, rowspanList, cellNo);
					}
				}
			}
			if (isdebug) {
				System.out.println("??????Sheet :" + sheet.getSheetName() + " ???head.");
			}
		}

		/**
		 * ???????????????.
		 * <dl>
		 * <dt><b>???????????????</b></dt>
		 * <dd>?????????????????????????????????????????????????????????</dd>
		 * </dl>
		 * <dt><b>???????????????</b></dt>
		 * 
		 * @param col
		 * @param t
		 * @param tableindexs
		 * @param cellNo
		 * @param r
		 * @param sheet
		 * @param theadRow
		 * @param value
		 * @throws Exception
		 */
		private int createCell(Element col, Sheet sheet, Row theadRow, Object value, Map<String, Object> t, List<Integer> rowspanList, int cellNo)
				throws Exception {
			// ?????????????????????????????????
			cellNo = getCurrCellIndex(rowspanList, cellNo);
			// ??????head??????
			CellStyle currCellStyle = createBodyStyle(col, cellNo);
			// ??????clospan???rowspan
			int colspan = setColspanAndRowSpan(sheet, theadRow, cellNo, col, currCellStyle);

			// ????????????
			String colWidth = col.getAttributeValue("width");
			if (!CommonFun.isNe(colWidth)) {
				int colWidthInt = Integer.parseInt(colWidth);
				if (colspan < 2) {
					// ???????????????????????????????????????????????????????????????????????????
					if (sheet.getColumnWidth(cellNo) < 32 * colWidthInt || sheet.getDefaultColumnWidth() * 256 == sheet.getColumnWidth(cellNo)) {
						sheet.setColumnWidth(cellNo, (int) (32 * colWidthInt));
					}
				} else {
					for (int i = cellNo; i < cellNo + colspan; i++) {
						colWidthInt -= sheet.getColumnWidth(i) / 32;
					}
					if (colWidthInt > 0) {
						sheet.setColumnWidth(colspan + cellNo - 1, 32 * colWidthInt);
					}
				}
			}
			// ???????????????
			Cell headCell = theadRow.createCell(cellNo++);
			// ??????colspan???????????????
			cellNo += (colspan - 1);
			// ???????????????????????????
			headCell.setCellStyle(currCellStyle);
			int currRowNo = sheetBodyRowNo.get(sheet);
			// ???????????????????????????
			setCellTypeAndValueByElement(col, value, headCell, t, currRowNo);
			return cellNo;
		}

		private int getCurrCellIndex(List<Integer> rowspanList, int cellNo) {
			for (int k = cellNo; k < rowspanList.size(); k++) {
				if (rowspanList.get(k) > 0) {
					cellNo = k;
					break;
				}
			}
			return cellNo;
		}

		private int setColspanAndRowSpan(Sheet sheet, Row theadRow, int cellNo, Element col, CellStyle currCellStyle) {
			int colspan = Integer.parseInt(col.getAttributeValue("colspan", "1"));
			int rowspan = Integer.parseInt(col.getAttributeValue("rowspan", "1"));
			int rownum = sheetSheetRowNum.get(sheet);
			if (rowspan > 1) {
				for (int f = 1; f < rowspan; f++) {
					Row mergedRow = getOrCreateRow(sheet, sheetSheetRowNum.get(sheet) + f - 1);
					Cell blankTemp = mergedRow.createCell(cellNo);
					blankTemp.setCellStyle(currCellStyle);
					blankTemp.setCellType(Cell.CELL_TYPE_BLANK);
				}
				sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum + rowspan - 2, cellNo, cellNo));
			}
			if (colspan > 1) {
				sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, cellNo, cellNo + colspan - 1));
				for (int f = 1; f < colspan; f++) {
					Cell blankTemp = theadRow.createCell(cellNo + f);
					blankTemp.setCellStyle(currCellStyle);
					blankTemp.setCellType(Cell.CELL_TYPE_BLANK);
				}
			}
			return colspan;
		}

		/**
		 * 
		 * ?????????????????????.
		 * <dl>
		 * <dt><b>???????????????</b></dt>
		 * <dd>??????head??????????????????????????????head?????????????????????tbode???td??????</dd>
		 * </dl>
		 * 
		 * @param details
		 *            tbody??????Element??????
		 * @param thead
		 *            thead??????Element??????
		 * @return
		 * 		<dt><b>???????????????</b></dt>
		 */
		@SuppressWarnings("unchecked")
		private int getColumnCount(Element details, Element thead) {
			int columnCount = 0;
			if (!CommonFun.isNe(thead)) {
				List<Element> trows = thead.getChildren("tr");
				if (!CommonFun.isNe(trows)) {
					Element row = trows.get(0);
					List<Element> cols = row.getChildren("th");
					for (int i = 0; i < cols.size(); i++) {
						Element col = cols.get(i);
						int colspan = Integer.parseInt(col.getAttributeValue("colspan", "1"));
						columnCount += colspan;
					}
				}
			}
			if (columnCount == 0) {
				if (!CommonFun.isNe(details)) {
					List<Element> trows = details.getChildren("tr");
					if (!CommonFun.isNe(trows)) {
						Element row = trows.get(0);
						List<Element> cols = row.getChildren("td");
						for (int i = 0; i < cols.size(); i++) {
							Element col = cols.get(i);
							int colspan = Integer.parseInt(col.getAttributeValue("colspan", "1"));
							columnCount += colspan;
						}
					}
				}
			}
			return columnCount;
		}

		/**
		 * 
		 * ??????excel??????,??????????????????.
		 * <dl>
		 * <dt><b>???????????????</b></dt>
		 * <dd>??????????????????????????????????????????????????????????????????excel?????????????????????????????????????????????????????????????????????????????????????????????????????????</dd>
		 * </dl>
		 * 
		 * @param module
		 *            ????????????
		 * @param t
		 *            ??????Map???????????????????????????
		 * @throws Throwable
		 *             ????????????????????????
		 *             <dt><b>???????????????</b></dt>
		 */
		private void exportExcel(File module, Map<String, Object> t) throws Throwable {
			String url = null;
			OutputStream out = null;
			try {
				Document document = CommonFun.loadXMLFile(module);
				Element root = document.getRootElement();
				String fileName = root.getAttributeValue("name") == null ? module.getName() : root.getAttributeValue("name");
				String fileId = CommonFun.getSSID();
				File file = ResourceManager.getTempFilePath(fileId);
				file.mkdirs();
				file = new File(file, fileName + ".xlsx");
				System.out.println("=======================???????????? filepath" + file.getAbsoluteFile());
				url = "rm/td/" + fileId + "/" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx";
				System.out.println("=======================??????URL url" + url);
				out = new BufferedOutputStream(new FileOutputStream(file));
				currWorkbook = new SXSSFWorkbook(100); // keep 50 rows in memory, exceeding rows will be flushed to disk
				createTemplateXLSX(t, root);
				currWorkbook.write(out);
				processBean.put("downloadUrl", url);
			} catch (Exception ex) {
				throw new ServletException(ex);
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
		/**
		 * @param styleID
		 * @param table
		 * @return
		 */
		public CellStyle createStyle(Element element) {
			@SuppressWarnings("unchecked")
			List<String> attrList = element.getAttributes();
			if (attrList.size() > 0) {
				CellStyle style = currWorkbook.createCellStyle();
				Font cellFont = currWorkbook.createFont();
				style.setFont(cellFont);
				DataFormat dataFormat = currWorkbook.createDataFormat();
				setAlignment(style, element);
				setBorder(style, element);
				setFont(element, cellFont);
				setDataType(element, style, dataFormat);
				return style;
			}
			return null;
		}
		public CellStyle createBodyStyle(Element element, int cellNo) {
			@SuppressWarnings("unchecked")
			List<String> attrList = element.getAttributes();
			if (attrList.size() > 0) {
				if (cacheCellStyle.containsKey(cellNo)) {
					return cacheCellStyle.get(cellNo);
				} else {
					CellStyle style = currWorkbook.createCellStyle();
					Font cellFont = currWorkbook.createFont();
					style.setFont(cellFont);
					DataFormat dataFormat = currWorkbook.createDataFormat();
					setAlignment(style, element);
					setBorder(style, element);
					setFont(element, cellFont);
					setDataType(element, style, dataFormat);
					cacheCellStyle.put(cellNo, style);
				}
				return cacheCellStyle.get(cellNo);
			}
			return null;
		}
		/**
		 * 
		 * ?????????????????????????????????or????????????.
		 * <dl>
		 * <dt><b>???????????????</b></dt>
		 * <dd>?????????????????? CENTER,LEFT,RIGHT ???????????? CENTER,MIDDLE,TOP,BOTTOM</dd>
		 * </dl>
		 * 
		 * @param style
		 * @param element
		 *            <dt><b>???????????????</b></dt>
		 */
		private void setAlignment(CellStyle style, Element element) {
			String align = element.getAttributeValue("align");
			String valign = element.getAttributeValue("valign");
			if (!CommonFun.isNe(align)) {
				if ("CENTER".equalsIgnoreCase(align)) {
					style.setAlignment(CellStyle.ALIGN_CENTER);
				} else if ("LEFT".equalsIgnoreCase(align)) {
					style.setAlignment(CellStyle.ALIGN_LEFT);
				} else if ("RIGHT".equalsIgnoreCase(align)) {
					style.setAlignment(CellStyle.ALIGN_RIGHT);
				}
			}
			if (!CommonFun.isNe(valign)) {
				if ("CENTER".equalsIgnoreCase(valign)) {
					style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				} else if ("MIDDLE".equalsIgnoreCase(valign)) {
					style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				} else if ("TOP".equalsIgnoreCase(valign)) {
					style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
				} else if ("BOTTOM".equalsIgnoreCase(valign)) {
					style.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
				}
			}
		}

		private void setBorder(CellStyle style, Element element) {
			if (!CommonFun.isNe(element.getAttributeValue("border"))) {
				String[] borders = element.getAttributeValue("border").split(",");
				style.setBorderTop(Short.parseShort(borders[0]));
				style.setBorderRight(Short.parseShort(borders[1]));
				style.setBorderBottom(Short.parseShort(borders[2]));
				style.setBorderLeft(Short.parseShort(borders[3]));
				String bordertype = element.getAttributeValue("border-type", "Continuous");
				if ("Continuous".equalsIgnoreCase(bordertype)) {
					setBorderType(style, CellStyle.BORDER_THIN);
				} else if ("Double".equalsIgnoreCase(bordertype)) {
					setBorderType(style, CellStyle.BORDER_DOUBLE);
				} else if ("Dash".equalsIgnoreCase(bordertype)) {
					setBorderType(style, CellStyle.BORDER_DASHED);
				} else if ("DASH_DOT".equalsIgnoreCase(bordertype)) {
					setBorderType(style, CellStyle.BORDER_DASH_DOT);
				} else if ("DASH_DOT_DOT".equalsIgnoreCase(bordertype)) {
					setBorderType(style, CellStyle.BORDER_DASH_DOT_DOT);
				} else if ("MEDIUM".equalsIgnoreCase(bordertype)) {
					setBorderType(style, CellStyle.BORDER_MEDIUM);
				}
			}
		}
		private void setBorderType(CellStyle style, Short type) {
			style.setBorderTop(type);
			style.setBorderRight(type);
			style.setBorderBottom(type);
			style.setBorderLeft(type);
		}
		/**
		 * 
		 * ?????????????????????.
		 * <dl>
		 * <dt><b>???????????????</b></dt>
		 * <dd>POI?????????????????????:
		 * 
		 * <pre>
		 * List&lt;String&gt; m = new ArrayList&lt;String&gt;();
		 * putFormat(m, 0, &quot;General&quot;);
		 * putFormat(m, 1, &quot;0&quot;);
		 * putFormat(m, 2, &quot;0.00&quot;);
		 * putFormat(m, 3, &quot;#,##0&quot;);
		 * putFormat(m, 4, &quot;#,##0.00&quot;);
		 * putFormat(m, 5, &quot;\&quot;$\&quot;#,##0_);(\&quot;$\&quot;#,##0)&quot;);
		 * putFormat(m, 6, &quot;\&quot;$\&quot;#,##0_);[Red](\&quot;$\&quot;#,##0)&quot;);
		 * putFormat(m, 7, &quot;\&quot;$\&quot;#,##0.00_);(\&quot;$\&quot;#,##0.00)&quot;);
		 * putFormat(m, 8, &quot;\&quot;$\&quot;#,##0.00_);[Red](\&quot;$\&quot;#,##0.00)&quot;);
		 * putFormat(m, 9, &quot;0%&quot;);
		 * putFormat(m, 0xa, &quot;0.00%&quot;);
		 * putFormat(m, 0xb, &quot;0.00E+00&quot;);
		 * putFormat(m, 0xc, &quot;# ?/?&quot;);
		 * putFormat(m, 0xd, &quot;# ??/??&quot;);
		 * putFormat(m, 0xe, &quot;m/d/yy&quot;);
		 * putFormat(m, 0xf, &quot;d-mmm-yy&quot;);
		 * putFormat(m, 0x10, &quot;d-mmm&quot;);
		 * putFormat(m, 0x11, &quot;mmm-yy&quot;);
		 * putFormat(m, 0x12, &quot;h:mm AM/PM&quot;);
		 * putFormat(m, 0x13, &quot;h:mm:ss AM/PM&quot;);
		 * putFormat(m, 0x14, &quot;h:mm&quot;);
		 * putFormat(m, 0x15, &quot;h:mm:ss&quot;);
		 * putFormat(m, 0x16, &quot;m/d/yy h:mm&quot;);
		 * </pre>
		 * 
		 * </dd>
		 * </dl>
		 * 
		 * @param element
		 * @param style
		 * @param dataFormat
		 *            <dt><b>???????????????</b></dt>
		 */
		private void setDataType(Element element, CellStyle style, DataFormat dataFormat) {
			if (!CommonFun.isNe(element.getAttributeValue("datatype"))) {
				if ("double".equalsIgnoreCase(element.getAttributeValue("datatype"))
						|| "float".equalsIgnoreCase(element.getAttributeValue("datatype"))) {
					style.setDataFormat(dataFormat.getFormat("#,##0.00"));
				} else if ("long".equalsIgnoreCase(element.getAttributeValue("datatype"))
						|| "int".equalsIgnoreCase(element.getAttributeValue("datatype"))
						|| "short".equalsIgnoreCase(element.getAttributeValue("datatype"))) {
					style.setDataFormat(dataFormat.getFormat("#,##0"));
				} else if ("currency".equalsIgnoreCase(element.getAttributeValue("datatype"))) {
					style.setDataFormat(dataFormat.getFormat("\"??\"#,##0.00_);(\"??\"#,##0.00)"));
				} else if ("date".equalsIgnoreCase(element.getAttributeValue("datatype"))) {
					style.setDataFormat(dataFormat.getFormat("yyyy-mm-dd"));
				} else if ("time".equalsIgnoreCase(element.getAttributeValue("datatype"))) {
					style.setDataFormat(dataFormat.getFormat("hh:mm:ss"));
				} else if ("datetime".equalsIgnoreCase(element.getAttributeValue("datatype"))) {
					style.setDataFormat(dataFormat.getFormat("yyyy-mm-dd hh:mm:ss"));
				} else if ("percent".equalsIgnoreCase(element.getAttributeValue("datatype"))) {
					style.setDataFormat(dataFormat.getFormat("0.00%"));
				}
			}
		}
		private void setFont(Element element, Font cellFont) {
			if (!CommonFun.isNe(element.getAttributeValue("font"))) {
				cellFont.setFontName(element.getAttributeValue("font"));
			} else {
				cellFont.setFontName(currWorkbook.getFontAt((short) 0).getFontName());
			}
			if (!CommonFun.isNe(element.getAttributeValue("font-size"))) {
				cellFont.setFontHeightInPoints(Short.parseShort(element.getAttributeValue("font-size")));
			} else {
				cellFont.setFontHeightInPoints(currWorkbook.getFontAt((short) 0).getFontHeightInPoints());
			}
			if (!CommonFun.isNe(element.getAttributeValue("font-bold"))) {
				cellFont.setBoldweight("1".equals(element.getAttributeValue("font-bold")) ? Font.BOLDWEIGHT_BOLD : Font.BOLDWEIGHT_NORMAL);
			}
			if (!CommonFun.isNe(element.getAttributeValue("font-italic"))) {
				cellFont.setItalic("1".equals(element.getAttributeValue("font-italic")) ? true : false);
			}
			if (!CommonFun.isNe(element.getAttributeValue("color"))) {
				cellFont.setColor(IndexedColors.valueOf(element.getAttributeValue("color")).getIndex());
			}
		}

		@SuppressWarnings("unchecked")
		private List<List<Integer>> getColumnIndexs(Element element, String cellname) {
			List<List<Integer>> indexs = null;
			if (element == null)
				return indexs;
			List<Element> rows, cols;
			indexs = new ArrayList<List<Integer>>();
			rows = element.getChildren("tr");
			for (int r = 0; r < rows.size(); r++) {
				Element row = rows.get(r);
				cols = row.getChildren(cellname);

				indexs.add(new ArrayList<Integer>());
				for (int i = 0; i < cols.size(); i++) {
					Element col = cols.get(i);
					int colspan = Integer.parseInt(col.getAttributeValue("colspan", "1"));
					int rowspan = Integer.parseInt(col.getAttributeValue("rowspan", "1"));
					// ??????????????? start = 0
					// indexs.size==1,index.get(r).size()==0
					int start = indexs.get(r).size();
					int index = 0;
					for (int ri = indexs.size() - 2; ri >= 0; ri--) {
						boolean next = false;
						for (int ci = start; ci < indexs.get(ri).size(); ci++) {
							if (indexs.get(ri).get(ci) == 0) {
								next = true;
								break;
							} else if (indexs.get(ri).get(ci) == 1) {
								break;
							} else {
								index++;
							}
						}
						if (!next) {
							break;
						}
					}

					for (int z = 0; z < index; z++) {
						indexs.get(r).add(0);
					}

					index = indexs.get(r).size() + 1;

					for (int z = 0; z < colspan; z++) {
						indexs.get(r).add(rowspan);
					}

					col.setAttribute("index", "" + index);

				}
				if (r > 0) {
					for (int ri = indexs.get(r).size(); ri < indexs.get(r - 1).size(); ri++) {
						indexs.get(r).add(0);
					}
				}
			}
			return indexs;
		}

	}
}
