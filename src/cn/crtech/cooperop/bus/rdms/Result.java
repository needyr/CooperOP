package cn.crtech.cooperop.bus.rdms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class Result {
	public static final String ROWNO_NAME = "rowno";
	
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public int getPerpage() {
		return perpage;
	}

	public void setPerpage(int perpage) {
		this.perpage = perpage;
	}

	public long getPageno() {
		return pageno;
	}

	public void setPageno(long pageno) {
		this.pageno = pageno;
	}

	public List<Record> getResultset() {
		return resultset;
	}

	public void setResultset(List<Record> resultset) {
		this.resultset = resultset;
	}

	public Record getResultset(int i) {
		return resultset.get(i);
	}

	public Record getTotals() {
		return totals;
	}

	public void setTotals(Record totals) {
		this.totals = totals;
	}

	public Record createRecord() {
		return new Record();
	}

	public void fillTotal(ResultSet rs) throws SQLException {
		for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
			String name = rs.getMetaData().getColumnName(i).toLowerCase();
			int type = rs.getMetaData().getColumnType(i);
			Object value = null;
			switch (type) {
				case Types.TIMESTAMP:
					value = rs.getTimestamp(i);
					break;
				case Types.TIME:
					value = rs.getTimestamp(i);
					break;
				case Types.DATE:
					value = rs.getTimestamp(i);
					break;
				case Types.BLOB:
					value = rs.getBytes(i);
					break;
				default:
					value = rs.getString(i);
				}
			totals.put(name, value);
		}
	}

	public void addRecord(Record record) {
		if (record == null) return;
		if (!record.containsKey(ROWNO_NAME)) {
			record.put(ROWNO_NAME, resultset.size() + 1);
		}
		resultset.add(record);
		count++;
	}

	public void addRecord(ResultSet rs) throws SQLException {
		Record record = new Record();
		for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
			String name = rs.getMetaData().getColumnName(i).toLowerCase();
			int type = rs.getMetaData().getColumnType(i);
			Object value = null;
			switch (type) {
			case Types.TIMESTAMP:
				value = rs.getTimestamp(i);
				break;
			case Types.TIME:
				value = rs.getTimestamp(i);
				break;
			case Types.DATE:
				value = rs.getTimestamp(i);
				break;
			case Types.BLOB:
				value = rs.getBytes(i);
				break;
			case Types.LONGVARBINARY:  //mssql image
				value = rs.getBytes(i);
				break;
			case Types.NUMERIC:
				value = rs.getBigDecimal(i);
				if(value != null){
					value = value.toString();
				}
				break;
			case Types.CHAR:
				value = rs.getString(i);
				if(value != null){
					value = ((String)value).trim();
				}
				break;
			case Types.NCHAR:
				value = rs.getString(i);
				if(value != null){
					value = ((String)value).trim();
				}
				break;
			default:
				value = rs.getString(i);
			}
			record.put(name, value);
		}
		addRecord(record);
	}

	public void fill(ResultSet rs) throws SQLException {
		while (rs.next()) {
			addRecord(rs);
		}
	}
	
	public Result() {
		count = 0;
		perpage = 0;
		pageno = 1;
	}

	public Result(ResultSet rs) throws SQLException {
		fill(rs);
	}

	protected long count;
	protected int perpage;
	protected long pageno;
	protected long start;
	protected long maxpage;

	public long getMaxpage() {
		return maxpage;
	}

	public void setMaxpage(long maxpage) {
		this.maxpage = maxpage;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	protected List<Record> resultset = new ArrayList<Record>();
	protected Record totals = new Record();

}
