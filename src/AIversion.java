import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * AI-assisted updated version of the Warehouse Inventory System.
 *
 * Improvements:
 * 1. Uses private fields and getter/setter methods.
 * 2. Uses ArrayList instead of a fixed-size array.
 * 3. Supports CRUD operations for products.
 * 4. Validates user input.
 * 5. Prevents duplicate product IDs.
 * 6. Uses BigDecimal for more accurate price calculations.
 * 7. Uses a menu-driven interface.
 */
public class AIversion {

    // Represents a product stored in the warehouse.
    static class Product {
        private int productID;
        private String name;
        private BigDecimal price;

        public Product(int productID, String name, BigDecimal price) {
            setProductID(productID);
            setName(name);
            setPrice(price);
        }

        public int getProductID() {
            return productID;
        }

        public void setProductID(int productID) {
            if (productID <= 0) {
                throw new IllegalArgumentException(
                        "Product ID must be greater than zero.");
            }
            this.productID = productID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Product name cannot be empty.");
            }
            this.name = name.trim();
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(
                        "Price cannot be negative.");
            }
            this.price = price.setScale(2, RoundingMode.HALF_UP);
        }

        public void display() {
            System.out.println("------------------------------");
            System.out.println("Product ID   : " + productID);
            System.out.println("Product Name : " + name);
            System.out.println("Price        : $" + price);
        }
    }

    // Represents a supplier.
    static class Supplier {
        private int supplierID;
        private String supplierName;

        public Supplier(int supplierID, String supplierName) {
            setSupplierID(supplierID);
            setSupplierName(supplierName);
        }

        public int getSupplierID() {
            return supplierID;
        }

        public void setSupplierID(int supplierID) {
            if (supplierID <= 0) {
                throw new IllegalArgumentException(
                        "Supplier ID must be greater than zero.");
            }
            this.supplierID = supplierID;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            if (supplierName == null || supplierName.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Supplier name cannot be empty.");
            }
            this.supplierName = supplierName.trim();
        }

        public void display() {
            System.out.println("------------------------------");
            System.out.println("Supplier ID   : " + supplierID);
            System.out.println("Supplier Name : " + supplierName);
        }
    }

    // Manages warehouse products using a dynamic ArrayList.
    static class Warehouse {
        private final List<Product> products = new ArrayList<>();

        public boolean addProduct(Product product) {
            if (findProductByID(product.getProductID()) != null) {
                return false;
            }

            products.add(product);
            return true;
        }

        public Product findProductByID(int productID) {
            for (Product product : products) {
                if (product.getProductID() == productID) {
                    return product;
                }
            }
            return null;
        }

        public boolean updateProduct(
                int productID,
                String newName,
                BigDecimal newPrice) {

            Product product = findProductByID(productID);

            if (product == null) {
                return false;
            }

            product.setName(newName);
            product.setPrice(newPrice);
            return true;
        }

        public boolean deleteProduct(int productID) {
            Product product = findProductByID(productID);

            if (product == null) {
                return false;
            }

            products.remove(product);
            return true;
        }

        public void displayInventory() {
            if (products.isEmpty()) {
                System.out.println("No products are currently available.");
                return;
            }

            System.out.println("\n===== Warehouse Inventory =====");

            for (Product product : products) {
                product.display();
            }

            System.out.println("------------------------------");
            System.out.println("Total products: " + products.size());
        }

        public BigDecimal calculateTotalInventoryValue() {
            BigDecimal total = BigDecimal.ZERO;

            for (Product product : products) {
                total = total.add(product.getPrice());
            }

            return total.setScale(2, RoundingMode.HALF_UP);
        }
    }

    private static final Scanner INPUT = new Scanner(System.in);
    private static final Warehouse WAREHOUSE = new Warehouse();
    private static Supplier supplier;

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("====================================");
        System.out.println(" AI-Assisted Warehouse Inventory");
        System.out.println("====================================");

        while (running) {
            displayMenu();
            int choice = readPositiveInteger("Enter your choice: ");

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    WAREHOUSE.displayInventory();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    manageSupplier();
                    break;
                case 7:
                    displayInventoryValue();
                    break;
                case 8:
                    running = false;
                    System.out.println(
                            "Program closed successfully.");
                    break;
                default:
                    System.out.println(
                            "Invalid choice. Please select 1 to 8.");
            }
        }

        INPUT.close();
    }

    private static void displayMenu() {
        System.out.println("\n========== Main Menu ==========");
        System.out.println("1. Add Product");
        System.out.println("2. Display All Products");
        System.out.println("3. Search Product");
        System.out.println("4. Update Product");
        System.out.println("5. Delete Product");
        System.out.println("6. Add or View Supplier");
        System.out.println("7. Display Total Inventory Value");
        System.out.println("8. Exit");
    }

    private static void addProduct() {
        System.out.println("\n--- Add Product ---");

        int id = readPositiveInteger("Enter Product ID: ");

        if (WAREHOUSE.findProductByID(id) != null) {
            System.out.println(
                    "A product with this ID already exists.");
            return;
        }

        String name = readNonEmptyText("Enter Product Name: ");
        BigDecimal price = readNonNegativePrice("Enter Price: $");

        try {
            Product product = new Product(id, name, price);
            WAREHOUSE.addProduct(product);
            System.out.println("Product added successfully.");
        } catch (IllegalArgumentException exception) {
            System.out.println(
                    "Unable to add product: " + exception.getMessage());
        }
    }

    private static void searchProduct() {
        System.out.println("\n--- Search Product ---");

        int id = readPositiveInteger("Enter Product ID to search: ");
        Product product = WAREHOUSE.findProductByID(id);

        if (product == null) {
            System.out.println("Product not found.");
        } else {
            System.out.println("Product found:");
            product.display();
        }
    }

    private static void updateProduct() {
        System.out.println("\n--- Update Product ---");

        int id = readPositiveInteger("Enter Product ID to update: ");
        Product product = WAREHOUSE.findProductByID(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        String newName = readNonEmptyText("Enter New Product Name: ");
        BigDecimal newPrice =
                readNonNegativePrice("Enter New Price: $");

        try {
            WAREHOUSE.updateProduct(id, newName, newPrice);
            System.out.println("Product updated successfully.");
        } catch (IllegalArgumentException exception) {
            System.out.println(
                    "Unable to update product: "
                            + exception.getMessage());
        }
    }

    private static void deleteProduct() {
        System.out.println("\n--- Delete Product ---");

        int id = readPositiveInteger("Enter Product ID to delete: ");

        if (WAREHOUSE.deleteProduct(id)) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void manageSupplier() {
        System.out.println("\n--- Supplier Management ---");

        if (supplier != null) {
            System.out.println("Current supplier details:");
            supplier.display();

            String answer =
                    readNonEmptyText("Replace supplier? (yes/no): ");

            if (!answer.equalsIgnoreCase("yes")) {
                return;
            }
        }

        int supplierID =
                readPositiveInteger("Enter Supplier ID: ");
        String supplierName =
                readNonEmptyText("Enter Supplier Name: ");

        try {
            supplier = new Supplier(supplierID, supplierName);
            System.out.println("Supplier saved successfully.");
        } catch (IllegalArgumentException exception) {
            System.out.println(
                    "Unable to save supplier: "
                            + exception.getMessage());
        }
    }

    private static void displayInventoryValue() {
        BigDecimal total =
                WAREHOUSE.calculateTotalInventoryValue();

        System.out.println(
                "Total inventory value: $" + total);
    }

    private static int readPositiveInteger(String message) {
        while (true) {
            System.out.print(message);
            String value = INPUT.nextLine().trim();

            try {
                int number = Integer.parseInt(value);

                if (number > 0) {
                    return number;
                }

                System.out.println(
                        "Please enter a number greater than zero.");
            } catch (NumberFormatException exception) {
                System.out.println(
                        "Invalid input. Please enter a whole number.");
            }
        }
    }

    private static BigDecimal readNonNegativePrice(String message) {
        while (true) {
            System.out.print(message);
            String value = INPUT.nextLine().trim();

            try {
                BigDecimal price = new BigDecimal(value);

                if (price.compareTo(BigDecimal.ZERO) >= 0) {
                    return price.setScale(
                            2, RoundingMode.HALF_UP);
                }

                System.out.println(
                        "Price cannot be negative.");
            } catch (NumberFormatException exception) {
                System.out.println(
                        "Invalid price. Enter a value such as 250.50.");
            }
        }
    }

    private static String readNonEmptyText(String message) {
        while (true) {
            System.out.print(message);
            String text = INPUT.nextLine().trim();

            if (!text.isEmpty()) {
                return text;
            }

            System.out.println("This field cannot be empty.");
        }
    }
}
