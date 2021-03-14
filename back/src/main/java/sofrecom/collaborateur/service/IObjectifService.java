package sofrecom.collaborateur.service;

import java.util.List;

import sofrecom.collaborateur.model.Objectif;

public interface IObjectifService {
	
	public List<Objectif> getAllObjectifs(String idCompagne,long idUser);
	public void autoEvaluateObjectif(Objectif objectif);
	public List<Objectif> getCollaborateurObjectifsForManager(long id);

}
