/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemie;

import java.util.ArrayList;

/**
 *
 * @author Alexis Robin
 */
public class EulerExplicite {
    private int P = 10000;
    private double k = 0.0001;
    private double h = 0.001;
    private int I0 = 200;
    
    /**
     * Get all the points for the graph
     * @param stop number of points
     * @return a list with all the points
     */
    public ArrayList<Double> getData(int stop){
        ArrayList<Double> res = new ArrayList<>();
        double Un = I0;
        res.add(Un);
        double UnPlusUn;
        for (int i = 0; i<stop; i++){
            UnPlusUn = Un + k*h*Un*(P-Un);
            res.add(UnPlusUn);
            Un = UnPlusUn;
        }
        
        return res;
        
    }
}
