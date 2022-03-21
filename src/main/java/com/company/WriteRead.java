package com.company;

import JSON.ReadJSON;
import XML.ReadXML;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Scanner;

public class WriteRead {

    // Наследуется классами WriteJSON и WriteXML, отвечающими за запись в файл
    public interface Write {
        void Write();
    }

    // Наследуется классами ReadJSON и ReadXML, отвечающими за чтение из файла
    public interface Read {

        void Read(String path);

        // Статический метод, для вывода списка файлов (xml или json), и выбора, какой файл мы хотим прочитать
        static void FormatFile (String format){
            com.company.GameList Gamelist = com.company.GameList.getInstance();
            Scanner console = new Scanner(System.in);
            String[] files;
            int FileNumber;
            File f = new File(new File("").getAbsolutePath()+"\\"+format);
            // Фильтр, чтобы отображались только файлы с расширением xml/json
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith("."+format);
                }};
            files = f.list(filter);
            System.out.println("Введите номер файла, указанный слева перед скобкой, чтобы напечатать его. Чтобы выйти из режима чтения XML " +
                    "введите любое другое число (например 0)");
            // Список файлов, лежащих в корневой_папке_проекта/(json или xml)
            for (int i=0;i<files.length;i++){
                System.out.println(" "+(i+1)+") "+files[i]);
            }
            // Считываем, какую игру хотим посмотреть
            FileNumber = console.nextInt();
            // Если введеное число не соответсвует никакому файлу из перечисленных просто выходим в главное меню
            if ((FileNumber<=files.length)&(FileNumber>0)){
                // Получаем информацию из xml/json файла
                if (format.equals("xml")){
                    WriteRead.Read Read = new ReadXML();
                    Read.Read(files[FileNumber-1]);
                    Gamelist.clearCell();
                } else {
                    WriteRead.Read Read = new ReadJSON();
                    Read.Read(files[FileNumber-1]);
                    Gamelist.clearCell();
                }
            }
            console.nextLine();
        }
    }
}
