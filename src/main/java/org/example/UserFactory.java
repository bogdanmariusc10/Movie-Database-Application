package org.example;
public class UserFactory
{
    public static User createUser(AccountType accountType, String username, String experience, User.Information info)
    {
        User newUser;
        switch (accountType) {
            case REGULAR:
                newUser = new Regular();
                break;
            case CONTRIBUTOR:
                newUser = new Contributor();
                break;
            case ADMIN:
                newUser = new Admin();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + accountType);
        }
        return newUser;
    }
}
