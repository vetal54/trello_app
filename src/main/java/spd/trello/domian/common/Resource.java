package spd.trello.domian.common;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Resource extends Domain {

    @Column(name = "create_by")
    String createBy;

    @Column(name = "update_by")
    String updateBy;

    @CreatedDate
    @Column(updatable = false)
    Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());

    @LastModifiedDate
    Timestamp updateDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Resource resource = (Resource) o;
        return getId() != null && Objects.equals(getId(), resource.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
