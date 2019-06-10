package hbue.it.service.impl;

import hbue.it.mapper.ContestMapper;
import hbue.it.pojo.Contest;
import hbue.it.pojo.ContestExample;
import hbue.it.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestMapper contestMapper;

    @Override
    public List<Contest> listByUId(int uid) {
        return contestMapper.selectByUserId(uid);
    }

    @Override
    public List<Contest> getTopCharacterization() {
        return contestMapper.getTopCharacterization();
    }

    @Override
    public void insertRelation(int uid, int cid) {
        contestMapper.insertRelation(uid, cid);
    }

    @Override
    public boolean isRelationExist(int uid, int cid) {
        return contestMapper.selectRelation(uid, cid)!=null;
    }

    @Override
    public Contest get(int id) {
        return null;
    }

    @Override
    public void update(Contest obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void insert(Contest obj) {

    }

    @Override
    public List<Contest> getAll() {
        ContestExample contestExample = new ContestExample();
        List<Contest> list = contestMapper.selectByExample(contestExample);
        return list;
    }



}
