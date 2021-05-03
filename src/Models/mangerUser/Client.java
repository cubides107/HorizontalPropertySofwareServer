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

    @Override
    public int getId() {
        return 0;
    }

    public String getNameUser() {
        return nameUser;
    }
}
