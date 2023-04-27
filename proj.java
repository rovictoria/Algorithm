
// Программа для операции вставки в красно-черном дереве - реализация
import java.util.Scanner;

class node {

    node left, right;
    int data;

    // красный - true, черный - false
    boolean color;

    node(int data) {
        this.data = data;
        left = null;
        right = null;

        // Новый узел - создается, всегда красного цвета.
        color = true;
    }
}

public class proj {

    private static node root = null;

    // Функция для поворота узла против часовой стрелки.
    node rotateLeft(node myNode) {
        System.out.printf("поворот влево!!\n");
        node child = myNode.right;
        node childLeft = child.left;

        child.left = myNode;
        myNode.right = childLeft;

        return child;
    }

    // Функция для поворота узла по часовой стрелке.
    node rotateRight(node myNode) {
        System.out.printf("вращение вправо\n");
        node child = myNode.left;
        node childRight = child.right;

        child.right = myNode;
        myNode.left = childRight;

        return child;
    }

    // Функция для проверки - является ли узел красного цвета.
    boolean isRed(node myNode) {
        if (myNode == null) {
            return false;
        }
        return (myNode.color == true);
    }

    // Функция для изменения цвета двух узлы.
    void swapColors(node node1, node node2) {
        boolean temp = node1.color;
        node1.color = node2.color;
        node2.color = temp;
    }

    // Вставка в левостороннее красно-черное дерево.
    node insert(node myNode, int data) {

        // Обычный код вставки для любого двоичного файла

        if (myNode == null) {
            return new node(data);
        }

        if (data < myNode.data) {
            myNode.left = insert(myNode.left, data);
        } else if (data > myNode.data) {
            myNode.right = insert(myNode.right, data);
        } else {
            return myNode;
        }

        // Случай 1.
        // когда правый дочерний элемент красный, а левый - черный или не существует.
        if (isRed(myNode.right) && !isRed(myNode.left)) {

            // Повернуть узел влево.
            myNode = rotateLeft(myNode);

            // Поменять местами цвета дочернего узла всегда должен быть красным
            swapColors(myNode, myNode.left);
        }

        // Случай 2
        // Когда левые (реб,вн) выделены красным цветом
        if (isRed(myNode.left) && isRed(myNode.left.left)) {

            // Повернуть узел вправо
            myNode = rotateRight(myNode);
            swapColors(myNode, myNode.right);
        }

        // Случай 3
        // когда и левый, и правый дочерние элементы окрашены в красный цвет.
        if (isRed(myNode.left) && isRed(myNode.right)) {

            // Инвертировать цвет узла это левый и правый дети.
            myNode.color = !myNode.color;

            // Изменить цвет на черный.
            myNode.left.color = false;
            myNode.right.color = false;
        }

        return myNode;
    }

    // Обход по порядку
    void inorder(node node) {
        if (node != null) {
            inorder(node.left);
            char c = '●';
            if (node.color == false)
                c = '◯';
            System.out.print(node.data + "" + c + " ");
            inorder(node.right);
        }
    }

    public static void main(String[] args) {

        proj node = new proj();
        Scanner scan = new Scanner(System.in);

        char ch;
        do {
            System.out.println("Введите целое число");

            int num = scan.nextInt();
            root = node.insert(root, num);

            node.inorder(root);
            System.out.println("\nВы хотите продолжить? Введите y или n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y' || ch == 'y');
    }
}