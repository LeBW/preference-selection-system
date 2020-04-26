package fudan.se.preferenceselectionsystem.repository;

import fudan.se.preferenceselectionsystem.domain.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LBW
 */
@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    Student findByTicketNumber(String ticketNumber);

    Integer countByDegreeTypeEqualsAndFirstChoiceMajorEquals(String degreeType, String firstChoiceMajor);
}
