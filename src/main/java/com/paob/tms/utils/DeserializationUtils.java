public class DeserializationUtils {
    
    // 不安全的反序列化风险
    public Object deserializeObject(byte[] data) {
        try {
            // 危险：不安全的反序列化
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}