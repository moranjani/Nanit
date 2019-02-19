package moranjani.nanitbday.view_models;

import android.app.Application;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import moranjani.nanitbday.R;
import moranjani.nanitbday.Utils.DateConverter;

public class BirthdayFragmentViewModel extends AndroidViewModel {


    private static final int THEME_ELEPHANT = 0;
    private static final int THEME_FOX = 1;
    private static final int THEME_PELICAN = 2;

    private int theme = -1;
    private int mainImageViewRes;
    private int picPlaceHolderImageRes;
    private int cameraImageRes;

    private String nameTemplate;
    private long birthDate;
    private String ageNumberToPresent;

    private String yearsOrMonths;
    private int ageRes;




    public BirthdayFragmentViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        setRandomTheme();
        nameTemplate = getApplication().getString(R.string.birthday_title);
    }

    private void setRandomTheme() {
        if (theme == -1) {
            theme = chooseRandomTheme();
        }
        switch (theme) {
            case THEME_ELEPHANT: {
                mainImageViewRes = R.drawable.android_elephant_popup;
                picPlaceHolderImageRes = R.drawable.default_place_holder_yellow;
                cameraImageRes = R.drawable.camera_icon_yellow;
                break;

            }
            case THEME_FOX: {
                mainImageViewRes = R.drawable.android_fox_popup;
                picPlaceHolderImageRes = R.drawable.default_place_holder_green;
                cameraImageRes = R.drawable.camera_icon_green;
                break;
            }
            case THEME_PELICAN: {
                mainImageViewRes = R.drawable.android_pelican_popup;
                picPlaceHolderImageRes = R.drawable.default_place_holder_blue;
                cameraImageRes = R.drawable.camera_icon_blue;
                break;

            }
        }
    }

    private int chooseRandomTheme() {
        int max = THEME_PELICAN;
        Random r = new Random();
        return  r.nextInt(max + 1);
    }

    private void calculateBirthDay() {
        int diffInYears = DateConverter.getDiffYears(birthDate, System.currentTimeMillis());
        int diffInMonths = DateConverter.getDiffMonths(birthDate, System.currentTimeMillis());
        String ageDesription = getApplication().getString(R.string.age_desription);
        if (diffInMonths < 0) {
            diffInMonths = 12 + diffInMonths;
        }

        if (diffInYears == 0) {
            yearsOrMonths = String.format(ageDesription, getApplication().getString(R.string.months));
            ageNumberToPresent = String.valueOf(diffInMonths);
        } else if (diffInYears > 0) {
            yearsOrMonths = String.format(ageDesription, getApplication().getString(R.string.years));
            if (diffInMonths == 6) {
                ageNumberToPresent = String.valueOf(1.5);
            } else {
                ageNumberToPresent = String.valueOf(diffInYears);
            }
        }
    }

    private void setCalculatedBirthDay() {
        calculateBirthDay();
        switch (ageNumberToPresent) {
            case ("0"): {
                ageRes = R.drawable.n0;
                break;
            }
            case ("1"): {
                ageRes = R.drawable.n1;
                break;
            }
            case ("1.5"): {
                ageRes = R.drawable.n1_half;
                break;
            }
            case ("2"): {
                ageRes = R.drawable.n2;
                break;
            }
            case ("3"): {
                ageRes = R.drawable.n3;
                break;
            }
            case ("4"): {
                ageRes = R.drawable.n4;
                break;
            }
            case ("5"): {
                ageRes = R.drawable.n5;
                break;
            }
            case ("6"): {
                ageRes = R.drawable.n6;
                break;
            }
            case ("7"): {
                ageRes = R.drawable.n7;
                break;
            }
            case ("8"): {
                ageRes = R.drawable.n8;
                break;
            }
            case ("9"): {
                ageRes = R.drawable.n9;
                break;
            }
            case ("10"): {
                ageRes = R.drawable.n10;
                break;
            }
            case ("11"): {
                ageRes = R.drawable.n11;
                break;
            }
            case ("12"): {
                ageRes = R.drawable.n12;
                break;
            }
        }
    }





    public int getMainImageViewRes() {
        return mainImageViewRes;
    }

    public int getPicPlaceHolderImageRes() {
        return picPlaceHolderImageRes;
    }

    public int getCameraImageRes() {
        return cameraImageRes;
    }

    public String getNameTemplate() {
        return nameTemplate;
    }

    public void setBirthDate(Long value) {
        birthDate = value;
        setCalculatedBirthDay();
    }

    public String getYearsOrMonths() {
        return yearsOrMonths;
    }

    public int getAgeRes() {
        return ageRes;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int anInt) {
        theme = anInt;
    }
}
