import encrypt.RsaUtil;

public class Test {
    public static void main(String[] args) throws Exception {
        String privateKeyStr = "XI0mHxwHYkLA4BKnfSA8yLnq7ad0AwHcUoldSDQRKZjwlRUcKZ+C0Vo2kO2gxz6GdcsIj8Im0YZh7kveIRUjjQ==";
        String publicKeyStr =
                "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEA0NwhrZJcurgoMwIs" +
                "eRIsA+2xZNA1aGGLyRjCw4InEIA/0y4YziFP/G3OFivh7A8Sh/TiqK1y5rkcXvxe" +
                "CBAtxwIDAQABAkBrsETRGPkk8RURD9rcZgk50VG4+D3BXyJTAqhRZAc+UuNQHv9r" +
                "JLCmsavQLfKZa7W6u9BaWMgFiJv5YLFaE66pAiEA840Oznkicy+B3fj1GAqdP8eE" +
                "orPtIHvbFSZWG2lHG0MCIQDbiSEigvc+HG3ayAI+RnXeIKJZYT0BMWYQ9s8ifBRh" +
                "LQIgAgG7iX/+BKzziPywKE6OFSRzt1N8NLRQjdAPDZLEfzkCICmqLuWz4WSiVJ8P" +
                "Eo3rJXeQzf8Um1VcoLhbfxhWYC/JAiEAua3Mh0yZc68+13W57IN7v8e2/kBwmsml" +
                "0xrkRRwcJ84=" ;

        System.out.println(RsaUtil.decrypt(publicKeyStr,privateKeyStr));

//        System.out.println(RandomUtil.randomNumberByLength(6));


//        System.out.println(Md5Util.encode("1"));

//        System.out.println(AesUtil.encryptString("appsetr","EBS"));
//        System.out.println(AesUtil.decryptString("10C615A9E5E6125181F01B6D6F1AD340","EBS"));
//
//        System.out.println(AesUtil.encryptString("weblogic123","EBS"));
//        System.out.println(AesUtil.decryptString("A8C709E60599D0610492B079C724674B","EBS"));
    }
}
