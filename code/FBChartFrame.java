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
      setLocation(600,300);
      this.setVisible(true);
  }
   public FBChartFrame(double[] x,double[] y,String title){
      this.chart=new FBChart(x, y);
      this.setSize(this.chart.getXzone()+FBChart.BLANK_REMAINED,this.chart.getYzone()+FBChart.BLANK_REMAINED);//加20是为了留白
      this.add(this.chart);
      new Thread(new FBRuleChecker(this)).start();
      this.setTitle(title);
      setLocation(600,300);
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
    public static void main(String[] args) {
        int a = 5;
        int b = 2;
        double fs=a*1.023e6;
        double fc=b*1.023e6;

         int n=(int)(2*fs/fc);
         double[] f=FBDataGen.getLineSeq(-1.5e7, 1.5e7,1000);
         double[] GBOC=new double[f.length];
         if(n%2==0){//偶数
            double[] temp1=FBDataGen.getTanArray(FBDataGen.multi(f, Math.PI/2/fs)) ;
            double[] temp2=FBDataGen.getSinArray(FBDataGen.multi(f, Math.PI/fc)) ;   
            double[] temp=FBDataGen.multi(FBDataGen.multi(temp1, temp2), 1/Math.PI) ;
            temp =FBDataGen.div(temp, f);
            temp =FBDataGen.pow(temp, 2);
            GBOC =FBDataGen.multi(temp, fc);
            //FBConsole.prt(GBOC);
            GBOC[0]=0;
            new FBChartFrame(f, GBOC);
         }else{//奇数
            double[] temp1=FBDataGen.getTanArray(FBDataGen.multi(f, Math.PI/2/fs)) ;
            double[] temp2=FBDataGen.getCosArray(FBDataGen.multi(f, Math.PI/fc)) ;   
            double[] temp=FBDataGen.multi(FBDataGen.multi(temp1, temp2), 1/Math.PI) ;
            temp =FBDataGen.div(temp, f);
            temp =FBDataGen.pow(temp, 2);
            GBOC =FBDataGen.multi(temp, fc);
            GBOC[0]=0;
            //FBConsole.prt(GBOC);
            new FBChartFrame(f, GBOC);
         }
         
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
 