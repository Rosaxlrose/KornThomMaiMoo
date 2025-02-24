package com.example.nakornprathom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.Locale;
import android.view.WindowManager;

public class Sakkara5Activity extends AppCompatActivity {

    private TextView titleText, subtitleText;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton menuButton;
    private TextView langTH, langCH, langEN;
    private Button backButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        loadLanguage(); // ✅ โหลดค่าภาษาก่อนสร้าง UI

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sakkara5);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // เชื่อมโยง UI
        titleText = findViewById(R.id.title);
        subtitleText = findViewById(R.id.title2);
        langTH = findViewById(R.id.langTH);
        langCH = findViewById(R.id.langCH);
        langEN = findViewById(R.id.langEN);
        backButton = findViewById(R.id.startButton2);

        // ตั้งค่าคลิกเปลี่ยนภาษา
        langTH.setOnClickListener(view -> changeLanguage("th"));
        langCH.setOnClickListener(view -> changeLanguage("zh"));
        langEN.setOnClickListener(view -> changeLanguage("en"));

        // ✨ เมนู Sidebar ✨
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuButton = findViewById(R.id.menuButton);

        // เปิดเมนู
        menuButton.setOnClickListener(view -> drawerLayout.openDrawer(navigationView));

        // เมนูนำทาง
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;

                if (item.getItemId() == R.id.nav_home) {
                    intent = new Intent(Sakkara5Activity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_about) {
                    intent = new Intent(Sakkara5Activity.this, AboutActivity.class);
                } else if (item.getItemId() == R.id.nav_sakkara) {
                    intent = new Intent(Sakkara5Activity.this, SakkaraActivity.class);
                } else if (item.getItemId() == R.id.nav_festival) {
                    intent = new Intent(Sakkara5Activity.this, FestivalActivity.class);
                } else if (item.getItemId() == R.id.nav_contact) {
                    intent = new Intent(Sakkara5Activity.this, ContactActivity.class);
                }

                if (intent != null) {
                    startActivity(intent);
                    finish(); // ปิดหน้าปัจจุบัน
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        // กลับไปหน้า Sakkara4Activity
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(Sakkara5Activity.this, Sakkara4Activity.class);
            startActivity(intent);
        });

        // ✅ อัปเดต UI ให้ตรงกับค่าภาษาที่เลือก
        updateUI();
    }

    private void changeLanguage(String langCode) {
        // บันทึกค่าภาษา
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Selected_Language", langCode);
        editor.apply();

        // เปลี่ยนภาษา
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // รีโหลดหน้า
        recreate();
    }

    private void loadLanguage() {
        String langCode = sharedPreferences.getString("Selected_Language", "th"); // ค่าเริ่มต้นเป็นไทย
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void updateUI() {
        titleText.setText(R.string.worship_points);
        subtitleText.setText(R.string.golden_takian_cave);
        backButton.setText(R.string.back);
    }
}