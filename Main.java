import java.util.Scanner;

class User {

    String FullName;
    String IdCard;
    String LoginId;
    String Password;
    int Balance;

    public User(String FullName, String IdCard, String LoginId, String Password, int Balance) {
        this.FullName = FullName;
        this.IdCard = IdCard;
        this.LoginId = LoginId;
        this.Password = Password;
        this.Balance = Balance;
    }
}

class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        User[] users = Register();
        if (users != null) {
            Login(users);
        }
        sc.close();
    }

    public static User[] Register() {
        System.out.print("How many users do you want to create: ");
        int userAmount = sc.nextInt();
        sc.nextLine();

        if (userAmount < 2) {
            System.out.println("Can't create less than 2 accounts");
            return null;
        }

        User[] newUsers = new User[userAmount];

        for (int i = 0; i < userAmount; i++) {
            System.out.println("________ Create account " + (i + 1) + " ________");

            System.out.print("Enter your name: ");
            String FullName = sc.nextLine();

            System.out.print("Enter your card ID: ");
            String IdCard = sc.nextLine();

            System.out.print("Account name: ");
            String LoginId = sc.nextLine();

            System.out.print("Password: ");
            String Password = sc.nextLine();

            System.out.print("Balance: ");
            int Balance = sc.nextInt();
            sc.nextLine();

            newUsers[i] = new User(FullName, IdCard, LoginId, Password, Balance);
        }

        return newUsers;
    }

    public static void Login(User[] users) {
        System.out.println("________ Login ________");

        while (true) {
            System.out.print("Enter your account name: ");
            String LoginId = sc.nextLine();
            System.out.print("Enter your password: ");
            String Password = sc.nextLine();

            User user = findOneUser(users, LoginId);

            if (user != null && user.Password.equals(Password)) {
                System.out.println("Login Successfully");
                menu(user, users);
                break;
            } else {
                System.out.println("ID or Password Invalid");
            }
        }
    }

    public static User findOneUser(User[] users, String LoginId) {
        for (User user : users) {
            if (user.LoginId.equals(LoginId)) {
                return user;
            }
        }
        return null;
    }

    public static void check(User user) {
        System.out.println("Account balance: " + user.Balance);
    }

    public static void withdraw(User user) {
        System.out.println("Account balance: " + user.Balance);
        System.out.print("Enter amount to withdraw: ");
        int withdrawAmount = sc.nextInt();

        if (withdrawAmount > user.Balance) {
            System.out.println("Insufficient funds.");
        } else {
            user.Balance -= withdrawAmount;
            System.out.println("Withdrawal successful. New balance: " + user.Balance);
        }
    }

    public static void deposit(User user) {
        System.out.println("Account balance: " + user.Balance);
        System.out.print("Enter amount to deposit: ");
        int depositAmount = sc.nextInt();
        user.Balance += depositAmount;
        System.out.println("Deposit successful. New balance: " + user.Balance);
    }

    public static void transfer(User[] users, User user) {
        System.out.println("Account balance: " + user.Balance);
        System.out.print("Enter the target account ID: ");
        sc.nextLine();
        String targetLoginId = sc.nextLine();
        System.out.print("Enter amount to transfer: ");
        int transferAmount = sc.nextInt();

        User targetUser = findOneUser(users, targetLoginId);

        if (targetUser == null) {
            System.out.println("Target account does not exist.");
        } else if (transferAmount > user.Balance) {
            System.out.println("Insufficient funds.");
        } else {
            user.Balance -= transferAmount;
            targetUser.Balance += transferAmount;
            System.out.println("Transfer successful. Your new balance: " + user.Balance);
            System.out.println("Target account new balance: " + targetUser.Balance);
        }
    }

    public static void menu(User user, User[] users) {
        while (true) {
            System.out.println("1. Check Balance\n2. Withdraw\n3. Deposit\n4. Transfer\n5. Logout");
            System.out.print("Choose an option: ");
            int menuList = sc.nextInt();

            switch (menuList) {
                case 1:
                    check(user);
                    break;
                case 2:
                    withdraw(user);
                    break;
                case 3:
                    deposit(user);
                    break;
                case 4:
                    transfer(users, user);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    askForAnotherLogin(users);
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public static void askForAnotherLogin(User[] users) {
        while (true) {
            System.out.print("Do you want to login to another account? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();

            if (choice.equals("yes")) {
                Login(users);
                break;
            } else if (choice.equals("no")) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }
}
