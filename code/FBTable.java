package fbcode.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.DirectoryNotEmptyException;


import fbcode.math.*;
import fbcode.tools.FBSnapShot;

/**
 * @author     fyra
 * @version    1.0
 * 
 * 用于显示计算参数的表格
 */

 public class FBTable extends JFrame{

    private JFileChooser fc;
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
            {"90%带宽功率(MHz)",FBBocCal.getBandByPercent(1.023e6, 0.9, bt)/1e6,FBBocCal.getBandByPercent(10.23e6, 0.9, bt)/1e6,FBBocCal.getBandByPercent(5,2,0.9,bt)/1e6,FBBocCal.getBandByPercent(8,4,0.9,bt)/1e6,FBBocCal.getBandByPercent(10,5,0.9,bt)/1e6},
            {"带外的损失(dB)",FBBocCal.getOutOfBandLoss(1.023e6,bt,br),FBBocCal.getOutOfBandLoss(10.23e6,bt,br),FBBocCal.getOutOfBandLoss(5,2,bt,br),FBBocCal.getOutOfBandLoss(8,4,bt,br),FBBocCal.getOutOfBandLoss(10,5,bt,br)},
            {"RMS带宽(MHz)",FBBocCal.getRMS(1.023e6,bt,br)/1e6,FBBocCal.getRMS(10.23e6,bt,br)/1e6,FBBocCal.getRMS(5,2,bt,br)/1e6,FBBocCal.getRMS(8,4,bt,br)/1e6,FBBocCal.getRMS(10,5,bt,br)/1e6},
            {"等效矩形带宽(MHz),",FBBocCal.getB_RECT(1.023e6, bt, br)/1e6,FBBocCal.getB_RECT(10.23e6, bt, br)/1e6,FBBocCal.getB_RECT(5, 2, bt, br)/1e6,FBBocCal.getB_RECT(8, 4, bt, br)/1e6,FBBocCal.getB_RECT(10, 5, bt, br)/1e6},
            {"与自身的频谱隔离系数(dB/Hz)",FBBocCal.getKLS(1.026e6,1.023e6,bt,br),FBBocCal.getKLS(10.26e6,10.23e6,bt,br),FBBocCal.getKLS(5,2,5,2,bt,br),FBBocCal.getKLS(4,8,4,8,bt,br),FBBocCal.getKLS(10,5,10,5,bt,br)},
            {"与1.023 MHzBPSK的频谱隔离系数",FBBocCal.getKLS(1.026e6,1.023e6,bt,br),FBBocCal.getKLS(10.26e6,1.023e6,bt,br),FBBocCal.getKLS(1.023e6,5,2,bt,br),-FBBocCal.getKLS(1.023e6,8,4,bt,br),-FBBocCal.getKLS(1.023e6,10,5,bt,br)},
            {"与 BOC(10,5)的频谱隔离系数", FBBocCal.getKLS(10,5,1.023e6,bt,br),-FBBocCal.getKLS(10,5,10.23e6,bt,br),FBBocCal.getKLS(5,2,10,5,bt,br),-FBBocCal.getKLS(8,4,10,5,bt,br),FBBocCal.getKLS(10,5,10,5,bt,br)},
            {"自相关函数主峰与第一副峰间的时延(ns)","无","无",FBBocCal.getHillDelay(5, 2, bt, br),FBBocCal.getHillDelay(8, 4, bt, br),FBBocCal.getHillDelay(10, 5, bt, br)},
            {"自相关函数第一副峰与主峰幅度平方之比","无","无",FBBocCal.getHillRate(5, 2, bt, br),FBBocCal.getHillRate(8, 4, bt, br),FBBocCal.getHillRate(10, 5, bt, br)},
        };
        JTable tb=new JTable(data,cloumNames);
        tb.setPreferredScrollableViewportSize(new Dimension(1600,175));
        JScrollPane scrollPane=new JScrollPane(tb);
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(btnSave,BorderLayout.NORTH);
        pack();
        setResizable(false);
        this.setVisible(true);

        /**点击按键5截图*/
        btnSave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(arg0.getSource()==btnSave){  
                    if(this==null){
                        JOptionPane.showMessageDialog(null, "请先勾选弹出窗口按钮并点击频域图像按钮");
                    }else{
                        setVisible(true);
                        File mypath=new File("./snapshot");
                        fc=new JFileChooser("./snapshot/");//用于选择保存文件
                        fc.setFileFilter(new javax.swing.filechooser.FileFilter(){
                        
                            @Override
                            public String getDescription() {
                                // TODO Auto-generated method stub
                                return ".png";
                            }
                        
                            @Override
                            public boolean accept(File arg0) {
                                // TODO Auto-generated method stub
                                if(arg0.getName().endsWith("png"))
                                    return true;
                                else
                                    return false;
                            }
                        });
                        
                    mypath.mkdir();
                    setLocation(0,0);
                    fc.showOpenDialog(fc);//浏览文件
                    if(fc.getSelectedFile()!=null){//没选则不执行
                        try {
                            Thread.sleep(500);                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        setAlwaysOnTop(true);
                        new FBSnapShot(fc.getSelectedFile().toPath().toString(),"png").snapShot(getLocation().x,getLocation().y,getSize().width,getSize().height);
                        setAlwaysOnTop(false);
                       }
                    }
                }
            }
        });

    }

    public static void main(String[] args) {
        new FBTable(30e6,24e6);
    }
 }