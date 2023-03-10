package features;


public class Filters {

    private Contains contains;
    private Sort sort;


    /**
     * @param contains
     * @param sort
     */
    public Filters(final Contains contains,
                   final Sort sort) {
        this.contains = contains;
        this.sort = sort;
    }

    /**
     *
     */
    public Filters() {

    }

    /**
     * @return
     */
    public Contains getContains() {
        return contains;
    }

    /**
     * @param contains
     */
    public void setContains(final Contains contains) {
        this.contains = contains;
    }

    /**
     * @return
     */
    public Sort getSort() {
        return sort;
    }

    /**
     * @param sort
     */
    public void setSort(final Sort sort) {
        this.sort = sort;
    }


}
