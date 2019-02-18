package moranjani.nanitbday;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import moranjani.nanitbday.fragments.BirthdayFragment;
import moranjani.nanitbday.fragments.DetailsFragment;
import moranjani.nanitbday.view_models.MainActivityViewModel;


public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        setObservers();
        moveToFragment(DetailsFragment.getNewInstance(), DetailsFragment.class.getSimpleName(), false);
    }



    private void setObservers() {
        viewModel.getMoveToBirthdayScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldMove) {
                if (shouldMove) {
                  moveToFragment(BirthdayFragment.getNewInstance(), BirthdayFragment.class.getSimpleName(), true);
                }
            }
        });
    }

    private void moveToFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_container, fragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commit();
    }


    @Override
    protected void onStop() {
        super.onStop();
        viewModel.persistData();
    }
}
