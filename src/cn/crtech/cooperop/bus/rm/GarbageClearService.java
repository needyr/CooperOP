package cn.crtech.cooperop.bus.rm;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class GarbageClearService extends Thread {

	/**
	 * 
	 */
	public GarbageClearService() {

	}

	/**
	 * 
	 */
	public void terminal() {
		running = false;
		this.interrupt();
		try {
			this.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean running;

	private boolean busy;

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		running = true;
		log.release("Resource Garbage Clear Service init success.");
		long cycle = 0;
		while (running) {
			try {
				sleep(cycle);
			} catch (Exception ex) {
			}

			if (!running)
				break;

			cycle = Long.parseLong(GlobalVar.getSystemProperty("rm.gc.cycle", "0"));
			if (cycle == 0) {
				cycle = 300000;
				continue;
			}
			log.release("Resource Garbage Clear Service start.");
			long start = System.currentTimeMillis();
			busy = true;

			try {
				ResourceManager.gc();
			} catch (Exception ex) {
				log.error(ex);
			}

			busy = false;
			cycle -= System.currentTimeMillis() - start;
			log.release("Resource Garbage Clear Service end.");
		}
	}

}
