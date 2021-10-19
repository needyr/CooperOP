package cn.crtech.cooperop.application.quartz;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.choho.authresource.log;
import cn.crtech.cooperop.application.service.SystemMessageService;
import cn.crtech.cooperop.application.service.tunnel.SmsService;
import cn.crtech.cooperop.bus.message.sender.AliYun;
import cn.crtech.cooperop.bus.message.sender.GateWay;
import cn.crtech.cooperop.bus.message.sender.MB345;
import cn.crtech.cooperop.bus.message.sender.SMSSender;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;
import cn.crtech.cooperop.bus.util.CommonFun;


public class SMSSchedule extends AbstractSchedule {
	private static boolean b = false;
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		b = true;
		try {
			SmsService smss = new SmsService();
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("state", "1");
			Result result = smss.query(p);
			if(result ==null || result.getCount()==0){
				return false;
			}
			int roadCode = 0;// 通道编号
			int maxSendNum = 0;// 每轮发送最大数量
			int sendCount = 0;
			List<Record> list = result.getResultset();
			SystemMessageService smes = new SystemMessageService();
		//	SMSSender ss = new SMSSender();
			for (int i = 0; i < list.size(); i++) {
				Record scs = list.get(i);
				GateWay gw = null;
				Object gateway = scs.get("gateway");
				if("1".equals(gateway)){
					gw = new MB345(scs.getString("protocol"), scs.getString("username"), scs.getString("password"), scs.getString("signature"));
				}else if("2".equals(gateway)){
					gw = new AliYun(scs.getString("protocol"), scs.getString("username"), scs.getString("password"), scs.getString("signature"));
				}
				if(gw == null){
					continue;
				}
				log.debug("[SMS]待发送条数【gw】");
				roadCode = scs.getInt("id");
				maxSendNum = Integer.parseInt(scs.get("limit_num")
						.toString());
				Map<String, Object> pa = new HashMap<String, Object>();
				pa.put("tunnel_id", roadCode);
				pa.put("maxSendNum", maxSendNum);
				pa.put("state", 0);
				pa.put("type", "1");
				Result msgResult = smes.query(pa);
				log.debug("[SMS]待发送条数【" + msgResult.getResultset().size() + "】");
				if (msgResult == null || msgResult.getCount() == 0)
					continue;
				List<Record> smsList = msgResult.getResultset();
				
				for (Record sms : smsList) {
					try {
						if("1".equals(gateway)){
							gw.send(sms.getString("send_to"), sms.getString("content"), CommonFun.json2Object(sms.getString("content_param"), Map.class), sms.get("id"));
							sendCount++;
						}else if("2".equals(gateway)){
							gw.send(sms.getString("send_to"), sms.getString("content_template_id"), CommonFun.json2Object(sms.getString("content_param"), Map.class), sms.get("id"));
							sendCount++;
						}
						//sendCount += ss.send(scs, sms);
						Thread.sleep(500);
					} catch (Exception e) {
						//throw new Exception("发送短信异常", e);
						e.printStackTrace();
						log.error("发送短信异常!" + e.getMessage());
					}
				}
			}
			log.debug("发送结果条数:[" + (sendCount > 0 ? sendCount : 0)
					+ "]!");
			return true;
		} catch (Exception e) {
			throw new Exception("发送短信异常",e);
		}finally {
			b = false;
		}
	}

}
