package it.appaltiecontratti.gestionereportsms.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ResponseSuccess extends BaseResponse {

    private String _idp_entity_id_;
    private String _purpose_;
    private String _request_id_;
    private String _spid_level_;
    private String _type_;
    private Date dateOfBirth;
    private String email;
    private String familyName;
    private String fiscalNumber;
    private String mobilePhone;
    private String name;
    private String placeOfBirth;
    private String spidCode;
}
