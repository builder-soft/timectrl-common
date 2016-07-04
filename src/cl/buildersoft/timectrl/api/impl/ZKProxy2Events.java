package cl.buildersoft.timectrl.api.impl;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.timectrl.api.com4j._ZKProxy2;
import cl.buildersoft.timectrl.api.com4j.events.__ZKProxy2;
import cl.buildersoft.timectrl.business.beans.AttendanceLog;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.services.MachineService2;
import cl.buildersoft.timectrl.business.services.impl.MachineServiceImpl2;

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
	private static final Logger LOG = LogManager.getLogger(ZKProxy2Events.class);
	String dsName = null;
	Machine machine = null;

	public ZKProxy2Events(String dsName, Machine machine) {
		this.dsName = dsName;
		this.machine = machine;
	}

	@DISPID(1)
	public void onConnected() {
		LOG.info(String.format("%s:%d onConnected()", machine.getIp(), machine.getPort()));

	}

	@DISPID(3)
	public void onDisConnected() {
		LOG.trace(String.format("%s:%d onDisConnected()", machine.getIp(), machine.getPort()));
	}

	@DISPID(13)
	public void onVerify(int userID) {
		LOG.trace(String.format("onVerify(%d)", userID));
	}

	@DISPID(16)
	public void onAttTransactionEx(String enrollNumber, int isInValid, int attState, int verifyMethod, int year, int month,
			int day, int hour, int minute, int second, int workCode) {
		LOG.entry(enrollNumber, isInValid, attState, verifyMethod, year, month, day, hour, minute, second, workCode);

		if (isInValid == 0) {
			BSConnectionFactory cf = new BSConnectionFactory();
			MachineService2 ms2 = new MachineServiceImpl2();
			AttendanceLog al = getAttendanceLog(enrollNumber, isInValid, attState, verifyMethod, year, month, day, hour, minute,
					second, workCode);
			Connection conn = cf.getConnection(this.dsName);
			if (!ms2.existsAttendanceLog(conn, al)) {
				ms2.saveAttendanceLog(conn, al);
			}
			cf.closeConnection(conn);
		} else {
			LOG.warn(String.format(getStringForLog(), machine.getIp(), machine.getPort(), enrollNumber, isInValid, attState,
					verifyMethod, year, month, day, hour, minute, second, workCode));
		}
		LOG.exit();
	}

	private String getStringForLog() {
		return "Mark is invalid for %s:%d values are:(enrollNumber=%s, isInValid=%d, attState=%d, verifyMethod=%d, year=%d, month=%d, day=%d, hour=%d, minute=%d, second=%d, workCode=%d)";
	}

	private AttendanceLog getAttendanceLog(String enrollNumber, int isInValid, int attState, int verifyMethod, int year,
			int month, int day, int hour, int minute, int second, int workCode) {
		AttendanceLog out = new AttendanceLog();
		out.setEmployeeKey(enrollNumber);
		out.setMachine(machine.getId());

		out.setDay(day);
		out.setHour(hour);
		out.setMarkType((long) attState);
		out.setMinute(minute);
		out.setMonth(month);
		out.setSecond(second);
		out.setYear(year);

		return out;
	}

	@DISPID(8)
	public void onFinger() {
		LOG.trace("onFinger");
	}

	@Override
	public boolean regEvent(int machine, int eventMask) {
		return false;
	}

	@Override
	public boolean connectAndRegEvent(String ip, short port, int machine) {
		return false;
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

}
