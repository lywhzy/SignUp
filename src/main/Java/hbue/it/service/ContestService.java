package hbue.it.service;

import hbue.it.pojo.Contest;

import java.util.List;

public interface ContestService extends BaseService<Contest>{
    List<Contest> listByUId(int uid);
    List<Contest> getTopCharacterization();
    void insertRelation(int uid,int cid);
    boolean isRelationExist(int uid,int cid);

}
