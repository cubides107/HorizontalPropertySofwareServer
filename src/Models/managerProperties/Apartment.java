package Models.managerProperties;

public class Apartment implements IDataNodeProperties{
    private int ID;

    public Apartment(int ID) {
        this.ID = ID;
    }

    public Apartment() {
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setId(int id) {
        this.ID = ID;
    }

}
