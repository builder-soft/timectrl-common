package cl.buildersoft.timectrl.util;

import java.sql.Connection;

import cl.buildersoft.framework.exception.BSProgrammerException;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.timectrl.api.com4j.ClassFactory;
import cl.buildersoft.timectrl.api.com4j.IZKEM;
import cl.buildersoft.timectrl.api.com4j._ZKProxy2;
import cl.buildersoft.timectrl.api.com4j._zkemProxy;
import cl.buildersoft.timectrl.api.impl.IZKEMemulatorProxy2XML;
import cl.buildersoft.timectrl.api.impl.IZKEMemulatorXML;

/**
 * Defines methods to create COM objects
 */
public class BSFactoryTimectrl {
	// private static final String CLS_ID =
	// "{30D8D362-701E-4B20-AD89-EC6EF36E57EF}";
	// "{69B0CFE9-91D4-40AD-820E-E5658A3CB0C3}";
	// "{B010C527-776A-4158-B10C-EB4F31B22213}";

	/**
	 * <code>
	public static cl.buildersoft.timectrl.api._zkemProxy createzkemProxy() {
		return COM4J.createInstance(cl.buildersoft.timectrl.api._zkemProxy.class, CLS_ID);
	}
</code>
	 */
	public _zkemProxy createzkemProxy(Connection conn) {
		Boolean emulate = getEmulate(conn);
		_zkemProxy out = null;

		if (emulate) {
			out = new IZKEMemulatorXML();
		} else {
			out = ClassFactory.createzkemProxy();
		}
		return out;
	}

	public _ZKProxy2 createZKProxy2(Connection conn) {
		Boolean emulate = getEmulate(conn);
		_ZKProxy2 out = null;

		if (emulate) {
			out = new IZKEMemulatorProxy2XML();
		} else {
			out = ClassFactory.createZKProxy2();
		}
		return out;

		// return COM4J.createInstance(
		// cl.buildersoft.timectrl.api.com4j._ZKProxy2.class,
		// "{CD8F0F8C-B43C-415D-B280-E43B08BC274D}" );
	}

	public static IZKEM createCZKEM(Connection conn) {
		throw new BSProgrammerException("Can't create a IZKEM instance");
	}

	private Boolean getEmulate(Connection conn) {
		BSConfig config = new BSConfig();
		String emulateIndicator = config.getString(conn, "EMULATE");
		if (emulateIndicator == null) {
			emulateIndicator = "false";
		}

		Boolean emulate = Boolean.parseBoolean(emulateIndicator);
		return emulate;
	}

}
