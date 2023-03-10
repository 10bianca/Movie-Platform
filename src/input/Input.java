package input;

import java.util.ArrayList;

public class Input {

    /**
     * create:
     * array users
     * array movies
     * array actions
     */
    private ArrayList<UsersInput> users;
    private ArrayList<MoviesInput> movies;
    private ArrayList<ActionsInput> actions;

    /**
     * @param users
     * @param movies
     * @param actions
     */
    public Input(final ArrayList<UsersInput> users,
                 final ArrayList<MoviesInput> movies,
                 final ArrayList<ActionsInput> actions) {
        this.users = users;
        this.movies = movies;
        this.actions = actions;
    }

    /**
     *
     */
    public Input() {

    }

    /**
     * @return
     */
    public ArrayList<UsersInput> getUsers() {
        return users;
    }

    /**
     * @param users
     */
    public void setUsers(final ArrayList<UsersInput> users) {
        this.users = users;
    }

    /**
     * @return
     */
    public ArrayList<MoviesInput> getMovies() {
        return movies;
    }

    /**
     * @param movies
     */
    public void setMovies(final ArrayList<MoviesInput> movies) {
        this.movies = movies;
    }

    /**
     * @return
     */
    public ArrayList<ActionsInput> getActions() {
        return actions;
    }

    /**
     * @param actions
     */
    public void setActions(final ArrayList<ActionsInput> actions) {
        this.actions = actions;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Input{" + "users=" + users + ", movies=" + movies + ", actions=" + actions + '}';
    }
}
