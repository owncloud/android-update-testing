/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.support.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import update.model.OCFile;

public class FileSAXHandler extends DefaultHandler {

    private OCFile file;
    private ArrayList<OCFile> listFiles = new ArrayList<>();
    private String text = null;

    @Override
    public void startElement(String uri, String localName, String node, Attributes attributes) {
        if (node.equals("d:response")) {
            file = new OCFile();
        }
    }

    @Override
    public void endElement(String uri, String localName, String node) {
        switch (node) {
            case "d:href": {
                file.setPath(text);
                break;
            }
            case "oc:permissions": {
                file.setPermissions(text);
                break;
            }
            case "oc:size": {
                file.setSize(text);
                break;
            }
            case "oc:privatelink": {
                file.setPrivateLink(text);
                break;
            }
            case "d:getlastmodified": {
                file.setLastModified(text);
                break;
            }
            case "d:response": {
                file.setName(getFileNameFromPath(file.getPath()));
                listFiles.add(file);
                break;
            }
            case "d:getcontenttype": {
                file.setType(text);
                break;
            }
            default:
                break;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text = String.copyValueOf(ch, start, length).trim();
    }

    public ArrayList<OCFile> getListFiles() {
        return listFiles;
    }

    private String getFileNameFromPath(String path) {
        String[] pathSplitted = path.split("/");
        return URLDecoder.decode(pathSplitted[pathSplitted.length - 1], StandardCharsets.UTF_8);
    }
}
