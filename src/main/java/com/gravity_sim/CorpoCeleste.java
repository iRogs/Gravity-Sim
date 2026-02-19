package com.gravity_sim;

import java.util.LinkedList;

import javafx.scene.paint.Color;

public class CorpoCeleste {
    private String nome;
    private double massa;
    private LinkedList<Vetor3D> rastro = new LinkedList<>();
    private static final int MAX_PONTOS = 5000; //
    private Vetor3D posicao;
    private Vetor3D velocidade;
    private Vetor3D forcaAcumulada = new Vetor3D(0, 0, 0);
    private Color cor;
    private double raioVisual;

    public CorpoCeleste(String nome, double massa, Vetor3D pos, Vetor3D vel, Color cor, double raioVisual) {
        this.nome = nome;
        this.massa = massa;
        this.posicao = pos;
        this.velocidade = vel;
        this.cor = cor;
        this.raioVisual = raioVisual;
    }

    public void aplicarForca(Vetor3D forca) {
        this.forcaAcumulada = this.forcaAcumulada.add(forca);
    }

    public void atualizar(double deltaTime) {
        // a = F / m
        Vetor3D aceleracao = forcaAcumulada.div(massa);
        // v = v0 + a * dt
        velocidade = velocidade.add(aceleracao.mult(deltaTime));
        // p = p0 + v * dt
        posicao = posicao.add(velocidade.mult(deltaTime));
        
        posicao = posicao.add(velocidade.mult(deltaTime));
    
        // Adiciona a posição atual ao rastro
        rastro.add(posicao);
    
        // Remove o ponto mais antigo se passar do limite
        if (rastro.size() > MAX_PONTOS) {
            rastro.removeFirst();
        }
    
        forcaAcumulada = new Vetor3D(0, 0, 0);
    }

    public Vetor3D getPosicao() { return posicao; }
    public String getNome() { return nome; }
    public double getMassa() { return massa; }
    public LinkedList<Vetor3D> getRastro() { return rastro; }
    public Color getCor() { return cor; }
    public double getRaioVisual() { return raioVisual; }
}