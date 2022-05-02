

import java.util.Scanner;

public class Main {
    public static Scanner globalScanner = new Scanner(System.in);

    public static Enums.MainPageOptions getMainMenueChoice(){
        System.out.println("1 - create new account\n" +
                "2 - login existing account\n" +
                "3 - exit");
        Enums.MainPageOptions choice;
        while(true){
        try{
            choice = Enums.MainPageOptions.values()[globalScanner.nextInt()-1];
            return choice;
        }
        catch(Exception e){
            System.out.println("please pick from the menu");
            globalScanner.nextLine();
        }
        }
    }

    public static void main(String[] args) {
        Store store = new Store();
        store.products.add(0,new Product("Coca-Cola",10,"Taste of life",8));
        store.products.add(1,new Product("XL",10,"Energy Drink",7));
        store.products.add(2,new Product("Red-Bull",8,"Energy Drink",5));
        store.products.add(3,new Product("Water",5,"water from holy land",4));
        store.products.add(4,new Product("Pepsi",10,"like cola",7));
        store.products.add(5,new Product("XL-Ten",10,"low calories",7));

        boolean running = true;
        Enums.MainPageOptions choice;
        while(running){
            switch(getMainMenueChoice()){
                case CREATE_ACCOUNT:
                    if(store.handleAccountCreation()) {
                        System.out.println("Account Created!");
                    }
                    break;
                case LOGIN:
                    Person user = store.handleLogin();
                    if(user!=null){
                    store.showUserMenu(user);
                    }else{
                        System.out.println("Username or password are incorrect, or wrong user type selected!");
                    }
                    break;
                case EXIT:
                    running = false;
                    break;
                default:
        }
    }
    }
}
