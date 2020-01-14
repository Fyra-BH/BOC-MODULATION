 /**
 * @author Fyra
 * @version 1.0
 */
package fbcode.math;

import fbcode.tools.FBConsole;
import fbcode.tools.FBTools;
import fbcode.gui.FBChartFrame;
import fbcode.math.FBComplexList;
import fbcode.math.FBDataGen;
import fbcode.math.FBDataGen;;


/**
 * 专门用于BOC计算的类
 * 同时也可以进行BPSK的一些运算
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
     * 计算BPSK的功率谱(归一化)
     * @param fc    码率
     * @param bw    带宽
     * @return      BPSK的功率谱(归一化)
     */
    public static double[] getGBPSK(double fc,double[] bw){
        return FBDataGen.multi(FBDataGen.pow( FBDataGen.getSincArray(FBDataGen.multi(bw, Math.PI/fc)), 2), 1/fc) ;
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
     * @return          随机基带信号
     */
    public static double[] getBBS(int alpha,int beta,double[] t ){
        int n=(int)alpha*2/beta;
        double fs= (double)alpha*1.023e6;
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
     * 获取基带信号
     * @param alpha     boc参数
     * @param beta      同上
     * @param an        码元
     * @return          基带信号
     */
    public static double[] getBBS(int alpha,int beta,int[] an){
        int n=(int)alpha*2/beta;
        double fs= (double)alpha*1.023e6;
        double fc= (double)alpha*1.023e6;
        double[] res=new double[10000];
        double ts=1/fs;//亚载波半周期
        double tc=1/fc;//码元时间
        double dur=tc*an.length;//时长
        // int[] an=FBDataGen.getRandomCode((int)(dur/n/ts+1), 0.5);//获取随机扩频码
        for (int i = 0; i < res.length; i++) {
            res[i]=an[(int)(i*an.length/res.length)];
        }

        double[] cts=new double[res.length];//亚载波
        int N=n*an.length;
        int[] CTS=new int[N];
        CTS[0]=-1;
        for (int i = 1; i < N; i++) {
            if(CTS[i-1]==-1){
                CTS[i]=1;
            }else{
                CTS[i]=-1;
            }
        }
        for (int i = 0; i < cts.length; i++) {
            cts[i]=CTS[(int)(i*CTS.length/cts.length)];
        }

        return FBDataGen.multi(cts, res);
    }

    /**
     * 获取随机基带信号
     * @param fs    BPSK码元速率
     * @param an        码元
     * @return      时域基带信号
     */
    public static double[]  getBBS(double fs, int[] an){
        double[] res=new double[10000];
        double ts=1/fs;//码元时间
        double dur=ts*an.length;//时长
        for (int i = 0; i < res.length; i++) {
            res[i]=an[(int)(i*an.length/res.length)];
        }
        return res;
    }


    /**
     * 获取随机基带信号
     * @param alpha     boc参数
     * @param beta      同上
     * @param N         随机码个数
     * @return          随机基带信号
     */
    public static double[] getBBS(int alpha,int beta,int N ){
        double[] t=FBDataGen.getLineSeq(0, (double)(N/beta/1.023e6), 10000);
        return getBBS((int)alpha*2/beta , (double)alpha*1.023e6,  t);
    }

    /**
     * 计算自相关函数
     * @param Gf    频谱
     * @param f     带宽
     * @param tau   即τ
     * @return      自相关函数
     */
    public static double[] getRt(double[] Gf,double[] f,double[] tau){
        FBComplexList B=FBComplexList.getIDTFT(new FBComplexList(Gf, 0), f, tau);
        return B.module();
    }

    /**
     * 计算自相关函数（仅BPSK）
     * 这是理论计算公式
     * @param fc    码率
     * @param t     横轴时间
     * @return      自相关函数
     */
    public static double[] getRt(double fc,double[] t){
        double tc=1/fc;//码元时间
        double[] res=new double[t.length];
        for (int i = 0; i < t.length; i++) {
            if(Math.abs(t[i])<tc){
                res[i]=1-Math.abs(t[i]/tc);
            }else{
                res[i]=0;
            }
        }
        return res;
    }
    /**
     * 计算BOC(alpha，beta)中心频偏
     * @param alpha     boc参数
     * @param beta      同上
     * @param bt    发射带宽(30MHz)
     * @return          中心频偏
     */
    public static double getCenterDrift(int alpha,int beta,double bt){
        double[] bw=FBDataGen.getLineSeq(0, bt/2, 10000);//默认以30MHz带宽计算
        double[] GBOC= getGBOC(alpha,beta, bw);
        double res= bw[(int)FBTools.max(GBOC)[1]];
        return FBTools.remain(res, 3);
    }

    /**
     * 计算BOC最大功率普密度
     * @param alpha     boc参数
     * @param beta      boc参数
     * @param bt        发射机带宽
     * @return          最大功率普密度 
     */
    public static double getGMAX(int alpha,int beta,double bt){
        double[] bw=FBDataGen.getLineSeq(0, bt/2, 100000);//默认以30MHz带宽计算
        double[] GBOC= getGBOC(alpha,beta, bw);
        double res= 10*Math.log10(FBTools.max(GBOC)[0]) ;
        return FBTools.remain(res, 3);
    }

    /**
     * 计算BPSK最大功率普密度
     * @param fc        BPSK码率
     * @param bt        发射机带宽
     * @return          最大功率普密度 
     */
    public static double getGMAX(double fc,double bt){
        double[] bw=FBDataGen.getLineSeq(-bt/2, bt/2, 10000);//默认以30MHz带宽计算
        double[] GBPSK= getGBPSK(fc,bw);
        double res=  10*Math.log10(FBTools.max(GBPSK)[0]) ;
        return FBTools.remain(res, 3);
    }

    /**
     * 计算给定功率百分比对应的带宽(以BOC发射带宽为基准)
     * @param alpha     boc参数
     * @param beta      boc参数
     * @param per       功率百分比
     * @param bt        发射机带宽
     * @return          最大功率普密度 
     */
    public static double getBandByPercent(int alpha,int beta,double per,double bt){
        double[] bw=FBDataGen.getLineSeq(0, bt/2, 10000);//默认以30MHz带宽计算(单边带)
        double[] GBOC= getGBOC(alpha,beta, bw);
        double[] SG=FBDataGen.getInte(GBOC, bw);//GBOC的积分
        double res= 2*bw[FBTools.match(SG,SG[SG.length-1]*per,1)];
        return FBTools.remain(res, 3);
    }
    /**
     * 计算给定功率百分比对应的带宽(以BPSK发射带宽为基准)
     * @param fc        BPSK码率
     * @param per       功率百分比
     * @param bt        发射机带宽
     * @return          最大功率普密度 
     */
    public static double getBandByPercent(double fc,double per,double bt){
        double[] bw=FBDataGen.getLineSeq(0.0001, bt/2, 2000);//默认以30MHz带宽计算(单边带)
        double[] GBPSK= getGBPSK(fc, bw);
        double[] SG=FBDataGen.getInte(GBPSK, bw);//GBOC的积分
        int index=FBTools.match(SG,SG[SG.length-1]*0.90,1);
        double res= bw[index]*2;
        return FBTools.remain(res, 3);
    }

    /**
     * 计算带外损失(BOC)
     * @param alpha boc参数
     * @param beta  boc参数
     * @param bt    发射带宽(30MHz)
     * @param br    接收带宽(24MHz)
     * @return      带外损失(dB)
     */
    public static double getOutOfBandLoss(int alpha,int beta,double bt,double br){
        double[] bw=FBDataGen.getLineSeq(0, bt/2, 10000);//默认以30MHz带宽计算(单边带)
        double[] GBOC= getGBOC(alpha,beta, bw);
        double[] SG=FBDataGen.getInte(GBOC, bw);//GBOC的积分
        double res= 10*Math.log10(SG[SG.length-1]/SG[(int)(SG.length*br/bt)]); 
        return FBTools.remain(res, 3);
    }
    /**
     * 计算带外损失(BPSK)
     * @param fc        BPSK码率
     * @param bt    发射带宽(30MHz)
     * @param br    接收带宽(24MHz)
     * @return      带外损失(dB)
     */
    public static double getOutOfBandLoss(double fc,double bt,double br){
        double[] bw=FBDataGen.getLineSeq(0.0001, bt/2, 10000);//默认以30MHz带宽计算(单边带)
        double[] GBPSK= getGBPSK(fc, bw);
        double[] SG=FBDataGen.getInte(GBPSK, bw);//GBOC的积分
        double res= 10*Math.log10(SG[SG.length-1]/SG[(int)(SG.length*br/bt)]); 
        return FBTools.remain(res, 3);
    }
    /**
     * 计算带限均方根带宽(BPSK)
     * @param fc    BPSK码率
     * @param bt    发射带宽(30MHz)
     * @param br    接收带宽(24MHz)
     * @return      带限均方根带宽(RMS)
     */
    public static double getRMS(double fc,double bt,double br){
        double[] bw=FBDataGen.getLineSeq(-br/2, br/2, 10000);//默认以30MHz带宽计算(双边带)
        double[] GBPSK= getGBPSK(fc, bw);
        double[] GS=FBDataGen.getInte(GBPSK, bw);//GBPSK的积分
        System.out.println("GS[2999]="+GS[2999]);
        double lambda=GS[GS.length-1];
        System.out.println(lambda);
        double[] GBPSK_Normalized=FBDataGen.multi(GBPSK,1/lambda);//归一化普密度

        double[] f=FBDataGen.getLineSeq(-br/2, br/2, 10000);//计算RMS时用到的带宽
        double res= Math.pow(FBDataGen.getInte(FBDataGen.multi(FBDataGen.pow(f,2),GBPSK_Normalized),f)[f.length-1], 0.5); 
        return FBTools.remain(res, 3);
    }

    /**
     * 计算带限均方根带宽(BOC)
     * @param alpha boc参数
     * @param beta  boc参数
     * @param bt    发射带宽(30MHz)
     * @param br    接收带宽(24MHz)
     * @return      带限均方根带宽(RMS)
     */
    public static double getRMS(int alpha,int beta,double bt,double br){
        double[] bw=FBDataGen.getLineSeq(-br/2, br/2, 10000);//默认以30MHz带宽计算(双边带)
        double[] GBOC= getGBOC(alpha,beta, bw);
        double[] GS=FBDataGen.getInte(GBOC, bw);//GBOC的积分
        double lambda=GS[GS.length-1];
        double[] GBOC_Normalized=FBDataGen.multi(GBOC,1/lambda);//归一化普密度

        double[] f=FBDataGen.getLineSeq(-br/2, br/2, 10000);//计算RMS时用到的带宽

        double res= Math.pow(FBDataGen.getInte(FBDataGen.multi(FBDataGen.pow(f,2),GBOC_Normalized),f)[f.length-1], 0.5); 
        return FBTools.remain(res, 3);
    }
    /**
     * 计算等效矩形带宽(BPSK)
     * @param fc    BPSK码率
     * @param bt    发射带宽(30MHz)
     * @param br    接收带宽(24MHz)
     * @return      等效矩形带宽
     */
    public static double getB_RECT(double fc,double bt,double br){
        double[] bw=FBDataGen.getLineSeq(-br/2, br/2, 10000);//默认以30MHz带宽计算(双边带)
        double[] GBPSK= getGBPSK(fc, bw);
        double[] GS=FBDataGen.getInte(GBPSK, bw);//GBPSK的积分
        double lambda=GS[GS.length-1];

        double res= lambda/FBTools.max(GBPSK)[0];
        return FBTools.remain(res, 3);
    }
    /**
     * 计算等效矩形带宽(BOC)
     * @param alpha boc参数
     * @param beta  boc参数
     * @param bt    发射带宽(30MHz)
     * @param br    接收带宽(24MHz)
     * @return      等效矩形带宽
     */
    public static double getB_RECT(int alpha,int beta,double bt,double br){
        double[] bw=FBDataGen.getLineSeq(-br/2, br/2, 10000);//默认以30MHz带宽计算(双边带)
        double[] GBOC= getGBOC(alpha,beta, bw);
        double[] GS=FBDataGen.getInte(GBOC, bw);//GBOC的积分
        double lambda=GS[GS.length-1];

        double res= lambda/FBTools.max(GBOC)[0];
        return FBTools.remain(res, 3);
    }
    /**
     * 计算频谱隔离系数
     * @param Gl    干扰频谱
     * @param GS    功率信号频谱
     * @param bt    发射带宽(范围)
     * @param br    接收带宽(范围)
     * @return      计算频谱隔离系数
     */
    private static double getKLS(double[] Gl,double[] Gs,double[] bt,double[] br){
        double lambda=FBDataGen.getInte(Gl, bt)[Gl.length-1];
        double[] Gl_N=FBDataGen.multi(Gl, 1/lambda);//归一化噪声谱
        Gl_N=FBTools.pic(Gl_N, 999, 8000);
        System.out.println("GL_N="+Gl_N.length);
        System.out.println("Gs="+Gs.length);
        return FBDataGen.getInte(FBDataGen.multi(Gl_N, Gs), br)[br.length-1];
    }
    /**
     * 计算频谱隔离系数(BOC&BOC)
     * @param alphal    BOC干扰信号的参数
     * @param betal     BOC干扰信号的参数
     * @param alphas    BOC信号的参数
     * @param betas     BOC信号的参数
     * @param bwt       发射带宽(具体数值)
     * @param bwr       发射带宽(具体数值)
     * @return          频谱隔离系数
     */
    public static double getKLS(int alphal,int betal,int alphas,int betas,double bwt,double bwr){
        double[] bt=FBDataGen.getLineSeq(-bwt/2, bwt/2, 10000);
        double[] br=FBTools.pic(bt,(int)(10000*(1-bwr/bwt)/2)-1 ,(int)(10000*bwr/bwt));

        double[] Gl=FBBocCal.getGBOC(alphal, betal, bt);
        double[] Gs=FBBocCal.getGBOC(alphas, betas, br);
        double lambda=FBDataGen.getInte(Gl, bt)[Gl.length-1];
        double[] Gl_N=FBDataGen.multi(Gl, 1/lambda);//归一化噪声谱
        Gl_N=FBTools.pic(Gl_N, (int)(10000*(1-bwr/bwt)/2)-1, (int)(10000*bwr/bwt));
        // System.out.println("GL_N="+Gl_N.length);
        // System.out.println("Gs="+Gs.length);
        double res=FBDataGen.getInte(FBDataGen.multi(Gl_N, Gs), br)[br.length-1];
        return FBTools.remain(10*Math.log10(res), 3);
    }

        /**
     * 计算频谱隔离系数（BPSK&BOC）
     * @param alphal    BOC干扰信号的参数
     * @param betal     BOC干扰信号的参数
     * @param fc        BPSK码率
     * @param bwt       发射带宽(具体数值)
     * @param bwr       发射带宽(具体数值)
     * @return          谱隔离系数
     */
    public static double getKLS(int alphal,int betal,double fc,double bwt,double bwr){
        double[] bt=FBDataGen.getLineSeq(-bwt/2, bwt/2, 10000);
        double[] br=FBTools.pic(bt,(int)(10000*(1-bwr/bwt)/2)-1 ,(int)(10000*bwr/bwt));

        double[] Gl=FBBocCal.getGBOC(alphal, betal, bt);
        double[] Gs=FBBocCal.getGBPSK(fc, br);
        double lambda=FBDataGen.getInte(Gl, bt)[Gl.length-1];
        double[] Gl_N=FBDataGen.multi(Gl, 1/lambda);//归一化噪声谱
        Gl_N=FBTools.pic(Gl_N, (int)(10000*(1-bwr/bwt)/2)-1, (int)(10000*bwr/bwt));
        // System.out.println("GL_N="+Gl_N.length);
        // System.out.println("Gs="+Gs.length);
        double res=FBDataGen.getInte(FBDataGen.multi(Gl_N, Gs), br)[br.length-1];
        return FBTools.remain(10*Math.log10(res), 3);
    }

    /**
     * 计算频谱隔离系数(BOC&BPSK)
     * @param fc        BPSK干扰信号的参数（码率）
     * @param alphas    BOC信号的参数
     * @param betas     BOC信号的参数
     * @param bwt       发射带宽(具体数值)
     * @param bwr       发射带宽(具体数值)
     * @return          频谱隔离系数
     */
    public static double getKLS(double fc,int alphas,int betas,double bwt,double bwr){
        double[] bt=FBDataGen.getLineSeq(-bwt/2, bwt/2, 10000);
        double[] br=FBTools.pic(bt,(int)(10000*(1-bwr/bwt)/2)-1 ,(int)(10000*bwr/bwt));

        double[] Gl=FBBocCal.getGBPSK(fc, bt);
        double[] Gs=FBBocCal.getGBOC(alphas, betas, br);
        double lambda=FBDataGen.getInte(Gl, bt)[Gl.length-1];
        double[] Gl_N=FBDataGen.multi(Gl, 1/lambda);//归一化噪声谱
        Gl_N=FBTools.pic(Gl_N, (int)(10000*(1-bwr/bwt)/2)-1, (int)(10000*bwr/bwt));
        double res=FBDataGen.getInte(FBDataGen.multi(Gl_N, Gs), br)[br.length-1];
        return FBTools.remain(10*Math.log10(res), 3);
    }

    /**
     * 计算频谱隔离系数(BPSK&BPSK)
     * @param fcl       BPSK干扰信号的参数（码率）
     * @param fcs       BPSK信号的参数（码率）
     * @param bwt       发射带宽(具体数值)
     * @param bwr       发射带宽(具体数值)
     * @return          频谱隔离系数
     */
    public static double getKLS(double fcl,double fcs,double bwt,double bwr){
        double[] bt=FBDataGen.getLineSeq(-bwt/2, bwt/2, 10000);
        double[] br=FBTools.pic(bt,(int)(10000*(1-bwr/bwt)/2)-1 ,(int)(10000*bwr/bwt));

        double[] Gl=FBBocCal.getGBPSK(fcl, bt);
        double[] Gs=FBBocCal.getGBPSK(fcs, br);
        double lambda=FBDataGen.getInte(Gl, bt)[Gl.length-1];
        double[] Gl_N=FBDataGen.multi(Gl, 1/lambda);//归一化噪声谱
        Gl_N=FBTools.pic(Gl_N, (int)(10000*(1-bwr/bwt)/2)-1, (int)(10000*bwr/bwt));
        double res=FBDataGen.getInte(FBDataGen.multi(Gl_N, Gs), br)[br.length-1];
        return FBTools.remain(10*Math.log10(res), 3);
    }
    /**
     * 找出第二峰值位置
     * @param y 数据(一定要是对称的)
     * @param x 自变量
     * @return  第二峰值位置
     */
    private static int getIndexOfSecondHill(double[] y ){
        int cnt=0;
        for (int i = 1; i < y.length-1; i++) {
          if(y[i-1]<y[i]&&y[i]>y[i+1]){//找到极大值点
            cnt++;
            System.out.println(i);
          }  
        }
        int n=0;
        for (int i = 1; i < y.length-1; i++) {
            if(y[i-1]<y[i]&&y[i]>y[i+1]){//找到极大值点
                n++;
                if(n==cnt/2) return i;
            }  
          }
          return 0;
    }
    /**
     * 获取相关峰距离(第二个峰)
     * @param alpha BOC信号的参数
     * @param beta  BOC信号的参数
     * @param bwt   发射带宽(具体数值)
     * @param bwr   发射带宽(具体数值)
     * @return      时延(ns)
     */
    public static double getHillDelay(int alpha,int beta,double bwt,double bwr){
        double[] bw=FBDataGen.getLineSeq(-bwt/2,bwt/2,1000);//这个1000不能动！
        double[] GBOC=FBBocCal.getGBOC(alpha,beta,bw);
        double[] tau=FBDataGen.getLineSeq(-0.2e-6,0.2e-6,2000);
        double[] Rt=FBBocCal.getRt(GBOC, bw,tau);
        Rt=FBTools.normalize(Rt);//归一化自相关
        int index= getIndexOfSecondHill(Rt);
        return FBTools.remain(Math.abs(tau[index])*1e9, 3);//保留3位，单位ns
    }

    /**
     * 获取相关峰距离(第二个峰)
     * @param alpha BOC信号的参数
     * @param beta  BOC信号的参数
     * @param bwt   发射带宽(具体数值)
     * @param bwr   发射带宽(具体数值)
     * @return      时延(ns)
     */
    public static double getHillRate(int alpha,int beta,double bwt,double bwr){
        double[] bw=FBDataGen.getLineSeq(-bwr/2,bwr/2,1000);//这个1000不能动！
        double[] GBOC=FBBocCal.getGBOC(alpha,beta,bw);
        double[] tau=FBDataGen.getLineSeq(-0.2e-6,0.2e-6,2000);
        double[] Rt=FBBocCal.getRt(GBOC, bw,tau);
        Rt=FBTools.normalize(Rt);//归一化自相关
        int index= getIndexOfSecondHill(Rt);
        double res=Rt[index]/FBTools.max(Rt)[0];
        res=res*res;//平方比
        return FBTools.remain(res, 3);//保留3位，单位ns
    }

    /**
     * 计算码跟踪误差
     * @param delta 码间隔(ns)
     * @param SNR   信噪比(自变量)
     * @param Gs    功率谱
     * @param T     相关积分时间(单位ms)
     * @return      跟踪误差
     */
    public static double[] getNELP(double delta,double[] SNR,double[] Gs,double T){
        double BL=0.1;//码跟踪环单边噪声等效矩形带宽,默认0.1Hz
        T=T/1e3;//ms转s
        delta=delta/1e9;//ns转s
        double[] f=FBDataGen.getLineSeq(-12e6, 12e6, Gs.length);//默认24MHz带宽
        double tmp=(1-0.25*BL*T)*BL;
        double[] tmp1=FBDataGen.getSinArray(FBDataGen.multi(f, Math.PI*delta));
        tmp1=FBDataGen.pow(tmp1, 2);
        tmp1=FBDataGen.multi(tmp1, Gs);
        tmp1=FBDataGen.getInte(tmp1, f);
        tmp=tmp1[tmp1.length-1]*tmp;//左式分子完成
        System.out.println("z="+tmp);

        double[] tmp2=FBDataGen.getSinArray(FBDataGen.multi(f, Math.PI*delta));
        tmp2=FBDataGen.multi(Gs,tmp2);
        tmp2=FBDataGen.multi(f,tmp2);
        tmp2=FBDataGen.getInte(tmp2, f);
        tmp2=FBDataGen.multi(tmp2, 2*Math.PI);
        tmp2=FBDataGen.pow(tmp2, 2);
        tmp1=FBDataGen.multi(SNR,tmp2[tmp2.length-1]);
        System.out.println("m="+tmp2[tmp2.length-1]);

        tmp1=FBDataGen.div(FBDataGen.getLineSeq(tmp, tmp, tmp1.length), tmp1);//左侧运算完成

        tmp2=FBDataGen.getCosArray(FBDataGen.multi(f, Math.PI*delta));
        tmp2=FBDataGen.pow(tmp2, 2);
        tmp2=FBDataGen.multi(tmp2, Gs);
        tmp2=FBDataGen.getInte(tmp2, f);//右式分子完成

        double[] tmp3=FBDataGen.getCosArray(FBDataGen.multi(f, Math.PI*delta));
        tmp3=FBDataGen.multi(tmp3, Gs);
        tmp3=FBDataGen.getInte(tmp3, f);
        tmp3=FBDataGen.pow(tmp3, 2);
        tmp3=FBDataGen.multi(SNR, T*tmp3[tmp3.length-1]);

        tmp3=FBDataGen.div(FBDataGen.getLineSeq(tmp2[tmp2.length-1], tmp2[tmp2.length-1], tmp3.length), tmp3);
        tmp3=FBDataGen.add(tmp3,1);//右式计算完成


        return FBDataGen.multi(tmp1,tmp3);

    }

    /**
     * 计算多径偏移误差
     * @param Gs        功率谱
     * @param delta     相关时间
     * @param tau       自变量
     * @param b         正负号标记
     * @return          多径偏移误差
     */
    public static double[] getNELP(double[] Gs,double delta,double[]tau,boolean b){
        double[] res=new double[tau.length];
        double[] t=new double[tau.length];
        double[] e=new double[tau.length];
        double alpha=Math.pow(0.1,0.3);
        if(b==true){
            alpha=-alpha;
        }
        double[] f=FBDataGen.getLineSeq(-12e6,12e6,Gs.length);
        for (int i = 0; i < res.length; i++) {
            double[] tmp=FBDataGen.getSinArray(FBDataGen.multi(f,2*Math.PI*tau[i]));
            tmp=FBDataGen.multi(tmp,FBDataGen.getSinArray(FBDataGen.multi(f,Math.PI*delta)));
            tmp=FBDataGen.multi(tmp,Gs);
            tmp=FBDataGen.multi(FBDataGen.getInte(tmp,f),alpha);

            double[] tmp1=FBDataGen.multi(FBDataGen.getCosArray(FBDataGen.multi(f,2*Math.PI*tau[i])),alpha);
            tmp1=FBDataGen.add(tmp1,1);
            tmp1=FBDataGen.multi(tmp1,FBDataGen.getSinArray(FBDataGen.multi(f,Math.PI*delta)));
            tmp1=FBDataGen.multi(tmp1,Gs);
            tmp1=FBDataGen.multi(tmp1,f);
            tmp1=FBDataGen.multi(FBDataGen.getInte(tmp1,f),2*Math.PI);

            res[i]=tmp[tmp.length-1]/tmp1[tmp1.length-1];
            t[i]=tmp[tmp.length-1];
            e[i]=tmp1[tmp1.length-1];
        }

        res=FBDataGen.multi(res, 3e8);
        return res;
    }

    public static void main(String[] args) {
        // System.out.println("BOC(10,5)的等效矩形带宽="+getB_RECT(10, 5, 30e6, 24e6)/1e6);
        // System.out.println("BOC(8,4)的等效矩形带宽="+getB_RECT(8, 4, 30e6, 24e6)/1e6);
        // System.out.println("BOC(5,2)的等效矩形带宽="+getB_RECT(5, 2, 30e6, 24e6)/1e6);
        // double KLS=getKLS(8,4,10,5,30e6,24e6);
        // System.out.println("BOC(10,5)与自身的隔离系数="+10*Math.log10(KLS) );

        /**BPSK测试 */
        double[] bw=FBDataGen.getLineSeq(-15e6, 15e6, 10000);
        FBChartFrame fr= new FBChartFrame(bw, FBBocCal.getGBPSK(1.023e6,bw));
        fr.setCh2(bw, FBBocCal.getGBPSK(10.23e6,bw));

        // /**BOC测试 */   
        // double[] bw=FBDataGen.getLineSeq(-12e6, 12e6, 1000);
        // double[] GBOC=FBBocCal.getGBOC(5,2, bw);
        // double[] tau=FBDataGen.getLineSeq(-0.2e-6, 0.2e-6, 10000);
        // double[] Rt=FBBocCal.getRt(GBOC, bw, tau);
        // GBOC=FBDataGen.div(GBOC, FBDataGen.getInte(GBOC, bw)[bw.length-1]);
        // FBChartFrame f=new FBChartFrame(tau,Rt);
        // System.out.println(Rt[FBBocCal.getIndexOnSecondHill(Rt)]);
        // System.err.println("BOC(10,5)自相关函数主峰与第一副峰幅度时延="+(-tau[FBBocCal.getIndexOnSecondHill(Rt)]+tau[(int)(FBTools.max(Rt)[1])])*1e9);
        // System.err.println("BOC(10,5)自相关函数主峰与第一副峰幅度平方之比="+ Math.pow(Rt[FBBocCal.getIndexOnSecondHill(Rt)]/Rt[(int)(FBTools.max(Rt)[1])],2));
    }
    
}