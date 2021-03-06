package Models.mangerUser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class TreeUsers {

    private NodeUser root;

    public TreeUsers(){
    }

    public void setRoot(NodeUser root) {
        this.root = root;
    }

    public boolean add(int idFather, NodeUser newNode){
        NodeUser father = search(idFather);
        if(father != null){
            father.add(newNode);
            return true;
        }
        return false;
    }

    public boolean checkExistUser(String nameUser){
        if(root != null) {
            ArrayList<NodeUser> childListRoot = root.getChildList();
            for (NodeUser node : childListRoot) {
                if (node.getData().getName().equals(nameUser)) {
                    return true;
                }
            }
        }
        return false;
    }



    public NodeUser search(int id){
        return search(id, root);
    }

    private NodeUser search(int id, NodeUser actual) {
        if(actual.getId() == id){
           return actual;
        }
        for (NodeUser child : actual.getChildList()) {
            NodeUser result = search(id, child);
            if(result != null){
                return result;
            }
        }
        return null;
    }

    public NodeUser searchByNameUser(String nameUser){
        return searchByNameUser(nameUser, root);
    }

    private NodeUser searchByNameUser(String nameUser, NodeUser actual) {
        if(actual.getData().getName().equals(nameUser)){
            return actual;
        }
        for (NodeUser child : actual.getChildList()) {
            NodeUser result = searchByNameUser(nameUser, child);
            if(result != null){
                return result;
            }
        }
        return null;
    }

    public void print() {
        print(root, "");
    }

    private void print(NodeUser actual, String space) {
        System.out.println(space + actual);
        space += "  ";
        for (NodeUser child : actual.getChildList()) {
            print(child, space);
        }
    }

    public void remove(int id){
        remove(id, root);
    }

    private boolean remove(int id, NodeUser actual) {
        if(actual.getId() == id){
            actual.getFather().remove(actual);
            return true;
        }
        Iterator<NodeUser> iterator = actual.getChildList().iterator();
        while (iterator.hasNext()){
            if(remove(id, iterator.next())){
                return true;
            }
        }
        return false;
    }

    public void removeByIdData(int id){
        removeByIdData(id, root);
    }

    private boolean removeByIdData(int id, NodeUser actual) {
        if(actual.getData().getId() == id){
            actual.getFather().removeByIdData(actual);
            return true;
        }
        Iterator<NodeUser> iterator = actual.getChildList().iterator();
        while (iterator.hasNext()){
            if(removeByIdData(id, iterator.next())){
                return true;
            }
        }
        return false;
    }

    public void printBreadth(){
        Queue<NodeUser> queue = new LinkedList<>();
        queue.add(root);
        printBreadth(root, queue);
        while (!queue.isEmpty()){
            System.out.println(queue.poll().getData().getName());
        }
    }




    public File fileWriteToUser(String nameUser){
        return  null;
    }

    private void printBreadth(NodeUser base, Queue<NodeUser> queue) {
        for (NodeUser child : base.getChildList()) {
            queue.add(child);
        }
        for (NodeUser child : base.getChildList()) {
            printBreadth(child, queue);
        }
    }

    public NodeUser getRoot(){
        return root;
    }

    public void deletePropertyToUser(int idProperty) {
        removeByIdData(idProperty);
    }
}
