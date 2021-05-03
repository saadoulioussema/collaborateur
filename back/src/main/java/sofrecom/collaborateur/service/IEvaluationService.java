package sofrecom.collaborateur.service;

import sofrecom.collaborateur.model.Evaluation;

public interface IEvaluationService {

	public Evaluation addEvaluation(Evaluation evaluation);
	public Evaluation updateEvaluationLevel(Evaluation evaluation);
	public Evaluation getEvaluationByUserAndCompetence(long idUser,long idCompetence);
	
	
	public Evaluation addEvaluationByUserAndCompetence(long idUser,long idCompetence);
}
