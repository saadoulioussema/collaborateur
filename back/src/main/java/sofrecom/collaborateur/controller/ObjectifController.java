package sofrecom.collaborateur.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.service.IObjectifService;

@RestController
public class ObjectifController {
	List<String> semestre = Arrays.asList("01", "02", "03", "04", "05", "06");
	@Autowired
	IObjectifService objectifService;

	
	@GetMapping("collaborateur/{id}/objectifs")
	public List<Objectif> getObjectifsList(@PathVariable("id") long id) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String year = formatter.format(date).substring(0, 4);
		String month = formatter.format(date).substring(5, 7);
		int yearConverted = Integer.parseInt(year);
		if (semestre.contains(month)) {
			String correctYear = String.valueOf(yearConverted - 1);
			String key = "S2" + correctYear;
			return objectifService.getAllObjectifs(key,id);

		} else {
			String key = "S1" + year;
			return objectifService.getAllObjectifs(key,id);
		}
	}
	
	@PutMapping("autoEvaluateObjectif")
	@ResponseBody
	public void autoEvaluateObjectif(@RequestBody Objectif objectif) {
		objectifService.autoEvaluateObjectif(objectif);
	}
	
	@GetMapping("objectifsForManager/{idCollaborateur}")
	public List<Objectif> getCollaborateurObjectifsForManager(@PathVariable("idCollaborateur") long id) {
		return objectifService.getCollaborateurObjectifsForManager(id);

	}

	
	

}
