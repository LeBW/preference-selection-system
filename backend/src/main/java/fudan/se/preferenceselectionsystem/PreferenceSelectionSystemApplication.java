package fudan.se.preferenceselectionsystem;

import fudan.se.preferenceselectionsystem.domain.Student;
import fudan.se.preferenceselectionsystem.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PreferenceSelectionSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreferenceSelectionSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(StudentRepository studentRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // create a student if not exists
                if (studentRepository.findByTicketNumber("123456") == null) {
                    Student student = new Student();
                    student.setTicketNumber("123456");
                    student.setName("李博文");
                    student.setIdNumber("421122199702250017");
                    student.setDepartment("软件学院");
                    student.setMajor("软件工程");
                    studentRepository.save(student);
                }

            }
        };
    }
}
