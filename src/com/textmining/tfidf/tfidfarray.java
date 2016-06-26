package com.textmining.tfidf;
public class tfidfarray {
    
    private String term;
    private double w;
    
    public  tfidfarray(){
        
    }
    
    public tfidfarray(String term, double w){
        this.term = term;
        this.w = w;
    }
    
    public void setTerm(String term){
        this.term = term;
    }
    
    public void setW(double w){
        this.w = w;
    }
    
    public String getTerm(){
        return term;
    }
    
    public double getW(){
        return w;
    }
}
