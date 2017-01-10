package com.madar.tracking.configuration.domain;
/**
 * @author Salma
 *
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Departement.
 */

@Data
@RedisHash("department")
@NoArgsConstructor
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String departmentSystemId;

	private String departmentName;

	private String departmentNameAr;

	private String citySystemId;

	private String cityName;

	private String createdBy;

	private LocalDate dateCreated;

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

	public Department citySystemId(String citySystemId) {
		this.citySystemId = citySystemId;
		return this;
	}

	public void setCitySystemId(String citySystemId) {
		this.citySystemId = citySystemId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Department createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public Department dateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
		return this;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getNotes() {
		return notes;
	}

	public Department notes(String notes) {
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentNameAr() {
		return departmentNameAr;
	}

	public void setDepartmentNameAr(String departmentNameAr) {
		this.departmentNameAr = departmentNameAr;
	}

	public String getDepartmentSystemId() {
		return departmentSystemId;
	}

	public void setDepartmentSystemId(String departmentSystemId) {
		this.departmentSystemId = departmentSystemId;
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
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Department departement = (Department) o;
		if (departement.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, departement.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentSystemId=" + departmentSystemId + ", departmentName="
				+ departmentName + ", departmentNameAr=" + departmentNameAr + ", citySystemId=" + citySystemId
				+ ", cityName=" + cityName + ", createdBy=" + createdBy + ", dateCreated=" + dateCreated + ", notes="
				+ notes + ", organizationSystemId=" + organizationSystemId + ", migrationId=" + migrationId + "]";
	}

}
