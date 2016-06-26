package com.textmining.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
        System.out.println("Starting buffered reader");
        BufferedReader in = null;
        for (File f : allfiles) {
            System.out.println("Starting file(s) loop");
            if (f.getName().endsWith(".txt")) {
                in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
                for (String term : tokenizedTerms) {
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
            }
        }
        int i = 0;
        System.out.println("Terms Docs Array: ");
        for(String[] data: termsDocsArray){
            System.out.print(data[i]+" ");
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
        
        System.out.println("Starting computing Tf-Idf");        
        for (String[] docTermsArray : termsDocsArray) {
            System.out.println("Doc "+i);
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                System.out.println("Term: "+terms);
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                System.out.println("Value: "+tfidf+"\n");
                tfidfvectors[count] = tfidf;
                if(tfidf>0){
                    array.add(new tfidfarray(terms, tfidf));
                }
                count++;
            }
            i++;
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
        
         String col[] = {"Term", "W = Tf * Idf"};
        DefaultTableModel model = new DefaultTableModel(col, 0);
        
        for(tfidfarray arr: array){
            Object[] data = {arr.getTerm(), arr.getW()};
            model.addRow(data);
        }
        
        JTable table = new JTable(model);
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    }
}
