package Models.Services;

import Models.managerProperties.IDataNodeProperties;

public class InternetService implements IDataNodeProperties {


    private int ID;

    public InternetService(int ID) {
        this.ID = ID;
    }

    public InternetService() {

    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setId(int id) {

    }
}
