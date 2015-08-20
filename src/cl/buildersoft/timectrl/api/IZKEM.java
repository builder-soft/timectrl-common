package cl.buildersoft.timectrl.api  ;

import com4j.Com4jObject;
import com4j.DISPID;
import com4j.Holder;
import com4j.IID;
import com4j.VTID;

/**
 * IZKEM Interface
 */
@IID("{102F4206-E43D-4FC9-BAB0-331CFFE4D25B}")
@Deprecated
public interface IZKEM extends Com4jObject {
  // Methods:
  /**
   * <p>
   * Property ReadMark
   * </p>
   * <p>
   * Getter method for the COM property "ReadMark"
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(7)
  boolean readMark();


  /**
   * <p>
   * Property ReadMark
   * </p>
   * <p>
   * Setter method for the COM property "ReadMark"
   * </p>
   * @param pVal Mandatory boolean parameter.
   */

  @DISPID(1) //= 0x1. The runtime will prefer the VTID if present
  @VTID(8)
  void readMark(
    boolean pVal);


  /**
   * <p>
   * Property CommPort
   * </p>
   * <p>
   * Getter method for the COM property "CommPort"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(9)
  int commPort();


  /**
   * <p>
   * Property CommPort
   * </p>
   * <p>
   * Setter method for the COM property "CommPort"
   * </p>
   * @param pVal Mandatory int parameter.
   */

  @DISPID(2) //= 0x2. The runtime will prefer the VTID if present
  @VTID(10)
  void commPort(
    int pVal);


  /**
   * <p>
   * Method ClearAdministrators
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(3) //= 0x3. The runtime will prefer the VTID if present
  @VTID(11)
  boolean clearAdministrators(
    int dwMachineNumber);


  /**
   * <p>
   * Method DeleteEnrollData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwEMachineNumber Mandatory int parameter.
   * @param dwBackupNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(4) //= 0x4. The runtime will prefer the VTID if present
  @VTID(12)
  boolean deleteEnrollData(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwEMachineNumber,
    int dwBackupNumber);


  /**
   * <p>
   * Method ReadSuperLogData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(5) //= 0x5. The runtime will prefer the VTID if present
  @VTID(13)
  boolean readSuperLogData(
    int dwMachineNumber);


  /**
   * <p>
   * Method ReadAllSLogData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(6) //= 0x6. The runtime will prefer the VTID if present
  @VTID(14)
  boolean readAllSLogData(
    int dwMachineNumber);


  /**
   * <p>
   * Method ReadGeneralLogData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(7) //= 0x7. The runtime will prefer the VTID if present
  @VTID(15)
  boolean readGeneralLogData(
    int dwMachineNumber);


  /**
   * <p>
   * Method ReadAllGLogData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(8) //= 0x8. The runtime will prefer the VTID if present
  @VTID(16)
  boolean readAllGLogData(
    int dwMachineNumber);


  /**
   * <p>
   * Method EnableUser
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwEMachineNumber Mandatory int parameter.
   * @param dwBackupNumber Mandatory int parameter.
   * @param bFlag Mandatory boolean parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(9) //= 0x9. The runtime will prefer the VTID if present
  @VTID(17)
  boolean enableUser(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwEMachineNumber,
    int dwBackupNumber,
    boolean bFlag);


  /**
   * <p>
   * Method EnableDevice
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param bFlag Mandatory boolean parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(10) //= 0xa. The runtime will prefer the VTID if present
  @VTID(18)
  boolean enableDevice(
    int dwMachineNumber,
    boolean bFlag);


  /**
   * <p>
   * Method GetDeviceStatus
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwStatus Mandatory int parameter.
   * @param dwValue Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(11) //= 0xb. The runtime will prefer the VTID if present
  @VTID(19)
  boolean getDeviceStatus(
    int dwMachineNumber,
    int dwStatus,
    Holder<Integer> dwValue);


  /**
   * <p>
   * Method GetDeviceInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwInfo Mandatory int parameter.
   * @param dwValue Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(12) //= 0xc. The runtime will prefer the VTID if present
  @VTID(20)
  boolean getDeviceInfo(
    int dwMachineNumber,
    int dwInfo,
    Holder<Integer> dwValue);


  /**
   * <p>
   * Method SetDeviceInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwInfo Mandatory int parameter.
   * @param dwValue Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(13) //= 0xd. The runtime will prefer the VTID if present
  @VTID(21)
  boolean setDeviceInfo(
    int dwMachineNumber,
    int dwInfo,
    int dwValue);


  /**
   * <p>
   * Method SetDeviceTime
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(14) //= 0xe. The runtime will prefer the VTID if present
  @VTID(22)
  boolean setDeviceTime(
    int dwMachineNumber);


  /**
   * <p>
   * Method GetBackupNumber
   * </p>
   */

  @DISPID(15) //= 0xf. The runtime will prefer the VTID if present
  @VTID(23)
  void powerOnAllDevice();


  /**
   * <p>
   * Method PowerOffDevice
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(16) //= 0x10. The runtime will prefer the VTID if present
  @VTID(24)
  boolean powerOffDevice(
    int dwMachineNumber);


  /**
   * <p>
   * Method ModifyPrivilege
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwEMachineNumber Mandatory int parameter.
   * @param dwBackupNumber Mandatory int parameter.
   * @param dwMachinePrivilege Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(17) //= 0x11. The runtime will prefer the VTID if present
  @VTID(25)
  boolean modifyPrivilege(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwEMachineNumber,
    int dwBackupNumber,
    int dwMachinePrivilege);


  /**
   * <p>
   * Method GetLastError
   * </p>
   * @param dwErrorCode Mandatory Holder<Integer> parameter.
   */

  @DISPID(18) //= 0x12. The runtime will prefer the VTID if present
  @VTID(26)
  void getLastError(
    Holder<Integer> dwErrorCode);


  /**
   * <p>
   * Method GetEnrollData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwEMachineNumber Mandatory int parameter.
   * @param dwBackupNumber Mandatory int parameter.
   * @param dwMachinePrivilege Mandatory Holder<Integer> parameter.
   * @param dwEnrollData Mandatory Holder<Integer> parameter.
   * @param dwPassWord Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(19) //= 0x13. The runtime will prefer the VTID if present
  @VTID(27)
  boolean getEnrollData(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwEMachineNumber,
    int dwBackupNumber,
    Holder<Integer> dwMachinePrivilege,
    Holder<Integer> dwEnrollData,
    Holder<Integer> dwPassWord);


  /**
   * <p>
   * Method SetEnrollData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwEMachineNumber Mandatory int parameter.
   * @param dwBackupNumber Mandatory int parameter.
   * @param dwMachinePrivilege Mandatory int parameter.
   * @param dwEnrollData Mandatory Holder<Integer> parameter.
   * @param dwPassWord Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(20) //= 0x14. The runtime will prefer the VTID if present
  @VTID(28)
  boolean setEnrollData(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwEMachineNumber,
    int dwBackupNumber,
    int dwMachinePrivilege,
    Holder<Integer> dwEnrollData,
    int dwPassWord);


  /**
   * <p>
   * Method GetDeviceTime
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwYear Mandatory Holder<Integer> parameter.
   * @param dwMonth Mandatory Holder<Integer> parameter.
   * @param dwDay Mandatory Holder<Integer> parameter.
   * @param dwHour Mandatory Holder<Integer> parameter.
   * @param dwMinute Mandatory Holder<Integer> parameter.
   * @param dwSecond Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(21) //= 0x15. The runtime will prefer the VTID if present
  @VTID(29)
  boolean getDeviceTime(
    int dwMachineNumber,
    Holder<Integer> dwYear,
    Holder<Integer> dwMonth,
    Holder<Integer> dwDay,
    Holder<Integer> dwHour,
    Holder<Integer> dwMinute,
    Holder<Integer> dwSecond);


  /**
   * <p>
   * Method GetGeneralLogData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwTMachineNumber Mandatory Holder<Integer> parameter.
   * @param dwEnrollNumber Mandatory Holder<Integer> parameter.
   * @param dwEMachineNumber Mandatory Holder<Integer> parameter.
   * @param dwVerifyMode Mandatory Holder<Integer> parameter.
   * @param dwInOutMode Mandatory Holder<Integer> parameter.
   * @param dwYear Mandatory Holder<Integer> parameter.
   * @param dwMonth Mandatory Holder<Integer> parameter.
   * @param dwDay Mandatory Holder<Integer> parameter.
   * @param dwHour Mandatory Holder<Integer> parameter.
   * @param dwMinute Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(22) //= 0x16. The runtime will prefer the VTID if present
  @VTID(30)
  boolean getGeneralLogData(
    int dwMachineNumber,
    Holder<Integer> dwTMachineNumber,
    Holder<Integer> dwEnrollNumber,
    Holder<Integer> dwEMachineNumber,
    Holder<Integer> dwVerifyMode,
    Holder<Integer> dwInOutMode,
    Holder<Integer> dwYear,
    Holder<Integer> dwMonth,
    Holder<Integer> dwDay,
    Holder<Integer> dwHour,
    Holder<Integer> dwMinute);


  /**
   * <p>
   * Method GetSuperLogData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwTMachineNumber Mandatory Holder<Integer> parameter.
   * @param dwSEnrollNumber Mandatory Holder<Integer> parameter.
   * @param params4 Mandatory Holder<Integer> parameter.
   * @param params1 Mandatory Holder<Integer> parameter.
   * @param params2 Mandatory Holder<Integer> parameter.
   * @param dwManipulation Mandatory Holder<Integer> parameter.
   * @param params3 Mandatory Holder<Integer> parameter.
   * @param dwYear Mandatory Holder<Integer> parameter.
   * @param dwMonth Mandatory Holder<Integer> parameter.
   * @param dwDay Mandatory Holder<Integer> parameter.
   * @param dwHour Mandatory Holder<Integer> parameter.
   * @param dwMinute Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(23) //= 0x17. The runtime will prefer the VTID if present
  @VTID(31)
  boolean getSuperLogData(
    int dwMachineNumber,
    Holder<Integer> dwTMachineNumber,
    Holder<Integer> dwSEnrollNumber,
    Holder<Integer> params4,
    Holder<Integer> params1,
    Holder<Integer> params2,
    Holder<Integer> dwManipulation,
    Holder<Integer> params3,
    Holder<Integer> dwYear,
    Holder<Integer> dwMonth,
    Holder<Integer> dwDay,
    Holder<Integer> dwHour,
    Holder<Integer> dwMinute);


  /**
   * <p>
   * Method GetAllSLogData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwTMachineNumber Mandatory Holder<Integer> parameter.
   * @param dwSEnrollNumber Mandatory Holder<Integer> parameter.
   * @param params4 Mandatory Holder<Integer> parameter.
   * @param params1 Mandatory Holder<Integer> parameter.
   * @param params2 Mandatory Holder<Integer> parameter.
   * @param dwManipulation Mandatory Holder<Integer> parameter.
   * @param params3 Mandatory Holder<Integer> parameter.
   * @param dwYear Mandatory Holder<Integer> parameter.
   * @param dwMonth Mandatory Holder<Integer> parameter.
   * @param dwDay Mandatory Holder<Integer> parameter.
   * @param dwHour Mandatory Holder<Integer> parameter.
   * @param dwMinute Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(24) //= 0x18. The runtime will prefer the VTID if present
  @VTID(32)
  boolean getAllSLogData(
    int dwMachineNumber,
    Holder<Integer> dwTMachineNumber,
    Holder<Integer> dwSEnrollNumber,
    Holder<Integer> params4,
    Holder<Integer> params1,
    Holder<Integer> params2,
    Holder<Integer> dwManipulation,
    Holder<Integer> params3,
    Holder<Integer> dwYear,
    Holder<Integer> dwMonth,
    Holder<Integer> dwDay,
    Holder<Integer> dwHour,
    Holder<Integer> dwMinute);


  /**
   * <p>
   * Method GetAllGLogData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwTMachineNumber Mandatory Holder<Integer> parameter.
   * @param dwEnrollNumber Mandatory Holder<Integer> parameter.
   * @param dwEMachineNumber Mandatory Holder<Integer> parameter.
   * @param dwVerifyMode Mandatory Holder<Integer> parameter.
   * @param dwInOutMode Mandatory Holder<Integer> parameter.
   * @param dwYear Mandatory Holder<Integer> parameter.
   * @param dwMonth Mandatory Holder<Integer> parameter.
   * @param dwDay Mandatory Holder<Integer> parameter.
   * @param dwHour Mandatory Holder<Integer> parameter.
   * @param dwMinute Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(25) //= 0x19. The runtime will prefer the VTID if present
  @VTID(33)
  boolean getAllGLogData(
    int dwMachineNumber,
    Holder<Integer> dwTMachineNumber,
    Holder<Integer> dwEnrollNumber,
    Holder<Integer> dwEMachineNumber,
    Holder<Integer> dwVerifyMode,
    Holder<Integer> dwInOutMode,
    Holder<Integer> dwYear,
    Holder<Integer> dwMonth,
    Holder<Integer> dwDay,
    Holder<Integer> dwHour,
    Holder<Integer> dwMinute);


  /**
   * <p>
   * Method ConvertPassword
   * </p>
   * @param dwSrcPSW Mandatory int parameter.
   * @param dwDestPSW Mandatory Holder<Integer> parameter.
   * @param dwLength Mandatory int parameter.
   */

  @DISPID(26) //= 0x1a. The runtime will prefer the VTID if present
  @VTID(34)
  void convertPassword(
    int dwSrcPSW,
    Holder<Integer> dwDestPSW,
    int dwLength);


  /**
   * <p>
   * Method ReadAllUserID
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(27) //= 0x1b. The runtime will prefer the VTID if present
  @VTID(35)
  boolean readAllUserID(
    int dwMachineNumber);


  /**
   * <p>
   * Method GetAllUserID
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory Holder<Integer> parameter.
   * @param dwEMachineNumber Mandatory Holder<Integer> parameter.
   * @param dwBackupNumber Mandatory Holder<Integer> parameter.
   * @param dwMachinePrivilege Mandatory Holder<Integer> parameter.
   * @param dwEnable Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(28) //= 0x1c. The runtime will prefer the VTID if present
  @VTID(36)
  boolean getAllUserID(
    int dwMachineNumber,
    Holder<Integer> dwEnrollNumber,
    Holder<Integer> dwEMachineNumber,
    Holder<Integer> dwBackupNumber,
    Holder<Integer> dwMachinePrivilege,
    Holder<Integer> dwEnable);


  /**
   * <p>
   * Method GetSerialNumber
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwSerialNumber Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(29) //= 0x1d. The runtime will prefer the VTID if present
  @VTID(37)
  boolean getSerialNumber(
    int dwMachineNumber,
    Holder<java.lang.String> dwSerialNumber);


  /**
   * <p>
   * Method ClearKeeperData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(30) //= 0x1e. The runtime will prefer the VTID if present
  @VTID(38)
  boolean clearKeeperData(
    int dwMachineNumber);


  /**
   * <p>
   * Method GetBackupNumber
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type int
   */

  @DISPID(32) //= 0x20. The runtime will prefer the VTID if present
  @VTID(39)
  int getBackupNumber(
    int dwMachineNumber);


  /**
   * <p>
   * Method GetProductCode
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param lpszProductCode Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(33) //= 0x21. The runtime will prefer the VTID if present
  @VTID(40)
  boolean getProductCode(
    int dwMachineNumber,
    Holder<java.lang.String> lpszProductCode);


  /**
   * <p>
   * Method GetFirmwareVersion
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param strVersion Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(34) //= 0x22. The runtime will prefer the VTID if present
  @VTID(41)
  boolean getFirmwareVersion(
    int dwMachineNumber,
    Holder<java.lang.String> strVersion);


  /**
   * <p>
   * Method GetSDKVersion
   * </p>
   * @param strVersion Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(35) //= 0x23. The runtime will prefer the VTID if present
  @VTID(42)
  boolean getSDKVersion(
    Holder<java.lang.String> strVersion);


  /**
   * <p>
   * Method ClearGLog
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(36) //= 0x24. The runtime will prefer the VTID if present
  @VTID(43)
  boolean clearGLog(
    int dwMachineNumber);


  /**
   * <p>
   * Method GetFPTempLength
   * </p>
   * @param dwEnrollData Mandatory Holder<Byte> parameter.
   * @return  Returns a value of type int
   */

  @DISPID(37) //= 0x25. The runtime will prefer the VTID if present
  @VTID(44)
  int getFPTempLength(
    Holder<Byte> dwEnrollData);


  /**
   * <p>
   * Method Connect_Com
   * </p>
   * @param comPort Mandatory int parameter.
   * @param machineNumber Mandatory int parameter.
   * @param baudRate Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(38) //= 0x26. The runtime will prefer the VTID if present
  @VTID(45)
  boolean connect_Com(
    int comPort,
    int machineNumber,
    int baudRate);


  /**
   * <p>
   * Method Connect_NET
   * </p>
   * @param ipAdd Mandatory java.lang.String parameter.
   * @param port Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(39) //= 0x27. The runtime will prefer the VTID if present
  @VTID(46)
  boolean connect_Net(
    java.lang.String ipAdd,
    int port);


  /**
   * <p>
   * Method Disconnect
   * </p>
   */

  @DISPID(40) //= 0x28. The runtime will prefer the VTID if present
  @VTID(47)
  void disconnect();


  /**
   * <p>
   * Method SetUserInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param name Mandatory java.lang.String parameter.
   * @param password Mandatory java.lang.String parameter.
   * @param privilege Mandatory int parameter.
   * @param enabled Mandatory boolean parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(41) //= 0x29. The runtime will prefer the VTID if present
  @VTID(48)
  boolean setUserInfo(
    int dwMachineNumber,
    int dwEnrollNumber,
    java.lang.String name,
    java.lang.String password,
    int privilege,
    boolean enabled);


  /**
   * <p>
   * Method GetUserInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param name Mandatory Holder<java.lang.String> parameter.
   * @param password Mandatory Holder<java.lang.String> parameter.
   * @param privilege Mandatory Holder<Integer> parameter.
   * @param enabled Mandatory Holder<Boolean> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(42) //= 0x2a. The runtime will prefer the VTID if present
  @VTID(49)
  boolean getUserInfo(
    int dwMachineNumber,
    int dwEnrollNumber,
    Holder<java.lang.String> name,
    Holder<java.lang.String> password,
    Holder<Integer> privilege,
    Holder<Boolean> enabled);


  /**
   * <p>
   * Method SetDeviceIP
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param ipAddr Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(43) //= 0x2b. The runtime will prefer the VTID if present
  @VTID(50)
  boolean setDeviceIP(
    int dwMachineNumber,
    java.lang.String ipAddr);


  /**
   * <p>
   * Method SetDeviceIP
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param ipAddr Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(44) //= 0x2c. The runtime will prefer the VTID if present
  @VTID(51)
  boolean getDeviceIP(
    int dwMachineNumber,
    Holder<java.lang.String> ipAddr);


  /**
   * <p>
   * method GetUserTmp
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param tmpData Mandatory Holder<Byte> parameter.
   * @param tmpLength Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(45) //= 0x2d. The runtime will prefer the VTID if present
  @VTID(52)
  boolean getUserTmp(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwFingerIndex,
    Holder<Byte> tmpData,
    Holder<Integer> tmpLength);


  /**
   * <p>
   * method SetUserTmp
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param tmpData Mandatory Holder<Byte> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(46) //= 0x2e. The runtime will prefer the VTID if present
  @VTID(53)
  boolean setUserTmp(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwFingerIndex,
    Holder<Byte> tmpData);


  /**
   * <p>
   * Method GetAllUserInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory Holder<Integer> parameter.
   * @param name Mandatory Holder<java.lang.String> parameter.
   * @param password Mandatory Holder<java.lang.String> parameter.
   * @param privilege Mandatory Holder<Integer> parameter.
   * @param enabled Mandatory Holder<Boolean> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(47) //= 0x2f. The runtime will prefer the VTID if present
  @VTID(54)
  boolean getAllUserInfo(
    int dwMachineNumber,
    Holder<Integer> dwEnrollNumber,
    Holder<java.lang.String> name,
    Holder<java.lang.String> password,
    Holder<Integer> privilege,
    Holder<Boolean> enabled);


  /**
   * <p>
   * method DelUserTmp
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(48) //= 0x30. The runtime will prefer the VTID if present
  @VTID(55)
  boolean delUserTmp(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwFingerIndex);


  /**
   * <p>
   * method RefreshData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(49) //= 0x31. The runtime will prefer the VTID if present
  @VTID(56)
  boolean refreshData(
    int dwMachineNumber);


  /**
   * <p>
   * method FPTempConvert
   * </p>
   * @param tmpData1 Mandatory Holder<Byte> parameter.
   * @param tmpData2 Mandatory Holder<Byte> parameter.
   * @param size Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(50) //= 0x32. The runtime will prefer the VTID if present
  @VTID(57)
  boolean fpTempConvert(
    Holder<Byte> tmpData1,
    Holder<Byte> tmpData2,
    Holder<Integer> size);


  /**
   * <p>
   * method SetCommPassword
   * </p>
   * @param commKey Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(51) //= 0x33. The runtime will prefer the VTID if present
  @VTID(58)
  boolean setCommPassword(
    int commKey);


  /**
   * <p>
   * Method SetUserGroup
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param userGrp Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(52) //= 0x34. The runtime will prefer the VTID if present
  @VTID(59)
  boolean getUserGroup(
    int dwMachineNumber,
    int dwEnrollNumber,
    Holder<Integer> userGrp);


  /**
   * <p>
   * Method SetUserGroup
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param userGrp Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(53) //= 0x35. The runtime will prefer the VTID if present
  @VTID(60)
  boolean setUserGroup(
    int dwMachineNumber,
    int dwEnrollNumber,
    int userGrp);


  /**
   * <p>
   * Method GetTZInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param tzIndex Mandatory int parameter.
   * @param tz Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(54) //= 0x36. The runtime will prefer the VTID if present
  @VTID(61)
  boolean getTZInfo(
    int dwMachineNumber,
    int tzIndex,
    Holder<java.lang.String> tz);


  /**
   * <p>
   * Method SetTZInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param tzIndex Mandatory int parameter.
   * @param tz Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(55) //= 0x37. The runtime will prefer the VTID if present
  @VTID(62)
  boolean setTZInfo(
    int dwMachineNumber,
    int tzIndex,
    java.lang.String tz);


  /**
   * <p>
   * Method GetUnlockGroups
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param grps Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(56) //= 0x38. The runtime will prefer the VTID if present
  @VTID(63)
  boolean getUnlockGroups(
    int dwMachineNumber,
    Holder<java.lang.String> grps);


  /**
   * <p>
   * Method SetUnlockGroups
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param grps Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(57) //= 0x39. The runtime will prefer the VTID if present
  @VTID(64)
  boolean setUnlockGroups(
    int dwMachineNumber,
    java.lang.String grps);


  /**
   * <p>
   * Method GetGroupTZs
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param groupIndex Mandatory int parameter.
   * @param tZs Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(58) //= 0x3a. The runtime will prefer the VTID if present
  @VTID(65)
  boolean getGroupTZs(
    int dwMachineNumber,
    int groupIndex,
    Holder<Integer> tZs);


  /**
   * <p>
   * Method SetGroupTZs
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param groupIndex Mandatory int parameter.
   * @param tZs Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(59) //= 0x3b. The runtime will prefer the VTID if present
  @VTID(66)
  boolean setGroupTZs(
    int dwMachineNumber,
    int groupIndex,
    Holder<Integer> tZs);


  /**
   * <p>
   * Method GetUserTZs
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param tZs Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(60) //= 0x3c. The runtime will prefer the VTID if present
  @VTID(67)
  boolean getUserTZs(
    int dwMachineNumber,
    int dwEnrollNumber,
    Holder<Integer> tZs);


  /**
   * <p>
   * Method SetUserTZs
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param tZs Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(61) //= 0x3d. The runtime will prefer the VTID if present
  @VTID(68)
  boolean setUserTZs(
    int dwMachineNumber,
    int dwEnrollNumber,
    Holder<Integer> tZs);


  /**
   * <p>
   * Method ACUnlock
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param delay Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(62) //= 0x3e. The runtime will prefer the VTID if present
  @VTID(69)
  boolean acUnlock(
    int dwMachineNumber,
    int delay);


  /**
   * <p>
   * Method GetACFun
   * </p>
   * @param acFun Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(63) //= 0x3f. The runtime will prefer the VTID if present
  @VTID(70)
  boolean getACFun(
    Holder<Integer> acFun);


  /**
   * <p>
   * Property ConvertBIG5
   * </p>
   * <p>
   * Getter method for the COM property "ConvertBIG5"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(64) //= 0x40. The runtime will prefer the VTID if present
  @VTID(71)
  int convertBIG5();


  /**
   * <p>
   * Property ConvertBIG5
   * </p>
   * <p>
   * Setter method for the COM property "ConvertBIG5"
   * </p>
   * @param pVal Mandatory int parameter.
   */

  @DISPID(64) //= 0x40. The runtime will prefer the VTID if present
  @VTID(72)
  void convertBIG5(
    int pVal);


  /**
   * <p>
   * Method GetGeneralLogDataStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory Holder<Integer> parameter.
   * @param dwVerifyMode Mandatory Holder<Integer> parameter.
   * @param dwInOutMode Mandatory Holder<Integer> parameter.
   * @param timeStr Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(65) //= 0x41. The runtime will prefer the VTID if present
  @VTID(73)
  boolean getGeneralLogDataStr(
    int dwMachineNumber,
    Holder<Integer> dwEnrollNumber,
    Holder<Integer> dwVerifyMode,
    Holder<Integer> dwInOutMode,
    Holder<java.lang.String> timeStr);


  /**
   * <p>
   * method GetUserTmpStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param tmpData Mandatory Holder<java.lang.String> parameter.
   * @param tmpLength Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(66) //= 0x42. The runtime will prefer the VTID if present
  @VTID(74)
  boolean getUserTmpStr(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwFingerIndex,
    Holder<java.lang.String> tmpData,
    Holder<Integer> tmpLength);


  /**
   * <p>
   * method SetUserTmpStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param tmpData Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(67) //= 0x43. The runtime will prefer the VTID if present
  @VTID(75)
  boolean setUserTmpStr(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwFingerIndex,
    java.lang.String tmpData);


  /**
   * <p>
   * Method GetEnrollDataStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwEMachineNumber Mandatory int parameter.
   * @param dwBackupNumber Mandatory int parameter.
   * @param dwMachinePrivilege Mandatory Holder<Integer> parameter.
   * @param dwEnrollData Mandatory Holder<java.lang.String> parameter.
   * @param dwPassWord Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(68) //= 0x44. The runtime will prefer the VTID if present
  @VTID(76)
  boolean getEnrollDataStr(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwEMachineNumber,
    int dwBackupNumber,
    Holder<Integer> dwMachinePrivilege,
    Holder<java.lang.String> dwEnrollData,
    Holder<Integer> dwPassWord);


  /**
   * <p>
   * Method SetEnrollDataStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwEMachineNumber Mandatory int parameter.
   * @param dwBackupNumber Mandatory int parameter.
   * @param dwMachinePrivilege Mandatory int parameter.
   * @param dwEnrollData Mandatory java.lang.String parameter.
   * @param dwPassWord Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(69) //= 0x45. The runtime will prefer the VTID if present
  @VTID(77)
  boolean setEnrollDataStr(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwEMachineNumber,
    int dwBackupNumber,
    int dwMachinePrivilege,
    java.lang.String dwEnrollData,
    int dwPassWord);


  /**
   * <p>
   * Method GetGroupTZStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param groupIndex Mandatory int parameter.
   * @param tZs Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(70) //= 0x46. The runtime will prefer the VTID if present
  @VTID(78)
  boolean getGroupTZStr(
    int dwMachineNumber,
    int groupIndex,
    Holder<java.lang.String> tZs);


  /**
   * <p>
   * Method SetGroupTZStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param groupIndex Mandatory int parameter.
   * @param tZs Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(71) //= 0x47. The runtime will prefer the VTID if present
  @VTID(79)
  boolean setGroupTZStr(
    int dwMachineNumber,
    int groupIndex,
    java.lang.String tZs);


  /**
   * <p>
   * Method GetUserTZStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param tZs Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(72) //= 0x48. The runtime will prefer the VTID if present
  @VTID(80)
  boolean getUserTZStr(
    int dwMachineNumber,
    int dwEnrollNumber,
    Holder<java.lang.String> tZs);


  /**
   * <p>
   * Method SetUserTZStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param tZs Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(73) //= 0x49. The runtime will prefer the VTID if present
  @VTID(81)
  boolean setUserTZStr(
    int dwMachineNumber,
    int dwEnrollNumber,
    java.lang.String tZs);


  /**
   * <p>
   * method FPTempConvertStr
   * </p>
   * @param tmpData1 Mandatory java.lang.String parameter.
   * @param tmpData2 Mandatory Holder<java.lang.String> parameter.
   * @param size Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(74) //= 0x4a. The runtime will prefer the VTID if present
  @VTID(82)
  boolean fpTempConvertStr(
    java.lang.String tmpData1,
    Holder<java.lang.String> tmpData2,
    Holder<Integer> size);


  /**
   * <p>
   * Method GetFPTempLengthStr
   * </p>
   * @param dwEnrollData Mandatory java.lang.String parameter.
   * @return  Returns a value of type int
   */

  @DISPID(75) //= 0x4b. The runtime will prefer the VTID if present
  @VTID(83)
  int getFPTempLengthStr(
    java.lang.String dwEnrollData);


  /**
   * <p>
   * Property BASE64
   * </p>
   * <p>
   * Getter method for the COM property "BASE64"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(76) //= 0x4c. The runtime will prefer the VTID if present
  @VTID(84)
  int basE64();


  /**
   * <p>
   * Property BASE64
   * </p>
   * <p>
   * Setter method for the COM property "BASE64"
   * </p>
   * @param pVal Mandatory int parameter.
   */

  @DISPID(76) //= 0x4c. The runtime will prefer the VTID if present
  @VTID(85)
  void basE64(
    int pVal);


  /**
   * <p>
   * Property PIN2
   * </p>
   * <p>
   * Getter method for the COM property "PIN2"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(78) //= 0x4e. The runtime will prefer the VTID if present
  @VTID(86)
  int piN2();


  /**
   * <p>
   * Property PIN2
   * </p>
   * <p>
   * Setter method for the COM property "PIN2"
   * </p>
   * @param pVal Mandatory int parameter.
   */

  @DISPID(78) //= 0x4e. The runtime will prefer the VTID if present
  @VTID(87)
  void piN2(
    int pVal);


  /**
   * <p>
   * Property AccGroup
   * </p>
   * <p>
   * Getter method for the COM property "AccGroup"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(79) //= 0x4f. The runtime will prefer the VTID if present
  @VTID(88)
  int accGroup();


  /**
   * <p>
   * Property AccGroup
   * </p>
   * <p>
   * Setter method for the COM property "AccGroup"
   * </p>
   * @param pVal Mandatory int parameter.
   */

  @DISPID(79) //= 0x4f. The runtime will prefer the VTID if present
  @VTID(89)
  void accGroup(
    int pVal);


  /**
   * <p>
   * Property AccTimeZones
   * </p>
   * <p>
   * Getter method for the COM property "AccTimeZones"
   * </p>
   * @param index Mandatory int parameter.
   * @return  Returns a value of type int
   */

  @DISPID(80) //= 0x50. The runtime will prefer the VTID if present
  @VTID(90)
  int accTimeZones(
    int index);


  /**
   * <p>
   * Property AccTimeZones
   * </p>
   * <p>
   * Setter method for the COM property "AccTimeZones"
   * </p>
   * @param index Mandatory int parameter.
   * @param pVal Mandatory int parameter.
   */

  @DISPID(80) //= 0x50. The runtime will prefer the VTID if present
  @VTID(91)
  void accTimeZones(
    int index,
    int pVal);


  /**
   * <p>
   * Method GetUserInfoByPIN2
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param name Mandatory Holder<java.lang.String> parameter.
   * @param password Mandatory Holder<java.lang.String> parameter.
   * @param privilege Mandatory Holder<Integer> parameter.
   * @param enabled Mandatory Holder<Boolean> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(81) //= 0x51. The runtime will prefer the VTID if present
  @VTID(92)
  boolean getUserInfoByPIN2(
    int dwMachineNumber,
    Holder<java.lang.String> name,
    Holder<java.lang.String> password,
    Holder<Integer> privilege,
    Holder<Boolean> enabled);


  /**
   * <p>
   * Method GetUserInfoByCard
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param name Mandatory Holder<java.lang.String> parameter.
   * @param password Mandatory Holder<java.lang.String> parameter.
   * @param privilege Mandatory Holder<Integer> parameter.
   * @param enabled Mandatory Holder<Boolean> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(82) //= 0x52. The runtime will prefer the VTID if present
  @VTID(93)
  boolean getUserInfoByCard(
    int dwMachineNumber,
    Holder<java.lang.String> name,
    Holder<java.lang.String> password,
    Holder<Integer> privilege,
    Holder<Boolean> enabled);


  /**
   * <p>
   * Property CardNumber
   * </p>
   * <p>
   * Getter method for the COM property "CardNumber"
   * </p>
   * @param index Mandatory int parameter.
   * @return  Returns a value of type int
   */

  @DISPID(83) //= 0x53. The runtime will prefer the VTID if present
  @VTID(94)
  int cardNumber(
    int index);


  /**
   * <p>
   * Property CardNumber
   * </p>
   * <p>
   * Setter method for the COM property "CardNumber"
   * </p>
   * @param index Mandatory int parameter.
   * @param pVal Mandatory int parameter.
   */

  @DISPID(83) //= 0x53. The runtime will prefer the VTID if present
  @VTID(95)
  void cardNumber(
    int index,
    int pVal);


  /**
   * <p>
   * Method CaptureImage
   * </p>
   * @param fullImage Mandatory boolean parameter.
   * @param width Mandatory Holder<Integer> parameter.
   * @param height Mandatory Holder<Integer> parameter.
   * @param image Mandatory Holder<Byte> parameter.
   * @param imageFile Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(86) //= 0x56. The runtime will prefer the VTID if present
  @VTID(96)
  boolean captureImage(
    boolean fullImage,
    Holder<Integer> width,
    Holder<Integer> height,
    Holder<Byte> image,
    java.lang.String imageFile);


  /**
   * <p>
   * Method UpdateFirmware
   * </p>
   * @param firmwareFile Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(87) //= 0x57. The runtime will prefer the VTID if present
  @VTID(97)
  boolean updateFirmware(
    java.lang.String firmwareFile);


  /**
   * <p>
   * Method StartEnroll
   * </p>
   * @param userID Mandatory int parameter.
   * @param fingerID Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(88) //= 0x58. The runtime will prefer the VTID if present
  @VTID(98)
  boolean startEnroll(
    int userID,
    int fingerID);


  /**
   * <p>
   * Method StartVerify
   * </p>
   * @param userID Mandatory int parameter.
   * @param fingerID Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(89) //= 0x59. The runtime will prefer the VTID if present
  @VTID(99)
  boolean startVerify(
    int userID,
    int fingerID);


  /**
   * <p>
   * Method StartIdentify
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(90) //= 0x5a. The runtime will prefer the VTID if present
  @VTID(100)
  boolean startIdentify();


  /**
   * <p>
   * Method CancelOperation
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(91) //= 0x5b. The runtime will prefer the VTID if present
  @VTID(101)
  boolean cancelOperation();


  /**
   * <p>
   * Method QueryState
   * </p>
   * @param state Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(92) //= 0x5c. The runtime will prefer the VTID if present
  @VTID(102)
  boolean queryState(
    Holder<Integer> state);


  /**
   * <p>
   * Method BackupData
   * </p>
   * @param dataFile Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(93) //= 0x5d. The runtime will prefer the VTID if present
  @VTID(103)
  boolean backupData(
    java.lang.String dataFile);


  /**
   * <p>
   * Method RestoreData
   * </p>
   * @param dataFile Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(94) //= 0x5e. The runtime will prefer the VTID if present
  @VTID(104)
  boolean restoreData(
    java.lang.String dataFile);


  /**
   * <p>
   * Method WriteLCD
   * </p>
   * @param row Mandatory int parameter.
   * @param col Mandatory int parameter.
   * @param text Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(95) //= 0x5f. The runtime will prefer the VTID if present
  @VTID(105)
  boolean writeLCD(
    int row,
    int col,
    java.lang.String text);


  /**
   * <p>
   * Method ClearLCD
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(96) //= 0x60. The runtime will prefer the VTID if present
  @VTID(106)
  boolean clearLCD();


  /**
   * <p>
   * Method Beep
   * </p>
   * @param delayMS Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(97) //= 0x61. The runtime will prefer the VTID if present
  @VTID(107)
  boolean beep(
    int delayMS);


  /**
   * <p>
   * Method PlayVoice
   * </p>
   * @param position Mandatory int parameter.
   * @param length Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(98) //= 0x62. The runtime will prefer the VTID if present
  @VTID(108)
  boolean playVoice(
    int position,
    int length);


  /**
   * <p>
   * Method PlayVoiceByIndex
   * </p>
   * @param index Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(99) //= 0x63. The runtime will prefer the VTID if present
  @VTID(109)
  boolean playVoiceByIndex(
    int index);


  /**
   * <p>
   * Method EnableClock
   * </p>
   * @param enabled Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(100) //= 0x64. The runtime will prefer the VTID if present
  @VTID(110)
  boolean enableClock(
    int enabled);


  /**
   * <p>
   * Method GetUserIDByPIN2
   * </p>
   * @param piN2 Mandatory int parameter.
   * @param userID Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(101) //= 0x65. The runtime will prefer the VTID if present
  @VTID(111)
  boolean getUserIDByPIN2(
    int piN2,
    Holder<Integer> userID);


  /**
   * <p>
   * Method PIN2Width
   * </p>
   * <p>
   * Getter method for the COM property "PINWidth"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(102) //= 0x66. The runtime will prefer the VTID if present
  @VTID(112)
  int pinWidth();


  /**
   * <p>
   * Method GetPIN2
   * </p>
   * @param userID Mandatory int parameter.
   * @param piN2 Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(103) //= 0x67. The runtime will prefer the VTID if present
  @VTID(113)
  boolean getPIN2(
    int userID,
    Holder<Integer> piN2);


  /**
   * <p>
   * method FPTempConvertNew
   * </p>
   * @param tmpData1 Mandatory Holder<Byte> parameter.
   * @param tmpData2 Mandatory Holder<Byte> parameter.
   * @param size Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(104) //= 0x68. The runtime will prefer the VTID if present
  @VTID(114)
  boolean fpTempConvertNew(
    Holder<Byte> tmpData1,
    Holder<Byte> tmpData2,
    Holder<Integer> size);


  /**
   * <p>
   * method FPTempConvertNewStr
   * </p>
   * @param tmpData1 Mandatory java.lang.String parameter.
   * @param tmpData2 Mandatory Holder<java.lang.String> parameter.
   * @param size Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(105) //= 0x69. The runtime will prefer the VTID if present
  @VTID(115)
  boolean fpTempConvertNewStr(
    java.lang.String tmpData1,
    Holder<java.lang.String> tmpData2,
    Holder<Integer> size);


  /**
   * <p>
   * Method ReadAllTemplate
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(106) //= 0x6a. The runtime will prefer the VTID if present
  @VTID(116)
  boolean readAllTemplate(
    int dwMachineNumber);


  /**
   * <p>
   * Method DisableDeviceWithTimeOut
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param timeOutSec Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(107) //= 0x6b. The runtime will prefer the VTID if present
  @VTID(117)
  boolean disableDeviceWithTimeOut(
    int dwMachineNumber,
    int timeOutSec);


  /**
   * <p>
   * Method SetDeviceTime2
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwYear Mandatory int parameter.
   * @param dwMonth Mandatory int parameter.
   * @param dwDay Mandatory int parameter.
   * @param dwHour Mandatory int parameter.
   * @param dwMinute Mandatory int parameter.
   * @param dwSecond Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(108) //= 0x6c. The runtime will prefer the VTID if present
  @VTID(118)
  boolean setDeviceTime2(
    int dwMachineNumber,
    int dwYear,
    int dwMonth,
    int dwDay,
    int dwHour,
    int dwMinute,
    int dwSecond);


  /**
   * <p>
   * Method ClearSLog
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(109) //= 0x6d. The runtime will prefer the VTID if present
  @VTID(119)
  boolean clearSLog(
    int dwMachineNumber);


  /**
   * <p>
   * Method Restart Device
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(110) //= 0x6e. The runtime will prefer the VTID if present
  @VTID(120)
  boolean restartDevice(
    int dwMachineNumber);


  /**
   * <p>
   * Method GetDeviceMAC
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param sMAC Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(111) //= 0x6f. The runtime will prefer the VTID if present
  @VTID(121)
  boolean getDeviceMAC(
    int dwMachineNumber,
    Holder<java.lang.String> sMAC);


  /**
   * <p>
   * Method SetDeviceMAC
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param sMAC Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(112) //= 0x70. The runtime will prefer the VTID if present
  @VTID(122)
  boolean setDeviceMAC(
    int dwMachineNumber,
    java.lang.String sMAC);


  /**
   * <p>
   * Method GetWiegandFmt
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param sWiegandFmt Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(113) //= 0x71. The runtime will prefer the VTID if present
  @VTID(123)
  boolean getWiegandFmt(
    int dwMachineNumber,
    Holder<java.lang.String> sWiegandFmt);


  /**
   * <p>
   * Method SetWiegandFmt
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param sWiegandFmt Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(114) //= 0x72. The runtime will prefer the VTID if present
  @VTID(124)
  boolean setWiegandFmt(
    int dwMachineNumber,
    java.lang.String sWiegandFmt);


  /**
   * <p>
   * Method ClearSMS
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(115) //= 0x73. The runtime will prefer the VTID if present
  @VTID(125)
  boolean clearSMS(
    int dwMachineNumber);


  /**
   * <p>
   * Method GetSms
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param id Mandatory int parameter.
   * @param tag Mandatory Holder<Integer> parameter.
   * @param validMinutes Mandatory Holder<Integer> parameter.
   * @param startTime Mandatory Holder<java.lang.String> parameter.
   * @param content Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(116) //= 0x74. The runtime will prefer the VTID if present
  @VTID(126)
  boolean getSMS(
    int dwMachineNumber,
    int id,
    Holder<Integer> tag,
    Holder<Integer> validMinutes,
    Holder<java.lang.String> startTime,
    Holder<java.lang.String> content);


  /**
   * <p>
   * Method SetSms
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param id Mandatory int parameter.
   * @param tag Mandatory int parameter.
   * @param validMinutes Mandatory int parameter.
   * @param startTime Mandatory java.lang.String parameter.
   * @param content Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(117) //= 0x75. The runtime will prefer the VTID if present
  @VTID(127)
  boolean setSMS(
    int dwMachineNumber,
    int id,
    int tag,
    int validMinutes,
    java.lang.String startTime,
    java.lang.String content);


  /**
   * <p>
   * Method DeleteSms
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param id Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(118) //= 0x76. The runtime will prefer the VTID if present
  @VTID(128)
  boolean deleteSMS(
    int dwMachineNumber,
    int id);


  /**
   * <p>
   * Method SetUserSMS
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param smsid Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(119) //= 0x77. The runtime will prefer the VTID if present
  @VTID(129)
  boolean setUserSMS(
    int dwMachineNumber,
    int dwEnrollNumber,
    int smsid);


  /**
   * <p>
   * Method DeleteUserSMS
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param smsid Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(120) //= 0x78. The runtime will prefer the VTID if present
  @VTID(130)
  boolean deleteUserSMS(
    int dwMachineNumber,
    int dwEnrollNumber,
    int smsid);


  /**
   * <p>
   * Method GetCardFun
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param cardFun Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(121) //= 0x79. The runtime will prefer the VTID if present
  @VTID(131)
  boolean getCardFun(
    int dwMachineNumber,
    Holder<Integer> cardFun);


  /**
   * <p>
   * Method ClearUserSMS
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(122) //= 0x7a. The runtime will prefer the VTID if present
  @VTID(132)
  boolean clearUserSMS(
    int dwMachineNumber);


  /**
   * <p>
   * Property MachineNumber
   * </p>
   * <p>
   * Getter method for the COM property "MachineNumber"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(123) //= 0x7b. The runtime will prefer the VTID if present
  @VTID(133)
  int machineNumber();


  /**
   * <p>
   * Property MachineNumber
   * </p>
   * <p>
   * Setter method for the COM property "MachineNumber"
   * </p>
   * @param pVal Mandatory int parameter.
   */

  @DISPID(123) //= 0x7b. The runtime will prefer the VTID if present
  @VTID(134)
  void machineNumber(
    int pVal);


  /**
   * <p>
   * Method SetDeviceCommPwd
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param commKey Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(124) //= 0x7c. The runtime will prefer the VTID if present
  @VTID(135)
  boolean setDeviceCommPwd(
    int dwMachineNumber,
    int commKey);


  /**
   * <p>
   * Method GetDoorState
   * </p>
   * @param machineNumber Mandatory int parameter.
   * @param state Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(128) //= 0x80. The runtime will prefer the VTID if present
  @VTID(136)
  boolean getDoorState(
    int machineNumber,
    Holder<Integer> state);


  /**
   * <p>
   * Method GetVendor
   * </p>
   * @param strVendor Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(129) //= 0x81. The runtime will prefer the VTID if present
  @VTID(137)
  boolean getVendor(
    Holder<java.lang.String> strVendor);


  /**
   * <p>
   * Method GetSensorSN
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param sensorSN Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(130) //= 0x82. The runtime will prefer the VTID if present
  @VTID(138)
  boolean getSensorSN(
    int dwMachineNumber,
    Holder<java.lang.String> sensorSN);


  /**
   * <p>
   * Method ReadCustData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param custData Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(131) //= 0x83. The runtime will prefer the VTID if present
  @VTID(139)
  boolean readCustData(
    int dwMachineNumber,
    Holder<java.lang.String> custData);


  /**
   * <p>
   * Method WriteCustData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param custData Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(132) //= 0x84. The runtime will prefer the VTID if present
  @VTID(140)
  boolean writeCustData(
    int dwMachineNumber,
    java.lang.String custData);


  /**
   * <p>
   * Method BeginBatchUpdate
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param updateFlag Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(133) //= 0x85. The runtime will prefer the VTID if present
  @VTID(141)
  boolean beginBatchUpdate(
    int dwMachineNumber,
    int updateFlag);


  /**
   * <p>
   * Method BatchUpdate
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(134) //= 0x86. The runtime will prefer the VTID if present
  @VTID(142)
  boolean batchUpdate(
    int dwMachineNumber);


  /**
   * <p>
   * Method ClearData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dataFlag Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(135) //= 0x87. The runtime will prefer the VTID if present
  @VTID(143)
  boolean clearData(
    int dwMachineNumber,
    int dataFlag);


  /**
   * <p>
   * Method GetDataFile
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dataFlag Mandatory int parameter.
   * @param fileName Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(136) //= 0x88. The runtime will prefer the VTID if present
  @VTID(144)
  boolean getDataFile(
    int dwMachineNumber,
    int dataFlag,
    java.lang.String fileName);


  /**
   * <p>
   * Method WriteCard
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param dwFingerIndex1 Mandatory int parameter.
   * @param tmpData1 Mandatory Holder<Byte> parameter.
   * @param dwFingerIndex2 Mandatory int parameter.
   * @param tmpData2 Mandatory Holder<Byte> parameter.
   * @param dwFingerIndex3 Mandatory int parameter.
   * @param tmpData3 Mandatory Holder<Byte> parameter.
   * @param dwFingerIndex4 Mandatory int parameter.
   * @param tmpData4 Mandatory Holder<Byte> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(137) //= 0x89. The runtime will prefer the VTID if present
  @VTID(145)
  boolean writeCard(
    int dwMachineNumber,
    int dwEnrollNumber,
    int dwFingerIndex1,
    Holder<Byte> tmpData1,
    int dwFingerIndex2,
    Holder<Byte> tmpData2,
    int dwFingerIndex3,
    Holder<Byte> tmpData3,
    int dwFingerIndex4,
    Holder<Byte> tmpData4);


  /**
   * <p>
   * Method GetGeneralExtLogData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory Holder<Integer> parameter.
   * @param dwVerifyMode Mandatory Holder<Integer> parameter.
   * @param dwInOutMode Mandatory Holder<Integer> parameter.
   * @param dwYear Mandatory Holder<Integer> parameter.
   * @param dwMonth Mandatory Holder<Integer> parameter.
   * @param dwDay Mandatory Holder<Integer> parameter.
   * @param dwHour Mandatory Holder<Integer> parameter.
   * @param dwMinute Mandatory Holder<Integer> parameter.
   * @param dwSecond Mandatory Holder<Integer> parameter.
   * @param dwWorkCode Mandatory Holder<Integer> parameter.
   * @param dwReserved Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(138) //= 0x8a. The runtime will prefer the VTID if present
  @VTID(146)
  boolean getGeneralExtLogData(
    int dwMachineNumber,
    Holder<Integer> dwEnrollNumber,
    Holder<Integer> dwVerifyMode,
    Holder<Integer> dwInOutMode,
    Holder<Integer> dwYear,
    Holder<Integer> dwMonth,
    Holder<Integer> dwDay,
    Holder<Integer> dwHour,
    Holder<Integer> dwMinute,
    Holder<Integer> dwSecond,
    Holder<Integer> dwWorkCode,
    Holder<Integer> dwReserved);


  /**
   * <p>
   * Method EmptyCard
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(139) //= 0x8b. The runtime will prefer the VTID if present
  @VTID(147)
  boolean emptyCard(
    int dwMachineNumber);


  /**
   * <p>
   * Method GetDeviceStrInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwInfo Mandatory int parameter.
   * @param value Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(140) //= 0x8c. The runtime will prefer the VTID if present
  @VTID(148)
  boolean getDeviceStrInfo(
    int dwMachineNumber,
    int dwInfo,
    Holder<java.lang.String> value);


  /**
   * <p>
   * Method GetSysOption
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param option Mandatory java.lang.String parameter.
   * @param value Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(141) //= 0x8d. The runtime will prefer the VTID if present
  @VTID(149)
  boolean getSysOption(
    int dwMachineNumber,
    java.lang.String option,
    Holder<java.lang.String> value);


  /**
   * <p>
   * Method SetUserInfoEx
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param verifyStyle Mandatory int parameter.
   * @param reserved Mandatory Holder<Byte> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(142) //= 0x8e. The runtime will prefer the VTID if present
  @VTID(150)
  boolean setUserInfoEx(
    int dwMachineNumber,
    int dwEnrollNumber,
    int verifyStyle,
    Holder<Byte> reserved);


  /**
   * <p>
   * Method GetUserInfoEx
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @param verifyStyle Mandatory Holder<Integer> parameter.
   * @param reserved Mandatory Holder<Byte> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(143) //= 0x8f. The runtime will prefer the VTID if present
  @VTID(151)
  boolean getUserInfoEx(
    int dwMachineNumber,
    int dwEnrollNumber,
    Holder<Integer> verifyStyle,
    Holder<Byte> reserved);


  /**
   * <p>
   * Method DeleteUserInfoEx
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(144) //= 0x90. The runtime will prefer the VTID if present
  @VTID(152)
  boolean deleteUserInfoEx(
    int dwMachineNumber,
    int dwEnrollNumber);


  /**
   * <p>
   * Method SSR_GetGeneralLogData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory Holder<java.lang.String> parameter.
   * @param dwVerifyMode Mandatory Holder<Integer> parameter.
   * @param dwInOutMode Mandatory Holder<Integer> parameter.
   * @param dwYear Mandatory Holder<Integer> parameter.
   * @param dwMonth Mandatory Holder<Integer> parameter.
   * @param dwDay Mandatory Holder<Integer> parameter.
   * @param dwHour Mandatory Holder<Integer> parameter.
   * @param dwMinute Mandatory Holder<Integer> parameter.
   * @param dwSecond Mandatory Holder<Integer> parameter.
   * @param dwWorkCode Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(145) //= 0x91. The runtime will prefer the VTID if present
  @VTID(153)
  boolean ssR_GetGeneralLogData(
    int dwMachineNumber,
    Holder<java.lang.String> dwEnrollNumber,
    Holder<Integer> dwVerifyMode,
    Holder<Integer> dwInOutMode,
    Holder<Integer> dwYear,
    Holder<Integer> dwMonth,
    Holder<Integer> dwDay,
    Holder<Integer> dwHour,
    Holder<Integer> dwMinute,
    Holder<Integer> dwSecond,
    Holder<Integer> dwWorkCode);


  /**
   * <p>
   * Method SSR_GetAllUserInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory Holder<java.lang.String> parameter.
   * @param name Mandatory Holder<java.lang.String> parameter.
   * @param password Mandatory Holder<java.lang.String> parameter.
   * @param privilege Mandatory Holder<Integer> parameter.
   * @param enabled Mandatory Holder<Boolean> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(146) //= 0x92. The runtime will prefer the VTID if present
  @VTID(154)
  boolean ssR_GetAllUserInfo(
    int dwMachineNumber,
    Holder<java.lang.String> dwEnrollNumber,
    Holder<java.lang.String> name,
    Holder<java.lang.String> password,
    Holder<Integer> privilege,
    Holder<Boolean> enabled);


  /**
   * <p>
   * Method SSR_GetUserInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param name Mandatory Holder<java.lang.String> parameter.
   * @param password Mandatory Holder<java.lang.String> parameter.
   * @param privilege Mandatory Holder<Integer> parameter.
   * @param enabled Mandatory Holder<Boolean> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(147) //= 0x93. The runtime will prefer the VTID if present
  @VTID(155)
  boolean ssR_GetUserInfo(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    Holder<java.lang.String> name,
    Holder<java.lang.String> password,
    Holder<Integer> privilege,
    Holder<Boolean> enabled);


  /**
   * <p>
   * Method SSR_GetUserTmp
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param tmpData Mandatory Holder<Byte> parameter.
   * @param tmpLength Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(148) //= 0x94. The runtime will prefer the VTID if present
  @VTID(156)
  boolean ssR_GetUserTmp(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFingerIndex,
    Holder<Byte> tmpData,
    Holder<Integer> tmpLength);


  /**
   * <p>
   * Method SSR_GetUserTmpStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param tmpData Mandatory Holder<java.lang.String> parameter.
   * @param tmpLength Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(149) //= 0x95. The runtime will prefer the VTID if present
  @VTID(157)
  boolean ssR_GetUserTmpStr(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFingerIndex,
    Holder<java.lang.String> tmpData,
    Holder<Integer> tmpLength);


  /**
   * <p>
   * Method SSR_DeleteEnrollData
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwBackupNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(150) //= 0x96. The runtime will prefer the VTID if present
  @VTID(158)
  boolean ssR_DeleteEnrollData(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwBackupNumber);


  /**
   * <p>
   * Method SSR_SetUserInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param name Mandatory java.lang.String parameter.
   * @param password Mandatory java.lang.String parameter.
   * @param privilege Mandatory int parameter.
   * @param enabled Mandatory boolean parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(151) //= 0x97. The runtime will prefer the VTID if present
  @VTID(159)
  boolean ssR_SetUserInfo(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    java.lang.String name,
    java.lang.String password,
    int privilege,
    boolean enabled);


  /**
   * <p>
   * Method SSR_SetUserTmp
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param tmpData Mandatory Holder<Byte> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(152) //= 0x98. The runtime will prefer the VTID if present
  @VTID(160)
  boolean ssR_SetUserTmp(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFingerIndex,
    Holder<Byte> tmpData);


  /**
   * <p>
   * Method SSR_SetUserTmpStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param tmpData Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(153) //= 0x99. The runtime will prefer the VTID if present
  @VTID(161)
  boolean ssR_SetUserTmpStr(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFingerIndex,
    java.lang.String tmpData);


  /**
   * <p>
   * Method SSR_DelUserTmp
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(154) //= 0x9a. The runtime will prefer the VTID if present
  @VTID(162)
  boolean ssR_DelUserTmp(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFingerIndex);


  /**
   * <p>
   * Property STR_CardNumber
   * </p>
   * <p>
   * Getter method for the COM property "STR_CardNumber"
   * </p>
   * @param index Mandatory int parameter.
   * @return  Returns a value of type java.lang.String
   */

  @DISPID(155) //= 0x9b. The runtime will prefer the VTID if present
  @VTID(163)
  java.lang.String stR_CardNumber(
    int index);


  /**
   * <p>
   * Property STR_CardNumber
   * </p>
   * <p>
   * Setter method for the COM property "STR_CardNumber"
   * </p>
   * @param index Mandatory int parameter.
   * @param pVal Mandatory java.lang.String parameter.
   */

  @DISPID(155) //= 0x9b. The runtime will prefer the VTID if present
  @VTID(164)
  void stR_CardNumber(
    int index,
    java.lang.String pVal);


  /**
   * <p>
   * Method SetWorkCode
   * </p>
   * @param workCodeID Mandatory int parameter.
   * @param aWorkCode Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(156) //= 0x9c. The runtime will prefer the VTID if present
  @VTID(165)
  boolean setWorkCode(
    int workCodeID,
    int aWorkCode);


  /**
   * <p>
   * Method GetWorkCode
   * </p>
   * @param workCodeID Mandatory int parameter.
   * @param aWorkCode Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(157) //= 0x9d. The runtime will prefer the VTID if present
  @VTID(166)
  boolean getWorkCode(
    int workCodeID,
    Holder<Integer> aWorkCode);


  /**
   * <p>
   * Method DeleteWorkCode
   * </p>
   * @param workCodeID Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(158) //= 0x9e. The runtime will prefer the VTID if present
  @VTID(167)
  boolean deleteWorkCode(
    int workCodeID);


  /**
   * <p>
   * Method ClearWorkCode
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(159) //= 0x9f. The runtime will prefer the VTID if present
  @VTID(168)
  boolean clearWorkCode();


  /**
   * <p>
   * Method ReadAttRule
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(160) //= 0xa0. The runtime will prefer the VTID if present
  @VTID(169)
  boolean readAttRule(
    int dwMachineNumber);


  /**
   * <p>
   * Method ReadDPTInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(161) //= 0xa1. The runtime will prefer the VTID if present
  @VTID(170)
  boolean readDPTInfo(
    int dwMachineNumber);


  /**
   * <p>
   * Method SaveTheDataToFile
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param theFilePath Mandatory java.lang.String parameter.
   * @param fileFlag Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(162) //= 0xa2. The runtime will prefer the VTID if present
  @VTID(171)
  boolean saveTheDataToFile(
    int dwMachineNumber,
    java.lang.String theFilePath,
    int fileFlag);


  /**
   * <p>
   * Method ReadTurnInfo
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(163) //= 0xa3. The runtime will prefer the VTID if present
  @VTID(172)
  boolean readTurnInfo(
    int dwMachineNumber);


  /**
   * <p>
   * Method SSR_OutPutHTMLRep
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param attFile Mandatory java.lang.String parameter.
   * @param userFile Mandatory java.lang.String parameter.
   * @param deptFile Mandatory java.lang.String parameter.
   * @param timeClassFile Mandatory java.lang.String parameter.
   * @param attruleFile Mandatory java.lang.String parameter.
   * @param bYear Mandatory int parameter.
   * @param bMonth Mandatory int parameter.
   * @param bDay Mandatory int parameter.
   * @param bHour Mandatory int parameter.
   * @param bMinute Mandatory int parameter.
   * @param bSecond Mandatory int parameter.
   * @param eYear Mandatory int parameter.
   * @param eMonth Mandatory int parameter.
   * @param eDay Mandatory int parameter.
   * @param eHour Mandatory int parameter.
   * @param eMinute Mandatory int parameter.
   * @param eSecond Mandatory int parameter.
   * @param tempPath Mandatory java.lang.String parameter.
   * @param outFileName Mandatory java.lang.String parameter.
   * @param htmlFlag Mandatory int parameter.
   * @param resv1 Mandatory int parameter.
   * @param resv2 Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(164) //= 0xa4. The runtime will prefer the VTID if present
  @VTID(173)
  boolean ssR_OutPutHTMLRep(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    java.lang.String attFile,
    java.lang.String userFile,
    java.lang.String deptFile,
    java.lang.String timeClassFile,
    java.lang.String attruleFile,
    int bYear,
    int bMonth,
    int bDay,
    int bHour,
    int bMinute,
    int bSecond,
    int eYear,
    int eMonth,
    int eDay,
    int eHour,
    int eMinute,
    int eSecond,
    java.lang.String tempPath,
    java.lang.String outFileName,
    int htmlFlag,
    int resv1,
    java.lang.String resv2);


  /**
   * <p>
   * Method ReadAOptions
   * </p>
   * @param aOption Mandatory java.lang.String parameter.
   * @param aValue Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(165) //= 0xa5. The runtime will prefer the VTID if present
  @VTID(174)
  boolean readAOptions(
    java.lang.String aOption,
    Holder<java.lang.String> aValue);


  /**
   * <p>
   * Method ReadRTLog
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(166) //= 0xa6. The runtime will prefer the VTID if present
  @VTID(175)
  boolean readRTLog(
    int dwMachineNumber);


  /**
   * <p>
   * Method GetRTLog
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(167) //= 0xa7. The runtime will prefer the VTID if present
  @VTID(176)
  boolean getRTLog(
    int dwMachineNumber);


  /**
   * <p>
   * Method GetHIDEventCardNumAsStr
   * </p>
   * @param strHIDEventCardNum Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(168) //= 0xa8. The runtime will prefer the VTID if present
  @VTID(177)
  boolean getHIDEventCardNumAsStr(
    Holder<java.lang.String> strHIDEventCardNum);


  /**
   * <p>
   * Method GetStrCardNumber
   * </p>
   * @param aCardNumber Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(169) //= 0xa9. The runtime will prefer the VTID if present
  @VTID(178)
  boolean getStrCardNumber(
    Holder<java.lang.String> aCardNumber);


  /**
   * <p>
   * Method SetStrCardNumber
   * </p>
   * @param aCardNumber Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(170) //= 0xaa. The runtime will prefer the VTID if present
  @VTID(179)
  boolean setStrCardNumber(
    java.lang.String aCardNumber);


  /**
   * <p>
   * Method RegEvent
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param eventMask Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(171) //= 0xab. The runtime will prefer the VTID if present
  @VTID(180)
  boolean regEvent(
    int dwMachineNumber,
    int eventMask);


  /**
   * <p>
   * Method CancelBatchUpdate
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(172) //= 0xac. The runtime will prefer the VTID if present
  @VTID(181)
  boolean cancelBatchUpdate(
    int dwMachineNumber);


  /**
   * <p>
   * Method SetSysOption
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param option Mandatory java.lang.String parameter.
   * @param value Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(173) //= 0xad. The runtime will prefer the VTID if present
  @VTID(182)
  boolean setSysOption(
    int dwMachineNumber,
    java.lang.String option,
    java.lang.String value);


  /**
   * <p>
   * Method Connect_Modem
   * </p>
   * @param comPort Mandatory int parameter.
   * @param machineNumber Mandatory int parameter.
   * @param baudRate Mandatory int parameter.
   * @param telephone Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(174) //= 0xae. The runtime will prefer the VTID if present
  @VTID(183)
  boolean connect_Modem(
    int comPort,
    int machineNumber,
    int baudRate,
    java.lang.String telephone);


  /**
   * <p>
   * Method UseGroupTimeZone
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(175) //= 0xaf. The runtime will prefer the VTID if present
  @VTID(184)
  boolean useGroupTimeZone();


  /**
   * <p>
   * Method SetHoliday
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param holiday Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(176) //= 0xb0. The runtime will prefer the VTID if present
  @VTID(185)
  boolean setHoliday(
    int dwMachineNumber,
    java.lang.String holiday);


  /**
   * <p>
   * Method GetHoliday
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param holiday Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(177) //= 0xb1. The runtime will prefer the VTID if present
  @VTID(186)
  boolean getHoliday(
    int dwMachineNumber,
    Holder<java.lang.String> holiday);


  /**
   * <p>
   * Method SetDaylight
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param support Mandatory int parameter.
   * @param beginTime Mandatory java.lang.String parameter.
   * @param endTime Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(178) //= 0xb2. The runtime will prefer the VTID if present
  @VTID(187)
  boolean setDaylight(
    int dwMachineNumber,
    int support,
    java.lang.String beginTime,
    java.lang.String endTime);


  /**
   * <p>
   * Method GetDaylight
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param support Mandatory Holder<Integer> parameter.
   * @param beginTime Mandatory Holder<java.lang.String> parameter.
   * @param endTime Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(179) //= 0xb3. The runtime will prefer the VTID if present
  @VTID(188)
  boolean getDaylight(
    int dwMachineNumber,
    Holder<Integer> support,
    Holder<java.lang.String> beginTime,
    Holder<java.lang.String> endTime);


  /**
   * <p>
   * Method SSR_SetUnLockGroup
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param combNo Mandatory int parameter.
   * @param group1 Mandatory int parameter.
   * @param group2 Mandatory int parameter.
   * @param group3 Mandatory int parameter.
   * @param group4 Mandatory int parameter.
   * @param group5 Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(180) //= 0xb4. The runtime will prefer the VTID if present
  @VTID(189)
  boolean ssR_SetUnLockGroup(
    int dwMachineNumber,
    int combNo,
    int group1,
    int group2,
    int group3,
    int group4,
    int group5);


  /**
   * <p>
   * Method SSR_GetUnLockGroup
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param combNo Mandatory int parameter.
   * @param group1 Mandatory Holder<Integer> parameter.
   * @param group2 Mandatory Holder<Integer> parameter.
   * @param group3 Mandatory Holder<Integer> parameter.
   * @param group4 Mandatory Holder<Integer> parameter.
   * @param group5 Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(181) //= 0xb5. The runtime will prefer the VTID if present
  @VTID(190)
  boolean ssR_GetUnLockGroup(
    int dwMachineNumber,
    int combNo,
    Holder<Integer> group1,
    Holder<Integer> group2,
    Holder<Integer> group3,
    Holder<Integer> group4,
    Holder<Integer> group5);


  /**
   * <p>
   * Method SSR_SetGroupTZ
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param groupNo Mandatory int parameter.
   * @param tz1 Mandatory int parameter.
   * @param tz2 Mandatory int parameter.
   * @param tz3 Mandatory int parameter.
   * @param vaildHoliday Mandatory int parameter.
   * @param verifyStyle Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(182) //= 0xb6. The runtime will prefer the VTID if present
  @VTID(191)
  boolean ssR_SetGroupTZ(
    int dwMachineNumber,
    int groupNo,
    int tz1,
    int tz2,
    int tz3,
    int vaildHoliday,
    int verifyStyle);


  /**
   * <p>
   * Method SSR_GetGroupTZ
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param groupNo Mandatory int parameter.
   * @param tz1 Mandatory Holder<Integer> parameter.
   * @param tz2 Mandatory Holder<Integer> parameter.
   * @param tz3 Mandatory Holder<Integer> parameter.
   * @param vaildHoliday Mandatory Holder<Integer> parameter.
   * @param verifyStyle Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(183) //= 0xb7. The runtime will prefer the VTID if present
  @VTID(192)
  boolean ssR_GetGroupTZ(
    int dwMachineNumber,
    int groupNo,
    Holder<Integer> tz1,
    Holder<Integer> tz2,
    Holder<Integer> tz3,
    Holder<Integer> vaildHoliday,
    Holder<Integer> verifyStyle);


  /**
   * <p>
   * Method SSR_GetHoliday
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param holidayID Mandatory int parameter.
   * @param beginMonth Mandatory Holder<Integer> parameter.
   * @param beginDay Mandatory Holder<Integer> parameter.
   * @param endMonth Mandatory Holder<Integer> parameter.
   * @param endDay Mandatory Holder<Integer> parameter.
   * @param timeZoneID Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(184) //= 0xb8. The runtime will prefer the VTID if present
  @VTID(193)
  boolean ssR_GetHoliday(
    int dwMachineNumber,
    int holidayID,
    Holder<Integer> beginMonth,
    Holder<Integer> beginDay,
    Holder<Integer> endMonth,
    Holder<Integer> endDay,
    Holder<Integer> timeZoneID);


  /**
   * <p>
   * Method SSR_SetHoliday
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param holidayID Mandatory int parameter.
   * @param beginMonth Mandatory int parameter.
   * @param beginDay Mandatory int parameter.
   * @param endMonth Mandatory int parameter.
   * @param endDay Mandatory int parameter.
   * @param timeZoneID Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(185) //= 0xb9. The runtime will prefer the VTID if present
  @VTID(194)
  boolean ssR_SetHoliday(
    int dwMachineNumber,
    int holidayID,
    int beginMonth,
    int beginDay,
    int endMonth,
    int endDay,
    int timeZoneID);


  /**
   * <p>
   * Method GetPlatform
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param platform Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(186) //= 0xba. The runtime will prefer the VTID if present
  @VTID(195)
  boolean getPlatform(
    int dwMachineNumber,
    Holder<java.lang.String> platform);


  /**
   * <p>
   * Method SSR_SetUserSMS
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param smsid Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(187) //= 0xbb. The runtime will prefer the VTID if present
  @VTID(196)
  boolean ssR_SetUserSMS(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int smsid);


  /**
   * <p>
   * Method SSR_DeleteUserSMS
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param smsid Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(188) //= 0xbc. The runtime will prefer the VTID if present
  @VTID(197)
  boolean ssR_DeleteUserSMS(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int smsid);


  /**
   * <p>
   * Method IsTFTMachine
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(189) //= 0xbd. The runtime will prefer the VTID if present
  @VTID(198)
  boolean isTFTMachine(
    int dwMachineNumber);


  /**
   * <p>
   * Method SSR_EnableUser
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param bFlag Mandatory boolean parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(190) //= 0xbe. The runtime will prefer the VTID if present
  @VTID(199)
  boolean ssR_EnableUser(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    boolean bFlag);


  /**
   * <p>
   * Property SSRPin
   * </p>
   * <p>
   * Getter method for the COM property "SSRPin"
   * </p>
   * @return  Returns a value of type int
   */

  @DISPID(191) //= 0xbf. The runtime will prefer the VTID if present
  @VTID(200)
  int ssrPin();


  /**
   * <p>
   * Method SendCMDMsg
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param param1 Mandatory int parameter.
   * @param param2 Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(192) //= 0xc0. The runtime will prefer the VTID if present
  @VTID(201)
  boolean sendCMDMsg(
    int dwMachineNumber,
    int param1,
    int param2);


  /**
   * <p>
   * Method SendFile
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param fileName Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(193) //= 0xc1. The runtime will prefer the VTID if present
  @VTID(202)
  boolean sendFile(
    int dwMachineNumber,
    java.lang.String fileName);


  /**
   * <p>
   * Method SetLanguageByID
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param languageID Mandatory int parameter.
   * @param language Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(194) //= 0xc2. The runtime will prefer the VTID if present
  @VTID(203)
  boolean setLanguageByID(
    int dwMachineNumber,
    int languageID,
    java.lang.String language);


  /**
   * <p>
   * Method ReadFile
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param fileName Mandatory java.lang.String parameter.
   * @param filePath Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(195) //= 0xc3. The runtime will prefer the VTID if present
  @VTID(204)
  boolean readFile(
    int dwMachineNumber,
    java.lang.String fileName,
    java.lang.String filePath);


  /**
   * <p>
   * Method SetLastCount
   * </p>
   * @param count Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(196) //= 0xc4. The runtime will prefer the VTID if present
  @VTID(205)
  boolean setLastCount(
    int count);


  /**
   * <p>
   * Method SetCustomizeAttState
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param stateID Mandatory int parameter.
   * @param newState Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(197) //= 0xc5. The runtime will prefer the VTID if present
  @VTID(206)
  boolean setCustomizeAttState(
    int dwMachineNumber,
    int stateID,
    int newState);


  /**
   * <p>
   * Method DelCustomizeAttState
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param stateID Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(198) //= 0xc6. The runtime will prefer the VTID if present
  @VTID(207)
  boolean delCustomizeAttState(
    int dwMachineNumber,
    int stateID);


  /**
   * <p>
   * Method EnableCustomizeAttState
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param stateID Mandatory int parameter.
   * @param enable Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(199) //= 0xc7. The runtime will prefer the VTID if present
  @VTID(208)
  boolean enableCustomizeAttState(
    int dwMachineNumber,
    int stateID,
    int enable);


  /**
   * <p>
   * Method SetCustomizeVoice
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param voiceID Mandatory int parameter.
   * @param fileName Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(200) //= 0xc8. The runtime will prefer the VTID if present
  @VTID(209)
  boolean setCustomizeVoice(
    int dwMachineNumber,
    int voiceID,
    java.lang.String fileName);


  /**
   * <p>
   * Method DelCustomizeVoice
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param voiceID Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(201) //= 0xc9. The runtime will prefer the VTID if present
  @VTID(210)
  boolean delCustomizeVoice(
    int dwMachineNumber,
    int voiceID);


  /**
   * <p>
   * Method EnableCustomizeVoice
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param voiceID Mandatory int parameter.
   * @param enable Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(202) //= 0xca. The runtime will prefer the VTID if present
  @VTID(211)
  boolean enableCustomizeVoice(
    int dwMachineNumber,
    int voiceID,
    int enable);


  /**
   * <p>
   * method StartEnrollEx
   * </p>
   * @param userID Mandatory java.lang.String parameter.
   * @param fingerID Mandatory int parameter.
   * @param flag Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(203) //= 0xcb. The runtime will prefer the VTID if present
  @VTID(212)
  boolean startEnrollEx(
    java.lang.String userID,
    int fingerID,
    int flag);


  /**
   * <p>
   * method SSR_SetUserTmpExt
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param isDeleted Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param tmpData Mandatory Holder<Byte> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(215) //= 0xd7. The runtime will prefer the VTID if present
  @VTID(213)
  boolean ssR_SetUserTmpExt(
    int dwMachineNumber,
    int isDeleted,
    java.lang.String dwEnrollNumber,
    int dwFingerIndex,
    Holder<Byte> tmpData);


  /**
   * <p>
   * method SSR_DelUserTmpExt
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(216) //= 0xd8. The runtime will prefer the VTID if present
  @VTID(214)
  boolean ssR_DelUserTmpExt(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFingerIndex);


  /**
   * <p>
   * method SSR_DeleteEnrollDataExt
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwBackupNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(217) //= 0xd9. The runtime will prefer the VTID if present
  @VTID(215)
  boolean ssR_DeleteEnrollDataExt(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwBackupNumber);


  /**
   * <p>
   * Method SSR_GetWorkCode
   * </p>
   * @param aWorkCode Mandatory int parameter.
   * @param name Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(218) //= 0xda. The runtime will prefer the VTID if present
  @VTID(216)
  boolean ssR_GetWorkCode(
    int aWorkCode,
    Holder<java.lang.String> name);


  /**
   * <p>
   * Method SSR_SetWorkCode
   * </p>
   * @param aWorkCode Mandatory int parameter.
   * @param name Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(219) //= 0xdb. The runtime will prefer the VTID if present
  @VTID(217)
  boolean ssR_SetWorkCode(
    int aWorkCode,
    java.lang.String name);


  /**
   * <p>
   * Method SSR_DeleteWorkCode
   * </p>
   * @param pin Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(220) //= 0xdc. The runtime will prefer the VTID if present
  @VTID(218)
  boolean ssR_DeleteWorkCode(
    int pin);


  /**
   * <p>
   * Method SSR_ClearWorkCode
   * </p>
   * @return  Returns a value of type boolean
   */

  @DISPID(221) //= 0xdd. The runtime will prefer the VTID if present
  @VTID(219)
  boolean ssR_ClearWorkCode();


  /**
   * <p>
   * Method SSR_GetSuperLogData
   * </p>
   * @param machineNumber Mandatory int parameter.
   * @param number Mandatory Holder<Integer> parameter.
   * @param admin Mandatory Holder<java.lang.String> parameter.
   * @param user Mandatory Holder<java.lang.String> parameter.
   * @param manipulation Mandatory Holder<Integer> parameter.
   * @param time Mandatory Holder<java.lang.String> parameter.
   * @param params1 Mandatory Holder<Integer> parameter.
   * @param params2 Mandatory Holder<Integer> parameter.
   * @param params3 Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(222) //= 0xde. The runtime will prefer the VTID if present
  @VTID(220)
  boolean ssR_GetSuperLogData(
    int machineNumber,
    Holder<Integer> number,
    Holder<java.lang.String> admin,
    Holder<java.lang.String> user,
    Holder<Integer> manipulation,
    Holder<java.lang.String> time,
    Holder<Integer> params1,
    Holder<Integer> params2,
    Holder<Integer> params3);


  /**
   * <p>
   * method SSR_SetShortkey
   * </p>
   * @param shortKeyID Mandatory int parameter.
   * @param shortKeyFun Mandatory int parameter.
   * @param stateCode Mandatory int parameter.
   * @param stateName Mandatory java.lang.String parameter.
   * @param stateAutoChange Mandatory int parameter.
   * @param stateAutoChangeTime Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(223) //= 0xdf. The runtime will prefer the VTID if present
  @VTID(221)
  boolean ssR_SetShortkey(
    int shortKeyID,
    int shortKeyFun,
    int stateCode,
    java.lang.String stateName,
    int stateAutoChange,
    java.lang.String stateAutoChangeTime);


  /**
   * <p>
   * method SSR_GetShortkey
   * </p>
   * @param shortKeyID Mandatory int parameter.
   * @param shortKeyFun Mandatory Holder<Integer> parameter.
   * @param stateCode Mandatory Holder<Integer> parameter.
   * @param stateName Mandatory Holder<java.lang.String> parameter.
   * @param autoChange Mandatory Holder<Integer> parameter.
   * @param autoChangeTime Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(224) //= 0xe0. The runtime will prefer the VTID if present
  @VTID(222)
  boolean ssR_GetShortkey(
    int shortKeyID,
    Holder<Integer> shortKeyFun,
    Holder<Integer> stateCode,
    Holder<java.lang.String> stateName,
    Holder<Integer> autoChange,
    Holder<java.lang.String> autoChangeTime);


  /**
   * <p>
   * Method Connect_USB
   * </p>
   * @param machineNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(225) //= 0xe1. The runtime will prefer the VTID if present
  @VTID(223)
  boolean connect_USB(
    int machineNumber);


  /**
   * <p>
   * method GetSuperLogData2
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwTMachineNumber Mandatory Holder<Integer> parameter.
   * @param dwSEnrollNumber Mandatory Holder<Integer> parameter.
   * @param params4 Mandatory Holder<Integer> parameter.
   * @param params1 Mandatory Holder<Integer> parameter.
   * @param params2 Mandatory Holder<Integer> parameter.
   * @param dwManipulation Mandatory Holder<Integer> parameter.
   * @param params3 Mandatory Holder<Integer> parameter.
   * @param dwYear Mandatory Holder<Integer> parameter.
   * @param dwMonth Mandatory Holder<Integer> parameter.
   * @param dwDay Mandatory Holder<Integer> parameter.
   * @param dwHour Mandatory Holder<Integer> parameter.
   * @param dwMinute Mandatory Holder<Integer> parameter.
   * @param dwSecs Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(226) //= 0xe2. The runtime will prefer the VTID if present
  @VTID(224)
  boolean getSuperLogData2(
    int dwMachineNumber,
    Holder<Integer> dwTMachineNumber,
    Holder<Integer> dwSEnrollNumber,
    Holder<Integer> params4,
    Holder<Integer> params1,
    Holder<Integer> params2,
    Holder<Integer> dwManipulation,
    Holder<Integer> params3,
    Holder<Integer> dwYear,
    Holder<Integer> dwMonth,
    Holder<Integer> dwDay,
    Holder<Integer> dwHour,
    Holder<Integer> dwMinute,
    Holder<Integer> dwSecs);


  /**
   * <p>
   * method GetUserFace
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFaceIndex Mandatory int parameter.
   * @param tmpData Mandatory Holder<Byte> parameter.
   * @param tmpLength Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(230) //= 0xe6. The runtime will prefer the VTID if present
  @VTID(225)
  boolean getUserFace(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFaceIndex,
    Holder<Byte> tmpData,
    Holder<Integer> tmpLength);


  /**
   * <p>
   * method SetUserFace
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFaceIndex Mandatory int parameter.
   * @param tmpData Mandatory Holder<Byte> parameter.
   * @param tmpLength Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(231) //= 0xe7. The runtime will prefer the VTID if present
  @VTID(226)
  boolean setUserFace(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFaceIndex,
    Holder<Byte> tmpData,
    int tmpLength);


  /**
   * <p>
   * method DelUserFace
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFaceIndex Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(232) //= 0xe8. The runtime will prefer the VTID if present
  @VTID(227)
  boolean delUserFace(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFaceIndex);


  /**
   * <p>
   * method GetUserFaceStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFaceIndex Mandatory int parameter.
   * @param tmpData Mandatory Holder<java.lang.String> parameter.
   * @param tmpLength Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(233) //= 0xe9. The runtime will prefer the VTID if present
  @VTID(228)
  boolean getUserFaceStr(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFaceIndex,
    Holder<java.lang.String> tmpData,
    Holder<Integer> tmpLength);


  /**
   * <p>
   * method SetUserFaceStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFaceIndex Mandatory int parameter.
   * @param tmpData Mandatory java.lang.String parameter.
   * @param tmpLength Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(234) //= 0xea. The runtime will prefer the VTID if present
  @VTID(229)
  boolean setUserFaceStr(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFaceIndex,
    java.lang.String tmpData,
    int tmpLength);


  /**
   * <p>
   * Method GetUserTmpEx
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param flag Mandatory Holder<Integer> parameter.
   * @param tmpData Mandatory Holder<Byte> parameter.
   * @param tmpLength Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(235) //= 0xeb. The runtime will prefer the VTID if present
  @VTID(230)
  boolean getUserTmpEx(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFingerIndex,
    Holder<Integer> flag,
    Holder<Byte> tmpData,
    Holder<Integer> tmpLength);


  /**
   * <p>
   * Method GetUserTmpExStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param flag Mandatory Holder<Integer> parameter.
   * @param tmpData Mandatory Holder<java.lang.String> parameter.
   * @param tmpLength Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(236) //= 0xec. The runtime will prefer the VTID if present
  @VTID(231)
  boolean getUserTmpExStr(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFingerIndex,
    Holder<Integer> flag,
    Holder<java.lang.String> tmpData,
    Holder<Integer> tmpLength);


  /**
   * <p>
   * Method SetUserTmpEx
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param flag Mandatory int parameter.
   * @param tmpData Mandatory Holder<Byte> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(237) //= 0xed. The runtime will prefer the VTID if present
  @VTID(232)
  boolean setUserTmpEx(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFingerIndex,
    int flag,
    Holder<Byte> tmpData);


  /**
   * <p>
   * Method SetUserTmpExStr
   * </p>
   * @param dwMachineNumber Mandatory int parameter.
   * @param dwEnrollNumber Mandatory java.lang.String parameter.
   * @param dwFingerIndex Mandatory int parameter.
   * @param flag Mandatory int parameter.
   * @param tmpData Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(238) //= 0xee. The runtime will prefer the VTID if present
  @VTID(233)
  boolean setUserTmpExStr(
    int dwMachineNumber,
    java.lang.String dwEnrollNumber,
    int dwFingerIndex,
    int flag,
    java.lang.String tmpData);


      /**
       * <p>
       * property PullMode
       * </p>
       * <p>
       * Getter method for the COM property "PullMode"
       * </p>
       * @return  Returns a value of type int
       */

      @DISPID(241) //= 0xf1. The runtime will prefer the VTID if present
      @VTID(236)
      int pullMode();


      /**
       * <p>
       * property PullMode
       * </p>
       * <p>
       * Setter method for the COM property "PullMode"
       * </p>
       * @param pVal Mandatory int parameter.
       */

      @DISPID(241) //= 0xf1. The runtime will prefer the VTID if present
      @VTID(237)
      void pullMode(
        int pVal);


      /**
       * <p>
       * method ReadUserAllTemplate
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param dwEnrollNumber Mandatory java.lang.String parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(242) //= 0xf2. The runtime will prefer the VTID if present
      @VTID(238)
      boolean readUserAllTemplate(
        int dwMachineNumber,
        java.lang.String dwEnrollNumber);


      /**
       * <p>
       * method UpdateFile
       * </p>
       * @param fileName Mandatory java.lang.String parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(243) //= 0xf3. The runtime will prefer the VTID if present
      @VTID(239)
      boolean updateFile(
        java.lang.String fileName);


      /**
       * <p>
       * method ReadLastestLogData
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param newLog Mandatory int parameter.
       * @param dwYear Mandatory int parameter.
       * @param dwMonth Mandatory int parameter.
       * @param dwDay Mandatory int parameter.
       * @param dwHour Mandatory int parameter.
       * @param dwMinute Mandatory int parameter.
       * @param dwSecond Mandatory int parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(244) //= 0xf4. The runtime will prefer the VTID if present
      @VTID(240)
      boolean readLastestLogData(
        int dwMachineNumber,
        int newLog,
        int dwYear,
        int dwMonth,
        int dwDay,
        int dwHour,
        int dwMinute,
        int dwSecond);


      /**
       * <p>
       * method SetOptionCommPwd
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param commKey Mandatory java.lang.String parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(245) //= 0xf5. The runtime will prefer the VTID if present
      @VTID(241)
      boolean setOptionCommPwd(
        int dwMachineNumber,
        java.lang.String commKey);


      /**
       * <p>
       * Method ReadSuperLogDataEx
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param dwYear_S Mandatory int parameter.
       * @param dwMonth_S Mandatory int parameter.
       * @param dwDay_S Mandatory int parameter.
       * @param dwHour_S Mandatory int parameter.
       * @param dwMinute_S Mandatory int parameter.
       * @param dwSecond_S Mandatory int parameter.
       * @param dwYear_E Mandatory int parameter.
       * @param dwMonth_E Mandatory int parameter.
       * @param dwDay_E Mandatory int parameter.
       * @param dwHour_E Mandatory int parameter.
       * @param dwMinute_E Mandatory int parameter.
       * @param dwSecond_E Mandatory int parameter.
       * @param dwLogIndex Mandatory int parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(246) //= 0xf6. The runtime will prefer the VTID if present
      @VTID(242)
      boolean readSuperLogDataEx(
        int dwMachineNumber,
        int dwYear_S,
        int dwMonth_S,
        int dwDay_S,
        int dwHour_S,
        int dwMinute_S,
        int dwSecond_S,
        int dwYear_E,
        int dwMonth_E,
        int dwDay_E,
        int dwHour_E,
        int dwMinute_E,
        int dwSecond_E,
        int dwLogIndex);


      /**
       * <p>
       * method GetSuperLogDataEx
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param enrollNumber Mandatory Holder<java.lang.String> parameter.
       * @param params4 Mandatory Holder<Integer> parameter.
       * @param params1 Mandatory Holder<Integer> parameter.
       * @param params2 Mandatory Holder<Integer> parameter.
       * @param dwManipulation Mandatory Holder<Integer> parameter.
       * @param params3 Mandatory Holder<Integer> parameter.
       * @param dwYear Mandatory Holder<Integer> parameter.
       * @param dwMonth Mandatory Holder<Integer> parameter.
       * @param dwDay Mandatory Holder<Integer> parameter.
       * @param dwHour Mandatory Holder<Integer> parameter.
       * @param dwMinute Mandatory Holder<Integer> parameter.
       * @param dwSecond Mandatory Holder<Integer> parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(247) //= 0xf7. The runtime will prefer the VTID if present
      @VTID(243)
      boolean getSuperLogDataEx(
        int dwMachineNumber,
        Holder<java.lang.String> enrollNumber,
        Holder<Integer> params4,
        Holder<Integer> params1,
        Holder<Integer> params2,
        Holder<Integer> dwManipulation,
        Holder<Integer> params3,
        Holder<Integer> dwYear,
        Holder<Integer> dwMonth,
        Holder<Integer> dwDay,
        Holder<Integer> dwHour,
        Holder<Integer> dwMinute,
        Holder<Integer> dwSecond);


      /**
       * <p>
       * Method GetPhotoByName
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param photoName Mandatory java.lang.String parameter.
       * @param photoData Mandatory Holder<Byte> parameter.
       * @param photoLength Mandatory Holder<Integer> parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(248) //= 0xf8. The runtime will prefer the VTID if present
      @VTID(244)
      boolean getPhotoByName(
        int dwMachineNumber,
        java.lang.String photoName,
        Holder<Byte> photoData,
        Holder<Integer> photoLength);


      /**
       * <p>
       * Method GetPhotoNamesByTime
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param iFlag Mandatory int parameter.
       * @param sTime Mandatory java.lang.String parameter.
       * @param eTime Mandatory java.lang.String parameter.
       * @param allPhotoName Mandatory Holder<java.lang.String> parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(249) //= 0xf9. The runtime will prefer the VTID if present
      @VTID(245)
      boolean getPhotoNamesByTime(
        int dwMachineNumber,
        int iFlag,
        java.lang.String sTime,
        java.lang.String eTime,
        Holder<java.lang.String> allPhotoName);


      /**
       * <p>
       * Method ClearPhotoByTime
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param iFlag Mandatory int parameter.
       * @param sTime Mandatory java.lang.String parameter.
       * @param eTime Mandatory java.lang.String parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(250) //= 0xfa. The runtime will prefer the VTID if present
      @VTID(246)
      boolean clearPhotoByTime(
        int dwMachineNumber,
        int iFlag,
        java.lang.String sTime,
        java.lang.String eTime);


      /**
       * <p>
       * Method GetPhotoCount
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param count Mandatory Holder<Integer> parameter.
       * @param iFlag Mandatory int parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(251) //= 0xfb. The runtime will prefer the VTID if present
      @VTID(247)
      boolean getPhotoCount(
        int dwMachineNumber,
        Holder<Integer> count,
        int iFlag);


      /**
       * <p>
       * Method ClearDataEx
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param tableName Mandatory java.lang.String parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(252) //= 0xfc. The runtime will prefer the VTID if present
      @VTID(248)
      boolean clearDataEx(
        int dwMachineNumber,
        java.lang.String tableName);


      /**
       * <p>
       * Method GetDataFileEx
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param sourceFileName Mandatory java.lang.String parameter.
       * @param destFileName Mandatory java.lang.String parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(253) //= 0xfd. The runtime will prefer the VTID if present
      @VTID(249)
      boolean getDataFileEx(
        int dwMachineNumber,
        java.lang.String sourceFileName,
        java.lang.String destFileName);


      /**
       * <p>
       * Method SSR_SetDeviceData
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param tableName Mandatory java.lang.String parameter.
       * @param datas Mandatory java.lang.String parameter.
       * @param options Mandatory java.lang.String parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(254) //= 0xfe. The runtime will prefer the VTID if present
      @VTID(250)
      boolean ssR_SetDeviceData(
        int dwMachineNumber,
        java.lang.String tableName,
        java.lang.String datas,
        java.lang.String options);


      /**
       * <p>
       * Method SSR_GetDeviceData
       * </p>
       * @param dwMachineNumber Mandatory int parameter.
       * @param buffer Mandatory Holder<java.lang.String> parameter.
       * @param bufferSize Mandatory int parameter.
       * @param tableName Mandatory java.lang.String parameter.
       * @param filedNames Mandatory java.lang.String parameter.
       * @param filter Mandatory java.lang.String parameter.
       * @param options Mandatory java.lang.String parameter.
       * @return  Returns a value of type boolean
       */

      @DISPID(255) //= 0xff. The runtime will prefer the VTID if present
      @VTID(251)
      boolean ssR_GetDeviceData(
        int dwMachineNumber,
        Holder<java.lang.String> buffer,
        int bufferSize,
        java.lang.String tableName,
        java.lang.String filedNames,
        java.lang.String filter,
        java.lang.String options);


      // Properties:
    }
