package cl.buildersoft.timectrl.api.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.timectrl.api.com4j._ZKProxy2;

import com4j.Com4jObject;
import com4j.ComThread;
import com4j.EventCookie;
import com4j.Holder;

public class IZKEMemulatorProxy2XML implements _ZKProxy2 {
	private static final String FILENAME = "MachinesEmu.xml";
	private String filePathEmu = null;
	Element rootElement = null;
	Integer errorNumber = null;
	String errorDescr = null;

	Element attendances = null;
	Element employees = null;
	Integer currentAttendance = 0;
	Integer currentEmployee = 0;

	private String cardNumber = null;

	public IZKEMemulatorProxy2XML() {
		BSConfig config = new BSConfig();
		this.filePathEmu = config.fixPath(System.getenv("BS_PATH")) + FILENAME;
	}

	@Override
	public boolean connect_Net(String ip, short port) {
		SAXReader reader = new SAXReader();
		Boolean out = true;
		try {
			Document document = reader.read(this.filePathEmu);
			rootElement = (Element) document.selectSingleNode("/Machines/Machine[@IP='" + ip + "'][@Port='" + port + "']");
			if (rootElement == null) {
				out = false;
				errorNumber = 102;
				errorDescr = "Machine not found.";
			}
		} catch (DocumentException e) {
			errorNumber = 101;
			errorDescr = e.getMessage();
			out = false;
		}
		return out;
	}

	@Override
	public void disconnect() {
		this.rootElement.getDocument().clearContent();
	}

	@Override
	public void getLastError(Holder<Integer> dwErrorCode) {
		dwErrorCode.value = this.errorNumber;
	}

	@Override
	public boolean readAllUserID(int dwMachineNumber) {
		this.employees = this.rootElement.element("Employees");
		return true;
	}

	@Override
	public boolean getSerialNumber(int dwMachineNumber, Holder<String> dwSerialNumber) {
		String serieValue = null;

		Attribute serie = this.rootElement.attribute("Serie");
		if (serie == null) {
			serie = DocumentHelper.createAttribute(this.rootElement, "Serie", "");
			this.rootElement.add(serie);
		}
		if (serie.getValue().trim().length() == 0) {
			serieValue = getRandom();
			serie.setValue(serieValue);
			saveDocument();
		} else {
			serieValue = serie.getValue();
		}

		dwSerialNumber.value = serieValue;
		return true;
	}

	private void saveDocument() {
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileWriter(filePathEmu));
			writer.write(this.rootElement.getDocument());
			writer.close();
		} catch (IOException e) {
			throw new BSConfigurationException(e);
		}
	}

	private String getRandom() {
		SecureRandom secureRandom = new SecureRandom();

		String out = new BigInteger(130, secureRandom).toString(32);

		out = out.length() > 20 ? out.substring(0, 20) : out;
		return out;
	}

	@Override
	public boolean refreshData(int dwMachineNumber) {
		return true;
	}

	@Override
	public boolean beginBatchUpdate(int dwMachineNumber, int updateFlag) {
		return true;
	}

	@Override
	public boolean batchUpdate(int dwMachineNumber) {
		return true;
	}

	@Override
	public boolean ssR_GetGeneralLogData(int dwMachineNumber, Holder<String> dwEnrollNumber, Holder<Integer> dwVerifyMode,
			Holder<Integer> dwInOutMode, Holder<Integer> dwYear, Holder<Integer> dwMonth, Holder<Integer> dwDay,
			Holder<Integer> dwHour, Holder<Integer> dwMinute, Holder<Integer> dwSecond, Holder<Integer> dwWorkCode) {

		Element attendance = (Element) this.attendances.selectSingleNode("Attendance[" + (++currentAttendance) + "]");
		if (attendance != null) {
			dwEnrollNumber.value = attendance.attributeValue("EnrollNumber");
			dwVerifyMode.value = Integer.parseInt(attendance.attributeValue("VerifyMode"));
			dwInOutMode.value = Integer.parseInt(attendance.attributeValue("InOutMode"));
			dwYear.value = Integer.parseInt(attendance.attributeValue("Year"));
			dwMonth.value = Integer.parseInt(attendance.attributeValue("Month"));
			dwDay.value = Integer.parseInt(attendance.attributeValue("Day"));
			dwHour.value = Integer.parseInt(attendance.attributeValue("Hour"));
			dwMinute.value = Integer.parseInt(attendance.attributeValue("Minute"));
			dwSecond.value = Integer.parseInt(attendance.attributeValue("Second"));
			dwWorkCode.value = Integer.parseInt(attendance.attributeValue("WorkCode"));
		}
		return attendance != null;
	}

	@Override
	public boolean ssR_GetAllUserInfo(int dwMachineNumber, Holder<String> dwEnrollNumber, Holder<String> name,
			Holder<String> password, Holder<Integer> privilege, Holder<Boolean> enabled) {

		if (this.employees == null) {
			readAllUserID(dwMachineNumber);
		}
		Element employee = (Element) this.employees.selectSingleNode("Employee[" + (++currentEmployee) + "]");
		if (employee != null) {
			dwEnrollNumber.value = employee.attributeValue("EnrollNumber");
			name.value = employee.attributeValue("Name");
			password.value = employee.attributeValue("Password");
			privilege.value = Integer.parseInt(employee.attributeValue("Privilege"));
			enabled.value = "1".equals(employee.attributeValue("Enabled"));
		}
		return employee != null;
	}

	@Override
	public boolean ssR_GetUserInfo(int machineNumber, Holder<String> enrollNumber, Holder<String> name, Holder<String> password,
			Holder<Integer> privilege, Holder<Boolean> enabled) {

		this.employees = this.rootElement.element("Employees");

		Element employee = (Element) this.employees.selectSingleNode("Employee[@EnrollNumber='" + enrollNumber.value + "']");
		if (employee != null) {
			// enrollNumber.value = employee.attributeValue("EnrollNumber");
			name.value = employee.attributeValue("Name");
			password.value = employee.attributeValue("Password");
			privilege.value = Integer.parseInt(employee.attributeValue("Privilege"));
			enabled.value = "1".equals(employee.attributeValue("Enabled"));
		}
		return employee != null;
	}

	@Override
	public boolean getUserTmpExStr(int dwMachineNumber, String dwEnrollNumber, int dwFingerIndex, Holder<Integer> flag,
			Holder<String> tmpData, Holder<Integer> tmpLength) {
		Boolean out = false;
		if (dwFingerIndex == 2) {
			flag.value = 1;
			tmpData.value = getRandom();
			tmpLength.value = tmpData.value.length();
			out = true;
		}
		return out;
	}

	@Override
	public boolean setUserTmpExStr(int dwMachineNumber, String dwEnrollNumber, int dwFingerIndex, int flag, String tmpData) {
		return true;
	}

	@Override
	public <T> EventCookie advise(Class<T> arg0, T arg1) {

		return null;
	}

	@Override
	public void dispose() {

	}

	@Override
	public ComThread getComThread() {

		return null;
	}

	@Override
	public long getIUnknownPointer() {

		return 0;
	}

	@Override
	public long getPointer() {

		return 0;
	}

	@Override
	public int getPtr() {

		return 0;
	}

	@Override
	public <T extends Com4jObject> boolean is(Class<T> arg0) {

		return false;
	}

	@Override
	public <T extends Com4jObject> T queryInterface(Class<T> arg0) {

		return null;
	}

	@Override
	public void setName(String arg0) {

	}

	@Override
	public boolean enableDevice(int machine, boolean enable) {
		return false;
	}

	@Override
	public boolean readGeneralLogData(int machine) {
		attendances = this.rootElement.element("Attendances");
		return true;
	}

	@Override
	public boolean ssR_SetUserInfo(int machine, String enrollNumber, String name, String password, int privilege, boolean enabled) {

		return false;
	}

	@Override
	public boolean ssR_DeleteEnrollData(int machine, String enrollNumber, int backupNumber) {

		return false;
	}

	@Override
	public boolean clearGLog(int machine) {

		return false;
	}

	@Override
	public boolean getStrCardNumber(Holder<String> aCardNumber) {
		aCardNumber.value = this.cardNumber;
		return true;
	}

	@Override
	public boolean setStrCardNumber(Holder<String> aCardNumber) {
		this.cardNumber = aCardNumber.value;
		return true;
	}

	@Override
	public boolean connectAndRegEvent(String ip, short port, int machine) {
		return connect_Net(ip, port);
	}

	@Override
	public boolean regEvent(int machine, int eventMask) {
		// TODO Auto-generated method stub
		return true;
	}
}
