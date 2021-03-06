package sofrecom.collaborateur.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sofrecom.collaborateur.model.Direction;
import sofrecom.collaborateur.repository.DirectionRepository;
import sofrecom.collaborateur.service.IDirectionService;

@Service
public class DirectionService implements IDirectionService{
	
	
	@Autowired
	DirectionRepository DirectionRepo;
	
	
	@Override
	public List<Direction> getAllDirections() {
		return (List<Direction>)DirectionRepo.findAll();
	}

}
