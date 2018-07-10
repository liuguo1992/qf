package com.qFun.qFun.common.dingding;

import java.util.List;


public class UserList {

	
	private Integer errcode;
	
	private String errmsg;
	
	private boolean hasMore;
	
	private List<DUser> userList;

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public List<DUser> getUserList() {
		return userList;
	}

	public void setUserList(List<DUser> userList) {
		this.userList = userList;
	}
	
	
	
}
