public  class Option {
    public static void displayInitialOptions(){
        System.out.println("1. Sign In");
        System.out.println("2. Register");
        System.out.println("3. Exit");
    }

    public static void displaySignInOptions(){
        System.out.println("1. Enter credentials");
        System.out.println("2. Forgot Password");
        System.out.println("3. Exit");
    }

    public static void displayProfileOptions(){
        System.out.println("1.Show Profile");
        System.out.println("2.Purchase");
    }

    public static  void backToCatalogOpt(){
        System.out.println("1.Back to Catalog");
        System.out.println("2.Logout");
    }

    public static void displayTypeOfUserOptions(){
        System.out.println("1. Admin");
        System.out.println("2. User");
        System.out.println("3. Exit");
    }

    public static void displayAdminOptions(){
        System.out.println("1. Add Book");
        System.out.println("2. Update Book Details");
        System.out.println("3. Display Books");
        System.out.println("4. Exit");
    }
}
