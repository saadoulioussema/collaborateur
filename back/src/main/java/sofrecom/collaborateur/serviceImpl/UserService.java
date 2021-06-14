package sofrecom.collaborateur.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sofrecom.collaborateur.service.IUserService;
import sofrecom.collaborateur.model.Compagne;
import sofrecom.collaborateur.model.DAOUser;
import sofrecom.collaborateur.model.DTOUser;
import sofrecom.collaborateur.model.Entretien;
import sofrecom.collaborateur.repository.CompagneRepository;
import sofrecom.collaborateur.repository.EntretienRepository;
import sofrecom.collaborateur.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserService implements IUserService {

	@Autowired
	CompagneService compagneService;

	@Autowired
	UserRepository userRepository;
	@Autowired
	CompagneRepository compagneRepo;
	@Autowired
	EntretienRepository entretienRepo;


	@Override
	public DAOUser getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public DAOUser getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public DAOUser getUserById(long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public List<DAOUser> getAllUsers() {
		return (List<DAOUser>) userRepository.findAll();
	}

	@Override
	public void deleteAllUsers() {
		userRepository.deleteAll(getAllUsers());
	}

	@Override
	public List<DTOUser> getUsersByManagerAndCompagne(long idManager) {
		DAOUser manager = userRepository.findById(idManager).get();
		List<DTOUser> listCollabs = new ArrayList<>();

		String key = compagneService.getSemesterAndYear();
		Compagne compagne = compagneRepo.findByIdCompagne(key);
		List<Entretien> eipsEnCours = entretienRepo.findByCompagne(compagne);

		for (Entretien e : eipsEnCours) {
			if (manager == e.getUser().getManager()) {
				DTOUser collab = new DTOUser();
				collab.setId(e.getUser().getId());
				collab.setEmail(e.getUser().getEmail());
				collab.setMatricule(e.getUser().getMatricule());
				collab.setFullname(e.getUser().getFullname());
				collab.setEntretienId(e.getId());

				listCollabs.add(collab);
			}
		}
		return listCollabs;
	}
}