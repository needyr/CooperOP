package cn.crtech.cooperop.bus.interf.ding;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.interf.MyHttpClient;

public class Organizational {
	private static String corpid;
	private static String corpsecret;
	private static String get_access_token;
	
	static {
		corpid = SystemConfig.getSystemConfigValue("cooperop", "corpid");
		corpsecret = SystemConfig.getSystemConfigValue("cooperop", "corpsecret");
		get_access_token = "https://oapi.dingtalk.com/gettoken?corpid="+corpid+"&corpsecret="+corpsecret;
	}
	
	/**
	 * 
	 * @return 
	 * 	department :部门list
	 */
	public Map<String, Object> queryDep(){
		Map<String, Object> dep = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/department/list?access_token="+m.get("access_token");
			dep.putAll(MyHttpClient.get(url));
		}else{
			dep.putAll(m);
		}
		return dep;
	}
	
	/**
	 * 
	 * @return 
	 *	keys | 说明 ---- | -----<br>
	 * errcode | 返回码<br>
	 *  errmsg | 对返回码的文本描述内容 <br>
	 *  id | 部门id <br>
	 *  name | 部门名称 <br>
	 *  parentid | 父部门id，根部门为1 <br>
	 *  order | 在父部门中的次序值 <br>
	 *  createDeptGroup | 是否同步创建一个关联此部门的企业群, true表示是, false表示不是 <br>
	 *  autoAddUser | 当群已经创建后，是否有新人加入部门会自动加入该群, true表示是, false表示不是 <br>
	 *  deptHiding | 是否隐藏部门, true表示隐藏, false表示显示 <br>
	 *  deptPerimits | 可以查看指定隐藏部门的其他部门列表，如果部门隐藏，则此值生效，取值为其他的部门id组成的的字符串，使用|符号进行分割 <br>
	 *  userPerimits | 可以查看指定隐藏部门的其他人员列表，如果部门隐藏，则此值生效，取值为其他的人员userid组成的的字符串，使用|符号进行分割 <br>
	 *  outerDept | 是否本部门的员工仅可见员工自己, 为true时，本部门员工默认只能看到员工自己 <br>
	 *  outerPermitDepts | 本部门的员工仅可见员工自己为true时，可以配置额外可见部门，值为部门id组成的的字符串，使用|符号进行分割 <br>
	 *  outerPermitUsers | 本部门的员工仅可见员工自己为true时，可以配置额外可见人员，值为userid组成的的字符串，使用| 符号进行分割 <br>
	 *  orgDeptOwner | 企业群群主 <br>
	 *  deptManagerUseridList | 部门的主管列表,取值为由主管的userid组成的字符串，不同的userid使用|符号进行分割
	 */
	public Map<String, Object> getDep(){
		Map<String, Object> dep = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/department/list?access_token="+m.get("access_token");
			dep.putAll(MyHttpClient.get(url));
		}else{
			dep.putAll(m);
		}
		return dep;
	}
	
	/**
	 * @param dep
	 *	keys| 参数类型 | 必须 | 说明 ----------| ------- | ------- | ------ <br>
	 *name | String | 是 | 部门名称。长度限制为1~64个字符 <br>
	 *parentid | String | 是 | 父部门id。根部门id为1 <br>
	 *order | String | 否 | 在父部门中的次序值。order值小的排序靠前 <br>
	 *createDeptGroup | Boolean | 否 | 是否创建一个关联此部门的企业群，默认为false <br>
	 *deptHiding | Boolean | 否 | 是否隐藏部门, true表示隐藏, false表示显示 <br>
	 *deptPerimits | String | 否 | 可以查看指定隐藏部门的其他部门列表，如果部门隐藏，则此值生效，取值为其他的部门id组成的的字符串，使用 | 符号进行分割 <br>
	 *userPerimits | String | 否 | 可以查看指定隐藏部门的其他人员列表，如果部门隐藏，则此值生效，取值为其他的人员userid组成的的字符串，使用| 符号进行分割 <br>
	 *outerDept | Boolean | 否 | 是否本部门的员工仅可见员工自己, 为true时，本部门员工默认只能看到员工自己 <br>
	 *outerPermitDepts | String | 否 | 本部门的员工仅可见员工自己为true时，可以配置额外可见部门，值为部门id组成的的字符串，使用|符号进行分割<br>
	 * outerPermitUsers | String | 否 | 本部门的员工仅可见员工自己为true时，可以配置额外可见人员，值为userid组成的的字符串，使用|符号进行分割<br>
	 * 
	 * @return 
	 *keys | 说明 ---------- | ------<br> errcode | 返回码<br> errmsg | 对返回码的文本描述内容<br> id | 创建的部门id
	 */
	public Map<String, Object> createDep(Map<String, Object> dep){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/department/create?access_token="+m.get("access_token");
			back.putAll(MyHttpClient.post(url, dep));
		}else{
			back.putAll(m);
		}
		return back;
	}
	
	/**
	 * 
	 * @param dep
	 * keys | 参数类型 | 必须 | 说明 ----------| ------- | ------- | ------ <br>
	 * name | String | 否 | 部门名称。长度限制为1~64个字符 <br>
	 * parentid | String | 否 | 父部门id。根部门id为1 <br>
	 * order | String | 否 | 在父部门中的次序值。order值小的排序靠前 <br>
	 * id | long | 是 | 部门id <br>
	 * createDeptGroup | Boolean | 否 | 是否创建一个关联此部门的企业群 <br>
	 * autoAddUser | Boolean | 否 | 如果有新人加入部门是否会自动加入部门群 <br>
	 * deptManagerUseridList | String | 否 | 部门的主管列表,取值为由主管的userid组成的字符串，不同的userid使用'| 符号进行分割 <br>
	 * deptHiding | Boolean | 否 | 是否隐藏部门, true表示隐藏, false表示显示<br>
	 *  deptPerimits | String | 否 | 可以查看指定隐藏部门的其他部门列表，如果部门隐藏，则此值生效，取值为其他的部门id组成的的字符串，使用 | 符号进行分割 <br>
	 *  userPerimits | String | 否 | 可以查看指定隐藏部门的其他人员列表，如果部门隐藏，则此值生效，取值为其他的人员userid组成的的字符串，使用| 符号进行分割 <br>
	 *  outerDept | Boolean | 否 | 是否本部门的员工仅可见员工自己, 为true时，本部门员工默认只能看到员工自己 <br>
	 *  outerPermitDepts | String | 否 | 本部门的员工仅可见员工自己为true时，可以配置额外可见部门，值为部门id组成的的字符串，使用|符号进行分割<br>
	 *   outerPermitUsers | String | 否 | 本部门的员工仅可见员工自己为true时，可以配置额外可见人员，值为userid组成的的字符串，使用|符号进行分割 <br>
	 *   orgDeptOwner | String | 否 | 企业群群主<br>
	 * @return
	 *keys | 说明 ---------- | ------<br> errcode | 返回码<br> errmsg | 对返回码的文本描述内容
	 */
	public Map<String, Object> updateDep(Map<String, Object> dep){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/department/update?access_token="+m.get("access_token");
			back.putAll(MyHttpClient.post(url, dep));
		}else{
			back.putAll(m);
		}
		return back;
	}
	/**
	 * @param id
	 * id | long | 是 | 部门id。（注：不能删除根部门；不能删除含有子部门、成员的部门，钉钉上部门的id）<br>
	 * @return
	 * keys | 说明 ---------- | ------<br> errcode | 返回码<br> errmsg | 对返回码的文本描述内容
	 */
	public Map<String, Object> deleteDep(String id){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/department/delete?access_token="+m.get("access_token")+"&id="+id;
			back.putAll(MyHttpClient.get(url));
		}else{
			back.putAll(m);
		}
		return back;
	}
	
	/**
	 * @param unionid
	 *unionid | String |是 | 用户在当前钉钉开放平台账号范围内的唯一标识，同一个钉钉开放平台账号可以包含多个开放应用，同时也包含ISV的套件应用及企业应用<br>
	 * @return
	 * keys | 说明 ---------- | ------<br> errcode | 返回码<br> errmsg | 对返回码的文本描述内容<br>userid | 员工唯一标识ID（不可修改）
	 */
	public Map<String, Object> getUserid(String unionid){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/user/getUseridByUnionid?access_token="+m.get("access_token")+"&unionid="+unionid;
			back.putAll(MyHttpClient.get(url));
		}else{
			back.putAll(m);
		}
		return back;
	}
	
	/**
	 * @param userid
	 *userid | String |是 | 员工在企业内的UserID，企业用来唯一标识用户的字段。 <br>
	 * @return
	 * keys |说明 ---------- | ------ <br>
	 * errcode | 返回码 <br>
	 * errmsg | 对返回码的文本描述内容 <br>
	 * userid | 员工唯一标识ID（不可修改）<br>
	 * name | 成员名称<br>
	 * tel | 分机号<br>
	 * workPlace | 办公地点 <br>
	 * remark | 备注<br>
	 * mobile | 手机号码<br>
	 * email | 员工的电子邮箱<br>
	 * orgEmail | 员工的企业邮箱<br>
	 * active | 是否已经激活, true表示已激活, false表示未激活 <br>
	 *    orderInDepts | 在对应的部门中的排序, Map结构的json字符串,key是部门的Id, value是人员在这个部门的排序值 <br>
	 *    isAdmin | 是否为企业的管理员, true表示是, false表示不是 <br>
	 *    isBoss | 是否为企业的老板, true表示是, false表示不是 <br>
	 *    dingId | 钉钉Id,在钉钉全局范围内标识用户的身份,但用户可以自行修改一次 <br>
	 *    unionid | 在当前isv全局范围内唯一标识一个用户的身份,用户无法修改 <br>
	 *    isLeaderInDepts | 在对应的部门中是否为主管, Map结构的json字符串, key是部门的Id, value是人员在这个部门中是否为主管, true表示是, false表示不是 <br>
	 *    isHide | 是否号码隐藏, true表示隐藏, false表示不隐藏<br>
	 *    department | 成员所属部门id列表 <br>
	 *    position | 职位信息 <br>
	 *    avatar | 头像url <br>
	 *    jobnumber | 员工工号 <br>
	 * extattr | 扩展属性，可以设置多种属性(但手机上最多只能显示10个扩展属性，具体显示哪些属性，请到OA管理后台->设置->通讯录信息设置和OA管理后台->设置->手机端显示信息设置)性<br>
	 */
	public Map<String, Object> getUser(String userid){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/user/get?access_token="+m.get("access_token")+"&userid="+userid;
			back.putAll(MyHttpClient.get(url));
		}else{
			back.putAll(m);
		}
		return back;
	}
	
	/**
	 * 
	 * @param user
	 * keys | 参数类型 | 必须 | 说明 ---------- | ------- | ------- | ------ <br>
	 * userid | String| 否 | 员工唯一标识ID（不可修改），企业内必须唯一。长度为1~64个字符，如果不传，服务器将自动生成一个userid <br>
	 * name | String | 是 | 成员名称。长度为1~64个字符 <br>
	 * orderInDepts | JSONObject | 否 | 在对应的部门中的排序, Map结构的json字符串, key是部门的Id, value是人员在这个部门的排序值 <br>
	 * department | List | 是 |数组类型，数组里面值为整型，成员所属部门id列表 <br>
	 * position |String | 否 | 职位信息。长度为0~64个字符 <br>
	 * mobile | String| 是 | 手机号码。企业内必须唯一 <br>
	 * tel | String| 否 | 分机号，长度为0~50个字符 <br>
	 * workPlace | String| 否 | 办公地点，长度为0~50个字符 <br>
	 * remark | String| 否 | 备注，长度为0~1000个字符 <br>
	 * email | String| 否 | 邮箱。长度为0~64个字符。企业内必须唯一 <br>
	 * jobnumber | String | 否 | 员工工号。对应显示到OA后台和客户端个人资料的工号栏目。长度为0~64个字符 <br>
	 * isHide | Boolean| 否 | 是否号码隐藏, true表示隐藏, false表示不隐藏。隐藏手机号后，手机号在个人资料页隐藏，但仍可对其发DING、发起钉钉免费商务电话。 <br>
	 * isSenior | Boolean| 否 | 是否高管模式，true表示是，false表示不是。开启后，手机号码对所有员工隐藏。普通员工无法对其发DING、发起钉钉免费商务电话。高管之间不受影响。 <br>
	 * extattr | JSONObject | 否 | 扩展属性，可以设置多种属性(但手机上最多只能显示10个扩展属性，具体显示哪些属性，请到OA管理后台->设置->通讯录信息设置和OA管理后台->设置->手机端显示信息设置)<br>
	 * @return
	 * keys | 说明 ---------- | ------<br> errcode | 返回码<br> errmsg | 对返回码的文本描述内容<br>userid | 员工唯一标识ID（不可修改）
	 */
	public Map<String, Object> createUser(Map<String, Object> user){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/user/create?access_token="+m.get("access_token");
			back.putAll(MyHttpClient.post(url, user));
		}else{
			back.putAll(m);
		}
		return back;
	}
	
	/**
	 * 
	 * @param user
	 * keys | 参数类型 | 必须 | 说明 ---------- | ------- | ------- | ------ <br>
	 * userid |String | 是 | 员工唯一标识ID（不可修改），企业内必须唯一。长度为1~64个字符 <br>
	 * name |String | 是 | 成员名称。长度为1~64个字符 <br>
	 * department |List | 否 | 成员所属部门id列表 <br>
	 * orderInDepts | JSONObject | 否 | 实际是Map的序列化字符串，Map的Key是deptId，表示部门id，Map的Value是order，表示排序的值，列表是按order的倒序排列输出的，即从大到小排列输出的<br> 
	 * position | String| 否 | 职位信息。长度为0~64个字符 <br>
	 * mobile |String | 否 | 手机号码。企业内必须唯一 <br>
	 * tel | String| 否 | 分机号，长度为0~50个字符 <br>
	 * workPlace | String| 否 | 办公地点，长度为0~50个字符<br> 
	 * remark | String| 否 | 备注，长度为0~1000个字符 <br>
	 * email |String | 否 | 邮箱。长度为0~64个字符。企业内必须唯一<br> 
	 * jobnumber | String | 否 | 员工工号，对应显示到OA后台和客户端个人资料的工号栏目。长度为0~64个字符 <br>
	 * isHide | Boolean| 否 | 是否号码隐藏, true表示隐藏, false表示不隐藏。隐藏手机号后，手机号在个人资料页隐藏，但仍可对其发DING、发起钉钉免费商务电话。 <br>
	 * isSenior | Boolean| 否 | 是否高管模式，true表示是，false表示不是。开启后，手机号码对所有员工隐藏。普通员工无法对其发DING、发起钉钉免费商务电话。高管之间不受影响。 <br>
	 * extattr |JSONObject | 否 | 扩展属性，可以设置多种属性(但手机上最多只能显示10个扩展属性，具体显示哪些属性，请到OA管理后台->设置->通讯录信息设置和OA管理后台->设置->手机端显示信息设置)<br>
	 * @return
	 * keys | 说明 ---------- | ------<br> errcode | 返回码<br> errmsg | 对返回码的文本描述内容
	 */
	public Map<String, Object> updateUser(Map<String, Object> user){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/user/update?access_token="+m.get("access_token");
			back.putAll(MyHttpClient.post(url, user));
		}else{
			back.putAll(m);
		}
		return back;
	}
	
	public Map<String, Object> deleteUser(String userid){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/user/delete?access_token="+m.get("access_token")+"&userid="+userid;
			back.putAll(MyHttpClient.get(url));
		}else{
			back.putAll(m);
		}
		return back;
	}
	
	/**
	 * 
	 * @param user
	 * keys | 参数类型 | 必须 | 说明 ---------- | ------- | ------- | ------ <br>
	 * useridlist | List | 是 | 员工UserID列表。列表长度在1到20之间<br>
	 * eg: {"useridlist":["zhangsan","lisi"]}
	 * @return
	 * keys | 说明 ---------- | ------<br> errcode | 返回码<br> errmsg | 对返回码的文本描述内容
	 */
	public Map<String, Object> batchDeleteUser(Map<String, Object> user){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/user/batchdelete?access_token="+m.get("access_token");
			back.putAll(MyHttpClient.post(url, user));
		}else{
			back.putAll(m);
		}
		return back;
	}
	/**
	 * 
	 * @param dep_id
	 * @return
	 * userlist 人员list
	 */
	public Map<String, Object> querySimpleUserByDepId(String dep_id){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/user/simplelist?access_token="+m.get("access_token")+"&department_id="+dep_id;
			back.putAll(MyHttpClient.get(url));
		}else{
			back.putAll(m);
		}
		return back;
	}
	/**
	 * 
	 * @param dep_id
	 * @return
	 * userlist 人员list
	 */
	public Map<String, Object> queryUserByDepId(String dep_id){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/user/list?access_token="+m.get("access_token")+"&department_id="+dep_id;
			back.putAll(MyHttpClient.get(url));
		}else{
			back.putAll(m);
		}
		return back;
	}
	/**
	 * 
	 * @return
	 * adminList 人员list
	 */
	public Map<String, Object> getAdmin(){
		Map<String, Object> back = new HashMap<String, Object>();
		Map<String, Object> m = MyHttpClient.get(get_access_token);
		if("0".equals(m.get("errcode").toString())){
			String url = "https://oapi.dingtalk.com/user/get_admin?access_token="+m.get("access_token");
			back.putAll(MyHttpClient.get(url));
		}else{
			back.putAll(m);
		}
		return back;
	}
	public static void main(String[] args) {
		System.out.println(new Organizational().getUser("01512838111076239"));
	}
}
