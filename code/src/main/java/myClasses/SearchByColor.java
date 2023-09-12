package myClasses;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SearchByColor {
    public List<ImageComparisonResult> searchByColor(String referenceImage, String[] folderPaths, double threshold) {
        //String referenceImage = "C:/testImages/photos/inverted-color-close-up-of-a-citrus-slice (62).png";
        //double threshold = 0.02;

        IndexedImage indexedImage = new IndexedImage();
        ColorHistogramViewer histogram = new ColorHistogramViewer();

        BufferedImage referenceColors =  indexedImage.indexedImage(referenceImage, 256);

        // Step 3: Calculate color histogram for the reference image
        int[] referenceHistogram = histogram.targetColorHistogram2(referenceColors, 8);

        // String[] folderPaths = new String[] { "C:/testImages/New folder/", "C:/testImages/New folder (2)/", "C:/testImages/New folder (3)/" };
        String[] targetImagePaths = IOFils.getImagesInFolders(folderPaths);

        List<ImageComparisonResult> similarities = new ArrayList<>();
        for (String imagePath : targetImagePaths) {

            BufferedImage targetColors = indexedImage.indexedImage(imagePath, 256);
            assert imagePath != null;
            int[] targetHistogram = histogram.targetColorHistogram2(targetColors, 8);

            double similarity = histogram.similarity(referenceHistogram, targetHistogram);

            if (similarity >= threshold) {
                similarities.add(new ImageComparisonResult(imagePath, similarity));
            }

        }

        // Step 10: Sort and display the similar images
        similarities.sort(Comparator.comparing(ImageComparisonResult::getSimilarity).reversed());
        for (ImageComparisonResult similarity : similarities) {
            System.out.println(similarity.getImagePath() + " - Similarity: " + similarity.getSimilarity());
        }
        return similarities;
    }

}

