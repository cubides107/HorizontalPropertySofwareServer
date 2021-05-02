package Models.managerProperties;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class TreeProperties {

    private NodePropeties root;

    public TreeProperties(){
    }

    public void setRoot(NodePropeties root) {
        this.root = root;
    }

    public boolean add(int idFather, NodePropeties newNode){
        NodePropeties father = search(idFather);
        if(father != null){
            father.add(newNode);
            return true;
        }
        return false;
    }

//    public boolean checkExistUser(String nameUser){
//        ArrayList<Node> childListRoot = root.getChildList();
//        for (Node node : childListRoot) {
//            if(node.getData().getName().equals(nameUser)){
//                return  true;
//            }
//        }
//        return false;
//    }



    public NodePropeties search(int id){
        return search(id, root);
    }

    private NodePropeties search(int id, NodePropeties actual) {
        if(actual.getId() == id){
           return actual;
        }
        for (NodePropeties child : actual.getChildList()) {
            NodePropeties result = search(id, child);
            if(result != null){
                return result;
            }
        }
        return null;
    }

    public void print() {
        print(root, "");
    }

    private void print(NodePropeties actual, String space) {
        System.out.println(space + actual);
        space += "  ";
        for (NodePropeties child : actual.getChildList()) {
            print(child, space);
        }
    }

    public void remove(int id){
        remove(id, root);
    }

    private boolean remove(int id, NodePropeties actual) {
        if(actual.getId() == id){
            actual.getFather().remove(actual);
            return true;
        }
        Iterator<NodePropeties> iterator = actual.getChildList().iterator();
        while (iterator.hasNext()){
            if(remove(id, iterator.next())){
                return true;
            }
        }
        return false;
    }

    public void printBreadth(){
        Queue<NodePropeties> queue = new LinkedList<>();
        queue.add(root);
        printBreadth(root, queue);
        while (!queue.isEmpty()){
            System.out.println(queue.poll().getData().getID());
        }
    }

    private void printBreadth(NodePropeties base, Queue<NodePropeties> queue) {
        for (NodePropeties child : base.getChildList()) {
            queue.add(child);
        }
        for (NodePropeties child : base.getChildList()) {
            printBreadth(child, queue);
        }
    }

    public NodePropeties getRoot(){
        return root;
    }

}
