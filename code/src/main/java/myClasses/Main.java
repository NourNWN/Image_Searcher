package myClasses;

import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {

        SearchByColor imageSearch = new SearchByColor();
        SearchBySize searchBySize = new SearchBySize();
        SearchByDate searchByDate = new SearchByDate();
        KMeans kMeans = new KMeans();
        ColorHistogramViewer colorHistogramViewer = new ColorHistogramViewer();

       /* Uniform uniform = new Uniform();
        Quadtree quadtree = new Quadtree();
        IndexedImage indexedImage = new IndexedImage();
        ColorPaletteViewer palette = new ColorPaletteViewer();
        ColorHistogramViewer histogram = new ColorHistogramViewer();
       Search search = new Search();*/

       /* BufferedImage image = kMeans.process("C:/testImages/photos/inverted-color-close-up-of-a-citrus-slice (47).png", 8);
           uniform.uniform("C:/folder/win.png", 8, "C:/folder/uniform.png");
        quadtree.quadtree("C:/folder/win.png", 8, "C:/folder/quadtree.png");
        indexedImage.indexedImage("C:/folder/kmeans.png", 64, "C:/folder/indexed.png");
        palette.colorPalette("C:/folder/indexed.png");
        histogram.taregetColorHistogram(image);
        histogram.colorHistogram(51, 204, 204, image);*/

       // BufferedImage image = IOFils.loadImage("C://testImages//photos//inverted-color-close-up-of-a-citrus-slice (55).jpg");
       //colorHistogramViewer.calculateColorHistogram(image, 0, 0, 0);
        // imageSearch.searchByColor("C:/testImages/photos/inverted-color-close-up-of-a-citrus-slice (62).png", new String[]{"C:/testImages/New folder/", "C:/testImages/New folder (2)/", "C:/testImages/New folder (3)/"});
        // searchBySize.searchBySize("C:/testImages/photos/inverted-color-close-up-of-a-citrus-slice (40).png", new String[]{"C:/testImages/New folder/", "C:/testImages/New folder (2)/", "C:/testImages/photos/"});
        //searchByDate.searchByDate("C:/testImages/photos/inverted-color-close-up-of-a-citrus-slice (40).png", new String[]{"C:/testImages/New folder/", "C:/testImages/New folder (2)/", "C:/testImages/photos/"});
    }

}


