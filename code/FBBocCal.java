 /**
 * @author Fyra
 * @version 1.0
 */
package fbcode.math;

import fbcode.tools.FBConsole;
import fbcode.gui.FBChartFrame;
import fbcode.math.FBDataGen;
import fbcode.math.FBDataGen;;


/**
 * 专门用于BOC计算的类
 */
public class FBBocCal{

    public FBBocCal(){
        /*do nothing*/
    }

    /**
     * 返回功率普(1000点)
     * @param a     BOC_ALPHA
     * @param b     BOC_BETA
     * @param bw    带宽
     * @return      功率普密度
     */
    public static double[] getGBOC(int a,int b,double[] bw){

        double fs=a*1.023e6;
        double fc=b*1.023e6;

         int n=(int)(2*fs/fc);
         double[] GBOC=new double[bw.length];
         if(n%2==0){//偶数
            double[] temp1=FBDataGen.getTanArray(FBDataGen.multi(bw, Math.PI/2/fs)) ;
            double[] temp2=FBDataGen.getSinArray(FBDataGen.multi(bw, Math.PI/fc)) ;   
            double[] temp=FBDataGen.multi(FBDataGen.multi(temp1, temp2), 1/Math.PI) ;
            temp =FBDataGen.div(temp, bw);
            temp =FBDataGen.pow(temp, 2);
            GBOC =FBDataGen.multi(temp, fc);
            GBOC[0]=0;
            return GBOC; 
         }else{//奇数
            double[] temp1=FBDataGen.getTanArray(FBDataGen.multi(bw, Math.PI/2/fs)) ;
            double[] temp2=FBDataGen.getCosArray(FBDataGen.multi(bw, Math.PI/fc)) ;   
            double[] temp=FBDataGen.multi(FBDataGen.multi(temp1, temp2), 1/Math.PI) ;
            temp =FBDataGen.div(temp, bw);
            temp =FBDataGen.pow(temp, 2);
            GBOC =FBDataGen.multi(temp, fc);
            GBOC[0]=0;
            return GBOC;            
         }         

    }

    /**
     * 获取随机基带信号
     * @param n     一个码元时间内亚载波半周期数
     * @param fs    亚载波速率
     * @param t     离散的时间(类似matlab中的用法)
     * @return      时域基带信号
     */
    public static double[]  getBBS(int n,double fs, double[] t){
        double[] res=new double[t.length];
        double ts=1/fs;//亚载波半周期
        double dur=t[t.length-1]-t[0];//时长
        int[] an=FBDataGen.getRandomCode((int)(dur/n/ts+1), 0.5);//获取随机扩频码
        for (int i = 0; i < res.length; i++) {
            res[i]=an[(int)(i*an.length/res.length)];
        }
        return res;
    }

    /**
     * 获取随机基带信号
     * @param alpha     boc参数
     * @param beta      同上
     * @param t         离散的时间(类似matlab中的用法)
     * @return
     */
    public static double[] getBBS(int alpha,int beta,double[] t ){

        return getBBS((int)alpha*2/beta , (double)alpha*1.023e6,  t);
    }

    /**
     * 获取随机基带信号
     * @param alpha     boc参数
     * @param beta      同上
     * @param N         随机码个数
     * @return
     */
    public static double[] getBBS(int alpha,int beta,int N ){
        double[] t=FBDataGen.getLineSeq(0, (double)(N/beta/1.023e6), 10000);
        return getBBS((int)alpha*2/beta , (double)alpha*1.023e6,  t);
    }


    public static void main(String[] args) {
        double[] s= FBBocCal.getBBS(10, 5, 20);
        double[] t=FBDataGen.getLineSeq(0, 1/1.023e6*20, s.length);
        new FBChartFrame(t, s);
       
    }
    
}