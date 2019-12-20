package fbcode.tools;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
 
import javax.imageio.ImageIO;


/**用于截图的类 */
//参考了https://www.cnblogs.com/ning-blogs/p/7247747.html
public class FBSnapShot{

    private String filePreStr="./"; // 默认前缀（选择存储路径例如： D：\\）
    private String defName = "cameraImg";  // 默认截图名称
    private String imageFormat; // 图像文件的格式
    private String defaultImageFormat = "png"; //截图后缀
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //获取全屏幕的宽高尺寸等数据

    public FBSnapShot() {
        filePreStr = defName;
        imageFormat = defaultImageFormat;
    }
 
    public FBSnapShot(String s, String format) {
        filePreStr = s;
        imageFormat = format;
    }

    public void snapShot() {
        try {
            // *** 核心代码 *** 拷贝屏幕到一个BufferedImage对象screenshot
            BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));
            // 根据文件前缀变量和文件格式变量，自动生成文件名
            String name = filePreStr+"." + imageFormat;
            File f = new File(name);
            System.out.print("Save File " + name);
            // 将screenshot对象写入图像文件
            ImageIO.write(screenshot, imageFormat, f);
            System.out.print("..Finished!\n");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }// 运行之后，即可将全屏幕截图保存到指定的目录下面

    public void snapShot(int x,int y,int w,int h) {
        try {
            // *** 核心代码 *** 拷贝屏幕到一个BufferedImage对象screenshot
            BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));            
            screenshot=screenshot.getSubimage(x,y,w,h);
            // 根据文件前缀变量和文件格式变量，自动生成文件名
            String name = filePreStr+"." + imageFormat;
            File f = new File(name);
            System.out.print("Save File " + name);
            // 将screenshot对象写入图像文件
            ImageIO.write(screenshot, imageFormat, f);
            System.out.print("..Finished!\n");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }// 运行之后，即可将全屏幕截图保存到指定的目录下面

    public static void main(String[] args) {
        FBSnapShot cam = new FBSnapShot("snapshot/1", "png");//
        cam.snapShot();
    }

}
