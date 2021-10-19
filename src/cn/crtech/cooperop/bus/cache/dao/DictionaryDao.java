package cn.crtech.cooperop.bus.cache.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class DictionaryDao extends BaseDao {
	public Result loadFields() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  lower(rtrim(f.fdname)) as fdname ,                                      ");
		sql.append("         rtrim(f.chnname) as chnname,                                     ");
		sql.append("         f.fdtype ,                                      ");
		sql.append("         f.fdsize ,                                      ");
		sql.append("         f.fddec ,                                       ");
		sql.append("         f.nouse ,                                       "); //--显示精度
		sql.append("         f.beizhu,                                       ");
		sql.append("         f.system_product_code                                        ");
		sql.append(" from    cr_fldlist(nolock) f                                       ");
		sql.append(" order by f.chnname ,                                    ");
		sql.append("         f.fdname                                        ");
		return executeQuery(sql.toString(), null);
	}

	public Result loadAllOptions() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  lower(rtrim(f.fdname)) as fdname ,                                      ");
		sql.append("         f.chnname ,                                     ");
		sql.append("         f.fdtype ,                                      ");
		sql.append("         f.fdsize ,                                      ");
		sql.append("         f.fddec ,                                       ");
		sql.append("         f.nouse ,                                       "); //--显示精度
		sql.append("         f.beizhu ,                                      ");
		sql.append("         f.system_product_code ,                         ");
		sql.append("         g.dictlist ,                                    ");
		sql.append("         g.bianh ,                                    ");
		sql.append("         g.order_no ,                                    ");
		sql.append("         g.pym ,                                         ");
		sql.append("         g.cxsql                                         ");
		sql.append(" from    cr_fldlist(nolock) f                                       ");
		sql.append("         inner join cr_gldict(nolock) g on g.fdname = f.fdname  ");
		//sql.append(" and f.system_product_code=g.system_product_code ");
		sql.append(" order by f.system_product_code,f.chnname ,                                    ");
		sql.append("         f.fdname ,                                      ");
		sql.append("         g.order_no ,                                    ");
		sql.append("         g.dictlist                                      ");
		return executeQuery(sql.toString(), null);
	}

	public int deleteOptions(String fdname, String system_product_code) throws Exception {
		Record conditions = new Record();
		conditions.put("fdname", fdname);
		//conditions.put("system_product_code", system_product_code);
		return executeDelete("cr_gldict", conditions);
	}

	public int insertOptions(String fdname, String system_product_code, Map<String, Object> option) throws Exception {
		option.put("fdname", fdname);
		StringBuffer sql = new StringBuffer();
		sql.append("insert into cr_gldict (system_product_code, fdname, dictlist, pym, bianh, order_no)");
		//sql.append("values(:system_product_code, :fdname, :dictlist, dbo.fun_getPY(:dictlist), :bianh, :order_no)");
		sql.append("values(:fdname, :dictlist, dbo.fun_getPY(:dictlist), :bianh, :order_no)");
		return executeUpdate(sql.toString(), option);
	}
}
