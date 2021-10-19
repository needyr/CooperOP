package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class Sfzl_tpnDao extends BaseDao{

	public Result query(Record parmas) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from shengfangzl_tpnzb(nolock) t1 INNER JOIN ");
		sql.append(" shengfangzl_tpnzb_mx(nolock) t2 on t1.shengfang_TPNZBID=t2.shengfang_TPNZBID ");
		sql.append(" where t1.beactive='是'  ");
		return executeQuery(sql.toString(),parmas);
	}

	public Result queryDrugMx(Record parmas) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from dict_his_drug_mx(nolock) ");
		return executeQuery(sql.toString(),parmas);
	}

	public Result getformulMapping(Record params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from tpn_formual(nolock) ");
		return executeQuery(sql.toString(),params);
	}
	
	public Result getTPN(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from TPNZL (nolock) a    ");
		sql.append(" inner join tpnzl_rule (nolock) b  ");
		sql.append(" on b.state = '1'                  ");
		sql.append(" and a.TPNZL_ID=b.TPNZL_ID         ");
		sql.append(" where a.state = '1'               ");
		return executeQuery(sql.toString(),params);
	}
	
	public Record getDrugByOrderCode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dict_his_drug (nolock)  "); 
		sql.append("where drug_code = :order_code "); 
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
