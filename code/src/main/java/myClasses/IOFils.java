package myClasses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class IOFils {

    public static BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveImage(BufferedImage image, String imagePath) {
        try {
            ImageIO.write(image, "png", new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getImagesInFolders(String[] folderPaths) {
        List<String> imagePathsList = new ArrayList<>();

        for (String folderPath : folderPaths) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));

            for (File file : files) {
                imagePathsList.add(file.getPath());
            }
        }

        String[] imagePaths = new String[imagePathsList.size()];
        imagePaths = imagePathsList.toArray(imagePaths);

        return imagePaths;
    }

    public static int[] getColorFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the RGB color values (comma-separated): ");
        String input = scanner.nextLine();
        String[] colorValues = input.split(",");
        int red = Integer.parseInt(colorValues[0].trim());
        int green = Integer.parseInt(colorValues[1].trim());
        int blue = Integer.parseInt(colorValues[2].trim());
        scanner.close();
        return new int[] {red, green, blue};
    }

    static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        Image temp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

    static int[] flatten(int[][][] threeDHistogram) {
        return Arrays.stream(threeDHistogram).flatMap(Arrays::stream).flatMapToInt(Arrays::stream).toArray();
    }

}

