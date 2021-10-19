package cn.crtech.cooperop.hospital_common.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SysdeptDao extends BaseDao {
	
	public Result queryTree(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		StringBuffer sql3 = new StringBuffer();
		
		if(CommonFun.isNe(params.get("filter")) && CommonFun.isNe(params.get("datasouce"))) {
			sql.append("select ");
			sql.append(" f.*   ");
			sql.append("from system_department (nolock) f ");
			sql.append(" where 1=1 ");
			return executeQuery(sql.toString(), params);
		}else{
			params.put("filter", "%" +params.get("filter")+ "%");
			sql3.append("with CTEGetChild as                             ");
			sql3.append("(                                               ");
			sql3.append("select f.* from system_department(nolock) f        ");
			sql3.append(" inner join dict_his_deptment (nolock) dhd on f.code=dhd.dept_CODE ");
			sql3.append("where   1=1                          ");
			if(!CommonFun.isNe(params.get("filter"))) {
				sql3.append(" and (f.code like :filter or f.name like :filter) ");
			}
			if (!CommonFun.isNe(params.get("datasouce"))) {
				if(params.get("datasouce") instanceof String) {
					sql3.append(" and dhd.outp_or_inp = :datasouce ");
				}else {
					String[] ss = (String[])params.get("datasouce");
					sql3.append(" and dhd.outp_or_inp in ( ");
					int i= 1;
					for (String string : ss) {
						if(i == 1) {
							sql3.append("'"+string+"'");
							i++;
						}else {
							sql3.append(",'"+string+"'");
						}
					}
					sql3.append(" ) ");
				}
			}
			sql3.append("UNION ALL                                       ");
			sql3.append("SELECT a.* from system_department(nolock)       ");
			sql3.append("as a inner join                                 ");
			sql3.append("CTEGetChild as b on a.parent_id=b.id     ");
			sql3.append(")                                               ");
			sql3.append("select a.*                       ");
			sql3.append(" from CTEGetChild a; ");
			
			sql2.append("with CTEGetChild as                             ");
			sql2.append("(                                               ");
			sql2.append("select f.* from system_department(nolock)   f      ");
			sql2.append(" inner join dict_his_deptment (nolock) dhd on f.code=dhd.dept_CODE ");
			sql2.append("where    1=1                         ");
			if(!CommonFun.isNe(params.get("filter"))) {
				sql2.append(" and (f.code like :filter or f.name like :filter)");
			}
			if (!CommonFun.isNe(params.get("datasouce"))) {
				if(params.get("datasouce") instanceof String) {
					sql2.append(" and dhd.outp_or_inp = :datasouce ");
				}else {
					String[] ss = (String[])params.get("datasouce");
					sql2.append(" and dhd.outp_or_inp in ( ");
					int i= 1;
					for (String string : ss) {
						if(i == 1) {
							sql2.append("'"+string+"'");
							i++;
						}else {
							sql2.append(",'"+string+"'");
						}
					}
					sql2.append(" ) ");
				}
			}
			sql2.append("UNION ALL                                       ");
			sql2.append("SELECT a.* from system_department(nolock)       ");
			sql2.append("as a inner join                                 ");
			sql2.append("CTEGetChild as b on a.id=b.parent_id     ");
			sql2.append(")                                               ");
			sql2.append("select a.*                       ");
			sql2.append(" from CTEGetChild a; ");
			Result executeQuery2 = executeQuery(sql2.toString(), params);
			List<Record> resultset3 = executeQuery(sql3.toString(), params).getResultset();
			List<Record> resultset2 = executeQuery2.getResultset();
			List<Record> re = new ArrayList<Record>();
			for (Record record : resultset3) {
				if(check_cf(re,record,"code")) {
					re.add(record);
				}
			}
			for (Record record : resultset2) {
				if(check_cf(re,record,"code")) {
					re.add(record);
				}
			}
			Result que = new Result();
			que.setResultset(re);
			return que;
		}
	}
	
	public boolean check_cf(List<Record> list, Record re, String id) {
		for (Record record : list) {
			if(record.get(id).equals(re.get(id))) {
				return false;
			}
		}
		return true;
	}
}
