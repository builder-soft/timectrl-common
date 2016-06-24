package cl.buildersoft.timectrl.api.com4j.events;

import com4j.*;

@IID("{E663379C-7183-4E72-B648-00D00174B89B}")
public abstract class __ZKProxy2 {
  // Methods:
  /**
   */

  @DISPID(1)
  public void onConnected() {
        throw new UnsupportedOperationException();
  }


  /**
   * @param enrollNumber Mandatory int parameter.
   * @param fingerIndex Mandatory int parameter.
   */

  @DISPID(2)
  public void onDeleteTemplate(
    int enrollNumber,
    int fingerIndex) {
        throw new UnsupportedOperationException();
  }


  /**
   */

  @DISPID(3)
  public void onDisConnected() {
        throw new UnsupportedOperationException();
  }


  /**
   * @param eventType Mandatory int parameter.
   */

  @DISPID(4)
  public void onDoor(
    int eventType) {
        throw new UnsupportedOperationException();
  }


  /**
   * @param actionResult Mandatory int parameter.
   */

  @DISPID(5)
  public void onEmptyCard(
    int actionResult) {
        throw new UnsupportedOperationException();
  }


  /**
   * @param enrollNumber Mandatory int parameter.
   * @param fingerIndex Mandatory int parameter.
   * @param actionResult Mandatory int parameter.
   * @param templateLength Mandatory int parameter.
   */

  @DISPID(6)
  public void onEnrollFinger(
    int enrollNumber,
    int fingerIndex,
    int actionResult,
    int templateLength) {
        throw new UnsupportedOperationException();
  }


  /**
   * @param enrollNumber Mandatory java.lang.String parameter.
   * @param fingerIndex Mandatory int parameter.
   * @param actionResult Mandatory int parameter.
   * @param templateLength Mandatory int parameter.
   */

  @DISPID(7)
  public void onEnrollFingerEx(
    java.lang.String enrollNumber,
    int fingerIndex,
    int actionResult,
    int templateLength) {
        throw new UnsupportedOperationException();
  }


  /**
   */

  @DISPID(8)
  public void onFinger() {
        throw new UnsupportedOperationException();
  }


  /**
   * @param score Mandatory int parameter.
   */

  @DISPID(9)
  public void onFingerFeature(
    int score) {
        throw new UnsupportedOperationException();
  }


  /**
   * @param cardNumber Mandatory int parameter.
   */

  @DISPID(10)
  public void onHIDNum(
    int cardNumber) {
        throw new UnsupportedOperationException();
  }


  /**
   * @param key Mandatory int parameter.
   */

  @DISPID(11)
  public void onKeyPress(
    int key) {
        throw new UnsupportedOperationException();
  }


  /**
   * @param enrollNumber Mandatory int parameter.
   */

  @DISPID(12)
  public void onNewUser(
    int enrollNumber) {
        throw new UnsupportedOperationException();
  }


  /**
   * @param userID Mandatory int parameter.
   */

  @DISPID(13)
  public void onVerify(
    int userID) {
        throw new UnsupportedOperationException();
  }


  /**
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

  @DISPID(15)
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

  @DISPID(16)
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


  // Properties:
}
