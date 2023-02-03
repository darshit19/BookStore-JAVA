import java.util.ArrayList;

public class User {
    private String fullName;
    private String username;
    private String password;
    private double points;

    private ArrayList<Integer> purchasedBooks;
    private boolean isLoggedIn;

    public User(){
        this.points=2000.0;
        this.isLoggedIn=false;
        purchasedBooks= new ArrayList<Integer>();
    }

    public User(String fName,String uName,String password){
        this.fullName=fName;
        this.username=uName;
        this.password=password;
        this.points=2000.0;
        this.isLoggedIn=false;
        purchasedBooks= new ArrayList<Integer>();
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setPoints(double points) {
        this.points = points;
    }
    public void setLoggedIn(boolean stat){this.isLoggedIn=stat;}

    public void setPurchasedBooks(ArrayList<Integer> myBooks){
        purchasedBooks=myBooks;
    }
    public void purchaseBook(int bid){
        purchasedBooks.add(bid);
    }
    public double getPoints() {
        return points;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPurchasedBooksSize(){
        return purchasedBooks.size();
    }

    public ArrayList<Integer> getPurchasedBooks(){
        return purchasedBooks;
    }
    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    boolean isPurchased(int bid){
        if(purchasedBooks.contains(bid)){
            return true;
        }
        return false;
    }
    public void showProfile(){
        Design.printLine();
        System.out.println("::: My Profile :::");
        System.out.println("Username : "+this.username);
        System.out.println("FullName : "+this.fullName);
        System.out.println("Credit Points : "+this.points);
        System.out.println("My Books : :");
    }
}
