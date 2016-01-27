package cl.buildersoft.timectrl.business.services;

import cl.buildersoft.framework.util.BSFactory;
import cl.buildersoft.timectrl.business.services.impl.EventLogServiceImpl;

public class ServiceFactory extends BSFactory {

	public static EventLogService creteEventLogService() {
		return new EventLogServiceImpl();
	}

}
