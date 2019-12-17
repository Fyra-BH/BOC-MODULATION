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
import javax.swing.JFrame;
import java.awt.geom.Point2D;



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
    
    public static final int MIN_WIDTH=320;//最小支持的分辨率
    public static final int MIN_HEIGHT=240;//同上

    public static final int BLANK_REMAINED=120;//边界留白

    private Point2D orign=new Point2D.Double(0,0);

    private boolean gridOn=false;//是否打开栅格

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

         System.out.println("x_len="+x_resampled.length);
         
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
        }
     }
     /**
      * 设置原点
      * @param x 要设置的坐标
      * @param y 要设置的坐标
      */
     public void setOrign(int x,int y){
        if(x>this.getSize().width){
           System.out.println("超出范围");
        }else if(x<0){
         this.orign.setLocation(0, y);
        }else{
         this.orign.setLocation(x, y);
        }

        if(y>this.getSize().height){
         System.out.println("超出范围");
         }else if(y<0){
            this.orign.setLocation(this.orign.getX(),0);
         }else{
            this.orign.setLocation(this.orign.getX(),y);
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

        double[] dis_temp=FBTools.resample(y_resampled,this.x_zone,y_resampled.length);//这是用于显示的原始数据，后面将进行y轴放缩
        /**下面将进行纵轴放缩及翻转 */
        int[] dis=new int[dis_temp.length];
        if(this.y_scale<this.y_zone*2/3){//小于显示高度的一半则放缩
         for(int i=0;i<dis_temp.length;i++){
             dis[i]=(int)(dis_temp[i]*this.y_zone/this.y_scale);
         }
        }
        /**下面画出横轴 */
         this.setOrign(BLANK_REMAINED/2, BLANK_REMAINED/4+this.y_zone+(int)FBTools.min(dis)[0]);//设置原点的像素坐标
         System.out.println(orign.getX()+","+orign.getY());
         g.drawLine((int)orign.getX(), (int)orign.getY(), this.x_zone+BLANK_REMAINED/2,(int)orign.getY() );

         /**下面画曲线 */
         bs=new BasicStroke(
           2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL
        );
            g.setStroke(bs);
            g.setColor(new Color(0xc6,0x28,0x28));
            for(int i=0;i<dis_temp.length-1;i++){
               g.drawLine(i+(int)this.orign.getX(), -dis[i]+(int)this.orign.getY(), i+(int)this.orign.getX()+1,  -dis[i+1]+(int)this.orign.getY());
            }
         /**下面画栅格(条件gridOn) */
         if(gridOn==true){
            bs=new BasicStroke(
               1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL);
               g.setStroke(bs);
               g.setColor(new Color(0xad,0xad,0xad));
               int N=y_zone/50;//栅格数
               int y_start=BLANK_REMAINED/4;
               int x_start=BLANK_REMAINED/2;
               for (int i = 1; i < N; i++) {//以50左右宽度画x方向栅格
                  g.drawLine(x_start, y_start+i*this.y_zone/N, x_start+x_zone, y_start+i*this.y_zone/N);
               }
               N=x_zone/50;
               y_start=BLANK_REMAINED/4;
                  for (int i = 0; i < N; i++) {//以50左右宽度画x方向栅格
                     g.drawLine(x_start+i*this.x_zone/N, y_start, x_start+i*this.x_zone/N, y_start+y_zone);
                  }
         }
     }


 }