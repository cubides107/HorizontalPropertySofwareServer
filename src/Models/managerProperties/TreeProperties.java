package Models.managerProperties;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class TreeProperties {

    private NodeProperties root;

    public TreeProperties(){
    }

    public void setRoot(NodeProperties root) {
        this.root = root;
    }

    public boolean add(int idFather, NodeProperties newNode){
        NodeProperties father = search(idFather);
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



    public NodeProperties search(int id){
        return search(id, root);
    }

    private NodeProperties search(int id, NodeProperties actual) {
        if(actual.getId() == id){
           return actual;
        }
        for (NodeProperties child : actual.getChildList()) {
            NodeProperties result = search(id, child);
            if(result != null){
                return result;
            }
        }
        return null;
    }

    public void print() {
        print(root, "");
    }

    private void print(NodeProperties actual, String space) {
        System.out.println(space + actual);
        space += "  ";
        for (NodeProperties child : actual.getChildList()) {
            print(child, space);
        }
    }

    public void remove(int id){
        remove(id, root);
    }

    private boolean remove(int id, NodeProperties actual) {
        if(actual.getId() == id){
            actual.getFather().remove(actual);
            return true;
        }
        Iterator<NodeProperties> iterator = actual.getChildList().iterator();
        while (iterator.hasNext()){
            if(remove(id, iterator.next())){
                return true;
            }
        }
        return false;
    }

    public void printBreadth(){
        Queue<NodeProperties> queue = new LinkedList<>();
        queue.add(root);
        printBreadth(root, queue);
        while (!queue.isEmpty()){
            System.out.println(queue.poll().getData().getID());
        }
    }

    private void printBreadth(NodeProperties base, Queue<NodeProperties> queue) {
        for (NodeProperties child : base.getChildList()) {
            queue.add(child);
        }
        for (NodeProperties child : base.getChildList()) {
            printBreadth(child, queue);
        }
    }

    public NodeProperties getRoot(){
        return root;
    }

}
