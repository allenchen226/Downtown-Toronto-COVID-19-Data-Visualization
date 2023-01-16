package Filter;

import java.time.*;
import java.util.ArrayList;
import java.util.Objects;
import DataView.IndividualPoint;

/**
 * FilterIndividualPoint class uses the Criteria classes.
 * class represents a singular covid case and includes attributes such as id,
 * gender, infection date, etc.
 *
 */

public class FilterIndividualPoint {

    public static ArrayList<IndividualPoint> filterMale(ArrayList<IndividualPoint> dataPoint){
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new MaleCriteria().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterFemale(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new FemaleCriteria().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterCloseContact(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new CloseContactCriteria().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterCommunity(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new CommunityCriteria().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterHospitalized(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new HospitalizedCriteria().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterHouseHold(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new HouseholdContactCritera().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterMiddleAge(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new MiddleAgeCriteria().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterNotHospitalized(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new NotHospitalizedCriteria().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterOld(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new OldCriteria().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterOutbreak(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new OutbreakCriteria().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterSporadic(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new SporadicFilter().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterTravel(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new TravelCriteria().meetCriteria(dataPoint);
    }
    public static ArrayList<IndividualPoint> filterYoung(ArrayList<IndividualPoint> dataPoint) {
        //ArrayList filtered = new ArrayList<ArrayList<IndividualPoint>>();
        return new YoungCriteria().meetCriteria(dataPoint);
    }
}