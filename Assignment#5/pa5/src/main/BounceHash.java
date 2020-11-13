/*
* Name: Young Woo Ju
* Student ID#: 2014122074
*/

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
/*
* Do NOT use any external packages/classes.
* If you (un)intentionally use them we did not provide,
* you will get 0.
* Also do NOT use auto-import function on IDEs.
* If the import statements change, you will also get 0.
*/

public final class BounceHash<K> implements IHash<K> {
    /*
    * Use some variables for your implementation.
    */
    
    private IBounceHashFunction<K> hashFunc;
    private IResizeFunction resizeFunc;
    private int tableSize = 0;
    private int itemSize = 0;
    private K[] hashTable;
    
    public BounceHash(IBounceHashFunction<K> h, IResizeFunction ex, int tablesize) {
        /*
         * Constructor
         * This function is an initializer for this class.
         * Input:
         *  + IBounceHashFunction<K> h:
         *      int h.hash1(K key, int tablesize): returns an integral hash value of key with h_1.
         *      int h.hash2(K key, int tablesize): returns an integral hash value of key with h_2.
         *      void h.changeHashFn(int tablesize): change the hash functions for rehashing.
         *  + IResizeFunction ex:
         *      boolean ex.checkResize(int tablesize, int numItems): returns 'true' if the table must be extended for containing 'numItems' items. Otherwise, returns 'false'.
         *      int ex.extendTable(int tablesize): returns new tablesize for extended table.
         *  + tablesize: the initial table size of the hash table.
        */
        
        // Declaring variables
        this.hashFunc = h;
        this.resizeFunc = ex;
        this.tableSize = tablesize;

        // Creating table array
        this.hashTable = createHashTable(tableSize);
    }
    
    // Creating empty table array
    private K[] createHashTable(int tableSize) {
        K[] table = (K[]) new Object[tableSize];
        
        for(int i = 0; i < tableSize; i++) {
            table[i] = null;
        }

        return table;
    }

    // Bounce hashing
    private void bounceCheck(K[] table, K key, int hashCode) {
        // Maximum number of items that can be bounced 
        int limit = (size() + 1) * 2;

        // Keep original key separately
        K originalKey = key;

        // Make a copy of original hash table separately
        K[] originalTable = createHashTable(tableSize);
        
        for(int i = 0; i < table.length; i++) {
            originalTable[i] = table[i];
        }

        // Try inserting key and rebounce if another key already exist at hash index
        while(limit > -1) {
            // If slot if empty, insert key and exit
            if(table[hashCode] == null) {
                table[hashCode] = key;
                return;
            }

            // If another key exist, replace it with inserted key and bounce it
            K tempKey = table[hashCode];

            int tempHashCode1 = hashFunc.hash1(tempKey, tableSize);
            int tempHashCode2 = hashFunc.hash2(tempKey, tableSize);

            int bouncedHashCode = tempHashCode1;

            // if h1(x) = h1(y) >>> use h2(y)
            if(hashCode == tempHashCode1) {
                bouncedHashCode = tempHashCode2;
            }

            // Insert a key
            table[hashCode] = key;

            // Update bounce key andits hash code
            hashCode = bouncedHashCode;
            key = tempKey;

            // Decrease possible number of rebounce
            limit--;
        }

        // Reaching this point means a cycle exist
        // Change hash functions
        hashFunc.changeHashFn(tableSize);

        // Rehashing with original table
        K[] resizeTable = createHashTable(tableSize);

        for(int i = 0; i < originalTable.length; i++) {
            if(originalTable[i] != null) {
                bounceCheck(resizeTable, originalTable[i], hashFunc.hash1(originalTable[i], tableSize));
            }
        }
        hashTable = resizeTable;

        // Try inserting original key again
        bounceCheck(hashTable, originalKey, hashFunc.hash1(originalKey, tableSize));
    }

    @Override
    public void put(K key) {
        /**
         * Input:
         * + key: A key to be added 
         * 
         * Job:
         *  Add the key into the hashtable.
         *  If the table must be extended, extend the table and retry adding the key.
         *  If the insertion results in a cycle, 
         *  If the key is already in the hashtable, ignore the request.
         *  To decide whether two keys are equal,
         *  you must use _key.equals_ method.
         */

        // If a key exist, exit
        if(exists(key)) {
            return;
        }

        // Get hash code for input key
        int hashCode1 = hashFunc.hash1(key, tableSize);

        // Insert + checking for bouncing
        bounceCheck(hashTable, key, hashCode1);
        
        // Increase item size
        itemSize++;

        // Check resize status
        if(resizeFunc.checkResize(tableSize, itemSize)) {
            // Extend table size and rehash
            tableSize = resizeFunc.extendTable(tableSize);

            K[] resizeTable = createHashTable(tableSize);

            for(int i = 0; i < hashTable.length; i++) {
                if(hashTable[i] != null) {
                    bounceCheck(resizeTable, hashTable[i], hashFunc.hash1(hashTable[i], tableSize));
                }
            }
            hashTable = resizeTable;
        }
    }

    @Override
    public void remove(K key) throws IllegalStateException {
        /*
        * Input:
        *  + key: A key to be removed
        *
        * Job:
        *  Delete the key from the hash table.
        *  To decide whether two keys are equal,
        *  you must use _key.equals_ method.
        *  If the key does not exist in the table, raise an exception.
        */

                // Possible hash codes
        int hashCode1 = hashFunc.hash1(key, tableSize);
        int hashCode2 = hashFunc.hash2(key, tableSize);

        // Check first hash code
        if(hashTable[hashCode1] != null) {
            if(hashTable[hashCode1].equals(key)) {
                hashTable[hashCode1] = null;
                itemSize--;
                return;
            }
        }

        // Check second hash code
        if(hashTable[hashCode2] != null) {
            if(hashTable[hashCode2].equals(key)) {
                hashTable[hashCode2] = null;
                itemSize--;
                return;
            }
        }

        // Raise exception
        throw new IllegalStateException();
    }

    @Override
    public boolean exists(K key) {
        /*
        * Input:
        *  + key: A key to be checked
        *
        * Job:
        *  Return true if the key is in the table; false otherwise.
        *  To decide whether two keys are equal,
        *  you must use _key.equals_ method.
        */

        // Possible hash codes
        int hashCode1 = hashFunc.hash1(key, tableSize);
        int hashCode2 = hashFunc.hash2(key, tableSize);

        // Check first hash code
        if(hashTable[hashCode1] != null) {
            if(hashTable[hashCode1].equals(key)) {
                return true;
            }
        }

        // Check second hash code
        if(hashTable[hashCode2] != null) {
            if(hashTable[hashCode2].equals(key)) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public int size() {
        /*
        * Job:
        *  Return the number of items in the hashtable.
        */
        return itemSize;
    }

    @Override
    public int tablesize() {
        /*
        * Job:
        *  Return the size of current hashtable.
        */
        return tableSize;
    }

    @Override
    public List<K> show() {
        /*
        * Job:
        *  Return the items in the hashtable.
        *  The list index must be the bucket index of the item.
        *  If a bucket has no item, assign null.
        *  Note that you can use ArrayList.
        */
        List<K> items = new ArrayList<>();

        for(K code: hashTable) {
            items.add(code);
        }

        return items;
    }

}
