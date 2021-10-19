package cn.crtech.cooperop.bus.message.bean;

import java.io.Serializable;

public class MessageConfigEmail implements Serializable {

	private final static long serialVersionUID = 1L;
	public static final String PROTOCOL="protocol";
	public static final String HOST="host";
	public static final String USERNAME="username";
	public static final String PASSWORD="password";
	public static final String PORT="port";
	public static final String IS_SSL="is_ssl";
	public static final String MAIL_FROM="mail_from";
	public static final String DEBUG="debug";
	public static final String SEPARATOR="separator";

	private String protocol;	 /* 传输协议(数据字典) */
	private String host;	 /* 邮件服务器 */
	private String username;	 /* 用户名 */
	private String password;	 /* 密码 */
	private Long port;	 /* 端口 */
	private Long isSsl;	 /* 是否采用安全登录 */
	private String mailFrom;	 /* 邮件发送者 */
	private Long debug;	 /* 调试模式 */
	private String separator;	 /* 多收件人地址分隔符 */

	public void setProtocol(String protocol){
		this.protocol = protocol;
	}
	public String getProtocol(){
		return this.protocol;
	}
	public void setHost(String host){
		this.host = host;
	}
	public String getHost(){
		return this.host;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public String getUsername(){
		return this.username;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return this.password;
	}
	public void setPort(Long port){
		this.port = port;
	}
	public Long getPort(){
		return this.port;
	}
	public void setIsSsl(Long isSsl){
		this.isSsl = isSsl;
	}
	public Long getIsSsl(){
		return this.isSsl;
	}
	public void setMailFrom(String mailFrom){
		this.mailFrom = mailFrom;
	}
	public String getMailFrom(){
		return this.mailFrom;
	}
	public void setDebug(Long debug){
		this.debug = debug;
	}
	public Long getDebug(){
		return this.debug;
	}
	public void setSeparator(String separator){
		this.separator = separator;
	}
	public String getSeparator(){
		return this.separator;
	}
}