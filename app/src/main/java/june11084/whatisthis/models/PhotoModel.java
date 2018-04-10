package june11084.whatisthis.models;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class PhotoModel {
    List<String> labels = new ArrayList<>();
    List<String> webLinks = new ArrayList<>();

    public PhotoModel() {
    }

    public PhotoModel(ArrayList<String> label, ArrayList<String> webLink) {
        this.labels = label;
        this.webLinks = webLink;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<String> getWebLinks(){
        return webLinks;
    }
}
