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

 import java.awt.Canvas;
 import java.awt.Graphics;
 import java.awt.event.*;
 import javax.swing.JFrame;

 public class FBChart extends Canvas{
    private FBComplexList x;//横坐标
    private FBComplexList y;//要显示的数据

    /**
     * 构造函数
     * @param y 图像纵轴数据
     * @param x 横轴数据
     */
     public FBChart(double[] y,double[] x){
        setSize(480,320);
     }
     public void paint(Graphics e){
        e.drawLine(50 , 50, 120, 120);
     }
     public static void main(String[] args) {
        JFrame jf=new JFrame("窗体");
        jf.setSize(800,480);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        double[] y=FBDataGen.getSinArray(0.1*3.14, 1000);
        double[] x=FBDataGen.getCosArray(0.1*3.14, 1000);
        FBChart ch=new FBChart(y, x);
        jf.add(ch);
    }
 }