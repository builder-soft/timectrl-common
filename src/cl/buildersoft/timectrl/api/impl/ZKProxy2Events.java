package cl.buildersoft.timectrl.api.impl;

import java.sql.Connection;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.util.BSConfig;
import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.timectrl.api.com4j._ZKProxy2;
import cl.buildersoft.timectrl.api.com4j.events.__ZKProxy2;
import cl.buildersoft.timectrl.business.beans.AttendanceLog;
import cl.buildersoft.timectrl.business.beans.Machine;
import cl.buildersoft.timectrl.business.beans.MarkType;
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
		LOG.trace(String.format("%s:%d onVerify(%d)", userID, machine.getIp(), machine.getPort()));
	}

	@DISPID(16)
	public void onAttTransactionEx(String enrollNumber, int isInValid, int attState, int verifyMethod, int year, int month,
			int day, int hour, int minute, int second, int workCode) {
		LOG.entry(enrollNumber, isInValid, attState, verifyMethod, year, month, day, hour, minute, second, workCode);

		if (isInValid == 0) {
			MachineService2 ms2 = new MachineServiceImpl2();
			BSConnectionFactory cf = new BSConnectionFactory();
			Connection conn = cf.getConnection(this.dsName);
			AttendanceLog al = getAttendanceLog(enrollNumber, isInValid, resolveMarkType(conn, attState), verifyMethod, year,
					month, day, hour, minute, second, workCode);
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

	private Integer resolveMarkType(Connection conn, int attState) {
		BSBeanUtils bu = new BSBeanUtils();
		MarkType markType = new MarkType();
		bu.search(conn, markType, "cKey=?", attState);

		return markType.getId().intValue();

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
		LOG.trace(String.format("%s:%d onFinger()", machine.getIp(), machine.getPort()));
	}

	@Override
	public boolean regEvent(int machine, int eventMask) {
		return false;
	}

	@Override
	public boolean connectAndRegEvent(String ip, short port, int machine) {
		return false;
	}

	@DISPID(1)
	public void onAlarm(int alarmType, int enrollNumber, int verified) {
		LOG.trace(String.format("%s:%d onAlarm(%d,%d,%d)", machine.getIp(), machine.getPort(), alarmType, enrollNumber, verified));

		BSConnectionFactory cf = new BSConnectionFactory();
		Connection conn = cf.getConnection(this.dsName);
		BSConfig cfg = new BSConfig();

		String host = cfg.getString(conn, "mail.smtp.host");
		String port = cfg.getString(conn, "mail.smtp.port");
		String starttls = cfg.getString(conn, "mail.smtp.starttls.enable");
		String auth = cfg.getString(conn, "mail.smtp.auth");
		String user = cfg.getString(conn, "mail.smtp.user");
		String password = cfg.getString(conn, "mail.smtp.password");
		String destiny = cfg.getString(conn, "mail.destiny");
		String subject = String.format("Alarm on %s:%d", machine.getIp(), machine.getPort());
		String messageText = String.format("Alarm on %s:%d\n AlarmType=%d, EnrollNumber=%d, Verified=%d", machine.getIp(),
				machine.getPort(), alarmType, enrollNumber, verified);

		if (isNullOrEmpty(port) || isNullOrEmpty(starttls) || isNullOrEmpty(host) || isNullOrEmpty(auth) || isNullOrEmpty(user)
				|| isNullOrEmpty(password)) {
			LOG.fatal("Parameters of mail configuration are empty or null " + messageText);
		} else {
			Properties props = System.getProperties();
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.starttls.enable", starttls);
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", auth);

			Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);
			Transport transport = null;

			/***************************/
			try {
				message.setFrom(new InternetAddress(user));
				String[] toArray = stringToArray(destiny);
				InternetAddress[] toAddress = new InternetAddress[toArray.length];

				for (int i = 0; i < toArray.length; i++) {
					toAddress[i] = new InternetAddress(toArray[i]);
				}

				for (int i = 0; i < toAddress.length; i++) {
					message.addRecipient(Message.RecipientType.TO, toAddress[i]);
				}

				message.setSubject(subject);
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(messageText, "text/html");

				/**
				 * <code>
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		
		if (pathAndFileNameList != null && pathAndFileNameList.size() > 0) {
			for (String filePath : pathAndFileNameList) {
				
				MimeBodyPart attachPart = new MimeBodyPart();
				try {
					attachPart.attachFile(filePath);
				} catch (IOException ex) {
					LOG.log(Level.SEVERE, ex.getMessage(), ex);
				}
				multipart.addBodyPart(attachPart);

				fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
				out.add("Archivo '" + fileName + "' enviado a " + to);
			}
			message.setContent(multipart);
		}
	</code>
				 */
				transport = session.getTransport("smtp");
				transport.connect(host, user, password);
				transport.sendMessage(message, message.getAllRecipients());
				/***************************/

			} catch (AddressException e) {
				LOG.fatal(e);
			} catch (MessagingException e) {
				LOG.fatal(e);
			} finally {
				if (transport != null) {
					try {
						transport.close();
					} catch (MessagingException e) {
						LOG.fatal(e);
					}
				}
			}
		}
		LOG.exit();
	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	private String[] stringToArray(String to) {
		LOG.info(String.format("Mail to %s", to));
		to = to.replaceAll(",", ";");
		String[] out = to.split(";");
		return out;
	}

	/************************** Natives methods *********************************/
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
	public boolean connect_Net(String ip, short port) {
		
		return false;
	}

	@Override
	public void disconnect() {
		
	}

	@Override
	public boolean enableDevice(int machine, boolean enable) {
		
		return false;
	}

	@Override
	public boolean readGeneralLogData(int machine) {
		
		return false;
	}

	@Override
	public boolean ssR_GetGeneralLogData(int machine, Holder<String> enrollNumber, Holder<Integer> verifyMode,
			Holder<Integer> inOutMode, Holder<Integer> year, Holder<Integer> month, Holder<Integer> day, Holder<Integer> hour,
			Holder<Integer> minute, Holder<Integer> second, Holder<Integer> workCode) {
		
		return false;
	}

	@Override
	public void getLastError(Holder<Integer> errorCode) {
		
	}

	@Override
	public boolean readAllUserID(int machine) {
		
		return false;
	}

	@Override
	public boolean ssR_GetUserInfo(int machineNumber, Holder<String> enrollNumber, Holder<String> name, Holder<String> password,
			Holder<Integer> privilege, Holder<Boolean> enabled) {
		
		return false;
	}

	@Override
	public boolean ssR_GetAllUserInfo(int machineNumber, Holder<String> enrollNumber, Holder<String> name,
			Holder<String> password, Holder<Integer> privilege, Holder<Boolean> enabled) {
		
		return false;
	}

	@Override
	public boolean getUserTmpExStr(int machine, String enrollNumber, int fingerIndex, Holder<Integer> flag,
			Holder<String> tmpData, Holder<Integer> tmpLength) {
		
		return false;
	}

	@Override
	public boolean beginBatchUpdate(int machine, int updateFlag) {
		
		return false;
	}

	@Override
	public boolean batchUpdate(int machine) {
		
		return false;
	}

	@Override
	public boolean refreshData(int machine) {
		
		return false;
	}

	@Override
	public boolean ssR_SetUserInfo(int machine, String enrollNumber, String name, String password, int privilege, boolean enabled) {
		
		return false;
	}

	@Override
	public boolean setUserTmpExStr(int machine, String enrollNumber, int fingerIndex, int flag, String tmpData) {
		
		return false;
	}

	@Override
	public boolean ssR_DeleteEnrollData(int machine, String enrollNumber, int backupNumber) {
		
		return false;
	}

	@Override
	public boolean getSerialNumber(int machine, Holder<String> serialNumber) {
		
		return false;
	}

	@Override
	public boolean clearGLog(int machine) {
		
		return false;
	}

	@Override
	public boolean getStrCardNumber(Holder<String> aCardNumber) {
		
		return false;
	}

	@Override
	public boolean setStrCardNumber(Holder<String> aCardNumber) {
		
		return false;
	}
}
