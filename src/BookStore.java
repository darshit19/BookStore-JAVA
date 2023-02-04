import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.Console;


public class BookStore {
    //creating user object
    private User currentuser ;
    private final Admin admin;
    //creating object array of books
    private ArrayList<Book> books;
    private ArrayList<User> users;

    public BookStore(){
        //initialize user
        currentuser = new User();
        books = new ArrayList<>();
        users = new ArrayList<>();
        //initialize books array
         books.add( new Book(0,"SatyaGrah",15,250));
         books.add( new Book(1,"Azadi Ki Ladai",5,350));
         books.add(new Book(2,"Loh Purush ",35,500));
         books.add(new Book(3,"The Boss ",0,250));
         books.add(new Book(4,"Freedom Fighters",7,600));
         admin = new Admin();
    }

    public static String toString(char[] a) {
        String string = new String(a);
        return string;
    }
    private static String readPassword() {
        Console console;
        if ((console = System.console()) != null) {
            char[] password = console.readPassword();
            return toString(password);
        }
        return null;
    }
    public int takeChoice(BufferedReader br){
        int choice;
        System.out.print("Enter your choice : ");
        try{
            choice = Integer.parseInt(br.readLine());
            return choice;
        }catch (Exception e){
            return -1;
        }
    }

    public void profilePart(BufferedReader br){
        currentuser.showProfile();

        Design.printPurchasedBooks();

        Design.printDashLine();
        System.out.printf("%-8s %-20s %-15s", "BOOK ID", "BOOKNAME", "PRICEPOINTS");
        System.out.println();
        Design.printDashLine();
        if(currentuser.getPurchasedBooksSize()==0){
            System.out.println("--------------------------You haven't purchased any books--------------------------------");
        }else{
            for(int i=currentuser.getPurchasedBooks().size()-1;i>=0;i--){
                int id = currentuser.getPurchasedBooks().get(i);
                Book tBook = books.get(id);
                System.out.format("%-8s %-20s %-15s", tBook.getBookId(), tBook.getBookName(), tBook.getPricePoints());
                System.out.println();
            }
        }

        Design.printLine();
        Option.backToCatalogOpt();

        int catalogChoice=takeChoice(br);
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

    public void DisplayCatalog(BufferedReader br){
        //Display Show Profile and Purchase Optins
        Option.displayProfileOptins();

        int profileCh=takeChoice(br);

        switch (profileCh){
            case 1:
                profilePart(br);
               return;
            case 2:
                System.out.println("Books Catalog");
                //Displaying books
                displayBooks();

                System.out.print("Enter the BookID which you want ot purchase : ");
                int  purchaseBid;
                try{
                    purchaseBid=Integer.parseInt(br.readLine());
                    if(currentuser.getPoints()>=books.get(purchaseBid).getPricePoints() && books.get(purchaseBid).getStock()>0){
                        currentuser.purchaseBook(purchaseBid);
                        double remPoints=currentuser.getPoints() - books.get(purchaseBid).getPricePoints();
                        currentuser.setPoints(remPoints);
                        System.out.println("Book Purchased successfully");
                        books.get(purchaseBid).setStock(books.get(purchaseBid).getStock()-1);
                        profilePart(br);
                        return;
                    }else{
                        if(books.get(purchaseBid).getStock()<=0){
                            System.out.println("This Book is Out Of stock");
                        }else{
                            System.out.println("You have not sufficient balance to buy this book");
                        }

                    }
                    break;
                }catch (Exception e){
                    System.out.println("Invalid Input...😣");
                }
            default:
                System.out.println("Invalid choice...😣");
                break;
        }
    }

    public void getSignInInformation(BufferedReader br) {
        Design.printSignIn();
        int choice;

        while (true) {
            //displaying sign in options
            Option.displaySignInOptions();

            try
            {
                choice = takeChoice(br);
                switch (choice) {
                    case 1:
                        String uName, uPass;
                        System.out.print("Enter Username : ");
                        uName = br.readLine();
                        System.out.print("Enter Password : ");
                        uPass = readPassword();
                        if (users.size()==0) {
                            System.out.println("😥 Please Register first...!!!\n");
                            return;
                        }

                        //validate credentials
                        System.out.println(users.size());
                        for(User tempUser:users){

                            if (uName.equals(tempUser.getUsername()) && uPass.equals(tempUser.getPassword())) {
                                //credentials are valid
                                System.out.println("Signed in Successfully...😀");

                                // then set the user to current user as he is the active user
                                currentuser=tempUser;
                                currentuser.setLoggedIn(true);

                                while(true) {
                                    Design.printAvailableBooks();
                                    displayBooks();
                                    //Display Catalog
                                    DisplayCatalog(br);

                                    if (!currentuser.getIsLoggedIn()) {
                                        currentuser=null;
                                        break;
                                    }
                                }
                                return;
                            }
                        }
                        System.out.println("😥 Invalid credentials ! Try Again...!!!\n");
                        break;

                    case 2:
                        Design.printForgotPassword();
                        String uname;
                        String pass;
                        System.out.print("Enter your username :");
                        uname = br.readLine();

                        for(User tempUser:users){
                            if (tempUser.getUsername().equals(uname)) {
                                System.out.print("Enter new password : ");
                                pass = br.readLine();
                                tempUser.setPassword(pass);
                                System.out.println("Password changed Successfully...😀");
                                return;
                            }
                        }
                        System.out.println("😥 User with given username doesn't exists...!!!\n");
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid Choice !!!😥");

                }
            }catch(Exception e){
                System.out.println("Invalid Input...😣");
            }
        }
    }

    //For registering user
    public void registerUser(BufferedReader br) throws IOException {
        String fullName,userName,userPass;

        while(true){

            System.out.print("Enter FullName : ");
            fullName = br.readLine();
            System.out.print("Enter Username : ");
            userName = br.readLine();
            System.out.print("Enter Password : ");
            userPass = readPassword();

            boolean flag=true;
            //set all values to user object
            for(User tempmUser:users){
                if(tempmUser.getUsername().equals(userName)){
                    System.out.println("------------------------------------------Sorry , This username is already taken by someone.Choose another one.------------------------------------------");
                    flag=false;
                    break;
                }
            }
            if(flag){
                User newUser=new User(fullName,userName,userPass);
                users.add(newUser);
                System.out.println("Registered in Successfully...😀");
                break;
            }
        }


        //success message

    }

    public void addBook(BufferedReader br){
        String bName;
        int bStock;
        double bPrice;
        try{
            while(true){
                System.out.print("Enter BookName : ");
                bName = br.readLine();
                System.out.print("Enter Book Stock : ");
                bStock = Integer.parseInt(br.readLine());
                System.out.print("Enter PricePoints : ");
                bPrice = Double.parseDouble(br.readLine());

                if(bStock<0 && bPrice<50.0){
                    System.out.println("Book Stock and Book Price can not be Negative ...!!!🥴");
                    continue;
                }
                if(bStock < 0){
                    System.out.println("Book Stock can not be Negative ...!!!🥴");
                    continue;
                }
                if(bPrice < 50.0){
                    System.out.println("Book Price can not be less than 50...!!!😌");
                    continue;
                }
                books.add(new Book(books.size(),bName,bStock,bPrice));
                System.out.println("New Book Added Successfully...✔");
                break;
            }
        }catch (Exception e){
            System.out.println("Invalid Input...!!!😥");
        }
    }

    public void displayBooks() {
        Design.printDashLine();
        System.out.printf("%-8s %-25s %-15s %-8s %-15s", "BOOK ID", "BOOKNAME", "ISAVAILABLE", "STOCK", "PRICEPOINTS");
        System.out.println();
        Design.printDashLine();
//iterates over the list
        for(Book book: books)
        {
            String temp;
            if(book.getStock()>0){
                temp="In-store";
            }else{
                temp="OutOfStock";
            }
            System.out.format("%-8s %-25s %-15s %-8s %-15s",book.getBookId(),book.getBookName(),temp,book.getStock(),book.getPricePoints() );
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------");
    }

    public void updateBook(BufferedReader br){
        displayBooks();
        int bID;
        try{
            while(true){
                System.out.print("Enter BookID to update its details : ");
                bID = Integer.parseInt(br.readLine());
                boolean isFound = false;

                for(Book book : books){
                    if(book.getBookId()==bID){
                        int choice;
                        while(true){
                            System.out.println("1. Update Book Stock");
                            System.out.println("2. Update Book Price");
                            System.out.println("3. Exit");
                            choice = takeChoice(br);
                            switch (choice){
                                case 1:
                                    System.out.print("Enter new Book Stock : ");
                                    int bStock=Integer.parseInt(br.readLine());
                                    if(bStock < 0){
                                        System.out.println("Book Stock can not be Negative ...!!!🥴");
                                        break;
                                    }
                                    book.setStock(bStock);
                                    System.out.println("--------------------------------Book Stock is updated successfully--------------------------------");
                                    break;
                                case 2:
                                    System.out.print("Enter new Book Price : ");
                                    double bPrice=Double.parseDouble(br.readLine());
                                    if(bPrice < 50.0){
                                        System.out.println("Book Price can not be less than 50...!!!😌");
                                        break;
                                    }
                                    book.setPricePoints(bPrice);
                                    System.out.println("--------------------------------Book Price is updated successfully--------------------------------");
                                    break;
                                case 3:
                                    isFound=true;
                                    break;
                                default:
                                    System.out.println("Invalid Choice...!!!😥");
                            }
                            if(isFound){
                                break;
                            }
                        }
                    }
                    if(isFound){
                        break;
                    }
                }
                if(!isFound){
                    System.out.println("Oops !!! Book with given ID Doesn't Exists");
                    continue;
                }
                break;
            }
        }catch (Exception e){
            System.out.println("Invalid Input...!!!😥");
        }
    }

    public void handleUser(BufferedReader br){
        int choice;
        while(true){
            Option.displayInitialOptions();
            System.out.print("Enter your choice : ");
            try{
                choice = Integer.parseInt(br.readLine());
                switch (choice){
                    case 1:
                        //for sign in option
                        getSignInInformation(br);
                        break;
                    case 2:
                        //for register option
                        //1.1 will print register details
                        Design.printRegister();
                        registerUser(br);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid Choice 😣!!!\n");
                }
            }catch (Exception e){
                System.out.println("Invalid Input...!!!😣");
            }
        }
    }

    public void handleAdmin(BufferedReader br) throws IOException {
        Design.printAdminWelcomeMsg();
        String aName;
        String aPass;

        while(true){
            System.out.print("Enter username : ");
            aName = br.readLine();
            System.out.print("Enter password : ");
            aPass = readPassword();

            if(aName.equals(admin.getUsername()) && aPass.equals(admin.getPassword())){
                System.out.println("Signed in Successfully...😀");
                int choice;

                while(true){
                    Option.displayAdminOptions();
                    choice = takeChoice(br);
                    switch(choice){
                        case 1:
                            addBook(br);
                            break;
                        case 2:
                            updateBook(br);
                            break;
                        case 3:
                            displayBooks();
                            break;
                        case 4:
                            return;
                        default:
                            System.out.println("Invalid Option...!!!");
                            break;
                    }
                }
            }else{
                System.out.println("Oops!! Invalid Credentials ...😥");
            }
        }
    }
    public void start(){
       
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        // for printing welcome message
        Design.printWelcomeMsg();
        Design.printLine();

        int choice;

        while(true){
            Option.displayTypeOfUserOptions();
            System.out.print("Enter your choice : ");
            try{
                choice = Integer.parseInt(br.readLine());
                switch (choice){
                    case 1:
                        handleAdmin(br);
                        break;
                    case 2:
                        handleUser(br);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid Choice...!!!");
                }
            }catch (Exception e ){
                System.out.println("Invalid Input...!!!😖");
            }
        }
    }
}
