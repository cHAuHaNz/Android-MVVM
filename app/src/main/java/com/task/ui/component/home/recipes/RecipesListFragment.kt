package com.task.ui.component.home.recipes

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.task.IntentExtras
import com.task.R
import com.task.data.Resource
import com.task.data.dto.recipes.Recipes
import com.task.data.dto.recipes.RecipesItem
import com.task.data.error.SEARCH_ERROR
import com.task.databinding.FragmentRecipesListBinding
import com.task.ui.base.BaseFragment
import com.task.ui.component.details.DetailsActivity
import com.task.utils.*
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Amandeep Chauhan
 */
@AndroidEntryPoint
class RecipesListFragment : BaseFragment<FragmentRecipesListBinding>(R.layout.fragment_recipes_list) {

    private val viewModel by viewModels<RecipesListViewModel>()
    private lateinit var recipesAdapter: RecipesAdapter

    override fun initViewBinding(view: View) = FragmentRecipesListBinding.bind(view)

    override fun setupViews() {
        setHasOptionsMenu(true)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecipesList.layoutManager = layoutManager
        binding.rvRecipesList.setHasFixedSize(true)
    }

    private fun handleSearch(query: String) {
        if (query.isNotEmpty()) {
            binding.pbLoading.visibility = View.VISIBLE
            viewModel.onSearchClick(query)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_actions, menu)
        // Associate searchable configuration with the SearchView
        val searchView = menu.findItem(R.id.action_search)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_by_name)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                handleSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> viewModel.getRecipes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindListData(recipes: Recipes) {
        if (!(recipes.recipesList.isNullOrEmpty())) {
            recipesAdapter = RecipesAdapter(viewModel, recipes.recipesList)
            binding.rvRecipesList.adapter = recipesAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<RecipesItem>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(requireContext(), DetailsActivity::class.java).apply {
                putExtra(IntentExtras.EXTRA_RECIPE_ITEM_KEY, it)
            }
            startActivity(nextScreenIntent)
        }
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Toast.LENGTH_LONG)
    }

    private fun showSearchError() {
        viewModel.showToastMessage(SEARCH_ERROR)
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) View.GONE else View.VISIBLE
        binding.rvRecipesList.visibility = if (show) View.VISIBLE else View.GONE
        binding.pbLoading.gone()
    }

    private fun showLoadingView() {
        binding.pbLoading.visible()
        binding.tvNoData.gone()
        binding.rvRecipesList.gone()
    }


    private fun showSearchResult(recipesItem: RecipesItem) {
        viewModel.openRecipeDetails(recipesItem)
        binding.pbLoading.gone()
    }

    private fun noSearchResult(unit: Unit) {
        showSearchError()
        binding.pbLoading.gone()
    }

    private fun handleRecipesList(status: Resource<Recipes>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(recipes = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { viewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(viewModel.recipesLiveData, ::handleRecipesList)
        observe(viewModel.recipeSearchFound, ::showSearchResult)
        observe(viewModel.noSearchFound, ::noSearchResult)
        observeEvent(viewModel.openRecipeDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(viewModel.showSnackBar)
        observeToast(viewModel.showToast)
    }
}