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
        dp.parseFiles("D:\\pdftotext"); //path to docs
        dp.tfIdfCalculator(); //calculates tfidf
    }
}
