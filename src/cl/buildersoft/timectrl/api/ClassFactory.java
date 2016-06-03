package cl.buildersoft.timectrl.api;

import java.sql.Connection;

import cl.buildersoft.framework.util.BSConfig;

import com4j.COM4J;

/**
 * Defines methods to create COM objects
 */
public abstract class ClassFactory {
	
	
	private static final String CLS_ID = 
			"{69B0CFE9-91D4-40AD-820E-E5658A3CB0C3}";
//			"{B010C527-776A-4158-B10C-EB4F31B22213}";
	
	/**
	 <code>
	 private static final String CLS_ID = "{B6DAF4E4-3FFD-41DB-925C-581239F45057}";
	 COM4J.createInstance( cl.buildersoft.timectrl.api._zkemProxy.class, "{B010C527-776A-4158-B10C-EB4F31B22213}" );
	 </code>*/

	private ClassFactory() {
	}


	public static cl.buildersoft.timectrl.api._zkemProxy createzkemProxy() {
		return COM4J.createInstance(cl.buildersoft.timectrl.api._zkemProxy.class, CLS_ID);
	}

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
			out = COM4J.createInstance(cl.buildersoft.timectrl.api._zkemProxy.class, CLS_ID);
		}
		return out;

	}

	public static IZKEM createCZKEM(Connection conn) {
		return null;
	}
	
	
	
	
	

}
