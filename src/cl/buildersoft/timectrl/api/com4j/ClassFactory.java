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
    return COM4J.createInstance( cl.buildersoft.timectrl.api.com4j._zkemProxy.class, "{AE1A5F86-CF36-4CB5-B20A-DA312428F0A6}" );
  }

  public static cl.buildersoft.timectrl.api.com4j._ZKProxy2 createZKProxy2() {
    return COM4J.createInstance( cl.buildersoft.timectrl.api.com4j._ZKProxy2.class, "{CD8F0F8C-B43C-415D-B280-E43B08BC274D}" );
  }
}
