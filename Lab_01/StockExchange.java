public class StockExchange {
    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        System.out.println(maxProfit(new int[]{1, 2, 3, 4, 5}));
        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1}));
    }

    public static int maxProfit(int[] prices) {
        int maxProfit = 0;

        for (int i = 0; i < prices.length - 1; i++) {
            int currentProfit = maxProfitInOneInterval(prices, 0, i)
                    + maxProfitInOneInterval(prices, i, prices.length);

            if(currentProfit > maxProfit){
                maxProfit = currentProfit;
            }
        }

        return maxProfit;
    }

    public static int maxProfitInOneInterval(int[] prices, int startIndex, int endIndex) {
        int maxProfit = 0;

        for (int i = startIndex; i < endIndex - 1; i++){
            for (int j = i+1; j<endIndex; j++){
                int currentProfit = prices[j] - prices[i];
                if(currentProfit > maxProfit){
                    maxProfit = currentProfit;
                }
            }
        }

        return maxProfit;
    }
}
