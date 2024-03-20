package ru.alishev.springcourse.repository;

import ru.alishev.springcourse.enums.OrderStatus;
import ru.alishev.springcourse.models.Addressee;
import ru.alishev.springcourse.models.Customer;
import ru.alishev.springcourse.models.Order;


import java.sql.*;
import java.util.ArrayList;

import static ru.alishev.springcourse.services.OrderService.getStatusForOrder;


public class OrderRepository {
    private final String url = "jdbc:postgresql://localhost:5432/CurierServise";
    private final String user = "postgres";
    private final String password = "12345";


    public Connection connectToDataBase() {

        Connection connection = null;
        {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println("Ошибка при соединение56789 ! "+e.getMessage());
            }
        }
        return connection;
    }

    public void saveOrder(Order order, Customer customer, Addressee addressee){
        try {
            Statement statement = connectToDataBase().createStatement();
            statement.executeUpdate("insert into tb_customer ( fio, city, phone_num, street, housenum, flatnum, floor_n,inn) values ('" + customer.getFio() + "', '" + customer.getCity() + "', '" + customer.getNumber() + "', '" + customer.getStreet() + "','"+customer.getHouseNum()+"','"+customer.getFlatNum()+"','"+customer.getFloor()+"','"+customer.getInn()+"')");
            statement.executeUpdate("insert into tb_addressee ( fio, city, phone_num, street, housenum, flatnum, floor_n,inn) values ('" + addressee.getFio() + "', '" + addressee.getCity() + "', '" + addressee.getNumber() + "', '" + addressee.getStreet() + "','"+addressee.getHouseNum()+"','"+addressee.getFlatNum()+"','"+addressee.getFloor()+"','"+addressee.getInn()+"')");
            PreparedStatement preparedStatement =connectToDataBase().prepareStatement("select id from tb_customer where inn = '"+customer.getInn()+"'");
            ResultSet res = preparedStatement.executeQuery();
            int id = 0;
            while (res.next()) {
                id = res.getInt(1);
            }
            PreparedStatement preparedStatement1 = connectToDataBase().prepareStatement("select id from tb_addressee where inn ='"+addressee.getInn()+"'");
            ResultSet res1 = preparedStatement1.executeQuery();
            int id1 = 0;
            while (res1.next()) {
                id1 = res1.getInt(1);
            }
            statement.executeUpdate("insert into tb_order(customer_id, addressee_id, weight, size, localdate, orderstatus, price) values('"+id+"','"+id1+"','"+order.getWeight()+"','"+order.getSize()+"','"+order.getDate()+"','"+order.getStatus()+"','"+order.getPrice()+"')");
        } catch (SQLException e) {
            System.out.println("Ошибка: "+e.getMessage());
        }
    }

    int id = 0;
    public ArrayList<Order> getAllOrder(){
        try {
            PreparedStatement preparedStatement = connectToDataBase().prepareStatement("select * from tb_order ");
            ArrayList<Order> orders = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                id = resultSet.getInt("id");
                order.setCustomer(getCustomer());
                order.setAddressee(getAddressee());
                order.setWeight(resultSet.getDouble("weight"));
                order.setSize(resultSet.getDouble("size"));
                order.setDate(resultSet.getDate("localdate").toLocalDate());
                order.setStatus(OrderStatus.valueOf(resultSet.getString("orderstatus")));
                order.setPrice(resultSet.getDouble("price"));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
            return null;
        }
    }

    private Customer getCustomer(){
        try {
            PreparedStatement preparedStatement = connectToDataBase().prepareStatement("select c.fio, c.city, c.phone_num, c.street, c.housenum, c.flatnum, c.floor_n from tb_customer c  join tb_order o on c.id = o.customer_id where o.id ='"+id+"'");
            ResultSet res = preparedStatement.executeQuery();
            Customer customer = new Customer();
            while (res.next()) {
                customer.setFio(res.getString("fio"));
                customer.setCity(res.getString("city"));
                customer.setNumber(res.getString("phone_num"));
                customer.setStreet(res.getString("street"));
                customer.setHouseNum(res.getString("housenum"));
                customer.setFlatNum(res.getInt("flatnum"));
                customer.setFloor(res.getInt("floor_n"));
            }
            return customer;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

  private Addressee getAddressee(){
        try {
            PreparedStatement preparedStatement = connectToDataBase().prepareStatement("select a.fio, a.city, a.phone_num, a.street, a.housenum, a.flatnum, a.floor_n from tb_addressee a  join tb_order o on a.id = o.addressee_id where o.id ='"+id+"'");
            ResultSet res = preparedStatement.executeQuery();
            Addressee addressee = new Addressee();
            while (res.next()) {
                addressee.setFio(res.getString("fio"));
                addressee.setCity(res.getString("city"));
                addressee.setNumber(res.getString("phone_num"));
                addressee.setStreet(res.getString("street"));
                addressee.setHouseNum(res.getString("housenum"));
                addressee.setFlatNum(res.getInt("flatnum"));
                addressee.setFloor(res.getInt("floor_n"));
            }
            return addressee;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void changeOrderByStatus(int statusNum, int numId) {
        try {
            Statement statement = connectToDataBase().createStatement();
            statement.executeUpdate("update tb_order set orderstatus = '"+getStatusForOrder(statusNum)+"' where id = '"+numId+"'");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

