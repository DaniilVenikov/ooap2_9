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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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

    public <T> void deepCopy(T target) throws Exception{
        try {
            target = getCopy();
        } catch (Exception e) {
            throw e;
        }
    }

    public <T> T deepClone() throws Exception {
        try {
            return getCopy();
        } catch (Exception e) {
            throw e;
        }
    }

    private <T> T getCopy() throws Exception  {
        try {
            var byteArrayOutputStream = new ByteArrayOutputStream();
            var objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject((T)this);
            var bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            var objectInputStream = new ObjectInputStream(bais);

            return (T) objectInputStream.readObject();
        }
        catch (Exception e) {
            throw e;
        }
    }

}

class Any extends General {

}

