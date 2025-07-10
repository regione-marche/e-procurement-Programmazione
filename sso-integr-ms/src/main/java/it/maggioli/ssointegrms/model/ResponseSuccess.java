package it.maggioli.ssointegrms.model;

import java.util.Date;

/**
 * Classe wrapper che rappresenta una richiesta di success
 * 
 * @author Cristiano Perin
 *
 */
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

	public String get_idp_entity_id_() {
		return _idp_entity_id_;
	}

	public void set_idp_entity_id_(String _idp_entity_id_) {
		this._idp_entity_id_ = _idp_entity_id_;
	}

	public String get_purpose_() {
		return _purpose_;
	}

	public void set_purpose_(String _purpose_) {
		this._purpose_ = _purpose_;
	}

	public String get_request_id_() {
		return _request_id_;
	}

	public void set_request_id_(String _request_id_) {
		this._request_id_ = _request_id_;
	}

	public String get_spid_level_() {
		return _spid_level_;
	}

	public void set_spid_level_(String _spid_level_) {
		this._spid_level_ = _spid_level_;
	}

	public String get_type_() {
		return _type_;
	}

	public void set_type_(String _type_) {
		this._type_ = _type_;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFiscalNumber() {
		return fiscalNumber;
	}

	public void setFiscalNumber(String fiscalNumber) {
		this.fiscalNumber = fiscalNumber;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getSpidCode() {
		return spidCode;
	}

	public void setSpidCode(String spidCode) {
		this.spidCode = spidCode;
	}

	@Override
	public String toString() {
		return "ResponseSuccess [_idp_entity_id_=" + _idp_entity_id_ + ", _purpose_=" + _purpose_ + ", _request_id_="
				+ _request_id_ + ", _spid_level_=" + _spid_level_ + ", _type_=" + _type_ + ", dateOfBirth="
				+ dateOfBirth + ", email=" + email + ", familyName=" + familyName + ", fiscalNumber=" + fiscalNumber
				+ ", mobilePhone=" + mobilePhone + ", name=" + name + ", placeOfBirth=" + placeOfBirth + ", spidCode="
				+ spidCode + ", get_uuid_()=" + get_uuid_() + ", get_ts_()=" + get_ts_() + "]";
	}

}
