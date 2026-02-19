package com.gravity_sim;

import javafx.scene.paint.Color;

public class RaioDeLuz extends ObjetoEspacial {
    private Vetor3D direcao;
    private static final double C = 5e3;

    public RaioDeLuz(String nome, Vetor3D posicao, Vetor3D direcaoInicial, Color cor) {
        super(nome, posicao, new Vetor3D(0, C, 0), cor, 1.0);
        this.direcao = direcaoInicial.normalize();
    }

    @Override
    public void atualizar(double dt, Engine engine) {
        Vetor3D g = engine.calcularAceleracao(this.posicao, this);
        
        Vetor3D desvio = g.mult(dt * 2.0);
        this.direcao = this.direcao.mult(C).add(desvio).normalize();

        this.posicao = this.posicao.add(this.direcao.mult(C * dt));

        rastro.add(this.posicao);
        if (rastro.size() > MAX_RASTRO) rastro.removeFirst();
    }
}