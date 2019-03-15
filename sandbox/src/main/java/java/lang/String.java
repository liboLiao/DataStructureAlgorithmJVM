package java.lang;

/**
 * Created by llb on 2018/10/9.
 */
public class String {

    public String toString(){
        Integer integer = new Integer(10);
        return "xxx:"+integer;
    }

    public static void main(String[] args){
        String s = new String();
//        System.out.println(s.getSameNameSpaceClass());
        System.out.println(s.toString());
    }

}
