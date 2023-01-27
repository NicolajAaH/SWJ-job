package dk.sdu.mmmi.jobservice.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private Job job;

    @Column(name = "created_at", columnDefinition = "timestamp default now()", insertable = false, updatable = false, nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp default now()", insertable = false, updatable = false, nullable = false)
    private Date updatedAt;
}
