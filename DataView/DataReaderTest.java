package DataView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.paint.Color;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to check that covid case data has been accurately read
 */
public class DataReaderTest {
    /**
     * Reads case data and initializes each case as an <IndividualPoint>. Sorts
     * the <IndividualPoint> objects into 16 neighbourhoods.
     *
     * @param args
     * @throws IOException in case the source file cannot be found.
     */
    public static void main(String [] args) throws IOException {
        // Reading in the data using the <initializeData> method from DataReader class.
        ArrayList<IndividualPoint> data = DataReader.initializeData("DataView/FinalCaseData.csv");
        // Sorting the initialized data into 16 neighbourhoods.
        ArrayList<Neighbourhood> sorted = DataReader.sortByNeighbourhood(data);
        System.out.print("Data Set Size: " + data.size() + " cases." + "\n");
        for (Neighbourhood n: sorted) {
            System.out.print("Neighbourhood: " + n.idPostalCode + " has " + n.getCaseCount() + " cases." + "\n");
        }
    }

    /**
     * Test to check that an <IndividualPoint> has been initialized correctly.
     * @throws IOException when the source file cannot be found.
     */
    @Test
    void testPointAttributes() throws IOException {
        ArrayList<IndividualPoint> data = DataReader.initializeData("DataView/FinalCaseData.csv");
        IndividualPoint testCase = data.get(1);
        assertEquals(testCase.id, "5");
        assertEquals(testCase.isOutbreakAssociated(), false);
        assertEquals(testCase.getAge(), "60 to 69 Years");
        assertEquals(testCase.getPostalCode(), "M4W");
        assertEquals(testCase.getSourceInfection(), "Travel");
        LocalDate date = LocalDate.parse("2020-02-26");
        assertEquals(testCase.getInfectionDate(), date);
        assertEquals(testCase.getGender(), "MALE");
        assertEquals(testCase.isHospitalized(), false);
    }

    /**
     * Test to ensure that a <Neighbourhood> has been initialized correctly.
     * Checks that neighbourhood M5H has the correct points in the correct order.
     * @throws IOException in the case that the original file cannot be found.
     */
    @Test
    void testNeighbourhoodSort() throws IOException {
        // Reading in the case data from the ORIGINAL file and storing only
        // the neighbourhoods with the postal code M5H into a list.
        ArrayList<IndividualPoint> casesM5H = null;
        try {
            FileReader compound = new FileReader("DataView/FinalCaseData.csv");
            BufferedReader reader = new BufferedReader(compound);
            String line = reader.readLine();
            line = reader.readLine();

            casesM5H = new ArrayList<>();
            while (line != null) {
                String[] record = line.split(",");
                if (record.length == 10 && record[4].equals("M5H")) {
                    casesM5H.add(new IndividualPoint(record));
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Initializing the data using the DataReader methods.
        ArrayList<IndividualPoint> data = DataReader.initializeData("DataView/FinalCaseData.csv");
        ArrayList<Neighbourhood> sorted = DataReader.sortByNeighbourhood(data);
        assert casesM5H != null;
        // sorted index 0 is the first neighbourhood, which is defined by
        // postal code M5H.
        assertEquals(sorted.get(0).getChildren().size(), casesM5H.size());
        // check if each data point has been initialized correctly.
        for (int i = 0; i < casesM5H.size(); i++) {
            assertEquals(sorted.get(0).getChildren().get(i), casesM5H.get(i));
        }
    }

    /**
     * Testing the addition and removal of new <DataPoint> to a neighbourhood.
     * Simultaneously checks that the computation of case counts is also accurate
     * since the <addChild> method calls the <computeCaseCount> method.
     * @throws IOException in case the input file is not found
     */
    @Test
    void addRemoveComputeCaseCountTest() throws IOException {
        ArrayList<IndividualPoint> data = DataReader.initializeData("DataView/FinalCaseData.csv");
        ArrayList<Neighbourhood> sorted = DataReader.sortByNeighbourhood(data);
        Neighbourhood composite = new Neighbourhood("all", new ArrayList<>());
        for (Neighbourhood neighbourhood : sorted) {
            composite.addChild(neighbourhood);
        }
        assertEquals(22000, composite.getCaseCount());
        composite.removeChild(sorted.get(0));
        composite.removeChild(sorted.get(1));
        composite.removeChild(sorted.get(2));
        assertEquals(20168, composite.getCaseCount());
    }

    /**
     * Checks whether the <computeMapColour> method returns the correct colour
     * for each neighbourhood based on its case count.
     *
     * @throws IOException in case the input file is not found
     */
    @Test
    void neighbourhoodColourTest() throws IOException {
        ArrayList<Color> correctColours = new ArrayList<>();
        // the correct colours for each neighbourhood are input into the test
        // array.
        correctColours.add(Color.rgb(204,229,255, 0.35));
        correctColours.add(Color.rgb(224, 224, 224, 0.4));
        correctColours.add(Color.rgb(255, 204, 153, 0.35));
        correctColours.add(Color.rgb(224, 224, 224, 0.4));
        correctColours.add(Color.rgb(255, 204, 153, 0.35));
        correctColours.add(Color.rgb(255, 204, 153, 0.35));
        correctColours.add(Color.rgb(255, 204, 153, 0.35));
        correctColours.add(Color.rgb(255, 153, 204, 0.35));
        correctColours.add(Color.rgb(255, 204, 153, 0.35));
        correctColours.add(Color.rgb(224, 224, 224, 0.4));
        correctColours.add(Color.rgb(255, 102, 102, 0.4));
        correctColours.add(Color.rgb(204,229,255, 0.35));
        correctColours.add(Color.rgb(255, 204, 153, 0.35));
        correctColours.add(Color.rgb(255, 204, 153, 0.35));
        correctColours.add(Color.rgb(255, 204, 153, 0.35));
        correctColours.add(Color.rgb(255, 153, 204, 0.35));
        // Initializing the data using the DataReader methods.
        ArrayList<IndividualPoint> data = DataReader.initializeData("DataView/FinalCaseData.csv");
        ArrayList<Neighbourhood> sorted = DataReader.sortByNeighbourhood(data);
        // Checking each neighbourhood has the correct colour.
        for (int i = 0; i < sorted.size(); i++) {
            assertEquals(sorted.get(i).getMapColour(), correctColours.get(i));
        }
    }

}
