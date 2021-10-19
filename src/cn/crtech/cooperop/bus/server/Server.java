package cn.crtech.cooperop.bus.server;

import cn.crtech.cooperop.bus.util.GlobalVar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void init_server() throws Exception {
        BufferedReader br = null;
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec("net start");
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));;
            String line = null;
            List<String> list = new ArrayList<String>();
            while((line=br.readLine())!=null){
                list.add(new String(line.getBytes("gb2312"), "gb2312").trim());
            }
            br.close();
            //redis启动
            if (!list.contains("Redis")) {
                rt.exec("net start Redis");
                Thread.sleep(6000);
            }
            //sql server启动
            if (!list.contains("MSSQLSERVER")) {
                rt.exec("net start MSSQLSERVER");
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            throw e;
        }finally {
            if (br != null) {
                br.close();
            }
        }
    }

}
