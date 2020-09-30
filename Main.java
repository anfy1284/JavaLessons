package com.anfy.java2.lesson2;

public class Main {

    static class WrongMatrixSizeException extends Exception {
        WrongMatrixSizeException() {
            super("Wrong matrix size!");
        }
    }

    static class NoIntegerValueException extends Exception {
        NoIntegerValueException() {
            super("No integer value!");
        }
    }

    static String[][] makeStringMatrixFromString(String inputString) {
        String[] matrixStrings = inputString.split("\\n+\\s*");
        String[][] resultValue = new String[matrixStrings.length][];
        for (int i = 0; i < matrixStrings.length; i++) {
            resultValue[i] = matrixStrings[i].split("\\s+");
        }
        return resultValue;
    }

    static int[][] convertStringMatrix(String[][] stringMatrix, int sizeX, int sizeY) throws WrongMatrixSizeException, NoIntegerValueException {
        if (stringMatrix.length != sizeY) throw new WrongMatrixSizeException();

        int[][] value = new int[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++) {
            if (stringMatrix[i].length != sizeX) throw new WrongMatrixSizeException();
            for (int j = 0; j < sizeX; j++) {
                try {
                    value[i][j] = Integer.parseInt(stringMatrix[i][j]);
                } catch (NumberFormatException e) {
                    throw new NoIntegerValueException();
                }
            }
        }
        return value;
    }

    static double calculateMatrix(int[][] matrix){
        int value = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                value += matrix[i][j];
            }
        }
        return value / 2;
    }

    static void doAllTheStuff(String inputString, int sizeX, int sizeY) {
        int[][] value = null;
        try {
            value = convertStringMatrix(makeStringMatrixFromString(inputString), sizeX, sizeY);
        } catch (WrongMatrixSizeException e) {
            System.out.println("Неправильный размер матрицы!");
            return;
        } catch (NoIntegerValueException e) {
            System.out.println("Не получилось преобразовать значение в число!");
            return;
        }
        System.out.printf("Результат нехитрых вычислений = %f\n", calculateMatrix(value));
    }

    static void doAllTheStuff(String inputString) {
        doAllTheStuff(inputString, 4, 4);
    }

    public static void main(String[] args) {

        doAllTheStuff("fdfe3   4534    fg3 \n e435  fggfh\n\n 34t54t \ner");
        doAllTheStuff("1   2    3 2 \n 4  5 6 5\n 7 8  9 3\n 4  8 2 ");
        doAllTheStuff("1   2    3 2 \n 4  5 6 5\n 7 8  9 3\n 4 6 8 2 ");

    }
}
