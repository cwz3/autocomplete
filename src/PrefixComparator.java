import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
       return new PrefixComparator(prefix);
    }


    @Override
    public int compare(Term v, Term w) {
        String vTerm = v.getWord();
        String wTerm = w.getWord();
        int min = Math.min(vTerm.length(),wTerm.length());
        min = Math.min(myPrefixSize,min);

        for (int i=0;i< min;i++) {
            if (vTerm.charAt(i)-wTerm.charAt(i) !=0){
                return vTerm.charAt(i)-wTerm.charAt(i);
            }
        }
        if (vTerm.length()>=myPrefixSize && wTerm.length()>=myPrefixSize){
            return 0;
        }
        return vTerm.length()-wTerm.length();
            }

    }

