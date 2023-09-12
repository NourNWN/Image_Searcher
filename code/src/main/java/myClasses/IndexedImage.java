package myClasses;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

public class IndexedImage {

    public BufferedImage indexedImage(String input, int colorCount) {
        // Load the original image
        BufferedImage originalImage = IOFils.loadImage(input);

        // تحجيم الصورة إلى حجم موحد
        int targetSize = 8;
        assert originalImage != null;
        originalImage = IOFils.resizeImage(originalImage, targetSize, targetSize);

        // Convert the image to indexed color
        BufferedImage indexedImage = convertToIndexedColor(originalImage, colorCount);

        // Save the indexed image
       // IOFils.saveImage(indexedImage, "C:/testImages/photos/result.png");

        return indexedImage;
    }

    public static BufferedImage convertToIndexedColor(BufferedImage originalImage, int colorCount) {
        // Get the width and height of the original image
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Create an indexed image with the same dimensions
        BufferedImage indexedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);

        // Create a color palette for the indexed image
        IndexColorModel colorModel = createColorModel(colorCount);

        // Set the color model to the indexed image
        indexedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED, colorModel);

        // Draw the original image onto the indexed image
        Graphics graphics = indexedImage.getGraphics();
        graphics.drawImage(originalImage, 0, 0, null);
        graphics.dispose();

        return indexedImage;
    }

    private static IndexColorModel createColorModel(int colorCount) {
        // Create arrays to store the RGB values for the color palette
        byte[] reds = new byte[colorCount];
        byte[] greens = new byte[colorCount];
        byte[] blues = new byte[colorCount];
        byte[] alphas = new byte[colorCount];

        //generate a grayscale palette
        // Calculate the RGB values for each color in the palette
        for (int i = 0; i < colorCount; i++) {
            // Calculate the RGB values based on your color generation algorithm
            // Here, we generate a grayscale palette
            byte value = (byte) (i * 255 / (colorCount - 1));
            reds[i] = value;
            greens[i] = value;
            blues[i] = value;
            alphas[i] = (byte) 255; // Fully opaque
        }

        // توليد الوان قوس قزح
       /* float increment = 360f / colorCount;
        for (int i = 0; i < colorCount; i++) {
            float hue = i * increment;
            Color color = Color.getHSBColor(hue / 360f, 1f, 1f);
            reds[i] = (byte) color.getRed();
            greens[i] = (byte) color.getGreen();
            blues[i] = (byte) color.getBlue();
            alphas[i] = (byte) 255; // Fully opaque
        }*/

        // توليد مجموعة من الالوان العشوائية
      /*  Random random = new Random();
        for (int i = 0; i < colorCount; i++) {
            reds[i] = (byte) random.nextInt(256);
            greens[i] = (byte) random.nextInt(256);
            blues[i] = (byte) random.nextInt(256);
            alphas[i] = (byte) 255; // Fully opaque
        }*/


        // Create an IndexColorModel using the RGB values
        return new IndexColorModel(8, colorCount, reds, greens, blues, alphas);
    }
}
