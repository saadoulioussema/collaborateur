package sofrecom.collaborateur.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.Entretien;
import sofrecom.collaborateur.repository.CompagneRepository;
import sofrecom.collaborateur.repository.EntretienRepository;
import sofrecom.collaborateur.repository.UserRepository;
import sofrecom.collaborateur.service.IEntretienService;

@Service
public class EntretienService implements IEntretienService {

	@Autowired
	EntretienRepository EntretienRepo;

	@Autowired
	UserRepository UserRepo;

	@Autowired
	CompagneRepository compagneRepo;

	List<String> semestre = Arrays.asList("01", "02", "03", "04", "05", "06");
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date(System.currentTimeMillis());
	String year = formatter.format(date).substring(0, 4);
	String month = formatter.format(date).substring(5, 7);

	

	@Override
	public Entretien addEntretien(Entretien entretien) {
		return EntretienRepo.save(entretien);
	}
	
	
	
	@Override
	public List<Entretien> getEIPsByManagerAndCompagne(long id) {
		List<Entretien> entretiens = new ArrayList<Entretien>();
		int yearConverted = Integer.parseInt(year);
		if (semestre.contains(month)) {
			String correctYear = String.valueOf(yearConverted - 1);
			String key = "S2" + correctYear;
			List<Entretien> entretiensByManager = (List<Entretien>) EntretienRepo.findEIPsByManager(id);
			for (Entretien entretien : entretiensByManager) {
				if (entretien.getCompagne().getIdCompagne().equals(key)) {
					entretiens.add(entretien);
				}
			}
			return entretiens;
		} else {
			String key = "S1" + year;
			List<Entretien> entretiensByManager = (List<Entretien>) EntretienRepo.findEIPsByManager(id);
			for (Entretien entretien : entretiensByManager) {
				if (entretien.getCompagne().getIdCompagne().equals(key)) {
					entretiens.add(entretien);
				}
			}
			return entretiens;
		}
	}

	@Override
	public DAOUser getCollaborateurByEntretien(long id) {
		return EntretienRepo.findCollaborateurByEntretien(id);
	}

	@Override
	public Entretien getEntretienByCollaborateurAndCompagne(long id) {
		int yearConverted = Integer.parseInt(year);
		if (semestre.contains(month)) {
			String correctYear = String.valueOf(yearConverted - 1);
			String key = "S2" + correctYear;
			return EntretienRepo.findByUserAndCompagne(UserRepo.findById(id).get(), compagneRepo.findByIdCompagne(key));
		} else {
			String key = "S1" + year;
			return EntretienRepo.findByUserAndCompagne(UserRepo.findById(id).get(), compagneRepo.findByIdCompagne(key));
		}
	}

}
