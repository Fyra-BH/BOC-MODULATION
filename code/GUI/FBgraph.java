import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.color;

import javax.swing.JFrame;

import javafx.scene.paint.Color;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FBgraph extends Canvas{
    public FBgraph(){
        super();
        setLocation(100,100);
        setSize(400,300);
    }
    public void paint(Graphics g){
        g.fillRect(100, 300, 400, 300);
    }
    public static void main(String[] args) {
        System.out.println("OK");
        JFrame frame=new JFrame("Graph test");
        frame.setSize(400,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        FBgraph gobj=new FBgraph();
        frame.add(gobj);
    }
}