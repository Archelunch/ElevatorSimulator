/**
 *@author Павлухин Михаил
 * @version 1.0
 */

package com.elevator;
import java.io.IOException;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        /** Для команд*/
        String t;
        /** Поток лифта*/
        Elevator el;
        do{
            System.out.println("Введите кол-во этажей(от 5 до 20)\nрасстояние между этажами(больше 0)\nскорость лифта(больше 0)\nскорость открытия дверей(брльше 0)");
            byte floor = sc.nextByte();
            double height = sc.nextDouble(), speed = sc.nextDouble(), time = sc.nextDouble();
            try{
                el = new Elevator(floor, height, speed, time);
                Thread elThread = new Thread(el);
                elThread.start();
                System.out.println("Чтобы убрать этот лифт нажмите q(й), чтобы вызвать лифт нажмите цифру");
                sc.nextLine();
                t = sc.nextLine();
                while (!t.equals("q") && !t.equals("й")){
                    try{
                        if(!t.equals("")) el.queueAdd(Byte.parseByte(t));
                    }catch (IllegalArgumentException ex){
                        System.out.println("Неправильный ввод, попробуйте еще раз или введите q(й), чтобы все завершить.");
                    }
                    System.out.println("Ввод этажа из подъезда");
                    t = sc.nextLine();
                }

                el.isWorked();
            }catch (IllegalArgumentException ex){
                System.out.println("Неправильный ввод");
            }
            System.out.println("Чтобы выйти нажмите q(й), чтобы продолжить - любую другую клавишу.");
            t = sc.nextLine();

        } while(!t.equals("q") && !t.equals("й"));
        sc.close();
    }
}
