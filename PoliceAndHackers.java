import java.io.*;
import java.util.*;

public class PoliceAndHackers {
    

    private static void io() {
        try {
            System.setIn(new FileInputStream("input.txt"));
            System.setOut(new PrintStream(new FileOutputStream("output.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private final static int MAX_PASSWORD = 999;

    private static class Vault{
        private String password;
        public Vault(String password){
            this.password = password;
        }
        public boolean verify(String password){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this.password.equals(password);
        }
    }
    
    private static abstract class Hacker extends Thread{
        protected Vault vault;
        public Hacker(Vault vault){
            this.vault = vault;
            this.setName(this.getClass().getName());
            this.setPriority(MAX_PRIORITY);
        }

        @Override
        public void start(){
            System.out.println("thread "+ this.getClass().getName() + " is running");
            super.start();
        }
    } 
    private static class FirstHacker extends Hacker{

        public FirstHacker(Vault vault) {
            super(vault);
        }

        @Override
        public void run(){
            for(int i = 0; i <= MAX_PASSWORD; i++){
                if(vault.verify(Integer.toString(i))){
                    System.out.println(this.getClass().toString()+ " "+" guessed your password " + i);
                    System.exit(0);
                }
            }
        }
    }

    private static class SecondHacker extends Hacker{

        public SecondHacker(Vault vault) {
            super(vault);
        }

        @Override
        public void run(){
            for(int i = MAX_PASSWORD; i >= 0; i--){
                if(vault.verify(Integer.toString(i))){
                    System.out.println(this.getName().toString()+" guessed your password " + i);
                    System.exit(0);
                }
            }
        }
    }

    private static class Police extends Thread{
        public Police(){
            this.setName(this.getClass().getName().toString());
        }
        @Override
        public void start(){
            System.out.println("thread "+ this.getName() + " is running");
            super.start();
        }

        @Override
        public void run(){
            for(int i = 10; i > 0; i--){
                System.out.println("remaining time is " + i);
                try {
                    this.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("BUSTED");
            System.exit(0);
        }
    }
    public static void main(String[] args) {
        io();
        Random random = new Random();
        Vault vault = new Vault(Integer.toString(random.nextInt(MAX_PASSWORD)));
        List<Thread>threads = new ArrayList<>();
        threads.add(new FirstHacker(vault));
        threads.add(new SecondHacker(vault));
        threads.add(new Police());
        for(Thread t : threads) t.start();
    }
}
 
