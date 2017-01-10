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
 * A City.
 */

@Data
@RedisHash("city")
@NoArgsConstructor
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String citySystemId;

    private String regionSystemId;
    
    private String regionName;
    
    private String cityName;
    
    private String cityNameAr;

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

    public String getCitySystemId() {
        return citySystemId;
    }

    public City citySystemId(String citySystemId) {
        this.citySystemId = citySystemId;
        return this;
    }

    public void setCitySystemId(String citySystemId) {
        this.citySystemId = citySystemId;
    }

    public String getRegionSystemId() {
        return regionSystemId;
    }

    public City regionSystemId(String regionSystemId) {
        this.regionSystemId = regionSystemId;
        return this;
    }

    public void setRegionSystemId(String regionSystemId) {
        this.regionSystemId = regionSystemId;
    }
        

    public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCreatedBy() {
        return createdBy;
    }

    public City createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
//    @JsonFormat(pattern="yyyy-MM-dd")
    public long getDateCreated() {
        return dateCreated;
    }

    public City dateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getNotes() {
        return notes;
    }

    public City notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    

    public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityNameAr() {
		return cityNameAr;
	}

	public void setCityNameAr(String cityNameAr) {
		this.cityNameAr = cityNameAr;
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
		result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
		result = prime * result + ((cityNameAr == null) ? 0 : cityNameAr.hashCode());
		result = prime * result + ((citySystemId == null) ? 0 : citySystemId.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + (int) (dateCreated ^ (dateCreated >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((migrationId == null) ? 0 : migrationId.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((organizationSystemId == null) ? 0 : organizationSystemId.hashCode());
		result = prime * result + ((regionName == null) ? 0 : regionName.hashCode());
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
		City other = (City) obj;
		if (cityName == null) {
			if (other.cityName != null)
				return false;
		} else if (!cityName.equals(other.cityName))
			return false;
		if (cityNameAr == null) {
			if (other.cityNameAr != null)
				return false;
		} else if (!cityNameAr.equals(other.cityNameAr))
			return false;
		if (citySystemId == null) {
			if (other.citySystemId != null)
				return false;
		} else if (!citySystemId.equals(other.citySystemId))
			return false;
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
		if (regionSystemId == null) {
			if (other.regionSystemId != null)
				return false;
		} else if (!regionSystemId.equals(other.regionSystemId))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "City{" +
            "id=" + id +
            ", citySystemId='" + citySystemId + "'" +
            ", regionSystemId='" + regionSystemId + "'" +
            ", createdBy='" + createdBy + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", note='" + notes + "'" +
            '}';
    }
}
