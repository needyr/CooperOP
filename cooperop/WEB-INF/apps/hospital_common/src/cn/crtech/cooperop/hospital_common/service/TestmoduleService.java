package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class TestmoduleService extends BaseService {

	// client
	public String clientTestModule(Map<String, Object> req) throws Exception {
		return null;
	}
	
	public String clientTrade(Map<String, Object> params) throws Exception {
		StringBuffer tradeStr = new StringBuffer();
		String type = (String)params.get("type");
		
		if (!CommonFun.isNe(type)) {
			if ("CRMS".equalsIgnoreCase(type)) {
				tradeStr.append(
					"<Request>" +
						"    <Header>" +
							"        <SourceSystem>123</SourceSystem> " +
							"        <MessageID>test</MessageID>" +
						"    </Header>" +
					"    <Body>" +
						"        <OutpatientEncounterStartedRt>" +
							"            <UpdateDate>7</UpdateDate>" +
							"            <PATPatientID>1</PATPatientID>" +
							"            <UpdateTime>8</UpdateTime>" +
							"            <UpdateUserCode>6</UpdateUserCode>" +
							"            <PAADMOPTime>5</PAADMOPTime>" +
							"            <PAADMOPDocCode>4</PAADMOPDocCode>" +
							"            <PAADMVisitNumber>2</PAADMVisitNumber>" +
							"            <PAADMOPDeptCode>3</PAADMOPDeptCode>" +
						"        </OutpatientEncounterStartedRt>" +
					"    </Body>  " +
					"</Request>");
			} else if ("DHCHIP".equalsIgnoreCase(type)) {
				tradeStr.append(
			"<Request>" +
				"    <Header>" +
					"        <SourceSystem>cr_pivas</SourceSystem>" +
					"        <MessageID>test</MessageID>" +
				"    </Header>" +
			"    <Body>" +
				"        <OutpatientEncounterStartedRt>" +
					"            <UpdateDate>17</UpdateDate>" +
					"            <PATPatientID>11</PATPatientID>" +
					"            <UpdateTime>18</UpdateTime>" +
					"            <UpdateUserCode>管理员</UpdateUserCode>" +
					"            <PAADMOPTime>15</PAADMOPTime>" +
					"            <PAADMOPDocCode>14</PAADMOPDocCode>" +
					"            <PAADMVisitNumber>12</PAADMVisitNumber>" +
					"            <PAADMOPDeptCode>13</PAADMOPDeptCode>" +
				"        </OutpatientEncounterStartedRt>" +
			"    </Body>  " +
			"</Request>");
			} else if ("JSONP".equalsIgnoreCase(type)) {
				tradeStr.append("[[{\\\"start\\\":\\\"1\\\",\\\"limit\\\":\\\"20\\\"}]]");
			}
		} else {
			log.error("服务 type 为空.");
			throw new Exception("服务 type 为空.");
		}
		
		return tradeStr.toString();
	}
	
	public Record client(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("example", clientTrade(params));
		return r;
	}
	
	// server
	public String serverTestModule(Map<String, Object> req) throws Exception {
		return null;
	}
	
	public String serverTrade(Map<String, Object> params) throws Exception {
		return clientTrade(params);
	}
	
	public Record server(Map<String, Object> params) throws Exception {
		Record r = client(params);
		r.put("soapheader", "{\\\"appId\\\": \\\"123\\\", \\\"sso_key\\\": \\\"~!precheck!~\\\"}");
		return r;
	}

}
