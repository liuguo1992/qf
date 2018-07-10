package com.qFun.qFun.common.dingding;

public class Department {
	
	private String id;
	
	private String name;
	
	private String parentId;
	
	private boolean createDeptGroup;
	
	private boolean autoAddUser;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isCreateDeptGroup() {
		return createDeptGroup;
	}

	public void setCreateDeptGroup(boolean createDeptGroup) {
		this.createDeptGroup = createDeptGroup;
	}

	public boolean isAutoAddUser() {
		return autoAddUser;
	}

	public void setAutoAddUser(boolean autoAddUser) {
		this.autoAddUser = autoAddUser;
	}
	
	
	

}
