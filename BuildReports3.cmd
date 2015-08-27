@echo off
echo.

IF "%BS_PATH%" == "" GOTO error

rem SET ALL_JARS=-classpath "%BS_PATH%\commons-logging-1.1.3.jar;%BS_PATH%\mysql-connector-java-5.1.26-bin.jar;%BS_PATH%\jasperreports-5.6.0.jar;%BS_PATH%\jasperreports-applet-5.6.0.jar;%BS_PATH%\jasperreports-fonts-5.6.0.jar;%BS_PATH%\jasperreports-javaflow-5.6.0.jar;%BS_PATH%\commons-collections-3.2.1.jar;%BS_PATH%\commons-digester-2.1.jar;%BS_PATH%\groovy-all-2.0.1.jar;%BS_PATH%\iText-2.1.7.js2.jar;%BS_PATH%\BSframework-lib-1.3.jar;%BS_PATH%\commons-fileupload-1.2.2.jar;%BS_PATH%\servlet-api.jar;%BS_PATH%\poi-3.11-20141221.jar;%BS_PATH%\timectrl-common.jar;%CATALINA_HOME%\lib\catalina.jar;%BS_PATH%\poi-ooxml-3.11-20141221.jar;%BS_PATH%\xmlbeans-2.6.0.jar;%BS_PATH%\poi-ooxml-schemas-3.11-20141221.jar;%BS_PATH%\javax.mail.jar"
SET ALL_JARS=-classpath "%BS_PATH%\commons-logging-1.1.3.jar;%BS_PATH%\mysql-connector-java-5.1.26-bin.jar;%BS_PATH%\jasperreports-5.6.0.jar;%BS_PATH%\jasperreports-applet-5.6.0.jar;%BS_PATH%\jasperreports-fonts-5.6.0.jar;%BS_PATH%\jasperreports-javaflow-5.6.0.jar;%BS_PATH%\commons-collections-3.2.1.jar;%BS_PATH%\commons-digester-2.1.jar;%BS_PATH%\groovy-all-2.0.1.jar;%BS_PATH%\iText-2.1.7.js2.jar;%BS_PATH%\BSframework-lib-1.3.jar;%BS_PATH%\commons-fileupload-1.2.2.jar;%BS_PATH%\servlet-api.jar;%BS_PATH%\poi-3.11-20141221.jar;%BS_PATH%\timectrl-common.jar;%CATALINA_HOME%\lib\catalina.jar;%BS_PATH%\poi-ooxml-3.11-20141221.jar;%BS_PATH%\xmlbeans-2.6.0.jar;%BS_PATH%\poi-ooxml-schemas-3.11-20141221.jar;%BS_PATH%\javax.mail.jar"


@echo on
java %ALL_JARS% cl.buildersoft.timectrl.business.console.BuildReport3 %*
@echo off

goto end

:error
	echo No esta definida la variable de entorno BS_PATH
	
:end
SET ALL_JARS=