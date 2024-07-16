package co.wali;

public class Part3 {

    public void testing() {
        System.out.println(twoOccurrences("by", "A story by Abby Long"));
        System.out.println(twoOccurrences("a", "banana"));
        System.out.println(twoOccurrences("atg", "ctgtagta"));
        System.out.println("\nLastPart Method .. ");

        System.out.println(lastPart("an","banana"));
        System.out.println(lastPart("zoo","forest"));
    }


    private boolean twoOccurrences(String stringa, String stringb) {
        int start = stringb.indexOf(stringa);
        int end = stringb.indexOf(stringa, start + (stringa.length()));
        if (end != -1) {
            return true;
        } else {
            return false;
        }
    }

    private String lastPart(String stra, String strb) {

        int start = strb.indexOf(stra);
        int end = strb.length();

        if (strb.indexOf(stra) != -1){
            return strb.substring(start+(stra.length()), end);
        }else {
            return strb;
        }
    }

}
