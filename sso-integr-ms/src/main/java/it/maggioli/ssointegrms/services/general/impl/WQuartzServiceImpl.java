package it.maggioli.ssointegrms.services.general.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.maggioli.ssointegrms.domain.WQuartz;
import it.maggioli.ssointegrms.domain.WQuartzPK;
import it.maggioli.ssointegrms.dto.WQuartzDTO;
import it.maggioli.ssointegrms.dto.WQuartzEditDTO;
import it.maggioli.ssointegrms.exceptions.amministrazione.QuartzNotFoundException;
import it.maggioli.ssointegrms.repositories.WQuartzRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.WQuartzService;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Service
public class WQuartzServiceImpl extends BaseService implements WQuartzService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WQuartzServiceImpl.class);

	@Autowired
	private WQuartzRepository wQuartzRepository;

	@Override
	public List<WQuartzDTO> listaPianificazioni() {

		LOGGER.debug("Execution start WQuartzServiceImpl::listaPianificazioni");

		List<WQuartz> listaQuartz = wQuartzRepository.findAll(Sort.by(Sort.Direction.ASC, "id.beanId"));
		List<WQuartzDTO> listaDTO = listaQuartz.stream().map(q -> getWQuartzDTOFromWQuartz(q))
				.collect(Collectors.toList());

		return listaDTO;
	}

	@Override
	public WQuartzDTO getPianificazione(final String codApp, final String beanId) {

		LOGGER.debug("Execution start WQuartzServiceImpl::getPianificazione for codApp [ {} ] and beanId [ {} ]",
				codApp, beanId);

		if (StringUtils.isBlank(codApp))
			throw new IllegalArgumentException("codApp null");

		if (StringUtils.isBlank(beanId))
			throw new IllegalArgumentException("beanId null");

		WQuartzPK id = new WQuartzPK(codApp, beanId);

		WQuartz found = wQuartzRepository.findById(id).orElse(null);

		if (found == null) {
			LOGGER.error("Pianificazione non presente per codApp [ {} ] e chiave [ {} ]", codApp, beanId);

			throw new QuartzNotFoundException();
		}

		return getWQuartzDTOFromWQuartz(found);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public WQuartzDTO updatePianificazione(final WQuartzEditDTO form) {

		if (form == null)
			throw new IllegalArgumentException("form null");

		LOGGER.debug("Execution start WQuartzServiceImpl::updatePianificazione for form [ {} ]", form);

		WQuartzPK id = new WQuartzPK(form.getCodApp(), form.getBeanId());

		WQuartz toUpdate = wQuartzRepository.findById(id).orElse(null);

		if (toUpdate == null) {
			LOGGER.error("Pianificazione non presente per codApp [ {} ] e chiave [ {} ]", form.getCodApp(),
					form.getBeanId());

			throw new QuartzNotFoundException();
		}

		String seconds = form.getSeconds();
		String minutes = form.getMinutes();
		String hours = form.getHours();
		String dayOfMonth = form.getDayOfMonth();
		String month = form.getMonth();
		String dayOfWeek = form.getDayOfWeek();
		String year = form.getYear();

		String expression = "";
		expression += (seconds != null ? seconds.trim() : "");
		expression += " " + (minutes != null ? minutes.trim() : "");
		expression += " " + (hours != null ? hours.trim() : "");
		expression += " " + (dayOfMonth != null ? dayOfMonth.trim() : "");
		expression += " " + (month != null ? month.trim() : "");
		expression += " " + (dayOfWeek != null ? dayOfWeek.trim() : "");
		expression += " " + (year != null ? year.trim() : "");
		expression = expression.trim();

		toUpdate.setSeconds(seconds);
		toUpdate.setMinutes(minutes);
		toUpdate.setHours(hours);
		toUpdate.setDayOfMonth(dayOfMonth);
		toUpdate.setMonth(month);
		toUpdate.setDayOfWeek(dayOfWeek);
		toUpdate.setYear(year);
		toUpdate.setExpression(expression);

		toUpdate = wQuartzRepository.save(toUpdate);

		return getWQuartzDTOFromWQuartz(toUpdate);
	}

	private WQuartzDTO getWQuartzDTOFromWQuartz(final WQuartz quartz) {

		WQuartzDTO dto = new WQuartzDTO();

		dto.setCodApp(quartz.getId().getCodApp());
		dto.setBeanId(quartz.getId().getBeanId());
		dto.setExpression(quartz.getExpression());
		dto.setSeconds(quartz.getSeconds());
		dto.setMinutes(quartz.getMinutes());
		dto.setHours(quartz.getHours());
		dto.setDayOfMonth(quartz.getDayOfMonth());
		dto.setMonth(quartz.getMonth());
		dto.setDayOfWeek(quartz.getDayOfWeek());
		dto.setYear(quartz.getYear());
		dto.setDescrizione(quartz.getDescrizione());

		return dto;
	}
}
