package sofrecom.collaborateur.model;


import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "description")
public class Description implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DescriptionPK descriptionPK;
	
	@ManyToOne
    @JoinColumn(insertable=false,updatable=false, name = "idCompetence", referencedColumnName = "id")
	private Competence competence;

	@ManyToOne
    @JoinColumn(insertable=false,updatable=false, name = "idNiveau", referencedColumnName = "id")
	private Niveau niveau;	
	
	String description;
	
	
	

	public DescriptionPK getDescriptionPK() {
		return descriptionPK;
	}

	public void setDescriptionPK(DescriptionPK descriptionPK) {
		this.descriptionPK = descriptionPK;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Description [descriptionPK=" + descriptionPK + ", competence=" + competence + ", niveau=" + niveau
				+ ", description=" + description + "]";
	}
	

}
