import java.io.Serializable;

public class Piatto implements Serializable {

    private String nome;
    private int prezzo;

    public Piatto(String nome, int prezzo){
        setNome(nome);
        this.setPrezzo(prezzo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Piatto ordinato (" +
                "Nome:" + nome +
                "" + ", Prezzo: " + prezzo + ")";
    }
}
