
import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        File file = new File("C:\\Users\\Снежана\\IdeaProjects\\Serialization\\src\\basket.txt");

        String[] products = {"Молоко", "Хлеб", "ГречневаяКрупа", "Мясо", "Сахар"};
        int[] prices = {50, 15, 60, 120, 45};
        Basket basket = new Basket(prices, products);

        if (file.exists()) {
            basket = Basket.loadFromTxtFile(file);
        }
        print(products, prices);


        while (true) {
            System.out.println("Выберите товар и количество или введите 'end'");
            String input = scanner.nextLine();

            if (input.equals("end")) {
                basket.printCart();
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Нужно ввести номер товара и через пробел его количество ");
                continue;
            }
            int productNumber = 0;
            int productCount = 0;
            try {
                productNumber = Integer.parseInt(parts[0]) - 1;
                productCount = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Введены некорректные данные. Нужно вводить целые числа");
                continue;
            }
            if (productNumber > products.length || productNumber < 0 || productCount < 1) {
                System.out.println("Введен некорректный номер продукта или количества");
                continue;
            }
            String name = products[productNumber];
            int currentPrice = prices[productNumber];
            if (basket.getMapBasket().containsKey(name)) {
                int oldValue = basket.getMapBasket().get(name);
                basket.getMapBasket().put(name, productCount + oldValue);
            } else {
                basket.getMapBasket().put(name, productCount);
            }
            basket.setSumProducts(basket.getSumProducts() + currentPrice * productCount);

            basket.saveTxt(file);
        }
    }

    private static void print(String[] products, int[] prices) {
        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i] + " " + prices[i] + " " + "руб/шт");
        }
    }
}