package typeinfo.pets;

 import sun.tools.jstat.Literal;

 import java.lang.System;
 import java.util.*;


class Pet {
}

class Dog extends Pet {
}

// 杂种狗
class Mutt extends Dog {
}

// 哈巴狗
class Pug extends Dog {
}

///---
class Cat extends Pet {
}

// 马恩岛猫
class Manx extends Cat {
}

// 啮齿动物
class Rodent extends Pet {
}

class Mouse extends Rodent {
}

abstract class PetCreator {
    private Random random = new Random(47);
    public abstract List<Class<? extends Pet>> types();
    Pet randomPet() {
        int n = random.nextInt(types().size());
        try {
            return types().get(n).newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

class LiteralPetCreator extends PetCreator {
    private final List<Class<? extends Pet>> allTypes =
            Collections.unmodifiableList(Arrays.asList(
                    Pet.class,
                    Dog.class,
                    Cat.class,
                    Rodent.class,
                    Mutt.class,
                    Pug.class,
                    Manx.class,
                    Mouse.class));
    private final List<Class<? extends Pet>> types = allTypes.subList(
            allTypes.indexOf(Mutt.class), allTypes.size());
    public List<Class<? extends Pet>> types() {
        return types;
    }
}

class Pets {
    // 默认采用LiteralPetCreator创建策略
    private static final PetCreator creater = new LiteralPetCreator();
    public static Pet[] createArray(int size) {
        Pet[] pets = new Pet[size];
        for (int i = 0; i < size; ++i) {
            pets[i] = creater.randomPet();
        }
        return pets;
    }
}

class TypeCounter extends HashMap<Class<?>, Integer> {
    private Class<?> baseType;
    public TypeCounter(Class<?> baseType) {
        this.baseType = baseType;
    }

    public void count(Object obj) {
        Class type = obj.getClass();
        if (!baseType.isAssignableFrom(type)) {
            throw new RuntimeException(obj + "incorrect type:" + type + ", should be type" +
                    " or subtype of " + baseType);
        }
        countClass(type);
    }
    private void countClass(Class<?> type) {
        Integer quantity = get(type);
        put(type, quantity == null  ? 1 : quantity + 1);

        Class<?> supperClass = type.getSuperclass();
        if (supperClass != null && baseType.isAssignableFrom(supperClass)) {
            countClass(supperClass);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for(Map.Entry<Class<?>, Integer> pair : entrySet()) {
            result.append(pair.getKey().getSimpleName());
            result.append("=");
            result.append(pair.getValue());
            result.append(", ");
        }
        result.delete(result.length() - 2, result.length());
        result.append("}");
        return result.toString();
    }
}

// 随机生成pet子类 20个
// 统计出每个子类的数目
public class PetCount4 {
    public static void main(String[] args) {
        TypeCounter counter = new TypeCounter(Pet.class);
        for (Pet pet : Pets.createArray(10)) {
            System.out.print(pet.getClass().getSimpleName() + " ");
            counter.count(pet);
        }
        System.out.println();
        System.out.println(counter);
    }
}
