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

public final class Hash<K> implements IHash<K> {
    /*
    * Use some variables for your implementation.
    */
    
    private IHashFunction<K> hashFunc;
    private IResizeFunction resizeFunc;
    private int tableSize = 0;
    private int itemSize = 0;
    private K[] hashTable;
    
    public Hash(IHashFunction<K> h, IResizeFunction ex, int tablesize) {
        /*
         * Constructor
         * This function is an initializer for this class.
         * Input:
         *  + IHashFunction<K> h:
         *      int h.hash(K key, int tablesize): returns an integral hash value of key.
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

        for(int i = 0; i < tableSize; i++) {
            hashTable[i] = null;
        }
    }
    
    private K[] createHashTable(int tableSize) {
        K[] table = (K[]) new Object[tableSize];
        
        for(int i = 0; i < tableSize; i++) {
            table[i] = null;
        }

        return table;
    }

    private void linearProbing(K[] hashTable, K key, int hashCode) {
        // Since current hascode is not empty, start with next index
        int start = hashCode + 1;
        
        // Looping array
        while(start != hashCode) {
            // If index reached end of table, set the index to 0
            if(start >= tableSize) {
                start = 0;
            }

            // If slot is empty, insert the value and exit
            if(hashTable[start] == null) {
                hashTable[start] = key;
                return;
            }

            start++;
        }
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
         *  If the key is already in the hashtable, ignore the request.
         *  To decide whether two keys are equal,
         *  you must use _key.equals_ method.
         */
         
         // If key exist, return nothing
        if(exists(key)) {
            return;
        }

        // Get hash code from the key
        int hashCode = hashFunc.hash(key, tableSize);
        
        // Hashing and inserting
        if(hashTable[hashCode] == null) {
            hashTable[hashCode] = key;
        } else {
            linearProbing(hashTable, key, hashCode);
        }

        // Increase item size
        itemSize++;

        // If hash table has to be extended
        if(resizeFunc.checkResize(tableSize, itemSize)) {
            // Set a new table size
            tableSize = resizeFunc.extendTable(tableSize);

            // Create new table array
            K[] hashTable_temp = createHashTable(tableSize);

            // Rehashing
            for(int i = 0; i < hashTable.length; i++) {
                if(hashTable[i] != null) {
                    int hashCode_temp = hashFunc.hash(hashTable[i], tableSize);

                    if(hashTable_temp[hashCode_temp] == null) {
                        hashTable_temp[hashCode_temp] = hashTable[i];
                    } else {
                        linearProbing(hashTable_temp, hashTable[i], hashCode_temp);
                    }

                }
            }
            // Assign extended table to current table
            hashTable = hashTable_temp;
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
        
        // Get hash code of removal key
        int hashCode = hashFunc.hash(key, tableSize);

        // Get table size for looping 
        int limit = tablesize();

        // Scanning whole table to check if the removal key exist
        while(limit >= 0) {
            // If hash table has a key and its key is same as removal key, remove it and exit 
            if(hashTable[hashCode] != null && hashTable[hashCode].equals(key)) {
                hashTable[hashCode] = null;
                itemSize--;
                return;
            }

            // Increment index to check next slot
            hashCode++;

            // If index reaches the end of table, reset it to start from 0
            if(hashCode >= tableSize) {
                hashCode = 0;
            }

            // Decrement number of scannings
            limit--;
        }

        // Throw exception when key nothing is returned in while loop
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
        
        // Get hash code
        int hashCode = hashFunc.hash(key, tableSize);

        // For looping the table
        int limit = tablesize();

        // Scan throughout the table to check the key
        while(limit >= 0) {
            if(hashTable[hashCode] != null && hashTable[hashCode].equals(key)) {
                return true;
            }

            hashCode++;

            if(hashCode >= tableSize) {
                hashCode = 0;
            }

            limit--;
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
