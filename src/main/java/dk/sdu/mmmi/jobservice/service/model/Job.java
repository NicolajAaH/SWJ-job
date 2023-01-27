package dk.sdu.mmmi.jobservice.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @Column(name = "salary", columnDefinition = "numeric(10,2)")
    private Double salary;

    @Column(name = "created_at", columnDefinition = "timestamp default now()", insertable = false, updatable = false, nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp default now()", insertable = false, updatable = false, nullable = false)
    private Date updatedAt;

    @Column(name = "expires_at", columnDefinition = "timestamp default now()+INTERVAL '30 days'", insertable = false, updatable = false, nullable = false)
    private Date expiresAt;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Application> applications;
}