/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.textmining.tfidf;

import java.util.List;

/**
 * Class to calculate TfIdf of term.
 * @author Mubin Shrestha
 */
public class TfIdf {
    
    /**
     * Calculated the tf of term termToCheck
     * @param totalterms : Array of all the words under processing document
     * @param termToCheck : term of which tf is to be calculated.
     * @return tf(term frequency) of term termToCheck
     */
    public double tfCalculator(String[] totalterms, String termToCheck) {
        System.out.println("Computing Term-Frequency");
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        System.out.println("Done computing Term-Frequency");
        return count / totalterms.length;
    }
    
    /**
     * Calculated idf of term termToCheck
     * @param allTerms : all the terms of all the documents
     * @param termToCheck
     * @return idf(inverse document frequency) score
     */
    public double idfCalculator(List<String[]> allTerms, String termToCheck) {
        double count = 0;
        System.out.println("Computing Inverse Document Frequency");
        for (String[] ss : allTerms) {
            for (String s : ss) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
        }
        System.out.println("Done computing Inverse Document Frequency");
        return Math.log(allTerms.size() / count);
    }
}
