package dk.sdu.mmmi.jobservice.inbound;

import dk.sdu.mmmi.jobservice.service.model.Application;
import dk.sdu.mmmi.jobservice.service.model.ApplicationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(target = "job", ignore = true)
    Application applicationDTOToApplication(ApplicationDTO applicationDTO);

    @Mapping(target = "jobId", source = "job.id")
    ApplicationDTO applicationToApplicationDTO(Application application);
}
