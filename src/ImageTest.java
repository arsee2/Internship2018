import com.comp.LinearAlgebra;
import com.comp.PerspectiveDestortionRemoving;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class ImageTest {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable()
                               {
                                   public void run(){
                                       ImageFrame frame = new ImageFrame();
                                       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                       frame.setVisible(true);


                                   }
                               }
        );
    }
}

class ImageFrame extends JFrame{

    public ImageFrame(){
        setTitle("ImageTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        ImageComponent component = new ImageComponent();
        add(component);

    }

    public static final int DEFAULT_WIDTH = 700;
    public static final int DEFAULT_HEIGHT = 800;
}


class   ImageComponent extends JComponent{
    private static final long serialVersionUID = 1L;
    private BufferedImage image;
    //______
    public ImageComponent(){
        try{
            
           Graphics g ;

            image = ImageIO.read(new File("building.jpg"));
            //PerspectiveDestortionRemoving.remove(image);
            double [][] m={{1,2},{3,4}};
            double b [] ={1,2};
            double[]n=LinearAlgebra.solve(m,b);
            LinearAlgebra.printMatrix(n);
            System.out.print(n.length+"|");
            image.getGraphics().drawRect(0,0,100,100);


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void paintComponent (Graphics g){
        if(image == null) return;
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);

        g.drawImage(image, 10, 10, this);


    }

}