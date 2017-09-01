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
        BufferedWriter BWR = null;
        FileWriter FWR = null;
        ArrayList<String> IPs = new ArrayList<>();
        ArrayList<String> Dates = new ArrayList<>();
        String filename = "SNDS3DeDuped";
        String[] IPString;
        String[] dateString;  
        String filePath = ("/Users/keith.mcmanus/Documents/FileReader/"+filename+".csv");
        int count = 0;
        try{
            inputStream = new FileInputStream(filePath);
            sc = new Scanner(inputStream);
            
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                    IPString = line.split(",");
                    IPs.add(IPString[1]);
            }
                    sc.close();
                Set<String> cleanIPs = new LinkedHashSet<>(IPs);
                IPs.clear();
                IPs.addAll(cleanIPs);
                System.out.println(IPs.size());
        }catch (Exception e) {}
       
        try{
            inputStream = new FileInputStream(filePath);
            sc = new Scanner(inputStream);

            while (sc.hasNextLine()){
                String line = sc.nextLine();
                dateString = line.split(",");
                Dates.add(dateString[0]);
            }
            sc.close();
        }catch (Exception e) {}
        Set<String> cleanDates = new LinkedHashSet<>(Dates);
        Dates.clear();
        Dates.addAll(cleanDates);
        System.out.println(Dates.size());
        
        
       
        
        try{
            FWR = new FileWriter("/Users/keith.mcmanus/Documents/FileReader/"+filename+"Formatted.csv");
            BWR = new BufferedWriter(FWR);
            BWR.write(",");
            BWR.flush();
            for(int i = 0;i<Dates.size();i++){
                BWR.write(Dates.get(i)+",");
                BWR.flush();
            }
            
            String line = null;
            String searchObj = null;
            String ipRep[] = null;
            String[] readLine = null;
            String rep = null;
            int repRecord = 0;
                for(int i = 0; i<=IPs.size();i++)
                {
                    searchObj =  IPs.get(i);
                    BWR.write("\n");
                    BWR.write(searchObj); 
                    count++;
                    System.out.println(count);
                    
                    for (int j = 0;j<Dates.size();j++){
                    //check line for date & IP, if has both record reputation.  if not at the end of the date for loop add a blank.
                        repRecord = 0; //set record for new date to not recorded
                        inputStream2 = new FileInputStream(filePath);
                        sc2 = new Scanner (inputStream2);
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
