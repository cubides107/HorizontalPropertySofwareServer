package Models.managerProperties;

import java.util.ArrayList;
import java.util.Iterator;

public class NodeProperties {

    private ArrayList<NodeProperties> childList;
    private NodeProperties father;
    private IDataNodeProperties data;
    private int id;

    public NodeProperties(int id, IDataNodeProperties data) {
        this.id = id;
        this.data = data;
        childList = new ArrayList<>();
    }

    public NodeProperties(int id) {
        this.id = id;
    }

    public NodeProperties(IDataNodeProperties data) {
        this.data = data;
    }

    public NodeProperties() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void add(NodeProperties newChild){
        childList.add(newChild);
        newChild.setFather(this);
    }

    public void remove(NodeProperties child){
        Iterator<NodeProperties> iterator = getChildList().iterator();
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

    public NodeProperties getFirstChild(){
        return childList.get(0);
    }

    public NodeProperties getLastsChild(){
        return childList.get(childList.size() - 1);
    }

    public boolean isEmpty(){
        return childList.size() == 0;
    }

    public ArrayList<NodeProperties> getChildList() {
        return childList;
    }

    public NodeProperties getFather() {
        return father;
    }

    public IDataNodeProperties getData() {
        return data;
    }

    public void setFather(NodeProperties father) {
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
        return "{" + "id=" + id + "-" +"dataId: "+data.getID() + "}";
    }
}
