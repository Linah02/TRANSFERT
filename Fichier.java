package socket;
public class Fichier {
    private int id;
    private String name;
    private byte[] data;
    private String fileExtensions;

    public Fichier(int id,String name,byte[] data,String fileExtension)
    {
        setId(id);
        setName(name);
        setData(data);
        setFileExtensions(fileExtension);
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    public void setFileExtensions(String fileExtensions) {
        this.fileExtensions = fileExtensions;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public byte[] getData() {
        return data;
    }
    public String getFileExtensions() {
        return fileExtensions;
    }
    public String getName() {
        return name;
    }
}
