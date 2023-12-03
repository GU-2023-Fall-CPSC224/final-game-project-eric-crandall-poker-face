package edu.gonzaga.utils;

import edu.gonzaga.items.Card;
import edu.gonzaga.items.FaceValue;
import edu.gonzaga.items.Suit;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

public class CardImages {
    Map<Card, ImageIcon> images;
    ImageIcon facedownImage;

    Map<Card, ImageIcon> smallImages;
    ImageIcon smallFacedownImage;

    void loadImages(String imagesPath) {
        BufferedImage currPicture;
        for (int i = 0; i < Suit.values().length; i++) {
            // j = 1 to skip blank, length = length - 1 to skip joker
            for (int j = 1; j < FaceValue.values().length - 1; j++) {
                try {
                    String suit = Suit.values()[i].toString();
                    String faceValue = FaceValue.values()[j].toString();
                    String filename = imagesPath + faceValue.toLowerCase() + "_of_" + suit.toLowerCase() + ".png";
                    Card card = new Card();
                    card.setFaceValue(FaceValue.values()[j]);
                    card.setSuit(Suit.values()[i]);
                    ImageIcon icon = null;
                    try {
                        icon = getSmallCardImage(card);
                    } catch (ConcurrentModificationException ignored) {
                        System.out.println("Skipping already loaded card...");
                        continue;
                    }
                    if (icon != null) {
                        System.out.println("Skipping already loaded card...");
                        continue;
                    }
                    System.out.println("Silent loading image: " + filename);
                    currPicture = ImageIO.read(new File(filename));
                    Image cimg = currPicture.getScaledInstance(60, 80, Image.SCALE_SMOOTH);
                    Image smallCimg = currPicture.getScaledInstance(45, 60, Image.SCALE_SMOOTH);
                    ImageIcon scaledImage = new ImageIcon(cimg);
                    ImageIcon smallScaledImage = new ImageIcon(smallCimg);
                    images.put(card, scaledImage);
                    smallImages.put(card, smallScaledImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void loadImportantImages(String imagesPath, ArrayList<Card> importantCards) {
        BufferedImage currPicture;
        loadFacedownImage(imagesPath);
        for (Card card : importantCards) {
            try {
                String suit = card.getSuit().toString().toLowerCase();
                String faceValue = card.getFaceValue().toString().toLowerCase();
                String filename = imagesPath + faceValue + "_of_" + suit + ".png";
                System.out.println("Loading important card: " + filename);
                currPicture = ImageIO.read(new File(filename));
                Image cimg = currPicture.getScaledInstance(60, 80, Image.SCALE_SMOOTH);
                Image smallCimg = currPicture.getScaledInstance(45, 60, Image.SCALE_SMOOTH);
                ImageIcon scaledImage = new ImageIcon(cimg);
                ImageIcon smallScaledImage = new ImageIcon(smallCimg);
                images.put(card, scaledImage);
                smallImages.put(card, smallScaledImage);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                new Thread("LoadImages") {
                    @Override
                    public void run() {
                        super.run();
                        loadImages(imagesPath);
                        this.interrupt();
                    }
                }.start();
            }
        }
    }

    void loadFacedownImage(String imagesPath) {
        try {
            BufferedImage currPicture;
            String filename = imagesPath + "card_back.png";
            System.out.println("Loading image: " + filename);
            currPicture = ImageIO.read(new File(filename));
            Image cimg = currPicture.getScaledInstance(60, 80, Image.SCALE_SMOOTH);
            Image smallCimg = currPicture.getScaledInstance(45, 60, Image.SCALE_SMOOTH);
            ImageIcon scaledImage = new ImageIcon(cimg);
            ImageIcon smallScaledImage = new ImageIcon(smallCimg);
            facedownImage = scaledImage;
            smallFacedownImage = smallScaledImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CardImages(String imagesPath, ArrayList<Card> importantCards) {
        images = new HashMap<>();
        smallImages = new HashMap<>();
        loadImportantImages(imagesPath, importantCards);
    }

    public ImageIcon getCardImage(Card card) {
        for (Card c : images.keySet()) {
            if (c.getFaceValue() == card.getFaceValue() && c.getSuit() == card.getSuit()) return images.get(c);
        }
        return null;
    }

    public ImageIcon getSmallCardImage(Card card) {
        for (Card c : smallImages.keySet()) {
            if (c.getFaceValue() == card.getFaceValue() && c.getSuit() == card.getSuit()) return smallImages.get(c);
        }
        return null;
    }

    public ImageIcon getFacedownImage() {
        return facedownImage;
    }

    public ImageIcon getSmallFacedownImage() {
        return smallFacedownImage;
    }
}
