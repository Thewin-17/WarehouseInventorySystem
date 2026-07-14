//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

class Product {
    int productID;
    String name;
    double price;

    public Product(int productID, String name, double price) {
        this.productID = productID;
        this.name = name;
        this.price = price;
    }

    public void display() {
        System.out.println("Product ID: " + productID);
        System.out.println("Name: " + name);
        System.out.println("Price: $" + price);
    }
}

class Supplier {
    int supplierID;
    String supplierName;

    public Supplier(int supplierID, String supplierName) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
    }

    public void display() {
        System.out.println("Supplier ID: " + supplierID);
        System.out.println("Supplier Name: " + supplierName);
    }
}

class Warehouse {
    Product[] products;
    int count = 0;

    public Warehouse(int number) {
        products = new Product[number];
    }

    public void addProduct(Product product) {
        if (count < products.length) {
            products[count] = product;
            count++;
        } else {
            System.out.println("Warehouse is full!");
        }
    }

    public void displayInventory() {
        for (int i = 0; i < count; i++) {
            products[i].display();
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Warehouse myWarehouse = new Warehouse(2);

        for (int i = 1; i <= 2; i++) {
            System.out.println("Enter details of Product " + i);
            System.out.print("Enter Product ID: ");
            int id = input.nextInt();
            input.nextLine();

            System.out.print("Enter Product Name: ");
            String name = input.nextLine();

            System.out.print("Enter Price: ");
            double price = input.nextDouble();

            Product product = new Product(id, name, price);
            myWarehouse.addProduct(product);
        }

        System.out.println("\n--- Warehouse Inventory ---");
        myWarehouse.displayInventory();

        System.out.println("--- Enter Supplier Details ---");
        System.out.print("Enter Supplier ID: ");
        int sID = input.nextInt();
        input.nextLine();

        System.out.print("Enter Supplier Name: ");
        String sName = input.nextLine();

        Supplier supplier = new Supplier(sID, sName);

        System.out.println("\n--- Supplier Details ---");
        supplier.display();
    }
}