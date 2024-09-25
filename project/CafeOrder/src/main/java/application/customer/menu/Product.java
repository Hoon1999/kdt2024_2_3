package application.customer.menu;

import java.util.List;

public class Product {
    private int id;
    private int categoryId;
    private String name;
    private int price;
    private int stock_count;
    private int out_of_stock;
    private String imagePath;
    private List<Option> options;

    public Product(int id, int categoryId, String name, int price, int stock_count, int out_of_stock, String image_path, List<Option> options) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.stock_count = stock_count;
        this.out_of_stock = out_of_stock;
        this.imagePath = image_path;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock_count() {
        return stock_count;
    }

    public int getOut_of_stock() {
        return out_of_stock;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<Option> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock_count=" + stock_count +
                ", out_of_stock=" + out_of_stock +
                '}';
    }
}
