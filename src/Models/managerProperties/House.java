package Models.managerProperties;

public class House implements IDataNodeProperties{
    private int ID;

    public House(int ID) {
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
