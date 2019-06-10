package hbue.it.util;

/**
 * 得到四位数的验证码
 */

public class RandomUtil {

    // 随机生成四位数
    public static int getRandNum(){
        return (int) ((Math.random() * 9 + 1) * 1000);
    }

}
