package pages;


public class Page {

    private PageFactory.PageType page;

    /**
     *
     */
    public Page() {

    }

    /**
     * @param page
     */
    public Page(final PageFactory.PageType page) {
        this.page = page;
    }

    /**
     * @return
     */
    public PageFactory.PageType getPage() {
        return page;
    }

    /**
     * @param page
     */
    public void setPage(final PageFactory.PageType page) {
        this.page = page;
    }

}


