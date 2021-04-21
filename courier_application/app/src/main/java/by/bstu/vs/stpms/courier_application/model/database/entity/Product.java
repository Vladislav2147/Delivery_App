package by.bstu.vs.stpms.courier_application.model.database.entity;

import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "product")
public class Product extends AbstractEntity implements Serializable {
    private String name;
    private double weight;
    private double price;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
