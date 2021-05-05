package Models.managerProperties;

public class CommonRoom implements IDataNodeProperties{


    private int ID;

    public CommonRoom(int ID) {
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
