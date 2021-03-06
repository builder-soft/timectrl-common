package cl.buildersoft.timectrl.util;

import static cl.buildersoft.framework.util.BSUtils.array2ObjectArray;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSSystemException;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.framework.util.BSSecurity;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.business.beans.Machine;

public class LicenseValidationUtil {
	private static final Logger LOG = Logger.getLogger(LicenseValidationUtil.class.getName());
	private static final int ONE_DAY = 1000 * 60 * 60 * 24;

	public Boolean licenseValidation(Connection conn, String fileContent) {
		Boolean success = true;
		Set<String> serialsSet = readSerialsFromDB(conn);

		if (serialsSet.size() > 0) {
			fileContent = decrypt(fileContent);

			Integer poundPosition = fileContent.indexOf("#");
			String serials = fileContent.substring(0, poundPosition);
			String dateExpired = fileContent.substring(poundPosition + 1);
			LOG.log(Level.FINE, "Serials: {0} For Date: {1}", array2ObjectArray(serials, dateExpired));

			String[] serialsArray = serials.split(",");

			Boolean seriesAreRigth = validateSeries(conn, serialsArray, serialsSet);

			if (seriesAreRigth) {
				Calendar expireDate = BSDateTimeUtil.string2Calendar(dateExpired, "yyyy-MM-dd");
				Calendar now = Calendar.getInstance();
				if (now.getTimeInMillis() > expireDate.getTimeInMillis()) {
					Integer daysExpired = calculateExpiratedDays(expireDate, now);
					Integer random = getRandom();

					if (daysExpired > random) {
						success = false;
					}
				}
			} else {
				LOG.log(Level.WARNING,
						"No match series with the registered license file:\nLI:[{0}] \nDB:[{1}]",
						BSUtils.array2ObjectArray(showList(serialsArray), showList(serialsSet)));
				success = false;
			}
		}
		return success;
	}

	private Set<String> readSerialsFromDB(Connection conn) {
		Set<String> serialsSet = new HashSet<String>();
		BSBeanUtils bu = new BSBeanUtils();

		@SuppressWarnings("unchecked")
		List<Machine> machines = (List<Machine>) bu.listAll(conn, new Machine());

		for (Machine machine : machines) {
			serialsSet.add(machine.getSerial());
		}

		return serialsSet;
	}

	private String decrypt(String fileContent) {
		BSSecurity security = new BSSecurity();
		return security.decript3des(fileContent);
	}

	public String readFile(String pathFile) {
		String out = null;

		LOG.log(Level.FINE, "Reading license file {0}", pathFile);
		File file = new File(pathFile);
		if (!file.exists()) {
			throw new BSConfigurationException("File not found '" + pathFile + "'");
		}
		try {
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			out = new String(chars);
			reader.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error reading file '" + pathFile + "'", e);
			throw new BSSystemException(e);
		}

		return out;
	}

	private Integer getRandom() {
		Double rnd = Math.random();
		rnd *= 100;
		return rnd.intValue() + 1;
	}

	private String showList(Set<String> serialsSet) {
		return showList((String[]) serialsSet.toArray(new String[] {}));
	}

	private String showList(String[] serialsArray) {
		String out = "";

		for (String s : serialsArray) {
			out += s + ",";
		}
		out = repairString(out);
		return out;
	}

	private String repairString(String out) {
		if (out.length() > 0) {
			out = out.substring(0, out.length() - 1);
		}
		return out;
	}

	private Integer calculateExpiratedDays(Calendar expireDate, Calendar now) {
		return (int) ((now.getTimeInMillis() - expireDate.getTimeInMillis()) / ONE_DAY);
	}

	private Boolean validateSeries(Connection conn, String[] serialList, Set<String> serialsDB) {
		Boolean out = true;

		if (serialsDB.size() == 0 && serialList.length == 0) {
			out = true;
		} else {
			if (serialList.length != serialsDB.size()) {
				out = false;
			} else {
				for (String serial : serialList) {
					if (!serialsDB.contains(serial)) {
						out = false;
						break;
					}
				}
			}
		}
		return out;
	}
}
