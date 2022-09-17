import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Basket implements Serializable{
    private static File file;
    private int[] prices;
    private String[] products;
    private Map<String, Integer> mapBasket = new HashMap<>();
    private int sumProducts ;
    private Map<String, Integer> mapPrices = new HashMap<>();

    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        for (int i = 0; i <products.length ; i++) {
            mapPrices.put(products[i], prices[i]);
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        FileReader fileReader = new FileReader(textFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String[] products = bufferedReader.readLine().split(" ");
        String[] prices = bufferedReader.readLine().split(" ");
        int[] intPrices = new int[prices.length];
        for (int i = 0; i < prices.length; i++) {
            intPrices[i] = Integer.parseInt(prices[i]);
        }
        Basket basket = new Basket(intPrices, products);
        basket.setSumProducts(Integer.parseInt(bufferedReader.readLine()));
        while (bufferedReader.ready()) {
            String string = bufferedReader.readLine();
            String[] strings = string.split(" ");
            basket.mapBasket.put(strings[0], Integer.parseInt(strings[1]));
        }
        bufferedReader.close();
        return basket;
    }

    public void saveTxt(File textFile) throws IOException {
        file = textFile;
        FileWriter fileWriter = new FileWriter(file);
        for (String product : products) {
            fileWriter.append(product + " ");
        }
        fileWriter.append("\n");
        for (int price : prices) {
            fileWriter.append(price + " ");
        }
        fileWriter.append("\n");
        fileWriter.append(String.valueOf(sumProducts)).append("\n");
        for (Map.Entry<String, Integer> stringIntegerEntry : mapBasket.entrySet()) {
            fileWriter.append(stringIntegerEntry.getKey() + " " + stringIntegerEntry.getValue() + "\n");
        }
        fileWriter.close();
    }

    public void printCart() {
        System.out.println("Ваша корзина: ");
        for (Map.Entry<String, Integer> stringIntegerEntry : mapBasket.entrySet())
        {
            String name = stringIntegerEntry.getKey();
            int price = mapPrices.get(stringIntegerEntry.getKey());
            int amount = stringIntegerEntry.getValue();
            int sum = price * amount;
            System.out.println(name+" "+ amount+" шт "+price+" руб/шт "+sum+" руб в сумме");
        }
        System.out.println("Итого "+sumProducts+" руб");
    }
    public void saveBin(File file) {
        try (FileOutputStream fos=new FileOutputStream(file)){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try (FileInputStream fis=new FileInputStream(file)){
            ObjectInputStream ois = new ObjectInputStream(fis);
            basket = (Basket) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return basket;
    }

    public Map<String, Integer> getMapBasket() {
        return mapBasket;
    }

    public void setMapBasket(Map<String, Integer> mapBasket) {
        this.mapBasket = mapBasket;
    }

    public int getSumProducts() {
        return sumProducts;
    }

    public void setSumProducts(int sumProducts) {
        this.sumProducts = sumProducts;
    }
}
