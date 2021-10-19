package cn.crtech.cooperop.bus.engine.window;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.Graphics2DExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleGraphics2DReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import cn.crtech.cooperop.application.service.BillService;
import cn.crtech.cooperop.application.service.CommonService;
import cn.crtech.cooperop.bus.engine.Engine;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.ReportDataSource;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.rm.core.service.ResourceIndexService;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.ELExpression;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.JavaScriptEngine;

public class PdfWindow implements Window {

	@SuppressWarnings("unchecked")
	@Override
	public void excute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		File template = null;//模版
		Object t = null;//数据
		String module = "";//产品
		if(!CommonFun.isNe(req.getParameter("selfprint"))){
			Engine.executeCallAction(req, resp);
			t = req.getAttribute("$return");
			Map<String, Object> m = (Map<String, Object>) t;
			template = (File) m.get("jasper_template_"); 
			module = (String) m.get("module_"); 
		}else{
			String cp = req.getContextPath() == null ? "" : req.getContextPath();
			String sp = req.getServletPath();
			String res = req.getRequestURI().substring((cp + sp).length() + 1);
			String pageid = res.substring(0, res.lastIndexOf(".")).replace('/', '.');
			module = pageid.substring(0, pageid.indexOf('.'));
			pageid = pageid.substring(pageid.indexOf('.') + 1);
			String uri = null;
			String custom_id = GlobalVar.getSystemProperty("custom.id");
			if(!CommonFun.isNe(custom_id)){
				uri = GlobalVar.getSystemProperty("view.jasper.custom.rule");
				uri = uri.replace("@[CUSTOM]", custom_id);
				uri = uri.replace("@[MODULE]", module);
				uri = uri.replace("@[PAGE]", pageid.replace('.', '/'));
				if(!new File(GlobalVar.getWorkPath() + uri).exists()){
					uri = null;
				}
			}
			if(uri == null){
				uri = GlobalVar.getSystemProperty("view.jasper.access.rule");
				uri = uri.replace("@[MODULE]", module);
				uri = uri.replace("@[PAGE]", pageid.replace('.', '/'));
			}
			
			String $_jasper = req.getParameter("$_jasper");
			if (!CommonFun.isNe($_jasper)) {
				if ($_jasper.startsWith("\\") || $_jasper.startsWith("/")) {
					$_jasper = $_jasper.substring(1);
				}
				if ($_jasper.indexOf("\\") > 0) {
					$_jasper.replace("\\", "/");
				}
				uri = uri.substring(0, uri.indexOf("/jasper/") + "/jasper/".length());
				template = new File(req.getSession().getServletContext().getRealPath(uri + $_jasper));
			} else {
				template = new File(req.getSession().getServletContext().getRealPath(uri));
			}
			
			Engine.executeCallAction(req, resp);
			t = req.getAttribute("$return");
			if (t == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "no report data.");
				return;
			}
		}

		String type = req.getParameter("$_type");
		if (CommonFun.isNe(type)) {
			type = req.getParameter("type");
		}
		if (CommonFun.isNe(type)) {
			type = "pdf";
		}
		String printer = req.getParameter("printer");
		boolean print = "true".equalsIgnoreCase(req.getParameter("print"));
		boolean attachment = "true".equalsIgnoreCase(req.getParameter("attachment"));
		String fileName = req.getParameter("filename");
		if (CommonFun.isNe(fileName)) {
			fileName = req.getParameter("$exportname");
			if (CommonFun.isNe(fileName)) {
				fileName = "noname";
			}
		}
		resp.setHeader("Content-Location", fileName + "." + type);
		resp.setHeader("Content-Title", fileName + "." + type);
		resp.setHeader("Content-Name", fileName + "." + type);
		resp.setHeader("Content-disposition", (attachment ? "attachment;" : "") + "fileName=" + new String(fileName.getBytes(), "iso-8859-1") + "." + type);
		resp.setCharacterEncoding("UTF-8");

		if (!template.exists()) {
			if (t instanceof Result) {
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("fileName", fileName);
				temp.put(fileName, t);
				temp.put(fileName + ".name", fileName);
				temp.put(fileName + ".title", fileName);
				temp.put(fileName + ".fields", req.getParameter("fields").split(","));
				temp.put(fileName + ".fielddefs", CommonFun.json2Object(req.getParameter("fielddefs"), HashMap.class));
				printPdf(temp, resp);
			} else {
				Map<String, Object> tm = (Map<String, Object>) t;
				printPdf(tm, resp);
			}
		} else {
			Map<String, Object> tm = null;
			if (t instanceof Result) {
				tm = new HashMap<String, Object>();
				tm.put("detail", t);
			} else {
				tm = (Map<String, Object>) t;
			}
			try {
				tm.put("SUBREPORT_DIR", template.getParent() + "/");

				// 没法https
				// String rm_url = req.getHeader("host");
				// if (!CommonFun.isNe(req.getHeader("Scheme"))) {
				// rm_url = request.getHeader("Scheme") + "://" + rm_url;
				// } else {
				// rm_url = req.getScheme() + "://" + rm_url;
				// }
				// rm_url += req.getContextPath() == null ? "" : ("/" +
				// req.getContextPath());
				// rm_url += "/rm/s/" + module + "/";
				// tm.put("RESOURCE_URL", template.getParent() + "/");

				ReportDataSource detail = null;
				for (Iterator<String> e = tm.keySet().iterator(); e.hasNext();) {
					String key = e.next();
					if (tm.get(key) != null) {
						if (tm.get(key) instanceof Result) {
							tm.put(key, new ReportDataSource((Result) tm.get(key), module));
							if (detail == null) {
								detail = (ReportDataSource) tm.get(key);
							}
						}
					}
				}

				// Load编译好的模板
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(template);
				JRParameter[] params = jasperReport.getParameters();
				if (params != null) {
					for (JRParameter p : params) {
						if (p.getValueClassName().equals("java.io.InputStream")) {
							Object file_id = tm.get(p.getName());
							if (!CommonFun.isNe(file_id) && file_id instanceof String) {
								Record record = ResourceManager.getResource(module, (String) file_id);
								if (record != null) {
									File f = ResourceManager.getFile(false, record);
									if (f.exists()) {
										tm.put(p.getName(), new FileInputStream(f));
										continue;
									}
								}
								tm.put(p.getName(), null);
							}
						}
					}
				}
				// 进行数据填充
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, tm, detail);
				if (!CommonFun.isNe(type)) {
					Method m = this.getClass().getMethod(CommonFun.capitalize(type.toLowerCase()) + "Export", JasperPrint.class, HttpServletRequest.class, HttpServletResponse.class);
					m.invoke(this, jasperPrint, req, resp);
				} else {
					PdfExport(jasperPrint, req, resp);
				}

			} catch (Throwable e) {
				throw new ServletException(e);
			} finally {
				resp.getOutputStream().flush();
				resp.getOutputStream().close();
			}
		}
	}

	public static ByteArrayInputStream excuteImage(String pageid, String type, Object t, double rate) throws Exception {
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		
		JasperPrint jasperPrint = getPrint(pageid, t);
		JpgExport(jasperPrint, out, rate);
		
		byte[] bt = out.toByteArray();
		in = new ByteArrayInputStream(bt);
		return in;
	}
	
	public static ByteArrayInputStream excutePdf(String pageid, String type, Object t) throws Exception {
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		
		JasperPrint jasperPrint = getPrint(pageid, t);
		PdfExport(jasperPrint, out);
		
		byte[] bt = out.toByteArray();
		in = new ByteArrayInputStream(bt);
		return in;
	}
	
	public static JasperPrint getPrint(String pageid, Object t) throws Exception {
		String module = pageid.substring(0, pageid.indexOf('.'));
		pageid = pageid.substring(pageid.indexOf('.') + 1);
		String uri = GlobalVar.getSystemProperty("view.jasper.access.rule");
		String custom_id = GlobalVar.getSystemProperty("custom.id");
		if(!CommonFun.isNe(custom_id)){
			uri = GlobalVar.getSystemProperty("view.jasper.custom.rule");
			uri = uri.replace("@[CUSTOM]", custom_id);
		}
		uri = uri.replace("@[MODULE]", module);
		uri = uri.replace("@[PAGE]", pageid.replace('.', '/'));

		File template = new File(GlobalVar.getWorkPath(), uri);
		

		if (!template.exists()) {
			throw new Exception("jasper file not found. " + template.getAbsolutePath());
		}

		Map<String, Object> tm = null;
		if (t instanceof Result) {
			tm = new HashMap<String, Object>();
			tm.put("detail", t);
		} else {
			tm = (Map<String, Object>) t;
		}
		try {
			tm.put("SUBREPORT_DIR", template.getParent() + "/");

			ReportDataSource detail = null;
			for (Iterator<String> e = tm.keySet().iterator(); e.hasNext();) {
				String key = e.next();
				if (tm.get(key) != null) {
					if (tm.get(key) instanceof Result) {
						tm.put(key, new ReportDataSource((Result) tm.get(key), module));
						if (detail == null) {
							detail = (ReportDataSource) tm.get(key);
						}
					}
				}
			}

			// Load编译好的模板
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(template);
			JRParameter[] params = jasperReport.getParameters();
			if (params != null) {
				for (JRParameter p : params) {
					if (p.getValueClassName().equals("java.io.InputStream")) {
						Object file_id = tm.get(p.getName());
						if (!CommonFun.isNe(file_id) && file_id instanceof String) {
							Record record = ResourceManager.getResource(module, (String) file_id);
							if (record != null) {
								File f = ResourceManager.getFile(false, record);
								if (f.exists()) {
									tm.put(p.getName(), new FileInputStream(f));
								}
							}
						}
					}
				}
			}
			// 进行数据填充
			return JasperFillManager.fillReport(jasperReport, tm, detail);
		} catch (Throwable e) {
			throw new ServletException(e);
		}
	}

	private JRExporter newExporter(String type, boolean print, String printer, HttpServletResponse resp) {
		JRExporter exporter = null;
		if (type.equals("xls")) {
			exporter = new JRXlsExporter();
			resp.setContentType("application/msexcel;charset=utf-8"); // application/vnd.ms-excel;application/msexcel;
		} else if (type.equals("pdf")) {
			exporter = new JRPdfExporter();
			if (print) {
				exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, printScript(printer));
			}
			resp.setContentType("application/pdf;charset=utf-8");
		} else if (type.equals("csv")) {
			exporter = new JRCsvExporter();
			resp.setContentType("application/csv;charset=utf-8");
		} else if (type.equals("txt")) {
			exporter = new JRTextExporter();
			exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(80));
			exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(25));
			resp.setContentType("html/txt;charset=utf-8");
		} else if (type.equals("rtf")) {
			exporter = new JRRtfExporter();
			resp.setContentType("application/rtf;charset=utf-8");
		} else if (type.equals("xml")) {
			exporter = new JRXmlExporter();
			resp.setContentType("text/xml;charset=utf-8");
		}
		return exporter;
	}

	private void printPdf(Map<String, Object> t, HttpServletResponse resp) throws ServletException, IllegalAccessException, InvocationTargetException {
		resp.setContentType("application/pdf;charset=utf-8");
		Document doc = null;
		try {
			Iterator<String> keys = t.keySet().iterator();
			doc = new Document();
			PdfWriter writer = PdfWriter.getInstance(doc, resp.getOutputStream());
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font fontCN = new Font(bfChinese, 10, Font.NORMAL);
			Font thFont = new Font(bfChinese, 14, Font.NORMAL);
			Font titleFont = new Font(bfChinese, 12, Font.NORMAL);
			Rectangle pageInfo = new Rectangle(36, 54, 788, 559);
			pageInfo.setBorderColor(BaseColor.BLACK);
			writer.setBoxSize("art", pageInfo);
			TableHeader header = new TableHeader();
			writer.setPageEvent(header);
			doc.setPageSize(new RectangleReadOnly(842, 595));
			doc.addAuthor("Shine.Xia");
			doc.open();
			while (keys.hasNext()) {
				String key = keys.next();
				Object tmp = t.get(key);
				if (tmp instanceof Result) {
					Result r = (Result) tmp;
					int rowcount = r.getResultset().size();
					// String pdfName = (String) t.get(key + ".name");
					String title = (String) t.get(key + ".title");
					// 设置title
					if (!CommonFun.isNe(title)) {
						Paragraph pg = new Paragraph(title, thFont);
						pg.setAlignment(Paragraph.ALIGN_CENTER);
						pg.setSpacingAfter(10);
						doc.add(pg);
					}
					String[] fields = (String[]) t.get(key + ".fields");
					if (fields == null && rowcount == 0) {
						continue;
					}
					@SuppressWarnings("unchecked")
					HashMap<String, HashMap<String, Object>> fielddefs = (HashMap<String, HashMap<String, Object>>) t.get(key + ".fielddefs");
					if (fields == null) {
						Object rObj = r.getResultset(0);
						Object[] fs = ((Record) rObj).keySet().toArray();
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
							fd.put("label", "序号");
							fd.put("datatype", "number");
							fielddefs.put("rowno", fd);
						}
					}
					PdfPTable table = new PdfPTable(fields.length);
					// table.setSpacingBefore(25);
					// table.setSpacingAfter(25);
					table.setHeaderRows(1);
					table.setWidthPercentage(100.0f);
					for (int i = 0; i < fields.length; i++) {
						table.addCell(new Phrase(fielddefs.get(fields[i]).get("label").toString(), titleFont));
					}
					for (int i = 0; i < rowcount; i++) {
						Object recordObj = r.getResultset(i);
						for (int c = 0; c < fields.length; c++) {
							String field = fields[c];
							HashMap<String, Object> fielddef = fielddefs.get(field);
							Object value = ((Record) recordObj).get(field);
							String datatype = "String";
							String svalue = "" + (CommonFun.isNe(value) ? "" : value);
							HashMap<String, String> dictionary = (HashMap<String, String>) fielddef.get("dictionary");
							if (dictionary != null && dictionary.size() > 0 && value != null) {
								value = dictionary.get(value) == null ? value : dictionary.get(value);
								svalue = "" + (CommonFun.isNe(value) ? "" : value);
							}
							if ("rowno".equals(fields[c]) && "合计".equals(value)) {
								datatype = "String";
							} else if ("date".equals(fielddef.get("datatype"))) {
								datatype = "String";
								if (value instanceof java.util.Date) {
									svalue = new java.text.SimpleDateFormat("yyyy-MM-dd").format(value);
								}
							} else if ("time".equals(fielddef.get("datatype"))) {
								datatype = "String";
								if (value instanceof java.util.Date) {
									svalue = new java.text.SimpleDateFormat("HH:mm:ss").format(value);
								}
							} else if ("datetime".equals(fielddef.get("datatype"))) {
								datatype = "String";
								if (value instanceof java.util.Date) {
									svalue = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
								}
							} else if ("number".equals(fielddef.get("datatype")) || "decimal".equals(fielddef.get("datatype")) || "currency".equals(fielddef.get("datatype"))) {
								datatype = "Number";
							} else if ("script".equals(fielddef.get("datatype"))) {
								datatype = "String";
								try {
									JavaScriptEngine engine = new JavaScriptEngine();
									engine.setParam("_svalue_", svalue, true);
									engine.setParam("record", "", false);
									StringBuffer script = new StringBuffer();
									script.append("record = " + CommonFun.object2Json(recordObj) + ";\r\n");
									script.append("function _script_() {\r\n");
									script.append(fielddef.get("script"));
									script.append("};\r\n");
									script.append("_svalue_ = _script_();");
									engine.excuteString(script.toString());
									svalue = (String)engine.getParamValue("_svalue_");
									if (svalue != null) {
										svalue = CommonFun.removeHtmlTag(svalue.toString());									
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else if ("template".equals(fielddef.get("datatype"))) {
								datatype = "String";
								try {
									svalue = ELExpression.excuteExpressionD(fielddef.get("template").toString(),
											(Map<String, Object>) recordObj);
									svalue = CommonFun.removeHtmlTag((String) svalue);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							Phrase phrase = new Phrase(svalue, fontCN);
							phrase.setLeading(fontCN.getSize() + 5);
							table.addCell(phrase);
						}
						// rownum++;
					}
					doc.add(table);
				}
			}
			resp.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			if (doc != null && doc.isOpen())
				doc.close();
			try {
				resp.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String printScript(String printer) {
		StringBuilder strbuff = new StringBuilder();
		strbuff.append("var pp = this.getPrintParams();");

		if (printer != null && printer.equals("") == false) {
			strbuff.append("pp.interactive = pp.constants.interactionLevel.silent;");
			// strbuff.append("pp.pageHandling =
			// pp.constants.handling.shrink;");
			strbuff.append("pp.printerName = \"" + printer + "\";");
		}
		strbuff.append("this.print(pp);");
		strbuff.append("this.closeDoc();");
		return strbuff.toString();
	}

	public void PdfExport(JasperPrint jasperPrint, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setContentType("application/pdf;charset=utf-8");
		PdfExport(jasperPrint, resp.getOutputStream());
	}

	public void PngExport(JasperPrint jasperPrint, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		JpgExport(jasperPrint, req, resp);
	}

	public void JpegExport(JasperPrint jasperPrint, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		JpgExport(jasperPrint, req, resp);
	}

	public void JpgExport(JasperPrint jasperPrint, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setContentType("image/jpeg;charset=utf-8");
		double rate = 1;
		if (!CommonFun.isNe(req.getParameter("_rate"))) {
			rate = Double.parseDouble(req.getParameter("_rate"));
		}
		JpgExport(jasperPrint, resp.getOutputStream(), rate);
	}

	private static void PdfExport(JasperPrint jasperPrint, OutputStream outputStream) throws Exception {
		JRPdfExporter exporter = new JRPdfExporter(DefaultJasperReportsContext.getInstance());
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		exporter.exportReport();
	}

	private static void JpgExport(JasperPrint jasperPrint, OutputStream outputStream, double rate) throws Exception {
		JRGraphics2DExporter exporter = new JRGraphics2DExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		BufferedImage bufferedImage = new BufferedImage((int) Math.floor(jasperPrint.getPageWidth() * rate), (int) Math.floor(jasperPrint.getPageHeight() * jasperPrint.getPages().size() * rate),
				BufferedImage.TYPE_INT_RGB);
		SimpleGraphics2DReportConfiguration config = new SimpleGraphics2DReportConfiguration();
		config.setZoomRatio((float) rate);
		exporter.setConfiguration(config);
		exporter.setExporterOutput(new Graphics2DExporterOutput() {
			@Override
			public Graphics2D getGraphics2D() {
				// TODO Auto-generated method stub
				return (Graphics2D) bufferedImage.getGraphics();
			}
		});
		exporter.exportReport();
		ImageIO.write(bufferedImage, "JPEG", outputStream);
	}

	class TableHeader extends PdfPageEventHelper {
		String header;
		PdfTemplate total;
		BaseFont bfChinese;
		Font fontCN;

		public void setHeader(String header) {
			this.header = header;
		}

		public void onOpenDocument(PdfWriter writer, Document document) {
			total = writer.getDirectContent().createTemplate(30, 15);
			try {
				bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fontCN = new Font(bfChinese, 10, Font.NORMAL);
		}

		public void onEndPage(PdfWriter writer, Document document) {
			Rectangle rect = writer.getBoxSize("art");
			PdfPTable table = new PdfPTable(2);
			try {
				table.setWidths(new int[] { 20, 20 });
				table.setTotalWidth(80);
				table.setLockedWidth(true);
				table.getDefaultCell().setFixedHeight(15);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				table.addCell(new Phrase(String.format("第 %d/", writer.getPageNumber()), fontCN));
				PdfPCell cell = new PdfPCell(Image.getInstance(total));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				table.writeSelectedRows(0, -1, (rect.getLeft() + rect.getRight()) / 2 - 30, rect.getBottom() - 18, writer.getDirectContent());

			} catch (DocumentException de) {
				throw new ExceptionConverter(de);
			}
		}

		public void onCloseDocument(PdfWriter writer, Document document) {
			ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(writer.getPageNumber() - 1 + " 页", fontCN), 0, 3, 0);

		}
	}

}
