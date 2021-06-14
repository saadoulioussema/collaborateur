package sofrecom.collaborateur.service;

import java.util.List;

import sofrecom.collaborateur.model.Objectif;

public interface IObjectifService {
	
	public List<Objectif> getObjectifListByEntretienAndEntretienCompagne(long id);
	public List<Objectif> getObjectifListByCurrentEntretienAndEntretienCompagne(long id);
	public Objectif autoEvaluateObjectif(Objectif objectif);
	public Objectif evaluateObjectif(Objectif objectif);
	public List<Objectif> newObjectif(List<Objectif> objectifs,long newEntretienId,long idEntretien);
	public void addOtherObjectifsByUserAndCompagne(List<Objectif> objectifs ,long idUser);
	public List<Objectif> deleteNewObjectif(long idUser,String designation);
	
	public Objectif feedbackObjectif(Objectif objectif);
	
	public List<Objectif> getAssignedObjectifs(long idUser);
	
	
	/****************************************************************************************************************
	 * 
	 * FOR TESTING OBJECTIF PURPOSE
	 * 
	 *************************************************************************************************************/
	
	public Objectif addObjectif(Objectif objectif);
	public Objectif getObjectif(long id);
	public Objectif updateObjectif(Objectif objectif);
	public void deleteObjectif(Objectif objectif);
	public List<Objectif> getAllObjectifs();

}
