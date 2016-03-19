package com.example.itingshuo;

import java.util.ArrayList;
import java.util.List;

import com.anim.ListAnim;
import com.movie.Movie;
import com.movie.MovieAdapter;
import com.refresh.RefreshableView;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

public class MovieListActivity extends Activity {
   private MovieAdapter adapter = null;  
   private List<Movie> movieList;
   private ListView movieListView;
   private RefreshableView refreshableView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.movie_list_fragment);
		 movieListView = (ListView) findViewById(R.id.lv_movie); 
	        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
	        movieInit();
	        refreshableView.setOnRefreshListener(new com.refresh.RefreshableView.PullToRefreshListener() {
				@Override
				public void onRefresh() {
					requestDataFromServer(true);
					refreshableView.finishRefreshing();
					
				}
			}, 0);
	        adapter = new MovieAdapter(MovieListActivity.this, R.layout.movie_item, movieList);  
		     movieListView.setAdapter(adapter);
		     movieListView.setLayoutAnimation(new ListAnim().getListAnim());
	}

	 /*
	 * 模拟向服务器请求数据
	 */
	private void requestDataFromServer(final boolean isLoading){
				SystemClock.sleep(3000);
				if(isLoading){
					Movie movienews = new Movie();
					movienews.setTitle("movienews");
					movienews.setContent("movienews");
					movienews.setTime("07:30");
					movienews.setImgSrc(R.drawable.image_arrrow);
			    	movieList.add(movienews);
				}else{
				}
	}
    
      /*
       * movie数据的初始化
       * 
       */
    public void movieInit(){
    	movieList= new ArrayList<Movie>();
//    	Movie movie1 = new Movie();
//    	movie1.setTitle("movie1");
//    	movie1.setContent("movieContent1");
//    	movie1.setTime("07:30");
//    	movie1.setImgSrc(R.drawable.image_arrrow);
//    	movieList.add(movie1);
//    	Movie movie2 = new Movie();
//    	movie2.setTitle("movie2");
//    	movie2.setContent("movieContent2");
//    	movie2.setTime("07:30");
//    	movie2.setImgSrc(R.drawable.image_arrrow);
//    	movieList.add(movie2);
//    	Movie movie3 = new Movie();
//    	movie3.setTitle("movie3");
//    	movie3.setContent("movieContent3");
//    	movie3.setTime("07:30");
//    	movie3.setImgSrc(R.drawable.image_arrrow);
//    	movieList.add(movie3);
//    	Movie movie4 = new Movie();
//    	movie4.setTitle("movie4");
//    	movie4.setContent("movieContent4");
//    	movie4.setTime("07:30");
//    	movie4.setImgSrc(R.drawable.image_arrrow);
//    	movieList.add(movie4);
//    	Movie movie5 = new Movie();
//    	movie5.setTitle("movie5");
//    	movie5.setContent("movieContent5");
//    	movie5.setTime("07:30");
//    	movie5.setImgSrc(R.drawable.image_arrrow);
//    	movieList.add(movie5);
//    	Movie movie6 = new Movie();
//    	movie6.setTitle("movie6");
//    	movie6.setContent("movieContent6");
//    	movie6.setTime("07:30");
//    	movie6.setImgSrc(R.drawable.image_arrrow);
//    	movieList.add(movie6);
//    	Movie movie7 = new Movie();
//    	movie7.setTitle("movie7");
//    	movie7.setContent("movieContent7");
//    	movie7.setTime("07:30");
//    	movie7.setImgSrc(R.drawable.image_arrrow);
//    	movieList.add(movie7);
    }
 

}
