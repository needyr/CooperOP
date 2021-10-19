package cn.crtech.cooperop.bus.cache.service;

import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.cache.dao.SystemConfigDao;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SystemConfigService extends BaseService {
	@Override
	public void connect() throws Exception {
		connect("base");
	}

	public void load() throws Exception {
		try {
			connect();
			SystemConfigDao dao = new SystemConfigDao();
			Result result = dao.load();
			memoryConfig(result);
		}
		catch (Exception ex) {
			log.error("load system config failed.", ex);
		}
		finally {
			disconnect();
		}
	}

	public int saveConfig(String productCode,String code ,String value) throws Exception {
		try {
			connect();
			SystemConfigDao configDao = new SystemConfigDao();
			int res = configDao.save(productCode, code, value);
			Result result = configDao.get(productCode, code);
			memoryConfig(result);
			return res;
		}catch (Exception ex) {
			log.error("load system config failed.", ex);
			throw ex;
		}finally {
			disconnect();
		}
	}

	/**
	 *
	 * 保存系统配置到缓存的私有方法，供saveConfig及load方法调用
	 * <dl><dt><b>业务描述：</b></dt><dd>
	 * 循环Result对象，生成Config对象，后保存到MemoryCache中
	 * </dd></dl>
	 * <dl><dt><b>范例：</b></dt><dd>
	 * 无
	 * </dd></dl>
	 * @param result 查询System_config表返回的Result对象
	 * <dt><b>修改历史：</b></dt>
	 *
	 */
	private void memoryConfig(Result result) {
		for (Record config : result.getResultset()) {
			String memkey = (CommonFun.isNe(config.getString("system_product_code")) ? "global" : config.getString("system_product_code")) + "." + config.getString("code");
			MemoryCache.put(SystemConfig.prix, memkey, config);
		}
	}

}
