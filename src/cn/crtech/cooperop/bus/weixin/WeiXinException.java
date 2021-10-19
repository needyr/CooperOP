package cn.crtech.cooperop.bus.weixin;

public class WeiXinException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1214399810075192804L;

	enum codes {
		WX_D1("系统繁忙，此时请开发者稍候再试"), WX_0("请求成功"), WX_40001("获取access_token时AppSecret错误，或者access_token无效。请开发者认真比对AppSecret的正确性，或查看是否正在为恰当的公众号调用接口"), WX_40002("不合法的凭证类型"), WX_40003(
				"不合法的OpenID，请开发者确认OpenID（该用户）是否已关注公众号，或是否是其他公众号的OpenID"), WX_40004("不合法的媒体文件类型"), WX_40005("不合法的文件类型"), WX_40006("不合法的文件大小"), WX_40007("不合法的媒体文件id"), WX_40008("不合法的消息类型"), WX_40009(
						"不合法的图片文件大小"), WX_40010("不合法的语音文件大小"), WX_40011("不合法的视频文件大小"), WX_40012("不合法的缩略图文件大小"), WX_40013("不合法的AppID，请开发者检查AppID的正确性，避免异常字符，注意大小写"), WX_40014(
								"不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口"), WX_40015("不合法的菜单类型"), WX_40016("不合法的按钮个数"), WX_40017("不合法的按钮个数"), WX_40018(
										"不合法的按钮名字长度"), WX_40019("不合法的按钮KEY长度"), WX_40020("不合法的按钮URL长度"), WX_40021("不合法的菜单版本号"), WX_40022("不合法的子菜单级数"), WX_40023("不合法的子菜单按钮个数"), WX_40024(
												"不合法的子菜单按钮类型"), WX_40025("不合法的子菜单按钮名字长度"), WX_40026("不合法的子菜单按钮KEY长度"), WX_40027("不合法的子菜单按钮URL长度"), WX_40028("不合法的自定义菜单使用用户"), WX_40029(
														"不合法的oauth_code"), WX_40030("不合法的refresh_token"), WX_40031("不合法的openid列表"), WX_40032("不合法的openid列表长度"), WX_40033(
																"不合法的请求字符，不能包含\\uxxxx格式的字符"), WX_40035("不合法的参数"), WX_40038("不合法的请求格式"), WX_40039("不合法的URL长度"), WX_40050("不合法的分组id"), WX_40051(
																		"分组名字不合法"), WX_40060("删除单篇图文时，指定的article_idx不合法"), WX_40117("分组名字不合法"), WX_40118("media_id大小不合法"), WX_40119(
																				"button类型错误"), WX_40120("button类型错误"), WX_40121("不合法的media_id类型"), WX_40132("微信号不合法"), WX_40137("不支持的图片格式"), WX_40155(
																						"请勿添加其他公众号的主页链接"), WX_41001("缺少access_token参数"), WX_41002("缺少appid参数"), WX_41003("缺少refresh_token参数"), WX_41004(
																								"缺少secret参数"), WX_41005("缺少多媒体文件数据"), WX_41006("缺少media_id参数"), WX_41007("缺少子菜单数据"), WX_41008(
																										"缺少oauthcode"), WX_41009("缺少openid"), WX_42001(
																												"access_token超时，请检查access_token的有效期，请参考基础支持-获取access_token中，对access_token的详细机制说明"), WX_42002(
																														"refresh_token超时"), WX_42003("oauth_code超时"), WX_42007(
																																"用户修改微信密码，accesstoken和refreshtoken失效，需要重新授权"), WX_43001(
																																		"需要GET请求"), WX_43002("需要POST请求"), WX_43003(
																																				"需要HTTPS请求"), WX_43004("需要接收者关注"), WX_43005(
																																						"需要好友关系"), WX_43019("需要将接收者从黑名单中移除"), WX_44001(
																																								"多媒体文件为空"), WX_44002(
																																										"POST的数据包为空"), WX_44003(
																																												"图文消息内容为空"), WX_44004(
																																														"文本消息内容为空"), WX_45001(
																																																"多媒体文件大小超过限制"), WX_45002(
																																																		"消息内容超过限制"), WX_45003(
																																																				"标题字段超过限制"), WX_45004(
																																																						"描述字段超过限制"), WX_45005(
																																																								"链接字段超过限制"), WX_45006(
																																																										"图片链接字段超过限制"), WX_45007(
																																																												"语音播放时间超过限制"), WX_45008(
																																																														"图文消息超过限制"), WX_45009(
																																																																"接口调用超过限制"), WX_45010(
																																																																		"创建菜单个数超过限制"), WX_45011(
																																																																				"API调用太频繁，请稍候再试"), WX_45015(
																																																																						"回复时间超过限制"), WX_45016(
																																																																								"系统分组，不允许修改"), WX_45017(
																																																																										"分组名字过长"), WX_45018(
																																																																												"分组数量超过上限"), WX_45047(
																																																																														"客服接口下行条数超过上限"), WX_46001(
																																																																																"不存在媒体数据"), WX_46002(
																																																																																		"不存在的菜单版本"), WX_46003(
																																																																																				"不存在的菜单数据"), WX_46004(
																																																																																						"不存在的用户"), WX_47001(
																																																																																								"解析JSON/XML内容错误"), WX_48001(
																																																																																										"api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限"), WX_48002(
																																																																																												"粉丝拒收消息（粉丝在公众号选项中，关闭了“接收消息”）"), WX_48004(
																																																																																														"api接口被封禁，请登录mp.weixin.qq.com查看详情"), WX_48005(
																																																																																																"api禁止删除被自动回复和自定义菜单引用的素材"), WX_48006(
																																																																																																		"api禁止清零调用次数，因为清零次数达到上限"), WX_48008(
																																																																																																				"没有该类型消息的发送权限"), WX_50001(
																																																																																																						"用户未授权该api"), WX_50002(
																																																																																																								"用户受限，可能是违规后接口被封禁"), WX_61451(
																																																																																																										"参数错误,invalidparameter)"), WX_61452(
																																																																																																												"无效客服账号,invalidkf_account)"), WX_61453(
																																																																																																														"客服帐号已存在,kf_accountexsited)"), WX_61454(
																																																																																																																"客服帐号名长度超过限制,仅允许10个英文字符，不包括@及@后的公众号的微信号),invalidkf_acountlength)"), WX_61455(
																																																																																																																		"客服帐号名包含非法字符,仅允许英文+数字),illegalcharacterinkf_account)"), WX_61456(
																																																																																																																				"客服帐号个数超过限制,10个客服账号),kf_accountcountexceeded)"), WX_61457(
																																																																																																																						"无效头像文件类型,invalidfiletype)"), WX_61450(
																																																																																																																								"系统错误,systemerror)"), WX_61500(
																																																																																																																										"日期格式错误"), WX_65301(
																																																																																																																												"不存在此menuid对应的个性化菜单"), WX_65302(
																																																																																																																														"没有相应的用户"), WX_65303(
																																																																																																																																"没有默认菜单，不能创建个性化菜单"), WX_65304(
																																																																																																																																		"MatchRule信息为空"), WX_65305(
																																																																																																																																				"个性化菜单数量受限"), WX_65306(
																																																																																																																																						"不支持个性化菜单的帐号"), WX_65307(
																																																																																																																																								"个性化菜单信息为空"), WX_65308(
																																																																																																																																										"包含没有响应类型的button"), WX_65309(
																																																																																																																																												"个性化菜单开关处于关闭状态"), WX_65310(
																																																																																																																																														"填写了省份或城市信息，国家信息不能为空"), WX_65311(
																																																																																																																																																"填写了城市信息，省份信息不能为空"), WX_65312(
																																																																																																																																																		"不合法的国家信息"), WX_65313(
																																																																																																																																																				"不合法的省份信息"), WX_65314(
																																																																																																																																																						"不合法的城市信息"), WX_65316(
																																																																																																																																																								"该公众号的菜单设置了过多的域名外跳（最多跳转到3个域名的链接）"), WX_65317(
																																																																																																																																																										"不合法的URL"), WX_9001001(
																																																																																																																																																												"POST数据参数不合法"), WX_9001002(
																																																																																																																																																														"远端服务不可用"), WX_9001003(
																																																																																																																																																																"Ticket不合法"), WX_9001004(
																																																																																																																																																																		"获取摇周边用户信息失败"), WX_9001005(
																																																																																																																																																																				"获取商户信息失败"), WX_9001006(
																																																																																																																																																																						"获取OpenID失败"), WX_9001007(
																																																																																																																																																																								"上传文件缺失"), WX_9001008(
																																																																																																																																																																										"上传素材的文件类型不合法"), WX_9001009(
																																																																																																																																																																												"上传素材的文件尺寸不合法"), WX_9001010(
																																																																																																																																																																														"上传失败"), WX_9001020(
																																																																																																																																																																																"帐号不合法"), WX_9001021(
																																																																																																																																																																																		"已有设备激活率低于50%，不能新增设备"), WX_9001022(
																																																																																																																																																																																				"设备申请数不合法，必须为大于0的数字"), WX_9001023(
																																																																																																																																																																																						"已存在审核中的设备ID申请"), WX_9001024(
																																																																																																																																																																																								"一次查询设备ID数量不能超过50"), WX_9001025(
																																																																																																																																																																																										"设备ID不合法"), WX_9001026(
																																																																																																																																																																																												"页面ID不合法"), WX_9001027(
																																																																																																																																																																																														"页面参数不合法"), WX_9001028(
																																																																																																																																																																																																"一次删除页面ID数量不能超过10"), WX_9001029(
																																																																																																																																																																																																		"页面已应用在设备中，请先解除应用关系再删除"), WX_9001030(
																																																																																																																																																																																																				"一次查询页面ID数量不能超过50"), WX_9001031(
																																																																																																																																																																																																						"时间区间不合法"), WX_9001032(
																																																																																																																																																																																																								"保存设备与页面的绑定关系参数错误"), WX_9001033(
																																																																																																																																																																																																										"门店ID不合法"), WX_9001034(
																																																																																																																																																																																																												"设备备注信息过长"), WX_9001035(
																																																																																																																																																																																																														"设备申请参数不合法"), WX_9001036(
																																																																																																																																																																																																																"查询起始值begin不合法"),;

		private String message;

		private codes(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	private String message;
	private Throwable cause;

	public WeiXinException(int code) {
		String c = "WX_" + (code < 0 ? "D" + Math.abs(code) : code);
		try {
			this.message = codes.valueOf(c).getMessage();
		} catch (Exception e) {
			this.message = "unkown code";
		}
		fillInStackTrace();
	}

	public WeiXinException(int code, String message) {
		String c = "WX_" + (code < 0 ? "D" + Math.abs(code) : code);
		try {
			this.message = codes.valueOf(c).getMessage();
		} catch (Exception e) {
			this.message = message;
		}
		fillInStackTrace();
	}

	public WeiXinException(int code, Throwable cause) {
		String c = "WX_" + (code < 0 ? "D" + Math.abs(code) : code);
		try {
			this.message = codes.valueOf(c).getMessage();
		} catch (Exception e) {
			this.message = cause.getMessage();
		}
		this.cause = cause;
		fillInStackTrace();
	}

	public WeiXinException(int code, String message, Throwable cause) {
		String c = "WX_" + (code < 0 ? "D" + Math.abs(code) : code);
		try {
			this.message = codes.valueOf(c).getMessage();
		} catch (Exception e) {
			this.message = message;
		}
		this.cause = cause;
		fillInStackTrace();
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
	@Override
	public synchronized Throwable getCause() {
		return this.cause;
	}
}
