package cn.crtech.cooperop.hospital_common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 图片转pdf
 * @author ruiheng
 *
 */
public class PdfToImgUtil {
	/*** @param picturePath 图片地址*/
    private static void createPic(Document document,String picturePath) {
        try {
            Image image = Image.getInstance(picturePath);
            float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
            float documentHeight = documentWidth / 580 * 320;//重新设置宽高
            image.scaleAbsolute(documentWidth, documentHeight);//重新设置宽高
            document.add(image);
        } catch (Exception ex) {
        }
    }
    
    /**
     * 
     * @param text 图片地址
     * @param pdf 输出pdf地址
     * @throws DocumentException
     * @throws IOException
     */
    public static void image2pdf(String text, String pdf) throws DocumentException, IOException {
        Document document = new Document();
        OutputStream os = new FileOutputStream(new File(pdf));
        PdfWriter.getInstance(document,os);
        document.open();
        createPic(document,text);
        document.close();
    }
    
    public static void main(String[] args) throws IOException, DocumentException {
        image2pdf("G:\\iadscp平台.png","g:\\test.pdf");
    }
}
