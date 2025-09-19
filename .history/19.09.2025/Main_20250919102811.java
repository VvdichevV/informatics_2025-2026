// Victor Dichev 12/4 Test A 19.09.2025
import java.util.HashMap;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Product> products = new HashMap<>();
        File file = new File("19.09.2025/product.txt");

        try{
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        while ((line = read.readLine()) != null) {
            if (line != null) {
                String[] data = line.split(",");
                    Product product = new Product(data[0], data[1], Double.parseDouble(data[2]),
                            Integer.parseInt(data[3]));
                    products.put(data[0], product);
            }
        }
        read.close();
    } catch (IOException e) {
        System.out.println(e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
    } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println(e.getMessage());
    }
        Supermarket supermarket = new Supermarket(products);
        supermarket.displayAllProducts();
    }
}

class Product {
    private String productID;
    private String name;
    private double price;
    private int stock;

    public Product(String productID, String name, double price, int stock) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void decreaseStock(int quantity){
        if(quantity <= stock){
            stock -= quantity;
        } 
    }

    public void displayDetails(){
        System.out.println("Product ID: " + productID);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
        System.out.println("Stock: " + stock);
    }
}

class Item {
    private Product product;
    private int quantity;

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double calculateTotal() {
        return product.getPrice() * quantity;
    }

    public void displayItem() {
        System.out.println("Item: " + product.getName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Total Price: " + calculateTotal());
    }
}

class Supermarket {
    HashMap<String, Product> products;

    public Supermarket(HashMap<String, Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.put(product.getProductID(), product);
    }

    public Product findProduct(String productID) {
        return products.get(productID);
    }

    public void displayAllProducts() {
        for (Product product : products.values()) {
            product.displayDetails();
            System.out.println("\n********************");
        }
    }

}