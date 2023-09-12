package myClasses;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorHistogramViewer {

    public double calculateSimilarity(int[] histogram1, int[] histogram2) {
        if (histogram1.length != histogram2.length) {
            throw new IllegalArgumentException("Histogram lengths are not equal");
        }

        int totalBins = histogram1.length;
        int totalPixels1 = 0;
        int totalPixels2 = 0;

        // Calculate the total number of pixels in each histogram
        for (int bin : histogram1) {
            totalPixels1 += bin;
        }

        for (int bin : histogram2) {
            totalPixels2 += bin;
        }

        double similarity = 0.0;

        // Calculate the intersection similarity
        for (int i = 0; i < totalBins; i++) {
            int minBin = Math.min(histogram1[i], histogram2[i]);
            int maxTotalPixels = Math.max(totalPixels1, totalPixels2);

            double binSimilarity = (double) minBin / maxTotalPixels;
            similarity += binSimilarity;
        }

        // Normalize the similarity by dividing by the total number of bins
        similarity /= totalBins;

        // Ensure similarity is within the range of 0 to 1
        similarity = Math.max(0, Math.min(1, similarity));

        return similarity;
    }

    public int[] calculateColorHistogram(BufferedImage image, int red, int green, int blue) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] histogram = new int[256];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb, true);

                int redValue = color.getRed();
                int greenValue = color.getGreen();
                int blueValue = color.getBlue();

                double distance = Math.sqrt(Math.pow(redValue - red, 2) + Math.pow(greenValue - green, 2) + Math.pow(blueValue - blue, 2));
                int bin = (int) (distance / 16);

                histogram[bin]++;
                if (color.getRed() == redValue && color.getGreen() == greenValue && color.getBlue() == blueValue) {
                    System.out.println("done");
                }
            }
        }

        return histogram;
    }

    public int[] targetColorHistogram(BufferedImage input) {
        // assert inputImage != null;
        int width = input.getWidth();
        int height = input.getHeight();

        // Create an array to store color frequencies
        int[] colorHistogram = new int[256];

        // Calculate color frequencies
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = input.getRGB(x, y);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                // Convert RGB to grayscale
                int grayscale = (red + green + blue) / 3;

                // Increment the frequency count for the grayscale value
                colorHistogram[grayscale]++;
            }
        }

        return colorHistogram;
    }

    public int[] targetColorHistogram2(BufferedImage img, int binsPerColor) {
        final int[][][] histogram = new int[binsPerColor][binsPerColor][binsPerColor];
        final int div = (int) Math.ceil(256d / binsPerColor);
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));
                histogram[c.getRed() / div][c.getGreen() / div][c.getBlue() / div]++;
            }
        }
        return (IOFils.flatten(histogram));
    }

    public boolean colorHistogram(BufferedImage img, int[] targetColors) {
        boolean isTargetColor = false;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));

                // Check if the color is in the target colors
                for (int color : targetColors) {
                    Color targetColor = new Color(color);
                    if (c.getRed() == targetColor.getRed() && c.getGreen() == targetColor.getGreen() && c.getBlue() == targetColor.getBlue()) {
                        isTargetColor = true;
                        break;
                    }
                }

                if (isTargetColor) {
                    // If the target color is found, exit the loop
                    break;
                }
            }

            if (isTargetColor) {
                // If the target color is found, exit the loop
                break;
            }
        }

        return isTargetColor;
    }


    public float L1distance(int[] vectorA, int[] vectorB) {
        float distance = 0;
        for (int i = 0; i < vectorA.length; i++) {
            final float diff = vectorA[i] - vectorB[i];
            distance += Math.abs(diff);
        }
        return distance;
    }

    public double similarity(int[] vectorA, int[] vectorB) {
        return 1 / (1 + L1distance(vectorA, vectorB));
    }

}
