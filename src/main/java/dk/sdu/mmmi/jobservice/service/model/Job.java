package dk.sdu.mmmi.jobservice.service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@ToString
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "job_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "salary", columnDefinition = "numeric(10,2)")
    private Double salary;

    @Column(name = "created_at")
    @ColumnDefault("now()")
    private Date createdAt;

    @Column(name = "updated_at")
    @ColumnDefault("now()")
    private Date updatedAt;

    @Column(name = "expires_at", nullable = false)
    private Date expiresAt;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private Set<Application> applications;
}