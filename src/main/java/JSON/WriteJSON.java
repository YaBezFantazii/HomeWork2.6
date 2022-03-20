package JSON;

import com.company.WriteRead;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WriteJSON implements WriteRead.Write {

    public void Write () {
        try {
            com.company.GameList GameList = com.company.GameList.getInstance();
            // Запись происходит при помощи библиотеки gson
            // Делаем запись удобной для чтения человека (.setPrettyPrinting()) и
            // переопредеялем сериализацию для соответствования образцу (.registerTypeAdapter)
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(GameList.getClass(), new serialize(){
                    })
                    .create();
            String json = gson.toJson(GameList);

            // Записываем данные в папку корневая_папка_проека/GSON
            String result = "JSON\\gameplay1.json";
            File file1 = new File(result);
            int i=1;
            // Если есть файл gameplay1.gson, по проверяем существует ли gameplay2.gson, потом gameplay3.gson
            // и так далее ( gameplay(i+1).gson ), пока не сможем создать новый файл.
            while (file1.exists()){
                result = "JSON\\gameplay"+i+".json";
                file1 = new File(result);
                i++;
            }
            BufferedWriter resultWrite = new BufferedWriter(new FileWriter(result, StandardCharsets.UTF_8));
            resultWrite.write(json);
            resultWrite.flush();
            resultWrite.close();
            System.out.println("Запись данных об игре успешно завершена. Путь: "+result+"\n");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
