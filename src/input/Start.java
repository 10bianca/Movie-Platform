package input;

import actions.*;
import features.Contains;

import features.Notifications;
import features.Search;
import features.Sort;
import pages.Page;
import com.fasterxml.jackson.databind.node.ArrayNode;
import pages.PageFactory;
import pages.SeeDetails;
import pages.Upgrades;

import java.util.ArrayList;
import java.util.Objects;


public class Start {
    private static Input inputJson;
    private static ArrayNode outputJson;

    private static final int MAX_RATE = 5;

    /**
     * @param inputJson
     * @param outputJson
     */
    public Start(final Input inputJson, final ArrayNode outputJson) {
        this.inputJson = inputJson;
        this.outputJson = outputJson;

    }


    /**
     *
     */
    public void startPlatform() {

        ArrayList<UsersInput> users = inputJson.getUsers();
        ArrayList<MoviesInput> movies = inputJson.getMovies();
        ArrayList<ActionsInput> actions = inputJson.getActions();
        /**
         * incepem cu pagina curenta
         * HomePage Neautentificat
         */
        Page currentPage = new Page(PageFactory.PageType.HomePageN);
        ArrayList<Page> pages = new ArrayList<>();
        UsersInput currentUser = new UsersInput();
        ArrayList<MoviesInput> currentMovieList = new ArrayList<>();
        ArrayList<MoviesInput> allMovieList = new ArrayList<>();
        /**
         * string care retine filmul
         * cu care trebuie lucrat
         */
        String movie = null;
        /**
         * suma fiecarui rating
         */
        ArrayList<Double> sumRate = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            sumRate.add(i, (double) 0);
        }
        /**
         * pentru fiecare user initializam o lista
         * de rating-uri pentru fiecare film
         */
        for (int j = 0; j < users.size(); j++) {
            rateForRatedMoviesForUser(movies, users.get(j));
        }
        for (int j = 0; j < actions.size(); j++) {
            String type = actions.get(j).getType();
            String nextPage = actions.get(j).getPage();

            /**
             * verificam de ce tip este actiunea
             */
            int val = actions.get(j).verifyType(type);

            if (val == 1) {
                movie = typeChangePage(actions, currentPage, pages, currentUser,
                        currentMovieList, allMovieList, movie, j, nextPage);
            } else if (val == 0) {
                OnPage onPage = new OnPage.Builder(currentPage, actions.get(j)).
                        currentMovieList(currentMovieList).
                        users(users).build();
                String feature = actions.get(j).getFeature();
                if (onPage.returnError(feature) == 1) {
                    if (Objects.equals(feature, "login") || Objects.equals(feature, "register")) {
                        currentMovieList.clear();
                        allMovieList.clear();
                        currentUser = onPage.setUser(feature);
                        onPage = new OnPage.Builder(currentPage, actions.get(j)).
                                currentUser(currentUser).
                                currentMovieList(currentMovieList).build();
                        currentMovieList = onPage.setMovieList(movies);
                        onPage = new OnPage.Builder(currentPage, actions.get(j)).
                                currentUser(currentUser).
                                currentMovieList(currentMovieList).build();
                        onPage.restoreList(allMovieList, currentMovieList);
                        /**
                         * daca user-ul este nou
                         * trebuie sa i initializam lista de rateForRatedMovies
                         */
                        if (feature.equals("register")) {
                            rateForRatedMoviesForUser(movies, currentUser);
                        }
                        onPage.outputRegister(outputJson);
                    } else if (Objects.equals(feature, "search")) {
                        currentMovieList = getMoviesAfterSearch(actions, currentPage,
                                currentUser, currentMovieList, j);
                    } else if (Objects.equals(feature, "filter")) {
                        filterMovieList(actions, currentPage, currentUser,
                                currentMovieList, allMovieList, j);
                    } else if (Objects.equals(feature, "buy tokens")) {
                        Upgrades upgrades = new Upgrades(currentUser);
                        currentUser = upgrades.buyTokens(actions.get(j).getCount());
                    } else if (Objects.equals(feature, "buy premium account")) {
                        Upgrades upgrades = new Upgrades(currentUser);
                        currentUser = upgrades.buyPremiumAccount();
                    } else if (Objects.equals(feature, "purchase")) {
                        currentUser = purchaseMovie(currentPage, currentUser,
                                currentMovieList, movie, nextPage, onPage, feature, pages);
                    } else if (Objects.equals(feature, "watch")) {
                        currentUser = watchMovie(currentPage, currentUser,
                                currentMovieList, movie, nextPage, onPage, feature, pages);
                    } else if (Objects.equals(feature, "like")) {
                        currentUser = likeMovie(movies, currentPage, currentUser,
                                currentMovieList, movie, nextPage, onPage, feature, pages);
                    } else if (Objects.equals(feature, "rate")) {
                        currentUser = rateMovie(users, movies, actions, currentPage,
                                currentUser, currentMovieList, movie,
                                sumRate, j, nextPage, onPage, feature, pages);
                    } else if (Objects.equals(feature, "subscribe")) {
                        SeeDetails seeDetails = SeeDetails.getSeeDetails();
                        seeDetails.setCurrentUser(currentUser);
                        seeDetails.setCurrentMovieList(currentMovieList);
                        String subscribedGenre = actions.get(j).getSubscribedGenre();
                        if (currentPage.getPage() == PageFactory.PageType.SeeDetails) {
                            if (seeDetails.errorSubscribe(subscribedGenre, movie) == 1) {
                                currentUser = seeDetails.subscribedGenre(subscribedGenre);
                                users = seeDetails.subscribeToUser(users, subscribedGenre);
                            } else {
                                onPage.error(outputJson);
                            }
                        } else {
                            onPage.error(outputJson);
                        }
                    }

                } else {
                    onPage.error(outputJson);
                }
            } else if (val == 2) {
                typeBack(currentPage, pages, currentUser, currentMovieList, nextPage);
            } else if (val == -1) {
                String feature = actions.get(j).getFeature();
                if (Objects.equals(feature, "add")) {
                    MoviesInput moviesInput = actions.get(j).getAddedMovie();
                    DatabaseAdd databaseAdd = new DatabaseAdd(movies, users);
                    if (databaseAdd.error(moviesInput) == 1) {
                        allMovieList = databaseAdd.addMovie(moviesInput);
                        OnPage onPage = new OnPage.Builder(currentPage, actions.get(j)).
                                currentUser(currentUser).
                                currentMovieList(currentMovieList).
                                build();
                        currentMovieList = onPage.setMovieList(movies);
                    } else {
                        databaseAdd.outputError(outputJson);
                    }
                }

            }

        }

        recommendationForPremium(currentPage, currentUser, currentMovieList, pages);
    }

    /**
     * parcurgem list de filme si
     * setam rating-ul initial al fiecaruia pe 0
     * @param movies
     * @param currentUser
     */
    private static void rateForRatedMoviesForUser(final ArrayList<MoviesInput> movies,
                                                  final UsersInput currentUser) {
        ArrayList<Double> rated = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            rated.add(i, (double) 0);
        }
        currentUser.setRateForRatedMovies(rated);
    }

    /**
     *
     * @param actions
     * @param currentPage
     * @param pages
     * @param currentUser
     * @param currentMovieList
     * @param allMovieList
     * @param movie
     * @param j
     * @param nextPage
     * @return
     */
    private static String typeChangePage(final ArrayList<ActionsInput> actions,
                                         final Page currentPage,
                                         final ArrayList<Page> pages,
                                         final UsersInput currentUser,
                                         final ArrayList<MoviesInput> currentMovieList,
                                         final ArrayList<MoviesInput> allMovieList,
                                         String movie,
                                         final int j,
                                         final String nextPage) {
        ChangePage changePage;
        if (Objects.equals(nextPage, "movies")) {
            changePage = new ChangePage.Builder(currentPage, nextPage, pages).
                    currentMovieList(allMovieList).
                    currentUser(currentUser).build();
        } else if (Objects.equals(nextPage, "see details")) {
            movie = actions.get(j).getMovie();
            changePage = new ChangePage.Builder(currentPage, nextPage, pages).
                    currentMovieList(currentMovieList).
                    currentUser(currentUser).
                    movie(movie).build();
        } else {
            changePage = new ChangePage.Builder(currentPage, nextPage, pages).
                        build();
        }
        Undo undo = new Undo(outputJson);
        undo.edit(changePage);
        return movie;
    }

    /**
     *
     * @param actions
     * @param currentPage
     * @param currentUser
     * @param currentMovieList
     * @param j
     * @return
     */
    private static ArrayList<MoviesInput> getMoviesAfterSearch(
            final ArrayList<ActionsInput> actions,
            final Page currentPage,
            final UsersInput currentUser,
            ArrayList<MoviesInput> currentMovieList,
            final int j) {
        OnPage onPage;
        Search search = new Search(currentMovieList);
        String startsWith = actions.get(j).getStartsWith();
        currentMovieList = search.isMovie(startsWith);
        onPage = new OnPage.Builder(currentPage, actions.get(j)).
                currentUser(currentUser).
                currentMovieList(currentMovieList).build();
        onPage.output1(outputJson);
        return currentMovieList;
    }

    /**
     * @param actions
     * @param currentPage
     * @param currentUser
     * @param currentMovieList
     * @param allMovieList
     * @param j
     */
    private static void filterMovieList(final ArrayList<ActionsInput> actions,
                                        final Page currentPage,
                                        UsersInput currentUser,
                                        final ArrayList<MoviesInput> currentMovieList,
                                        final ArrayList<MoviesInput> allMovieList,
                                        final int j) {
        OnPage onPage;
        onPage = new OnPage.Builder(currentPage, actions.get(j)).
                currentUser(currentUser).
                currentMovieList(currentMovieList).build();
        onPage.restoreList(currentMovieList, allMovieList);
        if (actions.get(j).getFilters().getSort() != null) {
            String rating = actions.get(j).getFilters().getSort().getRating();
            String duration = actions.get(j).getFilters().getSort().getDuration();
            Sort sort = new Sort(rating, duration);
            sort.sortCurrentMovieList(currentMovieList);
        }
        if (actions.get(j).getFilters().getContains() != null) {
            onPage.restoreList(currentMovieList, allMovieList);
            ArrayList<String> actors =
                    actions.get(j).getFilters().getContains().getActors();
            ArrayList<String> genre =
                    actions.get(j).getFilters().getContains().getGenre();
            Contains contains = new Contains(actors, genre);
            if (actors != null) {
                contains.containsActorsCurrentMovieList(currentMovieList, actors);
            }
            if (genre != null) {
                contains.containsGenresCurrentMovieList(currentMovieList, genre);
            }
        }
        onPage.output1(outputJson);
    }
    private static UsersInput rateMovie(final ArrayList<UsersInput> users,
                                        final ArrayList<MoviesInput> movies,
                                        final ArrayList<ActionsInput> actions,
                                        final Page currentPage,
                                        UsersInput currentUser,
                                        final ArrayList<MoviesInput> currentMovieList,
                                        final String movie,
                                        final ArrayList<Double> sumRate,
                                        final int j, final String nextPage,
                                        final OnPage onPage, final String feature,
                                        final ArrayList<Page> pages) {
        SeeDetails seeDetails = SeeDetails.getSeeDetails();
        seeDetails.setCurrentUser(currentUser);
        seeDetails.setCurrentMovieList(currentMovieList);
        double rate = actions.get(j).getRate();
        if (seeDetails.error(feature, movie) == 1 && rate <= MAX_RATE) {
            currentUser = seeDetails.rateMovie(movie, rate, movies, sumRate, users);
            ChangePage changePage = new ChangePage.Builder(currentPage, nextPage, pages).
                    currentMovieList(currentMovieList).
                    currentUser(currentUser).
                    movie(movie).build();
            changePage.ouputMovie(outputJson);
        } else {
            onPage.error(outputJson);
        }
        return currentUser;
    }

    private static UsersInput likeMovie(final ArrayList<MoviesInput> movies,
                                        final Page currentPage,
                                        UsersInput currentUser,
                                        final ArrayList<MoviesInput> currentMovieList,
                                        final String movie,
                                        final String nextPage,
                                        final OnPage onPage,
                                        final String feature,
                                        final ArrayList<Page> pages) {
        SeeDetails seeDetails = SeeDetails.getSeeDetails();
        seeDetails.setCurrentUser(currentUser);
        seeDetails.setCurrentMovieList(currentMovieList);
        if (seeDetails.error(feature, movie) == 1) {
            currentUser = seeDetails.likeMovie(movie, movies);
            ChangePage changePage = new ChangePage.Builder(currentPage, nextPage, pages).
                    currentMovieList(currentMovieList).
                    currentUser(currentUser).
                    movie(movie).build();
            changePage.ouputMovie(outputJson);
        } else {
            onPage.error(outputJson);
        }
        return currentUser;
    }

    private static UsersInput watchMovie(final Page currentPage,
                                         UsersInput currentUser,
                                         final ArrayList<MoviesInput> currentMovieList,
                                         final String movie,
                                         final String nextPage,
                                         final OnPage onPage,
                                         final String feature,
                                         final ArrayList<Page> pages) {
        SeeDetails seeDetails = SeeDetails.getSeeDetails();
        seeDetails.setCurrentUser(currentUser);
        seeDetails.setCurrentMovieList(currentMovieList);
        if (seeDetails.error(feature, movie) == 1) {
            currentUser = seeDetails.watchMovie(movie);
            ChangePage changePage = new ChangePage.Builder(currentPage, nextPage, pages).
                    currentMovieList(currentMovieList).
                    currentUser(currentUser).
                    movie(movie).build();
            changePage.ouputMovie(outputJson);
        } else {
            onPage.error(outputJson);
        }
        return currentUser;
    }

    private static UsersInput purchaseMovie(final Page currentPage,
                                            UsersInput currentUser,
                                            final ArrayList<MoviesInput> currentMovieList,
                                            final String movie, final String nextPage,
                                            final OnPage onPage,
                                            final String feature,
                                            final ArrayList<Page> pages) {
        SeeDetails seeDetails = SeeDetails.getSeeDetails();
        seeDetails.setCurrentUser(currentUser);
        seeDetails.setCurrentMovieList(currentMovieList);
        if (seeDetails.error(feature, movie) == 1) {
            currentUser = seeDetails.purchaseMovie(movie);
            ChangePage changePage = new ChangePage.Builder(currentPage, nextPage, pages).
                    currentMovieList(currentMovieList).
                    currentUser(currentUser).
                    movie(movie).build();
            changePage.ouputMovie(outputJson);
        } else {
            onPage.error(outputJson);
        }
        return currentUser;
    }



    /**
     * daca tipul actiunii este "back"
     *
     * @param currentPage
     * @param pages
     * @param currentUser
     * @param currentMovieList
     * @param nextPage
     */
    private static void typeBack(final Page currentPage,
                                 final ArrayList<Page> pages,
                                 final UsersInput currentUser,
                                 final ArrayList<MoviesInput> currentMovieList,
                                 final String nextPage) {
        ChangePage changePage = new ChangePage.Builder(currentPage, nextPage, pages).
                currentMovieList(currentMovieList).
                currentUser(currentUser).build();
        Undo undo = new Undo(outputJson);
        if (currentUser == null) {
            changePage.error(outputJson);
        } else {
            undo.undo(changePage);
        }
    }
    /**
     * daca mai este vreun user conectat
     * si acesta este premium
     * li se va oferi o recomandare
     */
    /**
     * @param currentPage
     * @param currentUser
     * @param currentMovieList
     */
    private static void recommendationForPremium(final Page currentPage,
                                                 final UsersInput currentUser,
                                                 final ArrayList<MoviesInput> currentMovieList,
                                                 final ArrayList<Page> pages) {
        if (currentPage.getPage() != PageFactory.PageType.HomePageN
                && Objects.equals(currentUser.getCredentials().getAccountType(), "premium")) {
            if (currentUser.getLikedMovies().size() == 0) {
                Notifications notification = new Notifications(
                        "No recommendation", "Recommendation");
                ArrayList<Notifications> notifications = new ArrayList<>();
                notifyUser(notifications, notification, currentUser);

                ChangePage changePage = new ChangePage.Builder(currentPage,
                        currentPage.getPage().name(), pages).
                        currentUser(currentUser).
                        currentMovieList(currentMovieList).build();
                UsersInput user = changePage.deepCopyCurrentUser();
                outputJson.addObject().putPOJO("error", null).
                        putPOJO("currentMoviesList", null).
                        putPOJO("currentUser", user);
            } else {
                Recommendation recomandation = new Recommendation(
                        currentUser.getLikedMovies(), currentMovieList);
                MoviesInput movieR = recomandation.recommendation(currentUser);
                if (currentUser.getNotifications() != null) {
                    ArrayList<Notifications> notifications = currentUser.getNotifications();
                    notifyUser(notifications,
                            new Notifications(movieR.getName(), "Recommendation"), currentUser);
                } else {
                    ArrayList<Notifications> notifications = new ArrayList<>();
                    notifyUser(notifications,
                            new Notifications(movieR.getName(), "Recommendation"), currentUser);
                }

                ChangePage changePage = new ChangePage.
                        Builder(currentPage, currentPage.getPage().name(), pages).
                        currentUser(currentUser).
                        currentMovieList(currentMovieList).build();
                UsersInput user = changePage.deepCopyCurrentUser();
                outputJson.addObject().putPOJO("error", null).
                        putPOJO("currentMoviesList", null).
                        putPOJO("currentUser", user);
            }
        }
    }

    private static void notifyUser(final ArrayList<Notifications> notifications,
                                   final Notifications movieR,
                                   final UsersInput currentUser) {
        notifications.add(movieR);
        currentUser.setNotifications(notifications);
    }
}






