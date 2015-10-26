package com.may.ple.parking.gateway.criteria;

public class VehicleSaveCriteriaReq {
	private Integer licenseNo;
	private String deviceId;
	private String gateName;
	
	public Integer getLicenseNo() {
		return licenseNo;
	}
	
	public void setLicenseNo(Integer licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getGateName() {
		return gateName;
	}

	public void setGateName(String gateName) {
		this.gateName = gateName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
