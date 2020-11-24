/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epidemie;

/**
 *
 * @author Alexis Robin
 */
public class EulerExplicite {
    private int P = 10000;
    private double k = 0.0001;
    private double h = 0.01;
    private int I0 = 200;
    
    public void printResult(int stop){
        double Un = I0;
        double UnPlusUn;
        for (int i = 0; i<stop; i++){
            UnPlusUn = Un + k*h*Un*(P-Un);
            System.out.println(UnPlusUn + "\n");
            Un = UnPlusUn;
        }
        
    }
}
