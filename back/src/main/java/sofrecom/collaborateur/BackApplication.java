package sofrecom.collaborateur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sofrecom.collaborateur.serviceImpl.CampagneService;

@SpringBootApplication
public class BackApplication {
	public static void main(String[] args) {
		CampagneService c = new CampagneService();
		SpringApplication.run(BackApplication.class, args);
		System.out.println("Previous Semester   " + c.getPreviousSemester());
		System.out.println("This Semester   " + c.getSemesterAndYear());
		System.out.println("Next Semester   " + c.getNextSemester());
	}

}
