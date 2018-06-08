package com.comp;

import javafx.util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LSD {
    int g[][];
    double angles[][];
    double S =0.8;
    int p=1;
    int amountOfBins=1024;
    Pair<Integer,Integer> highest[];

    public  LSD(BufferedImage img){
        int image[][]=scale(toGray(img),S);
         gradient(image);
         pseudoOrder();

    }
    public int[][] scale(int[][] img,double factor){
        int ans [][]= new int [(int)Math.floor(img.length*factor)][(int)Math.floor(img[0].length*factor)];
        for (int i=0;i<img.length;i++){
            for (int j=0;j<img[0].length;j++){
                ans[(int)Math.floor(i*factor)][(int)Math.floor(j*factor)]=img[i][j];
            }
        }
        return ans;
    }
    private int grayToRGB(int i){
        int j = Math.abs(i);
        j = Math.min(255,j);

        Color color =new Color(j,j,j);

        return color.getRGB();
    }
    public BufferedImage gradientVisualisation(){
        BufferedImage image = new BufferedImage(g.length,g[0].length,BufferedImage.TYPE_INT_RGB);
        for (int i=0;i<g.length;i++){
            for (int j=0;j<g[0].length;j++){
                image.setRGB( i,j,grayToRGB(g[i][j]));

            }
        }
        return image;
    }
    private int computeGx(int xy,int xxy,int xyy,int xxyy){
        return Math.round((xxy+xxyy-xy-xyy)/2);
    }
    private int computeGy(int xy,int xxy,int xyy,int xxyy){
        return Math.round((xyy+xxyy-xy-xxy)/2);
    }
    public void  gradient(int[][] img){
        angles = new double[img.length-1][img[0].length-1];
        g = new int[img.length-1][img[0].length-1];
        for (int i=0;i<img.length-1;i++){
            for (int j=0;j<img[0].length-1;j++){
                int gx =computeGx(img[i][j],img[i][j+1],img[i+1][j],img[i+1][j+1]);
               int  gy=computeGy(img[i][j],img[i][j+1],img[i+1][j],img[i+1][j+1]);
                g[i][j]=(int)Math.sqrt(gx*gx+gy*gy);
                angles[i][j] = Math.atan(-1.0*gx/gy);
                if (g[i][j]<p){
                    g[i][j]=0;
                }
                if (g[i][j]>255){
                    g[i][j]=255;
                }
            }
        }
    }
    private void pseudoOrder(){
        int bins [] =new int [256];
        for (int i=0;i<g.length;i++){
            for (int j=0;j<g[0].length;j++){
                bins[g[i][j]]++;
            }
        }

        int ordered[] = new int [amountOfBins];

        int ind =255;
        for (int sum=0;sum<amountOfBins;){
            int ost =  amountOfBins-sum;
            for (int i=0;i<Math.min(ost,bins[ind]);i++){
                ordered[sum+i]=ind;
            }
            sum+=Math.min(ost,bins[ind]);
            ind--;

        }
        int last = ordered[amountOfBins-1];
        int num = 0;
        for (int i=amountOfBins-1;last==ordered[i];i--){
            num++;
        }
        ind =0;
        highest = new Pair[amountOfBins];
        for (int i=0;i<g.length;i++){
            for (int j=0;j<g[0].length;j++){
                if (g[i][j]>last){
                    highest[ind]= new Pair<>(i,j);
                    ind++;
                }
                if (num>0&&g[i][j]==last){
                    num--;
                    highest[ind]= new Pair<>(i,j);
                    ind++;
                }

            }
        }

    }
    private int convert (int rgb){
        Color color = new Color(rgb);
        return (int)Math.floor(color.getBlue()*0.11+color.getRed()*0.30+color.getGreen()*0.59);
    }
    public int[][] toGray(BufferedImage img){
        int[][]ans = new int[img.getWidth()][img.getHeight()];
        for (int i=0;i<ans.length;i++){
            for (int j=0;j<ans[0].length;j++){
                ans[i][j] = convert(img.getRGB(i,j));
            }
        }
        return  ans;
    }

}
