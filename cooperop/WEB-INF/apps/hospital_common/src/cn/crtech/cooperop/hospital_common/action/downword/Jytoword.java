package cn.crtech.cooperop.hospital_common.action.downword;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.patient.PatientService;

//@WebServlet(loadOnStartup = 20, urlPatterns="/testword")
public class Jytoword extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			cn.crtech.cooperop.bus.log.log.error(e2);
		}
		Map<String, Object> reqParams = CommonFun.requestMap(req);
		OutputStream ouputStream = null;
		try {
			Map<String, Object> baseinfo = new PatientService().baseinfo(reqParams);
			Record patient = (Record) baseinfo.get("patient");
			Object lab_obj = new cn.crtech.cooperop.hospital_common.service.PatientService().queryrequestendetail(reqParams).get("requestendetail");
			List<Record> re = new ArrayList<Record>();
			if(!CommonFun.isNe(lab_obj)) {
				re = (List<Record>)lab_obj;
			}
			Object object = baseinfo.get("diagnosGroup");
			StringBuffer dia = new StringBuffer();
			if(!CommonFun.isNe(object)) {
				List<Record> ll = (List<Record>) object;
				for (Record record : ll) {
					dia.append(record.get("diagnosis_desc")+",");
				}
			}
			String filename = SystemConfig.getSystemConfigValue("hospital_common", "hospital_name", "")+reqParams.get("specimen")+"报告单";
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/msexcle");   
			resp.setHeader("content-disposition", "attachment;filename="+new String(filename.getBytes(),"ISO-8859-1")+".doc");
			MiniTableRenderData miniTableRenderData = new MiniTableRenderData(getFloraTableHead(),getFloraTable(re));
			ouputStream = resp.getOutputStream();  
			Map<String, Object> datas = new HashMap<String, Object>() {
	            {
	                put("hospital_name", SystemConfig.getSystemConfigValue("hospital_common", "hospital_name", ""));
	                put("jy_name", reqParams.get("specimen"));
	                put("patient_name", patient.get("patient_name"));
	                put("sex", patient.get("sex"));
	                put("age", patient.get("age"));
	                put("dept_name", patient.get("dept_in_name"));
	                put("num", reqParams.get("test_no"));
	                put("patient_no", patient.get("patient_no"));
	                put("bb_class", reqParams.get("specimen"));
	                put("bed_no", patient.get("bed_no"));
	                put("bq", "");
	                put("sq_doctor", reqParams.get("ordering_provider"));
	                put("diagnosis", dia.toString());
	                put("beiz", "");
	                put("tm", "");
	                put("create_time", reqParams.get("requested_date_time"));
	                put("table", miniTableRenderData);
	                put("js_time", "");
	                put("bg_time", reqParams.get("results_rpt_date_time"));
	                put("user_name", "");
	            }
	        };
	        File tmp = new File(req.getSession().getServletContext().getRealPath("/")+"WEB-INF\\apps\\hospital_common\\resource\\wordtemplate\\jy_msg.docx");
	        if(tmp.exists()) {
	        	XWPFTemplate  template = XWPFTemplate.compile(tmp)
		                .render(datas);
		        template.write(ouputStream);
		        template.close();
	        }
	        ouputStream.flush();
		} catch (Throwable e) {
			cn.crtech.cooperop.bus.log.log.error(e);
		}finally {
			try {
				if (ouputStream != null) {
					ouputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//添加表头
	private RowRenderData getFloraTableHead() {
	    RowRenderData headData = new RowRenderData();
	    Style style = new Style(); //设置单元格格式
	    style.setFontSize(10);
	    style.setFontFamily("宋体");
	    headData.setCellDatas(new ArrayList<CellRenderData>(){{
	         add(new CellRenderData(new TextRenderData("代号",style)));
	         add(new CellRenderData(new TextRenderData("检验项目",style)));
	         add(new CellRenderData(new TextRenderData("结果",style)));
	         add(new CellRenderData(new TextRenderData("单位",style)));
	         add(new CellRenderData(new TextRenderData("参考值",style)));
	    }});
	    return headData;
	}
	 
	//添加表数据
	private List<RowRenderData> getFloraTable(List<Record> floraList) {
	        List<RowRenderData> rowList = new ArrayList<RowRenderData>();
	        Style style = new Style();
	        style.setFontFamily("宋体");
	        style.setFontSize(10);
	        if(floraList!=null) {
	        	for (Map<String, Object> item:floraList) {
	        		RowRenderData rowRenderData = new RowRenderData();
	        		List<CellRenderData> cells = new ArrayList<CellRenderData>();
	        		cells.add(0,new CellRenderData(new TextRenderData((String)(item.get("report_item_code")==null?"":item.get("report_item_code")),style)));
	        		cells.add(1,new CellRenderData(new TextRenderData((String)(item.get("tag")==null?"":item.get("tag"))+item.get("report_item_name"),style)));
	        		cells.add(2,new CellRenderData(new TextRenderData((String)(item.get("result")==null?"":item.get("result")),style)));
	        		cells.add(3,new CellRenderData(new TextRenderData((String)(item.get("units")==null?"":item.get("units")),style)));
	        		cells.add(4,new CellRenderData(new TextRenderData((String)(item.get("print_context")==null?"":item.get("print_context")),style)));
	        		rowRenderData.setCellDatas(cells);
	        		rowList.add(rowRenderData);
	        	}
	        }
	        return rowList;
	}
	
	public static void main(String[] args) {
		Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("hospital_name", "22222");
                put("jy_name", "333");
                put("patient_name", "不玩啊玩不好玩");
                put("sex", "男");
                put("age", "333岁");
            }
        };
		XWPFTemplate template = XWPFTemplate.compile("T:\\chaoranHLYY-space\\CooperOP\\cooperop\\WEB-INF\\apps\\hospital_common\\src\\cn\\crtech\\cooperop\\hospital_common\\util\\jy_msg.docx").render(datas);  
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("T:\\chaoranHLYY-space\\CooperOP\\cooperop\\WEB-INF\\apps\\hospital_common\\src\\cn\\crtech\\cooperop\\hospital_common\\util\\output.docx");
			template.write(out);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				template.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
