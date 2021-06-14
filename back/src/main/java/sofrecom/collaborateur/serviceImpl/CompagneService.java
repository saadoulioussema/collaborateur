package sofrecom.collaborateur.serviceImpl;

import java.util.Calendar;


import org.springframework.stereotype.Service;


@Service
public class CompagneService {


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
		if (month > 6)
			semester = "S2";
		return semester;
	}

	public String getPreviousSemesterAndYear() {
		int year = getCurrentYear();
		String semester = getSemester();
		if (getSemester().equals("S1")) {
			semester = "S2";
			year--;
			return semester + year ;
		}
		else {
			semester = "S1";
			return semester+ year;
		}
	}
	
	public String getSemesterAndYear() {
		int year = getCurrentYear();
		String semester = getSemester();
			return semester+ year;
	}
	
}
