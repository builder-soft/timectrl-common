package cl.buildersoft.timectrl.business.console;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.buildersoft.framework.beans.Domain;
import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSUserException;
import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.framework.util.BSConsole;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.process.AbstractProcess;
import cl.buildersoft.timectrl.business.process.ExecuteProcess;

public class MachineListenerConsole extends AbstractProcess implements ExecuteProcess {
	private static final Logger LOG = LogManager.getLogger(MachineListenerConsole.class);
	private String[] validArguments = { "DOMAIN", "MACHINE_ID" };
	// private Boolean runFromConsole = true;
	private List<ListenerEventThread> listenerList = new ArrayList<ListenerEventThread>();

	@SuppressWarnings("static-access")
	private void startThread(String dsName, Machine machine) {
		LOG.trace(String.format("Starting thread for %s:%d", machine.getIp(), machine.getPort()));
		ListenerEventThread let = new ListenerEventThread(dsName, machine);
		let.setName(String.format("Thread-%d for %s:%d", let.activeCount(), machine.getIp(), machine.getPort()));
		listenerList.add(let);
		let.start();

	}

	public static void main(String[] args) {
		MachineListenerConsole mlc = new MachineListenerConsole();

		// String[] target = new String[args.length - 1];
		// System.arraycopy(args, 1, target, 0, target.length);

		mlc.doExecute(args);

		System.exit(0);
	}

	@Override
	protected String[] getArguments() {
		return validArguments;
	}

	@Override
	public List<String> doExecute(String[] args) {
		// Long machineId = Long.parseLong(args[0]);
		Integer totalMachines = 0;
		validateParameters(args);
		this.setRunFromConsole(true);

		List<String> out = new ArrayList<String>(1);
		out.add("Nothing to return on listener");

		List<Domain> domains = getDomains(args[0]);
		BSConnectionFactory cf = new BSConnectionFactory();
		String dsName = null;

		Integer secondsForStart = 5;
		BSConsole.println("+------------------------------------------------+");
		BSConsole.println("| To finish this process, press any key anytime. |");
		BSConsole.println(String.format("| (Now we are wainting %d seconds to start)       |", secondsForStart));
		BSConsole.println("+------------------------------------------------+");
		sleepSecond(secondsForStart);

		for (Domain domain : domains) {
			dsName = domain.getDatabase();
			this.setDSName(dsName);

			init();

			Connection conn = cf.getConnection(dsName);

			if (!licenseValidation(conn)) {
				throw new BSConfigurationException("License validation fail");
			}
			// mlc.setDSName(args[0]);

			List<Machine> machines = listMachines(conn, args[1]);

			for (Machine machine : machines) {
				startThread(dsName, machine);
								totalMachines++;
								sleepSecond(1);
			}

			/**
			 * <code>
			Machine m = getMachine(conn, 6L);

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


			proxy2.disconnect();
			// ms.disconnect(connMch);
			 
</code>
			 */
		}
		BSConsole.readString();
		LOG.info(String.format("Stoping threads, and waiting %d seconds", totalMachines + 5));
		stopThreads();
		sleepSecond(totalMachines + 5);
		LOG.info("End Listener");
		return out;

	}

	private void stopThreads() {
		// List<ListenerEventThread> listenerList
		for (ListenerEventThread listener : listenerList) {
			listener.setContinueRunning(false);
		}
	}

	@SuppressWarnings("unchecked")
	private List<Machine> listMachines(Connection conn, String machinesId) {
		List<Machine> out = null;
		BSBeanUtils bu = new BSBeanUtils();
		if ("0".equals(machinesId)) {
			out = (List<Machine>) bu.listAll(conn, new Machine());
		} else {
			String[] machineIdArray = machinesId.split(",");
			out = new ArrayList<Machine>();
			for (String machineId : machineIdArray) {
				Machine machine = new Machine();
				machine.setId(Long.parseLong(machineId));
				bu.search(conn, machine);
				out.add(machine);
			}

		}

		return out;
	}

	@SuppressWarnings("unchecked")
	private List<Domain> getDomains(String domainName) {
		BSBeanUtils bu = new BSBeanUtils();
		BSConnectionFactory cf = new BSConnectionFactory();
		Connection conn = cf.getConnection();
		List<Domain> out = null;

		if ("0".equals(domainName)) {
			out = (List<Domain>) bu.listAll(conn, new Domain());
		} else {
			Domain domain = new Domain();
			bu.search(conn, domain, "cDatabase=?", domainName);
			out = new ArrayList<Domain>();
			out.add(domain);
		}
		cf.closeConnection(conn);
		return out;
	}

	private void validateParameters(String[] args) {
		try {
			validateArguments(args);
		} catch (BSConfigurationException e) {
			LOG.fatal(e);
		}
		if (args.length != 2) {
			String message = "Amount parameters are not valid. Expected 2 parameters. First parameter is domain alias. Second parameter is the Machine Id. Both can be zero for all.";
			LOG.fatal(message);
			throw new BSUserException(message);
		} else {

		}
	}

	private void sleepSecond(Integer seconds) {
		boolean doContinue = true;
		long start = System.currentTimeMillis();
		while (doContinue) {
			// this.notify();
			try {
				Thread.sleep(10);
				// this.wait(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (System.currentTimeMillis() - start > (seconds * 1000)) {
				doContinue = false;
			}
		}
	}

	/**
	 * <code>
	private Machine getMachine(Connection conn, Long machineId) {
		Machine out = new Machine();
		BSBeanUtils bu = new BSBeanUtils();
		out.setId(machineId);
		bu.search(conn, out);

		return out;
	}
	 * 
	 * @Override public Boolean getRunFromConsole() { return
	 *           this.runFromConsole; }
	 * @Override public void setRunFromConsole(Boolean runFromConsole) {
	 *           this.runFromConsole = runFromConsole; } </code>
	 */

}
