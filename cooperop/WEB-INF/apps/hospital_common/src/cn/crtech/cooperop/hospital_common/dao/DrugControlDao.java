/**
 * 
 */
package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

/**
 * @Desc
 * @parameter 
 * @author yankangkang
 * @reutrn
 * @Date 2019年2月21日 上午9:58:41
 */
public class DrugControlDao extends BaseDao{

	/**药品权限表*/
	private final static String TABLE_NAME = "shengfangzl_drugcontrol";
	/**医院药品*/
	private final static String DICT_HIS_DRUG = "dict_his_drug";
	
	
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select Distinct(b.spbh) , a.*   from "+DICT_HIS_DRUG+" (nolock) a ");
		sql.append(" inner join  "+TABLE_NAME+" (nolock) b ");
		sql.append(" on a.DRUG_CODE=b.spbh where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%"+params.get("filter")+"%");
			sql.append(" and  a.DRUG_name like :filter or  a.INPUT_CODE like :filter");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	//更新上次操作
	public int updateDrug(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("DRUG_CODE", params.remove("DRUG_CODE"));
		return executeUpdate(DICT_HIS_DRUG, params, r);
	}
	
	public Result queryByDrugCode(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT                                                           ");
		sql.append("	a.id,                                                        ");
		sql.append("	a.item,                                                      ");
		sql.append("	a.CONDITION,                                                 ");
		sql.append("	a.message,                                                   ");
		sql.append("	a.remark,                                                    ");
		sql.append("	a.description,                                               ");
		sql.append("	a.beactive,                                                  ");
		sql.append("	a.value_name,                                                  ");
		sql.append("	b.thirdt_name                                                ");
		sql.append("FROM                                                             ");
		sql.append("	"+TABLE_NAME+" ( nolock ) a                         ");
		sql.append("	LEFT JOIN map_common_regulation b (nolock) ON a.zdy_type = b.thirdt_code AND b.product_code = 'ipc'	 AND b.check_type = '2'   ");
		sql.append("WHERE 1 = 1                                                      ");
		sql.append("AND a.spbh = :drug_code                                          ");
		sql.append("AND a.item = :item										 ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	//提示等级
	public Result queryCheckLevel(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT level_code ,level_name FROM sys_check_level (nolock) where  product_code='ipc'");
		return executeQueryLimit(sql.toString(), params);
	}
	
	//问题类别
	public Result queryRegulation(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select thirdt_code , thirdt_name from map_common_regulation (nolock) where  product_code='ipc' and check_type='2' ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("thirdt_name", "%"+params.get("filter")+"%");
			sql.append(" and  thirdt_name like :thirdt_name ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record getLevelName(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT level_name FROM sys_check_level (nolock)  ");
		sql.append(" where product_code='ipc' ");
		sql.append(" and level_code =:levelcode ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getDeptByCode(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select code , name from system_department (nolock) where 1 = 1");
		sql.append(" and code = :code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getDoctorByCode(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.no,a.name,department_name   ");          
		sql.append("from IADSCP..v_system_user a   (nolock)        ");                                       
		sql.append("inner join hospital_common..system_users b (nolock) on a.no=b.doctor_no and b.type='1'");
		sql.append(" and a.no = :code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
	    params.remove("id");
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ");
		sql.append("a.*,b.level_name , c.thirdt_name ");
		sql.append("FROM shengfangzl_drugcontrol a (nolock)  ");
		sql.append("inner join sys_check_level b (nolock) on a.level=b.level_code and product_code='ipc'  ");
		sql.append("left join map_common_regulation c ( nolock ) ON a.zdy_type = c.thirdt_code and c.product_code='ipc'  AND c.check_type = '2' ");
		sql.append(" where 1 = 1");
		sql.append(" and id =:id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
