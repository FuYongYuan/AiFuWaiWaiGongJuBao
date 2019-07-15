import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;

public class CodecTest
{
   public static void testBase64()
   {
      System.out.println("==============Base64================");
      byte[] data = "FYY".getBytes();
      Base64 base64 = new Base64();
      String encode = base64.encodeAsString(data);
      System.out.println(encode);
      System.out.println(new String(base64.decode(encode)));
   }

   public static void testMD5()
   {
      System.out.println("==============MD5================");
      String result = DigestUtils.md5Hex("FYY");
      System.out.println(result);
   }

   public static void testURLCodec() throws Exception
   {
      System.out.println("==============URLCodec================");
      URLCodec codec = new URLCodec();
      String data = "傅永源";
      String encode = codec.encode(data, "UTF-8");
      System.out.println(encode);
      System.out.println(codec.decode(encode, "UTF-8"));
   }
}