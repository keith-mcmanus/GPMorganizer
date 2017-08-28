/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logreader;


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author keith.mcmanus
 */
public class IPTranspose {
    
     private static boolean doesContain(String source, String subItem){
         String pattern = "\\b"+subItem+"\\b";
         Pattern p=Pattern.compile(pattern);
         Matcher m=p.matcher(source);
         return m.find();
    }
    
      //public void TransposeScanner(String filePath, String searchObj){
    public static void main(String args[]) {
        Boolean TF= false;
        FileInputStream inputStream = null;
        FileInputStream inputStream2 = null;
        Scanner sc = null;
        Scanner sc2 = null;
        /*BufferedWriter BWR2 = null;
        FileWriter FWR2 = null;*/
        BufferedWriter BWR = null;
        FileWriter FWR = null;
        ArrayList<String> IPs = new ArrayList<>();
        ArrayList<String> Dates = new ArrayList<>();
        
        String[] IPString;
        String[] dateString;
        
        String ReputationString = null;
        String filePath = "/Users/keith.mcmanus/Downloads/GooglePostmasterDatafinal.csv";
        String filePath2 = "/Users/keith.mcmanus/Downloads/GooglePostmasterDataFinal2.csv";
        String filePath3 = "/Users/keith.mcmanus/Documents/FileReader/cleanIPs.txt";
        int index;
        int count = 0;
        try{
            inputStream = new FileInputStream(filePath3);
            sc = new Scanner(inputStream);
            
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                IPs.add(line);
                    //IPString = line.split(",");
                    //IPs.add(IPString[0]);
            }
            sc.close();
        }catch (Exception e) {}
        Set<String> cleanIPs = new LinkedHashSet<String>(IPs);
        IPs.clear();
        IPs.addAll(cleanIPs);
        System.out.println(IPs.size());
        
        try{
            inputStream = new FileInputStream(filePath2);
            sc = new Scanner(inputStream);
            
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                dateString = line.split(",");
                Dates.add(dateString[0]);
                    //IPString = line.split(",");
                    //IPs.add(IPString[0]);
            }
            sc.close();
        }catch (Exception e) {}
        Set<String> cleanDates = new LinkedHashSet<>(Dates);
        Dates.clear();
        Dates.addAll(cleanDates);
        System.out.println(Dates.size());
        
        
       
        
        try{
            FWR = new FileWriter("/Users/keith.mcmanus/Documents/FileReader/c.txt");
            BWR = new BufferedWriter(FWR);
            
            String line = null;
            String searchObj = null;
            String ipRep[] = null;
            String[] readLine = null;
            String dateStart = "28-Mar-17";
            String rep = null;
            int repRecord = 0;
                for(int i = 0; i<=IPs.size();i++)
                {
                    //inputStream2 = new FileInputStream(filePath2);
                    //sc2 = new Scanner (inputStream2);
                    searchObj =  IPs.get(i);
                    //repRecord = 0;
                    //System.out.println(searchObj);
                    BWR.write("\n");
                    BWR.write(searchObj); 
                    count++;
                    System.out.println(count);
                    
                    for (int j = 0;j<Dates.size();j++){
                    //check line for date & IP, if has both record reputation.  if not at the end of the date for loop add a blank.
                        repRecord = 0; //set record for new date to not recorded
                        inputStream2 = new FileInputStream(filePath2);
                        sc2 = new Scanner (inputStream2);
                        //System.out.println(Dates.get(j));
                        while(sc2.hasNextLine())
                        {
                        line = sc2.nextLine();
                        readLine = line.split(",");
                                if(doesContain(line,searchObj)==true && doesContain(line,Dates.get(j))){
                                //run through the file and only record reputataion if it exists for that date
                                BWR.write(","+readLine[2]);
                                BWR.flush();
                                repRecord = 1;//set flag to 1 if reputatiaon is recorded
                            }  
                        } //while loop terminates, file is finished
                        if(repRecord != 1){
                            //if no reputatiaon was recorded for the date at the end of the flie
                            //reord a blank
                            BWR.write(",");
                            BWR.flush();
                        } 
                        //end date in for loop
                    }sc2.close();
                }
        }catch(Exception e){}
    }
}


/*if(repRecord == 0 && doesContain(realLine[1],searchObj)==false){
                                BWR.write(",");
                                BWR.flush();
                                repRecord = 1;
                            }*/
                              /*if (doesContain(readLine[0],dateStart)==false){
                                dateStart = readLine[0];
                                if(repRecord == 0){
                                    BWR.write(",");
                                    BWR.flush();
                                }
                                else{
                                repRecord = 0;
                                System.out.println(dateStart+" "+repRecord);
                                }
                            }*/