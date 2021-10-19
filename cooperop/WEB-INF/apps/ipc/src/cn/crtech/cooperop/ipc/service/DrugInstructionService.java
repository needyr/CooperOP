package cn.crtech.cooperop.ipc.service;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.dao.DrugSmsDao;
import cn.crtech.cooperop.ipc.schedule.SyncInstruction;
import cn.crtech.cooperop.ipc.util.JavaScriptUtil;
import cn.crtech.precheck.ipc.huiyaotong.EngineDrug;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrugInstructionService extends BaseService {

    /**
     * 批量格式化说明书(只有经过格式化的说明书才能通过系统说明书查看功能查看)
     */
    public void batchFormatInstruction() throws Exception{
        List<Record> drugs = initData();
        EngineDrug engineDrug = new EngineDrug();
        for (Record drug: drugs){
            try{
                Record fmtInfo = getHYTInstruction(drug, engineDrug);
                if(!CommonFun.isNe(fmtInfo)){
                    saveInTmp(fmtInfo);
                }
            }catch(Exception e){
                continue;
            }
        }
        try {
            connect("ipc");
            // 将临时表中的说明书数据更新到正式说明书表中
            new DrugSmsDao().execTmp2Spzl(null);
        } catch(Exception e){
            log.error(e);
        }finally {
            disconnect();
            // 同步说明书到药师端
            // Client.syncShuomsImg();
            new SyncInstruction().executeOn();
        }
    }

    /**
     * 根据系统药品编码 sysDrugCode格式化说明书
     * @param params (sys_drug_code) 系统药品编码
     */
    public void formatInstructionBySysCode(Record params) throws Exception{
        EngineDrug engineDrug = new EngineDrug();
        Record fmtInfo = getHYTInstruction(params, engineDrug);
        if(!CommonFun.isNe(fmtInfo) && !fmtInfo.isEmpty()){
            saveInTmp(fmtInfo);
        }
        try {
            connect("ipc");
            // 将临时表中的说明书数据更新到正式说明书表中
            new DrugSmsDao().execTmp2Spzl(null);
        } catch(Exception e){
            log.error(e);
        }finally {
            disconnect();
        }
    }

    /**
     * 数据初始化，批量格式化说明书的准备工作
     * @return 需要进行格式化的药品
     */
    private List<Record> initData() throws Exception {
        connect("ipc");
        DrugSmsDao dsDao = new DrugSmsDao();
        try {
            dsDao.deleteAllTemp(null);
            dsDao.updateHaveUrl(null);
            return dsDao.query(null).getResultset();
        } catch (Exception e) {
            log.error("批量格式化说明书-初始化失败", e);
            throw e;
        } finally {
            disconnect();
        }
    }

    /**
     * 获取说明书内容并格式化
     * @param drug 药品信息
     * @param engineDrug 药品说明书接口实例
     * @return 格式化后的说明书内容：项目-内容
     * @throws Exception
     */
    private Record getHYTInstruction(Record drug, EngineDrug engineDrug) throws Exception {
        Record fmtInfo = new Record();
        if(!CommonFun.isNe(drug) && !CommonFun.isNe(drug.get("sys_drug_code"))){

            /**
            drugCodeFmt = drugCodeFmt.replaceAll("\\|", "_").replaceAll("\\;", "_")
                    .replaceAll("\\/", "_").replaceAll("\\?", "_")
                    .replaceAll("\\:", "_").replaceAll("\\@", "_")
                    .replaceAll("\\&", "_").replaceAll("\\=", "_")
                    .replaceAll("\\+", "_").replaceAll("\\$", "_")
                    .replaceAll("\\,", "_").replaceAll("\\#", "_")
                    .replaceAll("\\*", "_");
             */
            String drugUrl = engineDrug.execMethod(drug.getString("sys_drug_code"));
            System.out.println("说明书地址：" + drugUrl);
            String body = getHtmlByUrl(drugUrl);
            if (!CommonFun.isNe(body)){
                // 通过JS取得html中有用的信息
                Map<String,Object> instruction = JavaScriptUtil.getFieldsValue(body);
                // 检查是否包含数据(仅一项包含数据就算有数据)
                boolean check = false;
                Iterator<Map.Entry<String, Object>> iterator = instruction.entrySet().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getValue() != null) {
                        check = true;
                        break;
                    }
                }
                if(check){
                    instruction.put("sys_drug_code", drug.get("sys_drug_code"));
                    fmtInfo = fmtInstruction(new Record(instruction));
                }
            }else{
                log.debug("批量格式化说明书-解析html为空：" + body);
            }
        }else{
            log.debug("批量格式化说明书-系统药品编码为空：" + drug.toString());
        }
        return fmtInfo;
    }

    /**
     * 根据网页链接获取其body部分源码
     * @param urlStr 网页地址
     * @return 网页的html代码-body部分
     */
    private static String getHtmlByUrl(String urlStr) throws IOException  {
        StringBuffer sb = null;
        String htmlBody = "";
        InputStream in = null; BufferedReader bf = null;
        try {
            String beforeHtmlUrl = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.length());
            String fmtUrl = urlStr.substring(0, urlStr.lastIndexOf("/") + 1) +
                    URLEncoder.encode(beforeHtmlUrl, "UTF-8");
            log.info("原格式化url：" + fmtUrl);
            log.info("直接格式化url：" + URLDecoder.decode(urlStr, "UTF-8"));
            URL url = new URL(fmtUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if(HttpURLConnection.HTTP_OK == responseCode
                    || responseCode == HttpURLConnection.HTTP_CREATED
                    || responseCode == HttpURLConnection.HTTP_ACCEPTED ) {
                in = conn.getInputStream();
            }else {
                in = conn.getErrorStream();
            }
            if(in != null) {
                bf = new BufferedReader(new InputStreamReader(in, "utf-8"));
                String line = "";
                sb = new StringBuffer();
                while(null != (line = bf.readLine())){
                    sb.append(line + "\n");
                }
                String html = sb.toString();
                Pattern pattern = Pattern.compile("<h5( .*?)?>.*?</h5>");
                Matcher matcher = pattern.matcher(html);
                while (matcher.find()) {
                    String trim = matcher.group(0).trim();
                    html = html.replace(trim, "");
                }
                htmlBody = Jsoup.parse(html).getElementsByTag("body").toString();
                // 一些网页错误的处理
                htmlBody = htmlBody.replaceAll("<loml", "<loml>")
                        .replaceAll("<l", "<l>");
            }else {
                log.debug("批量格式化说明书-未获取到说明书html");
            }
        } catch (Exception e) {
            log.error(e);
        }finally {
            if (bf != null) {
                bf.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return htmlBody;
    }

    /**
     * 存储格式化的药品说明书信息
     * @param info 药品说明书信息
     */
    private Record fmtInstruction(Record info){
        String drugCodeFmt = (String)info.get("sys_drug_code");
        // 去除特殊字符
        String[] spChar = new String[]{"\\|", "\\;", "\\/", "\\:", "\\?", "\\@", "\\&",
                "\\=", "\\+", "\\$", "\\,", "\\#", "\\*"};
        for (int i = 0; i < spChar.length; i++) {
            drugCodeFmt = drugCodeFmt.replaceAll(spChar[i], "_");
        }
        long img_count = 0;
        //添加药品说明书图片的解析
        for (Map.Entry<String, Object> entry : info.entrySet()) {
            try {
                String key = entry.getKey();
                Object object = entry.getValue();
                if (!CommonFun.isNe(object)) {
                    String value = object.toString();
                    String regxpForImgTag = "<\\s*img\\s*([^>]*)\\s*/?\\s*>";
                    Pattern p = Pattern.compile(regxpForImgTag, Pattern.MULTILINE);
                    Matcher m = p.matcher(value);
                    String face = "", add_name ="", houz = "";
                    while(m.find()) {
                        String data = m.group(0).trim();//img标签<img src="111.jqp" />
                        String regxpForImgSrc = "(src|SRC)=(\\\"|\\')(.*?)(\\\"|\\')";
                        Pattern pattern = Pattern.compile(regxpForImgSrc, Pattern.MULTILINE);
                        Matcher matcher = pattern.matcher(data);
                        while (matcher.find()) {
                            img_count += 1;
                            String[] trim = matcher.group(0).trim().split("\"");
                            String src = trim[trim.length-1];
                            //src有两种,一种为"111.jpg",一种为"images\drugimages\111.jpg"
                            int one = src.lastIndexOf("/");
                            if (one >= 0 ) {
                                String add_name_all = src.substring(one+1, src.length());
                                face = src.substring(0, one + 1);//前缀
                                int lastIndexOf = add_name_all.lastIndexOf(".");
                                add_name = add_name_all.substring(0, lastIndexOf);//文件名
                                houz = add_name_all.substring(lastIndexOf + 1, add_name_all.length());//后缀
                            }else {
                                String add_name_all = src;
                                int lastIndexOf = add_name_all.lastIndexOf(".");
                                add_name = add_name_all.substring(0, lastIndexOf);//文件名
                                houz = add_name_all.substring(lastIndexOf + 1, add_name_all.length());//后缀
                            }
                            value = value.replace(src, face+"sys" + drugCodeFmt + "p" + img_count +"."+houz);

                            //拷贝文件,更改文件名称:  sys+标准库code(|替换为K)+p+递增数字(从1开始)
                            URL resource = Thread.currentThread().getContextClassLoader().getResource("/");
                            String courseFile = resource.getPath().substring(resource.getPath().lastIndexOf(":")-1, resource.getPath().indexOf("/WEB-INF/")+9).replace("/", "\\");
                            String shuoms_img_address = SystemConfig.getSystemConfigValue("ipc", "shuoms_img_address", "apps\\hospital_common\\resource\\drugimg");
                            String file_add = courseFile + shuoms_img_address + "\\";
                            File craete_file = new File(file_add+face.replace("/", "\\\\"));
                            if(!craete_file.exists()) {
                                craete_file.mkdirs();
                            }
                            //String file_add = SystemConfig.getSystemConfigValue("ipc", "img_add", "X:\\Cooperop\\apache-tomcat-8.0.24\\webapps\\ROOT\\WEB-INF\\apps\\ipc\\resource\\hytimages\\");
                            String hty_add = SystemConfig.getSystemConfigValue("ipc", "hty_add", "X:\\hyt1\\慧药通\\分析服务WebService接口\\hytimages\\");
                            String files = hty_add + face + add_name + "." + houz;
                            String change = file_add + face + "sys" + drugCodeFmt + "p" + img_count + "." + houz;
                            copyFile(new File(files.replaceAll("/", "\\\\")), new File(change.replaceAll("/", "\\\\")));
                        }
                    }
                    //重新插入
                    info.put(key, value);
                }
            } catch (Exception e) {
                log.error(e);
                continue;
            }
        }
        //------------------------
        // info.put("sys_drug_code", sys_drug_code);
        info.put("owner_create", "0");
        //截取生产厂家
        String address = (String)info.get("manufacturer");
        String sc_name = getFromAddress(address,"企业名称:");
        String sc_address = getFromAddress(address,"生产地址:");
        String sc_phone = getFromAddress(address,"电话号码:");
        if (CommonFun.isNe(sc_name)) {
            sc_name = getFromAddress(address,"企业名称：");
        }
        if (CommonFun.isNe(sc_address)) {
            sc_address = getFromAddress(address,"生产地址：");
        }
        if (CommonFun.isNe(sc_phone)) {
            sc_phone = getFromAddress(address,"电话号码：");
        }
        info.put("manufacturer_short_name", sc_name);
        info.put("manufacturer_address", sc_address);
        info.put("manufacturer_tel", sc_phone);
        // save(info);
        return info;
    }

    /**
     * 将说明书数据存储到临时表中
     * @param info 说明书信息（格式化之后，可直接写入数据表）
     */
    private void saveInTmp(Record info){
        try {
            connect("ipc");
            Map<String,Object> mParam = new HashMap<String,Object>();
            mParam.put("drug_code", info.get("sys_drug_code"));
            DrugSmsDao drugSmsDao = new DrugSmsDao();
            //插入说明书表 临时表
            drugSmsDao.insert_temp(info);
            mParam.put("haveurl", "1") ;
            // 更新字典表标识
            drugSmsDao.updateSysDict(mParam);
        }catch (Exception e) {
            log.error("说明书抓取-将说明书写入临时表失败：" + info.toString(), e);
        }  finally {
            disconnect();
        }
    }

    /**
     * 拷贝文件
     * @param source 文件来源地址
     * @param target 文件目标地址
     */
    private static void copyFile(File source, File target) throws IOException {
        if (target.exists()) {
            target.delete();
        }
        if (!source.exists()) {
            return;
        }
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            log.error(e);
        } finally {
            inStream.close();
            in.close();
            outStream.close();
            out.close();
        }
    }

    /**
     * 获取说明书字符串中的厂家,地址等信息
     * @param string 地址内容
     * @param t 需要获取的内容前缀
     * @return
     */
    private String getFromAddress(String string, String t) {
        if(!CommonFun.isNe(string)) {
            String[] split = string.split(t);
            if (!CommonFun.isNe(split)) {
                if (split.length>1) {
                    String s = split[1];
                    String[] q = s.split(" ");
                    String w = new String();
                    for (String string2 : q) {
                        if (!string2.isEmpty()) {
                            w = string2;
                            break;
                        }
                    }
                    return w.trim();
                }
            }
        }
        return "";
    }

}
