package myClasses;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchBySize {

    public List<ImageComparisonResult> searchBySize(String referenceImage, String[] folderPaths) throws IOException {
        //String referenceImage = "C:/testImages/photos/inverted-color-close-up-of-a-citrus-slice (62).png";
        double threshold = 0.02;

        IndexedImage indexedImage = new IndexedImage();
        ColorHistogramViewer histogram = new ColorHistogramViewer();

        BufferedImage referenceColors = indexedImage.indexedImage(referenceImage, 256);

        // Step 3: Calculate color histogram for the reference image
        int[] referenceHistogram = histogram.targetColorHistogram2(referenceColors, 8);

        // String[] folderPaths = new String[] { "C:/testImages/New folder/", "C:/testImages/New folder (2)/", "C:/testImages/New folder (3)/" };
        String[] targetImagePaths = IOFils.getImagesInFolders(folderPaths);

        File file = new File(referenceImage);
        BufferedImage image = ImageIO.read(file);

        int targetWidth = image.getWidth();
        int targetHeight = image.getHeight();

        List<ImageComparisonResult> similarities = new ArrayList<>();
        for (String imagePath : targetImagePaths) {

            BufferedImage targetColors = indexedImage.indexedImage(imagePath, 128);
            assert imagePath != null;
            int[] targetHistogram = histogram.targetColorHistogram2(targetColors, 8);

            File file2 = new File(imagePath);
            BufferedImage image2 = ImageIO.read(file2);

            int width = image2.getWidth();
            int height = image2.getHeight();

            //double similarity = 0;
           // if (targetWidth == width && targetHeight == height) {

            double similarity = histogram.similarity(referenceHistogram, targetHistogram);
           // }

            // Step 10: Check if overall similarity exceeds the threshold for both colors
            if (targetWidth == width && targetHeight == height && similarity >= threshold) {
                similarities.add(new ImageComparisonResult(imagePath, similarity));
            }

        }

        // Step 10: Sort and display the similar images
        System.out.println("Found " + similarities.size() + " images with size " + image.getWidth() + "x" + image.getHeight());
        for (ImageComparisonResult similarity : similarities) {
            System.out.println(similarity.getImagePath());
        }
        return similarities;
    }
}

/*public List<ImageComparisonResult> searchBySize(String targetImage, String[] folderPaths) {
        BufferedImage imageSize = IOFils.loadImage(targetImage);

        String[] targetImagePaths = IOFils.getImagesInFolders(folderPaths);

        BufferedImage targetBufferedImage;
        targetBufferedImage = IOFils.loadImage(targetImage);

        assert targetBufferedImage != null;
        int targetWidth = targetBufferedImage.getWidth();
        int targetHeight = targetBufferedImage.getHeight();

        List<ImageComparisonResult> similarities = new ArrayList<>();
        for (String file : targetImagePaths) {
            BufferedImage image = IOFils.loadImage(file);
            assert image != null;
            int width = image.getWidth();
            int height = image.getHeight();

            if (width == targetWidth && height == targetHeight) {
                similarities.add(new ImageComparisonResult(file, 0.0));
            }
        }

        //display the similar images
        assert imageSize != null;
        System.out.println("Found " + similarities.size() + " images with size " + imageSize.getWidth() + "x" + imageSize.getHeight());
        for (ImageComparisonResult similarity : similarities) {
            System.out.println(similarity.getImagePath());
        }

        return similarities;
    }*/
