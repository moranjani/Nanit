package moranjani.nanitbday.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import moranjani.nanitbday.R;
import moranjani.nanitbday.databinding.BirthdayScreenBinding;
import moranjani.nanitbday.view_models.MainActivityViewModel;

public class BirthdayFragment extends Fragment implements IBirthdayFragment {

    public static BirthdayFragment getNewInstance() {
        return new BirthdayFragment();
    }

    MainActivityViewModel viewModel;
    BirthdayScreenBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.birthday_screen, container, false);
        binding.setModel(viewModel);
        binding.setIBirthdayFragment(this);
        binding.setLifecycleOwner(this);
        setListeners();
        View v = binding.getRoot();

        return v;
    }

    private void setListeners() {

    }
}
