package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView originTextview;
    TextView alsoKnownAsTextView;
    TextView ingredientsTextView;
    TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        originTextview = (TextView) findViewById(R.id.origin_value_tv);
        alsoKnownAsTextView = (TextView) findViewById(R.id.also_known_as_tv);
        ingredientsTextView = (TextView) findViewById(R.id.ingrediants_tv);
        descriptionTextView = (TextView) findViewById(R.id.description_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String placeOfOrigin = sandwich.getPlaceOfOrigin();

        if(placeOfOrigin != null && !placeOfOrigin.isEmpty()){
            originTextview.setText(placeOfOrigin);
        }else {
            originTextview.setText("NA");
        }

        List<String> alsoknownas = sandwich.getAlsoKnownAs();
        if(alsoknownas.size() != 0){
            for (String alsoknownasVal: alsoknownas){
                alsoKnownAsTextView.append(alsoknownasVal + ", ");
            }
        }else {
            alsoKnownAsTextView.setText("NA");
        }

        List<String> ingredients = sandwich.getIngredients();
        if(ingredients.size() != 0){
            for (String ingredientsVal: ingredients){
                ingredientsTextView.append(ingredientsVal + ", ");
            }
        }else {
            ingredientsTextView.setText("NA");
        }

        String description = sandwich.getDescription();

        if (description != null && !description.isEmpty()){
            descriptionTextView.setText(description);
        }else {
            descriptionTextView.setText("NA");
        }
    }
}
