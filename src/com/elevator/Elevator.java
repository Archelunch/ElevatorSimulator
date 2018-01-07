package com.elevator;

import java.util.*;

public class Elevator extends Thread {

    private  byte floor;
    private int floortime;
    private int time;
    private byte cfloor = 1; //Текущий этаж
    private boolean isWork = true;
    private Queue<Byte> floorQueue;
    private Scanner sc;
    /**
     @param floor  - этаж от 5 до 20
     @param height - высота этажа в метрах
     @param speed - скорость в м/c
     @param time - скорость открытия/закрытия дверей в секундах
     */
    public Elevator(byte floor, double height, double speed, double time){
        if(floor < 5 || floor > 20 || height <= 0 || speed <= 0 || time <= 0) throw new IllegalArgumentException();
        this.floor = floor;
        this.floortime = (int)(height/speed * 1000);
        this.time = (int)(time*1000);
        /** Очередь для определния где лифту лучше остановиться*/
        floorQueue = new PriorityQueue<>(new Comparator<Byte>() {
            @Override
            public int compare(Byte o1, Byte o2) {
                if(Math.abs(cfloor - o1) > Math.abs(cfloor - o2)){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        sc = new Scanner(System.in);
    }

    public void queueAdd(byte floor){ //Добавление в очередь вызовов
        if(floor < 0 || floor > this.floor) throw new IllegalArgumentException();
        if(!floorQueue.contains(floor)) floorQueue.add(floor);
    }

    public void isWorked(){ //Для выключения потока
        isWork = !isWork;
    }
    @Override
    public void run() {
        try {
            while(isWork) {
                if (floorQueue.peek() != null) {
                    /** qfloor - этаж из очереди вызова*/
                    byte qfloor = floorQueue.poll();
                    /** Ехать вверху или вниз*/
                    if (cfloor > qfloor) {
                        for (int i = cfloor; i > qfloor; i--) {
                            sleep(floortime);
                            System.out.println(String.format("Проезжаем этаж %d", i));
                        }
                    } else {
                        for (int i = cfloor; i < qfloor; i++) {
                            sleep(floortime);
                            System.out.println(String.format("Проезжаем этаж %d", i));
                        }
                    }
                    cfloor = qfloor;
                    sleep(time);
                    System.out.println(String.format("Лифт открыл дверь на %d этаже", qfloor));
                    System.out.println("Кто-то заходит на этом этаже? Если да, то укажите этажи в одной строке через пробел(1 2 3), если нет, то нажмите Enter");
                    System.out.println("Сперва происходит вызов из подъезда, потом из лифта.");
                    /** Вызов из лифта.*/
                    String t = sc.nextLine();
                    if(!t.equals("")) {
                        String[] queue = t.split(" ");
                        for (String s : queue) {
                            try {
                                queueAdd(Byte.parseByte(s));
                            } catch (Exception ex) {
                                System.out.println(String.format("%s - некорректный ввод", s));
                            }
                        }
                    }
                    sleep(time);
                    System.out.println("Лифт закрыл дверь");
                } else {
                    sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
