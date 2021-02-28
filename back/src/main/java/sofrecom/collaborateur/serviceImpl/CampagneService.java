package sofrecom.collaborateur.serviceImpl;

import java.util.Calendar;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import sofrecom.collaborateur.model.Campagne;
import sofrecom.collaborateur.repository.CampagneRepository;

@Service
public class CampagneService {

	@Autowired
	private CampagneRepository campagneRepository;

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			campagneRepository.updateActifStatus();
			campagneRepository.save(new Campagne(getSemesterAndYear(), true));
		};
	}

	public int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	public int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	public String getSemester() {
		String semester = "S1";
		int month = getCurrentMonth();
		if (month >= 6)
			semester = "S2";
		return semester;
	}

	public String getSemesterAndYear() {

		String semester = getSemester();
		int year = getCurrentYear();

		return semester + "-" + year;
	}

	public String getPreviousSemester() {
		String semester = getSemester();
		int year = getCurrentYear();
		if (semester == "S2")
			semester = "S1";
		if (semester == "S1") {
			semester = "S2";
			year = year - 1;
		}
		return semester + "-" + year;
	}

	public String getNextSemester() {
		String semester = getSemester();
		int year = getCurrentYear();
		if (semester == "S2") {
			semester = "S1";
			year = year + 1;
		}
		if (semester == "S1") {
			semester = "S2";

		}
		return semester + "-" + year;
	}
}
