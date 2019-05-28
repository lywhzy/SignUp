package hbue.it.service.impl;

import hbue.it.mapper.ContestMapper;
import hbue.it.pojo.Contest;
import hbue.it.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private ContestMapper contestMapper;

    @Override
    public List<Contest> getTopCharacterization() {
        List<Contest> list = contestMapper.getTopCharacterization();
        return list;
    }
}
