import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Point {

    private double empID,reasonForAbsence,monthOfAbsence,day,season,transportationExpenses,distanceToWork=0;
    private double service,age,workLoad,hitTarget,disciplina,education,son,socialDrinking,socialSmoker,pet,weight,height,bodyMass,absenteeism=0;

    Point(){
        this.empID = 0;
        this.absenteeism = 0;
    }
    Point(double empID, double reasonForAbsence, double monthOfAbsence, double day, double season, double transportationExpenses, double distanceToWork, double service, double age, double workLoad,
          double hitTarget, double disciplina, double education, double son, double socialDrinking, double socialSmoker, double pet, double weight, double height, double bodyMass, double absenteeism){
        this.empID = empID;
        this.absenteeism = absenteeism;
        this.reasonForAbsence = reasonForAbsence;
        this.monthOfAbsence = monthOfAbsence;
        this.day = day;
        this.season = season;
        this.transportationExpenses = transportationExpenses;
        this.distanceToWork = distanceToWork;
        this.service = service;
        this.age = age;
        this.workLoad = workLoad;
        this.hitTarget = hitTarget;
        this.disciplina = disciplina;
        this.education = education;
        this.son = son;
        this.socialDrinking = socialDrinking;
        this.socialSmoker = socialSmoker;
        this.pet = pet;
        this.weight = weight;
        this.height = height;
        this.bodyMass = bodyMass;
    }

    public void setEmpId(double empID){
         this.empID = empID;
    }
    public void setAbsenteeism(double absenteeism){
         this.absenteeism = absenteeism;
    }
    public double getEmpID(){
        return this.empID;
    }


    public double getAbsenteeism(){
        return this.absenteeism;
    }

    public double reasonForAbsence(){
        return this.reasonForAbsence;
    }
    public double monthOfAbsence(){
        return this.monthOfAbsence;
    }
    public double day(){
        return this.day;
    }
    public double season(){
        return this.season;
    }
    public double transportationExpenses(){
        return this.transportationExpenses;
    }
    public double distanceToWork(){
        return this.distanceToWork;
    }
    public double service(){
        return this.service;
    }
    public double age(){
        return this.age;
    }
    public double workLoad(){
        return this.workLoad;
    }
    public double hitTarget(){
        return this.hitTarget;
    }
    public double disciplina(){
        return this.disciplina;
    }
    public double education(){
        return this.education;
    }
    public double son(){
        return this.son;
    }
    public double socialDrinking(){
        return this.socialDrinking;
    }
    public double socialSmoker(){
        return this.socialSmoker;
    }
    public double pet(){
        return this.pet;
    }
    public double weight(){
        return this.weight;
    }
    public double bodyMass(){
        return this.bodyMass;
    }public double height(){
        return this.height;
    }

    //Calculates the distance between two points.
    public  double distance( Point centroid,Point p) {//no id
        double distance=0;
        distance = Math.sqrt(Math.pow((centroid.reasonForAbsence - p.reasonForAbsence),2)+
                Math.pow((centroid.monthOfAbsence - p.monthOfAbsence),2)+Math.pow((centroid.day - p.day),2)+
                Math.pow((centroid.season - p.season),2)+Math.pow((centroid.transportationExpenses - p.transportationExpenses),2)+
                Math.pow((centroid.distanceToWork - p.distanceToWork),2)+Math.pow((centroid.service - p.service),2)+
                Math.pow((centroid.age - p.age),2)+Math.pow((centroid.workLoad - p.workLoad),2)+
                Math.pow((centroid.hitTarget - p.hitTarget),2)+Math.pow((centroid.disciplina - p.disciplina),2)+
                Math.pow((centroid.education - p.education),2)+Math.pow((centroid.son - p.son),2)+
                Math.pow((centroid.socialDrinking - p.socialDrinking),2)+Math.pow((centroid.socialSmoker - p.socialSmoker),2)+
                Math.pow((centroid.pet - p.pet),2)+Math.pow((centroid.weight - p.weight),2)+
                Math.pow((centroid.height - p.height),2)+Math.pow((centroid.bodyMass - p.bodyMass),2)+
                Math.pow((centroid.absenteeism - p.absenteeism),2));

        return distance;
    }

  }