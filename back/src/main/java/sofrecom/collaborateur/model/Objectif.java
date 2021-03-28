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
	
	@ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
	private DAOUser user;
	
	
	@ManyToOne
    @JoinColumn(name = "idCompagne", referencedColumnName = "idCompagne")
	private Compagne compagne;
	
	
	public Objectif() {
		super();
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
	
	
	
	public DAOUser getUser() {
		return user;
	}

	public void setUser(DAOUser user) {
		this.user = user;
	}

	public Compagne getCompagne() {
		return compagne;
	}

	public void setCompagne(Compagne compagne) {
		this.compagne = compagne;
	}

	@Override
	public String toString() {
		return "Objectif [id=" + id + ", designation=" + designation + ", evaluation=" + evaluation + ", commentaire="
				+ commentaire + ", autoEvaluation=" + autoEvaluation + "]";
	}
	
	
}
