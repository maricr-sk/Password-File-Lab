import java.io.*;
import java.util.*;

public class Menu {
    // last:first:userID:Hashedpwd:Salt:algorithm
    static File file;
    static String username = "";

    public static void menu() throws IOException {
        file = new File("input.txt");
        file.delete();
        file = new File("input.txt");
        FileWriter fw = new FileWriter(file, true);
        fw.append("last:first:userID:pwd:Salt:algorithm");
        fw.close();
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm");
        }
        return null;
    }

    public static String reHash(String password, String SLT) {
        return MD5(password + SLT).substring(0, 12);
    }

    public static boolean login() throws Exception {
        Scanner scan = new Scanner(file);
        Scanner scann = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String user = scann.nextLine();
        username = user;
        System.out.println("Please enter your password: ");
        String pass = scann.nextLine();

        while (scan.hasNextLine()) {
            String hold = scan.nextLine();
            String[] answer = hold.split(":");
            String usen = answer[2];
            if (usen.equalsIgnoreCase(user)) {
                String p = reHash(pass, answer[4]);
                if (p.equals(answer[3])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void checker() throws Exception{
        if(login()){
            System.out.println("You have successfully logged in!");
        }
        else{
            System.out.println("You have entered either an invalid username or password. Please try again.");
        }
    }

    public static boolean login(String user, String pass) throws Exception {
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            String hold = scan.nextLine();
            String[] answer = hold.split(":");
            String usen = answer[2];
            if (usen.equalsIgnoreCase(user)) {
                String p = reHash(pass, answer[4]);
                if (p.equals(answer[3])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void register() throws Exception {
        Scanner scann = new Scanner(System.in);
        System.out.println("Please enter your first name: ");
        String first = scann.nextLine();
        System.out.println("Please enter your last name: ");
        String last = scann.nextLine();
        System.out.println("Please enter your username: ");
        String user = scann.nextLine();
        System.out.println("Please enter a password: ");
        String b = scann.nextLine();

        String salt = getSaltStr();
        String pass = reHash(b, salt);

        FileWriter prW = new FileWriter(file, true);
        System.out.println("Thank you! You have successfully created an account.");
        prW.append("\n" + last + ":" + first + ":" + user + ":" + pass + ":" + salt + ":" + "MD5");

        prW.close();
    }

    public static void updatePass() throws Exception {
        Scanner scann = new Scanner(System.in);
        Scanner scan = new Scanner(file);
        FileWriter fw = new FileWriter(file, true);

        System.out.println("Please enter your username: ");
        String use = scann.next();
        System.out.println("Please enter your old password: ");
        String pas = scann.next();

        while (scan.hasNextLine()) {
            String[] a = scan.nextLine().split(":");
            String p = reHash(pas, a[4]);
            if (use.equalsIgnoreCase(a[2])) {
                if (p.equals(a[3])) {
                    delete(a[3]);
                    System.out.println("Please enter your new password: ");
                    String newP = scann.next();
                    a[3] = newP;
                    fw.append(a.toString());
                }
            }
        }
        if (login(use, pas)) {
            System.out.println("You have successfully updated your password!");
        } else {
            System.out.println("You have entered either the wrong username or password");
        }
        fw.close();
    }

    protected static String getSaltStr() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVQXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random random = new Random();

        while (salt.length() < 8) {
            int index = (int) (random.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        return salt.toString();
    }

    public static void delete(String word) throws Exception {
        Scanner scan = new Scanner(file);
        ArrayList<String> all=new ArrayList<>();
        while (scan.hasNextLine()) all.add(scan.nextLine());
        int location = 0;
        for (int i=0;i<all.size();i++) {
            String[] stuff = all.get(i).split(":");
            if (stuff[2].equals(username)){
                location=i;
            }
        }
        all.remove(location);
        file =new File("passwords.txt");
        file.delete();
        file =new File("passwords.txt");
        FileWriter fw = new FileWriter(file, true);
        for (int i=0; i<all.size();i++){
            fw.append(all.get(i)+"\n");
        }
        fw.close();
    }

    public static void delete() {
        try {
            if (login()) {
                delete(username);
                System.out.println("You have successfully deleted your account!");
            } else {
                System.out.println("Account not found.");
            }
        }
        catch (Exception e) {
            System.out.println("Uh oh. You have a problem");
        }
    }

    public static void print() throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        while (scan.hasNext()) {
            String next = scan.next();
            System.out.println(next);
        }
    }

    public static void main(String[] args) throws Exception{
       //try {
           menu();
           boolean check = true;
           while(check) {
               Scanner scann = new Scanner(System.in);
               System.out.println("\nMenu: \n1. Login \n2. Register a New Account \n3. Update Password \n4. Delete An Account \n5. Print Password File \n6. Quit\n");
               System.out.println("Please enter a single number of your choice: ");
               switch (scann.nextInt()) {
                   case 1:
                       //login
                       checker();
                       break;

                   case 2:
                       //register a new account
                       register();
                       break;

                   case 3:
                       //update password
                       updatePass();
                       break;
                   case 4:
                       //delete an account
                       delete();
                       break;

                   case 5:
                       //print password file
                       print();
                       break;

                   case 6:
                       check = false;
                       break;

                   default:
                       System.out.println("Please enter a valid number from the menu: ");
                       break;
               }
           }
       }
      //  catch(Exception a){
        //   System.out.println("Please enter a number from the menu next time.");
       //}
    //}
}