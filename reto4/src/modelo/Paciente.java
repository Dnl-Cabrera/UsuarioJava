package modelo;

public class Paciente extends Persona{
    
    private double[] datosMuestras;
    public int[] puntajeMuestras=new int[3];


    public Paciente(String nombre, int cedula, String genero, double[] datosMuestras) {
        super(nombre, cedula, genero);
        this.datosMuestras = datosMuestras;
    }


    public double getDatosParcial(int muestra){
        return datosMuestras[muestra];
    }

    public int getPuntajeParcial(int numeroMuestra){
        int x;
        x=puntajeMuestras[numeroMuestra];
        return x;
    }

}
