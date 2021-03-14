package sofrecom.collaborateur.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Entretien;
import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.service.IEntretienService;

@RestController
public class EntretienController {
	
	@Autowired
	IEntretienService entretienService;
	

	@GetMapping("EIPs/{idManager}")
	public List<Entretien> getEIPsByManager(@PathVariable("idManager") long id) {
		return entretienService.getEIPsByManager(id);
	}
	
	@GetMapping("findCollaborateurByEntretien/{idEntretien}")
	public DAOUser getCollaborateurByEntretien(@PathVariable("idEntretien") long id) {
		return entretienService.getCollaborateurByEntretien(id);
	}
	
	@PutMapping("evaluateObjectif")
	@ResponseBody
	public void evaluateObjectif(@RequestBody Entretien entretien) {
		entretienService.evaluateObjectif(entretien);
	}

}
