package Models.managerProperties;

public class Field implements IDataNodeProperties{

    private int ID;

    public Field(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
       return ID;
    }

    @Override
    public void setId(int id) {

    }
}
