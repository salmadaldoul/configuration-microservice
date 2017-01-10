package com.madar.tracking.configuration.domain;
/**
 * @author Salma
 *
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Objects;


/**
 * A OrganisationLevels.
 */

@RedisHash("organisation_levels")
public class OrganisationLevels implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String organisationLevelsSystemId;

    private String level1English;

    private String level1Arabic;

    private String level2English;

    private String level2Arabic;

    private String level3English;

    private String level3Arabic;
    
    private String organisationSystemId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganisationLevelsSystemId() {
        return organisationLevelsSystemId;
    }

    public OrganisationLevels organisationLevelsSystemId(String organisationLevelsSystemId) {
        this.organisationLevelsSystemId = organisationLevelsSystemId;
        return this;
    }

    public void setOrganisationLevelsSystemId(String organisationLevelsSystemId) {
        this.organisationLevelsSystemId = organisationLevelsSystemId;
    }

    public String getLevel1English() {
        return level1English;
    }

    public OrganisationLevels level1English(String level1English) {
        this.level1English = level1English;
        return this;
    }

    public void setLevel1English(String level1English) {
        this.level1English = level1English;
    }

    public String getLevel1Arabic() {
        return level1Arabic;
    }

    public OrganisationLevels level1Arabic(String level1Arabic) {
        this.level1Arabic = level1Arabic;
        return this;
    }

    public void setLevel1Arabic(String level1Arabic) {
        this.level1Arabic = level1Arabic;
    }

    public String getLevel2English() {
        return level2English;
    }

    public OrganisationLevels level2English(String level2English) {
        this.level2English = level2English;
        return this;
    }

    public void setLevel2English(String level2English) {
        this.level2English = level2English;
    }

    public String getLevel2Arabic() {
        return level2Arabic;
    }

    public OrganisationLevels level2Arabic(String level2Arabic) {
        this.level2Arabic = level2Arabic;
        return this;
    }

    public void setLevel2Arabic(String level2Arabic) {
        this.level2Arabic = level2Arabic;
    }

    public String getLevel3English() {
        return level3English;
    }

    public OrganisationLevels level3English(String level3English) {
        this.level3English = level3English;
        return this;
    }

    public void setLevel3English(String level3English) {
        this.level3English = level3English;
    }

    public String getLevel3Arabic() {
        return level3Arabic;
    }

    public OrganisationLevels level3Arabic(String level3Arabic) {
        this.level3Arabic = level3Arabic;
        return this;
    }

    public void setLevel3Arabic(String level3Arabic) {
        this.level3Arabic = level3Arabic;
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
		result = prime * result + ((organisationSystemId == null) ? 0 : organisationSystemId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((level1Arabic == null) ? 0 : level1Arabic.hashCode());
		result = prime * result + ((level1English == null) ? 0 : level1English.hashCode());
		result = prime * result + ((level2Arabic == null) ? 0 : level2Arabic.hashCode());
		result = prime * result + ((level2English == null) ? 0 : level2English.hashCode());
		result = prime * result + ((level3Arabic == null) ? 0 : level3Arabic.hashCode());
		result = prime * result + ((level3English == null) ? 0 : level3English.hashCode());
		result = prime * result + ((organisationLevelsSystemId == null) ? 0 : organisationLevelsSystemId.hashCode());
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
		OrganisationLevels other = (OrganisationLevels) obj;
		if (organisationSystemId == null) {
			if (other.organisationSystemId != null)
				return false;
		} else if (!organisationSystemId.equals(other.organisationSystemId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level1Arabic == null) {
			if (other.level1Arabic != null)
				return false;
		} else if (!level1Arabic.equals(other.level1Arabic))
			return false;
		if (level1English == null) {
			if (other.level1English != null)
				return false;
		} else if (!level1English.equals(other.level1English))
			return false;
		if (level2Arabic == null) {
			if (other.level2Arabic != null)
				return false;
		} else if (!level2Arabic.equals(other.level2Arabic))
			return false;
		if (level2English == null) {
			if (other.level2English != null)
				return false;
		} else if (!level2English.equals(other.level2English))
			return false;
		if (level3Arabic == null) {
			if (other.level3Arabic != null)
				return false;
		} else if (!level3Arabic.equals(other.level3Arabic))
			return false;
		if (level3English == null) {
			if (other.level3English != null)
				return false;
		} else if (!level3English.equals(other.level3English))
			return false;
		if (organisationLevelsSystemId == null) {
			if (other.organisationLevelsSystemId != null)
				return false;
		} else if (!organisationLevelsSystemId.equals(other.organisationLevelsSystemId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrganisationLevels [id=" + id + ", organisationLevelsSystemId=" + organisationLevelsSystemId
				+ ", level1English=" + level1English + ", level1Arabic=" + level1Arabic + ", level2English="
				+ level2English + ", level2Arabic=" + level2Arabic + ", level3English=" + level3English
				+ ", level3Arabic=" + level3Arabic + ", customerSystemId=" + organisationSystemId + "]";
	}


}
