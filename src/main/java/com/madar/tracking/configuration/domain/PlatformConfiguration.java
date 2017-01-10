package com.madar.tracking.configuration.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Salma
 *
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A PlatformConfiguration.
 */
@Data
@RedisHash("platformConfiguration")
@NoArgsConstructor
public class PlatformConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Indexed
	private String className;

	private Integer lastSystemClassId;

	public PlatformConfiguration(String className, Integer lastSystemClassId) {

		this.className = className;
		this.lastSystemClassId = lastSystemClassId;
	}

	public PlatformConfiguration() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public PlatformConfiguration className(String className) {
		this.className = className;
		return this;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getLastSystemClassId() {
		return lastSystemClassId;
	}

	public PlatformConfiguration lastSystemClassId(Integer lastSystemClassId) {
		this.lastSystemClassId = lastSystemClassId;
		return this;
	}

	public void setLastSystemClassId(Integer lastSystemClassId) {
		this.lastSystemClassId = lastSystemClassId;
	}

	public void addOneToLastSystemClassId() {
		this.lastSystemClassId++;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PlatformConfiguration platformConfiguration = (PlatformConfiguration) o;
		if (platformConfiguration.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, platformConfiguration.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "PlatformConfiguration{" + "id=" + id + ", className=" + className + ", lastSystemClassId='"
				+ lastSystemClassId + "'" + '}';
	}
}
