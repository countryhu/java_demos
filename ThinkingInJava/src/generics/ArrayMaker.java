package generics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayMaker<T> {
    private Class<T> kind;
    public ArrayMaker(Class<T> kind) {
        this.kind = kind;
    }

    @SuppressWarnings("unchecked")
    T[] create(int size) {
        return (T[])Array.newInstance(kind, size);
    }

    public static void main(String[] args) {
        ArrayMaker<String> stringArrayMaker =
                new ArrayMaker<>(String.class);
        String[] stringArray = stringArrayMaker.create(10);
        System.out.println(Arrays.toString(stringArray));
        // [null, null, null, null, null, null, null, null, null, null]
        // Class<T> 也被擦除了
    }
}


class ListMaker<T> {
    public List<T> create(int size) {
        return new ArrayList<T>(size);
    }
    public static void main(String[] args) {
        ListMaker<String> listMaker = new ListMaker<String>();
        List<String> list = listMaker.create(10);
        System.out.println(list);
    }
}

class ListMaker1<T> {
    public List<T> create(T t, int size) {
        ArrayList<T> arrayList = new ArrayList<T>(size);
        for (int i = 0; i < size; ++i) {
            arrayList.add(t);
        }
        return arrayList;
    }
    public static void main(String[] args) {
        ListMaker1<String> listMaker = new ListMaker1<String>();
        List<String> list = listMaker.create("a", 10);
        System.out.println(list);
        // [a, a, a, a, a, a, a, a, a, a]
        // 边界
    }
}