package moranjani.nanitbday;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import moranjani.nanitbday.fragments.DetailsFragment;
import moranjani.nanitbday.view_models.MainActivityViewModel;


public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private Fragment detailsFragment;
    private Fragment birthdayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        detailsFragment = DetailsFragment.getNewInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_container, detailsFragment)
                .setPrimaryNavigationFragment(detailsFragment)
                .commit();




        //        viewModel.getAllNotes().observe(this, new Observer<List<ContactsContract.CommonDataKinds.Note>>() {
//            @Override
//            public void onChanged(@Nullable List<ContactsContract.CommonDataKinds.Note> notes) {
//                adapter.submitList(notes);
//            }
//        });

    }


    @Override
    protected void onStop() {
        super.onStop();
        viewModel.persistData();
    }
}
