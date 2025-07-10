package it.maggioli.ssointegrms.model;

/**
 * @author Cristiano Perin
 */
public abstract class BaseResponse {

	private String _uuid_;
	private Long _ts_;

	public String get_uuid_() {
		return _uuid_;
	}

	public void set_uuid_(String _uuid_) {
		this._uuid_ = _uuid_;
	}

	public Long get_ts_() {
		return _ts_;
	}

	public void set_ts_(Long _ts_) {
		this._ts_ = _ts_;
	}

	@Override
	public String toString() {
		return "BaseResponse [_uuid_=" + _uuid_ + ", _ts_=" + _ts_ + "]";
	}

}
