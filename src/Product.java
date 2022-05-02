public class Product implements Comparable<Product>{
    private String name;
    private int Price;
    private String description;
    private int vipDiscount;
    private int quantity;

    public Product(String name, int price, String description, int vipDiscount) {
        this.name = name;
        Price = price;
        this.description = description;
        this.vipDiscount = vipDiscount;
        this.quantity=1;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.Price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        Price = price;
    }

    @Override
    public int compareTo(Product o) {
        return this.getName().compareTo(o.getName());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getVipDiscount() {
        return vipDiscount;
    }

    public void setVipDiscount(int vipDiscount) {
        this.vipDiscount = vipDiscount;
    }
    public String toString(){
        return this.getName() + " | price:"+this.getPrice()+" | (stock): " +this.getQuantity();
    }
}
