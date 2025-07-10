package it.appaltiecontratti.tabellati.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface SqlMapper {
	
    class PureSqlProvider {
        public String sql(String sql) {
            return sql;
        }

        public String sqlObject(String sql) {
            return sql;
        }
        
        public String count(String from) {
            return "SELECT count(*) FROM " + from;
        }
    }
    
    @SelectProvider(type = PureSqlProvider.class, method = "sql")
    public List<Map<String,Object>> select(String sql);

    @SelectProvider(type = PureSqlProvider.class, method = "count")
    public Integer count(String from);

    @SelectProvider(type = PureSqlProvider.class, method = "sql")
    public Integer execute(String query);

    @SelectProvider(type = PureSqlProvider.class, method = "sqlObject")
    public String executeReturnString(String query);
}