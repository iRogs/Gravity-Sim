package com.gravity_sim;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private final double G = 6.67430e-11; // Constante Gravitacional real
    private List<CorpoCeleste> corpos = new ArrayList<>();

    Engine() {}

    public void adicionarCorpo(CorpoCeleste c) { corpos.add(c); }

    public void step(double deltaTime) {
        // 1. Calcular forças entre todos os pares (O(n²))
        for (int i = 0; i < corpos.size(); i++) {
            for (int j = i + 1; j < corpos.size(); j++) {
                calcularGravidade(corpos.get(i), corpos.get(j));
            }
        }

     // Elegante e eficiente
        corpos.forEach(c -> c.atualizar(deltaTime));
    }

    private void calcularGravidade(CorpoCeleste a, CorpoCeleste b) {
        Vetor3D direcao = b.getPosicao().sub(a.getPosicao());
        double distSq = direcao.magSq();
        
        if (distSq == 0) return; // Evitar divisão por zero em colisões

        double intensidade = (G * a.getMassa() * b.getMassa()) / distSq;
        Vetor3D forca = direcao.normalize().mult(intensidade);

        a.aplicarForca(forca);             
        b.aplicarForca(forca.mult(-1));    // Puxa B para A (3ª Lei de Newton)
    }


    public List<CorpoCeleste> getCorpos() {
        return corpos;
    }
}
