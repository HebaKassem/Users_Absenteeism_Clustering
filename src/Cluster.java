
import java.util.ArrayList;
import java.util.List;

public class Cluster {

    public List points;
    public Point centroid;
    public int id;

    //Creates a new Cluster
    public Cluster(int id) {
        this.id = id;
        this.points = new ArrayList();
        this.centroid = null;
    }

    public List getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void setPoints(List points) {
        this.points = points;
    }

    public Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    public int getId() {
        return id;
    }

    public void clear() {
        points.clear();
    }

    public void plotCluster() {
        System.out.println("[Cluster: " + id+"]");
        System.out.println("[Centroid Absenteeism: " + centroid.getAbsenteeism() + "]");
        System.out.println("[Points: \n");
        System.out.println("empID  rfa  MOA   day  season  trExpen distWork service  age  workLoad  hitTarget  discip  education  son  socDr  socSmok  pet  weight  height  bodyMass absenteeism");

        for(Object point : points) {
            Point p = (Point) point;
            System.out.print(  p.getEmpID()+"   "+  p.reasonForAbsence()+"   "+  p.monthOfAbsence()+"   "+  p.day()+"  "+
                      p.season()+"   "+  p.transportationExpenses()+"   "+  p.distanceToWork()+"     "+  p.service()+
                    "    "+  p.age()+"   "+  p.workLoad()+"   "+  p.hitTarget()+"       "+  p.disciplina()+
                    "     "+  p.education()+"       "+  p.son()+"   "+  p.socialDrinking()+"      "+  p.socialSmoker()+
                    "   "+  p.pet()+"   "+  p.weight()+"   "+  p.height()+"   "+  p.bodyMass()+
                    "      "+  p.getAbsenteeism()+"   ");
            System.out.println();
        }

        System.out.println("]");
    }

}