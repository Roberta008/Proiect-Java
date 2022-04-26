package main.java;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class Utilizator {

    private final String idUtilizator, usernameUtilizator, numeUtilizator, prenumeUtilizator, parolaUtilizator, nrTelefonUtilizator, sexUtilizator, arePermisSauNu, locatieUtilizator, adresaUtilizator, varstaUtilizator;
    private String idMasinaInchiriata;

    public Utilizator(String idUtilizator, String usernameUtilizator, String numeUtilizator, String prenumeUtilizator, String parolaUtilizator, String nrTelefonUtilizator, String sexUtilizator,
                      String arePermisSauNu, String locatieUtilizator, String adresaUtilizator, String varstaUtilizator, String idMasinaInchiriata) {
        this.idUtilizator = idUtilizator;
        this.usernameUtilizator = usernameUtilizator;
        this.numeUtilizator = numeUtilizator;
        this.prenumeUtilizator = prenumeUtilizator;
        this.parolaUtilizator = parolaUtilizator;
        this.nrTelefonUtilizator = nrTelefonUtilizator;
        this.sexUtilizator = sexUtilizator;
        this.arePermisSauNu = arePermisSauNu;
        this.locatieUtilizator = locatieUtilizator;
        this.adresaUtilizator = adresaUtilizator;
        this.varstaUtilizator = varstaUtilizator;
        this.idMasinaInchiriata = idMasinaInchiriata;
    }

    public String getIdUtilizator() {
        return idUtilizator;
    }

    public String getIdMasinaInchiriata() {
        return idMasinaInchiriata;
    }

    public void setIdMasinaInchiriata(String idMasinaInchiriata) {
        this.idMasinaInchiriata = idMasinaInchiriata;
    }

    public String getUsernameUtilizator() {
        return usernameUtilizator;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public String getPrenumeUtilizator() {
        return prenumeUtilizator;
    }

    public String getParolaUtilizator() {
        return parolaUtilizator;
    }

    public String getNrTelefonUtilizator() {
        return nrTelefonUtilizator;
    }

    public String getSexUtilizator() {
        return sexUtilizator;
    }

    public String getArePermisSauNu() {
        return arePermisSauNu;
    }

    public String getLocatieUtilizator() {
        return locatieUtilizator;
    }

    public String getAdresaUtilizator() {
        return adresaUtilizator;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "usernameUtilizator='" + usernameUtilizator + '\'' +
                ", numeUtilizator='" + numeUtilizator + '\'' +
                ", prenumeUtilizator='" + prenumeUtilizator + '\'' +
                ", parolaUtilizator='" + parolaUtilizator + '\'' +
                ", nrTelefonUtilizator='" + nrTelefonUtilizator + '\'' +
                ", sexUtilizator='" + sexUtilizator + '\'' +
                ", arePermisSauNu='" + arePermisSauNu + '\'' +
                ", locatieUtilizator='" + locatieUtilizator + '\'' +
                ", adresaUtilizator='" + adresaUtilizator + '\'' +
                '}';
    }

}
