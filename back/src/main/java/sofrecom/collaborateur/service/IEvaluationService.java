package sofrecom.collaborateur.service;

import sofrecom.collaborateur.model.Evaluation;

public interface IEvaluationService {

	
	public Evaluation addEvaluationByUserAndCompetence(long idUser,long idCompetence);
}
