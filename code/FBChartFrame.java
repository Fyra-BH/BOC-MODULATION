package fbcode.gui;


/**
 * Chart类的延伸，将加入事件监测
 */

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
import javax.swing.JFrame;


public class FBChartFrame extends JFrame{
    private FBChart chart;

    public FBChartFrame(double[] x,double[] y){
      this.chart=new FBChart(x, y);
      this.setSize(this.chart.getXzone()+FBChart.BLANK_REMAINED,this.chart.getYzone()+FBChart.BLANK_REMAINED);//加20是为了留白
      this.add(this.chart);
      new Thread(new FBRuleChecker(this)).start();
      this.setTitle("chart");
      setLocation(800,300);
      this.setVisible(true);
  }
   public FBChartFrame(double[] x,double[] y,String title){
      this.chart=new FBChart(x, y);
      this.setSize(this.chart.getXzone()+FBChart.BLANK_REMAINED,this.chart.getYzone()+FBChart.BLANK_REMAINED);//加20是为了留白
      this.add(this.chart);
      new Thread(new FBRuleChecker(this)).start();
      this.setTitle(title);
      setLocation(800,300);
      this.setVisible(true);
   }

          /**
      * 设置显示区域
      * @param x  横向大小
      * @param y  纵向大小
      */
      public void setZone(int x,int y){
          this.chart.setZone(x,y);
      }
           /**
      * 返回矩形框的长
      * @return 
      */
     public int getXzone(){
        return chart.getXzone();
     }
     /**
      * 返回矩形框的高
      * @return
      */
     public int getYzone(){
        return chart.getYzone();
     }
     /**设置栅格显示 */
     public void setGridOn(Boolean bool){
        this.chart.setGridOn(bool);
     }
     /**
      * 是否打开X刻度
      *@param bool  是/否
      */
      public void setXscaleOn(boolean b){
         chart.setXscaleOn(b);
        }
     /**
     * 是否打开Y刻度
     *@param bool  是/否
     */
         public void setYscaleOn(boolean b){
         chart.setYscaleOn(b);
     }

      /**
       * 设置线条粗细
       * @param w 粗细（正数）
       */
     public void setLineWidth(float w){
        chart.setLineWidth(w);
     }

}

/**
 * 用于检测规则的线程类
 */
 class FBRuleChecker implements Runnable{
    private FBChartFrame box;
    public FBRuleChecker (FBChartFrame box){
       this.box=box;
    }
    private int cnt=0;
    public void run(){
       while(true){
        try {         
            Thread.sleep(200);//200ms检测一次  
            cnt++;
        } catch (Exception e) {
           e.printStackTrace();
        }
        /**下面两个if用于限制尺寸 */
          if(box.getSize().width<FBChart.MIN_WIDTH+FBChart.BLANK_REMAINED){
              box.setSize(FBChart.MIN_WIDTH+FBChart.BLANK_REMAINED,box.getSize().height);
              box.repaint();
          }
          if(box.getSize().height<FBChart.MIN_HEIGHT+FBChart.BLANK_REMAINED){
              box.setSize(box.getSize().width,FBChart.MIN_HEIGHT+FBChart.BLANK_REMAINED);
              box.repaint();
          }

          /**下面的if用于确定矩形框的尺寸 */
          if(box.getSize().width-FBChart.BLANK_REMAINED!=box.getXzone()){
            box.setZone(box.getSize().width-FBChart.BLANK_REMAINED,box.getYzone());
            box.repaint();
         }
          if(box.getSize().height-FBChart.BLANK_REMAINED!=box.getYzone()){
             box.setZone(box.getXzone(),box.getSize().height-FBChart.BLANK_REMAINED);
             box.repaint();
          }
       }
    }
 
 }
 