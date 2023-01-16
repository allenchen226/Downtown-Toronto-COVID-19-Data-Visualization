package DataView;

import java.io.*;
import java.util.*;

/**
 * DataReader class. Used to read in the original file containing covid case
 * data and sort it into the 16 major neighbourhoods in downtown Toronto.
 */
public class DataReader {
    /**
     * Method that reads in a csv file containing covid case data, stores each
     * case as an <IndividualPoint>, and returns a list containing all cases
     * as instances of <IndividualPoint>.
     *
     * @param file String containing the name of the file to be read.
     * @return ArrayList containing
     * @throws IOException in case the filename passed by the parameter cannot be found.
     */
    public static ArrayList<IndividualPoint> initializeData(String file) throws IOException {
        try {
            FileReader compound = new FileReader(file);
            BufferedReader reader = new BufferedReader(compound);
            String line = reader.readLine();
            line = reader.readLine();

            ArrayList<IndividualPoint> cases = new ArrayList<>();
            while (line != null) {
                String[] record = line.split(",");
                if (record.length == 10) {
                    cases.add(new IndividualPoint(record));
                }
                line = reader.readLine();
            }
            return cases;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given covid data as a list of <IndividualPoint> objects, sorts each point
     * into one of the 16 major neighbourhoods based on postal code.
     * Returns a list of the 16 neighbourhoods, where each neighbourhood contains
     * cases that occurred within that postal code.
     *
     * @param allData list containing covid cases as <IndividualPoint> objects.
     * @return List of neighbourhoods. Each neighbourhood contains the cases that occurred
     * within its postal code.
     */
    public static ArrayList<Neighbourhood> sortByNeighbourhood(ArrayList<IndividualPoint> allData) {
        ArrayList<Neighbourhood> sortedNeighbourhoods = new ArrayList<>();
        String postalCodes = "M5H,M5K,M5J,M5L,M5S,M5T,M4V,M4X,M4W,M5X,M4Y,M5C,M5B,M5E,M5G,M6G";
        String [] allNeighs = postalCodes.split(",");
        HashMap<String, ArrayList<DataPoint>> mapForInit = new HashMap<>();
        for (String s: allNeighs) {
            mapForInit.put(s, new ArrayList<>());
        }
        for (IndividualPoint p: allData) {
            String postalCode = p.getPostalCode();
            mapForInit.get(postalCode).add(p);
        }
        for (String pc: mapForInit.keySet()) {
            sortedNeighbourhoods.add(new Neighbourhood(pc, mapForInit.get(pc)));
        }
        return sortedNeighbourhoods;
    }

}
