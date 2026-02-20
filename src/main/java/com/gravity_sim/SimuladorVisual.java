package com.gravity_sim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SimuladorVisual extends Application {
    private Engine engine = new Engine();
    private double escala = 4e-10;
    private String modoAtual = "LASER"; 

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Group root = new Group(canvas);
        Scene scene = new Scene(root, 800, 800, Color.BLACK);


    scene.setOnKeyPressed(e -> {
        switch (e.getCode()) {
            case DIGIT1 -> modoAtual = "LASER";
            case DIGIT2 -> modoAtual = "PLANETA";
            case DIGIT3 -> modoAtual = "ESTRELA";
            case DIGIT4 -> modoAtual = "BURACO_NEGRO";
        }
        System.out.println("Modo de criação: " + modoAtual);
    });

    scene.setOnMouseClicked(event -> {
        double uX = (event.getX() - 400) / escala;
        double uY = (event.getY() - 400) / escala;
        Vetor3D pos = new Vetor3D(uX, uY, 0);

        switch (modoAtual) {
            case "LASER" -> {
                engine.adicionarObjeto(new RaioDeLuz("Laser", pos, new Vetor3D(1, 0, 0), Color.RED));
            }
            case "PLANETA" -> {
                // Velocidade orbital padrão para o lado
                Vetor3D vel = new Vetor3D(0, 30000, 0); 
                engine.adicionarObjeto(new CorpoCeleste("Planeta", 6e24, pos, vel, Color.LIME, 6));
            }
            case "ESTRELA" -> {
                engine.adicionarObjeto(new CorpoCeleste("Sol", 2e30, pos, new Vetor3D(0,0,0), Color.GOLD, 20));
            }
            case "BURACO_NEGRO" -> {
                // Massa 1000x maior que o Sol para curvar a luz violentamente
                engine.adicionarObjeto(new CorpoCeleste("Singularidade", 2e33, pos, new Vetor3D(0,0,0), Color.PURPLE, 10));
            }
        }
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
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 16));
        gc.fillText("MODO: " + modoAtual + " | (1) Laser (2) Planeta (3) Estrela (4) B.Negro", 10, 25);
    }
}