public class Main {
    public static void main(String[] args) {
        
    }
}

class Product{
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
    public String getName(){
        return name;
    }
    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public decreaseStock(int quantity){
        if(quantity <= stock){
            stock -= quantity;
        } 
    }
    public displayDetails(){
        System.out.println("Product ID: " + productID);
        System.out.println("Name: " + name);
        System.out.println("Price: ", price);
        System.out.println("Stock: " + stock);
    }
}

class Item{
    private Product product;



}