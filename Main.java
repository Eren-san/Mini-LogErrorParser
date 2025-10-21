import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList; 
import java.util.List;


class Main{

    private static final String LOG_REGEX = "error|Error|ERROR|command not found|syntax error\n";
    private static final List<String> errorLogs = new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter file or directory path: ");
        String Path = sc.nextLine();
        System.out.println("Enter log file location: ");
        String fPath = sc.nextLine();
        sc.close();

        String actualPath = fPath + "/error_log.log";

        File f = new File(Path);

        if (f.isFile()) {
            readFile(f);
            wToFile(actualPath);
        }else if (f.isDirectory()){
            readDirectory(f);
            wToFile(actualPath);

        }else {
            System.err.println("Error: not valid path");
        }
        }
    
    public static void readFile(File filePath){

        Pattern pattern = Pattern.compile(LOG_REGEX);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher m = pattern.matcher(line);
                if(m.find()){
                    errorLogs.add(line);
                }
                
            } 
            }catch (IOException e) {
                    e.printStackTrace();
                }
    }

    public static void readDirectory(File folderPath){
        File[] logFiles = folderPath.listFiles();
        Pattern pattern = Pattern.compile(LOG_REGEX);

        for (File file : logFiles) {

            if(file.isFile()){
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher m = pattern.matcher(line);
                if(m.find()){
                    errorLogs.add(line);
                }
                
            } 
            }catch (IOException e) {
                    e.printStackTrace();
                }
            }   
        }       
    }

    public static void wToFile(String fPath){
        File eLog = new File(fPath);
        try {
            if(eLog.createNewFile()){
                System.out.println("error_log file created..");
            }
        }catch(Exception e){
            e.getStackTrace();
        }

        try (FileWriter output = new FileWriter(eLog, true)){
            for (String logLine : errorLogs){
                output.write(logLine + System.lineSeparator());
            }
            System.out.println("The logs were successfully written to " + fPath);
        }catch(Exception e){
            e.getStackTrace();
        }


    }




    }