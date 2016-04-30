/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses.SVM;

/**
 *
 * @author Administrator
 */
public class Data {
    public int target;
    public double [] vectorData;
    public double alpha;
    public Data (int y,double [] vector){
        vectorData = vector;
        target=y;
        alpha=0;
    }
}
