package moranjani.nanitbday.view_models;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import moranjani.nanitbday.Utils.GeneralUtils;

public class MainActivityViewModel extends AndroidViewModel {

    public static final String NAME_KEY = "moranjani.nanitbday.view_models.name_key";
    public static final String BDATE_KEY = "moranjani.nanitbday.view_models.bdate_key";
    public static final String URI_KEY = "moranjani.nanitbday.view_models.uri_key";


    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<Long> birthDate = new MutableLiveData<>();
    String pictureUri;
    public MutableLiveData<Boolean> buttonEnabled = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        init();
    }


    private void init() {
        SharedPreferences sharedPref = GeneralUtils.getSharedPrefs(getApplication());
        name.setValue(sharedPref.getString(NAME_KEY, null));
        birthDate.setValue(sharedPref.getLong(BDATE_KEY, -1));
        pictureUri = sharedPref.getString(URI_KEY, null);
    }

    public void persistData() {
        SharedPreferences sharedPref = GeneralUtils.getSharedPrefs(getApplication());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NAME_KEY, name.getValue()).
                putLong(BDATE_KEY, birthDate.getValue() == null ? -1L : birthDate.getValue()).
                putString(URI_KEY, pictureUri).
                apply();
    }


    public String getPictureUri() {
        return pictureUri;
    }


    public MutableLiveData<Boolean> getButtonEnabled() {
        buttonEnabled.setValue(!TextUtils.isEmpty(name.getValue()) && birthDate.getValue()!= null && birthDate.getValue() > 0 );
        return buttonEnabled;
    }
}
