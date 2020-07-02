package fudan.se.preferenceselectionsystem.repository;

import fudan.se.preferenceselectionsystem.domain.Major;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LBW
 */
@Repository
public interface MajorRepository extends CrudRepository<Major, Long> {
    public List<Major> findByDegreeType(String degreeType);

    public Major findFirstByMajor(String major);

    public List<Major> findByMajor(String major);

    @Query("SELECT DISTINCT major.major FROM Major major WHERE major.degreeType = ?1")
    public List<String> findDistinctMajorsByDegreeType(String degreeType);

}
