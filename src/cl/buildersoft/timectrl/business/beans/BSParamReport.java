package cl.buildersoft.timectrl.business.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.timectrl.type.BSParamReportType;

public class BSParamReport {
	private BSParamReportType paramType = null;
	private String label = null;
	private String name = null;
	private String min = null;
	private String max = null;
	private List<String[]> options = new ArrayList<String[]>();
	private String value = null;
	private Boolean required = Boolean.FALSE;

	public BSParamReport(BSParamReportType paramType, String name) {
		this.paramType = paramType;
		this.name = name;
		this.label = name;
	}

	public BSParamReportType getParamType() {
		return paramType;
	}

	public void setParamType(BSParamReportType paramType) {
		this.paramType = paramType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void addOption(String key, String value) {
		String[] values = new String[2];
		values[0] = key;
		values[1] = value;
		this.options.add(values);
	}

	public void addOption(Connection conn, String sql, List<Object> prms) {
		BSmySQL mysql = new BSmySQL();
		ResultSet rs = mysql.queryResultSet(conn, sql, prms);

		try {
			while (rs.next()) {
				addOption(rs.getString(1), rs.getString(2));
			}

		} catch (SQLException e) {
			throw new BSDataBaseException(e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new BSDataBaseException(e);
			}
		}
	}

	public List<String[]> listOptions() {
		return this.options;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}
}
