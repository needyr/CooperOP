package cn.crtech.cooperop.bus.engine.window;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Window {
	public void excute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
