package ru.alishev.springcourse.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String fio;
    private String city;
    private String number;
    private String street;
    private String houseNum;
    private int flatNum;
    private int floor;
    private  String inn;

    @Override
    public String toString() {
        return  "ФИО:" + fio +
                "\tГород:" + city  +
                "\tНомер телефона:" + number +
                "\tУлица:" + street +
                "\tНомер дома:" + houseNum +
                "\tНомер квартиры:" + flatNum +
                "\tЭтаж:" + floor ;
    }
}
