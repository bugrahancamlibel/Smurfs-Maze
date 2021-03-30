import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.exit;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Map extends JPanel implements KeyListener {

    ArrayList<Gozluklu> oyuncu1 = new ArrayList();
    ArrayList<Tembel> oyuncu2 = new ArrayList();
    ArrayList<Karakter> kotu_karakter = new ArrayList();
    ArrayList path[];
    ArrayList<Integer> yolx = new ArrayList();
    ArrayList<Integer> yoly = new ArrayList();
    String name;
    int x, y, m1, m2;
    boolean kazandi;
    boolean kaybetti;
    int[][] matris = matrisOku();
    Image image;
    ImageIcon img = new ImageIcon("image/gozluk22.png");
    ImageIcon img1 = new ImageIcon("image/tembel2.png");
    ImageIcon img2 = new ImageIcon("image/Azman22.png");
    ImageIcon img4 = new ImageIcon("image/gargamel22.png");
    ImageIcon son = new ImageIcon("image/win_smurf.png");
    ImageIcon over = new ImageIcon("image/uzgun.png");
    ImageIcon kupa = new ImageIcon("image/Sirine.png");

    Map(String name) throws IOException {
        this.name = name;
        if (!name.equalsIgnoreCase("gozluklu sirin") && !name.equalsIgnoreCase("tembel sirin")) {
            System.out.println("yanlis girdi yapildi ");
            exit(1);
        }
        if (name.equalsIgnoreCase("Gozluklu Sirin")) {
            Gozluklu o1 = new Gozluklu("Gozluklu Sirin", "iyi", 50 + 50 * 6, 50 + 50 * 5);
            oyuncu1.add(o1);
        } else if (name.equalsIgnoreCase("Tembel Sirin")) {
            Tembel o2 = new Tembel("Tembel Sirin", "iyi", 50 + 50 * 6, 50 + 50 * 5);
            oyuncu2.add(o2);
        }
        kotu_karakter = karakterOku();
        path = new ArrayList[kotu_karakter.size()];
        if (name.equalsIgnoreCase("Gozluklu Sirin")) {
            for (int i = 0; i < kotu_karakter.size(); i++) {
                Dijkstra temp = new Dijkstra();
                path[i] = temp.enKisaYol(oyuncu1.get(0).getLokasyonx(), oyuncu1.get(0).getLokasyony(), (kotu_karakter.get(i).getLokasyonx() / 50) - 1, (kotu_karakter.get(i).getLokasyony() / 50) - 1, matris);

            }
        } else {
            for (int i = 0; i < kotu_karakter.size(); i++) {
                Dijkstra temp = new Dijkstra();

                path[i] = temp.enKisaYol(oyuncu2.get(0).getLokasyonx(), oyuncu2.get(0).getLokasyony(), (kotu_karakter.get(i).getLokasyonx() / 50) - 1, (kotu_karakter.get(i).getLokasyony() / 50) - 1, matris);

            }
        }

        if (name.equalsIgnoreCase("Gozluklu Sirin")) {
            x = 50 + 50 * 6;
            y = 50 + 50 * 5;
        } else {
            x = 50 + 50 * 6;
            y = 50 + 50 * 5;
        }
        m1 = (x / 50) - 1;
        m2 = (y / 50) - 1;
        kazandi = false;
        kaybetti = false;

    }

    void setX(int lokasyon) {
        x = x + lokasyon;
        m1 = (x / 50) - 1;
        kazandi = end(m1, m2);
        if (name.equalsIgnoreCase("Gozluklu Sirin")) {
            oyuncu1.get(0).setLokasyonx(x);

            for (int i = 0; i < kotu_karakter.size(); i++) {
                Dijkstra temp = new Dijkstra();

                path[i] = temp.enKisaYol(oyuncu1.get(0).getLokasyonx(), oyuncu1.get(0).getLokasyony(), (kotu_karakter.get(i).getLokasyonx() / 50) - 1, (kotu_karakter.get(i).getLokasyony() / 50) - 1, matris);

            }
            kotuSet();
            for (int i = 0; i < kotu_karakter.size(); i++) {
                if (kotu_karakter.get(i).getLokasyonx() == (oyuncu1.get(0).getLokasyonx() + 1) * 50 && kotu_karakter.get(i).getLokasyony() == (oyuncu1.get(0).getLokasyony() + 1) * 50) {
                    kaybetti = true;
                    if (kotu_karakter.get(i).getAd().equalsIgnoreCase("Gargamel")) {
                        if (name.equalsIgnoreCase("Gozluklu Sirin")) {
                            oyuncu1.get(0).setPuan(15);
                        } else if (name.equalsIgnoreCase("Tembel Sirin")) {
                            oyuncu2.get(0).setPuan(15);
                        }
                    } else if (kotu_karakter.get(i).getAd().equalsIgnoreCase("Azman")) {
                        if (name.equalsIgnoreCase("Gozluklu Sirin")) {
                            oyuncu1.get(0).setPuan(5);
                        } else if (name.equalsIgnoreCase("Tembel Sirin")) {
                            oyuncu2.get(0).setPuan(5);
                        }
                    }

                }
            }


        } else {
            oyuncu2.get(0).setLokasyonx(x);
            for (int i = 0; i < kotu_karakter.size(); i++) {
                Dijkstra temp = new Dijkstra();
                path[i] = temp.enKisaYol(oyuncu2.get(0).getLokasyonx(), oyuncu2.get(0).getLokasyony(), (kotu_karakter.get(i).getLokasyonx() / 50) - 1, (kotu_karakter.get(i).getLokasyony() / 50) - 1, matris);
            }
            kotuSet();

            for (int i = 0; i < kotu_karakter.size(); i++) {
                if (kotu_karakter.get(i).getLokasyonx() == (oyuncu2.get(0).getLokasyonx() + 1) * 50 && kotu_karakter.get(i).getLokasyony() == (oyuncu2.get(0).getLokasyony() + 1) * 50) {
                    kaybetti = true;
                    if (kotu_karakter.get(i).getAd().equalsIgnoreCase("Gargamel")) {
                        oyuncu2.get(0).setPuan(15);
                    }
                    else if (kotu_karakter.get(i).getAd().equalsIgnoreCase("Azman")){
                        oyuncu2.get(0).setPuan(5);
                    }
                }
            }
        }


    }

    void setY(int lokasyon) {
        y = y + lokasyon;
        m2 = (y / 50) - 1;
        kazandi = end(m1, m2);

        if (name.equalsIgnoreCase("Gozluklu Sirin")) {
            oyuncu1.get(0).setLokasyony(y);
            for (int i = 0; i < kotu_karakter.size(); i++) {
                Dijkstra temp = new Dijkstra();

                path[i] = temp.enKisaYol(oyuncu1.get(0).getLokasyonx(), oyuncu1.get(0).getLokasyony(), (kotu_karakter.get(i).getLokasyonx() / 50) - 1, (kotu_karakter.get(i).getLokasyony() / 50) - 1, matris);

            }
            kotuSet();

            for (int i = 0; i < kotu_karakter.size(); i++) {
                if (kotu_karakter.get(i).getLokasyonx() == (oyuncu1.get(0).getLokasyonx() + 1) * 50 && kotu_karakter.get(i).getLokasyony() == (oyuncu1.get(0).getLokasyony() + 1) * 50) {
                    kaybetti = true;
                    if (kotu_karakter.get(i).getAd().equalsIgnoreCase("Gargamel")) {
                        oyuncu2.get(0).setPuan(15);
                    }
                    else if (kotu_karakter.get(i).getAd().equalsIgnoreCase("Azman")){
                        oyuncu2.get(0).setPuan(5);
                    }

                }
            }

        } else {
            oyuncu2.get(0).setLokasyony(y);
            for (int i = 0; i < kotu_karakter.size(); i++) {
                Dijkstra temp = new Dijkstra();
                path[i] = temp.enKisaYol(oyuncu2.get(0).getLokasyonx(), oyuncu2.get(0).getLokasyony(), (kotu_karakter.get(i).getLokasyonx() / 50) - 1, (kotu_karakter.get(i).getLokasyony() / 50) - 1, matris);

            }
            kotuSet();
            for (int i = 0; i < kotu_karakter.size(); i++) {
                if (kotu_karakter.get(i).getLokasyonx() == (oyuncu2.get(0).getLokasyonx() + 1) * 50 && kotu_karakter.get(i).getLokasyony() == (oyuncu2.get(0).getLokasyony() + 1) * 50) {
                    kaybetti = true;
                    if (kotu_karakter.get(i).getAd().equalsIgnoreCase("Gargamel")) {
                        oyuncu2.get(0).setPuan(15);
                    }
                    if (kotu_karakter.get(i).getAd().equalsIgnoreCase("Azman")) {
                        oyuncu2.get(0).setPuan(5);
                    }

                }
            }

        }

    }

    boolean end(int lx, int ly) {
        boolean bitti = false;
        if (lx == 12 && ly == 7) {
            bitti = true;
            if (name.equalsIgnoreCase("Gozluklu Sirin")) {
                System.out.println("OYUNCUNUN PUANI: " + oyuncu1.get(0).puan);
            } else if (name.equalsIgnoreCase("Tembel Sirin")) {
                System.out.println("OYUNCUNUN PUANI: " + oyuncu2.get(0).puan);
            }
        }
        return bitti;
    }


    @Override
    public void paintComponent(Graphics g) {


        super.paintComponent(g);
        this.setBackground(Color.green.darker().darker().darker());
        int j = 1;


        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 14; i++) {
            for (int k = 0; k < 11; k++) {
                if (matris[i][k] == 1) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(50 + i * 50, 50 + k * 50, 50, 50);
                }
                if (matris[i][k] == 0) {
                    g.setColor(Color.gray.darker());
                    g.fillRect(50 + i * 50, 50 + k * 50, 50, 50);
                }

            }
        }

        g.setColor(Color.white);
        for (int i = 50; i <= 600; i = i + 50) {
            g.drawLine(50, i, 750, i);
        }
        for (int i = 50; i <= 750; i = i + 50) {
            g.drawLine(i, 50, i, 600);
        }
        j = 0;
        g.setColor(Color.white);
        for (int i = 75; i < 600; i = i + 50) {
            g.drawString("" + j, 20, i);
            j++;
        }
        j = 0;
        for (int i = 60; i < 750; i = i + 50) {
            g.drawString("" + j, i + 5, 40);
            j++;
        }


        for (int k = 0; k < 14; k++) {
            for (int l = 0; l < 11; l++) {
                if (matris[k][l] == 0) {
                    g.setColor(Color.WHITE);
                    g.drawString("" + matris[k][l], 75 + k * 50, 75 + l * 50);
                } else {
                    g.setColor(Color.BLACK);
                    g.drawString("" + matris[k][l], 75 + k * 50, 75 + l * 50);
                }
            }
        }

        g.setColor(Color.blue.darker());
        g.fillRect(50 + 50 * 3, 50, 50, 50);
        g.fillRect(50 + 50 * 10, 50, 50, 50);
        g.fillRect(50 + 50 * 12, 50 + 50 * 7, 50, 50);
        g.fillRect(50 + 50 * 3, 50 + 50 * 10, 50, 50);
        g.fillRect(50, 50 + 50 * 5, 50, 50);
        g.setColor(Color.GREEN);
        g.fillRect(50 + 50 * 6, 50 + 50 * 5, 50, 50);


        g.setColor(Color.CYAN);
        g.drawString("C", 70, 50 + 55 * 5);
        g.drawString("A", 50 + 55 * 3, 75);
        g.drawString("B", 49 + 52 * 10, 75);
        g.drawString("E", 47 + 52 * 13, 50 + 55 * 7);
        g.drawString("D", 50 + 55 * 3, 53 + 52 * 10);

        image = kupa.getImage();
        g.drawImage(image, 14 * 50, 8 * 50, 60, 60, null);

        for (int i = 0; i < kotu_karakter.size(); i++) {

            if (kotu_karakter.get(i).getAd().equalsIgnoreCase("Azman")) {
                image = img2.getImage();

                g.drawImage(image, kotu_karakter.get(i).getLokasyonx(), kotu_karakter.get(i).getLokasyony(), 45, 45, null);

            } else if (kotu_karakter.get(i).getAd().equalsIgnoreCase("Gargamel")) {
                image = img4.getImage();
                g.drawImage(image, kotu_karakter.get(i).getLokasyonx(), kotu_karakter.get(i).getLokasyony(), 45, 45, null);
            }
        }

        if (name.equalsIgnoreCase("Gozluklu Sirin")) {
            image = img.getImage();
            g.drawImage(image, x, y, 45, 45, null);
        } else if (name.equalsIgnoreCase("Tembel Sirin")) {
            image = img1.getImage();
            g.drawImage(image, x, y, 45, 45, null);
        }

        j = 50;
        if (name.equalsIgnoreCase("Gozluklu Sirin")) {
            if (oyuncu1.get(0).getPuan() % 2 == 0) {
                for (int i = 0; i < oyuncu1.get(0).getPuan(); i = i + 2) {
                    j = j + 30;
                }
            } else {
                for (int i = 0; i < oyuncu1.get(0).getPuan() - 1; i = i + 2) {
                    g.drawImage(image, 770, j, 30, 30, null);
                    j = j + 30;
                }
            }
        } else {
            for (int i = 0; i < oyuncu2.get(0).getPuan(); i++) {
                j = j + 30;
            }
        }


        g.setColor(Color.red);
        for (int i = 0; i < kotu_karakter.size(); i++) {
            if (path[i] != null) {
                if (i == 1) {
                    g.setColor(Color.BLUE);
                }
                if (i == 2) {
                    g.setColor(Color.PINK);
                }
                if (i == 3) {
                    g.setColor(Color.YELLOW);
                }
                if (i == 4) {
                    g.setColor(Color.GREEN);
                }
                for (int k = 1; k < path[i].size(); k++) {
                    int x1, y1;
                    x1 = 0;
                    y1 = 0;
                    if ((int) path[i].get(k) == 84) {
                        x1 = (int) path[i].get(k) % 14 + 13;
                        y1 = (int) path[i].get(k) / 14 - 1;
                    } else {
                        x1 = (int) path[i].get(k) % 14 - 1;
                        y1 = (int) path[i].get(k) / 14;
                    }

                    g.drawRoundRect(50 + (x1) * 50, 50 + y1 * 50, 50, 50, 30, 30);
                }
            }
            if (kazandi) {
                image = son.getImage();
                g.drawImage(image, 0, 0, 850, 700, null);

            }

            if (kaybetti) {
                if (name.equalsIgnoreCase("Gozluklu Sirin")) {
                    try {
                        kotu_karakter = karakterOku();
                    } catch (IOException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    oyuncu1.get(0).setLokasyonx(50 + 50 * 6);
                    oyuncu1.get(0).setLokasyony(50 + 50 * 5);
                    x = 50 + 50 * 6;
                    y = 50 + 50 * 5;
                    m1 = (x / 50) - 1;
                    m2 = (y / 50) - 1;
                } else {
                    try {
                        kotu_karakter = karakterOku();
                    } catch (IOException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    oyuncu2.get(0).setLokasyonx(50 + 50 * 6);
                    oyuncu2.get(0).setLokasyony(50 + 50 * 5);
                    x = 50 + 50 * 6;
                    y = 50 + 50 * 5;
                    m1 = (x / 50) - 1;
                    m2 = (y / 50) - 1;

                }
                kaybetti = false;
                repaint();
            }
            if (name.equalsIgnoreCase("gozluklu sirin")) {
                if (oyuncu1.get(0).getPuan() <= 0) {
                    image = over.getImage();
                    g.drawImage(image, 0, 0, 850, 700, null);
                }
            } else {
                if (oyuncu2.get(0).getPuan() <= 0) {
                    image = over.getImage();
                    g.drawImage(image, 0, 0, 850, 700, null);
                    System.out.println("OYUNCUNUN PUANI: " + oyuncu2.get(0).getPuan());
                }
            }
            if (name.equalsIgnoreCase("gozluklu sirin")) {
                if (oyuncu1.get(0).getPuan() <= 0) {
                    image = over.getImage();
                    g.drawImage(image, 0, 0, 850, 700, null);
                    System.out.println("OYUNCUNUN PUANI: " + oyuncu1.get(0).getPuan());
                }
            } else {
                if (oyuncu2.get(0).getPuan() <= 0) {
                    image = over.getImage();
                    g.drawImage(image, 0, 0, 850, 700, null);
                    System.out.println("OYUNCUNUN PUANI: " + oyuncu2.get(0).getPuan());
                }
            }


        }

    }


    public int[][] matrisOku() throws IOException {
        int matris[][] = new int[14][11];


        for (int k = 0; k < 14; k++) {
            for (int l = 0; l < 11; l++) {
                matris[k][l] = 1;
            }
        }

        File file = new File("src/Harita.txt");
        BufferedReader bf = null;
        bf = new BufferedReader(new FileReader(file));
        String satir = bf.readLine();

        String line[] = null;
        int sayac = 0;
        while (satir != null) {

            line = satir.split("\t");

            if (line[0].equals("1") || line[0].equals("0") && sayac < 11) {

                for (int i = 0; i < line.length; i++) {
                    matris[i][sayac] = Integer.valueOf(line[i]);
                }

                sayac++;
            }

            satir = bf.readLine();
        }
        bf.close();
        return matris;
    }

    public ArrayList karakterOku() throws IOException {

        ArrayList<Karakter> kotu = new ArrayList();

        String line[] = null;
        String line2[] = null;
        String line3[] = null;
        int x = 0, y = 0;


        File file = new File("src/Harita.txt");
        BufferedReader bf = null;
        bf = new BufferedReader(new FileReader(file));
        String satir = bf.readLine();

        while (satir != null) {

            line = satir.split(",");

            if (line.length > 1) {
                line2 = line[0].split(":");
                line3 = line[1].split(":");

                switch (line3[1]) {
                    case "A":
                        x = 55 * 4 - 10;
                        y = 55;
                        break;
                    case "B":
                        x = 40 + 52 * 10;
                        y = 55;
                        break;
                    case "C":
                        x = 70;
                        y = 50 + 55 * 5;
                        break;
                    case "D":
                        x = 50 + 50 * 3;
                        y = 50 + 50 * 10;
                        break;
                    case "E":
                        x = 50 + 50 * 11;
                        y = 50 + 50 * 7;
                        break;
                }

                if (line2[1].equalsIgnoreCase("Gargamel")) {
                    Azman k1 = new Azman(line2[1], "kotu", x, y);
                    kotu.add(k1);
                } else if (line2[1].equalsIgnoreCase("Azman")) {
                    Gazman k2 = new Gazman(line2[1], "kotu", x, y);
                    kotu.add(k2);
                }

            }
            satir = bf.readLine();

        }
        bf.close();

        return kotu;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                if (m1 + 1 < 14) {
                    if (matris[m1 + 1][m2] != 0) {
                        if (this.name.equals("gozluklu sirin") || this.name.equals("Gozluklu Sirin")) {
                            if (matris[m1 + 2][m2] != 0) {
                                this.setX(100);
                            }
                        } else
                            this.setX(50);
                    }
                }

                break;
            case KeyEvent.VK_LEFT:
                if (m1 - 1 > -1) {
                    if (matris[m1 - 1][m2] != 0) {
                        if (this.name.equals("gozluklu sirin") || this.name.equals("Gozluklu Sirin")) {
                            if (matris[m1 - 2][m2] != 0) {
                                this.setX(-100);
                            }
                        } else
                            this.setX(-50);
                    }

                }
                break;

            case KeyEvent.VK_DOWN:
                if (m2 + 1 < 11) {
                    if (matris[m1][m2 + 1] != 0) {
                        if (this.name.equals("gozluklu sirin") || this.name.equals("Gozluklu Sirin")) {
                            if (matris[m1][m2 + 2] != 0) {
                                this.setY(100);
                            }
                        } else
                            this.setY(50);
                    }
                }

                break;
            case KeyEvent.VK_UP:
                if (m2 - 1 > -1) {
                    if (matris[m1][m2 - 1] != 0) {
                        if (this.name.equals("gozluklu sirin") || this.name.equals("Gozluklu Sirin")) {
                            if (matris[m1][m2 - 2] != 0) {
                                this.setY(-100);
                            }
                        } else
                            this.setY(-50);
                    }
                }
                break;
            default:
                break;
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void kotuSet() {
        int x = 0, y = 0, many = 0;

        for (int i = 0; i < kotu_karakter.size(); i++) {
            if (kotu_karakter.get(i).getAd().equalsIgnoreCase("Azman")) {
                if (path[i].size() > 1) {
                    x = (int) path[i].get(1) % 14;
                    y = (int) path[i].get(1) / 14 + 1;
                    kotu_karakter.get(i).setLokasyonx(x * 50);
                    kotu_karakter.get(i).setLokasyony(y * 50);
                    many = (int) path[i].size() - 1;
                } else {
                    kaybetti = true;
                }
            } else if (kotu_karakter.get(i).getAd().equalsIgnoreCase("gargamel")) {
                if (path[i].size() > 2) {
                    x = (int) path[i].get(2) % 14;
                    y = (int) path[i].get(2) / 14 + 1;
                    kotu_karakter.get(i).setLokasyonx(x * 50);
                    kotu_karakter.get(i).setLokasyony(y * 50);
                    many = (int) path[i].size() - 1;
                } else {
                    if (path[i].size() > 1) {
                        x = (int) path[i].get(1) % 14;
                        y = (int) path[i].get(1) / 14 + 1;
                        kotu_karakter.get(i).setLokasyonx(x * 50);
                        kotu_karakter.get(i).setLokasyony(y * 50);
                        many = (int) path[i].size() - 1;
                    } else {
                        kaybetti = true;
                    }
                }
            }
        }
        repaint();
    }
}