package Models.managerProperties;

public class Building implements IDataNodeProperties {

    private int ID;

    public Building(int ID) {
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
