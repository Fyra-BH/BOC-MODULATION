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

import fbcode.math.FBDataGen;
import fbcode.math.FBBocCal;
import fbcode.gui.FBChartFrame;
import fbcode.gui.FBChartPanel;
import fbcode.tools.FBSnapShot;

public class FBMainForm extends JFrame{


    public FBMainForm(){
        super("BOC 与 BPSK调制的分析");
        setSize(900,550);
        setMinimumSize(new Dimension(480,320));
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
    private double BOC_BW=3.0e6;//3M带宽
    private boolean CHART_FRAME_ENABLE=false;//弹窗式图像
    private FBChartFrame chframe;
    private JFileChooser fc=new JFileChooser("./snapshot/");//用于选择保存文件

    public FBChartFrame getChartFrame(){
        return chframe;
    }

    public BocPanel(){
        JPanel lfpanel=new JPanel();
        JPanel rtpanel=new  BackgroundPanel(new ImageIcon("icon/CHART.png").getImage());
       // rtpanel.add(new BackgroundPanel(new ImageIcon("icon/CHART.png").getImage()));

        JTextField tf_boc_alpha=new JTextField("10");
        JTextField tf_boc_beta=new JTextField("5");
        JTextField tf_boc_bw=new JTextField("30");

        JButton b1=new JButton("时域图像",new ImageIcon("icon/TIME_ICON.png"));
        JButton b2=new JButton("频域图像",new ImageIcon("icon/FREQ_ICON.png"));
        JButton b3=new JButton("计算参数",new ImageIcon("icon/cal.png"));
        JButton b4=new JButton("保存截图",new ImageIcon("icon/SNAPSHOT.png"));
        JRadioButton rb1=new JRadioButton("弹出图像");

        lfpanel.setBorder(BorderFactory.createBevelBorder(1));//子面板设置边界
        rtpanel.setBorder(BorderFactory.createBevelBorder(1));

        lfpanel.setLayout(new BoxLayout(lfpanel,BoxLayout.Y_AXIS));
        /**下面加入按键与参数框 */
        JPanel lab_temp= new JPanel();//临时面板，最终将加入lfpanel
        lab_temp.setLayout(new BoxLayout(lab_temp, BoxLayout.Y_AXIS));
        lab_temp.add(Box.createRigidArea(new Dimension(0,10)));
        lab_temp.add(new JLabel("请设置参数:"));
        lab_temp.add(new JLabel("BOC(α,β)"));
        lab_temp.add(new JLabel("带宽bw(MHz)"));
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
            lab_input.add(new JLabel("   bw="));
            tf_boc_bw.setMaximumSize(new Dimension(60,40));
            lab_input.add(tf_boc_bw);
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
        lfpanel.add(lab_temp);//一次性加入左边栏
        lfpanel.add(Box.createVerticalGlue()); 
        lfpanel.add(rb1);  

        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(lfpanel);
        add(rtpanel);

        /**点击按键2绘制功率普 */
        b2.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0){
                    if(arg0.getSource()==b2){                        
                    /**首先获取BOC各项参数 */
                        try {
                                if(Integer.valueOf( tf_boc_alpha.getText())==0){
                                    JOptionPane.showMessageDialog(null, "请输入正确参数");
                                    return;
                                }else{
                                    BOC_ALPHA=Integer.valueOf( tf_boc_alpha.getText());
                                }      
                                if(Integer.valueOf( tf_boc_beta.getText())==0){
                                    JOptionPane.showMessageDialog(null, "请输入正确参数");
                                    return;
                                }else{
                                    BOC_BETA=Integer.valueOf( tf_boc_beta.getText());
                                }  
                                if(Double.valueOf(tf_boc_bw.getText())<=0){
                                    JOptionPane.showMessageDialog(null, "请输入正确参数");
                                    return;
                                }else{
                                    BOC_BW=Double.valueOf(tf_boc_bw.getText())*1e6;
                                } 
                            } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "请输入正确参数");
                            return;
                        }

                        double[] GBOC=FBBocCal.getGBOC(BOC_ALPHA,BOC_BETA,BOC_BW);
                        double[] f=FBDataGen.getLineSeq(-BOC_BW/2,BOC_BW/2,10000);//这个10000不能动！
                        if(rb1.isSelected()==false){
                            rtpanel.removeAll();
                            rtpanel.add(new FBChartPanel(f,GBOC,600,400));
                        }else{
                            chframe= new FBChartFrame(f,GBOC,"BOC("+BOC_ALPHA+","+BOC_BETA+")");
                        }
                    }
            }
        });
    
        /**点击按键4截图*/
        b4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(arg0.getSource()==b4){  
                    if(chframe==null){
                        JOptionPane.showMessageDialog(null, "请先勾选弹出窗口按钮并点击频域图像按钮");
                    }else{

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
                        fc.showOpenDialog(fc);//浏览文件


                        chframe.setAlwaysOnTop(true);
                        chframe.setLocation(0,0);
                        try {
                            Thread.sleep(400);                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        new FBSnapShot(fc.getSelectedFile().toPath().toString(),"png").snapShot(chframe.getLocation().x+10,chframe.getLocation().y,chframe.getSize().width-20,chframe.getSize().height-10);
                        chframe.setAlwaysOnTop(false);
                    }
                }
            }
        });

    }
}

class BpskPanel extends JPanel{

    public BpskPanel(){

        JPanel lfpanel=new JPanel();
        JPanel rtpanel=new  BackgroundPanel(new ImageIcon("icon/CHART.png").getImage());

        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(lfpanel);
        add(rtpanel);

        JButton b1=new JButton("时域图像",new ImageIcon("icon/TIME_ICON.png"));
        JButton b2=new JButton("频域图像",new ImageIcon("icon/FREQ_ICON.png"));
        JButton b3=new JButton("计算参数",new ImageIcon("icon/cal.png"));

        lfpanel.setBorder(BorderFactory.createBevelBorder(1));//子面板设置边界
        rtpanel.setBorder(BorderFactory.createBevelBorder(1));

        lfpanel.setLayout(new BoxLayout(lfpanel,BoxLayout.Y_AXIS));
        /**下面加入按键与参数框 */
        
        lfpanel.add(b1);
        lfpanel.add(b2);
        lfpanel.add(b3);
        lfpanel.add(Box.createVerticalGlue());
    }
}

