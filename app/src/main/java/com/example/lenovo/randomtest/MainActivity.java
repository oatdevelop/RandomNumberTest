package com.example.lenovo.randomtest;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lenovo.randomtest.model.NumberRequest;
import com.example.lenovo.randomtest.model.NumberResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar1;
    TextView textViewOutput;
    EditText myNumber;
    Button btnCheck;
    private NumberResponse numberResponse;
    private static RandomNumberAPI service;
    private static String API_KEY = "fac1f34b-5785-47a8-b48a-f304b9e79435";
    public static String BASE_URL = "https://api.random.org/json-rpc/1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMyDesign();
        sendRequest();
    }

    private void initProgram(NumberRequest numberRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RandomNumberAPI.class);
        Call<NumberResponse> call = service.createModelNumber(numberRequest);

        call.enqueue(new Callback<NumberResponse>() {
            @Override
            public void onResponse(Call<NumberResponse> call, Response<NumberResponse> response) {
                numberResponse = response.body();
                dismissLoading();
                textViewOutput.setText("Choose Your Number");
            }

            @Override
            public void onFailure(Call<NumberResponse> call, Throwable t) {
                Log.d("MyFail" , "Error");
            }
        });
    }

    private void sendRequest(){

        showLoading();

        NumberRequest.Params params = new NumberRequest.Params();
        params.setApiKey(API_KEY);
        params.setN(1);
        params.setMin(1000);
        params.setMax(9999);
        params.setReplacement(true);

        NumberRequest numberRequest = new NumberRequest();
        numberRequest.setJsonrpc("2.0");
        numberRequest.setMethod("generateIntegers");
        numberRequest.setParams(params);
        numberRequest.setId(42);

        initProgram(numberRequest);
    }

    private void showMyResult(){

        boolean checkEndGame;

        if(!checkInputEmpty()) {
            RandomNumber randomNumber = new RandomNumber();
            int intMyNumber = 0;
            int intRandomNumber = 0;
            String resultString;

            intMyNumber = Integer.parseInt(myNumber.getText().toString());
            intRandomNumber = numberResponse.getRandomNumber();
            resultString = randomNumber.CompareRandomNumber(intMyNumber, intRandomNumber);
            textViewOutput.setText(resultString);
            checkEndGame = numberResponse.checkEndGame(intMyNumber);

            if(checkEndGame){
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage(resultString + ", Starting a new game");
                alert.setPositiveButton("OK",null);
                alert.show();
                sendRequest();
            }

        }
    }

    private void showLoading(){
        progressBar1.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    private boolean checkInputEmpty(){
        return myNumber.getText().toString().trim().length() == 0;
    }

    private void dismissLoading(){
        progressBar1.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void initMyDesign(){
        progressBar1 = (ProgressBar)findViewById(R.id.progressBar1);
        textViewOutput = (TextView)findViewById(R.id.textViewOutput);
        myNumber = (EditText)findViewById(R.id.editTextInput);
        btnCheck = (Button)findViewById(R.id.btnCheck);
    }

    public void checkResult(View view){    // OnClick btnCheck
        showMyResult();
    }

}
