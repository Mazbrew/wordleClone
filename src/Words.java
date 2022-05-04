import java.io.File;
import java.util.Scanner;

public class Words{
    private File Library = new File("Library.txt");
    private File Guesses = new File("Guesses.txt");

    private Scanner scan;

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
}
