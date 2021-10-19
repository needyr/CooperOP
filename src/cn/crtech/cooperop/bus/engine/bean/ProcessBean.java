package cn.crtech.cooperop.bus.engine.bean;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ProcessBean implements Serializable{
		private static final long serialVersionUID = 2404714026631073872L;
		private String sequence;
		private String downloadUrl;
		private String sessionId;
		private String pageid;
		private int total;
		private int currNumBegin;
		private int currNumEnd;
		private long beginTime;
		private long endTime;
		private String message;
		private Map<String, String> header = new HashMap<String, String>();
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public long getEndTime() {
			return endTime;
		}

		public void setEndTime(long endTime) {
			this.endTime = endTime;
		}
		public long getBeginTime() {
			return beginTime;
		}

		public void setBeginTime(long beginTime) {
			this.beginTime = beginTime;
		}

		@Override
		public String toString() {
			return "sequence:"+sequence+", total:"+total+", currNumBegin:"+currNumBegin+", currNumEnd:"+currNumEnd+", downloadUrl:"+downloadUrl;
		}

		public String getSequence() {
			return sequence;
		}
		public void setSequence(String sequence) {
			this.sequence = sequence;
		}
		
		public String getDownloadUrl() {
			return downloadUrl;
		}
		public void setDownloadUrl(String downloadUrl) {
			this.downloadUrl = downloadUrl;
		}
		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public String getPageid() {
			return pageid;
		}

		public void setPageid(String pageid) {
			this.pageid = pageid;
		}

		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getCurrNumBegin() {
			return currNumBegin;
		}
		public void setCurrNumBegin(int currNumBegin) {
			this.currNumBegin = currNumBegin;
		}
		public int getCurrNumEnd() {
			return currNumEnd;
		}
		public void setCurrNumEnd(int currNumEnd) {
			this.currNumEnd = currNumEnd;
		}

		public Map<String, String> getHeader() {
			return header;
		}

		public void setHeader(Map<String, String> header) {
			this.header = header;
		}
	}