package SupportMethod;

import com.company.GameList;
import com.company.Main;

public class Print {

    // Метод, печатающий в консоль ходы игры, полученные из xml файла
    public static void Print(){

        GameList GameList = com.company.GameList.getInstance();
        String filed[] = new String[]{"1","2","3","4","5","6","7","8","9"};
        System.out.println();
        // Так как игроки всегда ходят поочередно, 1 игрок всегда Х, 2 всегда 0,
        // то xml.getCellId[i] - массив int, где i[0,2,4..четное] - ходы 1 игрока, i = нечетное - ходы 2 игрока
        // i=1,2,3... - соответствует порядку ходов
        for (int i=0;i<GameList.getCell().size();i++){
            if ((i%2)==0) {
                System.out.println("Ход игрока 1 (X) : "+GameList.getNickName1());
                filed[GameList.getCellId(i)-1] = "X";
                System.out.println(Main.PrintField(filed));
            } else {
                System.out.println("Ход игрока 2 (O) : "+GameList.getNickName2());
                filed[GameList.getCellId(i)-1] = "O";
                System.out.println(Main.PrintField(filed));
            }
        }

        if (GameList.getWin()==0) {
            System.out.println("Player 1 -> " +GameList.getNickName1()+ " is winner as 'X'!\n");
        } else if (GameList.getWin()==1){
            System.out.println("Player 2 -> " +GameList.getNickName2()+ " is winner as 'O'!\n");
        } else {
            System.out.println("Draw!\n");
        }

    }
}
