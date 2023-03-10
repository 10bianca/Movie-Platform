package pages;

import input.UsersInput;
import users.Credentials;

import java.util.ArrayList;

public class Register extends Page {

    private ArrayList<UsersInput> users;

    /**
     *
     */
    public Register() {
        super.setPage(PageFactory.PageType.Register);
    }

    /**
     * @param users
     */
    public Register(final ArrayList<UsersInput> users) {
        this.users = users;
    }

    /**
     * @param credentials
     * @return 0 - exista un utilizator cu aceleasi date
     * ca cele transmise ca parametru
     * 1 - altfel
     */
    public int isUser(final Credentials credentials) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getCredentials() == credentials) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * @param credentials
     */
    public void addUser(final Credentials credentials) {
        UsersInput user = new UsersInput(credentials);
        users.add(user);
    }


}
