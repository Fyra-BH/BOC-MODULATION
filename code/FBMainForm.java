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

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;

import fbcode.math.FBDataGen;
import fbcode.gui.FBChartFrame;
import fbcode.gui.FBChartPanel;;

public class FBMainForm extends JFrame{

    public FBMainForm(){
        super("BOC 与 BPSK调制的分析");
        setSize(900,550);
        setMinimumSize(new Dimension(480,320));
        setLocation(600,300);
        setVisible(true);
        setResizable(false);
        JTabbedPane jtp =new JTabbedPane();
        jtp.addTab("BOC", new ImageIcon("icon/BOC_ICON.png"),new BocPanel(),"BOC调制");
        jtp.addTab("BPSK", new ImageIcon("icon/BPSK_ICON.png"), new BpskPanel(), "BPSK调制");
     
        getContentPane().add(jtp);
        validate();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    public static void main(String[] args) {
        new FBMainForm();
    }
}

class BackgroundPanel extends JPanel {  
           

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
    private boolean CHART_BOX_ENABLE=false;//弹窗式图像

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
        JRadioButton rb1=new JRadioButton("弹出图像");



        lfpanel.setLayout(new BoxLayout(lfpanel,BoxLayout.Y_AXIS));
        lfpanel.setBorder(BorderFactory.createBevelBorder(1));//子面板设置边界
        rtpanel.setBorder(BorderFactory.createBevelBorder(1));

        /**下面加入按键与参数框 */
        lfpanel.add(Box.createVerticalGlue());   
        lfpanel.add(Box.createRigidArea(new Dimension(20,20)));
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
        lfpanel.add(lab_temp);//一次性加入左边栏
                
        lfpanel.add(Box.createRigidArea(new Dimension(10,40)));
        lfpanel.add(b1);
        lfpanel.add(Box.createRigidArea(new Dimension(10,10)));
        lfpanel.add(b2);
        lfpanel.add(Box.createRigidArea(new Dimension(10,10)));
        lfpanel.add(b3); 
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

                    int a = BOC_ALPHA;
                    int b = BOC_BETA;
                    System.out.println("BOC_ALPHA="+BOC_ALPHA);
                    System.out.println("BOC_BETA="+BOC_BETA);
                    double fs=a*1.023e6;
                    double fc=b*1.023e6;
            
                     int n=(int)(2*fs/fc);
                     double[] f=FBDataGen.getLineSeq(-BOC_BW/2, BOC_BW/2,10000);
                     double[] GBOC=new double[f.length];
                     if(n%2==0){//偶数
                        double[] temp1=FBDataGen.getTanArray(FBDataGen.multi(f, Math.PI/2/fs)) ;
                        double[] temp2=FBDataGen.getSinArray(FBDataGen.multi(f, Math.PI/fc)) ;   
                        double[] temp=FBDataGen.multi(FBDataGen.multi(temp1, temp2), 1/Math.PI) ;
                        temp =FBDataGen.div(temp, f);
                        temp =FBDataGen.pow(temp, 2);
                        GBOC =FBDataGen.multi(temp, fc);
                        GBOC[0]=0;

                        if(rb1.isSelected()==false){
                            rtpanel.removeAll();
                            rtpanel.add(new FBChartPanel(f,GBOC,600,400));
                        }else{
                            new FBChartFrame(f, GBOC,"BOC("+BOC_ALPHA+","+BOC_BETA+")");
                        }
                     }else{//奇数
                        double[] temp1=FBDataGen.getTanArray(FBDataGen.multi(f, Math.PI/2/fs)) ;
                        double[] temp2=FBDataGen.getCosArray(FBDataGen.multi(f, Math.PI/fc)) ;   
                        double[] temp=FBDataGen.multi(FBDataGen.multi(temp1, temp2), 1/Math.PI) ;
                        temp =FBDataGen.div(temp, f);
                        temp =FBDataGen.pow(temp, 2);
                        GBOC =FBDataGen.multi(temp, fc);
                        GBOC[0]=0;
                        if(rb1.isSelected()==false){
                            rtpanel.removeAll();
                            rtpanel.add(new FBChartPanel(f,GBOC,600,400));
                        }else{
                            new FBChartFrame(f, GBOC,"BOC("+BOC_ALPHA+","+BOC_BETA+")");
                        }
                     } 
                }
            }
        });

        
    }
}
class BpskPanel extends JPanel{

    public BpskPanel(){

        JPanel lfpanel=new JPanel();
        JPanel rtpanel=new JPanel();

        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(lfpanel);
        add(rtpanel);

        JButton b1=new JButton("时域图像");
        JButton b2=new JButton("频域图像");
        JButton b3=new JButton("计算参数");
        lfpanel.setLayout(new BoxLayout(lfpanel,BoxLayout.Y_AXIS));
        lfpanel.setBorder(BorderFactory.createBevelBorder(1));
        
        lfpanel.add(b1);
        lfpanel.add(b2);
        lfpanel.add(b3);
        lfpanel.add(Box.createVerticalGlue());
    }
}

