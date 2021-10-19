package cn.crtech.precheck.ipc.audit_def;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.dao.AutoAuditOrdersDao;
import cn.crtech.cooperop.ipc.dao.Sfzl_tpnDao;

public class TPNVerdict extends BaseService{
	
	public List<Map<String, Object>> is_TPN(Map<String, Object> map, List<Record> yzlist) throws Exception {
		try {
			connect("hospital_common");
			List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
			//获取TPN规则
			List<Record> tpnlist = new Sfzl_tpnDao().getTPN(map).getResultset();
			//获取到之后判断病人医嘱是否为空
			if(yzlist.size()<=0){
				return new ArrayList<Map<String, Object>>();
			}
			//将医嘱信息 分到对应的医嘱下
			HashMap<String, ArrayList<Map<String, Object>>> yzmap = new HashMap<String, ArrayList<Map<String, Object>>>();
			for(int i=0;i<yzlist.size();i++) {
				Map<String, Object> r = yzlist.get(i);
				String order_no = (String)r.get("order_no");
				//判断yzmap中是否已存在order_no的key
				if(CommonFun.isNe(yzmap.get(order_no))) {
					//如果为空，则新建一个list 保存当前的record
					ArrayList<Map<String, Object>> yzinfoList = new ArrayList<Map<String, Object>>();
					yzinfoList.add(r);
					//并且在yzmap中添加对应的key
					yzmap.put(order_no, yzinfoList);
				}else {
					//如果不为空 则只需要将当前的record添加到yzmap的value的list中去
					yzmap.get(order_no).add(r);
				}
			}
			//将tpn规则非到对应的tpn组下 跟上面差不多
			//先判断tpn是否为空
			if(tpnlist.size()<=0) {
				return new ArrayList<Map<String, Object>>();
			}
			//循环分组
			HashMap<String, ArrayList<Map<String, Object>>> tpnmap = new HashMap<String, ArrayList<Map<String, Object>>>();
			for(int i=0;i<tpnlist.size();i++) {
				Record r = tpnlist.get(i);
				String tpnzl_id = r.getString("tpnzl_id");
				if(CommonFun.isNe(tpnmap.get(tpnzl_id))) {
					//如果为空，则新建一个list 保存当前的record
					ArrayList<Map<String, Object>> tpninfoList = new ArrayList<Map<String, Object>>();
					tpninfoList.add(r);
					//并且在yzmap中添加对应的key
					tpnmap.put(tpnzl_id, tpninfoList);
				}else {
					tpnmap.get(tpnzl_id).add(r);
				}
			}
			//获取到map中的value
			ArrayList<ArrayList<Map<String, Object>>> yzs = new ArrayList<ArrayList<Map<String, Object>>>(yzmap.values());
			ArrayList<ArrayList<Map<String, Object>>> tpns = new ArrayList<ArrayList<Map<String, Object>>>(tpnmap.values());
			//开始循环判断
			//循环医嘱
			for(ArrayList<Map<String, Object>> yz : yzs) {
				boolean b=true;
				//为了判断通用名称 在这将这个医嘱的所有的药名拼接起来 逗号隔开
				StringBuffer ypnames = new StringBuffer();
				for(Map<String, Object> yp : yz) {
					ypnames.append(yp.get("order_text")+",");
				}
				//循环tpn规则组
				for(ArrayList<Map<String, Object>> tpn : tpns) {
					//循环tpn单条规则
					for(Map<String, Object> tpnr : tpn) {
						String fdname = (String) tpnr.get("fdname");
						//判断是液体量还是给药规则 还是通用名称
						if(fdname==null) {
							break;
						}
						if(fdname.equals("ml_volume")) {
							//这里是液体量规则判断
							b = this.TPNytl(yz, tpnr);
							//有一个不符合跳出本次循环
							if(!b) {
								break;
							}
						}else if(fdname.equals("administration")) {
							//这里是给药方式规则判断
							if(!CommonFun.isNe(yz.get(0).get("administration"))) {
								b = this.compare((String)tpnr.get("value"), (String) tpnr.get("formul"), (String)yz.get(0).get("administration"));
								if(!b) {
									break;
								}
							}
						}else if(fdname.equals("drug_name")){
							//这里是进行通用名称判断
							String val = (String) tpnr.get("value");
							//val = val.replace("%", "");
							val = makeQueryStringAllRegExp(val).replaceAll("%", ".*");
							String formul = (String) tpnr.get("formul");
							if(formul.equals("like")) {
								//b = ypnames.toString().contains(val);
								b = Pattern.matches(val, ypnames.toString());
							}else if(formul.equals("not like")) {
								//b = !(ypnames.toString().contains(val));
								b = !Pattern.matches(val, ypnames.toString());
							}
							if(!b) {
								break;
							}
						}
					}
					//有符合跳出本次循环
					if(b) {
						break;
					}
				}
				//本医嘱符合规则 添加tpn
				if(b) {
					AutoAuditOrdersDao autoAuditOrdersDao = new AutoAuditOrdersDao();
					for (Map<String, Object> r : yz) {
						Map<String, Object> up = new HashMap<String, Object>();
						Map<String, Object> key_order = new HashMap<String, Object>();
						up.put("pzfyjch", "TPN");
						key_order.put("p_key", r.get("p_key"));
						autoAuditOrdersDao.executeUpdate("hospital_autopa..auto_audit_orders", up, key_order);
						//r.put("pzfyjch", "TPN");
						//result.add(r);
					}
				}
				/*else {
					for (Map<String, Object> r : yz) {
						result.add(r);
					}
				}*/
			}
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
		
	}
	
	private boolean TPNytl(ArrayList<Map<String, Object>> yz,Map<String, Object> tpnr) throws Exception {
		BigDecimal sum = new BigDecimal("0");
		Sfzl_tpnDao sfzl_tpnDao = new Sfzl_tpnDao();
		//循环算到该医嘱总的液体量
		for(Map<String, Object> r : yz) {
			Record drug = sfzl_tpnDao.getDrugByOrderCode(r);
			if(!CommonFun.isNe(drug) && !CommonFun.isNe(drug.get("use_dw")) && ("ml".equals(drug.get("use_dw")) || "L".equals(drug.get("use_dw")))) {
				BigDecimal ryt = this.zyt(r,drug);
				sum=sum.add(ryt);
			}
		}
		//判断tpn规则里面的液体量的单位
		//如果是L剩1000 变成ml
		String value="";
		if(!CommonFun.isNe(tpnr.get("unit")) && "L".equals(tpnr.get("unit"))) {
			BigDecimal tmp = new BigDecimal((String)tpnr.get("unit")).multiply(new BigDecimal("1000"));
			value = tmp.toString();
		}else {
			value = (String)tpnr.get("value");
		}
		
		boolean b = this.compare(sum.toString(), (String)tpnr.get("formul"), value);
		//通过tpn的公式计算是否符合规则
		return b;
	}
	
	private BigDecimal zyt(Map<String, Object> ord,Map<String, Object> drug) {
		Object ll = ord.get("dosage");
		if(ll!=null) {
			if("ml".equals(ord.get("dosage_units"))) {
				return new BigDecimal(ll+"");
			}else if("L".equals(ord.get("dosage_units"))){
				return new BigDecimal(ll+"").multiply(new BigDecimal("1000"));
			}else {
				if(ord.get("dosage_units").equals(drug.get("pack_unit"))) {
					if("ml".equals(drug.get("use_dw"))) {
						return new BigDecimal(ll+"")
								.multiply(new BigDecimal((String)drug.get("usejlgg")))
								.multiply(new BigDecimal((String)drug.get("pack_convertnum")));
					}else if("L".equals(drug.get("use_dw"))) {
						return new BigDecimal(ll+"").multiply(new BigDecimal("1000"))
								.multiply(new BigDecimal((String)drug.get("usejlgg")))
								.multiply(new BigDecimal((String)drug.get("pack_convertnum")));
					}
				}else if(ord.get("dosage_units").equals(drug.get("drug_unit"))) {
					if("ml".equals(drug.get("use_dw"))) {
						return new BigDecimal(ll+"")
								.multiply(new BigDecimal((String)drug.get("usejlgg")));
					}else if("L".equals(drug.get("use_dw"))) {
						return new BigDecimal(ll+"").multiply(new BigDecimal("1000"))
								.multiply(new BigDecimal((String)drug.get("usejlgg")));
					}
				}
			}
		}
		return new BigDecimal(0);
	}
	
	// a是规则 ，b是目标项, 符合true, 不符合false
	private boolean compare(String a, String calsign ,String b) {
		boolean t = false;
		// 对比项为null 直接过
		if(CommonFun.isNe(b)) {
			return t;
		}
		// 识别对比标识
		switch(calsign) {
			case "=": if (a.equals(b)) t = true; break;
			case "<>": if (!a.equals(b)) t = true; break;
			case ">=": if(Double.parseDouble(a) >= Double.parseDouble(b)) t = true; break;
			case ">": if(Double.parseDouble(a) > Double.parseDouble(b)) t = true; break;
			case "<=": if(Double.parseDouble(a) <= Double.parseDouble(b)) t = true; break;
			case "<": if(Double.parseDouble(a) < Double.parseDouble(b)) t = true; break;
			case "like": t = javaSQLLike(a, b);break;
			case "not like": t = !javaSQLLike(a, b); break;
			default : log.debug("无法识别的计算符"); break;
		}
		return t;
	}
	
	// 翻译SQL like
	public boolean javaSQLLike(String a, String b) {
		 a = a.toLowerCase();
		 a = a.replace(".",".");
		 a = a.replace("?",".");
		 a = a.replace("%",".*");
		 b = b.toLowerCase();
		return b.matches(a);
	}
	
	/**
     * 转义正则特殊字符 （$()*+.[]?\^{}
     * \\需要第一个替换，否则replace方法替换时会有逻辑bug
     */
    public static String makeQueryStringAllRegExp(String str) {
    	if(CommonFun.isNe(str)) {
    		return str;
    	}
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }
}
