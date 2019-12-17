 /**
 * @author Fyra
 * @version 1.0
 * 
 * 主界面（待完成）
 */
package fbcode.gui;

import fbcode.tools.FBConsole;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fbcode.math.FBDataGen;

public class FBMainForm extends JFrame{

    public FBMainForm(){

    }
    public static void main(String[] args) {
        System.out.println("test");
        new FBMainForm();
    }
}

class BocPanel extends JPanel{
    public BocPanel(){
        JButton b1=new JButton("");
    }
}

