package sofrecom.collaborateur.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import sofrecom.collaborateur.model.Niveau;
import sofrecom.collaborateur.service.INiveauService;

@RestController
public class NiveauController {

	@Autowired
	INiveauService niveaueService;
	
	
	
	@GetMapping("niveaux")
	public List<Niveau> getCompetenceListByUser() {
		return niveaueService.getNiveauList();
	}
}
