package com.textmining.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DocumentParser {

    //This variable will hold all terms of each document in an array.
    private List<String[]> termsDocsArray = new ArrayList<String[]>();
    private List<String> allTerms = new ArrayList<String>(); //to hold all terms
    private List<double[]> tfidfDocsVector = new ArrayList<double[]>();
    private List<tfidfarray> array = new ArrayList<>();
    
    
    /**
     * Method to read files and store in array.
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void parseFiles(String filePath) throws FileNotFoundException, IOException {
        System.out.println("Initiating file parsing\nGetting available file(s)");
        File[] allfiles = new File(filePath).listFiles();
        System.out.println("memulai buffered reader");
        BufferedReader in = null;
        for (File f : allfiles) {
            //System.out.println("Starting file(s) loop");
            if (f.getName().endsWith(".txt")) {
                in = new BufferedReader(new FileReader(f));//f adalah file txt yang akan di baca.
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    sb.append(s); //membaca satu baris
                }
                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //mencari satu terms
                for (String term : tokenizedTerms) {
                    if (!allTerms.contains(term)) {  //menghindari duplicate entry
                        allTerms.add(term); //menambah satu terms pada satu file
                        
                    }
                }
                termsDocsArray.add(tokenizedTerms);
            }
        }
        int i = 0;
        System.out.println("Terms Docs Array: ");
        for(String data: allTerms){
            
            System.out.print(data+" |");
            i++;
        }
    }

    /**
     * Method to create termVector according to its tfidf score.
     */
    public void tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency
        int i = 1;
        
        System.out.println("\nMenghitung Tf-Idf \n");   
        
            double[] tfidfvectors = new double[allTerms.size()];//allTerms size = 4
            int count = 0; 
            
            
            for (String terms : allTerms) {
                double jumlah=0;
               for (String[] docTermsArray : termsDocsArray) { 
                System.out.println("Term: "+terms+" pada Doc: "+i);
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                System.out.println("Weight: "+tfidf+"\n");
                jumlah=tfidf+jumlah;
                tfidfvectors[count] = tfidf;
                
               i++; 
            }System.out.println("jumlah Weight Term "+terms+": "+jumlah);
               i=1;count++;
            
            
            array.add(new tfidfarray(terms, jumlah));  //storing document vectors;            
        }
        /*
        for (String[] docTermsArray : termsDocsArray) {
            System.out.println("Doc "+i);
            double[] tfidfvectors = new double[allTerms.size()];//allTerms size = 4
            int count = 0; 
            
            for (String terms : allTerms) {
                System.out.println("Term: "+terms);
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                System.out.println("Weight: "+tfidf+"\n");
                tfidfvectors[count] = tfidf;
                if(tfidf>=0){
                    array.add(new tfidfarray(terms, tfidf));
                }
                count++;
            }
            
            i++;
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }*/
        
        String col[] = {"Term", "W = Tf * Idf"," Stopword"};
        DefaultTableModel model = new DefaultTableModel(col, 0);
        
        String[] terms=new String[allTerms.size()];
        double[] weight=new double[allTerms.size()];
        double[] termStopword=new double[allTerms.size()];
        double bobotStopword=0.05*allTerms.size(); 
        bobotStopword=(int)bobotStopword;
        System.out.println("bobot: "+allTerms.size()+" hasilnya "+bobotStopword);
        int j=0;
        
        for(tfidfarray arr:array){
            terms[j]=arr.getTerm();
             weight[j]=arr.getW();
             j++;
        }
        
        Arrays.sort(weight);
        
        int k=0;
        for(tfidfarray arr:array){
        if(k<=bobotStopword){
        Object[] data={terms[k],weight[k],"Stopword"};
        model.addRow(data);
        }else{
        Object[] data={terms[k],weight[k],"bukan Stopword"};
        model.addRow(data);
            }        
        k++;
        }
       /* 
        for(int k=0;k<allTerms.size();k++){
            for(int l=1;l<allTerms.size();l++){
            if(weight[k]>weight[l]){
                double simpanWeight=weight[k];
                String simpanTerms=terms[k];
                weight[k]=weight[l];
                terms[k]=terms[l];
                weight[l]=simpanWeight;
                terms[l]=simpanTerms;
            }
        }
        Object[] data={terms[k],weight[k]};
        model.addRow(data);
        }
        */
        /*for(tfidfarray arr: array){
            Object[] data = {arr.getTerm(), arr.getW()};
            model.addRow(data);
        }*/
        
        JTable table = new JTable(model);
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    }
}
