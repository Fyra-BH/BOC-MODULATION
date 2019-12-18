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
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import fbcode.math.FBDataGen;

public class FBMainForm extends JFrame{

    public FBMainForm(){
        super("BOC 与 BPSK调制的分析");
        setSize(480,320);
        setVisible(true);
      //  setResizable(false);
        JTabbedPane jtp =new JTabbedPane();
        ImageIcon icon1=new ImageIcon("icon/1.png");
        ImageIcon icon2=new ImageIcon("icon/2.png");
        jtp.addTab("BOC", icon1,new BocPanel(),"BOC调制");
        jtp.addTab("BPSK", icon2, new BpskPanel(), "BPSK调制");
     
        getContentPane().add(jtp);
        validate();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    public static void main(String[] args) {
        System.out.println("test");
        new FBMainForm();
    }
}
/**
 * BOC栏
 */
class BocPanel extends JPanel{
    public BocPanel(){
        JButton b1=new JButton("时域图像");
        JButton b2=new JButton("频域图像");
        JButton b3=new JButton("计算参数");
        JPanel lfpanel=new JPanel();
        JPanel rtpanel=new JPanel();

        lfpanel.setLayout(new BoxLayout(lfpanel,BoxLayout.Y_AXIS));
        lfpanel.setBorder(BorderFactory.createBevelBorder(1));//子面板设置边界
        rtpanel.setBorder(BorderFactory.createBevelBorder(1));

        lfpanel.add(b1);
        lfpanel.add(b2);
        lfpanel.add(b3);
        lfpanel.add(Box.createVerticalGlue());

        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(lfpanel);
        add(rtpanel);

        // JSplitPane tbsplit =new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,lfpanel,rtpanel);
        // tbsplit.setDividerSize(20);
        // tbsplit.setOneTouchExpandable(true);
        // add(tbsplit);

        
    }
}
class BpskPanel extends JPanel{
    public BpskPanel(){
        JButton b1=new JButton("时域图像");
        JButton b2=new JButton("频域图像");
        JButton b3=new JButton("计算参数");
        add(b1);
        add(b2);
        add(b3);
    }
}

