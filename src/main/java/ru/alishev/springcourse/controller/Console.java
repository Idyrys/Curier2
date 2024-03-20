package ru.alishev.springcourse.controller;

import ru.alishev.springcourse.services.OrderService;

import java.util.Scanner;

public class Console {
    public void startApp(){
        Scanner scanner = new Scanner(System.in);
        OrderService orderService = new OrderService();
        while (true){
            System.out.println("""
                    Выберите действия :
                    1-создание заказа
                    2-изменение статуса заказа
                    3-отображение всех заказов
                    4-отображение заказов с определенным статусом
                    """);
            int answer = scanner.nextInt();
            switch (answer){
                case 1:orderService.createOrder();
                break;
                case 2: orderService.changeOrderStatus();
                    break;
                case 3:
                     orderService.getAllOrder().forEach(System.out::println);
                    break;
                case 4: orderService.getOrdersByStatus().forEach(System.out::println);
                    break;
                default: System.out.println("Выведите номер действия от 1-4 ");
            }
        }
    }
}
