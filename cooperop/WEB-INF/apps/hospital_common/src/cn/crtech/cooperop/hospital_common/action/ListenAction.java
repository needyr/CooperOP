package cn.crtech.cooperop.hospital_common.action;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListenAction extends BaseAction {

    public static Record reStart(Map<String, Object> params) throws Exception{
        Record ret = new Record();
        // 验证密码
        if (!CommonFun.isNe(params) && !CommonFun.isNe(params.get("password"))
                && "crtech.2020".equals(params.get("password"))){
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            cachedThreadPool.execute(new Thread() {
                @Override
                public void run() {
                    this.setName("listen-exec-cmd");
                }
            });
            Runtime rt = Runtime.getRuntime();
            // String projectPath = "X:\\pharmacist\\bin";
            // String projectPath = "D:\\Java\\apache-tomcat-9.0.34";
            /**
            try{
                rt.exec("cmd /c start shutdown-for-listen.bat",
                        null, new File(projectPath));
            }catch(Exception e){
                e.printStackTrace();
            }
            */
            try{
                rt.exec("cmd /c start X:\\pharmacist\\reset.bat");
            }catch (Exception e){
                e.printStackTrace();
            }
            Thread.sleep(2000);
            /**
            try{
                rt.exec("cmd /c start startup.bat",
                        null, new File(projectPath));
            }catch (Exception e){
                e.printStackTrace();
            }
            */
            rt.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
            ret.put("state", 1);
        }else {
            ret.put("state", -1);
            ret.put("msg", "密码错误!");
        }
        return ret;
    }

}
