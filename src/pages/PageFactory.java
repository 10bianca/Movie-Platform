package pages;


/**
 * Factory pattern
 */
public final class PageFactory {
    /**
     * folosim enum pentru a retine pagina curenta
     */
    public enum PageType {
        HomePageA,
        HomePageN,
        Login,
        Logout,
        Movies,
        Register,
        SeeDetails,
        Upgrades
    }

    private PageFactory() {

    }

    /**
     * @param pageType
     * @return
     */
    public static Page createPage(final String pageType) {
        switch (pageType) {
            case "HomePageA":
                return new HomePageA();
            case "HomePageN":
                return new HomePageN();
            case "login":
                return new Login();
            case "logout":
                return new Logout();
            case "movies":
                return new Movies();
            case "register":
                return new Register();
            case "see details":
                return new SeeDetails();
            case "upgrades":
                return new Upgrades();
        }
        /**
         * daca nu e niciun tip din cele de mai sus
         */
        return null;
    }

}
