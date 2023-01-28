package dk.sdu.mmmi.jobservice.service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Table(name = "applications")
@Getter
@Setter
@ToString
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "created_at")
    @ColumnDefault("now()")
    private Date createdAt;

    @Column(name = "updated_at")
    @ColumnDefault("now()")
    private Date updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    @JsonBackReference
    private Job job;

}
