 /**
 * @author Fyra
 * @version 1.0
 */
package fbcode.math;

import fbcode.tools.FBConsole;
import fbcode.math.FBDataGen;
/**
 * ���������쳣
 */
class FBComplexException extends Exception{
     public FBComplexException(String msg){
         super(msg);
     }
}

public class FBComplexList{
    /**
     * ����һ��������������Ϊdouble
     * @param re1 ʵ��
     * @param im1 �鲿
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
     * ����һ��һά��������(1xN)��������Ϊ������ʽ
     * @param re ʵ��
     * @param im �鲿
     */
    public FBComplexList(double[] re,double[] im){
        try{
            if(re.length!=im.length){
                throw new FBComplexException("�������е��鲿��ʵ�����ȳ�");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        this.re=re;
        this.im=im;    
        this.len=re.length;
    }
    private int len;
    private double[] re;//ʵ��
    private double[] im;//�鲿
    /**
     * д��ʵ��
     * @param f ʵ��
     */
    public void setRe(double[] f){
        re=f;
    }
    /**
     * д���鲿
     * @param f �鲿
     */
    public void setIm(double[] f){
        im=f;
    }
    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public double[] getRe(){ 
        return re;
    }
    /**
     * ��ȡ�鲿
     * @return �鲿
     */
    public double[] getIm(){ 
        return im;
    }
    /**
     * ��ӡ�����е�����
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
     * ��ӡ�����е����ݼ����ӵ��ַ���
     * @param str ���ӵ��ַ���
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
     * �����˷�,����Ϊ����FBComplexList����
     * ���������������еĳ˻�
     * @param cmp1 ����1     
     * @param cmp2 ����2
     * @return ��˵Ľ��
     */
    public static FBComplexList muti(FBComplexList cmp1,FBComplexList cmp2){//�������
        int len=cmp1.im.length;
        double[] cmp1_re=cmp1.getRe();
        double[] cmp1_im=cmp1.getIm();  
        double[] cmp2_re=cmp2.getRe();
        double[] cmp2_im=cmp2.getIm();
        double res_im[]=new double[len];
        double res_re[]=new double[len];
        try {
            if(cmp1.len!=cmp2.len){
                throw new FBComplexException("����ά�ȱ������");
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
     * �󱾸�����������һ�������������
     * @param cmp Ҫ���˵�����
     * @return ��˵Ľ��
     */
     public FBComplexList muti(FBComplexList cmp){//�������
        double[] cmp_re=cmp.getRe();
        double[] cmp_im=cmp.getIm();  
        double res_im[]=new double[len];
        double res_re[]=new double[len];
        try {
            if(cmp.len!=len){
                throw new FBComplexException("����ά�ȱ������");
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
     * ��һ�������ĵ���
     * @param in ����ĸ�������
     * @return ��ÿ�������Ľ��
     * @throws Exception �����쳣
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
     * ��ǰ�������еĵ�������
     * @return ��������
     * @throws Exception �����쳣
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
 * �������еĳ���,����Ϊ����������
 * @param a ��������
 * @return �����Ľ��
 * @throws Exception ��������Ϊ��
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
                throw new FBComplexException("����ά�ȱ������");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.muti(a1);    
    }
    /**
     * �����������(��̬�����������¶���)
     * @param cmp1 ����1
     * @param cmp2 ����2
     * @return ��ӵĽ��
     */
    public static FBComplexList add(FBComplexList cmp1,FBComplexList cmp2){//�������
        int len=cmp1.im.length;
        double[] cmp1_re=cmp1.getRe();
        double[] cmp1_im=cmp1.getIm();  
        double[] cmp2_re=cmp2.getRe();
        double[] cmp2_im=cmp2.getIm();
        double res_im[]=new double[len];
        double res_re[]=new double[len];
        try {
            if(cmp1.len!=cmp2.len){
                throw new FBComplexException("����ά�ȱ������");
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
     * �������мӷ�
     * @param cmp ����
     * @return ��ӵĽ��
     */
    public FBComplexList add(FBComplexList cmp){
        double res_re[]=new double[len];
        double res_im[]=new double[len];
        double[] cmp_re=cmp.getRe();
        double[] cmp_im=cmp.getIm();
        try {
            if(cmp.len!=this.len){
                throw new FBComplexException("����ά�ȱ������");
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
 * �Ե��ȸ���������ģ
 * @return ģֵ
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
 * ������ĸ���������ģֵ
 * @param c ����ĸ�������
 * @return ģֵ
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
 * �������е����
 * @param cmp ��������ĸ�������
 * @return ���
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
                res[i]=pi/2;//������
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
 * �������е����
 * @return ���
 */
public double[] angle(){
    double[] res=new double[len];
    double pi=Math.PI;
    for(int i=0;i<len;i++){
        double a=re[i];
        double b=im[i];
        if(b!=0){
            if(a==0){
                res[i]=pi/2;//������
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
 * ͨ��ŷ����ʽ���㸴����
 * @param cmp ��ָ������
 * @return �ݽ��
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