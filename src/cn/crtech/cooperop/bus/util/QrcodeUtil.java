package cn.crtech.cooperop.bus.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;


import com.swetake.util.Qrcode;

import cn.crtech.cooperop.bus.rm.ResourceManager;
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

public class QrcodeUtil {
	 /** 
     * 编码字符串内容到目标File对象中 
     *  
     * @param encodeddata 编码的内容 
     * @param destFile  生成file文件  1381090722   5029067275903 
     * @throws IOException 
     */  
    public static void qrCodeEncode(String encodeddata, File destFile) throws IOException {  
        Qrcode qrcode = new Qrcode();  
        qrcode.setQrcodeErrorCorrect('M');  // 纠错级别（L 7%、M 15%、Q 25%、H 30%）和版本有关  
        qrcode.setQrcodeEncodeMode('B');      
        qrcode.setQrcodeVersion(10);     // 设置Qrcode包的版本  
          
        byte[] d = encodeddata.getBytes("GBK"); // 字符集  
        BufferedImage bi = new BufferedImage(177, 177, BufferedImage.TYPE_INT_RGB);  
        // createGraphics   // 创建图层  
        Graphics2D g = bi.createGraphics();  
          
        g.setBackground(Color.WHITE);   // 设置背景颜色（白色）  
        g.clearRect(0, 0, 177, 177);    // 矩形 X、Y、width、height  
        g.setColor(Color.BLACK);    // 设置图像颜色（黑色）  
  
        if (d.length > 0) {  
            boolean[][] b = qrcode.calQrcode(d);  
            for (int i = 0; i < b.length; i++) {  
                for (int j = 0; j < b.length; j++) {  
                    if (b[j][i]) {  
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);  
                    }  
                }  
            }  
        }  
          
//      Image img = ImageIO.read(new File("D:/tt.png"));  logo  
//      g.drawImage(img, 25, 55,60,50, null);  
                  
        g.dispose(); // 释放此图形的上下文以及它使用的所有系统资源。调用 dispose 之后，就不能再使用 Graphics 对象  
        bi.flush(); // 刷新此 Image 对象正在使用的所有可重构的资源  
  
        ImageIO.write(bi, "png", destFile);  
        System.out.println("Input Encoded data is：" + encodeddata);  
    }
    
    public static void qrCodeEncode(String encodeddata , OutputStream os) throws IOException {  
        Qrcode qrcode = new Qrcode();  
        qrcode.setQrcodeErrorCorrect('M');  // 纠错级别（L 7%、M 15%、Q 25%、H 30%）和版本有关  
        qrcode.setQrcodeEncodeMode('B');      
        qrcode.setQrcodeVersion(10);     // 设置Qrcode包的版本  
          
        byte[] d = encodeddata.getBytes("GBK"); // 字符集  
        BufferedImage bi = new BufferedImage(177, 177, BufferedImage.TYPE_INT_RGB);  
        // createGraphics   // 创建图层  
        Graphics2D g = bi.createGraphics();  
          
        g.setBackground(Color.WHITE);   // 设置背景颜色（白色）  
        g.clearRect(0, 0, 177, 177);    // 矩形 X、Y、width、height  
        g.setColor(Color.BLACK);    // 设置图像颜色（黑色）  
  
        if (d.length > 0) {  
            boolean[][] b = qrcode.calQrcode(d);  
            for (int i = 0; i < b.length; i++) {  
                for (int j = 0; j < b.length; j++) {  
                    if (b[j][i]) {  
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);  
                    }  
                }  
            }  
        }  
          
//      Image img = ImageIO.read(new File("D:/tt.png"));  logo  
//      g.drawImage(img, 25, 55,60,50, null);  
                  
        g.dispose(); // 释放此图形的上下文以及它使用的所有系统资源。调用 dispose 之后，就不能再使用 Graphics 对象  
        bi.flush(); // 刷新此 Image 对象正在使用的所有可重构的资源  
        ImageIO.write(bi, "png", os);
        System.out.println("Input Encoded data is：" + encodeddata);  
    }
    public static String qrCodeEncode(String module, String userid, String content, String filename) throws Exception {  
    	 Qrcode qrcode = new Qrcode();  
         qrcode.setQrcodeErrorCorrect('M');  // 纠错级别（L 7%、M 15%、Q 25%、H 30%）和版本有关  
         qrcode.setQrcodeEncodeMode('B');      
         qrcode.setQrcodeVersion(10);     // 设置Qrcode包的版本  
         int size = 1024;
         byte[] d = content.getBytes("GBK"); // 字符集  
         BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);  
         // createGraphics   // 创建图层  
         Graphics2D g = bi.createGraphics();  
           
         g.setBackground(Color.WHITE);   // 设置背景颜色（白色）  
         g.clearRect(0, 0, size, size);    // 矩形 X、Y、width、height  
         g.setColor(Color.BLACK);    // 设置图像颜色（黑色）  
   
         if (d.length > 0) {  
             boolean[][] b = qrcode.calQrcode(d);  
             for (int i = 0; i < b.length; i++) {  
                 for (int j = 0; j < b.length; j++) {  
                     if (b[j][i]) {  
                         g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);  
                     }  
                 }  
             }  
         }  
           
//       Image img = ImageIO.read(new File("D:/tt.png"));  logo  
//       g.drawImage(img, 25, 55,60,50, null);  
         
         g.dispose();
         bi.flush();
         ByteArrayOutputStream os = null;
         InputStream stream = null;
         String fileid;
         try{
        	 os = new ByteArrayOutputStream();
        	 ImageIO.write(bi, "png", os);
        	 stream = new ByteArrayInputStream(os.toByteArray());
        	 fileid = ResourceManager.storeResourceImage(module, userid, stream, "image/png", filename, String.valueOf(os.size()));
         }finally{
        	 if(stream != null){
        		 stream.close();
        	 }
        	 if(os != null){
        		 os.close();
        	 }
         }
         return fileid;
    } 
    /** 
     * 解析二维码，返回解析内容 
     *  
     * @param imageFile 
     * @return 
     */  
    public static String qrCodeDecode(File imageFile) {  
        String decodedData = null;  
        QRCodeDecoder decoder = new QRCodeDecoder();  
        BufferedImage image = null;  
        try {  
            image = ImageIO.read(imageFile);  
        } catch (IOException e) {  
            System.out.println("Error: " + e.getMessage());  
        }  
  
        try {  
            decodedData = new String(decoder.decode(new J2SEImage(image)), "GBK");  
            System.out.println("Output Decoded Data is：" + decodedData);  
        } catch (DecodingFailedException dfe) {  
            System.out.println("Error: " + dfe.getMessage());  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return decodedData;  
    }  
  
    public static void main(String[] args) {  
        String FilePath = "d:/qrcode.png";  
        File qrFile = new File(FilePath);  
  
        // 二维码内容  
        String encodeddata = "http://192.168.1.148:8085/fileupload.jsp?_CRSID=4BD08F08F4A2414CB0EC496EC904F07D&sequ=20170414110423124014&module=applicationaasdfasdf";  
        try {  
        	QrcodeUtil.qrCodeEncode(encodeddata, qrFile);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
        // 解码  
        String reText = QrcodeUtil.qrCodeDecode(qrFile);  
        System.out.println(reText);  
    }  
}

class J2SEImage implements QRCodeImage {  
    BufferedImage image;  
  
    public J2SEImage(BufferedImage image) {  
        this.image = image;  
    }  
  
    public int getWidth() {  
        return image.getWidth();  
    }  
  
    public int getHeight() {  
        return image.getHeight();  
    }  
  
    public int getPixel(int x, int y) {  
        return image.getRGB(x, y);  
    }  
} 