public class SchoolList {
    //Adapted from CM1210 Lab Session 3
    //https://learningcentral.cf.ac.uk/bbcswebdav/pid-4554011-dt-content-rid-9691102_2/courses/1718-CM1210/%5BPDF%5D%20CM1209-CM1210%20Lab%20Session%203%20-%20A%20Class%20with%20More%20Classes%20and%20Reading-Writing%20Files.pdf
    
    private SchoolEntry[] entries;
    private int size;
    private int maxEntriesLength = 1;

    public SchoolList() {
        entries = new SchoolEntry[maxEntriesLength];
    }

    public void add(String name){
        //if the array is full
        if (size == entries.length){
            //double it
            maxEntriesLength = maxEntriesLength * 2;
            //create a new array with this size
            SchoolEntry[] temp = new SchoolEntry[maxEntriesLength];
            //move the contents of the old array into the new one
            System.arraycopy(entries, 0, temp, 0, entries.length);
            entries = temp;
        }
        //create a new entry in the array and increase size
        entries[size] = new School(name);
        size++;     
    }
}