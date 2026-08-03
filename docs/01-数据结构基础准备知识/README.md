# 第 1 节 数据结构基础准备知识

本章整理学习数据结构前需要掌握的 Java 基础知识。

## 目录

- [1. Java 泛型](#1-java-泛型)
  - [1.1 为什么需要泛型](#11-为什么需要泛型)
  - [1.2 泛型命名约定](#12-泛型命名约定)
  - [1.3 泛型类](#13-泛型类)
  - [1.4 泛型接口](#14-泛型接口)
  - [1.5 泛型方法](#15-泛型方法)
  - [1.6 通配符与上下界](#16-通配符与上下界)
  - [1.7 PECS 原则](#17-pecs-原则)
  - [1.8 类型擦除](#18-类型擦除)
  - [1.9 使用限制与常见问题](#19-使用限制与常见问题)
  - [1.10 在 ArrayList 中的应用](#110-在-arraylist-中的应用)

---

## 1. Java 泛型

Java 泛型（Generics）是 JDK 5 引入的核心特性。

泛型的本质是**参数化类型（Parameterized Type）**：在定义类、接口或方法时，先把所操作的数据类型当作参数，等到使用时再指定具体类型。

例如：

```java
ArrayList<String> names = new ArrayList<>();
ArrayList<Integer> scores = new ArrayList<>();
```

同一个 `ArrayList` 可以保存不同类型的数据，但每个对象在创建时都会明确自己的元素类型。

### 1.1 为什么需要泛型

在泛型出现之前，Java 集合通常使用 `Object` 保存元素。

这会带来两个问题：

1. 取出元素时需要手动进行类型转换。
2. 编译器无法检查元素类型，错误可能到运行时才被发现。

#### 不使用泛型

```java
import java.util.ArrayList;
import java.util.List;

List list = new ArrayList();

list.add("Hello");
list.add(123); // 编译器允许放入不同类型

String value = (String) list.get(1);
```

`list.get(1)` 得到的是 `Integer`，却被转换为 `String`，运行时会抛出：

```text
ClassCastException
```

#### 使用泛型

```java
List<String> list = new ArrayList<>();

list.add("Hello");
// list.add(123); // 编译错误

String value = list.get(0);
```

使用泛型后，编译器知道集合只能保存 `String`：

- 放入错误类型时，编译器会直接报错。
- 取出元素时，不需要手动强制类型转换。

#### 泛型的主要优点

- **类型安全**：将一部分运行时错误提前到编译期发现。
- **减少强制类型转换**：代码更简洁，也更容易阅读。
- **提高复用性**：一份代码可以处理多种引用类型。

### 1.2 泛型命名约定

泛型参数可以使用合法的标识符，但通常使用单个大写字母：

| 字母 | 英文 | 常见用途 |
| --- | --- | --- |
| `T` | Type | 通用类型 |
| `E` | Element | 集合中的元素类型 |
| `K` | Key | 键的类型 |
| `V` | Value | 值的类型 |
| `N` | Number | 数值类型 |
| `R` | Result | 返回结果类型 |

这些只是命名约定，不是 Java 语法强制要求。

### 1.3 泛型类

在类名后面使用 `<T>` 声明类型参数：

```java
public class Box<T> {
    private T item;

    public void set(T item) {
        this.item = item;
    }

    public T get() {
        return this.item;
    }
}
```

使用时指定具体类型：

```java
Box<String> stringBox = new Box<>();
stringBox.set("Java");

String value = stringBox.get();
System.out.println(value);
```

这里的 `T` 在使用 `Box<String>` 时代表 `String`。

也可以使用其他类型：

```java
Box<Integer> integerBox = new Box<>();
integerBox.set(100);

Integer number = integerBox.get();
```

### 1.4 泛型接口

接口也可以声明泛型参数：

```java
public interface Container<T> {
    void add(T item);

    T get(int index);
}
```

实现泛型接口时有两种常见方式。

#### 方式一：实现类继续保留泛型

```java
public class MyList<T> implements Container<T> {
    @Override
    public void add(T item) {
        // 添加元素
    }

    @Override
    public T get(int index) {
        return null;
    }
}
```

使用时再指定具体类型：

```java
MyList<String> list = new MyList<>();
```

#### 方式二：实现类指定具体类型

```java
public class StringList implements Container<String> {
    @Override
    public void add(String item) {
        // 添加字符串
    }

    @Override
    public String get(int index) {
        return null;
    }
}
```

此时 `StringList` 只能按照 `String` 类型实现接口方法。

### 1.5 泛型方法

泛型方法拥有自己的类型参数，它可以定义在普通类或泛型类中。

泛型参数 `<T>` 要写在返回值类型前面：

```java
public class ArrayUtils {
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}
```

调用时，编译器通常可以自动推导 `T`：

```java
Integer[] numbers = {1, 2, 3};
String[] names = {"Java", "Python"};

ArrayUtils.printArray(numbers);
ArrayUtils.printArray(names);
```

注意下面两个方法的区别：

```java
public <T> T getValue(T value) {
    return value;
}
```

这是泛型方法，`T` 属于方法本身。

```java
public T getValue() {
    return value;
}
```

这不是泛型方法，它使用的是泛型类已经声明的 `T`。

### 1.6 通配符与上下界

假设：

```java
class Animal {
}

class Dog extends Animal {
}
```

虽然 `Dog` 是 `Animal` 的子类，但是：

```text
List<Dog> 不是 List<Animal> 的子类
```

Java 泛型默认具有不变性。下面的赋值不能通过编译：

```java
List<Dog> dogs = new ArrayList<>();
// List<Animal> animals = dogs; // 编译错误
```

为了更灵活地处理不同泛型类型，可以使用通配符 `?`。

| 写法 | 名称 | 可以匹配 |
| --- | --- | --- |
| `<?>` | 无界通配符 | 任意未知类型 |
| `<? extends T>` | 上界通配符 | `T` 或 `T` 的子类 |
| `<? super T>` | 下界通配符 | `T` 或 `T` 的父类 |

#### 无界通配符

```java
public static void printSize(List<?> list) {
    System.out.println(list.size());
}
```

`List<?>` 表示元素类型未知，因此可以接收：

```java
List<String>
List<Integer>
List<Dog>
```

但由于具体类型未知，通常不能向其中添加非 `null` 元素。

#### 上界通配符

```java
public static void printNumbers(List<? extends Number> list) {
    for (Number number : list) {
        System.out.println(number);
    }
}
```

它可以接收：

```java
List<Integer>
List<Double>
List<Float>
```

可以安全地把元素读取为 `Number`，但不能安全地添加具体数值：

```java
// list.add(10); // 编译错误
```

因为编译器不知道实际传入的是 `List<Integer>`、`List<Double>`，还是其他 `Number` 子类的列表。

#### 下界通配符

```java
public static void addIntegers(List<? super Integer> list) {
    list.add(100);
    list.add(200);
}
```

它可以接收：

```java
List<Integer>
List<Number>
List<Object>
```

可以安全地写入 `Integer`，但读取时只能确定得到的是 `Object`：

```java
Object value = list.get(0);
```

### 1.7 PECS 原则

PECS 是：

```text
Producer Extends, Consumer Super
```

- **Producer Extends**：集合向外提供数据，主要用于读取时，使用 `? extends T`。
- **Consumer Super**：集合接收数据，主要用于写入时，使用 `? super T`。

例如，把一个集合的数据复制到另一个集合：

```java
public static <T> void copy(
        List<? extends T> source,
        List<? super T> target) {

    for (T value : source) {
        target.add(value);
    }
}
```

这里：

- `source` 生产数据，所以使用 `extends`。
- `target` 接收数据，所以使用 `super`。

PECS 是选择通配符时的经验原则，不代表 `extends` 完全不能读写，也不代表 `super` 只能写不能读。准确地说：

- `? extends T` 可以安全读取为 `T`，但通常不能添加非 `null` 元素。
- `? super T` 可以安全添加 `T` 及其子类对象，但读取结果通常只能当作 `Object`。

### 1.8 类型擦除

Java 泛型主要通过**类型擦除（Type Erasure）**实现。

泛型类型检查主要发生在编译期。编译器生成字节码时，会把类型参数替换为它的擦除类型，并在需要的位置插入类型转换。

#### 无上界类型参数

编译前：

```java
public class Holder<T> {
    private T value;

    public T getValue() {
        return this.value;
    }
}
```

理解擦除后的核心结构：

```java
public class Holder {
    private Object value;

    public Object getValue() {
        return this.value;
    }
}
```

没有显式上界的 `T`，擦除后通常按 `Object` 处理。

#### 有上界类型参数

正确写法是：

```java
public class NumberBox<T extends Number> {
    private T value;
}
```

`T` 擦除后的类型是它的第一个上界 `Number`，而不是 `Object`。

#### 编译器插入类型转换

源代码：

```java
Holder<String> holder = new Holder<>();
String value = holder.getValue();
```

可以将编译后的核心行为理解为：

```java
String value = (String) holder.getValue();
```

这个强制类型转换由编译器帮助完成。

#### 桥接方法

泛型类参与继承和方法重写时，类型擦除可能造成方法签名变化。编译器会在必要时生成桥接方法（Bridge Method），用来维持 Java 的多态行为。

#### 为什么使用类型擦除

一个重要原因是保持与旧版本 Java 代码及字节码的兼容性。

需要注意：类型擦除不等于 `.class` 文件中绝对不存在任何泛型描述。类文件可以保留泛型签名等元数据供反射和工具读取，但 JVM 执行泛型对象时，通常不会为 `ArrayList<String>` 和 `ArrayList<Integer>` 创建两套不同的运行时类。

```java
ArrayList<String> strings = new ArrayList<>();
ArrayList<Integer> integers = new ArrayList<>();

System.out.println(strings.getClass() == integers.getClass()); // true
```

### 1.9 使用限制与常见问题

#### 1. 泛型不能直接使用基本数据类型

错误：

```java
// List<int> numbers;
```

正确：

```java
List<Integer> numbers = new ArrayList<>();
```

泛型类型参数只能使用引用类型。基本类型可以使用对应的包装类：

| 基本类型 | 包装类 |
| --- | --- |
| `int` | `Integer` |
| `long` | `Long` |
| `double` | `Double` |
| `char` | `Character` |
| `boolean` | `Boolean` |

#### 2. 不能直接创建泛型数组

错误：

```java
// T[] array = new T[10];
```

Java 数组在运行时需要知道自己的实际元素类型，但泛型类型参数会发生擦除，所以不能直接创建 `new T[]`。

有时可以通过强制转换实现：

```java
@SuppressWarnings("unchecked")
T[] array = (T[]) new Object[10];
```

但是这种写法存在 unchecked 警告，并且需要谨慎使用。

实现类似 `ArrayList<E>` 的结构时，更常见的方式是内部使用：

```java
private Object[] elementData;
```

存入时接收 `E`，取出时再转换为 `E`：

```java
public E get(int index) {
    return (E) this.elementData[index];
}
```

#### 3. 静态成员不能直接使用类的泛型参数

错误：

```java
public class Box<T> {
    // private static T value;
}
```

静态成员属于类本身，不属于某一个具体对象。

下面两个对象使用了不同的类型参数：

```java
Box<String> stringBox = new Box<>();
Box<Integer> integerBox = new Box<>();
```

但它们共享同一份静态成员，所以静态字段无法确定应该使用 `String` 还是 `Integer`。

静态泛型方法是允许的，因为它会声明自己的类型参数：

```java
public static <T> T getFirst(T[] array) {
    return array[0];
}
```

这里的 `T` 属于 `getFirst` 方法，不是类的类型参数。

#### 4. 不能定义泛型异常类

错误：

```java
// class MyException<T> extends Exception {
// }
```

Java 不允许泛型类直接或间接继承 `Throwable`。

因此也不能通过泛型参数区分需要捕获的异常类型。

#### 5. 避免使用裸类型

下面的代码是裸类型（Raw Type）：

```java
List list = new ArrayList();
```

它主要用于兼容泛型出现之前的旧代码，但会失去编译期类型检查。

推荐写法：

```java
List<String> list = new ArrayList<>();
```

#### 6. 泛型类型不能仅靠类型参数进行重载

下面两个方法不能同时存在：

```java
// public void print(List<String> list) {
// }

// public void print(List<Integer> list) {
// }
```

类型擦除后，它们都会变成类似：

```java
public void print(List list)
```

因此会产生方法签名冲突。

#### 7. 不能使用 `instanceof` 检查具体泛型参数

错误：

```java
// if (value instanceof List<String>) {
// }
```

可以检查原始类型或无界通配符：

```java
if (value instanceof List<?>) {
    System.out.println("value 是一个 List");
}
```

### 1.10 在 ArrayList 中的应用

`ArrayList<E>` 中的 `E` 代表元素类型：

```java
public class ArrayList<E> {
    transient Object[] elementData;
    private int size;
}
```

为什么底层数组是 `Object[]`，而不是直接创建 `E[]`？

因为下面的写法不合法：

```java
// E[] elementData = new E[10];
```

所以 `ArrayList` 内部使用 `Object[]` 保存对象，对外通过 `E` 提供类型安全。

简化实现：

```java
public class MyArrayList<E> {
    private Object[] elementData = new Object[10];
    private int size;

    public void add(E value) {
        this.elementData[this.size] = value;
        this.size++;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) this.elementData[index];
    }

    public int size() {
        return this.size;
    }
}
```

使用时：

```java
MyArrayList<String> list = new MyArrayList<>();

list.add("Java");
// list.add(100); // 编译错误

String value = list.get(0);
```

这里可以看到泛型的作用：

1. `add(E value)` 限制放入的元素类型。
2. `get(int index)` 对外返回 `E`，调用者不需要手动强转。
3. 内部仍然可以通过 `Object[]` 管理不同引用类型的数据。

---

## 本节总结

1. 泛型是把类型作为参数，提高代码的类型安全性和复用性。
2. 泛型类、泛型接口和泛型方法的类型参数作用域不同。
3. `? extends T` 适合读取，`? super T` 适合写入，可以使用 PECS 帮助判断。
4. Java 泛型主要通过类型擦除实现，无上界参数通常擦除为 `Object`，有上界参数擦除为第一个上界。
5. 泛型不能直接使用基本类型，也不能直接创建 `new T[]`。
6. `ArrayList<E>` 对外使用 `E` 保证类型安全，内部使用 `Object[]` 保存元素。
