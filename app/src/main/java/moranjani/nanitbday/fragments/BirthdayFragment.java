package moranjani.nanitbday.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import moranjani.nanitbday.R;
import moranjani.nanitbday.databinding.BirthdayScreenBinding;
import moranjani.nanitbday.view_models.BirthdayFragmentViewModel;
import moranjani.nanitbday.view_models.MainActivityViewModel;

public class BirthdayFragment extends Fragment implements IBirthdayFragment {

    private static final String THEME = "THEME";

    public static BirthdayFragment getNewInstance() {
        return new BirthdayFragment();
    }

    MainActivityViewModel mainActivityViewModel;
    BirthdayFragmentViewModel model;
    BirthdayScreenBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityViewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        model = ViewModelProviders.of(this).get(BirthdayFragmentViewModel.class);
        model.setBirthDate(mainActivityViewModel.getBirthDate().getValue());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.birthday_screen, container, false);
        binding.setActivityModel(mainActivityViewModel);
        binding.setModel(model);
        binding.setIBirthdayFragment(this);
        binding.setLifecycleOwner(this);
        loadPicture();
        View v = binding.getRoot();
        return v;
    }

    private void loadPicture() {
        String uri = mainActivityViewModel.getPictureUri().getValue();
        if (uri != null) {
            File f = new File(mainActivityViewModel.getPictureUri().getValue());
            Picasso.get().load(f)
                    .placeholder(model.getPicPlaceHolderImageRes())
                    .into(binding.pictureImageView);
        }
    }

    @Override
    public void onCloseButtonClicked() {
        getActivity().getSupportFragmentManager().popBackStack();
    }


}
