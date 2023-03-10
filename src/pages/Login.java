package pages;

import input.UsersInput;
import users.Credentials;

import java.util.ArrayList;
import java.util.Objects;

public class Login extends Page {

    private ArrayList<UsersInput> users;

    /**
     *
     */
    public Login() {
        super.setPage(PageFactory.PageType.Login);
    }

    /**
     * @param users
     */
    public Login(final ArrayList<UsersInput> users) {
        this.users = users;
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
     * @param credentials
     * @return i - daca se gaseste un utilizator cu datele transmise ca parametru
     * -1 - altfel
     */
    public int isUser(final Credentials credentials) {

        for (int j = 0; j < users.size(); j++) {
            if (Objects.equals(users.get(j).getCredentials().getName(), credentials.getName())
                    && Objects.equals(users.get(j).getCredentials().getPassword(),
                    credentials.getPassword())) {
                return j;
            }
        }
        return -1;

    }

}
