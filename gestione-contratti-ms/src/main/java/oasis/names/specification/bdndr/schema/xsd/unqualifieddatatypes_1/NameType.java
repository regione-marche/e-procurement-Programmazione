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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AdditionalStreetNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AgencyNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AliasNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BirthplaceNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BlockNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BrandNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BuildingNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CategoryNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CityNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CitySubdivisionNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ContractNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EconomicOperatorGroupNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FamilyNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FileNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.FirstNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.HolderNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MeterNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MiddleNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ModelNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OtherNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RegistrationNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RetailEventNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RoamingPartnerNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ServiceNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StreetNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TechnicalNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.VesselNameType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionAgencyNameType;
import un.unece.uncefact.data.specification.corecomponenttypeschemamodule._2.TextType;


/**
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;BDNDRUDT0000020&amp;lt;/ccts:UniqueID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:CategoryCode xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;UDT&amp;lt;/ccts:CategoryCode&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:DictionaryEntryName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Name. Type&amp;lt;/ccts:DictionaryEntryName&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:VersionID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;1.0&amp;lt;/ccts:VersionID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:Definition xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;A character string that constitutes the distinctive designation of a person, place, thing or concept.&amp;lt;/ccts:Definition&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:RepresentationTermName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Name&amp;lt;/ccts:RepresentationTermName&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:PrimitiveType xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;string&amp;lt;/ccts:PrimitiveType&amp;gt;
 * &lt;/pre&gt;
 * 
 *       
 * 
 * &lt;p&gt;Classe Java per NameType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="NameType"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;extension base="&amp;lt;urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2&amp;gt;TextType"&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameType")
@XmlSeeAlso({
    AdditionalStreetNameType.class,
    AgencyNameType.class,
    AliasNameType.class,
    BirthplaceNameType.class,
    BlockNameType.class,
    BrandNameType.class,
    BuildingNameType.class,
    CategoryNameType.class,
    CityNameType.class,
    CitySubdivisionNameType.class,
    ContractNameType.class,
    EconomicOperatorGroupNameType.class,
    FamilyNameType.class,
    FileNameType.class,
    FirstNameType.class,
    HolderNameType.class,
    MeterNameType.class,
    MiddleNameType.class,
    ModelNameType.class,
    oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType.class,
    OtherNameType.class,
    RegistrationNameType.class,
    RetailEventNameType.class,
    RoamingPartnerNameType.class,
    ServiceNameType.class,
    StreetNameType.class,
    TechnicalNameType.class,
    VesselNameType.class,
    ExtensionAgencyNameType.class
})
public class NameType
    extends TextType
{


}
