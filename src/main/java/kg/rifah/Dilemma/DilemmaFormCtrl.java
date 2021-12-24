package kg.rifah.Dilemma;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import kg.rifah.Dilemma.models.entity.Criterion;

import java.awt.*;
import java.io.IOException;
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
    private MenuItem mnuAbout;


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

        } else if (event.getSource().equals(btnClearTable)) {

        } else if (event.getSource().equals(btnGetResult)) {

        } else if (event.getSource().equals(mnuAbout)) {
            getMessage();
        }
    }

    @FXML
    void onKeyTyped(KeyEvent event) {

        if (KeyEvent.KEY_TYPED.equals(event.getEventType())) {
            checkOption();
        }
    }

    List<Criterion> criteria = new ArrayList<Criterion>();

    private String option1 = null;
    private String option2 = null;


    private void checkOption() {
        option1 = txtOption1.getText();
        option2 = txtOption2.getText();
        setColumnAsOptions(option1, option2);
        if (option1.length() == 0 || option2.length() == 0) {
            Alert fill = new Alert(Alert.AlertType.ERROR);
            fill.setTitle("Ошибка");
            fill.setHeaderText("Заполните поля выше (Варианты)!!!");
            fill.showAndWait();
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

    private void getMessage() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Об этой программе");
        message.setHeaderText("\"Дилемма\"");
        message.setContentText("Программа \"Дилемма\" создана для помощи при выборе,\n" +
                "когда предмет выбора не однозначный,\n" +
                "и сделать правильный выбор очень сложно.\n" +
                "Используйте эту программу для визуализации\n" +
                "своего выбора и получения некоторой помощи\n" +
                "и подсказки для принятия правильного решения\n\n" +
                "Автор идеи:\nБахытжан, e-mail:bahytjan@gmail.com\n" +
                "Написал программу:\nБакыт Мамбеталиев, e-mail: Bakyt.mrifah@gmail.com");
        message.showAndWait();
    }


    @FXML
    void initialize() {
//Поля принимают только Double значения, но пока еще типа String
        setAcceptOnlyDouble(txtEvalOpti1);
        setAcceptOnlyDouble(txtEvalOpti2);

        colSN.setCellValueFactory(new PropertyValueFactory<Criterion, Integer>("serialNum"));
        colCrit.setCellValueFactory(new PropertyValueFactory<Criterion, String>("criterion"));
        colEvalOp1.setCellValueFactory(new PropertyValueFactory<Criterion, Integer>("evalOpt1"));
        colEvalOp2.setCellValueFactory(new PropertyValueFactory<Criterion, Integer>("evalOpt2"));
        refresh();

    }


    private void addCriterion() {
        if (txtEvalOpti1.getText().length() == 0 || txtEvalOpti2.getText().length() == 0) {
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

    private TextField setAcceptOnlyDouble(final TextField value) {

        value.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,1}?")) {
                    value.setText(oldValue);
                    Toolkit.getDefaultToolkit().beep();
                }
                if (Integer.parseInt(newValue) < 1 || Integer.parseInt(newValue) > 3) {
                    Alert fill = new Alert(Alert.AlertType.ERROR);
                    fill.setTitle("Ошибка");
                    fill.setHeaderText("Введите значение от 1 до 3");
                    fill.showAndWait();
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
            Stage stage=new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/EditCriterionForm.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        ObservableList ol = FXCollections.observableList(criteria);
        tbCrit.setItems(ol);
        tbCrit.getSortOrder().add(colSN);
        txtEvalOpti1.clear();
        txtEvalOpti2.clear();
    }
}
