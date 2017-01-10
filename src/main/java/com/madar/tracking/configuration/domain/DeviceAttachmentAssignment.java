package com.madar.tracking.configuration.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * A Device Attachment Assignment.
 */

@RedisHash("device-attachmen-aassignment")
public class DeviceAttachmentAssignment {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String deviceAttachmentAssignmentSystemId;

	private String deviceSystemId;

	private String deviceName;

	private String attachmentSystemId;

	private String createdBy;

	private long dateCreated;

	private String attachmentName;

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

	public void setDeviceSystemId(String deviceSystemId) {
		this.deviceSystemId = deviceSystemId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getAttachmentSystemId() {
		return attachmentSystemId;
	}

	public void setAttachmentSystemId(String attachmentSystemId) {
		this.attachmentSystemId = attachmentSystemId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getOrganisationSystemId() {
		return organisationSystemId;
	}

	public void setOrganisationSystemId(String organisationSystemId) {
		this.organisationSystemId = organisationSystemId;
	}

	public String getDeviceAttachmentAssignmentSystemId() {
		return deviceAttachmentAssignmentSystemId;
	}

	public void setDeviceAttachmentAssignmentSystemId(String deviceAttachmentAssignmentSystemId) {
		this.deviceAttachmentAssignmentSystemId = deviceAttachmentAssignmentSystemId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachmentName == null) ? 0 : attachmentName.hashCode());
		result = prime * result + ((attachmentSystemId == null) ? 0 : attachmentSystemId.hashCode());
		result = prime * result + ((organisationSystemId == null) ? 0 : organisationSystemId.hashCode());
		result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
		result = prime * result + ((deviceSystemId == null) ? 0 : deviceSystemId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DeviceAttachmentAssignment other = (DeviceAttachmentAssignment) obj;
		if (attachmentName == null) {
			if (other.attachmentName != null)
				return false;
		} else if (!attachmentName.equals(other.attachmentName))
			return false;
		if (attachmentSystemId == null) {
			if (other.attachmentSystemId != null)
				return false;
		} else if (!attachmentSystemId.equals(other.attachmentSystemId))
			return false;
		if (organisationSystemId == null) {
			if (other.organisationSystemId != null)
				return false;
		} else if (!organisationSystemId.equals(other.organisationSystemId))
			return false;
		if (deviceName == null) {
			if (other.deviceName != null)
				return false;
		} else if (!deviceName.equals(other.deviceName))
			return false;
		if (deviceSystemId == null) {
			if (other.deviceSystemId != null)
				return false;
		} else if (!deviceSystemId.equals(other.deviceSystemId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeviceAttachmentAssignment [id=" + id + ", deviceSystemId=" + deviceSystemId + ", deviceName="
				+ deviceName + ", attachmentSystemId=" + attachmentSystemId + ", attachmentName=" + attachmentName
				+ ", customerSystemId=" + organisationSystemId + "]";
	}

}
