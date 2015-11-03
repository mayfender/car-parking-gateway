package com.may.ple.parking.gateway.criteria;

import java.util.Date;
import java.util.Map;

public class VehicleParking {
	public String id;
	public Date inDateTime;
	public Date outDateTime;
	public Integer price;
	public Integer status;
	public String licenseNo;
	public String deviceId;
	public String gateName;
	public Map<String, Long> dateTimeDiffMap;
}
