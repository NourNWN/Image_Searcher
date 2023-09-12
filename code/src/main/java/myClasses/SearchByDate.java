package myClasses;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchByDate {

    public List<ImageComparisonResult> searchByDate(String referenceImage, String[] folderPaths) {
        //String referenceImage = "C:/testImages/photos/inverted-color-close-up-of-a-citrus-slice (62).png";
        double threshold = 0.02;
        File targetImageFile = new File(referenceImage);

        // Get the last modified timestamp of the target image
        long targetTimestamp = targetImageFile.lastModified();

        // تنسيق التاريخ
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00");
        String formattedDate = dateFormat.format(new Date(targetTimestamp));

        IndexedImage indexedImage = new IndexedImage();
        ColorHistogramViewer histogram = new ColorHistogramViewer();

        // Step 2: Apply k-means clustering to the reference image
        BufferedImage referenceColors =  indexedImage.indexedImage(referenceImage, 256);

        // Step 3: Calculate color histogram for the reference image
        int[] referenceHistogram = histogram.targetColorHistogram2(referenceColors, 8);

        // String[] folderPaths = new String[] { "C:/testImages/New folder/", "C:/testImages/New folder (2)/", "C:/testImages/New folder (3)/" };
        String[] targetImagePaths = IOFils.getImagesInFolders(folderPaths);

        List<ImageComparisonResult> similarities = new ArrayList<>();
        for (String imagePath : targetImagePaths) {

            File imageFile = new File(imagePath);
            long fileTimestamp = imageFile.lastModified();
            String formattedDate2 = dateFormat.format(new Date(fileTimestamp));

            BufferedImage targetColors = indexedImage.indexedImage(imagePath, 256);
            int[] targetHistogram = histogram.targetColorHistogram2(targetColors, 8);

            double similarity = histogram.similarity(referenceHistogram, targetHistogram);

            if (formattedDate2.equals(formattedDate) && similarity >= threshold) {
                similarities.add(new ImageComparisonResult(imagePath, similarity));
            }

        }

        // Step 10: Sort and display the similar images
        System.out.println("Found " + similarities.size() + " images with the same date as the target image: " + formattedDate);
        for (ImageComparisonResult similarity : similarities) {
            System.out.println(similarity.getImagePath());
        }
        return similarities;
    }
}

 /* public List<ImageComparisonResult> searchByDate(String targetImage, String[] folderPaths) {

        String[] targetImagePaths = IOFils.getImagesInFolders(folderPaths);

        File targetImageFile = new File(targetImage);

        // Get the last modified timestamp of the target image
        long targetTimestamp = targetImageFile.lastModified();

        // تنسيق التاريخ
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00");
        String formattedDate = dateFormat.format(new Date(targetTimestamp));

        List<ImageComparisonResult> similarities = new ArrayList<>();
        for (String file : targetImagePaths) {
            File imageFile = new File(file);
            long fileTimestamp = imageFile.lastModified();

            // Compare the last modified timestamp of each image with the target timestamp
            String formattedDate2 = dateFormat.format(new Date(fileTimestamp));
            if (formattedDate2.equals(formattedDate)) {
                similarities.add(new ImageComparisonResult(file, 0.0));
            }
        }

        System.out.println("Found " + similarities.size() + " images with the same date as the target image: " + formattedDate);
        for (ImageComparisonResult similarity : similarities) {
            System.out.println(similarity.getImagePath());
        }
        return similarities;
    }*/