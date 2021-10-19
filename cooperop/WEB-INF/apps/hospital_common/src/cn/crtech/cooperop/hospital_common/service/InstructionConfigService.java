package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.InstructionConfigDao;

public class InstructionConfigService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new InstructionConfigDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryBrief(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new InstructionConfigDao().queryBrief(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryHas(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new InstructionConfigDao().queryHas(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new InstructionConfigDao().insert(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	//排序上移
	public int moveUp(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			start();
			Map<String, Object> params2=new HashMap<String, Object>();
			params2.put("column_name", params.remove("last_column_name"));
			InstructionConfigDao icd=new InstructionConfigDao();
			icd.updateSortUp(params);
			int res=icd.updateSortDown(params2);
			commit();
			return res;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	//排序下移
	public int moveDown(Map<String, Object> params) throws Exception {
			try {
				connect("hospital_common");
				start();
				Map<String, Object> params2=new HashMap<String, Object>();
				params2.put("column_name", params.remove("next_column_name"));
				InstructionConfigDao icd=new InstructionConfigDao();
				icd.updateSortDown(params);
				int res=icd.updateSortUp(params2);
				commit();
				return res;
			} catch (Exception e) {
				rollback();
				throw e;
			}finally {
				disconnect();
			}
	}
	
	//修改显示/隐藏
	public int updateShow(Map<String, Object> params) throws Exception {
			try {
				connect("hospital_common");
				new InstructionConfigDao().updateShow(params);
				return 0;
			} catch (Exception e) {
				throw e;
			}finally {
				disconnect();
			}
	}
	
	
	//排序上移
	public int moveUpBrief(Map<String, Object> params) throws Exception {
			try {
				connect("hospital_common");
				start();
				Map<String, Object> params2=new HashMap<String, Object>();
				params2.put("column_name", params.remove("last_column_name"));
				InstructionConfigDao icd=new InstructionConfigDao();
				icd.updateSortUpBrief(params);
				int res=icd.updateSortDownBrief(params2);
				commit();
				return res;
			} catch (Exception e) {
				rollback();
				throw e;
			}finally {
				disconnect();
			}
		}
		
		//排序下移
		public int moveDownBrief(Map<String, Object> params) throws Exception {
				try {
					connect("hospital_common");
					start();
					Map<String, Object> params2=new HashMap<String, Object>();
					params2.put("column_name", params.remove("next_column_name"));
					InstructionConfigDao icd=new InstructionConfigDao();
					icd.updateSortDownBrief(params);
					int res=icd.updateSortUpBrief(params2);
					commit();
					return res;
				} catch (Exception e) {
					rollback();
					throw e;
				}finally {
					disconnect();
				}
		}
		
		//修改显示/隐藏
		public int updateShowBrief(Map<String, Object> params) throws Exception {
				try {
					connect("hospital_common");
					new InstructionConfigDao().updateShowBrief(params);
					return 0;
				} catch (Exception e) {
					throw e;
				}finally {
					disconnect();
				}
		}
		
		//修改标题
		public int updateTitle(Map<String, Object> params) throws Exception {
			try {
					connect("hospital_common");
					new InstructionConfigDao().updateTitle(params);
					return 0;
				} catch (Exception e) {
					throw e;
				}finally {
					disconnect();
				}
		}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new InstructionConfigDao().delete(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new InstructionConfigDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
