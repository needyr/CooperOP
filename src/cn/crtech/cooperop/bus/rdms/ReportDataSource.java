package cn.crtech.cooperop.bus.rdms;

import java.io.File;
import java.io.FileInputStream;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.util.CommonFun;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRField;

public class ReportDataSource extends JREmptyDataSource {
	private Result result = new Result();
	private int cursor = -1;
	private String module = "application";

	public ReportDataSource(Result result) {
		super();
		if (result != null)
			this.result = result;
	}

	public ReportDataSource(Result result, String module) {
		super();
		if (result != null)
			this.result = result;
		if (module != null)
			this.module = module;
	}

	@Override
	public Object getFieldValue(JRField field) {
		Object rtn = result.getResultset(cursor).get(field.getName());
		if (rtn instanceof Result) {
			result.getResultset(cursor).put(field.getName(), new ReportDataSource((Result) rtn));
		}
		if (field.getValueClassName().equals("java.io.InputStream")) {
			String file_id = result.getResultset(cursor).getString(field.getName());
			if (!CommonFun.isNe(file_id)) {
				Record record;
				try {
					record = ResourceManager.getResource(module, file_id);
					if (record != null) {
						File f = ResourceManager.getFile(false, record);
						if (f.exists()) {
							return new FileInputStream(f);
						}
					}
				} catch (Exception e) {
					log.error("加载结果集资源文件失败：" + module + "." + file_id, e);
				}
			}
			return null;
		}
		return result.getResultset(cursor).get(field.getName());
	}

	@Override
	public boolean next() {
		cursor++;
		return (cursor < result.getResultset().size());
	}
}
