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
    return COM4J.createInstance( cl.buildersoft.timectrl.api.com4j._zkemProxy.class, "{C55FC34F-F94A-4862-A65F-E667977E3889}" );
  }

  public static cl.buildersoft.timectrl.api.com4j._ZKProxy2 createZKProxy2() {
    return COM4J.createInstance( cl.buildersoft.timectrl.api.com4j._ZKProxy2.class, "{DCD4A899-4ACF-4754-B610-E3A89F18ADE8}" );
  }
}
