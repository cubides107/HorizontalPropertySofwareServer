package Models.managerProperties;

import java.util.ArrayList;
import java.util.Iterator;

public class NodePropeties {

    private ArrayList<NodePropeties> childList;
    private NodePropeties father;
    private IDataNodeProperties data;
    private int id;

    public NodePropeties(int id, IDataNodeProperties data) {
        this.id = id;
        this.data = data;
        childList = new ArrayList<>();
    }

    public void add(NodePropeties newChild){
        childList.add(newChild);
        newChild.setFather(this);
    }

    public void remove(NodePropeties child){
        Iterator<NodePropeties> iterator = getChildList().iterator();
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

    public NodePropeties getFirstChild(){
        return childList.get(0);
    }

    public NodePropeties getLastsChild(){
        return childList.get(childList.size() - 1);
    }

    public boolean isEmpty(){
        return childList.size() == 0;
    }

    public ArrayList<NodePropeties> getChildList() {
        return childList;
    }

    public NodePropeties getFather() {
        return father;
    }

    public IDataNodeProperties getData() {
        return data;
    }

    public void setFather(NodePropeties father) {
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
