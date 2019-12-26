package fbcode.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import fbcode.math.*;

/**
 * @author     fyra
 * @version    1.0
 * 
 * 用于显示计算参数的表格
 */

 public class FBTable extends JFrame{

    /**
     * 计算参数
     * @param bt    发射机带宽
     * @param br    接收机带宽
     */
     public FBTable(double bt,double br){
        super("参数列表");
        JButton btnSave=new JButton("点击保存");
        String[] cloumNames={"特性","1.023MhzBPSK","10.23MhzBPSK","BOC(5,2)","BOC(8,4)","BOC(10,5)"}; 
        Object[][]data={
            {"频谱主瓣距频带中心的频偏(+-MHz)",0,0,FBBocCal.getCenterDrift(5, 2,bt)/1e6,FBBocCal.getCenterDrift(8, 4,bt)/1e6,FBBocCal.getCenterDrift(10, 5,bt)/1e6},
            {"主瓣最大功谱密度(dBW/Hz)",FBBocCal.getGMAX(1.023e6, bt),FBBocCal.getGMAX(10.23e6, bt),FBBocCal.getGMAX(5, 2,bt),FBBocCal.getGMAX(8, 4,bt),-FBBocCal.getGMAX(10, 5,bt)},
            {"90%带宽功率(MHz)",FBBocCal.getBandByPercent(1.023e6, 0.9, bt)/1e7,12.1,FBBocCal.getBandByPercent(5,2,0.9,bt)/1e6,FBBocCal.getBandByPercent(8,4,0.9,bt)/1e6,FBBocCal.getBandByPercent(10,5,0.9,bt)/1e6},
            {"带外的损失(dB)",0.0,0.1,0.1,0.0,0.4},
            {"RMS带宽(MHz)",1.1,3.5,4.8,7.5,9.1},
            {"等效矩形带宽(MHz),",1.0,9.3,4.0,7.8,9.0},
            {"与自身的频谱隔离系数(dB/Hz)",-61.8,-71.5,-68.7,-71.4,-72.5},
            {"与1023 MHzBPSK的频谱隔离系数",-61.8,-69.9,-77.2,85.2,87.1},
            {"与 BOC(10,5)的频谱隔离系数", -87.1,80.2,-84.2,73.5,-72.5},
            {"自相关函数主峰与第一副峰间的时延(ns)","无","无",101,64,54},
            {"自相关函数第一副峰与主峰幅度平方之比","无","无",0.57,0.54,0.48},
        };
        JTable tb=new JTable(data,cloumNames);
        tb.setPreferredScrollableViewportSize(new Dimension(1000,180));
        JScrollPane scrollPane=new JScrollPane(tb);
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(btnSave,BorderLayout.NORTH);
        pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new FBTable(30e6,24e6);
    }
 }