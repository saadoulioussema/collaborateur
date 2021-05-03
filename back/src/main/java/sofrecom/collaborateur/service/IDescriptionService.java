package sofrecom.collaborateur.service;


import java.util.List;

import sofrecom.collaborateur.model.Description;

public interface IDescriptionService {

	
	public Description addDescription(long idEntretien,Description description);	
	public Description updateDescriptionLevel(long idNiveau,Description description);
	public List<Description> getDescriptionList();
	public Description getDescription(long idCompetence,long idNiveau);
	public List<Description> getDescriptionByCompetence(long idCompetence);
}
