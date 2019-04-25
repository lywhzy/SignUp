package hbue.it.service;

import hbue.it.pojo.Contest;

import java.util.List;

/**
 * @author lyw
 *
 */

public interface MyContestService {
    List<Contest> listContestByid(int uid,int start);
    void deleteContest(int uid,int cid);
    void update(int uid,int cid);
}
