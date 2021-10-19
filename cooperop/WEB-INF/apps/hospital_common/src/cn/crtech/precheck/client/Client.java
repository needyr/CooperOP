package cn.crtech.precheck.client;

import java.io.File;

import cn.crtech.cooperop.bus.rdms.Record;

public class Client  extends Thread {

	public Record webservice = null;

	public Client(Record webservice) throws Exception {
		this.webservice = webservice;
	}

	public Client(File f) throws Exception {
	}

	public Object invokeMethod(String method, Object... args) throws Exception {
		return null;
	}

}
