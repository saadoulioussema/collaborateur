package sofrecom.collaborateur.service;

import java.util.List;

import javassist.tools.rmi.ObjectNotFoundException;
import sofrecom.collaborateur.model.Objectif;

public interface IObjectifService {
	
	public List<Objectif> getObjectifListByidUserAndidCompagne(long idUser);
	public void autoEvaluateObjectif(Objectif objectif);
	public void evaluateObjectif(Objectif objectif);
	public void newObjectif(Objectif objectif,long id) throws ObjectNotFoundException;
	public List<Objectif> getCollaborateurObjectifsForManager(long id);

}
