import java.util.ArrayList;
import java.util.Date;

public abstract class Person {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private ArrayList<Purchase> purchases;
    private Cart cart;

    public Person(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.purchases = new ArrayList<>();
        this.cart = new Cart();
    }

    public void addPurchase(Purchase p){
        this.purchases.add(p);
    }
    public abstract double calculateCart();
    public abstract void buy();

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
    return this.lastName;
    }

    @Override
    public String toString(){
        String purchese="";
        if(this.purchases.size()>0){
            purchese = this.purchases.get(this.purchases.size()-1).getDate().toString();
        }

    return "full name: "+this.firstName+" "+this.lastName+"\n"+
                "num of purchases: "+this.purchases.size()+"\n"+
                "total spent: "+sumPurchases()+"\n"+
                "last purchase date: "+purchese;
    }

    public double sumPurchases() {
        double sum=0;
        for (Purchase purchase:this.purchases) {
            sum+=purchase.getTotal();
        }
        return sum;
    }
    public Cart getCart(){
        return this.cart;
    }
    public void pay(double sum){
        System.out.println("total:"+sum);
        if(sum==0){
            System.out.println("empty cart");
        }else{
            this.addPurchase(new Purchase(new Date(System.currentTimeMillis()),sum));
            this.emptyCart();
            System.out.println("purchase complete");
        }
    }
    public void emptyCart(){
        this.cart = new Cart();
    }
    public void addItemToCart(Product p){
        this.cart.addProduct(p);
    }
}
