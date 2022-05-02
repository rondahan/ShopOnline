import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }
    public void addProduct(Product p){
        this.products.add(p);
    }
    public double getCartSubTotal(){
        double runningSum=0;
        for(int i = 0;i<products.size();i++){
            runningSum+=products.get(i).getPrice()*products.get(i).getQuantity();
        }
        return runningSum;
    }
    public double getCartVIPSubTotal(){
        double runningSum=0;
        for(int i = 0;i<products.size();i++){
            runningSum+=products.get(i).getVipDiscount()*products.get(i).getQuantity();
        }
        return runningSum;
    }
}
