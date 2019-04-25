package hbue.it.util;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageUtil {
    public static final int pageCount = 5;

    public static void setPage(int start){
        PageHelper.offsetPage(start,pageCount);
    }

    public static int getTotal(List list){
        PageInfo info = new PageInfo(list);
        return (int) info.getTotal();
    }


}
