package Models;

import Models.managerProperties.IDataNodeProperties;

public class HorizontalProperty implements IDataNodeProperties {

    private String name;
    private String ID;

    public HorizontalProperty() {

    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }
}
