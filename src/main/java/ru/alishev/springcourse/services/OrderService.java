package ru.alishev.springcourse.services;

import ru.alishev.springcourse.enums.OrderStatus;
import ru.alishev.springcourse.models.Addressee;
import ru.alishev.springcourse.models.Customer;
import ru.alishev.springcourse.models.Order;
import ru.alishev.springcourse.repository.OrderRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderService {
    OrderRepository orderRepository = new OrderRepository();
    Scanner scanner = new Scanner(System.in);

    public void createOrder()  {
        Order order = new Order();
        Addressee addressee = new Addressee();
        Customer customer = new Addressee();
        System.out.println("Введите ваш ФИО:");
        customer.setFio(scanner.nextLine());
        System.out.println("Введите ваш inn:");
        customer.setInn(scanner.nextLine());
        System.out.println("Введите ваш номер телефона формате 996XXXXXXXXX:");
        customer.setNumber(scanner.nextLine());
        System.out.println("Введите название вашей улицы:");
        customer.setStreet(scanner.nextLine());
        System.out.println("Введите номер вашего дома:");
        customer.setHouseNum(scanner.nextLine());
        System.out.println("Введите этаж вашего дома:");
        customer.setFloor(Integer.parseInt(scanner.nextLine()));
        System.out.println("Введите номер вашей квартиры:");
        customer.setFlatNum(Integer.parseInt(scanner.nextLine()));
        System.out.println("Введите название вашего города:");
        customer.setCity(scanner.nextLine());
        System.out.println("Введите ФИО получателя:");
        addressee.setFio(scanner.nextLine());
        System.out.println("Введите inn получателя:");
        addressee.setInn(scanner.nextLine());
        System.out.println("Введите номер телефона получателя в формате 996XXXXXXXXX:");
        addressee.setNumber(scanner.nextLine());
        System.out.println("Введите название улицы получателя:");
        addressee.setStreet(scanner.nextLine());
        System.out.println("Введите номер дома получателя:");
        addressee.setHouseNum(scanner.nextLine());
        System.out.println("Введите этаж дома получателя:");
        addressee.setFloor(Integer.parseInt(scanner.nextLine()));
        System.out.println("Введите номер квартиры получателя:");
        addressee.setFlatNum(Integer.parseInt(scanner.nextLine()));
        System.out.println("Введите название города получателя:");
        addressee.setCity(scanner.nextLine());
        System.out.println("Введите размер посылки в кубических метрах:");
        order.setSize(Double.parseDouble(scanner.nextLine()));
        System.out.println("Введите вес посылки на килограммах:");
        order.setWeight(Double.parseDouble(scanner.nextLine()));
        System.out.print("""
                Введите номер статус заказа:\s
                1-Новый
                2-В обработке
                3-В процессе
                4-Завершен
                5-Отменен
                """);
        int statusNum = Integer.parseInt(scanner.nextLine());
        order.setStatus(getStatusForOrder(statusNum));
        System.out.println("Введите желаемое время доставки в формате дд-ММ-гггг");
        order.setDate(LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        order.setPrice(getPrice(order.getWeight(), order.getSize()));
        order.setAddressee(addressee);
        order.setCustomer(customer);
        orderRepository.saveOrder(order,customer,addressee);
    }

    private double getPrice(double weight, double size) {
        double weightPrice = weight * 100;
        double sizePrice = size * 1000;
        return Math.max(weightPrice, sizePrice);

    }
    public static OrderStatus getStatusForOrder(int statusNum) {
        switch (statusNum) {
            case 1:
                return OrderStatus.New;
            case 2:
                return OrderStatus.IN_PROCESSING;
            case 3:
                return OrderStatus.IN_PROGRESS;
            case 4:
                return OrderStatus.COMPLETED;
            case 5:
                return OrderStatus.CANCELED;
            default:
                System.out.println("Выведите номер действия от 1-5 ");
        }
        return null;
    }
    public ArrayList<Order> getAllOrder() {
        return orderRepository.getAllOrder();
    }
    public void  changeOrderStatus(){
        System.out.println("Введите id заказа:");
        int numId = Integer.parseInt(scanner.nextLine());
        System.out.print("""
                Введите номер статус заказа:\s
                1-Новый
                2-В обработке
                3-В процессе
                4-Завершен
                5-Отменен
                """);
        int statusNum = Integer.parseInt(scanner.nextLine());
        orderRepository.changeOrderByStatus(statusNum,numId);
    }

    public List<Order> getOrdersByStatus(){
        System.out.print("""
                Введите номер статус заказа:\s
                1-Новый
                2-В обработке
                3-В процессе
                4-Завершен
                5-Отменен
                """);
        int statusNum = Integer.parseInt(scanner.nextLine());
        ArrayList<Order> orders = getAllOrder();
        return orders.stream().filter(order -> order.getStatus().equals(getStatusForOrder(statusNum))).collect(Collectors.toList());
    }
}
