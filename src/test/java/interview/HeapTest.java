package interview;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class HeapTest {
    public static void main(String[] args){
        try {
            DataInputStream bis  = new DataInputStream(new FileInputStream("/Users/test/demo.txt"));
            while ( bis.available()>0) {
                System.out.println( bis.readInt());
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
