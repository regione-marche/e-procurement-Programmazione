//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.bdndr.schema.xsd.unqualifieddatatypes_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ParameterNumericType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.StatisticsNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BudgetYearNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CalculationSequenceNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpectedValueNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FrozenPeriodDaysNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LineCountNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LineNumberNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumCopiesNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumLotsAwardedNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumLotsSubmittedNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumNumberNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumOriginalsNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumPaymentInstructionsNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MaximumValueNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumNumberNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MinimumValueNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MultiplierFactorNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OrderIntervalDaysNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OrderQuantityIncrementNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PackSizeNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReminderSequenceNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResidentOccupantsNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SequenceNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WeightNumericType;


/**
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;BDNDRUDT0000014&amp;lt;/ccts:UniqueID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:CategoryCode xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;UDT&amp;lt;/ccts:CategoryCode&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:DictionaryEntryName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Numeric. Type&amp;lt;/ccts:DictionaryEntryName&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:VersionID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;1.0&amp;lt;/ccts:VersionID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:Definition xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Numeric information that is assigned or is determined by calculation, counting, or sequencing. It does not require a unit of quantity or unit of measure.&amp;lt;/ccts:Definition&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:RepresentationTermName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Numeric&amp;lt;/ccts:RepresentationTermName&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:PrimitiveType xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;string&amp;lt;/ccts:PrimitiveType&amp;gt;
 * &lt;/pre&gt;
 * 
 *       
 * 
 * &lt;p&gt;Classe Java per NumericType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="NumericType"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;extension base="&amp;lt;urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2&amp;gt;NumericType"&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumericType")
@XmlSeeAlso({
    BudgetYearNumericType.class,
    CalculationSequenceNumericType.class,
    ExpectedValueNumericType.class,
    FrozenPeriodDaysNumericType.class,
    LineCountNumericType.class,
    LineNumberNumericType.class,
    MaximumCopiesNumericType.class,
    MaximumLotsAwardedNumericType.class,
    MaximumLotsSubmittedNumericType.class,
    MaximumNumberNumericType.class,
    MaximumOriginalsNumericType.class,
    MaximumPaymentInstructionsNumericType.class,
    MaximumValueNumericType.class,
    MinimumNumberNumericType.class,
    MinimumValueNumericType.class,
    MultiplierFactorNumericType.class,
    OrderIntervalDaysNumericType.class,
    OrderQuantityIncrementNumericType.class,
    PackSizeNumericType.class,
    ReminderSequenceNumericType.class,
    ResidentOccupantsNumericType.class,
    ResponseNumericType.class,
    SequenceNumericType.class,
    WeightNumericType.class,
    ParameterNumericType.class,
    StatisticsNumericType.class
})
public class NumericType
    extends un.unece.uncefact.data.specification.corecomponenttypeschemamodule._2.NumericType
{


}
