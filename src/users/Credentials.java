package users;

public class Credentials {

    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;

    /**
     * @param name
     * @param password
     * @param accountType
     * @param country
     * @param balance
     */
    public Credentials(final String name, final String password,
                       final String accountType, final String country,
                       final String balance) {
        this.name = name;
        this.password = password;
        this.accountType = accountType;
        this.country = country;
        this.balance = balance;
    }

    /**
     *
     */
    private Credentials() {

    }


    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * @param accountType
     */
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    /**
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * @return
     */
    public String getBalance() {
        return balance;
    }

    /**
     * @param balance
     */
    public void setBalance(final String balance) {
        this.balance = balance;
    }

}
