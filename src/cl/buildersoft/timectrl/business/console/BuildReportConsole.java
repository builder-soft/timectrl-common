package cl.buildersoft.timectrl.business.console;

import cl.buildersoft.timectrl.business.process.impl.BuildReport4;

public class BuildReportConsole {

	
	public static void main(String[] args) {
		BuildReport4 br4 = new BuildReport4();

		br4.setDSName(args[0]);
		br4.setRunFromConsole(true);

		String[] target = new String[args.length - 1];
		System.arraycopy(args, 1, target, 0, target.length);

		br4.doExecute(target);
		System.exit(0);
	}

}
