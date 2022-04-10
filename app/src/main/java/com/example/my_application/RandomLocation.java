package com.example.my_application;

public class RandomLocation {
    public static double[] generate(){
        double ans[] = new double[2] ;
        double min = -0.1;
        double max = 0.1;

        ans[0] = min + ((max-min)*Math.random());
        ans[1] = min + ((max-min)*Math.random());

        return ans;
    }
}
