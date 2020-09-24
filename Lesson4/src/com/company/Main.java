package com.company;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;

public class Main extends Application {

    private static Main window;

    class tTable {
        int buttonSize;
        int left = 16;
        int top = 16;
        boolean OnAI = true;
        Button[][] buttons;
        boolean isReal;
        Group root; //сохраним для рестарта без параметров
        int emptyCellsCount; //храним здесь количество пустых клеток
        int commandButtonsWidth;

        //Состояния
        static final int ERROR = -1;
        static final int EMPTY = 0;
        static final int CROSS = 1;
        static final int NOUGHT = 2;
        static final int VERTICAL_WIN = 3;
        static final int HORIZONTAL_WIN = 4;
        static final int LEFT_DIAGONAL_WIN = 5;
        static final int RIGHT_DIAGONAL_WIN = 6;

        //Текущий ход (крестики или нолики)
        int currentTurn;

        private byte[][] table;
        private int size;

        tTable(tTable parent) {
            isReal = false;
            size = parent.size;
            table = new byte[size][size];
            //table = parent.table.clone(); это почему-то работает очень станно
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    table[i][j] = parent.table[i][j];
                }
            }
            currentTurn = parent.currentTurn;
        }

        tTable(Group root, int size, int buttonSize, int commandButtonsWidth) {
            this.root = root;
            this.size = size;
            this.buttonSize = buttonSize;
            this.commandButtonsWidth = commandButtonsWidth;
            start();
        }

        void start(){
            currentTurn = CROSS;
            isReal = true;
            buttons = new Button[size][size];
            table = new byte[size][size];
            emptyCellsCount = size * size;
            draw(root);
        }

        int check(int x, int y) {
            if (x < 0 || y < 0 || x >= size || y >= size) return ERROR;
            return table[x][y];
        }

        //нет необходимости проверять всю матрицу если мы знаем координаты последнего хода
        int checkVictory(int x, int y) {
            int currentSight = table[x][y];
            int i;

            for (i = 0; i < size && table[i][y] == currentSight; i++) ; //Цикл без тела
            if (i == size) return VERTICAL_WIN;

            for (i = 0; i < size && table[x][i] == currentSight; i++) ;
            if (i == size) return HORIZONTAL_WIN;

            if (x == y) {
                for (i = 0; i < size && table[i][i] == currentSight; i++) ;
                if (i == size) return LEFT_DIAGONAL_WIN;
            }

            if (x == size - y - 1) {
                for (i = 0; i < size && table[size - i - 1][i] == currentSight; i++) ;
                if (i == size) return RIGHT_DIAGONAL_WIN;
            }

            return EMPTY;
        }

        private void setTextAndChangeTurn(int x, int y) {
            if (currentTurn == CROSS) {
                currentTurn = NOUGHT;
                if (isReal) buttons[y][x].setText("X");
            } else {
                currentTurn = CROSS;
                if (isReal) buttons[y][x].setText("O");
            }
        }

        void showVictory(int x, int y, int victory) {
            switch (victory) {
                case VERTICAL_WIN:
                    for (int i = 0; i < size; i++) {
                        buttons[y][i].setStyle("-fx-background-color: yellow");
                    }
                    break;
                case HORIZONTAL_WIN:
                    for (int i = 0; i < size; i++) {
                        buttons[i][x].setStyle("-fx-background-color: yellow");
                    }
                    break;
                case LEFT_DIAGONAL_WIN:
                    for (int i = 0; i < size; i++) {
                        buttons[i][i].setStyle("-fx-background-color: yellow");
                    }
                    break;
                case RIGHT_DIAGONAL_WIN:
                    for (int i = 0; i < size; i++) {
                        buttons[i][size - i - 1].setStyle("-fx-background-color: yellow");
                    }
            }
        }

        class turnResult {
            int x;
            int y;
            boolean isGoodTurn;
            boolean isBadTurn;

            turnResult(int x, int y, boolean isGoodTurn, boolean isBadTurn) {
                this.x = x;
                this.y = y;
                this.isGoodTurn = isGoodTurn;
                this.isBadTurn = isBadTurn;
            }
        }

        turnResult findTheWay(tTable table) {
            return findTheWay(this, true);
        }

        turnResult findTheWay(tTable table, boolean isMyTurn) {
            boolean[][] secondCalculation = new boolean[size][size];
            turnResult bestWay = null;
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {

                    if (table.table[x][y] == EMPTY) {
                        tTable vTable = new tTable(table);
                        int result = vTable.makeTurn(x, y);
                        if (result == EMPTY) {
                            secondCalculation[x][y] = false;
                            if (isMyTurn) {
                                return new turnResult(x, y, true, false);
                            } else {
                                return new turnResult(x, y, false, true);
                            }
                        } else {
                            secondCalculation[x][y] = true;
                        }
                    }
                }
            }

            if (isMyTurn) {
                for (int x = 0; x < size; x++) {
                    for (int y = 0; y < size; y++) {
                        if (secondCalculation[x][y]) {
                            if (bestWay == null) bestWay = new turnResult(x, y, false, true);
                            tTable vTable = new tTable(table);
                            int result = vTable.makeTurn(x, y);
                            turnResult res = findTheWay(vTable, !isMyTurn);
                            if (!res.isBadTurn && bestWay.isBadTurn) {
                                bestWay = new turnResult(x, y, res.isGoodTurn, false);
                            }
                            if (!res.isBadTurn && res.isGoodTurn && !bestWay.isGoodTurn) {
                                bestWay = new turnResult(x, y, true, false);
                            }
                        }
                    }
                }
            } else {
                for (int x = 0; x < size; x++) {
                    for (int y = 0; y < size; y++) {
                        if (secondCalculation[x][y]) {
                            if (bestWay == null) bestWay = new turnResult(x, y, true, false);
                            tTable vTable = new tTable(table);
                            int result = vTable.makeTurn(x, y);
                            turnResult res = findTheWay(vTable, !isMyTurn);
                            if (!res.isGoodTurn && bestWay.isGoodTurn) {
                                bestWay = new turnResult(x, y, false, res.isBadTurn);
                            }
                            if (!res.isGoodTurn && res.isBadTurn && !bestWay.isBadTurn) {
                                bestWay = new turnResult(x, y, false, true);
                            }
                        }
                    }
                }
            }

            if (bestWay == null)
                return new turnResult(0, 0, false, false);
            else
                return bestWay;
        }


        //Ход компьютера, робот тупо перебирает все возможные варианты и выбирает наиболее лучший благо тут у нас не шахматы
        //можно было бы сделать дерево и записать туда все варианты при загрузке, но мне лень
        //еще можно было сделать уровень сложности, но это всё-таки учебная задача
        //в теории можно сделать поле любого размера но уже при поле 4 на 4 программа думает очень долго,
        //да это далеко не самый эффективный метод но я давно хотел так сделать :)
        void makeTurnAI() {
            if(currentTurn == EMPTY) start();
            turnResult bestWay = findTheWay(this);
            if (bestWay != null) makeTurn(bestWay.x, bestWay.y);
        }

        int makeTurn(javafx.event.ActionEvent event) {
            int x = (int) ((Button) event.getSource()).getLayoutX() / buttonSize;
            int y = (int) ((Button) event.getSource()).getLayoutY() / buttonSize;
            int result = makeTurn(x, y);
            if (result != EMPTY && OnAI) {
                makeTurnAI();
            }
            return result;
        }


        int makeTurn(int x, int y) {
            if (currentTurn == EMPTY) return EMPTY;

            int check = check(x, y);
            if (check != 0) return EMPTY;

            table[x][y] = (byte) currentTurn;
            setTextAndChangeTurn(x, y);

            int victory = checkVictory(x, y);
            if (victory != EMPTY) {
                if (isReal) showVictory(x, y, victory);
                currentTurn = EMPTY;
            }

            emptyCellsCount--;
            if(emptyCellsCount == 0) currentTurn = EMPTY;

            return currentTurn;
        }


        void draw(Group root) {
            root.getChildren().clear();
            int y = top;
            for (int i = 0; i < size; i++) {
                int x = left;
                for (int j = 0; j < size; j++) {
                    buttons[i][j] = new Button();
                    Button tmpButton = buttons[i][j];
                    tmpButton.setLayoutX(x);
                    tmpButton.setLayoutY(y);
                    tmpButton.setMinHeight(buttonSize);
                    tmpButton.setMaxHeight(buttonSize);
                    tmpButton.setMinWidth(buttonSize);
                    tmpButton.setMinWidth(buttonSize);

                    tmpButton.setOnAction(e -> {
                        makeTurn(e);
                    });

                    root.getChildren().add(tmpButton);
                    x += buttonSize;
                }
                y += buttonSize;
            }

            int commandButtonHeight = buttonSize;
            int commandButtonWidth = buttonSize * commandButtonsWidth;

            //это можно вынести в отдельный метод чтобы не городить такое но уже час ночи.
            //restart button
            Button restartButton = new Button();
            restartButton.setLayoutX(left + buttonSize * (size + 1));
            restartButton.setLayoutY(top);
            restartButton.setMinHeight(commandButtonHeight);
            restartButton.setMaxHeight(commandButtonHeight);
            restartButton.setMinWidth(commandButtonWidth);
            restartButton.setMinWidth(commandButtonWidth);
            restartButton.setText("Restart");
            restartButton.setOnAction(e -> {
                start();
            });
            root.getChildren().add(restartButton);

            //AI-turn button
            Button AI_Button = new Button();
            AI_Button.setLayoutX(left + buttonSize * (size + 1));
            AI_Button.setLayoutY(top + buttonSize);
            AI_Button.setMinHeight(commandButtonHeight);
            AI_Button.setMaxHeight(commandButtonHeight);
            AI_Button.setMinWidth(commandButtonWidth);
            AI_Button.setMinWidth(commandButtonWidth);
            AI_Button.setText("AI's turn!");
            AI_Button.setOnAction(e -> {
                makeTurnAI();
            });
            root.getChildren().add(AI_Button);

            //ON-OFF-AI button
            Button OnAI_Button = new Button();
            OnAI_Button.setLayoutX(left + buttonSize * (size + 1));
            OnAI_Button.setLayoutY(top + buttonSize * 2);
            OnAI_Button.setMinHeight(commandButtonHeight);
            OnAI_Button.setMaxHeight(commandButtonHeight);
            OnAI_Button.setMinWidth(commandButtonWidth);
            OnAI_Button.setMinWidth(commandButtonWidth);
            OnAI_Button.setText((OnAI)?"AI is ON":"AI is OFF");
            OnAI_Button.setOnAction(e -> {
                OnAI = !OnAI;
                ((Button) e.getSource()).setText((OnAI)?"AI is ON":"AI is OFF");
            });
            root.getChildren().add(OnAI_Button);
        }
    }


    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {

        Group root = new Group();

        tTable table = new tTable(root, 3, 32, 4);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Крестики - Нолики");
        stage.setWidth(table.buttonSize * (table.size + 1) + table.commandButtonsWidth * table.buttonSize + 48);
        stage.setHeight(table.buttonSize * (table.size + 1) + 48);

        stage.show();

        //table.makeTurnAI(); // можно раскомментировать чтобы первым ходил комп.
    }
}




