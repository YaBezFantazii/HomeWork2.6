package com.company;

public class WriteRead {

    // Наследуется классами WriteJSON и WriteXML, отвечающими за запись в файл
    public interface Write {
        void Write();
    }

    // Наследуется классами ReadJSON и ReadXML, отвечающими за чтение из файла
    public interface Read {
        void Read(String path);
    }
}
