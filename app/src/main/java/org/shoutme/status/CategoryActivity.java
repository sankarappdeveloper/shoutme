package org.shoutme.status;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;


public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
//        RecyclerView view_category=(RecyclerView)findViewById(R.id.category);
//        String[] sa = {"sss", "dddd", "cccc", "ffff", "hhhhh", "oooo", "ppppp"};
//        int[] image = {R.drawable.son, R.drawable.son, R.drawable.son, R.drawable.son, R.drawable.son, R.drawable.son, R.drawable.son};
//        ArrayList<Category> category = new ArrayList<>();
////
//        for (int i = 0; i < sa.length; i++) {
//            Category category1=new Category();
//            category1.setImage(image[i]);
//            category1.setName(sa[i]);
//            category.add(category1);
//        }
//        view_category.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
//        view_category.setLayoutManager(layoutManager);
//        CategoryAdapter categoryAdapter=new CategoryAdapter(getApplicationContext(),category);
//        view_category.setAdapter(categoryAdapter);

        GridLayout gg = (GridLayout) findViewById(R.id.grid);
        int count = gg.getChildCount();
        for(int i = 0 ; i <count ; i++){

            final CardView card=(CardView)gg.getChildAt(i);

            final int finalI1 = i;
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(finalI1 ==0) {
                        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("category", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name","Comedy");
                        editor.commit();
                    Toast.makeText(getApplicationContext(), "first",Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(CategoryActivity.this, MainActivity.class);
                        in.putExtra("name","Comedy");
                        startActivity(in);
                    }
                    else if(finalI1 ==1)
                    {
                        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("category", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name","Committed");
                        editor.commit();
                       Toast.makeText(getApplicationContext(),"second",Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(CategoryActivity.this, MainActivity.class);
                        in.putExtra("name","Committed");
                        startActivity(in);
                    }
                    else if(finalI1 ==2)
                    {
                        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("category", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name","Single");
                        editor.commit();



                        Toast.makeText(getApplicationContext(),"second",Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(CategoryActivity.this, MainActivity.class);
                        in.putExtra("name","Single");
                        startActivity(in);
                    }
                    else if(finalI1 ==3)
                    {
                        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("category", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name","Lovefailure");
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"second",Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(CategoryActivity.this, MainActivity.class);
                        in.putExtra("name","Love failiue");
                        startActivity(in);
                    }
                    else if(finalI1 ==4)
                    {
                        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("category", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name","DP");
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"second",Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(CategoryActivity.this, MainActivity.class);
                        in.putExtra("name","DP");
                        startActivity(in);
                    }
                    else if(finalI1 ==5)
                    {
                        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("category", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name","Lovefeel");
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"second",Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(CategoryActivity.this, MainActivity.class);
                        in.putExtra("name","Love Feel");
                        startActivity(in);
                    }
                    else if(finalI1 ==6)
                    {
                        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("category", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name","Romantic");
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"second",Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(CategoryActivity.this, MainActivity.class);
                        in.putExtra("name","Romantic");
                        startActivity(in);
                    }
                    else if(finalI1 ==7)
                    {
                        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("category", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name","Funny");
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"second",Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(CategoryActivity.this, MainActivity.class);
                        in.putExtra("name","Funny");
                        startActivity(in);
                    }
                    else if(finalI1 ==8)
                    {
                        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("category", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name","Friendship");
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"second",Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(CategoryActivity.this, MainActivity.class);
                        in.putExtra("name","Friendship");
                        startActivity(in);
                    }


                }

            });


        }




    }

    @Override
    public void onClick(View v) {
        int vi=v.getId();
        if (v.getId() == 0) {
//                Toast.makeText(CategoryActivity.this, i, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CategoryActivity.this, MainActivity.class));
        } else if (vi == 1) {
//                Toast.makeText(CategoryActivity.this, i, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CategoryActivity.this, MainActivity.class));
        } else if (vi == 2) {
//                Toast.makeText(CategoryActivity.this, i, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CategoryActivity.this, MainActivity.class));
        } else if (vi == 3) {
//                Toast.makeText(CategoryActivity.this, i, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CategoryActivity.this, MainActivity.class));
        } else if (vi == 4) {
//                Toast.makeText(CategoryActivity.this, i, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CategoryActivity.this, MainActivity.class));
        } else if (vi == 5) {
//                Toast.makeText(CategoryActivity.this, i, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CategoryActivity.this, MainActivity.class));
        } else if (vi == 6) {
//                Toast.makeText(CategoryActivity.this, i, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CategoryActivity.this, MainActivity.class));
        } else if (vi == 7) {
//                Toast.makeText(CategoryActivity.this, i, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CategoryActivity.this, MainActivity.class));
        } else if (vi == 8) {
//                Toast.makeText(CategoryActivity.this, i, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CategoryActivity.this, MainActivity.class));
        }
    }
}
