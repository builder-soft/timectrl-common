package cl.buildersoft.timectrl.business.console;

import cl.buildersoft.framework.util.BSConsole;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MachineListenerConsole {
	static final Logger LOG = LogManager.getLogger(MachineListenerConsole.class.getName());

	public static void main(String[] args) {
		LOG.entry(args);
		LOG.info("INFO...");
		MachineListenerConsole mlc = new MachineListenerConsole();
		mlc.start();

	}

	private void start() {
		BSConsole.readString("Pause, press key to continue");

	}
}
