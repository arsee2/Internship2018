package com.comp;

import javafx.util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PerspectiveDestortionRemoving {
    public static Pair<double[][], double[]> getMatrix(Point p11, Point p12, Point p21, Point p22) {
        Point p11h = new Point(p11.x, p11.y);
        Point p12h = new Point(p11.x, p12.y);
        Point p21h = new Point(p21.x, p21.y);
        Point p22h = new Point(p21.x, p22.y);
        Point points[] = {p11, p12, p21, p22};
        Point pointsh[] = {p11h, p12h, p21h, p22h};
        double matrix[][] = new double[8][8];
        double vector[] = new double[8];

        for (int i = 0; i < pointsh.length; i++) {
            matrix[i * 2][0] = -pointsh[i].y;
            matrix[i * 2][1] = -1;
            matrix[i * 2][5] = pointsh[i].x * points[i].x;
            matrix[i * 2][6] = pointsh[i].y * points[i].x;
            matrix[i * 2][7] = points[i].x;
            vector[i * 2] = pointsh[i].x;

            matrix[i * 2 + 1][2] = -pointsh[i].x;
            matrix[i * 2 + 1][3] = -pointsh[i].y;
            matrix[i * 2 + 1][4] = -1;
            matrix[i * 2 + 1][5] = pointsh[i].x * points[i].y;
            matrix[i * 2 + 1][6] = pointsh[i].y * points[i].y;
            matrix[i * 2 + 1][7] = points[i].y;

        }
        /*for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("|"+vector[i]);
            System.out.print("\n");
        }*/
        return new Pair<>(matrix,vector);
    }

    public static BufferedImage remove(BufferedImage i) throws Unsolvable {

        double x11 = 634.0260655160307,
                y11 = 1067-672.1241170908934,
                x12 = 608.4425576092435,
                y12 =1067-523.0723753730896,
                x21 = 926.9209508015505,
                y21 =1067- 611.3228302929783,
                x22 = 938.2421227197373,
                y22 = 1067-516.5080154781632;
        /*double x11 = 190,
                y11 = 144,
                x12 = 536,
                y12 =145,
                x21 = 37,
                y21 =373,
                x22 = 658,
                y22 = 372;*/
            double h = i.getHeight();
            double w =i.getWidth();
            double S = Math.max(w,h);
       Point p11 = new Point((2*x11-w)/S,(2*(h-y11)-h)/S);
        Point p12 = new Point((2*x12-w)/S,(2*(h-y12)-h)/S);
        Point p21 = new Point((2*x21-w)/S,(2*(h-y21)-h)/S);
        Point p22 = new Point((2*x22-w)/S,(2*(h-y22)-h)/S);
       /* Point p11 = new Point(x11,y11);
        Point p12 = new Point(x12,y12);
        Point p21 = new Point(x21,y21);
        Point p22 = new Point(x22,y22);*/

        double[][] A = getMatrix(p11,p12,p21,p22).getKey();
        double[] b = getMatrix(p11,p12,p21,p22).getValue();
        double x []= LinearAlgebra.solve(A,b);
        double matrixH [][] = {{1,x[0],x[1]},{x[2],x[3],x[4]},{x[5],x[6],x[7]}};
       /* for (int j=0;j<matrixH.length;j++){
            for (int f=0;f<matrixH[0].length;f++){
                System.out.print("|"+matrixH[j][f]+"|");
            }
            System.out.print("\n");
        }*/
        double inv [][] = LinearAlgebra.invert(matrixH);
        NormalisedImage n  = new NormalisedImage(i);
        i.getGraphics().drawOval((int)x11-5,(int)y11-5,10,10);
        i.getGraphics().drawOval((int)x12-5,(int)y12-5,10,10);
        i.getGraphics().drawOval((int)x21-5,(int)y21-5,10,10);
        i.getGraphics().drawOval((int)x22-5,(int)y22-5,10,10);
        //return i;
        return n.applyMatrix(matrixH);


    }
}