package fudan.se.preferenceselectionsystem.repository;

import fudan.se.preferenceselectionsystem.domain.Attachment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author LBW
 */
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {
    @Query("SELECT new Attachment(e.id, e.name) from Attachment e")
    public List<Attachment> getIdAndNameOnly();
}
