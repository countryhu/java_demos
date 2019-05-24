package generics;


// 验证 手动强转 和 自动转 其实是做了一样的事情
// 1. 手动强转 反编译 字节码
// 2. 自动转 反编译 字节码
// 3. 字节码逻辑一样，说明编译器在哪里做事,->其实效果一样。
public class SimpleHolder {
    private Object obj;
    void set(Object obj) {
        this.obj = obj;
    }
    Object get() {
        return obj;
    }

    public static void main(String[] args) {
        SimpleHolder simpleHolder = new SimpleHolder();
        simpleHolder.set("a");
        String obj = (String)simpleHolder.get();
        /**
         *  javap -c SimpleHolder.class
         * Compiled from "SimpleHolder.java"
         * public class generics.SimpleHolder {
         *   public generics.SimpleHolder();
         *     Code:
         *        0: aload_0
         *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         *        4: return
         *
         *   void set(java.lang.Object);
         *     Code:
         *        0: aload_0
         *        1: aload_1
         *        2: putfield      #2                  // Field obj:Ljava/lang/Object;
         *        5: return
         *
         *   java.lang.Object get();
         *     Code:
         *        0: aload_0
         *        1: getfield      #2                  // Field obj:Ljava/lang/Object;
         *        4: areturn
         *
         *   public static void main(java.lang.String[]);
         *     Code:
         *        0: new           #3                  // class generics/SimpleHolder
         *        3: dup
         *        4: invokespecial #4                  // Method "<init>":()V
         *        7: astore_1
         *        8: aload_1
         *        9: ldc           #5                  // String a
         *       11: invokevirtual #6                  // Method set:(Ljava/lang/Object;)V
         *       14: aload_1
         *       15: invokevirtual #7                  // Method get:()Ljava/lang/Object;
         *       18: checkcast     #8                  // class java/lang/String
         *       21: astore_2
         *       22: return
         * }
         */
    }
}

class SimpleHolder1<T> {
    private T obj;
    void set(T obj) {
        this.obj = obj;
    }
    T get() {
        return obj;
    }

    public static void main(String[] args) {
        SimpleHolder1<String> simpleHolder = new SimpleHolder1<String>();
        simpleHolder.set("a");
        String obj = simpleHolder.get();
        /**
         * javap -c SimpleHolder1.class
         * Compiled from "SimpleHolder.java"
         * class generics.SimpleHolder1<T> {
         *   generics.SimpleHolder1();
         *     Code:
         *        0: aload_0
         *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         *        4: return
         *
         *   void set(T);
         *     Code:
         *        0: aload_0
         *        1: aload_1
         *        2: putfield      #2                  // Field obj:Ljava/lang/Object;
         *        5: return
         *
         *   T get();
         *     Code:
         *        0: aload_0
         *        1: getfield      #2                  // Field obj:Ljava/lang/Object;
         *        4: areturn
         *
         *   public static void main(java.lang.String[]);
         *     Code:
         *        0: new           #3                  // class generics/SimpleHolder1
         *        3: dup
         *        4: invokespecial #4                  // Method "<init>":()V
         *        7: astore_1
         *        8: aload_1
         *        9: ldc           #5                  // String a
         *       11: invokevirtual #6                  // Method set:(Ljava/lang/Object;)V
         *       14: aload_1
         *       15: invokevirtual #7                  // Method get:()Ljava/lang/Object;
         *       18: checkcast     #8                  // class java/lang/String
         *       21: astore_2
         *       22: return
         * }
         */
    }
}
