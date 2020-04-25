package fudan.se.preferenceselectionsystem.repository;

import fudan.se.preferenceselectionsystem.domain.Major;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LBW
 */
@Repository
public interface MajorRepository extends CrudRepository<Major, Long> {
    public List<Major> findByDegreeType(String degreeType);
}
