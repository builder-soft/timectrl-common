@echo off
echo.

IF "%BS_PATH%" == "" GOTO error

SET ALL_JARS=-classpath "%BS_PATH%\lib\commons-logging-1.1.3.jar;%JAVA_HOME%\lib\mysql-connector-java-5.1.26-bin.jar;%BS_PATH%\lib\jasperreports-5.6.0.jar;%BS_PATH%\lib\jasperreports-applet-5.6.0.jar;%BS_PATH%\lib\jasperreports-fonts-5.6.0.jar;%BS_PATH%\lib\jasperreports-javaflow-5.6.0.jar;%BS_PATH%\lib\commons-collections-3.2.1.jar;%BS_PATH%\lib\commons-digester-2.1.jar;%BS_PATH%\lib\groovy-all-2.0.1.jar;%BS_PATH%\lib\iText-2.1.7.js2.jar;%BS_PATH%\lib\BSframework-lib-1.3.jar;%BS_PATH%\lib\commons-fileupload-1.2.2.jar;%CATALINA_HOME%\lib\servlet-api.jar;%BS_PATH%\lib\poi-3.11-20141221.jar;%BS_PATH%\lib\timectrl-common.jar;%CATALINA_HOME%\lib\catalina.jar;%BS_PATH%\lib\poi-ooxml-3.11-20141221.jar;%BS_PATH%\lib\xmlbeans-2.6.0.jar;%BS_PATH%\lib\poi-ooxml-schemas-3.11-20141221.jar;%BS_PATH%\lib\javax.mail.jar;%BS_PATH%\lib\dom4j-1.6.1.jar;%BS_PATH%\lib\jaxen-1.1-beta-6.jar"

@echo on
java %ALL_JARS% cl.buildersoft.timectrl.business.process.impl.BuildReport4 %*
@echo off

goto end

:error
	echo No esta definida la variable de entorno BS_PATH
	
:end
SET ALL_JARS=