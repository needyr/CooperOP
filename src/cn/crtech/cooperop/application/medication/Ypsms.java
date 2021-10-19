package cn.crtech.cooperop.application.medication;

public class Ypsms {
	static boolean inited = false;

	public static void init() {
		if (inited)
			return;
		Mkrj.init();
	}

	public static String getYpsmsURL(String ypname) throws Exception {
		init();
		return Mkrj.getYpsmsURL(ypname);
	}
}
