package hbue.it.service.Facade;

import hbue.it.pojo.Contest;
import hbue.it.pojo.User;

import java.util.List;

/**
 * @author lyw
 *
 */

public interface ContestFacadeService {
    String SIGNING = "报名中";
    String SIGNED = "报名截止";
    String NotOpen = "暂未开放";

    //根据用户id获取当前报名比赛
    List<Contest> listContestByid(int uid,int start);

    //取消报名
    void deleteContest(int uid,int cid);


    void update(int uid,int cid);

    List<Contest> listContests(User user,int start);

    //判断当前用户是否报名该比赛 并返回状态 0：报名 ，1：修改
    int judgeUCStatus(int uid,int cid,int status);
}
