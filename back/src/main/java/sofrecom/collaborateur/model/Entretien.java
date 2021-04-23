package sofrecom.collaborateur.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "entretien")
public class Entretien {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column	
	private Date date;
	@Enumerated(EnumType.STRING)
	@Column
	private Status status;
	
	@Column
	private String projet;
	@Column
	private String points;
	@Column
	private String axes;
	@Column
	private String formations;
	@Column
	private String certifications;
	
	
	@ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
	private DAOUser user;
	
	@ManyToOne
    @JoinColumn(name = "idCompagne", referencedColumnName = "idCompagne")
	private Compagne compagne;
	
	@JsonIgnore
	@OneToMany(mappedBy="entretien")
	private List<Objectif> objectifs;

	public Entretien() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getProjet() {
		return projet;
	}

	public void setProjet(String projet) {
		this.projet = projet;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getAxes() {
		return axes;
	}

	public void setAxes(String axes) {
		this.axes = axes;
	}


	public String getFormations() {
		return formations;
	}

	public void setFormations(String formations) {
		this.formations = formations;
	}

	public String getCertifications() {
		return certifications;
	}

	public void setCertifications(String certifications) {
		this.certifications = certifications;
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

	public List<Objectif> getObjectifs() {
		return objectifs;
	}

	public void setObjectifs(List<Objectif> objectifs) {
		this.objectifs = objectifs;
	}

	@Override
	public String toString() {
		return "Entretien [id=" + id + ", date=" + date + ", status=" + status + ", projet=" + projet + ", points="
				+ points + ", axes=" + axes + ", formations=" + formations + ", certifications=" + certifications
				+ ", user=" + user + ", compagne=" + compagne + ", objectifs=" + objectifs + "]";
	}

}
