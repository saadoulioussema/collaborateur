package sofrecom.collaborateur.service;

import java.util.List;



import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Entretien;

public interface IEntretienService {
	public Entretien addEntretien(Entretien entretien);
	public List<Entretien> getEIPsByManagerAndCompagne(long id);
	public DAOUser getCollaborateurByEntretien(long id) ;
	public Entretien getEntretienByCollaborateurAndCompagne(long id) ;
	public Entretien saveProjectAndFormation(String projet, String formation,long idEntretien);
	
	public Entretien closeEntretien(Entretien entretien);
}
