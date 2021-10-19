package cn.crtech.cooperop.application.validate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.session.Session;

public class ValidImg extends HttpServlet {

	private static final long serialVersionUID = -6950185328928045230L;

	private final static int num = 6;
	private final static int font_size = 30;
	private final static int width = 128;
	private final static int height = 50;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		resp.setDateHeader("Last-Modified", System.currentTimeMillis());
		OutputStream os = resp.getOutputStream();
		ImageIO.setUseCache(false);
		ImageIO.write(validateCode(Session.getSession(req, resp)), "png", os);
		os.flush();
		os.close();
	}
	
	private static BufferedImage validateCode(Session session){
		BufferedImage buffimg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffimg.createGraphics();
		buffimg = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);  
		g = buffimg.createGraphics();
		g.setBackground(new Color(0, 0, 0, 0.3f));
		g.setFont(new Font("Arial", Font.BOLD, font_size));
		int red = 0, green = 0, blue = 0;
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(20);
			int yl = random.nextInt(20);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawLine(x, y, x + xl, y + yl);
		}
		StringBuffer randomcode = new StringBuffer();
		for (int i = 0; i < num; i++) {
			String strrand = random.nextInt(10)+"";
			red = random.nextInt(150);
			green = random.nextInt(150);
			blue = random.nextInt(150);
			float imght = 0;
			while (imght <= font_size) {
				imght = Float.parseFloat(String.valueOf(random.nextInt(height)));
			}
			g.setColor(new Color(red, green, blue));
			g.drawString(strrand, (width / (num + 5)) + font_size * 3  * i / 5 , imght);
			randomcode.append(strrand);
		}
		g.dispose();
		session.put("validcode", randomcode.toString());
		return buffimg;
	}
}
