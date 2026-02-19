package com.gravity_sim;

import java.util.LinkedList;

import javafx.scene.paint.Color;

public abstract class ObjetoEspacial {
    protected String nome;
    protected Vetor3D posicao;
    protected Vetor3D velocidade;
    protected Color cor;
    protected double raioVisual;
    protected LinkedList<Vetor3D> rastro = new LinkedList<>();
    protected static final int MAX_RASTRO = 500;

    public ObjetoEspacial(String nome, Vetor3D pos, Vetor3D vel, Color cor, double raioVisual) {
        this.nome = nome;
        this.posicao = pos;
        this.velocidade = vel;
        this.cor = cor;
        this.raioVisual = raioVisual;
    }

    public abstract void atualizar(double dt, Engine engine);

    public Color getCor() {return cor;}
    public double getRaioVisual() {return raioVisual;}
    public String getNome() { return nome; }
    public Vetor3D getPosicao() { return posicao; } 
    public Vetor3D getVelocidade() { return velocidade; }
    public LinkedList<Vetor3D> getRastro() { return rastro; }
}
