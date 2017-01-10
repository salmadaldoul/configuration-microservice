package com.madar.tracking.configuration.domain;
/**
 * @author Salma
 *
 */

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Region.
 */

@Data
@RedisHash("region")
@NoArgsConstructor
public class Region implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String regionSystemId;

	private String regionName;

	private String regionNameAr;

	private String createdBy;

	private long dateCreated;

	private String notes;

	@Indexed
	private String organizationSystemId;
	
	private String migrationId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegionSystemId() {
		return regionSystemId;
	}

	public Region regionSystemId(String regionSystemId) {
		this.regionSystemId = regionSystemId;
		return this;
	}

	public void setRegionSystemId(String regionSystemId) {
		this.regionSystemId = regionSystemId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Region createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public Region dateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
		return this;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getNotes() {
		return notes;
	}

	public Region notes(String notes) {
		this.notes = notes;
		return this;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionNameAr() {
		return regionNameAr;
	}

	public void setRegionNameAr(String regionNameAr) {
		this.regionNameAr = regionNameAr;
	}

	public String getOrganizationSystemId() {
		return organizationSystemId;
	}

	public void setOrganizationSystemId(String organizationSystemId) {
		this.organizationSystemId = organizationSystemId;
	}

	public String getMigrationId() {
		return migrationId;
	}

	public void setMigrationId(String migrationId) {
		this.migrationId = migrationId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + (int) (dateCreated ^ (dateCreated >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((migrationId == null) ? 0 : migrationId.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((organizationSystemId == null) ? 0 : organizationSystemId.hashCode());
		result = prime * result + ((regionName == null) ? 0 : regionName.hashCode());
		result = prime * result + ((regionNameAr == null) ? 0 : regionNameAr.hashCode());
		result = prime * result + ((regionSystemId == null) ? 0 : regionSystemId.hashCode());
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
		Region other = (Region) obj;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (dateCreated != other.dateCreated)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (migrationId == null) {
			if (other.migrationId != null)
				return false;
		} else if (!migrationId.equals(other.migrationId))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (organizationSystemId == null) {
			if (other.organizationSystemId != null)
				return false;
		} else if (!organizationSystemId.equals(other.organizationSystemId))
			return false;
		if (regionName == null) {
			if (other.regionName != null)
				return false;
		} else if (!regionName.equals(other.regionName))
			return false;
		if (regionNameAr == null) {
			if (other.regionNameAr != null)
				return false;
		} else if (!regionNameAr.equals(other.regionNameAr))
			return false;
		if (regionSystemId == null) {
			if (other.regionSystemId != null)
				return false;
		} else if (!regionSystemId.equals(other.regionSystemId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Region [id=" + id + ", regionSystemId=" + regionSystemId + ", regionName=" + regionName
				+ ", regionNameAr=" + regionNameAr + ", createdBy=" + createdBy + ", dateCreated=" + dateCreated
				+ ", notes=" + notes + ", organizationSystemId=" + organizationSystemId + "]";
	}

}
