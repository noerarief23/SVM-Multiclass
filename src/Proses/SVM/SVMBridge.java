/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses.SVM;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
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
    //struktur data untuk Abstrak message
    private ArrayList<Abstrak> abstrak;
    private ArrayList<Abstrak> abstrakTesting;
    private HashMap<String,ArrayList<Abstrak>> kata;
    
    //struktur data untuk visualisai
    private ArrayList<String> kataCS;
    private ArrayList<String> kataIS;
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
        kataCS = new ArrayList<String>();
        kataIS = new ArrayList<String>();
        for(String kt : kata.keySet()){
            double csFrek=0;
            double isFrek=0;
            for(Abstrak bb : kata.get(kt)){
                if(bb.getId()==0)
                    continue;
                if(bb.getkelas().equalsIgnoreCase("IS"))
                    isFrek+=bb.getBobot().get(kt);
                else
                    csFrek+=bb.getBobot().get(kt);
            }
            if(csFrek<isFrek)
                kataIS.add(kt);
            else if(csFrek>isFrek)
                kataCS.add(kt);
        }
    }
    public int countKelasIS(){
        int hasil=0;
        for(Abstrak bb : abstrak)
            if(bb.getkelas().equalsIgnoreCase("IS"))
                hasil++;
        return hasil;
    }
    public int countKelasISTesting(){
        int hasil=0;
        for(Abstrak bb : abstrakTesting)
            if(bb.getkelas().equalsIgnoreCase("IS"))
                hasil++;
        return hasil;
    }
    public double[][][] getXYDataTraing(){
        listKata();
        double bb [][][] = new double [2][][];
        double [][]is = new double[countKelasIS()][2];
        double [][]cs = new double[abstrak.size()-is.length][2];
        int k=0;
        int m=0;
        for(int i=0;i<abstrak.size();i++){
            if(abstrak.get(i).getkelas().equalsIgnoreCase("IS")){
                for(String kt : kataIS)
                    is[k][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataCS)
                    is[k][1]+=abstrak.get(i).bobot(kt);
                k++;
            }
            else if (abstrak.get(i).getkelas().equalsIgnoreCase("CS")){
                for(String kt : kataIS)
                    cs[m][0]+=abstrak.get(i).bobot(kt);
                for(String kt : kataCS)
                    cs[m][1]+=abstrak.get(i).bobot(kt);
                m++;
            }
        }
        bb[0]=is;
        bb[1]=cs;
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
        for(double i=0;i<=max;i+=0.1)
            for(double j=0;j<=max;j+=0.1){
                double [] vector= new double []{i,j};
                Data test = new Data(0,vector);
                double hasil = smoLineSimple.svmTestOne(test);
                if(hasil<=10e-3&&hasil>=-10e-3){
                    hyperplane.add(vector);
                }
            }
        return hyperplane;
    }
    public double [][][] getXYDataTesting(){
        listKata();
        double bb [][][] = new double [2][][];
        double [][]is = new double[countKelasISTesting()][2];
        double [][]cs = new double[abstrakTesting.size()-is.length][2];
        int k=0;
        int m=0;
        for(int i=0;i<abstrakTesting.size();i++){
            if(abstrakTesting.get(i).getkelas().equalsIgnoreCase("IS")){
                for(String kt : kataIS)
                    is[k][0]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataCS)
                    is[k][1]+=abstrakTesting.get(i).bobot(kt);
                k++;
            }
            else if (abstrakTesting.get(i).getkelas().equalsIgnoreCase("CS")){
                for(String kt : kataIS)
                    cs[m][0]+=abstrakTesting.get(i).bobot(kt);
                for(String kt : kataCS)
                    cs[m][1]+=abstrakTesting.get(i).bobot(kt);
                m++;
            }
        }
        bb[0]=is;
        bb[1]=cs;
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
        SVMmodel.Sigma=-1;
        for(int i=0;i<abstrak.size();i++){
            Abstrak se = abstrak.get(i);
            double [] vector = new double[kata.size()];
            for(String kata : se.getBobot().keySet()){
                vector[getIndexKata(kata)-1] = se.bobot(kata);
            }
            if(abstrak.get(i).getkelas().equalsIgnoreCase("IS"))
                SVMmodel.addData(vector, 1);
            else
                SVMmodel.addData(vector, -1);
        }
        smoSimple = new Proses.SVM.SVM(SVMmodel,3);
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
        int hasil = (nilai>0)?1:-1;
        if(hasil==1)
            return "IS";
        else
            return "CS";
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
            if(kataCS.contains(kt))
                row.add("CS");
            else
                row.add("IS");
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
