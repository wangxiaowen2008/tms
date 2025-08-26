public class DeserializationUtils {

    public Object deserializeObject(byte[] data) {
        try {
                // 反序列化对象，有漏洞，可以导致远程代码执行
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}