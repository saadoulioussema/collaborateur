package sofrecom.collaborateur.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sofrecom.collaborateur.model.Niveau;
import sofrecom.collaborateur.repository.NiveauRepository;
import sofrecom.collaborateur.service.INiveauService;

@Service
public class NiveauService implements INiveauService{
	
	@Autowired
	NiveauRepository niveauRepo;

	@Override
	public List<Niveau> getNiveauList() {
		return (List<Niveau>) niveauRepo.findAll();
	}

}
