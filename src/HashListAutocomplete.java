import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }

        initialize(terms,weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        List<Term> list = new ArrayList<>();
        if (myMap.containsKey(prefix)){
            List<Term> all = myMap.get(prefix);
            list = all.subList(0,Math.min(k,all.size()));

        }
        return list;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {

        myMap = new HashMap<String, List<Term>>();
        for (int k = 0; k<terms.length;k++){
            for (int i = 0;i<=MAX_PREFIX;i++) {
                if (terms[k].length()>=i){
                    String prefix = terms[k].substring(0,i);
                    myMap.putIfAbsent(prefix, new ArrayList<>());
                    myMap.get(prefix).add(new Term(terms[k], weights[k]));
                }


            }
        }
        if (!myMap.isEmpty()) {
            for (String key : myMap.keySet()) {
                Collections.sort(myMap.get(key), Comparator.comparing(Term::getWeight).reversed());
            }
        }
    }


    @Override
    public int sizeInBytes() {
        if (mySize == 0) {

            for(String key : myMap.keySet()) {
                mySize += BYTES_PER_CHAR*key.length();
                for (Term t:myMap.get(key)){
                    mySize+= BYTES_PER_DOUBLE+t.getWord().length()*BYTES_PER_CHAR;


                }

            }
        }
        return mySize;
    }


}
