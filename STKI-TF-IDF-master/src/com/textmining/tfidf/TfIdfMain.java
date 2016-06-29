package com.textmining.tfidf;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TfIdfMain {
    
    /**
     * Main method
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        DocumentParser dp = new DocumentParser();
        //parse file me list semua dokumen yang ada dan memgabi menjadi terms terms
        dp.parseFiles("/Users/fadil/NetBeansProjects/STKI-TF-IDF-master/txtKesehatan"); //path to docs
        
        dp.tfIdfCalculator(); //calculates tfidf
    }
}
