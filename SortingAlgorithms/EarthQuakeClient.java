import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth){
        ArrayList<QuakeEntry> answers = new ArrayList<QuakeEntry>();
        
        for(QuakeEntry quake : quakeData){
            if(quake.getDepth() > minDepth && quake.getDepth() < maxDepth){
                answers.add(quake);
            }
        }
        return answers;
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData,String where, String phrase){
        ArrayList<QuakeEntry> results = new ArrayList<QuakeEntry>();
        for(QuakeEntry quake : quakeData){
            if(quake.getInfo().contains(phrase)){
                if(where.equals("start")){
                    if(quake.getInfo().startsWith(phrase)){
                        results.add(quake);
                    }
                }else if(where.equals("any")){
                    int ind = quake.getInfo().indexOf(phrase);
                    if(ind != -1){
                        results.add(quake);
                    }
                } else if(where.equals("end")){
                    if(quake.getInfo().endsWith(phrase)){
                        results.add(quake);
                    }
                } else {
                    System.out.println("Could not find any methods matching "+where);
                }
            }
        }
        return results;
    }
    
    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry quake : quakeData){
            if(quake.getMagnitude() > magMin){
                answer.add(quake);
            }
        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry quake : quakeData){
            if(from.distanceTo(quake.getLocation()) < distMax){
                answer.add(quake);
            }
        }
        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }
    
    public void quakesByPhrase(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        ArrayList<QuakeEntry> results = filterByPhrase(list,"any","Can");
        System.out.println("Found "+results.size()+" that match search criteria");
        for(QuakeEntry quake : results){
            //System.out.println(quake);
        }
    }
    
    public void quakesOfDepth(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        double minDepth = -4000.0, maxDepth = -2000.0;
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> results = filterByDepth(list, minDepth, maxDepth);
        System.out.println("Found "+results.size()+" matching search criteria");
        for(QuakeEntry quake : results){
            //System.out.println(quake);
        }
    }
    
    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        double magnitude = 5.0;
        ArrayList<QuakeEntry> answers = filterByMagnitude(list,magnitude);
        System.out.println("Total number of quakes above "+magnitude+" is "+answers.size());
        for(QuakeEntry qe : answers){
            System.out.println(qe);
        }
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);
        // This location is Bridgeport, CA
        Location city =  new Location(38.17, -118.82);
        double distMax = 1000.0*1000;//*1000 to convert to kilometers
        ArrayList<QuakeEntry> answers = filterByDistanceFrom(list,distMax,city);
        System.out.println("Number of Quakes found: "+answers.size());
        for(QuakeEntry qe : answers){
            double dist = city.distanceTo(qe.getLocation());
            System.out.println(dist/1000+" "+qe.getInfo());
        }
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
}
