import couchbase.entity.Document;

public class CentextTest extends Document {
    public String Name;

    public CentextTest(String name) {
        Name = name;
    }

    public CentextTest() {
    }

    @Override
    public String toString() {
        return "CentextTest{" +
                "Name='" + Name + '\'' +
                ", Id='" + Id + '\'' +
                ", ClassTypeName='" + ClassTypeName + '\'' +
                ", CreateTime=" + CreateTime +
                ", CreateManId='" + CreateManId + '\'' +
                ", UpdateTime=" + UpdateTime +
                ", UpdateManId='" + UpdateManId + '\'' +
                ", IsValidity=" + IsValidity +
                '}';
    }
}
