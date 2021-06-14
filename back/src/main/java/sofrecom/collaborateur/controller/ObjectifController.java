package sofrecom.collaborateur.controller;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.service.IObjectifService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
public class ObjectifController {
	
	
	@Autowired
	IObjectifService objectifService;

	@GetMapping("entretien/{id}/objectifs")
	public List<Objectif> getObjectifListByEntretienAndEntretienCompagne(@PathVariable("id") long id) {
		return objectifService.getObjectifListByEntretienAndEntretienCompagne(id);
	}
	
	@GetMapping("currentEntretien/{id}/objectifs")
	public List<Objectif> getObjectifListByCurrentEntretienAndEntretienCompagne(@PathVariable("id") long id) {
		return objectifService.getObjectifListByCurrentEntretienAndEntretienCompagne(id);

	}

	@PutMapping("autoEvaluateObjectif")
	@ResponseBody
	public Objectif autoEvaluateObjectif(@RequestBody Objectif objectif) {
		return objectifService.autoEvaluateObjectif(objectif);
	}

	@PutMapping("evaluateObjectif")
	@ResponseBody
	public Objectif evaluateObjectif(@RequestBody Objectif objectif) {
		return objectifService.evaluateObjectif(objectif);
	}

	@PostMapping("newObjectifs/{newEntretienId}/{idEntretien}")
	@ResponseBody
	public void newObjectif(@RequestBody List<Objectif> objectifs, @PathVariable("newEntretienId") long newEntretienId,
			@PathVariable("idEntretien") long idEntretien) {
		objectifService.newObjectif(objectifs, newEntretienId, idEntretien);
	}

	@PutMapping("newOtherObjectifs/{idUser}")
	@ResponseBody
	public void addOtherObjectifsByUserAndCompagne(@RequestBody List<Objectif> objectifs,
			@PathVariable("idUser") long idUser) {
		objectifService.addOtherObjectifsByUserAndCompagne(objectifs, idUser);
	}

	@DeleteMapping("deleteNewObjectif/{idUser}/{designation}")
	public List<Objectif> deleteNewObjectif(@PathVariable("idUser") long idUser,
			@PathVariable("designation") String designation) {
		return objectifService.deleteNewObjectif(idUser, designation);
	}
	
	@PutMapping("feedbackObjectif")
	@ResponseBody
	public Objectif feedbackObjectif(@RequestBody Objectif objectif) {
		return objectifService.feedbackObjectif(objectif);
	}
	
	
	@GetMapping("assignedObjectifs/{idUser}")
	public List<Objectif> getAssignedObjectifs(@PathVariable("idUser") long idUser) {
		return objectifService.getAssignedObjectifs(idUser);

	}
	
	
	

	/****************************************************************************************************************
	 * 
	 * FOR TESTING PURPOSE
	 * 
	 *************************************************************************************************************/

	
	@PostMapping("/addObjectif")
	public Objectif addObjectif(@RequestBody Objectif newobjectif) {
		return objectifService.addObjectif(newobjectif);

	}
	
	@PostMapping("/addObjectif2")
	public ResponseEntity<Objectif> addObjectif2(@RequestBody Objectif newobjectif) {
		Objectif objectif = objectifService.addObjectif(newobjectif);
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objectif.getId()).toUri();
		if (objectif == null)
			return ResponseEntity.noContent().build();
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
		return ResponseEntity.created(location).contentType(MediaType.APPLICATION_JSON).build();
	}
	


	@GetMapping("getObjectif/{id}")
	public Objectif getObjectif(@PathVariable("id") long id) {
		return objectifService.getObjectif(id);

	}

	@GetMapping("getAllObjectifs")
	public List<Objectif> getAllObjectifs() {
		return objectifService.getAllObjectifs();

	}

}
