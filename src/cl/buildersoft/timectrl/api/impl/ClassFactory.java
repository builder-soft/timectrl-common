package cl.buildersoft.timectrl.api.impl;

import java.sql.Connection;

import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.timectrl.api.com4j.IZKEM;
import cl.buildersoft.timectrl.api.com4j._zkemProxy;

import com4j.COM4J;

/**
 * Defines methods to create COM objects
 */
public abstract class ClassFactory {
	
	
	private static final String CLS_ID =
			"{30D8D362-701E-4B20-AD89-EC6EF36E57EF}";
//			"{69B0CFE9-91D4-40AD-820E-E5658A3CB0C3}";
//			"{B010C527-776A-4158-B10C-EB4F31B22213}";
	
	private ClassFactory() {
	}

/**<code>
	public static cl.buildersoft.timectrl.api._zkemProxy createzkemProxy() {
		return COM4J.createInstance(cl.buildersoft.timectrl.api._zkemProxy.class, CLS_ID);
	}
</code>*/
	public static _zkemProxy createzkemProxy(Connection conn) {
		BSConfig config = new BSConfig();
		String emulateIndicator = config.getString(conn, "EMULATE");
		if (emulateIndicator == null) {
			emulateIndicator = "false";
		}

		Boolean emulate = Boolean.parseBoolean(emulateIndicator);
		_zkemProxy out = null;

		if (emulate) {
			out = new IZKEMemulatorXML();			
		} else {
			out = COM4J.createInstance(cl.buildersoft.timectrl.api.com4j._zkemProxy.class, CLS_ID);
		}
		return out;

	}

	public static IZKEM createCZKEM(Connection conn) {
		return null;
	}
	
}
