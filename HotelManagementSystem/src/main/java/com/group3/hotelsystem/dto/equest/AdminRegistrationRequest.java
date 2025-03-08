package com.group3.hotelsystem.dto.equest;

public class AdminRegistrationRequest extends RegistrationRequest {
	private String departmentId;
	private String adminRole;

	public AdminRegistrationRequest(String departmentId, String adminRole) {
		super();
		this.departmentId = departmentId;
		this.adminRole = adminRole;
	}

	public AdminRegistrationRequest() {
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(String adminRole) {
		this.adminRole = adminRole;
	}

}
