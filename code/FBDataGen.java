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
     * ʵ����һ���̶����ȵ�����������
     * @param len ����
     * @param Omega ��Ƶ��
     */
    FBDataGen(int len ,double Omega){
        this.len=len;
        this.Omega=Omega;
    } 
    /**
     * ����һ����������
     * @param Omega ��Ƶ��
     * @param len ����
     * @return ��������
     */
    public static double[] getCosArray(double Omega,int len){
        double[] res=new double[len];
        for(int i=0;i<len;i++){
            res[i]=Math.cos(Omega*i);
        }
         return res;
    }
    /**
     * ����һ����������
     * @return ��������
     */
    public double[] getCosArray(){
        double[] res =new double[this.len];
        for(int i=0;i<this.len;i++){
            res[i]=Math.cos(this.Omega*i);
        }
        return res;
    }
    /**
     * ����һ����������
     * @param Omega ��Ƶ��
     * @param len ����
     * @return ��������
     */
    public static double[] getSinArray(double Omega,int len){
        double[] res=new double[len];
        for(int i=0;i<len;i++){
            res[i]=Math.sin(Omega*i);
        }
         return res;
    }
    /**
     * ����һ����������
     * @return ��������
     */
    public double[] getSinArray(){
        double[] res =new double[this.len];
        for(int i=0;i<this.len;i++){
            res[i]=Math.sin(this.Omega*i);
        }
        return res;
    }
    /**
     * ���ڴ�ӡdouble����,�������
     * @param d Ҫ��ӡ������
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
