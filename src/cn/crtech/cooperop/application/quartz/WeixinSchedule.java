/*package cn.speedit.application.im;

import java.util.HashMap;
import java.util.List;

import cn.speedit.application.service.SystemExceptionService;
import cn.speedit.base.service.MessageSmsService;
import cn.speedit.base.service.SystemConfigSmsService;
import cn.speedit.base.service.weixin.ConfigService;
import cn.speedit.base.service.weixin.MessageService;
import cn.speedit.base.util.weixin.Config;
import cn.speedit.framework.rdms.Record;
import cn.speedit.framework.rdms.Result;
import cn.speedit.framework.schedule.AbstractSchedule;


public class WeixinSchedule extends AbstractSchedule {
	
	@Override
	public boolean executeOn() throws Exception {

		try {
			ConfigService config = new ConfigService();
			Result result = config.list(new HashMap<String, Object>(0));
			if(result ==null || result.getCount()==0){
				return false;
			}
			String userid = "";// 通道编号
			int maxSendNum = 0;// 每轮发送最大数量
			int sendCount = 0;
			List<Record> list = result.getResultset();
			for (int i = 0; i < list.size(); i++) {
				Record scs = list.get(i);
				userid = scs.get("userid").toString();
				maxSendNum = Integer.parseInt(scs.get("max_send_num")
						.toString());
				MessageService weixinMessage = new MessageService();
				Result msgResult = weixinMessage.onlyUseByWeixinSchedule(userid,maxSendNum);
				if (msgResult == null || msgResult.getCount() == 0)
					continue;
				List<Record> smsList = msgResult.getResultset();
				log.debug("[WEIXIN]待发送条数[" + smsList.size() + "]");
				for (HashMap<String, Object> sms : smsList) {
					try {
						MesagelInterface si = (MesagelInterface) Class.forName(scs.get("classz").toString()).newInstance();
						sendCount += si.send(scs, sms);
						Thread.sleep(1000);
					} catch (Exception e) {
						new SystemExceptionService().save(new Exception("发送微信异常", e), "", "");
						e.printStackTrace();
					}
				}
			}
			log.debug("发送结果条数:[" + (sendCount > 0 ? sendCount : 0)
					+ "]!");
			return true;
		} catch (Exception e) {
			new SystemExceptionService().save(new Exception("发送微信异常",e), "", "");
			throw e;
		}
	}

}
*/