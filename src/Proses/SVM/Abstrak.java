/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses.SVM;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class Abstrak {
    private int id;
    private HashMap<String,Integer> frekKata = new HashMap<String,Integer>();
    private HashMap<String,Double> bobotKata = new HashMap<String,Double>();
    String stemming;
    private String dokumen;
    private String berimbuhan;
    private String mengadungAngka;
    private String kelas;
    
    public Abstrak(int _id,String _dokumen,String _steming,String _kelas,String _berimbuhan, String _mengadungAngka){ 
        dokumen = _dokumen;
        stemming = _steming;
        kelas = _kelas;
        id = _id;
        berimbuhan = _berimbuhan;
        mengadungAngka = _mengadungAngka;
        String kata[] = stemming.split(" ");
        
        for(String _kata : kata){
            if(!frekKata.containsKey(_kata))
                frekKata.put(_kata,0);
            int count = frekKata.get(_kata);
            frekKata.remove(_kata);
            frekKata.put(_kata, count+1);
        }
        
        for(String i : frekKata.keySet()){
            bobotKata.put(i, 1 + Math.log10(frekKata.get(i)));
        }
    }
    public int getId(){
        return id;
    }
    public String getkelas(){
        return kelas;
    }
    public HashMap<String,Double> getBobot(){
        return bobotKata;
    }
    public void setBobot(String kata,double idf){
        if(frekKata.containsKey(kata)){
            double wtf = 1+Math.log10(frekKata.get(kata));
            bobotKata.remove(kata);
            bobotKata.put(kata,wtf*idf);
        }
    }
    public void setBobotNormal(){
        double sumSqrt2 = 0;
        for(String katas : frekKata.keySet())
            sumSqrt2 = sumSqrt2 + (bobotKata.get(katas)*bobotKata.get(katas));
        double sumSqrt = Math.sqrt(sumSqrt2);
        for(String kata : frekKata.keySet()){
            double bobot = bobotKata.get(kata);
            bobotKata.put(kata,bobot/sumSqrt);
        }
    }
    public int frekuensi(String kata){
        if(!frekKata.containsKey(kata))
            return 0;
        return frekKata.get(kata);
    }
    public double bobot(String kata){
        if(!bobotKata.containsKey(kata))
            return 0;
        return bobotKata.get(kata);
    }
    public double getCosimDistans(Abstrak se){
        double sum =0;
        for(String kt1 : se.frekKata.keySet())
            if(frekKata.containsKey(kt1))
                sum = sum +(this.bobotKata.get(kt1)*se.bobotKata.get(kt1));
        return sum;
    }
    public java.util.Set<String> setKata(){
        return frekKata.keySet();
    }
    //GUI
    public java.util.Vector toTable(){
        java.util.Vector row = new java.util.Vector();
        row.add(id);
        row.add(dokumen);
        row.add(stemming);
        //row.add(berimbuhan);
        //row.add(mengadungAngka);
        int jumlah = 0;
        for(int i=0;i<mengadungAngka.length();i++){
            if(mengadungAngka.charAt(i)>='0'&&mengadungAngka.charAt(i)<='9')
                jumlah++;
        }
        //row.add(jumlah);
        row.add(kelas);
        return row;
    }
    
}

