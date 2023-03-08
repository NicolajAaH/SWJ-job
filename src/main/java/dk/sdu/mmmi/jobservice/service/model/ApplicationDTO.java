package dk.sdu.mmmi.jobservice.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ApplicationDTO {

    private Long id;

    private String userId;

    private ApplicationStatus status;

    private Date createdAt;

    private Date updatedAt;

    private Long jobId;

    private String application;

}
