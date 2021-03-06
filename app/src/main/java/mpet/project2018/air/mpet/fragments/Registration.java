package mpet.project2018.air.mpet.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v4.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import Retrofit.DataPost.RegistracijaMethod;
import Retrofit.RemotePost.StatusListener;
import mpet.project2018.air.core.OnFragmentInteractionListener;
import mpet.project2018.air.mpet.R;

import static android.app.Activity.RESULT_OK;

public class Registration extends Fragment implements StatusListener {
    private OnFragmentInteractionListener mListener;

    /*slika*/
    private static int RESULT_LOAD_IMAGE = 1;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageButton imageButton;
    private Bitmap bit=null;
    private String slika=null;

    public String status=null;
    private RegistracijaMethod method=new RegistracijaMethod(this);
    private ProgressDialog progress;
    private ProgressBar spinner;


    public Registration() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.registracija, container, false);

        if (mListener != null) {
            mListener.onFragmentInteraction("Registracija korisnika");
        }

        Button buttonSpremi=(Button) view.findViewById(R.id.btnRegistracijaSpremi);
        Button buttonOdustani=(Button) view.findViewById(R.id.btnRegistracijaOdustani);
        imageButton= (ImageButton) view.findViewById(R.id.btnChooseImage);
        spinner = (ProgressBar)view.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        buttonSpremi.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                EditText imeEdit = (EditText)view.findViewById(R.id.unosIme);
                                                String ime= imeEdit.getText().toString();
                                                EditText prezimeEdit = (EditText)view.findViewById(R.id.unosPrezime);
                                                String prezime=prezimeEdit.getText().toString();
                                                EditText korisnickoEdit = (EditText)view.findViewById(R.id.unosKorIme);
                                                String korIme=korisnickoEdit.getText().toString();
                                                EditText adresaEdit = (EditText)view.findViewById(R.id.unosAdresa);
                                                String adresa=adresaEdit.getText().toString();

                                                EditText mailEdit = (EditText)view.findViewById(R.id.unosMail);
                                                String mail=mailEdit.getText().toString();
                                                EditText telefonEdit = (EditText)view.findViewById(R.id.unosTelefon);
                                                String telefon=telefonEdit.getText().toString();

                                                EditText mobitelEdit = (EditText)view.findViewById(R.id.unosMobitel);
                                                String mobitel=mobitelEdit.getText().toString();

                                                EditText lozinkaEdit = (EditText)view.findViewById(R.id.unosLozinka);
                                                String lozinka=lozinkaEdit.getText().toString();
                                                String provjera=null;
                                                /**/
                                                if(bit!=null){
                                                    slika = BitmapTOString(bit);
                                                }
                                                if(TextUtils.isEmpty(ime)||TextUtils.isEmpty(prezime)||TextUtils.isEmpty(mail)||lozinka.length()<3||korIme.length()<3){
                                                    alertingMessage("Niste unijeli sva potrebna polja.", R.drawable.exclamation_message);
                                                }
                                                else{
                                                    if (provjeraNedozvoljeniZnakovi(ime, prezime, korIme, adresa, mobitel, telefon))
                                                        alertingMessage("Koristili ste nedozvoljene znakove!", R.drawable.exclamation_message);
                                                    else if(provjeraMail(mail)){
                                                        alertingMessage("E-mail adresa nije u propisnom obliku.", R.drawable.exclamation_message);
                                                    }
                                                    else {
                                                        method.Upload(ime, prezime, korIme, adresa, mail, mobitel, telefon, lozinka, slika);
                                                        spinner.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            }
                                        }
        );

        buttonOdustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeLoggedOut frag;
                frag = new HomeLoggedOut();
                swapFragment(false,(HomeLoggedOut) frag);
            }
        });

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageButton.setImageBitmap(bitmap);
                bit=bitmap;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * konverzija slike za prijenos na poslužitelj
     * @param bitmap
     * @return string
     */
    public String BitmapTOString(Bitmap bitmap) {
        Bitmap bm = bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.DEFAULT);
        return imgString;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * provjera unosa nedozvoljenih znakova
     * @param ime
     * @param prezime
     * @param korIme
     * @param adresa
     * @param mobitel
     * @param telefon
     * @return rezultat provjere (true, false)
     */
    private boolean provjeraNedozvoljeniZnakovi(String ime,String prezime,String korIme,String adresa,String mobitel,String telefon){
        String pattern = "[\\'|\\!|\\?|\\#|\\*|\\$|\\%|\\&|\\/]";
        Pattern p = Pattern.compile(pattern);
        boolean found=false;
        if(p.matcher(ime).find())
            return true;
        if(p.matcher(prezime).find())
            return true;
        if(p.matcher(korIme).find())
            return true;
        if(p.matcher(ime).find())
            return true;
        if(p.matcher(adresa).find())
            return true;
        if(p.matcher(mobitel).find())
            return true;
        if(p.matcher(telefon).find())
            return true;
        return found;
    }

    /**
     * provjera oblika unešenog maila
     * @param mail
     * @return rezultat provjere (true, false)
     */
    private boolean provjeraMail(String mail){
        String pattern = "^[A-z0-9][A-z0-9]*\\.?[A-z0-9]*@[A-z0-9]+\\.[A-z0-9]{2,}$";
        Pattern p = Pattern.compile(pattern);
        if(p.matcher(mail).matches())
            return false;
        else return true;
    }

    private void alertingMessage(String message, int imageIcon)
    {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }
        builder.setTitle("Upozorenje")
                .setMessage(message)
                .setPositiveButton("U redu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(imageIcon)
                .show();
    }

    private void swapFragment(boolean addToBackstack, Fragment fragToShow){
        if(getActivity() == null)
            return;
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, (Fragment) fragToShow);
        if(addToBackstack)
            ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onStatusChanged(String s) {
        status=s;
        Activity a=getActivity();

        if(s.equals("uspjesno")) {
            HomeLoggedOut frag;
            frag = new HomeLoggedOut();

            swapFragment(false,(HomeLoggedOut) frag);
            spinner.setVisibility(View.GONE);

            Toast.makeText(a, "Registrirali ste se :)",
                    Toast.LENGTH_LONG).show();
        }
        else if(s.equals("duplikat")) {
            alertingMessage("Korisničko ime već postoji!", R.drawable.exclamation_message);
        }
        else if (s.equals("greska")) {
            alertingMessage("Ups, greška...", R.drawable.fail_message);
        }
    }

}

