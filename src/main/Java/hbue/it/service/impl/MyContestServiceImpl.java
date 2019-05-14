package hbue.it.service.impl;

import hbue.it.exception.ContestNotFoundException;
import hbue.it.exception.UserNotFoundException;
import hbue.it.mapper.Column_valueMapper;
import hbue.it.mapper.ContestMapper;
import hbue.it.mapper.UserMapper;
import hbue.it.pojo.Contest;
import hbue.it.pojo.User;
import hbue.it.service.MyContestService;
import hbue.it.util.PageUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MyContestServiceImpl implements MyContestService {

    @Autowired
    private Column_valueMapper column_valueMapper;
    @Autowired
    private ContestMapper contestMapper;
    @Autowired
    private UserMapper userMapper;

    static Logger logger = Logger.getLogger(MyContestServiceImpl.class);

    @Override
    public List<Contest> listContestByid(int uid, int start) {
        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            logger.info("该用户不存在");
            throw new UserNotFoundException();
        }
        PageUtil.setPage(start);
        List<Contest> list = contestMapper.selectByUserId(uid);
        setContestStatus(list);
        return list;
    }

    @Override
    public void deleteContest(int uid, int cid) {
        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            logger.info("该用户不存在");
            throw new UserNotFoundException();
        }
        Contest contest = contestMapper.selectByPrimaryKey(cid);
        if (contest == null) {
            logger.info("该比赛不存在");
            throw new ContestNotFoundException();
        }

        //修改报名信息的可见状态，目前通过删除解决
        column_valueMapper.deleteByUCId(uid,cid);

        contestMapper.deleteContestRelation(cid, uid);
    }

    @Override
    public void update(int uid, int cid) {

    }

    private void setContestStatus(List<Contest> list){
        for(Contest contest : list){
            if(new Date().before(contest.getBegindate())){
                contest.setStatus(this.NotOpen);
            }else if(new Date().before(contest.getEnddate())){
                contest.setStatus(this.SIGNING);
            }else{
                contest.setStatus(this.SIGNED);
            }
        }
    }
}