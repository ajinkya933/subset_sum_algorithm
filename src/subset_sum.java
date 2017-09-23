/*

***************Problem Statement**************
*
Requirements:

Write a program which will process the data listed below. The data must be placed into a csv file and read from it.
The first variable is the target price and the following data values are menu items you could buy.
The program should then find a combination of dishes that has a total of exactly the target price.
If there is no solution if found, then the program should print that there is no combination of dishes that is equal
to the target price. The program must run with different data files, so provide instructions on how to run the program
from command line with the correct file. Use any programming language to solve this puzzle as long as it can be ran from
linux command line.
Here are some sample data values:

Target price, $15.05

mixed fruit,$2.15
french fries,$2.75
side salad,$3.35
hot wings,$3.55
mozzarella sticks,$4.20
sampler plate,$5.80



*/


package subset_sum;
import java.util.*;
import java.io.*;
public class subset_sum {


    // dp[i][j] is going to store true if sum j is
    // possible with array elements from 0 to i.
    static boolean[][] dp;


    public static ArrayList<Integer> getPrices() throws IOException
    {
        //Read csv file
        BufferedReader CSVFile = new BufferedReader(new FileReader("Example.csv"));

        String dataRow = CSVFile.readLine(); // Read first line.
        // The while checks to see if the data is null. If
        // it is, we've hit the end of the file. If not,
        // process the data.
        ArrayList<Integer> all_prices=new ArrayList<>();
        int count=0;
        while (dataRow != null){
            //extract data to data array using " , " as a seperator
            String[] dataArray = dataRow.split(",");
            String price = dataArray[1].substring(1,dataArray[1].length());
            float f_price = Float.parseFloat(price);
            //extract price from csv file. After extracting price multiply it by 100
            //please see line 41 for clearer explanation so as to why multiply price by 100
            int i_price= (int)(f_price*100);
            all_prices.add(i_price);

            //Print the input csv file that we are considering

            for (String item:dataArray) {
                System.out.print(item + "\t");
            }
            System.out.println(); // Print the data line.
            dataRow = CSVFile.readLine(); // Read next line of data.
        }

        // Multiply each input value of csv file by 100 ( this is done because the subset sum algorithm I
        //used works for integers:multyplying by 100 shifts two decimals ahed and becomes easy to calculate )
        //for example calculating digits like 2.01, 3.75 becomes difficult but 2.01*100=201 similarly 375,
        //makes operations on these rounded digits  favourable to subset sum algorithm. In the final result I am dividing
        //the output by 100 so as to get the real value
	        /*  for (int item:all_prices) {
	                System.out.print(item + "\t");
	            }*/

        System.out.print("\n"+"***********output***********" );

        // Close the file once all data has been read.
        CSVFile.close();

        // End the printout with a blank line.
        System.out.println();

        return all_prices;
    }

    static void display(ArrayList<Integer> v)
    {
        for(int i:v)
            System.out.println((float)i/100);
    }

    // A recursive function to print all subsets with the
    // help of dp[][]. Vector p[] stores current subset.
    static void printSubsetsRec(int arr[], int i, int sum,
                                ArrayList<Integer> p)
    {
        // If we reached end and sum is non-zero. We print
        // p[] only if arr[0] is equal to sun OR dp[0][sum]
        // is true.
        if (i == 0 && sum != 0 && dp[0][sum])
        {

            p.add(arr[i]);

            display(p);

            p.clear();
            System.out.print("\n"+"The combination whose addition equals Target Price" +"\n");
            return;


        }

        // If sum becomes 0
        if (i == 0 && sum == 0)
        {
            display(p);
            p.clear();
            return;
        }

        // If given sum can be achieved after ignoring
        // current element.
        if (dp[i-1][sum])
        {
            // Create a new vector to store path
            ArrayList<Integer> b = new ArrayList<>();
            b.addAll(p);
            printSubsetsRec(arr, i-1, sum, b);
        }

        // If given sum can be achieved after considering
        // current element.
        if (sum >= arr[i] && dp[i-1][sum-arr[i]])
        {
            p.add(arr[i]);
            printSubsetsRec(arr, i-1, sum-arr[i], p);
        }
    }

    // Prints all subsets of arr[0..n-1] with sum 0.
    static void printAllSubsets(int arr[], int n, int sum)
    {
        if (n == 0 || sum < 0)
            return;

        // Sum 0 can always be achieved with 0 elements
        dp = new boolean[n][sum + 1];
        for (int i=0; i<n; ++i)
        {
            dp[i][0] = true;
        }

        // Sum arr[0] can be achieved with single element
        if (arr[0] <= sum)
            dp[0][arr[0]] = true;

        // Fill rest of the entries in dp[][]
        for (int i = 1; i < n; ++i)
            for (int j = 0; j < sum + 1; ++j)
                dp[i][j] = (arr[i] <= j) ? (dp[i-1][j] ||
                        dp[i-1][j-arr[i]])
                        : dp[i - 1][j];
        if (dp[n-1][sum] == false)
        {
            System.out.println("There are no subsets for given target price");
            return;
        }

        // Now recursively traverse dp[][] to find all
        // paths from dp[n-1][sum]
        ArrayList<Integer> p = new ArrayList<>();
        printSubsetsRec(arr, n-1, sum, p);
    }

    //Driver Program to test above functions
    public static void main(String args[])
    {
        //int arr[] = {215, 275, 335, 355, 420, 580};
        ArrayList<Integer> all_prices = null;
        try
        {
            all_prices= getPrices();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        int arr[] = new int[all_prices.size()];

        for(int i=0;i<all_prices.size();i++)
            arr[i] = all_prices.get(i);


        int n = arr.length;
        // Enter Target Price here:
        double Target_price= 15.05;
        int sum = (int) (Target_price*100);
        printAllSubsets(arr, n, sum);

    }
}
