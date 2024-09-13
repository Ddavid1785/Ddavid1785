package Inventory;

import java.util.Scanner;

public class ProductTester {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int maxsize = getNumProducts(in);
        int menuChoice;
        if(maxsize==0){
            System.out.println("No products required!");
        }else{
            Product[] products = new Product[maxsize];
            addToInventory(products,in,maxsize);
            do{
                menuChoice = getMenuOption(in);
                executeMenuChoice(menuChoice,products,in);
            }while(menuChoice!=0);
        }

    }

    public static void displayInventory(Product[] products) {
        for (Product product : products) {
            System.out.println(product);
            System.out.println();
        }

    }

    public static void addToInventory(Product[] products, Scanner in,int maxsize) {
        int wish = maxsize;
        int count = 0;
        // Product[] products = new Product[maxsize];
        if (wish == 0) {
            System.out.println("No products required!");
        } else {
            while (wish > 0) {
                int tempNumber = 0;
                String tempName = "";
                int tempQty = 0;
                double tempPrice = 0.0;
                System.out.println("Enter the name:");
                tempName = in.nextLine();
                System.out.println("Enter the number:");
                tempNumber = in.nextInt();
                in.nextLine();
                System.out.println("Enter the quantity:");
                tempQty = in.nextInt();
                in.nextLine();
                System.out.println("Enter the price:");
                tempPrice = in.nextDouble();
                in.nextLine();
                wish--;
                Product wishedObject = new Product(tempName, tempPrice, tempQty, tempNumber, true);
                products[count] = wishedObject;
                count++;
            }
        }
        //in.close();
    }

    public static int getNumProducts(Scanner in) {
        int wish = -1;
        do {
            System.out.println("How many items do you wish to add?");
            System.out.println("Enter 0 if you do not wish to add products");
            try {
                wish = in.nextInt();
                in.nextLine();
                if (wish < 0) {
                    System.out.println("Number of items must be non-negative.");
                }
            } catch (Exception e) {
                System.out.println("Wrong data type entered! Exception: " + e);
                System.out.println("Try again");
                in.nextLine();
                wish = -1;
            }
        } while (wish < 0);
        return wish;
    }

    public static int getMenuOption(Scanner in) {
        int menuOption;
        do {
            System.out.println("1. View Inventory");
            System.out.println("2. Add Stock");
            System.out.println("3. Deduct Stock");
            System.out.println("4. Discontinue Product");
            System.out.println("0. Exit");
            try {
                menuOption = in.nextInt();
                in.nextLine();
                if (menuOption < 0) {
                    System.out.println("Menu option must not be negative");
                }
            } catch (Exception e) {
                System.out.println("Wrong data type entered! Exception: " + e);
                System.out.println("Try again");
                in.nextLine();
                menuOption = -1;
            }
        } while (menuOption < 0);
        return menuOption;
    }

    public static int getProductNumber(Scanner in, Product[] products) {
        int productChoice = -1;
        do {
            for(int i=0;i<products.length;i++){
                System.out.println(i+1+"."+" "+products[i].getName());
            }
            try {
                System.out.println("Which product do you wish to change?");
                productChoice = in.nextInt()-1;
                in.nextLine();
                if (productChoice < 0) {
                    System.out.println("You must choose a product that is avaliable");
                }
            } catch (Exception e) {
                System.out.println("Wrong data type entered! Exception: " + e);
                System.out.println("Try again");
                in.nextLine();
                productChoice = -1;
            }
        }
        while (productChoice < 0) ;
        return productChoice;
    }

    public static void addInventory(Scanner in, Product[] products){
        int updateValue = -1;
        int productChoice = getProductNumber(in,products);
        do {
            System.out.println("How many products do you want to add?");
            try {
                updateValue = in.nextInt();
                in.nextLine();
                if (updateValue < 0) {
                    System.out.println("You must choose atleast 0 or more.");
                }
            } catch (Exception e) {
                System.out.println("Wrong data type entered! Exception: " + e);
                System.out.println("Try again");
                in.nextLine();
                updateValue = -1;
            }
        }
        while (updateValue < 0) ;

        products[productChoice].stock=products[productChoice].addToInventory(updateValue);
        System.out.println("Stock update succseful!");
    }

    public static void deductInventory(Scanner in, Product[] products){
        int updateValue = -1;
        int productChoice = getProductNumber(in,products);
        do {
            System.out.println("How many products do you want to remove?");
            try {
                updateValue = in.nextInt();
                in.nextLine();
                if (updateValue < 0) {
                    System.out.println("You must choose atleast 0 or more.");
                }
                if (updateValue>products[productChoice].getStock()) {
                    System.out.println("You cannot remove more stock then there is.");
                }
            } catch (Exception e) {
                System.out.println("Wrong data type entered! Exception: " + e);
                System.out.println("Try again");
                in.nextLine();
                updateValue = -1;
            }
        }
        while (updateValue < 0 || updateValue>products[productChoice].getStock()) ;

        products[productChoice].stock=products[productChoice].deductFromInventory(updateValue);
        System.out.println("Stock update succseful!");
    }

    public static void discontinueInventory(Scanner in,Product[] products){
        int productChoice = getProductNumber(in,products);
        products[productChoice].setActive(false);
        System.out.println("Product sucesfully discontinued!");
    }

    public static void executeMenuChoice(int menuChoice,Product[] products,Scanner in){
        switch (menuChoice){


            case 1:
                System.out.println("You chose:View Product list");
                System.out.println();
                displayInventory(products);
                break;

            case 2:
                System.out.println("You chose:Add Stock");
                System.out.println();
                addInventory(in,products);
                break;

            case 3:
                System.out.println("You chose:Deduct Stock");
                System.out.println();
                deductInventory(in,products);
                break;

            case 4:
                System.out.println("You chose:Discontinue Stock");
                System.out.println();
                discontinueInventory(in,products);
                break;

        }

    }
}



