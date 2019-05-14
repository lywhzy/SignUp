package hbue.it.service;

import hbue.it.pojo.Contest;

import java.util.List;

/**
 * @author lyw
 *
 */

public interface MyContestService {
    String SIGNING = "报名中";
    String SIGNED = "报名截止";
    String NotOpen = "暂未开放";

    List<Contest> listContestByid(int uid,int start);
    void deleteContest(int uid,int cid);
    void update(int uid,int cid);
}
