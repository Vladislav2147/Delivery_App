package by.bstu.vs.stpms.courier_application.model.database.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Collection;

import lombok.Data;

@Data
@Entity(tableName = "product")
public class Product {
    @PrimaryKey
    private long id;
    private String name;
    private double weight;
    private double price;
}
