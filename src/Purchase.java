import java.util.Date;

public class Purchase {
    private Date date;
    private double total;

    public Purchase(Date date, double total) {
        this.date = date;
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
