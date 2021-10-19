package cn.crtech.cooperop.crdc.service.scheme;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.crdc.action.scheme.SchemeUtil;
import cn.crtech.cooperop.crdc.dao.scheme.DataretrievalDao;
import cn.crtech.cooperop.crdc.dao.scheme.DistillDao;
import cn.crtech.cooperop.crdc.dao.scheme.PagemodifyDao;

public class DistillSercice extends BaseService {
	public void save(Map<String, Object> map) throws Exception {
		connect();
		new DistillDao().save(map);
	}

	public Record querySingle(Map<String, Object> map) throws Exception {
		connect();
		Record record=new DistillDao().querySingle(map);
//		SchemeUtil.inToString(record);
		return record;
	}
	public void update(Map<String, Object> map) throws Exception {
		   connect(); 
		   new DistillDao().update(map);
	}
	public Result query(Map<String, Object> map) throws Exception{
		   connect();
		   return new DistillDao().query(map);
	}
    public void delete(Map<String, Object> map) throws Exception { 
		   connect();
		   new DistillDao().delete(map);
	}
}
