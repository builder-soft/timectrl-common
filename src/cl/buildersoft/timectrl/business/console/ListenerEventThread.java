package cl.buildersoft.timectrl.business.console;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.timectrl.api.com4j._ZKProxy2;
import cl.buildersoft.timectrl.api.impl.IZKEMException;
import cl.buildersoft.timectrl.api.impl.ZKProxy2Events;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.util.BSFactoryTimectrl;

import com4j.Holder;

public class ListenerEventThread extends Thread {
	private static final Logger LOG = LogManager.getLogger(ListenerEventThread.class);
	private String dsName = null;
	private Machine machine = null;

	public ListenerEventThread(String dsName, Machine machine) {
		this.dsName = dsName;
		this.machine = machine;
	}

	public void run() {
		LOG.trace(String.format("Domain:%s - Machine %s:%d", dsName, machine.getIp(), machine.getPort()));
		BSConnectionFactory cf = new BSConnectionFactory();
		BSFactoryTimectrl ftc = new BSFactoryTimectrl();

		Connection conn = cf.getConnection(dsName);

		_ZKProxy2 proxy2 = ftc.createZKProxy2(conn);
		ZKProxy2Events events = new ZKProxy2Events(dsName, machine);

		proxy2.advise(cl.buildersoft.timectrl.api.com4j.events.__ZKProxy2.class, events);
		boolean connected = proxy2.connectAndRegEvent(machine.getIp(), machine.getPort().shortValue(), 1);
		if (!connected) {
			Holder<Integer> errorCode = new Holder<Integer>();
			proxy2.getLastError(errorCode);
			try {
				throw new IZKEMException(errorCode.value);
			} catch (IZKEMException e) {
				LOG.fatal(machine.toString() + String.format(" Error code is '%d'", errorCode.value), e);
			}
		} else {
			while (true) {
				sleepSecond(1);
				LOG.trace(String.format("Waiting for %s:%d events", machine.getIp(), machine.getPort()));
			}
		}
	}

	private void sleepSecond(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			LOG.fatal(e);
		}
	}
}
