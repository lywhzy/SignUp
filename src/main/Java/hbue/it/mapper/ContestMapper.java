package hbue.it.mapper;

import hbue.it.pojo.Contest;
import hbue.it.pojo.ContestExample;
import hbue.it.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ContestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Contest record);

    int insertSelective(Contest record);

    List<Contest> selectByExample(ContestExample example);

    Contest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Contest record);

    int updateByPrimaryKey(Contest record);

    List<Contest> selectByUserId(int uid);

    void deleteContestRelation(@Param("cid") int cid, @Param("uid") int uid);

    List<Contest> getTopCharacterization();

    @Insert("insert into user_contest(cid,uid) value(#{cid},#{uid})")
    void insertRelation(@Param("uid") int uid, @Param("cid") int cid);

    @Select(value = "select id from user_contest where cid=#{cid} and uid=#{uid}")
    Integer selectRelation(@Param("uid") int uid,@Param("cid") int cid);



}