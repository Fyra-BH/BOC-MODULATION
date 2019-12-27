 /**
 * @author Fyra
 * @version 1.0
 * 
 * 主界面（待完成）
 */
package fbcode.gui;

import fbcode.tools.FBConsole;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.DirectoryNotEmptyException;

import fbcode.math.FBDataGen;
import fbcode.math.FBBocCal;
import fbcode.gui.FBChartFrame;
import fbcode.gui.FBChartPanel;
import fbcode.tools.FBSnapShot;
import fbcode.tools.FBTable;
import fbcode.tools.FBTools;

public class FBMainForm extends JFrame{


    public FBMainForm(){
        super("BOC 与 BPSK调制的分析");
        setSize(900,640);
        setLocation(600,300);
        setResizable(false);
        JTabbedPane jtp =new JTabbedPane();
        jtp.addTab("BOC", new ImageIcon("icon/BOC_ICON.png"),new BocPanel(),"BOC调制");
        jtp.addTab("BPSK", new ImageIcon("icon/BPSK_ICON.png"),new BpskPanel(), "BPSK调制");
     
        getContentPane().add(jtp);
        validate();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new FBMainForm();    
    }
}

class BackgroundPanel extends JPanel {  
//摘自https://www.cnblogs.com/hthuang/archive/2013/12/04/3458351.html
    private static final long serialVersionUID = -6352788025440244338L;  
    
        private Image image = null;  

        public BackgroundPanel(Image image) {  
            this.image = image;  
        }  

        // 固定背景图片，允许这个JPanel可以在图片上添加其他组件  
        protected void paintComponent(Graphics g) {  
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);  
        }  
}  

/**
 * BOC栏
 */
class BocPanel extends JPanel{

    private int BOC_ALPHA=10;
    private int BOC_BETA=5;
    private double BOC_BT=30e6;//30M发射带宽
    private double BOC_BR=24e6;//24M接收带宽
    private boolean CHART_FRAME_ENABLE=false;//弹窗式图像
    private boolean CH2_ENABLE=false;//通道2
    private FBChartFrame chframe;
    private JFileChooser fc;

    public FBChartFrame getChartFrame(){
        return chframe;
    }

    public BocPanel(){
        JPanel lfpanel=new JPanel();
        JPanel rtpanel=new JPanel();//  BackgroundPanel(new ImageIcon("icon/CHART.png").getImage());
       // rtpanel.add(new BackgroundPanel(new ImageIcon("icon/CHART.png").getImage()));
       

        JTextField tf_boc_alpha=new JTextField("10");
        JTextField tf_boc_beta=new JTextField("5");
        JTextField tf_BOC_BT_trans=new JTextField("30");
        JTextField tf_BOC_BT_recv=new JTextField("24");

        JButton b1=new JButton("时域图像",new ImageIcon("icon/TIME_ICON.png"));
        JButton b2=new JButton("频域图像",new ImageIcon("icon/FREQ_ICON.png"));
        JButton b3=new JButton("相关函数",new ImageIcon("icon/R.png"));
        JButton b4=new JButton("计算参数",new ImageIcon("icon/cal.png"));
        JButton b5=new JButton("保存截图",new ImageIcon("icon/SNAPSHOT.png"));
        JRadioButton rb1=new JRadioButton("弹出图像");
        JRadioButton rb2=new JRadioButton("打开通道2");

        lfpanel.setBorder(BorderFactory.createBevelBorder(1));//子面板设置边界
        rtpanel.setBorder(BorderFactory.createBevelBorder(1));

        lfpanel.setLayout(new BoxLayout(lfpanel,BoxLayout.Y_AXIS));
        /**下面加入按键与参数框 */
        JPanel lab_temp= new JPanel();//临时面板，最终将加入lfpanel
        lab_temp.setLayout(new BoxLayout(lab_temp, BoxLayout.Y_AXIS));
        lab_temp.add(Box.createRigidArea(new Dimension(0,10)));
        lab_temp.add(new JLabel("请设置参数:"));
        lab_temp.add(new JLabel("BOC(α,β)"));
        lab_temp.add(new JLabel("发射带宽βt(MHz)"));
        lab_temp.add(new JLabel("接收带宽βr(MHz)"));
        lab_temp.add(Box.createRigidArea(new Dimension(0,10)));
            /**加入输入参数的面板 */        
            JPanel lab_input= new JPanel();//输入面板
            lab_input.setLayout(new BoxLayout(lab_input, BoxLayout.X_AXIS));
            lab_input.add(new JLabel("    α=  "));
            tf_boc_alpha.setMaximumSize(new Dimension(60,40));
            lab_input.add(tf_boc_alpha);
            lab_temp.add(lab_input);
            
            lab_input= new JPanel();//输入面板
            lab_input.setLayout(new BoxLayout(lab_input, BoxLayout.X_AXIS));
            lab_input.add(new JLabel("    β=  "));
            tf_boc_beta.setMaximumSize(new Dimension(60,40));
            lab_input.add(tf_boc_beta);
            lab_temp.add(lab_input);

            lab_input= new JPanel();//输入面板
            lab_input.setLayout(new BoxLayout(lab_input, BoxLayout.X_AXIS));
            lab_input.add(new JLabel("   βt=  "));
            tf_BOC_BT_trans.setMaximumSize(new Dimension(60,40));
            lab_input.add(tf_BOC_BT_trans);
            lab_temp.add(lab_input);

            lab_input= new JPanel();//输入面板
            lab_input.setLayout(new BoxLayout(lab_input, BoxLayout.X_AXIS));
            lab_input.add(new JLabel("   βr=  "));
            tf_BOC_BT_recv.setMaximumSize(new Dimension(60,40));
            lab_input.add(tf_BOC_BT_recv);
            lab_temp.add(lab_input);

            lab_temp.setBorder(BorderFactory.createBevelBorder(1));
            lab_temp.add(Box.createRigidArea(new Dimension(10,40)));
            lab_temp.add(b1);
            lab_temp.add(Box.createRigidArea(new Dimension(10,10)));
            lab_temp.add(b2);
            lab_temp.add(Box.createRigidArea(new Dimension(10,10)));
            lab_temp.add(b3);
            lab_temp.add(Box.createRigidArea(new Dimension(10,10)));
            lab_temp.add(b4); 
            lab_temp.add(Box.createRigidArea(new Dimension(10,10)));
            lab_temp.add(b5); 
            lab_temp.add(Box.createRigidArea(new Dimension(10,10)));
        lfpanel.add(lab_temp);//一次性加入左边栏
        lfpanel.add(Box.createVerticalGlue()); 
        lfpanel.add(rb1);  

        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(lfpanel);
        add(rtpanel);

        /**点击按键1绘制时域图像*/
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(arg0.getSource()==b1){
                    /** 首先获取BOC各项参数 */
                    try {
                        BOC_ALPHA=Integer.valueOf( tf_boc_alpha.getText());
                        BOC_BETA=Integer.valueOf( tf_boc_beta.getText());
                        BOC_BT=Double.valueOf(tf_BOC_BT_trans.getText())*1e6;
                        if(BOC_ALPHA!=0&&BOC_BETA!=0&&BOC_BT>0){
                            /**do nothing */
                        }else{
                            JOptionPane.showMessageDialog(null, "请输入正确参数");
                            return;
                        }                        
                    } catch (Exception e) {
                        e.printStackTrace();
                        //TODO: handle exception
                    }

                    double[] t=FBDataGen.getLineSeq(0,1/(BOC_ALPHA*1.023e6)*50,10000);//这个10000不能动！
                    double[] s=FBBocCal.getBBS(BOC_ALPHA,BOC_BETA,t);
                    /**生成图像 */
                    if(rb1.isSelected()==false){
                        rtpanel.removeAll();
                        FBChartPanel cp= new FBChartPanel(t,s,600,400);
                        cp.setYscaleOn(false);      
                        cp.setLineWidth(6);               
                        rtpanel.add(cp);
                    }else{
                        if(chframe!=null){
                            chframe.setVisible(false);
                        }
                        chframe= new FBChartFrame(t,s,"BOC("+BOC_ALPHA+","+BOC_BETA+")");
                    }     
                }
            }
        });

        /**点击按键2绘制功率普 */
        b2.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0){
                    if(arg0.getSource()==b2){                        
                    /**首先获取BOC各项参数 */
                        try {
                                BOC_ALPHA=Integer.valueOf( tf_boc_alpha.getText());
                                BOC_BETA=Integer.valueOf( tf_boc_beta.getText());
                                BOC_BT=Double.valueOf(tf_BOC_BT_trans.getText())*1e6;
                                BOC_BR=Double.valueOf(tf_BOC_BT_recv.getText())*1e6;
                                if(BOC_ALPHA!=0&&BOC_BETA!=0&&BOC_BT>0&&BOC_BR>0){
                                    /**do nothing */
                                }else{
                                    JOptionPane.showMessageDialog(null, "请输入正确参数");
                                    return;
                                }
                            } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "请输入正确参数");
                            return;
                        }

                        double[] bw=FBDataGen.getLineSeq(-BOC_BT/2,BOC_BT/2,10000);//这个10000不能动！
                        double[] GBOC=FBBocCal.getGBOC(BOC_ALPHA,BOC_BETA,bw);
                        
                        if(rb1.isSelected()==false){
                            rtpanel.removeAll();   
                            FBChartPanel cp= new FBChartPanel(bw,GBOC,600,400);
                            rtpanel.add(cp);
                        }else{
                            if(chframe!=null){
                              //  chframe.setVisible(false);
                            }
                            chframe= new FBChartFrame(bw,GBOC,"归一化"+"BOC("+BOC_ALPHA+","+BOC_BETA+")的功率谱");
                        }
                    }
            }
        });
        /**点击按键3绘制自相关函数 */
        b3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(arg0.getSource()==b3){
                    /**首先获取BOC各项参数 */
                    try {
                        BOC_ALPHA=Integer.valueOf( tf_boc_alpha.getText());
                        BOC_BETA=Integer.valueOf( tf_boc_beta.getText());
                        BOC_BT=Double.valueOf(tf_BOC_BT_trans.getText())*1e6;
                        if(BOC_ALPHA!=0&&BOC_BETA!=0&&BOC_BT>0){
                            /**do nothing */
                        }else{
                            JOptionPane.showMessageDialog(null, "请输入正确参数");
                            return;
                        }
                    } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "请输入正确参数");
                    return;
                    }                
                    double[] bw=FBDataGen.getLineSeq(-BOC_BT/2,BOC_BT/2,1000);//这个1000不能动！
                    double[] GBOC=FBBocCal.getGBOC(BOC_ALPHA,BOC_BETA,bw);
                    double[] tau=FBDataGen.getLineSeq(-0.2e-6,0.2e-6,2000);
                    double[] Rt=FBBocCal.getRt(GBOC, bw,tau);
                    if(rb1.isSelected()==false){
                        rtpanel.removeAll();   
                        FBChartPanel cp= new FBChartPanel(tau,Rt,600,400);
                        rtpanel.add(cp);
                    }else{
                        if(chframe!=null){
                          //  chframe.setVisible(false);
                        }
                        chframe= new FBChartFrame(tau,Rt,"自相关函数"+"BOC("+BOC_ALPHA+","+BOC_BETA+")");
                    }                    
                }
            }
        });
    
        b4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(arg0.getSource()==b4){
                    /**首先获取BOC各项参数 */
                    try {
                        BOC_ALPHA=Integer.valueOf( tf_boc_alpha.getText());
                        BOC_BETA=Integer.valueOf( tf_boc_beta.getText());
                        BOC_BT=Double.valueOf(tf_BOC_BT_trans.getText())*1e6;
                        if(BOC_ALPHA!=0&&BOC_BETA!=0&&BOC_BT>0){
                            /**do nothing */
                        }else{
                            JOptionPane.showMessageDialog(null, "请输入正确参数");
                            return;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "请输入正确参数");
                        return;
                    }    
                    new FBTable(BOC_BT,BOC_BR);               
                }
            }
        });

        /**点击按键5截图*/
        b5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(arg0.getSource()==b5){  
                    if(chframe==null){
                        JOptionPane.showMessageDialog(null, "请先勾选弹出窗口按钮并点击频域图像按钮");
                    }else{
                        chframe.setVisible(true);
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
                    chframe.setLocation(0,0);
                    fc.showOpenDialog(fc);//浏览文件
                    if(fc.getSelectedFile()!=null){//没选则不执行
                        try {
                            Thread.sleep(500);                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        chframe.setAlwaysOnTop(true);
                        new FBSnapShot(fc.getSelectedFile().toPath().toString(),"png").snapShot(chframe.getLocation().x+10,chframe.getLocation().y,chframe.getSize().width-20,chframe.getSize().height-10);
                        chframe.setAlwaysOnTop(false);
                       }
                    }
                }
            }
        });

    }
}

class BpskPanel extends JPanel{

    private double BPSK_RC=1.023;
    private double BPSK_BT=30e6;//3M带宽
    private double BPSK_BC=24e6;//3M带宽
    private boolean CHART_FRAME_ENABLE=false;//弹窗式图像
    private FBChartFrame chframe;
    private JFileChooser fc;

    public BpskPanel(){

        JPanel lfpanel=new JPanel();
        JPanel rtpanel=new  JPanel();//BackgroundPanel(new ImageIcon("icon/CHART.png").getImage());


        JTextField tf_bpsk_rate=new JTextField("10.23");
        JTextField tf_bpsk_bw_trans=new JTextField("30");
        JTextField tf_bpsk_bw_recv=new JTextField("30");

        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(lfpanel);
        add(rtpanel);

        JButton b1=new JButton("时域图像",new ImageIcon("icon/TIME_ICON.png"));
        JButton b2=new JButton("频域图像",new ImageIcon("icon/FREQ_ICON.png"));
        JButton b3=new JButton("相关函数",new ImageIcon("icon/R.png"));
        JButton b4=new JButton("计算参数",new ImageIcon("icon/cal.png"));
        JButton b5=new JButton("保存截图",new ImageIcon("icon/SNAPSHOT.png"));
        JRadioButton rb1=new JRadioButton("弹出图像");
        JRadioButton rb2=new JRadioButton("打开通道2");

        lfpanel.setBorder(BorderFactory.createBevelBorder(1));//子面板设置边界
        rtpanel.setBorder(BorderFactory.createBevelBorder(1));

        lfpanel.setLayout(new BoxLayout(lfpanel,BoxLayout.Y_AXIS));


        lfpanel.setLayout(new BoxLayout(lfpanel,BoxLayout.Y_AXIS));
        /**下面加入按键与参数框 */
        JPanel lab_temp= new JPanel();//临时面板，最终将加入lfpanel
        lab_temp.setLayout(new BoxLayout(lab_temp, BoxLayout.Y_AXIS));
        lab_temp.add(Box.createRigidArea(new Dimension(0,10)));
        lab_temp.add(new JLabel("请设置参数:"));
        lab_temp.add(new JLabel("BPSK码率fc(MHz)"));
        lab_temp.add(new JLabel("发射带宽βt(MHz)"));
        lab_temp.add(new JLabel("接收带宽βr(MHz)"));
        lab_temp.add(Box.createRigidArea(new Dimension(0,10)));
            /**加入输入参数的面板 */        
            JPanel lab_input= new JPanel();//输入面板
            lab_input.setLayout(new BoxLayout(lab_input, BoxLayout.X_AXIS));
            lab_input.add(new JLabel("   fc=  "));
            tf_bpsk_rate.setMaximumSize(new Dimension(80,50));
            lab_input.add(tf_bpsk_rate);
            lab_temp.add(lab_input);
            


            lab_input= new JPanel();//输入面板
            lab_input.setLayout(new BoxLayout(lab_input, BoxLayout.X_AXIS));
            lab_input.add(new JLabel("   βt=  "));
            tf_bpsk_bw_trans.setMaximumSize(new Dimension(80,50));
            lab_input.add(tf_bpsk_bw_trans);
            lab_temp.add(lab_input);

            lab_input= new JPanel();//输入面板
            lab_input.setLayout(new BoxLayout(lab_input, BoxLayout.X_AXIS));
            lab_input.add(new JLabel("   βr=  "));
            tf_bpsk_bw_recv.setMaximumSize(new Dimension(80,50));
            lab_input.add(tf_bpsk_bw_recv);
            lab_temp.add(lab_input);

            lab_temp.setBorder(BorderFactory.createBevelBorder(1));
            lab_temp.add(Box.createRigidArea(new Dimension(10,40)));
            lab_temp.add(b1);
            lab_temp.add(Box.createRigidArea(new Dimension(10,10)));
            lab_temp.add(b2);
            lab_temp.add(Box.createRigidArea(new Dimension(10,10)));
            lab_temp.add(b3);
            lab_temp.add(Box.createRigidArea(new Dimension(10,10)));
            lab_temp.add(b4); 
            lab_temp.add(Box.createRigidArea(new Dimension(10,10)));
            lab_temp.add(b5); 
            lab_temp.add(Box.createRigidArea(new Dimension(10,10)));
        lfpanel.add(lab_temp);//一次性加入左边栏
        lfpanel.add(Box.createVerticalGlue()); 
        lfpanel.add(rb1);  
        lfpanel.add(rb2);  

        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(lfpanel);
        add(rtpanel);

        /**点击按键1绘制时域图像*/
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(arg0.getSource()==b1){

                }
            }
        });

        /**点击按键2绘制频域图像 */
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(arg0.getSource()==b2){
                    /**首先获取BPSK各项参数 */
                    try {
                        BPSK_RC=Double.valueOf( tf_bpsk_rate.getText())*1e6;
                        BPSK_BT=Double.valueOf(tf_bpsk_bw_trans.getText())*1e6;
                        BPSK_BC=Double.valueOf(tf_bpsk_bw_recv.getText())*1e6;
                        if(BPSK_BC>0&&BPSK_BT>0&&BPSK_RC>0){
                            /**do nothing */
                        }else{
                            JOptionPane.showMessageDialog(null, "请输入正确参数");
                            return;
                        }
                    } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "请输入正确参数");
                    return;
                    }  

                    double[] bw=FBDataGen.getLineSeq(-BPSK_BT/2,BPSK_BT/2,10000);//这个10000不能动！
                    double[] GBPSK=FBBocCal.getGBPSK(BPSK_RC, bw);
                    
                    if(rb1.isSelected()==false){
                        rtpanel.removeAll();   
                        FBChartPanel cp= new FBChartPanel(bw,GBPSK,600,400);
                        rtpanel.add(cp);
                    }else{
                          //  chframe.setVisible(false);
                          if(rb2.isSelected()==false){
                                chframe= new FBChartFrame(bw, FBTools.getdB(GBPSK),"归一化"+"BPSK,中心频率="+BPSK_RC/1e6+"MHz");
                            }else{
                                if(chframe!=null){
                                    chframe.setVisible(true);
                                    chframe.setCh2(bw,FBTools.getdB(GBPSK));//打开通道2
                                    chframe=null;
                                }else{
                                    chframe= new FBChartFrame(bw,FBTools.getdB(GBPSK),"归一化"+"BPSK,中心频率="+BPSK_RC/1e6+"MHz");
                                }
                            }                                            
                    }                    

                }
            }
        });

        /**点击按键3绘制自相关函数 */
        b3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(arg0.getSource()==b3){
                    /**首先获取BPSK各项参数 */
                    try {
                        BPSK_RC=Double.valueOf( tf_bpsk_rate.getText())*1e6;
                        BPSK_BT=Double.valueOf(tf_bpsk_bw_trans.getText())*1e6;
                        BPSK_BC=Double.valueOf(tf_bpsk_bw_recv.getText())*1e6;
                        if(BPSK_BC>0&&BPSK_BT>0&&BPSK_RC>0){
                            /**do nothing */
                        }else{
                            JOptionPane.showMessageDialog(null, "请输入正确参数");
                            return;
                        }
                    } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "请输入正确参数");
                    return;
                    }                      

                    double[] bw=FBDataGen.getLineSeq(-BPSK_BT/2,BPSK_BT/2,1000);//这个1000不能动！
                    double[] GBPSK=FBBocCal.getGBPSK(BPSK_RC,bw);
                    double[] tau=FBDataGen.getLineSeq(-0.2e-6,0.2e-6,2000);
                    double[] Rt=FBBocCal.getRt(GBPSK, bw,tau);
                    if(rb1.isSelected()==false){
                        rtpanel.removeAll();   
                        FBChartPanel cp= new FBChartPanel(tau,Rt,600,400);
                        rtpanel.add(cp);
                    }else{
                        if(rb2.isSelected()==false){
                            chframe= new FBChartFrame(tau,Rt,"自相关函数"+"BPSK,中心频率="+BPSK_RC/1e6+"MHz");
                        }else{
                            if(chframe!=null){
                                chframe.setVisible(true);
                                chframe.setCh2(tau,Rt);//打开通道2
                                chframe=null;
                            }else{
                                chframe= new FBChartFrame(tau,Rt,"自相关函数"+"BPSK,中心频率="+BPSK_RC/1e6+"MHz");
                            }
                        }                         
                    }                    
                }
            }
        });        

    }
}

