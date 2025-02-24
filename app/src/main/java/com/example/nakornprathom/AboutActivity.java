package com.example.nakornprathom;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.Intent;
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

public class AboutActivity extends AppCompatActivity {

    private TextView titleText, subtitleText;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton menuButton;
    private TextView langTH, langCH, langEN;
    private Button startButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // โหลดภาษาที่เลือกไว้ล่าสุด
        loadLanguage();

        // เชื่อมโยง UI
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
                int id = item.getItemId();
                Intent intent = null;

                if (id == R.id.nav_home) {
                    intent = new Intent(AboutActivity.this, HomeActivity.class);
                } else if (id == R.id.nav_about) {
                    intent = new Intent(AboutActivity.this, AboutActivity.class);
                } else if (id == R.id.nav_sakkara) {
                    intent = new Intent(AboutActivity.this, SakkaraActivity.class);
                } else if (id == R.id.nav_festival) {
                    intent = new Intent(AboutActivity.this, FestivalActivity.class);
                } else if (id == R.id.nav_contact) {
                    intent = new Intent(AboutActivity.this, ContactActivity.class);
                }

                if (intent != null) {
                    startActivity(intent);
                    finish(); // ปิดหน้าเดิมเมื่อเปิดหน้าใหม่
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void changeLanguage(String langCode) {
        // บันทึกค่าภาษาใน SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Selected_Language", langCode);
        editor.apply();

        // เปลี่ยนภาษา UI
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
}
