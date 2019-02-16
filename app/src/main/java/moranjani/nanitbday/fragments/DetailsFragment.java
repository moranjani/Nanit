package moranjani.nanitbday.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import moranjani.nanitbday.IDetailsFragment;
import moranjani.nanitbday.R;
import moranjani.nanitbday.Utils.DateConverter;
import moranjani.nanitbday.databinding.DetailsScreenBinding;
import moranjani.nanitbday.view_models.MainActivityViewModel;

public class DetailsFragment extends Fragment implements IDetailsFragment {

    public static DetailsFragment getNewInstance() {
        return new DetailsFragment();
    }

    MainActivityViewModel viewModel;
    DetailsScreenBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.details_screen, container, false);
        binding.setModel(viewModel);
        binding.setIDetailsFragment(this);
        setListeners();

        View v = binding.getRoot();
        return v;
    }

    private void setListeners() {
        binding.birthDateEditText.setShowSoftInputOnFocus(false);
        binding.birthDateEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showDatePickerDialog();
            }
        });
        binding.nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.name.setValue(s.toString());
                viewModel.getButtonEnabled();
            }
        });

        viewModel.getButtonEnabled().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.continueButton.setEnabled(aBoolean);
            }
        });


    }

    @Override
    public void onBirthDateClicked() {
        showDatePickerDialog();
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener onDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        Date date = new Date();
                        calendar.setTime(date);
                        calendar.add(Calendar.DATE, 1);
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long selectedDate = calendar.getTimeInMillis();
                        viewModel.birthDate.setValue(selectedDate);
                        binding.birthDateEditText.setText(DateConverter.dateToString(selectedDate));
                        viewModel.getButtonEnabled();
                    }
                };


        Calendar markedDate = DateConverter.millisToCalendar(viewModel.birthDate.getValue());
        final long maxTime = Calendar.getInstance().getTimeInMillis();
        int mYear = markedDate.get(Calendar.YEAR);
        int mMonth = markedDate.get(Calendar.MONTH);
        int mDay = markedDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getActivity(), onDateSetListener, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(maxTime);
        datePickerDialog.show();
    }
}
