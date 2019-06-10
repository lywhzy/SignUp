package Test;

import hbue.it.mapper.ContestMapper;
import hbue.it.pojo.Contest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

public class ContestMapperTest {
    @Test
    public void  testMapper(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring/ApplicationContext.xml");
        ContestMapper contestMapper = applicationContext.getBean(ContestMapper.class);
        List<Contest> list =  contestMapper.selectByUserId(1);
        if(list!=null)
        list.forEach((contest)->{System.out.println(contest.toString());});
    }

    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring/ApplicationContext.xml");
        ContestMapper contestMapper = context.getBean(ContestMapper.class);
        System.out.println(contestMapper.selectRelation(2,3));
    }

}