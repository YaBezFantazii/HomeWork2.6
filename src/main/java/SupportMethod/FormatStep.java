package SupportMethod;

public class FormatStep {

    // Метод для преобразования формата записи координат ячеек, куда сделан ход.
    // Преобразует (1 1, 1 2 ... 3 3) в (1,2,3,4...9) , разделитель между координатами значения не имеет
    public static int FormatStep (String step) {
        char[] array = step.toCharArray();
        // Посимвольно проходим строку, 1 попавшаяся цифра - координата по вертикали, 2 - координата по горизонтали.
        for (int i = 0; i < array.length; i++) {
            if ((array[i] > '0') & (array[i] <= '9')) {
                if (array[0]=='0') {
                    array[0] = array[i];
                } else {
                    array[1] = array[i];
                }
            }
        }

        // Возращаем координату вида |1 1|1 2|1 3| в виде |1|2|3|
        //                           |2 1|2 2|2 3|        |4|5|6|
        //                           |3 1|3 2|3 3|        |7|8|9|
        switch (array[0]) {
            case '1' -> {
                switch (array[1]) {
                    case '1' -> {return 1;}
                    case '2' -> {return 2;}
                    case '3' -> {return 3;}
                    default -> throw new IllegalStateException("Unexpected value: " + array[1]);
                }
            }
            case '2' -> {
                switch (array[1]) {
                    case '1' -> {return 4;}
                    case '2'-> {return 5;}
                    case '3' -> {return 6;}
                    default -> throw new IllegalStateException("Unexpected value: " + array[1]);
                }
            }
            case '3' -> {
                switch (array[1]) {
                    case '1' -> {return 7;}
                    case '2' -> {return 8;}
                    case '3' -> {return 9;}
                    default -> throw new IllegalStateException("Unexpected value: " + array[1]);
                }
            }
        }
        return 0;
    }
}
