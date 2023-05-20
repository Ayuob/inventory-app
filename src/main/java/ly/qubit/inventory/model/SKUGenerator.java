package ly.qubit.inventory.model;

import org.springframework.stereotype.Service;

public class SKUGenerator{

    public static String generateSKU(String productName, int size) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name must not be null or empty");
        }

        String[] words = productName.split(" ");
        StringBuilder skuBuilder = new StringBuilder();

        if (words.length == 1 && productName.length() >= 3) {
            skuBuilder.append(productName.substring(0, 3).toUpperCase());
        } else {
        for (String word : words) {
            skuBuilder.append(word.charAt(0));
        }}

        String formattedSize = String.format("%03d", size); // Pad the size with zeros
        skuBuilder.append(formattedSize);
        String sku = skuBuilder.toString().toUpperCase();
        return  sku;
    }


}
