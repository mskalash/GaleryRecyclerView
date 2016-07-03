package nan.galeryrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private Utils utils;
    private ArrayList<String> imagePaths = new ArrayList<String>();

    private int columnWidth;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManagerVertical;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newview);


        utils = new Utils(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);


        staggeredGridLayoutManagerVertical = new StaggeredGridLayoutManager(3, // кол-во колонок в сетке
                LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManagerVertical);
        imagePaths = utils.getFilePaths();

        mAdapter = new RecyclerAdapter(MainActivity.this, imagePaths,
                columnWidth);
        mRecyclerView.setAdapter(mAdapter);

    }


}
