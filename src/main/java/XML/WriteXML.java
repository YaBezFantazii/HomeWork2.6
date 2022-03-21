package XML;

import com.company.GameList;
import com.company.WriteRead;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class WriteXML implements WriteRead.Write {

    public void Write() {
        GameList xml = GameList.getInstance();
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docFactory.newDocumentBuilder();
            Document doc = docBuild.newDocument();
            doc.setXmlStandalone(true);

            // Создаем элементы по образцу файла gameplay.xml
            //<Gameplay>
            Element gameplay = doc.createElement("Gameplay");
            doc.appendChild(gameplay);

            //<Player>
            for (int i=1;i<3;i++) {
                Element player = doc.createElement("Player");
                gameplay.appendChild(player);
                Attr atribut1 = doc.createAttribute("id");
                atribut1.setValue(Integer.toString(i));
                player.setAttributeNode(atribut1);
                // Когда i=1, заполняем информацию про 1 игрока, после (i=2) про 2 игрока
                if (i==1) {
                    Attr atribut2 = doc.createAttribute("name");
                    atribut2.setValue(xml.getNickName1());
                    Attr atribut3 = doc.createAttribute("symbol");
                    atribut3.setValue("X");
                    player.setAttributeNode(atribut2);
                    player.setAttributeNode(atribut3);
                } else {
                    Attr atribut2 = doc.createAttribute("name");
                    atribut2.setValue(xml.getNickName2());
                    Attr atribut3 = doc.createAttribute("symbol");
                    atribut3.setValue("O");
                    player.setAttributeNode(atribut2);
                    player.setAttributeNode(atribut3);
                }
            }

            //<Game>
            Element game = doc.createElement("Game");
            gameplay.appendChild(game);

            //<Step>
            for (int i=0;i<xml.getCell().size();i++){
                Element stepX = doc.createElement("Step");
                stepX.appendChild(doc.createTextNode(Integer.toString(xml.getCellId(i))));
                game.appendChild(stepX);
                Attr atribut2 = doc.createAttribute("num");
                atribut2.setValue(Integer.toString(i+1));
                Attr atribut3 = doc.createAttribute("playerId");
                // Если i четное, то ходит игрок 1, иначе игрок 2 (они всегда ходят по порядку)
                if ((i%2)==0){
                    atribut3.setValue("1");
                } else {
                    atribut3.setValue("2");
                }
                stepX.setAttributeNode(atribut2);
                stepX.setAttributeNode(atribut3);
            }

            //<GameResult>
            //Если ничья то пишем "Draw!"
            if (xml.getWin()==2) {
                Element GameResult = doc.createElement("GameResult");
                GameResult.appendChild(doc.createTextNode("Draw!"));
                gameplay.appendChild(GameResult);
            // Если победа одного из игроков, то пишем какого именно
            } else {
                Element GameResult = doc.createElement("GameResult");
                gameplay.appendChild(GameResult);
                Element Player = doc.createElement("Player");
                GameResult.appendChild(Player);
                Attr atribut1 = doc.createAttribute("id");
                Attr atribut2 = doc.createAttribute("name");
                Attr atribut3 = doc.createAttribute("symbol");
                // Если переменная win=0, то победа 1 игрока, иначе (win=1) победа 2 игрока
                if (xml.getWin()==0) {
                    atribut1.setValue("1");
                    atribut2.setValue(xml.getNickName1());
                    atribut3.setValue("X");
                } else {
                    atribut1.setValue("2");
                    atribut2.setValue(xml.getNickName2());
                    atribut3.setValue("O");
                }
                Player.setAttributeNode(atribut1);
                Player.setAttributeNode(atribut2);
                Player.setAttributeNode(atribut3);
            }

            // Запись в файл xml
            TransformerFactory transformerFactory =  TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // Такая кодировка была указана в образце
            transformer.setOutputProperty(OutputKeys.ENCODING, "windows-1251");
            // Нормализует формат для удобного просмотра в текстовых редакторах
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            String result = "XML\\gameplay1.xml";
            File file1 = new File(result);
            int i=1;
            // Если есть файл gameplay1.xml, по проверяем существует ли gameplay2.xml, потом gameplay3.xml
            // и так далее ( gameplay(i+1).xml ), пока не сможем создать новый файл.
            while (file1.exists()){
                result = "XML\\gameplay"+i+".xml";
                file1 = new File(result);
                i++;
            }
            StreamResult StreamResult =  new StreamResult(new File(result));
            transformer.transform(source, StreamResult);
            System.out.println("Запись данных об игре успешно завершена. Путь: "+result);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}