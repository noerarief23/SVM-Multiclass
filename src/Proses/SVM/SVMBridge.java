/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses.SVM;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import com.sun.org.apache.xpath.internal.operations.And;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
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
    
    private ArrayList<String> kataC;
    private ArrayList<String> kataD;
    private ArrayList<String> kataE;
    
    //model svm
    Kernel SVMmodel;
    Proses.SVM.SVM smoSimple;
    
    
    public SVMBridge(){
        abstrak = new ArrayList<Abstrak>();
        abstrakTesting = new ArrayList<Abstrak>();
        kata = new HashMap<String,ArrayList<Abstrak>>();
    }
    //struktur data kata dan Abstrak
    public void addAbstrakFile(Abstrak se){
        abstrak.add(se);
        WeigthedTfIdf();
        for(String s : se.setKata())
            if(!kata.containsKey(s)){
                ArrayList<Abstrak> newS = new ArrayList<Abstrak>();
                newS.add(se);
                kata.put(s,newS);
            }
            else{
                ArrayList<Abstrak> old = kata.get(s);
                if(!old.contains(se))
                    old.add(se);
            }
    }
    public void removeAbstrakFile(Abstrak se){
        abstrak.remove(se);
        for(String s : se.setKata())
            if(kata.containsKey(s)){
                ArrayList<Abstrak> old = kata.get(se);
                abstrak.remove(se);
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
            for(Abstrak bb : kata.get(kt)){
                if(bb.getId()==0)
                    continue;
                if(bb.getkelas().equalsIgnoreCase("A"))
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
            
            else if(itFrek<seFrek)
                kataSE.add(kt);
            else if(itFrek>seFrek)
                kataIT.add(kt);*/
        }
    }
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
    public double[][][] getXYDataTraing(){
        listKata();
        double bb [][][] = new double [6][][];
        double [][]A = new double[countKelasA()][6];
        double [][]B = new double[abstrak.size()-A.length][6];
        double [][]C = new double[abstrak.size()-B.length][6];
        double [][]D = new double[abstrak.size()-C.length][6];
        double [][]E = new double[abstrak.size()-D.length][6];
  
        int k=0;
        int m=0;
        int n=0;
        int o=0;
        int p=0;
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
        bb[0]=A;
        bb[1]=B;
        bb[2]=C;
        bb[3]=D;
        bb[4]=E;
        return bb;
    }
    public ArrayList<double []> getXYLineHiperplane(double [][][] training){
        Kernel SVMLinemodel = new Kernel(abstrak.size());
        SVMLinemodel.Sigma=1;
        double max=0;
        for(int i=0;i<training[0].length;i++){
            double [] vector = new double[2];
            vector[0]=training[0][i][0];
            vector[1]=training[0][i][1];
            SVMLinemodel.addData(vector, 1);
            max = Math.max(max,training[0][i][1]);
        }
        for(int i=0;i<training[1].length;i++){
            double [] vector = new double[2];
            vector[0]=training[1][i][0];
            vector[1]=training[1][i][1];
            SVMLinemodel.addData(vector, -1);
            max = Math.max(max,training[1][i][1]);
        }
        Proses.SVM.SVM smoLineSimple = new Proses.SVM.SVM(SVMLinemodel,1); //hyperplane
        ArrayList<double[]> hyperplane = new ArrayList<double []>();
        for(double i=0;i<=max;i+=0.1){
            for(double j=0;j<=max;j+=0.1){
                for(double k=0;k<=max;k+=0.1){
                for(double l=0;l<=max;l+=0.1){
                double [] vector= new double []{i,l};
                Data test = new Data(0,vector);
                double hasil = smoLineSimple.svmTestOne(test);
                if(hasil<=10e-3&&hasil>=-10e-3){
                    hyperplane.add(vector);
                    }
                }
                }                
            }
        }
        return hyperplane;
    }
    public double [][][] getXYDataTesting(){
        listKata();
        double bb [][][] = new double [5][][];
        double [][]A = new double[countKelasA()][5];
        double [][]B = new double[abstrak.size()-A.length][5];
        double [][]C = new double[abstrak.size()-B.length][5];
        double [][]D = new double[abstrak.size()-C.length][5];
        double [][]E = new double[abstrak.size()-D.length][5];
  
        int k=0;
        int m=0;
        int n=0;
        int o=0;
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
        bb[0]=A;
        bb[1]=B;
        bb[2]=C;
        bb[3]=D;
        bb[4]=E;
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
        for(Abstrak se : abstrak){
            se.setBobotNormal();
        }
    }

    //svm
    public void svmTraining(){
        //set Map Data training
        SVMmodel = new Kernel(abstrak.size());
        SVMmodel.Sigma=1;
        for(int i=0;i<abstrak.size();i++){
            Abstrak se = abstrak.get(i);
            double [] vector = new double[kata.size()];
            for(String kata : se.getBobot().keySet()){
                vector[getIndexKata(kata)-1] = se.bobot(kata);
            }
            if(abstrak.get(i).getkelas().equalsIgnoreCase("A"))
                SVMmodel.addData(vector, 1);
            else
                SVMmodel.addData(vector, -1);
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
    }
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
