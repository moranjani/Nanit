package moranjani.nanitbday.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import moranjani.nanitbday.R;
import moranjani.nanitbday.Utils.DateConverter;
import moranjani.nanitbday.Utils.GeneralUtils;
import moranjani.nanitbday.databinding.DetailsScreenBinding;
import moranjani.nanitbday.view_models.MainActivityViewModel;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class DetailsFragment extends Fragment implements IDetailsFragment {

    public static DetailsFragment getNewInstance() {
        return new DetailsFragment();
    }

    private static final int PERMISSION_CAMERA_REQUEST_CODE = 1;
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
        binding.setLifecycleOwner(this);
        setListeners();

        View v = binding.getRoot();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles,
                                       EasyImage.ImageSource source, int type) {
                if (imageFiles.size() > 0 && imageFiles.get(0) != null) {
                    viewModel.getPictureUri().setValue(imageFiles.get(0).getPath());
                }
            }

        });
    }

    private void setListeners() {
        binding.birthDateEditText.setShowSoftInputOnFocus(false);
        binding.birthDateEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                GeneralUtils.hideKeyboard(binding.birthDateEditText);
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
                viewModel.getName().setValue(s.toString());
                viewModel.refreshButtonEnabledState();
            }
        });
    }


    @Override
    public void onBirthDateClicked() {
        showDatePickerDialog();
    }

    @Override
    public void onContinueButtonClicked() {
        GeneralUtils.hideKeyboard(binding.getRoot());
        viewModel.getMoveToBirthdayScreen().setValue(true);
    }

    @Override
    public void onSelectPictureClicked() {
        onAddPhotoClickListener();
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
                        viewModel.getBirthDate().setValue(selectedDate);
                        viewModel.refreshButtonEnabledState();
                    }
                };


        Calendar markedDate = DateConverter.millisToCalendar(viewModel.getBirthDate().getValue());
        final long maxTime = Calendar.getInstance().getTimeInMillis();
        int mYear = markedDate.get(Calendar.YEAR);
        int mMonth = markedDate.get(Calendar.MONTH);
        int mDay = markedDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getActivity(), onDateSetListener, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(maxTime);
        datePickerDialog.show();
    }


    private void onAddPhotoClickListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    PERMISSION_CAMERA_REQUEST_CODE);
        } else {
            EasyImage.openChooserWithGallery(this, null, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    EasyImage.openChooserWithGallery(this, null, 0);
                }
            }
        }
    }


}
