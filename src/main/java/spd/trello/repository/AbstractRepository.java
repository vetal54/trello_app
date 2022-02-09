package spd.trello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import spd.trello.entity.common.Resource;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface AbstractRepository<E extends Resource> extends JpaRepository<E, UUID>,
        PagingAndSortingRepository<E, UUID>,
        JpaSpecificationExecutor<E> {
}
