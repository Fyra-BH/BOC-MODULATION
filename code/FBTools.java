package fbcode.tools;
import java.math.BigDecimal;
import java.text.Normalizer;

import fbcode.math.*;

/**
 * @author     fyra
 * @version    1.0
 * 
 * 实用工具
 */

 public class FBTools{

    /**
     * 构造函数(没啥用)
     */
    public FBTools(){

    }


    /**
     * 返回序列中最大数值及其索引
     * @param input 输入数组
     * @return      长度为2的数组，第一个为数值，第二个为索引，需要转int
     */
    public static double [] max(double[] input){
        double max=input[0];
        double index=0;
        for(int i=0;i<input.length;i++){
            if(max<input[i]){
                max=input[i];
                index=i;
            }
        }

        double[] res={max,index};
        return res;
    }
    /**
     * 返回序列中最大数值及其索引
     * @param input 输入数组
     * @return      长度为2的数组，第一个为数值，第二个为索引，需要转int
     */
    public static double [] max(int[] input){
        double max=input[0];
        double index=0;
        for(int i=0;i<input.length;i++){
            if(max<input[i]){
                max=input[i];
                index=i;
            }
        }

        double[] res={max,index};
        return res;
    }
    /**
     * 返回序列中最小数值及其索引
     * @param input 输入数组
     * @return      长度为2的数组，第一个为数值，第二个为索引，需要转int
     */
    public static double [] min(double[] input){
        double min=input[0];
        double index=0;
        for(int i=0;i<input.length;i++){
            if(min>input[i]){
                min=input[i];
                index=i;
            }
        }
        double[] res={min,index};
        return res;
    }
        /**
     * 返回序列中最小数值及其索引
     * @param input 输入数组
     * @return      长度为2的数组，第一个为数值，第二个为索引，需要转int
     */
    public static double [] min(int[] input){
        double min=input[0];
        double index=0;
        for(int i=0;i<input.length;i++){
            if(min>input[i]){
                min=input[i];
                index=i;
            }
        }
        double[] res={min,index};
        return res;
    }

    /**
     * 重采样
     * @param data  数据
     * @param fs    新的采样率
     * @param FS    旧的采样率
     * @return      重采样后的结果
     */
    public static double[] resample(double[] data,int fs,int FS){
        int len=data.length;
        double[] res=new double[(int)len*fs/FS];
        if(fs==FS){
            return data;
        }else if(fs<FS){
            for(int i=0;i<(int)len*fs/FS;i++){
                res[i]=data[(int)i*FS/fs];
            }
        }else{
            for(int i=0;i<(int)len*fs/FS;i++){
                if((i+1)%(fs/FS)==0){
                    res[i]=data[i*FS/fs];
                }else{
                    res[i]=0;
                }
            }
            
        }
        return res;
    }

    /**
     * 计算数量级
     * @param num 输入的数字
     * @return 数量级
     */
    public static int getOrder(double num){
        int n=0;
        num=Math.abs(num);

        if(num==0){
            return 0;
        }

        if(num>1){
            while(num>=10){
                num/=10;
                n++;
            }
        }else if(num<1){
            n--;
            while(num<0.1){
                num*=10;
                n--;
            }
        }
        return n;
    }
    /**
     * 计算底数
     * @param num 输入的数据
     * @return  底数
     */
    public static double getBase(double num){
        return num/Math.pow(10, getOrder(num));
    }
    /**
     * 计算分贝数
     * @param x
     * @return  分贝数
     */
    public static double getdB(double x){
        return 10*Math.log10(x);
    }

    /**
     * 计算分贝数
     * @param x
     * @return  分贝数
     */
    public static double[] getdB(double[] x){
        double[] res=new double[x.length];
        for (int i = 0; i < res.length; i++) {
            res[i]=10*Math.log10(x[i]);
        }
        return res;
    }
    /**
     * 保留小数点后n位
     * @param x 数值
     * @param n 位数
     * @return  保留后的值
     */
    public static double remain(double x,int n){
        double base=FBTools.getBase(x);
        base=(double)((int)(Math.pow(10,n)*base));
        base=base/Math.pow(10,n);
        String tmp= new String(base+"e"+getOrder(x));
        return  Double.valueOf(tmp);
    }

    /**
     * 匹配
     * @param in        输入数组
     * @param target    匹配的对象
     * @param delta     精度
     * @return          匹配的索引
     */
    public static int match(double[] in,double target,double delta){
        in=FBDataGen.add(in, -target);
        in=FBDataGen.getMode(in);
        double[] min=FBTools.min(in);
        if(Math.abs(min[0]-target)<=delta){
            return (int)min[1];
        }else{
            return -1;
        }
    }

    /**
     * 数组截取
     * @param in        输入数组
     * @param begin     开始序号
     * @param len       截取的长度
     * @return          截取的数组
     */
    public static double[] pic(double[] in,int begin,int len){
        double[] res=new double[len];
        for (int i = 0; i < len; i++) {
            res[i]=in[i+begin];
        }
        return res;
    }
    /**
     * 归一化（按最大值）
     * @param in    输入
     * @return      归一化结果
     */
    public static double[] normalize(double[] in){
        double r=FBTools.max(in)[0];
        return FBDataGen.multi(in, 1/r);
    }

    /**
     * 将0/1字符串变为数组
     * @param s 字符串
     * @return  0/1数组
     */
    public static int[] getBinaryCode(String s){
        char[] ch=s.toCharArray();
        int[] res=new int[ch.length];
        for (int i = 0; i < ch.length; i++) {
            if(ch[i]=='0'){
                res[i]=-1;
            }else if(ch[i]=='1'){
                res[i]=1;
            }else{
                System.out.println("错误");
                return null;
            }
        }
        return res;
    }
    /**
     * 分贝逆转换   
     * @param in    分贝值
     * @return      线性值
     */
    public static double[] getIdB(double[] in){
        double[] res =new double[in.length];
        for (int i = 0; i < res.length; i++) {
            res[i]=Math.pow(10, in[i]/10);
        }
        return res;
    }
    
    public static void main(String[] args) {

        // System.out.println(FBTools.getOrder(1.31e7));
        // System.out.println(FBTools.getBase(1.31e7));
        //System.out.println(FBTools.remain(1.36666666661e7,4));

        double[] x=FBDataGen.getLineSeq(-1, 1, 100);
        double[] y=FBTools.pic(x,25,50);
      //  FBConsole.prt(y);
    }
 }