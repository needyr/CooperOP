package cn.crtech.cooperop.hospital_common.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.fit.pdfdom.PDFDomTree;

import cn.crtech.cooperop.bus.log.log;

public class PdfConvertUtil {
	/*
    读取pdf文字
     */
    //@Test
    public String readPdfText(String path) throws Exception {
    	PDDocument document = null;
    	PDFTextStripper stripper = new PDFTextStripper();
    	String text = "";
    	try {
    		byte[] bytes = getBytes(path);
    		//加载PDF文档
    		document = PDDocument.load(bytes);
    		text = stripper.getText(document);
		}finally {
			if(document != null) {
				document.close();
			}
		}
        return text;
    }

    /*
    pdf转换html
     */
    //@Test
    public void pdfToHtmlPath(String inputPath,String outputPath) {
        byte[] bytes = getBytes(inputPath);
//        try() 写在()里面会自动关闭流
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputPath)), "UTF-8"));) {
            //加载PDF文档
            PDDocument document = PDDocument.load(bytes);
            PDFDomTree pdfDomTree = new PDFDomTree();
            pdfDomTree.writeText(document, out);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void pdftohtml(byte[] bytes, String htmlPath) throws Exception {
        //加载PDF文档
        PDDocument document = PDDocument.load(bytes);
        // 输出pdf文本
//        readText(document);
        //将字节流转换成字符流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(htmlPath)), "UTF-8"));
        //实例化pdfdom树对象
        PDFDomTree pdfDomTree = new PDFDomTree();
        //开始写入html文件
        pdfDomTree.writeText(document, out);
        //在文件末尾写入要引入的js，因为我将转换的html文件放在了webapp/pdfhtml文件夹下，所以这两个js文件也要放在pdfhtml文件夹下
        //out.write("<script src=\"https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js />");
        out.flush();
        out.close();
        document.close();
    }

    public void readText(PDDocument document) throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        log.debug(text);
    }

    /*
    将文件转换为byte数组
     */
    private byte[] getBytes(String filePath) {
        byte[] buffer = null;
        ByteArrayOutputStream bos = null;
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	if(fis!=null) {
        		try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	if(bos != null) {
        		try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
		}
        return buffer;
    }
}
