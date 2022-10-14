package spd.trello.domian.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Resource extends Domain {

    @CreatedBy
    @Column(name = "create_by")
    String createBy;

    @LastModifiedBy
    @Column(name = "update_by")
    String updateBy;

    @CreatedDate
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(updatable = false)
    LocalDateTime createDate;

    @LastModifiedDate
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "update_date")
    LocalDateTime updateDate;

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

