package ru.alishev.springcourse.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.alishev.springcourse.enums.OrderStatus;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private Customer customer;
    private Addressee addressee;
    private double weight;
    private double size;
    private LocalDate date;
    private OrderStatus status;
    private double price;
    @Override
    public String toString() {
        return "Заказ:\n" +
                "id = "+id+
                "\nОтправитель : " + customer.toString() +
                "\nПолучатель : " + addressee.toString() +
                "\nВес = " + weight +
                "\nРазмер = " + size +
                "\nДата = " + date +
                "\nСтатус = " + status +
                "\nСтоимость = " + price ;
    }
}
