package myClasses;

import java.awt.*;
import java.awt.image.BufferedImage;

public class KMeans {
    public int[][] kMeans(String input, int k) {
        // تحميل الصورة
        BufferedImage image = IOFils.loadImage(input);

        // تحجيم الصورة إلى حجم موحد
        int targetSize = 8;
        image = IOFils.resizeImage(image, targetSize, targetSize);

        // استخراج بيانات الصورة
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] pixels = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = image.getRGB(i, j);
            }
        }

        // تطبيق خوارزمية K-means
        int[][] clusteredPixels = kmeans(pixels, k);

        return clusteredPixels;
    }

    private static int[][] kmeans(int[][] pixels, int k) {
        int width = pixels.length;
        int height = pixels[0].length;
        int[][] clusteredPixels = new int[width][height];
        int[] centers = new int[k];

        // تحديد قيم بدءية للمراكز
        for (int i = 0; i < k; i++) {
            centers[i] = pixels[(i * width) / k][(i * height) / k];
        }

        boolean converged = false;

        while (!converged) {
            // حساب المسافة بين كل بكسل ومراكز الألوان
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = pixels[i][j];
                    int minDistance = Integer.MAX_VALUE;
                    int closestCenter = 0;

                    for (int c = 0; c < k; c++) {
                        int distance = calculateDistance(pixel, centers[c]);

                        if (distance < minDistance) {
                            minDistance = distance;
                            closestCenter = c;
                        }
                    }

                    clusteredPixels[i][j] = centers[closestCenter];
                }
            }

            // تحديث المراكز
            int[] newCenters = new int[k];
            int[] counts = new int[k];

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = clusteredPixels[i][j];
                    int centerIndex = findCenterIndex(pixel, centers);

                    newCenters[centerIndex] += pixel;
                    counts[centerIndex]++;
                }
            }

            // حساب المراكز الجديدة
            for (int c = 0; c < k; c++) {
                if (counts[c] > 0) {
                    newCenters[c] /= counts[c];
                }
            }

            // التحقق من تحقيق التقارب
            converged = true;

            for (int c = 0; c < k; c++) {
                if (newCenters[c] != centers[c]) {
                    converged = false;
                    break;
                }
            }

            // تحديث المراكز
            centers = newCenters;
        }

        return clusteredPixels;
    }

    private static int calculateDistance(int pixel1, int pixel2) {
        Color color1 = new Color(pixel1);
        Color color2 = new Color(pixel2);
        int redDiff = color1.getRed() - color2.getRed();
        int greenDiff = color1.getGreen() - color2.getGreen();
        int blueDiff = color1.getBlue() - color2.getBlue();
        return (redDiff * redDiff) + (greenDiff * greenDiff) + (blueDiff * blueDiff);
    }

    private static int findCenterIndex(int pixel, int[] centers) {
        int minDistance = Integer.MAX_VALUE;
        int closestCenter = 0;

        for (int c = 0; c < centers.length; c++) {
            int distance = calculateDistance(pixel, centers[c]);

            if (distance < minDistance) {
                minDistance = distance;
                closestCenter = c;
            }
        }

        return closestCenter;
    }

    public BufferedImage process(String input, int k) {

        int[][] clusteredPixels = kMeans(input, k);

        // عرض النتائج
        int width = clusteredPixels.length;
        int height = clusteredPixels[0].length;
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                resultImage.setRGB(i, j, clusteredPixels[i][j]);
            }
        }

        IOFils.saveImage(resultImage, "C:/testImages/photos/result.png");
        System.out.println("K-Means Done!");

        return resultImage;
    }
}

