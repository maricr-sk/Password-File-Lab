import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class e {
    static File file = new File("input.txt");
    public static boolean login(String user, String pass){
        try{
            Scanner scan = new Scanner(file);
            String usen = "";
            String[] answer = new String[6];
            while(scan.hasNextLine()) {
                String hold = scan.nextLine();
                answer = hold.split(":");
                if (hold.split(":")[2].equalsIgnoreCase(user)) {
                    usen = answer[2];
                    answer = hold.split(":");
                }
            }
            String p = reHash(pass, answer[4]);
            if(!usen.equals("")){
                if(p.equals(answer[3])){
                    return true;
                }
            }
        }
        catch(Exception e){
            System.out.println("File not found in the check method");
        }
        return false;
    }
    public static String reHash(String password, String SLT){ return MD5(password + SLT).substring(0,12); }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm");
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(login("m", "m"));
        //de978c87edba

        /*Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = scan.next();

        scan= new Scanner(System.in);
        System.out.println("Please enter your password: ");
        String password = scan.next();

        if (login(username, password)) {
            System.out.println("You have successfully logged in!");
        }
        else {
            System.out.println("You have entered either an invalid username or password. Please try again.");
        }
         */

        Scanner scan = new Scanner(file);
        Scanner scann = new Scanner(System.in);

        System.out.println("Menu: \n1. Login ");
        System.out.println("Please enter a single number of your choice: ");
        int input = scann.nextInt();

        PrintWriter pw = new PrintWriter(file);
        pw.append("last:first:userID:pwd:Salt:algorithm");
        boolean check = false;

        do {
            switch (input) {
                case 1:
                    //login

                    Scanner hold = new Scanner(System.in);
                    System.out.println("Please enter your username: ");
                    String username = hold.nextLine();

                    hold = new Scanner(System.in);
                    System.out.println("Please enter your password: ");
                    String password = hold.nextLine();
                    if (login(username, password)) {
                        System.out.println("You have successfully logged in!");
                    } else if(!login(username, password)) {
                        System.out.println("You have entered either an invalid username or password. Please try again.");
                    }
                    check = true;
                    break;

                default:
                    System.out.println("Please enter a valid number from the menu: ");
                    check = true;
                    break;
            }
        } while (!check) ;
        pw.close();
    }

}
