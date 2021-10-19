package cn.crtech.cooperop.hospital_common.dao.guide;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;

public class IndexDao extends BaseDao {

	public void resetDictflow(Map<String, Object> params) throws Exception {
		execute("update dict_flow set state = '0'", params);
	}
	
}
