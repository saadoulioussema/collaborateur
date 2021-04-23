package sofrecom.collaborateur.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import sofrecom.collaborateur.model.Description;
import sofrecom.collaborateur.service.IDescriptionService;

@RestController
public class DescriptionController {

	@Autowired
	IDescriptionService descriptionService;
	
	
		
	@PostMapping("newDescription/{idEntretien}")
	public Description addEvaluationByUserAndCompetence(@PathVariable("idEntretien") long idEntretien,@RequestBody Description description) {
		return descriptionService.addDescription(idEntretien,description);
	}
	
	
	
			
	@PutMapping("updateDescriptionLevel/{idNiveau}")
	public Description updateDescriptionLevel(@PathVariable("idNiveau") long idNiveau,@RequestBody Description description) {
		return descriptionService.updateDescriptionLevel(idNiveau,description);
	}
	
	@GetMapping("descriptionList")
	public List<Description> getDescriptionList() {
		return  descriptionService.getDescriptionList();
	}
	
	@GetMapping("description/{idCompetence}/{idNiveau}")
	public Description getDescription(@PathVariable("idCompetence") long idCompetence,@PathVariable("idNiveau") long idNiveau) {
		return  descriptionService.getDescription(idCompetence,idNiveau);
	}
	
	
	
}
