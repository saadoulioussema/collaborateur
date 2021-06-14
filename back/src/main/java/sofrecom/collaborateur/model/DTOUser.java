package sofrecom.collaborateur.model;



public class DTOUser {
	private long id;
	private long managerId;
	private long fonctionId;
	private long entretienId;
	private String fullname;
	private String email;
	private String matricule;
	private String dateIntegration;
	private String username;
	private String password;

	
	
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getManagerId() {
		return managerId;
	}

	public void setManagerId(long managerId) {
		this.managerId = managerId;
	}
	

	public long getFonctionId() {
		return fonctionId;
	}

	public void setFonctionId(long fonctionId) {
		this.fonctionId = fonctionId;
	}

	
	public long getEntretienId() {
		return entretienId;
	}

	public void setEntretienId(long entretienId) {
		this.entretienId = entretienId;
	}

	public String getFullname() {
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

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getDateIntegration() {
		return dateIntegration;
	}

	public void setDateIntegration(String dateIntegration) {
		this.dateIntegration = dateIntegration;
	}
	
	

	
	
}