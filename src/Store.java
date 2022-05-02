import java.util.*;

public class Store {
    public HashMap<String, Person> users;
    public ArrayList<Product> products;

    public Store(){
        this.users = new HashMap<>();
        this.products = new ArrayList<>();
    }

    private boolean registerEmployee(String firstName, String lastName, String userName, String password) {
        Enums.EmployeeRank choice;
        System.out.println("pick a role:\n" +
                "1 - Merchant\n" +
                "2 - Manager\n" +
                "3 - Director");
        while(true){
            try{
                choice = Enums.EmployeeRank.values()[Main.globalScanner.nextInt()-1];
                this.users.put(userName,new Employee(firstName,lastName,userName,password,choice));
                return true;
            }
            catch(Exception e){
                System.out.println("please pick from the menu");
                Main.globalScanner.nextLine();
            }
        }
    }
    private boolean registerCustomer(String firstName, String lastName, String userName, String password) {
        Enums.BooleanChoice choice;
        System.out.println("are you VIP?:\n" +
                "1 - Yes\n" +
                "2 - No");

        while(true){
            try{
                choice = Enums.BooleanChoice.values()[Main.globalScanner.nextInt()-1];
                boolean isVIP=false;
                switch(choice){
                    case YES:
                        isVIP = true;
                        break;
                    case NO:
                        isVIP = false;
                        break;
                    default:
                }
                this.users.put(userName,new Customer(firstName,lastName,userName,password,isVIP));
                return true;
            }
            catch(Exception e){
                System.out.println("please pick from the menu");
                Main.globalScanner.nextLine();
            }
        }
    }

    private boolean validateName(String name){
        if(name.matches(".*\\d.*")){
            System.out.println("name can't contain digits!");
            return false;
        }
        return true;
    }
    private boolean isUsernameExists(String username){
        if(this.users.containsKey(username)){
            return true;
        }
        return false;
    }
    private boolean validatePassword(String password){
        if(password.length()<Constants.MIN_PASSWORD_LEN){
            System.out.println("password must be at least 6 characters long");
            return false;
        }
        return true;
    }
    public boolean handleAccountCreation(){
        System.out.println("1 - new employee account\n" +
                "2 - new customer account");
        Enums.UserTypeChoise choice;
        while(true){
            try{
                choice = Enums.UserTypeChoise.values()[Main.globalScanner.nextInt()-1];
                String firstName;
                String lastName;
                String userName;
                String password;
                Main.globalScanner.nextLine();
                do{
                    System.out.println("first name: ");
                    firstName = Main.globalScanner.nextLine();
                }while(!validateName(firstName));
                do{
                    System.out.println("last name: ");
                    lastName = Main.globalScanner.nextLine();
                }while(!validateName(lastName));
                do{
                    System.out.println("username: ");
                    userName = Main.globalScanner.nextLine();
                    if(isUsernameExists(userName)){
                        System.out.println("username exists!");
                    }
                }while(isUsernameExists(userName));
                do{
                    System.out.println("password: ");
                    password = Main.globalScanner.nextLine();
                }while(!validatePassword(password));
                switch(choice){
                    case EMPLOYEE:
                        return registerEmployee(firstName,lastName,userName,password);
                    case CUSTOMER:
                        return registerCustomer(firstName,lastName,userName,password);
                    default:
                }
            }
            catch(Exception e){
                System.out.println("please pick from the menu");
                Main.globalScanner.nextLine();
            }
        }
    }

    public Person handleLogin(){
        System.out.println("1 - login as an employee\n" +
                "2 - login as a customer");
        Enums.UserTypeChoise choice;
        while(true){
            try{
                choice = Enums.UserTypeChoise.values()[Main.globalScanner.nextInt()-1];
                String userName;
                String password;
                Main.globalScanner.nextLine();
                System.out.print("username:");
                userName = Main.globalScanner.nextLine();
                do{
                    System.out.print("password:");
                    password = Main.globalScanner.nextLine();
                }while(!validatePassword(password));
                if(!isUsernameExists(userName)) {
                    return null;
                }
                switch(choice){
                    case EMPLOYEE:
                        return loginEmployee(userName,password);
                    case CUSTOMER:
                        return loginCustomer(userName,password);
                    default:
                }
            }
            catch(Exception e){
                System.out.println("please pick from the menu");
                Main.globalScanner.nextLine();
            }
        }
    }

    private Person loginCustomer(String userName, String password) {
        Person user = this.users.get(userName);
        if((user instanceof Customer)&&(Objects.equals(user.getPassword(), password))){
            return user;
        }
        return null;
    }

    private Person loginEmployee(String userName, String password) {
        Person user = this.users.get(userName);
        if((user instanceof Employee)&&(Objects.equals(user.getPassword(), password))){
            return user;
        }
        return null;
    }

    public void showUserMenu(Person user) {
        if(user instanceof Employee){
            showEmployeeMenu((Employee) user);
        }else{
            showCustomerMenu((Customer) user);
        }
    }

    public void showCustomerMenu(Person user) {
        String vipMessage = "";
        if(user instanceof Customer&&((Customer)user).isVIP()){
            vipMessage = "(VIP)";
        }
        System.out.println("Hi "+user.getFirstName()+" "+user.getLastName() +" "+vipMessage+"!");
        System.out.println("pick a product (-1 for checkout)");
        ArrayList<Product> productsList=getProductList(false);
        if(productsList==null){
            System.out.println("sorry no products");
            return;
        }
        showProductList(productsList);
        int choice = Main.globalScanner.nextInt()-1;
        while(choice>=0){
            if(choice<productsList.size()){
                System.out.println("how many would you like?");
                int ammount = Main.globalScanner.nextInt();
                if (ammount>this.products.get(choice).getQuantity()){
                    System.out.println("not enough in stock");
                }else {
                    user.addItemToCart(this.products.get(choice));
                }
                System.out.println("pick another product (-1 for checkout)");
                choice = Main.globalScanner.nextInt()-1;
            }
            else{
                System.out.println("invalid choice!\ntry again");
                choice = Main.globalScanner.nextInt()-1;
            }

        }
        user.buy();
    }


    public void showEmployeeMenu(Employee user) {
        System.out.println("Hello:"+user.getFirstName()+" "+user.getLastName()+" ("+user.getRank()+")!");
        boolean stayInEmployeeMenu = true;
        while(stayInEmployeeMenu){
            switch(getEmployeeMenuChoice()){
                case ALLCUSTOMERS:
                    printAllCustomers();
                    break;
                case VIPCUSTOMERS:
                    printVIPCustomers();
                    break;
                case PAYINGCUSTOMERS:
                    printPayingCustomers();
                    break;
                case BESTCUSTOMER:
                    printBestCustomer();
                    break;
                case ADDPRODUCT:
                    addProduct();
                    break;
                case CHANGEAVAILABILITY:
                    changeProductAvailability();
                    break;
                case SHOPSTORE:
                    showCustomerMenu(user);
                    break;
                case EXIT:
                    stayInEmployeeMenu = false;
                    break;
                default:
            }
        }

    }

    private void changeProductAvailability() {
        System.out.println("pick a product to update:");
        showProductList(getProductList(true));
        int productNum = Main.globalScanner.nextInt();
        System.out.println("how much left in stock?");
        this.products.get(productNum-1).setQuantity(Main.globalScanner.nextInt());
    }

    private void addProduct() {
        System.out.print("product name: ");
        Main.globalScanner.nextLine();
        String pName = Main.globalScanner.nextLine();
        System.out.print("product description: ");
        String pDesc = Main.globalScanner.nextLine();
        System.out.print("product price: ");
        int pPrice = Main.globalScanner.nextInt();
        System.out.print("VIP price: ");
        int pVIPPrice = Main.globalScanner.nextInt();
        this.products.add(new Product(pName,pPrice,pDesc,pVIPPrice));
    }

    private void printBestCustomer() {
        double maxAmmount=0;
        Person bestCustomer=null;
        for( Map.Entry<String, Person> entry : this.users.entrySet() ){
            if(entry.getValue().sumPurchases()>= maxAmmount){
                maxAmmount=entry.getValue().sumPurchases();
                bestCustomer = entry.getValue();
            }
        }
        System.out.println(bestCustomer);
    }

    private void printPayingCustomers() {
        this.users.forEach((username,user)->{if(user.sumPurchases()>0){
            System.out.println(user);
        }
        });
    }

    private void printVIPCustomers() {
        this.users.forEach((username,user)->{if(user instanceof Customer&&((Customer) user).isVIP()){
            System.out.println(user);
        }
        });
    }

    private void printAllCustomers() {
        this.users.forEach((k,v)->System.out.println(v.toString()));
    }

    public static Enums.EmployeeMenuOptions getEmployeeMenuChoice(){
                    System.out.println("1 - show users list\n" +
                    "2 - show vip customers\n" +
                    "3 - show paying customers\n" +
                    "4 - show best customer\n"+
                    "5 - add product\n" +
                    "6 - change product availability\n" +
                    "7 - shop products\n" +
                    "8 - logoff");
        Enums.EmployeeMenuOptions choice;
        while(true){
            try{
                choice = Enums.EmployeeMenuOptions.values()[Main.globalScanner.nextInt()-1];
                return choice;
            }
            catch(Exception e){
                System.out.println("please pick from the menu");
                Main.globalScanner.nextLine();
            }
        }
    }

    private void showProductList(ArrayList<Product> productsList) {
        if(productsList==null||productsList.size()==0){
            return;
        }
        System.out.println("products:");
        for(int i=0;i<productsList.toArray().length;i++) {
            System.out.println("#"+(i+1)+" - " + productsList.get(i));
        }
    }
    private ArrayList<Product> getProductList(boolean getOutOfStock) {
        ArrayList<Product> productList = (ArrayList<Product>) this.getProducts().clone();
        if(productList.size()==0){
            return null;
        }
        for(int i=0;i<productList.toArray().length;i++) {
            if(!getOutOfStock&&productList.get(i).getQuantity()<=0){
                productList.remove(i);
            }
        }
        return productList;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
