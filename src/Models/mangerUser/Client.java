package Models.mangerUser;

public class Client implements IDataNode {
    private String nameUser;

    public Client(String nameUser) {
        this.nameUser = nameUser;
    }

    @Override
    public String getName() {
       return nameUser;
    }

    public String getNameUser() {
        return nameUser;
    }
}
