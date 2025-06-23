package clinicaVeterinaria.modelo;
public abstract class Entidade {
    protected int id;

    public Entidade(int id) {
        this.id = id;
    }
    
    public Entidade() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id=" + id;
    }
}
