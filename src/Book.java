public class Book {
    private int bookId;
    private String bookName;
    private boolean isAvail;
    private int stock;
    private double pricePoints;

    public void setBookId(int bookId){this.bookId=bookId;}
    public void setBookName(String bookName){this.bookName=bookName;}
    public void setAvail(boolean status){this.isAvail=status;}
    public void setStock(int stock){this.stock=stock;}
    public void setPricePoints(double pricePoints){this.pricePoints=pricePoints;}

    public  int getBookId(){return this.bookId;}
    public String getBookName(){return this.bookName;}
    public boolean getAvailstatus(){return this.isAvail;}
    public int getStock(){return this.stock;}
    public double getPricePoints(){return this.pricePoints;}

    //constructor of book
    public Book(int bookId, String bookName, int stock, double pricePoints){
        setBookId(bookId);
        setBookName(bookName);
        setStock(stock);
        if(stock==0){
            setAvail(false);
        }else{
            setAvail(true);
        }
        setPricePoints(pricePoints);
    }
}
