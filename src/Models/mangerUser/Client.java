package Models.mangerUser;

public class Client implements IDataNode {
    private String nameUser;

    public Client(String nameUser) {
        this.nameUser = nameUser;
    }

    public Client() {
    }

    @Override
    public String getName() {
       return nameUser;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public void setName(String email) {
        nameUser = email;
    }

    public String getNameUser() {
        return nameUser;
    }
}
