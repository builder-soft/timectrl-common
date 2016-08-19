package cl.buildersoft.timectrl.api.com4j  ;

import com4j.*;

/**
 * IZKEM Event Interface
 */
@IID("{00020400-0000-0000-C000-000000000046}")
public interface _IZKEMEvents extends Com4jObject {
  // Methods:
  /**
   * <p>
   * Event OnAttTransaction
   * </p>
   * @param enrollNumber Mandatory int parameter.
   * @param isInValid Mandatory int parameter.
   * @param attState Mandatory int parameter.
   * @param verifyMethod Mandatory int parameter.
   * @param year Mandatory int parameter.
   * @param month Mandatory int parameter.
   * @param day Mandatory int parameter.
   * @param hour Mandatory int parameter.
   * @param minute Mandatory int parameter.
   * @param second Mandatory int parameter.
   */

  @DISPID(1)
  void onAttTransaction(
    int enrollNumber,
    int isInValid,
    int attState,
    int verifyMethod,
    int year,
    int month,
    int day,
    int hour,
    int minute,
    int second);


  /**
   * <p>
   * Event OnKeyPress
   * </p>
   * @param key Mandatory int parameter.
   */

  @DISPID(2)
  void onKeyPress(
    int key);


  /**
   * <p>
   * Event OnEnrollFinger
   * </p>
   * @param enrollNumber Mandatory int parameter.
   * @param fingerIndex Mandatory int parameter.
   * @param actionResult Mandatory int parameter.
   * @param templateLength Mandatory int parameter.
   */

  @DISPID(3)
  void onEnrollFinger(
    int enrollNumber,
    int fingerIndex,
    int actionResult,
    int templateLength);


  /**
   * <p>
   * Event OnNewUser
   * </p>
   * @param enrollNumber Mandatory int parameter.
   */

  @DISPID(4)
  void onNewUser(
    int enrollNumber);


  /**
   * <p>
   * Event OnEMData
   * </p>
   * @param dataType Mandatory int parameter.
   * @param dataLen Mandatory int parameter.
   * @param dataBuffer Mandatory Holder<Byte> parameter.
   */

  @DISPID(5)
  void onEMData(
    int dataType,
    int dataLen,
    Holder<Byte> dataBuffer);


  /**
   * <p>
   * Event OnConnected
   * </p>
   */

  @DISPID(6)
  void onConnected();


  /**
   * <p>
   * Event OnDisConnected
   * </p>
   */

  @DISPID(7)
  void onDisConnected();


  /**
   * <p>
   * Event OnFinger
   * </p>
   */

  @DISPID(8)
  void onFinger();


  /**
   * <p>
   * Event OnVerify
   * </p>
   * @param userID Mandatory int parameter.
   */

  @DISPID(9)
  void onVerify(
    int userID);


  /**
   * <p>
   * Event OnFingerFeature
   * </p>
   * @param score Mandatory int parameter.
   */

  @DISPID(10)
  void onFingerFeature(
    int score);


  /**
   * <p>
   * Event OnHIDNum
   * </p>
   * @param cardNumber Mandatory int parameter.
   */

  @DISPID(11)
  void onHIDNum(
    int cardNumber);


  /**
   * <p>
   * Event OnDoor
   * </p>
   * @param eventType Mandatory int parameter.
   */

  @DISPID(12)
  void onDoor(
    int eventType);


  /**
   * <p>
   * Event OnAlarm
   * </p>
   * @param alarmType Mandatory int parameter.
   * @param enrollNumber Mandatory int parameter.
   * @param verified Mandatory int parameter.
   */

  @DISPID(13)
  void onAlarm(
    int alarmType,
    int enrollNumber,
    int verified);


  /**
   * <p>
   * Event OnWriteCard
   * </p>
   * @param enrollNumber Mandatory int parameter.
   * @param actionResult Mandatory int parameter.
   * @param length Mandatory int parameter.
   */

  @DISPID(14)
  void onWriteCard(
    int enrollNumber,
    int actionResult,
    int length);


  /**
   * <p>
   * Event OnEmptyCard
   * </p>
   * @param actionResult Mandatory int parameter.
   */

  @DISPID(15)
  void onEmptyCard(
    int actionResult);


  /**
   * <p>
   * Event OnDeleteTemplate
   * </p>
   * @param enrollNumber Mandatory int parameter.
   * @param fingerIndex Mandatory int parameter.
   */

  @DISPID(16)
  void onDeleteTemplate(
    int enrollNumber,
    int fingerIndex);


  /**
   * <p>
   * Event OnAttTransactionEx
   * </p>
   * @param enrollNumber Mandatory java.lang.String parameter.
   * @param isInValid Mandatory int parameter.
   * @param attState Mandatory int parameter.
   * @param verifyMethod Mandatory int parameter.
   * @param year Mandatory int parameter.
   * @param month Mandatory int parameter.
   * @param day Mandatory int parameter.
   * @param hour Mandatory int parameter.
   * @param minute Mandatory int parameter.
   * @param second Mandatory int parameter.
   * @param workCode Mandatory int parameter.
   */

  @DISPID(17)
  void onAttTransactionEx(
    java.lang.String enrollNumber,
    int isInValid,
    int attState,
    int verifyMethod,
    int year,
    int month,
    int day,
    int hour,
    int minute,
    int second,
    int workCode);


  /**
   * <p>
   * method OnEnrollFingerEx
   * </p>
   * @param enrollNumber Mandatory java.lang.String parameter.
   * @param fingerIndex Mandatory int parameter.
   * @param actionResult Mandatory int parameter.
   * @param templateLength Mandatory int parameter.
   */

  @DISPID(18)
  void onEnrollFingerEx(
    java.lang.String enrollNumber,
    int fingerIndex,
    int actionResult,
    int templateLength);


  // Properties:
}
