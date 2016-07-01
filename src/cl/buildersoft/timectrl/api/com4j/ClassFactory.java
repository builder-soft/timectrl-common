package cl.buildersoft.timectrl.api.com4j  ;

import com4j.*;

/**
 * Defines methods to create COM objects
 */
public abstract class ClassFactory {
  private ClassFactory() {} // instanciation is not allowed


  /**
   * ZKEM Class
   */
  public static cl.buildersoft.timectrl.api.com4j.IZKEM createCZKEM() {
    return COM4J.createInstance( cl.buildersoft.timectrl.api.com4j.IZKEM.class, "{00853A19-BD51-419B-9269-2DABE57EB61F}" );
  }

  public static cl.buildersoft.timectrl.api.com4j._zkemProxy createzkemProxy() {
    return COM4J.createInstance( cl.buildersoft.timectrl.api.com4j._zkemProxy.class, "{171E36DE-C971-4450-BCBE-6185F12736AF}" );
  }

  public static cl.buildersoft.timectrl.api.com4j._ZKProxy2 createZKProxy2() {
    return COM4J.createInstance( cl.buildersoft.timectrl.api.com4j._ZKProxy2.class, "{9BA5352E-284E-4C8C-B316-7EDBBA641940}" );
  }
}
