package sofrecom.collaborateur.repository;

import org.springframework.data.repository.CrudRepository;
import sofrecom.collaborateur.model.Evaluation;


public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {
	
}
