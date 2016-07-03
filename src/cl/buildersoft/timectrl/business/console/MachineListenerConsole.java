package cl.buildersoft.timectrl.business.console;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSUserException;
import cl.buildersoft.framework.util.BSConsole;
import cl.buildersoft.timectrl.api.com4j._ZKProxy2;
import cl.buildersoft.timectrl.api.impl.ZKProxy2Events;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.process.AbstractProcess;
import cl.buildersoft.timectrl.business.process.ExecuteProcess;
import cl.buildersoft.timectrl.util.BSFactoryTimectrl;
import com4j.Holder;

public class MachineListenerConsole extends AbstractProcess implements ExecuteProcess {
	private static final Logger LOG = LogManager.getLogger(MachineListenerConsole.class);
	private String[] validArguments = { "DOMAIN", "MACHINE_ID" };
	private Boolean runFromConsole = true;

	public static void main(String[] args) {
		MachineListenerConsole mlc = new MachineListenerConsole();

		mlc.setDSName(args[0]);
		mlc.setRunFromConsole(true);

		String[] target = new String[args.length - 1];
		System.arraycopy(args, 1, target, 0, target.length);

		mlc.doExecute(target);

		System.exit(0);
	}

	@Override
	protected String[] getArguments() {
		return validArguments;
	}

	@Override
	public List<String> doExecute(String[] args) {
		Long machineId = Long.parseLong(args[0]);

		validateParameters(args);

		List<String> out = new ArrayList<String>(1);
		out.add("Nothing to return");

		init();

		Connection conn = getConnection();

		if (!licenseValidation(conn)) {
			throw new BSConfigurationException("License validation fail");
		}

		Machine m = getMachine(conn, machineId);

		BSFactoryTimectrl ftc = new BSFactoryTimectrl();
		_ZKProxy2 proxy2 = ftc.createZKProxy2(conn);

		ZKProxy2Events events = new ZKProxy2Events();

		// proxy2.advise(ZKProxy2Events.class, events);
		proxy2.advise(cl.buildersoft.timectrl.api.com4j.events.__ZKProxy2.class, events);
		System.out.println("connecting");

		boolean connected = proxy2.connectAndRegEvent(m.getIp(), m.getPort().shortValue(), 1);
		if (!connected) {
			Holder<Integer> errorCode = new Holder<Integer>();
			proxy2.getLastError(errorCode);
			System.out.println(errorCode.value);
		}

		// proxy2.connect_Net(m.getIp(), m.getPort().shortValue());
		// proxy2.regEvent(1, 65535);
		// _ZKProxy2 connMch = ms.connect2(conn, m);

		BSConsole.readString("Pause, press key to continue");

		proxy2.disconnect();
		// ms.disconnect(connMch);

		pauseInSeconds(2);
		System.out.println("End");
		return out;

	}

	private void validateParameters(String[] args) {
		validateArguments(args);
		if (args.length != 2) {
			String message = "Parameters are not valid. Expected is 2 parameters.";
			LOG.fatal(message);
			throw new BSUserException(message);
		}

	}

	private void pauseInSeconds(Integer seconds) {
		boolean doContinue = true;
		long start = System.currentTimeMillis();
		while (doContinue) {
			// this.notify();
			try {
				Thread.sleep(10);
				// this.wait(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (System.currentTimeMillis() - start > (seconds * 1000)) {
				doContinue = false;
			}
		}
	}

	private Machine getMachine(Connection conn, Long machineId) {
		Machine out = new Machine();
		BSBeanUtils bu = new BSBeanUtils();
		out.setId(machineId);
		bu.search(conn, out);

		return out;
	}

	@Override
	public Boolean getRunFromConsole() {
		return this.runFromConsole;
	}

	@Override
	public void setRunFromConsole(Boolean runFromConsole) {
		this.runFromConsole = runFromConsole;
	}

}
