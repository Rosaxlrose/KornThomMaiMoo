package com.example.nakornprathom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.Locale;
import android.view.WindowManager;

public class ContactActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton menuButton;
    private TextView langTH, langCH, langEN;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        loadLanguage(); // ✅ โหลดค่าภาษาที่บันทึกไว้ก่อนแสดง UI

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        langTH = findViewById(R.id.langTH);
        langCH = findViewById(R.id.langCH);
        langEN = findViewById(R.id.langEN);

        langTH.setOnClickListener(view -> changeLanguage("th"));
        langCH.setOnClickListener(view -> changeLanguage("zh"));
        langEN.setOnClickListener(view -> changeLanguage("en"));

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuButton = findViewById(R.id.menuButton);

        menuButton.setOnClickListener(view -> drawerLayout.openDrawer(navigationView));

        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;

            if (item.getItemId() == R.id.nav_home) {
                intent = new Intent(ContactActivity.this, HomeActivity.class);
            } else if (item.getItemId() == R.id.nav_about) {
                intent = new Intent(ContactActivity.this, AboutActivity.class);
            } else if (item.getItemId() == R.id.nav_sakkara) {
                intent = new Intent(ContactActivity.this, SakkaraActivity.class);
            } else if (item.getItemId() == R.id.nav_festival) {
                intent = new Intent(ContactActivity.this, FestivalActivity.class);
            } else if (item.getItemId() == R.id.nav_contact) {
                intent = new Intent(ContactActivity.this, ContactActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                finish(); // ✅ ปิดหน้าเดิมเพื่อให้ภาษาคงอยู่
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void changeLanguage(String langCode) {
        // ✅ บันทึกค่าภาษาใน SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Selected_Language", langCode);
        editor.apply();

        // ✅ อัปเดตภาษา UI
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // ✅ รีโหลดหน้าเพื่ออัปเดตเนื้อหา
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
}