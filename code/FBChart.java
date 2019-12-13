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

 public class FBChart extends Canvas{

    private double[] x_resampled;//以1920×1080为基准的坐标，将作为基础数据进行放缩
    private double[] y_resampled;//同上

    private double[] x_range;//定义域(min~max)
    private double[] y_range;//值域(min~max)

    private double x_scale;//数据的横向尺度(max-min)
    private double y_scale;//数据的纵向尺度(同上)

    private int x_zone;//实际显示时横向像素尺度
    private int y_zone;//实际显示时纵向像素尺度

    private final int MAX_WIDTH=1920;//最大支持的分辨率
    private final int MAX_HEIGHT=1080;//同上

    /**
     * 构造函数
     * @param y 图像纵轴数据
     * @param x 横轴数据
     */
     public FBChart(double[] x,double[] y){
      /**默认的显示大小为480×320 */  
      this.x_zone=480;
      this.y_zone=320;
        setSize(this.x_zone,this.y_zone);
        if(y.length!=x.length){
           System.out.println("长度不等！！");
        }else{
         
         this.x_resampled=FBTools.resample(x,x.length/MAX_WIDTH,x.length);
         this.y_resampled=FBTools.resample(y,y.length/MAX_WIDTH,y.length);
         
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

     public void paint(Graphics e){

      Graphics2D g= (Graphics2D) e;
        /**绘制轮廓 */
        
      //  rec.
        BasicStroke bs=new BasicStroke(
           16,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL
        );
        g.setColor(new Color(0x79,0x55,0x48));
        g.drawRect(10, 10, this.x_zone, this.y_zone);
     }

     public static void main(String[] args) {
        JFrame jf=new JFrame("窗体");
        jf.setSize(800,480);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        double[] x=FBDataGen.getLineSeq(0,3.14*2, 100000);
        double[] y=FBDataGen.getSinArray(x);
        double[] min=FBTools.min(y);
        FBChart ch=new FBChart(x, y);
        ch.repaint();
        jf.add(ch); 
    }
 }