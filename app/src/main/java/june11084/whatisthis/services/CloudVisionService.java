package june11084.whatisthis.services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import june11084.whatisthis.Constants;
import june11084.whatisthis.models.PhotoModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CloudVisionService {
    public static void scanPhoto(String imageData, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.CLOUDVISION_BASE_URL+ "?key=" +Constants.CLOUDVISION_KEY).newBuilder();
        String url = urlBuilder.build().toString();

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        String apiCallBody = "{\n" +
                "  \"requests\":[\n" +
                "    {\n" +
                "      \"image\":{\n" +
                "        \"content\":\"" + imageData + "\"\n" +
                "      },\n" +
                "      \"features\":[\n" +
                "        {\n" +
                "          \"type\":\"LABEL_DETECTION\",\n" +
                "          \"maxResults\":1\n" +
                "        },\n" +
                "         {\n" +
                "          \"type\":\"WEB_DETECTION\",\n" +
                "          \"maxResults\":10\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Log.v("apiCallBody", apiCallBody);
        RequestBody body =  RequestBody.create(JSON, apiCallBody);

        Request request= new Request.Builder()
                .url(url)
                .header("Authorization", Constants.CLOUDVISION_KEY)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static ArrayList<PhotoModel> processResults(Response response) {
        Log.v("respons", response.toString());
        ArrayList<PhotoModel> photos = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            JSONObject CloudVisionJSON = new JSONObject(jsonData);
            JSONArray responseJSON = CloudVisionJSON.getJSONArray("responses");
            for (int i = 0; i < responseJSON.length(); i++) {

                JSONObject photoJSON = responseJSON.getJSONObject(i);

                ArrayList<String> labels = new ArrayList<>();
                JSONArray labelsJSON = photoJSON.getJSONArray("labelAnnotations");
                for (int y = 0; y < labelsJSON.length(); y++) {
                    labels.add(labelsJSON.getJSONObject(y).getString("description"));
                    Log.v("label", "label ran");
                }

                ArrayList<String> webLinks = new ArrayList<>();
                JSONObject webLinksJSON = photoJSON.getJSONObject("webDetection");
                JSONArray webEntitiesJSON = webLinksJSON.getJSONArray("webEntities");
                for (int y = 0; y < webEntitiesJSON.length(); y++) {
                    webLinks.add(webEntitiesJSON.getJSONObject(y).optString("description"));
                    Log.v("webLinks", "webLinks ran");
                }

                PhotoModel restaurant = new PhotoModel(labels, webLinks);
                photos.add(restaurant);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return photos;
    }
}
