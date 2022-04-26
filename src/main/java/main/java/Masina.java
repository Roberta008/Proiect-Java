package main.java;

public class Masina {

    private String idMasina, marcaMasina, modelMasina, anMasina, esteInchiriata, pathImagineMasina;

    public Masina(String idMasina, String marcaMasina, String modelMasina, String anMasina, String esteInchiriata, String pathImagineMasina) {
        this.idMasina = idMasina;
        this.marcaMasina = marcaMasina;
        this.modelMasina = modelMasina;
        this.anMasina = anMasina;
        this.esteInchiriata = esteInchiriata;
        this.pathImagineMasina = pathImagineMasina;
    }

    @Override
    public String toString() {
        return marcaMasina + ", modelul " + modelMasina + " din anul " + anMasina;
    }

    public String getIdMasina() {
        return idMasina;
    }

    public void setIdMasina(String idMasina) {
        this.idMasina = idMasina;
    }

    public String getPathImagineMasina() {
        return pathImagineMasina;
    }

    public void setPathImagineMasina(String pathImagineMasina) {
        this.pathImagineMasina = pathImagineMasina;
    }

    public String getMarcaMasina() {
        return marcaMasina;
    }

    public void setMarcaMasina(String marcaMasina) {
        this.marcaMasina = marcaMasina;
    }

    public String getModelMasina() {
        return modelMasina;
    }

    public void setModelMasina(String modelMasina) {
        this.modelMasina = modelMasina;
    }

    public String getAnMasina() {
        return anMasina;
    }

    public void setAnMasina(String anMasina) {
        this.anMasina = anMasina;
    }

    public String getEsteInchiriata() {
        return esteInchiriata;
    }

    public void setEsteInchiriata(String esteInchiriata) {
        this.esteInchiriata = esteInchiriata;
    }

}
