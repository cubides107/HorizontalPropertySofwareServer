package Models.managerProperties;

public class Pool implements IDataNodeProperties{

    private int ID;

    public Pool(int ID) {
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
