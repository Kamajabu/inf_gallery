package com.kamajabu.infgallery.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kamajabu.infgallery.R;
import com.kamajabu.infgallery.adapter.GalleryAdapter;
import com.kamajabu.infgallery.model.Image;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class MainActivity extends Activity implements ConfigData {
    
    private String TAG = MainActivity.class.getSimpleName();
    private static final String endpoint = "http://api.androidhive.info/json/glide.json";
    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        
        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);
        
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                             @Override
                                             public void onScrollStateChanged(RecyclerView recyclerView, int state) {
                                                 super.onScrollStateChanged(recyclerView, state);
                
                                                 if (state == SCROLL_STATE_IDLE) {
                                                     GridLayoutManager glm = ((GridLayoutManager) recyclerView.getLayoutManager());
                                                     int lastVisiblePosition = glm.findLastVisibleItemPosition();
                                                     recyclerView.smoothScrollToPosition(lastVisiblePosition);
                                                 }
                                             }
                                         }
        
                                        );
        
        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(
                
                getApplicationContext(),
                
                recyclerView, new GalleryAdapter.ClickListener()
        
        {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);
                
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.anim_slide_in_top, R.animator.anim_slide_out_bottom);
                
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setCancelable(false);
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }
            
            @Override
            public void onLongClick(View view, int position) {
                
            }
        }
        
        ));
        
        populateModel();
    }
    
    private void populateModel() {
        images.clear();
        
        images.add(new Image("cat", R.drawable.cat1));
        images.add(new Image("Another cat", R.drawable.cat2));
        images.add(new Image("Yet another cat", R.drawable.cat3));
        
        images.add(new Image("Oh look! A cat.", R.drawable.cat4));
        images.add(new Image("Guess what.", R.drawable.cat5));
        images.add(new Image("A fish? Nah, a cat.", R.drawable.cat6));
    
        images.add(new Image("It could be a cat.", R.drawable.food1));
        images.add(new Image("Food.", R.drawable.food2));
        images.add(new Image("Hungry yet?", R.drawable.food3));
    
        images.add(new Image("Tomato potato.", R.drawable.food4));
        images.add(new Image("Bird.", R.drawable.nature1));
        images.add(new Image("Forest", R.drawable.nature2));
        
        mAdapter.notifyDataSetChanged();
    }
}