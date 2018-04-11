package june11084.whatisthis.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import june11084.whatisthis.R;
import june11084.whatisthis.models.PhotoModel;
import june11084.whatisthis.services.CloudVisionService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
public class MainActivity extends AppCompatActivity {
    public ArrayList<PhotoModel> mPhotos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.demoi_mage);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        Log.v("imageString", imageEncoded);
        getPhotoResults(imageEncoded);
    }

    public void getPhotoResults(String imageData){
        CloudVisionService service = new CloudVisionService();
        CloudVisionService.scanPhoto(imageData, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.v("APICALL", "Succeed");
                mPhotos = CloudVisionService.processResults(response);
                Log.v("mPhotos", mPhotos.toString());
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter = new RestaurantListAdapter(getActivity(), mRestaurants, mOnRestaurantSelectedListener);
//                        mRecyclerView.setAdapter(mAdapter);
//                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//                        mRecyclerView.setLayoutManager(layoutManager);
//                        mRecyclerView.setHasFixedSize(true);
//                    }
//                });
            }
        });
    }
}
