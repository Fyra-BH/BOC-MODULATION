package fbcode.gui;


import fbcode.math.FBComplexList;
import fbcode.math.FBDataGen;
import fbcode.tools.FBConsole;
import fbcode.tools.FBTools;
import fbcode.gui.FBChart;

import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;


/**
 * Chart类的延伸，使用固定尺寸
 */

public class FBChartPanel extends JPanel{

    private FBChart chart;
    /**
     * 生成一个固定大小的图像
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public FBChartPanel(double[] x,double y[],int width,int height){
        chart=new FBChart(x, y);
        chart.setZone(width, height);
        chart.repaint();
        this.add(chart);
    }

     /**
      * 是否打开X刻度
      *@param b  是/否
      */
      public void setXscaleOn(boolean b){
        chart.setXscaleOn(b);
       }
    /**
    * 是否打开Y刻度
    *@param b  是/否
    */
    public void setYscaleOn(boolean b){
        chart.setYscaleOn(b);
    }

    /**
    * 是否打开轮廓
    *@param b  是/否
    */
    public void setBoarderleOn(boolean b){
        chart.setBoarderleOn(b);
    }

      /**
       * 设置线条粗细
       * @param w 粗细（正数）
       */
      public void setLineWidth(float w){
        chart.setLineWidth(w);
     }



}
