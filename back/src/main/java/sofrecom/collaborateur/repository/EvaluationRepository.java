package sofrecom.collaborateur.repository;


import org.springframework.data.repository.CrudRepository;

import sofrecom.collaborateur.model.Competence;
import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Evaluation;
import sofrecom.collaborateur.model.EvaluationPK;


public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {
	
	public Evaluation findByUserAndCompetence(DAOUser user ,Competence competence);
	
    public Evaluation findByEvaluationPK(EvaluationPK pk);
	
}
