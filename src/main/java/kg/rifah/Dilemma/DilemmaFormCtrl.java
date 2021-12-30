package kg.rifah.Dilemma;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kg.rifah.Dilemma.models.entity.Criterion;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DilemmaFormCtrl {

    @FXML
    private TableView<Criterion> tbCrit;

    @FXML
    private TableColumn<Criterion, Integer> colSN;

    @FXML
    private TableColumn<Criterion, String> colCrit;

    @FXML
    private TableColumn<Criterion, Integer> colEvalOp1;

    @FXML
    private TableColumn<Criterion, Integer> colEvalOp2;

    @FXML
    private Button btnUp;

    @FXML
    private Button btnDown;

    @FXML
    private Button btnEditField;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnAddCrit;

    @FXML
    private Button btnGetResult;

    @FXML
    private Button btnClearTable;

    @FXML
    private TextField txtOption1;

    @FXML
    private TextField txtOption2;

    @FXML
    private TextField txtCriteria;

    @FXML
    private TextField txtEvalOpti1;

    @FXML
    private TextField txtEvalOpti2;

    @FXML
    private Label lblResult;

    @FXML
    private Label lblAbout;


    @FXML
    void onMouseClicked(MouseEvent event) {
        if (event.getSource().equals(lblAbout)) {
            about();
        }
    }

    @FXML
    void onButtonClicked(ActionEvent event) {
        if (event.getSource().equals(btnExit)) {
            onExitBtn(event);
        } else if (event.getSource().equals(btnUp)) {
            onBtnUpClicked();
        } else if (event.getSource().equals(btnDown)) {
            onBtnDownClicked();
        } else if (event.getSource().equals(btnAddCrit)) {
            addCriterion();
        } else if (event.getSource().equals(btnEditField)) {
            editCriterion();
        } else if (event.getSource().equals(btnRemove)) {
            removeCriterion();
        } else if (event.getSource().equals(btnClearTable)) {
            clearTable();
        } else if (event.getSource().equals(btnGetResult)) {
            getResult();
        }
    }


    @FXML
    void onKeyTyped(KeyEvent event) {

        if (KeyEvent.KEY_TYPED.equals(event.getEventType())) {
            checkOption();
        }
    }

    private static List<Criterion> criteria = new ArrayList<Criterion>();

    private String option1 = null;
    private String option2 = null;

    private void checkOption() {
        option1 = txtOption1.getText();
        option2 = txtOption2.getText();
        setColumnAsOptions(option1, option2);
        if (option1.length() == 0 || option2.length() == 0) {
            Toolkit.getDefaultToolkit().beep();
            txtCriteria.clear();
            Alert fill = new Alert(Alert.AlertType.ERROR);
            fill.setTitle("Ошибка");
            fill.setHeaderText("Заполните поля выше (Варианты)!!!");
            fill.showAndWait();
            return;
        }else if (txtOption1.getText().equals(txtOption2.getText())){
            Toolkit.getDefaultToolkit().beep();
            txtCriteria.clear();
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Ошибка заполнения");
            message.setHeaderText("Варианты не должны быть одинаковыми!!!");
            message.showAndWait();
            return;
        }
    }

    private void setColumnAsOptions(String opt1, String opt2) {
        if (colEvalOp1.getText().length() == 0 || colEvalOp2.getText().length() == 0) {
            colEvalOp1.setText("Оцентка для\nВариант-1");
            colEvalOp2.setText("Оцентка для\nВариант-2");
        } else {
            colEvalOp1.setText("Оцентка для\n" + opt1);
            colEvalOp2.setText("Оцентка для\n" + opt2);
        }
    }

    private void about() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Об этой программе");
        message.setHeaderText("\"Дилемма\"");
        message.setContentText("Программа \"Дилемма\" создана для помощи при выборе,\n" +
                "когда предмет выбора не однозначный,\n" +
                "и сделать правильный выбор очень сложно.\n" +
                "Используйте эту программу для визуализации\n" +
                "своего выбора и получения некоторой помощи\n" +
                "и подсказки для принятия правильного решения\n\n" +
                "Автор идеи:\nСейтмухамбетов Бахытжан Нурханович\n\n" +
                "Написал программу:\nБакыт Мамбеталиев, e-mail: Bakyt.mrifah@gmail.com");
        message.showAndWait();
    }


    @FXML
    void initialize() {
//Поля принимают только Integer значения, но пока еще типа String
        setAcceptOnlyInt(txtEvalOpti1);
        setAcceptOnlyInt(txtEvalOpti2);

        colSN.setCellValueFactory(new PropertyValueFactory<Criterion, Integer>("serialNum"));
        colCrit.setCellValueFactory(new PropertyValueFactory<Criterion, String>("criterion"));
        colEvalOp1.setCellValueFactory(new PropertyValueFactory<Criterion, Integer>("evalOpt1"));
        colEvalOp2.setCellValueFactory(new PropertyValueFactory<Criterion, Integer>("evalOpt2"));
        refresh();

    }


    private void addCriterion() {
        if (txtEvalOpti1.getText().length() == 0 || txtEvalOpti2.getText().length() == 0) {
            Toolkit.getDefaultToolkit().beep();
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Ошибка заполнения");
            message.setHeaderText("Поля оценки не могут быть пустыми");
            message.showAndWait();
            return;
        }
        String critName = txtCriteria.getText();
        //check for uniq criterion
        for (Criterion crit : criteria) {
            if (crit.getCriterion().equals(critName)) {
                Alert message = new Alert(Alert.AlertType.ERROR);
                message.setTitle("Ошибка заполнения");
                message.setHeaderText("Такой критерий уже существует");
                message.showAndWait();
                return;
            }

        }
        int evalOpt1 = Integer.parseInt(txtEvalOpti1.getText());
        int evalOpt2 = Integer.parseInt(txtEvalOpti2.getText());

        Criterion criterion = new Criterion();
        criterion.setCriterion(critName);
        criterion.setEvalOpt1(evalOpt1);
        criterion.setEvalOpt2(evalOpt2);
        if (criteria != null) {
            criterion.setSerialNum(criteria.size() + 1);
        } else {
            criterion.setSerialNum(1);
        }
        criteria.add(criterion);
        refresh();
        txtCriteria.requestFocus();
    }


    private void onExitBtn(ActionEvent event) {
        /*Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();*/

//        (btnExit.getScene().getWindow()).hide();

        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public TextField setAcceptOnlyInt(final TextField value) {

        value.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,1}?")) {
                    value.setText(oldValue);
                    Toolkit.getDefaultToolkit().beep();
                }
                if (Integer.parseInt(newValue) < 1 || Integer.parseInt(newValue) > 3) {
                    Toolkit.getDefaultToolkit().beep();
                    Alert fill = new Alert(Alert.AlertType.ERROR);
                    fill.setTitle("Ошибка");
                    fill.setHeaderText("Введите значение от 1 до 3");
                    fill.showAndWait();
                    value.setText(oldValue);
                }
            }
        });
        return value;
    }

    private void onBtnUpClicked() {
        if (tbCrit.getSelectionModel().getSelectedItem().getSerialNum() != 1) {
            Criterion criterion = tbCrit.getSelectionModel().getSelectedItem();
            Criterion criterion1 = criteria.get(criterion.getSerialNum() - 2);
            criterion1.setSerialNum(criterion.getSerialNum());
            criterion.setSerialNum(criterion1.getSerialNum() - 1);
            for (Criterion crit : criteria) {
                if (crit.equals(criterion)) {
                    criteria.set(criterion.getSerialNum() - 1, criterion);
                    criteria.set(criterion.getSerialNum(), criterion1);
                }
            }
        }
        refresh();
    }

    private void onBtnDownClicked() {
        if (tbCrit.getSelectionModel().getSelectedItem().getSerialNum() != criteria.size()) {
            Criterion criterion = tbCrit.getSelectionModel().getSelectedItem();
            Criterion criterion1 = criteria.get(criterion.getSerialNum());
            criterion1.setSerialNum(criterion.getSerialNum());
            criterion.setSerialNum(criterion1.getSerialNum() + 1);
            for (Criterion crit : criteria) {
                if (crit.equals(criterion)) {
                    criteria.set(criterion1.getSerialNum() - 1, criterion1);
                    criteria.set(criterion1.getSerialNum(), criterion);
                }
            }
        }
        refresh();
    }

    private void editCriterion() {
        final Criterion criterion = tbCrit.getSelectionModel().getSelectedItem();

        if (criterion != null) {

            Stage stage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditCriterionForm.fxml"));
                loader.load();
                stage.setScene(new Scene((Parent) loader.getRoot()));
                EditCriterionFormCtrl editCriterionFormCtrl = loader.getController();

                editCriterionFormCtrl.initData(stage, criterion);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent event) {
                        refresh();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        } else checkElement();
    }

    public void getEditCrit(Criterion criterion) {
        System.out.println("Принимаем: " + criterion);
        System.out.println(criteria);
        criteria.set(criterion.getSerialNum() - 1, criterion);
        refresh();
    }

    private void removeCriterion() {
        Criterion criterion = tbCrit.getSelectionModel().getSelectedItem();
        if (criterion != null) {
            criteria.remove(criterion);
            int a = 1;
            for (Criterion crit :
                    criteria) {
                crit.setSerialNum(a++);
            }
            refresh();
        } else checkElement();
    }

    private void clearTable() {
        criteria.clear();
        refresh();
    }

    private void getResult() {
        if (criteria.size() == 0) {
            Toolkit.getDefaultToolkit().beep();
            Alert fill = new Alert(Alert.AlertType.ERROR);
            fill.setTitle("Ошибка");
            fill.setHeaderText("Нет критериев");
            fill.setContentText("Заполните критерии!!!\n" +
                    "Рекомендуется заполнить более 10 критериев.\n" +
                    "Чем больше критериев, тем точнее результат ;)\n\n" +
                    "              Удачи!!!");
            fill.showAndWait();
            return;
        }
        int a = criteria.size();
        for (Criterion crit :
                criteria) {
            crit.setPriority(a--);
        }
        System.out.println(criteria + "Fill priority");
        calculate();
    }

    private void calculate() {
        int ideal = 0;
        double opti1 = 0,
               opti2 = 0;

        for (int i = criteria.size(); i >= 0; i--) {
            ideal = ideal + (i * 3);
        }

        for (Criterion crit :
                criteria) {
            opti1 = opti1 + crit.getPriority() * crit.getEvalOpt1();
            opti2 = opti2 + crit.getPriority() * crit.getEvalOpt2();
        }
        System.out.println(ideal+" "+opti1+" "+opti2);

        lblResult.setWrapText(true);
        lblResult.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 15; -fx-text-fill: #b4fff6;");

        if (opti1 == opti2) {
            lblResult.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 20; -fx-text-fill: #b4fff6;");
            lblResult.setText("Варианты абсолютно равны!!!");
        } else if (opti1 > opti2) {
            System.out.println("Отработал: "+opti1+">"+opti2);
            result(ideal, opti1, opti2, txtOption1.getText(), txtOption2.getText());
        } else {
            System.out.println("Отработал: "+opti1+"<"+opti2);
            result(ideal, opti2, opti1, txtOption2.getText(), txtOption1.getText());
        }
    }

    private void result(Integer ideal, Double opt1, Double opt2, String good, String bad) {
        double goodOption, badOption;

        goodOption = new Double( (100)- opt1 / ideal * 100);
        badOption = (100 - (opt2 / opt1 * 100));

        System.out.println("Good: "+goodOption);
        System.out.println("Bad: "+badOption);
        String goodContent;
        String badContent;
        if (goodOption <= 20) {
            goodContent=(new DecimalFormat("##.##").format(goodOption) + "% - \"" + good + "\" - Однозначно отличный вариант!!!\n" +
                    "Очень рекомендуется к выбору");
        } else {
            goodContent=(new DecimalFormat("##.##").format(goodOption) + "% - \"" + good + "\" - Не худший вариант.\n" +
                    "Лучше чем \"" + bad + " \", но не однозначно.\n" +
                    "Не стоит спешить с выбором!");
        }

        if (badOption - 100 >= 20) {
            badContent=("\n\n__________________________\n"+ new DecimalFormat("##.##").format(badOption) + "% - \"" + bad + "\" - Однозначно вариант \"ТУХЛЫЙ\"!!!\n" +
                    "Очень не рекомендуется к выбору");
        } else {
            badContent=("\n\n__________________________\n"+ new DecimalFormat("##.##").format(badOption) + "% - \"" + bad + "\" - Вариант не лучший. Но, лучше чем ничего");
        }
        lblResult.setText(goodContent+"\n\n\ninfo: Отличный вариант от 1% до 20%"+badContent+"\n\n\ninfo: Наихудший вариант от 20% и выше, а все что меньше 20%- имеет право на рассмотрение.");
    }

    private void checkElement() {
        Toolkit.getDefaultToolkit().beep();
        Alert fill = new Alert(Alert.AlertType.ERROR);
        fill.setTitle("Ошибка");
        fill.setHeaderText("Выберите элемент");
        fill.showAndWait();
    }

    private void refresh() {
        ObservableList ol = FXCollections.observableList(criteria);
        tbCrit.setItems(ol);
        tbCrit.getSortOrder().add(colSN);
        txtEvalOpti1.clear();
        txtEvalOpti2.clear();
    }
}
