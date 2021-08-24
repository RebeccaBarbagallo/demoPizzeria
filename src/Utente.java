import java.io.Serializable;

public class Utente implements Serializable {

    private String CF;
    private String Nome;
    private String Cognome;
    private String Mansione;

    public Utente(String CF, String nome, String cognome, String mansione ){
        this.CF = CF;
        Nome = nome;
        Cognome = cognome;
        Mansione = mansione;
    }

    public String getCF() {
        return CF;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public String getMansione() {
        return Mansione;
    }

    public void setMansione(String mansione) {
        Mansione = mansione;
    }

    @Override
    public String toString() {
        return "Utente (" + "CF:" + CF + ", Nome:" + Nome +
                ", Cognome:" + Cognome + ", Mansione:"
                + Mansione + ")";
    }
}
