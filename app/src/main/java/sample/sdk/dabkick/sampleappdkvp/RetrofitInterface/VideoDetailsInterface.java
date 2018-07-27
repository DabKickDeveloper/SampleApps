package sample.sdk.dabkick.sampleappdkvp.RetrofitInterface;

import sample.sdk.dabkick.sampleappdkvp.PlayListModels.PlayListModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface VideoDetailsInterface {

    //Get Playlist details
    @GET
    Call<PlayListModel> getPlayLists(@Url String url);
}
