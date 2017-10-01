package com.keehoo.kree.budgetguru.rest;

import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.data_models.User;

import java.util.List;

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

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://145.239.89.73/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("users/allUsers")
    Call<List<User>> listUsers();

    @POST("users/addUser")
    Call<Void> createUser(@Body User user);

    @POST("budget/add")
    Call<Void> addBudget(@Body BudgetEntryModel budgetEntry);
}
