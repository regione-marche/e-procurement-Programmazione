package it.maggioli.ssointegrms.utils;

import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.maggioli.ssointegrms.domain.StoUteSys;
import it.maggioli.ssointegrms.domain.WConfig;
import it.maggioli.ssointegrms.dto.StoUteSysDTO;
import it.maggioli.ssointegrms.dto.WConfigDTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
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
