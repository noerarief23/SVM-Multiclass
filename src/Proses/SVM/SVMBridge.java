/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses.SVM;

<<<<<<< HEAD
=======
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import com.sun.org.apache.xpath.internal.operations.And;
import java.io.IOException;
>>>>>>> origin/master
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class SVMBridge {
    //struktur data untuk Abstrak 
    private ArrayList<Abstrak> abstrak;
    private ArrayList<Abstrak> abstrakTesting;
    private HashMap<String,ArrayList<Abstrak>> kata;
    
    //struktur data untuk visualisai
    private ArrayList<String> kataA;
    private ArrayList<String> kataB;
<<<<<<< HEAD
=======
    
>>>>>>> origin/master
    private ArrayList<String> kataC;
    private ArrayList<String> kataD;
    private ArrayList<String> kataE;
    
<<<<<<< HEAD
//model svm
=======
    //model svm
>>>>>>> origin/master
    Kernel SVMmodel;
    SVM smoSimple;
    
    public SVMBridge(){
        abstrak = new ArrayList<Abstrak>();
        abstrakTesting = new ArrayList<Abstrak>();
        kata = new HashMap<String,ArrayList<Abstrak>>();
    }

    //struktur data kata dan Abstrak
    public void addAbstrakFile(Abstrak abs){
        abstrak.add(abs);
        WeigthedTfIdf();
        for(String s : abs.setKata())
            if(!kata.containsKey(s)){
                ArrayList<Abstrak> newS = new ArrayList<Abstrak>();
                newS.add(abs);
                kata.put(s,newS);
            }
            else{
                ArrayList<Abstrak> old = kata.get(s);
                if(!old.contains(abs))
                    old.add(abs);
            }
    }
    public void removeAbstrakFile(Abstrak abs){
        abstrak.remove(abs);
        for(String s : abs.setKata())
            if(kata.containsKey(s)){
                ArrayList<Abstrak> old = kata.get(abs);
                abstrak.remove(abs);
                if(kata.get(s).size()==0)
                    kata.remove(s);
            }
    }
    public Abstrak addTesting(Abstrak te){
        //tf-idf
        for(String s : te.setKata()){
            if(kata.containsKey(s))
                te.setBobot(s, Math.log10(abstrak.size()/kata.get(s).size()));
            else
                te.setBobot(s, 0);
        }
        te.setBobotNormal();
        abstrakTesting.add(te);
        return te;
    }
    public void removeTesting(){
        abstrakTesting = new ArrayList<Abstrak>();
    }
    public int getIndexKata(String kata){
        int i=1;
        for(String kat : this.kata.keySet()){
            if(kat.equals(kata))
                return i;
            i++;
        }
        return -1;
    }
    public int getSumKata(String kata){
        int sum = 0;
        ArrayList<Abstrak> bb = this.kata.get(kata);
        for(Abstrak a : bb)
            sum+=a.frekuensi(kata);
        return sum;
    }

    //Visualisasi Data
    private void listKata(){
<<<<<<< HEAD
        kataB = new ArrayList<String>();
        kataA = new ArrayList<String>();
        kataC = new ArrayList<String>();
        kataD = new ArrayList<String>();
        kataE = new ArrayList<String>();
        for(String kt : kata.keySet()){
            double aFrek=0;
            double bFrek=0;
            double cFrek=0;
            double dFrek=0;
            double eFrek=0;
=======
        kataA = new ArrayList<String>();
        kataB = new ArrayList<String>();
        kataC = new ArrayList<String>();
        kataD = new ArrayList<String>();
        kataE = new ArrayList<String>();
        
        for(String kt : kata.keySet()){
            double AFrek=0;
            double BFrek=0;
            double CFrek=0;
            double DFrek=0;
            double EFrek=0;
>>>>>>> origin/master
            for(Abstrak bb : kata.get(kt)){
                if(bb.getId()==0)
                    continue;
                if(bb.getkelas().equalsIgnoreCase("A"))
<<<<<<< HEAD
                    aFrek+=bb.getBobot().get(kt);
                else
                if(bb.getkelas().equalsIgnoreCase("B"))
                    bFrek+=bb.getBobot().get(kt);
                else
                if(bb.getkelas().equalsIgnoreCase("C"))
                    cFrek+=bb.getBobot().get(kt);
                else
                if(bb.getkelas().equalsIgnoreCase("D"))
                    dFrek+=bb.getBobot().get(kt);
                else
                    eFrek+=bb.getBobot().get(kt);
            }
=======
                    AFrek+=bb.getBobot().get(kt);
                else
                if(bb.getkelas().equalsIgnoreCase("B"))
                    BFrek+=bb.getBobot().get(kt);
                else
                if(bb.getkelas().equalsIgnoreCase("C"))
                    CFrek+=bb.getBobot().get(kt);
                else
                if(bb.getkelas().equalsIgnoreCase("D"))
                    DFrek+=bb.getBobot().get(kt);
                else
                if(bb.getkelas().equalsIgnoreCase("E"))
                    EFrek+=bb.getBobot().get(kt);
            }
            //Aslinya
            //if(csFrek<isFrek)
              //  kataIS.add(kt);
            //else if(csFrek>isFrek)
              //  kataCS.add(kt);
            /*
            if((isFrek>csFrek)||(isFrek>itFrek)||(isFrek>seFrek))
                kataIS.add(kt);
            else if((csFrek>isFrek)||(csFrek>itFrek)||(csFrek>seFrek))
                kataCS.add(kt);
            else if((itFrek>isFrek)||(itFrek>csFrek)||(itFrek>seFrek))
                kataIT.add(kt);
            else if((seFrek>isFrek)||(seFrek>csFrek)||(seFrek>itFrek))
                kataSE.add(kt);  
              */
            if((AFrek>BFrek)||(AFrek>CFrek)||(AFrek>DFrek)||(AFrek>EFrek))
                kataA.add(kt);
            else if((BFrek>AFrek)||(BFrek>CFrek)||(BFrek>DFrek)||(BFrek>EFrek))
                kataB.add(kt);
            else if((CFrek>AFrek)||(CFrek>BFrek)||(CFrek>DFrek)||(CFrek>EFrek))
                kataC.add(kt);
            else if((DFrek>AFrek)||(DFrek>BFrek)||(DFrek>CFrek)||(DFrek>EFrek))
                kataD.add(kt);
            else if((EFrek>AFrek)||(EFrek>BFrek)||(EFrek>CFrek)||(EFrek>DFrek))
                kataE.add(kt);
            
            //lse if(csFrek<seFrek)
             //   kataSE.add(kt);
            //else if(csFrek>seFrek)
             //   kataCS.add(kt);
            
            //if(isFrek<itFrek)
             //   kataIT.add(kt);
            //else if(isFrek>itFrek)
              //  kataIS.add(kt);
            /*
            else if(isFrek<seFrek)
                kataSE.add(kt);
            else if(isFrek>seFrek)
                kataIS.add(kt);
>>>>>>> origin/master
            
            if((aFrek>bFrek)||(aFrek>cFrek)||(aFrek>dFrek)||(aFrek>eFrek))
                kataA.add(kt);
            else if((bFrek>aFrek)||(bFrek>cFrek)||(bFrek>dFrek)||(bFrek>eFrek))
                kataB.add(kt);
            else if((cFrek>aFrek)||(cFrek>bFrek)||(cFrek>dFrek)||(cFrek>eFrek))
                kataC.add(kt);
            else if((dFrek>aFrek)||(dFrek>bFrek)||(dFrek>cFrek)||(dFrek>eFrek))
                kataD.add(kt);
            else if((eFrek>aFrek)||(eFrek>bFrek)||(eFrek>cFrek))
                kataE.add(kt);
        }
    }
<<<<<<< HEAD
    
=======
>>>>>>> origin/master
    public int countKelasA(){
        int hasil=0;
        for(Abstrak bb : abstrak)
            if(bb.getkelas().equalsIgnoreCase("A"))
                hasil++;
        return hasil;
    }
    public int countKelasATesting(){
        int hasil=0;
        for(Abstrak bb : abstrakTesting)
            if(bb.getkelas().equalsIgnoreCase("A"))
                hasil++;
        return hasil;
    }
    public int countKelasB(){
        int hasil=0;
        for(Abstrak bb : abstrak)
            if(bb.getkelas().equalsIgnoreCase("B"))
                hasil++;
        return hasil;
    }
    public int countKelasBTesting(){
        int hasil=0;
        for(Abstrak bb : abstrakTesting)
            if(bb.getkelas().equalsIgnoreCase("B"))
                hasil++;
        return hasil;
    }
    public int countKelasC(){
        int hasil=0;
        for(Abstrak bb : abstrak)
            if(bb.getkelas().equalsIgnoreCase("C"))
                hasil++;
        return hasil;
    }
    public int countKelasCTesting(){
        int hasil=0;
        for(Abstrak bb : abstrakTesting)
            if(bb.getkelas().equalsIgnoreCase("C"))
                hasil++;
        return hasil;
    }
    public int countKelasD(){
        int hasil=0;
        for(Abstrak bb : abstrak)
            if(bb.getkelas().equalsIgnoreCase("D"))
                hasil++;
        return hasil;
    }
    public int countKelasDTesting(){
        int hasil=0;
        for(Abstrak bb : abstrakTesting)
            if(bb.getkelas().equalsIgnoreCase("D"))
                hasil++;
        return hasil;
    }
    public int countKelasE(){
        int hasil=0;
        for(Abstrak bb : abstrak)
            if(bb.getkelas().equalsIgnoreCase("E"))
                hasil++;
        return hasil;
    }
    public int countKelasETesting(){
        int hasil=0;
        for(Abstrak bb : abstrakTesting)
            if(bb.getkelas().equalsIgnoreCase("E"))
                hasil++;
        return hasil;
    }
    
    public double[][][] getXYDataTraing(){
        listKata();
<<<<<<< HEAD
        double bb [][][] = new double [5][][];
        double[][] A = new double[countKelasA()][5];
        double[][] B = new double[countKelasB()][5];
        double[][] C = new double[countKelasC()][5];
        double[][] D = new double[countKelasD()][5];
        double[][] E = new double[countKelasE()][5];
=======
        double bb [][][] = new double [6][][];
        double [][]A = new double[countKelasA()][6];
        double [][]B = new double[abstrak.size()-A.length][6];
        double [][]C = new double[abstrak.size()-B.length][6];
        double [][]D = new double[abstrak.size()-C.length][6];
        double [][]E = new double[abstrak.size()-D.length][6];
>>>>>>> origin/master
  
        int k=0;
        int m=0;
        int n=0;
        int o=0;
<<<<<<< HEAD
        int l=0;
        
=======
        int p=0;
>>>>>>> origin/master
        for(int i=0;i<abstrak.size();i++){
            if(abstrak.get(i).getkelas().equalsIgnoreCase("A")){
                for(String kt : kataA)
                    A[k][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataB)
                    A[k][1]+=abstrak.get(i).bobot(kt);
                for(String kt : kataC)
                    A[k][2]+=abstrak.get(i).bobot(kt);
                for(String kt : kataD)
                    A[k][3]+=abstrak.get(i).bobot(kt);
                for(String kt : kataE)
                    A[k][4]+=abstrak.get(i).bobot(kt);
                k++;
            }
            else if (abstrak.get(i).getkelas().equalsIgnoreCase("B")){
                for(String kt : kataA)
                    B[m][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataB)
                    B[m][1]+=abstrak.get(i).bobot(kt);
                for(String kt : kataC)
                    B[m][2]+=abstrak.get(i).bobot(kt);
                for(String kt : kataD)
                    B[m][3]+=abstrak.get(i).bobot(kt);
                for(String kt : kataE)
                    B[m][4]+=abstrak.get(i).bobot(kt);
                m++;
            }else if (abstrak.get(i).getkelas().equalsIgnoreCase("C")){
                for(String kt : kataA)
                    C[n][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataB)
                    C[n][1]+=abstrak.get(i).bobot(kt);
                for(String kt : kataC)
                    C[n][2]+=abstrak.get(i).bobot(kt);
                for(String kt : kataD)
                    C[n][3]+=abstrak.get(i).bobot(kt);
                for(String kt : kataE)
                    C[n][4]+=abstrak.get(i).bobot(kt);
                n++;
            }else if (abstrak.get(i).getkelas().equalsIgnoreCase("D")){
                for(String kt : kataA)
                    D[o][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataB)
                    D[o][1]+=abstrak.get(i).bobot(kt);
                for(String kt : kataC)
                    D[o][2]+=abstrak.get(i).bobot(kt);
                for(String kt : kataD)
                    D[o][3]+=abstrak.get(i).bobot(kt);
                for(String kt : kataE)
                    D[o][4]+=abstrak.get(i).bobot(kt);
                o++;
            }else {
                for(String kt : kataA)
                    E[l][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataB)
                    E[l][1]+=abstrak.get(i).bobot(kt);
                for(String kt : kataC)
                    E[l][2]+=abstrak.get(i).bobot(kt);
                for(String kt : kataD)
                    E[l][3]+=abstrak.get(i).bobot(kt);
                for(String kt : kataE)
                    E[l][4]+=abstrak.get(i).bobot(kt);
                l++;
            }
            else if (abstrak.get(i).getkelas().equalsIgnoreCase("E")){
                for(String kt : kataA)
                    E[p][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataB)
                    E[p][1]+=abstrak.get(i).bobot(kt);
                for(String kt : kataC)
                    E[p][2]+=abstrak.get(i).bobot(kt);
                for(String kt : kataD)
                    E[p][3]+=abstrak.get(i).bobot(kt);
                for(String kt : kataE)
                    E[p][4]+=abstrak.get(i).bobot(kt);
                p++;
            }
        }
<<<<<<< HEAD
        
=======
>>>>>>> origin/master
        bb[0]=A;
        bb[1]=B;
        bb[2]=C;
        bb[3]=D;
        bb[4]=E;
<<<<<<< HEAD
        
=======
>>>>>>> origin/master
        return bb;
    }
    public ArrayList<double []> getXYLineHiperplane(double [][][] training){
        Kernel SVMLinemodel = new Kernel(abstrak.size());
        SVMLinemodel.Sigma=1;
        double max=0;
        for(int i=0;i<training[0].length;i++){
            double [] vector = new double[5];
            vector[0]=training[0][i][0];
            vector[1]=training[0][i][1];
            vector[2]=training[0][i][2];
            vector[3]=training[0][i][3];
            vector[4]=training[0][i][4];
            SVMLinemodel.addData(vector, 1);
            max = Math.max(max,training[0][i][0]);
        }
        for(int i=0;i<training[1].length;i++){
            double [] vector = new double[5];
            vector[0]=training[1][i][0];
            vector[1]=training[1][i][1];
            vector[2]=training[1][i][2];
            vector[3]=training[1][i][3];
            vector[4]=training[1][i][4];
            SVMLinemodel.addData(vector, -1);
            max = Math.max(max,training[1][i][1]);
        }
        for(int i=0;i<training[2].length;i++){
            double [] vector = new double[5];
            vector[0]=training[2][i][0];
            vector[1]=training[2][i][1];
            vector[2]=training[2][i][2];
            vector[3]=training[2][i][3];
            vector[4]=training[2][i][4];
            SVMLinemodel.addData(vector, -1);
            max = Math.max(max,training[2][i][2]);
        }
        for(int i=0;i<training[3].length;i++){
            double [] vector = new double[5];
            vector[0]=training[3][i][0];
            vector[1]=training[3][i][1];
            vector[2]=training[3][i][2];
            vector[3]=training[3][i][3];
            vector[4]=training[3][i][4];
            SVMLinemodel.addData(vector, -1);
            max = Math.max(max,training[3][i][3]);
        }
        for(int i=0;i<training[4].length;i++){
            double [] vector = new double[5];
            vector[0]=training[4][i][0];
            vector[1]=training[4][i][1];
            vector[2]=training[4][i][2];
            vector[3]=training[4][i][3];
            vector[4]=training[4][i][4];
            SVMLinemodel.addData(vector, -1);
            max = Math.max(max,training[4][i][4]);
        }
        
        SVM smoLineSimple = new SVM(SVMLinemodel,1); //hyperplane
        ArrayList<double[]> hyperplane = new ArrayList<double []>();
        for(double i=0;i<=max;i+=0.1){
            for(double j=0;j<=max;j+=0.1){
                for(double k=0;k<=max;k+=0.1){
<<<<<<< HEAD
                    for(double l=0;l<=max;l+=0.1){
                        double [] vector= new double []{i,l};
                        Data test = new Data(0,vector);
                        double hasil = smoLineSimple.svmTestOne(test);
                    
                        if(hasil<=10e-3&&hasil>=-10e-3){
                            hyperplane.add(vector);
                        }
                    }
=======
                for(double l=0;l<=max;l+=0.1){
                double [] vector= new double []{i,l};
                Data test = new Data(0,vector);
                double hasil = smoLineSimple.svmTestOne(test);
                if(hasil<=10e-3&&hasil>=-10e-3){
                    hyperplane.add(vector);
                    }
                }
>>>>>>> origin/master
                }                
            }
        }
        return hyperplane;
    }
    
    public double [][][] getXYDataTesting(){
        listKata();
        double bb [][][] = new double [5][][];
<<<<<<< HEAD
        double[][] A = new double[countKelasATesting()][5];
        double[][] B = new double[countKelasBTesting()][5];
        double[][] C = new double[countKelasCTesting()][5];
        double[][] D = new double[countKelasDTesting()][5];
        double[][] E = new double[countKelasETesting()][5];
=======
        double [][]A = new double[countKelasA()][5];
        double [][]B = new double[abstrak.size()-A.length][5];
        double [][]C = new double[abstrak.size()-B.length][5];
        double [][]D = new double[abstrak.size()-C.length][5];
        double [][]E = new double[abstrak.size()-D.length][5];
>>>>>>> origin/master
  
        int k=0;
        int m=0;
        int n=0;
        int o=0;
<<<<<<< HEAD
        int l=0;
        
        for(int i=0;i<abstrakTesting.size();i++){
            if(abstrakTesting.get(i).getkelas().equalsIgnoreCase("A")){
                for(String kt : kataA)
                    A[k][0]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataB)
                    A[k][1]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataC)
                    A[k][2]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataD)
                    A[k][3]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataE)
                    A[k][4]+=abstrakTesting.get(i).bobot(kt);
                k++;
            }
            else if (abstrakTesting.get(i).getkelas().equalsIgnoreCase("B")){
                for(String kt : kataA)
                    B[m][0]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataB)
                    B[m][1]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataC)
                    B[m][2]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataD)
                    B[m][3]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataE)
                    B[m][4]+=abstrakTesting.get(i).bobot(kt);
                m++;
            }else if (abstrakTesting.get(i).getkelas().equalsIgnoreCase("C")){
                for(String kt : kataA)
                    C[n][0]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataB)
                    C[n][1]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataC)
                    C[n][2]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataD)
                    C[n][3]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataE)
                    C[n][4]+=abstrakTesting.get(i).bobot(kt);
                n++;
            }else if (abstrakTesting.get(i).getkelas().equalsIgnoreCase("D")){
                for(String kt : kataA)
                    D[o][0]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataB)
                    D[o][1]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataC)
                    D[o][2]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataD)
                    D[o][3]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataE)
                    D[o][4]+=abstrakTesting.get(i).bobot(kt);
                o++;
            }else {
                for(String kt : kataA)
                    E[l][0]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataB)
                    E[l][1]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataC)
                    E[l][2]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataD)
                    E[l][3]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataE)
                    E[l][4]+=abstrakTesting.get(i).bobot(kt);
                l++;
            }
        }
        
=======
        int p=0;
        for(int i=0;i<abstrakTesting.size();i++){
            if(abstrak.get(i).getkelas().equalsIgnoreCase("A")){
                for(String kt : kataA)
                    A[k][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataB)
                    A[k][1]+=abstrak.get(i).bobot(kt);
                for(String kt : kataC)
                    A[k][2]+=abstrak.get(i).bobot(kt);
                for(String kt : kataD)
                    A[k][3]+=abstrak.get(i).bobot(kt);
                for(String kt : kataE)
                    A[k][4]+=abstrak.get(i).bobot(kt);
                k++;
            }
            else if (abstrak.get(i).getkelas().equalsIgnoreCase("B")){
                for(String kt : kataA)
                    B[m][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataB)
                    B[m][1]+=abstrak.get(i).bobot(kt);
                for(String kt : kataC)
                    B[m][2]+=abstrak.get(i).bobot(kt);
                for(String kt : kataD)
                    B[m][3]+=abstrak.get(i).bobot(kt);
                for(String kt : kataE)
                    B[m][4]+=abstrak.get(i).bobot(kt);
                m++;
            }else if (abstrak.get(i).getkelas().equalsIgnoreCase("C")){
                for(String kt : kataA)
                    C[n][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataB)
                    C[n][1]+=abstrak.get(i).bobot(kt);
                for(String kt : kataC)
                    C[n][2]+=abstrak.get(i).bobot(kt);
                for(String kt : kataD)
                    C[n][3]+=abstrak.get(i).bobot(kt);
                for(String kt : kataE)
                    C[n][4]+=abstrak.get(i).bobot(kt);
                n++;
            }else if (abstrak.get(i).getkelas().equalsIgnoreCase("D")){
                for(String kt : kataA)
                    D[o][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataB)
                    D[o][1]+=abstrak.get(i).bobot(kt);
                for(String kt : kataC)
                    D[o][2]+=abstrak.get(i).bobot(kt);
                for(String kt : kataD)
                    D[o][3]+=abstrak.get(i).bobot(kt);
                for(String kt : kataE)
                    D[o][4]+=abstrak.get(i).bobot(kt);
                o++;
            }else if (abstrak.get(i).getkelas().equalsIgnoreCase("E")){
                for(String kt : kataA)
                    E[p][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataB)
                    E[p][1]+=abstrak.get(i).bobot(kt);
                for(String kt : kataC)
                    E[p][2]+=abstrak.get(i).bobot(kt);
                for(String kt : kataD)
                    E[p][3]+=abstrak.get(i).bobot(kt);
                for(String kt : kataE)
                    E[p][4]+=abstrak.get(i).bobot(kt);
                p++;
            }
        }
>>>>>>> origin/master
        bb[0]=A;
        bb[1]=B;
        bb[2]=C;
        bb[3]=D;
        bb[4]=E;
<<<<<<< HEAD
        
=======
>>>>>>> origin/master
        return bb;
    }
    //cosim
    private void WeigthedTfIdf(){
        //tf-idf
        for(String kt :kata.keySet()){
            for(Abstrak s : kata.get(kt)){
                s.setBobot(kt, Math.log10(abstrak.size()/kata.get(kt).size()));
            }
        }
        //Normalisasi
        for(Abstrak D : abstrak){
            D.setBobotNormal();
        }
    }
 public void svmTraining(){
        //set Map Data training
        SVMmodel = new Kernel(abstrak.size());
        SVMmodel.Sigma=1;
        for(int i=0;i<abstrak.size();i++){
            Abstrak se = abstrak.get(i);
            double [] vector = new double[kata.size()];
            
            double [] vector1 = new double[kata.size()];
            for(String kata : se.getBobot().keySet()){
                vector[getIndexKata(kata)-1] = se.bobot(kata);
            }
            if(abstrak.get(i).getkelas().equalsIgnoreCase("A"))
<<<<<<< HEAD
                SVMmodel.addData(vector, 1);
            else
                SVMmodel.addData(vector1, -1);
        }
        smoSimple = new Proses.SVM.SVM(SVMmodel,1);
    }
    public String svmTesting(Abstrak testing){   
        //setProblem data uji
        double [] vector = new double[kata.size()];
        for(String kata : testing.getBobot().keySet()){
            if(this.kata.containsKey(kata))
            vector[getIndexKata(kata)-1] = testing.bobot(kata);
        }
        Data nodeTesting = new Data(0,vector);
        //bangun trainig model
        double nilai = smoSimple.svmTestOne(nodeTesting);
        return String.valueOf(nilai);   
        //if (nilai>=(1/2))
           // return "IS";
       // else
       // if ((nilai>=-(1/2))&&(nilai<(1/2)))
       //     return "CS";
       // else
       // if ((nilai>=-1)&&(nilai<-(1/2)))
       //     return "IT";
       // else
       //     return "SE";
        //else
          //  return "CS";  
        
       // int hasil = (nilai>5)?1:-1;
        //if(hasil==1)
        //   return "A";
        //else
        //   return "B";
        //return String.valueOf(hasil);
    }
    //svm
    /*
    public void svmTraining(){
        //set Map Data training
        SVMmodel = new Kernel(abstrak.size());
        SVMmodel.Sigma=-1;
        
        for(int i=0; i < abstrak.size(); i++){
            Abstrak abs = abstrak.get(i);
            double [] vector = new double[kata.size()];
            
            for(String kata : abs.getBobot().keySet())
                vector[getIndexKata(kata)-1] = abs.bobot(kata);
            
            if(abs.getkelas().equalsIgnoreCase("A"))
                SVMmodel.addData(vector, 1);
            else
                svmTrainingB();
        }
        
        smoSimple = new SVM(SVMmodel,1);
    }
    public void svmTrainingB(){
        //set Map Data training
        SVMmodel = new Kernel(abstrak.size());
        SVMmodel.Sigma=-1;
        
        for(int i=0; i < abstrak.size(); i++){
            Abstrak abs = abstrak.get(i);
            double [] vector = new double[kata.size()];
            
            for(String kata : abs.getBobot().keySet())
                vector[getIndexKata(kata)-1] = abs.bobot(kata);
            
            if(abs.getkelas().equalsIgnoreCase("B"))
                SVMmodel.addData(vector, 1);
            else
                svmTrainingC();
        }
        
        smoSimple = new SVM(SVMmodel,1);
    }
    public void svmTrainingC(){
        //set Map Data training
        SVMmodel = new Kernel(abstrak.size());
        SVMmodel.Sigma=-1;
        
        for(int i=0; i < abstrak.size(); i++){
            Abstrak abs = abstrak.get(i);
            double [] vector = new double[kata.size()];
            
            for(String kata : abs.getBobot().keySet())
                vector[getIndexKata(kata)-1] = abs.bobot(kata);
            
            if(abs.getkelas().equalsIgnoreCase("C"))
                SVMmodel.addData(vector, 1);
            else
                svmTrainingD();
        }
        
        smoSimple = new SVM(SVMmodel,1);
    }
    public void svmTrainingD(){
        //set Map Data training
        SVMmodel = new Kernel(abstrak.size());
        SVMmodel.Sigma=-1;
        
        for(int i=0; i < abstrak.size(); i++){
            Abstrak abs = abstrak.get(i);
            double [] vector = new double[kata.size()];
            
            for(String kata : abs.getBobot().keySet())
                vector[getIndexKata(kata)-1] = abs.bobot(kata);
            
            if(abs.getkelas().equalsIgnoreCase("D"))
=======
>>>>>>> origin/master
                SVMmodel.addData(vector, 1);
            else
                SVMmodel.addData(vector, -1);
        }
<<<<<<< HEAD
        
        smoSimple = new SVM(SVMmodel,1);
=======
        smoSimple = new Proses.SVM.SVM(SVMmodel,1);
>>>>>>> origin/master
    }
    
    public String svmTesting(Abstrak testing){   
        //setProblem data uji
        double [] vector = new double[kata.size()];
        for(String kata : testing.getBobot().keySet()){
            if(this.kata.containsKey(kata))
            vector[getIndexKata(kata)-1] = testing.bobot(kata);
        }
        Data nodeTesting = new Data(0,vector);
        //bangun trainig model
        double nilai = smoSimple.svmTestOne(nodeTesting);
<<<<<<< HEAD
        
        int hasil = (nilai>0)?1:-1;
        if(hasil==1)
           return "A";
        else
           return svmTestingB(testing);
=======
        //return String.valueOf(nilai);   
        //if (nilai>=(1/2))
           // return "IS";
       // else
       // if ((nilai>=-(1/2))&&(nilai<(1/2)))
       //     return "CS";
       // else
       // if ((nilai>=-1)&&(nilai<-(1/2)))
       //     return "IT";
       // else
       //     return "SE";
        //else
          //  return "CS";  
        
        int hasil = (nilai>0)?1:-1;
        //if(hasil==1)
        //   return "A";
        //else
        //   return "B";
        return String.valueOf(hasil);
>>>>>>> origin/master
    }
    public String svmTestingB(Abstrak testing){   
        //setProblem data uji
        double [] vector = new double[kata.size()];
        for(String kata : testing.getBobot().keySet()){
            if(this.kata.containsKey(kata))
            vector[getIndexKata(kata)-1] = testing.bobot(kata);
        }
        Data nodeTesting = new Data(0,vector);
        //bangun trainig model
        double nilai = smoSimple.svmTestOne(nodeTesting);
        
        int hasil = (nilai>0)?1:-1;
        if(hasil==1)
           return "B";
        else
            return svmTestingC(testing);
    }
    public String svmTestingC(Abstrak testing){   
        //setProblem data uji
        double [] vector = new double[kata.size()];
        for(String kata : testing.getBobot().keySet()){
            if(this.kata.containsKey(kata))
            vector[getIndexKata(kata)-1] = testing.bobot(kata);
        }
        Data nodeTesting = new Data(0,vector);
        //bangun trainig model
        double nilai = smoSimple.svmTestOne(nodeTesting);
        
        int hasil = (nilai>0)?1:-1;
        if(hasil==1)
           return "C";
        else
            return svmTestingD(testing);
    }
    public String svmTestingD(Abstrak testing){   
        //setProblem data uji
        double [] vector = new double[kata.size()];
        for(String kata : testing.getBobot().keySet()){
            if(this.kata.containsKey(kata))
            vector[getIndexKata(kata)-1] = testing.bobot(kata);
        }
        Data nodeTesting = new Data(0,vector);
        //bangun trainig model
        double nilai = smoSimple.svmTestOne(nodeTesting);
        
        int hasil = (nilai>0)?1:-1;
        if(hasil==1)
            return "D";
        else
            return "E";
    }
    */

//GUI 

    public Vector toKontenTable(){
        Vector isiTable = new Vector();
        double sumAVG = 0;
        for(String kt : kata.keySet()){
            Vector rowTable = new Vector();
            rowTable.add(kt);
            double avg = 0;
            for(Abstrak ss : abstrak){
                int frek = ss.frekuensi(kt);
                rowTable.add(frek);
                avg+=frek;
            }
            rowTable.add(kata.get(kt).size());
            avg/=abstrak.size();
            rowTable.add(avg);
            sumAVG+=avg;
            isiTable.add(rowTable);
        }
        Vector sumTable = new Vector();
        sumTable.add("Jumlah");
        for(Abstrak ss : abstrak){
            String [] kontent = ss.stemming.split(" ");
            sumTable.add(kontent.length);
        }
        sumTable.add("---");
        sumTable.add(sumAVG);
        isiTable.add(sumTable);
        return isiTable;
    }
    public Vector toKontenHeaderTable(){
        Vector header = new Vector();
        header.add("kata\\Abstrak");
        for(Abstrak ss : abstrak)
            header.add("Abs-"+ss.getId());
        header.add("Dft");
        header.add("rata-rata");
        return header;
    }
    public Vector toWeightTable(){
        Vector isiTable = new Vector();
        for(String kt : kata.keySet()){
            Vector rowTable = new Vector();
            rowTable.add(kt);
            for(Abstrak ss : abstrak)
                rowTable.add(ss.bobot(kt));
            rowTable.add(kata.get(kt).size());
            isiTable.add(rowTable);
        }
        return isiTable;
    }
    public Vector toWeightHeaderTable(){
        Vector header = new Vector();
        header.add("kata\\Abstrak");
        for(Abstrak ss : abstrak)
            header.add("Abs-"+ss.getId());
        return header;
    }
    public Vector toBagOfWordTable(){
        Vector vector = new Vector();
        for(String kt : kata.keySet()){
            Vector row = new Vector();
            row.add(kt);
<<<<<<< HEAD
            
            if(kataA.contains(kt))
                row.add("A");
            else if(kataB.contains(kt))
                row.add("B");
            else if(kataC.contains(kt))
                row.add("C");
            else if(kataD.contains(kt))
=======
            if(kataA.contains(kt))
                row.add("A");
            else
            if(kataB.contains(kt))
                row.add("B");
            else
            if(kataC.contains(kt))
                row.add("C");
            else
            if(kataD.contains(kt))
>>>>>>> origin/master
                row.add("D");
            else
                row.add("E");
            
            vector.add(row);
        }
        return vector;
    }
    public Vector toBagOfWordHeaderTable(){
        Vector head = new Vector();
        head.add("Kata");
        head.add("Kelas");
        return head;
    }
}
