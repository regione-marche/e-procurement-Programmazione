/**
 * DescrizioneRisultatoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.appaltiecontratti.monitoraggiocontratti.contratti.entity;

public class DescrizioneRisultatoType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DescrizioneRisultatoType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "OK";
    public static final java.lang.String _value2 = "CIG inesistente o non valido";
    public static final java.lang.String _value3 = "Matrice inesistente o non valida";
    public static final java.lang.String _value4 = "Login non effettuato";
    public static final java.lang.String _value5 = "Errore di validazione";
    public static final java.lang.String _value6 = "Validatore non trovato";
    public static final java.lang.String _value7 = "Errore generico";
    public static final java.lang.String _value8 = "Nome tipologica non valido";
    public static final java.lang.String _value9 = "Codice tipologica non valido";
    public static final DescrizioneRisultatoType value1 = new DescrizioneRisultatoType(_value1);
    public static final DescrizioneRisultatoType value2 = new DescrizioneRisultatoType(_value2);
    public static final DescrizioneRisultatoType value3 = new DescrizioneRisultatoType(_value3);
    public static final DescrizioneRisultatoType value4 = new DescrizioneRisultatoType(_value4);
    public static final DescrizioneRisultatoType value5 = new DescrizioneRisultatoType(_value5);
    public static final DescrizioneRisultatoType value6 = new DescrizioneRisultatoType(_value6);
    public static final DescrizioneRisultatoType value7 = new DescrizioneRisultatoType(_value7);
    public static final DescrizioneRisultatoType value8 = new DescrizioneRisultatoType(_value8);
    public static final DescrizioneRisultatoType value9 = new DescrizioneRisultatoType(_value9);
    public java.lang.String getValue() { return _value_;}
    public static DescrizioneRisultatoType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DescrizioneRisultatoType enumeration = (DescrizioneRisultatoType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DescrizioneRisultatoType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
  

}
