/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import Proses.SVM.SVMBridge;
import Proses.SVM.Abstrak;
import Preprosesing.PreProcessing;
import Preprosesing.toSql;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import jxl.read.biff.BiffException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Administrator
 */
public class MainFrame extends javax.swing.JFrame {

    private toSql sql;
    private SVMBridge svm;
    private PreProcessing pre;
    
    //Table
    File data;
    Vector headerTable1;
    Vector headerTableTesting;
    Vector headerTable2;
    Vector kontenTable1;
    
    //ploting
    double[][][] xyTrainig;
    ArrayList<double []>xyHiperplane;
    
    public MainFrame() {
        initComponents();
        sql = new toSql();
        pre = new PreProcessing();
        headerTable1 = new Vector();
        headerTable1.add("Id");
        headerTable1.add("Abstrak");
        headerTable1.add("Preprocessing");
        //headerTable1.add("Kata Berimbuhan");
        //headerTable1.add("Kata Mengandung Angka");
        //headerTable1.add("Jumlah Angka");
        headerTable1.add("Kelas");
        
        headerTableTesting = new Vector();
        headerTableTesting.add("Id");
        headerTableTesting.add("isi Dokumen");
        headerTableTesting.add("Preprocessing");
        headerTableTesting.add("Kelas");
        headerTableTesting.add("Klasifikasi");
        
        headerTable2 = new Vector();
        kontenTable1 = new Vector();
        LoadDataFromSQL();
        xyTrainig=svm.getXYDataTraing();
        xyHiperplane=svm.getXYLineHiperplane(xyTrainig);
        loadVisualDocument();
    }
    public void LoadDataFromSQL(){
        try {
            kontenTable1.removeAllElements();
            svm = new SVMBridge();
            ResultSet rs = sql.getTable("select * from abstrak");
            int jumlahAbstrak = 0;
            while(rs.next()){
                String doc = rs.getString("Abstrak");
                String dasar = rs.getString("preProsesing");
                Abstrak newS = new Abstrak(rs.getInt("No"),doc,dasar, rs.getString("Kelas"),PreProcessing.getKataBerimbuhan(doc, dasar),PreProcessing.getKataMangandungAngka(doc));
                svm.addAbstrakFile(newS);
                kontenTable1.add(newS.toTable());
                jumlahAbstrak++;
            }
            tableTrainigSVM.setModel(new javax.swing.table.DefaultTableModel(kontenTable1,headerTable1));
            tableDataTraining.setModel(new javax.swing.table.DefaultTableModel(kontenTable1,headerTable1));
            setKontentTable();
            tableTermFrekuensi.setModel(new javax.swing.table.DefaultTableModel(svm.toKontenTable(),svm.toKontenHeaderTable()));
            tebleWeightedTerm.setModel(new javax.swing.table.DefaultTableModel(svm.toWeightTable(),svm.toWeightHeaderTable()));
            //tableCosim.setModel(new javax.swing.table.DefaultTableModel(svm.getCoSim(),svm.getCoSimHeader()));
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setKontentTable(){
            tableTrainigSVM.getColumnModel().getColumn(0).setPreferredWidth(70);
            tableTrainigSVM.getColumnModel().getColumn(headerTable1.size()-1).setPreferredWidth(100);
            for(int i=1;i<headerTable1.size()-1;i++){
                tableTrainigSVM.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer());
                tableTrainigSVM.getColumnModel().getColumn(i).setPreferredWidth(400);
                tableTrainigSVM.getColumnModel().getColumn(i).setCellEditor(new CustomEditor());
            }
            tableDataTraining.getColumnModel().getColumn(0).setPreferredWidth(70);
            tableDataTraining.getColumnModel().getColumn(headerTable1.size()-1).setPreferredWidth(100);
            for(int i=1;i<headerTable1.size()-1;i++){
                tableDataTraining.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer());
                tableDataTraining.getColumnModel().getColumn(i).setPreferredWidth(400);
                tableDataTraining.getColumnModel().getColumn(i).setCellEditor(new CustomEditor());
            }
    }
    public void loadVisualDocument(){
        XYSeries dataset1 = new XYSeries("A");
        for(int i=0;i<xyTrainig[0].length;i++)
            dataset1.add(xyTrainig[0][i][0], xyTrainig[0][i][1]);
<<<<<<< HEAD
        
=======
>>>>>>> origin/master
        XYSeries dataset2 = new XYSeries("B");
        for(int i=0;i<xyTrainig[1].length;i++)
            dataset2.add(xyTrainig[1][i][0], xyTrainig[1][i][1]);
        
        XYSeries dataset6 = new XYSeries("C");
        for(int i=0;i<xyTrainig[2].length;i++)
<<<<<<< HEAD
            dataset6.add(xyTrainig[2][i][0], xyTrainig[2][i][1]);
        
        XYSeries dataset7 = new XYSeries("D");
        for(int i=0;i<xyTrainig[3].length;i++)
            dataset7.add(xyTrainig[3][i][0], xyTrainig[3][i][1]);
        
        XYSeries dataset8 = new XYSeries("E");
        for(int i=0;i<xyTrainig[4].length;i++)
            dataset8.add(xyTrainig[4][i][0], xyTrainig[4][i][1]);
=======
            dataset6.add(xyTrainig[1][i][0], xyTrainig[0][i][1]);
        
        XYSeries dataset7 = new XYSeries("D");
        for(int i=0;i<xyTrainig[2].length;i++)
            dataset7.add(xyTrainig[0][i][0], xyTrainig[1][i][1]);
        
        XYSeries dataset8 = new XYSeries("E");
        for(int i=0;i<xyTrainig[2].length;i++)
            dataset7.add(xyTrainig[0][i][0], xyTrainig[1][i][1]);
>>>>>>> origin/master
        
        XYSeries dataset4 = new XYSeries("hyperplane");
        for(int i=0;i<xyHiperplane.size();i++)
            dataset4.add(xyHiperplane.get(i)[0],xyHiperplane.get(i)[1]);
<<<<<<< HEAD
        
=======
        /*
        XYSeries dataset8 = new XYSeries("hyperplane2");
        for(int i=0;i<xyHiperplane.size();i++)
            dataset8.add(xyHiperplane.get(i)[1],xyHiperplane.get(i)[1]);
        
        XYSeries dataset9 = new XYSeries("hyperplane3");
        for(int i=0;i<xyHiperplane.size();i++)
            dataset9.add(xyHiperplane.get(i)[1],xyHiperplane.get(i)[0]);
        
        XYSeries dataset10 = new XYSeries("hyperplane4");
        for(int i=0;i<xyHiperplane.size();i++)
            dataset10.add(xyHiperplane.get(i)[0],xyHiperplane.get(i)[0]);
        */
>>>>>>> origin/master
        //data testing
        double dataTesting [][][] = svm.getXYDataTesting();
        XYSeries dataset3 = new XYSeries("DataTesting 1");
        for(int i=0;i<dataTesting[0].length;i++)
            dataset3.add(dataTesting[0][i][0], dataTesting[0][i][1]);
        XYSeries dataset5 = new XYSeries("DataTesting 2");
        for(int i=0;i<dataTesting[1].length;i++)
            dataset5.add(dataTesting[1][i][0], dataTesting[1][i][1]);
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(dataset1);
        dataset.addSeries(dataset2);
        dataset.addSeries(dataset4);
        dataset.addSeries(dataset6);
        dataset.addSeries(dataset7);
<<<<<<< HEAD
        dataset.addSeries(dataset8);
        dataset.addSeries(dataset3);
        dataset.addSeries(dataset5);
        JFreeChart chart = ChartFactory.createXYLineChart("Visualisasi Data","A","B", dataset, PlotOrientation.VERTICAL, true, true, false);
=======
        dataset.addSeries(dataset3);
        dataset.addSeries(dataset5);
        dataset.addSeries(dataset8);
        //dataset.addSeries(dataset9);
        //dataset.addSeries(dataset10);
        JFreeChart chart = ChartFactory.createXYLineChart("Visualisasi Data","IS","CS", dataset, PlotOrientation.VERTICAL, true, true, false);
>>>>>>> origin/master
        final XYPlot plot = chart.getXYPlot();
        plot.setWeight(40);
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(2, false);
        renderer.setSeriesLinesVisible(3, false);
        renderer.setSeriesLinesVisible(4, false);
        renderer.setSeriesLinesVisible(5, false);
        
        renderer.setSeriesLinesVisible(6, false);
<<<<<<< HEAD
        renderer.setSeriesLinesVisible(7, false);
=======
        
        renderer.setSeriesLinesVisible(7, false);
        
        //renderer.setSeriesLinesVisible(8, false);
        //renderer.setSeriesLinesVisible(9, false);
>>>>>>> origin/master
        plot.setRenderer(renderer);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        Visualisasi2D.removeAll();
        Visualisasi2D.add(chartPanel, BorderLayout.CENTER);
        tableBagOfWord.setModel(new javax.swing.table.DefaultTableModel(svm.toBagOfWordTable(),svm.toBagOfWordHeaderTable()));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        ImportDataTraining = new javax.swing.JButton();
        removeDataTrainig = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableDataTraining = new javax.swing.JTable();
        buttonRemoveAllDataTraining = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableTestingSVM = new javax.swing.JTable();
        ButtonTestingData = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTrainigSVM = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        Akurasi = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableTestingSVM1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        tableNormWeightedTerm = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTermFrekuensi = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tebleWeightedTerm = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableBagOfWord = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        Visualisasi2D = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jFileChooser1.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f){
                if(f.getName().endsWith(".txt")||f.isDirectory()||f.getName().endsWith(".xls"))
                return true;
                return false;
            }
            public String getDescription(){
                return "Just docFile";
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SVM Classification V.2.8 BETA");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Implementasi Support Vector Machines");

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        ImportDataTraining.setText("Inpur Data Training");
        ImportDataTraining.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportDataTrainingActionPerformed(evt);
            }
        });

        removeDataTrainig.setText("Hapus Data");
        removeDataTrainig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeDataTrainigActionPerformed(evt);
            }
        });

        tableDataTraining.setModel(new javax.swing.table.DefaultTableModel(new Object [][] { },new String [] { }));
        tableDataTraining.setRowHeight(150);
        tableDataTraining.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableDataTraining.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(tableDataTraining);

        buttonRemoveAllDataTraining.setText("Hapus Semua");
        buttonRemoveAllDataTraining.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoveAllDataTrainingActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText(".xls");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(ImportDataTraining)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(removeDataTrainig)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonRemoveAllDataTraining)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ImportDataTraining, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeDataTrainig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonRemoveAllDataTraining, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addComponent(jSeparator2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Data Training", jPanel8);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tableTestingSVM.setModel(new javax.swing.table.DefaultTableModel(new Object [][] {},new String [] { }));
        tableTestingSVM.setRowHeight(150);
        tableTestingSVM.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane3.setViewportView(tableTestingSVM);

        ButtonTestingData.setText("Masukkan Data Testing");
        ButtonTestingData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTestingDataActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText(".xls");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(ButtonTestingData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonTestingData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(60, 60, 60))
        );

        jTabbedPane2.addTab("Data Testing", jPanel13);

        jSplitPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jSplitPane1.setDividerLocation(300);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tableTrainigSVM.setModel(new javax.swing.table.DefaultTableModel(new Object [][] {   },new String [] {  }));
        tableTrainigSVM.setRowHeight(150);
        tableTrainigSVM.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tableTrainigSVM);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Akurasi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Akurasi.setForeground(new java.awt.Color(153, 0, 0));
        Akurasi.setText("akurasi");

        tableTestingSVM1.setModel(new javax.swing.table.DefaultTableModel(new Object [][] {},new String [] { }));
        tableTestingSVM1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane4.setViewportView(tableTestingSVM1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Akurasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(Akurasi, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addGap(135, 135, 135))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(34, 34, 34)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(24, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        tableNormWeightedTerm.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        tableTermFrekuensi.setModel(new javax.swing.table.DefaultTableModel(new Object [][] { },new String [] {}));
        tableTermFrekuensi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(tableTermFrekuensi);

        tableNormWeightedTerm.addTab("Term Frekuensi", jScrollPane2);

        tebleWeightedTerm.setModel(new javax.swing.table.DefaultTableModel(new Object [][] {  }, new String [] {  }));
        tebleWeightedTerm.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane5.setViewportView(tebleWeightedTerm);

        tableNormWeightedTerm.addTab("Bobot Normalisasi", jScrollPane5);

        tableBagOfWord.setModel(new javax.swing.table.DefaultTableModel(new Object [][] {},new String [] {}));
        jScrollPane7.setViewportView(tableBagOfWord);

        tableNormWeightedTerm.addTab("Bag Of Word", jScrollPane7);

        Visualisasi2D.setBorder(javax.swing.BorderFactory.createTitledBorder("Visualisasi"));
        Visualisasi2D.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Visualisasi2D, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Visualisasi2D, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addContainerGap())
        );

        tableNormWeightedTerm.addTab("Visualisasi", jPanel5);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableNormWeightedTerm)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(tableNormWeightedTerm)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel2);

        jTabbedPane2.addTab("Support Vector Machine", jSplitPane1);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("pada Klasifikasi Laporan Skripsi Prodi Teknik Informatika Unikom");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AjaxLoader.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonTestingDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTestingDataActionPerformed

        if(jFileChooser1.showOpenDialog(this)==0){
            final File data = jFileChooser1.getSelectedFile();
            Thread th = new Thread(new Runnable() {
                public void run() {
                    ButtonTestingData.setEnabled(false);
                    tableTermFrekuensi.setEnabled(false);
                    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AjaxLoader.gif"))); // NOI18N
                    Vector testingTable =null;
                    svm.removeTesting();
                    if(data.getName().endsWith(".txt"))
                    testingTable = setTesting(data);
                    else if(data.getName().endsWith(".xls"))
                    try {
                        testingTable = setTestings(data);
                    } catch (BiffException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tableTermFrekuensi.setModel(new javax.swing.table.DefaultTableModel(svm.toKontenTable(),svm.toKontenHeaderTable()));
                    tebleWeightedTerm.setModel(new javax.swing.table.DefaultTableModel(svm.toWeightTable(),svm.toWeightHeaderTable()));
                    //tableCosim.setModel(new javax.swing.table.DefaultTableModel(svm.getCoSim(),svm.getCoSimHeader()));
                    tableTrainigSVM.setModel(new javax.swing.table.DefaultTableModel(kontenTable1,headerTable1));
                    tableTestingSVM.setModel(new javax.swing.table.DefaultTableModel(testingTable,headerTableTesting));
                    tableTestingSVM1.setModel(new javax.swing.table.DefaultTableModel(testingTable,headerTableTesting));
                    
                    setKontentTable();
                    tableTestingSVM.getColumnModel().getColumn(0).setPreferredWidth(70);
                    tableTestingSVM.getColumnModel().getColumn(headerTable1.size()-3).setPreferredWidth(100);
                    for(int i=1;i<headerTable1.size()-3;i++){
                        tableTestingSVM.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer());
                        tableTestingSVM.getColumnModel().getColumn(i).setPreferredWidth(400);
                        tableTestingSVM.getColumnModel().getColumn(i).setCellEditor(new CustomEditor());
                    }
                    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AjaxLoader.png"))); // NOI18N
                    ButtonTestingData.setEnabled(true);
                    tableTermFrekuensi.setEnabled(true);
                    loadVisualDocument();
                }
            });
            th.start();
        }
    }//GEN-LAST:event_ButtonTestingDataActionPerformed

    private void buttonRemoveAllDataTrainingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoveAllDataTrainingActionPerformed
        int i = JOptionPane.showConfirmDialog(this, "anda yakin hapus semua data training", "confirmasi", JOptionPane.YES_NO_OPTION);
        if(i==0)
        try {
            sql.updateTable("delete from abstrak");
            LoadDataFromSQL();
            xyTrainig=svm.getXYDataTraing();
            xyHiperplane=svm.getXYLineHiperplane(xyTrainig);
            loadVisualDocument();
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonRemoveAllDataTrainingActionPerformed

    private void removeDataTrainigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeDataTrainigActionPerformed
        int id = (int)tableDataTraining.getValueAt(tableDataTraining.getSelectedRow(), 0);
        int i = JOptionPane.showConfirmDialog(this, "anda yakin hapus training no"+id, "confirmasi", JOptionPane.YES_NO_OPTION);
        if(i==0)
        try {
            sql.updateTable("delete from abstrak where No="+id);
            LoadDataFromSQL();
            xyTrainig=svm.getXYDataTraing();
            xyHiperplane=svm.getXYLineHiperplane(xyTrainig);
            loadVisualDocument();
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_removeDataTrainigActionPerformed

    private void ImportDataTrainingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportDataTrainingActionPerformed
        // TODO add your handling code here:
        if(jFileChooser1.showOpenDialog(this)==0){
            final File data = jFileChooser1.getSelectedFile();
            Thread th = new Thread(new Runnable() {
                public void run() {
                    try {
                        ImportDataTraining.setEnabled(false);
                        tableTrainigSVM.setEnabled(false);
                        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AjaxLoader.gif")));
                        if(data.getName().endsWith(".txt"))
                        addTraining(data);
                        else if(data.getName().endsWith(".xls"))
                        addTrainings(data);
                        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AjaxLoader.png"))); // NOI18N
                        ImportDataTraining.setEnabled(true);
                        tableTrainigSVM.setEnabled(true);
                    } catch (SQLException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (BiffException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            th.start();
        }
    }//GEN-LAST:event_ImportDataTrainingActionPerformed
    private void addTraining(File data) throws SQLException{
        String kontent = pre.getContent(data);
        String stemming = pre.getStemmingTxt(data);
        String kelas = javax.swing.JOptionPane.showInputDialog(null, "Masukan Kelas Abstrak", "Permintaan", javax.swing.JOptionPane.QUESTION_MESSAGE);
        sql.updateTable("insert into abstrak (kelas,Abstrak,preProsesing) values (\""+kelas+"\",\""+kontent+"\",\""+stemming+"\")");
        LoadDataFromSQL();
        xyTrainig=svm.getXYDataTraing();
        xyHiperplane=svm.getXYLineHiperplane(xyTrainig);
        loadVisualDocument();
    }
    private void addTrainings(File data) throws SQLException, BiffException{
        String hasil[][]= pre.getStemmingXls(data);
        for(int i=0;i<hasil.length;i++){
            String kontent = hasil[i][2];
            String stemming = hasil[i][0];
            String kelas = hasil[i][1];
            sql.updateTable("insert into abstrak (kelas,Abstrak,preProsesing) values (\""+kelas+"\",\""+kontent+"\",\""+stemming+"\")");
        }
        LoadDataFromSQL();
        xyTrainig=svm.getXYDataTraing();
        xyHiperplane=svm.getXYLineHiperplane(xyTrainig);
        loadVisualDocument();
    }
    private Vector setTesting(File data){
        String kontent = pre.getContent(data);
        String stemming = pre.getStemmingTxt(data);
        Abstrak testing = new Abstrak(0, kontent, stemming,PreProcessing.getKataBerimbuhan(kontent, stemming),PreProcessing.getKataMangandungAngka(kontent),"??");
        svm.addAbstrakFile(testing);
        
        Vector testingTable = new Vector();
        Vector row = new Vector();
        row.add(testing.getId());
        row.add(kontent);
        row.add(stemming);
        String kelas = "";
        svm.svmTraining();
        kelas = (svm.svmTesting(testing));
        row.add(kelas);
        testingTable.add(row);
        return testingTable;
    }
    private Vector setTestings(File data) throws BiffException{
        String [][] steaming = pre.getStemmingXls(data);
        Abstrak testing []= new Abstrak[steaming.length];
        Vector testingTable = new Vector();
        
        for(int i=0;i<steaming.length;i++){
            String kontent = steaming[i][2];
            String stemming = steaming[i][0];            
            testing[i] = new Abstrak(i+1, kontent,stemming, steaming[i][1],PreProcessing.getKataBerimbuhan(kontent, stemming),PreProcessing.getKataMangandungAngka(kontent));
            
            Vector row = new Vector();
            row.add(testing[i].getId());
            row.add(kontent);
            row.add(stemming);
            testingTable.add(row);
        }
        
        String [] hasil = new String [testing.length];
        double benar = 0;
        svm.svmTraining();
<<<<<<< HEAD
        
=======
        double TP=0;
        double TN=0;
        double A=0;
        double B=0;
>>>>>>> origin/master
        for(int i=0;i<steaming.length;i++){
            testing[i]=svm.addTesting(testing[i]);
            hasil[i] = svm.svmTesting(testing[i]);
            Vector row = (Vector)testingTable.get(i);
            row.add(steaming[i][1]);
            row.add(hasil[i]);
            if(hasil[i].trim().equalsIgnoreCase("A")){
                benar+=1;
<<<<<<< HEAD
            }
=======
                //if(hasil[i].trim().equalsIgnoreCase("A"))
                  //  TP++;
                //if(hasil[i].trim().equalsIgnoreCase("B"))
                  //  TN++;
            }
            if("S".equalsIgnoreCase(steaming[i][1].trim()))
                A++;
            else
                B++;
>>>>>>> origin/master
        }
        BigDecimal predict = new BigDecimal((benar/steaming.length)*100).setScale(3, RoundingMode.HALF_EVEN);
        
        Akurasi.setText(predict+" %");
        return testingTable;
    }    

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel());
        }catch(Exception e){}
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                MainFrame frame = new MainFrame();
                frame.setLocation((dim.width - frame.getSize().width)/2,(dim.height - frame.getSize().height)/2);
                frame.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Akurasi;
    private javax.swing.JButton ButtonTestingData;
    private javax.swing.JButton ImportDataTraining;
    private javax.swing.JPanel Visualisasi2D;
    private javax.swing.JButton buttonRemoveAllDataTraining;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JButton removeDataTrainig;
    private javax.swing.JTable tableBagOfWord;
    private javax.swing.JTable tableDataTraining;
    private javax.swing.JTabbedPane tableNormWeightedTerm;
    private javax.swing.JTable tableTermFrekuensi;
    private javax.swing.JTable tableTestingSVM;
    private javax.swing.JTable tableTestingSVM1;
    private javax.swing.JTable tableTrainigSVM;
    private javax.swing.JTable tebleWeightedTerm;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JList list = new javax.swing.JList();
}
