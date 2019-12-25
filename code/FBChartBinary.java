 /**
 * @author Fyra
 * @version 1.0
 */

 package fbcode.gui;

import javax.swing.JPanel;

import fbcode.gui.FBChart;

/**
 * 专门用于显示二进制波形
 */

 public class FBChartBinary extends JPanel{
    
    private FBChart chartA;//用于显示扩频码域符号之积
    private FBChart chartC;//用于显示亚载波
    private FBChart chartS;//用于显示BOC基带信号，即上两项之积
    public FBChartBinary(){
        
    }      
 }