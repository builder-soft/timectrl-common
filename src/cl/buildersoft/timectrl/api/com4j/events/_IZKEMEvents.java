package cl.buildersoft.timectrl.api.com4j.events;

import com4j.*;

/**
 * IZKEM Event Interface
 */
@IID("{CF83B580-5D32-4C65-B44E-BEDC750CDFA8}")
public abstract class _IZKEMEvents {
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
  public void onAttTransaction(
    int enrollNumber,
    int isInValid,
    int attState,
    int verifyMethod,
    int year,
    int month,
    int day,
    int hour,
    int minute,
    int second) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnKeyPress
   * </p>
   * @param key Mandatory int parameter.
   */

  @DISPID(2)
  public void onKeyPress(
    int key) {
        throw new UnsupportedOperationException();
  }


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
  public void onEnrollFinger(
    int enrollNumber,
    int fingerIndex,
    int actionResult,
    int templateLength) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnNewUser
   * </p>
   * @param enrollNumber Mandatory int parameter.
   */

  @DISPID(4)
  public void onNewUser(
    int enrollNumber) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnEMData
   * </p>
   * @param dataType Mandatory int parameter.
   * @param dataLen Mandatory int parameter.
   * @param dataBuffer Mandatory Holder<Byte> parameter.
   */

  @DISPID(5)
  public void onEMData(
    int dataType,
    int dataLen,
    Holder<Byte> dataBuffer) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnConnected
   * </p>
   */

  @DISPID(6)
  public void onConnected() {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnDisConnected
   * </p>
   */

  @DISPID(7)
  public void onDisConnected() {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnFinger
   * </p>
   */

  @DISPID(8)
  public void onFinger() {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnVerify
   * </p>
   * @param userID Mandatory int parameter.
   */

  @DISPID(9)
  public void onVerify(
    int userID) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnFingerFeature
   * </p>
   * @param score Mandatory int parameter.
   */

  @DISPID(10)
  public void onFingerFeature(
    int score) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnHIDNum
   * </p>
   * @param cardNumber Mandatory int parameter.
   */

  @DISPID(11)
  public void onHIDNum(
    int cardNumber) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnDoor
   * </p>
   * @param eventType Mandatory int parameter.
   */

  @DISPID(12)
  public void onDoor(
    int eventType) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnAlarm
   * </p>
   * @param alarmType Mandatory int parameter.
   * @param enrollNumber Mandatory int parameter.
   * @param verified Mandatory int parameter.
   */

  @DISPID(13)
  public void onAlarm(
    int alarmType,
    int enrollNumber,
    int verified) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnWriteCard
   * </p>
   * @param enrollNumber Mandatory int parameter.
   * @param actionResult Mandatory int parameter.
   * @param length Mandatory int parameter.
   */

  @DISPID(14)
  public void onWriteCard(
    int enrollNumber,
    int actionResult,
    int length) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnEmptyCard
   * </p>
   * @param actionResult Mandatory int parameter.
   */

  @DISPID(15)
  public void onEmptyCard(
    int actionResult) {
        throw new UnsupportedOperationException();
  }


  /**
   * <p>
   * Event OnDeleteTemplate
   * </p>
   * @param enrollNumber Mandatory int parameter.
   * @param fingerIndex Mandatory int parameter.
   */

  @DISPID(16)
  public void onDeleteTemplate(
    int enrollNumber,
    int fingerIndex) {
        throw new UnsupportedOperationException();
  }


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
  public void onAttTransactionEx(
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
    int workCode) {
        throw new UnsupportedOperationException();
  }


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
  public void onEnrollFingerEx(
    java.lang.String enrollNumber,
    int fingerIndex,
    int actionResult,
    int templateLength) {
        throw new UnsupportedOperationException();
  }


  // Properties:
}
