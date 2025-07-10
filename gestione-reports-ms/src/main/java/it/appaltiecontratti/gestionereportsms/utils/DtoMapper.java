package it.appaltiecontratti.gestionereportsms.utils;

import it.appaltiecontratti.gestionereportsms.domain.StoUteSys;
import it.appaltiecontratti.gestionereportsms.domain.WConfig;
import it.appaltiecontratti.gestionereportsms.dto.StoUteSysDTO;
import it.appaltiecontratti.gestionereportsms.dto.WConfigDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public <Source, Destination> void addMapping(PropertyMap<Source, Destination> propertyMap) {
        modelMapper.addMappings(propertyMap);

        // aggiunta configurazioni WConfig
        modelMapper.typeMap(WConfig.class, WConfigDTO.class)
                .addMappings(m -> m.map(src -> src.getId().getCodApp(), WConfigDTO::setCodApp));
        modelMapper.typeMap(WConfig.class, WConfigDTO.class)
                .addMappings(m -> m.map(src -> src.getId().getChiave(), WConfigDTO::setChiave));

        // aggiunta configurazioni StoUteSys
        modelMapper.typeMap(StoUteSys.class, StoUteSysDTO.class)
                .addMappings(m -> m.map(src -> src.getId().getSysnom(), StoUteSysDTO::setSysnom));
        modelMapper.typeMap(StoUteSys.class, StoUteSysDTO.class)
                .addMappings(m -> m.map(src -> src.getId().getSyspwd(), StoUteSysDTO::setSyspwd));
    }

    public <Source, Target> Target convertTo(Source source, Class<Target> resultClass) {
        return modelMapper.map(source, resultClass);
    }

    public <Source, Target> Target convertTo(Source source, Type resultType) {
        return modelMapper.map(source, resultType);
    }
}
