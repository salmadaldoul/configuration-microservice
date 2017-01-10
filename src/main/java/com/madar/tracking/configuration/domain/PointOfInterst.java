package com.madar.tracking.configuration.domain;
/**
 * @author Salma
 *
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PointOfInterst.
 */

@RedisHash("point_of_interst")
public class PointOfInterst implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String poiSystemId;

    private String poiName;

    private byte[] poiIcon;

    private String poiIconContentType;

    private String color;

    private String createdBy;

    private long dateCreated;

    private String longitude;

    private String latitude;

    private String description;
    
    private String organisationSystemId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoiSystemId() {
        return poiSystemId;
    }

    public PointOfInterst poiSystemId(String poiSystemId) {
        this.poiSystemId = poiSystemId;
        return this;
    }

    public void setPoiSystemId(String poiSystemId) {
        this.poiSystemId = poiSystemId;
    }

    public String getPoiName() {
        return poiName;
    }

    public PointOfInterst poiName(String poiName) {
        this.poiName = poiName;
        return this;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public byte[] getPoiIcon() {
        return poiIcon;
    }

    public PointOfInterst poiIcon(byte[] poiIcon) {
        this.poiIcon = poiIcon;
        return this;
    }

    public void setPoiIcon(byte[] poiIcon) {
        this.poiIcon = poiIcon;
    }

    public String getPoiIconContentType() {
        return poiIconContentType;
    }

    public PointOfInterst poiIconContentType(String poiIconContentType) {
        this.poiIconContentType = poiIconContentType;
        return this;
    }

    public void setPoiIconContentType(String poiIconContentType) {
        this.poiIconContentType = poiIconContentType;
    }

    public String getColor() {
        return color;
    }

    public PointOfInterst color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PointOfInterst createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public long getDateCreated() {
        return dateCreated;
    }

    public PointOfInterst dateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLongitude() {
        return longitude;
    }

    public PointOfInterst longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public PointOfInterst latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public PointOfInterst description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   
    public String getOrganisationSystemId() {
		return organisationSystemId;
	}

	public void setOrganisationSystemId(String organisationSystemId) {
		this.organisationSystemId = organisationSystemId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointOfInterst pointOfInterst = (PointOfInterst) o;
        if(pointOfInterst.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pointOfInterst.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PointOfInterst{" +
            "id=" + id +
            ", poiSystemId='" + poiSystemId + "'" +
            ", poiName='" + poiName + "'" +
            ", poiIcon='" + poiIcon + "'" +
            ", poiIconContentType='" + poiIconContentType + "'" +
            ", color='" + color + "'" +
            ", createdBy='" + createdBy + "'" +
            ", dateCreated='" + dateCreated + "'" +
            ", longitude='" + longitude + "'" +
            ", latitude='" + latitude + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
