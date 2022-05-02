import java.util.Date;

import static java.lang.String.valueOf;

public class Employee extends Person{
    private Enums.EmployeeRank rank;
    public Employee(String firstName,
                    String lastName,
                    String username,
                    String password,
                    Enums.EmployeeRank e) {
        super(firstName, lastName, username, password);
        this.rank=e;
    }

    @Override
    public double calculateCart() {
        final double MERCHANT_PRICE = 0.9;
        final double MANAGER_PRICE = 0.8;
        final double DIRECTOR_PRICE = 0.7;
        switch(this.rank){
            case MERCHANT:
                return super.getCart().getCartSubTotal()*MERCHANT_PRICE;
            case MANAGER:
                return super.getCart().getCartSubTotal()*MANAGER_PRICE;
            case DIRECTOR:
                return super.getCart().getCartSubTotal()*DIRECTOR_PRICE;
            default:
                return super.getCart().getCartSubTotal();
        }
    }

    @Override
    public void buy(){
        super.pay(calculateCart());
    }

    public String getRank() {
        return this.rank.toString();
    }

    @Override
    public String toString(){
        return super.toString()+"\n"+
                "rank: "+this.getRank();
    }
}
