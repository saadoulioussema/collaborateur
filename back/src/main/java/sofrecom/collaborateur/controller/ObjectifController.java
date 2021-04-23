package sofrecom.collaborateur.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javassist.tools.rmi.ObjectNotFoundException;
import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.service.IObjectifService;

@RestController
public class ObjectifController {
	@Autowired
	IObjectifService objectifService;

	
	@GetMapping("entretien/{id}/objectifs")
	public List<Objectif> getObjectifListByEntretienAndCompagne(@PathVariable("id") long id) {
		return 	objectifService.getObjectifListByEntretienAndCompagne(id);

	}
		
	@PutMapping("autoEvaluateObjectif")
	@ResponseBody
	public void autoEvaluateObjectif(@RequestBody Objectif objectif) {
		objectifService.autoEvaluateObjectif(objectif);
	}
	
	
	@PutMapping("evaluateObjectif")
	@ResponseBody
	public void evaluateObjectif(@RequestBody Objectif objectif) {
		objectifService.evaluateObjectif(objectif);
	}
	
	//A Verifier ObjectNotFoundException import 
	@PostMapping("newObjectif/{id}")
	@ResponseBody
	public void newObjectif(@PathVariable("id") long id, @RequestBody Objectif objectif) throws ObjectNotFoundException {
		objectifService.newObjectif(objectif,id);
	}
	
	@DeleteMapping("deleteObjectif/{idCollaborateur}/{designation}")
	public void deleteObjectif(@PathVariable("idCollaborateur") long id, @PathVariable("designation") String designation) {
		 objectifService.deleteObjectif(id,designation);
	}
}
