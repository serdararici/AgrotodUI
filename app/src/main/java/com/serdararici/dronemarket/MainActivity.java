package com.serdararici.dronemarket;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.serdararici.dronemarket.databinding.ActivityMainBinding;
import com.serdararici.dronemarket.ui.SteeringWheelView;
import com.serdararici.dronemarket.ui.fragment.MainFragment;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;
import com.serdararici.dronemarket.utils.NetworkChangeReceiver;

import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver.NetworkStatusListener{
    private ActivityMainBinding binding;
    private MainViewModel viewModelMain;
    private Handler handler;
    private Runnable runnable;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private NetworkChangeReceiver networkChangeReceiver;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiReceiver;

    private Handler handlerStopwatch = new Handler();
    private boolean isRunning = false;
    private long startTime = 0;
    private long elapsedTime = 0;

    MediaPlayer mediaPlayer;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String LANGUAGE_KEY = "language";
    private static final String DEFAULT_LANGUAGE = "en";

    boolean isWorkStart = false;
    boolean isLineSelect = false;
    boolean isWorkSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelMain = new ViewModelProvider(this).get(MainViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        //wifi güncelleme
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // WiFi dinleyicisini kaydet
        registerWifiReceiver();

        // Konum izni kontrol et
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            updateNetworkInfo();  // İzin verilmişse güncelle
        }

        viewModelMain.selectedField.observe(this,field -> {
            if (field != null) {
                binding.tvFieldName.setText(field.getFieldName());
                String processedArea = Double.toString(field.getProcessedArea()) + " Ha";
                binding.tvFieldArea.setText(processedArea);
            } else {
                binding.tvFieldName.setText(getString(R.string.field_name));
                binding.tvFieldArea.setText("0.0 Ha");
            }

        });

        viewModelMain.selectedTool.observe(this, tool -> {
            if (tool != null) {
                String toolWidth = String.valueOf(tool.getToolWidth()) + " CM";
                binding.tvWidth.setText(toolWidth);
            } else {
                binding.tvWidth.setText("0 CM");
            }

        });

        viewModelMain.selectedLine.observe(this , line -> {
            if (line != null) {
                Log.e("line", "notNull");
                binding.tvlineNameMain.setText(line.getLineName());
                binding.cardViewOpenABLine.setCardBackgroundColor(getColor(R.color.my_light_primary));
                isLineSelect = true;
            } else {
                binding.cardViewOpenABLine.setCardBackgroundColor(getColor(R.color.blue));
                isLineSelect = false;
            }
        });

        viewModelMain.isWorkSelect.observe(this, result -> {
            Log.e("MainActivity", "isWorkSelect changed: " + result);
            if(result != null) {
                if (result == true) {
                    isWorkSelect = true;
                    binding.ivWheel.setVisibility(View.VISIBLE);
                    binding.cardViewEndWork.setVisibility(View.VISIBLE);
                    binding.cardViewOpenABLine.setVisibility(View.VISIBLE);
                    binding.cardViewOpenField.setVisibility(View.GONE);

                    if(!isLineSelect) {
                        binding.tvlineNameMain.setText(getString(R.string.selectABLine));
                    }
                } else {
                    isWorkSelect = false;
                    binding.ivWheel.setVisibility(View.GONE);
                    binding.cardViewEndWork.setVisibility(View.GONE);
                    binding.cardViewOpenABLine.setVisibility(View.GONE);
                    binding.cardViewOpenField.setVisibility(View.VISIBLE);

                    binding.tvlineNameMain.setText("");
                }
            }
        });

        viewModelMain.isWorkStart.observe(this, result -> {
            if (result != null) {
                isWorkStart = result;
                if (!result) {
                    binding.ivWheel.setImageResource(R.drawable.black_wheel_icon);
                }
            }
        });




        // MapFragment'i yükleyin
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerViewMain, new MainFragment())
                .commit();

        // Tam ekran modu aktif et
        hideSystemUI();

        // Ekran tıklanma olayını dinle ve sistem çubuklarını göster/gizle
        findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSystemUIVisible()) {
                    hideSystemUI();
                } else {
                    showSystemUI();
                }

                // Klavyeyi kapat
                hideKeyboard();
            }
        });

        // Saat işlemleri
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String currentTime = sdf.format(new Date());
                binding.tvTime.setText(currentTime);
                handler.postDelayed(this, 1000); // Update every second
            }
        };
        handler.post(runnable);



        EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.cardViewOpenField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavInflater inflater = navController.getNavInflater();
                NavGraph newGraph = inflater.inflate(R.navigation.main_activity_nav);
                navController.setGraph(newGraph);
                binding.drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        binding.cardViewOpenABLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavInflater inflater = navController.getNavInflater();
                NavGraph newGraph = inflater.inflate(R.navigation.ab_line_nav);
                navController.setGraph(newGraph);
                // Ayrıca Drawer'ı açmak için:
                binding.drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        binding.cardViewEndWork.setOnClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle(R.string.workWillEnd)
                    .setMessage(R.string.confirmEndWorkMessage)
                    .setPositiveButton(R.string.confirm, (dialog, which) -> {
                        //Gönderilen verileri sıfırla
                        viewModelMain.selectField(null);
                        viewModelMain.selectTool(null);
                        viewModelMain.selectLine(null);
                        viewModelMain.isWorkSelect.setValue(false);
                        viewModelMain.isWorkStart.setValue(false);

                        resetTimer();
                    })
                    .setNegativeButton(R.string.cancel, (dialog, which) -> {
                        // dialog kapat
                        dialog.dismiss();
                    })
                    .show();

            //İş bitirildiğinde nav değişsin
            NavInflater inflater = navController.getNavInflater();
            NavGraph newGraph = inflater.inflate(R.navigation.main_activity_nav);
            navController.setGraph(newGraph);
        });

        // MediaPlayer'i oluştur ve ses dosyasını bağla
        mediaPlayer = MediaPlayer.create(this, R.raw.start_sound_beep);
        binding.ivWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLineSelect) {
                    //Kronometre başlama ve sonlandırma
                    if (isRunning) {
                        stopTimer();
                    } else {
                        startTimer();
                    }
                    if (!isWorkStart) {
                        Log.e("isStartWork", "isWorkStart"+ isWorkStart);
                        binding.ivWheel.setImageResource(R.drawable.green_wheel_icon);
                        isWorkStart = true;
                        // Ses çalmaya başla
                        if (mediaPlayer != null) {
                            mediaPlayer.start();
                        }
                    } else {
                        binding.ivWheel.setImageResource(R.drawable.black_wheel_icon);
                        isWorkStart = false;
                        // Ses çalmaya başla
                        if (mediaPlayer != null) {
                            mediaPlayer.start();
                        }
                    }
                } else {
                    // AlertDialog oluştur
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.warning)
                            .setMessage(getString(R.string.selectABLine))
                            .create();

                    // AlertDialog'u göster
                    alertDialog.show();

                    // 1 saniye (1000 ms) sonra dialogu kapat
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (alertDialog.isShowing()) {
                                alertDialog.dismiss(); // Dialogu kapat
                            }
                        }
                    }, 1500); // 1000 milisaniye (1 saniye)
                }
            }
        });

        // SharedPreferences başlatma
        sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Dil ayarını SharedPreferences'den çekiyoruz
        String language = sharedPreferences.getString(LANGUAGE_KEY, "");

        // Eğer SharedPreferences'de daha önce bir seçim yoksa, cihazın dilini kontrol ediyoruz
        if (language.isEmpty()) {
            Locale deviceLocale = Locale.getDefault();
            if (deviceLocale.getLanguage().equals("tr")) {
                language = "tr"; // Cihaz dili Türkçe ise Türkçe başlat
            } else {
                language = DEFAULT_LANGUAGE; // Diğer dillerde İngilizce başlat
            }
            editor.putString(LANGUAGE_KEY, language);
            editor.apply();
        }
        // Uygulama dilini yalnızca gerçekten değiştiğinde ayarla
        if (!language.equals(getCurrentLanguage())) {
            setLocale(language);
        }
        // Bayrak ayarlama
        updateFlag(language);

        binding.cvLanguage.setOnClickListener(view -> {
            //İş seçili ise dil değişikliği yapılamıcak
            if(!isWorkSelect) {
                // Dil değiştirme işlemi
                String currentLanguage = sharedPreferences.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE);
                if (currentLanguage.equals("en")) {
                    setLanguage("tr");
                } else {
                    setLanguage("en");
                }
            } else {
                // AlertDialog oluştur
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.warning)
                        .setMessage(getString(R.string.changeLanguageMessage))
                        .create();

                // AlertDialog'u göster
                alertDialog.show();

                // 1 saniye (1000 ms) sonra dialogu kapat
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss(); // Dialogu kapat
                        }
                    }
                }, 1500); // 1000 milisaniye (1 saniye)
            }

        });


        /*
        // üçgen renklerini duruma göre değiştirmek için kullanılabilir
        boolean condition = true;
        if (condition == true){
            binding.ivTriAngleLeft1.setColorFilter(getColor(R.color.red));
        }else {
            binding.ivTriAngleLeft1.setColorFilter(getColor(R.color.triangle));
        }

         */


    }


    // drawer kapatmak için fonksiyon - toolFragment içerisnde geri butonu çalıştırmak için
    public void closeDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END);
        }
    }


    private void hideSystemUI() {
        // Sistem çubuklarını gizle
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        // Sistem çubuklarını göster
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    private boolean isSystemUIVisible() {
        int visibility = getWindow().getDecorView().getSystemUiVisibility();
        return (visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0;
    }

    // Klavyeyi kapatmak için fonksiyon
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            // Eğer odaklanmış bir view yoksa, root view'e odaklanarak klavyeyi kapat
            View rootView = findViewById(android.R.id.content);
            if (rootView != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
            }
        }
    }

    // Sayaç başlatma fonksiyonu
    private void startTimer() {
        //int originalTextColor = binding.tvFieldTime.getCurrentTextColor();
        //TextView'in rengini ve boyutunu değiştir
        binding.tvFieldTime.setTextColor(Color.WHITE); // Kırmızı renk
        binding.tvFieldTime.setTextSize(20); // Yeni boyut (örneğin 30sp)
        Handler handlerTimerAnimate = new Handler();
        handlerTimerAnimate.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.tvFieldTime.setTextColor(Color.BLACK); // Eski renk
                binding.tvFieldTime.setTextSize(16); // Eski boyut
            }
        }, 1000);
        isRunning = true;
        startTime = System.currentTimeMillis() - elapsedTime;
        handlerStopwatch.postDelayed(runnableStopwatch, 0);
    }
    // Sayaç durdurma fonksiyonu
    private void stopTimer() {
        isRunning = false;
        elapsedTime = System.currentTimeMillis() - startTime;
        handlerStopwatch.removeCallbacks(runnableStopwatch);
    }
    // Sayaç sıfırlama fonksiyonu
    private void resetTimer() {
        isRunning = false;
        startTime = 0;
        elapsedTime = 0;
        binding.tvFieldTime.setText("00:00");
        handlerStopwatch.removeCallbacks(runnableStopwatch);
    }
    // Sayaç güncelleyen Runnable
    private Runnable runnableStopwatch = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTextView(elapsedTime);
            handlerStopwatch.postDelayed(this, 1000); // 1 saniyede bir güncelle
        }
    };
    // TextView'i güncelleyen fonksiyon (00:00 formatında dakika:saniye)
    private void updateTextView(long time) {
        int minutes = (int) (time / 1000 / 60);
        int seconds = (int) (time / 1000) % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        binding.tvFieldTime.setText(timeString);
    }

    // Mevcut dili döndüren metod
    private String getCurrentLanguage() {
        return Locale.getDefault().getLanguage();
    }

    // Dil değiştirme ve kaydetme
    private void setLanguage(String languageCode) {
        // Seçilen dili SharedPreferences'e kaydetme
        editor.putString(LANGUAGE_KEY, languageCode);
        editor.apply();

        // Uygulama dilini güncelleme
        setLocale(languageCode);

        // Bayrağı güncelleme
        updateFlag(languageCode);
    }

    // Locale'i ayarlayan ve activity'yi yeniden yükleyen metod
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        // Yeni Locale ayarını yapılandırmaya ekle
        config.setLocale(locale);

        // Güncellenmiş ayarları uygula
        resources.updateConfiguration(config, displayMetrics);

        // Ekranı yeniden yükle (Opsiyonel, dil değişikliğinin anında yansıması için)
        recreate();
    }

    private void updateFlag(String languageCode) {
        // Dil koduna göre bayrağı ayarlama
        if (languageCode.equals("tr")) {
            binding.ivLanguage.setImageResource(R.drawable.turkish_flag_icon); // Türk bayrağı
            binding.tvLanguage.setText("TR");
        } else {
            binding.ivLanguage.setImageResource(R.drawable.english_flag_icon); // İngiliz bayrağı
            binding.tvLanguage.setText("EN");
        }
    }


    /////////////////////////////// Wifi Güncelleme İşlemleri
    @Override
    protected void onStart() {
        super.onStart();
        networkChangeReceiver = new NetworkChangeReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);

        // İzin kontrolü ve başlangıç güncellemesi
        checkLocationPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeReceiver);
        unregisterReceiver(wifiReceiver);  // WiFi dinleyicisini kaldır
    }

    private void registerWifiReceiver() {
        wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateNetworkInfo(); // Sinyal değiştiğinde güncelle
            }
        };
        IntentFilter filter = new IntentFilter(WifiManager.RSSI_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateNetworkInfo();  // İzin verildiyse güncelle
            } else {
                // İzin verilmezse kullanıcıya bilgi verin
                Toast.makeText(this, "Konum izni gerekli!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            updateNetworkInfo();  // İzin verilmişse güncelle
        }
    }

    @Override
    public void onNetworkStatusChanged(NetworkInfo activeNetwork) {
        updateNetworkInfo();  // Her bağlantı değiştiğinde güncelle
    }

    private void updateNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // WiFi bağlantısı
                //WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                String ssid = wifiManager.getConnectionInfo().getSSID();

                if (ssid != null && ssid.length() > 0) {
                    ssid = ssid.replace("\"", "");
                }
                // SSID'nin uzunluğunu kontrol et
                if (ssid.length() > 10) {
                    ssid = ssid.substring(0, 10) + ".."; // İlk 7 karakteri al ve ".." ekle
                }

                int wifiLevel = WifiManager.calculateSignalLevel(wifiManager.getConnectionInfo().getRssi(), 5);
                updateWifiLevel(wifiLevel);

                // WiFi SSID'yi göster
                binding.tvWifiLevel.setText(ssid);
                Log.e("Bağlı olduğunuz WiFi: ",ssid);
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // Mobil bağlantı
                binding.ivWifi.setImageResource(R.drawable.vector_wifi);
                binding.tvWifiLevel.setText(activeNetwork.getSubtypeName());
            }
        } else {
            binding.ivWifi.setImageResource(R.drawable.baseline_wifi_off_24);
            Toast.makeText(this, getString(R.string.noInternetConnection), Toast.LENGTH_LONG).show();
            binding.tvWifiLevel.setText(getString(R.string.noInternet));
            //networkInfo.setText("İnternete bağlı değil");
        }
    }

    private void updateWifiLevel(int wifiLevel) {
        switch (wifiLevel) {
            case 0:
                binding.ivWifi.setImageResource(R.drawable.baseline_wifi_1_bar_2_black);
                break;
            case 1:
                binding.ivWifi.setImageResource(R.drawable.baseline_wifi_2_bar_24_black);
                break;
            case 2:
                binding.ivWifi.setImageResource(R.drawable.baseline_wifi_2_bar_24_black);
                break;
            case 3:
                binding.ivWifi.setImageResource(R.drawable.vector_wifi);
                break;
            case 4:
                binding.ivWifi.setImageResource(R.drawable.vector_wifi);
                break;
        }
    }
    //////////////////////////////////////////////////

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        // MediaPlayer'i serbest bırak
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }



}