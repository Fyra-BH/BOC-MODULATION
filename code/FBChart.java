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
 import java.awt.event.*;
 import javax.swing.JFrame;

 public class FBChart extends Canvas{
    private double[] x;//横坐标
    private double[] y;//要显示的数据

    /**
     * 构造函数
     * @param y 图像纵轴数据
     * @param x 横轴数据
     */
     public FBChart(double[] y,double[] x){
        setSize(480,320);
        if(y.length!=x.length){
           System.out.println("长度不等！！");
        }else{
         this.x=x;
         this.y=y;
        }
     }
     /**
      * 将要显示的数据变成整数
      */
     private static void getAxises(){

     }

     public void paint(Graphics e){
        e.drawLine(50 , 50, 120, 120);
     }

     public static void main(String[] args) {
        JFrame jf=new JFrame("窗体");
        jf.setSize(480,320);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        double[] x=FBDataGen.getLineSeq(0,3.14*2, 20000000);
        double[] y=FBDataGen.getSinArray(x);
        double[] max=FBTools.max(y);
        System.out.println("max="+max[0]+",index="+max[1]);
        FBChart ch=new FBChart(y, x);
        jf.add(ch); 
    }
 }