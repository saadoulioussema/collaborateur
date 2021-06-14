package sofrecom.collaborateur.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import sofrecom.collaborateur.model.Competence;
import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Evaluation;
import sofrecom.collaborateur.model.EvaluationPK;


public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {
	
	public Evaluation findByUserAndCompetence(DAOUser user ,Competence competence);
	
    public Evaluation findByEvaluationPK(EvaluationPK pk);
    
    @Query("select e from Evaluation e where e.evaluationPK.idUser=:idUser")
    public List<Evaluation> findEvaluationByUserId(@Param("idUser") long idUser); 
    

  	
}
