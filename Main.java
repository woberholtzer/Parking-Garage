import java.util.*;
import java.io.*;

class Main {
  public static ArrayList<String> readCSV(String filePath){
     
    ArrayList<String> lines = new ArrayList<String>();

    try{
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      String line;
      while((line = br.readLine()) != null){
        if(line.strip().length() > 0){
          lines.add(line);
        }
      }
    } catch(IOException e){
      System.err.println("Error reading CSV file!");
      e.printStackTrace(System.err);
    }
    
    return lines;
  }
  public static String[] characterSeperate(String line){
    String[] tokens = line.split(",");
    return tokens;
  }
  public static void copyContent(File a, File b)
        throws Exception
    {
        FileInputStream in = new FileInputStream(a);
        FileOutputStream out = new FileOutputStream(b);
  
        try {
  
            int n;
  
            // read() function to read the
            // byte of data
            while ((n = in.read()) != -1) {
                // write() function to write
                // the byte of data
                out.write(n);
            }
        }
        finally {
            if (in != null) {
  
                // close() function to close the
                // stream
                in.close();
            }
            // close() function to close
            // the stream
            if (out != null) {
                out.close();
            }
        }
        System.out.println("File Copied");
    }
  public static void main(String[] args) throws IOException, Exception {

    File ParkingGarageArray = new File("lib/ParkingGarageArray.txt");
    String filePath = "lib/Example.txt";
    File InputFile = new File(filePath);
    BufferedWriter bw = new BufferedWriter(new FileWriter((ParkingGarageArray)));
    
    ArrayList<String> CSV = new ArrayList<String>();
    ParkingGarage pg = new ParkingGarage(5,5);
    //CSV = readCSV(filePath);
      
      boolean valid = false;
      do{
        System.out.println("Please press enter to Begin!");
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        if (userInput.equals("")){
          pg.start();
          valid = true;
        } else if (userInput.equals("clear")){
          pg.clearGarage();
          valid = true;
        } else if(userInput.equals("fill")){
          pg.fillGarage();
          valid = true;
        } else {
          System.out.println("Please don't press any characters, just enter");
          valid = false;
        }
      } while(!valid);
      
      System.out.println(pg.display(null));
      bw.write(pg.toString());
      bw.close();
      copyContent(ParkingGarageArray,InputFile);
  }
}



