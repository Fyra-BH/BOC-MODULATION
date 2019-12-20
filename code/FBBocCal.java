 /**
 * @author Fyra
 * @version 1.0
 */
package fbcode.math;

import fbcode.tools.FBConsole;
import fbcode.math.FBDataGen;


/**
 * 专门用于BOC参数计算的类
 */
public class FBBocCal{

    public FBBocCal(){
        /*do nothing*/
    }

    /**
     * 返回功率普
     * @param a     BOC_ALPHA
     * @param b     BOC_BETA
     * @param bw    带宽
     * @return
     */
    public static double[] getGBOC(int a,int b,Double bw){

        double fs=a*1.023e6;
        double fc=b*1.023e6;

         int n=(int)(2*fs/fc);
         double[] f=FBDataGen.getLineSeq(-bw/2, bw/2,10000);
         double[] GBOC=new double[f.length];
         if(n%2==0){//偶数
            double[] temp1=FBDataGen.getTanArray(FBDataGen.multi(f, Math.PI/2/fs)) ;
            double[] temp2=FBDataGen.getSinArray(FBDataGen.multi(f, Math.PI/fc)) ;   
            double[] temp=FBDataGen.multi(FBDataGen.multi(temp1, temp2), 1/Math.PI) ;
            temp =FBDataGen.div(temp, f);
            temp =FBDataGen.pow(temp, 2);
            GBOC =FBDataGen.multi(temp, fc);
            GBOC[0]=0;
            return GBOC; 
         }else{//奇数
            double[] temp1=FBDataGen.getTanArray(FBDataGen.multi(f, Math.PI/2/fs)) ;
            double[] temp2=FBDataGen.getCosArray(FBDataGen.multi(f, Math.PI/fc)) ;   
            double[] temp=FBDataGen.multi(FBDataGen.multi(temp1, temp2), 1/Math.PI) ;
            temp =FBDataGen.div(temp, f);
            temp =FBDataGen.pow(temp, 2);
            GBOC =FBDataGen.multi(temp, fc);
            GBOC[0]=0;
            return GBOC;            
         }         

    }

    
}