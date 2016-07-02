package cl.buildersoft.timectrl.api.impl;

import cl.buildersoft.timectrl.api.com4j._ZKProxy2;
import cl.buildersoft.timectrl.api.com4j.events.__ZKProxy2;
import com4j.Com4jObject;
import com4j.ComThread;
import com4j.DISPID;
import com4j.EventCookie;
import com4j.Holder;

/**
 * @author cmoscoso
 * 
 */
public class ZKProxy2Events extends __ZKProxy2 implements _ZKProxy2 {
	@DISPID(1)
	public void onConnected() {
		System.out.println("onConnected :D");
	}

	@DISPID(3)
	public void onDisConnected() {
		System.out.println("onDisConnected");
	}

	@DISPID(13)
	public void onVerify(int userID) {
		System.out.println("onVerify" + userID);
	}

	@DISPID(16)
	public void onAttTransactionEx(java.lang.String enrollNumber, int isInValid, int attState, int verifyMethod, int year,
			int month, int day, int hour, int minute, int second, int workCode) {
		System.out.println("onAttTransactionEx");
	}

	@DISPID(8)
	public void onFinger() {
		System.out.println("onFinger");
	}

	/** Natives methods */
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
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ssR_GetGeneralLogData(int machine, Holder<String> enrollNumber, Holder<Integer> verifyMode,
			Holder<Integer> inOutMode, Holder<Integer> year, Holder<Integer> month, Holder<Integer> day, Holder<Integer> hour,
			Holder<Integer> minute, Holder<Integer> second, Holder<Integer> workCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getLastError(Holder<Integer> errorCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean readAllUserID(int machine) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ssR_GetUserInfo(int machineNumber, Holder<String> enrollNumber, Holder<String> name, Holder<String> password,
			Holder<Integer> privilege, Holder<Boolean> enabled) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ssR_GetAllUserInfo(int machineNumber, Holder<String> enrollNumber, Holder<String> name,
			Holder<String> password, Holder<Integer> privilege, Holder<Boolean> enabled) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getUserTmpExStr(int machine, String enrollNumber, int fingerIndex, Holder<Integer> flag,
			Holder<String> tmpData, Holder<Integer> tmpLength) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean beginBatchUpdate(int machine, int updateFlag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean batchUpdate(int machine) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean refreshData(int machine) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ssR_SetUserInfo(int machine, String enrollNumber, String name, String password, int privilege, boolean enabled) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setUserTmpExStr(int machine, String enrollNumber, int fingerIndex, int flag, String tmpData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ssR_DeleteEnrollData(int machine, String enrollNumber, int backupNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getSerialNumber(int machine, Holder<String> serialNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean clearGLog(int machine) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getStrCardNumber(Holder<String> aCardNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setStrCardNumber(Holder<String> aCardNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean regEvent(int machine, int eventMask) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean connectAndRegEvent(String ip, short port, int machine) {
		// TODO Auto-generated method stub
		return false;
	}

}
