package cl.buildersoft.timectrl.business.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSProgrammerException;
import cl.buildersoft.framework.util.BSConnectionFactory;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.IdRut;
import cl.buildersoft.timectrl.business.beans.Report;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportType;
import cl.buildersoft.timectrl.business.services.ReportService;

/**
 * <code>
 http://leonardotrujillo.com/2014/12/error-contrasena-incorrecta-en-thunderbird-al-acceder-a-gmail/
 
</code>
 */
public class SendReportByMailImpl extends AbstractReportService implements ReportService, Runnable {
	private String subReport = null;
	private String server = null;
	private String port = null;
	private String enableTLS = null;
	private String smtpAuth = null;
	private String usernameMail = null;
	private String passwordMail = null;
	private String subject = null;
	private String messageText = null;
	private String destiny = null;
	private String manpowerMail = null;

	private String dsName = null;
	private Long reportId = null;
	private Integer waitBeforeRun = 0;

	private ReportType reportType = null;
	private List<ReportPropertyBean> reportPropertyList = null;
	private List<ReportParameterBean> reportParameterList = null;

	private static final Logger LOG = Logger.getLogger(SendReportByMailImpl.class.getName());

	@Override
	public void run() {
		BSConnectionFactory cf = new BSConnectionFactory();
		Connection conn = null;
		try {
			Thread.sleep(this.waitBeforeRun * 1000);
			long start = System.currentTimeMillis();

			String thisClassName = SendReportByMailImpl.class.getName();
			LOG.log(Level.INFO, "Start thread of class {0}", thisClassName);

			conn = cf.getConnection(this.dsName);

			execute(conn, this.reportId, this.reportType, reportPropertyList, reportParameterList);

			long end = System.currentTimeMillis();

			LOG.log(Level.INFO, "End thread of class {0}. It ended in {1} miliseconds.",
					BSUtils.array2ObjectArray(thisClassName, end - start));

		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			cf.closeConnection(conn);
		}

	}

	public synchronized List<String> execute(Connection conn, Long idReport, ReportType reportType,
			List<ReportPropertyBean> reportPropertyList, List<ReportParameterBean> reportParameterList) {
		readProperties(conn, reportPropertyList);

		DestinyEnum destiny = getDestiny(reportPropertyList);
		if (destiny == null) {
			throw new BSConfigurationException("No set destination. It can be '" + DestinyEnum.EACH_ONE.name() + "', '"
					+ DestinyEnum.MANPOWER.name() + "' or '" + DestinyEnum.BOSS_ONLY.name() + "'. This was '" + this.destiny
					+ "'");
		}
		List<String> out = null;
		List<String> fileList = null;
		switch (destiny) {
		case BOSS_ONLY:
			/**
			 * <code>
 
 	Busca Id de Jefe usando el parametro Jefe (BOSS_LIST);
 	Generar informe(Properties, parameters )
 	Enviar informe generado(Jefe)
 
    delete file temp
</code>
			 */
			ReportParameterBean bossParameter = getBossParameter(reportParameterList);
			if (bossParameter == null) {
				throw new BSConfigurationException("Report was configured as 'BOSS_ONLY' but 'Boss' parameter not exists.");
			}

			Employee boss = getEmployee(conn, Long.parseLong(bossParameter.getValue()));

			fileList = executeReport(conn, idReport, reportType, reportPropertyList, reportParameterList);
			out = sendMail(fileList, boss.getMail());

			deleteTempFiles(fileList);

			break;
		case MANPOWER:
			fileList = executeReport(conn, idReport, reportType, reportPropertyList, reportParameterList);
			if (fileList != null && fileList.size() >= 1) {
				LOG.log(Level.INFO, "File was created in {0}", fileList.get(0));
			} else {
				LOG.log(Level.SEVERE, "Can not created correcty report file.");
			}
			out = sendMail(fileList, manpowerMail);

			deleteTempFiles(fileList);

			break;
		case EACH_ONE:
			ReportParameterBean employeeParameter = getEmployeeParameter(reportParameterList);
			List<IdRut> employeeList = null;
			if (employeeParameter != null) {
				employeeList = getEmployeeList(conn, employeeParameter.getValue());
			} else {
				employeeList = getEmployeeList(conn, "0");
			}

			out = new ArrayList<String>();
			String mail = null;
			for (IdRut idRut : employeeList) {
				// this.idRut = idRut;
				mail = idRut.getMail();
				if (mail != null && mail.trim().length() > 0) {
					updateEmployeeId(reportParameterList, idRut.getId());

					fileList = executeReport(conn, idReport, reportType, reportPropertyList, reportParameterList);
					List<String> outTemp = sendMail(fileList, mail);

					for (String oneOutTemp : outTemp) {
						out.add(oneOutTemp);
					}

					deleteTempFiles(fileList);
				}
			}
			break;
		}

		return out;
	}

	private Employee getEmployee(Connection conn, Long id) {
		BSBeanUtils bu = new BSBeanUtils();
		Employee out = new Employee();
		out.setId(id);

		bu.search(conn, out);

		return out;
	}

	private ReportParameterBean getBossParameter(List<ReportParameterBean> reportParameterList) {
		ReportParameterBean out = null;
		for (ReportParameterBean reportParameter : reportParameterList) {
			if (reportParameter.getTypeKey().equalsIgnoreCase("BOSS_LIST")) {
				out = reportParameter;
				break;
			}
		}
		return out;
	}

	private void deleteTempFiles(List<String> fileList) {
		File file = null;
		for (String fileName : fileList) {
			file = new File(fileName);
			if (!file.delete()) {
				LOG.log(Level.SEVERE, "Cant delete file '{0}'", fileName);
			} else {
				LOG.log(Level.INFO, "File {0} was deleted", fileName);
			}
		}

	}

	private List<String> executeReport(Connection conn, Long idReport, ReportType reportType,
			List<ReportPropertyBean> reportPropertyList, List<ReportParameterBean> reportInputParameterList) {
		List<String> out = null;

		Report subReport = getReportByKey(conn, this.subReport);
		ReportService subReportService = getInstance(conn, subReport);

		List<ReportPropertyBean> subReportPropertyList = subReportService.loadReportProperties(conn, subReport.getId());
		List<ReportParameterBean> subReportInputParameterList = subReportService.loadParameter(conn, subReport.getId());

		ReportPropertyBean outputPath = getOutputPath(subReportPropertyList);
		String tempPath = getTempPath();
		outputPath.setPropertyValue(tempPath);
		copyParameterValues(reportInputParameterList, subReportInputParameterList);

		out = subReportService.execute(conn, subReport.getId(), getReportType(conn, subReport), subReportPropertyList,
				subReportInputParameterList);

		LOG.log(Level.INFO, "File created was {0}", out.toString());

		return out;
	}

	private void copyParameterValues(List<ReportParameterBean> reportInputParameterList,
			List<ReportParameterBean> subReportInputParameterList) {
		for (int i = 0; i < reportInputParameterList.size(); i++) {
			subReportInputParameterList.get(i).setValue(reportInputParameterList.get(i).getValue());
		}

	}

	/**
	 * <code>
	private String getOutputFileName(String extension) {
		return "File-{Random}" + extension;
	}

	private String getExtension(ReportPropertyBean outputFile) {
		String value = outputFile.getPropertyValue();
		return value.substring(value.lastIndexOf("."));
	}

	private ReportPropertyBean getOutputFile(List<ReportPropertyBean> subReportPropertyList) {
		return getProperty(subReportPropertyList, "OUTPUT_FILE");
	}
</code>
	 */
	private String getTempPath() {
		String out = System.getProperty("java.io.tmpdir");
		if (out == null) {
			out = System.getenv("TEMP");
			if (out == null) {
				out = System.getenv("TMP");
			}
		}
		return out;
	}

	private ReportPropertyBean getOutputPath(List<ReportPropertyBean> subReportPropertyList) {
		return getProperty(subReportPropertyList, "OUTPUT_PATH");
	}

	private ReportPropertyBean getProperty(List<ReportPropertyBean> subReportPropertyList, String propertyKey) {
		ReportPropertyBean out = null;

		for (ReportPropertyBean prb : subReportPropertyList) {
			if (propertyKey.equalsIgnoreCase(prb.getPropertyTypeKey())) {
				out = prb;
				break;
			}
		}

		return out;
	}

	private Report getReportByKey(Connection conn, String subReportKey) {
		BSBeanUtils bu = new BSBeanUtils();
		Report out = new Report();

		if (!bu.search(conn, out, "cKey=?", subReportKey)) {
			throw new BSConfigurationException("Report '" + subReportKey + NOT_FOUND);
		}

		return out;
	}

	private DestinyEnum getDestiny(List<ReportPropertyBean> reportPropertyList) {
		// BOSS_ONLY, EACH_ONE, MANPOWER
		DestinyEnum out = null;
		if ("EACH_ONE".equalsIgnoreCase(this.destiny)) {
			out = DestinyEnum.EACH_ONE;
		} else if ("MANPOWER".equalsIgnoreCase(this.destiny)) {
			out = DestinyEnum.MANPOWER;
		} else if ("BOSS_ONLY".equalsIgnoreCase(this.destiny)) {
			out = DestinyEnum.BOSS_ONLY;
		}
		return out;
	}

	private List<String> sendMail(List<String> pathAndFileNameList, String to) {
		/**
		 * <code>
 http://www.codejava.net/java-ee/javamail/send-e-mail-with-attachment-in-java
 </code>
		 */
		List<String> out = new ArrayList<String>();
		if (to != null) {
			Properties props = System.getProperties();

			String fileName = null;

			props.put("mail.smtp.port", this.port);
			props.put("mail.smtp.starttls.enable", this.enableTLS);
			props.put("mail.smtp.host", this.server);

			props.put("mail.smtp.auth", this.smtpAuth);

			Session session = Session.getDefaultInstance(props);
			// session.setDebug(true);
			MimeMessage message = new MimeMessage(session);

			Transport transport = null;
			try {
				message.setFrom(new InternetAddress(this.usernameMail));
				String[] toArray = stringToArray(to);
				InternetAddress[] toAddress = new InternetAddress[toArray.length];

				// To get the array of addresses
				for (int i = 0; i < toArray.length; i++) {
					toAddress[i] = new InternetAddress(toArray[i]);
				}

				for (int i = 0; i < toAddress.length; i++) {
					message.addRecipient(Message.RecipientType.TO, toAddress[i]);
				}

				message.setSubject(this.subject);
				// message.setText(this.messageText);

				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(this.messageText, "text/html");

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
				transport = session.getTransport("smtp");
				transport.connect(this.server, this.usernameMail, this.passwordMail);
				transport.sendMessage(message, message.getAllRecipients());
			} catch (AddressException e) {
				LOG.log(Level.SEVERE, "File " + pathAndFileNameList.toString() + " can't sended to " + to + "", e);
				// throw new BSConfigurationException(e);
			} catch (MessagingException e) {
				LOG.log(Level.SEVERE, "File " + pathAndFileNameList.toString() + " can't sended to " + to + "", e);
				// throw new BSConfigurationException(e);
			} finally {
				if (transport != null) {
					try {
						transport.close();
					} catch (MessagingException e) {
						LOG.log(Level.SEVERE, e.getMessage(), e);
					}
				}
			}
		}
		return out;
	}

	private String[] stringToArray(String to) {
		LOG.log(Level.CONFIG, "Mail to {0}", to);
		to = to.replaceAll(",", ";");
		String[] out = to.split(";");
		return out;
	}

	private ReportType getReportType(Connection conn, Report report) {
		ReportType reportType = new ReportType();
		reportType.setId(report.getType());
		BSBeanUtils bu = new BSBeanUtils();
		if (!bu.search(conn, reportType)) {
			throw new BSProgrammerException("Report type '" + report.getType() + NOT_FOUND);
		}
		return reportType;
	}

	@Override
	protected Boolean getPropertyValue(String key, String value) {
		Boolean out = true;
		if ("SUB_REPORT".equalsIgnoreCase(key)) {
			this.subReport = value;
		} else if ("mail.smtp.host".equalsIgnoreCase(key)) {
			this.server = value;
		} else if ("mail.smtp.port".equalsIgnoreCase(key)) {
			this.port = value;
		} else if ("mail.smtp.port".equalsIgnoreCase(key)) {
			this.port = value;
		} else if ("mail.smtp.starttls.enable".equalsIgnoreCase(key)) {
			this.enableTLS = value;
		} else if ("mail.smtp.auth".equalsIgnoreCase(key)) {
			this.smtpAuth = value;
		} else if ("mail.smtp.user".equalsIgnoreCase(key)) {
			this.usernameMail = value;
		} else if ("mail.smtp.password".equalsIgnoreCase(key)) {
			this.passwordMail = value;
		} else if ("SUBJECT".equalsIgnoreCase(key)) {
			this.subject = value;
		} else if ("TEXT".equalsIgnoreCase(key)) {
			this.messageText = value;
		} else if ("DESTINY".equalsIgnoreCase(key)) {
			this.destiny = value.trim();
		} else if ("MANPOWER_MAIL".equalsIgnoreCase(key)) {
			this.manpowerMail = value;
		}
		return out;
	}

	@Override
	protected String parseCustomVariable(String key) {
		return null;
	}

	@Override
	public Boolean runAsDetachedThread() {
		return true;
	}

	@Override
	public void setConnectionData(String dsName) {
		this.dsName = dsName;

	}

	@Override
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	@Override
	public void setReportType(ReportType reportType) {
		this.reportType = reportType;

	}

	@Override
	public void setReportPropertyList(List<ReportPropertyBean> reportPropertyList) {
		this.reportPropertyList = reportPropertyList;
	}

	@Override
	public void setReportParameterList(List<ReportParameterBean> reportParameterList) {
		this.reportParameterList = reportParameterList;
	}

	@Override
	public void waitBeforeRun(Integer seconds) {
		this.waitBeforeRun = waitBeforeRun;

	}

}
