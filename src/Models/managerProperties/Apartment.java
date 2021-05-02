package Models.managerProperties;

public class Apartment implements IDataNodeProperties{
    private int ID;

    public Apartment(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String getName() {
        return null;
    }
}
