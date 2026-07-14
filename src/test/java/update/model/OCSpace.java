/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.model;

public class OCSpace {

    private String type;
    private String id;
    private String name;

    public OCSpace(){
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
