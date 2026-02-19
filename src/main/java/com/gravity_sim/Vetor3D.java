package com.gravity_sim;

public record Vetor3D(double x, double y, double z) {
    public Vetor3D add(Vetor3D v) { return new Vetor3D(x + v.x, y + v.y, z + v.z); }
    public Vetor3D sub(Vetor3D v) { return new Vetor3D(x - v.x, y - v.y, z - v.z); }
    public Vetor3D mult(double n) { return new Vetor3D(x * n, y * n, z * n); }
    public Vetor3D div(double n) { return n != 0 ? new Vetor3D(x / n, y / n, z / n) : this; }

    public double magSq() { return x * x + y * y + z * z; }
    public double mag() { return Math.sqrt(magSq()); }

    public Vetor3D normalize() {
        double m = mag();
        return m > 0 ? this.div(m) : new Vetor3D(0, 0, 0);
    }
}