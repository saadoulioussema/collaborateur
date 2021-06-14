package sofrecom.collaborateur.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Entretien;
import sofrecom.collaborateur.service.IEntretienService;

@RestController
public class EntretienController {
	
	@Autowired
	IEntretienService entretienService;
	
	
	@PostMapping("newEntretien")
	@ResponseBody
	public Entretien addEntretien(@RequestBody  Entretien entretien) {
		return entretienService.addEntretien(entretien);
	}
	@GetMapping("EIPs/{idManager}")
	public List<Entretien> getEIPsByManagerAndCompagne(@PathVariable("idManager") long id) {
		return entretienService.getEIPsByManagerAndCompagne(id);
	}
	
	@GetMapping("findCollaborateurByEntretien/{idEntretien}")
	public DAOUser getCollaborateurByEntretien(@PathVariable("idEntretien") long id) {
		return entretienService.getCollaborateurByEntretien(id);
	}
	
	@GetMapping("findEntretienByCollaborateur/{idCollaborateur}")
	public Entretien getEntretienByCollaborateurAndCompagne(@PathVariable("idCollaborateur") long id) {
		return entretienService.getEntretienByCollaborateurAndCompagne(id);
	}
	
	@PutMapping("saveProjectAndFormation/{projet}/{formation}/{idEntretien}")
	@ResponseBody
	public Entretien saveProjectAndFormation(@PathVariable("projet") String projet ,@PathVariable("formation") String formation,@PathVariable("idEntretien") long idEntretien) {
		return entretienService.saveProjectAndFormation(projet,formation,idEntretien);
	}	
	
	@PutMapping("closeEntretien")
	@ResponseBody
	public Entretien closeEntretien(@RequestBody Entretien entretien) {
		return entretienService.closeEntretien(entretien);
	}
}
