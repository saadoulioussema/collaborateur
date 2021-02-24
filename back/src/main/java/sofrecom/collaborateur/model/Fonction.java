package sofrecom.collaborateur.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "fonction")
public class Fonction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String libelle;
	
	
	@ManyToOne
    @JoinColumn(name = "idCampagne", referencedColumnName = "id")
	private Campagne campagne;
	
	
	@OneToMany(mappedBy="fonction")
	private List<DAOUser> users;
}
