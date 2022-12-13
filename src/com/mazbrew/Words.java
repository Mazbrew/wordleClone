package com.mazbrew;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Words{
    private File Library = new File("Library.txt");
    private RandomAccessFile Guesses;

    private Scanner scan;

    private String wordString;

    public Words() throws Exception{
        
    }

    public boolean checkExist(String word){
        try{
            scan = new Scanner(Library);
        }catch (Exception e){
            System.out.println("ERROR");
        } 

        if(scan.useDelimiter("\\Z").next().contains(word))
        {
            scan.close();
            return true;
        }else{
            scan.close();
            return false;
        }
    }

    public char[] generateWord(){
        char word[] = new char[5];
        try{
            Guesses = new RandomAccessFile("Guesses.txt","r");
            long pos = (long) (Math.random() * Guesses.length());
            System.out.println(pos-(pos%7));
            Guesses.seek(pos-(pos%7));

            wordString = Guesses.readLine().toUpperCase();
            Guesses.close();
        }catch(Exception e){
            System.out.println("ERROR");
        }

        for(int i=0;i<5;i++){
            word[i]= wordString.charAt(i);
        }

        return word;
    }
}
