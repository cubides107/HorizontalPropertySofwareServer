package Models.managerProperties;

public class House implements IDataNodeProperties{
    private int ID;

    public House(int ID) {
        this.ID = ID;
    }

    public House() {
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setId(int ID) {
        this.ID = ID;
    }

}
