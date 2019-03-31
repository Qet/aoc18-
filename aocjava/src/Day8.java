import InputReader.InputReader;

import java.util.ArrayList;

class Node{
    public Node(){
        children = new ArrayList<>();
        metadata = new ArrayList<>();
    }
    public ArrayList<Node> children;
    public ArrayList<Integer> metadata;

    public int metadataSum(){
        return metadata.stream().mapToInt(a -> a).sum();
    }

    public boolean hasMetadata(){
        return metadata.size() > 0;
    }

    public boolean hasChildren(){
        return children.size() > 0;
    }

    public int value(){
        if (hasChildren()){
            int sum = 0;
            for (int i : metadata){
                int index = i - 1;
                if (index >= 0 && index < children.size()){
                    sum += children.get(index).value();
                }
            }
            return sum;
        }

        return metadataSum();
    }

}

public class Day8 {
    public static void main(String[] args) {
        InputReader ir = new InputReader(8);
        ArrayList<Integer> data = readData(ir);

        Node rootNode = new Node();
        createNodes(rootNode, data, 0);

        System.out.println("[Part 1]: metadata sum = " + sumMetadata(rootNode, 0));
        System.out.println("[Part 1]: metadata sum = " + sumMetadata2(rootNode));
        System.out.println("[Part 1]: metadata sum = " + sumMetadata3(rootNode));

        System.out.println("[Part 2]: root node value = " + rootNode.value());

    }



    public static int sumMetadata(Node parentNode, int currentSum){
        currentSum +=  parentNode.metadataSum();
        for(Node n : parentNode.children){
            currentSum = sumMetadata(n, currentSum);
        }
        return currentSum;
    }

    public static int sumMetadata2(Node parentNode){

        int ret = 0;

        ret += parentNode.metadataSum();
        for(Node n : parentNode.children){
            ret += sumMetadata2(n);
        }
        return ret;
    }

    public static int sumMetadata3(Node parentNode){
        return parentNode.metadataSum() + parentNode.children.stream().mapToInt(node -> sumMetadata3(node)).sum();
    }


    public static int createNodes(Node parentNode, ArrayList<Integer> data, int dataOffset){
        int numChildren = data.get(dataOffset++);
        int numMetadata = data.get(dataOffset++);

        System.out.println("num children: " + numChildren + ". num metadata: " + numMetadata);

        for (int i = 0; i < numChildren; i++) {
            Node childNode = new Node();
            parentNode.children.add(childNode);
            dataOffset = createNodes(childNode, data, dataOffset);
        }

        System.out.print("Metadata: ");

        for (int endValue = dataOffset + numMetadata; dataOffset < endValue; dataOffset++) {
            int metadata = data.get(dataOffset);
            System.out.print(metadata + ", ");
            parentNode.metadata.add(metadata);
        }
        System.out.println("");
        return dataOffset;
    }

    public static ArrayList<Integer> readData(InputReader ir){
        String line = ir.getLines().get(0); //There's just one line of data for this one.
        String[] elements = line.split(" ");
        ArrayList<Integer> ret = new ArrayList<>();
        for (int i = 0; i < elements.length; i++) {
            ret.add(Integer.parseInt(elements[i]));
        }
        return ret;
    }
}
