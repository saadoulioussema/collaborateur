package sofrecom.collaborateur.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sofrecom.collaborateur.model.Evaluation;
import sofrecom.collaborateur.service.IEvaluationService;

@RestController
public class EvaluationController {

	@Autowired
	IEvaluationService evaluationService;
	
	
	// CHANGED ! 
	@PostMapping("newEvaluation/{idUser}/{idCompetence}")
	public Evaluation addEvaluationByUserAndCompetence(@PathVariable("idUser") long idUser,@PathVariable("idCompetence") long idCompetence) {
		return evaluationService.addEvaluationByUserAndCompetence(idUser,idCompetence);
	}	
	
	
	@PostMapping("newEvaluation")
	public Evaluation addEvaluation(@RequestBody Evaluation evaluation) {
		return evaluationService.addEvaluation(evaluation);
	}	
	
	@PutMapping("updateEvaluationLevel")
	public Evaluation updateEvaluationLevel(@RequestBody Evaluation evaluation) {
		return evaluationService.updateEvaluationLevel(evaluation);
	}
	
	
	@GetMapping("evaluation/{idUser}/{idCompetence}")
	public Evaluation getEvaluationByUserAndCompetence(@PathVariable("idUser") long idUser,@PathVariable("idCompetence") long idCompetence) {
		return  evaluationService.getEvaluationByUserAndCompetence(idUser,idCompetence);
	}
	
	
}
