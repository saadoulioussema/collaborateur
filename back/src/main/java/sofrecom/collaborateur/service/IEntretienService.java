package sofrecom.collaborateur.service;

import java.util.List;



import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Entretien;

public interface IEntretienService {
	
	public List<Entretien> getEIPsByManager(long id);
	public DAOUser getCollaborateurByEntretien(long id) ;
	public void evaluateObjectif(Entretien entretien);

}
