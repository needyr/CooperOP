package cn.crtech.cooperop.bus.message.sender;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import cn.crtech.cooperop.application.service.SystemMessageService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;


public class AliYun implements GateWay{
	private String url_mould;
	private String defaultConnectTimeout;
	private String defaultReadTimeout;
	private String accessKeyId;
	private String accessKeySecret;
	private int mobile_limit;
	private String mobile_separater;
	private int letter_limit;
	private String SignName;

	public AliYun(String url, String accessKeyId, String accessKeySecret, String SignName) {
		this.url_mould = url;
		this.defaultConnectTimeout = GlobalVar.getSystemProperty("defaultConnectTimeout", "10000");
		this.defaultReadTimeout = GlobalVar.getSystemProperty("defaultReadTimeout", "10000");
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.mobile_limit = Integer.parseInt(GlobalVar.getSystemProperty("mobile_limit", "60"));
		this.mobile_separater = GlobalVar.getSystemProperty("mobile_separater", ",");
		this.letter_limit = Integer.parseInt(GlobalVar.getSystemProperty("letter_limit", "20"));
		this.SignName = SignName;

	}
	@Override
	public void send(String mobile, String template, Map<String, Object> params, Object smsId) throws Exception {
		send(new String[] { mobile }, template, params, smsId);
	}
	@Override
	public void send(String[] mobiles, String template, Map<String, Object> params, Object smsId) throws Exception {
		if (CommonFun.isNe(mobiles))
			throw new Exception("接收短信手机号码不能为空.");
		// 设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", defaultConnectTimeout);
		System.setProperty("sun.net.client.defaultReadTimeout", defaultReadTimeout);
		Record tp = new Record();
		Iterator<String> it = params.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (!CommonFun.isNe(params.get(key))) {
				String value = params.get(key).toString();
				if (value.length() <= letter_limit) {
					tp.put(key, value);
				} else {
					int i = 0, l = 0;
					while (value.length() > 0) {
						l = value.length() > letter_limit ? letter_limit : value.length();
						i ++;
						tp.put(key + i, value.substring(0, l));
						value = value.substring(l);
					}
				}
			}
		}
		// 初始化ascClient需要的几个参数
		String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
		String domain = "dysmsapi.aliyuncs.com";//url_mould;// 短信API产品域名（接口地址固定，无需修改）
		// 初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		// 使用post提交
		request.setMethod(MethodType.POST);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(SignName);
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(template);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam(CommonFun.object2Json(tp));
		// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		//request.setOutId("yourOutId");
		StringBuffer t = new StringBuffer();
		for (int i = 0; i < mobiles.length; i++) {
			if (i % mobile_limit == 0) {
				t = new StringBuffer();
			} else {
				t.append(mobile_separater);
			}
			t.append(mobiles[i]);
			if (i == mobiles.length - 1 || i % mobile_limit == mobile_limit - 1) {
				String m = t.toString();
				// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
				request.setPhoneNumbers(m);
				// 请求失败这里会抛ClientException异常
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
					// 请求成功
				} else {
					if(smsId != null){
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("state", -1);
						param.put("error_msg", sendSmsResponse.getMessage());
						param.put("id", smsId);
						try {
							new SystemMessageService().update(param);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					throw new Exception(sendSmsResponse.getMessage());
				}
			}
		}
		if(smsId != null){
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("state", 1);
			param.put("id", smsId);
			try {
				new SystemMessageService().update(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		AliYun a = new AliYun("dysmsapi.aliyuncs.com", "LTAIy261Ag12CLKs", "b5cr8p6fRuA8oYl5x2EMdrZMQKrkM3", "东龙");
		try {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("code", "382776");
			a.send("18777114221", "SMS_172008177", m, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
