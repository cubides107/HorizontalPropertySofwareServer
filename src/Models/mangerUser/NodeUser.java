package Models.mangerUser;

import java.util.ArrayList;
import java.util.Iterator;

public class NodeUser {

    private ArrayList<NodeUser> childList;
    private NodeUser father;
    private IDataNode data;
    private int id;

    public NodeUser(int id, IDataNode data) {
        this.id = id;
        this.data = data;
        childList = new ArrayList<>();
    }

    public void add(NodeUser newChild){
        childList.add(newChild);
        newChild.setFather(this);
    }

    public void remove(NodeUser child){
        Iterator<NodeUser> iterator = getChildList().iterator();
        while (iterator.hasNext()){
            if(iterator.next().getId() == child.getId()){
                iterator.remove();
                return;
            }
        }
    }

    public int getId() {
        return id;
    }

    public NodeUser getFirstChild(){
        return childList.get(0);
    }

    public NodeUser getLastsChild(){
        return childList.get(childList.size() - 1);
    }

    public boolean isEmpty(){
        return childList.size() == 0;
    }

    public ArrayList<NodeUser> getChildList() {
        return childList;
    }

    public NodeUser getFather() {
        return father;
    }

    public IDataNode getData() {
        return data;
    }

    public void setFather(NodeUser father) {
        this.father = father;
    }

    /*public int calculateSizeRecursive(){
        AtomicInteger total = new AtomicInteger();
        total.addAndGet(data.getName());
        calculateSizeRecursive(this, total);
        return total.get();
    }*/

/*    private void calculateSizeRecursive(Node actual, AtomicInteger total) {
        for (Node node : actual.getChildList()) {
            total.addAndGet(node.getData().getName());
            calculateSizeRecursive(node, total);
        }
    }

    public int calculateSize(){
        return calculateSize(this) + getData().getName();
    }*/

    /*private int calculateSize(Node actual) {
        int size = 0;
        for (Node node : actual.getChildList()) {
            size += calculateSize(node) + node.getData().getName();
        }
        return size;
    }*/

    @Override
    public String toString() {
        return "{" + "id=" + id + "-" + "}";
    }
}
