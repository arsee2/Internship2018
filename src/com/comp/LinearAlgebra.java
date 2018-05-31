package com.comp;

public  class LinearAlgebra {
    public static void matrixMultiplication(float mat1[][],float mat2[][]){
        float answer[][] = new float[mat1.length][mat2[0].length];
        for (int i=0;i< mat1.length;i++){
            for (int j=0;j<mat2[0].length;j++){
                float ans=0;
                for (int f=0;f<mat1[0].length;f++){
                    System.out.print(mat1[i][f]+"*"+mat2[f][j]+"+");
                    ans=ans+mat1[i][f]*mat2[f][j];
                }
                System.out.print(" \n");
                answer[i][j]=ans;
            }
        }
        for (int i=0;i<answer.length;i++){
            for (int j=0;j<answer[0].length;j++){
                System.out.print(answer[i][j]+" ");
            }
            System.out.print("\n");
        }
    }
}
