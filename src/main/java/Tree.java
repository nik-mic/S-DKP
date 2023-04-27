import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class Tree {

    List<Integer> treeLevels = new ArrayList<>();
    List<String> treeContent = new ArrayList<>();
    private String getFlatline(List<Object> parsetree, String content) {
        String flatline = content;
        for (Object o : parsetree) {
            if (o instanceof List) {
                flatline = getFlatline((List<Object>)o, flatline);
            } else {
                flatline = flatline + o;
            }
        }
        return flatline;
    }

    public int getTreeFormat(List<Object> parsetree, int max){
        int maxCurrent = max;
        if(parsetree.size() >= maxCurrent){
            maxCurrent = parsetree.size();
        }
        for (Object o : parsetree) {
            if (o instanceof List) {
                treeLevels.add(((List<Object>)o).size());
                getTreeFormat((List<Object>)o, maxCurrent);
            } else {
                treeLevels.add(0);
                treeContent.add(o.toString());
            }
        }
        return (maxCurrent+"").length()-1;
    }

    public String displayTree(List<Object> t){
        return t.toString();
    }

    public String getStump(List<Object> parsetree){
        String initTree = "1";
        String treeNumber = "";
        String key = getKey(getFlatline(parsetree, ""), 1, "a");
        int treeFormat = getTreeFormat(parsetree, 0);
        for(int i = 0; i < treeFormat; i++){
            initTree = "0" + initTree;
        }
        for(Integer x : treeLevels) {
            String current = "" + x;
            while(current.length() < initTree.length()){
                current = "0" + current;
            } treeNumber = treeNumber + current;
        }

        String content = "";
        for(String s : treeContent){
            content = content + key + s;
        }

        treeNumber = treeNumber.replaceAll("0+$", "");
        return "a" + key + "a" + initTree + treeNumber + "a" + content;
    }

    private void iterate(int[] index) {
        index[0]++;
        IntStream.range(0, index.length)
                .filter(i -> index[i] >= Values.D.length)
                .filter(i -> i + 1 < index.length)
                .forEach(i -> {
                    index[i + 1]++;
                    index[i] = 0;
                });
    }

    private String generateFromBytes(int[] s) {
        StringBuilder g = new StringBuilder();
        Arrays.stream(s).forEach(v -> {
            char c = (char) (v + 97);
            g.append(c);
        });
        return g.toString();
    }

    private String getKey(String s, int length, String id) {
        String keyword;
        int[] index = new int[length];
        iterate(index);
        keyword = generateFromBytes(index);
        while (s.contains(keyword) || keyword.contains(id)) {
            keyword = generateFromBytes(index);
            iterate(index);
        }
        return keyword;
    }

    public List<Object> parse(String urbild){
        char id = urbild.charAt(0);
        String[] parts = urbild.split(id+"");
        String key = parts[0];
        String tree = parts[1];
        String content = parts[2];

        String[] contentParts = content.split(key);
        return generateTree(tree);
    }

    public List<Object> generateTree(String number){
        String format = "";
        for(int i = 0; i < number.length(); i++){
            format = format + format.charAt(i);
            if(format.charAt(i) == '1'){
                break;
            }
        }
        int formatIndex = 0;
        while (format.charAt(formatIndex)=='1'){
            formatIndex++;
        }

        List<Object> tree = new ArrayList<>();
        tree.add(new ArrayList<>());
        for(int i = formatIndex; i < number.length(); i = i + formatIndex){
            String sub = number.substring(i, i+formatIndex);
            Integer numberOfNodes = Integer.parseInt(sub);
            List<Object> toAdd = (List<Object>) tree.get(0);
            for(int x = 0; x < numberOfNodes; x++){
                toAdd.add(new ArrayList<>());
            }
        }
        return buildStructure(new Stack<Integer>(), new ArrayList<Object>(), 0);
    }

    public List<Object> buildStructure(Stack<Integer> numbers, List<Object> tree, int counter){
        if(numbers.empty()) return tree;
        int toAdd = numbers.peek();
        List<Object> currentNode = (List<Object>) tree.get(counter);
        for(int i = 0; i < toAdd; i++){
            tree.add(new ArrayList<>());
        }
        if(counter >= currentNode.size()){
            int i = 0;
            for(Object o : currentNode){
                i++;
                if(((List<Object>)o).size()>0){
                    buildStructure(numbers, (List<Object>) o, i);
                }
            }
        }
        return tree;
    }
}
