package sofrecom.collaborateur.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class EvaluationPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idUser;
	private long idCompetence;

	public EvaluationPK() {
		super();
	}

	public long getIdCompetence() {
		return idCompetence;
	}

	public void setIdCompetence(long idCompetence) {
		this.idCompetence = idCompetence;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCompetence ^ (idCompetence >>> 32));
		result = prime * result + (int) (idUser ^ (idUser >>> 32));
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
		EvaluationPK other = (EvaluationPK) obj;
		if (idCompetence != other.idCompetence)
			return false;
		if (idUser != other.idUser)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EvaluationPK [idUser=" + idUser + ", idCompetence=" + idCompetence + "]";
	}
}
