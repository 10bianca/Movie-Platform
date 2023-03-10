package actions;

import input.ActionsInput;
import input.MoviesInput;
import input.UsersInput;
import com.fasterxml.jackson.databind.node.ArrayNode;
import pages.Login;
import pages.Page;
import pages.PageFactory;
import pages.Register;
import users.Credentials;

import java.util.ArrayList;
import java.util.Objects;

public class OnPage {


    private Page currentPage; //mandatory
    private ActionsInput actionsInput; //mandatory
    private ArrayList<MoviesInput> currentMovieList; //mandatory
    private UsersInput currentUser; //mandatory

    private ArrayList<Page> pages; //optional
    private ArrayList<UsersInput> users; //optional

    private ArrayList<MoviesInput> movies; //optional

    public static class Builder {
        private Page currentPage; //mandatory
        private ActionsInput actionsInput; //mandatory
        private ArrayList<MoviesInput> currentMovieList; //mandatory

        private ArrayList<Page> pages; //optional

        private ArrayList<MoviesInput> movies; //optional
        private UsersInput currentUser; //mandatory

        private ArrayList<UsersInput> users; //optional

        /**
         * @param currentPage
         * @param actionsInput
         */
        public Builder(final Page currentPage, final ActionsInput actionsInput) {
            this.currentPage = currentPage;
            this.actionsInput = actionsInput;
        }

        /**
         * @param pages1
         * @return
         */
        public Builder pages(final ArrayList<Page> pages1) {
            this.pages = pages1;
            return (this);
        }


        /**
         * @param currentMovieList1
         * @return
         */
        public Builder currentMovieList(final ArrayList<MoviesInput> currentMovieList1) {
            this.currentMovieList = currentMovieList1;
            return (this);
        }

        /**
         * @param movies1
         * @return
         */
        public Builder movies(final ArrayList<MoviesInput> movies1) {
            this.movies = movies1;
            return (this);
        }

        /**
         * @param currentUser1
         * @return
         */
        public Builder currentUser(final UsersInput currentUser1) {
            this.currentUser = currentUser1;
            return (this);
        }

        /**
         * @param users1
         * @return
         */
        public Builder users(final ArrayList<UsersInput> users1) {
            this.users = users1;
            return (this);
        }

        /**
         * @return
         */
        public OnPage build() {
            return new OnPage(this);
        }

    }


    /**
     *
     */
    public OnPage() {

    }

    /**
     * @param builder
     */
    public OnPage(final Builder builder) {
        this.currentPage = builder.currentPage;
        this.actionsInput = builder.actionsInput;
        this.currentMovieList = builder.currentMovieList;
        this.pages = builder.pages;
        this.movies = builder.movies;
        this.currentUser = builder.currentUser;
        this.users = builder.users;

    }

    /**
     * @param page
     * @param feature
     * @return 0 - daca nu se poate realiza actiunea ceruta pe pagina curenta
     * 1 - altfel
     */
    public int match(final Page page, final String feature) {

        if (Objects.equals(feature, "login")
                && page.getPage() != PageFactory.PageType.Login) {
            return 0;
        } else if (Objects.equals(feature, "register")
                && page.getPage() != PageFactory.PageType.Register) {
            return 0;
        } else if (Objects.equals(feature, "search")
                && page.getPage() != PageFactory.PageType.Movies) {
            return 0;
        } else if (Objects.equals(feature, "filter")
                && page.getPage() != PageFactory.PageType.Movies) {
            return 0;
        } else if (Objects.equals(feature, "purchase")
                && page.getPage() != PageFactory.PageType.SeeDetails) {
            return 0;
        } else if (Objects.equals(feature, "watch")
                && page.getPage() != PageFactory.PageType.SeeDetails) {
            return 0;
        } else if (Objects.equals(feature, "like")
                && page.getPage() != PageFactory.PageType.SeeDetails) {
            return 0;
        } else if (Objects.equals(feature, "rate the movie")
                && page.getPage() != PageFactory.PageType.SeeDetails) {
            return 0;
        } else if (Objects.equals(feature, "buy premium account")
                && page.getPage() != PageFactory.PageType.Upgrades) {
            return 0;
        } else if (Objects.equals(feature, "buy tokens")
                && page.getPage() != PageFactory.PageType.Upgrades) {
            return 0;
        }

        return 1;
    }

    /**
     * @param feature
     * @return 0 - daca se intampina o eroare
     * 1 - altfel
     */
    public int returnError(final String feature) {
        Credentials credentials = actionsInput.getCredentials();
        /**
         * se apeleaza functia de verificare
         * a actiunii in cadrul paginii curente
         */
        if (match(currentPage, feature) == 0) {
            return 0;
        }

        /**
         * daca actiunea este login sau register
         * verificam daca utilizatorul se afla printre cei inregistrati
         */
        if (Objects.equals(feature, "login")) {
            Login login = new Login(users);
            if (login.isUser(credentials) == -1) {
                /**
                 * daca nu se afla si actiunea este login
                 * rezulta eroare
                 */
                currentPage.setPage(PageFactory.PageType.HomePageN);
                return 0;
            }
        } else if (Objects.equals(feature, "register")) {
            Register register = new Register(users);
            if (register.isUser(credentials) == 0) {
                /**
                 * daca se afla si actiunea este register
                 * rezulta eroare
                 */
                currentPage.setPage(PageFactory.PageType.HomePageN);
                return 0;
            }
        }

        return 1;
    }

    /**
     * @param output
     */
    public void error(final ArrayNode output) {
        ArrayList<MoviesInput> emptyMovies = new ArrayList<>();
        output.addObject().putPOJO("error", "Error").
                putPOJO("currentMoviesList", emptyMovies).
                putPOJO("currentUser", null);
    }

    /**
     * @param output
     */
    public void outputRegister(final ArrayNode output) {
        /**
         * deep copy pentru currentUser
         */
        String nextPage = currentPage.getPage().name();
        ChangePage changePage = new ChangePage.Builder(currentPage, nextPage, pages).
                currentUser(currentUser).build();
        UsersInput user = changePage.deepCopyCurrentUser();
        ArrayList<MoviesInput> emptyMovies = new ArrayList<>();

        output.addObject().putPOJO("error", null).
                putPOJO("currentMoviesList", emptyMovies).
                putPOJO("currentUser", user);
    }

    /**
     * @param output
     */
    public void output1(final ArrayNode output) {
        String nextPage = currentPage.getPage().name();
        /**
         * deep copy currentMovieList
         */
        ChangePage changePage = new ChangePage.Builder(currentPage, nextPage, pages).
                currentUser(currentUser).currentMovieList(currentMovieList).build();
        ArrayList<MoviesInput> movieCopy = changePage.deepCopyCurrentMovieList(currentMovieList);

        /**
         * deep copy pentru currentUser
         */

        UsersInput user = changePage.deepCopyCurrentUser();

        output.addObject().putPOJO("error", null).
                putPOJO("currentMoviesList", movieCopy).
                putPOJO("currentUser", user);

    }

    /**
     * @param feature seteaza utilizatorul curent
     *                in caz de login sau register
     */
    public UsersInput setUser(final String feature) {
        Credentials credentials = actionsInput.getCredentials();
        if (Objects.equals(feature, "login")) {
            Login login = new Login(users);
            int j = login.isUser(credentials);
            currentPage.setPage(PageFactory.PageType.HomePageA);
            return users.get(j);

        } else if (Objects.equals(feature, "register")) {
            Register register = new Register(users);
            register.addUser(credentials);
            currentPage.setPage(PageFactory.PageType.HomePageA);
            return users.get(users.size() - 1);
        }
        return currentUser;
    }

    /**
     * @param moviesList
     * @return
     */

    public ArrayList<MoviesInput> setMovieList(final ArrayList<MoviesInput> moviesList) {
        /**
         * parcurgem fiecare film
         * si fiecare tara in care acesta este interzis
         * si verficam daca aceasta tara este cea in care
         * se afla utilizatorul curent
         */
        for (int i = 0; i < moviesList.size(); i++) {
            int error = 0;
            int size = moviesList.get(i).getCountriesBanned().size();
            for (int j = 0; j < size; j++) {
                if (currentUser != null
                        && Objects.equals(currentUser.getCredentials().getCountry(),
                        moviesList.get(i).getCountriesBanned().get(j))) {
                    error = 1;
                }
            }
            /**
             * verificam ca filmul sa nu se afle
             * deja in lista de filme a utilizatorului
             */
            if (currentMovieList != null) {
                for (int j = 0; j < currentMovieList.size(); j++) {
                    if (Objects.equals(currentMovieList.get(j).getName(),
                            moviesList.get(i).getName())) {
                        error = 1;
                    }
                }
            }


            /**
             * daca am gasit un film
             * care se poate difuza in tara utilizatorului
             * il aifsam si crestem lista filmelor care pot fi vizionate de acesta
             */
            if (error == 0) {
                /**
                 * deep copy pentru currentMoviesList
                 */
                ChangePage.copy(moviesList, currentMovieList, i);
            }
        }
        return currentMovieList;
    }

    /**
     * @param list1
     * @param list2
     */
    public void restoreList(final ArrayList<MoviesInput> list1,
                            final ArrayList<MoviesInput> list2) {
        list1.clear();
        for (int i = 0; i < list2.size(); i++) {
            list1.add(list2.get(i));
        }
    }


}
