package com.gungoren.rle;

import net.sf.image4j.util.ConvertUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageScaleConverter {

    public static void main (String args[]) throws IOException {
        convertTo4Bit(args[0], args[1]);
        convertToGrayScale(args[0], args[2]);
    }

    public static void convertToGrayScale(String input, String output) throws IOException {

        File inFile = new File(input);
        File outFile = new File(output);
        BufferedImage inImage = ImageIO.read(inFile);
        BufferedImage image = new BufferedImage(inImage.getWidth(), inImage.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = image.getGraphics();
        g.drawImage(inImage, 0, 0, null);
        g.dispose();

        ImageIO.write(image, "bmp", outFile);
    }

    public static void convertTo4Bit(String input, String output) throws IOException {
        File inFile = new File(input);
        File outFile = new File(output);
        BufferedImage inImage = ImageIO.read(inFile);
        BufferedImage outImage = ConvertUtil.convert4(inImage); // Converts to 4 Bit

        ImageIO.write(outImage, "bmp", outFile);
        return;
    }


    public static void convertTo8Bit(String input, String output) throws IOException {
        File inFile = new File(input);
        File outFile = new File(output);
        BufferedImage inImage = ImageIO.read(inFile);
        BufferedImage outImage = ConvertUtil.convert8(inImage); // Converts to 8 Bit

        ImageIO.write(outImage, "bmp", outFile);
        return;
    }
}
