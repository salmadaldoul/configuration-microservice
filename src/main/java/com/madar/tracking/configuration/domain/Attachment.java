package com.madar.tracking.configuration.domain;
/**
 * @author Salma
 *
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import com.madar.tracking.configuration.domain.enumeration.AttachmentStatus;
import com.madar.tracking.configuration.domain.enumeration.PortType;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Attachment
 */

@Data
@RedisHash("attachment")
@NoArgsConstructor
public class Attachment {
	
	 private static final long serialVersionUID = 1L;

	    @Id
	    private String id;

	    private String attachmentSystemId;

	    private String attachmentName;
	    
	    private String portRange;
	    
	    private AttachmentStatus attachmentstatus;
	   
	    private String category;
	    
	    private PortType portType;
	    
	    private String createdBy;
	    
	    private long dateCreated;
	    
	    @Indexed
	    private String organizationSystemId;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
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

		public String getPortRange() {
			return portRange;
		}

		public void setPortRange(String portRange) {
			this.portRange = portRange;
		}

		public AttachmentStatus getAttachmentstatus() {
			return attachmentstatus;
		}

		public void setAttachmentstatus(AttachmentStatus attachmentstatus) {
			this.attachmentstatus = attachmentstatus;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public PortType getPortType() {
			return portType;
		}

		public void setPortType(PortType portType) {
			this.portType = portType;
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

	    public Attachment dateCreated(long dateCreated) {
	        this.dateCreated = dateCreated;
	        return this;
	    }

	    public void setDateCreated(long dateCreated) {
	        this.dateCreated = dateCreated;
	    }

		public String getOrganizationSystemId() {
			return organizationSystemId;
		}

		public void setOrganizationSystemId(String organizationSystemId) {
			this.organizationSystemId = organizationSystemId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((attachmentName == null) ? 0 : attachmentName.hashCode());
			result = prime * result + ((attachmentSystemId == null) ? 0 : attachmentSystemId.hashCode());
			result = prime * result + ((attachmentstatus == null) ? 0 : attachmentstatus.hashCode());
			result = prime * result + ((category == null) ? 0 : category.hashCode());
			result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
			result = prime * result + (int) (dateCreated ^ (dateCreated >>> 32));
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((organizationSystemId == null) ? 0 : organizationSystemId.hashCode());
			result = prime * result + ((portRange == null) ? 0 : portRange.hashCode());
			result = prime * result + ((portType == null) ? 0 : portType.hashCode());
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
			Attachment other = (Attachment) obj;
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
			if (attachmentstatus != other.attachmentstatus)
				return false;
			if (category == null) {
				if (other.category != null)
					return false;
			} else if (!category.equals(other.category))
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
			if (organizationSystemId == null) {
				if (other.organizationSystemId != null)
					return false;
			} else if (!organizationSystemId.equals(other.organizationSystemId))
				return false;
			if (portRange == null) {
				if (other.portRange != null)
					return false;
			} else if (!portRange.equals(other.portRange))
				return false;
			if (portType != other.portType)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Attachment [id=" + id + ", attachmentSystemId=" + attachmentSystemId + ", attachmentName="
					+ attachmentName + ", portRange=" + portRange + ", attachmentstatus=" + attachmentstatus
					+ ", category=" + category + ", portType=" + portType + ", customerSystemId=" + organizationSystemId
					+ "]";
		}

	    
	    
	    
}
