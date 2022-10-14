package spd.trello.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spd.trello.domian.Attachment;

import java.util.UUID;

@Repository
public interface AttachmentRepository extends AbstractRepository<Attachment> {

    @Query("Select link from Attachment  Where id in :ids")
    String getLinkById(@Param("ids") UUID id);
}
