 

import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 
    
    public ArrayList<QuakeEntry> readData(){
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
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
    
    public void quakesWithFilter() { 
        ArrayList<QuakeEntry> list = readData();

        Filter f1 = new MagnitudeFilter(3.5,4.5,"Magnitude");
        Filter f2 = new DepthFilter(-55000.0,-20000.0,"Depth");
        ArrayList<QuakeEntry> mag  = filter(list, f1); 
        ArrayList<QuakeEntry> list2 = filter(mag,f2);
        /*Location city = new Location(39.7392, -104.9903);
        Filter f1 = new PhraseFilter("end","a","Phrase");
        Filter f2 = new DistanceFilter(city,1000000.0,"Distance");
        ArrayList<QuakeEntry> list1 = filter(list,f1);
        ArrayList<QuakeEntry> list2 = filter(list1,f2);*/
        printResults(list2,false); 
    }
    
    public void testMatchAllFilter(){
        ArrayList<QuakeEntry> list = readData();
        
        MatchAllFilter f = new MatchAllFilter();
        f.addFilter(new MagnitudeFilter(1.0,4.0,"Magnitude"));
        f.addFilter(new DepthFilter(-180000.0,-30000.0,"Depth"));
        f.addFilter(new PhraseFilter("any","o","Phrase"));
        
        ArrayList<QuakeEntry> results = filter(list,f);
        printResults(results,false);
    }
    
    public void testMatchAllFilter2(){
        ArrayList<QuakeEntry> list = readData();
        
        MatchAllFilter f = new MatchAllFilter();
        f.addFilter(new MagnitudeFilter(0.0,5.0,"Magnitude"));
        f.addFilter(new DistanceFilter(new Location(55.7308, 9.1153),3000000.0,"Distance"));
        f.addFilter(new PhraseFilter("any","e","Phrase"));
        
        ArrayList<QuakeEntry> results = filter(list,f);
        printResults(results,false);
        System.out.println("Filters use are: "+f.getName());
    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }

}
