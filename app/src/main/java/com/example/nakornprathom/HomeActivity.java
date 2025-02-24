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

public class HomeActivity extends AppCompatActivity {

    private TextView titleText, subtitleText;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton menuButton;
    private TextView langTH, langCH, langEN;
    private Button startButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ✅ โหลดค่าภาษาเริ่มต้นก่อนสร้าง Layout
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        loadLanguage();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // เชื่อมโยง UI
        titleText = findViewById(R.id.titleText);
        subtitleText = findViewById(R.id.subText);
        startButton = findViewById(R.id.startButton);
        langTH = findViewById(R.id.langTH);
        langCH = findViewById(R.id.langCH);
        langEN = findViewById(R.id.langEN);

        // ตั้งค่าคลิกเปลี่ยนภาษา
        langTH.setOnClickListener(view -> changeLanguage("th"));
        langCH.setOnClickListener(view -> changeLanguage("zh"));
        langEN.setOnClickListener(view -> changeLanguage("en"));

        // ✨ เพิ่มเมนู Sidebar ✨
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuButton = findViewById(R.id.menuButton);

        // คลิกปุ่มเมนูเพื่อเปิด Drawer
        menuButton.setOnClickListener(view -> drawerLayout.openDrawer(navigationView));

        // คลิกเลือกเมนูใน Sidebar
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;

                if (item.getItemId() == R.id.nav_home) {
                    intent = new Intent(HomeActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_about) {
                    intent = new Intent(HomeActivity.this, AboutActivity.class);
                } else if (item.getItemId() == R.id.nav_sakkara) {
                    intent = new Intent(HomeActivity.this, SakkaraActivity.class);
                } else if (item.getItemId() == R.id.nav_festival) {
                    intent = new Intent(HomeActivity.this, FestivalActivity.class);
                } else if (item.getItemId() == R.id.nav_contact) {
                    intent = new Intent(HomeActivity.this, ContactActivity.class);
                }

                if (intent != null) {
                    startActivity(intent);
                    finish(); // ปิดหน้าเดิมเมื่อเปิดหน้าใหม่
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        // กดปุ่ม "เริ่มต้น" แล้วไปหน้า AboutActivity
        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        // ✅ อัปเดต UI ให้ตรงกับค่าภาษาที่เลือก
        updateUI();
    }

    private void changeLanguage(String langCode) {
        // ✅ บันทึกค่าภาษาใน SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Selected_Language", langCode);
        editor.apply();

        // ✅ เปลี่ยนภาษา UI
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // ✅ รีโหลดหน้า
        recreate();
    }

    private void loadLanguage() {
        // ✅ โหลดค่าภาษาเดิมที่เคยเลือกไว้
        String langCode = sharedPreferences.getString("Selected_Language", "th"); // ค่าเริ่มต้นเป็นไทย
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void updateUI() {
        titleText.setText(R.string.app_title);
        subtitleText.setText(R.string.app_subtitle);
        startButton.setText(R.string.start_button);
        updateNavigationMenu();
    }

    private void updateNavigationMenu() {
        navigationView.getMenu().clear(); // ล้างเมนูเก่า
        navigationView.inflateMenu(R.menu.navigation_menu); // โหลดเมนูใหม่ (ให้ใช้ค่าภาษาใหม่)
    }
}