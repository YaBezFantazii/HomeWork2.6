package JSON;

import SupportMethod.FormatStep;
import SupportMethod.Print;
import com.company.GameList;
import com.company.WriteRead;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReadJSON implements WriteRead.Read {

    // Метод, отвечающий за вывод данных из json файла
    public void Read(String json) {
        try {
            GameList GameList = com.company.GameList.getInstance();
            File file = new File("JSON\\"+json);

            JsonElement rider = JsonParser.parseReader(new FileReader(file));
            JsonObject gameplay = rider.getAsJsonObject();
            JsonObject player = gameplay.get("Gameplay").getAsJsonObject();
            JsonArray nick = player.get("Player").getAsJsonArray();

            // Объекты их списка Player, которые содержат имена игроков и их порядок
            JsonObject nick1 = nick.get(0).getAsJsonObject();
            JsonObject nick2 = nick.get(1).getAsJsonObject();

            // Проверяем на то, как записан объект в файле (name или _name), и записываем имена игроков в GameList
            if (nick1.has("name")) {
                GameList.setNickName(nick1.get("name").getAsString(), nick2.get("name").getAsString());
            } else {
                GameList.setNickName(nick1.get("_name").getAsString(), nick2.get("_name").getAsString());
            }

            // Проходим массив step из файла, записываем ходы игроков в массив cell в GameList,
            // и так же делаем проверку на то, как записан ход (__text или text)
            JsonObject game = player.get("Game").getAsJsonObject();
            JsonArray steps = game.get("Step").getAsJsonArray();
            for (int i = 0; i < steps.size(); i++) {
                JsonObject step = steps.get(i).getAsJsonObject();
                // Так же проверяем формат записи ячеек поля игры, если 1,2,3,4... и т.д., то длина getTextContent().length() будет равна 1
                // если нет, то запись произведена по координатам (к примеру 1,1, 1,2 ...) и т.д., и тогда вызывем метод для преобразования
                // этого формата в 1,2,3,4... (метод FormatStep)
                if (step.has("__text")) {
                    if (step.get("__text").getAsString().length()==1){
                        GameList.setCellId(step.get("__text").getAsInt());
                    } else {
                        GameList.setCellId(FormatStep.FormatStep(step.get("__text").getAsString()));
                    }
                } else {
                    if (step.get("text").getAsString().length()==1){
                        GameList.setCellId(step.get("text").getAsInt());
                    } else {
                        GameList.setCellId(FormatStep.FormatStep(step.get("text").getAsString()));
                    }
                }
            }

            // Проверяем объект GameResult, если он не содержит объекты, то ничья (draw или null там записано,
            // значения не имеет)
            if (!player.get("GameResult").isJsonObject()) {
                GameList.setWin(2);
            } else {
                // Если победил 1 игрок, то в GameList записываем win=0, если игрок 2 - win=1
                JsonObject gameresult = player.get("GameResult").getAsJsonObject();
                JsonObject win = gameresult.get("Player").getAsJsonObject();
                if (win.has("_id")) {
                    // -1, потому что в файле записан 1 и 2 игрок, а в win победа учитывается при значениях 0 и 1
                    GameList.setWin(win.get("_id").getAsInt() - 1);
                } else {
                    GameList.setWin(win.get("id").getAsInt() - 1);
                }
            }

            Print.Print();

        }
     catch(FileNotFoundException e)
    {
        System.out.println(e);
    }
}

}
