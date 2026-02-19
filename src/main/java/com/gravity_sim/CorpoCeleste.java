package com.gravity_sim;

import java.util.LinkedList;

import javafx.scene.paint.Color;

public class CorpoCeleste extends ObjetoEspacial {
    private double massa;
    private Vetor3D forcaAcumulada = new Vetor3D(0, 0, 0);

    public CorpoCeleste(String nome, double massa, Vetor3D pos, Vetor3D vel, Color cor, double raioVisual) {
        super(nome, pos, vel, cor, raioVisual);
        this.massa = massa;

    }

    public void aplicarForca(Vetor3D forca) {
        this.forcaAcumulada = this.forcaAcumulada.add(forca);
    }

    public void atualizar(double deltaTime, Engine engine) {
        // a = F / m
        Vetor3D g = engine.calcularAceleracao(posicao, this);
        // v = v0 + a * dt
        velocidade = velocidade.add(g.mult(deltaTime));
        // p = p0 + v * dt
        posicao = posicao.add(velocidade.mult(deltaTime));
        
        posicao = posicao.add(velocidade.mult(deltaTime));
    
        rastro.add(posicao);
    
        // Remover o ponto mais antigo se passar do limite
        rastro.add(this.posicao);
        if (rastro.size() > MAX_RASTRO) {
            rastro.removeFirst();
        }
        forcaAcumulada = new Vetor3D(0, 0, 0);
    }

    public double getMassa() { return massa; }
}