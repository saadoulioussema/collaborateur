package sofrecom.collaborateur.service;

import java.util.List;



import sofrecom.collaborateur.model.Objectif;

public interface IObjectifService {
	
	public List<Objectif> getObjectifListByEntretienAndEntretienCompagne(long id);
	public void autoEvaluateObjectif(Objectif objectif);
	public void evaluateObjectif(Objectif objectif);
	public void newObjectif(List<Objectif> objectifs,long newEntretienId,long idEntretien);
	public void addOtherObjectifsByUserAndCompagne(List<Objectif> objectifs ,long idUser);
	public void deleteNewObjectif(long idUser,String designation);
	
	
	/****************************************************************************************************************
	 * 
	 * FOR TESTING PURPOSE
	 * 
	 *************************************************************************************************************/
	
	public Objectif addObjectif(Objectif objectif);
	public Objectif updateObjectif(Objectif objectif);
	public void deleteObjectif(Objectif objectif);

}
