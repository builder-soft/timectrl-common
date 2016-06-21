package cl.buildersoft.timectrl.business.console;

import com4j.Com4jObject;
import com4j.ComThread;
import com4j.EventCookie;
import com4j.Holder;

import cl.buildersoft.timectrl.api._IZKEMEvents;

public class ZKEvent implements _IZKEMEvents {

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
	public void onAttTransaction(int enrollNumber, int isInValid, int attState, int verifyMethod, int year, int month, int day,
			int hour, int minute, int second) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyPress(int key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnrollFinger(int enrollNumber, int fingerIndex, int actionResult, int templateLength) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNewUser(int enrollNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEMData(int dataType, int dataLen, Holder<Byte> dataBuffer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected() {
		System.out.println("Connected Event");

	}

	@Override
	public void onDisConnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinger() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onVerify(int userID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFingerFeature(int score) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHIDNum(int cardNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDoor(int eventType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAlarm(int alarmType, int enrollNumber, int verified) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWriteCard(int enrollNumber, int actionResult, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEmptyCard(int actionResult) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeleteTemplate(int enrollNumber, int fingerIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAttTransactionEx(String enrollNumber, int isInValid, int attState, int verifyMethod, int year, int month,
			int day, int hour, int minute, int second, int workCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnrollFingerEx(String enrollNumber, int fingerIndex, int actionResult, int templateLength) {
		// TODO Auto-generated method stub

	}

}
