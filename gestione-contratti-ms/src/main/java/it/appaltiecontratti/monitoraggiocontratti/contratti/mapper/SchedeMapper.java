package it.appaltiecontratti.monitoraggiocontratti.contratti.mapper;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.AttoDocument;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CPVSecondario;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CategoriaLotto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CondizioneAtto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.DatiIdSchedeW9FasiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.*;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ChiaveConfigurazione;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface SchedeMapper {


	@Select(" select fl.xml, f.id_scheda_simog as idScheda, fl.key01 as codGara, fl.key02 as codLotto, fl.key04 as num, fl.key03 as faseEsecuzione from w9flussi fl left join w9fasi f on " +
			" fl.key01 = f.codgara and fl.key02 = f.codlott and fl.key03 = f.fase_esecuzione and fl.key04 = f.num" +
			" where fl.key01 = #{codGara} and fl.key02= #{codLotto} and fl.key03 = #{tipoScheda} and f.num_appa = #{numAppa} and fl.tinvio2 = 3 and f.daexport = '2'")
	public List<FlussoJsonSchedaEntry> getFlussoJsonScheda(@Param("codGara") Long codGara,
											@Param("codLotto") Long codLotto,
											@Param("tipoScheda") Long tipoScheda,
											@Param("numAppa") Long numAppa);

	@Select(" select distinct l.cig from w9flussi wf join w9lott l on wf.key01 = l.codgara and wf.key02 = l.codlott where tinvio2 = 3 and l.cig is not null ")
	public List<String> getListaCig();

	@Select(" select distinct l.cig from w9flussi wf join w9lott l on wf.key01 = l.codgara and wf.key02 = l.codlott where tinvio2 = 3  and l.cig is not null and wf.datinv BETWEEN #{dataDa} AND #{dataA} ")
	public List<String> getListaCigDataFilter(@Param("dataDa") Date dataDa, @Param("dataA") Date dataA);



}
