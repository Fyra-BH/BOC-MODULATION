package fbcode.math;
/**
 * 收录一些数字处理工具和算法
 */

import fbcode.tools.FBConsole;


public class FBDsp{

    /**
     * 空的构造函数
     */
    public FBDsp(){

    }

    /**
     * 获取一定长度的随机0/1码
     * @param len   长度
     * @param P     1的概率
     * @return
     */
    public static int[] getRandomCode(int len,double P){
        int[] res=new int[len];
        for (int i = 0; i < len; i++) {
            res[i]=P>Math.random()? 1:0;
        }
        return res;
    }

}
