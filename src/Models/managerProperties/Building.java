package Models.managerProperties;

public class Building implements IDataNodeProperties {

    private int ID;

    public Building(int ID) {
        this.ID = ID;
    }

    public Building() {
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
