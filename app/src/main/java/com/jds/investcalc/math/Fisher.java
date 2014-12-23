package com.jds.investcalc.math;

/**
 * Created by eduardo on 22/12/14.
 */
public class Fisher {
    public static float realTax(float inflation, float interest){
        /*
        Fisher equation to determine the real interest tax discounting the inflation;
         */

        float r = (1.0f + interest) / (1.0f + inflation);
        r -= 1.0f;
        return r;
    }
}
