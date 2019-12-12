package fbcode.tools;

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
     * 重采样
     * @param data  数据
     * @param fs    新的采样率
     * @param FS    旧的采样率
     * @return      重采样后的结果
     */
    public static double[] resample(double[] data,int fs,int FS){
        int len=data.length;
        double[] res=new double[(int)len*FS/fs];
        if(fs==FS){
            return data;
        }else if(fs<FS){
            for(int i=0;i<(int)len*fs/FS;i++){
                res[i]=data[(int)i*FS/fs];
            }
        }
        return res;
    }
 }