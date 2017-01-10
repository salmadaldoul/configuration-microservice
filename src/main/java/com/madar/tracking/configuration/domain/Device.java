package com.madar.tracking.configuration.domain;
/**
 * @author Salma
 *
 */

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.madar.tracking.configuration.domain.enumeration.DeviceStatus;

/**
 * A Device.
 */

@RedisHash("device")
public class Device implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String deviceSystemId;

	private String deviceName;

	private String vehicleSystemId;

	private String deviceSerialNumber;

	private String deviceUID;

	private String deviceImei;

	private String deviceBrand;

	private String vehicleName;

	private String vehicleNameAr;

	private String createdBy;

	private long dateCreated;

	private String decription;

	private String simCountryCode;

	private String simNumber;

	private String simType;

	private String deviceType;

	private DeviceStatus deviceStatus;

	private String organisationSystemId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceSystemId() {
		return deviceSystemId;
	}

	public Device deviceSystemId(String deviceSystemId) {
		this.deviceSystemId = deviceSystemId;
		return this;
	}

	public void setDeviceSystemId(String deviceSystemId) {
		this.deviceSystemId = deviceSystemId;
	}

	public String getVehicleSystemId() {
		return vehicleSystemId;
	}

	public Device vehicleSystemId(String vehicleSystemId) {
		this.vehicleSystemId = vehicleSystemId;
		return this;
	}

	public void setVehicleSystemId(String vehicleSystemId) {
		this.vehicleSystemId = vehicleSystemId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public Device deviceName(String deviceName) {
		this.deviceName = deviceName;
		return this;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceImei() {
		return deviceImei;
	}

	public Device deviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
		return this;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}

	public String getDeviceBrand() {
		return deviceBrand;
	}

	public Device deviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
		return this;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public Device vehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
		return this;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Device createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public Device dateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
		return this;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDecription() {
		return decription;
	}

	public Device decription(String decription) {
		this.decription = decription;
		return this;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}

	public String getDeviceUID() {
		return deviceUID;
	}

	public void setDeviceUID(String deviceUID) {
		this.deviceUID = deviceUID;
	}

	public String getVehicleNameAr() {
		return vehicleNameAr;
	}

	public void setVehicleNameAr(String vehicleNameAr) {
		this.vehicleNameAr = vehicleNameAr;
	}

	public String getSimCountryCode() {
		return simCountryCode;
	}

	public void setSimCountryCode(String simCountryCode) {
		this.simCountryCode = simCountryCode;
	}

	public String getSimNumber() {
		return simNumber;
	}

	public void setSimNumber(String simNumbre) {
		this.simNumber = simNumbre;
	}

	public String getSimType() {
		return simType;
	}

	public void setSimType(String simType) {
		this.simType = simType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public DeviceStatus getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(DeviceStatus deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getOrganisationSystemId() {
		return organisationSystemId;
	}

	public void setOrganisationSystemId(String organisationSystemId) {
		this.organisationSystemId = organisationSystemId;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + (int) (dateCreated ^ (dateCreated >>> 32));
		result = prime * result + ((decription == null) ? 0 : decription.hashCode());
		result = prime * result + ((deviceBrand == null) ? 0 : deviceBrand.hashCode());
		result = prime * result + ((deviceImei == null) ? 0 : deviceImei.hashCode());
		result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
		result = prime * result + ((deviceSerialNumber == null) ? 0 : deviceSerialNumber.hashCode());
		result = prime * result + ((deviceStatus == null) ? 0 : deviceStatus.hashCode());
		result = prime * result + ((deviceSystemId == null) ? 0 : deviceSystemId.hashCode());
		result = prime * result + ((deviceType == null) ? 0 : deviceType.hashCode());
		result = prime * result + ((deviceUID == null) ? 0 : deviceUID.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((organisationSystemId == null) ? 0 : organisationSystemId.hashCode());
		result = prime * result + ((simCountryCode == null) ? 0 : simCountryCode.hashCode());
		result = prime * result + ((simNumber == null) ? 0 : simNumber.hashCode());
		result = prime * result + ((simType == null) ? 0 : simType.hashCode());
		result = prime * result + ((vehicleName == null) ? 0 : vehicleName.hashCode());
		result = prime * result + ((vehicleNameAr == null) ? 0 : vehicleNameAr.hashCode());
		result = prime * result + ((vehicleSystemId == null) ? 0 : vehicleSystemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (dateCreated != other.dateCreated)
			return false;
		if (decription == null) {
			if (other.decription != null)
				return false;
		} else if (!decription.equals(other.decription))
			return false;
		if (deviceBrand == null) {
			if (other.deviceBrand != null)
				return false;
		} else if (!deviceBrand.equals(other.deviceBrand))
			return false;
		if (deviceImei == null) {
			if (other.deviceImei != null)
				return false;
		} else if (!deviceImei.equals(other.deviceImei))
			return false;
		if (deviceName == null) {
			if (other.deviceName != null)
				return false;
		} else if (!deviceName.equals(other.deviceName))
			return false;
		if (deviceSerialNumber == null) {
			if (other.deviceSerialNumber != null)
				return false;
		} else if (!deviceSerialNumber.equals(other.deviceSerialNumber))
			return false;
		if (deviceStatus != other.deviceStatus)
			return false;
		if (deviceSystemId == null) {
			if (other.deviceSystemId != null)
				return false;
		} else if (!deviceSystemId.equals(other.deviceSystemId))
			return false;
		if (deviceType == null) {
			if (other.deviceType != null)
				return false;
		} else if (!deviceType.equals(other.deviceType))
			return false;
		if (deviceUID == null) {
			if (other.deviceUID != null)
				return false;
		} else if (!deviceUID.equals(other.deviceUID))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (organisationSystemId == null) {
			if (other.organisationSystemId != null)
				return false;
		} else if (!organisationSystemId.equals(other.organisationSystemId))
			return false;
		if (simCountryCode == null) {
			if (other.simCountryCode != null)
				return false;
		} else if (!simCountryCode.equals(other.simCountryCode))
			return false;
		if (simNumber == null) {
			if (other.simNumber != null)
				return false;
		} else if (!simNumber.equals(other.simNumber))
			return false;
		if (simType == null) {
			if (other.simType != null)
				return false;
		} else if (!simType.equals(other.simType))
			return false;
		if (vehicleName == null) {
			if (other.vehicleName != null)
				return false;
		} else if (!vehicleName.equals(other.vehicleName))
			return false;
		if (vehicleNameAr == null) {
			if (other.vehicleNameAr != null)
				return false;
		} else if (!vehicleNameAr.equals(other.vehicleNameAr))
			return false;
		if (vehicleSystemId == null) {
			if (other.vehicleSystemId != null)
				return false;
		} else if (!vehicleSystemId.equals(other.vehicleSystemId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", deviceSystemId=" + deviceSystemId + ", vehicleSystemId=" + vehicleSystemId
				+ ", deviceSerialNumber=" + deviceSerialNumber + ", deviceUID=" + deviceUID + ", deviceName="
				+ deviceName + ", deviceImei=" + deviceImei + ", deviceBrand=" + deviceBrand + ", vehicleName="
				+ vehicleName + ", vehicleNameAr=" + vehicleNameAr + ", createdBy=" + createdBy + ", dateCreated="
				+ dateCreated + ", decription=" + decription + ", simCountryCode=" + simCountryCode + ", simNumbre="
				+ simNumber + ", simType=" + simType + ", deviceType=" + deviceType + ", deviceStatus=" + deviceStatus
				+ ", customerSystemId=" + organisationSystemId + "]";
	}

}
