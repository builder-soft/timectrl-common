package cl.buildersoft.timectrl.api.com4j  ;

import com4j.*;

@IID("{3C58C63D-649F-42D8-9C6F-50B78715E5B8}")
public interface _ZKProxy2 extends Com4jObject {
  // Methods:
  /**
   * <p>
   * Getter method for the COM property "CZKEM"
   * </p>
   * @return  Returns a value of type cl.buildersoft.timectrl.api.com4j.IZKEM
   */

  @DISPID(1073938432) //= 0x40030000. The runtime will prefer the VTID if present
  @VTID(7)
  cl.buildersoft.timectrl.api.com4j.IZKEM czkem();


  /**
   * <p>
   * Setter method for the COM property "CZKEM"
   * </p>
   * @param czkem Mandatory cl.buildersoft.timectrl.api.com4j.IZKEM parameter.
   */

  @DISPID(1073938432) //= 0x40030000. The runtime will prefer the VTID if present
  @VTID(9)
  void czkem(
    cl.buildersoft.timectrl.api.com4j.IZKEM czkem);


  /**
   * @param ip Mandatory java.lang.String parameter.
   * @param port Mandatory short parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809346) //= 0x60030002. The runtime will prefer the VTID if present
  @VTID(10)
  boolean connect_Net(
    java.lang.String ip,
    short port);


  /**
   */

  @DISPID(1610809347) //= 0x60030003. The runtime will prefer the VTID if present
  @VTID(11)
  void disconnect();


  /**
   * @param machine Mandatory int parameter.
   * @param enable Mandatory boolean parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809348) //= 0x60030004. The runtime will prefer the VTID if present
  @VTID(12)
  boolean enableDevice(
    int machine,
    boolean enable);


  /**
   * @param machine Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809349) //= 0x60030005. The runtime will prefer the VTID if present
  @VTID(13)
  boolean readGeneralLogData(
    int machine);


  /**
   * @param machine Mandatory int parameter.
   * @param enrollNumber Mandatory Holder<java.lang.String> parameter.
   * @param verifyMode Mandatory Holder<Integer> parameter.
   * @param inOutMode Mandatory Holder<Integer> parameter.
   * @param year Mandatory Holder<Integer> parameter.
   * @param month Mandatory Holder<Integer> parameter.
   * @param day Mandatory Holder<Integer> parameter.
   * @param hour Mandatory Holder<Integer> parameter.
   * @param minute Mandatory Holder<Integer> parameter.
   * @param second Mandatory Holder<Integer> parameter.
   * @param workCode Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809350) //= 0x60030006. The runtime will prefer the VTID if present
  @VTID(14)
  boolean ssR_GetGeneralLogData(
    int machine,
    Holder<java.lang.String> enrollNumber,
    Holder<Integer> verifyMode,
    Holder<Integer> inOutMode,
    Holder<Integer> year,
    Holder<Integer> month,
    Holder<Integer> day,
    Holder<Integer> hour,
    Holder<Integer> minute,
    Holder<Integer> second,
    Holder<Integer> workCode);


  /**
   * @param errorCode Mandatory Holder<Integer> parameter.
   */

  @DISPID(1610809351) //= 0x60030007. The runtime will prefer the VTID if present
  @VTID(15)
  void getLastError(
    Holder<Integer> errorCode);


  /**
   * @param machine Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809352) //= 0x60030008. The runtime will prefer the VTID if present
  @VTID(16)
  boolean readAllUserID(
    int machine);


  /**
   * @param machineNumber Mandatory int parameter.
   * @param enrollNumber Mandatory Holder<java.lang.String> parameter.
   * @param name Mandatory Holder<java.lang.String> parameter.
   * @param password Mandatory Holder<java.lang.String> parameter.
   * @param privilege Mandatory Holder<Integer> parameter.
   * @param enabled Mandatory Holder<Boolean> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809353) //= 0x60030009. The runtime will prefer the VTID if present
  @VTID(17)
  boolean ssR_GetUserInfo(
    int machineNumber,
    Holder<java.lang.String> enrollNumber,
    Holder<java.lang.String> name,
    Holder<java.lang.String> password,
    Holder<Integer> privilege,
    Holder<Boolean> enabled);


  /**
   * @param machineNumber Mandatory int parameter.
   * @param enrollNumber Mandatory Holder<java.lang.String> parameter.
   * @param name Mandatory Holder<java.lang.String> parameter.
   * @param password Mandatory Holder<java.lang.String> parameter.
   * @param privilege Mandatory Holder<Integer> parameter.
   * @param enabled Mandatory Holder<Boolean> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809354) //= 0x6003000a. The runtime will prefer the VTID if present
  @VTID(18)
  boolean ssR_GetAllUserInfo(
    int machineNumber,
    Holder<java.lang.String> enrollNumber,
    Holder<java.lang.String> name,
    Holder<java.lang.String> password,
    Holder<Integer> privilege,
    Holder<Boolean> enabled);


  /**
   * @param machine Mandatory int parameter.
   * @param enrollNumber Mandatory java.lang.String parameter.
   * @param fingerIndex Mandatory int parameter.
   * @param flag Mandatory Holder<Integer> parameter.
   * @param tmpData Mandatory Holder<java.lang.String> parameter.
   * @param tmpLength Mandatory Holder<Integer> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809355) //= 0x6003000b. The runtime will prefer the VTID if present
  @VTID(19)
  boolean getUserTmpExStr(
    int machine,
    java.lang.String enrollNumber,
    int fingerIndex,
    Holder<Integer> flag,
    Holder<java.lang.String> tmpData,
    Holder<Integer> tmpLength);


  /**
   * @param machine Mandatory int parameter.
   * @param updateFlag Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809356) //= 0x6003000c. The runtime will prefer the VTID if present
  @VTID(20)
  boolean beginBatchUpdate(
    int machine,
    int updateFlag);


  /**
   * @param machine Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809357) //= 0x6003000d. The runtime will prefer the VTID if present
  @VTID(21)
  boolean batchUpdate(
    int machine);


  /**
   * @param machine Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809358) //= 0x6003000e. The runtime will prefer the VTID if present
  @VTID(22)
  boolean refreshData(
    int machine);


  /**
   * @param machine Mandatory int parameter.
   * @param enrollNumber Mandatory java.lang.String parameter.
   * @param name Mandatory java.lang.String parameter.
   * @param password Mandatory java.lang.String parameter.
   * @param privilege Mandatory int parameter.
   * @param enabled Mandatory boolean parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809359) //= 0x6003000f. The runtime will prefer the VTID if present
  @VTID(23)
  boolean ssR_SetUserInfo(
    int machine,
    java.lang.String enrollNumber,
    java.lang.String name,
    java.lang.String password,
    int privilege,
    boolean enabled);


  /**
   * @param machine Mandatory int parameter.
   * @param enrollNumber Mandatory java.lang.String parameter.
   * @param fingerIndex Mandatory int parameter.
   * @param flag Mandatory int parameter.
   * @param tmpData Mandatory java.lang.String parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809360) //= 0x60030010. The runtime will prefer the VTID if present
  @VTID(24)
  boolean setUserTmpExStr(
    int machine,
    java.lang.String enrollNumber,
    int fingerIndex,
    int flag,
    java.lang.String tmpData);


  /**
   * @param machine Mandatory int parameter.
   * @param enrollNumber Mandatory java.lang.String parameter.
   * @param backupNumber Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809361) //= 0x60030011. The runtime will prefer the VTID if present
  @VTID(25)
  boolean ssR_DeleteEnrollData(
    int machine,
    java.lang.String enrollNumber,
    int backupNumber);


  /**
   * @param machine Mandatory int parameter.
   * @param serialNumber Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809362) //= 0x60030012. The runtime will prefer the VTID if present
  @VTID(26)
  boolean getSerialNumber(
    int machine,
    Holder<java.lang.String> serialNumber);


  /**
   * @param machine Mandatory int parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809363) //= 0x60030013. The runtime will prefer the VTID if present
  @VTID(27)
  boolean clearGLog(
    int machine);


  /**
   * @param aCardNumber Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809364) //= 0x60030014. The runtime will prefer the VTID if present
  @VTID(28)
  boolean getStrCardNumber(
    Holder<java.lang.String> aCardNumber);


  /**
   * @param aCardNumber Mandatory Holder<java.lang.String> parameter.
   * @return  Returns a value of type boolean
   */

  @DISPID(1610809365) //= 0x60030015. The runtime will prefer the VTID if present
  @VTID(29)
  boolean setStrCardNumber(
    Holder<java.lang.String> aCardNumber);


  // Properties:
}
