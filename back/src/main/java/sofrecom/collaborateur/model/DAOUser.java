package sofrecom.collaborateur.model;



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
	

	public DAOUser() {
		super();
	}


	public DAOUser(long id, String fullname, String email, String username, String password) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.username = username;
		this.password = password;
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
	
	@Override
	public String toString() {
		return "DAOUser [id=" + id + ", fullname=" + fullname + ", email=" + email + ", username=" + username
				+ ", password=" + password + "]";
	}
}