package JSON;

import com.company.GameList;
import com.google.gson.*;

import java.lang.reflect.Type;

public class serialize implements JsonSerializer<GameList> {

    @Override
    // Переопределяем сериализацию при записи в json файл, для того, чтобы соответствовать образцу
    public JsonElement serialize(GameList GameList, Type type, JsonSerializationContext jsonContext) {
        JsonObject result = new JsonObject();
        JsonObject game = new JsonObject();
        JsonObject gameResult = new JsonObject();
        JsonArray playerList = new JsonArray();
        JsonObject gameplay = new JsonObject();

        result.add("Gameplay",gameplay);
        gameplay.add("Player",playerList);
        gameplay.add("Game",game);

        JsonObject nickName1 = new JsonObject();
        nickName1.addProperty("id","1");
        nickName1.addProperty("name", GameList.getNickName1());
        nickName1.addProperty("symbol", "X");
        JsonObject nickName2 = new JsonObject();
        nickName2.addProperty("id","2");
        nickName2.addProperty("name",GameList.getNickName2());
        nickName2.addProperty("symbol", "0");
        playerList.add(nickName1);
        playerList.add(nickName2);

        JsonArray step = new JsonArray();
        game.add("Step", step);
        int nums = 1;
        for (int i=0;i<GameList.getCell().size();i++){
            JsonObject num = new JsonObject();
            num.addProperty("num",Integer.toString(nums));
            if ((nums%2)==0){
                num.addProperty("playerId","2");
            } else {
                num.addProperty("playerId","1");
            }
            num.addProperty("text", Integer.toString(GameList.getCellId(i)));
            nums++;
            step.add(num);
        }

        JsonObject gameRes = new JsonObject();
        if (GameList.getWin()==2){
            gameplay.addProperty("GameResult","Draw!");
        } else if (GameList.getWin()==1){
            gameplay.add("GameResult",gameResult);
            gameRes.addProperty("id","2");
            gameRes.addProperty("name",GameList.getNickName2());
            gameRes.addProperty("symbol", "O");
            gameResult.add("Player",gameRes);
        } else {
            gameplay.add("GameResult",gameResult);
            gameRes.addProperty("id","1");
            gameRes.addProperty("name",GameList.getNickName1());
            gameRes.addProperty("symbol", "X");
            gameResult.add("Player",gameRes);
        }

        return result;
    }
}
