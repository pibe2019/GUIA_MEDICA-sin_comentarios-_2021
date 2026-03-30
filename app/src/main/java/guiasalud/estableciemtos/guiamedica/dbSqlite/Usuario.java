package guiasalud.estableciemtos.guiamedica.dbSqlite;

public class Usuario {
    private int id;
    private String alias;

    public Usuario() {
    }

    public Usuario(int id, String alias) {
        this.id = id;
        this.alias = alias;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
