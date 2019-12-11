 /**
 * @author Fyra
 * @version 1.0
 */
package fbcode.math;

import fbcode.tools.FBConsole;
import fbcode.math.FBDataGen;
/**
 * 复数阵列异常
 */
class FBComplexException extends Exception{
     public FBComplexException(String msg){
         super(msg);
     }
}

public class FBComplexList{
    /**
     * 构造一个复数，参数均为double
     * @param re1 实部
     * @param im1 虚部
     */
    public FBComplexList(double re1,double im1){
        double[] re=new double[1];
        double[] im=new double[1];
        re[0]=re1;
        im[0]=im1;  
        this.re=re;
        this.im=im;    
        this.len=1;
    }
    /**
     * 构造一个一维复数阵列(1xN)，参数均为数组形式
     * @param re 实部
     * @param im 虚部
     */
    public FBComplexList(double[] re,double[] im){
        try{
            if(re.length!=im.length){
                throw new FBComplexException("复数阵列的虚部和实部不等长");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        this.re=re;
        this.im=im;    
        this.len=re.length;
    }
    private int len;
    private double[] re;//实部
    private double[] im;//虚部
    /**
     * 写入实部
     * @param f 实部
     */
    public void setRe(double[] f){
        re=f;
    }
    /**
     * 写入虚部
     * @param f 虚部
     */
    public void setIm(double[] f){
        im=f;
    }
    /**
     * 获取实部
     * @return 实部
     */
    public double[] getRe(){ 
        return re;
    }
    /**
     * 获取虚部
     * @return 虚部
     */
    public double[] getIm(){ 
        return im;
    }
    /**
     * 打印阵列中的内容
     */
    public void prt(){
        for(int i=0;i<len;i++){
            if(im[i]>0){
                System.out.println("member["+i+"]="+re[i]+"+j"+im[i]);
            }else{                
                System.out.println("member["+i+"]="+re[i]+"-j"+(-im[i]));
            }
        }
    }
    
    /**
     * 打印阵列中的内容及附加的字符串
     * @param str 附加的字符串
     */
    public void prt(String str){
        System.out.println(str+":");
        for(int i=0;i<len;i++){
            if(im[i]>0){
                System.out.println("member["+i+"]="+re[i]+"+j"+im[i]);
            }else{                
                System.out.println("member["+i+"]="+re[i]+"-j"+(-im[i]));
            }
        }
    }

    /**
     * 复数乘法,输入为两个FBComplexList对象
     * 返回两个复数数列的乘积
     * @param cmp1 乘数1     
     * @param cmp2 乘数2
     * @return 相乘的结果
     */
    public static FBComplexList muti(FBComplexList cmp1,FBComplexList cmp2){//复数相乘
        int len=cmp1.im.length;
        double[] cmp1_re=cmp1.getRe();
        double[] cmp1_im=cmp1.getIm();  
        double[] cmp2_re=cmp2.getRe();
        double[] cmp2_im=cmp2.getIm();
        double res_im[]=new double[len];
        double res_re[]=new double[len];
        try {
            if(cmp1.len!=cmp2.len){
                throw new FBComplexException("阵列维度必须相等");
            }else{
                for(int i=0;i<len;i++){
                res_re[i]=cmp1_re[i]*cmp2_re[i]-cmp1_im[i]*cmp2_im[i];
                res_im[i]=cmp1_re[i]*cmp2_im[i]+cmp1_im[i]*cmp2_re[i];
                return new FBComplexList(res_re,res_im); 
                }       
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }   
    /**
     * 求本复数阵列与另一个复数阵列相乘
     * @param cmp 要乘伤的数组
     * @return 相乘的结果
     */
     public FBComplexList muti(FBComplexList cmp){//复数相乘
        double[] cmp_re=cmp.getRe();
        double[] cmp_im=cmp.getIm();  
        double res_im[]=new double[len];
        double res_re[]=new double[len];
        try {
            if(cmp.len!=len){
                throw new FBComplexException("阵列维度必须相等");
            }else{
                for(int i=0;i<len;i++) {
                res_re[i]=cmp_re[i]*re[i]-cmp_im[i]*im[i];
                res_im[i]=cmp_re[i]*im[i]+cmp_im[i]*re[i];
                return new FBComplexList(res_re,res_im); 
        }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
    }

    /**
     * 求一个复数的倒数
     * @param in 输入的复数阵列
     * @return 对每项求倒数的结果
     * @throws Exception 算数异常
     */
    public static FBComplexList getReciprocal(FBComplexList in) throws Exception{
        int len=in.len;
        double[] res_im=new double[len];
        double[] res_re=new double[len];
        double[] in_im=in.getIm();
        double[] in_re=in.getRe();
        for(int i=0;i<len;i++){
            double a=in_re[i];
            double b=in_im[i];
            try {
                res_re[i]=a/(a*a+b*b);
                res_im[i]=-b/(a*a+b*b);                
            } catch (Exception e) {
                throw e;
            }
        }
        FBComplexList res=new FBComplexList(res_re,res_im);
        return res;
    }
    /**
     * 求当前复数阵列的倒数阵列
     * @return 倒数阵列
     * @throws Exception 算数异常
     */
    public FBComplexList getReciprocal() throws Exception{
        double[] res_im=new double[len];
        double[] res_re=new double[len];
        for(int i=0;i<len;i++){
            double a=re[i];
            double b=im[i];
            try {
                res_re[i]=a/(a*a+b*b);
                res_im[i]=-b/(a*a+b*b);                
            } catch (Exception e) {
                throw e;
            }
        }
        FBComplexList res=new FBComplexList(res_re,res_im);
        return res;

    }

/**
 * 求复数阵列的除法,自身为被除数数组
 * @param a 除数数组
 * @return 除法的结果
 * @throws Exception 除数可能为零
 */
    public FBComplexList dev(FBComplexList a)throws Exception{
        FBComplexList a1=null;
        try {
            a1=a.getReciprocal();
        } catch (Exception e) {
            throw e;
        }
        try {
            if(a.len!=len){
                throw new FBComplexException("阵列维度必须相等");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.muti(a1);    
    }
    /**
     * 两个复数相加(静态方法，产生新对象)
     * @param cmp1 加数1
     * @param cmp2 加数2
     * @return 相加的结果
     */
    public static FBComplexList add(FBComplexList cmp1,FBComplexList cmp2){//复数相加
        int len=cmp1.im.length;
        double[] cmp1_re=cmp1.getRe();
        double[] cmp1_im=cmp1.getIm();  
        double[] cmp2_re=cmp2.getRe();
        double[] cmp2_im=cmp2.getIm();
        double res_im[]=new double[len];
        double res_re[]=new double[len];
        try {
            if(cmp1.len!=cmp2.len){
                throw new FBComplexException("阵列维度必须相等");
            }else{
                for(int i=0;i<len;i++){
                    res_re[i]=cmp1_re[i]+cmp2_re[i];
                    res_im[i]=cmp1_im[i]+cmp2_im[i];                      
                    return new FBComplexList(res_re,res_im); 
                 } 
            }
        }catch(Exception e){
                e.printStackTrace();
             }
        return null;
    }
    /**
     * 复数阵列加法
     * @param cmp 加数
     * @return 相加的结果
     */
    public FBComplexList add(FBComplexList cmp){
        double res_re[]=new double[len];
        double res_im[]=new double[len];
        double[] cmp_re=cmp.getRe();
        double[] cmp_im=cmp.getIm();
        try {
            if(cmp.len!=this.len){
                throw new FBComplexException("阵列维度必须相等");
            }else{
                for(int i=0;i<len;i++){
                    res_re[i]=cmp_re[i]+re[i];
                    res_im[i]=cmp_im[i]+im[i];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new FBComplexList(res_re, res_im);
    }
/*
 * 对当先复数阵列求模
 * @return 模值
 */
public double[] module(){
    double[] res=new double[len];     
    for(int i=0;i<len;i++){
        double a=re[i];
        double b=im[i];
        res[i]=Math.sqrt(a*a+b*b);
    }
    return res;
} 
/**
 * 对输入的复数阵列求模值
 * @param c 输入的复数阵列
 * @return 模值
 */
public static double[] module(FBComplexList c){
    int len=c.len;
    double[] res=new double[len];
    double[] c_re=c.getRe();
    double[] c_im=c.getIm();     
    for(int i=0;i<len;i++){
        double a=c_re[i];
        double b=c_im[i];
        res[i]=Math.sqrt(a*a+b*b);
    }
    return res;
}
/**
 * 求复数阵列的相角
 * @param cmp 参与运算的复数阵列
 * @return 相角
 */
public static double[] angle(FBComplexList cmp){
    double[] res=new double[cmp.len];
    double pi=Math.PI;
    double[] cmp_re=cmp.getRe();
    double[] cmp_im=cmp.getIm();
    for(int i=0;i<cmp.len;i++){
        double a=cmp_re[i];
        double b=cmp_im[i];
        if(b!=0){
            if(a==0){
                res[i]=pi/2;//虚轴上
            }else{
                res[i]=Math.atan(b/a);
            }
        }else{
            res[i]=0;
        }
    }
    return res;
}
/**
 * 求复数阵列的相角
 * @return 相角
 */
public double[] angle(){
    double[] res=new double[len];
    double pi=Math.PI;
    for(int i=0;i<len;i++){
        double a=re[i];
        double b=im[i];
        if(b!=0){
            if(a==0){
                res[i]=pi/2;//虚轴上
            }else{
                res[i]=Math.atan(b/a);
            }
        }else{
            res[i]=0;
        }
    }
    return res;
}
/**
 * 通过欧拉公式计算复数幂
 * @param cmp 复指数阵列
 * @return 幂结果
 */
public static FBComplexList exp(FBComplexList cmp){
    double[] res_re =new double[cmp.len];
    double[] res_im =new double[cmp.len];
    double[] cmp_re =cmp.getRe();
    double[] cmp_im =cmp.getIm();
    for(int i=0;i<cmp.len;i++){
        res_re[i]=Math.exp(cmp_re[i])*Math.cos(cmp_im[i]);
        res_im[i]=Math.exp(cmp_re[i])*Math.sin(cmp_im[i]);
    }
    return new FBComplexList(res_re, res_im);
}
    public static void main(String[] args){
        FBDataGen gen=new FBDataGen(100,0.05);
        double[] re=gen.getCosArray();
        double[] im=gen.getSinArray();
        FBComplexList a=new FBComplexList(re, im);
        a.prt();
        a=FBComplexList.exp(a);
        a.prt();
    }
}