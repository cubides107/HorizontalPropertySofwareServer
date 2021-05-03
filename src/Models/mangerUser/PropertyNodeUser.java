package Models.mangerUser;

public class PropertyNodeUser implements IDataNode{

    private int id;

    public PropertyNodeUser(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getId() {
       return id;
    }
}
