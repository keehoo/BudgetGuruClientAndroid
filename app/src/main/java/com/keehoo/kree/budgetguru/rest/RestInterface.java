package com.keehoo.kree.budgetguru.rest;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.data_models.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by krzysztof on 28.09.2017.
 */

public interface RestInterface {

    String BASE_URL = "http://145.239.89.73/api/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Retrofit rxRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET("users/allUsers")
    Call<List<User>> listUsers();

    @GET("category/getAll")
    Call<List<String>> getAllCategories();

    @GET("budget/allBudgets")
    Observable<List<BudgetEntryModel>> getAll();

    @POST("users/addUser")
    Call<User> createUser(@Body User user);

    @POST("budget/add")
    Call<Void> addBudget(@Body BudgetEntryModel budgetEntry);




}
