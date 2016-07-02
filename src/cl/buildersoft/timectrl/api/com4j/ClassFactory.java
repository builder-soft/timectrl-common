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
    return COM4J.createInstance( cl.buildersoft.timectrl.api.com4j._zkemProxy.class, "{A61034F1-0193-4EE5-AE3E-4C5A4362125D}" );
  }

  public static cl.buildersoft.timectrl.api.com4j._ZKProxy2 createZKProxy2() {
    return COM4J.createInstance( cl.buildersoft.timectrl.api.com4j._ZKProxy2.class, "{FAD3DAEF-3F8C-4EA2-B940-D632A641F674}" );
  }
}
