package sofrecom.collaborateur.model;



import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class DAOUser {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String fullname;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String username;
	@Column
//	@JsonIgnore
	private String password;
	@Column(nullable = true)
	private String matirucule;
	@Column(nullable = true)
	private Date dateIntegration;
	@Column(nullable = true)
	private DAOUser manager;
	

	public DAOUser() {
		super();
	}

	public long getId() {
		return id;
	}
	public String getFullnamee() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMatirucule() {
		return matirucule;
	}
	public void setMatirucule(String matirucule) {
		this.matirucule = matirucule;
	}
	public Date getDateIntegration() {
		return dateIntegration;
	}

	public void setDateIntegration(Date dateIntegration) {
		this.dateIntegration = dateIntegration;
	}

	public String getFullname() {
		return fullname;
	}
	

	public DAOUser getManager() {
		return manager;
	}


	public void setManager(DAOUser manager) {
		this.manager = manager;
	}


	@Override
	public String toString() {
		return "DAOUser [id=" + id + ", fullname=" + fullname + ", email=" + email + ", username=" + username
				+ ", password=" + password + ", matirucule=" + matirucule + ", dateIntegration=" + dateIntegration
				+ ", manager=" + manager + "]";
	}

	
}