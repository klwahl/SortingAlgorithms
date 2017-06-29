
/**
 * Write a description of Sort here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Sort {

    public ArrayList<QuakeEntry> sortByMagnitude(ArrayList<QuakeEntry> in){
        for(int i=0; i < in.size(); i++){
            int minInd = getSmallestMagnitude(in,i);
            QuakeEntry curr = in.get(i);
            QuakeEntry min = in.get(minInd);
            in.set(i,min);
            in.set(minInd,curr);
        }
        return in;
    }
    
    
    public int getSmallestMagnitude(ArrayList<QuakeEntry> list, int from){
        int minInd = from;
        for(int i = from+1; i < list.size(); i++){
            if(list.get(i).getMagnitude() < list.get(minInd).getMagnitude()){
                minInd = i;
            }
       }
        return minInd;
    }
    
    public ArrayList<QuakeEntry> readData(){
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        return list;
    }
    
    public void printResults(ArrayList<QuakeEntry> list, boolean printAll){
        System.out.println("Found "+list.size()+" matching filter criteria");
        if(printAll){
            for(QuakeEntry quake : list){
                System.out.println(quake);
            }
        }
    }
    
    public void tester(){
        ArrayList<QuakeEntry> list = readData();
        ArrayList<QuakeEntry> results = sortByMagnitude(list);
        printResults(results,true);
    }
}
