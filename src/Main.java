//Buğrahan Çamlıbel 190201022
//Çağataya Koçyiğit 190201070
//MAIN

import java.awt.Color;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Oynamak istediginiz karakterin adini giriniz.");
        String name = keyboard.nextLine();

        Map s = new Map(name);
        s.setBackground(Color.BLACK);
        JFrame jf = new JFrame("ŞİRİNLER");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(850, 700);
        jf.add(s);
        jf.setVisible(true);
        jf.addKeyListener(s);
    }

}