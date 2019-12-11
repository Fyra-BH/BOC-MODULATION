 /**
 * @author Fyra
 * @version 1.0
 */
package fbcode.math;


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
                res[i]=begin=i*gap;
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
     * 用于打印double数组,方便调试
     * @param d 要打印的数组
     */
    public static void prt(double[] d){
        for(int i=0;i<d.length;i++){
            System.out.println("d["+i+"]="+d[i]);
            }
     }
    // public static void main(String[] args) {
    //     double[] d=FBDataGen.getCosArray(0.02*pi, 1000);
    //     prt(d);
    // }
}
