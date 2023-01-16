package DataView;

/**
 * Interface specifying the methods required for each covid data point.
 */
public interface DataPoint {
    /**
     * Returns whether a point is a neighbourhood in and of itself.
     *
     * @return boolean indicating whether a point is a neighbourhood.
     */
    boolean getIsNeighbourhood();

    /**
     * Returns the number of cases within a neighbourhood, or 1 if the point is
     * not a neighbourhood.
     *
     * @return integer specifying the number of cases represented by a point.
     */
    int getCaseCount();
}
