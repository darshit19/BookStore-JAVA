import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BookStore {

    //creating user object
    User currentuser;

    //creating object array of books
    ArrayList<Book> books=new ArrayList<Book>();

    public BookStore(){
        //initialize user
        currentuser = new User();

        //initialize books array
         books.add( new Book(0,"SatyaGrah",true,15,250));
         books.add( new Book(1,"Azadi Ki Ladai",true,5,350));
         books.add(new Book(2,"Loh Purush ",true,35,500));
         books.add(new Book(3,"The Boss ",false,0,250));
         books.add(new Book(4,"Freedom Fighters",true,7,600));

    }

    int takeChoice(Scanner sc){
        int choice;
        System.out.print("Enter your choice : ");
        try{
            choice = sc.nextInt();
            return choice;
        }catch (InputMismatchException e){
            sc.nextLine();
            return -1;
        }
    }

    public void profilePart(Scanner sc){
        currentuser.showProfile();

        Design.printPurchasedBooks();

        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("%-8s %-20s %-15s", "BOOK ID", "BOOKNAME", "PRICEPOINTS");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------");
        if(currentuser.getPurchasedBooksSize()==0){
            System.out.println("--------------------------You haven't purchased any books--------------------------------");
        }else{
            for(int i=0;i< books.size();i++){
                if(currentuser.isPurchased(books.get(i).getBookId())){
                    Book book = books.get(i);
                    System.out.format("%-8s %-20s %-15s",book.getBookId(),book.getBookName(),book.getPricePoints() );
                    System.out.println();
                }
            }
        }

        Design.printLine();
        Option.backToCatalogOpt();

        int catalogChoice=takeChoice(sc);
        switch(catalogChoice){
            case 1:
                return;
            case 2:
                currentuser.setLoggedIn(false);
                return;
            default:
                System.out.println("Invalid Choice ...!!!😥");
        }
    }

    void displayBooks() {
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("%-8s %-25s %-15s %-8s %-15s", "BOOK ID", "BOOKNAME", "ISAVAILABLE", "STOCK", "PRICEPOINTS");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------");
//iterates over the list
        for(Book book: books)
        {
            String temp;
            if(book.getAvailstatus()){
                temp="In-store";
            }else{
                temp="OutOfStock";
            }
            System.out.format("%-8s %-25s %-15s %-8s %-15s",book.getBookId(),book.getBookName(),temp,book.getStock(),book.getPricePoints() );
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------");
    }

    public void DisplayCatalog(Scanner sc){
        //Display Show Profile and Purchase Optins
        Option.displayProfileOptins();

        int profileCh=takeChoice(sc);

        switch (profileCh){
            case 1:
                profilePart(sc);
               return;
            case 2:
                System.out.println("Books Catalog");
                //Displaying books
                displayBooks();

                System.out.print("Enter the BookID which you want ot purchase : ");
                int  purchaseBid;
                try{
                    purchaseBid=sc.nextInt();
                    if(currentuser.getPoints()>=books.get(purchaseBid).getPricePoints() && books.get(purchaseBid).getStock()>0){
                        currentuser.setPurchasedBooks(purchaseBid);
                        double remPoints=currentuser.getPoints() - books.get(purchaseBid).getPricePoints();
                        currentuser.setPoints(remPoints);
                        System.out.println("Book Purchased successfully");
                        books.get(purchaseBid).setStock(books.get(purchaseBid).getStock()-1);
                        profilePart(sc);
                        return;
                    }else{
                        if(books.get(purchaseBid).getStock()<=0){
                            System.out.println("This Book is Out Of stock");
                        }else{
                            System.out.println("You have not sufficient balance to buy this book");
                        }

                    }
                    break;
                }catch (InputMismatchException e){
                    System.out.println("Invalid Input...😣");
                    sc.nextLine();
                }
        }
    }

    public void getSignInInformation(Scanner sc) {
        Design.printSignIn();
        int choice;

        while (true) {
            //displaying sign in options
            Option.displaySignInOptions();

            try
            {
                choice = takeChoice(sc);
                switch (choice) {
                    case 1:
                        String uName, uPass;
                        System.out.print("Enter Username : ");
                        uName = sc.next();
                        System.out.print("Enter Password : ");
                        uPass = sc.next();
                        if (currentuser.getUsername() == null || currentuser.getPassword() == null) {
                            System.out.println("😥 Please Register first...!!!\n");
                            return;
                        }

                        //validate credentials
                        if (uName.equals(currentuser.getUsername()) && uPass.equals(currentuser.getPassword())) {
                            //credentials are valid
                            System.out.println("Signed in Successfully...😀");
                            currentuser.setLoggedIn(true);

                            while(true) {
                                Design.printAvailableBooks();
                                displayBooks();
                                //Display Catalog
                                DisplayCatalog(sc);

                                if (!currentuser.getIsLoggedIn()) {
                                    break;
                                }
                            }
                            return;
                        }

                        System.out.println("😥 Invalid credentials ! Try Again...!!!\n");
                        break;
                    case 2:
                        Design.printForgotPassword();
                        String user;
                        String pass;
                        System.out.print("Enter your username :");
                        user = sc.next();
                        if (currentuser.getUsername() != null && user.equals(currentuser.getUsername())) {
                            System.out.print("Enter new password : ");
                            pass = sc.next();
                            currentuser.setPassword(pass);
                            System.out.println("Password changed Successfully...😀");
                            return;
                        } else {
                            System.out.println("😥 User with given username doesn't exists...!!!\n");
                        }
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid Choice !!!😥");

                }
            }catch(InputMismatchException exception){
                System.out.println("Invalid Input...😣");
                sc.nextLine();
            }
        }
    }

    //For registering user
    public void registerUser(Scanner sc){
        String fullName,userName,userPass;
        System.out.print("Enter FullName : ");
        fullName = sc.nextLine();
        System.out.print("Enter Username : ");
        userName = sc.nextLine();
        System.out.print("Enter Password : ");
        userPass = sc.nextLine();

        //set all values to user object
        currentuser.setFullName(fullName);
        currentuser.setUsername(userName);
        currentuser.setPassword(userPass);

        //success message
        System.out.println("Registered in Successfully...😀 \n");
    }

    public void start(){
        Scanner sc = new Scanner(System.in);

        // for printing welcome message
        Design.printWelcomeMsg();
        Design.printLine();

        int choice;

        while(true){
            Option.displayInitialOptions();
            System.out.print("Enter your choice : ");
            try{
                choice = sc.nextInt();
                switch (choice){
                    case 1:
                        //for sign in option
                        getSignInInformation(sc);
                        break;
                    case 2:
                        //for register option

                        //1.1 will print register details
                        Design.printRegister();
                        registerUser(sc);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid Choice 😣!!!\n");
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid Input...!!!😣");
                sc.nextLine();
            }
        }
    }
}
