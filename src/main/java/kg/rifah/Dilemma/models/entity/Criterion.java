package kg.rifah.Dilemma.models.entity;


import lombok.Data;

@Data
public class Criterion {
    private int serialNum;
    private int priority;
    private String criteria;
    private double evalOpt1;
    private double evalOpt2;
}
