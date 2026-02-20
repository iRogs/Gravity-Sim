package com.gravity_sim;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private final double G = 6.67430e-11; // Constante Gravitacional
    private List<ObjetoEspacial> objetos = new ArrayList<>();

    Engine() {}

    public void adicionarObjeto(ObjetoEspacial obj) { objetos.add(obj); }

    public void step(double dt) {
        objetos.forEach(c -> c.atualizar(dt, this));
    }

    public Vetor3D calcularAceleracao(Vetor3D ponto, ObjetoEspacial ignorar) {
        return objetos.stream()
        .filter(obj -> obj instanceof CorpoCeleste) 
        .filter(obj -> obj != ignorar)
        .map(obj -> (CorpoCeleste) obj) 
        .map(corpo -> {
            Vetor3D direcao = corpo.getPosicao().sub(ponto);
            double distSq = direcao.magSq();
            
            if (distSq < 1e6) return new Vetor3D(0, 0, 0); 

            double intensidade = (G * corpo.getMassa()) / distSq;
            return direcao.normalize().mult(intensidade);
        })
        .reduce(new Vetor3D(0, 0, 0), Vetor3D::add);
}

    public List<ObjetoEspacial> getObjetos() {
        return objetos;
    }
}
