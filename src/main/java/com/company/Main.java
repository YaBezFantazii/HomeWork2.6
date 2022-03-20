package com.company;

import JSON.ReadJSON;
import JSON.WriteJSON;
import XML.*;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String NickName1, NickName2;
        Scanner console = new Scanner(System.in);
        String[] FieldGame;
        String CheckNew = "";
        String Player;
        Boolean CheckWin;
        int number, DeadHeat, FileNum;
        // Массив с результатами игр. Элементами массива являются объекты класса Logs.
        ArrayList<Logs> GameList = new ArrayList<>();
        // Вспомогательный объект класса GameListXML для записи результатов в xml
        com.company.GameList xml = com.company.GameList.getInstance();

        // Правила
        System.out.println("Для выбора ячейки куда хотите поставить крестик введите через консоль" +
                " соответствующее число ");
        System.out.println(" | 1 | 2 | 3 | \n" +
                           " | 4 | 5 | 6 | \n" +
                           " | 7 | 8 | 9 | ");
        System.out.println("Запись рейтинга в файл происходит после полного завершения игр через команду 'n' в консоли.\n" +
                "Так же команда 'r' в консоли приводит к архивации старого рейтинга (перезаписи его в другой файл и \n" +
                "удалению текущего рейтинга");
        System.out.println("Команда 'json' в консоли приводит к чтению списка json файлов лежащих в корневая_папка_проекта/JSON");
        System.out.println("Команда 'xml' в консоли приводит к чтению списка xml файлов лежащих в корневая_папка_проекта/XML\n");
        // Вводим имя игроков.
        do {
            System.out.println("Введите имя 1 игрока");
            NickName1 = console.nextLine();
            System.out.println("Введите имя 2 игрока");
            NickName2 = console.nextLine();
            if ((NickName1.equals(NickName2))||(NickName1=="")||NickName2==""){
                System.out.println("!Имена игроков совпадают или пусты!");
            }
        } while ((NickName1.equals(NickName2))||(NickName1=="")||NickName2=="");

        GameList.add(new Logs(NickName1,0,0,0));
        GameList.add(new Logs(NickName2,0,0,0));

        // Проверка на то, хотим ли мы начать новую игру(y), закончить (n) или архивировать и обнулить рейтинг (r)
        while (!CheckNew.equals("n")){

            System.out.println("Для новой игры введите 'y'\nДля завершения игр и записи результатов в файл введите 'n'\n" +
                    "Для архивации и удаления старого рейтинга введите 'r'.\nДля работы с xml файлами введите 'xml'." +
                    "\nДля работы с json файлами введите 'json'." +
                    "Каждая законченная игра автоматически записывается в xml и json файлы");
            CheckNew = console.nextLine();

            if (CheckNew.equals("y")) {

                xml.setNickName(NickName1,NickName2);
                // Обнуляем поле игры
                DeadHeat = 0;
                FieldGame = new String[] {"-", "-", "-", "-", "-", "-", "-", "-", "-"};
                Player = "X";
                CheckWin = false;

                // Играем
                while (!CheckWin) {

                    if (Player.equals("X")){
                        System.out.println("Ходит игрок "+NickName1+". Введите число, куда хотите поставить " + Player);
                    } else {
                        System.out.println("Ходит игрок "+NickName2+". Введите число, куда хотите поставить " + Player);
                    }

                    number = console.nextInt()-1;

                    // Проверка на корректность введенного числа и заполнености ячейки
                    if (number>8 || number<0 || FieldGame[number].equals("X") || FieldGame[number].equals("0")) {
                        System.out.println("Ячейка уже занята или введено неккоректное число (больше 9 или меньше 1)");
                    // Если все в порядке, записываем Х или 0 в поле,производим проверку на победу, меняем игрока
                    } else {
                        // Записываем Х или 0 в ячейку поля
                        FieldGame[number] = Player;
                        // Меняем текущего игрока
                        if (Player.equals("X")) {Player = "0";} else {Player = "X";}
                        console.nextLine();
                        //Печататем текущее поле в консоль
                        System.out.println(PrintField(FieldGame));
                        // Проверка на победу
                        CheckWin = Check.Check(FieldGame);
                        // Записываем шаг в массив cell объекта класса GameListXML
                        xml.setCellId(number+1);
                        // Если DeadHeat станет больше 8, то ничья, так как все ячейки будут заполненые и проверку на победу не пройдена.
                        DeadHeat++;
                    }
                    // Пишем как закончилась игра, и обновляем результаты GameList и GameListXLM (xlm)
                    if (CheckWin & Player.equals("0")) {
                        System.out.println("!!!Победил игрок "+NickName1+"!!!");
                        GameList.get(0).setWin( GameList.get(0).getWin()+1 );
                        GameList.get(1).setLose( GameList.get(1).getLose()+1 );
                        xml.setWin(0);
                    } else if (CheckWin & Player.equals("X")) {
                        System.out.println("!!!Победил игрок "+NickName2+"!!!");
                        GameList.get(0).setLose( GameList.get(0).getLose()+1 );
                        GameList.get(1).setWin( GameList.get(1).getWin()+1 );
                        xml.setWin(1);
                    } else if (DeadHeat>8) {
                        System.out.println("!!!Ничья!!!");
                        GameList.get(0).setDeadHeat( GameList.get(0).getDeadHeat()+1 );
                        GameList.get(1).setDeadHeat( GameList.get(1).getDeadHeat()+1 );
                        xml.setWin(2);
                        CheckWin = true;
                    }
                }
                // Записываем результат в xml файл
                WriteRead.Write WriteXML = new WriteXML();
                WriteXML.Write();
                // Записываем результат в json файл
                WriteRead.Write WriteJSON = new WriteJSON();
                WriteJSON.Write();
                // Очищаем массив cell
                xml.clearCell();
            // Конец игр. Записываем данные о прошедших играх в рейтинг.
            } else if (CheckNew.equals("n")) {
                // Если хотя бы 1 игра произошла, то производим запись
                if ((GameList.get(0).getWin()!=0)||(GameList.get(0).getLose()!=0)||(GameList.get(0).getDeadHeat()!=0)){
                    Logs.WriteFile(GameList);
                }
                System.out.println("Конец игры. Вся статистика записана в файл Result.txt");

            // Архивируем и удаляем старый рейтинг. Рейтинг из текущей сессии сохранятся в архивном файле
            } else if (CheckNew.equals("r")){
                if ((GameList.get(0).getWin()!=0)||(GameList.get(0).getLose()!=0)||(GameList.get(0).getDeadHeat()!=0)){
                    Logs.WriteFile(GameList);
                }
                //Архивируем рейтинг
                Logs.Archive();
                // Очищаем текущую сессию от данных
                GameList.clear();
                // Создаем игроков снова, чтобы они могли продолжать играть в этой сессии
                GameList.add(new Logs(NickName1,0,0,0));
                GameList.add(new Logs(NickName2,0,0,0));
                System.out.println("Рейтинг заархивирован и result.txt обнулен.");

            // Получения списка xml файлов
            } else if (CheckNew.equals("xml")){
                String[] files;
                File f = new File(new File("").getAbsolutePath()+"\\XML");
                // Фильтр, чтобы отображались только файлы с расширением xml
                FilenameFilter filter = new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".xml");
                    }};
                files = f.list(filter);
                System.out.println("Введите номер файла, указанный слева перед скобкой, чтобы напечатать его. Чтобы выйти из режима чтения XML " +
                        "введите любое другое число (например 0)");
                // Список xml файлов, лежащих в корневой папке проекта
                for (int i=0;i<files.length;i++){
                    System.out.println(" "+(i+1)+") "+files[i]);
                }
                // Считываем, какую игру хотим посмотреть
                FileNum = console.nextInt();
                // Если введеное число не соответсвует никакому файлу из перечисленных просто выходим в главное меню
                if ((FileNum<=files.length)&(FileNum>0)){
                    // Получаем информацию из xml файла
                    WriteRead.Read ReadXML = new ReadXML();
                    ReadXML.Read(files[FileNum-1]);
                    xml.clearCell();
                }
                console.nextLine();

            // Получения списка json файлов
            } else if (CheckNew.equals("json")){
                String[] files;
                File f = new File(new File("").getAbsolutePath()+"\\JSON");
                // Фильтр, чтобы отображались только файлы с расширением json
                FilenameFilter filter = new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".json");
                    }};
                files = f.list(filter);
                System.out.println("Введите номер файла, указанный слева перед скобкой, чтобы напечатать его. Чтобы выйти из режима чтения XML " +
                        "введите любое другое число (например 0)");
                // Список xml файлов, лежащих в корневой папке проекта
                for (int i=0;i<files.length;i++){
                    System.out.println(" "+(i+1)+") "+files[i]);
                }
                // Считываем, какую игру хотим посмотреть
                FileNum = console.nextInt();
                // Если введеное число не соответсвует никакому файлу из перечисленных просто выходим в главное меню
                if ((FileNum<=files.length)&(FileNum>0)){
                    // Получаем информацию из json файла
                    WriteRead.Read ReadJSON = new ReadJSON();
                    ReadJSON.Read(files[FileNum-1]);
                    xml.clearCell();
                }
                console.nextLine();

                // Можно вводить только "y" "n" "r" "xml" "json"
            } else {
                System.out.println("Введен неккоректный символ");
            }
        }




    }


    // Метод, возвращающий поле текущей игры в формате строки
    public static String PrintField(String[] field){
        return ( " | "+field[0]+" | "+field[1]+" | "+field[2]+" | \r\n" +
                 " | "+field[3]+" | "+field[4]+" | "+field[5]+" | \r\n" +
                 " | "+field[6]+" | "+field[7]+" | "+field[8]+" |");
    }
}
