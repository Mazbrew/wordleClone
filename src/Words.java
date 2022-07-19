import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Words{
    private File Library = new File("Library.txt");
    private File spacing = new File("spacing.txt");
    private RandomAccessFile Guesses;

    private Scanner scan;

    private String wordString;

    public Words() throws Exception{
        
    }

    public boolean checkExist(String word){
        try{
            scan = new Scanner(Library);
        }catch (Exception e){
            System.out.println("Error occurred with library");
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
        try{
        scan = new Scanner(spacing);
        }catch(Exception e){
            System.out.println("Error occurred with buffer.");
        }
        int buffer = scan.nextLine().length();
        scan.close();
        char word[] = new char[5];
        try{

            Guesses = new RandomAccessFile("Guesses.txt","r");
            long pos = (long) (Math.random() * Guesses.length());
            System.out.println(pos-(pos%(5+buffer)));
            Guesses.seek(pos-(pos%(5+buffer)));

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
