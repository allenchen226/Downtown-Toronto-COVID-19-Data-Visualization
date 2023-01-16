package CovidCarriers;
import CovidCarriers.observer.PostalCodesObserver;
import DataView.*;
import DataView.Neighbourhood;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class ObserverTests {
    @Test
    void UpdateTest() {
        HashMap<String, Integer> cases = new HashMap<>();
        cases.put("M5H",377);
        PostalCodesObserver o = new Residents();
        o.update(cases);
        assertEquals(377, ((Residents) o).getCases("M5H"));
    }
    @Test
    void AddPostalCodeTest() throws IOException {
        ArrayList<IndividualPoint> point = DataReader.initializeData("FinalCaseData.csv");
        ArrayList<Neighbourhood> postal_codes = DataReader.sortByNeighbourhood(point);
        PostalCodes code = new PostalCodes();
        assertEquals(Collections.emptyMap(), code.getPostalCodesList()); // empty list
        code.addPostalCode(postal_codes.get(0));
        code.addPostalCode(postal_codes.get(1));
        assertEquals(377, code.getPostalCodesList().get("M5H"));
        assertEquals(11, code.getPostalCodesList().get("M5K"));
    }
    @Test
    void NotifyTest() throws IOException {
        ArrayList<IndividualPoint> point = DataReader.initializeData("FinalCaseData.csv");
        ArrayList<Neighbourhood> postal_codes = DataReader.sortByNeighbourhood(point);
        PostalCodes code = new PostalCodes();
        Residents p = new Residents();
        code.register(p);
        code.addPostalCode(postal_codes.get(0));
        p.update(code.getPostalCodesList());
        assertEquals(377, p.getCases("M5H"));

    }

}
