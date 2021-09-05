import java.io.Serializable;

public class Piatto implements Serializable {

    private String Nome;
    private int prezzo;

    public Piatto(String nome, int prezzo){
        setNome(nome);
        this.setPrezzo(prezzo);
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Piatto {" +
                "Nome:" + Nome +
                ", prezzo: " + prezzo +
                '}';
    }
}
