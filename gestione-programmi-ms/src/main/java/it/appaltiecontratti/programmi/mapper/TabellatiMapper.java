package it.appaltiecontratti.programmi.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import it.appaltiecontratti.programmi.entity.validazione.TabellatoEntry;

/**
 * DAO Interface per l'estrazione delle informazioni relative ai tabellati mediante MyBatis.
 *
 * @author Michele.DiNapoli
 */
@Mapper
public interface TabellatiMapper {

	class PureSqlProvider {
        public String sql(String sql) {
            return sql;
        }
    }

	/**
	   * Estrae le righe da tab1 con tab1cod uguale al filtro .
	   *
	   * @param cod
	   *        codice da filtrare nella tab1
	   * @return elenco delle righe filtrate
	   */
	  @Select("SELECT tab1tip codice, tab1desc descrizione from tab1 where tab1cod= #{cod} order by tab1nord")
	  @Results({
	    @Result(property = "codice", column = "codice"),
	    @Result(property = "descrizione", column = "descrizione")
	  })
	  public List<TabellatoEntry> getTabellato1(@Param("cod") String cod);
	  
	  /**
	   * Estrae la riga da tab1 con tab1cod, tab1tip uguale al filtro .
	   *
	   * @param cod
	   *        codice da filtrare nella tab1
	   * @param valore
	   *        codice da filtrare nel tabellato
	   * @return elenco delle righe filtrate
	   */
	  @Select("SELECT tab1tip codice, tab1desc descrizione from tab1 where tab1cod= #{cod} and tab1tip=#{valore}")
	  @Results({
	    @Result(property = "codice", column = "codice"),
	    @Result(property = "descrizione", column = "descrizione")
	  })
	  public TabellatoEntry getValore1(@Param("cod") String cod, @Param("valore") Long valore);
	  
	  
   /**
   * Estrae le righe da tab3 con tab3cod uguale al filtro .
   *
   * @param cod
   *        codice da filtrare nella tab3
   * @return elenco delle righe filtrate
   */
  @Select("SELECT tab3tip codice, tab3desc descrizione from tab3 where tab3cod= #{cod} order by tab3nord")
  @Results({
    @Result(property = "codice", column = "codice"),
    @Result(property = "descrizione", column = "descrizione")
  })
  public List<TabellatoEntry> getTabellato3(@Param("cod") String cod);

  @SelectProvider(type = PureSqlProvider.class, method = "sql")
  public List<TabellatoEntry> getTabsche(String sql);
  
  /**
   * Estrae la riga da tabsche con tabcod, tabcod1 uguale al filtro .
   *
   * @param cod
   *        codice da filtrare nella tabsche
   * @param valore
   *        codice da filtrare nel tabellato
   * @return elenco delle righe filtrate
   */
  @Select("SELECT tabcod1 codice, tabdesc descrizione from tabsche where tabcod= #{cod} and tabcod1=#{valore}")
  @Results({
    @Result(property = "codice", column = "codice"),
    @Result(property = "descrizione", column = "descrizione")
  })
  public TabellatoEntry getValoreTabsche1(@Param("cod") String cod, @Param("valore") String valore);

  /**
   * Estrae la riga da tabsche con tabcod, tabcod2 uguale al filtro .
   *
   * @param cod
   *        codice da filtrare nella tabsche
   * @param valore
   *        codice da filtrare nel tabellato
   * @return elenco delle righe filtrate
   */
  @Select("SELECT tabcod1 || tabcod2 codice, tabdesc descrizione from tabsche where tabcod= #{cod} and tabcod1 || tabcod2 =#{valore}")
  @Results({
    @Result(property = "codice", column = "codice"),
    @Result(property = "descrizione", column = "descrizione")
  })
  public TabellatoEntry getValoreCategoriaDM112011(@Param("cod") String cod, @Param("valore") String valore);

  
  /**
   * Estrae la riga da tab3 con tab3cod, tab3tip uguale al filtro .
   *
   * @param cod
   *        codice da filtrare nella tab3
   * @param valore
   *        codice da filtrare nel tabellato
   * @return elenco delle righe filtrate
   */
  @Select("SELECT tab3tip codice, tab3desc descrizione from tab3 where tab3cod= #{cod} and tab3tip=#{valore}")
  @Results({
    @Result(property = "codice", column = "codice"),
    @Result(property = "descrizione", column = "descrizione")
  })
  public TabellatoEntry getValore3(@Param("cod") String cod, @Param("valore") String valore);

  
  /**
   * Estrae le righe da tab2 con tab2cod uguale al filtro .
   *
   * @param cod
   *        codice da filtrare nella tab2
   * @return elenco delle righe filtrate
   */
  @Select("SELECT tab2tip codice, tab2d2 descrizione from tab2 where tab2cod= #{cod} order by tab2nord")
  @Results({
    @Result(property = "codice", column = "codice"),
    @Result(property = "descrizione", column = "descrizione")
  })
  public List<TabellatoEntry> getTabellato2(@Param("cod") String cod);
  
  /**
   * Estrae la riga da tab2 con tab2cod, tab2tip uguale al filtro .
   *
   * @param cod
   *        codice da filtrare nella tab2
   * @param valore
   *        codice da filtrare nel tabellato
   * @return elenco delle righe filtrate
   */
  @Select("SELECT tab2tip codice, tab2d2 descrizione from tab2 where tab2cod= #{cod} and tab2tip=#{valore}")
  @Results({
    @Result(property = "codice", column = "codice"),
    @Result(property = "descrizione", column = "descrizione")
  })
  public TabellatoEntry getValore2(@Param("cod") String cod, @Param("valore") String valore);

  /**
   * Estrae le righe da tab5 con tab5cod uguale al filtro .
   *
   * @param cod
   *        codice da filtrare nella tab3
   * @return elenco delle righe filtrate
   */
  @Select("SELECT tab5tip codice, tab5desc descrizione from tab5 where tab5cod= #{cod} order by tab5nord")
  @Results({
    @Result(property = "codice", column = "codice"),
    @Result(property = "descrizione", column = "descrizione")
  })
  public List<TabellatoEntry> getTabellato5(@Param("cod") String cod);

  /**
   * Estrae la riga da tab5 con tab5cod, tab5tip uguale al filtro .
   *
   * @param cod
   *        codice da filtrare nella tab5
   * @param valore
   *        codice da filtrare nel tabellato
   * @return elenco delle righe filtrate
   */
  @Select("SELECT tab5tip codice, tab5desc descrizione from tab5 where tab5cod= #{cod} and tab5tip=#{valore}")
  @Results({
    @Result(property = "codice", column = "codice"),
    @Result(property = "descrizione", column = "descrizione")
  })
  public TabellatoEntry getValore5(@Param("cod") String cod, @Param("valore") String valore);

  

}
