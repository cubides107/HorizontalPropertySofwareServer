package Models.mangerUser;

public class PropertyNodeUser implements IDataNode{

    private int id;
    private String name;

    public PropertyNodeUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
       return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String email) {

    }
}
