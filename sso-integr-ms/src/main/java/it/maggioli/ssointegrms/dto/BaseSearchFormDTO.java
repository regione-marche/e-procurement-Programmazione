package it.maggioli.ssointegrms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class BaseSearchFormDTO implements Serializable {

	private static final long serialVersionUID = 4446180648273001113L;
	@NotNull
	protected Integer skip;
	@NotNull
	protected Integer take;
	@NotNull
	protected String sort;
	@NotNull
	protected String sortDirection;

	public Integer getSkip() {
		return skip;
	}

	public void setSkip(Integer skip) {
		this.skip = skip;
	}

	public Integer getTake() {
		return take;
	}

	public void setTake(Integer take) {
		this.take = take;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	@Override
	public String toString() {
		return "BaseSearchFormDTO [skip=" + skip + ", take=" + take + ", sort=" + sort + ", sortDirection="
				+ sortDirection + "]";
	}

}
