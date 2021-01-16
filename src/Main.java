import com.sun.rowset.internal.Row;
import javafx.scene.control.Cell;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main {

    private  List<Cluster> clusters = new ArrayList<Cluster>();
    private  List<Point>  points = new ArrayList<Point>();


    public  String run(Integer k, float percent) throws IOException {
        String result=" ";
       createTuples(percent);
        init(k,percent);
        calculate(k);

        return result;
    }

    public  void init( Integer k, float percent ){

        //set random centroids
        initRandomCentroids(k, percent);

        //plotClusters(k);
    }
    private  void initRandomCentroids( int k, float percent) {
        long max = getStopLine(percent);
        max--;
        //System.out.println("max is "+max);

        Random ran = new Random();
        for(int i =0; i < k; i++){
            Cluster cluster = new Cluster(i);

            int randi = Math.abs(ran.nextInt((int) max)-1);
           // System.out.println("rand is "+randi);

            Point centroid = points.get(randi-1) ;
            cluster.setCentroid(centroid);
            clusters.add(cluster);

        }
    }
    private  void plotClusters(int k) {
        for(int i = 0; i < k ;i++){
            Cluster c = clusters.get(i);
            c.plotCluster();
        }
    }


    //group pts and recalculate centroids
    private  void calculate(Integer k){
        int iteration = 0;
        boolean finish = false;
        while(!finish) {
          //clear the pts
          clearClusters();

          List<Point> lastCentroids = getCentroids(k);

          //add pts to nearest cluster
          assignCluster();

          //calc new centroids
          calculateCentroids();

          iteration ++;

          List<Point> currentCentroids = getCentroids(k);

          System.out.println("#################");
          System.out.println("Iteration: " + iteration);
          plotClusters(k);
           double distance=0;
            for(int i = 0; i < lastCentroids.size(); i++) {
                Point pt = new Point();
                distance += pt.distance(lastCentroids.get(i),currentCentroids.get(i));
            }

          if(distance==0 ) {
            System.out.println("centroids are now equal ");
            finish = true;
              System.out.println(showOutliers());

          }

        }
    }

    private  String  showOutliers() {
        List<Point> outliers = new ArrayList<Point>();
        float ptsToMeanSum = 0;
        String outliersString="OUTLIERS are.. \n";

        for (Object c : clusters) {
            outliers.clear();
            ptsToMeanSum=0;//
            Cluster cluster = (Cluster) c;
            Point aux = cluster.getCentroid();
            List list = cluster.getPoints();
            int j = 0;
            for (Object p : list) {
                Point point = (Point) p;
                Point pt=new Point();
                double ptToMeanDistance = pt.distance(aux,point);
                ptsToMeanSum += ptToMeanDistance;
                j++;
            }
            double ptsToMeanavg = ptsToMeanSum / j;

            for (Object p : list) {
                Point point = (Point) p;
                Point pt = new Point();
                double distance = pt.distance(aux,point);
                if (distance > ptsToMeanavg){
                    outliers.add(point);
                 }
                }
            outliersString += "empID  rfa  MOA   day  season  trExpen distWork service  age  workLoad  hitTarget  discip  education  son  socDr  socSmok  pet  weight  height  bodyMass absenteeism\n";

            for(int i=0; i < outliers.size(); i++){

                outliersString += outliers.get(i).getEmpID()+"   "+outliers.get(i).reasonForAbsence()+"   "+outliers.get(i).monthOfAbsence()+"   "+outliers.get(i).day()+"  "+
                        outliers.get(i).season()+"   "+outliers.get(i).transportationExpenses()+"   "+outliers.get(i).distanceToWork()+"     "+outliers.get(i).service()+
                        "    "+outliers.get(i).age()+"   "+outliers.get(i).workLoad()+"   "+outliers.get(i).hitTarget()+"       "+outliers.get(i).disciplina()+
                        "     "+outliers.get(i).education()+"       "+outliers.get(i).son()+"   "+outliers.get(i).socialDrinking()+"      "+outliers.get(i).socialSmoker()+
                        "   "+outliers.get(i).pet()+"   "+outliers.get(i).weight()+"   "+outliers.get(i).height()+"   "+outliers.get(i).bodyMass()+
                        "      "+outliers.get(i).getAbsenteeism()+"   ";
                outliersString+="\n";
            }

                outliersString+="**\n";

            }

        return outliersString;
    }
        /*
            int centroidAbsenteeism = aux.getAbsenteeism();

            for (Object p : list) {
                Point point = (Point) p;
                int pointEmpID = point.getEmpID();
                int pointAbsenteeism = point.getAbsenteeism();
                // System.out.println("Point is " + (point).getAbsenteeism());
                double distance = Math.sqrt(Math.pow((pointAbsenteeism - centroidAbsenteeism),2));
                System.out.println(pointEmpID+"  "+pointAbsenteeism+ "  distance is "+distance);

                if(distance > 10){
                    outliers.add(point);
                    System.out.println(pointEmpID+"  "+ pointAbsenteeism + " is an outlier");
                    System.out.println("");
                }
            }
        }
        return outliers;

    }*/


   private List getCentroids(int k) { //fixed :)
       List<Point> centroids = new ArrayList(k);
       for(Object c : clusters) {
           Cluster cluster = (Cluster) c;
           Point aux = cluster.getCentroid();
           centroids.add(aux);
       }
       return centroids;
   }


    private void calculateCentroids() {
        for (Object c : clusters) {
            Cluster cluster = (Cluster) c;
            double reasonForAbsenceSum=0,monthOfAbsenceSum=0,daySum=0,seasonSum=0,transportationExpensesSum=0,distanceToWorkSum=0;
            double serviceSum=0,ageSum=0,workLoadSum=0,hitTargetSum=0,disciplinaSum=0,educationSum=0,sonSum=0,socialDrinkingSum=0,
                     socialSmokerSum=0,petSum=0,weightSum=0,heightSum=0,bodyMassSum=0,absenteeismSum=0;

            List list = cluster.getPoints();
            int n_points = list.size();

            for (Object p : list) {
                Point point = (Point) p;
                reasonForAbsenceSum += point.reasonForAbsence();
                monthOfAbsenceSum += point.monthOfAbsence();
                daySum += point.day();
                seasonSum += point.season();
                transportationExpensesSum += point.transportationExpenses();
                distanceToWorkSum += point.distanceToWork();
                serviceSum += point.service();
                ageSum += point.age();
                workLoadSum += point.workLoad();
                hitTargetSum += point.hitTarget();
                disciplinaSum += point.disciplina();
                educationSum += point.education();
                sonSum += point.son();
                socialSmokerSum += point.socialSmoker();
                socialDrinkingSum += point.socialDrinking();
                petSum += point.pet();
                weightSum += point.weight();
                heightSum += point.height();
                bodyMassSum += point.bodyMass();
                absenteeismSum += point.getAbsenteeism();
               // System.out.println("Point is " + (point).getAbsenteeism());
            }

            if(n_points > 0) {
                double reasonForAbsence =  reasonForAbsenceSum / n_points;
                double monthOfAbsence =  monthOfAbsenceSum / n_points;
                double day =  daySum / n_points;
                double season =  seasonSum / n_points;
                double transportationExpenses =  transportationExpensesSum / n_points;
                double distanceToWork =  distanceToWorkSum / n_points;
                double service =  serviceSum / n_points;
                double age =  ageSum / n_points;
                double workLoad =  workLoadSum / n_points;
                double hitTarget =  hitTargetSum / n_points;
                double disciplina =  disciplinaSum / n_points;
                double education =  educationSum / n_points;
                double son =  sonSum / n_points;
                double socialDrinking =  socialDrinkingSum / n_points;
                double socialSmoker =  socialSmokerSum / n_points;
                double pet =  petSum / n_points;
                double weight =  weightSum / n_points;
                double height =  heightSum / n_points;
                double bodyMass =  bodyMassSum / n_points;
                double absenteeism =  absenteeismSum / n_points;
                double id=0;
                Point p = new Point(id,reasonForAbsence,monthOfAbsence,day,season,transportationExpenses,distanceToWork,service,age,workLoad,hitTarget,disciplina,education,son,
                        socialDrinking,socialSmoker,pet,weight,height,bodyMass,absenteeism);

                cluster.setCentroid(p);
            }

        }
    }

    public  void clearClusters(){
        for(int i = 0; i < clusters.size(); i++){
            Cluster c = clusters.get(i);
            c.clear();
        }
    }

    private  void assignCluster() {

        int clusterIndex;
        double minDistance;

        for (Object point : points) {
             clusterIndex = 0;
             minDistance = 1000;
             Point p = (Point) point;
            for (int j = 0; j < clusters.size(); j++) {
                Cluster c = clusters.get(j);
                Point pt = new Point();
                double distance = pt.distance(c.centroid,p);
                if (distance < minDistance) {
                    minDistance = distance;
                    clusterIndex = j;
                }
            }
            //System.out.println("add pt to cluster"+clusterIndex);
            //p.setCluster(clusterIndex);
            Cluster c =  clusters.get(clusterIndex);
            c.addPoint(p);
        }

    }


        private  void createTuples( float percent) throws IOException {
        Long stopLine;
        stopLine = getStopLine(percent);

        String fileName="D:\\FCI =)\\4th\\y4t1\\DM\\Assignments\\a2_Clustering\\a2_clustering\\Absenteeism_at_work.xls";
        readInput(fileName,stopLine);

    }

    public  void readInput(String ifile, long stopLine) throws IOException {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(ifile));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            //HSSFCell cell;

            int rows; // No of rows
            rows = (int) stopLine;

            int cols = 0; // No of columns
            int tmp = 0;

            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for(int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if(row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if(tmp > cols) cols = tmp;
                }
            }

            for(int r = 1; r < rows; r++) {
                row = sheet.getRow(r);
                if(row != null) {
                    //System.out.println("Row...");
                       double id = Double.parseDouble(row.getCell(0).toString());
                       double reasonForAbsence = Double.parseDouble(row.getCell(1).toString());
                       double monthOfAbsence = Double.parseDouble(row.getCell(2).toString());
                       double day = Double.parseDouble(row.getCell(3).toString());
                       double season = Double.parseDouble(row.getCell(4).toString());
                       double transportationExpenses = Double.parseDouble(row.getCell(5).toString());
                       double distanceToWork = Double.parseDouble(row.getCell(6).toString());
                       double service = Double.parseDouble(row.getCell(7).toString());
                       double age = Double.parseDouble(row.getCell(8).toString());
                       double workLoad = Double.parseDouble(row.getCell(9).toString());
                       double hitTarget = Double.parseDouble(row.getCell(10).toString());
                       double disciplina = Double.parseDouble(row.getCell(11).toString());
                       double education = Double.parseDouble(row.getCell(12).toString());
                       double son = Double.parseDouble(row.getCell(13).toString());
                       double socialDrinking = Double.parseDouble(row.getCell(14).toString());
                       double SocialSmoker = Double.parseDouble(row.getCell(15).toString());
                       double pet = Double.parseDouble(row.getCell(16).toString());
                       double weight = Double.parseDouble(row.getCell(17).toString());
                       double height = Double.parseDouble(row.getCell(18).toString());
                       double bodyMass = Double.parseDouble(row.getCell(19).toString());
                       double absenteeism = Double.parseDouble(row.getCell(20).toString());


                    Point p = new Point(id,reasonForAbsence,monthOfAbsence,day,season,transportationExpenses,distanceToWork,service,age,workLoad,hitTarget,disciplina,education,son,
                            socialDrinking,SocialSmoker,pet,weight,height,bodyMass,absenteeism);
                    points.add(p);
                    }
            }

        } catch(Exception ioe) {
            ioe.printStackTrace();
        }
    }

    private  long getStopLine( float percentage) {
        double totalLines;
        long stopLine;

        totalLines = 740;
        stopLine = Math.round((percentage/100.0)*totalLines);
        System.out.println("stop line is "+stopLine);

        return stopLine;
    }
    private  void assignPoints(List<List<Integer>> tuples) {
        points.clear();
        for(int i = 0; i < tuples.size(); i++){
            Point p = new Point() ;
            //new Point( tuples.get(i).get(0), tuples.get(i).get(1));
            p.setEmpId(tuples.get(i).get(0));
            p.setAbsenteeism(tuples.get(i).get(1));
            points.add(p);
            // System.out.println(i+" "+p.getAbsenteeism());
        }

        for(int i=0; i < points.size(); i++){
            Point p = points.get(i);
            System.out.println(p.getEmpID()+" "+p.getAbsenteeism());
        }
    }

    public  int getRandomIndex(float percent){
        long max = getStopLine(percent);
        max--;
        //System.out.println("max is "+max);
        Random ran = new Random();
        int x = ran.nextInt(6);
        return x;
    }

}
