package sofrecom.collaborateur.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "objectif")
public class Objectif {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String designation;
	@Column
	private String evaluation;
	@Column
	private String commentaire;
	@Column
	private String autoEvaluation;
	@Column
	private String feedback;
	@ManyToOne
    @JoinColumn(name = "idEntretien", referencedColumnName = "id")
	private Entretien entretien;
	
	


	public Objectif() {
		super();
	}

	
	
	
	
	public Objectif(long id, String designation, String evaluation, String commentaire, String autoEvaluation,
			Entretien entretien) {
		super();
		this.id = id;
		this.designation = designation;
		this.evaluation = evaluation;
		this.commentaire = commentaire;
		this.autoEvaluation = autoEvaluation;
		this.entretien = entretien;
	}





	public Objectif(long id, String designation, String evaluation, String commentaire, String autoEvaluation) {
		super();
		this.id = id;
		this.designation = designation;
		this.evaluation = evaluation;
		this.commentaire = commentaire;
		this.autoEvaluation = autoEvaluation;
	}
	



	public Objectif(String designation, String evaluation, String commentaire, String autoEvaluation) {
		this.designation=designation;
		this.evaluation=evaluation;
		this.commentaire=commentaire;
		this.autoEvaluation=autoEvaluation;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	
	public String getCommentaire() {
		return commentaire;
	}
	
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
	public String getAutoEvaluation() {
		return autoEvaluation;
	}
	
	public void setAutoEvaluation(String autoEvaluation) {
		this.autoEvaluation = autoEvaluation;
	}
	
	public Entretien getEntretien() {
		return entretien;
	}

	public void setEntretien(Entretien entretien) {
		this.entretien = entretien;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}


	@Override
	public String toString() {
		return "Objectif [id=" + id + ", designation=" + designation + ", evaluation=" + evaluation + ", commentaire="
				+ commentaire + ", autoEvaluation=" + autoEvaluation + ", feedback=" + feedback + ", entretien="
				+ entretien + "]";
	}
		
}
