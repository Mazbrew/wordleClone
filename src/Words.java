import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Words{
    private File Library = new File("Library.txt");
    private RandomAccessFile Guesses = new RandomAccessFile("Guesses.txt","r");

    private Scanner scan;

    private String wordString;

    public Words() throws Exception{
        
    }

    public boolean checkExist(String word){
        try{
            scan = new Scanner(Library);
        }catch (Exception e){
            System.out.println("file not found");
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
            Guesses.seek(pos);
            Guesses.readLine();

            wordString = Guesses.readLine();
            Guesses.close();
        }catch(Exception e){
            System.out.println("failed to read file");
        }

        System.out.println(wordString);

        for(int i=0;i<5;i++){
            word[i]= wordString.charAt(i);
        }

        return word;
    }
}
