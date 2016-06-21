package cl.buildersoft.timectrl.business.console;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.framework.util.BSConsole;
import cl.buildersoft.timectrl.api._zkemProxy;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.services.MachineService2;
import cl.buildersoft.timectrl.business.services.impl.MachineServiceImpl2;

public class MachineListenerConsole {
	static final Logger LOG = LogManager.getLogger(MachineListenerConsole.class.getName());

	public static void main(String[] args) {
		LOG.entry(args);
		LOG.info("INFO...");
		MachineListenerConsole mlc = new MachineListenerConsole();
		mlc.start();

	}

	private void start() {
		MachineService2 ms = new MachineServiceImpl2();

		BSConnectionFactory cf = new BSConnectionFactory();
		Connection conn = cf.getConnection("enlasa");
		Machine m = getMachine(conn);
		_zkemProxy connMch = ms.connect(conn, m);

		ms.disconnect(connMch);
		BSConsole.readString("Pause, press key to continue");
	}

	private Machine getMachine(Connection conn) {
		Machine out = new Machine();
		BSBeanUtils bu = new BSBeanUtils();
		out.setId(1L);
		bu.search(conn, out);

		return out;
	}
}
