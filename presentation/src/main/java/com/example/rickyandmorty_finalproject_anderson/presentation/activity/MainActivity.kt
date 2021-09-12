package com.example.rickyandmorty_finalproject_anderson.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.rickyandmorty_finalproject_anderson.App
import com.example.rickyandmorty_finalproject_anderson.R
import com.example.rickyandmorty_finalproject_anderson.databinding.ActivityMainBinding
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsEpisodes.ui.DetailsEpisodesFragment
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsHeroes.ui.DetailsHeroesFragment
import com.example.rickyandmorty_finalproject_anderson.presentation.detailsLocations.ui.DetailsLocationsFragment
import com.example.rickyandmorty_finalproject_anderson.presentation.episodes.ui.EpisodesFragment
import com.example.rickyandmorty_finalproject_anderson.presentation.heroes.ui.HeroesFragment
import com.example.rickyandmorty_finalproject_anderson.presentation.locations.ui.LocationsFragment

class MainActivity : AppCompatActivity(),
    EpisodesFragment.OnSetSupportActionBarEpisodes,
    HeroesFragment.OnSetSupportActionBarHeroes,
    LocationsFragment.OnSetSupportActionBarLocations,
    DetailsHeroesFragment.OnHideShowBottomNavigationFilterHeroes,
    DetailsHeroesFragment.OnClickBackDetailsHeroes,
    DetailsEpisodesFragment.OnClickBackDetailsEpisodes,
    DetailsEpisodesFragment.OnHideShowBottomNavigationFilterEpisodes,
    DetailsLocationsFragment.OnClickBackDetailsLocation,
    DetailsLocationsFragment.OnHideShowBottomNavigationFilterLocation {

    private lateinit var mMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainBinding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragments, HeroesFragment.newInstance())
            .commit()

        mMainBinding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.characters -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.containerFragments, HeroesFragment.newInstance())
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.episods -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.containerFragments, EpisodesFragment.newInstance())
                        .commit()
                    return@setOnItemSelectedListener true

                }
                R.id.locations -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.containerFragments, LocationsFragment.newInstance())
                        .commit()
                    return@setOnItemSelectedListener true

                }
                else -> false
            }
        }

        mMainBinding.bottomNavigation.itemIconTintList = null;

    }

    override fun onBackPressed() {

        when (supportFragmentManager.backStackEntryCount) {
            0 -> {
                finish()
            }
            1 -> {
                mMainBinding.bottomNavigation.visibility = View.VISIBLE
                supportFragmentManager.popBackStack()
            }
            else -> {
                mMainBinding.bottomNavigation.visibility = View.GONE
                supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onSetActionBarEpisodes(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    override fun onSetActionBarHeroes(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    override fun onSetActionBarLocations(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    override fun onHideHeroesDetails() {
        mMainBinding.bottomNavigation.visibility = View.GONE
    }

    override fun onHideEpisodesDetails() {
        mMainBinding.bottomNavigation.visibility = View.GONE
    }

    override fun onClickDetailsHero() {
        onBackPressed()
    }

    override fun onClickDetailsEpisodes() {
        onBackPressed()
    }

    override fun onClickDetailsLocation() {
        onBackPressed()
    }

    override fun onHideLocationDetails() {
        mMainBinding.bottomNavigation.visibility = View.GONE
    }


}