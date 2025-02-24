import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

abstract class General implements Serializable, Cloneable {

    public Class<?> getType() {
        return this.getClass();
    }

    @Override
    public String toString() {
        return "Instance of " + this.getClass().getSimpleName();
    }

    public boolean isInstanceOf(Class<?> clazz) {
        return clazz.isInstance(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return Objects.equals(this.toString(), obj.toString());
    }

    public String serialize() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);

        out.writeObject(this);
        out.flush();

        return bos.toString(StandardCharsets.ISO_8859_1);
    }

    public static General deserialize(String data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data.getBytes(StandardCharsets.ISO_8859_1));
        ObjectInputStream in = new ObjectInputStream(bis);
        return (General) in.readObject();
    }

    public abstract General clone(); // создание нового объекта и глубокое копирование

    public void copy(General other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy from null");
        }

        deepCopy(other);
    }

    public abstract void deepCopy(General general);

}

class Any extends General {

    @Override
    public General clone() {
        Any any = new Any();
        deepCopy(any);

        return any;
    }

    @Override
    public void deepCopy(General general) {
        // логика глубокого копирования
    }
}

