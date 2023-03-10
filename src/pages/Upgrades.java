package pages;

import input.UsersInput;

public class Upgrades extends Page {

    private UsersInput currentUser;

    private static final int TOKENS_USED = 10;

    /**
     *
     */
    public Upgrades() {
        super.setPage(PageFactory.PageType.Upgrades);
    }

    /**
     * @param currentUser
     */
    public Upgrades(final UsersInput currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @param count
     * @return
     */
    public UsersInput buyTokens(final int count) {
        currentUser.setTokensCount(count);
        int balance = Integer.valueOf(currentUser.getCredentials().getBalance()) - count;
        currentUser.getCredentials().setBalance(String.valueOf(balance));

        return currentUser;
    }

    /**
     * @return
     */
    public UsersInput buyPremiumAccount() {
        int tokens = currentUser.getTokensCount();
        currentUser.setTokensCount(tokens - TOKENS_USED);
        currentUser.getCredentials().setAccountType("premium");
        return currentUser;
    }

}
