/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.model;

public class OCFile {

    private String name;
    private String path;
    private String size;
    private String permissions;
    private String privateLink;
    private String lastModified;

    private String type;

    public OCFile() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void setPrivateLink(String privateLink) {
        this.privateLink = privateLink;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public void setType(String type) {
        this.type = type;
    }
}
