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

import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.service.IObjectifService;

@RestController
public class ObjectifController {
	@Autowired
	IObjectifService objectifService;

	
	@GetMapping("entretien/{id}/objectifs")
	public List<Objectif> getObjectifListByEntretienAndEntretienCompagne(@PathVariable("id") long id) {
		return 	objectifService.getObjectifListByEntretienAndEntretienCompagne(id);

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

	@PostMapping("newObjectifs/{newEntretienId}/{idEntretien}")
	@ResponseBody
	public void newObjectif(@RequestBody List<Objectif> objectifs ,@PathVariable("newEntretienId") long newEntretienId,@PathVariable("idEntretien") long idEntretien){
		objectifService.newObjectif(objectifs,newEntretienId,idEntretien);
	}
	
	@PutMapping("newOtherObjectifs/{idUser}")
	@ResponseBody
	public void addOtherObjectifsByUserAndCompagne(@RequestBody List<Objectif> objectifs ,@PathVariable("idUser") long idUser){
		objectifService.addOtherObjectifsByUserAndCompagne(objectifs,idUser);
	}
		
	@DeleteMapping("deleteNewObjectif/{idUser}/{designation}")
	public void deleteNewObjectif(@PathVariable("idUser") long idUser, @PathVariable("designation") String designation) {
		 objectifService.deleteNewObjectif(idUser,designation);
	}
}
