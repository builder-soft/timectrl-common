package cl.buildersoft.timectrl.business.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
public class SendReportByMailImpl extends AbstractReportService implements ReportService {
	private String subReport = null;
	private String server = null;
	private String port = null;
	private String enableTLS = null;
	private String smtpAuth = null;
	private String username = null;
	private String password = null;
	private String subject = null;
	private String messageText = null;
	private String destiny = null;
	private String manpowerMail = null;

	// private IdRut idRut = null;

	private List<ReportPropertyBean> loadReportProperties_(Connection conn, Long idReport) {
		List<ReportPropertyBean> properties = super.loadReportProperties(conn, idReport);

		return properties;
	}

	private List<ReportParameterBean> loadInputParameter_(Connection conn, Long idReport) {
		return null;
	}

	public List<String> execute(Connection conn, Long idReport, ReportType reportType,
			List<ReportPropertyBean> reportPropertyList, List<ReportParameterBean> reportParameterList) {

		/**
		 * <code>
		 * ******************************************************
Supuesto:
Es obligatorio que cada empleado tenga un jefe asignado, as� como los grupos que conversamos la semana pasada?
�o puede ser que existan empleados sin jefe?
lo pregunto, ademas por que cuando se sincronize un reloj que tenga un empleado nuevo, habria que asignarle un "jefe por defecto"
Tambien pensando en cuando se hega el upgrade de version, hay que asignarles un jefe por defecto a todos los empleados
O tal vez, se pueda dejar sin jefe asignado
DEFINICION: El empleado puede tener en el campo jefe el valor NULL, por lo cual, no es necesario que en un inicio se tenga que asignar a todos los empleados un jefe al cual se har� llegar el informe.
*******************************************************

----INICIO----------
obtener propiedad Destiny 
�Propiedad.destino = BOSS_ONLY?
   obtener lista de empleados
   recorrer lista de empleados
	   �El empleado tiene subalternos?
		  Generar informe(Properties, parameters, empleado)
		  Enviar informe generado(empleado)
	   continuar
   continuar   
SI NO �Propiedad.destino = EACH_ONE?
   obtener lista de empleados
   recorrer lista de empleados
      Generar informe(Properties, parameters, empleado)
      Enviar informe generado(empleado)
   continuar   
	
SI NO �Propiedad.destino = MANPOWER? 
   Generar informe(Properties, parameters, null)
   Enviar informe(s) generado(s)(MANPOWER)
SI NO 
   lanzar error de configuracion "Propiedad.destino no configurada"
continuar

Fin
-------------------------------------------------------
Generar informe(idReport, reportType, Properties, parameters, empleado)
buscar subReporte a partir del Key
generar instancia del sub-reporte 
Obtener Parametros y propiedades del subReporte

buscar propiedad outputPath de subReporte
buscar variable de entorno TEMP
establecer valor de propiedad outputPath con el valor de TEMP
buscar outputFile de subReporte
rescatar extencion del subreporte
setear nombre de archivo como {Random} + extencion.

Preparar las propiedades con las 2 recien cambiadas.
filePath = ejecutar subReporte con parametros y nuevas propiedades.
ejectuar subReporte(idReport, reportType, Properties, parameters)

---------APLICA DENTRO DEL INFORME PARA EL CASO EN QUE HAY SUBALTERNOS--------------------
obtener subalternos(empleado)
   OUT = select * from temployee where cBoss = empleado.cid
   recorrer listado de subalternos
      �subalterno tiene subalternos?
	  OUT += obtener subalternos(subalteno)
   continuar
retornar OUT

 * </code>
		 */
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

			break;
		case MANPOWER:
			fileList = executeReport(conn, idReport, reportType, reportPropertyList, reportParameterList);
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

		/**
		 * <code>
Obtener propiedad Destiny 
¿Propiedad.destino = BOSS_ONLY?
   obtener lista de empleados
   recorrer lista de empleados
	   ¿El empleado tiene subalternos?
		  Generar informe(Properties, parameters, empleado)
		  Enviar informe generado(empleado)
	   continuar
   continuar   
SI NO ¿Propiedad.destino = EACH_ONE?
   obtener lista de empleados
   recorrer lista de empleados
      Generar informe(Properties, parameters, empleado)
      Enviar informe generado(empleado)
   continuar
	
SI NO ¿Propiedad.destino = MANPOWER? 
   Generar informe(Properties, parameters, null)
   Enviar informe(s) generado(s)(MANPOWER)
SI NO 
   lanzar error de configuracion "Propiedad.destino no configurada"
continuar

Fin
</code>
		 */

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
		return null;
	}

	private void deleteTempFiles(List<String> fileList) {
		File file = null;
		for (String fileName : fileList) {
			file = new File(fileName);
			if (!file.delete()) {
				System.out.println("Cant delete file '" + fileName + "'");
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
		/**
		 * <code>
		ReportPropertyBean outputFile = getOutputFile(subReportPropertyList);
		String extension = getExtension(outputFile);

		String outputFileName = getOutputFileName(extension);
		outputFile.setPropertyValue(outputFileName);
</code>
		 */
		copyParameterValues(reportInputParameterList, subReportInputParameterList);
		/**
		 * <code>
		if (inParams.size() != target.size()) {
			throw new BSConfigurationException("Amount of parameters do not match");
		}
		reportService.fillInputParameters(subReportInputParameterList, target);
</code>
		 */
		out = subReportService.execute(conn, subReport.getId(), getReportType(conn, subReport), subReportPropertyList,
				subReportInputParameterList);

		/**
		 * <code>
		buscar subReporte a partir del Key
		generar instancia del sub-reporte 
		Obtener Parametros y propiedades del subReporte
		
		buscar propiedad outputPath de subReporte
		buscar variable de entorno TEMP
		establecer valor de propiedad outputPath con el valor de TEMP
		buscar outputFile de subReporte
		rescatar extencion del subreporte
		setear nombre de archivo como {Random} + extencion.
		actualizar propiedad outputFile
		
		Preparar las propiedades con las 2 recien cambiadas.
		filePath = ejecutar subReporte con parametros y nuevas propiedades.
		ejectuar subReporte(idReport, reportType, Properties, parameters)

</code>
		 */

		return out;
	}

	private void copyParameterValues(List<ReportParameterBean> reportInputParameterList,
			List<ReportParameterBean> subReportInputParameterList) {
		for (int i = 0; i < reportInputParameterList.size(); i++) {
			subReportInputParameterList.get(i).setValue(reportInputParameterList.get(i).getValue());
		}

	}

	private String getOutputFileName(String extension) {
		return "File-{Random}" + extension;
	}

	private String getExtension(ReportPropertyBean outputFile) {
		String value = outputFile.getPropertyValue();
		return value.substring(value.lastIndexOf("."));
	}

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

	private ReportPropertyBean getOutputFile(List<ReportPropertyBean> subReportPropertyList) {
		return getProperty(subReportPropertyList, "OUTPUT_FILE");
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
		Properties props = System.getProperties();

		List<String> out = new ArrayList<String>();
		String fileName = null;

		props.put("mail.smtp.port", this.port);
		props.put("mail.smtp.starttls.enable", this.enableTLS);
		props.put("mail.smtp.host", this.server);

		props.put("mail.smtp.auth", this.smtpAuth);

		Session session = Session.getDefaultInstance(props);
		// session.setDebug(true);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(this.username));
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
						ex.printStackTrace();
					}
					multipart.addBodyPart(attachPart);

					fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
					out.add("Archivo '" + fileName + "' enviado a " + to);
				}
				message.setContent(multipart);
			}

			Transport transport = session.getTransport("smtp");
			transport.connect(this.server, this.username, this.password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException e) {
			throw new BSConfigurationException(e);
		} catch (MessagingException e) {
			throw new BSConfigurationException(e);
		}
		return out;
	}

	private String[] stringToArray(String to) {
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
			this.username = value;
		} else if ("mail.smtp.password".equalsIgnoreCase(key)) {
			this.password = value;
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
}
