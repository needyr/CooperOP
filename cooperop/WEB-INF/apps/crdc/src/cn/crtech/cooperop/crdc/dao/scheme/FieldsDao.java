package cn.crtech.cooperop.crdc.dao.scheme;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class FieldsDao extends BaseDao {
	public Result query(Map<String, Object> map) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" select  lower(rtrim(f.fdname)) as name,                                      ");
		sql.append("         f.chnname as label,                                     ");
		sql.append("         f.fdtype ,                                      ");
		sql.append("         f.fdsize as size,                                      ");
		sql.append("         f.fddec as digitsize,                                       ");
		sql.append("         f.nouse ,                                       "); //--显示精度
		sql.append("         f.beizhu                                        ");
		sql.append(" from    cr_fldlist f                                       ");
		//sql.append(" where   exists (select g.fdname from cr_gldict g where g.fdname = f.fdname)   ");
		sql.append(" where 1=1                                    ");
		
		if(!CommonFun.isNe(map.get("filter"))){
			map.put("key", "%"+map.get("filter")+"%");
		}
		if(!CommonFun.isNe(map.get("fdname_"))){
			map.put("fdname_", map.get("fdname_")+"%");
		}
		if(!CommonFun.isNe(map.get("chnname_"))){
			map.put("chnname_", "%"+map.get("chnname_")+"%");
		}
		setParameter(map, "filter", " and (f.fdname like :key or f.chnname like :key) ", sql);
		setParameter(map, "chnname_", " and f.chnname like :chnname_ ", sql);
		setParameter(map, "fdname_", " and f.fdname like :fdname_ ", sql);
		setParameter(map, "name", " and f.fdname = :name ", sql);
//		sql.append(" order by f.chnname ,                                    ");
//		sql.append("         f.fdname                                        ");
		if(CommonFun.isNe(map.get("sort"))){
			map.put("sort","label,name");
		}
		if(CommonFun.isNe(map.get("limit"))){
			map.put("limit",10);
		}
		return executeQueryLimit(sql.toString(), map);
	}
}
