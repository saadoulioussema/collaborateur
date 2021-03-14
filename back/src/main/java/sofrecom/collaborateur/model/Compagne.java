package sofrecom.collaborateur.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "compagne")
public class Compagne {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id ;
	
	@Id
	private String idCompagne;
	
	@Column(nullable = false)
	private Boolean actif;
	

	@OneToMany(mappedBy="compagne")
	private List<Objectif> objectifs;
	

	@OneToMany(mappedBy="compagne")
	private List<Entretien> entretiens;
	
	
	
	public Compagne() {
		super();
	}

	public String getIdCompagne() {
		return idCompagne;
	}


	public void setIdCompagne(String idCompagne) {
		this.idCompagne = idCompagne;
	}
	
	public Boolean getActif() {
		return actif;
	}


	public void setActif(Boolean actif) {
		this.actif = actif;
	}




}
