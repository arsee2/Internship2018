package com.comp;

import javafx.util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Arrays;

public class NormalisedImage{
    double width;
    double height;
    double S;
    BufferedImage img;
    NormalisedImage(BufferedImage img){
        width =img.getWidth();
        height = img.getHeight();
        S = Math.max(width,height);
        this.img = img;
    }
    private Pair<Double,Double> norm(int x,int y){

        return new Pair<Double, Double>((2*x-width)/1.0/S,(2*y-height)/1.0/S);
    }
    private Pair<Integer,Integer> back(Double x,Double y){
        int xx = (int)Math.round((S*x+width)/2);
        int yy = (int)Math.round((S*y+height)/2);

        return new Pair<Integer, Integer>(xx,yy);
    }
    private Pair<Integer,Integer>pixelTransform(int i,int j,double[][] matrix){
        Pair<Double,Double> p = norm(i,j);
        double vec[] = {p.getKey(),p.getValue(),1};
        double vec2 [] = LinearAlgebra.matrixAndVectorMultiplication(matrix,vec);
        Pair<Integer,Integer> p2 = back(vec2[0]/vec2[2],vec2[1]/vec2[2]);
        int x =(int) Math.round(p2.getKey());
        int y = (int)Math.round(p2.getValue());
        return  new Pair<>(x,y);
    }
    private BufferedImage colors(BufferedImage img){
        BufferedImage img2 = img;
        for (int i=3;i<img2.getWidth()-4;i++){
            for (int j=3;j<img2.getHeight()-4;j++){

                if (Math.abs(img2.getRGB(i,j)+16454894)>=0){
                    int distance =7;
                    int r=0,g=0,b=0;
                    for (int f=-2;f<3;f++){
                        for (int h=-2;h<3;h++){
                            if (f!=0&&h!=0){

                                Color color = new Color(img2.getRGB(i+f,j+h));
                                r+=color.getRed()/(f*f+h*h);
                                b+=color.getBlue()/(f*f+h*h);
                                g+=color.getGreen()/(f*f+h*h);
                            }
                        }
                    }
                    Color c  =new Color(r/7,g/7,b/7);
                    img2.setRGB(i,j,c.getRGB());
                }
            }
        }
        return img2;

    }
    
    private BufferedImage crop(BufferedImage img){
        int minx=img.getWidth()-1,miny = img.getHeight()-1,maxx = 0,maxy = 0;
        int threshold =0;
        for (int i=1;i<img.getWidth()-1;i++){
            for (int j=1;j<img.getHeight()-1;j++){
                int count=0;
                for (int f=-1;f<2;f++){
                    for (int h=-1;h<2;h++){
                        if (Math.abs(img.getRGB(i+f,j+h)+16454894)<100){
                            count++;
                        }
                    }
                }
                if (count>=threshold){
                    minx = Math.min(minx,i);
                    miny = Math.min(miny,j);
                    maxx = Math.max(maxx,i);
                    maxy = Math.max(maxy,j);
                }
            }
        }
        BufferedImage img2 = new BufferedImage(maxx-minx,maxy-miny,BufferedImage.TYPE_INT_RGB);
        for (int i=0;i<img2.getWidth();i++){
            for (int j=0;j<img2.getHeight();j++){
                img2.setRGB(i,j,img.getRGB(i+minx,j+miny));
            }
        }

        return img2;
    }

    public BufferedImage applyMatrix(double[][] matrix){
        int arX [] = {pixelTransform(0,0,matrix).getKey(),pixelTransform(0,img.getHeight()-1,matrix).getKey(),
                pixelTransform(img.getWidth()-1,0,matrix).getKey(),pixelTransform(img.getWidth()-1,img.getHeight()-1,matrix).getKey()};
        int arY [] = {pixelTransform(0,0,matrix).getValue(),pixelTransform(0,img.getHeight()-1,matrix).getValue(),
                pixelTransform(img.getWidth()-1,0,matrix).getValue(),pixelTransform(img.getWidth()-1,img.getHeight()-1,matrix).getValue()};

        int minx=Integer.MAX_VALUE,miny=Integer.MAX_VALUE,maxx=Integer.MIN_VALUE,maxy=Integer.MIN_VALUE;
        for (int i=0;i<4;i++){
            minx = Math.min(minx,arX[i]);
            miny = Math.min(miny,arY[i]);
            maxx = Math.max(maxx,arX[i]);
            maxy = Math.max(maxy,arY[i]);
        }
        /*Arrays.sort(arX);
        Arrays.sort(arY);
        minx=arX[1];
        maxx=arX[2];
        miny=arY[1];
        maxy=arY[2];*/
        BufferedImage img2 = new BufferedImage(2000,2000,BufferedImage.TYPE_INT_RGB);
        System.out.print(minx+"|"+miny+"|"+maxx+"|"+maxy+"|\n");
        for (int i=0;i<img2.getWidth();i++){
            for (int j=0;j<img2.getHeight();j++){
                img2.setRGB(i,j,322322);

            }
        }

        for (int i=0;i<img.getWidth();i++){
            for (int j=0;j<img.getHeight();j++){

                int x = pixelTransform(i,j,matrix).getKey();
                int y = pixelTransform(i,j,matrix).getValue();

                try {
                    img2.setRGB(x+200,y+200,img.getRGB(i,j));
                }catch (Exception e){

                  //  System.out.print(x+2+" "+y+"\n");
                }



            }
        }
        img2 =colors(img2);
        System.out.print(img2.getRGB(1666,1666));
        return img2;
    }

}