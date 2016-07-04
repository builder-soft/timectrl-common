package cl.buildersoft.timectrl.api.com4j  ;

import com4j.*;

@IID("{00020400-0000-0000-C000-000000000046}")
public interface __ZKProxy2 extends Com4jObject {
  // Methods:
  /**
   */

  @DISPID(2)
  void onConnected();


  /**
   * @param enrollNumber Mandatory int parameter.
   * @param fingerIndex Mandatory int parameter.
   */

  @DISPID(3)
  void onDeleteTemplate(
    int enrollNumber,
    int fingerIndex);


  /**
   */

  @DISPID(4)
  void onDisConnected();


  /**
   * @param eventType Mandatory int parameter.
   */

  @DISPID(5)
  void onDoor(
    int eventType);


  /**
   * @param actionResult Mandatory int parameter.
   */

  @DISPID(6)
  void onEmptyCard(
    int actionResult);


  /**
   * @param enrollNumber Mandatory int parameter.
   * @param fingerIndex Mandatory int parameter.
   * @param actionResult Mandatory int parameter.
   * @param templateLength Mandatory int parameter.
   */

  @DISPID(7)
  void onEnrollFinger(
    int enrollNumber,
    int fingerIndex,
    int actionResult,
    int templateLength);


  /**
   * @param enrollNumber Mandatory java.lang.String parameter.
   * @param fingerIndex Mandatory int parameter.
   * @param actionResult Mandatory int parameter.
   * @param templateLength Mandatory int parameter.
   */

  @DISPID(8)
  void onEnrollFingerEx(
    java.lang.String enrollNumber,
    int fingerIndex,
    int actionResult,
    int templateLength);


  /**
   */

  @DISPID(9)
  void onFinger();


  /**
   * @param score Mandatory int parameter.
   */

  @DISPID(10)
  void onFingerFeature(
    int score);


  /**
   * @param cardNumber Mandatory int parameter.
   */

  @DISPID(11)
  void onHIDNum(
    int cardNumber);


  /**
   * @param key Mandatory int parameter.
   */

  @DISPID(12)
  void onKeyPress(
    int key);


  /**
   * @param enrollNumber Mandatory int parameter.
   */

  @DISPID(13)
  void onNewUser(
    int enrollNumber);


  /**
   * @param userID Mandatory int parameter.
   */

  @DISPID(14)
  void onVerify(
    int userID);


  /**
   * @param enrollNumber Mandatory int parameter.
   * @param actionResult Mandatory int parameter.
   * @param length Mandatory int parameter.
   */

  @DISPID(15)
  void onWriteCard(
    int enrollNumber,
    int actionResult,
    int length);


  /**
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

  @DISPID(16)
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
   * @param alarmType Mandatory int parameter.
   * @param enrollNumber Mandatory int parameter.
   * @param verified Mandatory int parameter.
   */

  @DISPID(1)
  void onAlarm(
    int alarmType,
    int enrollNumber,
    int verified);


  // Properties:
}
