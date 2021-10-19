package cn.crtech.cooperop.bus;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.license.License;
import cn.crtech.cooperop.bus.server.Server;
import org.jdom.Document;
import org.jdom.Element;

import cn.crtech.choho.authresource.Resource;
import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.cache.SystemMessageTemplate;
import cn.crtech.cooperop.bus.cache.SystemUser;
import cn.crtech.cooperop.bus.im.JavaScriptServlet;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.weixin.WeiXin;
import cn.crtech.cooperop.bus.workflow.core.WorkFlowEngine;
import cn.crtech.cooperop.bus.ws.server.Engine;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.ConnectionPool;
import cn.crtech.cooperop.bus.schedule.ScheduleEngine;
import cn.crtech.cooperop.bus.session.Session;

public class Bus extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 5453631585717512619L;
	public static boolean finish_GlobalVar = false;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String workPath = config.getServletContext().getRealPath("");
		String configfile = config.getServletContext().getRealPath(config.getInitParameter("configfile"));

		try{
			GlobalVar.init(workPath, configfile);
		}catch (Exception e){
			System.err.println("系统配置读取失败请检查");
			System.exit(-1);
		}

		try{
			Server.init_server();
		}catch (Exception e){
			System.err.println("前置服务开启失败, 请检查, 或手动启动!");
			System.exit(-1);
		}

		try{
			MemoryCache.init();
			log.init();
			// ygz.2021.02.18 授权验证
			// Class.forName("cn.crtech.xin.launcher.license.License");
			// License.init();
		}catch (Exception e){
			System.err.println("please use xin-launcher to start !");
			System.exit(-1);
		}

		try {
			//Resource.init(workPath, GlobalVar.getSystemProperty("auth.config"));
			LoadModuleClasses(workPath);
			ConnectionPool.init();
			ScheduleEngine.init();
			SystemConfig.load();
			Dictionary.load();
			SystemUser.load();
			SystemMessageTemplate.load();
			WorkFlowEngine.init();
			Session.init();
			Engine.init();
			initModules(workPath);
			/*try {
				MQEngine.init();
			} catch (ResourceException e) {
				e.printStackTrace();
			}*/
			if (!CommonFun.isNe(GlobalVar.getSystemProperty("weixin.config"))) {
				WeiXin.init(workPath, GlobalVar.getSystemProperty("weixin.config"));
			}
			finish_GlobalVar = true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Cooperop Load Failed.", e);
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

	private void LoadModuleClasses(String workPath) {
		try {
			URLClassLoader classloader = (URLClassLoader) getClass().getClassLoader();
			Method add = null;
			boolean acc = false;
			try {
				add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
				acc = add.isAccessible();
				add.setAccessible(true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			File file = new File(workPath, GlobalVar.getSystemProperty("app.path", "plugins"));

			File[] modules = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			});

			if (modules != null) {
				for (File f : modules) {
					file = new File(f, "classes");
					if (file.exists() && file.isDirectory()) {
						try {
							add.invoke(classloader, new Object[] { file.toURI().toURL() });
						} catch (Exception e) {
							log.debug("load module classes failed." + e.getMessage() + ", " + file.getAbsolutePath());
							e.printStackTrace();
						}
					}

					file = new File(f, "lib");
					if (file.exists()) {
						File[] jars = file.listFiles(new FileFilter() {
							@Override
							public boolean accept(File pathname) {
								return pathname.isFile();
							}
						});
						for (File jar : jars) {
							try {
								add.invoke(classloader, new Object[] { jar.toURI().toURL() });
							} catch (Exception e) {
								log.debug("load module jar failed." + e.getMessage() + ", " + jar.getAbsolutePath());
								e.printStackTrace();
							}
						}
					}
				}
			}
			add.setAccessible(acc);
		} catch (Exception ex) {
			log.debug("load module failed." + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void initModules(String workPath) {
		try {
			File file = new File(workPath, GlobalVar.getSystemProperty("app.path", "plugins"));

			File[] modules = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			});

			if (modules != null) {
				for (File f : modules) {
					file = new File(f, "app.xml");
					if (file.exists() && file.isFile()) {
						Document document = CommonFun.loadXMLFile(file);
						Element root = document.getRootElement();
						if (root != null) {
							@SuppressWarnings("unchecked")
							List<Element> servlets = root.getChildren("initmethod");
							if (servlets != null) {
								for (Element servlet : servlets) {
									String name = servlet.getChildTextTrim("name");
									String clazz = servlet.getChildTextTrim("class");
									String method = servlet.getChildTextTrim("method");
									try {
										Class<?> c = Class.forName(clazz);
										Method m = c.getMethod(method);
										m.invoke(null);
									} catch (Exception ex) {
										log.error("init module serlvet[" + name + "] failed." + ex.getMessage(), ex);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			log.error("load module failed." + ex.getMessage(), ex);
		}
	}

}
