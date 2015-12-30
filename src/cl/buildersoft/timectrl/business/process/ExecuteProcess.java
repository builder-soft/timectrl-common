package cl.buildersoft.timectrl.business.process;

import java.sql.Connection;
import java.util.List;

public interface ExecuteProcess {
	public List<String> doExecute(String[] args);

	public void setConnection(Connection conn);

}
