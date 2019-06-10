package hbue.it.service.Facade.impl;

import hbue.it.pojo.Contest;
import hbue.it.service.ContestService;
import hbue.it.service.Facade.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private ContestService contestService;

    @Override
    public List<Contest> getTopCharacterization() {
        List<Contest> list = contestService.getTopCharacterization();
        return list;
    }
}
