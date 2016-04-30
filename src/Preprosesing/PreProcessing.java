
package Preprosesing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Komando
 */
public class PreProcessing {

    static toSql sql;
    final static int txt=0;
    final static int exel=2;
    final static int csv=1;
    public PreProcessing() {
        sql = new toSql();
    }

    private static boolean cekKamus(String kata) {
        try {
            return sql.isExists("Select * from katadasar where kata='" + kata + "'");
        } catch (SQLException ex) {
            Logger.getLogger(PreProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //
    private String Delete_Inflection_Suffixes(String kata) {
        String kataDasar = kata;
        if (kataDasar.matches(".*([km]u|nya|[kl]ah|pun)$")) {
            String kataDasar_ = kataDasar.replaceAll("([km]u|nya|[kl]ah|pun)*$", "");
            if (kata.matches(".*([klt]ah|pun)$")) {
                if (kataDasar_.matches(".*([km]u|nya)$")) {
                    String kataDasara__ = kataDasar_.replaceAll("([km]u|nya)$", "");
                    return kataDasara__;
                }
            }
            return kataDasar_;
        }
        return kataDasar;
    }

    private boolean Cek_Prefix_Disallowed_Sufixes(String kata) {
        if (kata.matches("^(be).*") && kata.matches(".*(i)$")) { // be- dan -i
            return true;
        }
        if (kata.matches("^(di).*") && kata.matches(".*(an)$")) { // di- dan -an				
            return true;
        }
        if (kata.matches("^(ke).*") && kata.matches(".*(i|kan)$")) { // ke- dan -i,-kan
            return true;
        }
        if (kata.matches("^(me).*") && kata.matches(".*(an)$")) { // me- dan -an
            return true;
        }
        if (kata.matches("^(se).*") && kata.matches(".*(i|kan)$")) { // se- dan -i,-kan
            return true;
        }
        return false;
    }
    //akhiran
    private String Delete_Derivation_Suffixes(String kata) {
        String kataAsal = kata;
        if (kata.matches(".*(i|an)$")) {
            String kataAsal_ = kata.replaceAll("(i|an)$", "");
            if (cekKamus(kataAsal_)) { 	
                return kataAsal_;
            }
            if (kata.matches(".*(kan)$")) { 				
                String kataAsal__ = kata.replaceAll("(kan)$", "");
                if (cekKamus(kataAsal__)) {
                    return kataAsal__;
                }
            }
            if (Cek_Prefix_Disallowed_Sufixes(kata)) {
                return kataAsal;
            }
        }
        return kataAsal;
    }
    //awalan
    private String Delete_Derivation_Prefix(String kata, int iterasi) {
        if (iterasi > 3) {
            return kata;
        }
        String kataAsal = kata;
        /* ------ Tentukan Tipe Awalan ------------*/
        if (kata.matches("^(di|[ks]e).*")) { // Jika di-,ke-,se-
            String kata_ = kata.replaceAll("^(di|[ks]e)", "");
            if (cekKamus(kata_)) {
                return kata_; // Jika ada balik
            }
            String kata__ = Delete_Derivation_Suffixes(kata_);
            if (cekKamus(kata__)) {
                return kata__;
            }
            String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
            if (cekKamus(kata___)) {
                return kata___;
            }
            /*------------end “diper-”, ---------------------------------------------*/
            if (kata.matches("^(diper).*")) {
                kata_ = kata.replaceAll("^(diper)", "");
                if (cekKamus(kata_)) {
                    return kata_; // Jika ada balik
                }
                kata__ = Delete_Derivation_Suffixes(kata__);
                if (cekKamus(kata__)) {
                    return kata__;
                }
                kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                if (cekKamus(kata___)) {
                    return kata___;
                }
                /*-- Cek luluh -r ----------*/
                kata_ = kata.replaceAll("^(diper)", "r");
                if (cekKamus(kata_)) {
                    return kata_; // Jika ada balik
                }
                kata__ = Delete_Derivation_Suffixes(kata_);
                if (cekKamus(kata__)) {
                    return kata__;
                }
                kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                if (cekKamus(kata___)) {
                    return kata___;
                }
            }
            /*------------end “diper-”, ---------------------------------------------*/
        }
        if (kata.matches("^([tmbp]e).*")) { //Jika awalannya adalah “te-”, “me-”, “be-”, atau “pe-”
            /*------------ Awalan “te-”, ---------------------------------------------*/
            if (kata.matches("^(te).*")) { // Jika awalan “te-”,
                if (kata.matches("^(terr).*")) { // 1.
                    return kata;
                }
                if (kata.matches("^(ter)[aiueo].*")) { // 2.
                    String kata_ = kata.replaceAll("^(ter)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(ter[^aiueor]er[aiueo]).*")) { // 3.
                    String kata_ = kata.replaceAll("^(ter)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(ter[^aiueor]er[^aiueo]).*")) { // 4.
                    String kata_ = kata.replaceAll("^(ter)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(ter[^aiueor][^(er)]).*")) { // 5.
                    String kata_ = kata.replaceAll("^(ter)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(te[^aiueor]er[aiueo]).*")) { // 6.
                    return kata; // return none
                }
                if (kata.matches("^(te[^aiueor]er[^aiueo]).*")) { // 7.
                    String kata_ = kata.replaceAll("^(te)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
            }
            /*------------end “te-”, ---------------------------------------------*/
            /*------------ Awalan “me-”, ---------------------------------------------*/
            if (kata.matches("^(me).*")) { // Jika awalan “me-”,
                if (kata.matches("^(meng)[aiueokghq].*")) { // 1.
                    String kata_ = kata.replaceAll("^(meng)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                    /*--- cek luluh k- --------*/
                    kata_ = kata.replaceAll("^(meng)", "k"); // luluh k-
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(meny).*")) { // 2.
                    String kata_ = kata.replaceAll("^(meny)", "s");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(mem)[bfpv].*")) { // 3.
                    String kata_ = kata.replaceAll("^(mem)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                    /*--- cek luluh p- --------*/
                    kata_ = kata.replaceAll("^(mem)", "p"); // luluh p-
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(mem).*")) { // 3.
                    String kata_ = kata.replaceAll("^(mem)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                    /*--- cek luluh p- --------*/
                    kata_ = kata.replaceAll("^(mem)", "p"); // luluh p-
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(men)[cdjsz].*")) { // 4.
                    String kata_ = kata.replaceAll("^(men)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                }
                if (kata.matches("^(me).*")) { // 5.
                    String kata_ = kata.replaceAll("^(me)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                    /*--- cek luluh t- --------*/
                    kata_ = kata.replaceAll("^(men)", "t"); // luluh t-
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
            }
            /*------------end “me-”, ---------------------------------------------*/
            /*------------ Awalan “be-”, ---------------------------------------------*/
            if (kata.matches("^(be).*")) { // Jika awalan “be-”,
                if (kata.matches("^(ber)[aiueo].*")) { // 1.
                    String kata_ = kata.replaceAll("^(ber)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    kata_ = kata.replaceAll("^(ber)", "r");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    kata_ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata_)) {
                        return kata_;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("(ber)[^aiueo].*")) { // 2.
                    String kata_ = kata.replaceAll("(ber)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(be)[k].*")) { // 3.
                    String kata_ = kata.replaceAll("^(be)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
            }
            /*------------end “be-”, ---------------------------------------------*/
            /*------------ Awalan “pe-”, ---------------------------------------------*/
            if (kata.matches("^(pe).*")) { // Jika awalan “pe-”,
                if (kata.matches("^(peng)[aiueokghq].*")) { // 1.
                    String kata_ = kata.replaceAll("^(peng)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(peny).*")) { // 2.
                    String kata_ = kata.replaceAll("^(peny)", "s");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(pem)[bfpv].*")) { // 3.
                    String kata_ = kata.replaceAll("^(pem)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(pen)[cdjsz].*")) { // 4.
                    String kata_ = kata.replaceAll("^(pen)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                    /*-- Cek luluh -p ----------*/
                    kata_ = kata.replaceAll("^(pem)", "p");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(pe).*")) { // 6.
                    String kata_ = kata.replaceAll("^(pe)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                if (kata.matches("^(per).*")) { // 5.				
                    String kata_ = kata.replaceAll("^(per)", "");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    String kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                    /*-- Cek luluh -r ----------*/
                    kata_ = kata.replaceAll("^(per)", "r");
                    if (cekKamus(kata_)) {
                        return kata_; // Jika ada balik
                    }
                    kata__ = Delete_Derivation_Suffixes(kata_);
                    if (cekKamus(kata__)) {
                        return kata__;
                    }
                    kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                    if (cekKamus(kata___)) {
                        return kata___;
                    }
                }
                
            }
            /*------------end “pe-”, ---------------------------------------------*/
            /*------------ Awalan “memper-”, ---------------------------------------------*/
            if (kata.matches("^(memper).*")) {
                String kata_ = kata.replaceAll("^(memper)", "");
                if (cekKamus(kata_)) {
                    return kata_; // Jika ada balik
                }
                String kata__ = Delete_Derivation_Suffixes(kata_);
                if (cekKamus(kata__)) {
                    return kata__;
                }
                String kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                if (cekKamus(kata___)) {
                    return kata___;
                }
                /*-- Cek luluh -r ----------*/
                kata_ = kata.replaceAll("^(memper)", "r");
                if (cekKamus(kata_)) {
                    return kata_; // Jika ada balik
                }
                kata__ = Delete_Derivation_Suffixes(kata_);
                if (cekKamus(kata__)) {
                    return kata__;
                }
                kata___ = Delete_Derivation_Prefix(kata_, iterasi + 1);
                if (cekKamus(kata___)) {
                    return kata___;
                }
            }
        }
        /* --- Cek Ada Tidaknya Prefik/Awalan (“di-”, “ke-”, “se-”, “te-”, “be-”, “me-”, atau “pe-”) ------*/
        if (kata.matches("^(di|[kstbmp]e).*") == false) {
            return kataAsal;
        }
        return kataAsal;
    }

    public String getStemming(String kata) {
        String kataAsal = kata;
        /* 1. Cek Kata di Kamus jika Ada SELESAI */
        if (kataAsal.matches(".*(-).*")) {
            kataAsal = kataAsal.substring(0, kataAsal.indexOf("-"));
        }
        if (cekKamus(kataAsal)) { // Cek Kamus
            return kataAsal; // Jika Ada kembalikan
        }
        /* 2. Buang Infection suffixes (\-lah", \-kah", \-ku", \-mu", atau \-nya") */
        kataAsal = Delete_Inflection_Suffixes(kataAsal);
        /* 3. Buang Derivation suffix (\-i" or \-an") */
        kataAsal = Delete_Derivation_Suffixes(kataAsal);
        /* 4. Buang Derivation prefix */
        kataAsal = Delete_Derivation_Prefix(kataAsal,0);
        return kataAsal;
    }
    
    public String getContent(File data){
        BufferedReader read = null;
        String isi = "";
        try {
            String line;
            read = new BufferedReader(new FileReader(data));
            while ((line = read.readLine()) != null) 
                isi += line;
        } catch (FileNotFoundException ex) {
            System.out.println(isi);
            Logger.getLogger(PreProcessing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(isi);
            Logger.getLogger(PreProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isi.replaceAll("\"", "");
    }
    public static String getKataBerimbuhan(String kataAsli, String kataDasar){
        String hasil="";
        String kataA[] = kataAsli.split(" ");
        //filter kata
        String isiFilter = "";
        for (String fildKata : kataA) {
            fildKata = fildKata.replaceAll("[^\\x00-\\x7F]", " ");
            fildKata = fildKata.replaceAll("([0-9]|,|\"|#|/|:|\\.|\\)|\\(|'|“|\\?|\\|#|!)", " ");
            if (fildKata.trim().length() >1) {
                try {
                    if (!sql.isExists("select * from stoplist where kata='" + fildKata + "'")) {
                        if (fildKata.trim().length() > 1) {
                            isiFilter += fildKata.toLowerCase().trim() + " ";
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PreProcessing.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        kataA = isiFilter.trim().split(" ");
        String kataD[] = kataDasar.split(" ");
        //System.out.println(kataA.length+"-"+kataD.length);
//        bandingkan dengan hasil steming
        for(int i=0;i<kataA.length;i++){
            String imbuhan = kataA[i];
            if(i<kataD.length)
                imbuhan = imbuhan.replace(kataD[i], "");
            if(!imbuhan.equals(""))
                hasil=hasil+" "+kataA[i].toLowerCase();
        }
        return hasil;
    }
    public static String getKataMangandungAngka(String kataAsli){
        String hasil="";
        String kataA[] = kataAsli.split(" ");
        for(int i=0;i<kataA.length;i++)
            if(kataA[i].matches(".*([0-9]).*")){
                    hasil=hasil+" "+kataA[i];
            }
        return hasil;
    }
    public String[][] getStemmingXls(File data) throws BiffException {
        if(data.getName().endsWith(".txt")||data.getName().endsWith(".csv"))
            return null;
        String [][]hasil=null;
        try {
            Workbook workbook = Workbook.getWorkbook(data);
            Sheet sheet = workbook.getSheet(0);
            int len=sheet.getColumn(0).length;
            hasil = new String [len][5];
            for (int i = 0; i < hasil.length; ++i){
                String ret = sheet.getCell(0, i).getContents();
                String fild[] = ret.split(" ");
                String store = "";
                for (String fildKata : fild) {
                    fildKata = fildKata.replaceAll("[^\\x00-\\x7F]", " ");
                    fildKata = fildKata.replaceAll("([0-9]|,|\"|#|/|:|\\.|\\)|\\(|'|“|\\?|\\|#|!)", " ");
                    if (fildKata.trim().length() >1) {
                        if (!sql.isExists("select * from stoplist where kata='" + fildKata + "'")) {
                            if (fildKata.trim().length() > 1) {
                                store += getStemming(fildKata.toLowerCase().trim()) + " ";
                            }
                        }
                    }
                }
                hasil[i][0]=store.trim();
                hasil[i][1]=sheet.getCell(1, i).getContents();
                hasil[i][2]=ret.replaceAll("\"", "");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PreProcessing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PreProcessing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PreProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasil;
    }
    
    public String getStemmingTxt(File data) {
        BufferedReader read = null;
        String isi = "";
        try {
            String line;
            read = new BufferedReader(new FileReader(data));
            while ((line = read.readLine()) != null) {
                String fild[] = line.split(" ");
                for (String fildKata : fild) {
                    fildKata = fildKata.replaceAll("[^\\x00-\\x7F]", "");
                    fildKata = fildKata.replaceAll("([0-9]|,|\"|/|:|\\.|\\)|\\(|'|“|\\?|\\!)", "");
                    if (fildKata.trim().length() >1) {
                        if (!sql.isExists("select * from stoplist where kata='" + fildKata + "'")) {
                            if (fildKata.trim().length() > 1) {
                                isi += getStemming(fildKata.toLowerCase().trim()) + " ";
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(isi);
            Logger.getLogger(PreProcessing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(isi);
            Logger.getLogger(PreProcessing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(isi);
            Logger.getLogger(PreProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isi.substring(0, isi.length() - 1);
    }

    public static void main(String[] args) {
        PreProcessing stemming = new PreProcessing();
        Scanner scan = new Scanner (System.in);
        while(true){
            System.out.print("Masukan Kata : ");
            String a = scan.next();
            System.out.println("Hasil : "+stemming.getStemming(a));
        }
        //System.out.println(stemming.getStemming(new File("Munaslub Kadin Ternyata Hanya Dihadiri 6 Orang.txt")));
    }
}
