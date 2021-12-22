package kg.rifah.Dilemma.models.entity;


import lombok.Data;

@Data
public class Criterion {
    private int serialNum;
    private int priority;
    private String criterion;
    private int evalOpt1;
    private int evalOpt2;
}
