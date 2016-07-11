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
	private Boolean continueRunning = true;

	public ListenerEventThread(String dsName, Machine machine) {
		this.dsName = dsName;
		this.machine = machine;
	}

	public void run() {
		LOG.trace(String.format("Connecting for Domain:%s - Machine %s:%d", dsName, machine.getIp(), machine.getPort()));
		BSConnectionFactory cf = new BSConnectionFactory();
		BSFactoryTimectrl ftc = new BSFactoryTimectrl();

		Connection conn = cf.getConnection(dsName);

		_ZKProxy2 proxy2 = ftc.createZKProxy2(conn);
		ZKProxy2Events events = new ZKProxy2Events(dsName, machine);
		
		sleepSecond(1);
		proxy2.advise(cl.buildersoft.timectrl.api.com4j.events.__ZKProxy2.class, events);
		boolean connected = proxy2.connectAndRegEvent(machine.getIp(), machine.getPort().shortValue(), 1);
		if (!connected) {
			Holder<Integer> errorCode = new Holder<Integer>();
			proxy2.getLastError(errorCode);
			try {
				throw new IZKEMException(errorCode.value);
			} catch (IZKEMException e) {
				LOG.fatal(machine.toString() + String.format("Error code is %d, message='%s'", errorCode.value, e.getMessage()), e);
			}
		} else {
			Integer loopCounter = 0;
			while (getContinueRunning()) {
				sleepSecond(1);
				if (loopCounter % 60 == 0 || loopCounter == 0) {
					LOG.trace(String.format("(loop:%d) Waiting for %s:%d events", loopCounter, machine.getIp(), machine.getPort()));
				}
				loopCounter++;
			}
			LOG.trace(String.format("Disconecting %s:%d", machine.getIp(), machine.getPort()));
			proxy2.disconnect();
		}
	}

	private void sleepSecond(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			LOG.fatal(e);
		}
	}

	public Boolean getContinueRunning() {
		return continueRunning;
	}

	public void setContinueRunning(Boolean continueRunning) {
		this.continueRunning = continueRunning;
	}
}
