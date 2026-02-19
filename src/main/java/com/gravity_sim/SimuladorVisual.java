package com.gravity_sim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SimuladorVisual extends Application {
    private Engine engine = new Engine();
    private double escala = 4e-10;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Group root = new Group(canvas);
        Scene scene = new Scene(root, 800, 800, Color.BLACK);

        CorpoCeleste sol = new CorpoCeleste("Sol", 50.989e30, 
        new Vetor3D(0,0,0), new Vetor3D(0,0,0), Color.YELLOW, 25);

        CorpoCeleste terra = new CorpoCeleste("Terra", 5.972e24, 
        new Vetor3D(1.496e11, 0, 0), new Vetor3D(0, 129780, 0), Color.DEEPSKYBLUE, 8);

        CorpoCeleste marte = new CorpoCeleste("Marte", 6.39e23, 
        new Vetor3D(2.279e11, 0, 0), new Vetor3D(0, 54070, 0), Color.ORANGERED, 6);

        CorpoCeleste jupiter = new CorpoCeleste("Jupiter", 1.898e27, 
        new Vetor3D(7.785e11, 0, 0), new Vetor3D(0, 43070, 0), Color.ORANGE, 15);

        engine.adicionarObjeto(sol);
        engine.adicionarObjeto(terra);
        engine.adicionarObjeto(marte);
        engine.adicionarObjeto(jupiter);

        scene.setOnMouseClicked(event -> {
    // 1. Pega as coordenadas do clique em pixels
    double mouseX = event.getX();
    double mouseY = event.getY();

    // 2. Converte para metros (usando o centro da tela como 400, 400)
    double universoX = (mouseX - 400) / escala;
    double universoY = (mouseY - 400) / escala;

    // 3. Define a direção (ex: sempre para a direita)
    Vetor3D posicaoInicial = new Vetor3D(universoX, universoY, 0);
    Vetor3D direcaoLaser = new Vetor3D(1, 0, 0);

        // 4. Cria e adiciona o objeto à engine
        RaioDeLuz laser = new RaioDeLuz(
            "LaserClick", 
            posicaoInicial, 
            direcaoLaser, 
            Color.RED
        );
        
        engine.adicionarObjeto(laser);
    });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                renderizar(gc);
            }
        }.start();

        stage.setTitle("Simulador de Gravidade 2D");
        stage.setScene(scene);
        stage.show();
    }

    private void renderizar(GraphicsContext gc) {

    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, 800, 800);

    // Atualizar frames
    engine.step(10200); 

    for (ObjetoEspacial c : engine.getObjetos()) {

        // --- DESENHAR RASTRO ---
        gc.setStroke(c.getCor().deriveColor(0, 1, 1, 0.3));        gc.setLineWidth(1);
        
        gc.beginPath();
        boolean primeiro = true;
        for (Vetor3D p : c.getRastro()) {
            double rx = 400 + (p.x() * escala);
            double ry = 400 + (p.y() * escala);
            
            if (primeiro) {
                gc.moveTo(rx, ry);
                primeiro = false;
            } else {
                gc.lineTo(rx, ry);
            }
        }
        gc.stroke();

        double x = 400 + (c.getPosicao().x() * escala);
        double y = 400 + (c.getPosicao().y() * escala);

        gc.setFill(c.getCor());
        double r = c.getRaioVisual();
        gc.fillOval(x - r/2, y - r/2, r, r);
    }
}
}