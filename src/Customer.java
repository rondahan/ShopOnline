import java.util.Date;

public class Customer extends Person{
    private boolean isVIP;
    public Customer(String firstName,
                    String lastName,
                    String username,
                    String password,
                    boolean isVIP) {
        super(firstName, lastName, username, password);
        this.isVIP = isVIP;
    }
    @Override
    public double calculateCart() {
        if(this.isVIP()){
        return super.getCart().getCartVIPSubTotal();
        }
        return super.getCart().getCartSubTotal();
    }

    @Override
    public void buy(){
        super.pay(this.calculateCart());
    }

    public boolean isVIP() {
        return this.isVIP;
    }
    @Override
    public String toString(){
        return super.toString()+"\n"+
                "isVIP: "+this.isVIP();
    }
}
