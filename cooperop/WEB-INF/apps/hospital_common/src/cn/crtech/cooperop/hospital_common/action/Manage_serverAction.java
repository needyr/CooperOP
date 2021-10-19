package cn.crtech.cooperop.hospital_common.action;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.util.Util;

import java.util.Map;

public class Manage_serverAction extends BaseAction {
    public Result query(Map<String, Object> params) throws Exception {
        Result re = new Result();
        Record r = new Record();
        r.put("name","a-pdc-server");
        r.put("info","初始化数据同步");
        if(Util.existsPort("9100")){
            r.put("status","1");
        }else{
            r.put("status","0");
        }
        re.addRecord(r);
        r = new Record();
        r.put("name","a-dtc-server");
        r.put("info","数据采集");
        if(Util.existsPort("9000")){
            r.put("status","1");
        }else{
            r.put("status","0");
        }
        re.addRecord(r);
        r = new Record();
        r.put("name","a-audit-core");
        r.put("info","审查引擎");
        if(Util.existsPort("9272")){
            r.put("status","1");
        }else{
            r.put("status","0");
        }
        re.addRecord(r);
        r = new Record();
        r.put("name","a-pharmacist-8.0.24");
        r.put("info","药师端人工审方");
        if(Util.existsPort("8086")){
            r.put("status","1");
        }else{
            r.put("status","0");
        }
        re.addRecord(r);
        return re;
    }

    public void start(Map<String, Object> params) throws Exception {
        if(!CommonFun.isNe(params.get("name"))){
            boolean b = false;
            if (params.get("name").equals("a-pdc-server") && !Util.existsPort("9100")){
                b = true;
            }
            if (params.get("name").equals("a-dtc-server") && !Util.existsPort("9000")){
                b = true;
            }
            if (params.get("name").equals("a-audit-core") && !Util.existsPort("9272")){
                b = true;
            }
            if (params.get("name").equals("a-pharmacist-8.0.24") && !Util.existsPort("8086")){
                b = true;
            }
            if(b){
                String path = Util.getServerPath((String) params.get("name"));
                Util.execCMD(path+"\\jdk1.8.0_51",path,"cmd /c start "+path+"\\bin\\startup.bat");
                new Util().killProcess();
                Thread.sleep(6000);
            }
        }
    }

    public void stop(Map<String, Object> params) throws Exception {
        if(!CommonFun.isNe(params.get("name"))){
            int port = 0;
            if (params.get("name").equals("a-pdc-server") && Util.existsPort("9100")){
                port= 9100;
            }
            if (params.get("name").equals("a-dtc-server") && Util.existsPort("9000")){
                port= 9000;
            }
            if (params.get("name").equals("a-audit-core") && Util.existsPort("9272")){
                port= 9272;
            }
            if (params.get("name").equals("a-pharmacist-8.0.24") && Util.existsPort("8086")){
                port= 8086;
            }
            new Util().killServer(port);
        }
    }

    public void restart(Map<String, Object> params) throws Exception {
        stop(params);
        start(params);
    }

}
