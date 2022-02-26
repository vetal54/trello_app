package spd.trello.domian.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import springfox.documentation.spring.web.json.JsonSerializer;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Resource extends Domain {

    @Column(name = "create_by")
    String createBy;

    @Column(name = "update_by")
    String updateBy;

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a", timezone = "UTC")
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

//public class JsonDateSerializer extends JsonSerializer<Timestamp> {
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
//
//    @Override
//    public void serialize(Timestamp arg0, JsonGenerator arg1, SerializerProvider arg2)
//            throws IOException, JsonProcessingException {
//        String formattedDate = dateFormat.format(arg0);
//        arg1.writeString(formattedDate);
//
//    }
//}

