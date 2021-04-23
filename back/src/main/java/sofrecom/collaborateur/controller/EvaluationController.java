package sofrecom.collaborateur.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sofrecom.collaborateur.model.Evaluation;
import sofrecom.collaborateur.service.IEvaluationService;

@RestController
public class EvaluationController {

	@Autowired
	IEvaluationService evaluationService;
	
	@PostMapping("newEvaluation/{idUser}/{idCompetence}")
	public Evaluation addEvaluationByUserAndCompetence(@PathVariable("idUser") long idUser,@PathVariable("idCompetence") long idCompetence) {
		return evaluationService.addEvaluationByUserAndCompetence(idUser,idCompetence);
	}				
}
