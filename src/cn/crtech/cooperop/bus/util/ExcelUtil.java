package cn.crtech.cooperop.bus.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

/**
 * excel工具类
 * @version 1.0
 */

public class ExcelUtil {
	public static void main(String[] args) throws Exception {
		//analysisXLS(new FileInputStream("f://aaa.xls"));
		//analysisXLSX(new FileInputStream("f://aaa1.xlsx"));
		List<Result> analysisXLSTopwd = analysisXLSXBypwd(new FileInputStream("f:\\111.xlsx"),"5805e33b8dca73d");
		for (Result result : analysisXLSTopwd) {
			for (Record r : result.getResultset()) {
				System.out.println(r);
			}
		}
	}
	
	/**
	 * 
	 * 解析2003版本的excel文件，此类文件后缀为 xls，注意：此方法未关闭传入的输入流.
	 * <dl><dt><b>业务描述：</b></dt><dd>
	 * 解析03版本的excel，不能解析03版本以上excel文件，如果需要，见analysisXLSX方法
	 * </dd></dl>
	 * @param in 输入流 注意：此输入流未在此方法中关闭.
	 * @return 
	 * @throws IOException io异常
	 * <dt><b>修改历史：</b></dt>
	 * @see ExcelUtil#analysisXLSX(InputStream)
	 */
	public static List<Result> analysisXLS(InputStream in) throws IOException {
		Workbook wb = new HSSFWorkbook(in);
		return analysis(wb);
	}
	
	/**
	 * 
	 * 解析2003版本的excel文件，此类文件后缀为 xls，注意：此方法未关闭传入的输入流, 解密.
	 * <dl><dt><b>业务描述：</b></dt><dd>
	 * 解析03版本的excel，不能解析03版本以上excel文件，如果需要，见analysisXLSX方法
	 * </dd></dl>
	 * @param in 输入流 注意：此输入流未在此方法中关闭.
	 * @return 
	 * @throws IOException io异常
	 * <dt><b>修改历史：</b></dt>
	 * @see ExcelUtil#analysisXLSX(InputStream)
	 */
	public static List<Result> analysisXLSBypwd(InputStream in,String pwd) throws IOException {
		POIFSFileSystem pfs = new POIFSFileSystem(in); 
		Biff8EncryptionKey.setCurrentUserPassword(pwd);
		Workbook wb = new HSSFWorkbook(pfs);
		return analysis(wb);
	}

	/**
	 *
	 * 解析2007版本的excel文件，此类文件后缀为 xlsx，注意：此方法未关闭传入的输入流.
	 * <dl><dt><b>业务描述：</b></dt><dd>
	 * 解析07版本的excel，不能解析07版本以下excel文件，如果需要，见analysisXLS方法
	 * </dd></dl>
	 * @param in 输入流 注意：此输入流未在此方法中关闭.
	 * @return
	 * @throws IOException io异常
	 * <dt><b>修改历史：</b></dt>
	 * @see ExcelUtil#analysisXLS(InputStream)
	 */
	public static List<Result> analysisXLSXBypwd(InputStream in,String pwd) throws Exception {
		POIFSFileSystem poifsFileSystem = new POIFSFileSystem(in);
        EncryptionInfo encInfo = new EncryptionInfo(poifsFileSystem);
        Decryptor decryptor = Decryptor.getInstance(encInfo);
        decryptor.verifyPassword(pwd);
        XSSFWorkbook wb = new XSSFWorkbook(decryptor.getDataStream(poifsFileSystem));
		return analysis(wb);
	}
	
	/**
	 * 
	 * 解析2007版本的excel文件，此类文件后缀为 xlsx，注意：此方法未关闭传入的输入流.
	 * <dl><dt><b>业务描述：</b></dt><dd>
	 * 解析07版本的excel，不能解析07版本以下excel文件，如果需要，见analysisXLS方法
	 * </dd></dl>
	 * @param in 输入流 注意：此输入流未在此方法中关闭.
	 * @return 
	 * @throws IOException io异常
	 * <dt><b>修改历史：</b></dt>
	 * @see ExcelUtil#analysisXLS(InputStream)
	 */
	public static List<Result> analysisXLSX(InputStream in) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook(in);
		return analysis(wb);
	}
	private static List<Result> analysis(Workbook wb) {
		List<Result> sheetList = new ArrayList<Result>();
		int sheetNum = wb.getNumberOfSheets();
		for (int i = 0; i < sheetNum; i++) {
			Result result = new Result();
			Sheet sheet = wb.getSheetAt(i);
			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();
			if (lastRowNum == firstRowNum) {
				continue;
			}
			int blankRowNums = 0;
			row:for (int j = firstRowNum; j <= lastRowNum; j++) {
				Row row = sheet.getRow(j);
				if(row==null){
					continue;
				}
				if(j==321){
					System.out.println(j);
				}
				short firstCellNum = row.getFirstCellNum();
				short lastCellNum = row.getLastCellNum();
				Record record = new Record();
				boolean allCellIsBlank = true;
				for (int k = firstCellNum; k <= lastCellNum; k++) {
					Cell cell = row.getCell(k);
					if (cell == null) {
						record.put(k + "", "");
						continue;
					}
					int type = cell.getCellType();
					Object val = "";
					switch (type) {
						case Cell.CELL_TYPE_STRING :
							val = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_BOOLEAN :
							val = cell.getBooleanCellValue();
							break;
						case Cell.CELL_TYPE_ERROR :
							val = cell.getErrorCellValue();
							break;
						case Cell.CELL_TYPE_FORMULA :
							val = cell.getCellFormula();
							break;
						case Cell.CELL_TYPE_NUMERIC :
							if (HSSFDateUtil.isCellDateFormatted(cell)){// 判断单元格是否属于日期格式
								Date date  = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
								Calendar cal = Calendar.getInstance();
								cal.setTime(date);
								if(cal.get(Calendar.YEAR)<=1900){
									val = cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
								}else{
									val = date;
								}
							}
							else{
								double tempVal = cell.getNumericCellValue();
								long longVal = Math.round(tempVal);
								if (Double.parseDouble(longVal + ".0") == tempVal)
									val = longVal;
								else
									val = tempVal;
							}
							break;
						default :
							val = "";
							break;
					}
					if(!CommonFun.isNe(val.toString().trim())){
						allCellIsBlank=false;
					}
					record.put(k + "", val);
				}
				if(allCellIsBlank){
					if(++blankRowNums>=2){
						List<Record> list = result.getResultset();
						list.remove(list.size()-1);
						break row;
					}
				}
				result.addRecord(record);
			}
			sheetList.add(result);
		}
		return sheetList;
	}
}
