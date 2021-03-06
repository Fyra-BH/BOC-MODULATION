 /**
 * @author Fyra
 * @version 1.0
 */
package fbcode.math;

import fbcode.gui.FBChartFrame;
import fbcode.tools.FBConsole;

public class FBDataGen{
    static final double pi=Math.PI;
    private final int len;
    private final double Omega;
    /**
     * 实例化一个固定长度的数列生成器
     * @param len 长度
     * @param Omega 角频率
     */
    FBDataGen(int len ,double Omega){
        this.len=len;
        this.Omega=Omega;
    } 

    /**
     * 生成线性序列
     * @param begin 开始的数值
     * @param end   结束的数值
     * @param len   点数
     * @return      double数组
     */
    public static double[] getLineSeq(double begin,double end,int len){
        if(begin>end){
            System.out.println("错误：顺序");
        }else{
            double gap=(end-begin)/(len-1);
            double[] res=new double[len]; 
            for(int i=0;i<len;i++){
                res[i]=begin+i*gap;
            }
            return res;
        }
        return null;
    }

    /**
     * 生成一个余弦阵列
     * @param Omega 角频率
     * @param len 长度
     * @return 余弦阵列
     */
    public static double[] getCosArray(double Omega,int len){
        double[] res=new double[len];
        for(int i=0;i<len;i++){
            res[i]=Math.cos(Omega*i);
        }
         return res;
    }
    /**
     * 生成一个余弦阵列
     * @return 余弦阵列
     */
    public double[] getCosArray(){
        double[] res =new double[this.len];
        for(int i=0;i<this.len;i++){
            res[i]=Math.cos(this.Omega*i);
        }
        return res;
    }
    /**
     * 由数组直接生成余弦序列
     * @param ph    相位数组
     * @return      余弦序列
     */
    public static double[] getCosArray(double[] ph){   
        double[] res =new double[ph.length];
        for(int i=0;i<ph.length;i++){
            res[i]=Math.cos(ph[i]);
        }
        return res;
    }

    /**
     * 生成一个正弦阵列
     * @param Omega 角频率
     * @param len 长度
     * @return 正弦阵列
     */
    public static double[] getSinArray(double Omega,int len){
        double[] res=new double[len];
        for(int i=0;i<len;i++){
            res[i]=Math.sin(Omega*i);
        }
         return res;
    }
    /**
     * 生成一个正弦阵列
     * @return 正弦阵列
     */
    public double[] getSinArray(){
        double[] res =new double[this.len];
        for(int i=0;i<this.len;i++){
            res[i]=Math.sin(this.Omega*i);
        }
        return res;
    }
    /**
     * 由数组直接生成正弦序列
     * @param ph    相位数组
     * @return      余弦序列
     */
    public static double[] getSinArray(double[] ph){   
        double[] res =new double[ph.length];
        for(int i=0;i<ph.length;i++){
            res[i]=Math.sin(ph[i]);
        }
        return res;
    }

    /**
     * 由数组直接生成正切序列
     * @param ph    相位数组
     * @return      余弦序列
     */
    public static double[] getTanArray(double[] ph){   
        double[] res =new double[ph.length];
        for(int i=0;i<ph.length;i++){
            res[i]=Math.tan(ph[i]);
        }
        return res;
    }

    /**
     * Sa函数
     * @param ph    相位数组
     * @return      Sa函数序列
     */
    public static double[] getSincArray(double[] ph){
        return FBDataGen.div(FBDataGen.getSinArray(ph), ph);
    }

    /**
     * 乘法
     * @param x
     * @param y
     * @return  乘积
     */
    public static double[] multi(double[] x,double y){
        double[] res=new double[x.length];
        for(int i=0;i<x.length;i++){
            res[i]=x[i]*y;
        }
        return res;
    }
    /**
     * 乘法
     * @param x
     * @param y
     * @return 乘积
     */
    public static double[] multi(double[] x,double[] y){
        double[] res=new double[x.length];
        for(int i=0;i<x.length;i++){
            res[i]=x[i]*y[i];
        }
        return res;
    }
    /**安全除法
     * 使用1.7e308代替无穷
     * @param x
     * @param y
     * @return  商
     */
    public static double[] div(double[] x,double[] y){
        double[] res=new double[x.length];
        for(int i=0;i<x.length;i++){
            if(y[i]!=0)
                res[i]=x[i]/y[i];
            else
                res[i]=1.7e308/x[i]*Math.abs(x[i]);
        }
        return res;
    }
    /**
     * 除法
     * @param x
     * @param y
     * @return  商
     */
    public static double[] div(double[] x,double y){
        double[] res=new double[x.length];
        for(int i=0;i<x.length;i++){
            res[i]=x[i]/y;
        }
        return res;
    }
    /**
     * 加法
     * @param x
     * @param y
     * @return  和
     */
    public static double[] add(double[] x,double y){
        double[] res=new double[x.length];
        for(int i=0;i<x.length;i++){
            res[i]=x[i]+y;
        }
        return res;
    }
    /**
     * 加法
     * @param x
     * @param y
     * @return  和
     */
    public static double[] add(double[] x,double[] y){
        double[] res=new double[x.length];
        for(int i=0;i<x.length;i++){
            res[i]=x[i]+y[i];
        }
        return res;
    }
    /**
     * 乘方
     * @param x 底数
     * @param y 指数
     * @return      乘方结果
     */
    public static double[] pow(double[] x,double y){
        double[] res=new double[x.length];
        for (int i = 0; i < res.length; i++) {
            res[i]=Math.pow(x[i], y);
        }
        return res;
    }
    /**
     * 定积分
     * @param y
     * @param x
     * @return  定积分
     */
    public static double[] getInte(double[] y,double[] x){
        double res[]=new double[y.length]; 
        res[0]=0;
        for (int i = 1; i < y.length; i++) {
            res[i]=res[i-1]+y[i]*(x[i]-x[i-1]);
        }
        return res;
    }

    /**
     * 计算微分
     * @param y 输入变量
     * @param x 微分变量
     * @return  微分
     */
    public static double[] getDiff(double[] y,double[] x){
        double[] res=new double[y.length];
        for (int i = 0; i < res.length-1; i++) {
            res[i]=(y[i+1]-y[i])/(x[i+1]-x[i]);
        }
        res[res.length-1]=res[res.length-2];
        return res;
    }

    /**
     * 求模
     * @param in
     * @return  模
     */
    public static double[] getMode(double[] in){
        double[] res=new double[in.length];
        for (int i = 0; i < in.length; i++) {
            res[i]=Math.abs(in[i]);
        }
        return res;
    }
        /**
     * 获取一定长度的随机0/1码
     * @param len   长度
     * @param P     1的概率
     * @return      随机0/1码
     */
    public static int[] getRandomCode(int len,double P){
        int[] res=new int[len];
        for (int i = 0; i < len; i++) {
            res[i]=P>Math.random()? 1:0;
        }
        return res;
    }

    public static void main(String[] args) {
        double[] x=FBDataGen.getLineSeq(-9,9.00001, 10000);
        //FBConsole.prt(x);
       // System.out.println("x_len="+x.length);
        double[] y= FBDataGen.getSinArray(x);
        //FBConsole.prt(y);
        //System.out.println("y_len="+y.length);
        FBChartFrame f= new FBChartFrame(x,FBDataGen.getSinArray(x));
        f.setCh2(x,FBDataGen.getDiff(FBDataGen.getSinArray(x), x));
    }
}
