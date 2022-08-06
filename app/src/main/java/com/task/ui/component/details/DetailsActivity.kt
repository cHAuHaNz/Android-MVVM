package com.task.ui.component.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.task.R
import com.task.IntentExtras
import com.task.data.Resource
import com.task.data.dto.recipes.RecipesItem
import com.task.databinding.DetailsLayoutBinding
import com.task.ui.base.BaseActivity
import com.task.utils.observe
import com.task.utils.gone
import com.task.utils.visible
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Amandeep Chauhan
 */

@AndroidEntryPoint
class DetailsActivity : BaseActivity<DetailsLayoutBinding>() {

    private val viewModel: DetailsViewModel by viewModels()

    private var menu: Menu? = null

    override fun initViewBinding() = DetailsLayoutBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initIntentData(intent.getParcelableExtra(IntentExtras.EXTRA_RECIPE_ITEM_KEY) ?: RecipesItem())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        this.menu = menu
        viewModel.isFavourites()
        return true
    }

    fun onClickFavorite(mi: MenuItem) {
        mi.isCheckable = false
        if (viewModel.isFavourite.value?.data == true) {
            viewModel.removeFromFavourites()
        } else {
            viewModel.addToFavourites()
        }
    }

    override fun observeViewModel() {
        observe(viewModel.recipeData, ::initializeView)
        observe(viewModel.isFavourite, ::handleIsFavourite)
    }

    private fun handleIsFavourite(isFavourite: Resource<Boolean>) {
        when (isFavourite) {
            is Resource.Loading -> {
                binding.pbLoading.visible()
            }
            is Resource.Success -> {
                isFavourite.data?.let {
                    handleIsFavouriteUI(it)
                    menu?.findItem(R.id.add_to_favorite)?.isCheckable = true
                    binding.pbLoading.gone()
                }
            }
            is Resource.DataError -> {
                menu?.findItem(R.id.add_to_favorite)?.isCheckable = true
                binding.pbLoading.gone()
            }
        }
    }

    private fun handleIsFavouriteUI(isFavourite: Boolean) {
        menu?.let {
            it.findItem(R.id.add_to_favorite)?.icon =
                    if (isFavourite) {
                        ContextCompat.getDrawable(this, R.drawable.ic_star_24)
                    } else {
                        ContextCompat.getDrawable(this, R.drawable.ic_outline_star_border_24)
                    }
        }
    }

    private fun initializeView(recipesItem: RecipesItem) {
        binding.tvName.text = recipesItem.name
        binding.tvHeadline.text = recipesItem.headline
        binding.tvDescription.text = recipesItem.description
        Glide.with(this).load(recipesItem.image).placeholder(R.drawable.ic_healthy_food_small)
                .into(binding.ivRecipeImage)

    }
}
