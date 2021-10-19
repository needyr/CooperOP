package cn.crtech.cooperop.application.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.dao.DingdingDao;
import cn.crtech.cooperop.bus.interf.ding.Attends;
import cn.crtech.cooperop.bus.mvc.control.BaseService;

public class DingdingService extends BaseService {

	/**
	 * 提取考勤数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getAttendance(Map<String, Object> params) throws Exception {
		try {
			connect();
			DingdingDao dd = new DingdingDao();
			String[] userids = dd.getDDUserids(params).getString("userids").split(",");		//所有人员
			//1.人员批次数（每批次50人）
			int userbatch = (int) Math.ceil(userids.length/50.0);
			int userindex = userids.length-1;
			//2.起始日期和日期批次数（每批次7天）
			Long dayms = (long) (1000*60*60*24);
			String edate = getDateFormat(new Date().getTime(), "yyyy-MM-dd");
			Long edate_ms = getMsFormat(edate, "yyyy-MM-dd");
			Long maxdate = dd.getAttendanceMaxDate(params).getLong("sdate");		//当前已有数据的最大日期
			Long sdate_ms = maxdate!=0?maxdate:edate_ms-dayms*31;
			int datebatch = (int)  Math.ceil((edate_ms-sdate_ms+dayms)/(dayms*7.0));
			//3.按日期及人员分批查询插入考勤表
			start();
			//3.1 回删7天数据，以免最后一批数据有异常导致未提取完整
			Long deldate_ms = sdate_ms-dayms*6;		//需要删除的最小日期
			dd.delAttendance(deldate_ms);
			//3.2 提取数据
			for(int j=0;j<datebatch;j++){
				Long sdatems = deldate_ms+dayms*7*j;
				Long edatems = sdatems+dayms*6>edate_ms?edate_ms:sdatems+dayms*6;
				params.put("checkDateFrom", getDateFormat(sdatems, "yyyy-MM-dd")+" 00:00:00");
				params.put("checkDateTo", getDateFormat(edatems, "yyyy-MM-dd")+" 23:59:59");
//					params.put("checkDateFrom", "2017-10-13 00:00:00");
//					params.put("checkDateTo", "2017-10-19 23:59:59");
				for(int i=0;i<userbatch;i++){
					String[] userids50 = Arrays.copyOfRange(userids, 50*i, 50*(i+1)>userindex?userindex:50*(i+1));
					params.put("userIds", userids50);
					Map<String, Object> map = new Attends().listAttendsRecord(params);		//获取打卡记录
					//保存打卡记录
					List<Map<String, Object>> lm = (List<Map<String, Object>>) map.get("recordresult");
					for(Map<String, Object> m : lm){
						dd.insertAttendance(m);
					}
				}
			}
			commit();
			return 1;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public String getDateFormat(Long ms,String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);	//设置时间格式
		Date date=new Date();
		date.setTime(ms);		//毫秒转为日期
		String dd=formatter.format(date);		//日期转为指定时间格式
		return dd;
	}
	@SuppressWarnings("finally")
	public Long getMsFormat(String date,String format) throws Exception{
		Long mstime = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);	//设置时间格式
			mstime = formatter.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
			return mstime;
		}
	}
}
