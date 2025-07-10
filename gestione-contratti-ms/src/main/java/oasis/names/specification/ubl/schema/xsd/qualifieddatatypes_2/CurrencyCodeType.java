//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.ubl.schema.xsd.qualifieddatatypes_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import oasis.names.specification.bdndr.schema.xsd.unqualifieddatatypes_1.CodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DocumentCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentAlternativeCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PricingCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequestedInvoiceCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SourceCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TargetCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValueCurrencyCodeType;


/**
 * &lt;p&gt;Classe Java per CurrencyCodeType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="CurrencyCodeType"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;restriction base="&amp;lt;urn:oasis:names:specification:bdndr:schema:xsd:UnqualifiedDataTypes-1&amp;gt;CodeType"&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyCodeType")
@XmlSeeAlso({
    oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CurrencyCodeType.class,
    DocumentCurrencyCodeType.class,
    PaymentAlternativeCurrencyCodeType.class,
    PaymentCurrencyCodeType.class,
    PricingCurrencyCodeType.class,
    RequestedInvoiceCurrencyCodeType.class,
    SourceCurrencyCodeType.class,
    TargetCurrencyCodeType.class,
    TaxCurrencyCodeType.class,
    ValueCurrencyCodeType.class
})
public class CurrencyCodeType
    extends CodeType
{


}
