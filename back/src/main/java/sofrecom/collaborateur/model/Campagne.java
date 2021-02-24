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
@Table(name = "campagne")
public class Campagne {
	
	@Id
	private String id;
	@Column(nullable = true)
	private Boolean actif;
	
	
	@OneToMany(mappedBy="campagne")
	private List<Objectif> objectifs;
	
	@OneToMany(mappedBy="campagne")
	private List<Entretien> entretiens;
	
	
	
	public Campagne() {
		super();
	}


	public Boolean getActif() {
		return actif;
	}


	public void setActif(Boolean actif) {
		this.actif = actif;
	}

}
