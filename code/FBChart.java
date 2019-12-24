/**
 * @author:Fyra
 * @version:1.0
 * 
 * 用于绘制图表的类
 */

 package fbcode.gui;

import fbcode.math.FBComplexList;
import fbcode.math.FBDataGen;
import fbcode.tools.FBConsole;
import fbcode.tools.FBTools;


import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.geom.*;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;



 public class FBChart extends Canvas{

    private double[] x_resampled;//以1920×1080为基准的坐标，将作为基础数据进行放缩
    private double[] y_resampled;//同上

    private double[] x_range;//定义域(min~max)
    private double[] y_range;//值域(min~max)

    private double x_scale;//数据的横向尺度(max-min)
    private double y_scale;//数据的纵向尺度(同上)

    private int x_zone;//实际显示时横向像素尺度
    private int y_zone;//实际显示时纵向像素尺度

    public static final int MAX_WIDTH=1920;//最大支持的分辨率
    public static final int MAX_HEIGHT=1080;//同上
    
    public static final int MIN_WIDTH=480;//最小支持的分辨率
    public static final int MIN_HEIGHT=320;//同上

    public static final int BLANK_REMAINED=120;//边界留白
    public static final int GRID_WIDTH=100;//栅格大小

    private boolean gridOn=true;//是否打开栅格
    private boolean X_SCALE_ON=true;//x轴刻度
    private boolean Y_SCALE_ON=true;//y轴刻度

    private boolean downSampling=true;//降采样标志
    private double[] dataForUpsampling;//配合上面标志使用
    /**
     * 构造函数
     * @param y 图像纵轴数据
     * @param x 横轴数据
     */
     public FBChart(double[] x,double[] y){
      /**默认的显示大小为480×320 */  
      this.x_zone=800;
      this.y_zone=480;
        setSize(this.x_zone,this.y_zone);
        if(y.length!=x.length){
           System.out.println("长度不等！！");
        }else{
            this.x_resampled=FBTools.resample(x,MAX_WIDTH,x.length);
            this.y_resampled=FBTools.resample(y,MAX_WIDTH,y.length);
            
            /**确定定义域和值域 */
            this.x_range=new double[2];
            this.y_range=new double[2];
            this.x_range[0]=FBTools.min(this.x_resampled)[0];
            this.x_range[1]=FBTools.max(this.x_resampled)[0];
            this.y_range[0]=FBTools.min(this.y_resampled)[0];
            this.y_range[1]=FBTools.max(this.y_resampled)[0];

            /**确定横向和纵向尺度 */
            this.x_scale=this.x_range[1]-this.x_range[0];
            this.y_scale=this.y_range[1]-this.y_range[0];
            if(x.length>x_zone){//降采样
               downSampling=true;
            }else{//升采样
               dataForUpsampling=y;
               downSampling=false;
            }
        }
     }
     /**
      * 设置显示区域
      * @param x  横向大小
      * @param y  纵向大小
      */
     public void setZone(int x,int y){
         if(x>MAX_WIDTH){
            this.x_zone =MAX_WIDTH;
         }else if(x<MIN_WIDTH){
            this.x_zone=MIN_WIDTH;
         }else{
            this.x_zone=x;
         }

         if(y>MAX_HEIGHT){
            this.y_zone=MAX_HEIGHT;
         }else if(y<MIN_HEIGHT){
            this.y_zone=MIN_HEIGHT;
         }else{
            this.y_zone=y;
         }
     }   
     /**
      * 返回矩形框的长
      * @return 
      */
     public int getXzone(){
        return x_zone;
     }
     /**
      * 返回矩形框的高
      * @return
      */
     public int getYzone(){
        return y_zone;
     }

     /**
      * 是否打开栅格
      * @param bool  是/否
      */
     public void setGridOn(boolean bool){
      this.gridOn=bool;
     }
     
     /**
      * 是否打开X刻度
      *@param bool  是/否
      */
     public void setXscaleOn(boolean b){
      this.X_SCALE_ON=b;
     }
     /**
      * 是否打开Y刻度
      *@param bool  是/否
      */
      public void setYscaleOn(boolean b){
         this.Y_SCALE_ON=b;
        }
     /**
      * 绘图方法
      */
     public void paint(Graphics e){
      Graphics2D g= (Graphics2D) e;
        /**绘制轮廓 */
        BasicStroke bs=new BasicStroke(
           2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL
        );
        Rectangle2D rect=new Rectangle2D.Double(0,0,50,50);
        g.setStroke(bs);
        g.setColor(new Color(0x79,0x55,0x48));
        g.drawRect(BLANK_REMAINED/2, BLANK_REMAINED/4, this.x_zone, this.y_zone);
        double[] dis_temp;
        if(downSampling){//当数据足够多时
            dis_temp =FBTools.resample(y_resampled,this.x_zone,y_resampled.length);//这是用于显示的原始数据，后面将进行y轴放缩
        }else{//数据较少时
            dis_temp =FBTools.resample(dataForUpsampling,this.x_zone,dataForUpsampling.length);
         //   FBConsole.prt(dis_temp);
        }

        /**下面将进行纵轴放缩及翻转 */
        int[] dis=new int[dis_temp.length];
        if(this.y_scale<this.y_zone*2/3){//小于显示高度的一半则放缩
         for(int i=0;i<dis_temp.length;i++){
             dis[i]=(int)(dis_temp[i]*this.y_zone/this.y_scale);
         }
        }else if(this.y_scale>this.x_zone){//大于显示尺寸
         for(int i=0;i<dis_temp.length;i++){
             dis[i]=(int)(dis_temp[i]*this.y_zone/this.y_scale);
        }

      }
         int y_center=(int)(FBTools.max(dis)[0]+FBTools.min(dis)[0])/2;//y轴中心
        // System.out.println("y_center="+y_center);

         /**下面画曲线 */
         bs=new BasicStroke(
           2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL
        );
            g.setStroke(bs);
            g.setColor(new Color(0xc6,0x28,0x28));
            for(int i=0;i<dis_temp.length-1;i++){
               g.drawLine(i+BLANK_REMAINED/2, y_center-dis[i]+BLANK_REMAINED/4+y_zone/2, i+BLANK_REMAINED/2+1, y_center-dis[i+1]+BLANK_REMAINED/4+y_zone/2);
            }
         /**下面画栅格(条件gridOn) ,并且显示刻度数据*/
         if(gridOn==true){
            bs=new BasicStroke(
               1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL);
               g.setStroke(bs);
               g.setColor(new Color(0xbd,0xbd,0xcc));
               int N=y_zone/GRID_WIDTH;//栅格数
               int y_start=BLANK_REMAINED/4;
               int x_start=BLANK_REMAINED/2;
               for (int i = 0; i <=N; i++) {//以50左右宽度画x方向栅格
                  g.setColor(new Color(0xbd,0xbd,0xcc));
                  g.drawLine(x_start, y_start+i*this.y_zone/N, x_start+x_zone, y_start+i*this.y_zone/N);

                  /**下面显Y示刻度 */
                  if(Y_SCALE_ON){
                     g.setColor(new Color(0x0,0x0,0x0));
                     g.setFont(new Font("Dialog",Font.PLAIN,16));
                     DecimalFormat df=new DecimalFormat("#.00");
                     if(Math.abs(FBTools.getOrder(y_scale))>=0){//需要使用科学计数法               
                        g.drawString(df.format(FBTools.getBase(y_range[1]-y_scale*i/N)) +"e"+FBTools.getOrder(y_range[1]-y_scale*i/N), x_start+g.getFont().getSize()/2,y_start+i*this.y_zone/N-g.getFont().getSize()/2);
                     }
                  }
               }
               N=x_zone/GRID_WIDTH;
               y_start=BLANK_REMAINED/4;
               for (int i = 0; i <N; i++) {//以50左右宽度画x方向栅格
                  g.setColor(new Color(0xbd,0xbd,0xcc));
                  g.drawLine(x_start+i*this.x_zone/N, y_start, x_start+i*this.x_zone/N, y_start+y_zone);
                    /**下面显X示刻度 */
                    if(X_SCALE_ON){
                     g.setColor(new Color(0x0,0x0,0x0));
                     g.setFont(new Font("Dialog",Font.PLAIN,16));
                     DecimalFormat df=new DecimalFormat("#.00");
                     if(Math.abs(FBTools.getOrder(y_scale))>=0){//需要使用科学计数法
                        g.drawString(df.format(FBTools.getBase(x_range[0]+x_scale*i/N)) +"e"+FBTools.getOrder(x_range[1]-x_scale*i/N), x_start+i*this.x_zone/N+g.getFont().getSize()/2,y_start+y_zone+g.getFont().getSize()*2);
                     }
                    }
               }

         }
     }


 }
