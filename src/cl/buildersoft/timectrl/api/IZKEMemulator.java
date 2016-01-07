package cl.buildersoft.timectrl.api;

import com4j.Com4jObject;
import com4j.ComThread;
import com4j.EventCookie;
import com4j.Holder;

public class IZKEMemulator implements _zkemProxy {
	public int maxRecords = 4;
	private Integer counter = 0;

	@Override
	public void getLastError(Holder<Integer> dwErrorCode) {
		dwErrorCode.value = -100;
	}

	@Override
	public boolean readAllUserID(int dwMachineNumber) {
		this.counter = 0;
		return true;
	}

	@Override
	public boolean getSerialNumber(int dwMachineNumber, Holder<String> dwSerialNumber) {
		Integer valueInteger = 0;
		String key = "dwSerialNumber";

		String valueString = System.getProperty(key);
		if (valueString == null) {
			valueInteger = 101;
		} else {
			valueInteger = Integer.parseInt(valueString);
			valueInteger++;
		}
		valueString = "" + valueInteger;
		System.setProperty(key, valueString);
		dwSerialNumber.value = valueString;
		return true;
	}

	@Override
	public boolean refreshData(int dwMachineNumber) {
		return true;
	}

	@Override
	public boolean beginBatchUpdate(int dwMachineNumber, int updateFlag) {
		this.counter = 0;
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

		this.counter++;

		dwEnrollNumber.value = "77" + counter;
		dwVerifyMode.value = 1;
		dwInOutMode.value = 1;
		dwYear.value = 2020;
		dwMonth.value = 7;
		dwDay.value = 27;
		dwHour.value = 23;
		dwMinute.value = 38;
		dwSecond.value = 19;
		dwWorkCode.value = 1;

		if (counter == 101) {
//			dwDay.value = 32;
		}

		return counter <= maxRecords;
	}

	@Override
	public boolean ssR_GetAllUserInfo(int dwMachineNumber, Holder<String> dwEnrollNumber, Holder<String> name,
			Holder<String> password, Holder<Integer> privilege, Holder<Boolean> enabled) {
		this.counter++;

		dwEnrollNumber.value = "13" + this.counter;
		name.value = "Juan Perez " + this.counter;
		password.value = "xxxxxxxxxxxx";
		privilege.value = 1;
		enabled.value = true;

		return this.counter <= maxRecords;
	}

	@Override
	public boolean getUserTmpExStr(int dwMachineNumber, String dwEnrollNumber, int dwFingerIndex, Holder<Integer> flag,
			Holder<String> tmpData, Holder<Integer> tmpLength) {
		Boolean out = false;
		if (dwFingerIndex == 2) {
			flag.value = 1;
			tmpData.value = "abscdefghijklm0987654321";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public ComThread getComThread() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getIUnknownPointer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getPointer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPtr() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends Com4jObject> boolean is(Class<T> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T extends Com4jObject> T queryInterface(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean connect_Net(String ip, short port) {
		return true;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean enableDevice(int machine, boolean enable) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean readGeneralLogData(int machine) {
		return true;
	}

	@Override
	public boolean ssR_GetUserInfo(int machineNumber, Holder<String> enrollNumber, Holder<String> name, Holder<String> password,
			Holder<Integer> privilege, Holder<Boolean> enabled) {

		enrollNumber.value = "13" + this.counter;
		name.value = "Juan Perez " + this.counter;
		password.value = "xxxxxxxxxxxx";
		privilege.value = 1;
		enabled.value = true;

		return true;
	}

	@Override
	public boolean ssR_SetUserInfo(int machine, String enrollNumber, String name, String password, int privilege, boolean enabled) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ssR_DeleteEnrollData(int machine, String enrollNumber, int backupNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean clearGLog(int machine) {
		// TODO Auto-generated method stub
		return false;
	}

	private String cardNumber = null;

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

}
