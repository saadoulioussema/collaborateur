package sofrecom.collaborateur.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "campagne")
public class Campagne {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	@Column(nullable = true)
	private Boolean actif;
	
	
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
