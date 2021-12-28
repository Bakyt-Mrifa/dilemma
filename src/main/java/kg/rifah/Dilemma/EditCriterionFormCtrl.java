package kg.rifah.Dilemma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kg.rifah.Dilemma.models.entity.Criterion;

public class EditCriterionFormCtrl {
    private Criterion criterion;
    private Stage stage;
    private DilemmaFormCtrl dilemmaFormCtrl=new DilemmaFormCtrl();


    @FXML
    private TextField txtEditCrit;

    @FXML
    private TextField txtEditEvalOpt1;

    @FXML
    private TextField txtEditEvalOpt2;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnCancel;

    @FXML
    void onButtonClicked(ActionEvent event) {
        if (event.getSource().equals(btnEdit)){
            onEditButton();
        }
        else if (event.getSource().equals(btnCancel)){
            onCloseButton();
        }

    }
    @FXML
    void initialize() {
        dilemmaFormCtrl.setAcceptOnlyInt(txtEditEvalOpt1);
        dilemmaFormCtrl.setAcceptOnlyInt(txtEditEvalOpt2);
    }

    private void onCloseButton() {
        if (stage!=null){
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    public void initData(Stage stage, Criterion criterion){

        this.stage=stage;
        this.criterion=criterion;

        if (criterion!=null){
            txtEditCrit.setText(criterion.getCriterion());
            txtEditEvalOpt1.setText(Integer.toString(criterion.getEvalOpt1()));
            txtEditEvalOpt2.setText(Integer.toString(criterion.getEvalOpt2()));
        }

    }

    private void onEditButton() {
        Criterion newCrit=new Criterion();
        newCrit.setCriterion(txtEditCrit.getText());
        newCrit.setEvalOpt1(Integer.parseInt(txtEditEvalOpt1.getText()));
        newCrit.setEvalOpt2(Integer.parseInt(txtEditEvalOpt2.getText()));
        newCrit.setSerialNum(criterion.getSerialNum());
        System.out.println("Передаем: "+newCrit);

        onCloseButton();
        dilemmaFormCtrl.getEditCrit(newCrit);
    }

}
