package com.company;

import java.util.ArrayList;

// Вспомогательный класс для работы с файлами (считывание и запись)
public class GameList {
    private String NickName1; // Имя игрока 1
    private String NickName2; // Имя игрока 2
    private ArrayList<Integer> cell = new ArrayList<>(); // Ячейка, куда сделан ход. Номера элемента массива - порядок ходов
    private int win; // Кто победил: 0 - игрок 1, 1 - игрок 2, 2 - ничья

    // Паттерн проектирования "одиночка"
    private GameList() {}
    private static class XMLHolder {
        public static final GameList HOLDER_INSTANCE = new GameList();
    }
    public static GameList getInstance() {
        return XMLHolder.HOLDER_INSTANCE;
    }

    // геттеры и сеттеры
    public String getNickName1() {
        return NickName1;
    }

    public String getNickName2() {
        return NickName2;
    }

    public void setNickName(String nickName1, String nickName2) {
        NickName1 = nickName1;
        NickName2 = nickName2;
    }

    public ArrayList<Integer> getCell() {
        return cell;
    }

    public int getCellId (int i){
        return cell.get(i);
    }

    public void setCellId (int number) {
        cell.add(number);
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public void clearCell (){
        cell.clear();
    }
}
