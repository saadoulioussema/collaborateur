package sofrecom.collaborateur.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import sofrecom.collaborateur.model.Competence;
import sofrecom.collaborateur.service.ICompetenceService;

@RestController
public class CompetenceController {

	@Autowired
	ICompetenceService competenceService;
		
	
	@GetMapping("competences")
	public List<Competence> getCompetenceList() {
		return competenceService.getCompetenceList();
	}
	
	
}
