package cn8;

import static java.lang.Thread.sleep;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class demo {

    volatile LinkedList<Integer> bucket = new LinkedList<>();
    volatile int bucketsize = 0;
    volatile int token = 0;

    public void incoming(int a) {
        if (bucketsize + a < 50) {
            bucketsize += a;
            bucket.addLast(a);
            System.out.println("Bucketsize " + bucketsize + bucket);
        } else {
            System.out.println(a + "bytes of data lost");
        }
    }

    public void outgoing() {
        int max = 10, total = 0;
        try {
            while (total + bucket.peek() <= max) {
                if (token >= 1) {
                    total += bucket.peek();
                    bucketsize -= bucket.peek();
                    token--;
                    System.out.println("Outging " + bucket.poll());
                }
                
            }
        } catch (Exception e) {
            System.out.println("Bucket is empty");
        }

    }

    public void tokengenerator() throws InterruptedException {
        token++;
        System.out.println("Token no "+token);
    }

    public static void main(String[] args) {
        demo d = new demo();
        Thread in = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 15; i++) {
                    d.incoming((int) (Math.random() * 11));
                    try {
                        sleep((int) (Math.random() * 700));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(demo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        Thread out = new Thread(new Runnable() {
            public void run() {
                while (d.bucketsize >= 0) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(demo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    d.outgoing();
                }
            }
        });
        Thread token = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        d.tokengenerator();
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(demo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        in.start();
        out.start();
        token.start();
    }
}
