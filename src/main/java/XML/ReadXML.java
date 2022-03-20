package XML;

import SupportMethod.FormatStep;
import SupportMethod.Print;
import com.company.WriteRead;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ReadXML implements WriteRead.Read {

    public void Read(String FileName){
        try {
            com.company.GameList GameList = com.company.GameList.getInstance();
            // Получение фабрики, чтобы после получить билдер документов.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам нужно.
            Document game = builder.parse(new File("XML\\"+FileName));
            // Получение списка всех элементов employee внутри корневого элемента (getDocumentElement возвращает ROOT элемент XML файла).
            NodeList testList = game.getDocumentElement().getElementsByTagName("Player");

            // Перебор всех элементов
            Node Player1 = testList.item(0);
            Node Player2 = testList.item(1);
            // Получение атрибутов каждого элемента
            NamedNodeMap attributes1 = Player1.getAttributes();
            NamedNodeMap attributes2 = Player2.getAttributes();
            // Записываем имена 1 и 2 игрока
            GameList.setNickName(
                    attributes1.getNamedItem("name").getNodeValue(),
                    attributes2.getNamedItem("name").getNodeValue()
            );

            NodeList testList3 = game.getDocumentElement().getElementsByTagName("Step");
            Node step = testList3.item(0);

            // Проходим все элементы <step>, и записываем их по порядку в массив cell класса GameListXML.
            // Так же проверяем формат записи ячеек поля игры, если 1,2,3,4... и т.д., то длина getTextContent().length() будет равна 1
            // если нет, то запись произведена по координатам (к примеру 1,1, 1,2 ...) и т.д., и тогда вызывем метод для преобразования
            // этого формата в 1,2,3,4...
            if (step.getTextContent().length()==1){
                for (int i = 0; i < testList3.getLength(); i++) {
                    step = testList3.item(i);
                    GameList.setCellId(Integer.parseInt(step.getTextContent()));
                }
            } else {
                for (int i = 0; i < testList3.getLength(); i++) {
                    step = testList3.item(i);
                    // GameListXML.FormatStep - метод для преобразования данных
                    //System.out.println(FormatStep.FormatStep(step.getTextContent()));
                    GameList.setCellId(FormatStep.FormatStep(step.getTextContent()));
                }
            }

            // если у <Player> 3 элемента (последний лежит в <GameResult>), значит это не ничья
            if (testList.getLength()==3){
                Node Result = testList.item(2);
                NamedNodeMap attributes3 = Result.getAttributes();
                // Если id 3 элемента <Player> = 1, значит победил игрок 1, иначе игрок 2
                if (Integer.parseInt(attributes3.getNamedItem("id").getNodeValue())==1) {
                    GameList.setWin(0);
                } else {
                    GameList.setWin(1);
                }
            // ничья
            } else {
                GameList.setWin(2);
            }

            // Передаем в метод Print массив GameList с полученными данными (метод производит печать
            // данных в консоль).
            Print.Print();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
