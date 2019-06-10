package hbue.it.util;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class Page {

    public Page() {

    }

    private int count = 1;

    private int start;

    private PageInfo pageInfo;

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }



    public Page(List list,int start) {
        this.pageInfo = new PageInfo<>(list);
        this.start = start;
    }


    public Page(int count, int start,List list) {
        this(list, start);
        this.count = count;
    }

    /**
     * 获取比赛的数量
     * @return
     */
    public int getTotal(){
        int total = (int)pageInfo.getTotal();
        return total%count==0 ? total/count : total/count+1;
    }

    public boolean ishasNext(){
        return pageInfo.isHasNextPage();
    }

    public boolean ishasPre(){
        return pageInfo.isHasPreviousPage();
    }

    public int getFinalPage(){
        return pageInfo.getNavigateLastPage();
    }
}
