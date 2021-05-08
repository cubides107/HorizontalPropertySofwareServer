package Models.managerProperties;

public class CommonRoom implements IDataNodeProperties{


    private int ID;

    public CommonRoom(int ID) {
        this.ID = ID;
    }

    public CommonRoom() {
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
