package edu.gonzaga.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

import edu.gonzaga.items.Card;
import edu.gonzaga.items.FaceValue;
import edu.gonzaga.items.Suit;

import javax.imageio.*;
import java.util.ArrayList;

public class CardImages {
    ArrayList<ImageIcon> images;
    ImageIcon facedownImage;

    ArrayList<ImageIcon> smallImages;
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
                    System.out.println("Loading image: " + filename);
                    currPicture = ImageIO.read(new File(filename));
                    Image cimg = currPicture.getScaledInstance(60, 80, Image.SCALE_SMOOTH);
                    Image smallCimg = currPicture.getScaledInstance(45, 60, Image.SCALE_SMOOTH);
                    ImageIcon scaledImage = new ImageIcon(cimg);
                    ImageIcon smallScaledImage = new ImageIcon(smallCimg);
                    images.add(scaledImage);
                    smallImages.add(smallScaledImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void loadFacedownImage(String imagesPath) {
        try {
            BufferedImage currPicture;
            // TODO: change from joker location to specific facedown
            String filename = imagesPath + "red_joker.png";
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

    public CardImages(String imagesPath) {
        images = new ArrayList<>();
        smallImages = new ArrayList<>();
        loadImages(imagesPath);
        loadFacedownImage(imagesPath);
    }

    public ImageIcon getCardImage(Card card) {
        Suit suit = card.getSuit();
        FaceValue faceValue = card.getFaceValue();

        int index = ((suit.ordinal()) * 13 ) + (faceValue.ordinal() - 1);
        return images.get(index);
    }

    public ImageIcon getSmallCardImage(Card card) {
        Suit suit = card.getSuit();
        FaceValue faceValue = card.getFaceValue();

        int index = ((suit.ordinal()) * 13 ) + (faceValue.ordinal() - 1);
        return smallImages.get(index);
    }

    public ImageIcon getFacedownImage() {
        return facedownImage;
    }

    public ImageIcon getSmallFacedownImage() {
        return smallFacedownImage;
    }
}
