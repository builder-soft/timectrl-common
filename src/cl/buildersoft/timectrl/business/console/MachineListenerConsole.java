package cl.buildersoft.timectrl.business.console;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.util.BSConsole;
import cl.buildersoft.timectrl.api.com4j._ZKProxy2;
import cl.buildersoft.timectrl.api.com4j._zkemProxy;
import cl.buildersoft.timectrl.api.impl.ZKProxy2Events;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.process.AbstractProcess;
import cl.buildersoft.timectrl.business.process.ExecuteProcess;
import cl.buildersoft.timectrl.business.services.MachineService2;
import cl.buildersoft.timectrl.business.services.impl.MachineServiceImpl2;
import cl.buildersoft.timectrl.util.BSFactoryTimectrl;

public class MachineListenerConsole extends AbstractProcess implements ExecuteProcess {
	static final Logger LOG = LogManager.getLogger(MachineListenerConsole.class.getName());
	private String[] validArguments = { "DOMAIN", "REPORT_KEY" };

	public static void main(String[] args) {
		MachineListenerConsole br4 = new MachineListenerConsole();

		br4.setDSName(args[0]);
		br4.setRunFromConsole(true);

		String[] target = new String[args.length - 1];
		System.arraycopy(args, 1, target, 0, target.length);

		br4.doExecute(target);
		System.exit(0);
	}

	@Override
	protected String[] getArguments() {
		return validArguments;
	}

	@Override
	public List<String> doExecute(String[] args) {
		LOG.entry(args);
		LOG.info("INFO...");

		List<String> out = new ArrayList<String>(1);
		out.add("Nothing to return");

		init();

		MachineService2 ms = new MachineServiceImpl2();

		Connection conn = getConnection();

		if (!licenseValidation(conn)) {
			throw new BSConfigurationException("License validation fail");
		}

		Machine m = getMachine(conn);

		BSFactoryTimectrl ftc = new BSFactoryTimectrl();
		_ZKProxy2 proxy2 = ftc.createZKProxy2(conn);
		
	ZKProxy2Events	events = new ZKProxy2Events(); 
		
		proxy2.advise(cl.buildersoft.timectrl.api.com4j.events.__ZKProxy2.class, events);

		_zkemProxy connMch = ms.connect(conn, m);

		ms.disconnect(connMch);
		BSConsole.readString("Pause, press key to continue");

		return out;

	}

	private Machine getMachine(Connection conn) {
		Machine out = new Machine();
		BSBeanUtils bu = new BSBeanUtils();
		out.setId(1L);
		bu.search(conn, out);

		return out;
	}

	@Override
	public Boolean getRunFromConsole() {
		return null;
	}

	@Override
	public void setRunFromConsole(Boolean runFromConsole) {

	}

}
