package cn.crtech.cooperop.hospital_common.util;

import java.io.*;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.crtech.cooperop.bus.rdms.Connection;
import cn.crtech.cooperop.bus.rdms.Record;

public class Util {

	/**
	 * 运行tomcat bat启动
	 * @param java_home jdk地址
	 * @param catalina_home tomcat地址
	 * @param cmd
	 * @throws IOException
	 */
	public static void execCMD (String java_home,String catalina_home, String cmd) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		String[] envp = { "JAVA_HOME="+java_home,
				"CATALINA_HOME="+catalina_home };
		runtime.exec(
				cmd,
				envp, new File(catalina_home));
	}

	public static void main(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		try {
			String[] envp = { "JAVA_HOME=D:\\a-audit-core\\jdk1.8.0_51",
					"CATALINA_HOME=D:\\a-audit-core" };
			runtime.exec(
					"cmd /c start D:\\a-audit-core\\bin\\startup.bat",
					envp, new File("D:\\a-audit-core"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//execCMD("cmd /k start D:\\a-audit-core\\bin\\startup.bat");
	}

	public void killServer(int port){
		Runtime runtime = Runtime.getRuntime();
		try {
			//查找进程号
			Process p = runtime.exec("cmd /c netstat -ano | findstr \""+port+"\"");
			InputStream inputStream = p.getInputStream();
			List<String> read = read(inputStream, "UTF-8",port);
			if(read.size() == 0){
				System.out.println("找不到该端口的进程");
			}else{
				/*for (String string : read) {
					System.out.println(string);
				}*/
				System.out.println("找到"+read.size()+"个进程，正在准备清理");
				kill(read);
			}
			Thread.sleep(6000);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭cmd.exe
	 */
	public void killProcess(){
		Runtime rt = Runtime.getRuntime();
		Process p = null;
		try {
			rt.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
			} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查电脑服务端口是否被占用, true 为存在
	 * @return
	 */
	public static boolean existsPort(String port) throws IOException {
		boolean re = false;
		try{
			Process p = Runtime.getRuntime().exec("cmd /c netstat -ano | findstr \""+port+"\"");
			InputStream inputStream = p.getInputStream();
			List<String> read = read(inputStream, "UTF-8",Integer.parseInt(port));
			if(read.size() > 0){
				re = true;
			}
		} catch (Exception e) {
			throw e;
		}
		return re;
	}

	/**
	 * 获取服务路径
	 * @param server_name
	 * @return
	 */
	public static String getServerPath(String server_name) {
		String re = "";
		File[] roots = File.listRoots();
		for (int i =0; i < roots.length; i++) {
			File file = new File(roots[i]+server_name);
			if(file.exists() && file.isDirectory()){
				re = file.getPath();
			}
		}
		return re;
	}

	/**
	 * 验证此行是否为指定的端口，因为 findstr命令会是把包含的找出来，例如查找80端口，但是会把8099查找出来
	 * @param str
	 * @return
	 */
	private static boolean validPort(String str, int port){
		Pattern pattern = Pattern.compile("^ *[a-zA-Z]+ +\\S+");
		Matcher matcher = pattern.matcher(str);

		matcher.find();
		String find = matcher.group();
		int spstart = find.lastIndexOf(":");
		find = find.substring(spstart + 1);

		int i_port = 0;
		try {
			i_port = Integer.parseInt(find);
		} catch (NumberFormatException e) {
			//System.out.println("查找到错误的端口:" + find);
			return false;
		}
		if(i_port == port){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 更换为一个Set，去掉重复的pid值
	 * @param data
	 */
	public void kill(List<String> data){
		Set<Integer> pids = new HashSet<>();
		for (String line : data) {
			int offset = line.lastIndexOf(" ");
			String spid = line.substring(offset);
			spid = spid.replaceAll(" ", "");
			int pid = 0;
			try {
				pid = Integer.parseInt(spid);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				//System.out.println("获取的进程号错误:" + spid);
			}
			pids.add(pid);
		}
		killWithPid(pids);
	}

	/**
	 * 一次性杀除所有的端口
	 * @param pids
	 */
	public void killWithPid(Set<Integer> pids){
		for (Integer pid : pids) {
			try {
				Runtime.getRuntime().exec("taskkill /F /pid "+pid+"");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	private static List<String> read(InputStream in, String charset, int port) throws IOException{
		List<String> data = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
		String line;
		while((line = reader.readLine()) != null){
			boolean validPort = validPort(line,port);
			if(validPort){
				data.add(line);
			}
		}
		reader.close();
		return data;
	}

	/**
	 * 批处理插入
	 * @param table
	 * @param params
	 * @param conn
	 * @throws Exception
	 */
	public static void executeBatchInsert(String table, List<Record> params, Connection conn) throws Exception {
		//log.debug("-------------------------批处理插入开始--------------------------");
		PreparedStatement pstmt = null;
		try {
			StringBuffer cache_sql_key = new StringBuffer();
			StringBuffer cache_sql_value = new StringBuffer();
			Map<String, Object> map = new HashMap<String, Object>();
			int cache_value = 0;
			Record record = params.get(0);
			Iterator<Entry<String, Object>> iterator = record.entrySet().iterator();
			cache_sql_value.append(" VALUES(");
			cache_sql_key.append(" (");
			while(iterator.hasNext()){
				Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				if(!"rowno".equals(key)) {
					cache_value++;
					if(cache_value==1) {
						cache_sql_value.append("?");
						cache_sql_key.append(key);
					}else {
						cache_sql_value.append(",?");
						cache_sql_key.append(","+key);
					}
					map.put(String.valueOf(cache_value), key);
				}
			}
			cache_sql_value.append(")");
			cache_sql_key.append(")");
			pstmt = conn.prepareStatement("insert into "+table+cache_sql_key.toString()+cache_sql_value.toString());
			//conn.setAutoCommit(false);//取消自动提交
			for (Record re : params) {
				for(int i=1;i<=cache_value;i++) {
					pstmt.setObject(i, "".equals(re.get(map.get(String.valueOf(i))))?null:re.get(map.get(String.valueOf(i))));
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			//conn.commit();
		} catch (Exception ex) {
			try{
		      //conn.rollback();
		    }catch(Exception e1){

		    }
			throw ex;
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					throw e;
				}
			}
			//log.debug("-------------------------批处理插入结束--------------------------");
		}
	}
}
