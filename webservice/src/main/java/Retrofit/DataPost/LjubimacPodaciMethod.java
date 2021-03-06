package Retrofit.DataPost;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Retrofit.RemotePost.StatusListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Retrofit.RemotePost.LjubimacPodaciService;
public class LjubimacPodaciMethod {

    public static StatusListener listener;

    public LjubimacPodaciMethod(StatusListener statusListener){
        this.listener=statusListener;
    }

    /**
     * metoda za spremanje ljubimca na pslužitelj
     * @param ime
     * @param godina
     * @param masa
     * @param vrsta
     * @param spol
     * @param opis
     * @param url_slike
     * @param vlasnik
     * @param kartica
     * @param slika
     */
    public static void Upload(String ime, String godina, String masa, String vrsta, String spol, String opis, String url_slike, String vlasnik, String kartica, String slika) {

        final LjubimacPodaciResponse body = new LjubimacPodaciResponse();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit;
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://airprojekt.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LjubimacPodaciService api = retrofit.create(LjubimacPodaciService.class);

        LjubimacPodaciRequest request = new LjubimacPodaciRequest();

        request.setIme(ime);

        request.setGodina(godina);

        request.setMasa(masa);

        request.setVrsta(vrsta);

        request.setSpol(spol);

        request.setOpis(opis);

        request.setUrl_slike("/");

        request.setVlasnik(vlasnik);

        request.setKartica(kartica);

        request.setSlika(slika);

        Call<LjubimacPodaciResponse> LjubimacPodaciCall = api.createLjubimac(request,"https://airprojekt.000webhostapp.com/noviLjubimac.php");

        LjubimacPodaciCall.enqueue(new Callback<LjubimacPodaciResponse>() {
            @Override
            public void onResponse(Call<LjubimacPodaciResponse> call, Response<LjubimacPodaciResponse> response) {

                LjubimacPodaciResponse resp=response.body();
                body.id= resp.id;
                listener.onStatusChanged(body.id);

            }

            @Override
            public void onFailure(Call<LjubimacPodaciResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    /**
     * metoda za promejnu podataka ljubimca na poslužitelju
     * @param id
     * @param ime
     * @param godina
     * @param masa
     * @param vrsta
     * @param spol
     * @param opis
     * @param slika
     */
    public static void Update(String id, String ime, String godina, String masa, String vrsta, String spol, String opis, String slika) {

        final LjubimacPodaciResponse body = new LjubimacPodaciResponse();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit;
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://airprojekt.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LjubimacPodaciService api = retrofit.create(LjubimacPodaciService.class);

        LjubimacPodaciRequest request = new LjubimacPodaciRequest();

        request.setId(id);

        request.setIme(ime);

        request.setGodina(godina);

        request.setMasa(masa);

        request.setVrsta(vrsta);

        request.setSpol(spol);

        request.setOpis(opis);

        request.setUrl_slike("/");

        request.setSlika(slika);

        Call<LjubimacPodaciResponse> LjubimacPodaciCall = api.createLjubimac(request,"https://airprojekt.000webhostapp.com/updateLjubimac.php");

        LjubimacPodaciCall.enqueue(new Callback<LjubimacPodaciResponse>() {
            @Override
            public void onResponse(Call<LjubimacPodaciResponse> call, Response<LjubimacPodaciResponse> response) {

                LjubimacPodaciResponse resp=response.body();
                body.id= resp.id;
                listener.onStatusChanged(body.id);

            }

            @Override
            public void onFailure(Call<LjubimacPodaciResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    /**
     * metoda za razdvajanje kartice i ljubimca na poslužitelju
     * @param id
     * @param kartica
     */
    public static void RemoveKartica(String id, String kartica) {

        final LjubimacPodaciResponse body = new LjubimacPodaciResponse();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit;
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://airprojekt.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LjubimacPodaciService api = retrofit.create(LjubimacPodaciService.class);

        LjubimacPodaciRequest request = new LjubimacPodaciRequest();

        request.setId(id);
        request.setKartica(kartica);

        Call<LjubimacPodaciResponse> LjubimacPodaciCall = api.createLjubimac(request,"https://airprojekt.000webhostapp.com/removeKartica.php");

        LjubimacPodaciCall.enqueue(new Callback<LjubimacPodaciResponse>() {
            @Override
            public void onResponse(Call<LjubimacPodaciResponse> call, Response<LjubimacPodaciResponse> response) {

                LjubimacPodaciResponse resp=response.body();
                body.id= resp.id;
                listener.onStatusChanged(body.id);

            }

            @Override
            public void onFailure(Call<LjubimacPodaciResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    /**
     * metoda za brisanje ljubimca na poslužitelju
     * @param id
     * @param kartica
     * @param urlSlike
     */
    public static void DeleteLjubimac(String id, String kartica, String urlSlike) {

        final LjubimacPodaciResponse body = new LjubimacPodaciResponse();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit;
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://airprojekt.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LjubimacPodaciService api = retrofit.create(LjubimacPodaciService.class);

        LjubimacPodaciRequest request = new LjubimacPodaciRequest();

        request.setId(id);
        request.setKartica(kartica);
        request.setUrl_slike(urlSlike);

        Call<LjubimacPodaciResponse> LjubimacPodaciCall = api.createLjubimac(request,"https://airprojekt.000webhostapp.com/deleteLjubimac.php");

        LjubimacPodaciCall.enqueue(new Callback<LjubimacPodaciResponse>() {
            @Override
            public void onResponse(Call<LjubimacPodaciResponse> call, Response<LjubimacPodaciResponse> response) {

                LjubimacPodaciResponse resp=response.body();
                body.id= resp.id;
                listener.onStatusChanged(body.id);

            }

            @Override
            public void onFailure(Call<LjubimacPodaciResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}

