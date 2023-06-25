// Java program to demonstrate the removing
// of the elements from the stack
import java.util.*;
import java.io.*;
 
public class StackDemo {
    public static void main(String args[])
    {
        // Creating an empty Stack
        Stack<Integer> stack = new Stack<Integer>();
 
        // Use add() method to add elements
        stack.push(10);
        stack.push(15);
        stack.push(30);
        stack.push(20);
        stack.push(5);
 
        // Displaying the Stack
        System.out.println("Initial Stack: " + stack);
 
        // Removing elements using pop() method
        System.out.println("Popped element: "
                           + stack.pop());
        System.out.println("Popped element: "
                           + stack.pop());
 
        // Displaying the Stack after pop operation
        System.out.println("Stack after pop operation "
                           + stack);


        Stack<HashMap<String, Integer>> cookiePile = new Stack<HashMap<String, Integer>>();

        HashMap<String, Integer> newCookie = new HashMap<String, Integer>();
        newCookie.put("sausage", 99);

        cookiePile.push(newCookie);

        HashMap<String,Integer> peeked = cookiePile.peek();

        System.out.println(peeked.values().toString());

        int peekedAmount = 0;
            for(int i: peeked.values()){
                peekedAmount = i;
            }
        System.out.println(peekedAmount);

        System.out.println("");

        /* int thisInt = newCookie.get("bro");
        if(thisInt.equals(null)){
            System.out.println("null");
        }else{System.out.println(i);} */

        if(Objects.isNull(newCookie.get("bro"))){
            System.out.println("null");
        }else{System.out.println("aman");}
    }
}