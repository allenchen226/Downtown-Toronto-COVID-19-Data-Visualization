package DataView;

import java.time.*;
import java.util.Objects;

/**
 * IndividualPoint class implements the DataPoint interface. An instance of this
 * class represents a singular covid case and includes attributes such as id,
 * gender, infection date, etc.
 *
 */
public class IndividualPoint implements DataPoint {
    /**
     * Unique string identifier for each case.
     */
    public String id;
    /**
     * Number of cases represented by this point; is always 1.
     */
    private final int caseCount;
    /**
     * Indicates whether this point is a neighbourhood, i.e. is comprised of
     * other data points. Always false.
     */
    private final boolean isNeighbourhood;
    /**
     * Biological sex of the infected individual represented by this object.
     */
    private final String gender;
    /**
     * Reported date of infection as a LocalDate object.
     */
    private final LocalDate infectionDate;
    /**
     * Whether this case was associated with an outbreak, or sporadic.
     */
    private final boolean outbreakAssociated;
    /**
     * Age category of the affected individual.
     */
    private final String age;
    /**
     * Postal code where the case was reported; used for sorting cases based on
     * neighbourhood.
     */
    private final String postalCode;
    /**
     * Source of infection, i.e. travel, close contact, etc.
     */
    private final String sourceInfection;
    /**
     * Whether the patient was hospitalized.
     */
    private final boolean hospitalized;

    /**
     * Class constructor, used to initialize an <IndividualPoint>.
     *
     * @param input String array containing data on a single case:
     *              input[0] unique identifier for each case.
     *              input[1] outbreak association of a case
     *              input[2] age category of infected individual
     *              input[3] infected individual's neighbourhood by NAME
     *              input[4] infected individual's neighbourhood by POSTAL CODE
     *              input[5] source of infection (travel, close contact, etc.)
     *              input[6] date case was reported in M/dd/YYYY format
     *              input[7] gender of infected individual.
     *              input[8] whether the case was resolved
     *              input[9] whether the infection resulted in hospitalization.
     */

    public IndividualPoint(String [] input) {
        this.id = input[0];
        this.caseCount = 1;
        this.isNeighbourhood = false;
        this.gender = input[7];
        // Converting date from the input into ISO_LOCAL_DATE format.
        String [] date = input[6].split("/");
        String month = date[0];
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = date[1];
        if (day.length() == 1) {
            day = "0" + day;
        }
        String newDate = date[2] + "-" + month + "-" + day;
        this.infectionDate = LocalDate.parse(newDate);
        this.outbreakAssociated = getAssociation(input[1]);
        this.age = input[2];
        this.postalCode = input[4];
        this.sourceInfection = input[5];
        this.hospitalized = getHospitalization(input[9]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndividualPoint that = (IndividualPoint) o;
        return caseCount == that.caseCount && isNeighbourhood == that.isNeighbourhood
                && outbreakAssociated == that.outbreakAssociated &&
                hospitalized == that.hospitalized && id.equals(that.id) &&
                gender.equals(that.gender) && infectionDate.equals(that.infectionDate)
                && age.equals(that.age) && postalCode.equals(that.postalCode) &&
                sourceInfection.equals(that.sourceInfection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caseCount);
    }

    /**
     * Getter method for neighbourhood status.
     * @return whether this <IndividualPoint> is a neighbourhood.
     */
    @Override
    public boolean getIsNeighbourhood() {
        return this.isNeighbourhood;
    }

    /**
     * Getter method.
     * @return number of cases represented by this point
     */
    @Override
    public int getCaseCount() {
        return this.caseCount;
    }

    /**
     * Getter method.
     * @return gender of the infected individual represented by this point.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Getter method
     * @return whether the individual was hospitalized.
     */
    public boolean isHospitalized() {
        return hospitalized;
    }

    /**
     * Getter method.
     * @return whether the case was related to an outbreak.
     */
    public boolean isOutbreakAssociated() {
        return outbreakAssociated;
    }

    /**
     * Getter method.
     * @return Age category of the infected individual.
     */
    public String getAge() {
        return age;
    }

    /**
     * Getter method
     * @return postal code of the infected individual.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Getter method
     * @return source of infection, i.e travel, outbreak, etc.
     */
    public String getSourceInfection() {
        return sourceInfection;
    }

    /**
     * Getter method.
     * @return date of infection.
     */
    public LocalDate getInfectionDate() {
        return infectionDate;
    }

    /**
     * Given an input string describing the association of the case, returns
     * whether it was outbreak related.
     * @param input String that is either equal to "Outbreak" or "Sporadic"
     * @return whether the case was related to an outbreak.
     */
    private boolean getAssociation(String input) {
        return !input.equals("Sporadic");
    }

    /**
     * Given an input string describing the hospitalization, returns whether
     * the case resulted in hospitalization.
     * @param input String equal to either "Yes" or "No"
     * @return whether the individual was hospitalized.
     */
    private boolean getHospitalization(String input) {
        return !input.equals("No");
    }

}
