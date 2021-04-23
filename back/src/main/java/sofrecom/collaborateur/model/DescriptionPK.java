package sofrecom.collaborateur.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DescriptionPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long idCompetence;
	private long idNiveau;
	
	
	public DescriptionPK() {
		super();
	}
	


	public long getIdCompetence() {
		return idCompetence;
	}

	public void setIdCompetence(long idCompetence) {
		this.idCompetence = idCompetence;
	}

	public long getIdNiveau() {
		return idNiveau;
	}

	public void setIdNiveau(long idNiveau) {
		this.idNiveau = idNiveau;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCompetence ^ (idCompetence >>> 32));
		result = prime * result + (int) (idNiveau ^ (idNiveau >>> 32));
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
		DescriptionPK other = (DescriptionPK) obj;
		if (idCompetence != other.idCompetence)
			return false;
		if (idNiveau != other.idNiveau)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "DescriptionPK [idCompetence=" + idCompetence + ", idNiveau=" + idNiveau + "]";
	}
	
}
