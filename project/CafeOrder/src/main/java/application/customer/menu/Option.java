package application.customer.menu;

public class Option {
    private int id;
    private String name;
    private int price;
    private int stock_count;
    private int out_of_stock;
    private int option_type_id;

    public Option(int id, String name, int price, int stock_count, int out_of_stock, int option_type_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock_count = stock_count;
        this.out_of_stock = out_of_stock;
        this.option_type_id = option_type_id;
    }

    public int getId() {
        return id;
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

    public int getOption_type_id() {
        return option_type_id;
    }
}
