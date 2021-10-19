package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class Login_ipcDao extends BaseDao{

	public Result queryMenu(Map<String, Object> map) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ph.*,sp.icon,sp.name  ");
		sql.append(",sp.code,sp.plugin,sp.child_num,p.name  ");
		sql.append(" as system_product_name from personal_homepage ph (nolock)");
		sql.append("left join system_popedom sp (nolock) on cast(sp.id  ");
		sql.append(" as varchar)=ph.fk_id ");
		sql.append("		left join system_product p (nolock) on p.code=sp.plugin   ");
		sql.append("		where ph.type='M' ");
		sql.append("		   and (ph.fk_id in (select distinct system_popedom_id               ");
		sql.append("		                  from system_role_popedom(nolock) srp                   ");
		sql.append("		                  left join system_rule(nolock) sr                       ");
		sql.append("		                    on srp.system_role_id = sr.system_role_id    ");
		sql.append("		               inner join v_system_user vsu (nolock) on sr.system_user_id = vsu.id            ");
		sql.append("		                 where vsu.no = :userid )              ");
		sql.append("		    or ph.fk_id in (select distinct system_popedom_id                ");
		sql.append("		                  from system_role_popedom(nolock) srp                   ");
		sql.append("		inner join  ");
		sql.append("		(select default_role from system_user_type sut (nolock) ");
		sql.append("		       inner join v_system_user vsu (nolock) on vsu.type=sut.type ");
		sql.append("		        where no= :userid ) t on t.default_role = srp.system_role_id     ");
		sql.append("		               )) and  sp.id is not null");
		return executeQuery(sql.toString(), map);
	}

}
