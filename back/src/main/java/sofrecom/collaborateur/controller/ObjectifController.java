package sofrecom.collaborateur.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import sofrecom.collaborateur.model.Objectif;
import sofrecom.collaborateur.service.IObjectifService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class ObjectifController {
	List<String> semestre = Arrays.asList("01","02","03" ,"04","05","06");
	@Autowired
	IObjectifService objectifService;
	
	
	@GetMapping("collaborateur/objectif")
	public List<Objectif> getObjectifsList() {
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String year = formatter.format(date).substring(0,4);
		String month = formatter.format(date).substring(5,7);
		int yearConverted = Integer.parseInt(year);
		if (semestre.contains(month)) {
			String correctYear = String.valueOf(yearConverted-1);
			String key = "S2"+correctYear;
			return objectifService.getAllObjectifs(key);
			
		}
		else {
			String key = "S1"+year;
			return objectifService.getAllObjectifs(key);
		}
	}

}
