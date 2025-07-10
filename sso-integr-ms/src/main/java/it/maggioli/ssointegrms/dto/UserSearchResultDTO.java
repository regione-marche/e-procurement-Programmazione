package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.List;

import it.maggioli.ssointegrms.domain.User;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class UserSearchResultDTO implements Serializable {

	private static final long serialVersionUID = 7391981068691270441L;

	private List<User> userList;
	private Long totalCount;

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
