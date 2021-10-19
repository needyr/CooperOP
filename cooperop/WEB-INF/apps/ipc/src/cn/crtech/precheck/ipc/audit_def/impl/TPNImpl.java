package cn.crtech.precheck.ipc.audit_def.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.singularsys.jep.Jep;
import com.singularsys.jep.JepException;
import com.singularsys.jep.bigdecimal.BigDecComponents;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.service.CheckResultInfoService;
import cn.crtech.cooperop.ipc.service.CheckResultService;
import cn.crtech.cooperop.ipc.service.Sfzl_tpnService;
import cn.crtech.precheck.ipc.audit_def.AuditInterface;
import cn.crtech.precheck.ipc.audit_def.MemoryCache;

public class TPNImpl implements AuditInterface {

	@Override
	public List<Map<String, Object>> call(Map<String, Object> info, Record config) throws Exception {
		//System.out.println("----------------tpn问题审查---------------------------------");
		boolean pass = true;
		// 数据补充和计算
		ordersComAdd();
		// 待审查的药品列
		List<Record> ingOrdlist = new ArrayList<Record>();
		ingOrdlist = info.get("orders") == null ? new ArrayList<Record>(): (List<Record>)info.get("orders");
		// 重组医嘱结构 医嘱列表 => 医嘱组列表 > 医嘱列表
		List<List<Record>> ordGroups = new ArrayList<List<Record>>();
		for (Record ord : ingOrdlist) {
			boolean newGroup = true;
			for (List<Record> gro : ordGroups) {
				if(ord.getString("order_no").equals(gro.get(0).getString("order_no"))) {
					gro.add(ord);
					newGroup = false;
				}
			}
			if(newGroup) {
				if("TPN".equals(ord.get("pzfyjch"))) {
					List<Record> ords = new ArrayList<Record>();
					ords.add(ord);
					ordGroups.add(ords);
				}
			}
		}
		// 规则列表
		Map<String,List<Record>> drugRule = MemoryCache.getTPN_RULES();
		Map<String,Object> drugmx = MemoryCache.getDRUGMX();
		//这里获取药品属性 name-code 映射
		Map<String,String> mapping = MemoryCache.getTPN_FORMUAL_MAPPING();
		Object object = info.get("basicinfo");
		Record pp = (Record)object;
		if(CommonFun.isNe(pp.get("age_zdy"))) {
			return null;
		}
		double age = pp.getDouble("age_zdy");
		//循环获取tpn规则的年龄 来匹配病人该用哪个tpn规则
		Set<String> keySet = drugRule.keySet();
		//查找该病人适用的tpn规则组
		ArrayList<List<Record>> meettpn = new ArrayList<List<Record>>();
		for(String key : keySet) {
			//获取全部tpn规则组中的一个规则组
			List<Record> list = drugRule.get(key);
			//判断病人的年龄在这个规则组里面没有
			//先获取这个规则组的开始年龄和结束年龄
			double tpnsage = list.get(0).getDouble("nianl_start_tpnzb");
			double tpneage = list.get(0).getDouble("nianl_end_tpnzb");
			//判断病人年龄是否在这个区间			nianl_end_tpnzb
			if(age>=tpnsage&&age<=tpneage) {
				//如果病人在这个区间 就表示适用这个tpn规则
//				for(Record r : list) {
//					//这里是循环规则组里面的每条规则 
//					//这里进行tpn规则的判断
//				}
				meettpn.add(list);
			}
		}
		//如果病人满足多个tpn 那么就需要判断有没有重复的规则 如果有就需要保留级别高的 删除级别低的
		if(meettpn.size()>1) {
			for (int i = 0; i < meettpn.size()-1; i++) {
				List<Record> a = meettpn.get(i);
				for (int j = i+1; j < meettpn.size(); j++) {
					List<Record> b = meettpn.get(j);
					for (int k = 0; k < a.size(); k++) {
						String aname = a.get(k).getString("tpnzb_name");
						for (int l = 0; l < b.size(); l++) {
							String bname = b.get(l).getString("tpnzb_name");
							if(aname.equals(bname)){
								int alevel = a.get(k).getInt("level");
								int blevel = b.get(l).getInt("level");
								if(alevel>blevel){
									meettpn.get(j).get(l).put("delete", 1);
									break;
								}else{
									meettpn.get(i).get(k).put("delete",1);
									break;
								}
							}
						}
					}  
				}
			}
		}
		
		// 审查结果
		List<Map<String, Object>> reslist = new ArrayList<Map<String, Object>>();
		//雷达图表的
		List<Record> tulist = new ArrayList<Record>();
		
		//循环判断yz是否满足条件
		for(List<Record> yz: ordGroups) {
			boolean is_tpn=true;
			String desc="";
			String tpnzbid="";
			//循环满足的规则组
			for(List<Record> tpns : meettpn) {
				//循环每条规则
				for(Record r : tpns) {
					//假如这个规则有delete属性 则表示该病人有多个规则组有这个规则，并且这个规则还是在优先级低的规则组下，不进行计算
					if(!CommonFun.isNe(r.get("delete"))) {
						continue;
					}
					// r 为每条ytpn规则 就是shengfangzl_tpnzb_mx的数据（当然每条数据也带有他父表对应的数据：比如开始年龄等）
					//获取到规则名称
					String name = r.getString("tpnzb_name");
					BigDecimal tpnzb_min =  new BigDecimal(r.getString("tpnzb_min"));
					BigDecimal tpnzb_max =  new BigDecimal(r.getString("tpnzb_max"));
					BigDecimal show_bl =  new BigDecimal(r.getString("show_bl"));
					tpnzbid=r.getString("shengfang_tpnzbid");
					String formul_code = r.getString("formul_tpnzbs");
					//这里调用动态公式进行计算
					BigDecimal sum = parseformul(formul_code, mapping, yz, drugmx,name);
					//与规则的进行比对
					if(sum.compareTo(tpnzb_min)<=-1||sum.compareTo(tpnzb_max)>=1) {
						is_tpn=false;
						desc=desc+" "+name;
					}
					//将每次规则对比完进行存表
					//全都存表 雷达图的表  cr_tmp_fenxitux_radarchart
					Record re = new Record();
					re.put("patient_id", yz.get(0).get("patient_id"));
					re.put("visit_id", yz.get(0).get("visit_id"));
					re.put("group_id", yz.get(0).get("group_id"));
					re.put("order_no", yz.get(0).get("order_no"));
					re.put("fdname", name);
					re.put("value_current", sum);
					re.put("value_lowest", tpnzb_min);
					re.put("value_highest", tpnzb_max);
					re.put("show_value_current", sum.divide(show_bl,4,BigDecimal.ROUND_HALF_DOWN));
					re.put("show_value_lowest", tpnzb_min.divide(show_bl,4,BigDecimal.ROUND_HALF_DOWN));
					re.put("show_value_highest", tpnzb_max.divide(show_bl,4,BigDecimal.ROUND_HALF_DOWN));
					re.put("show_fdname", name+" "+r.getString("show_dw"));
					re.put("auto_audit_id", info.get("auto_audit_id"));
					tulist.add(re);
				}
			}
			if(!is_tpn) {
				//表示该医嘱至少不满足一条规则
				Record tpmr = new Record();
				CheckResultService crd = new CheckResultService();
				CheckResultInfoService crid = new CheckResultInfoService();
    			Map<String, Object> resultMap = new HashMap<String, Object>();
    			String rid;
        		resultMap.put("auto_audit_id", info.get("auto_audit_id"));
        		tpmr.put("auto_audit_id", info.get("auto_audit_id"));
        		resultMap.put("keyword", "84");
        		tpmr.put("keyword", "83");
        		resultMap.put("type", "2");
        		tpmr.put("type", "2");
        		rid = crd.insert(resultMap);
        		Map<String, Object> InfoMap = new HashMap<String, Object>();
    			InfoMap.put("auto_audit_id", info.get("auto_audit_id"));
    			InfoMap.put("parent_id", rid);
    			tpmr.put("parent_id", rid);
    			InfoMap.put("level", "3");
    			tpmr.put("level", "3");
    			InfoMap.put("reference", "tpn审查");
    			tpmr.put("reference", "tpn审查");
    			InfoMap.put("description", desc+" 不满足！");
    			tpmr.put("description", desc+" 不满足！");
    			
    			StringBuffer order_id = new StringBuffer();
    			for (int i = 0; i < yz.size(); i++) {
					if(i == 0) {
						order_id.append(yz.get(i).get("p_key"));
					}else {
						order_id.append("," + yz.get(i).get("p_key"));
					}
				}
    			
    			InfoMap.put("order_id", order_id.toString());
    			tpmr.put("order_id", order_id.toString());
    			crid.insert(InfoMap);
    			reslist.add(tpmr);
			}else if(tulist.size() > 0){
				//表示该医嘱满足全部的规则
				Record tpmr = new Record();
				CheckResultService crd = new CheckResultService();
				CheckResultInfoService crid = new CheckResultInfoService();
    			Map<String, Object> resultMap = new HashMap<String, Object>();
    			String rid;
        		resultMap.put("auto_audit_id", info.get("auto_audit_id"));
        		tpmr.put("auto_audit_id", info.get("auto_audit_id"));
        		resultMap.put("keyword", "84");
        		tpmr.put("keyword", "83");
        		resultMap.put("type", "2");
        		tpmr.put("type", "2");
        		rid = crd.insert(resultMap);
        		Map<String, Object> InfoMap = new HashMap<String, Object>();
    			InfoMap.put("auto_audit_id", info.get("auto_audit_id"));
    			InfoMap.put("parent_id", rid);
    			tpmr.put("parent_id", rid);
    			InfoMap.put("level", "1");
    			tpmr.put("level", "1");
    			InfoMap.put("reference", "tpn审查");
    			tpmr.put("reference", "tpn审查");
    			InfoMap.put("description", "TPN指标结果查看。");
    			tpmr.put("description", "TPN指标结果查看。");
    			StringBuffer order_id = new StringBuffer();
    			for (int i = 0; i < yz.size(); i++) {
					if(i == 0) {
						order_id.append(yz.get(i).get("p_key"));
					}else {
						order_id.append("," + yz.get(i).get("p_key"));
					}
				}
    			InfoMap.put("order_id", order_id.toString());
    			tpmr.put("order_id", order_id.toString());
    			crid.insert(InfoMap);
    			reslist.add(tpmr);
			}
		}
		if(tulist.size()>0) {
			try {
				new Sfzl_tpnService().insertRadarChart(tulist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return reslist;
	}
	
	public void ordersComAdd() {
		 // 医嘱数据补充（原本不存在的信息，通过计算得出）；统计数据计算
	}

	public static BigDecimal parseformul(String f,Map<String,String> mapping,List<Record> yz,Map<String,Object> drugmx,String name) throws JepException {
		//如果规则公式为空 返回0
		if(f==null) {
			return new BigDecimal("0");
		}
		String[] arr = f.split("[+,-,*,/,(,)]");
		Jep jep = new Jep(new BigDecComponents(MathContext.DECIMAL32, true));
		for (int i = 0; i < arr.length; i++) {
			String s = arr[i].trim();
			if(!is_number(s)) {
				//判断是否是液体总量
				if(arr[i].equals("lq")) {
					BigDecimal zbvalue = ytlJS(yz);
					jep.addVariable(s, zbvalue);
					
				}else {
					//这里是公式里的一个元素不是数字或液体量的话，那么就拿这个去找到对应的中文
					String chinese = mapping.get(s);
					//这里得到该元素对应的中文名在医嘱中的值
					BigDecimal zbvalue = jszb(yz, drugmx, chinese);
					//这里开始给jep公式未知数赋值
					jep.addVariable(s, zbvalue);
				}
			}
		}
		BigDecimal evaluate =new BigDecimal("0");
		try {
			jep.parse(f);
			evaluate = (BigDecimal) jep.evaluate();
		}catch (Exception e) {
			//log.error("tpn公式解析错误"+f);
			//e.printStackTrace();
			evaluate =new BigDecimal("0");
		}
		return evaluate;
	}
	
	public static boolean is_number(String s) {
		if(s!=null&&s.length()>0) {
			if(s.matches("[a-zA-Z]+")) {
				return false;			
			}
		}
		return true;
	}
	
	//计算液体总量 
	public static BigDecimal ytlJS(List<Record> yz) {
		BigDecimal sum = new BigDecimal("0.0");
		for(Record r:yz) {
			Object ll = r.get("dosage");
			if(ll!=null) {
				if("ml".equals(r.get("dosage_units"))) {
					sum = sum.add(new BigDecimal(ll+""));
				}else if("L".equals(r.get("dosage_units"))) {
					sum = sum.add(new BigDecimal(ll+"").multiply(new BigDecimal("1000")));
				}else if("ml".equals(r.get("drug_use_dw")) || "L".equals(r.get("drug_use_dw"))) {
					if(r.get("dosage_units").equals(r.get("drug_common_unit"))) {
						Object drug_usejlgg = r.get("drug_usejlgg");
						if("ml".equals(r.get("drug_use_dw"))) {
							sum = sum.add(new BigDecimal(ll+"").multiply(new BigDecimal(drug_usejlgg+"")));
						}else if("L".equals(r.get("drug_use_dw"))) {
							sum = sum.add(new BigDecimal(ll+"").multiply(new BigDecimal("1000")).multiply(new BigDecimal(drug_usejlgg+"")));
						}
					}else if(r.get("dosage_units").equals(r.get("drug_pack_unit"))) {
						Object drug_usejlgg = r.get("drug_usejlgg");
						Object drug_pack_convertnum = r.get("drug_pack_convertnum");
						if("ml".equals(r.get("drug_use_dw"))) {
							sum = sum.add(new BigDecimal(ll+"")
									.multiply(new BigDecimal(drug_usejlgg+""))
									.multiply(new BigDecimal(drug_pack_convertnum+"")));
						}else if("L".equals(r.get("drug_use_dw"))) {
							sum = sum.add(new BigDecimal(ll+"")
									.multiply(new BigDecimal("1000"))
									.multiply(new BigDecimal(drug_usejlgg+""))
									.multiply(new BigDecimal(drug_pack_convertnum+"")));
						}
					}
				}
			}
		}
		return sum;
	}
	
	//计算每个总量的方法（后加）
	public static BigDecimal jszb(List<Record> yz,Map<String,Object> drugmx,String zb) {		
		BigDecimal sum = new BigDecimal("0");
		for(Record r : yz) {
			String order_code = r.getString("order_code");
			String dosage = r.getString("dosage");
			String drug_Usejlgg = r.getString("drug_usejlgg");
			//DICT_HIS_DRUG_MX表中的value值		
			Object object = drugmx.get(order_code+"-"+zb);
			if(!CommonFun.isNe(object) && !CommonFun.isNe(dosage) && !CommonFun.isNe(drug_Usejlgg) ) {
				BigDecimal zbvalue = new BigDecimal(object+"").multiply(new BigDecimal(dosage)).divide(new BigDecimal(drug_Usejlgg));			
				sum = sum.add(zbvalue);
			}
		}
		return sum;
	}
	
	
}
