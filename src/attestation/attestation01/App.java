/*
Промежуточная аттестация Модуль 1 «Введение в разработку/Введение в Java»

Формулировка задания: Необходимо реализовать приложение, принимающее
пользователей, продуктов и обрабатывающее покупку пользователя.

Подробное описание функционала приложения
1. Создать классы Покупатель (Person) и Продукт (Product).
Список характеристик покупателя: имя, сумма денег и пакет с продуктами
(массив объектов типа Продукт). Имя не может быть пустой строкой и не может
быть короче 3 символов. Деньги не могут быть отрицательным числом.
Если Покупатель может позволить себе Продукт, то Продукт добавляется
в пакет. Если у Покупателя недостаточно денег, то добавление не происходит.
Характеристики Продукта: название и стоимость. Название продукта не
может быть пустой строкой, оно должно быть. Стоимость продукта не может
быть отрицательным числом.
2. Поля в классах должны быть private, доступ к полям осуществляется
через геттеры и сеттеры или конструктор класса.
3. В классах переопределены методы toString(), equals(), hashcode().
4. Создать в классе App метод main и проверить работу приложения.
Данные Покупателей и Продукты вводятся с клавиатуры, для считывания
данных потребуется использовать класс Scanner и его метод nextLine().
Продукты в цикле выбираются покупателями по очереди и, пока не введено
слово END, наполняется пакет.
5. Обработать следующие ситуации:
а. Если покупатель не может позволить себе продукт, то напечатайте
соответствующее сообщение ("[Имя человека] не может позволить себе
[Название продукта]").
б. Если ничего не куплено, выведите имя человека, за которым
следует "Ничего не куплено".
в. В случае неверного ввода - сообщение: "Деньги не могут быть
отрицательными", пустого имени - сообщение: "Имя не может быть
пустым" или длина имени менее 3 символов – сообщение: "Имя не может
быть короче 3 символов".
 */

package attestation.attestation01;

import java.util.*; // Импорт всех утилит Java (коллекции, сканер и т.д.)
import java.util.stream.Collectors; // Для работы со Stream API


class Person { // Класс "Покупатель" - паспорт(карточка) клиента магазина
    private final String name; // // Имя (как в паспорте) - нельзя изменить
    private double money; // Текущий баланс (кошелек)
    private final List<Product> products = new ArrayList<>(); // Личная корзина покупок

    // Конструктор - обеспечивает процесс регистрации покупателя
    public Person(String name, double money) {
        // Проверка 1: Имя не пустое
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }

        String trimmedName = name.trim(); // Удаляем лишние пробелы

        // Проверка 2: Длина имени
        if (trimmedName.length() < 3) {
            throw new IllegalArgumentException(
                    "Имя не может быть короче 3 символов! Обладателям коротких имён из двух букв, " +
                            "таких как Ян, Ия, Эя, Ив, Ки, Ид, Юн, Ли и т.д., рекомендуется обратиться " +
                            "к администратору Магазина и обсудить дискриминацию по длине имени."
            ); // выводим текст ошибки (119 СТ. УК. РФ) =)
        }

        // Проверка 3: Баланса (баланс не может быть отрицательным значением)
        if (money < 0) {
            throw new IllegalArgumentException("Деньги не могут быть отрицательными"); // выводим текс ошибки
        }
        this.name = trimmedName; // Записываем имя в паспорт/карточку покупателя
        this.money = money; // деньги в кошелеке покупателя
    }

    // Геттеры - "просмотр через окошко" данных
    public String getName() { return name; } // Чтение имени
    public List<Product> getProducts() { return new ArrayList<>(products); } // Копия корзины


    // Покупка товара
    public void buyProduct(Product product) {
        if (product.getPrice() > money) { // Проверка бюджета покупателя
            System.out.printf("%s не может позволить себе %s%n", name, product.getName());
            return; // Уходим без покупки
        }
        money -= product.getPrice();// Списание средств со счёта покупателя
        products.add(product); // Кладем товар в корзину
        System.out.printf("%s купил %s%n", name, product.getName()); // Результат покупки
    }

    @Override
    public String toString() { // Как будет выглядеть покупатель на экране
        if (products.isEmpty()) return name + " - Ничего не куплено"; // Пустая корзина, сообщение что покупок нет
        return name + " - " + products.stream() // Преобразуем корзину в строку
                .map(Product::getName) // Берем только названия
                .collect(Collectors.joining(", ")); // Соединяем через запятую
    }

    // Методы сравнения
    @Override
    public boolean equals(Object o) { // Сравниваем по всем полям
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Double.compare(person.money, money) == 0 &&
                Objects.equals(name, person.name) &&
                Objects.equals(products, person.products);
    }

    @Override
    public int hashCode() { return Objects.hash(name, money, products); } // Уникальный код покупателя
}

//Класс "Продукт"
class Product {
    private final String name; // название продукта (не меняеться после создания)
    private final double price; //цена (фиксированная)

    // Конструктор реестра с продуктом(товаром)
    public Product(String name, double price) { // Процесс создания продукта

        if (name == null || name.trim().isEmpty()) { //проверка на ошибки названия
            throw new IllegalArgumentException("Название продукта не может быть пустой строкой"); // ошибка пустое название
        }
        if (price < 0) { //проверка на ошибки цены
            throw new IllegalArgumentException("Стоимость продукта не может иметь отрицательное значение"); // ошибка орицательная стоимось
        }
        this.name = name.trim(); // Форматируем название продукта
        this.price = price; // Устанавливаем цену продукта
    }

    // геттеры и методы сравнения аналогично классу Person
    public String getName() { return name; } // Узнать название
    public double getPrice() { return price; }

    @Override
    public String toString() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name, price); }
}



class App {
    public static void main(String[] args) { // В вод данных в магазин
        Scanner scanner = new Scanner(System.in); // Сканер для ввода данных
        Map<String, Person> people = new HashMap<>(); // База покупателей
        Map<String, Product> products = new HashMap<>();// База продуктов

        try { // процес покупок

            // Этап 1: Регистрация покупателей
            System.out.println("Введите список покупателей в формате: Имя = Сумма; ...");
            parseInput(scanner.nextLine(), people, true);

            // Этап 2: Воод продуктов-товаров
            System.out.println("Введите список продуктов в формате: Продукт = Цена; ...");
            parseInput(scanner.nextLine(), products, false);

            // Этап 3: Процесс покупок
            System.out.println("Введите список покупок в формате: Имя - Продукт; ...");
            processPurchases(scanner.nextLine(), people, products);

        } catch (IllegalArgumentException e) { // вывод проблемы
            System.out.println("Кажется у нас возникла проблема: "+ e.getMessage());
            return;
        }

        people.values().forEach(System.out::println); // Финансовый отчет
    }

    private static void parseInput(String input, Map<String, ?> map, boolean isPerson) {
        for (String item : input.split(";")) { // Разделяем записи ";"
            String[] parts = item.split("="); // Делим на ключ-значение "="
            if (parts.length != 2) continue; // Пропускаем ошибочныее

            String key = parts[0].trim(); // Чистим имя/название
            double value = Double.parseDouble(parts[1].trim()); // Преобразуем число

            try { // Пытаемся создать объект
                if (isPerson) { // Для покупателей
                    ((Map<String, Person>)map).put(key, new Person(key, value));
                } else { // Для товаров
                    ((Map<String, Product>)map).put(key, new Product(key, value));
                }
            } catch (IllegalArgumentException e) { // Перехват ошибок
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    private static void processPurchases(String input, Map<String, Person> people, Map<String, Product> products) {
        for (String purchase : input.split(";")) {// Разбиваем покупки через ";"
            String[] parts = purchase.split("-"); // разделяем покупателя и товар через "-"
            if (parts.length != 2) continue;

            String personName = parts[0].trim();// Ищем покупателя
            String productName = parts[1].trim();// Ищем продукт-товар

            Person person = people.get(personName); // Достаем из базы
            Product product = products.get(productName);

            if (person != null && product != null) { // Если персона и продукт существуют
                person.buyProduct(product); // Совершаем покупку
            }
        }
    }
}