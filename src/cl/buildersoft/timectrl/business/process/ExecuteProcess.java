package cl.buildersoft.timectrl.business.process;

import java.util.List;

public interface ExecuteProcess {
	public List<String> doExecute(String[] args);

	// public void setConnection(Connection conn);
	public void setDSName(String dsName);

}
