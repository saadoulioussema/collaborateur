package sofrecom.collaborateur.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "direction")
public class Direction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

}
