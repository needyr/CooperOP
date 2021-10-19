package util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import cn.crtech.cooperop.bus.util.CommonFun;
import net.sf.jasperreports.engine.JasperCompileManager;

import java.io.*;
import java.util.List;
import java.util.Map;

public class ParseXML {                                                             

	public static void createXml(Map<String, Object> params) {
		Document document = DocumentHelper.createDocument();
		Element jasperReport = document.addElement("jasperReport");
		jasperReport.addAttribute("xmlns", "http://jasperreports.sourceforge.net/jasperreports");
		jasperReport.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		jasperReport.addAttribute("xsi:schemaLocation",
				"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd");
		// 设置jasper的属性
		jasperReport.addAttribute("name", (String) params.get("name"));
		jasperReport.addAttribute("language", (String) params.get("language"));
		String[] jstyle = params.get("style").toString().split(";");
		for (int i = 0; i < jstyle.length; i++) {
			String[] s = jstyle[i].split(":");
			jasperReport.addAttribute(s[0], s[1]);
		}

		List<Map<String, Object>> chs = (List<Map<String, Object>>) params.get("childs");
		List<Map<String, Object>> pf = (List<Map<String, Object>>) params.get("pf");
		for(int j = 0; j<pf.size() ;j++){
			if (!CommonFun.isNe(pf.get(j).get("fieldtype"))) {
				jasperReport.addElement((String) pf.get(j).get("fieldtype"))
						.addAttribute("name", (String) pf.get(j).get("value"))
						.addAttribute("class", (String) pf.get(j).get("classtype"));
			}
		}
		for (int i = 0; i < chs.size(); i++) {
			Element ch = jasperReport.addElement((String) chs.get(i).get("type"));
			Element band = ch.addElement("band");
			String[] chjstyle = chs.get(i).get("style").toString().split(";");
			for (int ci = 0; ci < chjstyle.length; ci++) {
				String[] s = chjstyle[ci].split(":");
				band.addAttribute(s[0], s[1].split("px")[0]);
			}
			List<Map<String, Object>> lchs = (List<Map<String, Object>>) chs.get(i).get("childs");
			for (int li = 0; li < lchs.size(); li++) {
				Element lch = band.addElement((String) lchs.get(li).get("type"));
				Element position = lch.addElement("reportElement");
				Element box = lch.addElement("box");
				Element textalign = lch.addElement("textElement");
				Element expression = lch.addElement("textFieldExpression");
				if(CommonFun.isNe(lchs.get(li).get("fieldtype"))){
					expression.addCDATA((String) lchs.get(li).get("value"));
				}else if("field".equals(lchs.get(li).get("fieldtype"))){
					expression.addCDATA("$F{"+(String) lchs.get(li).get("value")+"}");
				}else if("parameter".equals(lchs.get(li).get("fieldtype"))){
					expression.addCDATA("$P{"+(String) lchs.get(li).get("value")+"}");
				}
				String[] lchjstyle = lchs.get(li).get("style").toString().split(";");
				for (int lci = 0; lci < lchjstyle.length; lci++) {
					String[] s = lchjstyle[lci].split(":");
					if ("left".equals(s[0])) {
						position.addAttribute("x", s[1].split("px")[0]);
					} else if ("top".equals(s[0])) {
						position.addAttribute("y", s[1].split("px")[0]);
					} else if ("height".equals(s[0]) || "width".equals(s[0])) {
						position.addAttribute(s[0], s[1].split("px")[0]);
					} else if ("padding-top".equals(s[0])) {
						box.addAttribute("topPadding=", s[1].split("px")[0]);
					} else if ("padding-left".equals(s[0])) {
						box.addAttribute("leftPadding", s[1].split("px")[0]);
					} else if ("padding-right".equals(s[0])) {
						box.addAttribute("rightPadding", s[1].split("px")[0]);
					} else if ("padding-bottom".equals(s[0])) {
						box.addAttribute("bottomPadding", s[1].split("px")[0]);
					} else if ("border-top".equals(s[0])) {
						box.addElement("topPen").addAttribute("lineWidth", s[1].split("px")[0]);
					} else if ("border-right".equals(s[0])) {
						box.addElement("rightPen").addAttribute("lineWidth", s[1].split("px")[0]);
					} else if ("border-bottom".equals(s[0])) {
						box.addElement("bottomPen").addAttribute("lineWidth", s[1].split("px")[0]);
					} else if ("border-left".equals(s[0])) {
						box.addElement("leftPen").addAttribute("lineWidth", s[1].split("px")[0]);
					} else if ("text-align".equals(s[0])) {
						textalign.addAttribute("textAlignment", s[1]);
					} else if ("vertical-align".equals(s[0])) {
						textalign.addAttribute("verticalAlignment", s[1]);
					} else if ("font-family".equals(s[0])) {
						textalign.addElement("font").addAttribute("fontName", s[1]);
					}
				}
			}
		}
		try {
			XMLWriter output = new XMLWriter(new FileWriter(new File("D:/Users/jq123/Desktop/jas.jrxml")));
			output.write(document);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		ParseXML.by();
	}

	public static void by() {
		try {
			String jrxmlSourcePath = "D:/Users/jq123/Desktop/jas.jrxml";
			System.out.println(jrxmlSourcePath);
			String jrxmlDestSourcePath = "D:/Users/jq123/Desktop/jas.jasper";
			JasperCompileManager.compileReportToFile(jrxmlSourcePath, jrxmlDestSourcePath);
			// InputStream isRef = new FileInputStream(new
			// File(jrxmlDestSourcePath));
			// OutputStream sosRef = new FileOutputStream("");
			// JasperRunManager.runReportToPdfStream(isRef, sosRef, new
			// HashMap(), new JREmptyDataSource());
			// sosRef.flush();
			// sosRef.close();
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] argv) {
		ParseXML.by();
	}

}
