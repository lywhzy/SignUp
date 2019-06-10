package hbue.it.service.Facade.impl;

import com.github.pagehelper.PageHelper;
import hbue.it.mapper.Column_valueMapper;
import hbue.it.mapper.ContestMapper;
import hbue.it.pojo.Contest;
import hbue.it.pojo.User;
import hbue.it.service.ContestService;
import hbue.it.service.Facade.ContestFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ContestFacadeServiceImpl implements ContestFacadeService {

    @Autowired
    private Column_valueMapper column_valueMapper;
    @Autowired
    private ContestMapper contestMapper;
    @Autowired
    private ContestService contestService;

    @Override
    public List<Contest> listContestByid(int uid, int start) {
        PageHelper.offsetPage(start,1);
        List<Contest> list = contestService.listByUId(uid);
        setContestStatus(list);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")
    @Override
    public void deleteContest(int uid, int cid) {
        //修改报名信息的可见状态，目前通过删除解决
        column_valueMapper.deleteByUCId(uid,cid);

        contestMapper.deleteContestRelation(cid, uid);
    }


    @Override
    public void update(int uid, int cid) {

    }

    @Override
    public List<Contest> listContests(User user,int start) {
        PageHelper.offsetPage(start,3);
        List<Contest> list = contestService.getAll();
        setContestStatus(list,user);
        setContestStatus(list);
        return list;
    }

    @Override
    public int judgeUCStatus(int uid, int cid,int status) {
        if(status==-1){
            return contestService.isRelationExist(uid, cid) ? 1 : 0;
        }
        return status;
    }

    private void setContestStatus(List<Contest> list){
        String status = null;
        for(Contest contest : list){
            status = contest.getStatus();
            if(status!=null&&status.equals("已报名")) continue;
            if(new Date().before(contest.getBegindate())){
                contest.setStatus(this.NotOpen);
            }else if(new Date().before(contest.getEnddate())){
                contest.setStatus(this.SIGNING);
            }else{
                contest.setStatus(this.SIGNED);
            }
        }
    }

    private void setContestStatus(List<Contest> list, User user){
        List<Contest> oldlist = contestMapper.selectByUserId(user.getId());
        for(Contest contest : list){
            contest.setStatus("未报名");
            for(Contest oldcontest : oldlist){
                if(contest.getId()==oldcontest.getId()){
                    contest.setStatus("已报名");
                }
            }
        }

    }
}