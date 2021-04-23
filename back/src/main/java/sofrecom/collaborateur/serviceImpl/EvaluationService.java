package sofrecom.collaborateur.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sofrecom.collaborateur.model.Evaluation;
import sofrecom.collaborateur.model.EvaluationPK;
import sofrecom.collaborateur.repository.EvaluationRepository;
import sofrecom.collaborateur.service.IEvaluationService;

@Service
public class EvaluationService implements IEvaluationService {
		
	@Autowired
	EvaluationRepository evalRepo;

	@Override
	public Evaluation addEvaluationByUserAndCompetence(long idUser, long idCompetence) {
		EvaluationPK evalPK = new EvaluationPK();
		evalPK.setIdUser(idUser);
		evalPK.setIdCompetence(idCompetence);
		Evaluation eval = new Evaluation();
		eval.setEvaluationPK(evalPK);
		evalRepo.save(eval);
		return eval;
	}
}
