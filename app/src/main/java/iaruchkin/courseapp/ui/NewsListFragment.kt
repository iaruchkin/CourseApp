package iaruchkin.courseapp.ui

import android.content.Context
import android.content.res.Configuration
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner

import iaruchkin.courseapp.data.NewsCategory
import iaruchkin.courseapp.network.NetworkSilngleton
import iaruchkin.courseapp.network.TopStoriesResponse
import iaruchkin.courseapp.room.ConverterNews
import iaruchkin.courseapp.room.NewsEntity
import iaruchkin.courseapp.ui.adapter.CategoriesSpinnerAdapter
import iaruchkin.courseapp.ui.adapter.NewsItemAdapter
import iaruchkin.courseapp.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import iaruchkin.courseapp.ui.MainActivity.Companion.ABOUT_TAG
import iaruchkin.courseapp.ui.MainActivity.Companion.NEWS_DETAILS_TAG
import iaruchkin.courseapp.ui.MainActivity.Companion.NEWS_LIST_TAG


class NewsListFragment : Fragment(), NewsItemAdapter.NewsAdapterOnClickHandler {
    private var listener: MessageFragmentListener? = null

    private var mAdapter: NewsItemAdapter? = null
    private var mRecyclerView: RecyclerView? = null
    private var mLoadingIndicator: ProgressBar? = null
    private var mError: View? = null
    private var errorAction: Button? = null
    private var mUpdate: FloatingActionButton? = null
    private var toolbar: Toolbar? = null
    private var spinnerCategories: Spinner? = null

    private var categoriesAdapter: CategoriesSpinnerAdapter? = null

    private val compositeDisposable = CompositeDisposable()

    private val newsCategory: String
        get() = categoriesAdapter!!.selectedCategory.serverValue()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e(NEWS_LIST_TAG, "OnCreateView executed on thread:" + Thread.currentThread().name)

        val view = inflater.inflate(LAYOUT, container, false)

        setupUi(view)
        setupUx()

        return view
    }

    override fun onStart() {
        Log.e(NEWS_LIST_TAG, "onStart")
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        mLoadingIndicator!!.visibility = View.GONE
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter = null
        mRecyclerView = null
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MessageFragmentListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    private fun setupUi(view: View) {
        findViews(view)
        setupToolbar()
        setupSpinner()
        setupOrientation(mRecyclerView)
        setupRecyclerViewAdapter()
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        (context as AppCompatActivity).setSupportActionBar(toolbar)
        (context as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    private fun setupRecyclerViewAdapter() {
        mAdapter = NewsItemAdapter(this)
        mRecyclerView!!.adapter = mAdapter
    }

    private fun setupOrientation(recyclerView: RecyclerView?) {

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        } else {
            val columnsCount = resources.getInteger(R.integer.landscape_news_columns_count)
            mRecyclerView!!.layoutManager = GridLayoutManager(context, columnsCount)
        }
    }

    private fun setupSpinner() {
        val categories = NewsCategory.values()
        categoriesAdapter = CategoriesSpinnerAdapter.createDefault(context!!, categories)
        spinnerCategories!!.adapter = categoriesAdapter
    }

    private fun setupUx() {

        mUpdate!!.setOnClickListener { v -> loadFromNet(newsCategory) }

        errorAction!!.setOnClickListener { v -> loadFromDb(newsCategory) }

        categoriesAdapter!!.setOnCategorySelectedListener({ category ->
            mLoadingIndicator!!.visibility = View.VISIBLE
            loadFromDb(newsCategory)
        }, spinnerCategories!!)
        //        categoriesAdapter.setOnCategorySelectedListener(NewsCategory::serverValue, spinnerCategories);

    }

    private fun loadFromDb(category: String) {
        mLoadingIndicator!!.visibility = View.VISIBLE
        val loadFromDb = Single.fromCallable {
            ConverterNews
                    .loadNewsFromDb(context, category)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<List<NewsEntity>> { this.updateNews(it) }, Consumer<Throwable> { this.handleError(it) })
        compositeDisposable.add(loadFromDb)
    }

    private fun updateNews(news: List<NewsEntity>?) {
        if (news!!.size == 0) {
            loadFromNet(newsCategory)
            Log.e(NEWS_LIST_TAG, "there is no news in category : $newsCategory")
        } else {
            if (mAdapter != null) {
                mAdapter!!.replaceItems(news)
            }
            mError!!.visibility = View.GONE
            mLoadingIndicator!!.visibility = View.GONE
            Log.e(NEWS_LIST_TAG, "loaded from DB: " + news[0].category + " / " + news[0].title)
            Log.e(NEWS_LIST_TAG, "updateNews executed on thread: " + Thread.currentThread().name)
        }
    }

    private fun loadFromNet(category: String) {
        mLoadingIndicator!!.visibility = View.VISIBLE
        val disposable = NetworkSilngleton.getInstance()
                .topStories()
                .get(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<TopStoriesResponse> { this.updateDB(it) }, Consumer<Throwable> { this.handleError(it) })
        compositeDisposable.add(disposable)
    }

//    fun updateDB(response: TopStoriesResponse) {
//        if (response.news.size == 0) {
//            mError!!.visibility = View.VISIBLE
//            mLoadingIndicator!!.visibility = View.GONE
//        } else {
//            val saveNewsToDb = Single.fromCallable<List<NewsDTO>>(Callable<List<NewsDTO>> { response.news })
//                    .subscribeOn(Schedulers.io())
//                    .map { newsDTO ->
//                        ConverterNews.saveAllNewsToDb(context, ConverterNews
//                                .dtoToDao(newsDTO, newsCategory), newsCategory)
//                        ConverterNews.loadNewsFromDb(context, newsCategory)
//                    }
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe { newsEntities ->
//                        mAdapter!!.replaceItems(newsEntities)
//                        Log.e(NEWS_LIST_TAG, "loaded from NET to DB: " + newsEntities[0].category + " / " + newsEntities[0].title)
//                    }
//            compositeDisposable.add(saveNewsToDb)
//            mLoadingIndicator!!.visibility = View.GONE
//        }
//    }

    private fun handleError(th: Throwable) {
        Log.w(NEWS_LIST_TAG, th.message, th)
        mLoadingIndicator!!.visibility = View.GONE
        mRecyclerView!!.visibility = View.GONE
        mError!!.visibility = View.VISIBLE

        Log.e(NEWS_LIST_TAG, "handleError executed on thread: " + Thread.currentThread().name)

    }

    override fun onClick(newsItem: NewsEntity) {
        listener!!.onActionClicked(NEWS_DETAILS_TAG, newsItem.url)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.about_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_switch -> {
                listener!!.onActionClicked(ABOUT_TAG, null)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun findViews(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        mRecyclerView = view.findViewById(R.id.idRecyclerView)
        mLoadingIndicator = view.findViewById(R.id.pb_loading_indicator)
        mError = view.findViewById(R.id.error_layout)
        errorAction = view.findViewById(R.id.action_button)
        spinnerCategories = view.findViewById(R.id.spinner_categories)
        mUpdate = view.findViewById(R.id.floatingActionButton)
    }

    companion object {

        private val LAYOUT = R.layout.activity_news_list
    }
}