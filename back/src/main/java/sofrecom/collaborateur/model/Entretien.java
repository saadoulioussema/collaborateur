package sofrecom.collaborateur.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "entretien")
public class Entretien {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private Date date;
	
	@ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
	private DAOUser user;
	
	@ManyToOne
    @JoinColumn(name = "idCampagne", referencedColumnName = "idCampagne")
	private Campagne campagne;

}
