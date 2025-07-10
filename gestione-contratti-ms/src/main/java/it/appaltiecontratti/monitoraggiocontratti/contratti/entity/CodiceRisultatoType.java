/**
 * CodiceRisultatoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

public class CodiceRisultatoType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CodiceRisultatoType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _COD_001 = "COD_001";
    public static final java.lang.String _COD_002 = "COD_002";
    public static final java.lang.String _COD_003 = "COD_003";
    public static final java.lang.String _COD_004 = "COD_004";
    public static final java.lang.String _COD_005 = "COD_005";
    public static final java.lang.String _COD_006 = "COD_006";
    public static final java.lang.String _COD_007 = "COD_007";
    public static final java.lang.String _COD_008 = "COD_008";
    public static final java.lang.String _COD_009 = "COD_009";
    public static final CodiceRisultatoType COD_001 = new CodiceRisultatoType(_COD_001);
    public static final CodiceRisultatoType COD_002 = new CodiceRisultatoType(_COD_002);
    public static final CodiceRisultatoType COD_003 = new CodiceRisultatoType(_COD_003);
    public static final CodiceRisultatoType COD_004 = new CodiceRisultatoType(_COD_004);
    public static final CodiceRisultatoType COD_005 = new CodiceRisultatoType(_COD_005);
    public static final CodiceRisultatoType COD_006 = new CodiceRisultatoType(_COD_006);
    public static final CodiceRisultatoType COD_007 = new CodiceRisultatoType(_COD_007);
    public static final CodiceRisultatoType COD_008 = new CodiceRisultatoType(_COD_008);
    public static final CodiceRisultatoType COD_009 = new CodiceRisultatoType(_COD_009);
    public java.lang.String getValue() { return _value_;}
    public static CodiceRisultatoType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CodiceRisultatoType enumeration = (CodiceRisultatoType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CodiceRisultatoType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}


}
