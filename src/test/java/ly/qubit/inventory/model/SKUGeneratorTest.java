package ly.qubit.inventory.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SKUGeneratorTest {

    @Test
    public void skuShouldBeGeneratedCorrectly(){
        String productName = "Test Product";
        int size = 10;
        String expectedSKU = "TP010";
        String actualSKU = SKUGenerator.generateSKU(productName, size);
        assertEquals(expectedSKU, actualSKU);
    }


        @Test
        public void testGenerateSKU() {
            String productName = "First Cold Press";
            int size = 128;
            String sku = SKUGenerator.generateSKU(productName, size);

            // Check that the SKU starts with the expected prefix
            String expectedPrefix = "FCP";
            assertTrue(sku.startsWith(expectedPrefix), "SKU should start with " + expectedPrefix);

            // Check that the SKU ends with the expected suffix
            String expectedSuffix = String.valueOf(size);
            assertTrue(sku.endsWith(expectedSuffix), "SKU should end with " + expectedSuffix);

            // Check that the SKU has the expected length
            int expectedLength = expectedPrefix.length() + expectedSuffix.length();
            assertEquals(expectedLength, sku.length(), "SKU should have length " + expectedLength);
        }
        @Test
        public void skuShortSuffixShouldFillwithZeros() {
            String productName = "First Cold Press";
            int size = 8;
            String sku = SKUGenerator.generateSKU(productName, size);

            // Check that the SKU starts with the expected prefix
            String expectedPrefix = "FCP";
            assertTrue(sku.startsWith(expectedPrefix), "SKU should start with " + expectedPrefix);

            // Check that the SKU ends with the expected suffix
            String expectedSuffix = "008"; // Size is now always a three-digit number
            assertTrue(sku.endsWith(expectedSuffix), "SKU should end with " + expectedSuffix);

            // Check that the SKU has the expected length
            int expectedLength = expectedPrefix.length() + expectedSuffix.length();
            assertEquals(expectedLength, sku.length(), "SKU should have length " + expectedLength);
        }
    @Test
    public void ProductNameWithOneWordShouldTakeFirst3LittersAsSKUPrefix() {
        String productName = "oneWord";
        int size = 8;
        String sku = SKUGenerator.generateSKU(productName, size);

        // Check that the SKU starts with the expected prefix
        String expectedPrefix = "ONE";
        assertTrue(sku.startsWith(expectedPrefix), "SKU should start with " + expectedPrefix);

        // Check that the SKU ends with the expected suffix
        String expectedSuffix = "008"; // Size is now always a three-digit number
        assertTrue(sku.endsWith(expectedSuffix), "SKU should end with " + expectedSuffix);

        // Check that the SKU has the expected length
        int expectedLength = expectedPrefix.length() + expectedSuffix.length();
        assertEquals(expectedLength, sku.length(), "SKU should have length " + expectedLength);
    }
    @Test
    public void ProductNameWithTowWordsShouldTakeFirstLittersUppercaseAsSKUPrefix() {
        String productName = "Tow Word";
        int size = 64;
        String sku = SKUGenerator.generateSKU(productName, size);

        // Check that the SKU starts with the expected prefix
        String expectedPrefix = "TW";
        assertTrue(sku.startsWith(expectedPrefix), "SKU should start with " + expectedPrefix);

        // Check that the SKU ends with the expected suffix
        String expectedSuffix = "064"; // Size is now always a three-digit number
        assertTrue(sku.endsWith(expectedSuffix), "SKU should end with " + expectedSuffix);

        // Check that the SKU has the expected length
        int expectedLength = expectedPrefix.length() + expectedSuffix.length();
        assertEquals(expectedLength, sku.length(), "SKU should have length " + expectedLength);
    }
    }


