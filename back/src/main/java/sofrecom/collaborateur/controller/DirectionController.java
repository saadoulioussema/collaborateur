package sofrecom.collaborateur.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import sofrecom.collaborateur.model.Direction;
import sofrecom.collaborateur.service.IDirectionService;


@RestController
public class DirectionController {
	
	
	@Autowired
	IDirectionService directionService;
	
	
	
	@GetMapping("direction")
	public List<Direction> getDirections() {
		return directionService.getAllDirections();
	}

}
