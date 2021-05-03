package sofrecom.collaborateur.model;


import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "evaluation")
public class Evaluation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EvaluationPK evaluationPK;
	
	
	@ManyToOne
    @JoinColumn(insertable=false,updatable=false, name = "idUser", referencedColumnName = "id")
	private DAOUser user;	
	
	
	@ManyToOne
    @JoinColumn(insertable=false,updatable=false, name = "idCompetence", referencedColumnName = "id")
	private Competence competence;
	
	private int niveau ;

	
	
	public EvaluationPK getEvaluationPK() {
		return evaluationPK;
	}

	public void setEvaluationPK(EvaluationPK evaluationPK) {
		this.evaluationPK = evaluationPK;
	}

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}
}
