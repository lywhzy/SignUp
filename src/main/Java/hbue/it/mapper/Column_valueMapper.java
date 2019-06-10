package hbue.it.mapper;

import hbue.it.pojo.Column_value;
import hbue.it.pojo.Column_valueExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Column_valueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Column_value record);

    int insertSelective(Column_value record);

    List<Column_value> selectByExample(Column_valueExample example);

    Column_value selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Column_value record);

    int updateByPrimaryKey(Column_value record);

    void deleteByUCId(@Param("uid") int uid, @Param("cid") int cid);

    void updateByUCId(Column_value column_value);

    @Delete("delete from column_value where uid=#{uid} and cid=#{cid}")
    void deleteColumn_value(Column_value column_value);
}