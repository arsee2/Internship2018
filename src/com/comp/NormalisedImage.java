package com.comp;

import javafx.util.Pair;

import java.awt.image.BufferedImage;

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

        return new Pair<Double, Double>((2*x-width)/1.0/S,(2*(height-y)-height)/1.0/S);
    }
    private Pair()
    public BufferedImage applyMatrix(double[][] matrix){
        BufferedImage img2 = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);

    }
}